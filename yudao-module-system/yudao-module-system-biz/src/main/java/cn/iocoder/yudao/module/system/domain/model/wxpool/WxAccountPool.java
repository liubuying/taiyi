package cn.iocoder.yudao.module.system.domain.model.wxpool;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.system.domain.enums.AccountTypeEnum;
import cn.iocoder.yudao.module.system.domain.enums.YesOrNoEnum;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class WxAccountPool{

    private Long id;
    /**
     * 公司 ID
     */
    private String companyId;
    /**
     * 账号类型，如 wechat/work_wechat
     */
    private AccountTypeEnum accountType;
    /**
     * 账号唯一 ID，如 openid/userid
     */
    private String accountId;
    /**
     * 微信平台统一标识
     */
    private String unionId;
    /**
     * 使用设备
     */
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
     * 创建人
     */
    private UserInfo creator;

    /**
     * 操作人
     */
    private UserInfo operator;
    /**
     * 是否过期 0 未过期 1 过期
     */
    private YesOrNoEnum isExpired;

    private Integer deleted;

    private Integer status;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 域名绑定IP
     */
    private DomainName domainName;

    /**
     * 绑定员工
     */
    private UserInfo employeeUser;


}
