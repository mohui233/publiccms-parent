package com.publiccms.improve.redis.hibernate.regions;

import java.util.Properties;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;

import com.publiccms.improve.redis.DatabaseRedisClient;
import com.publiccms.improve.redis.hibernate.ConfigurableRedisRegionFactory;
import com.publiccms.improve.redis.hibernate.strategy.RedisAccessStrategyFactory;

/**
 *
 * RedisEntityRegion
 * 
 */
public class RedisEntityRegion extends RedisTransactionalDataRegion implements EntityRegion {

    /**
     * @param accessStrategyFactory
     * @param redis
     * @param configurableRedisRegionFactory
     * @param regionName
     * @param options
     * @param metadata
     * @param props
     */
    public RedisEntityRegion(RedisAccessStrategyFactory accessStrategyFactory, DatabaseRedisClient redis,
            ConfigurableRedisRegionFactory configurableRedisRegionFactory, String regionName, SessionFactoryOptions options,
            CacheDataDescription metadata, Properties props) {
        super(accessStrategyFactory, redis, configurableRedisRegionFactory, regionName, options, metadata, props);
    }

    @Override
    public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        return accessStrategyFactory.createEntityRegionAccessStrategy(this, accessType);
    }
}
