package cn.iocoder.yudao.module.system.api.wx.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class WxMessageVo {
    private String wxId;

    private String msgId;


    private String msgType;



    private Date sendTime ;


    private String context ;

}
