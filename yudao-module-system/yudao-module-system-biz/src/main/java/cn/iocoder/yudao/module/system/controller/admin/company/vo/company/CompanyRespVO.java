package cn.iocoder.yudao.module.system.controller.admin.company.vo.company;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 公司 Response VO")
@Data
public class CompanyRespVO {
    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "公司名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道源码")
    private String name;

    @Schema(description = "统一社会信用代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "91110108MA01GJR37B")
    private String unifiedSocialCreditCode;

    @Schema(description = "联络人", example = "芋艿")
    private String liaison;

    @Schema(description = "电话", example = "15601691300")
    private String phone;

    @Schema(description = "电子邮件", example = "aoteman@126.com")
    private String email;

    @Schema(description = "行业", example = "互联网")
    private String industry;

    @Schema(description = "注册地址", example = "北京市海淀区上地十街10号")
    private String registeredAddress;

    @Schema(description = "办公地址", example = "北京市海淀区上地十街10号")
    private String officeAddress;

    @Schema(description = "入驻日期")
    private LocalDate entryDate;

    @Schema(description = "退出日期")
    private LocalDate exitDate;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "营业执照URL", example = "https://www.iocoder.cn/xxx.jpg")
    private String businessLicenseUrl;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
