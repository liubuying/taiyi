package cn.iocoder.yudao.module.system.api.workcaht.biz.TokenRefresh;

import cn.iocoder.yudao.module.system.api.workwe.config.WorkWeChatConfig;
import cn.iocoder.yudao.module.system.api.workwe.dto.CorpInfoDTO;
import cn.iocoder.yudao.module.system.api.workwe.dto.HttpUtils.HttpClientUtils;
import cn.iocoder.yudao.module.system.api.workwe.dto.TokenRefresh.AppInfo;
import cn.iocoder.yudao.module.system.api.workwe.dto.TokenRefresh.JsapiTokenResponseDTO;
import cn.iocoder.yudao.module.system.api.workwe.dto.TokenRefresh.TokenResponseDTO;
import cn.iocoder.yudao.module.system.api.workwe.dto.TokenRefresh.TokenResult;
import cn.iocoder.yudao.module.system.api.workwe.service.CorpInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 企业微信Token刷新器
 * 负责获取、刷新和缓存企业微信的access_token和jsapi_ticket
 */
@Component
public class TokenRefresher {
    private static final Logger log = LoggerFactory.getLogger(TokenRefresher.class);
    
    // 企业微信获取token的API URL
    private static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    // 获取jsapi_ticket的API URL
    private static final String JSAPI_TICKET_URL = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=%s";
    
    // Redis缓存中access_token的key前缀
    private static final String ACCESS_TOKEN_KEY_PREFIX = "workwe_access_token_";
    // Redis缓存中jsapi_ticket的key前缀
    private static final String JSAPI_TICKET_KEY_PREFIX = "workwe_jsapi_ticket_";
    // 分布式锁的key前缀
    private static final String LOCK_KEY_PREFIX = "workwe_token_lock_";
    // 全局刷新锁
    private static final String GLOBAL_REFRESH_LOCK = "workwe_global_refresh_lock";
    
    // 最大失败重试次数
    private static final int MAX_FAIL_COUNT = 3;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired(required = false)
    private CorpInfoService corpInfoService;
    
    // Redis Lua脚本用于释放锁（确保只有加锁者才能释放锁）
    private static final String RELEASE_LOCK_SCRIPT = 
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "    return redis.call('del', KEYS[1]) " +
            "else " +
            "    return 0 " +
            "end";
    
    /**
     * 获取某个应用的token
     * 
     * @param corpId 企业ID
     * @param corpSecret 应用密钥
     * @param appId 应用ID
     * @param redisTemplate Redis操作模板
     * @param config 配置对象
     * @return 包含access_token和jsapi_ticket的结果对象
     * @throws Exception 如果获取过程中发生异常
     */
    public TokenResult getToken(String corpId, String corpSecret, Long appId, 
                              StringRedisTemplate redisTemplate, WorkWeChatConfig config) throws Exception {
        if (corpId == null || corpSecret == null) {
            throw new IllegalArgumentException("企业ID和应用密钥不能为空");
        }
        
        // 构建Redis缓存key
        String accessTokenKey = getAccessTokenKey(corpId, appId);
        String jsapiTicketKey = getJsapiTicketKey(corpId, appId);
        String lockKey = getLockKey(corpId, appId);
        
        // 尝试从Redis缓存获取token
        String cachedAccessToken = redisTemplate.opsForValue().get(accessTokenKey);
        String cachedJsapiTicket = redisTemplate.opsForValue().get(jsapiTicketKey);
        
        // 如果缓存中有值，直接返回
        if (cachedAccessToken != null && cachedJsapiTicket != null) {
            log.info("从缓存获取token成功，corpId: {}, appId: {}", corpId, appId);
            return new TokenResult(cachedAccessToken, cachedJsapiTicket);
        }
        
        // 缓存中没有，尝试获取分布式锁进行刷新
        String lockValue = UUID.randomUUID().toString();
        boolean locked = acquireLock(redisTemplate, lockKey, lockValue, config.getLockExpiry());
        
        try {
            if (locked) {
                // 获取锁成功，先再次检查缓存（双重检查锁定，防止其他线程已经刷新了token）
                cachedAccessToken = redisTemplate.opsForValue().get(accessTokenKey);
                cachedJsapiTicket = redisTemplate.opsForValue().get(jsapiTicketKey);
                
                if (cachedAccessToken != null && cachedJsapiTicket != null) {
                    log.info("获取锁后再次检查，缓存中已有token，corpId: {}, appId: {}", corpId, appId);
                    return new TokenResult(cachedAccessToken, cachedJsapiTicket);
                }
                
                // 刷新token并更新数据库刷新状态
                TokenResult result = null;
                try {
                    // 缓存真的没有，调用企业微信API获取access_token
                    String accessToken = fetchAndCacheAccessToken(corpId, corpSecret, accessTokenKey, 
                                                            redisTemplate, config.getTokenCacheExpiry());
                    // 使用access_token获取jsapi_ticket
                    String jsapiTicket = fetchAndCacheJsapiTicket(accessToken, jsapiTicketKey, 
                                                            redisTemplate, config.getTokenCacheExpiry());
                    
                    result = new TokenResult(accessToken, jsapiTicket);
                    
                    // 如果配置了企业应用管理服务，更新刷新状态
                    if (corpInfoService != null) {
                        corpInfoService.updateTokenRefreshStatus(corpId, appId, true);
                    }
                    
                    return result;
                } catch (Exception e) {
                    // 刷新失败，更新数据库状态
                    if (corpInfoService != null) {
                        corpInfoService.updateTokenRefreshStatus(corpId, appId, false);
                    }
                    throw e;
                }
            } else {
                // 获取锁失败，说明有其他线程正在刷新，等待一段时间后重试获取缓存
                log.info("获取锁失败，等待其他线程刷新token，corpId: {}, appId: {}", corpId, appId);
                Thread.sleep(500); // 等待500毫秒
                
                // 重新获取缓存
                cachedAccessToken = redisTemplate.opsForValue().get(accessTokenKey);
                cachedJsapiTicket = redisTemplate.opsForValue().get(jsapiTicketKey);
                
                if (cachedAccessToken != null && cachedJsapiTicket != null) {
                    return new TokenResult(cachedAccessToken, cachedJsapiTicket);
                } else {
                    // 经过等待后仍然没有获取到缓存，可能是其他线程执行失败
                    // 递归尝试，最多重试3次
                    return retryGetToken(corpId, corpSecret, appId, redisTemplate, config, 1, 3);
                }
            }
        } finally {
            // 释放锁
            if (locked) {
                releaseLock(redisTemplate, lockKey, lockValue);
            }
        }
    }
    
