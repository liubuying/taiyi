package cn.iocoder.yudao.module.system.controller.admin.wechat;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.wecom.token.request.JsApiInfoRequest;
import cn.iocoder.yudao.module.system.controller.admin.wecom.token.vo.JsApiInfoVO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.service.wechat.WeChatService;
import cn.iocoder.yudao.module.system.util.qianxun.QianXunUtils;
import cn.iocoder.yudao.module.system.util.qianxun.QianXunUtilsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.GET;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;


@Tag(name = "管理后台 - 微信账号管理")
@Slf4j
@Controller
@RequestMapping("/system/wechat")
public class WeChatQueryController {

    @Resource
    private AdminUserService userService;

    @Resource
    private WeChatService weChatService;

    private CommonResult<AdminUserDO> getUser(){
        Long loginUserId = getLoginUserId();
        AdminUserDO user = userService.getUser(loginUserId);
        if(user == null){
            return CommonResult.error(12223,"用户不存在");
        }
        return CommonResult.success(user);
    }

    /**
     * 获取登录微信二维码
     */
    @PostMapping("/getQrCode")
    @Operation(summary = "获取微信二维码")
    @ApiAccessLog(operateType = GET)
    public CommonResult<String> getQrCode(@RequestBody Object request) {
        CommonResult<AdminUserDO> userResult = getUser();
        if(userResult.isSuccess()){
            return CommonResult.error(userResult);
        }
        AdminUserDO userDO = userResult.getCheckedData();
        // 查询 登录人下管理的微信列表
        List<Object> objectList = weChatService.queryManageWechatList(getLoginUserId());

        // 获取域名
        Object o = objectList.get(0);
        String domain = o.toString();
        Map<String, Object> loginQrCode = QianXunApi.getLoginQrCode(domain);
        return CommonResult.success(loginQrCode.get("result").toString());

    }

    /**
     * 获取微信列表
     */
    @PostMapping( "/queryWeChatList")
    public CommonResult<JsApiInfoVO> queryWeChatList(@RequestBody JsApiInfoRequest request) {
        return null;
    }

    /**
     * 获取微信好友数据
     *//*
    @RequestMapping(name = "/queryWechatFriendList",  method = RequestMethod.POST)
    public CommonResult<JsApiInfoVO> queryWechatFriendList(@RequestBody JsApiInfoRequest request) {
        return null;
    }

    *//**
     * 获取是否登录
     *//*
    @RequestMapping(name = "/checkWxLogin",  method = RequestMethod.POST)
    public CommonResult<Boolean> checkWxLogin(@RequestBody JsApiInfoRequest request) {
        return null;
    }

    *//**
     * 获取微信个人信息
     *//*
    @RequestMapping(name = "/getLoginUserInfo",  method = RequestMethod.POST)
    public CommonResult<JsApiInfoVO> getLoginUserInfo(@RequestBody JsApiInfoRequest request) {
        return null;
    }*/


    /**
     * 获取微信群列表
     */

}
