package cn.iocoder.yudao.module.system.controller.admin.company.vo.legalperson;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema(description = "管理后台 - 法人信息创建/修改 Request VO")
@Data
public class LegalPersonSaveVO {

    @Schema(description = "法人ID", example = "1024")
    private Long id;

    @Schema(description = "法人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "法人姓名不能为空")
    private String name;

    @Schema(description = "证件号码", requiredMode = Schema.RequiredMode.REQUIRED, example = "110101199001011234")
    @NotEmpty(message = "证件号码不能为空")
    private String certNo;

    @Schema(description = "证件类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "身份证")
    @NotEmpty(message = "证件类型不能为空")
    private String certType;

    @Schema(description = "联系电话", example = "15601691300")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
    private String phone;

    @Schema(description = "电子邮箱", example = "zhangsan@iocoder.cn")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "关联公司ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "关联公司不能为空")
    private Long companyId;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
