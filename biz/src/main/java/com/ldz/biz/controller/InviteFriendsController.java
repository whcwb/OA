package com.ldz.biz.controller;
import com.github.pagehelper.Page;
import com.ldz.biz.model.InviteFriends;
import com.ldz.biz.model.WxOperateLog;
import com.ldz.biz.service.InviteFriendsService;
import com.ldz.biz.service.WxOperateLogService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 客户端 好友邀请
 */
@RestController
@RequestMapping("/api/invitefriends")
public class InviteFriendsController{
    @Autowired
    private InviteFriendsService service;
    @Autowired
    private WxOperateLogService wxLogService;

    /**
     * 分页查询
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<InviteFriends>> pager(InviteFriends entity, Page<InviteFriends> pager){
        return service.pager(pager);
    }

    /**
     * 回访标记
     * @param id  邀请表ID
     * @param status 回访状态 [ZDCLK1022]  0、待回访   2、被邀请人没有报名意向  3、跟踪回访中
     * @param backDescribe 回访描述
     *
     * @return
     */
    @RequestMapping(value="/hfbj", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<String> updateCallBack(String id,String status,String backDescribe){
        RuntimeCheck.ifBlank(id,"请选择邀请");
        RuntimeCheck.ifBlank(status,"请填选择回访状态");
        RuntimeCheck.ifBlank(backDescribe,"请填写回访描述");
        InviteFriends obj=service.findById(id);
        RuntimeCheck.ifNull(obj, "该学员不存在，请核实");
        obj.setStatus(status);
        obj.setBackDescribe(backDescribe);
        return service.updateCallBack(obj);
    }

    @RequestMapping(value="/logList", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<WxOperateLog>> getLogList(String id){
        RuntimeCheck.ifBlank(id,"请选择投诉记录");
        SimpleCondition condition = new SimpleCondition(WxOperateLog.class);
        condition.like(WxOperateLog.InnerColumn.operateId,id);//
        condition.like(WxOperateLog.InnerColumn.type,"2");//
        condition.setOrderByClause(WxOperateLog.InnerColumn.id.desc());
        List<WxOperateLog> list=wxLogService.findByCondition(condition);
        return ApiResponse.success(list);
    }




}