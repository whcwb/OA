package com.ldz.wechat.wxpkg.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.wechat.wxpkg.budiler.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Value("${replyConfigPath}")
    private String replypath;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        try {
            Map<String, String> replyMap = new HashMap<>();
            List<String> list = FileUtils.readLines(new File(replypath),"UTF-8");
            list.forEach(s -> {
                String[] split = s.split("=");
                replyMap.put(split[0].toUpperCase(), split[1].replaceAll("&A;","\r\n"));
            });
            String msg = wxMessage.getContent();
            if (StringUtils.isNotBlank(msg) && replyMap.containsKey(msg.toUpperCase())) {
                return new TextBuilder().build(replyMap.get(msg.toUpperCase()), wxMessage, weixinService);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
            // TODO 可以选择将消息保存到本地
        }

        if (StringUtils.isNotEmpty(wxMessage.getMsgType())) {
            switch (wxMessage.getMsgType()) {
                case "appdown":
                    return new TextBuilder().build("敬请期待", wxMessage, weixinService);
                default:
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        // TODO 组装回复消息
        String content = "";
        try {
            content = "收到信息内容：" + mapper.writeValueAsString(wxMessage);
        } catch (JsonProcessingException e) {

        }
//		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
//		item.setDescription("");
//		item.setPicUrl("http://www.520xclm.com:8001/wechatImg.jpg");
//		item.setTitle("点击查看");
//		item.setUrl("http://www.520xclm.com/wx");
//
//		WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
//				.fromUser(wxMessage.getToUser())
//				.toUser(wxMessage.getFromUser())
//				.addArticle(item)
//				.build();
//		return m;

//		return new ImageBuilder().build("http://xclm.xxpt123.com:8001/123456789.png",wxMessage,weixinService);
//
//		return new TextBuilder().build("敬请期待", wxMessage, weixinService);
        return null;
    }

}
