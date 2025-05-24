
package cn.iocoder.yudao.module.system.controller.admin.callback.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.module.system.controller.admin.callback.request.CallBackReq;
import cn.iocoder.yudao.module.system.controller.admin.callback.request.event.QianXunAccountChangeEvent;
import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatLoginRecordSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.wxpool.vo.WxAccountPoolVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WechatLoginRecordDO;
import cn.iocoder.yudao.module.system.domain.enums.YesOrNoEnum;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import cn.iocoder.yudao.module.system.domain.model.wxpool.WxAccountPool;
import cn.iocoder.yudao.module.system.domain.request.DomainNameRequest;
import cn.iocoder.yudao.module.system.domain.request.WxAccountPoolRequest;
import cn.iocoder.yudao.module.system.enums.qianxun.QianXunCallbackType;
import cn.iocoder.yudao.module.system.service.domainurll.WxDomainUrlService;
import cn.iocoder.yudao.module.system.service.wx.WechatLoginRecordService;
import cn.iocoder.yudao.module.system.service.wx.WxFriendService;
import cn.iocoder.yudao.module.system.service.wxpool.WxAccountPoolService;
import cn.iocoder.yudao.module.system.util.cache.RedisWrapper;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.REDIS_PORT_KEY_PREFIX;

@Slf4j
@Service
public class QianXunCallbackServiceImpl implements QianXunCallbackService {

    private static final String UNKNOWN = "未知";

    @Resource
    private WechatLoginRecordService wechatLoginRecordService;

    @Resource
    private WxAccountPoolService wxAccountPoolService;

    @Resource
    private WxDomainUrlService wxDomainUrlService;

    @Resource
    private RedisWrapper redisWrapper;

    @Resource
    private WxFriendService wxFriendService;

    /**
     * 处理通用回调
     *
     * @param requestBody 请求体JSON内容
     */
    @Override
    public void handleQianXunCallback(CallBackReq request) {
        try {
            log.info("收到千寻回调数据: {}", request);

            // 解析回调JSON数据
            String event = request.getEvent();
            String wxid = request.getWxid();
            Object data = request.getData();

            if (QianXunCallbackType.wxidChange.equals(event)) { // 登录状态变更事件
                if(data instanceof QianXunAccountChangeEvent){
                   changeAccountEvent(request);
                }

            }
        } catch (IllegalArgumentException e) {
            log.warn("处理千寻回调参数异常", e);
        } catch (Exception e) {
            log.error("处理千寻回调异常", e);
            throw new RuntimeException("处理千寻回调异常", e);
        }
    }

    private void changeAccountEvent(CallBackReq request) {
        String event = request.getEvent();
        String wxid = request.getWxid();
        Object dataObj = request.getData();

        if (!(dataObj instanceof QianXunAccountChangeEvent)) {
            log.warn("回调事件: {} 数据类型错误，wxid: {}, data: {}", event, wxid, dataObj);
            return;
        }

        QianXunAccountChangeEvent data = (QianXunAccountChangeEvent) dataObj;
        String type = data.getType();
        Integer port = data.getPort();

        // 校验端口范围
        if (port == null || port <= 0 || port > 65535) {
            log.warn("无效的端口号: {}, 事件: {}, wxid: {}", port, event, wxid);
            return;
        }

        // 获取Redis中的缓存数据
        String cacheKey = redisWrapper.buildKey(REDIS_PORT_KEY_PREFIX, "_", port.toString());
        String cacheData = redisWrapper.getValue(cacheKey);

        if (!StringUtils.hasText(cacheData)) {
            log.warn("未找到端口{}的缓存数据，事件: {}, wxid: {}", port, event, wxid);
            return;
        }

        List<QianXunResponse<?>> qrCodeList;
        try {
            qrCodeList = JSON.parseObject(cacheData, new TypeReference<List<QianXunResponse<?>>>() {});
        } catch (JSONException e) {
            log.warn("端口{}的缓存数据解析失败，事件: {}, wxid: {}", port, event, wxid, e);
            return;
        }

        if (qrCodeList == null || qrCodeList.isEmpty()) {
            log.warn("端口{}的缓存数据为空列表，事件: {}, wxid: {}", port, event, wxid);
            return;
        }

        // 获取IP地址（从缓存数据中提取）
        String serverIp = qrCodeList.get(0).getDomain();

        boolean handledSuccessfully = false;
        try {
            if (YesOrNoEnum.YES.getStatus() != null && Objects.equals(YesOrNoEnum.YES.getStatus(), type)) { // 登录事件
                handleLogin(wxid, port, serverIp, data);
            } else { // 登出事件
                handleLogout(wxid, port, serverIp);
            }
            handledSuccessfully = true;
        } catch (Exception e) {
            log.error("回调事件：账号登录失败，param:{}, 原因：{}", JSON.toJSONString(request), Throwables.getStackTraceAsString(e));
        } finally {
            // 清除Redis缓存（可考虑异步执行）
            if (handledSuccessfully) {
                redisWrapper.delValue(cacheKey);
                log.info("已清除端口{}的缓存数据，事件: {}, wxid: {}", port, event, wxid);
            }
        }
    }

