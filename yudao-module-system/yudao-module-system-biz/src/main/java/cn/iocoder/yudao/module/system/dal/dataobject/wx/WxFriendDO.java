package cn.iocoder.yudao.module.system.dal.dataobject.wx;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.util.Date;

@TableName(value = "system_users", autoResultMap = true) // 由于 SQL Server 的 system_user 是关键字，所以使用 system_users
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxFriendDO {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 好友微信id/群聊微信id
     */
    private String wxId;
    /**
     * 所属人微信id
     */
    private String wxPersonId;
    /**
     * 好友/群聊微信号
     */
    private String wxNo;
    /**
     * 好友昵称/群聊名称
     */
    private String nick;
    /**
     * 备注
     */
    private String remark;
    /**
     * 呢称简称
     */
    private String nickBrief;
    /**
     * 呢称全称
     */
    private String nickWhole;
    /**
     * 备注简称
     */
    private String remarkBrief;
    /**
     * 备注全称
     */
    private String remarkWhole;
    /**
     * 英文简介
     */
    private String enBrief;
    /**
     * 英文全称
     */
    private String enWhole;
    /**
     * 数据v3
     */
    private String v3;
    /**
     * 个性签名
     */
    private String sign;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 朋友圈背景图
     */
    private String momentsBackgroudImgUrl;
    /**
     * 小头像图
     */
    private String avatarMinUrl;
    /**
     * 大头像图
     */
    private String avatarMaxUrl;
    /**
     * 性别 0 1 2
     */
    private Integer sex;
    /**
     * 标签id
     */
    private String label;
    /**
     * 1好友 2 群聊 3其他
     */
    private Integer type;
    /**
     * 群成员数量
     */
    private Integer groupNumberCount;
    /**
     * 群主微信id
     */
    private String groupManger;
    /**
     * creatorId
     */
    private Long creatorId;
    /**
     * 租户编号
     */
    private Long tenantId;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
}
