package cn.iocoder.yudao.module.system.controller.admin.company;

import cn.iocoder.yudao.module.system.service.company.CompanyUserRelationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Tag(name = "管理后台 - 公司管理")
@RestController
@RequestMapping("/system/company")
@Validated
public class CompanyUserRelationController {

    @Resource
    private CompanyUserRelationService companyUserRelationService;
}
