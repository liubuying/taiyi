package cn.iocoder.yudao.module.system.controller.admin.wechat;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.service.wx.WxFriendService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "wx - 用户")
@RestController
@RequestMapping("/system/api/wx")
@Validated
public class WxFrinendController {

    @Resource
    private WxFriendService wxFriendService;




    @PermitAll
    @PostMapping("/pageFriendList")
    public CommonResult<PageResult<WxFriendVO>> getUserPage(@RequestBody WxQueryDTO pageReqVO) {
        // 获得用户分页列表
        return wxFriendService.queryFriendDataList(pageReqVO);
    }



}
