package org.publiccms.views.directive.cms;

// Generated 2017-5-22 22:21:07 by com.sanluan.common.generator.SourceGenerator

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.publiccms.logic.service.cms.CmsPartnerService;
import org.publiccms.common.base.AbstractTemplateDirective;
import com.publiccms.common.handler.RenderHandler;
import com.publiccms.common.handler.PageHandler;

@Component
public class CmsPartnerListDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        PageHandler page = service.getPage(handler.getString("title"), handler.getString("image"), 
                handler.getString("label"), handler.getString("url"), 
                handler.getInteger("pageIndex",1), handler.getInteger("count",30));
        handler.put("page", page).render();
    }

    @Autowired
    private CmsPartnerService service;

}