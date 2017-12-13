package org.publiccms.logic.dao.cms;
import static com.publiccms.common.tools.CommonUtils.notEmpty;


import org.publiccms.entities.cms.CmsMessage;

// Generated 2017-5-22 22:10:43 by com.sanluan.common.generator.SourceGenerator


import org.springframework.stereotype.Repository;

import com.publiccms.common.base.BaseDao;
import com.publiccms.common.handler.PageHandler;
import com.publiccms.common.handler.QueryHandler;


@Repository
public class CmsMessageDao extends BaseDao<CmsMessage> {
    public PageHandler getPage(String q1, String q2, 
                String q3, String station, Integer pageIndex, Integer pageSize) {
        QueryHandler queryHandler = getQueryHandler("from CmsMessage bean");
        if (notEmpty(q1)) {
            queryHandler.condition("bean.q1 like :q1").setParameter("q1", like(q1));
        }
        if (notEmpty(q2)) {
            queryHandler.condition("bean.q2 like :q2").setParameter("q2", like(q2));
        }
        if (notEmpty(q3)) {
            queryHandler.condition("bean.q3 like :q3").setParameter("q3", like(q3));
        }
        if (notEmpty(station)) {
            queryHandler.condition("bean.station like :station").setParameter("station", like(station));
        }
        queryHandler.order("bean.id desc");
        return getPage(queryHandler, pageIndex, pageSize);
    }

    @Override
    protected CmsMessage init(CmsMessage entity) {
        return entity;
    }

	public int getCount(String q2) {
        QueryHandler queryHandler = getQueryHandler("from CmsMessage bean");
        if (notEmpty(q2)) {
            queryHandler.condition("bean.q2 = :q2").setParameter("q2", q2);
        }
        int totalCount = countResult(queryHandler);
        return totalCount;
	}

}