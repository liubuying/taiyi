package cn.iocoder.yudao.module.system.service.wx;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;

public interface WxFriendService {

    CommonResult<PageResult<WxFriendVO>> queryFriendDataList(WxQueryDTO dto);

}
