package cn.iocoder.yudao.module.system.domain.repository.wxpool;

import cn.iocoder.yudao.framework.common.exception.ServerException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.company.account.UsersWxAccountRelationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.account.WxAccountPoolDO;
import cn.iocoder.yudao.module.system.dal.dataobject.domainurl.DomainNameDO;
import cn.iocoder.yudao.module.system.dal.dataobject.domainurl.DomainWxAccountRelationDO;
import cn.iocoder.yudao.module.system.dal.mysql.company.account.DomainWxAccountRelationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.company.account.UsersWxAccountRelationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.company.account.WxAccountPoolMapper;
import cn.iocoder.yudao.module.system.dal.mysql.domainurl.DomainNameMapper;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.domain.convert.UserInfoConvert;
import cn.iocoder.yudao.module.system.domain.convert.WxDomainUrlConvert;
import cn.iocoder.yudao.module.system.domain.enums.AccountTypeEnum;
import cn.iocoder.yudao.module.system.domain.enums.YesOrNoEnum;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import cn.iocoder.yudao.module.system.domain.repository.adminuser.EmployeeDomainRepository;
import cn.iocoder.yudao.module.system.domain.request.WxAccountPoolRequest;
import cn.iocoder.yudao.module.system.domain.model.wxpool.WxAccountPool;
import cn.iocoder.yudao.module.system.enums.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 微信账号池
 */
@Slf4j
@Service
public class WxAccountDomainRepositoryImpl implements WxAccountDomainRepository {


    @Resource
    private WxAccountPoolMapper wxAccountPoolMapper;

    @Resource
    private UsersWxAccountRelationMapper usersWxAccountRelationMapper;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private DomainWxAccountRelationMapper domainWxAccountRelationMapper;

    @Resource
    private EmployeeDomainRepository employeeDomainRepository;

    @Resource
    private DomainNameMapper domainNameMapper;


