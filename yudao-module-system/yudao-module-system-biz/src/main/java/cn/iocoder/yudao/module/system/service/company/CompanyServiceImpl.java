package cn.iocoder.yudao.module.system.service.company;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.company.CompanyPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.company.CompanySaveVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.role.RoleSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserSaveReqVO;
import cn.iocoder.yudao.module.system.convert.tenant.TenantConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyUserRelationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.tenant.TenantPackageDO;
import cn.iocoder.yudao.module.system.dal.mysql.company.CompanyMapper;
import cn.iocoder.yudao.module.system.dal.mysql.company.CompanyUserRelationMapper;
import cn.iocoder.yudao.module.system.enums.permission.RoleCodeEnum;
import cn.iocoder.yudao.module.system.enums.permission.RoleTypeEnum;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
import static java.util.Collections.singleton;

@Service
@Validated
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private CompanyUserRelationMapper companyUserRelationMapper;

    @Resource
    @Lazy
    private AdminUserService adminUserService;

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public Long createCompany(CompanySaveVO createReqVO) {
        // 校验统一社会信用代码唯一
        validateUnifiedSocialCreditCodeUnique(null, createReqVO.getUnifiedSocialCreditCode());

        // 插入公司信息
        CompanyDO company = CompanyDO.builder()
                .name(createReqVO.getName())
                .unifiedSocialCreditCode(createReqVO.getUnifiedSocialCreditCode())
                .liaison(createReqVO.getLiaison())
                .phone(createReqVO.getPhone())
                .email(createReqVO.getEmail())
                .industry(createReqVO.getIndustry())
                .registeredAddress(createReqVO.getRegisteredAddress())
                .officeAddress(createReqVO.getOfficeAddress())
                .status(createReqVO.getStatus())
                .businessLicenseUrl(createReqVO.getBusinessLicenseUrl())
                .build();
        companyMapper.insert(company);

        // 如果有传入用户名和密码，则同时创建关联用户
        if (ObjectUtil.isAllNotEmpty(createReqVO.getUsername(), createReqVO.getPassword())) {
            // 创建管理员账号
            UserSaveReqVO userCreateReqVO = new UserSaveReqVO();
            userCreateReqVO.setUsername(createReqVO.getUsername());
            userCreateReqVO.setPassword(createReqVO.getPassword());
            userCreateReqVO.setNickname(createReqVO.getName());
            userCreateReqVO.setRemark("公司管理员");
            userCreateReqVO.setMobile(createReqVO.getPhone());
            userCreateReqVO.setEmail(createReqVO.getEmail());
            // 这里可以设置部门和角色，如果需要
            Long userId = adminUserService.createUser(userCreateReqVO);

            // 建立公司和用户的关联关系
            createCompanyUserRelation(company.getId(), userId, createReqVO.getName());
        }

        // 返回
        return company.getId();
    }

    /**
     * 创建公司用户关联关系
     *
     * @param companyId 公司ID
     * @param userId 用户ID
     * @param userName 用户名称
     */
    private void createCompanyUserRelation(Long companyId, Long userId, String userName) {
        CompanyUserRelationDO relation = new CompanyUserRelationDO();
        relation.setCompanyId(companyId);
        relation.setUserId(userId);
        relation.setUserName(userName);
        //relation.setOperatorId(userId); // 操作人就是用户自己
        companyUserRelationMapper.insert(relation);
        log.info("[createCompanyUserRelation][创建公司({})与用户({})的关联关系]", companyId, userId);
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void updateCompany(CompanySaveVO updateReqVO) {
        // 校验存在
        validateCompanyExists(updateReqVO.getId());
        // 校验统一社会信用代码唯一
        validateUnifiedSocialCreditCodeUnique(updateReqVO.getId(), updateReqVO.getUnifiedSocialCreditCode());

        // 更新
        CompanyDO updateObj = CompanyDO.builder()
                .id(updateReqVO.getId())
                .name(updateReqVO.getName())
                .unifiedSocialCreditCode(updateReqVO.getUnifiedSocialCreditCode())
                .liaison(updateReqVO.getLiaison())
                .phone(updateReqVO.getPhone())
                .email(updateReqVO.getEmail())
                .industry(updateReqVO.getIndustry())
                .registeredAddress(updateReqVO.getRegisteredAddress())
                .officeAddress(updateReqVO.getOfficeAddress())
                .entryDate(updateReqVO.getEntryDate())
                .exitDate(updateReqVO.getExitDate())
                .status(updateReqVO.getStatus())
                .businessLicenseUrl(updateReqVO.getBusinessLicenseUrl())
                .build();
        companyMapper.updateById(updateObj);
        log.info("[updateCompany][更新公司({})成功]", updateReqVO.getId());
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteCompany(Long id) {
        // 校验存在
        validateCompanyExists(id);
        CompanyDO company = companyMapper.selectById(id);

        // 删除公司
        companyMapper.deleteById(id);
        log.info("[deleteCompany][删除公司({})成功]", id);

        // 删除公司的关联关系
        deleteCompanyUserRelations(id);
    }

    /**
     * 删除公司下的关联关系
     *
     * @param companyId 公司ID
     */
    private void deleteCompanyUserRelations(Long companyId) {
        // 查询关联关系
        List<CompanyUserRelationDO> relations = companyUserRelationMapper.selectListByCompanyId(companyId);
        if (CollUtil.isEmpty(relations)) {
            return;
        }

        // 删除关联关系
        // TODO

        log.info("[deleteCompanyUserRelations][删除公司({})的{}个用户关联关系]", companyId, relations.size());
    }

    @Override
    public void validateCompanyExists(Long id) {
        if (companyMapper.selectById(id) == null) {
            throw exception(COMPANY_NOT_EXISTS);
        }
    }

    /**
     * 校验公司是否有效，包括状态检查等
     *
     * @param id 公司ID
     */
    public void validCompany(Long id) {
        CompanyDO company = getCompany(id);
        if (company == null) {
            throw exception(COMPANY_NOT_EXISTS);
        }
        if (ObjectUtil.equal(company.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(COMPANY_DISABLE, company.getName());
        }
    }

    @Override
    public void validateUnifiedSocialCreditCodeUnique(Long id, String unifiedSocialCreditCode) {
        // 使用新增的selectByUnifiedSocialCreditCode方法查询
        CompanyDO company = companyMapper.selectByUnifiedSocialCreditCode(unifiedSocialCreditCode);
        if (company == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的公司
        if (id == null) {
            throw exception(COMPANY_UNIFIED_SOCIAL_CREDIT_CODE_EXISTS);
        }
        if (!company.getId().equals(id)) {
            throw exception(COMPANY_UNIFIED_SOCIAL_CREDIT_CODE_EXISTS);
        }
    }

    @Override
    public CompanyDO getCompany(Long id) {
        return companyMapper.selectById(id);
    }

    @Override
    public List<CompanyDO> getCompanyList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return companyMapper.selectCompanyListByIds(ids);
    }

    @Override
    public PageResult<CompanyDO> getCompanyPage(CompanyPageReqVO pageReqVO) {
        return companyMapper.selectPage(pageReqVO);
    }


    @Override
    public List<CompanyDO> getCompanyListByName(String name) {
        return companyMapper.selectListByName(name);
    }

    @Override
    public List<CompanyDO> getCompanyListByStatus(Integer status) {
        return companyMapper.selectListByStatus(status);
    }

    /**
     * 根据统一社会信用代码获取公司
     *
     * @param unifiedSocialCreditCode 统一社会信用代码
     * @return 公司对象
     */
    public CompanyDO getCompanyByUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        return companyMapper.selectByUnifiedSocialCreditCode(unifiedSocialCreditCode);
    }

    /**
     * 获取公司数量
     *
     * @return 公司数量
     */
    public Long getCompanyCount() {
        return companyMapper.selectCount();
    }

    /**
     * 获取所有公司ID列表
     *
     * @return 公司ID列表
     */
    public List<Long> getCompanyIdList() {
        List<CompanyDO> companies = companyMapper.selectList();
        return convertList(companies, CompanyDO::getId);
    }
}
