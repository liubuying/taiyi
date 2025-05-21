package cn.iocoder.yudao.module.system.dal.mysql.company;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.companyuserrelation.CompanyUserRelationPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyUserRelationDO;

import org.apache.ibatis.annotations.Mapper;
/**
 * 入驻公司信息 Mapper
 *
 *
 */
@Mapper
public interface CompanyUserRelationMapper extends BaseMapperX<CompanyUserRelationDO> {

    default PageResult<CompanyUserRelationDO> selectPage(CompanyUserRelationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CompanyUserRelationDO>()
                .eqIfPresent(CompanyUserRelationDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(CompanyUserRelationDO::getUserId, reqVO.getUserId())
                .likeIfPresent(CompanyUserRelationDO::getUserName, reqVO.getUserName())
                .eqIfPresent(CompanyUserRelationDO::getUpdater, reqVO.getOperatorId())
                .eqIfPresent(CompanyUserRelationDO::getCreateTime, reqVO.getGmtCreate())
                .eqIfPresent(CompanyUserRelationDO::getUpdateTime, reqVO.getGmtModified())
                .orderByDesc(CompanyUserRelationDO::getId));
    }

    /**
     * 根据用户ID查询所有关联的公司关系
     *
     * @param userId 用户ID
     * @return 关联的公司用户关系列表
     */
    default List<CompanyUserRelationDO> selectListByUserId(Long userId) {
        return selectList(new LambdaQueryWrapperX<CompanyUserRelationDO>()
                .eq(CompanyUserRelationDO::getUserId, userId));
    }

    /**
     * 根据公司ID查询所有关联的用户关系
     *
     * @param companyId 公司ID
     * @return 关联的公司用户关系列表
     */
    default List<CompanyUserRelationDO> selectListByCompanyId(Long companyId) {
        return selectList(new LambdaQueryWrapperX<CompanyUserRelationDO>()
                .eq(CompanyUserRelationDO::getCompanyId, companyId));
    }

    /**
     * 根据用户ID和公司ID查询关系
     *
     * @param userId 用户ID
     * @param companyId 公司ID
     * @return 公司用户关系
     */
    default CompanyUserRelationDO selectByUserIdAndCompanyId(Long userId, Long companyId) {
        return selectOne(new LambdaQueryWrapperX<CompanyUserRelationDO>()
                .eq(CompanyUserRelationDO::getUserId, userId)
                .eq(CompanyUserRelationDO::getCompanyId, companyId));
    }

    /**
     * 根据用户ID统计关联的公司数量
     *
     * @param userId 用户ID
     * @return 关联公司数量
     */
    default Long selectCountByUserId(Long userId) {
        return selectCount(new LambdaQueryWrapperX<CompanyUserRelationDO>()
                .eq(CompanyUserRelationDO::getUserId, userId));
    }

    /**
     * 根据公司ID统计关联的用户数量
     *
     * @param companyId 公司ID
     * @return 关联用户数量
     */
    default Long selectCountByCompanyId(Long companyId) {
        return selectCount(new LambdaQueryWrapperX<CompanyUserRelationDO>()
                .eq(CompanyUserRelationDO::getCompanyId, companyId));
    }

    /**
     * 批量删除指定公司ID的所有关联关系
     *
     * @param companyId 公司ID
     * @return 删除数量
     */
    default int deleteByCompanyId(Long companyId) {
        return delete(new LambdaQueryWrapperX<CompanyUserRelationDO>()
                .eq(CompanyUserRelationDO::getCompanyId, companyId));
    }

    /**
     * 批量删除指定用户ID的所有关联关系
     *
     * @param userId 用户ID
     * @return 删除数量
     */
    default int deleteByUserId(Long userId) {
        return delete(new LambdaQueryWrapperX<CompanyUserRelationDO>()
                .eq(CompanyUserRelationDO::getUserId, userId));
    }

}