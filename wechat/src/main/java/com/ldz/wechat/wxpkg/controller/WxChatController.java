package com.ldz.wechat.wxpkg.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.util.WechatUtils;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 微信服务
 * @author Lee
 *
 * 1.获取openid
 * 2.获取payId
 * 3.调取扫描二维码
 */
@Controller
@RequestMapping("wechat/message")
public class WxChatController {

	private final Logger logger = LoggerFactory.getLogger("access_info");


	@Autowired
	private WxMpService wxService;

	@Autowired
	private WxMpMessageRouter router;

	@Autowired
	private WechatUtils wechatUtils;

	@Value("${wechat.domain}")
	private String domainUrl;


	@RequestMapping("getOpenid")
	@ResponseBody
	public ApiResponse<String> getOpenid(String code){
		System.out.println("----------------------------------"+code);
		return ApiResponse.success(wechatUtils.getOpenid(code));
	}


	@RequestMapping("getJsApiSign")
	@ResponseBody
	public ApiResponse<String> getJsApiSign(String url, String timestamp, String nonceStr){
		try {
			String ticket = wxService.getJsapiTicket();
			String params = "jsapi_ticket=" +ticket +
					"&noncestr=" + nonceStr +
					"&timestamp="+ timestamp +
					"&url="+url;
			System.out.println(params);
			String sign = DigestUtils.shaHex(params);
			System.out.println(sign);
			return ApiResponse.success(sign);
		} catch (WxErrorException e) {
			logger.error("getJsApiSign error",e);
			e.printStackTrace();
		}
		return ApiResponse.fail("getJsApiSign error");
	}

	/**
	 * 微信服务器认证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@GetMapping(produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String authGet(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr) {

		if (StringUtils.isNoneEmpty(signature, timestamp, nonce, echostr)){
			if (this.wxService.checkSignature(timestamp, nonce, signature)) {
				this.logger.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]", signature, timestamp, nonce, echostr);

				return echostr;
			}
		}

		return "非法请求";
	}


	@PostMapping(produces = "application/xml; charset=UTF-8")
	@ResponseBody
	public String post(@RequestBody String requestBody, @RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce) {
		System.out.println("----------------------");
		this.logger.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ", signature,
				timestamp, nonce, requestBody);
		//测试的时候，使用明文接收，暂时不需要解密
		/*WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, this.wxService.getWxMpConfigStorage(),
				timestamp, nonce, signature);
		this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());*/
		//明文内容传输
		WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
		this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
		System.out.println("----------------------");
		WxMpXmlOutMessage outMessage = this.route(inMessage);
		if (outMessage == null) {
			return "";
		}

		this.logger.debug("\n发送内容明文：\n{} ", outMessage.toString());
		String out = outMessage.toXml();
		this.logger.debug("\n组装回复信息：{}", out);
		return out;
	}

	private WxMpXmlOutMessage route(WxMpXmlMessage message) {
		try {
			return this.router.route(message);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		return null;
	}
}