    /**
     * 重试获取token，防止死锁情况
     */
    private TokenResult retryGetToken(String corpId, String corpSecret, Long appId, 
                                    StringRedisTemplate redisTemplate, WorkWeChatConfig config, 
                                    int currentRetry, int maxRetries) throws Exception {
        if (currentRetry > maxRetries) {
            throw new RuntimeException("获取token重试次数超过上限: " + maxRetries);
        }
        
        log.info("尝试重新获取token，第{}次重试，corpId: {}, appId: {}", currentRetry, corpId, appId);
        Thread.sleep(1000 * currentRetry); // 每次重试增加等待时间
        
        // 重新检查缓存
        String accessTokenKey = getAccessTokenKey(corpId, appId);
        String jsapiTicketKey = getJsapiTicketKey(corpId, appId);
        
        String cachedAccessToken = redisTemplate.opsForValue().get(accessTokenKey);
        String cachedJsapiTicket = redisTemplate.opsForValue().get(jsapiTicketKey);
        
        if (cachedAccessToken != null && cachedJsapiTicket != null) {
            return new TokenResult(cachedAccessToken, cachedJsapiTicket);
        }
        
        // 如果缓存仍然不存在，递归重试
        return getToken(corpId, corpSecret, appId, redisTemplate, config);
    }
    
    /**
     * 刷新所有应用的token并缓存，从数据库读取企业应用信息
     * 
     * @param redisTemplate Redis操作模板
     * @param config 配置对象 
     * @throws Exception 如果刷新过程中发生异常
     */
    public void refreshAllTokens(StringRedisTemplate redisTemplate, WorkWeChatConfig config) throws Exception {
        // 检查是否启用了自动刷新
        if (!config.getEnableAutoRefresh()) {
            log.info("自动刷新token功能已禁用，跳过本次刷新");
            return;
        }
        
        // 尝试获取全局分布式锁
        String lockValue = UUID.randomUUID().toString();
        boolean locked = acquireLock(redisTemplate, GLOBAL_REFRESH_LOCK, lockValue, config.getLockExpiry());
        
        if (!locked) {
            log.info("获取全局刷新锁失败，跳过本次定时刷新");
            return;
        }
        
        try {
            // 获取锁成功，执行刷新
            log.info("获取全局刷新锁成功，开始执行定时刷新任务");
            
            // 首先刷新主配置的应用（兼容旧配置）
            if (config.getCorpId() != null && config.getCorpSecret() != null) {
                // 使用默认的应用ID 1000001L
                refreshToken(config.getCorpId(), config.getCorpSecret(), 1000001L, 
                          redisTemplate, config);
            }
            
            // 然后刷新数据库中的企业应用
            if (corpInfoService != null) {
                // 从数据库获取所有启用的企业应用
                List<CorpInfoDTO> corpList = corpInfoService.listAllEnabledCorps();
                for (CorpInfoDTO corp : corpList) {
                    refreshToken(corp.getCorpId(), corp.getCorpSecret(), corp.getAppId(), 
                              redisTemplate, config);
                }
                
                // 检查并尝试刷新之前失败的企业应用
                retryFailedTokens(redisTemplate, config);
            }
            
            log.info("所有企业应用的token刷新任务执行完毕");
        } finally {
            // 释放全局锁
            releaseLock(redisTemplate, GLOBAL_REFRESH_LOCK, lockValue);
        }
    }
    
