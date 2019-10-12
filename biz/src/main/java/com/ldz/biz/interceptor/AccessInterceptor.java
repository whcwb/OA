package com.ldz.biz.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.mapper.SysYhJsMapper;
import com.ldz.sys.model.SysGn;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.GnService;
import com.ldz.sys.service.YhService;
import com.ldz.util.commonUtil.JwtUtil;
import com.ldz.util.spring.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 访问接口控制
 *
 * @author 李彬彬
 *
 */
@Slf4j
public class AccessInterceptor extends HandlerInterceptorAdapter {

	private GnService gnService;

	private YhService yhService;

	private SysYhJsMapper sysYhJsMapper;

	private StringRedisTemplate redisDao;

	// 只要登录的用户都能访问
	private List<String> whiteList = Arrays.asList("/api/traineeinformation/pager","/api/jg/getOrgPath","/api/gn/getPermissionTreeWithChecked","/api/gn/getRolePermissionTree","/api/jg/getCurrentOrgTree","/api/gn/getMenuTree","/api/jg/pager","/api/zd/pager","/api/jg/getTree","/api/gn/getMenuTree","/api/jg/pager","/api/jg/getOrgTree","/api/jg/getOrgTree","/api/clsbyxsjjl/history","/api/clsbyxsjjl/history","/api/jg/getCurrentUserOrgTree","/api/yh/mdfPwd","/api/traineeinformation/exportTestCharge","/api/exception/dashboard");

	public AccessInterceptor() {
	}

	public AccessInterceptor(StringRedisTemplate redisTemp) {
		this.gnService = SpringContextUtil.getBean(GnService.class);
		this.yhService = SpringContextUtil.getBean(YhService.class);
		this.sysYhJsMapper = SpringContextUtil.getBean(SysYhJsMapper.class);
		this.redisDao = redisTemp;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*long begin_nao_time = System.currentTimeMillis();
		System.out.println(request.getRequestURI()+"开始时间:" + begin_nao_time);
		//String realIp = HttpHeadTool.getRealIpAddr(request);
		//request.setAttribute("p_real_ip", realIp);
		request.setAttribute("begin_nao_time", begin_nao_time);*/


		// 查看请求类型
		String method = request.getMethod();
		if (method.equals("OPTIONS")) {
			// 如果收到的是跨域预请求消息，直接响应，返回true，以便后续跨域请求成功
			return true;
		}

		// 测试代码
		// 访问权限值
		// String userid = "1";
		// String token =
		// "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3Y3BtcyIsImF1ZCI6IndjcG1zIiwibG9naW5OYW1lIjoiYWRtaW5pIiwiaXNzIjoid2NwbXMiLCJ1c2VySWQiOiIxIn0.vok82zo-zveVlXrjKxgJiRRdXqKGpv1PFBngxhyR-Cg";
		String userid = request.getHeader("userid");
		String token = request.getHeader("token");
		String url = request.getHeader("url");

		if (token == null)
		{
			token = request.getParameter("token");
		}
		if (userid == null)
		{
			userid = request.getParameter("userid");
		}

		if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(token)){
			request.getRequestDispatcher("/authFiled").forward(request, response);
			return false;
		}
		log.debug("访问地址[{}]", request.getRequestURI());

		// 验证用户状态
		SysYh user = yhService.findById(userid);
		if (!Dict.UserStatus.VALID.getCode().equals(user.getZt())){
			request.getRequestDispatcher("/authFiled").forward(request, response);
			return false;
		}
		try {
			// 验证访问者是否合法
			String userId = JwtUtil.getClaimAsString(token, "userId");
			if (!userid.equals(userId)){
				request.getRequestDispatcher("/authFiled").forward(request, response);
				return false;
			}
			String value = redisDao.boundValueOps(userid).get();
			if (StringUtils.isEmpty(value) || !value.equals(token)){
				request.getRequestDispatcher("/authFiled").forward(request, response);
				return false;
			}
			request.setAttribute("userInfo", user);
			request.setAttribute("orgCode", user.getJgdm());
			request.setAttribute("orgCodes",user.getJgdms());
//			String userInfoJson = redisDao.boundValueOps(userid + "-userInfo").get();
//			ObjectMapper mapper = new ObjectMapper();
//			SysYh userInfo = mapper.readValue(userInfoJson, SysYh.class);
//			if (!whiteList.contains(request.getRequestURI()) && !"su".equals(userInfo.getLx())) { // su 用户可访问所有权限
//					if (!checkPermission(userInfo, request)) {
//						request.getRequestDispatcher("/403").forward(request, response);
//						return false;
//					}
//			}
		} catch (Exception e) {
			return false;
		}

