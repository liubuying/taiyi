package cn.iocoder.yudao.module.system.dal.dataobject.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName(value = "system_users", autoResultMap = true) // 由于 SQL Server 的 system_user 是关键字，所以使用 system_users
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxFriendDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String wxId;
    private String wxNum;
    private String nick;
    private String remark;
    private String nickBrief;
    private String nickWhole;
    private String remarkBrief;
    private String remarkWhole;
    private String enBrief;
    private String enWhole;
    private String v3;
    private String sign;
    private String country;
    private String province;
    private String city;
    private String momentsBackgroudImgUrl;
    private String avatarMinUrl;
    private String avatarMaxUrl;
    private String sex;
    private String label;
}
