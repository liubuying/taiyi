package cn.iocoder.yudao.module.system.dal.mysql.wx;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.wxgroup.WxGroupPersonRelationDO;

/**
 * @author Administrator
 */
public interface WxGroupPersonRelationMapper extends BaseMapperX<WxGroupPersonRelationDO> {
    /**
     * 查询wx用户所有群聊
     * @param wxPersonId
     * @return
     */
    default WxGroupPersonRelationDO selectByWxPersonId(String wxPersonId){
        return selectOne(WxGroupPersonRelationDO::getWxPersonId,wxPersonId);
    }

}