		return super.preHandle(request, response, handler);
	}

	private boolean authCheck(boolean expression,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/999").forward(request, response);
		return true;
	}

	private boolean checkPermission(SysYh user, HttpServletRequest request) {
		return checkPermissionByRedis(user,request);
	}
	private boolean checkPermissionNew(SysYh user, HttpServletRequest request) {
		String redisVal = redisDao.boundValueOps(user.getYhid()+"-apiQz").get();
		if (StringUtils.isEmpty(redisVal)) {
			return false;
		}

		List<String> qzs = Arrays.asList(redisVal.split(","));
		if (CollectionUtils.isEmpty(qzs)){
			return false;
		}

		String apiqz = getApiQz(request.getRequestURI());
		if (StringUtils.isEmpty(apiqz)){
			return false;
		}
		return qzs.contains(apiqz);
	}

	private boolean checkPermissionByRedis(SysYh user, HttpServletRequest request){
		boolean flag =false;
		// 获取用户的所有角色 Id
		List<String> sysYhJs = sysYhJsMapper.findJsIdByYhId(user.getYhid());
		if(CollectionUtils.isEmpty(sysYhJs)){
			return false;
		}
		String apiqz = getApiQz(request.getRequestURI());
		if (StringUtils.isEmpty(apiqz)) {
			return false;
		}
		for(String s : sysYhJs){
			String s1 = redisDao.boundValueOps("permission_" + s).get();
			List<String> list = Arrays.asList(s1.split(","));
			if(CollectionUtils.isNotEmpty(list) && list.contains(apiqz)){
				flag = true;
				break;
			}
		}
		return flag;
	}


	private boolean checkPermissionOld(SysYh user, HttpServletRequest request) {
		List<SysGn> functions = gnService.getUserFunctions(user);
		if (functions == null || functions.size() == 0)
		{
			return false;
		}

		String uri = request.getRequestURI();
		String apiPrefix = uri.substring(0, uri.indexOf("/", 5) + 1);
		for (SysGn function : functions) {
			if (StringUtils.isEmpty(function.getApiQz()))
			{
				continue;
			}
			if (function.getApiQz().contains(apiPrefix))
			{
				return true;
			}
		}
		return false;
	}

	private String getApiQz(String uri){
		String apiPrefix = uri.substring(0, uri.indexOf("/", 5) + 1);
		return apiPrefix;
//
//		Map<String,Object> requestMappings = SpringContextUtil.getByAnnotation(RequestMapping.class);
//		for (Map.Entry<String, Object> entry : requestMappings.entrySet()) {
//			if (excludeCtrls.contains(entry.getKey()))continue;
//			RequestMapping requestMapping = entry.getValue().getClass().getDeclaredAnnotation(RequestMapping.class);
//			if (requestMapping == null)continue;
//			String name = requestMapping.name();
//			if (StringUtils.isEmpty(name))continue;
//			if (uri.contains(name)){
//				return entry.getKey();
//			}
//		}
//		return null;
	}

	/*public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object arg2, Exception arg3)	throws Exception {
		long begin_nao_time = (Long) req.getAttribute("begin_nao_time");
		long interval = System.currentTimeMillis() - begin_nao_time;

		String uri = req.getRequestURI();
		System.out.println(uri+"请求结束时间：" + System.currentTimeMillis());
		System.out.println(uri + "请求时长" + interval);
		//
	}*/


}
