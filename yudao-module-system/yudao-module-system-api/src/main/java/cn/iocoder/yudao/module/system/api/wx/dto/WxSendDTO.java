package cn.iocoder.yudao.module.system.api.wx.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WxSendDTO {




    @NotEmpty(message = "当前微信号")
    private String fromUser;


    @NotEmpty(message = "对方微信号")
    private String toUser;


    @NotEmpty(message = "消息内容不可以为空")
    private String msgContext;


}
