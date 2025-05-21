package cn.iocoder.yudao.module.system.controller.admin.company;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.company.CompanyPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.company.CompanyRespVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.company.CompanySaveVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.system.service.company.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "管理后台 - 公司")
@RestController
@RequestMapping("/system/company")
@Validated
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @PostMapping("/create")
    @Operation(summary = "创建公司")
    @PreAuthorize("@ss.hasPermission('system:company:create')")
    public CommonResult<Long> createCompany(@Valid @RequestBody CompanySaveVO createReqVO) {
        return success(companyService.createCompany(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公司")
    @PreAuthorize("@ss.hasPermission('system:company:update')")
    public CommonResult<Boolean> updateCompany(@Valid @RequestBody CompanySaveVO updateReqVO) {
        companyService.updateCompany(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公司")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:company:delete')")
    public CommonResult<Boolean> deleteCompany(@RequestParam("id") Long id) {
        companyService.deleteCompany(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公司")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:company:query')")
    public CommonResult<CompanyRespVO> getCompany(@RequestParam("id") Long id) {
        CompanyDO company = companyService.getCompany(id);
        return success(BeanUtils.toBean(company, CompanyRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公司分页")
    @PreAuthorize("@ss.hasPermission('system:company:query')")
    public CommonResult<PageResult<CompanyRespVO>> getCompanyPage(@Valid CompanyPageReqVO pageVO) {
        PageResult<CompanyDO> pageResult = companyService.getCompanyPage(pageVO);
        return success(BeanUtils.toBean(pageResult, CompanyRespVO.class));
    }

    @GetMapping("/get-id-by-name")
    @PermitAll
    @Operation(summary = "使用公司名称，获得公司编号", description = "根据用户的公司名称，获得公司编号")
    @Parameter(name = "name", description = "公司名称", required = true, example = "芋道源码")
    public CommonResult<Long> getCompanyIdByName(@RequestParam("name") String name) {
        List<CompanyDO> companies = companyService.getCompanyListByName(name);
        return success(companies.isEmpty() ? null : companies.get(0).getId());
    }

    @GetMapping("/list-by-status")
    @PermitAll
    @Operation(summary = "获取公司精简信息列表", description = "只包含被开启的公司")
    public CommonResult<List<CompanyRespVO>> getCompanySimpleList() {
        List<CompanyDO> list = companyService.getCompanyListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(convertList(list, companyDO ->
                new CompanyRespVO().setId(companyDO.getId()).setName(companyDO.getName())));
    }

}

//# 公司管理模块 API 文档
//
//## 1. 创建公司
//
//**接口地址**: `/system/company/create`
//        **请求方式**: POST
//**接口描述**: 创建一个新的公司记录
//
//### 请求参数
//
//```json
//{
//    "name": "芋道源码",
//        "unifiedSocialCreditCode": "91110108MA01GJR37B",
//        "liaison": "芋艿",
//        "phone": "15601691300",
//        "email": "aoteman@126.com",
//        "industry": "互联网",
//        "registeredAddress": "北京市海淀区上地十街10号",
//        "officeAddress": "北京市海淀区上地十街10号",
//        "status": 0,
//        "businessLicenseUrl": "https://www.iocoder.cn/xxx.jpg",
//        "username": "admin",
//        "password": "123456"
//}
//```
//
//        ### 返回结果
//
//```json
//{
//    "code": 200,
//        "data": 1024,
//        "msg": "成功"
//}
//```
//
//        ## 2. 更新公司
//
//**接口地址**: `/system/company/update`
//        **请求方式**: PUT
//**接口描述**: 更新已有的公司信息
//
//### 请求参数
//
//```json
//{
//    "id": 1024,
//        "name": "芋道源码科技有限公司",
//        "unifiedSocialCreditCode": "91110108MA01GJR37B",
//        "liaison": "芋艿",
//        "phone": "15601691300",
//        "email": "aoteman@126.com",
//        "industry": "互联网",
//        "registeredAddress": "北京市海淀区上地十街10号",
//        "officeAddress": "北京市海淀区上地十街10号",
//        "entryDate": "2023-01-01",
//        "exitDate": null,
//        "status": 0,
//        "businessLicenseUrl": "https://www.iocoder.cn/xxx.jpg"
//}
//```
//
//        ### 返回结果
//
//```json
//{
//    "code": 200,
//        "data": true,
//        "msg": "成功"
//}
//```
//
//        ## 3. 删除公司
//
//**接口地址**: `/system/company/delete`
//        **请求方式**: DELETE
//**接口描述**: 根据ID删除公司记录
//
//### 请求参数
//
//| 参数名 | 类型 | 是否必须 | 描述 |
//        |-------|------|---------|------|
//        | id   | Long | 是 | 公司编号 |
//
//        ### 返回结果
//
//```json
//{
//    "code": 200,
//        "data": true,
//        "msg": "成功"
//}
//```
//
//        ## 4. 获取公司详情
//
//**接口地址**: `/system/company/get`
//        **请求方式**: GET
//**接口描述**: 根据ID获取公司详细信息
//
//### 请求参数
//
//| 参数名 | 类型 | 是否必须 | 描述 |
//        |-------|------|---------|------|
//        | id   | Long | 是 | 公司编号 |
//
//        ### 返回结果
//
//```json
//{
//    "code": 200,
//        "data": {
//    "id": 1024,
//            "name": "芋道源码",
//            "unifiedSocialCreditCode": "91110108MA01GJR37B",
//            "liaison": "芋艿",
//            "phone": "15601691300",
//            "email": "aoteman@126.com",
//            "industry": "互联网",
//            "registeredAddress": "北京市海淀区上地十街10号",
//            "officeAddress": "北京市海淀区上地十街10号",
//            "entryDate": "2023-01-01",
//            "exitDate": null,
//            "status": 0,
//            "businessLicenseUrl": "https://www.iocoder.cn/xxx.jpg",
//            "createTime": "2023-01-01T10:00:00"
//},
//    "msg": "成功"
//}
//```
//
//        ## 5. 获取公司分页列表
//
//**接口地址**: `/system/company/page`
//        **请求方式**: GET
//**接口描述**: 分页查询公司列表
//
//### 请求参数
//
//| 参数名 | 类型 | 是否必须 | 描述 |
//        |-------|------|---------|------|
//        | pageNo | Integer | 是 | 页码 |
//        | pageSize | Integer | 是 | 每页条数 |
//        | name | String | 否 | 公司名称，模糊匹配 |
//        | unifiedSocialCreditCode | String | 否 | 统一社会信用代码，精确匹配 |
//        | liaison | String | 否 | 联络人，模糊匹配 |
//        | phone | String | 否 | 电话，模糊匹配 |
//        | email | String | 否 | 电子邮件，模糊匹配 |
//        | industry | String | 否 | 行业，模糊匹配 |
//        | status | Integer | 否 | 状态，精确匹配 |
//        | createTime | LocalDateTime[] | 否 | 创建时间区间 |
//
//        ### 返回结果
//
//```json
//{
//    "code": 200,
//        "data": {
//    "list": [
//    {
//        "id": 1024,
//            "name": "芋道源码",
//            "unifiedSocialCreditCode": "91110108MA01GJR37B",
//            "liaison": "芋艿",
//            "phone": "15601691300",
//            "email": "aoteman@126.com",
//            "industry": "互联网",
//            "registeredAddress": "北京市海淀区上地十街10号",
//            "officeAddress": "北京市海淀区上地十街10号",
//            "entryDate": "2023-01-01",
//            "exitDate": null,
//            "status": 0,
//            "businessLicenseUrl": "https://www.iocoder.cn/xxx.jpg",
//            "createTime": "2023-01-01T10:00:00"
//    }
//    ],
//    "total": 1
//},
//    "msg": "成功"
//}
//```
//
//        ## 6. 根据公司名称获取公司ID
//
//**接口地址**: `/system/company/get-id-by-name`
//        **请求方式**: GET
//**接口描述**: 根据公司名称查询公司ID，用于前端选择公司
//
//### 请求参数
//
//| 参数名 | 类型 | 是否必须 | 描述 |
//        |-------|------|---------|------|
//        | name | String | 是 | 公司名称 |
//
//        ### 返回结果
//
//```json
//{
//    "code": 200,
//        "data": 1024,
//        "msg": "成功"
//}
//```
//
//        ## 7. 获取启用状态的公司列表
//
//**接口地址**: `/system/company/list-by-status`
//        **请求方式**: GET
//**接口描述**: 获取所有启用状态的公司简要信息列表
//
//### 请求参数
//
//        无
//
//### 返回结果
//
//```json
//{
//    "code": 200,
//        "data": [
//    {
//        "id": 1024,
//            "name": "芋道源码"
//    },
//    {
//        "id": 1025,
//            "name": "芋道科技"
//    }
//  ],
//    "msg": "成功"
//}
//```

