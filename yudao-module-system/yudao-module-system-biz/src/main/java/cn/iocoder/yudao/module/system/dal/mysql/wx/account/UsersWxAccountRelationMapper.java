package cn.iocoder.yudao.module.system.dal.mysql.wx.account;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.taiyi.dal.dataobject.userswxaccountrelation.UsersWxAccountRelationDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.taiyi.controller.admin.userswxaccountrelation.vo.*;

/**
 * 员工与微信账号关系 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface UsersWxAccountRelationMapper extends BaseMapperX<UsersWxAccountRelationDO> {

    default PageResult<UsersWxAccountRelationDO> selectPage(UsersWxAccountRelationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UsersWxAccountRelationDO>()
                .eqIfPresent(UsersWxAccountRelationDO::getEmployeeId, reqVO.getEmployeeId())
                .eqIfPresent(UsersWxAccountRelationDO::getUnionId, reqVO.getUnionId())
                .eqIfPresent(UsersWxAccountRelationDO::getStatus, reqVO.getStatus())
                .eqIfPresent(UsersWxAccountRelationDO::getOperatorId, reqVO.getOperatorId())
                .eqIfPresent(UsersWxAccountRelationDO::getGmtCreate, reqVO.getGmtCreate())
                .eqIfPresent(UsersWxAccountRelationDO::getGmtModified, reqVO.getGmtModified())
                .orderByDesc(UsersWxAccountRelationDO::getId));
    }

}