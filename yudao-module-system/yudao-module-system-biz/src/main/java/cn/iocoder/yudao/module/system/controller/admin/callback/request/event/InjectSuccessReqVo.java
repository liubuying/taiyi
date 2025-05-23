package cn.iocoder.yudao.module.system.controller.admin.callback.request.event;

import lombok.Data;

@Data
public class InjectSuccessReqVo {
    /**
     * 监听端口
     */
    private String port;

    /**
     * 进程PID
     */
    private String pid;
}
