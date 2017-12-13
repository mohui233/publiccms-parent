package org.publiccms.controller.web.cms;


import com.google.gson.Gson;
import com.publiccms.common.handler.PageHandler;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.publiccms.common.base.AbstractBaseResp;
import org.publiccms.common.base.AbstractController;
import org.publiccms.entities.cms.CmsContent;
import org.publiccms.logic.service.cms.CmsContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import static com.publiccms.common.tools.CommonUtils.notEmpty;

/**
 * ContentController 内容
 */
@Controller
@RequestMapping("cmsContent")
public class CmsContentroller extends AbstractController {
	@Autowired
	private CmsContentService service;
	
	/**
	 * 根据类别查询内容
	 *
	 * @param categoryId
	 * @param queue      排序方式: true推荐排序, false最新排序
	 * @param pageSize
	 * @param request
	 * @param response
	 */
	@RequestMapping("list")
	public void contentList(Integer categoryId, Boolean queue, Integer pageSize, Boolean containChild, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		AbstractBaseResp baseResp = new AbstractBaseResp();
		PageHandler page = service.getPage(1, null, categoryId, containChild, null, false, null, null, true, null, null, null, null, null, null, null, null, null, null, queue, 1, pageSize);
		baseResp.setObject(page);
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

	/**
	 * 相关产品除去自身的
	 *
	 * @param id
	 * @param categoryId
	 * @param request
	 * @param response
	 */
	@RequestMapping("related")
	public void relatedtList(Long id, Integer categoryId, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		AbstractBaseResp baseResp = new AbstractBaseResp();
		PageHandler page = service.getRelatedPage(id, 1, null, categoryId, null, null, false, null, null, true, null, null, null, null, null, null, null, null, null, null, 1, null);
		baseResp.setObject(page);
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

	/**
	 * 文章点击数统计
	 *
	 * @param id
	 * @param clicks
	 * @param request
	 * @param response
	 */
	@RequestMapping("clicks")
	public void totalCount(Long id, int clicks, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		if (notEmpty(clicks)) {
			service.updateClicks(id, clicks);
		}
	}

	/**
	 * 上传新闻到数据库
	 */
	@RequestMapping("/fileUpload")
	public void insertArticle() {
		String path = "D:/a.xlsx";
		File file = new File(path);
		try {
			List<List<Object>> list = read2007Excel(file);
			int i = 0;
			for (List<Object> li : list) {
				if (i++ == 0) continue; //表头不读取
				if (li == null || li.size() == 0) break;  //读取到空结束
				String categoryId = li.get(1).toString();
				if(categoryId.equals("11") || categoryId.equals("1") || categoryId.equals("3") || categoryId.equals("36")
				 || categoryId.equals("37") || categoryId.equals("113") || categoryId.equals("2") || categoryId.equals("9")){
					
					CmsContent content = new CmsContent();
					String id = li.get(0).toString();
					content.setId(Long.valueOf(id));

					content.setCategoryId(Integer.parseInt(categoryId));

					String clicks = li.get(2).toString();
					content.setClicks(Integer.parseInt(clicks));

					String title = li.get(3).toString();
					content.setTitle(title);

					String author = li.get(4).toString();
					content.setAuthor(author);

					String cover = li.get(5).toString();
					content.setCover(cover);

					String createDate = li.get(6).toString();
					content.setCreateDate(new Date(Long.parseLong(createDate) * 1000));

					String publishDate = li.get(7).toString();
					content.setPublishDate(new Date(Long.parseLong(publishDate) * 1000));

					String keywords = li.get(8).toString();
					content.setKeywords(keywords);

					String disabled = li.get(9).toString();
					if (disabled.equals("1")){
						content.setDisabled(true);
					} else {
						content.setDisabled(false);
					}
					
					String description = li.get(10).toString();
					content.setDescription(description);
					
					if(categoryId.equals("3") || categoryId.equals("36") || categoryId.equals("37") || categoryId.equals("113")){
						content.setModelId("activity");
					} else if (categoryId.equals("1")) {
						content.setModelId("introduce");
					} else if (categoryId.equals("2")) {
						content.setModelId("example");
					} else if (categoryId.equals("9")) {
						content.setModelId("position");
					} else if (categoryId.equals("11")) {
						content.setModelId("mass");
					} 
					content.setSiteId(1);
					content.setUserId(Long.valueOf(1));
					content.setCheckUserId(Long.valueOf(1));
					content.setUrl("");
					content.setTagIds("");
					content.setStatus(0);

					service.updateContent(content.getId(), content);// 更新保存扩展字段，文本字段
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对外提供读取excel 的方法,
	 * 读取Office 2007 excel
	 */
	private static List<List<Object>> read2007Excel(File file)
			throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		int counter = 0;
		for (int i = sheet.getFirstRowNum(); counter < sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				counter++;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				// 读到行尾结束
				if (j == row.getLastCellNum()) {
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
				// 字符
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
				DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
				// 如果单元格是空就用 -代替
				if (cell == null) {
					value = "-";
				} else {
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						//		System.out.println(i + "行" + j + " 列 is String type");
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						/*  System.out.println(i + "行" + j
                            + " 列 is Number type ; DateFormt:"
                            + cell.getCellStyle().getDataFormatString());*/
						if ("@".equals(cell.getCellStyle().getDataFormatString())) {
							value = df.format(cell.getNumericCellValue());
						} else if ("General".equals(cell.getCellStyle()
								.getDataFormatString())) {
							value = nf.format(cell.getNumericCellValue());
						} else {
							value = sdf.format(HSSFDateUtil.getJavaDate(cell
									.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						//      System.out.println(i + "行" + j + " 列 is Boolean type");
						value = cell.getBooleanCellValue();
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						//  System.out.println(i + "行" + j + " 列 is Blank type");
						value = "-";
						break;
					default:
						//  System.out.println(i + "行" + j + " 列 is default type");
						value = cell.toString();
					}
				}
				if (value == null || "".equals(value)) {
					continue;
				}
				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}

}
