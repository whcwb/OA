package com.ldz.wechat.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.YhService;
import com.ldz.util.commonUtil.JwtUtil;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.CoachManagementService;
import com.ldz.wechat.service.TraineeInformationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问接口控制
 *
 * @author 李彬彬
 *
 */
@Slf4j
public class AccessInterceptor extends HandlerInterceptorAdapter {

	//学员方法
	private TraineeInformationService traineeService;
	//教练
	private CoachManagementService coachService;

	//boss
	private YhService yhService;

	private StringRedisTemplate redisDao;

	public AccessInterceptor(StringRedisTemplate redisTemp, TraineeInformationService traineeService, CoachManagementService coachService,YhService yhService) {
		this.traineeService=traineeService;
		this.coachService=coachService;
		this.redisDao = redisTemp;
		this.yhService = yhService;
	}


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 查看请求类型
		String method = request.getMethod();
		if (method.equals("OPTIONS")) {
			// 如果收到的是跨域预请求消息，直接响应，返回true，以便后续跨域请求成功
			return true;
		}

		String userid = request.getHeader("userid");
		String userrole = request.getHeader("userrole");
		String token = request.getHeader("token");
		String url = request.getHeader("url");
		log.debug("openid=" + request.getHeader("openid")+"---------------------------------------------------");
		if (token == null){
			token = request.getParameter("token");
		}
		if (userid == null){
			userid = request.getParameter("userid");
		}

		if (StringUtils.isEmpty(userrole)){
			userrole = request.getParameter("userrole");
		}

		if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(token)|| StringUtils.isEmpty(userrole)) {
			request.getRequestDispatcher("/pub/authFiled").forward(request, response);
			return false;
		}
		log.debug("访问地址[{}], 请求openid[{}],请求token[{},header请求地址[{}]]", request.getRequestURI(), userid, token, url);

		try {
			//1、验证访问者是否合法
			String userId = JwtUtil.getClaimAsString(token, "userId");
			log.debug("userId=" + userId);
			if (!userid.equals(userId)) {
				request.getRequestDispatcher("/pub/authFiled").forward(request, response);
				log.debug("用户验证失败");
				return false;
			}
			//2、获取访问者的用户角色  用户角色  1、学员  2、教练 3.boss
			String userRole= JwtUtil.getClaimAsString(token, "userRole");
			log.debug("userRole=" + userRole);
			if (!userrole.equals(userRole)) {
				request.getRequestDispatcher("/pub/authFiled").forward(request, response);
				log.debug("用户角色验证失败");
				return false;
			}
			TraineeInformation traineeInfo =null;
			CoachManagement coachInfo=null;
			SysYh sysUser = null;
			//2、验证用户状态
			if(StringUtils.equals(userRole,"1")){
				traineeInfo = traineeService.findById(userid);
				if(traineeInfo==null){
					request.getRequestDispatcher("/pub/authFiled").forward(request, response);
					log.debug("学员用户鉴权失败！");
					return false;
				}
			}else if(StringUtils.equals(userRole,"2")){
				coachInfo=coachService.findById(userid);
				if(coachInfo==null){
					request.getRequestDispatcher("/pub/authFiled").forward(request, response);
					log.debug("教练员用户鉴权失败！");
					return false;
				}
				if(StringUtils.equals(coachInfo.getCoachStatus(),"10")){
					request.getRequestDispatcher("/pub/authFiled").forward(request, response);
					log.debug(coachInfo.getCoachName()+"教练员您好，您已经离职，不能再登录系统了。");
					return false;
				}
			}else if(StringUtils.equals(userRole,"3")){
				sysUser = yhService.findById(userid);
				if(sysUser == null){
					request.getRequestDispatcher("/pub/authFiled").forward(request, response);
					log.debug("系统用户鉴权失败！");
					return false;
				}
			}
			String value = redisDao.boundValueOps("wechat"+userid).get();
			log.debug("value=" + value);
			log.debug("token=" + token);
			if (StringUtils.isEmpty(value) || !value.equals(token)) {
				request.getRequestDispatcher("/pub/authFiled").forward(request, response);
				log.debug("用户验证失败");
				return false;
			}

			request.setAttribute("wechatTraineeUserInfo", traineeInfo);
			request.setAttribute("wechatCoachUserInfo", coachInfo);
			request.setAttribute("wechatUserInfo", sysUser);
			if(StringUtils.equals(userRole,"3")){
				request.setAttribute("orgCode", sysUser.getJgdm());
			}
			log.debug("boundValueOps");
			String userInfoJson = redisDao.boundValueOps("wechat"+userid + "-appUserInfo").get();
			log.debug("boundValueOps");
			if (StringUtils.isEmpty(userInfoJson)) {
				request.getRequestDispatcher("/pub/authFiled").forward(request, response);
				return false;
			}
			ObjectMapper mapper = new ObjectMapper();
			if(StringUtils.equals(userRole,"1")){
				TraineeInformation userInfo = mapper.readValue(userInfoJson, TraineeInformation.class);
				log.debug("userInfoJson:" + userInfo);
			}else if(StringUtils.equals(userRole,"2")){
				CoachManagement userInfo = mapper.readValue(userInfoJson, CoachManagement.class);
				log.debug("userInfoJson:" + userInfo);
			}
			log.debug("userInfoJson:" + userInfoJson);
		} catch (Exception e) {
			request.getRequestDispatcher("/pub/authFiled").forward(request, response);
			return false;
		}
		return super.preHandle(request, response, handler);
	}



}
