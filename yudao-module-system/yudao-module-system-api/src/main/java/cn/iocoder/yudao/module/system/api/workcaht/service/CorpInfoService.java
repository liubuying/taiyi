package cn.iocoder.yudao.module.system.api.workcaht.service;

import cn.iocoder.yudao.module.system.api.workwe.dto.CorpInfoDTO;

import java.util.List;

/**
 * 企业微信应用管理服务接口
 */
public interface CorpInfoService {
    
    /**
     * 获取所有启用的企业应用信息
     *
     * @return 企业应用列表
     */
    List<CorpInfoDTO> listAllEnabledCorps();
    
    /**
     * 根据企业ID和应用ID获取企业应用信息
     *
     * @param corpId 企业ID
     * @param appId 应用ID
     * @return 企业应用信息
     */
    CorpInfoDTO getCorpInfo(String corpId, Long appId);
    
    /**
     * 添加新的企业应用信息
     *
     * @param corpInfo 企业应用信息
     * @return 添加后的企业应用信息（包含ID）
     */
    CorpInfoDTO addCorpInfo(CorpInfoDTO corpInfo);
    
    /**
     * 更新企业应用信息
     *
     * @param corpInfo 企业应用信息
     * @return 是否更新成功
     */
    boolean updateCorpInfo(CorpInfoDTO corpInfo);
    
    /**
     * 更新企业应用的Token刷新状态
     *
     * @param corpId 企业ID
     * @param appId 应用ID
     * @param success 是否刷新成功
     */
    void updateTokenRefreshStatus(String corpId, Long appId, boolean success);
    
    /**
     * 获取所有需要重试的企业应用信息
     * 如果失败次数超过阈值，不再尝试刷新
     *
     * @param maxFailCount 最大失败次数
     * @return 需要重试的企业应用列表
     */
    List<CorpInfoDTO> listCorpsNeedRetry(int maxFailCount);
} 