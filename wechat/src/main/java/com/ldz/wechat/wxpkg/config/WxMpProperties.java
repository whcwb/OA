package com.ldz.wechat.wxpkg.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "wechat")
public class WxMpProperties {
  /**
   * 设置微信公众号的corpId
   */
  private String appId;

  /**
   * 设置微信公众应用的Secret
   */
  private String secret;

  /**
   * 设置微信公众号的token
   */
  private String token;

  /**
   * 设置微信公众号的EncodingAESKey
   */
  private String aesKey;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}