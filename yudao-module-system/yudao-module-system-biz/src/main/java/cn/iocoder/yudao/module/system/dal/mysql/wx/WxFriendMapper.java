package cn.iocoder.yudao.module.system.dal.mysql.wx;

import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WxFriendDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface WxFriendMapper extends BaseMapper<WxFriendDO> {


    @Select("<script>" +
            "SELECT * FROM taiyi_wx_friend_info " +
            "<where>" +
            "  <if test='nick != null and nick.trim() != \"\"'>" +
            "    <bind name=\"nick\" value=\"'%' + nick + '%'\" />" +
            "    AND (nick LIKE #{nick} " +
            "    OR wx_num LIKE #{nick} " +
            "    OR province LIKE #{nick})" +
            "  </if>" +
            "  <if test='wxId!= null and wxId.trim() != \"\"'>" +

            "    AND wx_persion_id = #{wxId}" +
            "  </if>" +
            "  <if test='type != null and type.trim() != \"\"'>" +
            "    AND type = #{type}" +
            "  </if>" +
            "AND tenant_id = #{tenantId}" +
            "</where>" +
            "ORDER BY update_time DESC " +
            "LIMIT #{offset}, #{pageSize}" +
            "</script>")
    List<WxFriendVO> queryFriendDataList(@Param("nick")String  nick,@Param("wxId")String  wxId, @Param("type")String  type,@Param("tenantId") String tenantId, @Param("offset") int offset, @Param("pageSize") int pageSize);


    @Select("<script>" +
            "SELECT count(*) FROM taiyi_wx_friend_info " +
            "<where>" +
            "  <if test='nick != null and nick.trim() != \"\"'>" +
            "    <bind name=\"nick\" value=\"'%' + nick + '%'\" />" +
            "    AND (nick LIKE #{nick} " +
            "    OR wx_num LIKE #{nick} " +
            "    OR province LIKE #{nick})" +
            "  </if>" +
            "  <if test='wxId!= null and wxId.trim() != \"\"'>" +

            "    AND wx_persion_id = #{wxId}" +
            "  </if>" +
            "  <if test='type != null and type.trim() != \"\"'>" +
            "    AND type = #{type}" +
            "  </if>" +
            "AND tenant_id = #{tenantId}" +
            "</where>" +
            "ORDER BY update_time DESC " +
            "</script>")
   Long queryFriendDataListCount(@Param("nick")String  nick,@Param("wxId")String  wxId, @Param("type")String  type,@Param("tenantId") String tenantId);

    default List<WxFriendDO> selectListByWxPersonId(String  wxId){
        return this.selectList(new LambdaQueryWrapper<WxFriendDO>().eq(WxFriendDO::getWxPersonId, wxId));
    }
}
