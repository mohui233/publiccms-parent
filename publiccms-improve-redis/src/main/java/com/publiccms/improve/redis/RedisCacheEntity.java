package com.publiccms.improve.redis;

import static com.publiccms.improve.tools.RedisUtils.createJedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.publiccms.common.base.Base;
import com.publiccms.common.cache.CacheEntity;
import com.publiccms.improve.redis.serializer.BinarySerializer;
import com.publiccms.improve.redis.serializer.StringSerializer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * RedisCacheEntity
 * 
 * @param <K>
 * @param <V>
 * 
 */
public class RedisCacheEntity<K, V> implements CacheEntity<K, V>, java.io.Serializable, Base {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int size = 100;
    private JedisPool jedisPool;
    private String name;
    private byte[] byteName;
    private byte[] keysPattern;
    private final static StringSerializer regionSerializer = new StringSerializer();
    private final BinarySerializer<K> keySerializer = new BinarySerializer<>();
    private final BinarySerializer<V> valueSerializer = new BinarySerializer<>();

    @Override
    public List<V> put(K key, V value) {
        Jedis jedis = jedisPool.getResource();
        byte[] byteKey = getKey(key);
        jedis.set(byteKey, valueSerializer.serialize(value));
        jedis.zadd(byteName, System.currentTimeMillis(), byteKey);
        return clearCache(jedis);
    }

    @Override
    public void put(K key, V value, Integer expiry) {
        Jedis jedis = jedisPool.getResource();
        byte[] byteKey = getKey(key);
        if (null == expiry) {
            jedis.set(byteKey, valueSerializer.serialize(value));
            jedis.zadd(byteName, System.currentTimeMillis(), byteKey);
        } else {
            jedis.setex(byteKey, expiry, valueSerializer.serialize(value));
        }
        jedis.close();
    }

    @Override
    public V get(K key) {
        Jedis jedis = jedisPool.getResource();
        byte[] fullKey = getKey(key);
        V value = valueSerializer.deserialize(jedis.get(fullKey));
        jedis.zadd(byteName, System.currentTimeMillis(), fullKey);
        jedis.close();
        return value;
    }

    @Override
    public List<V> clear() {
        Jedis jedis = jedisPool.getResource();
        Set<byte[]> keyList = jedis.zrange(byteName, 0, -1);
        List<V> list = new ArrayList<>();
        for (byte[] key : keyList) {
            list.add(valueSerializer.deserialize(jedis.get(key)));
            jedis.del(key);
        }
        jedis.del(byteName);
        jedis.close();
        return list;
    }

    @Override
    public V remove(K key) {
        Jedis jedis = jedisPool.getResource();
        byte[] byteKey = getKey(key);
        if (jedis.exists(byteKey)) {
            V value = valueSerializer.deserialize(jedis.get(byteKey));
            jedis.del(byteKey);
            jedis.zrem(byteName, byteKey);
            jedis.close();
            return value;
        }
        return null;
    }

    @Override
    public long getDataSize() {
        Jedis jedis = jedisPool.getResource();
        long size = jedis.zrevrange(byteName, 0, -1).size();
        jedis.close();
        return size;
    }

    private List<V> clearCache(Jedis jedis) {
        List<V> list = null;
        if (size < jedis.keys(keysPattern).size()) {
            int helf = size / 2;
            Set<byte[]> keys = jedis.zrange(byteName, 0, helf);
            list = new ArrayList<>();
            for (byte[] key : keys) {
                list.add(valueSerializer.deserialize(jedis.get(key)));
                jedis.zrem(byteName, key);
                jedis.del(key);
            }
        }
        jedis.close();
        return list;
    }

    /**
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public Map<K, V> getAll() {
        Jedis jedis = jedisPool.getResource();
        Set<byte[]> keySet = jedis.zrange(byteName, 0, -1);
        Map<K, V> map = new HashMap<>();
        for (byte[] key : keySet) {
            map.put(keySerializer.deserialize(key), valueSerializer.deserialize(jedis.get(key)));
        }
        jedis.close();
        return map;
    }

    @Override
    public boolean contains(K key) {
        Jedis jedis = jedisPool.getResource();
        boolean exits = jedis.exists(getKey(key));
        jedis.close();
        return exits;
    }

    private byte[] getKey(K key) {
        return keySerializer.serialize(name + DOT + key);
    }

    @Override
    public void init(String name, Integer size, Properties properties) {
        init(name, size, createJedisPool(properties));
    }

    public void init(String name, Integer size, JedisPool jedisPool) {
        this.name = name;
        this.byteName = regionSerializer.serialize(name);
        this.keysPattern = regionSerializer.serialize(name + DOT + ASTERISK);
        if (null != size) {
            this.size = size;
        }
        this.jedisPool = jedisPool;
    }

}
