package cn.iocoder.yudao.module.system.domain.repository.wxpool;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.company.account.WxAccountPoolDO;
import cn.iocoder.yudao.module.system.dal.dataobject.domainurl.DomainNameDO;
import cn.iocoder.yudao.module.system.dal.mysql.company.account.UsersWxAccountRelationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.company.account.WxAccountPoolMapper;
import cn.iocoder.yudao.module.system.domain.convert.UserInfoConvert;
import cn.iocoder.yudao.module.system.domain.enums.AccountTypeEnum;
import cn.iocoder.yudao.module.system.domain.enums.YesOrNoEnum;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import cn.iocoder.yudao.module.system.domain.repository.adminuser.EmployeeDomainRepository;
import cn.iocoder.yudao.module.system.domain.request.WxAccountPoolRequest;
import cn.iocoder.yudao.module.system.domain.model.wxpool.WxAccountPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 微信账号池
 */
@Slf4j
@Service
public class WxAccountDomainRepositoryImpl implements WxAccountDomainRepository{


    @Resource
    private WxAccountPoolMapper wxAccountPoolMapper;

    @Resource
    private UsersWxAccountRelationMapper usersWxAccountRelationMapper;

    @Resource
    private EmployeeDomainRepository employeeDomainRepository;

    @Override
    public PageResult<WxAccountPool> queryWxAccountPoolForPage(WxAccountPoolRequest wxAccountPoolRequest) {
        if(wxAccountPoolRequest == null){
            log.info("查询账号池分页数据 参数为空");
            return PageResult.empty();
        }
        PageResult<WxAccountPoolDO> wxAccountPoolDOPageResult = wxAccountPoolMapper.selectPage(wxAccountPoolRequest);

        Set<Long> creatorIdList = wxAccountPoolDOPageResult.getList().stream()
                .filter(accountPoolDO -> accountPoolDO.getCreatorId() != null)
                .map(WxAccountPoolDO::getCreatorId)
                .collect(Collectors.toSet());
        Set<Long> operatorIdList = wxAccountPoolDOPageResult.getList().stream()
                .filter(accountPoolDO -> accountPoolDO.getOperatorId() != null)
                .map(WxAccountPoolDO::getOperatorId)
                .collect(Collectors.toSet());
        creatorIdList.addAll(operatorIdList);
        List<UserInfo> userInfos = employeeDomainRepository.queryEmployeeList(new ArrayList<Long>(creatorIdList));
        Map<Long, UserInfo> userInfoMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, userInfo -> userInfo));

        List<WxAccountPool> result = wxAccountPoolDOPageResult.getList()
                .stream()
                .map(wxAccountPoolDO -> {
                    WxAccountPool wxAccountPool = new WxAccountPool();
                    BeanUtils.copyProperties(wxAccountPoolDO, wxAccountPool);
                    wxAccountPool.setAccountType(AccountTypeEnum.getEnum(wxAccountPoolDO.getAccountType()));
                    wxAccountPool.setCreator(userInfoMap.get(wxAccountPoolDO.getCreatorId()));
                    wxAccountPool.setOperator(userInfoMap.get(wxAccountPoolDO.getOperatorId()));
                    wxAccountPool.setIsExpired(YesOrNoEnum.getNameByStatus(wxAccountPoolDO.getIsExpired()));
                    return wxAccountPool;
                })
                .collect(Collectors.toList());
        return new PageResult<>(result, wxAccountPoolDOPageResult.getTotal());
    }

    @Override
    public void saveWxAccountPool(WxAccountPool wxAccountPool) {
        // 查询账号池信息
        WxAccountPoolDO wxAccountPoolDO = wxAccountPoolMapper.selectOne(WxAccountPoolDO::getUnionId, wxAccountPool.getUnionId());
        if (wxAccountPoolDO == null || wxAccountPoolDO.getId() == null) {

            insertWxAccountPool(wxAccountPool);
        } else {
            updateWxAccountPool(wxAccountPool, wxAccountPoolDO);
        }
    }

    private void insertWxAccountPool(WxAccountPool wxAccountPool) {
        WxAccountPoolDO wxAccountPoolDO = new WxAccountPoolDO();
        BeanUtils.copyProperties(wxAccountPool, wxAccountPoolDO);
        wxAccountPoolDO.setIsExpired(YesOrNoEnum.NO.getStatus());
        wxAccountPoolDO.setCreatorId(wxAccountPool.getCreator().getUserId());
        wxAccountPoolDO.setCreatorNickName(wxAccountPool.getCreator().getUserName());
        // 默认为微信账号
        wxAccountPoolDO.setAccountType(wxAccountPool.getAccountType().getStatus());
        wxAccountPoolMapper.insert(wxAccountPoolDO);
    }

    private void updateWxAccountPool(WxAccountPool wxAccountPool, WxAccountPoolDO wxAccountPoolDO) {
        wxAccountPoolDO.setOperatorId(wxAccountPool.getOperator().getUserId());
        wxAccountPoolDO.setOperatorNickName(wxAccountPool.getOperator().getUserName());
        BeanUtils.copyProperties(wxAccountPool, wxAccountPoolDO);
        wxAccountPoolDO.setAccountType(wxAccountPool.getAccountType().getStatus());
        wxAccountPoolMapper.updateById(wxAccountPoolDO);
    }

    @Override
    public void deleteWxAccountPoolById(WxAccountPool wxAccountPool) {
        WxAccountPoolDO wxAccountPoolDO = new WxAccountPoolDO();
        BeanUtils.copyProperties(wxAccountPool, wxAccountPoolDO);
        wxAccountPoolDO.setOperatorId(wxAccountPool.getOperator().getUserId());
        wxAccountPoolDO.setOperatorNickName(wxAccountPool.getOperator().getUserName());
        wxAccountPoolMapper.updateById(wxAccountPoolDO);
    }

    @Override
    public void bindAccount() {

    }
}
