package cn.iocoder.yudao.module.system.controller.admin.wechat;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.callback.impl.QianXunCallbackService;
import cn.iocoder.yudao.module.system.controller.admin.callback.request.CallBackReq;
import cn.iocoder.yudao.module.system.controller.admin.callback.request.event.QianXunAccountChangeEvent;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.company.CompanyPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatQuery;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WechatLoginRecordDO;
import cn.iocoder.yudao.module.system.domain.enums.YesOrNoEnum;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import cn.iocoder.yudao.module.system.domain.request.DomainNameRequest;
import cn.iocoder.yudao.module.system.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.system.service.company.CompanyService;
import cn.iocoder.yudao.module.system.service.domainurll.WxDomainUrlService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.service.wechat.WeChatService;
import cn.iocoder.yudao.module.system.service.wx.WechatLoginRecordService;
import cn.iocoder.yudao.module.system.service.wxpool.WxAccountPoolService;
import cn.iocoder.yudao.module.system.util.cache.RedisWrapper;
import cn.iocoder.yudao.module.system.wrapper.qianxun.QXunWrapper;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunInfoSelf;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunLoginStatus;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunQrCode;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Throwables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.REDIS_PORT_KEY_PREFIX;
import static cn.iocoder.yudao.module.system.util.babanceip.LoadBalancer.selectBalancedIp;


@Tag(name = "管理后台 - 微信账号管理")
@Slf4j
@RestController
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

    @Resource
    private RedisWrapper redisWrapper;

    @Resource
    private QianXunCallbackService qianXunCallbackService;

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
    public CommonResult<Object> getQrCode() {
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

            return CommonResult.success(loginQrCode);
        } catch (Exception e) {
            log.error("获取二维码异常，请稍后再试: {}", e.getMessage());
            return CommonResult.error(ErrorCodeConstants.SYSTEM_ERROR);
        }
    }

    private void putQrCodeCache(String balancedIp, QianXunResponse<QianXunQrCode> loginQrCode) {
        // 缓存二维码数据 和port 和pid数据
        String key = redisWrapper.buildKey(REDIS_PORT_KEY_PREFIX, "_", loginQrCode.getPort());
        List<QianXunResponse<QianXunQrCode>> qianXunQrCodes = new ArrayList<>();
        if (redisWrapper.exists(key)) {
            String value = redisWrapper.getValue(key);
            qianXunQrCodes = JSON.parseObject(value, new TypeReference<List<QianXunResponse<QianXunQrCode>>>() {});
            if (CollectionUtils.isEmpty(qianXunQrCodes)) {
                qianXunQrCodes = new ArrayList<>();
            }
        }
        qianXunQrCodes.add(loginQrCode);
        redisWrapper.setValue(key, JSON.toJSONString(qianXunQrCodes), 60 * 5);
    }

    private List<DomainName> queryCompanyAllDomainUrl(Long companyId) {
        DomainNameRequest domainNameRequest = new DomainNameRequest();
        domainNameRequest.setCompanyId(companyId.toString());
        domainNameRequest.setDomainStatus(YesOrNoEnum.NO.getStatus());
        domainNameRequest.setDeleted(YesOrNoEnum.NO.getStatus());
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
            loginStatus.getResult().setDomain(request.getDomain());
            return CommonResult.success(loginStatus.getResult());
        }catch (Exception e){
            log.error("登录状态校验异常,param:{},error:{}",JSON.toJSONString(request),Throwables.getStackTraceAsString(e));
            return CommonResult.error(ErrorCodeConstants.SYSTEM_ERROR);
        }
    }

    /**
     * 获取微信个人数据
     */
    @PostMapping("/querySelf")
    public CommonResult<Boolean> queryWechatFriendList(@RequestBody WechatQuery request) {
        try {


            // 查询千寻当前登录账号的个人信息 参数必须传 IP  port wxUnionId
            QianXunResponse<QianXunInfoSelf> userInfo = qXunWrapper.getSelfInfo(request.getDomain(), request.getWxUnionId(), request.getPort());
            if (userInfo == null) {
                return CommonResult.error(ErrorCodeConstants.QUERY_LOGIN_WECHAT_INFO_ERROR);
            }
            // 拼装 回调事件 请求参数
            CallBackReq<QianXunAccountChangeEvent> req = new CallBackReq<>();
        /*    JSONObject jsonObject = new JSONObject();
        jsonObject.put("event", request.getEventId());
        jsonObject.put("wxid", request.getWxUnionId());
        jsonObject.put("data", userInfo.getResult());*/
            req.setEvent(request.getEventId());
            req.setWxid(request.getWxUnionId());
            QianXunAccountChangeEvent qianXunAccountChangeEvent = new QianXunAccountChangeEvent();
            BeanUtils.copyProperties(userInfo.getResult(), qianXunAccountChangeEvent);
            qianXunAccountChangeEvent.setType(YesOrNoEnum.YES.getStatus().toString());
            if(StringUtils.isNumeric(userInfo.getPort())) {
                qianXunAccountChangeEvent.setPort(Integer.valueOf(userInfo.getPort()));
            }
            req.setData(qianXunAccountChangeEvent);
            log.info("组装登录回调事件信息 ：{}", JSONObject.toJSONString(req));
            qianXunCallbackService.handleQianXunCallback(req);
            return CommonResult.success(true);
        }catch (Exception e){
            log.error("查询个人信息异常，请稍后再试，param:{},error:{}",JSON.toJSONString(request),Throwables.getStackTraceAsString(e));
            return CommonResult.error(ErrorCodeConstants.SYSTEM_ERROR);
        }
    }



}
