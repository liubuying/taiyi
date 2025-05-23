package cn.iocoder.yudao.module.system.service.wx;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.api.wx.dto.WxMessageVo;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryMessageDTO;
import cn.iocoder.yudao.module.system.api.wx.dto.WxSendDTO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WxSendDO;
import cn.iocoder.yudao.module.system.dal.mysql.wx.WxSendMapper;
import cn.iocoder.yudao.module.system.domain.convert.DateRangeValidator;
import cn.iocoder.yudao.module.system.domain.enums.SendStatusEnum;
import cn.iocoder.yudao.module.system.wrapper.qianxun.QXunWrapper;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunMessage;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunResponse;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.xxl.job.core.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants.QIANXUN_API_RESULT_ERROR;

@Service
@Slf4j
public class WxSendServiceImpl implements WxSendService {
    @Resource
    private WxSendMapper wxSendMapper;


    @Resource
    private QXunWrapper qXunWrapper;


    @Override
    public CommonResult<?> sendMessageText(WxSendDTO dto) {
        WxSendDO wxSendDO=  BeanUtils.toBean(dto,WxSendDO.class);
        wxSendDO.setSendStatus(SendStatusEnum.SENDING.getCode());
        sendMessage(wxSendDO);
         ///to do 三方发消息
        log.info("开始发送消息");

        return CommonResult.success("成功");
    }

    public WxSendDO sendMessage(WxSendDO wxSendDO) {
        try {
            // 1. 调用千寻接口发送消息
            QianXunResponse<QianXunMessage> result = qXunWrapper.sendReferText(
                    IpUtil.getIp(),
                    wxSendDO.getFromUser(),
                    wxSendDO.getSendUser(),
                    wxSendDO.getMsgContext(),
                    wxSendDO.getWxMsgId()
            );
            // 2. 设置消息状态
            boolean isSuccess = result.getCode() == 200;
            wxSendDO.setSendStatus(isSuccess ? SendStatusEnum.SUCCESS.getCode() : SendStatusEnum.FAILED.getCode());
            wxSendDO.setGmtModified(new Date());
            // 3. 设置固定字段
            wxSendDO.setMsgType(1);
            wxSendDO.setSendTime(new Date());
            // 4. 持久化到数据库
            wxSendMapper.insert(wxSendDO);
            // 5. 记录日志
            if (isSuccess) {
                log.info("消息发送成功，消息ID: {}", wxSendDO.getId());
            } else {
                log.warn("消息发送失败，错误码: {}, 错误信息: {}");
                throw exception(QIANXUN_API_RESULT_ERROR);
            }
            return wxSendDO;

        } catch (Exception e) {
            log.error("消息发送异常，发送人: {}, 接收人: {}, 异常信息: {}",
                    wxSendDO.getFromUser(), wxSendDO.getSendUser(), e.getMessage(), e);
            throw exception(QIANXUN_API_RESULT_ERROR);
        }
    }




    @Override
    public CommonResult<List<WxMessageVo>> queryMsgList(WxQueryMessageDTO dto) {
        log.info("查询参数"+ JSON.toJSONString(dto));
        ///’/ 2. 构造时间范围（当天00:00:00 ~ 23:59:59）
        Date startTime = DateUtils.truncate(dto.getStartTime(), Calendar.DATE);
        Date endTime = DateRangeValidator.getEndOfDay(dto.getEndTime());
        ////3. 校验并查询
        DateRangeValidator.validate(startTime, endTime);
        List<WxSendDO> wxSendDOList = wxSendMapper.selectList(
                new LambdaQueryWrapper<WxSendDO>()
                        .ge(WxSendDO::getSendTime, startTime)  // >= start time
                        .le(WxSendDO::getSendTime, endTime)    // <= end time
                        .eq(WxSendDO::getFromUser, dto.getWxId())  // = wechat ID
                        .eq(WxSendDO::getMsgType, dto.getMsgType())  // = message type
                        .le(WxSendDO::getSendStatus, dto.getSendStatus())  // <= send status
                        .orderByDesc(WxSendDO::getSendTime)  // order by send time descending
        );
        return CommonResult.success(BeanUtils.toBean(wxSendDOList,WxMessageVo.class));
    }


}

