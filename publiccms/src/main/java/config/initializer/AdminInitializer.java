package config.initializer;

import static config.spring.AdminConfig.ADMIN_BASE_PATH;

import org.publiccms.common.servlet.AdminDispatcherServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.publiccms.common.base.BaseServletInitializer;

import config.spring.AdminConfig;

/**
 * 管理后台初始化
 * 
 * Management Initializer
 *
 */
public class AdminInitializer extends BaseServletInitializer implements WebApplicationInitializer {

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { AdminConfig.class };
    }

    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        return new AdminDispatcherServlet(servletAppContext);
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { ADMIN_BASE_PATH + "/*" };
    }

}
