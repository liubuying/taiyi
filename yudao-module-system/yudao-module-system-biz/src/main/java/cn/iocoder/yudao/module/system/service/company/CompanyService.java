package cn.iocoder.yudao.module.system.service.company;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.company.CompanyPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.company.CompanySaveVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import javax.validation.Valid;
import java.util.*;


/**
 * 公司 Service 接口
 *
 * @author 芋道源码
 */
public interface CompanyService {

    /**
     * 创建公司
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCompany(@Valid CompanySaveVO createReqVO);

    /**
     * 更新公司
     *
     * @param updateReqVO 更新信息
     */
    void updateCompany(@Valid CompanySaveVO updateReqVO);

    /**
     * 删除公司
     *
     * @param id 编号
     */
    void deleteCompany(Long id);

    /**
     * 获得公司
     *
     * @param id 编号
     * @return 公司
     */
    CompanyDO getCompany(Long id);

    /**
     * 获得公司列表
     *
     * @param ids 编号
     * @return 公司列表
     */
    List<CompanyDO> getCompanyList(Collection<Long> ids);

    /**
     * 获得公司分页
     *
     * @param pageReqVO 分页查询
     * @return 公司分页
     */
    PageResult<CompanyDO> getCompanyPage(CompanyPageReqVO pageReqVO);

    /**
     * 校验公司是否存在
     *
     * @param id 公司ID
     */
    void validateCompanyExists(Long id);

    /**
     * 校验社会统一信用代码是否唯一
     *
     * @param id 公司ID
     * @param unifiedSocialCreditCode 社会统一信用代码
     */
    void validateUnifiedSocialCreditCodeUnique(Long id, String unifiedSocialCreditCode);

    /**
     * 根据公司名称查询公司列表
     *
     * @param name 公司名称
     * @return 公司列表
     */
    List<CompanyDO> getCompanyListByName(String name);

    /**
     * 根据状态查询公司列表
     *
     * @param status 状态
     * @return 公司列表
     */
    List<CompanyDO> getCompanyListByStatus(Integer status);
}
