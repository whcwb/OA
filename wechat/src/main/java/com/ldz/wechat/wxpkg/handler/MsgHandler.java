package com.ldz.wechat.wxpkg.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.wechat.wxpkg.budiler.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) {

		if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
			// TODO 可以选择将消息保存到本地
		}

		if (StringUtils.isNotEmpty(wxMessage.getMsgType())){
			switch (wxMessage.getMsgType()){
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
