package org.publiccms.controller.web.cms;

import static com.publiccms.common.tools.CommonUtils.empty;
import static com.publiccms.common.tools.CommonUtils.getDate;
import static com.publiccms.common.tools.CommonUtils.notEmpty;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

import org.publiccms.common.base.AbstractBaseResp;
import org.publiccms.entities.cms.CmsMessage;
import org.publiccms.logic.service.cms.CmsMessageService;

@Controller
@RequestMapping("cmsMessage")
public class CmsMessageAdminController {

	private String[] ignoreProperties = new String[]{"id"};

    @RequestMapping("save")
    public void save(CmsMessage entity, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
    	AbstractBaseResp baseResp = new AbstractBaseResp();
    	if (null != entity.getId()) {
            entity = service.update(entity.getId(), entity, ignoreProperties);
        } else {
        	if (notEmpty(entity.getQ2())) {
       		 int i = service.getCount(entity.getQ2());
                if (i <= 0) {  
               	 Date now = getDate();
        		 if (empty(entity.getCreateDate())) {
        			entity.setCreateDate(now);
        		 }
               	 service.save(entity);
               	 baseResp.setCode(0);
                } else {  
               	 baseResp.setCode(1);
                }  
            }
        }
		baseResp.setObject(entity);
		Gson gson = new Gson();
		JSONObject json = new JSONObject();
		String baseRespToJson = gson.toJson(baseResp);
		/*发送到前台*/
		response.setCharacterEncoding("utf-8");
		PrintWriter writer;
		try {
			json.put("baseResp", baseRespToJson);
			writer = response.getWriter();
			writer.print(baseRespToJson);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	

    @RequestMapping("delete")
    public void delete(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
    	if (notEmpty(ids)) {
	        service.delete(ids);
        }
		/*发送到前台*/
		response.setCharacterEncoding("utf-8");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Autowired
    private CmsMessageService service;
}