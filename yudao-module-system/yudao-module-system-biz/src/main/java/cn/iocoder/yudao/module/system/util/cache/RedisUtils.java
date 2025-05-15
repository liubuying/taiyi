package cn.iocoder.yudao.module.system.util.cache;

import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisUtils {

    private static StringRedisTemplate stringRedisTemplate;

    /**
     * 获取redis key
     */
    public static String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public static void setValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }


    public static void setValue(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout);
    }


    public static void delValue(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 校验redis key 是否存在
     *
     * @param stringRedisTemplate
     */
    public static boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * redis 值是否到期
     *
     * @param stringRedisTemplate
     */
    public static boolean isExpired(String key) {
        return stringRedisTemplate.getExpire(key) == null;
    }


    public static void setRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisUtils.stringRedisTemplate = stringRedisTemplate;
    }


    public static String buildKey(String... s) {
        StringBuffer keyBuffer = new StringBuffer();
        for (String str : s) {
            keyBuffer.append(str);
        }
        return keyBuffer.toString();
    }
}
