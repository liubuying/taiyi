package cn.iocoder.yudao.module.system.util.qianxun.QianXunModel;

import java.util.Map;

public class QianXunBaseResponse {
    private int code;
    private String msg;
    private long timestamp;

    // 以下字段并非所有响应都会出现
    private String wxid;
    private Integer port;
    private Integer pid;
    private String flag;

    private Map result;

    // getters & setters
    // …
}
