package com.ldz.wechat.wxpkg.handler;

import com.ldz.wechat.wxpkg.budiler.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class SubscribeHandler extends AbstractHandler {

	@Value("${wechat.appId}")
	private String appId;
	@Value("${wechat.domain}")
	private String domain;
//	@Autowired
//	private PtyhServiceImpl ptyhService;

//	@Autowired
//	private WechatService wechatService;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) throws WxErrorException {

		this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

		// 获取微信用户基本信息 关注后发送地址，不做其他处理
		WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser());

		if (userWxInfo != null) {}else{
			return new TextBuilder().build("操作失败，请重新关注本公众号！", wxMessage, weixinService);
		}

		WxMpXmlOutMessage responseResult = null;
		try {
			responseResult = handleSpecial(wxMessage);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		if (responseResult != null) {
			return responseResult;
		}
		String ticket = wxMessage.getTicket();

		try {
			WxMenu wxMenu = new WxMenu();
			List<WxMenuButton> wxButtons = new ArrayList<>();

			WxMenuButton button1 = new WxMenuButton();
			List<WxMenuButton> subButtons1 = new ArrayList<>();
			WxMenuButton subButton1 = new WxMenuButton();
			WxMenuButton subButton2 = new WxMenuButton();
			WxMenuButton subButton3 = new WxMenuButton();
			WxMenuButton subButton4 = new WxMenuButton();
			WxMenuButton subButton5 = new WxMenuButton();

			button1.setKey("button1");
			button1.setName("明涛文化");

			subButton1.setKey("yhhd");
			subButton1.setName("优惠活动");
			subButton1.setType(WxConsts.MenuButtonType.CLICK);

			subButton2.setKey("jsyjndbw");
			subButton2.setName("驾驶员技能大比武");
			subButton2.setType(WxConsts.MenuButtonType.VIEW);
			subButton2.setUrl("http://u.eqxiu.com/s/zWa6rjaN");

			subButton3.setKey("jxjj");
			subButton3.setName("驾校简介");
			subButton3.setType(WxConsts.MenuButtonType.CLICK);

			subButton4.setName("发展历史");
			subButton4.setType(WxConsts.MenuButtonType.VIEW);
			subButton4.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5NDM1MTIzOQ==&mid=404705592&idx=1&sn=13aad8e0edc7659a2e53c874f8b63626&scene=18#wechat_redirect");

			subButton5.setName("驾校微视频");
			subButton5.setType(WxConsts.MenuButtonType.VIEW);
			subButton5.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5NDM1MTIzOQ==&mid=406520892&idx=1&sn=7bd23cfee6470e54aa88bbd5e7d5c3ec&scene=18#wechat_redirect");

			subButtons1.add(subButton1);
			subButtons1.add(subButton2);
			subButtons1.add(subButton3);
			subButtons1.add(subButton4);
			subButtons1.add(subButton5);
			button1.setSubButtons(subButtons1);


			WxMenuButton button2 = new WxMenuButton();

			List<WxMenuButton> subButtons2 = new ArrayList<>();
			WxMenuButton subButton6 = new WxMenuButton();
			WxMenuButton subButton7 = new WxMenuButton();
			WxMenuButton subButton8 = new WxMenuButton();
			WxMenuButton subButton9 = new WxMenuButton();
			WxMenuButton subButton10 = new WxMenuButton();
			button2.setKey("button2");
			button2.setName("服务学员");

			subButton6.setKey("zxbm");
			subButton6.setName("在线报名");
			subButton6.setType(WxConsts.MenuButtonType.VIEW);
			subButton6.setUrl("http://www.whmt.cn/hmzxxz.html");

			subButton7.setKey("jkdt");
			subButton7.setName("驾考动态");
			subButton7.setType(WxConsts.MenuButtonType.VIEW);
			subButton7.setUrl("http://www.weitaotong.cn/Column_23625/id_1928");

			subButton8.setKey("mnks");
			subButton8.setName("模拟考试");
			subButton8.setType(WxConsts.MenuButtonType.VIEW);
			subButton8.setUrl("http://m.jiakaobaodian.com/mnks/wuhan.html");

			subButton9.setKey("ksjq");
			subButton9.setName("考试技巧");
			subButton9.setType(WxConsts.MenuButtonType.VIEW);
			subButton9.setUrl("http://www.whmt.cn/hmksjq.html");

//			subButton10.setKey("xcxz");
//			subButton10.setName("学车须知");
//			subButton10.setType(WxConsts.MenuButtonType.VIEW);
//			subButton10.setUrl("http://www.weitaotong.cn/Content_27228");

			subButton10.setKey("xsd");
			subButton10.setName("学员助手");
			subButton10.setType(WxConsts.MenuButtonType.VIEW);
			subButton10.setUrl("http://mt.xxpt123.com/wx-web/student/index.html");



			subButtons2.add(subButton6);
			subButtons2.add(subButton7);
			subButtons2.add(subButton8);
			subButtons2.add(subButton9);
			subButtons2.add(subButton10);
			button2.setSubButtons(subButtons2);


			WxMenuButton button3 = new WxMenuButton();
			List<WxMenuButton> subButtons3 = new ArrayList<>();
			WxMenuButton button11 = new WxMenuButton();
			WxMenuButton button12 = new WxMenuButton();
			WxMenuButton button13 = new WxMenuButton();
			WxMenuButton button14 = new WxMenuButton();

			button3.setKey("appdown");
			button3.setName("联系我们");
//			button3.setType(WxConsts.MenuButtonType.CLICK);
			button11.setKey("xqfb");
			button11.setName("校区分布");
			button11.setType(WxConsts.MenuButtonType.VIEW);
			button11.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5NDM1MTIzOQ==&mid=406382516&idx=1&sn=7ae19070afa609fda5b75aef5936c632&scene=18#wechat_redirect");

			button12.setKey("mfdh");
			button12.setName("免费电话");
			button12.setType(WxConsts.MenuButtonType.CLICK);

			button13.setKey("jld");
			button13.setName("教练助手");
			button13.setType(WxConsts.MenuButtonType.VIEW);
			button13.setUrl("http://mt.xxpt123.com/wx-web/coach/index.html");

			button14.setKey("glzs");
			button14.setName("管理助手");
			button14.setType(WxConsts.MenuButtonType.VIEW);
			button14.setUrl("http://mt.xxpt123.com/wx-web/boss/index.html");
//
			subButtons3.add(button11);
			subButtons3.add(button12);
			subButtons3.add(button13);
			subButtons3.add(button14);
			button3.setSubButtons(subButtons3);

			wxButtons.add(button1);
			wxButtons.add(button2);
			wxButtons.add(button3);

			wxMenu.setButtons(wxButtons);
			// 设置菜单

			this.logger.info("自定义菜单JSON报文" + wxMenu.toJson());
			weixinService.getMenuService().menuCreate(wxMenu);

			WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
			item.setDescription("欢迎关注明涛驾校公众号！");
			item.setPicUrl("http://mt.xxpt123.com/mt_logo20181016125955.png");
			item.setTitle("感谢关注");
			item.setUrl("http://www.whmt.cn");

			WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
					.fromUser(wxMessage.getToUser())
					.toUser(wxMessage.getFromUser())
					.addArticle(item)
					.build();

			String EventKey=wxMessage.getEventKey();
			if(StringUtils.isNotEmpty(EventKey)){
//				ptyhService.sendRegisterInvite(EventKey.replace("qrscene_",""),wxMessage.getFromUser());
			}
			this.logger.info("设置菜单:2 " + wxButtons.size());
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
	 */
	private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {
		// TODO
		return null;
	}

}
