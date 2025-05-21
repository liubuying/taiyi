package cn.iocoder.yudao.module.system.domain.model.wx;


import lombok.Data;

@Data
public class WxFriend {

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
