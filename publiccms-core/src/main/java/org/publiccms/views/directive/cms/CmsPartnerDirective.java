package org.publiccms.views.directive.cms;
import static com.publiccms.common.tools.CommonUtils.notEmpty;

// Generated 2017-5-22 22:21:07 by com.sanluan.common.generator.SourceGenerator

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.publiccms.entities.cms.CmsPartner;
import org.publiccms.logic.service.cms.CmsPartnerService;
import org.publiccms.common.base.AbstractTemplateDirective;
import com.publiccms.common.handler.RenderHandler;

@Component
public class CmsPartnerDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        Integer id = handler.getInteger("id");
        if (notEmpty(id)) {
            handler.put("object", service.getEntity(id)).render();
        } else {
            Integer[] ids = handler.getIntegerArray("ids");
            if (notEmpty(ids)) {
                List<CmsPartner> entityList = service.getEntitys(ids);
                Map<String, CmsPartner> map = new LinkedHashMap<String, CmsPartner>();
                for (CmsPartner entity : entityList) {
                    map.put(String.valueOf(entity.getId()), entity);
                }
                handler.put("map", map).render();
            }
        }
    }

    @Autowired
    private CmsPartnerService service;

}
