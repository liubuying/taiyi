package cn.iocoder.yudao.module.system.domain.enums;

import cn.hutool.core.util.ObjUtil;
import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AccountTypeEnum implements ArrayValuable<String> {
    WX("wx", "微信账号"),
    WE_COM("we_com", "企业微信账号");

    public static final String[] ARRAYS = Arrays.stream(values()).map(AccountTypeEnum:: getStatus).toArray(String[]::new);

    /**
     * 状态值
     */
    private final String status;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public String[] array() {
        return ARRAYS;
    }

    public static AccountTypeEnum getEnum(String status) {
        return Arrays.stream(values())
                .filter(item -> ObjUtil.equal(item.getStatus(), status))
                .findFirst().orElse(null);
    }

}
