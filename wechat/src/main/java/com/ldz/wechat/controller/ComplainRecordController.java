package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.ComplainRecord;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.ComplainRecordService;
import com.ldz.wechat.service.TraineeReduceAuditingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 投诉记录
 */
@RestController
@RequestMapping("/api/complainrecord")
public class ComplainRecordController extends BaseController<ComplainRecord,String> {
    @Autowired
    private ComplainRecordService service;

    @Autowired
    private TraineeReduceAuditingService auditingService;
    @Override
    protected BaseService<ComplainRecord, String> getBaseService() {
        return service;
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

    @RequestMapping("/saveEntity")
    public ApiResponse<String> saveEetity(ComplainRecord complainRecord){
        TraineeInformation user=getTraineeCurrentUser(true);
        complainRecord.setTraineeId(user.getId());
        return service.saveEntity(complainRecord);
    }
    /**
     * 投诉处理
     * @param id  投诉ID
     * @param  complainStatus 投诉状态 [ZDCLK1023] 0、已经投诉 1、处理成功 2、受理投诉中
     * @param feedback 投诉处理结果描述
     *
     * @return
     */
    @RequestMapping(value="/deal", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<String> updateCallBack(String id,String complainStatus,String feedback){
        RuntimeCheck.ifBlank(id,"请选择投诉记录");
        RuntimeCheck.ifBlank(complainStatus,"请填选择投诉状态");
        RuntimeCheck.ifBlank(feedback,"请填写反馈内容");
        ComplainRecord obj = service.findById(id);
        RuntimeCheck.ifNull(obj, "该投诉不存在，请核实");
        RuntimeCheck.ifTrue(StringUtils.equals(obj.getComplainStatus(),"1"),"该投诉已完成，无需再次操作");
        obj.setComplainStatus(complainStatus);
        obj.setFeedback(feedback);
        return service.updateCallBack(obj);
    }

}