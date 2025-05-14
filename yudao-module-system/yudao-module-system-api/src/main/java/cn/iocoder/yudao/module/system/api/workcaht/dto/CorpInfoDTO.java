package cn.iocoder.yudao.module.system.api.workcaht.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 企业微信应用配置信息
 */
@Data
@Accessors(chain = true)
public class CorpInfoDTO {
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 企业ID
     */
    private String corpId;
    
    /**
     * 应用ID
     */
    private Long appId;
    
    /**
     * 应用密钥
     */
    private String corpSecret;
    
    /**
     * 企业名称
     */
    private String corpName;
    
    /**
     * 应用名称
     */
    private String appName;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 最后刷新token时间
     */
    private LocalDateTime lastRefreshTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * Token失败次数
     */
    private Integer failCount;
} 