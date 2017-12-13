package com.publiccms.improve.redis.hibernate;

import static com.publiccms.common.tools.CommonUtils.notEmpty;
import static com.publiccms.improve.tools.RedisUtils.createJedisPool;
import static org.springframework.core.io.support.PropertiesLoaderUtils.loadAllProperties;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;

import com.publiccms.improve.redis.DatabaseRedisClient;
/**
 *
 * SingletonRedisRegionFactory
 * 
 */
public class SingletonRedisRegionFactory extends AbstractRedisRegionFactory {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final AtomicInteger referenceCount = new AtomicInteger();

    /**
     * @param props
     */
    public SingletonRedisRegionFactory(Properties props) {
        super(props);
        log.info("create SingletonRedisRegionFactory instance.");
    }

    @Override
    public void start(SessionFactoryOptions options, Properties properties) throws CacheException {
        log.debug("SingletonRedisRegionFactory is starting...");
        this.options = options;
        try {
            if (redisClient == null) {
                String configurationResourceName = (String) properties
                        .get("hibernate.redis.configurationResourceName");
                if (notEmpty(configurationResourceName)) {
                    Properties redisProperties = loadAllProperties(configurationResourceName);
                    this.redisClient = new DatabaseRedisClient(createJedisPool(redisProperties));
                }
                this.cacheTimestamper = createCacheTimestamper(redisClient, SingletonRedisRegionFactory.class.getName());
            }
            referenceCount.incrementAndGet();
            log.info("RedisRegionFactory is started.");
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void stop() {
        if (this.redisClient == null)
            return;

        if (referenceCount.decrementAndGet() == 0) {
            log.debug("RedisRegionFactory is stopping...");
            try {
                redisClient.shutdown();
                redisClient = null;
                cacheTimestamper = null;
                log.info("RedisRegionFactory is stopped.");
            } catch (Exception ignored) {
                log.error("Fail to stop SingletonRedisRegionFactory.", ignored);
            }
        }
    }
}
