package cn.iocoder.yudao.module.system.domain.convert;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateRangeValidator {

    /**
     * 校验时间范围有效性
     * @throws IllegalArgumentException 如果时间范围无效
     */
    public static void validate(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("开始时间和结束时间不能为空");
        }
        if (startTime.after(endTime)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }
//        // 可选：限制最大查询范围（如不超过31天）
//        if (DateUtils.addDays(startTime, 31).before(endTime)) {
//            throw new IllegalArgumentException("查询时间范围不能超过 3年天");
//        }
    }

    /**
     * 安全获取结束时间的23:59:59
     */
    public static Date getEndOfDay(Date date) {
        return date == null ? null : DateUtils.addSeconds(
                DateUtils.truncate(date, Calendar.DATE),
                86399 // 23:59:59
        );
    }
}
