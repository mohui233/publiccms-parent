package com.publiccms.improve.redis.hibernate;

import static com.publiccms.improve.tools.RedisUtils.createJedisPool;
import static org.springframework.core.io.support.PropertiesLoaderUtils.loadAllProperties;

import java.util.Properties;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;

import com.publiccms.improve.redis.DatabaseRedisClient;

/**
 * Redis领域工厂
 * 
 * Redis Region Factory
 * 
 */
public class RedisRegionFactory extends AbstractRedisRegionFactory {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param props
     */
    public RedisRegionFactory(Properties props) {
        super(props);
    }

    @Override
    public void start(SessionFactoryOptions options, Properties properties) throws CacheException {
        log.debug("RedisRegionFactory is starting... ");

        this.options = options;
        try {
            if (redisClient == null) {
                String configurationResourceName = (String) properties
                        .get("hibernate.redis.configurationResourceName");
                if (null != configurationResourceName) {
                    Properties redisProperties = loadAllProperties(configurationResourceName);
                    this.redisClient = new DatabaseRedisClient(createJedisPool(redisProperties));
                }
                this.cacheTimestamper = createCacheTimestamper(redisClient, RedisRegionFactory.class.getName());
            }
            log.info("RedisRegionFactory is started.");
        } catch (Exception e) {
            log.error("Fail to start RedisRegionFactory.", e);
            throw new CacheException(e);
        }
    }

    @Override
    public void stop() {
        if (redisClient == null)
            return;

        try {
            redisClient.shutdown();
            redisClient = null;
            cacheTimestamper = null;
            log.info("RedisRegionFactory is stopped.");
        } catch (Exception ignored) {
            log.error("Fail to stop RedisRegionFactory.", ignored);
        }
    }
}
