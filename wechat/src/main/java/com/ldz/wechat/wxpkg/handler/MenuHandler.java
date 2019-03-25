package com.ldz.wechat.wxpkg.handler;

import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MenuHandler extends AbstractHandler {
	

	@Value("${wechat.domain}")
	private String domainUrl;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) {

		String msg = String.format("type:%s, event:%s, key:%s", wxMessage.getMsgType(), wxMessage.getEvent(),
				wxMessage.getEventKey());
		if (MenuButtonType.CLICK.equalsIgnoreCase(wxMessage.getEvent())) {
			//点击我的服务按钮
			String eventKey = wxMessage.getEventKey();
				//
				if(StringUtils.equals(eventKey,"yhhd")){
					WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
					item.setDescription("3000元全程贴心服务，轻松拿驾照，明涛驾校全城最贴心服务");
					item.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/odibiakeQyKCvZtuQKhJa8iaTGRy35wYdTpZOJ5EicXXMWGFKoVnIibjWvwCkTPPttkNM1TPujGyTQugCVVLDWnkwxQ/640?wx_fmt=jpeg");
					item.setTitle("明涛，享优惠！");
					item.setUrl("https://mp.weixin.qq.com/s?__biz=MjM5NDM1MTIzOQ==&mid=406381325&idx=1&sn=66620ce4b01a4c0f554e3a4ddd79146c&scene=18#rd");
					WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
						.fromUser(wxMessage.getToUser())
						.toUser(wxMessage.getFromUser())
						.addArticle(item)
						.build();
					return m;
				}else if(StringUtils.equals(eventKey,"jxjj")){
                    WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
                    item.setDescription("武汉市明涛驾校简介 明涛驾校创建于一九九三年。二十多年来一直秉承“优质服务、廉政教学”的企业精神为社会输送");
                    item.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/odibiakeQyKCvpQ6Af11bbYm6GBcrrgAuhVwqE5BtWMjj7Ig5babOsY0icS1U3VCricwBWpPTiaAqmIR28NPQoR65tA/640?wx_fmt=jpeg");
                    item.setTitle("明涛驾校简介");
                    item.setUrl("https://mp.weixin.qq.com/s?__biz=MjM5NDM1MTIzOQ==&mid=406381617&idx=1&sn=43f3ec6c0a7bbada62b1b9f876e2c428&scene=18#rd");
                    WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .addArticle(item)
                        .build();
                    return m;
                }else if(StringUtils.equals(eventKey,"mfdh")){//免费电话
//					明涛驾校全国免费客服热线：4001332133
					return WxMpXmlOutMessage.TEXT().content("明涛驾校全国免费客服热线：4001332133").fromUser(wxMessage.getToUser())
							.toUser(wxMessage.getFromUser()).build();
				}

		}

		return WxMpXmlOutMessage.TEXT().content(msg).fromUser(wxMessage.getToUser())
				.toUser(wxMessage.getFromUser()).build();
	}
	
	/**
	 * 创建菜单
	 * @param wxMessage
	 * @return
	 */
	public WxMpXmlOutNewsMessage getNewsMenu(WxMpXmlMessage wxMessage){
		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();

		WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
		  .fromUser(wxMessage.getToUser())
		  .toUser(wxMessage.getFromUser())
		  .addArticle(item)
		  .build();
		
		return m;
	}

}
