package cn.iocoder.yudao.module.system.controller.admin.callback;


import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.callback.impl.QianXunCallbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;


/**
 * 千寻微信框架Pro 回调控制器
 * 接收千寻微信框架发送的各类回调事件，通过event字段区分不同事件类型：
 * - 10000: 注入成功
 * - 10014: 账号变动事件
 * - 10008: 群聊消息
 * - 10009: 私聊消息
 * - 10010: 自己发出消息
 * - 10006: 转账事件
 * - 10013: 撤回事件
 * - 10011: 好友请求
 * - 10007: 支付事件
 * - 99999: 授权到期
 * - 10015: 二维码收款事件
 * - 10016: 群成员变动事件
 * - 10: 心跳事件
 */
@Tag(name = "管理后台 - 千寻微信框架回调")
@RestController
@RequestMapping("/system/qianxun/callback")
@Slf4j
public class CallBackCpntroller {

    @Resource
    private QianXunCallbackService qianXunCallbackService;

    /**
     * 通用回调接口，处理千寻微信框架的所有回调事件
     *
     * @param callbackData 回调数据
     * @return 处理结果
     */
    @PostMapping("/receive")
    @PermitAll
    @Operation(summary = "接收千寻微信框架的回调事件")
    public CommonResult<Boolean> receiveCallback(@RequestBody String callbackData) {
        // 记录调试日志
        if (log.isDebugEnabled()) {
            log.debug("[receiveCallback][收到回调请求] 数据: {}", callbackData);
        }

        // 处理回调事件
        qianXunCallbackService.handleQianXunCallback(callbackData);

        // 返回成功
        return success(true);

    }
}
