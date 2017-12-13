package config.initializer;

import static org.publiccms.common.constants.CommonConstants.applicationContext;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import config.spring.CmsConfig;

/**
 * CMS初始化
 * 
 * Management Initializer
 *
 */
public class CmsInitializer extends AbstractContextLoaderInitializer implements WebApplicationInitializer {

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
        applicationContext = rootAppContext;
        rootAppContext.register(CmsConfig.class);
        return rootAppContext;
    }
}
