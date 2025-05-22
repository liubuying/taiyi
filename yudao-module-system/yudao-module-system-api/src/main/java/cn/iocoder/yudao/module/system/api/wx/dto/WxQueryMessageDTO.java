package cn.iocoder.yudao.module.system.api.wx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class WxQueryMessageDTO {

    /**
     * 来源类型：1|私聊 2|群聊 3|公众号
     */
    private Integer type;


    /**
     * 1:发送中 2:发送成功 3:发送失败 4:已读 5:撤回
     *
     *
     */
    private Integer sendStatus;

    /**
     * 来源类型：1|私聊 2|群聊 3|公众号
     */
    private Integer fromType;



    /**
     * 消息类型(1:文本 2:图片 3:语音.. 4 链接 5名片 . 6引用发消息 7动态表情 8 小程序 9 音乐 10 聊天记录)
     */
    private Integer msgType;

    //1 开始时间
    // 开始时间（建议使用明确的时间格式注解）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    //1 结束时间
    // 开始时间（建议使用明确的时间格式注解）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

}
