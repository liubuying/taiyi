package cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QianXunInfoSelf extends QianXunInfo {
    private String device;
    private String phone;
    private String email;
    private String qq;
     // 事件回调使用 类型
    private String type;
}
