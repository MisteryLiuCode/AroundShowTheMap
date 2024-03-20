package com.macro.mall.tiny.config;

import com.macro.mall.tiny.common.domain.AliyunProperties;
import com.macro.mall.tiny.common.service.OSSUnity;
import com.macro.mall.tiny.common.service.impl.AliyunServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS自动配置类
 *
 * @author 轻描淡写 linkxs@qq.com
 */
@Configuration
@EnableConfigurationProperties({AliyunProperties.class})
public class OSSAutoConfiguration {

    @Bean
    public OSSUnity builderOSS() {
        return new AliyunServiceImpl();
    }
}
