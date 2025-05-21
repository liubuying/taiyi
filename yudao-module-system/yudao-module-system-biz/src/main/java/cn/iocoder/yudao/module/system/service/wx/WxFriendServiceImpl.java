package cn.iocoder.yudao.module.system.service.wx;

import cn.hutool.db.Page;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.dal.mysql.wx.WxFriendMapper;
import cn.iocoder.yudao.module.system.domain.model.wx.WxFriend;
import cn.iocoder.yudao.module.system.domain.wx.WxFriendRespository;
import cn.iocoder.yudao.module.system.service.wechat.WeChatService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("WxFriendService")
@Slf4j
public class WxFriendServiceImpl  implements WxFriendService {
    @Resource
    private WxFriendRespository wxFriendRespository;

    @Resource
    private  RedisTemplate redisTemplate;

    @Override
    public CommonResult<PageResult<WxFriendVO>> queryFriendDataList(WxQueryDTO dto) {
        return wxFriendRespository.queryFriendDataList(dto);
    }

}

