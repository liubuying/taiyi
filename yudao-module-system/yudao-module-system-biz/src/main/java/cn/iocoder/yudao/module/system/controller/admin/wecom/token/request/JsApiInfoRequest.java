package cn.iocoder.yudao.module.system.controller.admin.wecom.token.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsApiInfoRequest implements Serializable {

    private static final long serialVersionUID = -1733582216460936274L;

    private String callbackUrl;

}
