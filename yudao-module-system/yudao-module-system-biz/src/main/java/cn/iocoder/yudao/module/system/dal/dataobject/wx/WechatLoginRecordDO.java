package cn.iocoder.yudao.module.system.dal.dataobject.wx;

import cn.iocoder.yudao.module.system.dal.dataobject.BaseDO;
import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 微信用户登录记录 DO
 *
 * @author 芋道源码
 */
@TableName("taiyi_wechat_login_record")
@KeySequence("taiyi_wechat_login_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatLoginRecordDO extends BaseDO {

    /**
     * 自增主键ID
     */
    @TableId
    private Long id;
    /**
     * 员工ID
     */
    private Long employeeUserId;
    /**
     * 微信UnionId
     */
    private String wxUnionId;
    /**
     * 微信No
     */
    private String wxNo;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 登录二维码
     */
    private String loginQrCode;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 进程ID
     */
    private Integer pid;
    /**
     * IP
     */
    private String ip;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 是否下线（0-在线，1-下线）
     */
    private Integer isOffline;

}