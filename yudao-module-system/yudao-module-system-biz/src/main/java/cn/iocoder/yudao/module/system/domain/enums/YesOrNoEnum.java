package cn.iocoder.yudao.module.system.domain.enums;

import cn.hutool.core.util.ObjUtil;
import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum YesOrNoEnum implements ArrayValuable<Integer> {
    YES(1, "是"),
    NO(0, "否");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(YesOrNoEnum::getStatus).toArray(Integer[]::new);


    private final Integer status;
    private final String name;


    @Override
    public Integer[] array() {
        return ARRAYS;
    }

    public static YesOrNoEnum getNameByStatus(Integer status) {
        for (YesOrNoEnum value : values()) {
            if (ObjUtil.equal(value.status, status)) {
                return value;
            }
        }
        return null;
    }
}
