package cn.iocoder.yudao.module.system.controller.admin.callback.vo.event;

import cn.iocoder.yudao.module.system.controller.admin.callback.vo.BaseEventVo;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class QianXunAccountChangeEventVo extends BaseEventVo {
    /**
     * 事件类型
     * 1: 上线
     * 0: 下线
     */
    private String type;

    /**
     * 微信号
     */
    private String wxNum;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 设备信息
     */
    private String device;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 个性签名
     */
    private String sign;
}
