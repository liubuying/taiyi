package cn.iocoder.yudao.module.system.api.workcaht.dto.TokenRefresh;

import lombok.Data;

/**
 * 企业微信JSAPI Ticket响应对象
 */
@Data
public class JsapiTokenResponseDTO {
    /**
     * 错误码，0表示成功
     */
    private Integer errcode;
    
    /**
     * 错误信息
     */
    private String errmsg;
    
    /**
     * jsapi_ticket
     */
    private String ticket;
    
    /**
     * ticket有效期（秒）
     */
    private Integer expires_in;
} 