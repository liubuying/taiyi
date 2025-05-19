package cn.iocoder.yudao.module.system.dal.mysql.company;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyUserRelationDO;

import org.apache.ibatis.annotations.Mapper;
/**
 * 入驻公司信息 Mapper
 *
 *
 */
@Mapper
public interface CompanyUserRelationMapper extends BaseMapperX<CompanyUserRelationDO> {

    /*default PageResult<CompanyUserRelationDO> selectPage(CompanyUserRelationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CompanyUserRelationDO>()
                .eqIfPresent(CompanyUserRelationDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(CompanyUserRelationDO::getUserId, reqVO.getUserId())
                .likeIfPresent(CompanyUserRelationDO::getUserName, reqVO.getUserName())
                .eqIfPresent(CompanyUserRelationDO::getOperatorId, reqVO.getOperatorId())
                .eqIfPresent(CompanyUserRelationDO::getGmtCreate, reqVO.getGmtCreate())
                .eqIfPresent(CompanyUserRelationDO::getGmtModified, reqVO.getGmtModified())
                .orderByDesc(CompanyUserRelationDO::getId));
    }*/

}