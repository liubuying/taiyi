package cn.iocoder.yudao.module.system.enums.qianxun;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 千寻API类型枚举
 */
@Getter
@AllArgsConstructor
public enum QianXunApiTypeEnum {

    // 获取信息相关API
    GET_SELF_INFO("getSelfInfo", "获取个人信息失败"),
    GET_FRIEND_LIST("getFriendList", "获取好友列表失败"),
    GET_GROUP_LIST("getGroupList", "获取群聊列表失败"),
    GET_PUBLIC_LIST("getPublicList", "获取公众号列表失败"),
    GET_LABEL_LIST("getLabelList", "获取标签列表失败"),
    GET_MEMBER_LIST("getMemberList", "获取群成员列表失败"),
    GET_MEMBER_NICK("getMemberNick", "获取群成员昵称失败"),
    QUERY_OBJ("queryObj", "查询对象信息失败"),
    QUERY_NEW_FRIEND("queryNewFriend", "查询陌生人信息失败"),
    QUERY_GROUP("queryGroup", "查询群聊信息失败"),

    // 发送消息相关API
    SEND_MESSAGE("sendMessage", "发送消息失败"),
    SEND_TEXT("sendText", "发送文本消息失败"),
    SEND_TEXT2("sendText2", "发送文本消息2失败"),
    SEND_REFER_TEXT("sendReferText", "发送引用回复文本失败"),
    SEND_IMAGE("sendImage", "发送图片失败"),
    SEND_FILE("sendFile", "发送文件失败"),
    SEND_GIF("sendGif", "发送动态表情失败"),
    SEND_SHARE_URL("sendShareUrl", "发送分享链接失败"),
    SEND_APPLET("sendApplet", "发送小程序失败"),
    SEND_MUSIC("sendMusic", "发送音乐分享失败"),
    SEND_CHAT_LOG("sendChatLog", "发送聊天记录失败"),
    SEND_CARD("sendCard", "发送名片消息失败"),
    SEND_XML("sendXml", "发送XML失败"),

    // 登录相关API
    GET_LOGIN_QRCODE("getLoginQrCode", "获取登录二维码失败"),
    CHECK_LOGIN_STATUS("checkLoginStatus", "检查登录状态失败"),
    GET_WECHAT_LIST("getWeChatList", "获取微信列表失败"),
    GET_LOGIN_STATUS("getLoginStatus", "获取登录状态失败"),
    KILL_WECHAT("killWeChat", "结束微信进程失败"),

    // 好友管理相关API
    AGREE_FRIEND_REQ("agreeFriendReq", "同意好友请求失败"),
    ADD_FRIEND_BY_V3("addFriendByV3", "通过v3添加好友失败"),
    ADD_FRIEND_BY_GROUP_WXID("addFriendByGroupWxid", "通过群wxid添加好友失败"),
    DEL_FRIEND("delFriend", "删除好友失败"),
    EDIT_OBJ_REMARK("editObjRemark", "修改对象备注失败"),

    // 群聊管理相关API
    QUIT_GROUP("quitGroup", "退出群聊失败"),
    CREATE_GROUP("createGroup", "创建群聊失败"),
    ADD_MEMBERS("addMembers", "添加群成员失败"),
    INVITE_MEMBERS("inviteMembers", "邀请群成员失败"),
    DEL_MEMBERS("delMembers", "移除群成员失败"),
    EDIT_SELF_MEMBER_NICK("editSelfMemberNick", "修改自己群昵称失败"),

    // 其他操作API
    DECRYPT_IMAGE("decryptImage", "解密dat图片失败"),
    CHECK_WECHAT("checkWeChat", "微信状态检测失败"),
    GET_AUTH_INFO("getAuthInfo", "查询授权信息失败"),
    CONFIRM_TRANS("confirmTrans", "确认收款失败"),
    RETURN_TRANS("returnTrans", "退还收款失败"),
    OPEN_BROWSER("openBrowser", "打开浏览器失败"),
    RUN_CLOUD_FUNCTION("runCloudFunction", "执行云函数失败"),
    EDIT_VERSION("editVersion", "修改微信版本号失败"),
    SET_DOWNLOAD_IMAGE("setDownloadImage", "设置下载图片时间失败");

    /**
     * API请求类型
     */
    private final String type;

    /**
     * 错误消息
     */
    private final String errorMessage;

