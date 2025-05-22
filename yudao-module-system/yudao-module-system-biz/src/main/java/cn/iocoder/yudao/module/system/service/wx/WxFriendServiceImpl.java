package cn.iocoder.yudao.module.system.service.wx;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.domain.repository.wx.WxFriendRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("WxFriendService")
@Slf4j
public class WxFriendServiceImpl  implements WxFriendService {
    @Resource
    private WxFriendRespository wxFriendRespository;

    @Override
    public void refreshWxFriendFromQianxun(String wxid) {
        wxFriendRespository.refreshWxFriendFromQianxun(wxid);
    }

    @Override
    public CommonResult<PageResult<WxFriendVO>> queryFriendDataList(WxQueryDTO dto) {
        return wxFriendRespository.queryFriendDataList(dto);
    }

}