    /**
     * 处理登录事件
     */
    @Transactional(rollbackFor = Exception.class)
    private void handleLogin(String wxid, Integer port, String serverIp, QianXunAccountChangeEvent data) {
        log.info("处理微信登录事件: wxid={}, port={}, serverIp={}", wxid, port, serverIp);


        // 查询域名数据
        DomainNameRequest domainNameRequest = new DomainNameRequest();
        domainNameRequest.setDomain(serverIp);
        List<DomainName> domainNames = wxDomainUrlService.queryAllDomainUrl(domainNameRequest);
        if(CollectionUtils.isEmpty(domainNames)){
            log.info("handleLogin 查询的域名不存在,param:{}",JSON.toJSONString(data));
            return;
        }

        DomainName domainName = domainNames.get(0);

        // 查询IP和微信ID以及在线状态和未删除状态查询登录记录表
        List<WechatLoginRecordDO> existingLoginRecord = getExistingLoginRecord(wxid);

        // 如果存在记录，先删除
        if (CollectionUtils.isNotEmpty(existingLoginRecord)) {
            Optional<WechatLoginRecordDO> optionalRecord = existingLoginRecord.stream()
                    .filter(record -> serverIp.equals(record.getIp()))
                    .findFirst();
            optionalRecord.ifPresent(record -> {
                wechatLoginRecordService.deleteWechatLoginRecord(record.getId());
                log.info("已删除微信ID{}的登录记录", wxid);
            });
        }

        addWechatLoginRecord(wxid, serverIp, port, data);

        Long id = 0L;
        if(!existingWxPool(wxid)){
            id = addAccountPool(wxid, data);
        }

        // 在查询一遍数据
        WxAccountPoolRequest request = new WxAccountPoolRequest();
        request.setUnionId(wxid);
        // 绑定微信ID和IP
        WxAccountPoolVO bindWxAccountPoolVO = new WxAccountPoolVO();
        bindWxAccountPoolVO.setDomainName(domainName);
        bindWxAccountPoolVO.setUnionId(wxid);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(data.getUserId() != null ? data.getUserId() : 0L);
        bindWxAccountPoolVO.setCreator(userInfo);
        wxAccountPoolService.bindDomainUrl(bindWxAccountPoolVO);
        log.info("已绑定微信ID{}与IP{}", wxid, serverIp);

        // 同步好友和群信息
        wxFriendService.refreshWxFriendFromQianxun(wxid);
    }

    private Long addAccountPool(String wxid, QianXunAccountChangeEvent data) {
        // 保存到微信公共池
        WxAccountPoolVO accountPool = new WxAccountPoolVO();
        accountPool.setDeleted(YesOrNoEnum.NO.getStatus());
        accountPool.setAccountType("wx");
        accountPool.setUnionId(wxid);
        accountPool.setNickName(data.getNick());
        accountPool.setAvatar(data.getAvatarUrl());
        accountPool.setPhone(data.getPhone());
        accountPool.setEmail(data.getEmail());
        accountPool.setIsExpired(YesOrNoEnum.NO); // 未过期
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(data.getUserId() != null ? data.getUserId() : 0L);
        accountPool.setCreator(userInfo); // 根据业务设置正确的操作员ID

        Long id = wxAccountPoolService.saveWxAccountPool(accountPool);
        log.info("已保存微信ID{}到公共池", wxid);
        return id;
    }

    private Boolean existingWxPool(String wxid) {
        WxAccountPoolRequest request = new WxAccountPoolRequest();
        request.setUnionId(wxid);
        PageResult<WxAccountPool> result = wxAccountPoolService.queryWxAccountPoolForPage(request);
        return result != null && CollectionUtils.isNotEmpty(result.getList());
    }

