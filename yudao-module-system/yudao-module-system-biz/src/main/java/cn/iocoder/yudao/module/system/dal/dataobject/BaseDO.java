package cn.iocoder.yudao.module.system.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * 基础实现类
 */
@Data
public abstract class BaseDO {

    /**
     * creatorId
     */
    private Long creatorId;
    /**
     * 操作人ID
     */
    private Long operatorId;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;

}
