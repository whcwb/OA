package com.ldz.biz.controller;
import com.github.pagehelper.Page;
import com.ldz.biz.model.CoachManagement;
import com.ldz.biz.model.ComplainRecord;
import com.ldz.biz.model.WxOperateLog;
import com.ldz.biz.service.ComplainRecordService;
import com.ldz.biz.service.WxOperateLogService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 投诉记录表
 */
@RestController
@RequestMapping("/api/complainrecord")
public class ComplainRecordController{
    @Autowired
    private ComplainRecordService service;
    @Autowired
    private WxOperateLogService wxLogService;
    /**
     * 分页查询
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<ComplainRecord>> pager(ComplainRecord entity, Page<ComplainRecord> pager){
        return service.pager(pager);
    }

    /**
     * 投诉处理
     * @param id  投诉ID
     * @param status 投诉状态 [ZDCLK1023] 0、已经投诉 1、处理成功 2、受理投诉中
     * @param feedback 投诉处理结果描述
     *
     * @return
     */
    @RequestMapping(value="/tscl", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<String> updateCallBack(String id,String status,String feedback){
        RuntimeCheck.ifBlank(id,"请选择投诉记录");
        RuntimeCheck.ifBlank(status,"请填选择投诉状态");
        RuntimeCheck.ifBlank(feedback,"请填写反馈内容");
        ComplainRecord obj=service.findById(id);
        RuntimeCheck.ifNull(obj, "该投诉不存在，请核实");
        RuntimeCheck.ifTrue(StringUtils.equals(obj.getComplainStatus(),"1"),"该投诉已完成，无需再次操作");
        obj.setComplainStatus(status);
        obj.setFeedback(feedback);
        return service.updateCallBack(obj);
    }

    @RequestMapping(value="/logList", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<WxOperateLog>> getLogList(String id){
        RuntimeCheck.ifBlank(id,"请选择投诉记录");
        SimpleCondition condition = new SimpleCondition(WxOperateLog.class);
        condition.like(WxOperateLog.InnerColumn.operateId,id);//
        condition.like(WxOperateLog.InnerColumn.type,"1");//
        condition.setOrderByClause(WxOperateLog.InnerColumn.id.desc());
        List<WxOperateLog> list=wxLogService.findByCondition(condition);
        return ApiResponse.success(list);
    }
}