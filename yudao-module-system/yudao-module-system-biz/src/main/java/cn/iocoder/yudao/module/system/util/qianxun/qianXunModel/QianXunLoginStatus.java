package cn.iocoder.yudao.module.system.util.qianxun.qianXunModel;

import lombok.Data;

/**
 * 千寻微信框架登录状态信息
 */
@Data
public class QianXunLoginStatus {
    /**
     * 登录状态
     * 0=尚未登陆微信！
     * 1=请在手机上点击登录！
     * 2=正在登录微信中...
     * 3=微信登陆完成！
     * 4=点击登录界面
     * 5=未知
     */
    private Integer status;

    /**
     * 登录状态说明
     */
    private String statusMsg;

    /**
     * 微信ID
     */
    private String wxid;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 进程ID
     */
    private Integer pid;

    /**
     * 标志
     */
    private String flag;

    /**
     * 开始运行时间戳
     */
    private String startTimeStamp;

    /**
     * 开始运行时间
     */
    private String startTime;

    /**
     * 已运行时间
     */
    private String runTime;

    /**
     * 接收消息数
     */
    private Integer recv;

    /**
     * 发送消息数
     */
    private Integer send;

    /**
     * 微信号
     */
    private String wxNum;

    /**
     * 昵称
     */
    private String nick;
}
