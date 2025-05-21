package cn.iocoder.yudao.module.system.dal.mysql.company.account;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.domainurl.DomainWxAccountRelationDO;

import org.apache.ibatis.annotations.Mapper;


/**
 * 域名与微信账号关系 Mapper
 *
 * 
 */
@Mapper
public interface DomainWxAccountRelationMapper extends BaseMapperX<DomainWxAccountRelationDO> {


}