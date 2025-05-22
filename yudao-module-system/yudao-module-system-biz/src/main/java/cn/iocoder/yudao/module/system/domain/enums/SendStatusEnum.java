package cn.iocoder.yudao.module.system.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 根据code获取枚举实例
 * 消息发送状态枚举
 * zhangbin
 */
@Getter
public enum SendStatusEnum {
    SENDING(1, "发送中"),
    SUCCESS(2, "发送成功"),
    FAILED(3, "发送失败"),
    READ(4, "已读"),
    REVOKED(5, "撤回");

    private final Integer code;
    private final String desc;

    private static final Map<Integer, SendStatusEnum> CODE_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(SendStatusEnum::getCode, e -> e));

    SendStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据code获取枚举实例
     */
    public static SendStatusEnum fromCode(Integer code) {
        return CODE_MAP.get(code);
    }

    /**
     * 根据code获取描述（安全方法，无匹配返回null）
     */
    public static String getDescByCode(Integer code) {
        SendStatusEnum status = fromCode(code);
        return status != null ? status.getDesc() : null;
    }

    /**
     * 判断code是否有效
     */
    public static boolean isValidCode(Integer code) {
        return CODE_MAP.containsKey(code);
    }
}
