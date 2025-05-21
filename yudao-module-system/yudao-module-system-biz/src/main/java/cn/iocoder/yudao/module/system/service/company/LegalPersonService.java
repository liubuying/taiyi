package cn.iocoder.yudao.module.system.service.company;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.legalperson.LegalPersonPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.legalperson.LegalPersonSaveVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.LegalPersonDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 法人 Service 接口
 *
 * @author 芋道源码
 */
public interface LegalPersonService {
    /**
     * 创建法人信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLegalPerson(@Valid LegalPersonSaveVO createReqVO);

    /**
     * 更新法人信息
     *
     * @param updateReqVO 更新信息
     */
    void updateLegalPerson(@Valid LegalPersonSaveVO updateReqVO);

    /**
     * 删除法人信息
     *
     * @param id 编号
     */
    void deleteLegalPerson(Long id);

    /**
     * 获得法人信息
     *
     * @param id 编号
     * @return 法人信息
     */
    LegalPersonDO getLegalPerson(Long id);

    /**
     * 获得法人信息列表
     *
     * @param ids 编号
     * @return 法人信息列表
     */
    List<LegalPersonDO> getLegalPersonList(Collection<Long> ids);

    /**
     * 获得法人信息分页
     *
     * @param pageReqVO 分页查询
     * @return 法人信息分页
     */
    PageResult<LegalPersonDO> getLegalPersonPage(LegalPersonPageReqVO pageReqVO);

    /**
     * 获得指定公司的法人信息列表
     *
     * @param companyId 公司编号
     * @return 法人信息列表
     */
    List<LegalPersonDO> getLegalPersonListByCompanyId(Long companyId);

    /**
     * 根据证件号码获得法人信息
     *
     * @param certNo 证件号码
     * @return 法人信息
     */
    LegalPersonDO getLegalPersonByCertNo(String certNo);

    /**
     * 根据姓名和公司ID获得法人信息
     *
     * @param name 姓名
     * @param companyId 公司ID
     * @return 法人信息
     */
    LegalPersonDO getLegalPersonByNameAndCompanyId(String name, Long companyId);

    /**
     * 校验法人信息是否存在
     *
     * @param id 法人ID
     */
    void validateLegalPersonExists(Long id);

    /**
     * 校验证件号码是否唯一
     *
     * @param id 法人ID
     * @param certNo 证件号码
     */
    void validateCertNoUnique(Long id, String certNo);
}
