package cn.iocoder.yudao.module.system.dal.dataobject.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

@TableName(value = "taiyi_wx_msg_info", autoResultMap = true) // 由于 SQL Server 的 system_user 是关键字，所以使用 system_users
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxSendDO {

    /**
     * 消息id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 微信消息唯一ID
     */
    private String wxMsgId;

    /**
     * 本地消息id
     */
    private String localMsgId;

    /**
     * 对方微信id
     */
    private String sendUser;

    /**
     * 是否群聊
     */
    private Integer isGroup;

    /**
     * 会话标识
     */
    private Integer conversationId;


    /**
     * 来源类型：1|私聊 2|群聊 3|公众号
     */
    private Integer fromType;

    /**
     * 消息类型(1:文本 2:图片 3:语音.. 4 链接 5名片 . 6引用发消息 7动态表情 8 小程序 9 音乐 10 聊天记录)
     */
    private Integer msgType;


    /**
     * 消息内容
     */
    private String msgContext;

    /**
     * fromType=1时为好友wxid，fromType=2时为群wxid，fromType=3时公众号wxid
     */
    private String fromUser;

    /**
     * 是否引用
     */
    private Integer isUser;



    /**
     * 1:发送中 2:发送成功 3:发送失败 4:已读 5:撤回
     */
    private Integer sendStatus;

    /**
     * 文件路劲
     */
    private String filePath;

    /**
     * 仅fromType=2时有效，为群内发言人wxid
     */
    private Integer finalFromWxId;

    /**
     * 消息签名
     */
    private String signature;

    /**
     * 仅fromType=2时有效，为消息中艾特人wxid列表
     */
    private String axIdlist;

    /**
     * 创建人
     */
    private Integer creatorId;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 修改人
     */
    private Integer operateId;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 0|别人发送 1|自己发送
     */
    private String msgSource;
}
