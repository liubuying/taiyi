package cn.iocoder.yudao.module.system.api.workcaht;

import cn.iocoder.yudao.module.system.api.workwe.biz.TokenRefresh.TokenRefresher;
import cn.iocoder.yudao.module.system.api.workwe.config.WorkWeChatConfig;
import cn.iocoder.yudao.module.system.api.workwe.dto.CorpInfoDTO;
import cn.iocoder.yudao.module.system.api.workwe.dto.TokenRefresh.TokenResult;
import cn.iocoder.yudao.module.system.api.workwe.service.CorpInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 企业微信API接口实现类
 */
@Service
public class WorkWeChatApiImpl implements WorkWeChatApi {
    private static final Logger log = LoggerFactory.getLogger(WorkWeChatApiImpl.class);

    @Autowired
    private TokenRefresher tokenRefresher;
    
    @Autowired
    private WorkWeChatConfig config;
    
    @Autowired(required = false)
    private CorpInfoService corpInfoService;
    
    private StringRedisTemplate redisTemplate;
    
    @Override
    public TokenResult getToken(Long appId) throws Exception {
        if (config.getCorpId() == null || config.getCorpSecret() == null) {
            throw new IllegalStateException("企业ID或应用密钥未设置");
        }
        return getToken(config.getCorpId(), config.getCorpSecret(), appId);
    }
    
    @Override
    public TokenResult getToken(String corpId, String corpSecret, Long appId) throws Exception {
        checkRedisTemplate();
        return tokenRefresher.getToken(corpId, corpSecret, appId, redisTemplate, config);
    }
    
    @Override
    public void refreshAllTokens() throws Exception {
        checkRedisTemplate();
        tokenRefresher.refreshAllTokens(redisTemplate, config);
    }
    
    @Override
    public CorpInfoDTO addCorpInfo(CorpInfoDTO corpInfo) throws Exception {
        checkRedisTemplate();
        if (corpInfoService == null) {
            throw new IllegalStateException("未配置企业应用管理服务");
        }
        
        // 保存企业应用信息到数据库
        CorpInfoDTO savedCorpInfo = corpInfoService.addCorpInfo(corpInfo);
        
        // 立即刷新获取token
        boolean refreshSuccess = tokenRefresher.refreshTokenImmediately(
                savedCorpInfo, redisTemplate, config);
        
        if (!refreshSuccess) {
            log.warn("新增企业应用后刷新token失败，企业ID: {}, 应用ID: {}", 
                    savedCorpInfo.getCorpId(), savedCorpInfo.getAppId());
        }
        
        return savedCorpInfo;
    }
    
    @Override
    public List<CorpInfoDTO> listAllEnabledCorps() {
        if (corpInfoService == null) {
            return Collections.emptyList();
        }
        return corpInfoService.listAllEnabledCorps();
    }
    
    @Override
    public boolean updateCorpInfo(CorpInfoDTO corpInfo) {
        if (corpInfoService == null) {
            return false;
        }
        return corpInfoService.updateCorpInfo(corpInfo);
    }
    
    @Override
    public boolean isTokenValid(String corpId, Long appId) {
        checkRedisTemplate();
        return tokenRefresher.isTokenValid(corpId, appId, redisTemplate);
    }
    
    @Override
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    /**
     * 设置配置对象
     * 
     * @param config 配置对象
     */
    public void setConfig(WorkWeChatConfig config) {
        this.config = config;
    }
    
    /**
     * 设置企业ID
     * 
     * @param corpId 企业ID
     */
    public void setCorpId(String corpId) {
        if (this.config == null) {
            this.config = new WorkWeChatConfig();
        }
        this.config.setCorpId(corpId);
    }
    
    /**
     * 设置应用密钥
     * 
     * @param corpSecret 应用密钥
     */
    public void setCorpSecret(String corpSecret) {
        if (this.config == null) {
            this.config = new WorkWeChatConfig();
        }
        this.config.setCorpSecret(corpSecret);
    }
    
    /**
     * 检查RedisTemplate是否已设置
     */
    private void checkRedisTemplate() {
        if (redisTemplate == null) {
            throw new IllegalStateException("RedisTemplate未设置，请先调用setRedisTemplate方法");
        }
    }
} 