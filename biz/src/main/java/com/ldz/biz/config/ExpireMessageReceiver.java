package com.ldz.biz.config;


import com.ldz.sys.service.SysMessageService;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * 过期的消息订单
 */
@Slf4j
public class ExpireMessageReceiver implements MessageListener {
	Logger errorLog = LoggerFactory.getLogger("error_info");

	private SysMessageService sysMessageService;


	private RedisTemplateUtil redisTemplate;

	public ExpireMessageReceiver(RedisTemplateUtil redisTemplate, SysMessageService sysMessageService) {
		this.redisTemplate = redisTemplate;
		this.sysMessageService=sysMessageService;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
		String itemValue = new String(message.getBody());
		String topic =  new String(message.getChannel());
		errorLog.debug("过期的消息订单********************"+topic+"__" + itemValue);

		if(org.apache.commons.lang3.StringUtils.indexOf(itemValue,"sendMessageEstimated")==0){
			sysMessageService.expireMessage(itemValue.replaceAll("sendMessageEstimated",""));
		}
		System.out.println("收到一条消息："+itemValue);
		}catch (Exception e){
			e.printStackTrace();
			errorLog.error("延时发送，消息订阅报出异常",e);
		}
	}
}