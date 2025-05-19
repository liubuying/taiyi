package cn.iocoder.yudao.module.system.dal.dataobject.company;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 法人信息 DO
 *
 *
 */
@TableName("taiyi_legal_person")
@KeySequence("taiyi_legal_person_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegalPersonDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 法人姓名
     */
    private String name;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 证件类型
     */
    private String certType;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 关联公司ID
     */
    private Long companyId;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

}