package cn.iocoder.yudao.module.system.domain.wx;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;

public interface WxFriendRespository {
    CommonResult<PageResult<WxFriendVO>> queryFriendDataList(WxQueryDTO dto);
}
