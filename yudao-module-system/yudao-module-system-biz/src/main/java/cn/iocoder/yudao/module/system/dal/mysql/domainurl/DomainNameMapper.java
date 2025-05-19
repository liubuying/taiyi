package cn.iocoder.yudao.module.system.dal.mysql.domainurl;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.domainurl.DomainNameDO;

import org.apache.ibatis.annotations.Mapper;


/**
 * 域名管理 Mapper
 *
 *
 */
@Mapper
public interface DomainNameMapper extends BaseMapperX<DomainNameDO> {

    /*default PageResult<DomainNameDO> selectPage(DomainNamePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DomainNameDO>()
                .likeIfPresent(DomainNameDO::getDomainName, reqVO.getDomainName())
                .eqIfPresent(DomainNameDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DomainNameDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(DomainNameDO::getOperatorId, reqVO.getOperatorId())
                .eqIfPresent(DomainNameDO::getGmtCreate, reqVO.getGmtCreate())
                .eqIfPresent(DomainNameDO::getGmtModified, reqVO.getGmtModified())
                .orderByDesc(DomainNameDO::getId));
    }*/

}