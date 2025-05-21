package cn.iocoder.yudao.module.system.enums.qianxun;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 千寻API错误码枚举
 */
@Getter
@AllArgsConstructor
public enum QianXunErrorCodeEnum {

    // 通用错误
    API_RESULT_ERROR(1_002_068_000, "千寻框架接口响应异常"),

    // 获取信息相关错误 (1_002_068_001 - 1_002_068_050)
    GET_SELF_INFO_ERROR(1_002_068_001, "获取个人信息失败"),
    GET_FRIEND_LIST_ERROR(1_002_068_002, "获取好友列表失败"),
    GET_GROUP_LIST_ERROR(1_002_068_003, "获取群聊列表失败"),
    GET_WECHAT_LIST_ERROR(1_002_068_004, "获取微信列表失败"),
    GET_PUBLIC_LIST_ERROR(1_002_068_005, "获取公众号列表失败"),
    GET_LABEL_LIST_ERROR(1_002_068_006, "获取标签列表失败"),
    GET_MEMBER_LIST_ERROR(1_002_068_007, "获取群成员列表失败"),
    GET_MEMBER_NICK_ERROR(1_002_068_008, "获取群成员昵称失败"),
    QUERY_OBJ_ERROR(1_002_068_009, "查询对象信息失败"),
    QUERY_NEW_FRIEND_ERROR(1_002_068_010, "查询陌生人信息失败"),
    QUERY_GROUP_ERROR(1_002_068_011, "查询群聊信息失败"),

    // 登录相关错误 (1_002_068_051 - 1_002_068_100)
    GET_LOGIN_QRCODE_ERROR(1_002_068_051, "获取登录二维码失败"),
    CHECK_LOGIN_STATUS_ERROR(1_002_068_052, "检查登录状态失败"),
    GET_LOGIN_STATUS_ERROR(1_002_068_053, "获取登录状态失败"),
    EDIT_VERSION_ERROR(1_002_068_054, "修改微信版本号失败"),
    SET_DOWNLOAD_IMAGE_ERROR(1_002_068_055, "设置下载图片时间失败"),

    // 消息相关错误 (1_002_068_101 - 1_002_068_150)
    SEND_MESSAGE_ERROR(1_002_068_101, "发送消息失败"),
    SEND_TEXT_ERROR(1_002_068_102, "发送文本消息失败"),
    SEND_TEXT2_ERROR(1_002_068_103, "发送文本消息2失败"),
    SEND_REFER_TEXT_ERROR(1_002_068_104, "发送引用回复文本失败"),
    SEND_IMAGE_ERROR(1_002_068_105, "发送图片失败"),
    SEND_FILE_ERROR(1_002_068_106, "发送文件失败"),
    SEND_GIF_ERROR(1_002_068_107, "发送动态表情失败"),
    SEND_SHARE_URL_ERROR(1_002_068_108, "发送分享链接失败"),
    SEND_APPLET_ERROR(1_002_068_109, "发送小程序失败"),
    SEND_MUSIC_ERROR(1_002_068_110, "发送音乐分享失败"),
    SEND_CHAT_LOG_ERROR(1_002_068_111, "发送聊天记录失败"),
    SEND_CARD_ERROR(1_002_068_112, "发送名片消息失败"),
    SEND_XML_ERROR(1_002_068_113, "发送XML失败"),

    // 好友管理相关错误 (1_002_068_151 - 1_002_068_200)
    AGREE_FRIEND_REQ_ERROR(1_002_068_151, "同意好友请求失败"),
    ADD_FRIEND_BY_V3_ERROR(1_002_068_152, "通过v3添加好友失败"),
    ADD_FRIEND_BY_GROUP_WXID_ERROR(1_002_068_153, "通过群wxid添加好友失败"),
    DEL_FRIEND_ERROR(1_002_068_154, "删除好友失败"),
    EDIT_OBJ_REMARK_ERROR(1_002_068_155, "修改对象备注失败"),

    // 群聊管理相关错误 (1_002_068_201 - 1_002_068_250)
    QUIT_GROUP_ERROR(1_002_068_201, "退出群聊失败"),
    CREATE_GROUP_ERROR(1_002_068_202, "创建群聊失败"),
    ADD_MEMBERS_ERROR(1_002_068_203, "添加群成员失败"),
    INVITE_MEMBERS_ERROR(1_002_068_204, "邀请群成员失败"),
    DEL_MEMBERS_ERROR(1_002_068_205, "移除群成员失败"),
    EDIT_SELF_MEMBER_NICK_ERROR(1_002_068_206, "修改自己群昵称失败"),

    // 其他操作错误 (1_002_068_251 - 1_002_068_300)
    KILL_WECHAT_ERROR(1_002_068_251, "结束微信进程失败"),
    DECRYPT_IMAGE_ERROR(1_002_068_252, "解密dat图片失败"),
    CHECK_WECHAT_ERROR(1_002_068_253, "微信状态检测失败"),
    GET_AUTH_INFO_ERROR(1_002_068_254, "查询授权信息失败"),
    CONFIRM_TRANS_ERROR(1_002_068_255, "确认收款失败"),
    RETURN_TRANS_ERROR(1_002_068_256, "退还收款失败"),
    OPEN_BROWSER_ERROR(1_002_068_257, "打开浏览器失败"),
    RUN_CLOUD_FUNCTION_ERROR(1_002_068_258, "执行云函数失败");

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误描述
     */
    private final String message;

    /**
     * 转换为ErrorCode对象
     *
     * @return ErrorCode对象
     */
    public ErrorCode toErrorCode() {
        return new ErrorCode(code, message);
    }
}