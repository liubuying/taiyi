package cn.iocoder.yudao.module.system.dal.mysql.company;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.legalperson.LegalPersonPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.LegalPersonDO;

import org.apache.ibatis.annotations.Mapper;


/**
 * 法人信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LegalPersonMapper extends BaseMapperX<LegalPersonDO> {

    default PageResult<LegalPersonDO> selectPage(LegalPersonPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LegalPersonDO>()
                .likeIfPresent(LegalPersonDO::getName, reqVO.getName())
                .eqIfPresent(LegalPersonDO::getCertNo, reqVO.getCertNo())
                .eqIfPresent(LegalPersonDO::getCertType, reqVO.getCertType())
                .eqIfPresent(LegalPersonDO::getPhone, reqVO.getPhone())
                .eqIfPresent(LegalPersonDO::getEmail, reqVO.getEmail())
                .eqIfPresent(LegalPersonDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(LegalPersonDO::getCreateTime, reqVO.getGmtCreate())
                .eqIfPresent(LegalPersonDO::getUpdater, reqVO.getGmtModified())
                .orderByDesc(LegalPersonDO::getId));
    }

    /**
     * 根据公司ID查询法人信息
     *
     * @param companyId 公司ID
     * @return 法人信息
     */
    default List<LegalPersonDO> selectListByCompanyId(Long companyId) {
        return selectList(LegalPersonDO::getCompanyId, companyId);
    }

    /**
     * 根据证件号码查询法人信息
     *
     * @param certNo 证件号码
     * @return 法人信息
     */
    default LegalPersonDO selectByCertNo(String certNo) {
        return selectOne(LegalPersonDO::getCertNo, certNo);
    }

    /**
     * 根据姓名和公司ID查询法人信息
     *
     * @param name 姓名
     * @param companyId 公司ID
     * @return 法人信息
     */
    default LegalPersonDO selectByNameAndCompanyId(String name, Long companyId) {
        return selectOne(new LambdaQueryWrapperX<LegalPersonDO>()
                .eq(LegalPersonDO::getName, name)
                .eq(LegalPersonDO::getCompanyId, companyId));
    }

}