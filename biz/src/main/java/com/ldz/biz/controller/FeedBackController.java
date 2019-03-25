package com.ldz.biz.controller;
import com.github.pagehelper.Page;
import com.ldz.biz.model.FeedBack;
import com.ldz.biz.model.WxOperateLog;
import com.ldz.biz.service.FeedBackService;
import com.ldz.biz.service.WxOperateLogService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 意见反馈（针对驾校的）
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedBackController {
    @Autowired
    private FeedBackService service;
    @Autowired
    private WxOperateLogService wxLogService;


    /**
     * 分页查询
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<FeedBack>> pager(FeedBack entity, Page<FeedBack> pager){
        return service.pager(pager);
    }

    /**
     * 意见反馈
     * @param id  投诉ID
     * @param reply 意见反馈结果描述
     *
     * @return
     */
    @RequestMapping(value="/yjfk", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<String> updateCallBack(String id,String reply){
        RuntimeCheck.ifBlank(id,"请选择意见反馈");
        RuntimeCheck.ifBlank(reply,"请填写反馈内容");
        FeedBack obj=service.findById(id);
        RuntimeCheck.ifNull(obj, "该意见反馈不存在，请核实");

        obj.setReply(reply);//反馈回复

//        service.update(obj)>0?ApiResponse.success():ApiResponse.fail("数据库操作失败")
        return service.updateCallBack(obj);
    }
    @RequestMapping(value="/logList", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<WxOperateLog>> getLogList(String id){
        RuntimeCheck.ifBlank(id,"请选择投诉记录");
        SimpleCondition condition = new SimpleCondition(WxOperateLog.class);
        condition.like(WxOperateLog.InnerColumn.operateId,id);//
        condition.like(WxOperateLog.InnerColumn.type,"4");//
        condition.setOrderByClause(WxOperateLog.InnerColumn.id.desc());
        List<WxOperateLog> list=wxLogService.findByCondition(condition);
        return ApiResponse.success(list);
    }

}