    private void addWechatLoginRecord(String wxid, String serverIp, Integer port, QianXunAccountChangeEvent data) {
        // 创建新的登录记录
        WechatLoginRecordSaveReqVO newRecord = new WechatLoginRecordSaveReqVO();
        newRecord.setWxUnionId(wxid);
        newRecord.setWxNo(data.getWxNum());
        newRecord.setNickname(data.getNick());
        newRecord.setIp(serverIp);
        newRecord.setPort(port);
        Date date = new Date();
        newRecord.setLoginTime(date);
        newRecord.setIsOffline(false);
        newRecord.setGmtCreate(date);
        newRecord.setGmtModified(date);
        newRecord.setCreatorId(data.getUserId() != null ? data.getUserId() : 0L);
        wechatLoginRecordService.createWechatLoginRecord(newRecord);
        log.info("已创建微信ID{}的新登录记录", wxid);
    }

    private List<WechatLoginRecordDO> getExistingLoginRecord(String wxUnionId) {
        return wechatLoginRecordService.getOnlineWechatAccountsByWxIdList(Collections.singletonList(wxUnionId));
    }

    /**
     * 处理登出事件
     */
    private void handleLogout(String wxid, Integer port, String serverIp) {
        log.info("处理微信登出事件: wxid={}, port={}, serverIp={}", wxid, port, serverIp);

        // 查询登录记录
        List<WechatLoginRecordDO> existingLoginRecord = getExistingLoginRecord(wxid);

        // 如果存在记录，更新状态或删除
        if (CollectionUtils.isNotEmpty(existingLoginRecord)) {
            Optional<WechatLoginRecordDO> optionalRecord = existingLoginRecord.stream()
                    .filter(record -> serverIp.equals(record.getIp()))
                    .findFirst();
            optionalRecord.ifPresent(record -> {
                WechatLoginRecordSaveReqVO wechatLoginRecordSaveReqVO = new WechatLoginRecordSaveReqVO();
                wechatLoginRecordSaveReqVO.setId(record.getId());
                record.setIsOffline(YesOrNoEnum.YES.getStatus());
                record.setDeleted(YesOrNoEnum.YES.getStatus());
                wechatLoginRecordService.updateWechatLoginRecord(wechatLoginRecordSaveReqVO);
                wechatLoginRecordService.deleteWechatLoginRecord(record.getId());
            });
            log.info("用户登出: 已将微信ID{}的登录记录标记为离线", wxid);
        } else {
            log.warn("未找到微信ID{}的登录记录，无法处理登出", wxid);
        }
    }

    /**
     * 处理账号变动事件(10014)
     *
     * @param callbackData 回调数据
     */
    @Override
    @SuppressWarnings("unchecked")
    public void handleAccountChange(Map<String, Object> callbackData) {
        if (callbackData == null) {
            log.warn("[handleAccountChange][回调数据为空]");
            return;
        }

        try {
            // 提取基础信息
            String wxid = Objects.toString(callbackData.get("wxid"), UNKNOWN);
            Map<String, Object> data = (Map<String, Object>) callbackData.get("data");

            if (data == null) {
                log.warn("[handleAccountChange][data字段为空] wxid: {}", wxid);
                return;
            }

            // 提取关键信息
            Integer type = getIntValue(data, "type");

            // 使用ObjectMapper将data转换为VO对象
            QianXunAccountChangeEvent eventVo;
            try {
                // 先将data转换为JSON字符串，再解析为VO对象
                String dataJson = JsonUtils.toJsonString(data);
                eventVo = JsonUtils.parseObject(dataJson, QianXunAccountChangeEvent.class);
            } catch (Exception e) {
                log.error("[handleAccountChange][数据转换为VO异常] wxid: {}", wxid, e);
                return;
            }

            if (eventVo == null) {
                log.warn("[handleAccountChange][VO对象转换为空] wxid: {}", wxid);
                return;
            }

            /// 设置wxid，确保VO对象中的wxid与回调的wxid一致
            if (!wxid.equals(UNKNOWN)) {
                eventVo.setWxid(wxid);
            }

            // 打印日志
            if (type != null && type == 1) { // 上线事件
                log.info("[handleAccountChange][微信账号上线] wxid: {}, 昵称: {}, 微信号: {}, 手机: {}",
                        wxid, eventVo.getNick(), eventVo.getWxNum(), eventVo.getPhone());
                handleAccountLogin(wxid, eventVo);
            } else if (type != null && type == 0) { // 下线事件
                log.info("[handleAccountChange][微信账号下线] wxid: {}", wxid);
                handleAccountLogout(wxid, eventVo);
            } else {
                log.warn("[handleAccountChange][未知的账号事件类型] wxid: {}, type: {}", wxid, type);
            }
        } catch (Exception e) {
            log.error("[handleAccountChange][处理账号变动事件异常]", e);
        }
    }

