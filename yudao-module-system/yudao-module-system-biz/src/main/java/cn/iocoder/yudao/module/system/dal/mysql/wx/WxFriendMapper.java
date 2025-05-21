package cn.iocoder.yudao.module.system.dal.mysql.wx;

import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WxFriendDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface WxFriendMapper extends BaseMapper<WxFriendDO> {



@Select("<script>" +
        "SELECT * FROM wx_friend_info " +
        "<where>" +
        "  <if test='param.nick != null'>AND friend_nick LIKE CONCAT('%', #{param.nick}, '%')</if>" +
        "  <if test='param.wxId != null'>AND wx_persion_id = #{param.wxId}</if>" +
        "  <if test='param.type != null'>AND type = #{param.type}</if>" +
        "</where>" +
        "ORDER BY id DESC " +
        "LIMIT #{offset}, #{pageSize}" +
        "</script>")
    List<WxFriendVO> queryFriendDataList(@Param("param")WxQueryDTO  param, @Param("offset") int offset, @Param("pageSize") int pageSize);



    @Select("SELECT Count(1) FROM wx_friend_info")

    Long queryFriendDataListCount(WxQueryDTO dto);
}
