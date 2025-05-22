package cn.iocoder.yudao.module.system.dal.dataobject.company.account;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 微信池账号 DO
 *
 * 
 */
@TableName("taiyi_wx_account_pool")
@KeySequence("taiyi_wx_account_pool_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxAccountPoolDO{

    /**
     * 主键 ID
     */
    @TableId
    private Long id;
    /**
     * 公司 ID
     */
    private Long companyId;
    /**
     * 账号类型，如 wechat/work_wechat
     */
    private String accountType;
    /**
     * 账号唯一 ID，如 openid/userid
     */
    @Deprecated
    private String accountId;
    /**
     * 微信平台统一标识
     */
    private String unionId;
    /**
     * 使用设备
     */
    @Deprecated
    private String device;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 备注
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 是否过期 0 未过期 1 过期
     */
    private Integer isExpired;
    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 账号状态 0 未分配 1 已分配
     */
    private Integer status;
    /**
     * 更新时间
     */
    private Date gmtModified;

    private Long creatorId;

    private String creatorNickName;

    private Long operatorId;

    private String operatorNickName;

    private Integer deleted;
}