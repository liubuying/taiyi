package cn.iocoder.yudao.module.system.wrapper.qianxun;

import java.util.List;

import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.*;

public interface QXunWrapper {

// ==================== 登录相关API =========================

    /**
     * 获取登录二维码
     *
     * @param ip 服务器域名
     * @return 响应对象，包含二维码信息
     */
    QianXunResponse<QianXunQrCode> getLoginQrCode(String ip);

    /**
     * 获取登录状态
     *
     * @param ip 服务器域名
     * @param prot 服务器端口号
     * @return 响应对象，包含登录状态
     */
    QianXunResponse<QianXunLoginStatus> getLoginStatus(String ip, String prot);

    /**
     * 获取微信列表
     *
     * @param ip 服务器域名
     * @return 响应对象，包含微信列表
     */
    QianXunResponse<List<QianXunLoginStatus>> getWeChatList(String ip);

    /**
     * 结束微信进程
     *
     * @param ip 服务器域名
     * @return 响应对象
     */
    QianXunResponse<Object> killWeChat(String ip, String prot);

    /**
     * 修改微信版本号
     *
     * @param ip 服务器域名
     * @param version 版本号
     * @return 响应对象
     */
     QianXunResponse<Object> editVersion(String ip, String version);

    /**
     * 设置下载图片时间
     *
     * @param ip 服务器域名
     * @param time 时间(单位：秒)
     * @return 响应对象
     */
     QianXunResponse<Object> setDownloadImage(String ip, Integer time);

    // ==================== 获取信息相关API =========================
    /**
     * 获取个人信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含个人信息
     */
     QianXunResponse<QianXunInfo> getSelfInfo(String ip, String wxId);

    /**
     * 获取好友列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含好友列表
     */
     QianXunResponse<List<QianXunInfoFriend>> getFriendList(String ip, String wxId);

    /**
     * 获取群聊列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含群聊列表
     */
     QianXunResponse<List<QianXunInfoGroup>> getGroupList(String ip, String wxId);

    /**
     * 获取公众号列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含公众号列表
     */
     QianXunResponse<List<QianXunInfo>> getPublicList(String ip, String wxId);

    /**
     * 获取标签列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含标签列表
     */
     QianXunResponse<List<Object>> getLabelList(String ip, String wxId);

    /**
     * 获取群成员列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @return 响应对象，包含群成员列表及其群昵称
     */
     QianXunResponse<List<QianXunInfoGroupMember>> getMemberList(String ip, String wxId, String groupWxId);

    /**
     * 获取群成员昵称
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param toWxId 目标wxId
     * @return 响应对象
     */
     QianXunResponse<QianXunInfoGroupMember> getMemberNick(String ip, String wxId, String groupWxId, String toWxId);

    /**
     * 查询对象信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @return 响应对象
     */
     QianXunResponse<QianXunInfo> queryObj(String ip, String wxId, String toWxId);

    /**
     * 查询陌生人信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toId 目标wxId
     * @return 响应对象
     */
    QianXunResponse<QianXunInfoNewFriend> queryNewFriend(String ip, String wxId, String toId);

    /**
     * 查询群聊信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupwxId 群wxId
     * @return 响应对象
     */
     QianXunResponse<QianXunInfoGroup> queryGroup(String ip, String wxId, String groupwxId);

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
     QianXunResponse<Object> agreeFriendReq(String ip, String wxId, String v3, String v4);

    /**
     * 添加好友(通过v3)
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param v3 v3数据
     * @param message 验证消息
     * @return 响应对象
     */
     QianXunResponse<Object> addFriendByV3(String ip, String wxId, String v3, String message);

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
     QianXunResponse<Object> addFriendByGroupWxid(String ip, String wxId, String groupWxId, String toWxId, String message);

    /**
     * 删除好友
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @return 响应对象
     */
     QianXunResponse<QianXunLoginStatus> delFriend(String ip, String wxId, String toWxId);

    /**
     * 修改对象备注
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param remark 备注
     * @return 响应对象
     */
     QianXunResponse<Object> editObjRemark(String ip, String wxId, String toWxId, String remark);

    // ==================== 群聊管理相关API =========================

    /**
     * 退出群聊
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @return 响应对象
     */
     QianXunResponse<Object> quitGroup(String ip, String wxId, String groupWxId);

    /**
     * 创建群聊
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param wxIds 目标wxId列表，用英文逗号分隔
     * @return 响应对象
     */
     QianXunResponse<Object> createGroup(String ip, String wxId, String wxIds);

