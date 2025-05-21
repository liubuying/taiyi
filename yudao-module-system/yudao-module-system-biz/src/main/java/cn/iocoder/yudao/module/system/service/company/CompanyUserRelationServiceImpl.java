package cn.iocoder.yudao.module.system.service.company;

import cn.iocoder.yudao.module.system.dal.mysql.company.CompanyUserRelationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公司用户关系 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class CompanyUserRelationServiceImpl implements CompanyUserRelationService {
    @Resource
    private CompanyUserRelationMapper companyUserRelationMapper;

    @Resource
    private AdminUserMapper userMapper;


}
