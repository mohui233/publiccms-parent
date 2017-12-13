package org.publiccms.controller.admin.cms;

// Generated 2017-5-22 22:21:07 by com.sanluan.common.generator.SourceGenerator
import static com.publiccms.common.tools.CommonUtils.notEmpty;
import static com.publiccms.common.tools.CommonUtils.getDate;
import static com.publiccms.common.tools.RequestUtils.getIpAddress;
import static com.publiccms.common.tools.JsonUtils.getString;
import static org.apache.commons.lang3.StringUtils.join;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.publiccms.common.base.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import org.publiccms.entities.cms.CmsPartner;
import org.publiccms.logic.service.cms.CmsPartnerService;
import org.publiccms.entities.sys.SysSite;
import org.publiccms.entities.log.LogOperate;
import org.publiccms.logic.service.log.LogLoginService;

@Controller
@RequestMapping("cmsPartner")
public class CmsPartnerAdminController extends AbstractController {

	private String[] ignoreProperties = new String[]{"id"};

    @RequestMapping("save")
    public String save(CmsPartner entity, HttpServletRequest request, HttpSession session) {
    	SysSite site = getSite(request);
        if (null != entity.getId()) {
            entity = service.update(entity.getId(), entity, ignoreProperties);
            logOperateService.save(
                        new LogOperate(site.getId(), getAdminFromSession(session).getId(), LogLoginService.CHANNEL_WEB_MANAGER,
                                "update.cmsPartner", getIpAddress(request), getDate(), getString(entity)));
        } else {
            service.save(entity);
            logOperateService
                    .save(new LogOperate(site.getId(), getAdminFromSession(session).getId(), LogLoginService.CHANNEL_WEB_MANAGER,
                            "save.cmsPartner", getIpAddress(request), getDate(), getString(entity)));
        }
        return TEMPLATE_DONE;
    }

    @RequestMapping("delete")
    public String delete(Integer[] ids, HttpServletRequest request, HttpSession session) {
    	SysSite site = getSite(request);
    	if (notEmpty(ids)) {
	        service.delete(ids);
	        logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "delete.cmsPartner", getIpAddress(request), getDate(), join(ids, ',')));
        }
        return TEMPLATE_DONE;
    }
    
    @Autowired
    private CmsPartnerService service;
}