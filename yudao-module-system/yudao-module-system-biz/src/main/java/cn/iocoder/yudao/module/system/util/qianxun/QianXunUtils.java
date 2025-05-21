package cn.iocoder.yudao.module.system.util.qianxun;

import java.util.List;

import cn.iocoder.yudao.module.system.util.qianxun.qianXunModel.*;

public interface QianXunUtils {

// ==================== 登录相关API =========================

    /**
     * 获取登录二维码
     *
     * @param ip 服务器域名
     * @return 响应对象，包含二维码信息
     */
    static QianXunResponse<QianXunQrCode> getLoginQrCode(String ip){
        return null;
    }

    /**
     * 获取登录状态
     *
     * @param ip 服务器域名
     * @return 响应对象，包含登录状态
     */
    static QianXunResponse<QianXunLoginStatus> getLoginStatus(String ip){
        return null;
    }

    /**
     * 获取微信列表
     *
     * @param ip 服务器域名
     * @return 响应对象，包含微信列表
     */
    static QianXunResponse<List<QianXunLoginStatus>> getWeChatList(String ip){
        return null;
    }

    /**
     * 结束微信进程
     *
     * @param ip 服务器域名
     * @return 响应对象
     */
    static QianXunResponse<Object> killWeChat(String ip){
        return null;
    }


    // 暂时不使用
    /**
     * 修改微信版本号
     *
     * @param ip 服务器域名
     * @param version 版本号
     * @return 响应对象
     */
    static QianXunResponse<Object> editVersion(String ip, String version){
        return null;
    }

    /**
     * 设置下载图片时间
     *
     * @param ip 服务器域名
     * @param time 时间(单位：秒)
     * @return 响应对象
     */
    static QianXunResponse<Object> setDownloadImage(String ip, Integer time){
        return null;
    }

    // ==================== 获取信息相关API =========================
    /**
     * 获取个人信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含个人信息
     */
    static QianXunResponse<QianXunInfo> getSelfInfo(String ip, String wxId){
        return null;
    }

    /**
     * 获取好友列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含好友列表
     */
    static QianXunResponse<List<QianXunInfo>> getFriendList(String ip, String wxId){
        return null;
    }

    /**
     * 获取群聊列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含群聊列表
     */
    static QianXunResponse<List<QianXunInfoGroup>> getGroupList(String ip, String wxId){
        return null;
    }

    /**
     * 获取公众号列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含公众号列表
     */
    static QianXunResponse<List<QianXunInfo>> getPublicList(String ip, String wxId){
        return null;
    }

    /**
     * 获取标签列表
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @return 响应对象，包含标签列表
     */
    static QianXunResponse<List<Object>> getLabelList(String ip, String wxId){
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
    static QianXunResponse<List<QianXunInfoGroupMember>> getMemberList(String ip, String wxId, String groupWxId){
        return null;
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
    static QianXunResponse<QianXunInfoGroupMember> getMemberNick(String ip, String wxId, String groupWxId, String toWxId){
        return null;
    }

    /**
     * 查询对象信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @return 响应对象
     */
    static QianXunResponse<QianXunInfo> queryObj(String ip, String wxId, String toWxId){
        return null;
    }

    /**
     * 查询陌生人信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param towxId 目标wxId
     * @return 响应对象
     */
    static QianXunResponse<QianXunInfo> queryNewFriend(String ip, String wxId, String towxId){
        return null;
    }

    /**
     * 查询群聊信息
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param groupwxId 群wxId
     * @return 响应对象
     */
    static QianXunResponse<QianXunInfoGroup> queryGroup(String ip, String wxId, String groupwxId){
        return null;
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
    static QianXunResponse<Object> agreeFriendReq(String ip, String wxId, String v3, String v4){
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
    static QianXunResponse<Object> addFriendByV3(String ip, String wxId, String v3, String message){
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
    static QianXunResponse<Object> addFriendByGroupWxid(String ip, String wxId, String groupWxId, String toWxId, String message){
        return null;
    }

    /**
     * 删除好友
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @return 响应对象
     */
    static QianXunResponse<Object> delFriend(String ip, String wxId, String toWxId){
        return null;
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
    static QianXunResponse<Object> editObjRemark(String ip, String wxId, String toWxId, String remark){
        return null;
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
    static QianXunResponse<Object> quitGroup(String ip, String wxId, String groupWxId){
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
    static QianXunResponse<Object> createGroup(String ip, String wxId, String wxIds){
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
    static QianXunResponse<Object> addMembers(String ip, String wxId, String groupWxId, String toWxIds){
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
    static QianXunResponse<Object> inviteMembers(String ip, String wxId, String groupWxId, String toWxIds){
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
    static QianXunResponse<Object> delMembers(String ip, String wxId, String groupWxId, String toWxIds){
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
    static QianXunResponse<Object> editSelfMemberNick(String ip, String wxId, String groupWxId, String nick){
        return null;
    }
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
    static QianXunResponse<QianXunMessage> sendText(String ip, String wxId, String toWxId, String content){
        return null;
    }

    /**
     * 发送文本消息2
     *
     * @param ip 服务器域名
     * @param wxId 微信ID
     * @param toWxId 目标wxId
     * @param content 消息内容
     * @return 响应对象
     */
    static QianXunResponse<QianXunMessage> sendText2(String ip, String wxId, String toWxId, String content){
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
    static QianXunResponse<QianXunMessage> sendReferText(String ip, String wxId, String toWxId, String content, String msgId){
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
    static QianXunResponse<QianXunMessage> sendImage(String ip, String wxId, String toWxId, String path){
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
    static QianXunResponse<QianXunMessage> sendFile(String ip, String wxId, String toWxId, String path){
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
    static QianXunResponse<QianXunMessage> sendGif(String ip, String wxId, String toWxId, String path){
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
    static QianXunResponse<QianXunMessage> sendShareUrl(String ip, String wxId, String toWxId, String title, String desc, String url, String imgUrl){
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
    static QianXunResponse<QianXunMessage> sendApplet(String ip, String wxId, String toWxId, String applet){
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
    static QianXunResponse<QianXunMessage> sendMusic(String ip, String wxId, String toWxId, String musicXml){
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
    static QianXunResponse<QianXunMessage> sendChatLog(String ip, String wxId, String toWxId, String title, String dataList){
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
    static QianXunResponse<QianXunMessage> sendCard(String ip, String wxId, String toWxId, String content, String nickName){
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
    static QianXunResponse<QianXunMessage> sendXml(String ip, String wxId, String toWxId, String xml){
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
    static QianXunResponse<String> decryptImage(String ip, String datPath){
        return null;
    }

    /**
     * 微信状态检测
     *
     * @param ip 服务器域名
     * @return 响应对象
     */
    static QianXunResponse<Object> checkWeChat(String ip){
        return null;
    }

    /**
     * 查询授权信息
     *
     * @param ip 服务器域名
     * @return 响应对象
     */
    static QianXunResponse<Object> getAuthInfo(String ip){
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
    static QianXunResponse<Object> confirmTrans(String ip, String wxId, String transferId){
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
    static QianXunResponse<Object> returnTrans(String ip, String wxId, String transferId){
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
    static QianXunResponse<Object> openBrowser(String ip, String wxId, String url){
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
    static QianXunResponse<Object> runCloudFunction(String ip, String wxId, String hookName, String json){
        return null;
    }
}
