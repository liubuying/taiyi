package cn.iocoder.yudao.module.system.api.workcaht;

import cn.iocoder.yudao.module.system.api.workwe.config.WorkWeChatConfig;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 企业微信API构建器
 * 用于创建WorkWeChatApi实例
 */
public class WorkWeChatApiBuilder {
    
    /**
     * 构建企业微信API实例
     *
     * @param redisTemplate Redis操作模板
     * @param corpId 企业ID
     * @param corpSecret 应用密钥
     * @return WorkWeChatApi实例
     */
    public static WorkWeChatApi build(StringRedisTemplate redisTemplate, String corpId, String corpSecret) {
        WorkWeChatConfig config = WorkWeChatConfig.create(corpId, corpSecret);
        WorkWeChatApiImpl apiImpl = new WorkWeChatApiImpl();
        apiImpl.setRedisTemplate(redisTemplate);
        apiImpl.setConfig(config);
        return apiImpl;
    }
    
    /**
     * 构建企业微信API实例，使用自定义配置
     *
     * @param redisTemplate Redis操作模板
     * @param config 自定义配置
     * @return WorkWeChatApi实例
     */
    public static WorkWeChatApi build(StringRedisTemplate redisTemplate, WorkWeChatConfig config) {
        WorkWeChatApiImpl apiImpl = new WorkWeChatApiImpl();
        apiImpl.setRedisTemplate(redisTemplate);
        apiImpl.setConfig(config);
        return apiImpl;
    }
    
    /**
     * 使用默认配置构建企业微信API实例，可以后续添加多个企业应用配置
     * 注意：使用默认配置时，需要后续调用addCorpApp方法添加企业应用信息
     *
     * @param redisTemplate Redis操作模板
     * @return WorkWeChatApi实例
     */
    public static WorkWeChatApi buildWithDefaultConfig(StringRedisTemplate redisTemplate) {
        WorkWeChatConfig config = WorkWeChatConfig.createDefault();
        WorkWeChatApiImpl apiImpl = new WorkWeChatApiImpl();
        apiImpl.setRedisTemplate(redisTemplate);
        apiImpl.setConfig(config);
        return apiImpl;
    }
    
    /**
     * 创建支持多企业多应用的API实例
     * 
     * @param redisTemplate Redis操作模板
     * @param configs 多个企业应用配置，格式为 {{corpId1, corpSecret1, appId1}, {corpId2, corpSecret2, appId2}, ...}
     * @return WorkWeChatApi实例
     */
    public static WorkWeChatApi buildMultiCorpApps(StringRedisTemplate redisTemplate, Object[][] configs) {
        WorkWeChatConfig config = WorkWeChatConfig.createDefault();
        
        for (Object[] corpConfig : configs) {
            if (corpConfig.length >= 3) {
                String corpId = String.valueOf(corpConfig[0]);
                String corpSecret = String.valueOf(corpConfig[1]);
                Long appId = Long.valueOf(String.valueOf(corpConfig[2]));
                config.addCorpApp(corpId, corpSecret, appId);
            }
        }
        
        WorkWeChatApiImpl apiImpl = new WorkWeChatApiImpl();
        apiImpl.setRedisTemplate(redisTemplate);
        apiImpl.setConfig(config);
        return apiImpl;
    }
} 