package cn.iocoder.yudao.module.system.api.wx.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WxSendDTO {


    @NotEmpty(message = "微信id不可以为空")
    private String wxId;



    @NotEmpty(message = "消息内容不可以为空")
    private String context;


}
