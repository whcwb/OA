package com.ldz.biz.service;


import com.ldz.biz.model.WxOperateLog;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysYh;

public interface WxOperateLogService extends BaseService<WxOperateLog, String> {

    /**
     *
     * @param user  管理员ID
     * @param operateId  操作ID
     * @param operateType        操作状态。
     * @param operateDescribe        操作描述
     * @param type          类型：   1、投诉管理日志  2、邀请回访日志 3、教练员删除日志 4、意见反馈日志
     * @return
     */
    int saveEntity(SysYh user, String operateId, String operateType, String operateDescribe,String type);
}