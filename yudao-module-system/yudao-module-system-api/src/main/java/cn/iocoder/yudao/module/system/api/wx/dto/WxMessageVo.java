package cn.iocoder.yudao.module.system.api.wx.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class WxMessageVo {

    /**
     * 微信消息唯一ID
     */
    private String id;

    /**
     * 微信消息唯一ID
     */
    private String wxMsgId;


    /**
     * 1:发送中 2:发送成功 3:发送失败 4:已读 5:撤回
     */
    private Integer sendStatus;



    private String msgType;


    //1 开始时间
    // 开始时间（建议使用明确的时间格式注解）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime ;


    /**
     * 消息内容
     */
    private String msgContext;

    /**
     * fromType=1时为好友wxid，fromType=2时为群wxid，fromType=3时公众号wxid
     */
    private String fromUser;

}
