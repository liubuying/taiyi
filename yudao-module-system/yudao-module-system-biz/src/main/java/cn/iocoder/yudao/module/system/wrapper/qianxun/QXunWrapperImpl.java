package cn.iocoder.yudao.module.system.wrapper.qianxun;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.framework.common.util.http.HttpUtils;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.module.system.enums.qianxun.QianXunApiTypeEnum;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


/**
 * 千寻微信框架Pro 接口请求工具类
 *
 */
@Slf4j
@Service
public class QXunWrapperImpl implements QXunWrapper {

    private static final String QIANXUN_REQUEST_URL_PATH = ":7777/qianxun/httpapi";
    private static final String WECHAT_REQUEST_URL_PATH = "/wechat/httpapi";

    // ==================== 登录相关API =========================

    /**
     * 获取登录二维码
     *
     * @param ip 服务器域名
     * @return 响应对象，包含二维码信息
     */
    @Override
    public  QianXunResponse<QianXunQrCode> getLoginQrCode(String ip){
        return sendRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                null,
                QianXunApiTypeEnum.GET_LOGIN_QRCODE,
                data -> new HashMap<>(), // 空data
                QianXunQrCode.class
        );
    }

    /**
     * 获取登录状态
     *
     * @param ip 服务器域名
     * @return 响应对象，包含登录状态
     */
    @Override
    public  QianXunResponse<QianXunLoginStatus> getLoginStatus(String ip, String port){
        // 构建请求URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://" + ip + ":" + port + WECHAT_REQUEST_URL_PATH);
        String url = builder.toUriString();

        try {
            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("type", QianXunApiTypeEnum.GET_LOGIN_STATUS.getType());

            // 发送POST请求
            String responseStr = HttpUtils.post(url, new HashMap<>(), JsonUtils.toJsonString(requestBody));

            // 检查空响应
            if (responseStr == null || responseStr.trim().isEmpty()) {
                throw new RuntimeException("服务器返回空响应");
            }

            // 解析响应
            com.fasterxml.jackson.databind.JsonNode rootNode = JsonUtils.parseTree(responseStr);
            int code = rootNode.path("code").asInt();
            String msg = rootNode.path("msg").asText();
            String timestamp = rootNode.path("timestamp").asText();
            QianXunResponse<QianXunLoginStatus> response = new QianXunResponse<>();
            response.setCode(code);
            response.setMsg(msg);
            response.setTimestamp(timestamp);
            response.setWxid(rootNode.path("wxid").asText());
            response.setPort(rootNode.path("port").asText());
            response.setPid(rootNode.path("pid").asText());
            response.setFlag(rootNode.path("flag").asText());

            // 如果有data字段且状态码为200，解析data
            if (code == 200 && rootNode.has("result")) {
                // 创建包含所有字段的map
                Map<String, Object> fullResult = new HashMap<>();

                // 添加result中的字段
                if (rootNode.has("result")) {
                    Map<String, Object> resultMap = JsonUtils.parseObject(rootNode.path("result").toString(), Map.class);
                    if (resultMap != null) {
                        fullResult.putAll(resultMap);
                    }
                }

                // 添加顶层字段
                if (rootNode.has("wxid")) fullResult.put("wxid", rootNode.path("wxid").asText());
                if (rootNode.has("port")) fullResult.put("port", rootNode.path("port").asInt());
                if (rootNode.has("pid")) fullResult.put("pid", rootNode.path("pid").asInt());
                if (rootNode.has("flag")) fullResult.put("flag", rootNode.path("flag").asText());
                if (rootNode.has("timestamp"))
                    fullResult.put("startTimeStamp", rootNode.path("timestamp").asText());

                // 使用map创建QianXunLoginStatus对象
                QianXunLoginStatus loginStatus = JsonUtils.parseObject(JsonUtils.toJsonString(fullResult), QianXunLoginStatus.class);
                response.setResult(loginStatus);
            }  else {
                throw ServiceExceptionUtil.exception(QianXunApiTypeEnum.GET_LOGIN_STATUS.getErrorCode());
            }

            return response;
        }catch (ServiceException e) {
            // 直接抛出业务异常
            throw e;
        } catch (Exception e) {
            log.error(QianXunApiTypeEnum.GET_LOGIN_STATUS.getErrorMessage(), e);
            return QianXunResponse.error(500, QianXunApiTypeEnum.GET_LOGIN_STATUS.getErrorMessage() + e.getMessage());
        }
    }

    /**
     * 获取微信列表
     *
     * @param ip 服务器域名
     * @return 响应对象，包含微信列表
     */
    @Override
    public  QianXunResponse<List<QianXunLoginStatus>> getWeChatList(String ip){
        return sendArrayRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                null,
                QianXunApiTypeEnum.GET_WECHAT_LIST,
                data -> new HashMap<>(),
                QianXunLoginStatus.class
        );
    }

    /**
     * 结束微信进程
     *
     * @param ip 服务器域名
     * @return 响应对象
     */
    @Override
    public QianXunResponse<Object> killWeChat(String ip, String prot){
        ip = ip + ":" + prot;
        return sendRequest(
                ip,
                WECHAT_REQUEST_URL_PATH,
                null,
                QianXunApiTypeEnum.KILL_WECHAT,
                data -> new HashMap<>(), // 空data
                Object.class
        );
    }

    /**
     * 修改微信版本号
     *
     * @param ip 服务器域名
     * @param version 版本号
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> editVersion (String ip, String version){
        // QianXunApiTypeEnum.EDIT_VERSION;
        return null;
    }

    /**
     * 设置下载图片时间
     *
     * @param ip 服务器域名
     * @param time 时间(单位：秒)
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> setDownloadImage(String ip, Integer time){
        // QianXunApiTypeEnum.SET_DOWNLOAD_IMAGE;
        return null;
    }
    // ==================== 获取信息相关API =========================

    /**
     * 获取个人信息
     *
     * @param ip   服务器域名
     * @param wxid 微信ID
     * @return 个人信息
     */
    @Override
    public QianXunResponse<QianXunInfo> getSelfInfo(String ip, String wxid) {
        return sendRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxid,
                QianXunApiTypeEnum.GET_SELF_INFO,
                data -> {
                    data.put("type", "2");
                    return data;
                },
                QianXunInfo.class
        );
    }

    /**
     * 获取好友列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 好友列表
     */
    @Override
    public QianXunResponse<List<QianXunInfoFriend>> getFriendList(String ip, String wxId) {
        return sendArrayRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxId,
                QianXunApiTypeEnum.GET_FRIEND_LIST,
                data -> {
                    data.put("type", "2");
                    return data;
                },
                QianXunInfoFriend.class
        );
    }

    /**
     * 获取群聊列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含群聊列表
     */
    @Override
    public  QianXunResponse<List<QianXunInfoGroup>> getGroupList(String ip, String wxId) {
        return sendArrayRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxId,
                QianXunApiTypeEnum.GET_GROUP_LIST,
                data -> {
                    data.put("type", "2");
                    return data;
                },
                QianXunInfoGroup.class
        );
    }

    // --
    /**
     * 获取公众号列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含公众号列表
     */
    @Override
    public  QianXunResponse<List<QianXunInfo>> getPublicList(String ip, String wxId){
        return null;
    }

    // --
    /**
     * 获取标签列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含标签列表
     */
    @Override
    public  QianXunResponse<List<Object>> getLabelList(String ip, String wxId){
        return null;
    }

    /**
     * 获取群成员列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @return 响应对象，包含群成员列表及其群昵称
     */
    @Override
    public  QianXunResponse<List<QianXunInfoGroupMember>> getMemberList(String ip, String wxId, String groupWxId){
        return sendArrayRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxId,
                QianXunApiTypeEnum.GET_MEMBER_LIST,
                data -> {
                    data.put("wxid",groupWxId);
                    data.put("type","2");
                    data.put("getNick","2");
                    return data;
                },
                QianXunInfoGroupMember.class
        );
    }

    /**
     * 获取群成员昵称
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param toWxId 目标wxId
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunInfoGroupMember> getMemberNick(String ip, String wxId, String groupWxId, String toWxId){
        return sendRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxId,
                QianXunApiTypeEnum.GET_MEMBER_NICK,
                data -> {
                    data.put("wxid",groupWxId);
                    data.put("objWxid",toWxId);
                    return data;
                },
                QianXunInfoGroupMember.class
        );
    }


    // --
    /**
     * 查询对象信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunInfo> queryObj(String ip, String wxId, String toWxId){
        return null;
    }

    /**
     * 查询陌生人信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toId 目标 QQ 或者 手机号 或者 微信号
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunInfoNewFriend> queryNewFriend(String ip, String wxId, String toId){
        return sendRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxId,
                QianXunApiTypeEnum.QUERY_NEW_FRIEND,
                data -> {
                    data.put("obj",toId);
                    return data;
                },
                QianXunInfoNewFriend.class
        );
    }

    /**
     * 查询群聊信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupwxId 群wxId
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunInfoGroup> queryGroup(String ip, String wxId, String groupwxId){
        return sendRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxId,
                QianXunApiTypeEnum.QUERY_GROUP,
                data -> {
                    data.put("wxid",groupwxId);
                    data.put("type","2");
                    return data;
                },
                QianXunInfoGroup.class
        );
    }

    // ==================== 好友管理相关API =========================

    /**
     * 同意好友请求
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param v3 v3数据
     * @param v4 v4数据
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> agreeFriendReq(String ip, String wxId, String v3, String v4){
        return null;
    }

    /**
     * 添加好友(通过v3)
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param v3 v3数据
     * @param message 验证消息
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> addFriendByV3(String ip, String wxId, String v3, String message){
        return null;
    }

    /**
     * 添加好友(通过群wxId)
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param toWxId 目标wxId
     * @param message 验证消息
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> addFriendByGroupWxid(String ip, String wxId, String groupWxId, String toWxId, String message){
        return sendRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxId,
                QianXunApiTypeEnum.ADD_FRIEND_BY_GROUP_WXID,
                data ->{
                    data.put("wxid",toWxId);
                    data.put("gid",groupWxId);
                    data.put("content",message);
                    data.put("scene","14");
                    return data;
                },
                Object.class
        );
    }

    /**
     * 删除好友
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunLoginStatus> delFriend(String ip, String wxId, String toWxId){
        return sendRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxId,
                QianXunApiTypeEnum.DEL_FRIEND,
                data ->{
                    data.put("wxid",toWxId);
                    return data;
                },
                QianXunLoginStatus.class
        );
    }

    /**
     * 修改对象备注
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param remark 备注
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> editObjRemark(String ip, String wxId, String toWxId, String remark){
        return sendRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxId,
                QianXunApiTypeEnum.EDIT_OBJ_REMARK,
                data ->{
                    data.put("wxid",toWxId);
                    data.put("remark",remark);
                    return data;
                },
                Object.class
        );
    }

    // ==================== 群聊管理相关API =========================

    /**
     * 退出群聊
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> quitGroup(String ip, String wxId, String groupWxId){
        return null;
    }

    /**
     * 创建群聊
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param wxIds 目标wxId列表，用英文逗号分隔
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> createGroup(String ip, String wxId, String wxIds){
        return null;
    }

    /**
     * 添加群成员
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param toWxIds 目标wxId列表，用英文逗号分隔
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> addMembers(String ip, String wxId, String groupWxId, String toWxIds){
        return null;
    }

    /**
     * 邀请群成员
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param toWxIds 目标wxId列表，用英文逗号分隔
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> inviteMembers(String ip, String wxId, String groupWxId, String toWxIds){
        return null;
    }

    /**
     * 移除群成员
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param toWxIds 目标wxId列表，用英文逗号分隔
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> delMembers(String ip, String wxId, String groupWxId, String toWxIds){
        return null;
    }

    /**
     * 修改自己群昵称
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param nick 昵称
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> editSelfMemberNick(String ip, String wxId, String groupWxId, String nick){
        return null;
    }
    // ==================== 发送消息相关API =========================

    /**
     * 发送文本消息 - 2048 字符
     *
     * @param ip 服务器域名
     * @param wxId 要使用微信的wxid
     * @param toWxId 对方wxid 要给谁，支持好友、群聊、公众号等
     * @param msg 消息内容
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendText(String ip, String wxId, String toWxId, String msg){
        return sendRequest(
                ip,
                QIANXUN_REQUEST_URL_PATH,
                wxId,
                QianXunApiTypeEnum.SEND_TEXT,
                data -> {
                    data.put("wxid",toWxId);
                    data.put("msg",msg);
                    return data;
                },
                QianXunMessage.class
        );
    }

    /**
     * 发送文本消息2 -字符 无上限
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param content 消息内容
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendText2(String ip, String wxId, String toWxId, String content){
        return null;
    }

    /**
     * 发送引用回复文本
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param content 消息内容
     * @param msgId 引用消息ID
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendReferText(String ip, String wxId, String toWxId, String content, String msgId){
        return null;
    }

    /**
     * 发送图片
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param path 图片路径
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendImage(String ip, String wxId, String toWxId, String path){
        return null;
    }

    /**
     * 发送文件
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param path 文件路径
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendFile(String ip, String wxId, String toWxId, String path){
        return null;
    }

    /**
     * 发送动态表情
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param path gif路径
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendGif(String ip, String wxId, String toWxId, String path){
        return null;
    }

    /**
     * 发送分享链接
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param title 标题
     * @param desc 描述
     * @param url 链接地址
     * @param imgUrl 图片地址
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendShareUrl(String ip, String wxId, String toWxId, String title, String desc, String url, String imgUrl){
        return null;
    }

    /**
     * 发送小程序
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param applet 小程序xml
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendApplet(String ip, String wxId, String toWxId, String applet){
        return null;
    }

    /**
     * 发送音乐分享
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param musicXml 音乐分享xml
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendMusic(String ip, String wxId, String toWxId, String musicXml){
        return null;
    }

    /**
     * 发送聊天记录
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param title 标题
     * @param dataList 聊天记录数据
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendChatLog(String ip, String wxId, String toWxId, String title, String dataList){
        return null;
    }

    /**
     * 发送名片消息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param content 名片wxId
     * @param nickName 昵称
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendCard(String ip, String wxId, String toWxId, String content, String nickName){
        return null;
    }

    /**
     * 发送XML
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param xml XML内容
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<QianXunMessage> sendXml(String ip, String wxId, String toWxId, String xml){
        return null;
    }

    // ==================== 其他操作API =========================

    /**
     * 解密dat图片
     *
     * @param ip 服务器域名
     * @param datPath dat图片路径
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<String> decryptImage(String ip, String datPath){
        return null;
    }

    /**
     * 微信状态检测
     *
     * @param ip 服务器域名
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> checkWeChat(String ip){
        return null;
    }

    /**
     * 查询授权信息
     *
     * @param ip 服务器域名
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> getAuthInfo(String ip){
        return null;
    }

    /**
     * 确认收款
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param transferId 转账ID
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> confirmTrans(String ip, String wxId, String transferId){
        return null;
    }

    /**
     * 退还收款
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param transferId 转账ID
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> returnTrans(String ip, String wxId, String transferId){
        return null;
    }

    /**
     * 打开浏览器
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param url 链接地址
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> openBrowser(String ip, String wxId, String url){
        return null;
    }

    /**
     * 执行云函数
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param hookName 云函数名
     * @param json JSON参数
     * @return 响应对象
     */
    @Override
    public  QianXunResponse<Object> runCloudFunction(String ip, String wxId, String hookName, String json){
        return null;
    }


    // ==================== 公共工具包 =========================
    /**
     * 发送请求并返回对象
     *
     * @param <T> 返回数据类型
     * @param ip 服务器地址
     * @param path 请求地址
     * @param wxid 微信ID
     * @param apiType API类型
     * @param dataBuilder 数据构建器错误码
     * @return API响应对象
     */
    public  <T>QianXunResponse<T> sendRequest(
            String ip,
            String path,
            String wxid,
            QianXunApiTypeEnum apiType,
            Function<Map<String, Object>, Map<String, Object>> dataBuilder,
            Class<T> tClass
    ) {
        return sendBaseRequest(
                ip,
                path,
                wxid,
                apiType,
                dataBuilder,
                dataNode -> {
                    if (dataNode.isArray()) {
                        throw new UnsupportedOperationException("数组结果请使用sendArrayRequest方法");
                    }
                    return JsonUtils.parseObject(dataNode.toString(), tClass);
                }
        );
    }

    /**
     * 发送请求并返回数组对象
     *
     * @param <T> 数组元素类型
     * @param ip 服务器地址
     * @param path 请求路径
     * @param wxid 微信ID
     * @param apiType API类型
     * @param dataBuilder 数据构建器
     * @param elementType 元素类型
     * @return API响应对象
     */
    public  <T> QianXunResponse<List<T>> sendArrayRequest(
            String ip,
            String path,
            String wxid,
            QianXunApiTypeEnum apiType,
            Function<Map<String, Object>, Map<String, Object>> dataBuilder,
            Class<T> elementType
            ) {
        return sendBaseRequest(
                ip,
                path,
                wxid,
                apiType,
                dataBuilder,
                dataNode -> {
                    if (!dataNode.isArray()) {
                        throw new RuntimeException("获得非数组结果");
                    }
                    return JsonUtils.parseArray(dataNode.toString(), elementType);
                }
        );
    }

    /**
     * 基础请求方法，处理公共的请求构建和发送逻辑
     *
     * @param <R> 响应数据类型
     * @param ip 服务器地址
     * @param path 请求路径
     * @param wxid 微信ID
     * @param apiType API类型
     * @param dataBuilder 数据构建器
     * @param responseProcessor 响应处理器，处理JSON Node并返回结果
     * @return API响应对象
     */
    private  <R> QianXunResponse<R> sendBaseRequest(
            String ip,
            String path,
            String wxid,
            QianXunApiTypeEnum apiType,
            Function<Map<String, Object>, Map<String, Object>> dataBuilder,
            Function<com.fasterxml.jackson.databind.JsonNode, R> responseProcessor
    ) {

        try {
            // 构建请求URL
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://"+ ip + path);
            if (wxid != null && !wxid.isEmpty()) {
                builder.queryParam("wxid", wxid);
            }
            String url = builder.toUriString();

            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("type", apiType.getType());

            // 使用数据构建器构建data部分
            Map<String, Object> data = new HashMap<>();
            Map<String, Object> builtData = dataBuilder != null ? dataBuilder.apply(data) : null;
            if (builtData != null && !builtData.isEmpty()) {
                requestBody.put("data", builtData);
            }

            // 发送POST请求
            String responseStr = HttpUtils.post(url, new HashMap<>(), JsonUtils.toJsonString(requestBody));

            // 检查空响应
            if (responseStr == null || responseStr.trim().isEmpty()) {
                throw new RuntimeException("服务器返回空响应");
            }

            // 解析响应
            com.fasterxml.jackson.databind.JsonNode rootNode = JsonUtils.parseTree(responseStr);
            int code = rootNode.path("code").asInt();
            String msg = rootNode.path("msg").asText();
            String timestamp = rootNode.path("timestamp").asText();

            QianXunResponse<R> response = new QianXunResponse<>();
            response.setCode(code);
            response.setMsg(msg);
            response.setTimestamp(timestamp);
            response.setWxid(rootNode.path("wxid").asText());
            response.setPort(rootNode.path("port").asText());
            response.setPid(rootNode.path("pid").asText());
            response.setFlag(rootNode.path("flag").asText());

            // 如果有result字段且状态码为200，解析result
            if (code == 200 && rootNode.has("result")) {
                R result = responseProcessor.apply(rootNode.path("result"));
                response.setResult(result);
            }

            // 检查响应状态
            if (response.getCode() == 200) {
                return response;
            }

            // 使用提供的错误码抛出异常
            throw ServiceExceptionUtil.exception(apiType.getErrorCode());
        } catch (ServiceException e) {
            // 直接抛出业务异常
            throw e;
        } catch (Exception e) {
            log.error(apiType.getErrorMessage(), e);
            return QianXunResponse.error(500, apiType.getErrorMessage() + e.getMessage());
        }
    }
}

