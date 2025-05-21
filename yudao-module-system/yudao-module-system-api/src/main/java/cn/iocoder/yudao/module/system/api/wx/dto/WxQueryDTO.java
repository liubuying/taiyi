package cn.iocoder.yudao.module.system.api.wx.dto;


import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WxQueryDTO  extends PageParam {


    @NotEmpty(message = "微信id不可以为空")
    private String wxId;


    private String nick;

}
