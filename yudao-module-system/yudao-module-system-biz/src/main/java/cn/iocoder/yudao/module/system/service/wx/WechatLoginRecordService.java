package cn.iocoder.yudao.module.system.service.wx;

import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatLoginRecordPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatLoginRecordSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WechatLoginRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.List;

/**
 * 微信用户登录记录 Service 接口
 *
 * @author 芋道源码
 */
public interface WechatLoginRecordService {

    /**
     * 创建微信用户登录记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWechatLoginRecord(@Valid WechatLoginRecordSaveReqVO createReqVO);

    /**
     * 更新微信用户登录记录
     *
     * @param updateReqVO 更新信息
     */
    void updateWechatLoginRecord(@Valid WechatLoginRecordSaveReqVO updateReqVO);

    /**
     * 删除微信用户登录记录
     *
     * @param id 编号
     */
    void deleteWechatLoginRecord(Long id);

    /**
     * 获得微信用户登录记录
     *
     * @param id 编号
     * @return 微信用户登录记录
     */
    WechatLoginRecordDO getWechatLoginRecord(Long id);

    /**
     * 获得微信用户登录记录分页
     *
     * @param pageReqVO 分页查询
     * @return 微信用户登录记录分页
     */
    PageResult<WechatLoginRecordDO> getWechatLoginRecordPage(WechatLoginRecordPageReqVO pageReqVO);

    /**
     * 根据微信ID获取已登录微信账号列表
     * @param wxUnionIdList
     * @return
     */
    List<WechatLoginRecordDO> getOnlineWechatAccountsByWxIdList(List<String> wxUnionIdList);

    /**
     * 根据IP获取已登录微信账号列表
     * @param domainList
     * @return
     */
    List<WechatLoginRecordDO> getOnlineWechatAccountsByDomainUrl(List<String> domainList);
}