package cn.iocoder.yudao.module.system.controller.admin.wechat.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WechatQuery implements Serializable {

    /**
     * 账号unionID
     */
    private String wxUnionId;

    private String wxOpenId;

    private String wxLoginStaus;

    private Long employeeId;

    private String domain;

    private String port;

    private String pid;
}
