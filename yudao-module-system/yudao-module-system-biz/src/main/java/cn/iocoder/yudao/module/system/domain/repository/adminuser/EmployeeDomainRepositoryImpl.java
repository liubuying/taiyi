package cn.iocoder.yudao.module.system.domain.repository.adminuser;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.domain.convert.UserInfoConvert;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeDomainRepositoryImpl implements EmployeeDomainRepository{

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public UserInfo getEmployeeByUsername(String username) {
        AdminUserDO userDO = adminUserMapper.selectByUsername(username);
        if(ObjectUtil.isNull(userDO)){
            log.info("未查到当前员工，param:{}", username);
            return null;
        }
        return UserInfoConvert.INSTANCE.convert(userDO.getId(), userDO.getUsername(), null);
    }

    @Override
    public UserInfo getEmployeeByMobile(String userId) {
        AdminUserDO userDO = adminUserMapper.selectById(userId);
        if(ObjectUtil.isNull(userDO)){
            log.info("未查到当前员工，param:{}", userId);
            return null;
        }
        return UserInfoConvert.INSTANCE.convert(userDO.getId(), userDO.getUsername(), null);
    }

    @Override
    public List<UserInfo> queryEmployeeList(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return Collections.emptyList();
        }
        List<AdminUserDO> adminUserDOS = adminUserMapper.selectList(AdminUserDO::getId, ids);
        return adminUserDOS.stream()
                .map(adminUserDO -> UserInfoConvert.INSTANCE.convert(adminUserDO.getId(), adminUserDO.getUsername(), null))
                .collect(Collectors.toList());
    }
}
