package cn.iocoder.yudao.module.system.dal.dataobject.company;

import lombok.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 入驻公司信息与用户绑定 DO
 *
 * 
 */
@TableName("taiyi_company_user_relation")
@KeySequence("taiyi_company_user_relation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUserRelationDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 公司ID
     */
    private Long companyId;
    /**
     * 员工ID
     */
    private Long userId;
    /**
     * 员工名称
     */
    private String userName;
}