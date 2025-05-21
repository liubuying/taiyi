package cn.iocoder.yudao.module.system.api.wx;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.service.wx.WxFriendService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class WxFriendApiImpl implements WxFriendApi {




    @Resource
    private WxFriendService wxFriendService;


    @Override
    public CommonResult<PageResult<WxFriendVO>> queryFriendDataList(WxQueryDTO dto) {
        return wxFriendService.queryFriendDataList(dto);
    }

}
