package com.ldz.wechat.wxpkg.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class LogHandler extends AbstractHandler {
	Logger accessLogger = LoggerFactory.getLogger("access_info");

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService WxMpService,
									WxSessionManager sessionManager) {
		try {
			this.accessLogger.info("\n接收到请求消息，内容：{}", mapper.writeValueAsString(wxMessage));
		} catch (JsonProcessingException e) {
		}

		return null;
	}

}
