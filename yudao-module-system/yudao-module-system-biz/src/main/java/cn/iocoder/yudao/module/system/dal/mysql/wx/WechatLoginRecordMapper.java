package cn.iocoder.yudao.module.system.dal.mysql.wx;



import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatLoginRecordPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WechatLoginRecordDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 微信用户登录记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface WechatLoginRecordMapper extends BaseMapperX<WechatLoginRecordDO> {

    default PageResult<WechatLoginRecordDO> selectPage(WechatLoginRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WechatLoginRecordDO>()
                .eqIfPresent(WechatLoginRecordDO::getEmployeeUserId, reqVO.getEmployeeUserId())
                .eqIfPresent(WechatLoginRecordDO::getWxUnionId, reqVO.getWxUnionId())
                .eqIfPresent(WechatLoginRecordDO::getWxNo, reqVO.getWxNo())
                .likeIfPresent(WechatLoginRecordDO::getNickname, reqVO.getNickname())
                .eqIfPresent(WechatLoginRecordDO::getLoginQrCode, reqVO.getLoginQrCode())
                .eqIfPresent(WechatLoginRecordDO::getPort, reqVO.getPort())
                .eqIfPresent(WechatLoginRecordDO::getPid, reqVO.getPid())
                .eqIfPresent(WechatLoginRecordDO::getIp, reqVO.getIp())
                .betweenIfPresent(WechatLoginRecordDO::getLoginTime, reqVO.getLoginTime())
                .eqIfPresent(WechatLoginRecordDO::getGmtCreate, reqVO.getGmtCreate())
                .eqIfPresent(WechatLoginRecordDO::getGmtModified, reqVO.getGmtModified())
                .eqIfPresent(WechatLoginRecordDO::getIsOffline, reqVO.getIsOffline())
                .orderByDesc(WechatLoginRecordDO::getId));
    }

}