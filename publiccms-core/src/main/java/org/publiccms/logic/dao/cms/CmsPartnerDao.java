package org.publiccms.logic.dao.cms;
import static com.publiccms.common.tools.CommonUtils.notEmpty;
import org.publiccms.entities.cms.CmsPartner;

// Generated 2017-5-22 22:21:06 by com.sanluan.common.generator.SourceGenerator


import org.springframework.stereotype.Repository;

import com.publiccms.common.base.BaseDao;
import com.publiccms.common.handler.PageHandler;
import com.publiccms.common.handler.QueryHandler;


@Repository
public class CmsPartnerDao extends BaseDao<CmsPartner> {
    public PageHandler getPage(String title, String image, 
                String label, String url, 
                Integer pageIndex, Integer pageSize) {
        QueryHandler queryHandler = getQueryHandler("from CmsPartner bean");
        if (notEmpty(title)) {
            queryHandler.condition("bean.title like :title").setParameter("title", like(title));
        }
        if (notEmpty(image)) {
            queryHandler.condition("bean.image like :image").setParameter("image", like(image));
        }
        if (notEmpty(label)) {
            queryHandler.condition("bean.label like :label").setParameter("label", like(label));
        }
        if (notEmpty(url)) {
            queryHandler.condition("bean.url like :url").setParameter("url", like(url));
        }
        queryHandler.order("bean.weight desc");
        queryHandler.order("bean.id desc");
        return getPage(queryHandler, pageIndex, pageSize);
    }

    @Override
    protected CmsPartner init(CmsPartner entity) {
        return entity;
    }

}