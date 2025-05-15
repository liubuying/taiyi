package cn.iocoder.yudao.module.system.controller.admin.wecom.token.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Builder
public class JsApiInfoVO implements Serializable {

    private static final long serialVersionUID = -5606292066613933301L;
    private String callbackUrl;
    private String nonceStr;
    private String timestamp;
    private String signature;
}
