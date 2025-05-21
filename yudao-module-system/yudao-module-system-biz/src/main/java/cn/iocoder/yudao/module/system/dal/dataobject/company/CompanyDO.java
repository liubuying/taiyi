package cn.iocoder.yudao.module.system.dal.dataobject.company;

import java.time.LocalDate;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

/**
 * 公司 DO
 *
 * @author 芋道源码
 */
@TableName("taiyi_legal_person")
@KeySequence("taiyi_legal_person_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDO extends BaseDO {
    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 统一社会信用代码
     */
    private String unifiedSocialCreditCode;

    /**
     * 联络人
     */
    private String liaison;

    /**
     * 电话
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 行业
     */
    private String industry;

    /**
     * 注册地址
     */
    private String registeredAddress;

    /**
     * 办公地址
     */
    private String officeAddress;

    /**
     * 入驻日期
     */
    private LocalDate entryDate;

    /**
     * 退出日期
     */
    private LocalDate exitDate;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 营业执照URL
     */
    private String businessLicenseUrl;
}
