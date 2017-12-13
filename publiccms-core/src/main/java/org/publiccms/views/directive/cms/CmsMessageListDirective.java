package org.publiccms.views.directive.cms;

// Generated 2017-5-22 22:10:43 by com.sanluan.common.generator.SourceGenerator

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.publiccms.logic.service.cms.CmsMessageService;
import org.publiccms.common.base.AbstractTemplateDirective;
import com.publiccms.common.handler.RenderHandler;
import com.publiccms.common.handler.PageHandler;

@Component
public class CmsMessageListDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        PageHandler page = service.getPage(handler.getString("q1"), handler.getString("q2"), 
                handler.getString("q3"), handler.getString("station"), 
                handler.getInteger("pageIndex",1), handler.getInteger("count",30));
        handler.put("page", page).render();
    }

    @Autowired
    private CmsMessageService service;

}