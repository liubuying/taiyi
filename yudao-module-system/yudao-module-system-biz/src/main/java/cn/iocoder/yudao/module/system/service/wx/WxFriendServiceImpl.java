package cn.iocoder.yudao.module.system.service.wx;

import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.system.api.wx.WxDeleteDTO;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryMessageDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.domain.enums.SendStatusEnum;
import cn.iocoder.yudao.module.system.domain.repository.wx.WxFriendRespository;
import cn.iocoder.yudao.module.system.wrapper.qianxun.QXunWrapper;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunLoginStatus;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunResponse;
import com.xxl.job.core.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants.*;

@Service("WxFriendService")
@Slf4j
public class WxFriendServiceImpl  implements WxFriendService {
    @Resource
    private WxFriendRespository wxFriendRespository;

    @Resource
    private QXunWrapper qXunWrapper;



    @Override
    public void refreshWxFriendFromQianxun(String wxid) {
        wxFriendRespository.refreshWxFriendFromQianxun(wxid);
    }

    @Override
    public CommonResult<?> deleteFriend(WxDeleteDTO dto) {
        try {
            log.info("删除好友请求: wxId={}, toWxId={}", dto.getWxId(), dto.getToWxId());
            QianXunResponse<QianXunLoginStatus> qianXunResponse = qXunWrapper.delFriend(
                    //ip 优先获取网络ip
                    IpUtil.getIp(),
                    dto.getWxId(),
                    dto.getToWxId()
            );
            if (qianXunResponse == null) {
                log.error("业务数据为空");
                throw exception(DEMO03_STUDENT_NOT_EXISTS);
            }
            if (qianXunResponse.getCode() != 200) {
                //千寻接口调用失败
                log.error("接口调用失败: code={}, msg={}", qianXunResponse.getCode(), qianXunResponse.getMsg());
                throw exception(QIANXUN_API_RESULT_ERROR);
            }

            if (qianXunResponse.getCode() == 200) {
                wxFriendRespository.deleteFriend(dto.getToWxId());
                return CommonResult.success("删除成功");
            } else {
                throw exception(DEMO03_FRINEND_FALL);
            }
        } catch (Exception e) {
            log.error("删除好友异常: ", e);
            throw exception(INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResult<PageResult<WxFriendVO>> queryFriendDataList(WxQueryDTO dto) {
        return wxFriendRespository.queryFriendDataList(dto);
    }

}

