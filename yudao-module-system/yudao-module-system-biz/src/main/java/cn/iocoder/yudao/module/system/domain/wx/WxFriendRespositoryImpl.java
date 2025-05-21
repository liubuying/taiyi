package cn.iocoder.yudao.module.system.domain.wx;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WxFriendDO;
import cn.iocoder.yudao.module.system.dal.mysql.wx.WxFriendMapper;
import cn.iocoder.yudao.module.system.domain.model.wx.WxFriend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WxFriendRespositoryImpl implements WxFriendRespository {
    @Resource
    private WxFriendMapper wxFriendMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public CommonResult<PageResult<WxFriendVO>> queryFriendDataList(WxQueryDTO dto) {
        //1.查询redis中是否有数据
        //2.redis中未查到，请求千寻接口
        //3.将数据存入redis中
        //4.将数据存入数据库
        //5.返回数据给前端

        // 标准计算公式 mysql偏移量
        int offset = (dto.getPageNo() - 1) * dto.getPageSize();
        List<WxFriendVO> wxList =wxFriendMapper.queryFriendDataList(dto,offset,dto.getPageSize());

        if(wxList.size()>0){
            PageResult pageResult=new PageResult();
            pageResult.setList(wxList);
            pageResult.setTotal(wxFriendMapper.queryFriendDataListCount(dto));
            return CommonResult.success(pageResult);
        }

        if(wxList.isEmpty()){

            //开始拉去微信好友列表
            List<WxFriendDO> lists=getWxFriendList(dto.getWxId(),"getFriendList");

            //拉取好友列表数据为空 没有添加好友
            if(lists.size()>=0){
                PageResult pageResult=new PageResult();
                pageResult.setList(lists);
                pageResult.setTotal(Long.getLong(String.valueOf(lists.size())));
                if(lists.size()>0){
                    log.info("数据落库开始");
                    wxFriendMapper.insert(lists);
                    //TODO 保存群组关联表数据
                    log.info("数据落库结束");
                }
                return CommonResult.success(pageResult);
            }

        }
        PageResult pageResult=new PageResult();
        pageResult.setList(wxList);
        pageResult.setTotal(wxFriendMapper.queryFriendDataListCount(dto));

        return CommonResult.success(pageResult);
    }

    private List<WxFriendDO> getWxFriendList(String wxId, String getFriendList) {
        List<WxFriendDO> list=new ArrayList<>();


        return list;
    }

}