    @Override
    public PageResult<WxAccountPool> queryWxAccountPoolForPage(WxAccountPoolRequest wxAccountPoolRequest) {
        if (wxAccountPoolRequest == null) {
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
                    if (YesOrNoEnum.YES.equals(wxAccountPoolDO.getStatus())) {
                        // 查询分配数据接口数据
                        UsersWxAccountRelationDO usersWxAccountRelationDO = usersWxAccountRelationMapper.selectOne(UsersWxAccountRelationDO::getUnionId, wxAccountPoolDO.getUnionId(), UsersWxAccountRelationDO::getStatus, YesOrNoEnum.NO.getStatus());
                        if (usersWxAccountRelationDO != null) {
                            UserInfo userInfo = new UserInfo();
                            userInfo.setUserId(usersWxAccountRelationDO.getEmployeeId());
                            userInfo.setUserName(adminUserMapper.selectById(userInfo.getUserId()).getUsername());
                            wxAccountPool.setEmployeeUser(userInfo);
                        }

                        DomainWxAccountRelationDO relationDO = domainWxAccountRelationMapper.selectOne(DomainWxAccountRelationDO::getUnionId, usersWxAccountRelationDO.getUnionId(), DomainWxAccountRelationDO::getStatus, YesOrNoEnum.NO.getStatus());
                        if (relationDO != null) {
                            DomainNameDO domainNameDO = domainNameMapper.selectOne(DomainNameDO::getId, relationDO.getDomainId(), DomainNameDO::getStatus, YesOrNoEnum.NO.getStatus());
                            wxAccountPool.setDomainName(WxDomainUrlConvert.INSTANCE.convert(domainNameDO));
                        }
                    }
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
    public Boolean bindDomainUrl(WxAccountPool wxAccountPool) {
        if (StringUtils.isBlank(wxAccountPool.getUnionId()) ||
                wxAccountPool.getOperator() == null ||
                wxAccountPool.getDomainName() == null) {
            log.info("绑定域名 wxId / domainName 参数为空");
            return false;
        }
        // 账号和域名绑定
        List<DomainWxAccountRelationDO> domainWxAccountRelationDOS = domainWxAccountRelationMapper.selectList(DomainWxAccountRelationDO::getUnionId, wxAccountPool.getUnionId(),
                DomainWxAccountRelationDO::getDeleted, YesOrNoEnum.NO.getStatus());
        if (CollectionUtils.isNotEmpty(domainWxAccountRelationDOS)) {
            return true;
        }

        DomainWxAccountRelationDO relationDO = new DomainWxAccountRelationDO();
        relationDO.setUnionId(wxAccountPool.getUnionId());
        relationDO.setDomainId(wxAccountPool.getDomainName().getId());
        relationDO.setStatus(YesOrNoEnum.NO.getStatus());
        relationDO.setOperatorId(wxAccountPool.getOperator().getUserId());
        relationDO.setCreatorId(wxAccountPool.getOperator().getUserId());
        relationDO.setDeleted(YesOrNoEnum.NO.getStatus());
        int insert = domainWxAccountRelationMapper.insert(relationDO);
        return insert > 0;
    }

    @Override
    public Boolean unBindDomainUrl(WxAccountPool wxAccountPool) {
        if (StringUtils.isBlank(wxAccountPool.getUnionId())
                || wxAccountPool.getOperator() == null
                || wxAccountPool.getDomainName() == null) {
            log.info("绑定域名 wxId参数为空");
            return false;
        }

        DomainWxAccountRelationDO relationDO = domainWxAccountRelationMapper.selectOne(DomainWxAccountRelationDO::getUnionId, wxAccountPool.getUnionId(),
                DomainWxAccountRelationDO::getDomainId, wxAccountPool.getDomainName().getId(),
                DomainWxAccountRelationDO::getDeleted, YesOrNoEnum.NO.getStatus());
        if (relationDO == null) {
            log.error("未查到绑定数据");
            throw new ServerException(ErrorCodeConstants.DATA_NOT_EXISTS);
        }
        relationDO.setStatus(YesOrNoEnum.YES.getStatus());
        domainWxAccountRelationMapper.updateById(relationDO);
        DomainWxAccountRelationDO deleteDO = new DomainWxAccountRelationDO();
        deleteDO.setId(relationDO.getId());
        deleteDO.setDeleted(YesOrNoEnum.YES.getStatus());
        return domainWxAccountRelationMapper.deleteById(relationDO) > 0;
    }

    @Override
    public Boolean bindEmployee(WxAccountPool wxAccountPool) {
        if (StringUtils.isBlank(wxAccountPool.getUnionId())
                || wxAccountPool.getOperator() == null
                || wxAccountPool.getEmployeeUser() == null) {
            log.info("绑定域名 wxId参数为空");
            return false;
        }
        // 账号和域名绑定
        List<UsersWxAccountRelationDO> usersWxAccountRelationDOS = usersWxAccountRelationMapper.selectList(UsersWxAccountRelationDO::getUnionId, wxAccountPool.getUnionId(),
                UsersWxAccountRelationDO::getStatus, YesOrNoEnum.NO.getStatus());
        if (CollectionUtils.isNotEmpty(usersWxAccountRelationDOS)) {
            log.info("wxId已绑定,wxId:{}", wxAccountPool.getUnionId());
            return true;
        }
        UsersWxAccountRelationDO usersWxAccountRelationDO = new UsersWxAccountRelationDO();
        usersWxAccountRelationDO.setEmployeeId(wxAccountPool.getEmployeeUser().getUserId());
        usersWxAccountRelationDO.setUnionId(wxAccountPool.getUnionId());
        usersWxAccountRelationDO.setStatus(YesOrNoEnum.NO.getStatus());
        usersWxAccountRelationDO.setOperatorId(wxAccountPool.getOperator().getUserId());
        return usersWxAccountRelationMapper.insert(usersWxAccountRelationDO) > 1;
    }

    @Override
    public Boolean unBindEmployee(WxAccountPool wxAccountPool) {
        if (StringUtils.isBlank(wxAccountPool.getUnionId())
                || wxAccountPool.getOperator() == null
                || wxAccountPool.getEmployeeUser() == null) {
            log.info("解绑域名 wxId参数为空");
            return false;
        }
        // 账号和域名绑定
        UsersWxAccountRelationDO usersWxAccountRelation = usersWxAccountRelationMapper.selectOne(UsersWxAccountRelationDO::getUnionId, wxAccountPool.getUnionId(),
                UsersWxAccountRelationDO::getEmployeeId, wxAccountPool.getEmployeeUser().getUserId(),
                UsersWxAccountRelationDO::getStatus, YesOrNoEnum.NO.getStatus());
        if (usersWxAccountRelation == null) {
            log.info("wxId未绑定,wxId:{}", wxAccountPool.getUnionId());
            throw new ServerException(ErrorCodeConstants.DATA_NOT_EXISTS);
        }
        usersWxAccountRelation.setStatus(YesOrNoEnum.YES.getStatus());
        return usersWxAccountRelationMapper.updateById(usersWxAccountRelation) > 1;
    }

    @Override
    public List<WxAccountPool> queryWxAccountByEmployeeId(WxAccountPoolRequest poolRequest) {
        // 查询员工绑定账号数据
        List<UsersWxAccountRelationDO> usersWxAccountRelationDOS = usersWxAccountRelationMapper.selectList(UsersWxAccountRelationDO::getEmployeeId, poolRequest.getEmployeeId(),
                UsersWxAccountRelationDO::getStatus, YesOrNoEnum.NO.getStatus());
        if (CollectionUtils.isEmpty(usersWxAccountRelationDOS)) {
            return Collections.emptyList();
        }
        List<String> wxUnionIdList = usersWxAccountRelationDOS.stream().map(UsersWxAccountRelationDO::getUnionId).collect(Collectors.toList());
        poolRequest.setPageSize(500);
        poolRequest.setWxUnionIdList(wxUnionIdList);
        PageResult<WxAccountPool> wxAccountPoolPageResult = this.queryWxAccountPoolForPage(poolRequest);
        if (wxAccountPoolPageResult == null || CollectionUtils.isEmpty(wxAccountPoolPageResult.getList())) {
            return Collections.emptyList();
        }
        return wxAccountPoolPageResult.getList();
    }
}
