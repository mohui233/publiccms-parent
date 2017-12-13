package org.publiccms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by com.publiccms.common.source.SourceGenerator

import com.publiccms.common.base.BaseService;
import com.publiccms.common.handler.FacetPageHandler;
import com.publiccms.common.handler.PageHandler;
import org.publiccms.entities.cms.CmsCategory;
import org.publiccms.entities.cms.CmsContent;
import org.publiccms.logic.dao.cms.CmsCategoryDao;
import org.publiccms.logic.dao.cms.CmsContentDao;
import org.publiccms.views.pojo.CmsContentStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Future;

import static com.publiccms.common.tools.CommonUtils.*;
import static org.apache.commons.lang3.ArrayUtils.add;
import static org.apache.commons.lang3.StringUtils.splitByWholeSeparator;

/**
 *
 * CmsContentService
 * 
 */
@Service
@Transactional
public class CmsContentService extends BaseService<CmsContent> {
	private String[] ignoreProperties = new String[] { "id" };
	/**
	 * 
	 */
	public static final int STATUS_DRAFT = 0;
	/**
	 * 
	 */
	public static final int STATUS_NORMAL = 1;
	/**
	 * 
	 */
	public static final int STATUS_PEND = 2;

	/**
	 * @param siteId
	 * @param text
	 * @param tagId
	 * @param startPublishDate
	 * @param endPublishDate
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = true)
	public PageHandler query(Integer siteId, String text, String tagId, Date startPublishDate, Date endPublishDate,
			Integer pageIndex, Integer pageSize) {
		return dao.query(siteId, text, tagId, startPublishDate, endPublishDate, pageIndex, pageSize);
	}

	/**
	 * @param siteId
	 * @param categoryIds
	 * @param modelIds
	 * @param userIds
	 * @param text
	 * @param tagId
	 * @param startPublishDate
	 * @param endPublishDate
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = true)
	public FacetPageHandler facetQuery(Integer siteId, String[] categoryIds, String[] modelIds, String[] userIds, String text,
			String tagId, Date startPublishDate, Date endPublishDate, Integer pageIndex, Integer pageSize) {
		return dao.facetQuery(siteId, categoryIds, modelIds, userIds, text, tagId, startPublishDate, endPublishDate, pageIndex,
				pageSize);
	}

	/**
	 * @param siteId
	 * @param ids
	 */
	public void index(int siteId, Serializable[] ids) {
		dao.index(siteId, ids);
	}

	/**
	 * @return
	 */
	public Future<?> reCreateIndex() {
		return dao.reCreateIndex();
	}

