package com.ldz.wechat.wxpkg.handler;

import com.ldz.wechat.mapper.TraineeInformationMapper;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.TrainingRecordService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class ScanHandler extends AbstractHandler {
	
	@Autowired
	private StringRedisTemplate stringRedisDao;

	@Autowired
	private TrainingRecordService recordService;

	@Autowired
	private TraineeInformationMapper informationMapper;
//	@Autowired
//	private PtyhService ptyhService;

//	@Autowired
//	private WechatService wechatService;


	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) {
		String ticket = wxMessage.getTicket();
		this.logger.info("新关注用户 ticket 扫码: " + wxMessage.getTicket());
		if(org.apache.commons.lang.StringUtils.isNotEmpty(ticket)){
			String EventKey=wxMessage.getEventKey();
			if(StringUtils.isNotEmpty(EventKey)){
				//获取用户openId
				String openId = wxMessage.getFromUser();
				//进入扫码状态区分学员大写X或者教练大写J
				//这里处理我的逻辑，1.验证用户是否绑定openid   2.添加签到信息（也就是在培训表中新增一条信息）

			}
		}

		//扫码进入公众号
		if (wxMessage.getEvent().equalsIgnoreCase(WxConsts.EventType.SCAN)){
//			String imei = this.stringRedisDao.boundValueOps(ticket).get();
//			if (StringUtils.isBlank(imei)){
//
//			}else{}

			TraineeInformation information = informationMapper.queryByOpenId(wxMessage.getFromUser());
			if(information == null){
				return WxMpXmlOutMessage.TEXT().content("您尚未登录").fromUser(wxMessage.getToUser())
					.toUser(wxMessage.getFromUser()).build();
			}else{
				recordService.afterScan(wxMessage.getEventKey(),wxMessage.getFromUser(),information.getId());
				return WxMpXmlOutMessage.TEXT().content("签到成功").fromUser(wxMessage.getToUser())
					.toUser(wxMessage.getFromUser()).build();
			}

		}
		
		return WxMpXmlOutMessage.TEXT().content("请求消息不正确").fromUser(wxMessage.getToUser())
				.toUser(wxMessage.getFromUser()).build();
	}
}
