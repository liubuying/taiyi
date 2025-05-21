package cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QianXunInfoGroup extends QianXunInfo {

    private String remark;
    private String nickBrief;
    private String nickWhole;
    private String remarkBrief;
    private String remarkWhole;
    private String enBrief;
    private String enWhole;
    private String v3;
    private String momentsBackgroudImgUrl;
    private String avatarMinUrl;
    private String avatarMaxUrl;
    private String sex;
    private int groupMemberNum;
    private String groupManger;
}