    /**
     * 登出后清理资源
     */
    private void cleanupAfterLogout(String wxid) {
        try {
            // TODO: 注销心跳检测
            // heartbeatManager.unregisterHeartbeat(wxid);

            // TODO: 释放相关资源
            // resourceManager.releaseResources(wxid);

            log.info("[cleanupAfterLogout][微信账号资源清理完成] wxid: {}", wxid);
        } catch (Exception e) {
            log.error("[cleanupAfterLogout][微信账号资源清理异常] wxid: {}", wxid, e);
        }
    }

    /**
     * 处理账号登出事件
     *
     * @param wxid 微信ID
     * @param eventVo 账号变动事件VO
     */
    private void handleAccountLogout(String wxid, QianXunAccountChangeEvent eventVo) {
        try {
            // TODO: 更新微信账号状态
            // wechatAccountService.updateStatus(wxid, false);

            // TODO: 记录登出事件
            // wechatLogService.recordLogoutEvent(wxid, System.currentTimeMillis());

            // TODO: 异步通知相关业务系统
            // messagingService.sendAccountStatusMessage(wxid, "OFFLINE", System.currentTimeMillis());

            // 清理相关资源
            cleanupAfterLogout(wxid);
        } catch (Exception e) {
            log.error("[handleAccountLogout][处理微信账号登出事件异常] wxid: {}", wxid, e);
        }
    }

    /**
     * 初始化操作相关方法
     */
    private void initializeAfterLogin(String wxid) {
        try {
            // TODO: 登录成功后的初始化操作
            // 1. 同步好友列表
            // wechatFriendService.syncFriendList(wxid);

            // 2. 同步群聊列表
            // wechatGroupService.syncGroupList(wxid);

            // 3. 设置心跳检测
            // heartbeatManager.registerHeartbeat(wxid);

            log.info("[initializeAfterLogin][微信账号初始化完成] wxid: {}", wxid);
        } catch (Exception e) {
            log.error("[initializeAfterLogin][微信账号初始化异常] wxid: {}", wxid, e);
        }
    }

    /**
     * 处理账号登录事件
     *
     * @param wxid 微信ID
     * @param eventVo 账号变动事件VO
     */
    private void handleAccountLogin(String wxid, QianXunAccountChangeEvent eventVo) {
        try {
            log.info("[handleAccountLogin][微信账号登录详情] wxid: {}, 昵称: {}, 地区: {}{}{}",
                    wxid, eventVo.getNick(),
                    eventVo.getCountry(),
                    StringUtils.hasText(eventVo.getProvince()) ? " " + eventVo.getProvince() : "",
                    StringUtils.hasText(eventVo.getCity()) ? " " + eventVo.getCity() : "");

            // TODO: 保存或更新微信账号信息到数据库
            // wechatAccountService.updateAccount(wxid, eventVo);

            // TODO: 记录登录事件
            // wechatLogService.recordLoginEvent(wxid, eventVo.getNick(), System.currentTimeMillis());

            // TODO: 异步通知相关业务系统
            // messagingService.sendAccountStatusMessage(wxid, "ONLINE", System.currentTimeMillis());

            // 登录成功后，可以执行初始化操作
            initializeAfterLogin(wxid);
        } catch (Exception e) {
            log.error("[handleAccountLogin][处理微信账号登录事件异常] wxid: {}", wxid, e);
        }
    }

    /**
     * 安全获取整型值
     *
     * @param map 数据Map
     * @param key 键名
     * @return 整型值，如果不存在或不是整型则返回null
     */
    private Integer getIntValue(Map<String, Object> map, String key) {
        if (map == null || !map.containsKey(key)) {
            return null;
        }

        Object value = map.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}