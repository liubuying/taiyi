package cn.iocoder.yudao.module.system.controller.admin.wechat;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.wx.WxDeleteDTO;
import cn.iocoder.yudao.module.system.api.wx.dto.WxMessageVo;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryMessageDTO;
import cn.iocoder.yudao.module.system.api.wx.dto.WxSendDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.service.wx.WxFriendService;
import cn.iocoder.yudao.module.system.service.wx.WxSendService;
import cn.iocoder.yudao.module.system.wrapper.qianxun.QXunWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

import java.util.List;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "wx - 用户")
@RestController
@RequestMapping("/system/api/wx")
@Validated
public class WxFrinendController {

    @Resource
    private WxFriendService wxFriendService;



    @Resource
    private WxSendService wxSendService;





    /**
     * 好友 群聊列表查询
     * author zhangbin
     */
    @PermitAll
    @PostMapping("/pageFriendList")
    public CommonResult<PageResult<WxFriendVO>> getUserPage(@RequestBody WxQueryDTO pageReqVO) {
        // 获得用户分页列表
        return wxFriendService.queryFriendDataList(pageReqVO);
    }


    /**
     * 好友 发送文本消息
     * author zhangbin
     */
    @PermitAll
    @PostMapping("/sendMessageText")
    public CommonResult<?> sendMessageText(@RequestBody WxSendDTO dto) {
        return wxSendService.sendMessageText(dto);
    }


    /**
     * 好友 消息记录查询
     * author zhangbin
     */
    @PermitAll
    @PostMapping("/queryMsgList")
    public CommonResult<List<WxMessageVo>> queryMsgList(@RequestBody WxQueryMessageDTO dto) {
        return wxSendService.queryMsgList(dto);
    }



    /**
     * 好友 删除好友
     * author zhangbin
     */
    @PermitAll
    @PostMapping("/deleteFriend")
    public CommonResult<?> deleteFriend(@RequestBody WxDeleteDTO dto) {
        return wxFriendService.deleteFriend(dto);
    }





}
