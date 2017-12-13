package config.initializer;

import org.publiccms.common.servlet.WebDispatcherServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.publiccms.common.base.BaseServletInitializer;

import config.spring.WebConfig;

/**
 * 
 * WebInitializer Servlet3.0 工程入口类
 *
 */
public class WebInitializer extends BaseServletInitializer implements WebApplicationInitializer {

    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        return new WebDispatcherServlet(servletAppContext);
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
