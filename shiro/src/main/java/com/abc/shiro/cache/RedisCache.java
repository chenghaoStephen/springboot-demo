package com.abc.shiro.cache;

import com.abc.shiro.session.RedisConfig;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

/**
 * 配置权限数据从redis获取（可以使用本地缓存进一步提高性能）
 */
@Component
public class RedisCache<K,V> implements Cache<K,V> {
    /**
     * 前缀
     */
    private final String CACHE_PREFIX = "cache";

    @Autowired
    private RedisConfig redisConfig;

    private byte[] getKey(K k) {
        if (k instanceof String) {
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    @Override
    public V get(K k) throws CacheException {
        System.out.println("从redis获取授权数据");
        byte[] value = redisConfig.get(getKey(k));
        if (value != null) {
            return (V)SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        redisConfig.set(key, value);
        redisConfig.expire(key, 600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key = getKey(k);
        V value = get(k);
        redisConfig.del(key);
        return value;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
