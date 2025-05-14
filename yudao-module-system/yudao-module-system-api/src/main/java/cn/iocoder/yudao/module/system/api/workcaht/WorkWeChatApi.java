package cn.iocoder.yudao.module.system.api.workcaht;

import cn.iocoder.yudao.module.system.api.workwe.dto.CorpInfoDTO;
import cn.iocoder.yudao.module.system.api.workwe.dto.TokenRefresh.TokenResult;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * 企业微信API接口
 * 提供企业微信相关功能的统一API
 */
public interface WorkWeChatApi {
    
    /**
     * 获取企业微信access_token和jsapi_ticket
     * 使用默认配置中的企业信息和指定的应用ID
     *
     * @param appId 应用ID
     * @return 包含access_token和jsapi_ticket的结果对象
     * @throws Exception 如果获取过程中发生异常
     */
    TokenResult getToken(Long appId) throws Exception;
    
    /**
     * 获取企业微信access_token和jsapi_ticket，使用指定的企业信息
     *
     * @param corpId 企业ID
     * @param corpSecret 应用密钥
     * @param appId 应用ID
     * @return 包含access_token和jsapi_ticket的结果对象
     * @throws Exception 如果获取过程中发生异常
     */
    TokenResult getToken(String corpId, String corpSecret, Long appId) throws Exception;
    
    /**
     * 刷新所有企业应用的token
     *
     * @throws Exception 如果刷新过程中发生异常
     */
    void refreshAllTokens() throws Exception;
    
    /**
     * 添加新的企业应用配置到数据库并立即刷新token
     *
     * @param corpInfo 企业应用配置信息
     * @return 添加的企业应用信息，包含ID
     * @throws Exception 如果添加过程中发生异常
     */
    CorpInfoDTO addCorpInfo(CorpInfoDTO corpInfo) throws Exception;
    
    /**
     * 获取所有已配置的启用企业应用
     *
     * @return 企业应用列表
     */
    List<CorpInfoDTO> listAllEnabledCorps();
    
    /**
     * 更新企业应用配置
     *
     * @param corpInfo 企业应用配置信息
     * @return 是否更新成功
     */
    boolean updateCorpInfo(CorpInfoDTO corpInfo);
    
    /**
     * 检查指定企业应用的token是否有效
     *
     * @param corpId 企业ID
     * @param appId 应用ID
     * @return 是否有效
     */
    boolean isTokenValid(String corpId, Long appId);
    
    /**
     * 设置Redis模板
     * 
     * @param redisTemplate Redis操作模板
     */
    void setRedisTemplate(StringRedisTemplate redisTemplate);
} 