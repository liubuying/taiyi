package cn.iocoder.yudao.module.system.dal.dataobject.wxgroup;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 *  微信群组用户关联表
 * @author lx
 */
@TableName("taiyi_wx_group_person_relation")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxGroupPersonRelationDO {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户wxid
     */
    private String wxPersonId;
    /**
     * 群聊wxid
     */
    private String wxGroupId;
}
