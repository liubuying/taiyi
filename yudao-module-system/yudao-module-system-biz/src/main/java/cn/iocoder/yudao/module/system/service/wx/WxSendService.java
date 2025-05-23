package cn.iocoder.yudao.module.system.service.wx;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.wx.dto.WxMessageVo;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryMessageDTO;
import cn.iocoder.yudao.module.system.api.wx.dto.WxSendDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WxSendDO;

import java.util.List;

public interface WxSendService {


    CommonResult sendMessageText(WxSendDTO dto);

    CommonResult<List<WxMessageVo>> queryMsgList(WxQueryMessageDTO dto);

}