    /**
     * 获取对应的错误码对象
     *
     * @return 错误码对象
     */
    public ErrorCode getErrorCode() {
        QianXunErrorCodeEnum errorCodeEnum;
        switch (this) {
            // 获取信息相关API
            case GET_SELF_INFO:
                errorCodeEnum = QianXunErrorCodeEnum.GET_SELF_INFO_ERROR;
                break;
            case GET_FRIEND_LIST:
                errorCodeEnum = QianXunErrorCodeEnum.GET_FRIEND_LIST_ERROR;
                break;
            case GET_GROUP_LIST:
                errorCodeEnum = QianXunErrorCodeEnum.GET_GROUP_LIST_ERROR;
                break;
            case GET_PUBLIC_LIST:
                errorCodeEnum = QianXunErrorCodeEnum.GET_PUBLIC_LIST_ERROR;
                break;
            case GET_LABEL_LIST:
                errorCodeEnum = QianXunErrorCodeEnum.GET_LABEL_LIST_ERROR;
                break;
            case GET_MEMBER_LIST:
                errorCodeEnum = QianXunErrorCodeEnum.GET_MEMBER_LIST_ERROR;
                break;
            case GET_MEMBER_NICK:
                errorCodeEnum = QianXunErrorCodeEnum.GET_MEMBER_NICK_ERROR;
                break;
            case QUERY_OBJ:
                errorCodeEnum = QianXunErrorCodeEnum.QUERY_OBJ_ERROR;
                break;
            case QUERY_NEW_FRIEND:
                errorCodeEnum = QianXunErrorCodeEnum.QUERY_NEW_FRIEND_ERROR;
                break;
            case QUERY_GROUP:
                errorCodeEnum = QianXunErrorCodeEnum.QUERY_GROUP_ERROR;
                break;

            // 发送消息相关API
            case SEND_MESSAGE:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_MESSAGE_ERROR;
                break;
            case SEND_TEXT:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_TEXT_ERROR;
                break;
            case SEND_TEXT2:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_TEXT2_ERROR;
                break;
            case SEND_REFER_TEXT:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_REFER_TEXT_ERROR;
                break;
            case SEND_IMAGE:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_IMAGE_ERROR;
                break;
            case SEND_FILE:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_FILE_ERROR;
                break;
            case SEND_GIF:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_GIF_ERROR;
                break;
            case SEND_SHARE_URL:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_SHARE_URL_ERROR;
                break;
            case SEND_APPLET:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_APPLET_ERROR;
                break;
            case SEND_MUSIC:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_MUSIC_ERROR;
                break;
            case SEND_CHAT_LOG:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_CHAT_LOG_ERROR;
                break;
            case SEND_CARD:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_CARD_ERROR;
                break;
            case SEND_XML:
                errorCodeEnum = QianXunErrorCodeEnum.SEND_XML_ERROR;
                break;

            // 登录相关API
            case GET_LOGIN_QRCODE:
                errorCodeEnum = QianXunErrorCodeEnum.GET_LOGIN_QRCODE_ERROR;
                break;
            case CHECK_LOGIN_STATUS:
                errorCodeEnum = QianXunErrorCodeEnum.CHECK_LOGIN_STATUS_ERROR;
                break;
            case GET_WECHAT_LIST:
                errorCodeEnum = QianXunErrorCodeEnum.GET_WECHAT_LIST_ERROR;
                break;
            case GET_LOGIN_STATUS:
                errorCodeEnum = QianXunErrorCodeEnum.GET_LOGIN_STATUS_ERROR;
                break;
            case EDIT_VERSION:
                errorCodeEnum = QianXunErrorCodeEnum.EDIT_VERSION_ERROR;
                break;
            case SET_DOWNLOAD_IMAGE:
                errorCodeEnum = QianXunErrorCodeEnum.SET_DOWNLOAD_IMAGE_ERROR;
                break;

            // 好友管理相关API
            case AGREE_FRIEND_REQ:
                errorCodeEnum = QianXunErrorCodeEnum.AGREE_FRIEND_REQ_ERROR;
                break;
            case ADD_FRIEND_BY_V3:
                errorCodeEnum = QianXunErrorCodeEnum.ADD_FRIEND_BY_V3_ERROR;
                break;
            case ADD_FRIEND_BY_GROUP_WXID:
                errorCodeEnum = QianXunErrorCodeEnum.ADD_FRIEND_BY_GROUP_WXID_ERROR;
                break;
            case DEL_FRIEND:
                errorCodeEnum = QianXunErrorCodeEnum.DEL_FRIEND_ERROR;
                break;
            case EDIT_OBJ_REMARK:
                errorCodeEnum = QianXunErrorCodeEnum.EDIT_OBJ_REMARK_ERROR;
                break;

            // 群聊管理相关API
            case QUIT_GROUP:
                errorCodeEnum = QianXunErrorCodeEnum.QUIT_GROUP_ERROR;
                break;
            case CREATE_GROUP:
                errorCodeEnum = QianXunErrorCodeEnum.CREATE_GROUP_ERROR;
                break;
            case ADD_MEMBERS:
                errorCodeEnum = QianXunErrorCodeEnum.ADD_MEMBERS_ERROR;
                break;
            case INVITE_MEMBERS:
                errorCodeEnum = QianXunErrorCodeEnum.INVITE_MEMBERS_ERROR;
                break;
            case DEL_MEMBERS:
                errorCodeEnum = QianXunErrorCodeEnum.DEL_MEMBERS_ERROR;
                break;
            case EDIT_SELF_MEMBER_NICK:
                errorCodeEnum = QianXunErrorCodeEnum.EDIT_SELF_MEMBER_NICK_ERROR;
                break;

            // 其他操作API
            case KILL_WECHAT:
                errorCodeEnum = QianXunErrorCodeEnum.KILL_WECHAT_ERROR;
                break;
            case DECRYPT_IMAGE:
                errorCodeEnum = QianXunErrorCodeEnum.DECRYPT_IMAGE_ERROR;
                break;
            case CHECK_WECHAT:
                errorCodeEnum = QianXunErrorCodeEnum.CHECK_WECHAT_ERROR;
                break;
            case GET_AUTH_INFO:
                errorCodeEnum = QianXunErrorCodeEnum.GET_AUTH_INFO_ERROR;
                break;
            case CONFIRM_TRANS:
                errorCodeEnum = QianXunErrorCodeEnum.CONFIRM_TRANS_ERROR;
                break;
            case RETURN_TRANS:
                errorCodeEnum = QianXunErrorCodeEnum.RETURN_TRANS_ERROR;
                break;
            case OPEN_BROWSER:
                errorCodeEnum = QianXunErrorCodeEnum.OPEN_BROWSER_ERROR;
                break;
            case RUN_CLOUD_FUNCTION:
                errorCodeEnum = QianXunErrorCodeEnum.RUN_CLOUD_FUNCTION_ERROR;
                break;

            // 默认错误码
            default:
                errorCodeEnum = QianXunErrorCodeEnum.API_RESULT_ERROR;
        }
        return errorCodeEnum.toErrorCode();
    }
}
