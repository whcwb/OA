package com.ldz.wechat.wxpkg.service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

public interface WechatService {
    String sendTemplateMsg(WxMpTemplateMessage var1) throws WxErrorException;
}
