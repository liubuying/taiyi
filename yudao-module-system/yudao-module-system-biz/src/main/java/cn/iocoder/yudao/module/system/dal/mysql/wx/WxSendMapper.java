package cn.iocoder.yudao.module.system.dal.mysql.wx;

import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WxFriendDO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WxSendDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WxSendMapper extends BaseMapper<WxSendDO> {



}
