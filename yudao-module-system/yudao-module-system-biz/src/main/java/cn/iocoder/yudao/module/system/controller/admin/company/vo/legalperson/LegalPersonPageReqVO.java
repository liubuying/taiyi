package cn.iocoder.yudao.module.system.controller.admin.company.vo.legalperson;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 法人分页查询 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LegalPersonPageReqVO extends PageParam {

    @Schema(description = "法人姓名", example = "张三")
    private String name;

    @Schema(description = "证件号码", example = "110101199001011234")
    private String certNo;

    @Schema(description = "证件类型", example = "身份证")
    private String certType;

    @Schema(description = "联系电话", example = "15601691300")
    private String phone;

    @Schema(description = "电子邮箱", example = "zhangsan@iocoder.cn")
    private String email;

    @Schema(description = "关联公司ID", example = "1024")
    private Long companyId;

    @Schema(description = "创建时间", example = "2022-07-01 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime gmtCreate;

    @Schema(description = "更新时间", example = "2022-07-01 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime gmtModified;
}
