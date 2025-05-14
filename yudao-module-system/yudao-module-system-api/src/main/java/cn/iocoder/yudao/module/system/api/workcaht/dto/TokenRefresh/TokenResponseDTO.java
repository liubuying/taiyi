package cn.iocoder.yudao.module.system.api.workcaht.dto.TokenRefresh;

import lombok.Data;

/**
 * 企业微信API返回的Token响应对象
 */
@Data
public class TokenResponseDTO {
    /**
     * 错误码，0表示成功
     */
    private Integer errcode;
    
    /**
     * 错误信息
     */
    private String errmsg;
    
    /**
     * 访问token
     */
    private String access_token;
    
    /**
     * token有效期（秒）
     */
    private Integer expires_in;
} 