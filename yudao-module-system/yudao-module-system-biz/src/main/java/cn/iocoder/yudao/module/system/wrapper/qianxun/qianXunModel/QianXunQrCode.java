package cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QianXunQrCode {
    private String qrCode;
    private Integer port;
    private Integer pid;
    private String flag;
}
