package org.publiccms.logic.service.cms;

// Generated 2017-5-22 22:21:07 by com.sanluan.common.generator.SourceGenerator


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.publiccms.entities.cms.CmsPartner;
import org.publiccms.logic.dao.cms.CmsPartnerDao;
import com.publiccms.common.base.BaseService;
import com.publiccms.common.handler.PageHandler;

@Service
@Transactional
public class CmsPartnerService extends BaseService<CmsPartner> {

    @Transactional(readOnly = true)
    public PageHandler getPage(String title, String image, 
                String label, String url, 
                Integer pageIndex, Integer pageSize) {
        return dao.getPage(title, image, 
                label, url, 
                pageIndex, pageSize);
    }
    
    @Autowired
    private CmsPartnerDao dao;
}