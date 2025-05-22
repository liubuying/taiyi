package cn.iocoder.yudao.module.system.controller.admin.wechat;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatQuery;
import cn.iocoder.yudao.module.system.controller.admin.wecom.token.request.JsApiInfoRequest;
import cn.iocoder.yudao.module.system.controller.admin.wecom.token.vo.JsApiInfoVO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.domain.model.wxpool.WxAccountPool;
import cn.iocoder.yudao.module.system.domain.request.WxAccountPoolRequest;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.service.wechat.WeChatService;
import cn.iocoder.yudao.module.system.service.wxpool.WxAccountPoolService;
import cn.iocoder.yudao.module.system.wrapper.qianxun.QXunWrapper;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunQrCode;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.GET;
import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
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

    @Resource
    private QXunWrapper qXunWrapper;

    @Resource
    private WxAccountPoolService wxAccountPoolService;

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
    public CommonResult<String> getQrCode() {
        Long loginUserId = getLoginUserId();
        if(loginUserId == null ){
            return CommonResult.error(UNAUTHORIZED);
        }
        WxAccountPoolRequest poolRequest = new WxAccountPoolRequest();
        poolRequest.setEmployeeId(loginUserId.toString());
        // 查询当前登陆人 分配的账号信息 和域名IP
        List<WxAccountPool> wxAccountPools = wxAccountPoolService.queryWxAccountByEmployeeId(poolRequest);
        // 查询 登录人下管理的微信列表


        // 获取域名
        String domain = "192.168.50.23";
        QianXunResponse<QianXunQrCode> loginQrCode = qXunWrapper.getLoginQrCode(domain);
        return CommonResult.success(loginQrCode.getResult().getQrCode());
    }

    /**
     *
     */
    @PostMapping( "/checkWeChatStatus")
    public CommonResult<JsApiInfoVO> checkWeChatStatus(@RequestBody WechatQuery request) {
        // 查询记录登录的 微信端口记录
        String domain = "192.168.50.23";
        qXunWrapper.getLoginStatus(domain,  "7777");
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
