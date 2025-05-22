package cn.iocoder.yudao.module.system.controller.admin.callback.vo;

import lombok.Data;

@Data
public class BaseEventVo {
    // 事件类型
    private String type;
    // 事件描述
    private String des;
    // 事件时间戳
    private String timestamp;
    // 微信ID
    private String wxid;
    // 端口号
    private Integer port;
    // 进程ID
    private Integer pid;
    // 标识
    private String flag;
}

