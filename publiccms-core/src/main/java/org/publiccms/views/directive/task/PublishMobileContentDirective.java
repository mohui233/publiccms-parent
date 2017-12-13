package org.publiccms.views.directive.task;

import static com.publiccms.common.tools.CommonUtils.notEmpty;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.publiccms.common.base.AbstractTaskDirective;
import org.publiccms.entities.cms.CmsContent;
import org.publiccms.entities.sys.SysSite;
import org.publiccms.logic.component.template.TemplateComponent;
import org.publiccms.logic.service.cms.CmsContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publiccms.common.handler.RenderHandler;

/**
 *
 * PublishContentDirective
 * 
 */
@Component
public class PublishMobileContentDirective extends AbstractTaskDirective {
    
    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        Long id = handler.getLong("id");
        SysSite site = getSite(handler);
        Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
        if (notEmpty(id)) {
            map.put(id.toString(), templateComponent.createContentMobileFile(site, service.getEntity(id), null, null));
        } else {
            Long[] ids = handler.getLongArray("ids");
            if (notEmpty(ids)) {
                List<CmsContent> entityList = service.getEntitys(ids);
                for (CmsContent entity : entityList) {
                    map.put(entity.getId().toString(), templateComponent.createContentMobileFile(site, entity, null, null));
                }
            }
        }
        handler.put("map", map).render();
    }

    @Autowired
    private TemplateComponent templateComponent;
    @Autowired
    private CmsContentService service;
    
}
