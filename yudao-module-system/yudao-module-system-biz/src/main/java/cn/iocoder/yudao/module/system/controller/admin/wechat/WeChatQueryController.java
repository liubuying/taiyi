package cn.iocoder.yudao.module.system.controller.admin.wechat;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.company.CompanyPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatQuery;
import cn.iocoder.yudao.module.system.controller.admin.wecom.token.vo.JsApiInfoVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WechatLoginRecordDO;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import cn.iocoder.yudao.module.system.domain.request.DomainNameRequest;
import cn.iocoder.yudao.module.system.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.system.service.company.CompanyService;
import cn.iocoder.yudao.module.system.service.domainurll.WxDomainUrlService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.service.wechat.WeChatService;
import cn.iocoder.yudao.module.system.service.wx.WechatLoginRecordService;
import cn.iocoder.yudao.module.system.service.wxpool.WxAccountPoolService;
import cn.iocoder.yudao.module.system.util.cache.RedisUtils;
import cn.iocoder.yudao.module.system.wrapper.qianxun.QXunWrapper;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunLoginStatus;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunQrCode;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Throwables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.GET;
import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.system.util.babanceip.LoadBalancer.selectBalancedIp;


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

    @Resource
    private WxDomainUrlService wxDomainUrlService;

    @Resource
    private WechatLoginRecordService wechatLoginRecordService;

    @Resource
    private CompanyService companyService;

    private CommonResult<AdminUserDO> getUser() {
        Long loginUserId = getLoginUserId();
        AdminUserDO user = userService.getUser(loginUserId);
        if (user == null) {
            return CommonResult.error(12223, "用户不存在");
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
        try {
            Long loginUserId = getLoginUserId();
            if (loginUserId == null) {
                return CommonResult.error(UNAUTHORIZED);
            }

            AdminUserDO user = userService.getUser(loginUserId);
            if (user == null) {
                return CommonResult.error(12223, "用户不存在");
            }
            Long tenantId = user.getTenantId();

            CompanyPageReqVO reqVO = new CompanyPageReqVO();
            reqVO.setTenantId(tenantId);
            PageResult<CompanyDO> companyPage = companyService.getCompanyPage(reqVO);
            if (companyPage == null) {
                return CommonResult.error(ErrorCodeConstants.COMPANY_NOT_EXISTS);
            }

            List<CompanyDO> companyList = companyPage.getList();
            if (companyList.isEmpty()) {
                return CommonResult.error(ErrorCodeConstants.COMPANY_NOT_EXISTS);
            }
            Long companyId = companyList.get(0).getId();

            List<DomainName> companyAllDomainUrl = queryCompanyAllDomainUrl(companyId);
            Set<String> domainSet = companyAllDomainUrl.stream()
                    .map(DomainName::getDomain)
                    .collect(Collectors.toSet());

            if (domainSet.isEmpty()) {
                return CommonResult.error(ErrorCodeConstants.DOMAIN_NOT_FOUND);
            }

            List<String> domainList = new ArrayList<>(domainSet);

            List<WechatLoginRecordDO> onlineWechatAccounts = wechatLoginRecordService.getOnlineWechatAccountsByDomainUrl(domainList);

            Map<String, Long> ipMap = onlineWechatAccounts.stream()
                    .collect(Collectors.groupingBy(WechatLoginRecordDO::getIp, Collectors.counting()));

            String balancedIp = selectBalancedIp(domainList, ipMap);
            if (balancedIp == null) {
                return CommonResult.error(ErrorCodeConstants.IP_SELECTION_FAILED);
            }

            QianXunResponse<QianXunQrCode> loginQrCode = qXunWrapper.getLoginQrCode(balancedIp);
            if (loginQrCode == null) {
                return CommonResult.error(ErrorCodeConstants.WECHAT_LOGIN_QRCODE_ERROR);
            }
            loginQrCode.setDomain(balancedIp);
            try {
                putQrCodeCache(balancedIp, loginQrCode);
            } catch (Exception e) {
                log.warn("缓存二维码失败，不影响主流程", e);
            }

            return CommonResult.success(loginQrCode.getResult().getQrCode());
        } catch (Exception e) {
            log.error("获取二维码异常，请稍后再试: {}", e.getMessage());
            return CommonResult.error(ErrorCodeConstants.SYSTEM_ERROR);
        }
    }

    private void putQrCodeCache(String balancedIp, QianXunResponse<QianXunQrCode> loginQrCode) {
        // 缓存二维码数据 和port 和pid数据
        String key = RedisUtils.buildKey(balancedIp, "_", loginQrCode.getPort());
        List<QianXunQrCode> qianXunQrCodes = new ArrayList<>();
        if (RedisUtils.exists(key)) {
            String value = RedisUtils.getValue(key);
            qianXunQrCodes = JSONArray.parseArray(value, QianXunQrCode.class);
            if (CollectionUtils.isEmpty(qianXunQrCodes)) {
                qianXunQrCodes = new ArrayList<>();
            }
            qianXunQrCodes.add(loginQrCode.getResult());
        }
        RedisUtils.setValue(key, JSON.toJSONString(qianXunQrCodes), 60);
    }

    private List<DomainName> queryCompanyAllDomainUrl(Long companyId) {
        DomainNameRequest domainNameRequest = new DomainNameRequest();
        domainNameRequest.setCompanyId(companyId.toString());
        // 查询全量域名数据
        return wxDomainUrlService.queryAllDomainUrl(domainNameRequest);
    }

    /**
     * 登录检测
     */
    @PostMapping("/checkWeChatStatus")
    @Operation(summary = "获取微信二维码")
    public CommonResult<QianXunLoginStatus> checkWeChatStatus(@RequestBody WechatQuery request) {
        try {
            // 查询记录登录的 微信端口记录
            QianXunResponse<QianXunLoginStatus> loginStatus = qXunWrapper.getLoginStatus(request.getDomain(), request.getPort());
            return CommonResult.success(loginStatus.getResult());
        }catch (Exception e){
            log.error("登录状态校验异常,param:{},error:{}",JSON.toJSONString(request),Throwables.getStackTraceAsString(e));
            return CommonResult.error(ErrorCodeConstants.SYSTEM_ERROR);
        }
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
