package cn.iocoder.yudao.module.system.controller.admin.company.vo.companyuserrelation;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公司用户关系分页查询 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanyUserRelationPageReqVO extends PageParam {

    @Schema(description = "公司ID", example = "1024")
    private Long companyId;

    @Schema(description = "员工ID", example = "2048")
    private Long userId;

    @Schema(description = "员工名称，模糊匹配", example = "张三")
    private String userName;

    @Schema(description = "操作人ID", example = "3072")
    private Long operatorId;

    @Schema(description = "创建时间", example = "2022-07-01 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime gmtCreate;

    @Schema(description = "更新时间", example = "2022-07-01 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime gmtModified;
}