package com.ldz.wechat.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.sys.model.SysMessage;
import com.ldz.sys.service.SysMessageService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.FileUtil;
import com.ldz.util.commonUtil.ParameterVerification;
import com.ldz.util.commonUtil.StringDivUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.CoachTraineeRercord;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.*;
import com.ldz.wechat.util.BASE64DecodedMultipartFile;
import com.ldz.wechat.util.DloadImgUtil;
import com.ldz.wechat.wxpkg.service.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 处理用户登陆、登出、查询字典信息等相关访问接口
 * @author Lee
 * @date 2017年8月12日
 */
@RestController
@RequestMapping("/pub")
public class BizMainController {

    //基础方法
    @Autowired
    private BizMainSerivce bizMainSerivce;
    //教练
    @Autowired
    private CoachManagementService coachService;

    @Autowired
    private StringRedisTemplate redisDao;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private TraineeInformationService traineeInformationService;
    @Autowired
    private CoachTraineeRercordService coachTraineeRercordService;
    @Autowired
    private TrainingRecordService recordService;
    @Autowired
    private SysMessageService sysMessageService;

    @Value("${debug_test}")
    private String debugTest;

    @Value("${staticPath}")
    private String staticPath;

    // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    protected Logger logger = LoggerFactory.getLogger(getClass());

//登录、验证、
    /**
     * 用户登陆短信下发
     * @param phone     手机号码
     * @param userrole     用户角色  1、学员  2、教练
     * @return
     */
    @RequestMapping(value="/sendSMSlogin")
    public ApiResponse<String> sendSMSlogin(@RequestParam(name = "phone") String phone, @RequestParam(name = "userrole") String userrole){
//        参数非空验证
        RuntimeCheck.ifTrue((StringUtils.isEmpty(phone) ||StringUtils.isEmpty(userrole)),"请填写手机号码！");
        RuntimeCheck.ifFalse(ParameterVerification.isPhoneValid(phone), "请填写正确的手机号");
        RuntimeCheck.ifTrue(StringUtils.containsNone(userrole, new char[]{'1', '2','3'}),"请填写正确的手机号！");
        return bizMainSerivce.sendSmsLogin(phone,userrole);
    }



    /**
     * 用户登陆
     * @param phone     手机号码
     * @param userrole     用户角色  1、学员  2、教练 3、boss
     * @param pollcode     验证码
     * @param openid       微信的OPEN_ID 非必填
     * @return
     */
    @RequestMapping(value="/SMSlogin")
    public ApiResponse<Map<String,Object>> SMSlogin(@RequestParam(name = "phone") String phone, @RequestParam(name = "userrole") String userrole, @RequestParam(name = "pollcode") String pollcode, @RequestParam(name = "openid",required = false) String openid, String password, String loginType){
        RuntimeCheck.ifFalse(StringDivUtils.isPhoneValid(phone),"请填写正确的手机号");
        RuntimeCheck.ifTrue(StringUtils.isEmpty(pollcode),"验证号码不能为空");
        RuntimeCheck.ifTrue(StringUtils.isEmpty(userrole),"用户角色不能为空");
        RuntimeCheck.ifTrue(StringUtils.containsNone(userrole, new char[]{'1', '2','3'}),"请填写正确的用户角色！");
        //通过验证码校验，并返回用户的ID
        String userId;
        if (StringUtils.isBlank(loginType)) {
            ApiResponse<String> retValidate=bizMainSerivce.smsLoginGetUserId(phone,userrole,pollcode);
            RuntimeCheck.ifFalse(retValidate.isSuccess(),retValidate.getMessage());
            userId = retValidate.getMessage();
        }else if(StringUtils.equals(userrole, "2") && StringUtils.isNotBlank(loginType)){
            RuntimeCheck.ifBlank(password, "请输入密码");
            userId = bizMainSerivce.loginByPassword(phone,password);
        }else {
            userId = "";
        }
        return bizMainSerivce.getUserInfo(userId,userrole,openid);
    }

