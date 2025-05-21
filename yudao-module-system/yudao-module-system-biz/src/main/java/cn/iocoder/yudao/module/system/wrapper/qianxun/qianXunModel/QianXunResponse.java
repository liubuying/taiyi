package cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel;

import lombok.Data;
/**
 * 千寻微信框架通用响应类
 *
 * @param <T> 响应结果类型
 */
@Data
public class QianXunResponse<T> {
    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 响应数据
     */
    private T result;


    public static <T> QianXunResponse<T> error(Integer code, String message) {

        QianXunResponse<T> result = new QianXunResponse<>();
        result.code = code;
        result.msg = message;
        long  time = System.currentTimeMillis();
        result.timestamp = Long.toString(time);;
        return result;
    }
}
