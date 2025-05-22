package cn.iocoder.yudao.module.system.api.wx.dto;


import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WxQueryDTO  extends PageParam {


    @NotEmpty(message = "微信id不可以为空")
    private String wxId;

    //微信id
    private String nick;

    //企业id
    private String tenantId;

    //1 好友 2 群聊
    private String type;


}