    /**
     * 教练员密码修改
     */
    @PostMapping("editPwd")
    public ApiResponse<String> editPwd(String oldPwd, String newPwd, String newPwd1, HttpServletRequest request){
        return bizMainSerivce.editPwd(oldPwd,newPwd,newPwd1,request);
    }


    /**
     * 教练获取二维码
     * 三小时失效
     * @param id
     * @return
     */
    @RequestMapping("/getQrCode")
    public ApiResponse<String> getQrCode(@RequestParam("id" ) String id){
        String qrCode = redisDao.boundValueOps("QRCode_"+id).get();
        if (StringUtils.isEmpty(qrCode)){
            try {
                if(StringUtils.isNotEmpty(id)){
                    WxMpQrcodeService wx = wxMpService.getQrcodeService();
                    WxMpQrCodeTicket wxMpQrCodeTicket = wx.qrCodeCreateTmpTicket(id, 3*60*60);
                    String url = wxMpQrCodeTicket.getUrl();
                    CoachManagement coachManagement = coachService.findById(id);
                    coachManagement.setSenceUrl(url);
                    coachService.update(coachManagement);
                    String ticket=wxMpQrCodeTicket.getTicket();
                    String qrcodesFile=wx.qrCodePictureUrl(ticket);
                    redisDao.boundValueOps("QRCode_"+id).set(qrcodesFile, 3, TimeUnit.HOURS);
                    qrCode = redisDao.boundValueOps("QRCode_"+id).get();
                }
            }catch (WxErrorException e){
                e.printStackTrace();
            }
        }
        return ApiResponse.success(qrCode);
    }

    /**
     * 教练给学员发送消息
     * @param message
     * @return
     */
    @RequestMapping("/sendMsg")
    public ApiResponse<String> sendMsg(WxMpTemplateMessage message,String openId,String remark,String traineeId,String coachPhone,String coachName){
//        CoachManagement coachManagement=getCoachCurrentUser(true);
//        SimpleCondition condition = new SimpleCondition(CoachTraineeRercord.class);
//        condition.eq(CoachTraineeRercord.InnerColumn.coachId,coachManagement.getId());//教练ID
//        condition.eq(CoachTraineeRercord.InnerColumn.traineeId,traineeId);//学员ID
//        condition.eq(CoachTraineeRercord.InnerColumn.status,"00");//学员状态  00:学习中 10：学习结束 20:已转教练 30：已退学
//        List<CoachTraineeRercord> coachTraineeRercordList=coachTraineeRercordService.findByCondition(condition);
//        RuntimeCheck.ifTrue(coachTraineeRercordList==null||coachTraineeRercordList.size()==0, "该学员未分配给您，您无法对其发送消息！");

        TraineeInformation traineeInformation =  traineeInformationService.findById(traineeId);
//        RuntimeCheck.ifTrue(traineeInformation==null||StringUtils.equals(traineeInformation.getStatus(),"50")||StringUtils.equals(traineeInformation.getStatus(),"60"),"学员不存在或者已结业");//50：结业 60：退学
        List<WxMpTemplateData> data = new ArrayList<>();
        data.add(new WxMpTemplateData("userName", traineeInformation.getName()));
        data.add(new WxMpTemplateData("trainer",coachName ));
        data.add(new WxMpTemplateData("message", remark));
        data.add(new WxMpTemplateData("dz", remark));
//        data.add(new WxMpTemplateData("keyword3", "您的姓名"));
//        data.add(new WxMpTemplateData("remark", "明细信息"));
        WxMpTemplateMessage msg = new WxMpTemplateMessage();
        msg.setToUser(openId);
        msg.setTemplateId("L7kX4ArY2NlHf0-OJe9J9ASrNNTKtnLaZEvKvhpUlTU");
        //        msg.setUrl(wxJlDomain);
        msg.setData(data);
        String res = "";
        try {
            res = wechatService.sendTemplateMsg(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isNotBlank(res)){
            return ApiResponse.success();
        }
        return ApiResponse.fail();
    }

    /**
     * 签到
     * @param url
     * @param openId
     * @return
     */
    @RequestMapping("/equalSenceUrl")
    public ApiResponse<String> equalSenceUrl(String url,String openId,String userId){
        CoachManagement coachManagement = coachService.queryBySenceUrl(url);
        if (coachManagement != null){
            recordService.afterScan(coachManagement.getId(),openId, userId);
            return ApiResponse.success("签到成功");
        }
        return ApiResponse.fail("签到失败");
    }


    /**
     * 获取当前登录学员用户信息
     * @return
     */
    public static TraineeInformation getTraineeCurrentUser(boolean require) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        TraineeInformation userInfo = (TraineeInformation) request.getAttribute("wechatTraineeUserInfo");
        RuntimeCheck.ifTrue(require && userInfo == null, "当前登录用户未空！");
        return userInfo;
    }

