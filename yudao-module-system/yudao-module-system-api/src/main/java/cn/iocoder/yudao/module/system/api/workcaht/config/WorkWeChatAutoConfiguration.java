package cn.iocoder.yudao.module.system.api.workcaht.config;

import cn.iocoder.yudao.module.system.api.workwe.biz.TokenRefresh.TokenRefresher;
import cn.iocoder.yudao.module.system.api.workwe.biz.TokenRefresh.TokenScheduleTask;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 企业微信自动配置类
 */
@Configuration
@EnableConfigurationProperties(WorkWeChatProperties.class)
public class WorkWeChatAutoConfiguration {
    
    /**
     * 创建企业微信配置Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public WorkWeChatConfig workWeChatConfig(WorkWeChatProperties properties) {
        return properties.toWorkWeChatConfig();
    }
    
    /**
     * 创建Token刷新器Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public TokenRefresher tokenRefresher() {
        return new TokenRefresher();
    }
    
    /**
     * 创建Token定时任务Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public TokenScheduleTask tokenScheduleTask(TokenRefresher tokenRefresher, 
                                             WorkWeChatConfig workWeChatConfig,
                                             StringRedisTemplate redisTemplate) {
        TokenScheduleTask task = new TokenScheduleTask();
        return task;
    }
} 