    /**
     * 添加群成员
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param toWxIds 目标wxId列表，用英文逗号分隔
     * @return 响应对象
     */
     QianXunResponse<Object> addMembers(String ip, String wxId, String groupWxId, String toWxIds);

    /**
     * 邀请群成员
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param toWxIds 目标wxId列表，用英文逗号分隔
     * @return 响应对象
     */
     QianXunResponse<Object> inviteMembers(String ip, String wxId, String groupWxId, String toWxIds);

    /**
     * 移除群成员
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param toWxIds 目标wxId列表，用英文逗号分隔
     * @return 响应对象
     */
     QianXunResponse<Object> delMembers(String ip, String wxId, String groupWxId, String toWxIds);

    /**
     * 修改自己群昵称
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupWxId 群wxId
     * @param nick 昵称
     * @return 响应对象
     */
     QianXunResponse<Object> editSelfMemberNick(String ip, String wxId, String groupWxId, String nick);
    // ==================== 发送消息相关API =========================

    /**
     * 发送文本消息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param content 消息内容
     * @return 响应对象
     */
     QianXunResponse<QianXunMessage> sendText(String ip, String wxId, String toWxId, String content);

    /**
     * 发送文本消息2
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param content 消息内容
     * @return 响应对象
     */
     QianXunResponse<QianXunMessage> sendText2(String ip, String wxId, String toWxId, String content);

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
     QianXunResponse<QianXunMessage> sendReferText(String ip, String wxId, String toWxId, String content, String msgId);

    /**
     * 发送图片
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param path 图片路径
     * @return 响应对象
     */
     QianXunResponse<QianXunMessage> sendImage(String ip, String wxId, String toWxId, String path);

    /**
     * 发送文件
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param path 文件路径
     * @return 响应对象
     */
     QianXunResponse<QianXunMessage> sendFile(String ip, String wxId, String toWxId, String path);

    /**
     * 发送动态表情
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param path gif路径
     * @return 响应对象
     */
     QianXunResponse<QianXunMessage> sendGif(String ip, String wxId, String toWxId, String path);

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
     QianXunResponse<QianXunMessage> sendShareUrl(String ip, String wxId, String toWxId, String title, String desc, String url, String imgUrl);

    /**
     * 发送小程序
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param applet 小程序xml
     * @return 响应对象
     */
     QianXunResponse<QianXunMessage> sendApplet(String ip, String wxId, String toWxId, String applet);

    /**
     * 发送音乐分享
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param musicXml 音乐分享xml
     * @return 响应对象
     */
     QianXunResponse<QianXunMessage> sendMusic(String ip, String wxId, String toWxId, String musicXml);

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
     QianXunResponse<QianXunMessage> sendChatLog(String ip, String wxId, String toWxId, String title, String dataList);

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
     QianXunResponse<QianXunMessage> sendCard(String ip, String wxId, String toWxId, String content, String nickName);

    /**
     * 发送XML
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param xml XML内容
     * @return 响应对象
     */
     QianXunResponse<QianXunMessage> sendXml(String ip, String wxId, String toWxId, String xml);

    // ==================== 其他操作API =========================

    /**
     * 解密dat图片
     *
     * @param ip 服务器域名
     * @param datPath dat图片路径
     * @return 响应对象
     */
     QianXunResponse<String> decryptImage(String ip, String datPath);

    /**
     * 微信状态检测
     *
     * @param ip 服务器域名
     * @return 响应对象
     */
     QianXunResponse<Object> checkWeChat(String ip);

    /**
     * 查询授权信息
     *
     * @param ip 服务器域名
     * @return 响应对象
     */
     QianXunResponse<Object> getAuthInfo(String ip);

    /**
     * 确认收款
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param transferId 转账ID
     * @return 响应对象
     */
     QianXunResponse<Object> confirmTrans(String ip, String wxId, String transferId);

    /**
     * 退还收款
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param transferId 转账ID
     * @return 响应对象
     */
     QianXunResponse<Object> returnTrans(String ip, String wxId, String transferId);

    /**
     * 打开浏览器
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param url 链接地址
     * @return 响应对象
     */
     QianXunResponse<Object> openBrowser(String ip, String wxId, String url);

    /**
     * 执行云函数
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param hookName 云函数名
     * @param json JSON参数
     * @return 响应对象
     */
     QianXunResponse<Object> runCloudFunction(String ip, String wxId, String hookName, String json);
}
