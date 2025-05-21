package cn.iocoder.yudao.module.system.dal.dataobject.company;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

import java.time.LocalDateTime;

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
}