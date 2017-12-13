package com.publiccms.improve.redis.hibernate.regions;

import java.util.Properties;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;

import com.publiccms.improve.redis.DatabaseRedisClient;
import com.publiccms.improve.redis.hibernate.ConfigurableRedisRegionFactory;
import com.publiccms.improve.redis.hibernate.strategy.RedisAccessStrategyFactory;

/**
 *
 * RedisCollectionRegion
 * 
 */
public class RedisCollectionRegion extends RedisTransactionalDataRegion implements CollectionRegion {

    /**
     * @param accessStrategyFactory
     * @param redis
     * @param configurableRedisRegionFactory
     * @param regionName
     * @param options
     * @param metadata
     * @param props
     */
    public RedisCollectionRegion(RedisAccessStrategyFactory accessStrategyFactory, DatabaseRedisClient redis,
            ConfigurableRedisRegionFactory configurableRedisRegionFactory, String regionName, SessionFactoryOptions options,
            CacheDataDescription metadata, Properties props) {
        super(accessStrategyFactory, redis, configurableRedisRegionFactory, regionName, options, metadata, props);
    }

    @Override
    public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        return accessStrategyFactory.createCollectionRegionAccessStrategy(this, accessType);
    }
}
