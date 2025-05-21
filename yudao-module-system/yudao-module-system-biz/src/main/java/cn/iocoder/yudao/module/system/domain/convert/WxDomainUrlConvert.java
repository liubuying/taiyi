
package cn.iocoder.yudao.module.system.domain.convert;


import cn.iocoder.yudao.module.system.dal.dataobject.domainurl.DomainNameDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserInfoConvert.class})
public interface WxDomainUrlConvert {

    WxDomainUrlConvert INSTANCE = Mappers.getMapper(WxDomainUrlConvert.class);


    @Mapping(source = "status", target = "domainStatus")
    DomainName convert(DomainNameDO domainNameDO);


    default List<DomainName> convertList(List<DomainNameDO> domainNameDOList, Map<Long, UserInfo> userInfoMap) {
        return domainNameDOList.stream()
                .map(d -> {
                    DomainName domainName = convert(d);
                    // 假设 DomainNameDO 有 creator 字段是 UserDO 类型
                    if (d.getCreatorId() != null) {
                        UserInfo userInfo = userInfoMap.get(d.getCreatorId());
                        domainName.setCreator(userInfo);
                    }
                    if (d.getOperatorId() != null) {
                        UserInfo userInfo = userInfoMap.get(d.getOperatorId());
                        domainName.setOperator(userInfo);
                    }
                    return domainName;
                })
                .collect(Collectors.toList());
    }
}
