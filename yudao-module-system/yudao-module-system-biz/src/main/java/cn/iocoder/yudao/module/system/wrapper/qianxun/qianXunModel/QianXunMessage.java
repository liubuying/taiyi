package cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QianXunMessage {
    /**
     * 消息发送ID
     */
    private String sendId;
}
