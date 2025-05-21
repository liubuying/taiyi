package cn.iocoder.yudao.module.system.controller.admin.company.vo.company;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "管理后台 - 公司创建/修改 Request VO")
@Data
public class CompanySaveVO{

    @Schema(description = "公司编号", example = "1024")
    private Long id;

    @Schema(description = "公司名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道源码")
    @NotNull(message = "公司名称不能为空")
    private String name;

    @Schema(description = "统一社会信用代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "91110108MA01GJR37B")
    @NotNull(message = "统一社会信用代码不能为空")
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
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "营业执照URL", example = "https://www.iocoder.cn/xxx.jpg")
    private String businessLicenseUrl;

    // ========== 仅【创建】时，需要传递的字段 ==========

    @Schema(description = "用户账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "yudao")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,30}$", message = "用户账号由 数字、字母 组成")
    @Size(min = 4, max = 30, message = "用户账号长度为 4-30 个字符")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

    @AssertTrue(message = "用户账号、密码不能为空")
    @JsonIgnore
    public boolean isUsernameValid() {
        return id != null // 修改时，不需要传递
                || (ObjectUtil.isAllNotEmpty(username, password)); // 新增时，必须都传递 username、password
    }
}
