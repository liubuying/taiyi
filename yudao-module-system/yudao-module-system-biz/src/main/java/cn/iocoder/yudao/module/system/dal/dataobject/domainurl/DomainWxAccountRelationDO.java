package cn.iocoder.yudao.module.system.dal.dataobject.domainurl;

import cn.iocoder.yudao.module.system.dal.dataobject.BaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;


/**
 * 域名与微信账号关系 DO
 *
 *
 */
@TableName("taiyi_domain_wx_account_relation")
@KeySequence("taiyi_domain_wx_account_relation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainWxAccountRelationDO extends BaseDO {

    /**
     * 主键 ID
     */
    @TableId
    private Long id;
    /**
     * 账号unionId
     */
    private String unionId;
    /**
     * 域名管理 ID
     */
    private Long domainId;
    /**
     * 状态：0=禁用，1=启用
     */
    private Integer status;
    /**
     * 修改人
     */
    private Long operatorId;

}