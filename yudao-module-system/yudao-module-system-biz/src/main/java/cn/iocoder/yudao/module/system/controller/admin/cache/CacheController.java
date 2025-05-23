package cn.iocoder.yudao.module.system.controller.admin.cache;

import cn.iocoder.yudao.module.system.util.cache.RedisWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Tag(name = "管理后台 - 邮箱账号")
@RestController
@RequestMapping("/system/cache")
public class CacheController {



    private static final Logger log = LoggerFactory.getLogger(CacheController.class);

    @Resource
    private RedisWrapper redisWrapper;


    @PostMapping("/setValue")
    @Operation(summary = "保存缓存数据")
    @PreAuthorize("@ss.hasPermission('system:cache:setValue')")
    public Boolean setValue(String key, String value, Long expiresIn) {
        log.info("保存缓存数据,key:{},value:{},expiresIn:{}", key, value, expiresIn);
        Assert.isNull(key,  "corpId 不能为空");
        Assert.isNull(value,  "value 不能为空");
        Assert.notNull(expiresIn,  "expiresIn 不能为空");
        redisWrapper.setValue(key, value, expiresIn);
        return true;
    }

    @PostMapping("/getValue")
    @Operation(summary = "查询缓存数据")
    @PreAuthorize("@ss.hasPermission('system:cache:getValue')")
    public String getValue(String key) {
        if(!redisWrapper.exists(key)){
            return null;
        }
        return redisWrapper.getValue(key);
    }


}
