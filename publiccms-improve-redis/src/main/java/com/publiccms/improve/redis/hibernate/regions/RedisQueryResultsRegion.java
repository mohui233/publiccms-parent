package com.publiccms.improve.redis.hibernate.regions;

import java.util.Properties;

import org.hibernate.cache.spi.QueryResultsRegion;

import com.publiccms.improve.redis.DatabaseRedisClient;
import com.publiccms.improve.redis.hibernate.ConfigurableRedisRegionFactory;
import com.publiccms.improve.redis.hibernate.strategy.RedisAccessStrategyFactory;

/**
 *
 * RedisQueryResultsRegion
 * 
 */
public class RedisQueryResultsRegion extends RedisGeneralDataRegion implements QueryResultsRegion {

    /**
     * @param accessStrategyFactory
     * @param redis
     * @param configurableRedisRegionFactory
     * @param regionName
     * @param props
     */
    public RedisQueryResultsRegion(RedisAccessStrategyFactory accessStrategyFactory, DatabaseRedisClient redis,
            ConfigurableRedisRegionFactory configurableRedisRegionFactory, String regionName, Properties props) {
        super(accessStrategyFactory, redis, configurableRedisRegionFactory, regionName, props);
    }
}
