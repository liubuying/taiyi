package cn.iocoder.yudao.module.system.dal.mysql.company;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.company.LegalPersonDO;
import cn.iocoder.yudao.module.taiyi.dal.dataobject.legalperson.LegalPersonDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.taiyi.controller.admin.legalperson.vo.*;

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
                .eqIfPresent(LegalPersonDO::getGmtCreate, reqVO.getGmtCreate())
                .eqIfPresent(LegalPersonDO::getGmtModified, reqVO.getGmtModified())
                .orderByDesc(LegalPersonDO::getId));
    }

}