package cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QianXunQrCode {
    private String qrCode;
    private String port;
    private String pid;
    private String flag;
}
