package cn.iocoder.yudao.module.system.dal.mysql.domainurl;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.domainurl.DomainWxAccountRelationDO;
import cn.iocoder.yudao.module.taiyi.dal.dataobject.domainwxaccountrelation.DomainWxAccountRelationDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.taiyi.controller.admin.domainwxaccountrelation.vo.*;

/**
 * 域名与微信账号关系 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DomainWxAccountRelationMapper extends BaseMapperX<DomainWxAccountRelationDO> {

    default PageResult<DomainWxAccountRelationDO> selectPage(DomainWxAccountRelationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DomainWxAccountRelationDO>()
                .eqIfPresent(DomainWxAccountRelationDO::getUnionId, reqVO.getUnionId())
                .eqIfPresent(DomainWxAccountRelationDO::getDomainId, reqVO.getDomainId())
                .eqIfPresent(DomainWxAccountRelationDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DomainWxAccountRelationDO::getOperatorId, reqVO.getOperatorId())
                .eqIfPresent(DomainWxAccountRelationDO::getGmtCreate, reqVO.getGmtCreate())
                .eqIfPresent(DomainWxAccountRelationDO::getGmtModified, reqVO.getGmtModified())
                .orderByDesc(DomainWxAccountRelationDO::getId));
    }

}