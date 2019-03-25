package com.ldz.biz.mq;


import com.ldz.util.redis.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Redis消息队列的消费者
 * 短信消息订阅
 *
 */
@Component
public class SMSMessageListener implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SMSMessageListener.class);
    @Autowired
    private RedisTemplateUtil redisTemplate;
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("短信消息下发线程开启!");
        new Thread(() -> {
            while (true) {
                try {
                    Object val=redisTemplate.opsForList().rightPop("sms_send_message_lis");
                    if(val!=null){
                        String messageId = val.toString();
                        System.out.printf("查询到ID"+messageId);
//                        bizMainSerivce.sendMesageToUser(messageId);
//                        SendSmsUtil.sendSms(map);//短信下发
                        System.out.println("处理成功，被清除"+messageId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
                try {
                    Thread.sleep(1000*1);     //1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }).start();
    }
}