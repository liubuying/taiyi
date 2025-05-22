package cn.iocoder.yudao.module.system.controller.admin.wechat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 微信用户登录记录新增/修改 Request VO")
@Data
public class WechatLoginRecordSaveReqVO {

    @Schema(description = "自增主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1403")
    private Long id;

    @Schema(description = "员工ID", example = "22507")
    private Long employeeUserId;

    @Schema(description = "微信UnionId", example = "25235")
    private String wxUnionId;

    @Schema(description = "微信No", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "微信No不能为空")
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
    private LocalDateTime loginTime;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;

    @Schema(description = "修改时间")
    private LocalDateTime gmtModified;

    @Schema(description = "是否下线（0-在线，1-下线）")
    private Boolean isOffline;

}