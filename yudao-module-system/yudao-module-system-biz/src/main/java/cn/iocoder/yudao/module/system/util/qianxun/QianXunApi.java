package cn.iocoder.yudao.module.system.util.qianxun;

import cn.hutool.core.map.MapUtil;
import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.framework.common.util.http.HttpUtils;
import cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 千寻微信框架Pro 接口请求工具类
 */
@Component

public class QianXunApi {
    private static final Logger log = LoggerFactory.getLogger(QianXunApi.class);
    static String request_url_path = "/qianxun/httpapi";

    /**
     * 发送请求到千寻微信框架接口并处理响应
     *
     * @param ip 服务器域名
     * @param type 请求类型
     * @param data 请求数据
     * @param wxid 微信ID (可选，没有传null)
     * @param errorMessage 错误日志信息
     * @return 响应结果Map
     */
    private static Map<String, Object> sendRequest(String ip, String type, Map<String, Object> data,
                                                   String wxid, String errorMessage) {
        try {
            // 构建URL
            String url = ip + request_url_path;
            if (wxid != null && !wxid.isEmpty()) {
                url += "?wxid=" + wxid;
            }

            // 创建请求头
            Map<String, String> headers = new HashMap<>();

            // 创建请求体
            Map<String, Object> reqDataMap = new HashMap<>();
            reqDataMap.put("type", type);
            reqDataMap.put("data", data != null ? data : new HashMap<>());

            // 将请求体转为JSON字符串
            String requestBody = JSONObject.toJSONString(reqDataMap);

            // 发送POST请求并获取响应
            String responseStr = HttpUtils.post(url, headers, requestBody);

            // 检查空响应
            if (responseStr == null || responseStr.trim().isEmpty()) {
                throw new RuntimeException("服务器返回空响应");
            }
            Map<String, Object> result = JSON.parseObject(responseStr, new TypeReference<Map<String, Object>>() {
            });
            if(result != null && "200".equals(result.get("code"))){
                return JSON.parseObject(result.get("result").toString(), new TypeReference<Map<String, Object>>() {
                });
            }
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.QIANXUN_API_RESULT_ERROR);
        } catch (Exception e) {
            log.error(errorMessage, e);
            return MapUtil.empty();
        }
    }

    /**
     * 获取微信列表（getWeChatList）
     *
     * @param ip 服务器域名
     * @return 响应结果Map，包含code、msg、result等字段
     */
    public static Map<String, Object> getWeChatList(String ip) {
        return sendRequest(ip, "getWeChatList", null, null, "获取微信列表失败");
    }

    /**
     * 获取登录二维码（getLoginQrCode）
     *
     * @param ip 服务器域名
     * @return 响应格式
     */
    public static Map<String, Object> getLoginQrCode(String ip) {
        return sendRequest(ip, "getLoginQrCode", null, null, "获取登录二维码失败");
    }

    /**
     * 获取个人信息（getSelfInfo）
     * @param ip 服务器域名
     * @param wxid 微信ID
     * @return 响应结果Map，包含code、msg、result等字段
     */
    public static Map<String, Object> getSelfInfo(String ip, String wxid) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("type", "1");
        return sendRequest(ip, "getSelfInfo", dataMap, wxid, "获取个人信息失败");
    }

    /**
     * 获取好友列表（getFriendList）
     *
     * @param ip 服务器IP地址
     * @param wxid 微信ID
     * @return 响应结果Map，包含code、msg、result等字段
     */
    public static Map<String, Object> getFriendList(String ip, String wxid) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("type", "2");
        return sendRequest(ip, "getFriendList", dataMap, wxid, "获取好友列表失败");
    }

    /**
     * 获取群聊列表（getGroupList）
     *
     * @param ip 服务器IP地址
     * @param wxid 微信ID
     * @return 响应结果Map，包含code、msg、result等字段
     */
    public static Map<String, Object> getGroupList(String ip, String wxid) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("type", "2");
        return sendRequest(ip, "getGroupList", dataMap, wxid, "获取群聊列表失败");
    }
}

