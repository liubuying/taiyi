package cn.iocoder.yudao.module.system.controller.admin.domainurl.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxDomainUrlVO implements Serializable {

    private Long id;

    private String domainName;

    private String domainStatus;

    private String companyId;

    private Long creatorId;

    private Long operatorId;
}
