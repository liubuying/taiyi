package cn.iocoder.yudao.module.system.controller.admin.company;


import cn.iocoder.yudao.module.system.service.company.CompanyService;
import cn.iocoder.yudao.module.system.service.company.LegalPersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Tag(name = "管理后台 - 法人")
@RestController
@RequestMapping("/system/LegalPerson")
@Validated
public class LegalPersonController {

    @Resource
    private LegalPersonService legalPersonService;

}