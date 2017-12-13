package com.publiccms.improve.redis.hibernate;

import com.publiccms.improve.redis.DatabaseRedisClient;
import com.publiccms.improve.redis.hibernate.timestamper.CacheTimestamper;

/**
 *
 * ConfigurableRedisRegionFactory
 * 
 */
public interface ConfigurableRedisRegionFactory {
    
    /**
     * @param redisClient
     * @param cacheKey
     * @return
     */
    public CacheTimestamper createCacheTimestamper(DatabaseRedisClient redisClient, String cacheKey);
}
