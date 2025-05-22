package cn.iocoder.yudao.module.system.api.wx.vo;


import lombok.Data;

@Data
public class WxFriendVO {

    private String wxId; //微信id

    private Integer type; //类型 1 好友 2群聊

    private String groupNumber; //类型 1 好友 2群聊

    private Integer groupNumberCount;  //群成员数量

    private String groupManger;  //群主微信id

    private String wxNo; //微信

    private String nick;  //昵称

    private String country;//国家

    private String province;//省份

    private String city;//城市

    private String avatarMinUrl;//头像小图

    private String avatarMaxUrl;//头像大图

    private Integer sex;//性别0=未知，1=男，2=女
}
