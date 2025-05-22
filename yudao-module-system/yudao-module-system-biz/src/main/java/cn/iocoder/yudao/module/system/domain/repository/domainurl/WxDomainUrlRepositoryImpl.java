package cn.iocoder.yudao.module.system.domain.repository.domainurl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.domainurl.vo.WxDomainUrlVO;
import cn.iocoder.yudao.module.system.dal.dataobject.domainurl.DomainNameDO;
import cn.iocoder.yudao.module.system.dal.mysql.domainurl.DomainNameMapper;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.domain.convert.WxDomainUrlConvert;
import cn.iocoder.yudao.module.system.domain.enums.YesOrNoEnum;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import cn.iocoder.yudao.module.system.domain.repository.adminuser.EmployeeDomainRepository;
import cn.iocoder.yudao.module.system.domain.request.DomainNameRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WxDomainUrlRepositoryImpl implements WxDomainUrlRepository{

    @Autowired
    private DomainNameMapper domainNameMapper;
    @Autowired
    private EmployeeDomainRepository employeeDomainRepository;
    
    @Override
    public PageResult<DomainName> selectPage(DomainNameRequest reqVO) {
        if (reqVO == null) {
            log.error("参数为空");
        }
        PageResult<DomainNameDO> domainNameDOPageResult = domainNameMapper.selectPage(reqVO);
        if (domainNameDOPageResult != null) {
            PageResult<DomainName> domainNamePageResult = new PageResult<>();
            getUserInfoMap(domainNameDOPageResult.getList());
            domainNamePageResult.setList(WxDomainUrlConvert.INSTANCE.convertList(domainNameDOPageResult.getList(),userInfoMap));
            domainNamePageResult.setTotal(domainNameDOPageResult.getTotal());
            return domainNamePageResult;
        }
        return PageResult.empty();
    }

    private Map<Long, UserInfo> getUserInfoMap(List<DomainNameDO> list) {
        Set<Long> creatorIdList = list.stream()
                .filter(domainNameDO -> domainNameDO.getCreatorId() != null)
                .map(DomainNameDO::getCreatorId)
                .collect(Collectors.toSet());
        Set<Long> operatorIdList = list.stream()
                .filter(domainNameDO -> domainNameDO.getOperatorId() != null)
                .map(DomainNameDO::getOperatorId)
                .collect(Collectors.toSet());
        creatorIdList.addAll(operatorIdList);
        List<UserInfo> userInfos = employeeDomainRepository.queryEmployeeList(new ArrayList<Long>(creatorIdList));
        return userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, userInfo -> userInfo));
    }

    @Override
    public Long deleteDomainUrl(WxDomainUrlVO domainUrlVO) {
        if (domainUrlVO == null || domainUrlVO.getId() == null) {
            log.error("参数为空");
        }
        DomainNameDO domainNameDO = domainNameMapper.selectById(domainUrlVO.getId());
        if(domainNameDO != null){
            domainNameDO.setStatus(YesOrNoEnum.YES.getStatus());
            domainNameDO.setOperatorId(domainUrlVO.getOperator().getUserId());
            domainNameMapper.updateById(domainNameDO);
        }
        return domainNameDO.getId();
    }

    @Override
    public void saveDomainUrl(DomainName domainName) {
        if(domainName.getId() != null){
            saveDomain(domainName);
        } else {
            addDomain(domainName);
        }
    }

    @Override
    public List<DomainName> selectList(DomainNameRequest domainNameRequest) {
        List<DomainNameDO> domainNameDOS = domainNameMapper.selectList(domainNameRequest);
        return WxDomainUrlConvert.INSTANCE.convertList(domainNameDOS,getUserInfoMap(domainNameDOS));
    }


    private void addDomain(DomainName domainName) {
        if  (domainName == null || StringUtils.isBlank(domainName.getDomain()) || StringUtils.isBlank(domainName.getDomainName())) {
            log.error("参数为空");
            return;
        }
        DomainNameDO domainNameDO = WxDomainUrlConvert.INSTANCE.convert(domainName);
        domainNameDO.setCreatorId(domainName.getCreator().getUserId());
        Date date = new Date();
        domainNameDO.setGmtCreate(date);
        domainNameDO.setGmtModified(date);
        domainNameMapper.insert(domainNameDO);
    }

    private void saveDomain(DomainName domainName) {
        DomainNameDO domainNameDO = domainNameMapper.selectById(domainName.getId());
        if(domainNameDO == null){
            log.error("查询数据为空，请重新选择，domainId:{}", domainName.getId());
            return;
        }
        DomainNameDO convert = WxDomainUrlConvert.INSTANCE.convert(domainName);
        convert.setOperatorId(domainName.getOperator().getUserId());
        domainNameMapper.updateById(convert);
    }


}
