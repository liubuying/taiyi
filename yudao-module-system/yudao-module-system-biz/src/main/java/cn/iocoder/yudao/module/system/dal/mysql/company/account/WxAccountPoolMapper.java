package cn.iocoder.yudao.module.system.dal.mysql.company.account;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.company.account.WxAccountPoolDO;
import cn.iocoder.yudao.module.system.domain.request.WxAccountPoolRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Mapper;


/**
 * 微信池账号 Mapper
 *
 *
 */
@Mapper
public interface WxAccountPoolMapper extends BaseMapperX<WxAccountPoolDO> {

    default PageResult<WxAccountPoolDO> selectPage(WxAccountPoolRequest request) {
        return selectPage(request, new LambdaQueryWrapperX<WxAccountPoolDO>()
                .eqIfPresent(WxAccountPoolDO::getCompanyId, request.getCompanyId())
                .eqIfPresent(WxAccountPoolDO::getAccountType, request.getAccountType())
                .eqIfPresent(WxAccountPoolDO::getUnionId, request.getUnionId())
                .eqIfPresent(WxAccountPoolDO::getPhone, request.getPhone())
                .eqIfPresent(WxAccountPoolDO::getEmail, request.getEmail())
                .likeIfPresent(WxAccountPoolDO::getNickName, request.getNickName())
                .eqIfPresent(WxAccountPoolDO::getOperatorId, request.getOperatorId())
                .eqIfPresent(WxAccountPoolDO::getIsExpired, request.getIsExpired())
                .eqIfPresent(WxAccountPoolDO::getDeleted, 0)
                .isNotNull(CollectionUtils.isNotEmpty(request.getWxUnionIdList()), WxAccountPoolDO::getUnionId)
                .in(WxAccountPoolDO::getUnionId, request.getWxUnionIdList())
                .orderByDesc(WxAccountPoolDO::getId));
    }

}