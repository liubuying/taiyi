package cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 千寻微信框架群成员信息模型
 */
@Data
@Accessors(chain = true)
public class QianXunInfoGroupMember {

    private String wxid;        // 微信ID
    private String groupNick;   // 群昵称

}