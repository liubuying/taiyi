package cn.iocoder.yudao.module.system.api.cache;

import cn.iocoder.yudao.module.system.util.cache.RedisWrapper;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class RedisApiImpl implements RedisApi{


    public static final String ACCESS_TOKEN_PREFIX =  "access_token";

    public static final String JSAPI_TICKET_PREFIX =  "jsapi_ticket";

    @Resource
    private RedisWrapper redisWrapper;


    @Override
    public Boolean setValue(String corpId, String accessToken, Long expiresIn) {
        Assert.isNull(corpId,  "corpId 不能为空");
        Assert.isNull(accessToken,  "accessToken 不能为空");
        Assert.notNull(expiresIn,  "expiresIn 不能为空");
        redisWrapper.setValue(ACCESS_TOKEN_PREFIX + corpId, accessToken, expiresIn);
        return true;
    }

    @Override
    public String getValue(String key) {
        if(!redisWrapper.exists(key)){
            return null;
        }
        return redisWrapper.getValue(key);
    }


}
