package org.publiccms.test;

import static com.publiccms.common.tools.CommonUtils.getDate;

import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.publiccms.entities.cms.CmsContent;
import org.publiccms.entities.sys.SysSite;
import org.publiccms.logic.service.cms.CmsContentService;
import org.publiccms.logic.service.sys.SysSiteService;
import org.publiccms.test.config.CmsTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.publiccms.common.handler.PageHandler;

/**
 *
 * SysSiteServiceTest
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CmsTestConfig.class)
@Transactional
public class SysSiteServiceTest {
    @Autowired
    CmsContentService cmsService;
    
    @Autowired
    SysSiteService siteService;

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    @Test
    public void searchTest() {
        cmsService.reCreateIndex();
        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PageHandler page = cmsService.query(1, "啊", null, DateUtils.addHours(getDate(), -1), getDate(), null, null);
        for (CmsContent site : (List<CmsContent>) page.getList()) {
            System.out.println(site.getTitle());
        }
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void queryTest() {
        PageHandler page = siteService.getPage(null, null, null, null);
        for (SysSite site : (List<SysSite>) page.getList()) {
            System.out.println(site.getName());
        }
    }
}
