package com.ldz.wechat.util;

import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by chenwei on 16/9/5.
 */
@Slf4j
@Component
public class WechatUtils {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;

    @Value("${wechat.domain}")
    private String domain;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public String getOpenid(String code){
        logger.info("code----------"+code);
        String result = HttpUtil
                .get("https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                        + appId
                        + "&secret="
                        + secret
                        + "&code="
                        + code
                        + "&grant_type=authorization_code");
        Map<?, ?> bean = JsonUtil.toBean(result, Map.class);
        if(bean.get("openid") == null){
            logger.info("返回openid----------null");
            return null;
        }
        logger.info("--------------返回openid"+bean.get("openid").toString());
        return bean.get("openid").toString();
    }

}
