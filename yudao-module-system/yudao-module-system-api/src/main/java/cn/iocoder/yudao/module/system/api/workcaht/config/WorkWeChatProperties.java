package cn.iocoder.yudao.module.system.api.workcaht.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 企业微信配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "work-wechat")
public class WorkWeChatProperties {
    
    /**
     * 企业ID
     */
    private String corpId;
    
    /**
     * 应用密钥
     */
    private String corpSecret;
    
    /**
     * token缓存过期时间（秒）
     * 默认为7000秒（略小于企业微信token的2小时有效期）
     */
    private Long tokenCacheExpiry = 7000L;
    
    /**
     * 分布式锁过期时间（秒）
     * 默认为60秒
     */
    private Long lockExpiry = 60L;
    
    /**
     * 是否启用自动刷新token
     * 默认为true
     */
    private Boolean enableAutoRefresh = true;
    
    /**
     * 转换为WorkWeChatConfig对象
     * 
     * @return WorkWeChatConfig对象
     */
    public WorkWeChatConfig toWorkWeChatConfig() {
        WorkWeChatConfig config = new WorkWeChatConfig();
        config.setCorpId(this.corpId);
        config.setCorpSecret(this.corpSecret);
        config.setTokenCacheExpiry(this.tokenCacheExpiry);
        config.setLockExpiry(this.lockExpiry);
        config.setEnableAutoRefresh(this.enableAutoRefresh);
        return config;
    }
} 