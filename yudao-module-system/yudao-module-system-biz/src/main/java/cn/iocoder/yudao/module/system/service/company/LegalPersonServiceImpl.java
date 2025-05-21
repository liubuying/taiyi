package cn.iocoder.yudao.module.system.service.company;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.legalperson.LegalPersonPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.legalperson.LegalPersonSaveVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.LegalPersonDO;
import cn.iocoder.yudao.module.system.dal.mysql.company.LegalPersonMapper;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 法人信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class LegalPersonServiceImpl implements LegalPersonService {

    @Resource
    private LegalPersonMapper legalPersonMapper;

    @Resource
    private CompanyService companyService;

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public Long createLegalPerson(LegalPersonSaveVO createReqVO) {
        // 校验关联公司存在
        companyService.validateCompanyExists(createReqVO.getCompanyId());
        // 校验证件号码唯一
        validateCertNoUnique(null, createReqVO.getCertNo());

        // 插入法人信息
        LegalPersonDO legalPerson = new LegalPersonDO();
        legalPerson.setName(createReqVO.getName());
        legalPerson.setCertNo(createReqVO.getCertNo());
        legalPerson.setCertType(createReqVO.getCertType());
        legalPerson.setPhone(createReqVO.getPhone());
        legalPerson.setEmail(createReqVO.getEmail());
        legalPerson.setCompanyId(createReqVO.getCompanyId());
        legalPersonMapper.insert(legalPerson);

        // 返回
        return legalPerson.getId();
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void updateLegalPerson(LegalPersonSaveVO updateReqVO) {
        // 校验存在
        validateLegalPersonExists(updateReqVO.getId());
        // 校验关联公司存在
        companyService.validateCompanyExists(updateReqVO.getCompanyId());
        // 校验证件号码唯一
        validateCertNoUnique(updateReqVO.getId(), updateReqVO.getCertNo());

        // 更新
        LegalPersonDO updateObj = new LegalPersonDO();
        updateObj.setId(updateReqVO.getId());
        updateObj.setName(updateReqVO.getName());
        updateObj.setCertNo(updateReqVO.getCertNo());
        updateObj.setCertType(updateReqVO.getCertType());
        updateObj.setPhone(updateReqVO.getPhone());
        updateObj.setEmail(updateReqVO.getEmail());
        updateObj.setCompanyId(updateReqVO.getCompanyId());
        legalPersonMapper.updateById(updateObj);
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteLegalPerson(Long id) {
        // 校验存在
        validateLegalPersonExists(id);

        // 删除
        legalPersonMapper.deleteById(id);
    }

    @Override
    public LegalPersonDO getLegalPerson(Long id) {
        return legalPersonMapper.selectById(id);
    }

    @Override
    public List<LegalPersonDO> getLegalPersonList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return legalPersonMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<LegalPersonDO> getLegalPersonPage(LegalPersonPageReqVO pageReqVO) {
        return legalPersonMapper.selectPage(pageReqVO);
    }

    @Override
    public List<LegalPersonDO> getLegalPersonListByCompanyId(Long companyId) {
        return legalPersonMapper.selectListByCompanyId(companyId);
    }

    @Override
    public LegalPersonDO getLegalPersonByCertNo(String certNo) {
        return legalPersonMapper.selectByCertNo(certNo);
    }

    @Override
    public LegalPersonDO getLegalPersonByNameAndCompanyId(String name, Long companyId) {
        return legalPersonMapper.selectByNameAndCompanyId(name, companyId);
    }

    @Override
    public void validateLegalPersonExists(Long id) {
        if (legalPersonMapper.selectById(id) == null) {
            throw exception(LEGAL_PERSON_NOT_EXISTS);
        }
    }

    @Override
    public void validateCertNoUnique(Long id, String certNo) {
        // 查询是否已经存在
        LegalPersonDO legalPerson = legalPersonMapper.selectByCertNo(certNo);
        if (legalPerson == null) {
            return;
        }
        // 如果是更新操作，判断是否是当前记录
        if (id != null && legalPerson.getId().equals(id)) {
            return;
        }
        // 如果证件号已存在，则抛出异常
        throw exception(LEGAL_PERSON_CERT_NO_EXISTS);
    }
}
