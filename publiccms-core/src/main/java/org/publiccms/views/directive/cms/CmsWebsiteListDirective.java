package org.publiccms.views.directive.cms;

// Generated 2017-6-2 15:12:46 by com.sanluan.common.generator.SourceGenerator

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.publiccms.logic.service.cms.CmsWebsiteService;
import org.publiccms.common.base.AbstractTemplateDirective;
import com.publiccms.common.handler.RenderHandler;
import com.publiccms.common.handler.PageHandler;

@Component
public class CmsWebsiteListDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        PageHandler page = service.getPage(handler.getString("name"), handler.getString("code"), 
                handler.getString("keywords"), handler.getString("description"), 
                handler.getInteger("pageIndex",1), handler.getInteger("count",30));
        handler.put("page", page).render();
    }

    @Autowired
    private CmsWebsiteService service;

}