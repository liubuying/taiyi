package cn.iocoder.yudao.module.system.controller.admin.callback.request.event;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseEvent extends AbstractEvent {
    // 端口号
    private Integer port;
    // 进程ID
    private Integer pid;
    // 标识
    private String flag;
}

