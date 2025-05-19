package cn.iocoder.yudao.module.system.controller.admin.wxpool.vo;

import cn.iocoder.yudao.module.system.domain.enums.YesOrNoEnum;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class WxAccountPoolVO implements Serializable {

    private static final long serialVersionUID = -1778510608041777328L;

    private Long id;

    private String companyId;

    private String companyName;

    private String unionId;

    private String accountType;

    private String avatar;

    private String nickName;

    private String phone;

    private String email;

    private UserInfo creator;

    private UserInfo operator;

    private YesOrNoEnum isExpired;

    private String accountId;

    private String expireTime;
    private int deleted;

}
