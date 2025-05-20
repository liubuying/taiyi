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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
            Set<Long> creatorIdList = domainNameDOPageResult.getList().stream()
                    .filter(domainNameDO -> domainNameDO.getCreatorId() != null)
                    .map(DomainNameDO::getCreatorId)
                    .collect(Collectors.toSet());
            Set<Long> operatorIdList = domainNameDOPageResult.getList().stream()
                    .filter(domainNameDO -> domainNameDO.getOperatorId() != null)
                    .map(DomainNameDO::getOperatorId)
                    .collect(Collectors.toSet());
            creatorIdList.addAll(operatorIdList);
            List<UserInfo> userInfos = employeeDomainRepository.queryEmployeeList(new ArrayList<Long>(creatorIdList));
            Map<Long, UserInfo> userInfoMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, userInfo -> userInfo));
            domainNamePageResult.setList(WxDomainUrlConvert.INSTANCE.convertList(domainNameDOPageResult.getList(),userInfoMap));
            domainNamePageResult.setTotal(domainNameDOPageResult.getTotal());
            return domainNamePageResult;
        }
        return PageResult.empty();
    }

    @Override
    public Long deleteDomainUrl(WxDomainUrlVO domainUrlVO) {
        if (domainUrlVO == null || domainUrlVO.getId() == null) {
            log.error("参数为空");
        }
        DomainNameDO domainNameDO = domainNameMapper.selectById(domainUrlVO.getId());
        if(domainNameDO != null){
            domainNameDO.setStatus(YesOrNoEnum.YES.getStatus());
            domainNameDO.setOperatorId(domainUrlVO.getOperatorId());
            domainNameMapper.updateById(domainNameDO);
        }
        return domainNameDO.getId();
    }

    @Override
    public void saveDomainUrl(DomainName domainName) {

    }


}
