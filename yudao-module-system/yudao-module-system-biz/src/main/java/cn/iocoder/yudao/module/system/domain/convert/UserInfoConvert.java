package cn.iocoder.yudao.module.system.domain.convert;

import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface UserInfoConvert {

    UserInfoConvert INSTANCE = Mappers.getMapper(UserInfoConvert.class);

    // 假设 UserDO 是你的数据库实体类
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "username", target = "userName")
    @Mapping(source = "operationTime", target = "operationTime")
    UserInfo convert(Long userId, String username, Date operationTime);
}
