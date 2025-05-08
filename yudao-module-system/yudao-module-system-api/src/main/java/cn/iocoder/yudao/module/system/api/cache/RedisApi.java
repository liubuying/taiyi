package cn.iocoder.yudao.module.system.api.cache;

import cn.iocoder.yudao.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 缓存数据更新/读取")
public interface RedisApi {

    public Boolean setValue(String corpId,String accessToken, Long expiresIn);

    public String getValue(String key);




}
