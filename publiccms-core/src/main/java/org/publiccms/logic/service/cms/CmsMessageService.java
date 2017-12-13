package org.publiccms.logic.service.cms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.publiccms.entities.cms.CmsMessage;
import org.publiccms.logic.dao.cms.CmsMessageDao;
import com.publiccms.common.base.BaseService;
import com.publiccms.common.handler.PageHandler;

@Service
@Transactional
public class CmsMessageService extends BaseService<CmsMessage> {

    @Transactional(readOnly = true)
    public PageHandler getPage(String q1, String q2, 
                String q3, String station, 
                Integer pageIndex, Integer pageSize) {
        return dao.getPage(q1, q2, 
                q3, station,
                pageIndex, pageSize);
    }
    
    @Transactional(readOnly = true)
	public int getCount(String q2) {
		return dao.getCount(q2);
	}
	
    @Autowired
    private CmsMessageDao dao;
}