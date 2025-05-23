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
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WxSendServiceImpl implements WxSendService {
    @Resource
    private WxSendMapper wxSendMapper;


    @Override
    public CommonResult<?> sendMessageText(WxSendDTO dto) {
        WxSendDO wxSendDO=  BeanUtils.toBean(dto,WxSendDO.class);
        wxSendDO.setSendStatus(SendStatusEnum.SENDING.getCode());
         ///to do 三方发消息
        log.info("开始发送消息");
        WxSendDO wxSendDO1=  sendMessage(wxSendDO);
        wxSendDO.setSendStatus(1);
        wxSendDO.setMsgType(1);
        wxSendDO.setSendTime(new Date());
        //发送
        wxSendMapper.insert(wxSendDO1);
        return CommonResult.success("成功");
    }

    // 发送消息操作示例
    public WxSendDO sendMessage(WxSendDO wxSendDO) {
        try {
            // 发送消息的业务逻辑  to do
            boolean result = true;
            // weChatService.send(wxSendDO);
            if (result) {
                wxSendDO.setSendStatus(SendStatusEnum.SUCCESS.getCode());
                wxSendDO.setGmtModified(new Date());
            } else {
                wxSendDO.setSendStatus(SendStatusEnum.FAILED.getCode());
                wxSendDO.setGmtModified(new Date());
            }
        } catch (Exception e) {
            wxSendDO.setSendStatus(SendStatusEnum.FAILED.getCode());
            wxSendDO.setGmtModified(new Date());
            log.error("消息发送失败", e);
        }
        return wxSendDO;
    }




    @Override
    public CommonResult<List<WxMessageVo>> queryMsgList(WxQueryMessageDTO dto) {
        log.info("查询参数"+ JSON.toJSONString(dto));
        // 2. 构造时间范围（当天00:00:00 ~ 23:59:59）
        Date startTime = DateUtils.truncate(dto.getStartTime(), Calendar.DATE);
        Date endTime = DateRangeValidator.getEndOfDay(dto.getEndTime());
        // 3. 校验并查询
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

