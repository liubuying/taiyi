package cn.iocoder.yudao.module.system.dal.mysql.domainurl;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.domainurl.DomainNameDO;

import cn.iocoder.yudao.module.system.domain.request.DomainNameRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 域名管理 Mapper
 *
 *
 */
@Mapper
public interface DomainNameMapper extends BaseMapperX<DomainNameDO> {

    default PageResult<DomainNameDO> selectPage(DomainNameRequest reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DomainNameDO>()
                .likeIfPresent(DomainNameDO::getDomainName, reqVO.getDomainName())
                .eqIfPresent(DomainNameDO::getStatus, reqVO.getDomainStatus())
                .eqIfPresent(DomainNameDO::getCompanyId, reqVO.getCompanyId())
                .orderByDesc(DomainNameDO::getId));
    }

    default List<DomainNameDO> selectList(DomainNameRequest reqVO) {
        return selectList(new LambdaQueryWrapperX<DomainNameDO>()
                .likeIfPresent(DomainNameDO::getDomainName, reqVO.getDomainName())
                .eqIfPresent(DomainNameDO::getStatus, reqVO.getDomainStatus())
                .eqIfPresent(DomainNameDO::getCompanyId, reqVO.getCompanyId())
                .eq(DomainNameDO::getDeleted, 0));
    }

}