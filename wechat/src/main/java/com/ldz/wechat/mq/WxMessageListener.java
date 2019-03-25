package com.ldz.wechat.mq;


import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.wechat.service.BizMainSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Redis消息队列的消费者
 * 微信消息订阅
 *
 */
@Component
public class WxMessageListener implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(WxMessageListener.class);
    @Autowired
    private RedisTemplateUtil redisTemplate;

    //基础方法
    @Autowired
    private BizMainSerivce bizMainSerivce;


    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("消息下发线程开启!");
        new Thread(() -> {
            while (true) {
                try {
                    Object val=redisTemplate.opsForList().rightPop("wx_send_message_lis");
                    if(val!=null){
                        String messageId = val.toString();
                        System.out.printf("查询到ID"+messageId);
                        bizMainSerivce.sendMesageToUser(messageId);
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