    /**
     * 获取当前登录教练员的用户信息
     * @param require
     * @return
     */
    public static CoachManagement getCoachCurrentUser(boolean require) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        CoachManagement userInfo = (CoachManagement) request.getAttribute("wechatCoachUserInfo");
        RuntimeCheck.ifTrue(require && userInfo == null, "当前登录用户未空！");
        return userInfo;
    }

    @RequestMapping(value = "/authFiled",method = {RequestMethod.GET,RequestMethod.POST})
    public ApiResponse<String> authFiled(){
        return ApiResponse.authFailed();
    }
    @RequestMapping(value = "/403",method = {RequestMethod.GET,RequestMethod.POST})
    public ApiResponse<String> forbidden(){
        return ApiResponse.forbidden();
    }

    /**
     *从微信服务器下载文件
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/getPic")
    public ApiResponse<String> getPic(String serverId, String savePath,String userId,String userrole){
        String accessToken = null;
        try {
            accessToken = wxMpService.getAccessToken();
        } catch (WxErrorException e) {
             logger.error("------------"+e);
             return ApiResponse.fail("accessToken获取失败");
        }
        String picUrl =  DloadImgUtil.downloadMedia(serverId,staticPath,accessToken);
        //如果是学生
        if("1".equals(userrole)){
            TraineeInformation information =  traineeInformationService.findById(userId);
            information.setHeadImg(picUrl);
            traineeInformationService.update(information);
        }else if("2".equals(userrole)){
            //TODO 这里不确定教练端是否可上传头像，预留
        }
       if(StringUtils.isNotEmpty(picUrl)){
           return ApiResponse.success(picUrl);
       }
       return ApiResponse.fail("获取失败");
    }

    /**
     * 完成一个节点就给学员发消息
     * 现在直接调用service ,不调接口
     * @param openId
     * @param templateId
     * @param content
     * @return
     */
    @RequestMapping("/sendWxToUser")
    public ApiResponse<String> sendWxToUser(String openId ,String templateId ,String content,String messageId) {
        return bizMainSerivce.sendMesageToUser(messageId);
    }

    /**
     * 微信上传base64编码图片，返回地址
     * @param Base64
     * @param targetPath
     * @return
     */
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<String> uploadImg(@RequestParam("Base64") String Base64, String targetPath,String userId) {
        MultipartFile file = BASE64DecodedMultipartFile.base64ToMultipart(Base64);
        if (StringUtils.isEmpty(targetPath)) targetPath = "temp";
        targetPath = targetPath + "/";
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        fileName = uuid.toString().replaceAll("-", "") + suffix;
        String datePath =  DateUtils.getToday("YYYYMMdd")+"/";
        String filePath = staticPath + targetPath + datePath;
        String path = targetPath +datePath + fileName;
        //保存图片路径
        RuntimeCheck.ifBlank(userId,"用户信息为空，无法上传");
        TraineeInformation information =  traineeInformationService.findById(userId);
        information.setHeadImg(path);
        traineeInformationService.update(information);
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.success(path);
    }
}

