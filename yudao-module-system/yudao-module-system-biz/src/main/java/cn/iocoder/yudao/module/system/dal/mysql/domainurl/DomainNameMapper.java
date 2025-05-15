package cn.iocoder.yudao.module.system.dal.mysql.domainurl;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.domainurl.DomainNameDO;
import cn.iocoder.yudao.module.taiyi.dal.dataobject.domainname.DomainNameDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.taiyi.controller.admin.domainname.vo.*;

/**
 * 域名管理 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DomainNameMapper extends BaseMapperX<DomainNameDO> {

    default PageResult<DomainNameDO> selectPage(DomainNamePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DomainNameDO>()
                .likeIfPresent(DomainNameDO::getDomainName, reqVO.getDomainName())
                .eqIfPresent(DomainNameDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DomainNameDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(DomainNameDO::getOperatorId, reqVO.getOperatorId())
                .eqIfPresent(DomainNameDO::getGmtCreate, reqVO.getGmtCreate())
                .eqIfPresent(DomainNameDO::getGmtModified, reqVO.getGmtModified())
                .orderByDesc(DomainNameDO::getId));
    }

}