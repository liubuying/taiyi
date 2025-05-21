package cn.iocoder.yudao.module.system.dal.mysql.company;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.company.CompanyPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公司 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CompanyMapper extends BaseMapperX<CompanyDO> {


    default PageResult<CompanyDO> selectPage(CompanyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CompanyDO>()
                .likeIfPresent(CompanyDO::getName, reqVO.getName())
                .eqIfPresent(CompanyDO::getUnifiedSocialCreditCode, reqVO.getUnifiedSocialCreditCode())
                .likeIfPresent(CompanyDO::getLiaison, reqVO.getLiaison())
                .likeIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .likeIfPresent(CompanyDO::getEmail, reqVO.getEmail())
                .likeIfPresent(CompanyDO::getIndustry, reqVO.getIndustry())
                .likeIfPresent(CompanyDO::getRegisteredAddress, reqVO.getRegisteredAddress())
                .likeIfPresent(CompanyDO::getOfficeAddress, reqVO.getOfficeAddress())
                .betweenIfPresent(CompanyDO::getEntryDate, reqVO.getEntryDate())
                .betweenIfPresent(CompanyDO::getExitDate, reqVO.getExitDate())
                .eqIfPresent(CompanyDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

    /**
     * 根据统一社会信用代码查询公司
     *
     * @param unifiedSocialCreditCode 统一社会信用代码
     * @return 公司对象
     */
    default CompanyDO selectByUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        return selectOne(CompanyDO::getUnifiedSocialCreditCode, unifiedSocialCreditCode);
    }

    /**
     * 根据名称模糊查询公司列表
     *
     * @param name 公司名称
     * @return 公司列表
     */
    default List<CompanyDO> selectListByName(String name) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .likeIfPresent(CompanyDO::getName, name)
                .orderByDesc(CompanyDO::getId));
    }

    /**
     * 根据状态查询公司列表
     *
     * @param status 状态
     * @return 公司列表
     */
    default List<CompanyDO> selectListByStatus(Integer status) {
        return selectList(CompanyDO::getStatus, status);
    }

    /**
     * 根据ID列表查询公司列表，并按ID降序排序
     *
     * @param ids ID列表
     * @return 公司列表
     */
    default List<CompanyDO> selectCompanyListByIds(Collection<Long> ids) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .in(CompanyDO::getId, ids)
                .orderByDesc(CompanyDO::getId));
    }

}
