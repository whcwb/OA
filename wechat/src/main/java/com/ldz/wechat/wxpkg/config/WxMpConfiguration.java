package com.ldz.wechat.wxpkg.config;

import com.ldz.wechat.wxpkg.handler.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(WxMpService.class)
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {
	@Autowired
	private WxMpProperties properties;
	@Autowired
	private LogHandler logHandler;
	@Autowired
	private LocationHandler locationHandler;
	@Autowired
	private MenuHandler menuHandler;
	@Autowired
	private MsgHandler msgHandler;
	@Autowired
	private UnsubscribeHandler unsubscribeHandler;
	@Autowired
	private SubscribeHandler subscribeHandler;
	@Autowired
	private ScanHandler scanHandler;

	@Bean
	@ConditionalOnMissingBean
	public WxMpConfigStorage configStorage() {
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		configStorage.setAppId(this.properties.getAppId());
		configStorage.setSecret(this.properties.getSecret());
		configStorage.setToken(this.properties.getToken());
		configStorage.setAesKey(this.properties.getAesKey());
		return configStorage;
	}

	@Bean
	@ConditionalOnMissingBean
	public WxMpService WxMpService(WxMpConfigStorage configStorage) {
		// WxMpService WxMpService = new
		// me.chanjar.weixin.cp.api.impl.okhttp.WxMpServiceImpl();
		// WxMpService WxMpService = new
		// me.chanjar.weixin.cp.api.impl.jodd.WxMpServiceImpl();
		// WxMpService WxMpService = new
		// me.chanjar.weixin.cp.api.impl.apache.WxMpServiceImpl();
		WxMpService service = new WxMpServiceImpl();
		service.setWxMpConfigStorage(configStorage);
		return service;
	}

	@Bean
	public WxMpMessageRouter router(WxMpService WxMpService) {
		final WxMpMessageRouter newRouter = new WxMpMessageRouter(WxMpService);

		// 记录所有事件的日志 （异步执行）
		newRouter.rule().handler(this.logHandler).next();

		// 自定义菜单事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(MenuButtonType.CLICK)
				.handler(this.getMenuHandler()).end();

		// 点击菜单连接事件
		/*newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(MenuButtonType.VIEW)
				.handler(this.nullHandler).end();*/

		// 关注事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE)
				.handler(this.getSubscribeHandler()).end();

		// 取消关注事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE)
				.handler(this.getUnsubscribeHandler()).end();

		// 上报地理位置事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(WxConsts.EventType.LOCATION)
				.handler(this.getLocationHandler()).end();

		// 接收地理位置消息
		newRouter.rule().async(false).msgType(XmlMsgType.LOCATION).handler(this.getLocationHandler()).end();

		// 扫码事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(WxConsts.EventType.SCAN)
				.handler(this.getScanHandler()).end();

		// 默认
		newRouter.rule().async(false).handler(this.getMsgHandler()).end();

		return newRouter;
	}

	protected MenuHandler getMenuHandler() {
		return this.menuHandler;
	}

	protected SubscribeHandler getSubscribeHandler() {
		return this.subscribeHandler;
	}

	protected UnsubscribeHandler getUnsubscribeHandler() {
		return this.unsubscribeHandler;
	}

	protected AbstractHandler getLocationHandler() {
		return this.locationHandler;
	}

	protected MsgHandler getMsgHandler() {
		return this.msgHandler;
	}

	protected ScanHandler getScanHandler() {
		return this.scanHandler;
	}

}