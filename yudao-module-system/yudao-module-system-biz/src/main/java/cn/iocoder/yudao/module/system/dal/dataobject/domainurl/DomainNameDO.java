package cn.iocoder.yudao.module.system.dal.dataobject.domainurl;

import cn.iocoder.yudao.module.system.dal.dataobject.BaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 域名管理 DO
 *
 * 
 */
@TableName("taiyi_domain_name")
@KeySequence("taiyi_domain_name_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainNameDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 绑定域名
     */
    private String domainName;

    /**
     * 绑定域名
     */
    private String domain;

    private String domainDesc;
    /**
     * 状态：1=启用，0=禁用
     */
    private Integer status;
    /**
     * 所属公司ID
     */
    private Long companyId;

}