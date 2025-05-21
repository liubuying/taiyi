package cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QianXunInfo {

    private String wxid;        // 微信ID
    private String wxNum;       // 微信号
    private String nick;        // 昵称
    private String avatarUrl;   // 头像
    private String country;     // 国籍
    private String province;    // 省份
    private String city;        // 城市
    private String sign;        // 个性签名

}