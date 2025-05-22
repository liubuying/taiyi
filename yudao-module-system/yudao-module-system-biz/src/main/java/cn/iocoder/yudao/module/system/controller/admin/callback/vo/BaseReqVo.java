package cn.iocoder.yudao.module.system.controller.admin.callback.vo;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;


@Data
public class BaseReqVo {
    private String event;
    private T data;
}
