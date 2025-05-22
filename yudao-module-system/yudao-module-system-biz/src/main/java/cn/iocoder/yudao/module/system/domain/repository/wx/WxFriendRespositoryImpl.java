package cn.iocoder.yudao.module.system.domain.repository.wx;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.api.wx.dto.WxQueryDTO;
import cn.iocoder.yudao.module.system.api.wx.vo.WxFriendVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WxFriendDO;
import cn.iocoder.yudao.module.system.dal.mysql.wx.WxFriendMapper;
import cn.iocoder.yudao.module.system.util.enums.WxFriendConstants;
import cn.iocoder.yudao.module.system.wrapper.qianxun.QXunWrapper;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunInfoFriend;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunInfoGroup;
import cn.iocoder.yudao.module.system.wrapper.qianxun.qianXunModel.QianXunResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.WX_GROUP_PERSON;

@Slf4j
@Service
public class WxFriendRespositoryImpl implements WxFriendRespository {
    @Resource
    private WxFriendMapper wxFriendMapper;

    @Resource
    private QXunWrapper qXunWrapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public CommonResult<PageResult<WxFriendVO>> queryFriendDataList(WxQueryDTO dto) {
        //1.查询redis中是否有数据
        //2.redis中未查到，请求千寻接口
        //3.将数据存入redis中
        //4.将数据存入数据库
        //5.返回数据给前端

        // 标准计算公式 mysql偏移量
        int offset = (dto.getPageNo() - 1) * dto.getPageSize();
        List<WxFriendVO> wxList =wxFriendMapper.queryFriendDataList(dto.getNick(),dto.getWxId(),dto.getType(),dto.getTenantId(),offset,dto.getPageSize());

        if(wxList.size()>0){
            PageResult pageResult=new PageResult();
            pageResult.setList(wxList);
            pageResult.setTotal(wxFriendMapper.queryFriendDataListCount(dto.getNick(),dto.getWxId(),dto.getType(),dto.getTenantId()));
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


    //TO DO
    private List<WxFriendDO> getWxFriendList(String wxId, String getFriendList) {
        List<WxFriendDO> list=new ArrayList<>();


        return list;
    }

    @Override
    public void refreshWxFriendFromQianxun(String wxid) {
        Long loginUserId = getLoginUserId();
        //1.调用千寻接口（好友、群聊）
        List<WxFriendDO> wxFriendFromQx = getWxFriendGroupFromQx(wxid, WxFriendConstants.FRIEND_TYPE);
        List<WxFriendDO> wxGroupFromQx = getWxFriendGroupFromQx(wxid, WxFriendConstants.GROUP_TYPE);

        //只有获取到好友、群聊数据才执行以下逻辑
        if(wxFriendFromQx == null || wxGroupFromQx == null){
            wxFriendFromQx = wxFriendFromQx == null ? new ArrayList<>() : wxFriendFromQx;
            wxFriendFromQx.addAll(wxGroupFromQx);
            //2.获取数据库数据
            List<WxFriendDO> wxFriends = wxFriendMapper.selectListByWxPersonId(wxid);

            // 将数据库对象转换为Map结构（wxid为键）
            Map<String, WxFriendDO> dbMap = wxFriends.stream()
                    .collect(Collectors.toMap(WxFriendDO::getWxId, Function.identity()));

            //过滤出更新的数据
            List<WxFriendDO> updateList = wxFriendFromQx.stream()
                    // 先过滤出数据库存在的记录
                    .filter(vo -> dbMap.containsKey(vo.getWxId()))
                    .map(vo -> {
                        WxFriendDO dbDO = dbMap.get(vo.getWxId());
                        WxFriendDO updateDO = BeanUtils.toBean(vo, WxFriendDO.class);
                        updateDO.setId(dbDO.getId());
                        updateDO.setType(dbDO.getType());
                        return updateDO;
                    })
                    .filter(Objects::nonNull) // 过滤掉不需要更新的记录
                    .collect(Collectors.toList());

            //过滤需要插入的数据
            List<WxFriendDO> insertList = wxFriendFromQx.stream()
                    // 过滤出数据库不存在的记录
                    .filter(vo -> !dbMap.containsKey(vo.getWxId()))
                    .map(vo -> {
                        WxFriendDO dbDO = BeanUtils.toBean(vo, WxFriendDO.class);
                        //todo

                        dbDO.setCreatorId(loginUserId);
                        return dbDO;
                    })
                    .collect(Collectors.toList());
            //整合数据，插入数据库
            updateList.addAll(insertList);
            wxFriendMapper.insertOrUpdate(updateList);

            //3.获取redis数据，redis先删除后全插入
//        deleteWxFriendFromRedis(wxid);
            //TODO
//            setWxFriendToRedis(wxid, wxFriendFromQx);
        }

    }

    List<WxFriendDO> getWxFriendGroupFromQx(String wxid, Integer type){
        //todo  1.调用千寻接口（好友、群聊）
        String ip = "192.168.50.23";
        String wxidd = "wxid_f8zq0gmp9qih22";
        List<WxFriendDO> friendList;
        if(type.equals(WxFriendConstants.FRIEND_TYPE)){
            QianXunResponse<List<QianXunInfoFriend>> qxFriendList = qXunWrapper.getFriendList(ip, wxid);
            friendList = BeanUtils.toBean(qxFriendList.getResult(), WxFriendDO.class);
        }else{
            QianXunResponse<List<QianXunInfoGroup>> qxGroupList = qXunWrapper.getGroupList(ip, wxidd);
            friendList = qxGroupList.getResult().stream().map(qxGroup ->{
                        WxFriendDO vo = BeanUtils.toBean(qxGroup, WxFriendDO.class);
                        vo.setGroupNumberCount(qxGroup.getGroupMemberNum());
                        return vo;
                    })
                    .collect(Collectors.toList());
        }

        friendList.forEach(friend  -> {
            friend.setType(type);
        });
        return friendList;
    }

    private List<WxFriendVO> getWxFriendFromRedis(String wxid) {
        String redisKey = formatKey(wxid);//formatKey(accessToken);
        String redisData = stringRedisTemplate.opsForValue().get(redisKey); //RedisUtils.getValue(redisKey);//
        return JsonUtils.parseArray(redisData, WxFriendVO.class);
    }

    private void setWxFriendToRedis(String wxid, List<WxFriendVO> wxFriends) {
        String redisKey = formatKey(wxid);//formatKey(accessToken);
        stringRedisTemplate.opsForValue().set(redisKey, JsonUtils.toJsonString(wxFriends));//JsonUtils.toJsonString(wxFriends));
    }

    private void deleteWxFriendFromRedis(String wxid) {
        String redisKey = formatKey(wxid);//formatKey(accessToken);
        stringRedisTemplate.delete(redisKey);
    }

    private String formatKey(String wxid) {
        return String.format(WX_GROUP_PERSON, wxid);
    }

}
