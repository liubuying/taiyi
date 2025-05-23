package cn.iocoder.yudao.module.system.util.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisWrapper {

    @Autowired
    private  StringRedisTemplate stringRedisTemplate ;

    /**
     * 获取redis key
     */
    public  String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public  void setValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }


    public  void setValue(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout);
    }


    public  void delValue(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 校验redis key 是否存在
     *
     * @param stringRedisTemplate
     */
    public  boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * redis 值是否到期
     *
     * @param stringRedisTemplate
     */
    public  boolean isExpired(String key) {
        return stringRedisTemplate.getExpire(key) == null;
    }




    public  String buildKey(String... s) {
        StringBuffer keyBuffer = new StringBuffer();
        for (String str : s) {
            keyBuffer.append(str);
        }
        return keyBuffer.toString();
    }
}
