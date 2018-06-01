package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
/**
 * 以下的信息都可以在商户平台登录查看
 */
public class WechatAccountConfig {
    //appId
    private String mpAppId;
    //appSecret
    private String mpAppSecret;
    //商户id
    private String mchId;
    //商户秘钥
    private String mchKey;
    //证书路径
    private String keyPath;

    private String notifyUrl;
}
