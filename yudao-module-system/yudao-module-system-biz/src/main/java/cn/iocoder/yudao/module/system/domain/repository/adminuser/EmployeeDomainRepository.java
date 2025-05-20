package cn.iocoder.yudao.module.system.domain.repository.adminuser;

import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;

import java.util.List;

public interface EmployeeDomainRepository {
    UserInfo getEmployeeByUsername(String username);

    UserInfo getEmployeeByMobile(String userId);

    List<UserInfo> queryEmployeeList(List<Long> ids);
}
