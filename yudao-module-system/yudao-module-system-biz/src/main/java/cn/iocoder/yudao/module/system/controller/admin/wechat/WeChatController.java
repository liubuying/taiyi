package cn.iocoder.yudao.module.system.controller.admin.wechat;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.wecom.token.request.JsApiInfoRequest;
import cn.iocoder.yudao.module.system.controller.admin.wecom.token.vo.JsApiInfoVO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.service.wechat.WeChatService;
import cn.iocoder.yudao.module.system.util.qianxun.QianXunApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;


@Tag(name = "管理后台 - 微信账号管理")
@Validated
@Slf4j
@RestController
@RequestMapping("/system/wechat")
public class WeChatController {

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
    @RequestMapping(name = "/getQrCode",  method = RequestMethod.POST)
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<String> getQrCode(@RequestBody JsApiInfoRequest request) {
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
    @RequestMapping(name = "/queryWeChatList",  method = RequestMethod.POST)
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<JsApiInfoVO> queryWeChatList(@RequestBody JsApiInfoRequest request) {

    }

    /**
     * 获取微信好友数据
     */
    @RequestMapping(name = "/queryWechatFriendList",  method = RequestMethod.POST)
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<JsApiInfoVO> queryWechatFriendList(@RequestBody JsApiInfoRequest request) {

    }

    /**
     * 获取是否登录
     */
    @RequestMapping(name = "/checkWxLogin",  method = RequestMethod.POST)
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> checkWxLogin(@RequestBody JsApiInfoRequest request) {

    }

    /**
     * 获取微信个人信息
     */
    @RequestMapping(name = "/getLoginUserInfo",  method = RequestMethod.POST)
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<JsApiInfoVO> getLoginUserInfo(@RequestBody JsApiInfoRequest request) {

    }


    /**
     * 获取微信群列表
     */
    @RequestMapping(name = "/queryWxGroupList",  method = RequestMethod.POST)
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<JsApiInfoVO> queryWxGroupList(@RequestBody JsApiInfoRequest request) {

    }
}
