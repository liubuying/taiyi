package cn.iocoder.yudao.module.system.controller.admin.domainurl.vo;

import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxDomainUrlVO implements Serializable {

    private Long id;

    private String domainName;

    private String domain;

    private String desc;

    private String domainStatus;

    private String companyId;

    private UserInfo creator;

    private UserInfo operator;
}
