package cn.iocoder.yudao.module.system.domain.model.domainurl;

import cn.iocoder.yudao.module.system.domain.enums.YesOrNoEnum;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * 域名管理
 */
@Data
public class DomainName implements Serializable {


    private static final long serialVersionUID = -6476793507637746865L;

    private Long id;

    private String domainName;

    private String domain;

    private String domainDesc;

    private String domainStatus;

    private Integer deleted;

    private UserInfo creator;

    private UserInfo operator;
}
