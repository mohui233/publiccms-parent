package org.publiccms.views.directive.cms;

// Generated 2017-6-2 15:12:46 by com.sanluan.common.generator.SourceGenerator

import static com.publiccms.common.tools.CommonUtils.notEmpty;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.publiccms.entities.cms.CmsWebsite;
import org.publiccms.logic.service.cms.CmsWebsiteService;
import org.publiccms.common.base.AbstractTemplateDirective;
import com.publiccms.common.handler.RenderHandler;

@Component
public class CmsWebsiteDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        Integer id = handler.getInteger("id");
        if (notEmpty(id)) {
            handler.put("object", service.getEntity(id)).render();
        } else {
            Integer[] ids = handler.getIntegerArray("ids");
            if (notEmpty(ids)) {
                List<CmsWebsite> entityList = service.getEntitys(ids);
                Map<String, CmsWebsite> map = new LinkedHashMap<String, CmsWebsite>();
                for (CmsWebsite entity : entityList) {
                    map.put(String.valueOf(entity.getId()), entity);
                }
                handler.put("map", map).render();
            }
        }
    }

    @Autowired
    private CmsWebsiteService service;

}
