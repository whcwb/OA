package com.ldz.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.sys.bean.AccessToken;
import com.ldz.sys.model.SysMessage;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.SysMessageService;
import com.ldz.sys.service.YhService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.*;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.wechat.mapper.WxModuleMapper;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.BizMainSerivce;
import com.ldz.wechat.service.CoachManagementService;
import com.ldz.wechat.service.TraineeInformationService;
import com.ldz.wechat.wxpkg.service.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class BizMainSerivceImpl implements BizMainSerivce {
//    @Value("${redis_login_key}")
//    private String redisLoginKey;
    //用户登录缓存
    @Value("${app_user_login_register}")
    private String appUserLoginRegister;
    //用户短信验证码发送 缓存
    @Value("${app_send_sms_register}")
    private String appSendSMSRegister;



    //学员方法
    @Autowired
    private TraineeInformationService traineeService;
    //教练
    @Autowired
    private CoachManagementService coachService;
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private YhService yhService;

    @Autowired
    private  WxModuleMapper wxModuleMapper;
    @Autowired
    private WechatService wechatService;

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Autowired
    private StringRedisTemplate redisDao;
    // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Value("${debug_test}")
    private String debugTest;

    protected Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 用户登陆短信下发
     * @param phone     手机号码
     * @param userrole     用户角色  1、学员  2、教练
     * @return
     */
    @Override
    public ApiResponse<String> sendSmsLogin(String phone, String userrole){

        String userId="";
//        参数有效性验证
        if(StringUtils.equals(userrole,"1")) {//学员
            TraineeInformation user=traineeService.queryPhone(phone);
            RuntimeCheck.ifTrue(user==null,"该手机号码还未注册，请您核实");
            userId=user.getId();
        }else if(StringUtils.equals(userrole,"2")){//教练
            CoachManagement user=coachService.queryPhone(phone);
            RuntimeCheck.ifTrue(user==null,"该手机号码还未注册，请联系公司运营人员。");
            RuntimeCheck.ifTrue(StringUtils.equals(user.getCoachStatus(),"10"),"您好，您已经离职，不能再登录系统了。");
            userId=user.getId();
        }else if(StringUtils.equals(userrole,"3")){
            SysYh user =  yhService.queryByPhone(phone);
            RuntimeCheck.ifTrue(user==null,"您不是管理员，请您核实");
            userId=user.getYhid();
        }

//		4、生成手机验证码
        String identifyingCode= StringDivUtils.getSix();//获取验证码
        boolean sendType=sendSMS(phone,1,identifyingCode);
        if(sendType){
            //将用户的ID放到redis中。  appUserLoginRegister
            redisDao.boundValueOps(appUserLoginRegister+"_"+phone).set(userId, 5, TimeUnit.MINUTES);//设备邀请码，为10分钟过期
//            if (debugTest != null && debugTest.equals("1")) {// todo 调试上生产环境时，这里需要删除
//                return  ApiResponse.success(redisDao.boundValueOps(appSendSMSRegister+phone).get());
//            }
            return ApiResponse.success();
        }else{
            return  ApiResponse.fail("短信下发失败");
        }
    }


    /**
     * 下发短信
     *
     * @param tel             手机号码
     * @param type            1、验证码登录
     * @param identifyingCode 验证码
     * @return
     */
    public boolean sendSMS(String tel, int type, String identifyingCode) {
        boolean ret = false;
        if (StringUtils.isEmpty(identifyingCode)) {
            identifyingCode = StringDivUtils.getSix();//获取验证码
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("phoneNumbers", tel);//电话号码
        map.put("templateParam", "{\"code\":\"" + identifyingCode + "\"}");//短信验证码
        String redisKey = "";
        if (type == 1) {
            redisKey = appSendSMSRegister;
            //使用注册模板下发
            map.put("templateCode", "SMS_147260049");//用户登录验证码
        } else {
            //类型不存在，不能下发
            return false;
        }
//        查询当前KEY过期时间还有多少秒 返回0时，表示永远有效 当验证码过期后，才可以再次下发短信
        long identifying = redisDao.getExpire(redisKey + tel, TimeUnit.SECONDS);
        if (identifying > 0) {//
            return true;
        }

        //  2018/5/19 调试模式。
//        if (debugTest != null && debugTest.equals("1")) {//调试
//            ret = true;
//        } else {
            //短信下发
            ret = SendSmsUtil.sendSms(map);
//        }
        if (ret) {
            //将验证码放到缓存中去
            redisDao.boundValueOps(redisKey + tel).set(identifyingCode, 5, TimeUnit.MINUTES);//单位是分
        }
        return ret;
    }

    /**
     * 短信验证
     *
     * @param tel             手机号码
     * @param type            1、验证码登录
     * @param identifyingCode 验证码
     * @return
     */
    public ApiResponse<String> validateSms(String tel, String type, String identifyingCode) {
        if (StringUtils.isEmpty(identifyingCode)) {
            return ApiResponse.fail("验证码不能为空");
        }
        if (StringUtils.isEmpty(tel)) {
            return ApiResponse.fail("手机号码不能为空");
        }
        String redisKey = "";
        if (StringUtils.equals(type, "1")) {//
            redisKey = appSendSMSRegister;
        } else {
            return ApiResponse.fail("验证失败");
        }
        String identifying = redisDao.boundValueOps(redisKey + tel).get();
        if (StringUtils.equals(identifying, identifyingCode)) {
            return ApiResponse.success();
        } else {
            return ApiResponse.fail("验证码有误");
        }
    }
    @Override
    public ApiResponse<String> smsLoginGetUserId(String phone, String userrole, String pollcode){
        ApiResponse<String> retType=this.validateSms(phone,"1",pollcode);
        if(retType.isSuccess()){
            String userId = redisDao.boundValueOps(appUserLoginRegister+"_"+phone).get();
            if(StringUtils.isNotEmpty(userId)){
                return ApiResponse.success(userId);
            }else{
                return ApiResponse.fail("验证码，验证失败。请稍后尝试");
            }
        }else{
            return retType;
        }
    }
    /**
     * 通过用户ID  获取用户信息
     * @param userId    用户ID
     * @param userrole  用户角色  1、学员  2、教练  3.boss
     * @param openId
     * @return
     */
    @Override
    public ApiResponse<Map<String,Object>> getUserInfo(String userId, String userrole, String openId){
        RuntimeCheck.ifTrue(StringUtils.isBlank(userId),"您好，登录失败请稍后尝试");
        RuntimeCheck.ifTrue(StringUtils.containsNone(userrole, new char[]{'1', '2','3'}),"请填写正确的用户角色！");
        ApiResponse<Map<String,Object>> result = new ApiResponse<>();
        Map<String,Object> rMap = new HashMap<>(2);
        Boolean querType=false;
        String userName="";
        String userInfoString="";
        String phone="";//电话
        String sceneStr="";//二维码的参数
        TraineeInformation traineeInfo = null;
        if(StringUtils.equals(userrole,"1")) {//学员
            querType=true;
            traineeInfo= traineeService.findById(userId);
            RuntimeCheck.ifTrue(traineeInfo==null,"学员登录失败，请联系公司运营人员。");
            //登录的时候保存openid
            logger.info("----------用户openId----------: " + openId);
            if(StringUtils.isNotBlank(openId)){
                traineeInfo.setOpenId(openId);
            }
            traineeService.update(traineeInfo);
            userName=traineeInfo.getName();//用户姓名
            phone=traineeInfo.getPhone();//联系电话
            rMap.put("userInfo",traineeInfo);
            try {
                userInfoString= mapper.writeValueAsString(traineeInfo);
                sceneStr="X"+traineeInfo.getId();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(StringUtils.equals(userrole,"2")) {//教练
            querType=true;
            CoachManagement user= coachService.findById(userId);

            rMap.put("userInfo",user);
            RuntimeCheck.ifTrue(user==null,"教练登录失败，请联系公司运营人员。");
            //保存openid
            logger.info("----------用户openId----------: " + openId);
            if(StringUtils.isNotBlank(openId)){
                user.setOpenId(openId);
            }
            coachService.update(user);
            RuntimeCheck.ifTrue(StringUtils.equals(user.getCoachStatus(),"10"),"您好，您已经离职，不能再登录系统了。");
            userName=user.getCoachName();//教练姓名
            phone=user.getPhone();//联系电话

            try {
                userInfoString=mapper.writeValueAsString(user);
                sceneStr="J"+user.getId();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(StringUtils.equals("3",userrole)){
            querType=true;
            Map<String ,List> authorityMap = null;
            SysYh sysUser = yhService.findById(userId);
            RuntimeCheck.ifTrue(sysUser == null,"管理员登录失败，请联系工作人员");
            sysUser.setMm(null);
            rMap.put("userInfo",sysUser);

            authorityMap = this.getAuthority(userId);
            rMap.put("authorityMap",authorityMap);
//            //尝试从redis中获取权限信息
//            String authorityList = redisDao.boundValueOps("wechat"+userId+"-authorityList").get();
//            //若果redis中获取不到，查询一次再次放入redis
//            if (StringUtils.isBlank(authorityList)){
//               authorityMap = this.getAuthority(userId);
//                rMap.put("authorityMap",authorityMap);
//                try {
//                    String authority = mapper.writeValueAsString(authorityMap);
//                    RuntimeCheck.ifFalse(StringUtils.isNotBlank(authority),"未获取到权限列表信息。");
//                    redisDao.boundValueOps("wechat"+userId+"-authorityList").set(authority, 30, TimeUnit.DAYS);
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//            }else{
//                //如果从redis中得到，就转成map返回
//                rMap.put("authorityMap",JsonUtil.toMap(authorityList));
//            }
            try {
                userInfoString=mapper.writeValueAsString(sysUser);
                sceneStr="J"+sysUser.getYhid();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        String url="";
        RuntimeCheck.ifFalse(querType,"登录失败，请稍后尝试");
        RuntimeCheck.ifFalse(StringUtils.isNotBlank(userInfoString),"登录失败，未获取到用户信息。");
        try {
            String token = JwtUtil.createToken(userId,userName,userrole);
            redisDao.boundValueOps("wechat"+userId).set(token, 1, TimeUnit.DAYS);
            redisDao.boundValueOps("wechat"+userId+"-appUserInfo").set(userInfoString, 1, TimeUnit.DAYS);
            AccessToken aToken = new AccessToken();
            aToken.setUserId(userId);
            aToken.setUsername(userName);//用户别名
            aToken.setToken(token);
            rMap.put("accessToken", aToken);
            rMap.put("userrole", userrole);
            result.setResult(rMap);
        } catch (Exception e) {
            result.setCode(ApiResponse.FAILED);
            result.setMessage("用户登陆失败，请重试！");
        }

        //清除本人的登录验证码
        if(result.isSuccess()){
            if(StringUtils.isNotEmpty(openId)){
                long identifying = redisDao.getExpire("wechat"+userId, TimeUnit.SECONDS);
                if (identifying < 0) {
                    //将用户本次登录的OPEN_ID写到缓存中
                    redisDao.boundValueOps("wechat"+userId+"-openId").set(openId, identifying, TimeUnit.SECONDS);
                }
            }
            redisDao.delete(appSendSMSRegister+phone);
        }
        return result;
    }

    /**
     * 发送微信消息服务
     * @param messageId
     * @return
     */
    @Override
    public  ApiResponse<String> sendMesageToUser(String messageId) {
        SysMessage message = sysMessageService.findById(messageId);
        //TODO　这里需要定义的　redis　　ｋｅｙ
//        SysMessage message =  redisTemplateUtil.getSerializerValue(sysMessageInfo+"_"+messageId,SysMessage.class);
        try {
            if ("2".equals(message.getType())) {
                if (StringUtils.isNotBlank(message.getTempId())) {
                    //解析json
                    Map maps = (Map) JSON.parse(message.getParameterBody());
                    List<WxMpTemplateData> data = new ArrayList<>();
                    for (Object map : maps.entrySet()) {
                        logger.info(((Map.Entry) map).getKey() + "     " + ((Map.Entry) map).getValue());
                        data.add(new WxMpTemplateData(((Map.Entry) map).getKey().toString(), ((Map.Entry) map).getValue().toString()));
                    }
                    //发送模板消息
                    WxMpTemplateMessage msg = new WxMpTemplateMessage();
                    msg.setToUser(message.getSendeeCode());
                    msg.setTemplateId(message.getTempId());
                    msg.setData(data);
                    String res = "";
                    try {
                        res = wechatService.sendTemplateMsg(msg);
                        if (StringUtils.isNotBlank(res)) {
                            this.sendSuccess(message, "微信模板消息发送成功");
                        } else {
                            //发送失败
                            this.sendfail(message, "微信模板消息发送失败");
                        }
                    } catch (WxErrorException e) {
                        //微信异常，将信息反馈
                        this.sendfail(message, "微信模板消息发送失败" + e.getError());
                    }

                } else {
                    try {
                        boolean flag = this.sendWxMsg(message.getParameterBody(), message.getSendeeCode());
                        if (flag) {
                            this.sendSuccess(message, "微信客服消息发送成功");
                        } else {
                            this.sendfail(message, "微信客服消息发送失败");
                        }
                    } catch (WxErrorException e) {
                        this.sendfail(message, "微信客服消息发送失败" + e.getError());
                    }
                }
            } else {
                this.sendfail(message, "消息类型有误，请检查");
            }
        } catch (Exception e) {
            logger.error("----------异常", e);
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    @Override
    public String loginByPassword(String phone, String password) {
        SimpleCondition condition = new SimpleCondition(CoachManagement.class);
        condition.eq(CoachManagement.InnerColumn.phone, phone);
        List<CoachManagement> managementList = coachService.findByCondition(condition);
        if(CollectionUtils.isNotEmpty(managementList)){
            CoachManagement management = managementList.get(0);
            String userPwd = EncryptUtil.encryptUserPwd(password);
            String pwd = management.getPassword();
            if(StringUtils.equals(userPwd, pwd)){
                return management.getId();
            }
        }

        return "";
    }

    @Override
    public ApiResponse<String> editPwd(String oldPwd, String newPwd, String newPwd1, HttpServletRequest request) {
        String userid = request.getHeader("userid");
        if (userid == null){
            userid = request.getParameter("userid");
        }
        CoachManagement coach = coachService.findById(userid);
        String oldUsrPwd = EncryptUtil.encryptUserPwd(oldPwd);
        RuntimeCheck.ifTrue( StringUtils.equals(oldUsrPwd, coach.getPassword()), "原始密码错误");
        RuntimeCheck.ifFalse(StringUtils.equals(newPwd ,newPwd1), "两次密码不一致");
        coach.setPassword(EncryptUtil.encryptUserPwd(newPwd));
        coachService.update(coach);
        return ApiResponse.success();
    }

    /**
     * 主动发送普通消息
     * @param msg
     * @param OpenId
     * @return
     * @throws WxErrorException
     */
    public boolean sendWxMsg(String msg, String OpenId) throws WxErrorException {
      return   wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage
            .TEXT()
            .toUser(OpenId)
            .content(msg)
            .build());
    }

    /**
     * 发送成功回写数据库
     * @param message
     * @return
     */
    public ApiResponse<String> sendSuccess(SysMessage message,String remark){
        message.setStatus("1");
        message.setRemark(remark);
        message.setSendCount(message.getSendCount() + 1);
        try {
            message.setCreationTime(DateUtils.getDateToday("yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            logger.error("----------时间处理异常",e);
            e.printStackTrace();
        }
        sysMessageService.update(message);
        return ApiResponse.success();
    }

    /**
     * 发送失败回写数据库
     * @param message remark
     * @return
     */
    public ApiResponse<String> sendfail(SysMessage message,String remark){
        message.setStatus("2");
        message.setRemark(remark);
        message.setSendCount(message.getSendCount() + 1);
        try {
            message.setCreationTime(DateUtils.getDateToday("yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            logger.error("----------时间处理异常",e);
            e.printStackTrace();
        }
        sysMessageService.update(message);
        return ApiResponse.fail();
    }

    public Map<String,List> getAuthority(String yhId){
        List<Map> reqList = wxModuleMapper.getPression(yhId);
        Map reponseMap = new HashMap();
        List reponseList1 = new ArrayList();
        List reponseList2 = new ArrayList();
        for(Map reqMap : reqList){
           String type =  reqMap.get("type").toString();
           // 1 查询 2 处理
           if("1".equals(type)){
               reponseList1.add(reqMap);
               reponseMap.put("1",reponseList1);
           }else{
               reponseList2.add(reqMap);
               reponseMap.put("2",reponseList2);
           }
        }
        return  reponseMap;
    }

}

