package com.ldz.wechat.wxpkg.service.impl;

import com.ldz.wechat.wxpkg.service.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatServiceImpl implements WechatService {
    Logger log = LoggerFactory.getLogger("access_info");

    @Autowired
    private WxMpService wxService;
    @Override
    public String sendTemplateMsg(WxMpTemplateMessage var1) throws WxErrorException {
        WxMpTemplateMsgService msgService = wxService.getTemplateMsgService();
        return msgService.sendTemplateMsg(var1);
    }



//    List<WxMpTemplateData> data = new ArrayList<>();
//            data.add(new WxMpTemplateData("first", "专员您好，已为您分配到了新的学员"));
//            data.add(new WxMpTemplateData("keyword1", "用户姓名"));
//            data.add(new WxMpTemplateData("keyword2", "教练电话"));//教练电话
//            data.add(new WxMpTemplateData("keyword3", "您的姓名"));
//            data.add(new WxMpTemplateData("remark", "明细信息"));
//    WxMpTemplateMessage msg = new WxMpTemplateMessage();
//            msg.setToUser(appJlUser.getYhOpenId());//用户的OPEN_ID
//            msg.setTemplateId(examMsgIdJl);
//            msg.setUrl(wxJlDomain);
//            msg.setData(data);
//    String res = "";

//    try {
//        res = wechatService.sendTemplateMsg(msg);
//    } catch (Exception e) {
//    }


}
