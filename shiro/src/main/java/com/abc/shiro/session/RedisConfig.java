package com.abc.shiro.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisConfig {

    @Autowired
    private RedisTemplate redisTemplate;


    public void set(byte[] key, byte[] value) {
        //插入session值
        redisTemplate.opsForValue().set(key, value);
        //stringRedisTemplate.opsForValue().set(new String(key), new String(value), i, TimeUnit.SECONDS);
    }

    public void expire(byte[] key, int i) {
        //设置超时时间
        //三个参数 分别是key 时间 时间类型（时分秒等）
        redisTemplate.expire(key, i, TimeUnit.SECONDS);
    }

    public byte[] get(byte[] key) {
        //获取session值
        return (byte[]) redisTemplate.opsForValue().get(key);
    }

    public void del(byte[] key) {
        //删除session值
        redisTemplate.delete(key);
    }

    public Set<byte[]> keys(String shiro_session_prefix) {
        return redisTemplate.keys(shiro_session_prefix + "*");
    }
}
