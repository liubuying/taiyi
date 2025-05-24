package cn.iocoder.yudao.module.system.controller.admin.callback.request.event;

import lombok.Data;

@Data
public abstract class AbstractEvent {
    private String type;

    private String des;

    private String timestamp;

    private String wxid;

    private Long userId;
}
