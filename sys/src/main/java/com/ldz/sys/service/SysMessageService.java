package com.ldz.sys.service;

import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysMessage;
import com.ldz.util.bean.ApiResponse;

/**
 * Created by Administrator on 2018/5/10.
 */
public interface SysMessageService extends BaseService<SysMessage,String> {
    void sendMessage(SysMessage sysMessage, String openId, String phone);

    ApiResponse<String> repeatSendMessage(SysMessage entity);

    void nowSendMessage(String toString);

    void expireMessage(String toString);
}
