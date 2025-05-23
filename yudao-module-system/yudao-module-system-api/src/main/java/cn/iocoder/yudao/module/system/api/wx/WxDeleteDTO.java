package cn.iocoder.yudao.module.system.api.wx;

import lombok.Data;

@Data
public class WxDeleteDTO {


    //当前微信
    private String wxId;

    //要删除的微信id
    private String toWxId;

}
