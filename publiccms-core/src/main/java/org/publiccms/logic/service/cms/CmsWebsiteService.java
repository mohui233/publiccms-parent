package org.publiccms.logic.service.cms;

// Generated 2017-6-2 15:12:46 by com.sanluan.common.generator.SourceGenerator


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.publiccms.entities.cms.CmsWebsite;
import org.publiccms.logic.dao.cms.CmsWebsiteDao;
import com.publiccms.common.base.BaseService;
import com.publiccms.common.handler.PageHandler;

@Service
@Transactional
public class CmsWebsiteService extends BaseService<CmsWebsite> {

    @Transactional(readOnly = true)
    public PageHandler getPage(String name, String code, 
                String keywords, String description, 
                Integer pageIndex, Integer pageSize) {
        return dao.getPage(name, code, 
                keywords, description, 
                pageIndex, pageSize);
    }
    
    @Autowired
    private CmsWebsiteDao dao;
}