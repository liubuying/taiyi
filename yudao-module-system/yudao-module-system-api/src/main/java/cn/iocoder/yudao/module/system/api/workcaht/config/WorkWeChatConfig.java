package cn.iocoder.yudao.module.system.api.workcaht.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业微信配置类
 */
@Data
public class WorkWeChatConfig {
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
     * 多企业应用配置列表
     */
    private List<CorpAppConfig> corpApps = new ArrayList<>();
    
    /**
     * 创建默认配置
     * 
     * @return 默认配置对象
     */
    public static WorkWeChatConfig createDefault() {
        return new WorkWeChatConfig();
    }
    
    /**
     * 创建包含企业信息的配置
     * 
     * @param corpId 企业ID
     * @param corpSecret 应用密钥
     * @return 配置对象
     */
    public static WorkWeChatConfig create(String corpId, String corpSecret) {
        WorkWeChatConfig config = new WorkWeChatConfig();
        config.setCorpId(corpId);
        config.setCorpSecret(corpSecret);
        return config;
    }
    
    /**
     * 添加企业应用配置
     * 
     * @param corpId 企业ID
     * @param corpSecret 应用密钥
     * @param appId 应用ID
     * @return 当前配置对象（链式调用）
     */
    public WorkWeChatConfig addCorpApp(String corpId, String corpSecret, Long appId) {
        CorpAppConfig corpApp = new CorpAppConfig();
        corpApp.setCorpId(corpId);
        corpApp.setCorpSecret(corpSecret);
        corpApp.setAppId(appId);
        this.corpApps.add(corpApp);
        return this;
    }
    
    /**
     * 企业应用配置
     */
    @Data
    public static class CorpAppConfig {
        /**
         * 企业ID
         */
        private String corpId;
        
        /**
         * 应用密钥
         */
        private String corpSecret;
        
        /**
         * 应用ID
         */
        private Long appId;
    }
} 