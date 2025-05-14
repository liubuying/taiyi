package cn.iocoder.yudao.module.system.api.workcaht.dto.TokenRefresh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token结果对象，包含访问token和jsapi_ticket
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResult {
    /**
     * 访问token
     */
    private String accessToken;
    
    /**
     * jsapi_ticket
     */
    private String jsapiTicket;
} 