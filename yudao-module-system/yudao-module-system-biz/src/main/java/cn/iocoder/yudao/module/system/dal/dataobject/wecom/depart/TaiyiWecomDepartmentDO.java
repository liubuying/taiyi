package cn.iocoder.yudao.module.system.dal.dataobject.wecom.depart;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 企微部门信息
 */
@Data
@TableName("taiyi_wecom_department")
public class TaiyiWecomDepartmentDO implements Serializable {


    private static final long serialVersionUID = -7233321763224070880L;
    @TableId
    private Long id;                // 部门ID
    private String name;            // 部门名称
    private Long parentId;          // 父部门ID
    private Integer orderNum;       // 排序
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