    /**
     * 尝试刷新之前失败的企业应用token
     */
    private void retryFailedTokens(StringRedisTemplate redisTemplate, WorkWeChatConfig config) {
        if (corpInfoService == null) {
            return;
        }
        
        try {
            List<CorpInfoDTO> failedCorps = corpInfoService.listCorpsNeedRetry(MAX_FAIL_COUNT);
            log.info("发现{}个失败的企业应用需要重试刷新token", failedCorps.size());
            
            for (CorpInfoDTO corp : failedCorps) {
                try {
                    refreshToken(corp.getCorpId(), corp.getCorpSecret(), corp.getAppId(), 
                              redisTemplate, config);
                } catch (Exception e) {
                    log.error("重试刷新token失败，corpId: {}, appId: {}, 错误: {}", 
                            corp.getCorpId(), corp.getAppId(), e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("重试失败的token刷新过程发生错误", e);
        }
    }
    
    /**
     * 立即刷新指定企业应用的token（用于添加新企业后调用）
     * 
     * @param corpInfoDTO 企业应用信息
     * @param redisTemplate Redis操作模板
     * @param config 配置对象
     * @return 是否刷新成功
     */
    public boolean refreshTokenImmediately(CorpInfoDTO corpInfoDTO, 
                                         StringRedisTemplate redisTemplate, 
                                         WorkWeChatConfig config) {
        if (corpInfoDTO == null || corpInfoDTO.getCorpId() == null || 
            corpInfoDTO.getCorpSecret() == null || corpInfoDTO.getAppId() == null) {
            log.error("企业应用信息不完整，无法刷新token");
            return false;
        }
        
        try {
            // 刷新token
            refreshToken(corpInfoDTO.getCorpId(), corpInfoDTO.getCorpSecret(), 
                      corpInfoDTO.getAppId(), redisTemplate, config);
            return true;
        } catch (Exception e) {
            log.error("立即刷新token失败，corpId: {}, appId: {}, 错误: {}", 
                    corpInfoDTO.getCorpId(), corpInfoDTO.getAppId(), e.getMessage());
            return false;
        }
    }
    
    /**
     * 检查token是否有效
     * 
     * @param corpId 企业ID
     * @param appId 应用ID
     * @param redisTemplate Redis操作模板
     * @return 是否有效
     */
    public boolean isTokenValid(String corpId, Long appId, StringRedisTemplate redisTemplate) {
        String accessTokenKey = getAccessTokenKey(corpId, appId);
        String jsapiTicketKey = getJsapiTicketKey(corpId, appId);
        
        String cachedAccessToken = redisTemplate.opsForValue().get(accessTokenKey);
        String cachedJsapiTicket = redisTemplate.opsForValue().get(jsapiTicketKey);
        
        return cachedAccessToken != null && cachedJsapiTicket != null;
    }
    
    /**
     * 刷新指定企业应用的token
     * 
     * @param corpId 企业ID
     * @param corpSecret 应用密钥
     * @param appId 应用ID
     * @param redisTemplate Redis操作模板
     * @param config 配置对象
     * @throws Exception 如果刷新过程中发生异常
     */
    private void refreshToken(String corpId, String corpSecret, Long appId,
                           StringRedisTemplate redisTemplate, WorkWeChatConfig config) throws Exception {
        if (corpId == null || corpSecret == null || appId == null) {
            log.warn("企业ID、应用密钥或应用ID为空，跳过刷新");
            return;
        }
        
        // 为应用获取独立的锁
        String appLockKey = getLockKey(corpId, appId);
        String appLockValue = UUID.randomUUID().toString();
        boolean appLocked = acquireLock(redisTemplate, appLockKey, appLockValue, config.getLockExpiry());
        
        if (!appLocked) {
            log.info("获取应用锁失败，跳过刷新，corpId: {}, appId: {}", corpId, appId);
            return;
        }
        
        try {
            // 构建Redis key
            String accessTokenKey = getAccessTokenKey(corpId, appId);
            String jsapiTicketKey = getJsapiTicketKey(corpId, appId);
            
            // 刷新access_token和jsapi_ticket
            String accessToken = fetchAndCacheAccessToken(corpId, corpSecret, accessTokenKey, 
                                                      redisTemplate, config.getTokenCacheExpiry());
            fetchAndCacheJsapiTicket(accessToken, jsapiTicketKey, 
                                  redisTemplate, config.getTokenCacheExpiry());
            
            log.info("刷新token成功，corpId: {}, appId: {}", corpId, appId);
            
            // 更新数据库刷新状态
            if (corpInfoService != null) {
                corpInfoService.updateTokenRefreshStatus(corpId, appId, true);
            }
        } catch (Exception e) {
            log.error("刷新token失败，corpId: {}, appId: {}, 错误: {}", corpId, appId, e.getMessage());
            
            // 更新数据库刷新状态
            if (corpInfoService != null) {
                corpInfoService.updateTokenRefreshStatus(corpId, appId, false);
            }
            throw e;
        } finally {
            // 释放应用锁
            releaseLock(redisTemplate, appLockKey, appLockValue);
        }
    }
    
    /**
     * 获取访问令牌的Redis键
     * 
     * @param corpId 企业ID
     * @param appId 应用ID
     * @return 访问令牌键
     */
    private String getAccessTokenKey(String corpId, Long appId) {
        return ACCESS_TOKEN_KEY_PREFIX + corpId + "_" + appId;
    }
    
    /**
     * 获取JS API票据的Redis键
     * 
     * @param corpId 企业ID
     * @param appId 应用ID
     * @return JS API票据键
     */
    private String getJsapiTicketKey(String corpId, Long appId) {
        return JSAPI_TICKET_KEY_PREFIX + corpId + "_" + appId;
    }
    
    /**
     * 获取锁的Redis键
     * 
     * @param corpId 企业ID
     * @param appId 应用ID
     * @return 锁键
     */
    private String getLockKey(String corpId, Long appId) {
        return LOCK_KEY_PREFIX + corpId + "_" + appId;
    }
    
    /**
     * 获取分布式锁
     * 
     * @param redisTemplate Redis操作模板
     * @param lockKey 锁的key
     * @param lockValue 锁的值（用于标识锁的所有者）
     * @param expireSeconds 锁的过期时间（秒）
     * @return 是否获取锁成功
     */
    private boolean acquireLock(StringRedisTemplate redisTemplate, String lockKey, String lockValue, long expireSeconds) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, expireSeconds, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }
    
    /**
     * 释放分布式锁
     * 使用Lua脚本确保原子性操作（只有加锁者才能释放锁）
     * 
     * @param redisTemplate Redis操作模板
     * @param lockKey 锁的key
     * @param lockValue 锁的值（用于验证是否是锁的所有者）
     */
    private void releaseLock(StringRedisTemplate redisTemplate, String lockKey, String lockValue) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(RELEASE_LOCK_SCRIPT);
        redisScript.setResultType(Long.class);
        
        redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockValue);
    }
    
