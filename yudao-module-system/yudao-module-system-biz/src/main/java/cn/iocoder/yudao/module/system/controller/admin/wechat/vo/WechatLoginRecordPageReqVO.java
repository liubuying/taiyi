package cn.iocoder.yudao.module.system.controller.admin.wechat.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 微信用户登录记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WechatLoginRecordPageReqVO extends PageParam {

    @Schema(description = "员工ID", example = "22507")
    private Long employeeUserId;

    @Schema(description = "微信UnionId", example = "25235")
    private String wxUnionId;

    @Schema(description = "微信No")
    private String wxNo;

    @Schema(description = "昵称", example = "王五")
    private String nickname;

    @Schema(description = "登录二维码")
    private String loginQrCode;

    @Schema(description = "端口")
    private Integer port;

    @Schema(description = "进程ID", example = "27769")
    private Integer pid;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "登录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] loginTime;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;

    @Schema(description = "修改时间")
    private LocalDateTime gmtModified;

    @Schema(description = "是否下线（0-在线，1-下线）")
    private Boolean isOffline;

}