package cn.iocoder.yudao.module.system.dal.dataobject.company.account;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 员工与微信账号关系 DO
 *
 * 
 */
@TableName("taiyi_users_wx_account_relation")
@KeySequence("taiyi_users_wx_account_relation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersWxAccountRelationDO{

    /**
     * 主键 ID
     */
    @TableId
    private Long id;
    /**
     * 员工 ID
     */
    private Long employeeId;
    /**
     * 账号union ID
     */
    private String unionId;
    /**
     * 状态：0=禁用，1=启用
     */
    private Integer status;
    /**
     * 修改人
     */
    private Long operatorId;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;

}