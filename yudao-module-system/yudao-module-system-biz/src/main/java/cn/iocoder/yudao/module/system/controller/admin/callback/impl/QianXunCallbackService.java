package cn.iocoder.yudao.module.system.controller.admin.callback.impl;

import java.util.Map;

/**
 * 千寻微信框架回调服务
 * 处理千寻微信框架Pro发送的各类回调事件
 */
public interface QianXunCallbackService {
    /**
     * 处理通用回调
     *
     * @param requestBody 请求体JSON内容
     */
    void handleQianXunCallback(String requestBody);

    /**
     * 处理账号变动事件(10014)
     *
     * @param callbackData 回调数据
     */
    void handleAccountChange(Map<String, Object> callbackData);
}
