package com.ldz.biz.config;


import com.ldz.sys.service.SysMessageService;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

@Slf4j
public class MessageReceiver implements MessageListener {

	Logger errorLog = LoggerFactory.getLogger("error_info");

	private SysMessageService sysMessageService;

	private RedisTemplateUtil redisTemplate;

	public MessageReceiver(RedisTemplateUtil redisTemplate,SysMessageService sysMessageService) {
		this.sysMessageService = sysMessageService;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		val redisChannel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
		val eventMessage = redisTemplate.getValueSerializer().deserialize(message.getBody());
		String topic = redisChannel;
		errorLog.debug("********************"+topic+"__" + eventMessage.toString());
		System.out.println("********************"+topic+"__" + eventMessage.toString());
		log.info("订阅的topic:"+topic);
		log.info("订阅的值:"+eventMessage.toString());

		switch (topic){
			case "now_send_message":
				sysMessageService.nowSendMessage(eventMessage.toString());//立刻下发消息
				break;
//			case "gps":
//				gpsservice.onReceiveGps(gpsInfo);
//				break;
		}

		System.out.println("收到一条消息："+redisChannel);
	}

}
