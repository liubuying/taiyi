package cn.iocoder.yudao.module.system.domain.request;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.system.domain.enums.AccountTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "管理后台 - 公司账号池请求对象")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WxAccountPoolRequest extends PageParam {

    @Schema(description = "微信账号")
    private String unionId;

    @Schema(description = "微信昵称")
    private String nickName;

    @Schema(description ="公司名称")
    private String companyName;

    @Schema(description ="公司ID")
    private String companyId;

    @Schema(description ="员工名称")
    private String employeeName;

    @Schema(description ="员工ID")
    private String employeeId;

    @Schema(description ="操作人ID")
    private String operatorId;

    @Schema(description ="手机号")
    private String phone;

    @Schema(description ="邮箱")
    private String email;

    @Schema(description = "账号类型")
    private String accountType ;

    @Schema(description = "是否过期")
    private String isExpired ;


}
