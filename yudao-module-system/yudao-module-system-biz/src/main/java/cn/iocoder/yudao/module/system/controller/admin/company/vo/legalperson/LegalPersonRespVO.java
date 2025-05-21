package cn.iocoder.yudao.module.system.controller.admin.company.vo.legalperson;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 法人信息 Response VO")
@Data
public class LegalPersonRespVO {

    @Schema(description = "法人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "法人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;

    @Schema(description = "证件号码", requiredMode = Schema.RequiredMode.REQUIRED, example = "110101199001011234")
    private String certNo;

    @Schema(description = "证件类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "身份证")
    private String certType;

    @Schema(description = "联系电话", example = "15601691300")
    private String phone;

    @Schema(description = "电子邮箱", example = "zhangsan@iocoder.cn")
    private String email;

    @Schema(description = "关联公司ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long companyId;

    @Schema(description = "关联公司名称", example = "芋道源码")
    private String companyName;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}