    /**
     * 从企业微信API获取access_token并缓存到Redis
     */
    private String fetchAndCacheAccessToken(String corpId, String corpSecret, String redisKey, 
                                         StringRedisTemplate redisTemplate, Long cacheExpirySeconds) throws Exception {
        String url = String.format(ACCESS_TOKEN_URL, corpId, corpSecret);
        String response = HttpClientUtils.get(url);
        
        TokenResponseDTO tokenResponse = objectMapper.readValue(response, TokenResponseDTO.class);
        if (tokenResponse.getErrcode() != 0) {
            throw new RuntimeException("获取access_token失败: " + tokenResponse.getErrmsg());
        }
        
        String accessToken = tokenResponse.getAccess_token();
        // 将token存入Redis，使用配置的过期时间
        redisTemplate.opsForValue().set(redisKey, accessToken, cacheExpirySeconds, TimeUnit.SECONDS);
        log.info("成功获取并缓存access_token，corpId: {}", corpId);
        
        return accessToken;
    }
    
    /**
     * 从企业微信API获取jsapi_ticket并缓存到Redis
     */
    private String fetchAndCacheJsapiTicket(String accessToken, String redisKey, 
                                         StringRedisTemplate redisTemplate, Long cacheExpirySeconds) throws Exception {
        String url = String.format(JSAPI_TICKET_URL, accessToken);
        String response = HttpClientUtils.get(url);
        
        JsapiTokenResponseDTO jsapiResponse = objectMapper.readValue(response, JsapiTokenResponseDTO.class);
        if (jsapiResponse.getErrcode() != 0) {
            throw new RuntimeException("获取jsapi_ticket失败: " + jsapiResponse.getErrmsg());
        }
        
        String jsapiTicket = jsapiResponse.getTicket();
        // 将jsapi_ticket存入Redis，使用配置的过期时间
        redisTemplate.opsForValue().set(redisKey, jsapiTicket, cacheExpirySeconds, TimeUnit.SECONDS);
        log.info("成功获取并缓存jsapi_ticket");
        
        return jsapiTicket;
    }
} 