package cn.iocoder.yudao.module.system.service.wx;

import cn.hutool.db.Page;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.dal.mysql.wx.WxFriendMapper;
import cn.iocoder.yudao.module.system.domain.model.wx.WxFriend;
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
    private WxFriendMapper wxFriendMapper;

    @Resource
    private  RedisTemplate redisTemplate;

    @Override
    public CommonResult<PageResult<WxFriendVO>> queryFriendDataList(WxQueryDTO dto) {

       // 标准计算公式 mysql偏移量
        int offset = (dto.getPageNo() - 1) * dto.getPageSize();
        List<WxFriendVO>  wxList =wxFriendMapper.queryFriendDataList(dto.getNick(),dto.getWxId(),dto.getType(),dto.getTenantId(),offset,dto.getPageSize());

       if(wxList.size()>0){
           PageResult pageResult=new PageResult();
           pageResult.setList(wxList);
           pageResult.setTotal(wxFriendMapper.queryFriendDataListCount(dto.getNick(),dto.getWxId(),dto.getType(),dto.getTenantId()));
           return CommonResult.success(pageResult);
       }

        if(wxList.isEmpty()){
            //开始拉去微信好友列表
            List<WxFriend> lists=getWxFriendList(dto.getWxId(),"getFriendList");
            //拉取好友列表数据为空 没有添加好友
            if(lists.size()>=0){
                PageResult pageResult=new PageResult();
                pageResult.setList(lists);
                pageResult.setTotal(Long.getLong(String.valueOf(lists.size())));
                if(lists.size()>0){
                    log.info("数据落库开始");
                    wxFriendMapper.insert(lists);
                    log.info("数据落库结束");
                }
                return CommonResult.success(pageResult);
            }

        }
        PageResult pageResult=new PageResult();
        pageResult.setList(wxList);
        pageResult.setTotal(wxFriendMapper.queryFriendDataListCount(dto.getNick(),dto.getWxId(),dto.getType(),dto.getTenantId()));

        return CommonResult.success(pageResult);
    }

    private List<WxFriend> getWxFriendList(String wxId, String getFriendList) {
        List<WxFriend> list=new ArrayList<>();


        return list;
    }

}

