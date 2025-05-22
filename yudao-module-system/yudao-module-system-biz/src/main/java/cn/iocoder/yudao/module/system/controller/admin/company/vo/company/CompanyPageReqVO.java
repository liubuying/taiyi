package cn.iocoder.yudao.module.system.controller.admin.company.vo.company;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公司分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CompanyPageReqVO extends PageParam {

    @Schema(description = "公司名称", example = "芋道源码")
    private String name;

    @Schema(description = "统一社会信用代码", example = "91110108MA01GJR37B")
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
    private Date[] entryDate;

    @Schema(description = "退出日期")
    private Date[] exitDate;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;
    
    private Long tenantId;

}
