package cn.iocoder.yudao.module.system.api.workcaht.dto.TokenRefresh;

import lombok.Data;

/**
 * 企业微信应用信息
 */
@Data
public class AppInfo {
    /**
     * 企业ID
     */
    private String corpId;
    
    /**
     * 应用ID
     */
    private String agentId;
    
    /**
     * 应用密钥
     */
    private String corpSecret;
} 