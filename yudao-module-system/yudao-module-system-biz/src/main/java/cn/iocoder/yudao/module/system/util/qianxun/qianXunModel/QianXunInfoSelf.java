package cn.iocoder.yudao.module.system.util.qianxun.qianXunModel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QianXunInfoSelf extends QianXunInfo {
    private String device;
    private String phone;
    private String email;
    private String qq;
}
