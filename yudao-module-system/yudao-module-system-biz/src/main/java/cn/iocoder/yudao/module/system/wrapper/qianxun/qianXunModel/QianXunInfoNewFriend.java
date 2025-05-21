package cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QianXunInfoNewFriend extends QianXunInfo {
    private String ret;
    private String phone;
    private String v3;
    private String v4;
    private String avatarMinUrl;
    private String avatarMaxUrl;
    private String sex;
}
