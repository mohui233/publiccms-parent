package com.publiccms.improve.redis.hibernate.regions;

import java.util.Properties;

import org.hibernate.cache.spi.TimestampsRegion;

import com.publiccms.improve.redis.DatabaseRedisClient;
import com.publiccms.improve.redis.hibernate.ConfigurableRedisRegionFactory;
import com.publiccms.improve.redis.hibernate.strategy.RedisAccessStrategyFactory;

/**
 *
 * RedisTimestampsRegion
 * 
 */
public class RedisTimestampsRegion extends RedisGeneralDataRegion implements TimestampsRegion {

    /**
     * @param accessStrategyFactory
     * @param redis
     * @param configurableRedisRegionFactory
     * @param regionName
     * @param props
     */
    public RedisTimestampsRegion(RedisAccessStrategyFactory accessStrategyFactory, DatabaseRedisClient redis,
            ConfigurableRedisRegionFactory configurableRedisRegionFactory, String regionName, Properties props) {
        super(accessStrategyFactory, redis, configurableRedisRegionFactory, regionName, props);
    }
}