	/**
	 * @param siteId
	 * @param status
	 * @param categoryId
	 * @param containChild
	 * @param categoryIds
	 * @param disabled
	 * @param modelIds
	 * @param parentId
	 * @param emptyParent
	 * @param onlyUrl
	 * @param hasImages
	 * @param hasFiles
	 * @param title
	 * @param userId
	 * @param checkUserId
	 * @param startPublishDate
	 * @param endPublishDate
	 * @param orderField
	 * @param orderType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = true)
	public PageHandler getPage(Integer siteId, Integer[] status, Integer categoryId, Boolean containChild, Integer[] categoryIds,
			Boolean disabled, String[] modelIds, Long parentId, Boolean emptyParent, Boolean onlyUrl, Boolean hasImages,
			Boolean hasFiles, String title, Long userId, Long checkUserId, Date startPublishDate, Date endPublishDate,
			String orderField, String orderType, Boolean queue, Integer pageIndex, Integer pageSize) {
		return dao.getPage(siteId, status, categoryId, getCategoryIds(containChild, categoryId, categoryIds), disabled, modelIds,
				parentId, emptyParent, onlyUrl, hasImages, hasFiles, title, userId, checkUserId, startPublishDate, endPublishDate,
				orderField, orderType, queue, pageIndex, pageSize);
	}

	/**
	 * @param id
	 * @param sort
	 * @param siteId
	 * @param status
	 * @param categoryId
	 * @param containChild
	 * @param categoryIds
	 * @param disabled
	 * @param modelIds
	 * @param parentId
	 * @param emptyParent
	 * @param onlyUrl
	 * @param hasImages
	 * @param hasFiles
	 * @param title
	 * @param userId
	 * @param checkUserId
	 * @param startPublishDate
	 * @param endPublishDate
	 * @param orderField
	 * @param orderType
	 * @param queue
	 * @param pageState
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = true)
	public PageHandler getStatePage(Long id, int sort, Integer siteId, Integer[] status, Integer categoryId, Boolean containChild, Integer[] categoryIds,
			Boolean disabled, String[] modelIds, Long parentId, Boolean emptyParent, Boolean onlyUrl, Boolean hasImages,
			Boolean hasFiles, String title, Long userId, Long checkUserId, Date startPublishDate, Date endPublishDate,
			String orderField, String orderType, Boolean queue, String pageState, Integer pageIndex, Integer pageSize) {
		return dao.getStatePage(id, sort, siteId, status, categoryId, getCategoryIds(containChild, categoryId, categoryIds), disabled, modelIds,
				parentId, emptyParent, onlyUrl, hasImages, hasFiles, title, userId, checkUserId, startPublishDate, endPublishDate,
				orderField, orderType, queue, pageState, pageIndex, pageSize);
	}

	/**
	 * @param siteId
	 * @param status
	 * @param categoryId
	 * @param containChild
	 * @param categoryIds
	 * @param disabled
	 * @param modelIds
	 * @param parentId
	 * @param emptyParent
	 * @param onlyUrl
	 * @param hasImages
	 * @param hasFiles
	 * @param title
	 * @param userId
	 * @param checkUserId
	 * @param startPublishDate
	 * @param endPublishDate
	 * @param orderField
	 * @param orderType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = true)
	public PageHandler getRelatedPage(Long id, Integer siteId, Integer[] status, Integer categoryId, Boolean containChild, Integer[] categoryIds,
			Boolean disabled, String[] modelIds, Long parentId, Boolean emptyParent, Boolean onlyUrl, Boolean hasImages,
			Boolean hasFiles, String title, Long userId, Long checkUserId, Date startPublishDate, Date endPublishDate,
			String orderField, String orderType, Integer pageIndex, Integer pageSize) {
		return dao.getRelatedPage(id, siteId, status, categoryId, getCategoryIds(containChild, categoryId, categoryIds), disabled, modelIds,
				parentId, emptyParent, onlyUrl, hasImages, hasFiles, title, userId, checkUserId, startPublishDate, endPublishDate,
				orderField, orderType, pageIndex, pageSize);
	}

	/**
	 * @param siteId
	 * @param ids
	 */
	public void refresh(int siteId, Serializable[] ids) {
		List<CmsContent> list = getEntitys(ids);
		Collections.reverse(list);
		for (CmsContent entity : list) {
			if (null != entity && STATUS_NORMAL == entity.getStatus() && siteId == entity.getSiteId()) {
				Date now = getDate();
				if (now.after(entity.getPublishDate())) {
					entity.setPublishDate(now);
				}
			}
		}
	}

	/**
	 * @param siteId
	 * @param userId
	 * @param ids
	 * @param refresh 
	 * @return
	 */
	public List<CmsContent> check(int siteId, Long userId, Serializable[] ids, Boolean refresh) {
		List<CmsContent> entityList = new ArrayList<>();
		for (CmsContent entity : getEntitys(ids)) {
			if (null != entity && siteId == entity.getSiteId() && STATUS_PEND == entity.getStatus()) {
				entity.setStatus(STATUS_NORMAL);
				entity.setCheckUserId(userId);
				entityList.add(entity);
				if (null != refresh && refresh) {
					Date now = getDate();
					if (now.after(entity.getPublishDate())) {
						entity.setPublishDate(now);
					}
				}
			}
		}
		return entityList;
	}

	/**
	 * @param siteId
	 * @param userId
	 * @param ids
	 * @return
	 */
	public List<CmsContent> uncheck(int siteId, Long userId, Serializable[] ids) {
		List<CmsContent> entityList = new ArrayList<>();
		for (CmsContent entity : getEntitys(ids)) {
			if (null != entity && siteId == entity.getSiteId() && STATUS_NORMAL == entity.getStatus()) {
				entity.setStatus(STATUS_PEND);
				entityList.add(entity);
			}
		}
		return entityList;
	}

	/**
	 * @param id
	 * @param tagIds
	 * @return
	 */
	public CmsContent updateTagIds(Serializable id, String tagIds) {
		CmsContent entity = getEntity(id);
		if (null != entity) {
			entity.setTagIds(tagIds);
		}
		return entity;
	}

	/**
	 * @param entitys
	 */
	public void updateStatistics(Collection<CmsContentStatistics> entitys) {
		for (CmsContentStatistics entityStatistics : entitys) {
			CmsContent entity = getEntity(entityStatistics.getId());
			if (null != entity) {
				entity.setClicks(entity.getClicks() + entityStatistics.getClicks());
				entity.setComments(entity.getComments() + entityStatistics.getComments());
				entity.setScores(entity.getScores() + entityStatistics.getScores());
			}
		}
	}

	/**
	 * @param siteId
	 * @param id
	 * @param categoryId
	 * @return
	 */
	public CmsContent updateCategoryId(int siteId, Serializable id, int categoryId) {
		CmsContent entity = getEntity(id);
		if (null != entity && siteId == entity.getSiteId()) {
			entity.setCategoryId(categoryId);
		}
		return entity;
	}

