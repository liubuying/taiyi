package cn.iocoder.yudao.module.system.domain.request;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 域名请求查询
 */
@Data
@Schema(description = "管理后台 - 域名链接上传")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DomainNameRequest extends PageParam  implements Serializable {


    private static final long serialVersionUID = -1670442185569231857L;
    private String domainName;

    private String domain;

    private String domainType;

    private Integer domainStatus;

    private String companyId;

    private Integer deleted;


}
