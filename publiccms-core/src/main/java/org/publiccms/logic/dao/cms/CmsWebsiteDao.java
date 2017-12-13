package org.publiccms.logic.dao.cms;

// Generated 2017-6-2 15:12:46 by com.sanluan.common.generator.SourceGenerator

import static com.publiccms.common.tools.CommonUtils.notEmpty;
import org.springframework.stereotype.Repository;

import org.publiccms.entities.cms.CmsWebsite;
import com.publiccms.common.base.BaseDao;
import com.publiccms.common.handler.PageHandler;
import com.publiccms.common.handler.QueryHandler;

@Repository
public class CmsWebsiteDao extends BaseDao<CmsWebsite> {
    public PageHandler getPage(String name, String code, 
                String keywords, String description, 
                Integer pageIndex, Integer pageSize) {
        QueryHandler queryHandler = getQueryHandler("from CmsWebsite bean");
        if (notEmpty(name)) {
            queryHandler.condition("bean.name like :name").setParameter("name", like(name));
        }
        if (notEmpty(code)) {
            queryHandler.condition("bean.code like :code").setParameter("code", like(code));
        }
        if (notEmpty(keywords)) {
            queryHandler.condition("bean.keywords like :keywords").setParameter("keywords", like(keywords));
        }
        if (notEmpty(description)) {
            queryHandler.condition("bean.description like :description").setParameter("description", like(description));
        }
        queryHandler.order("bean.id desc");
        return getPage(queryHandler, pageIndex, pageSize);
    }

    @Override
    protected CmsWebsite init(CmsWebsite entity) {
        return entity;
    }

}