	/**
	 * @param id
	 * @param num
	 * @return
	 */
	public CmsContent updateChilds(Serializable id, int num) {
		CmsContent entity = getEntity(id);
		if (null != entity) {
			entity.setChilds(entity.getChilds() + num);
		}
		return entity;
	}

	public CmsContent sort(Integer siteId, Long id, int sort) {
		CmsContent entity = getEntity(id);
		if (null != entity && siteId == entity.getSiteId()) {
			entity.setSort(sort);
		}
		return entity;
	}

	/**
	 * @param id
	 * @param url
	 * @param hasStatic
	 * @return
	 */
	public CmsContent updateUrl(Serializable id, String url, boolean hasStatic) {
		CmsContent entity = getEntity(id);
		if (null != entity) {
			entity.setUrl(url);
			entity.setHasStatic(hasStatic);
		}
		return entity;
	}

	/**
	 * @param id
	 * @param url
	 * @param hasStatic
	 * @return
	 */
	public CmsContent updateMobileUrl(Serializable id, String url, boolean hasStatic) {
		CmsContent entity = getEntity(id);
		if (null != entity) {
			entity.setHasStatic(hasStatic);
		}
		return entity;
	}

	/**
	 * @param siteId
	 * @param categoryIds
	 * @return
	 */
	public int deleteByCategoryIds(int siteId, Integer[] categoryIds) {
		return dao.deleteByCategoryIds(siteId, categoryIds);
	}

	/**
	 * @param id
	 * @param clicks
	 * @return
	 */
	public CmsContent updateClicks(Long id, int clicks) {
		CmsContent entity = getEntity(id);
		if (null != entity) {
			entity.setClicks(clicks);
		}
		return entity;
	}

	/**
	 * @param siteId
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmsContent> delete(int siteId, Serializable[] ids) {
		List<CmsContent> entityList = new ArrayList<>();
		for (CmsContent entity : getEntitys(ids)) {
			if (siteId == entity.getSiteId() && !entity.isDisabled()) {
				if (0 < entity.getChilds()) {
					for (CmsContent child : (List<CmsContent>) getPage(siteId, null, null, null, null, false, null,
							entity.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null)
							.getList()) {
						child.setDisabled(true);
						entityList.add(child);
					}
				}
				entity.setDisabled(true);
				entityList.add(entity);
			}
		}
		return entityList;
	}

	private Integer[] getCategoryIds(Boolean containChild, Integer categoryId, Integer[] categoryIds) {
		if (empty(categoryId)) {
			return categoryIds;
		} else if (notEmpty(containChild) && containChild) {
			CmsCategory category = categoryDao.getEntity(categoryId);
			if (null != category && notEmpty(category.getChildIds())) {
				String[] categoryStringIds = add(splitByWholeSeparator(category.getChildIds(), COMMA_DELIMITED),
						String.valueOf(categoryId));
				categoryIds = new Integer[categoryStringIds.length + 1];
				for (int i = 0; i < categoryStringIds.length; i++) {
					categoryIds[i] = Integer.parseInt(categoryStringIds[i]);
				}
				categoryIds[categoryStringIds.length] = categoryId;
			}
		}
		return categoryIds;
	}

	/**
	 * @param siteId
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmsContent> recycle(int siteId, Serializable[] ids) {
		List<CmsContent> entityList = new ArrayList<>();
		for (CmsContent entity : getEntitys(ids)) {
			if (siteId == entity.getSiteId() && entity.isDisabled()) {
				if (0 < entity.getChilds()) {
					for (CmsContent child : (List<CmsContent>) getPage(siteId, null, null, null, null, false, null,
							entity.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null)
							.getList()) {
						child.setDisabled(false);
						entityList.add(child);
					}
				}
				entity.setDisabled(false);
				entityList.add(entity);
			}
		}
		return entityList;
	}

	/**
	 * @param siteId
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmsContent> realDelete(Integer siteId, Long[] ids) {
		List<CmsContent> entityList = new ArrayList<>();
		for (CmsContent entity : getEntitys(ids)) {
			if (siteId == entity.getSiteId() && entity.isDisabled()) {
				if (0 < entity.getChilds()) {
					for (CmsContent child : (List<CmsContent>) getPage(siteId, null, null, null, null, false, null,
							entity.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null)
							.getList()) {
						delete(child.getId());
					}
				}
				delete(entity.getId());
			}
		}
		return entityList;
	}

	/**
	 * @param id
	 * @param content
	 */
	@SuppressWarnings("unused")
	public void updateContent(Long id, CmsContent content) {
		CmsContent entity = getEntity(id);
		if (null != entity ) {
			if (null != entity) {
				update(entity.getId(), content, ignoreProperties);
			} else {
				delete(entity.getId());
			}
		} else {
			if (null != content) {
				content.setId(id);
				save(content);
			}
		}
	}

	@Autowired
	private CmsContentDao dao;
	@Autowired
	private CmsCategoryDao categoryDao;


}