package cn.iocoder.yudao.module.system.controller.admin.callback.request;

import lombok.Data;


@Data
public class CallBackReq<T> {
    private String event;
    private T data;
    private String wxid;
}
