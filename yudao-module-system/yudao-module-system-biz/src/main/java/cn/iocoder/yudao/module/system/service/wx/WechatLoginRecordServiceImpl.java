package cn.iocoder.yudao.module.system.service.wx;

import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatLoginRecordPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatLoginRecordSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WechatLoginRecordDO;
import cn.iocoder.yudao.module.system.dal.mysql.wx.WechatLoginRecordMapper;
import cn.iocoder.yudao.module.system.domain.enums.YesOrNoEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.WECHAT_LOGIN_RECORD_NOT_EXISTS;

/**
 * 微信用户登录记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class WechatLoginRecordServiceImpl implements WechatLoginRecordService {

    @Resource
    private WechatLoginRecordMapper wechatLoginRecordMapper;

    @Override
    public Long createWechatLoginRecord(WechatLoginRecordSaveReqVO createReqVO) {
        // 插入
        WechatLoginRecordDO wechatLoginRecord = BeanUtils.toBean(createReqVO, WechatLoginRecordDO.class);
        wechatLoginRecordMapper.insert(wechatLoginRecord);
        // 返回
        return wechatLoginRecord.getId();
    }

    @Override
    public void updateWechatLoginRecord(WechatLoginRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateWechatLoginRecordExists(updateReqVO.getId());
        // 更新
        WechatLoginRecordDO updateObj = BeanUtils.toBean(updateReqVO, WechatLoginRecordDO.class);
        wechatLoginRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteWechatLoginRecord(Long id) {
        // 校验存在
        validateWechatLoginRecordExists(id);
        // 删除
        wechatLoginRecordMapper.deleteById(id);
    }

    private void validateWechatLoginRecordExists(Long id) {
        if (wechatLoginRecordMapper.selectById(id) == null) {
            throw exception(WECHAT_LOGIN_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public WechatLoginRecordDO getWechatLoginRecord(Long id) {
        return wechatLoginRecordMapper.selectById(id);
    }

    @Override
    public PageResult<WechatLoginRecordDO> getWechatLoginRecordPage(WechatLoginRecordPageReqVO pageReqVO) {
        return wechatLoginRecordMapper.selectPage(pageReqVO);
    }

    @Override
    public List<WechatLoginRecordDO> getOnlineWechatAccountsByWxIdList(List<String> wxUnionIdList) {
        // 查询数据
        if (CollectionUtils.isEmpty(wxUnionIdList)) {
            return Collections.emptyList();
        }
        return wechatLoginRecordMapper.selectList(new LambdaQueryWrapper<WechatLoginRecordDO>()
                .in(CollectionUtils.isNotEmpty(wxUnionIdList), WechatLoginRecordDO::getWxUnionId, wxUnionIdList)
                .eq(WechatLoginRecordDO::getDeleted, YesOrNoEnum.NO.getStatus())
                .eq(WechatLoginRecordDO::getIsOffline, YesOrNoEnum.NO.getStatus()));
    }

    @Override
    public List<WechatLoginRecordDO> getOnlineWechatAccountsByDomainUrl(List<String> domainList) {
        // 查询数据
        if (CollectionUtils.isEmpty(domainList)) {
            return Collections.emptyList();
        }
        return wechatLoginRecordMapper.selectList(new LambdaQueryWrapper<WechatLoginRecordDO>()
                .in(CollectionUtils.isNotEmpty(domainList), WechatLoginRecordDO::getIp, domainList)
                .eq(WechatLoginRecordDO::getDeleted, YesOrNoEnum.NO.getStatus())
                .eq(WechatLoginRecordDO::getIsOffline, YesOrNoEnum.NO.getStatus()));
    }

}