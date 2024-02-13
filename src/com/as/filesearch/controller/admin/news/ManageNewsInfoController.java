package com.as.filesearch.controller.admin.news;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.AdminBaseController;
import com.as.filesearch.base.helper.DateHelper;
import com.as.filesearch.entity.NewsInfoEntity;
import com.as.filesearch.service.admin.news.INewsInfoEntityService;

@Controller
@RequestMapping(value = "/admin/news/manageNewsInfo")
public class ManageNewsInfoController extends AdminBaseController {

	@Resource(name = "newsInfoService")
	private INewsInfoEntityService newsInfoService;

	/**
	 * 新闻查询---草稿查询页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/news_Info_draft")
	public ModelAndView returnNewsInfoDraftView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/news/news_info_draft");
		return modelAndView;
	}

	/**
	 * 新闻查询--草稿的数据(查询状态为1)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/query_news_info_draft", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllNewsInfoDraftJsonStr(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("queryAllNewsInfoJsonStr()==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int startNum = pageSize * (pageId - 1);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("infoStatus", 1); // 1:为新建的草稿新闻
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		// 显示名查询
		String createTime_start = request.getParameter("createTime_start");
		if (null != createTime_start && !"".equals(createTime_start)) {
			paramMap.put("createTime_start", createTime_start);
		}

		// 显示名查询
		String createTime_end = request.getParameter("createTime_end");
		if (null != createTime_end && !"".equals(createTime_end)) {
			try {
				createTime_end = DateHelper.getAddDay(createTime_end, "DAY", 1, "yyyy-MM-dd");

			} catch (ParseException e) {
				baseLogger.error(e.getMessage());
				return null;
			}
			paramMap.put("createTime_end", createTime_end);
		}

		// 显示名查询
		String releaseTime_start = request.getParameter("releaseTime_start");
		if (null != releaseTime_start && !"".equals(releaseTime_start)) {
			paramMap.put("releaseTime_start", releaseTime_start);
		}

		// 显示名查询
		String releaseTime_end = request.getParameter("releaseTime_end");
		if (null != releaseTime_end && !"".equals(releaseTime_end)) {
			try {
				releaseTime_end = DateHelper.getAddDay(releaseTime_end, "DAY", 1, "yyyy-MM-dd");

			} catch (ParseException e) {
				baseLogger.error(e.getMessage());
				return null;
			}
			paramMap.put("releaseTime_end", releaseTime_end);
		}

		// 状态
		/*
		 * String infoStatus = request.getParameter("infoStatus"); if (null !=
		 * infoStatus && !"".equals(infoStatus)) { paramMap.put("infoStatus",
		 * infoStatus); }
		 */
		// 栏目
		String columnId = request.getParameter("columnId");
		if (null != columnId && !"".equals(columnId)) {
			paramMap.put("columnId", columnId);
		}

		// 新闻名称
		String infoName = request.getParameter("infoName");
		if (null != infoName && !"".equals(infoName)) {
			paramMap.put("infoName", infoName);
		}

		// 新闻标题
		String infoTitle = request.getParameter("infoTitle");
		if (null != infoTitle && !"".equals(infoTitle)) {
			paramMap.put("infoTitle", infoTitle);
		}

		// 排序字段
		String sortItemString = request.getParameter("sort");
		if ("newsId".equals(sortItemString)) {
			sortItemString = "news_id";
		} else if ("columnId".equals(sortItemString)) {
			sortItemString = "column_id";
		} else if ("rejectReason".equals(sortItemString)) {
			sortItemString = "reject_reason";
		} else if ("columnName".equals(sortItemString)) {
			sortItemString = "column_name";
		} else if ("createId".equals(sortItemString)) {
			sortItemString = "create_id";
		} else if ("createTime".equals(sortItemString)) {
			sortItemString = "create_time";
		} else if ("modifyId".equals(sortItemString)) {
			sortItemString = "modify_id";
		} else if ("modifyTime".equals(sortItemString)) {
			sortItemString = "modify_time";
		} else if ("releaseId".equals(sortItemString)) {
			sortItemString = "release_id";
		} else if ("releaseTime".equals(sortItemString)) {
			sortItemString = "release_time";
		} else if ("modifyId".equals(sortItemString)) {
			sortItemString = "modify_id";
		} else if ("modifyId".equals(sortItemString)) {
			sortItemString = "modify_id";
		}

		paramMap.put("sortItem", sortItemString);
		// 排序方向
		String sortDirectionString = request.getParameter("order");
		if (null == sortDirectionString || "".equals(sortDirectionString)) {
			sortDirectionString = "asc";
		}
		paramMap.put("sortOrder", sortDirectionString);
		Map<String, Object> paginationMap = newsInfoService.selectAllByPagination(paramMap);
		baseLogger.trace("queryAllNewsColumnJsonStr()<==");
		return paginationMap;
	}

	/**
	 * 新闻编辑的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/news_info_draft_detail")
	public ModelAndView returnNewsInfoDetailView(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("returnNewsInfoDetailView()==>");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/news/news_info_draft_detail");
		String infoIdString = request.getParameter("infoId");
		NewsInfoEntity newsInfoEntity = new NewsInfoEntity();
		if (null != infoIdString && !"".equals(infoIdString)) {
			Long columnId = Long.valueOf(infoIdString);
			newsInfoEntity = newsInfoService.selectByPrimaryKey(columnId);

		}
		modelAndView.addObject(newsInfoEntity);
		baseLogger.trace("returnNewsInfoDetailView()<==");
		return modelAndView;
	}

	/**
	 * 新闻编辑页面的保存
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @param newsInfo
	 * @return
	 */
	@RequestMapping(value = "/saveNewsInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map saveNewsInfoDetail(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			NewsInfoEntity newsInfo) {
		baseLogger.trace("saveNewsInfoDetail()==>");
		try {
			Date currentDate = DateHelper.getCurDate("yyyy-MM-dd HH:mm:ss");
			Long newsId = newsInfo.getNewsId();
			if (newsId != null) {
				newsInfo.setModifyId(new Long(222)); // 修改人
				newsInfo.setModifyTime(currentDate); // 修改时间
				newsInfoService.updateByPrimaryKeySelective(newsInfo);
				baseLogger.trace("saveNewsColumnDetail()<==修改成功");
				return jsonResult(true, "保存成功");
			} else {
				newsInfo.setCreateTime(currentDate);
				newsInfo.setModifyTime(currentDate);
				newsInfo.setCreateId(new Long(111)); // 创建人
				newsInfo.setModifyId(new Long(111)); // 修改人
				newsInfo.setStatus(1);
				newsInfo.setIsDelete(1);// 1未删除，0删除
				newsInfoService.insert(newsInfo);
				baseLogger.trace("saveNewsInfoDetail()<==增加成功");
				return jsonResult(true, "增加成功");
			}

		} catch (Exception ex) {
			baseLogger.error(ex.getMessage());
			return jsonResult(false, "保存失败");
		}

	}

	/**
	 * 删除新闻
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteNewsInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteNewsInfo(Long[] ids) {
		baseLogger.trace("deleteNewsColumn()==>");
		try {
			newsInfoService.deleteByPrimaryKeys(ids);
			baseLogger.trace("deleteNewsInfo()<==删除成功");
			return jsonResult(true, "删除成功");
		} catch (Exception e) {
			baseLogger.error(e.getMessage());
			baseLogger.trace("deleteNewsInfo()<==删除失败");
			return jsonResult(false, "删除失败");
		}
	}

	/**
	 * 新闻预览的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/news_info_draft_preview")
	public ModelAndView returnNewsInfoPreviewView(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("returnNewsInfoPreviewView()==>");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/news/news_info_draft_preview");
		String infoIdString = request.getParameter("infoId");
		NewsInfoEntity newsInfoEntity = new NewsInfoEntity();
		if (null != infoIdString && !"".equals(infoIdString)) {
			Long columnId = Long.valueOf(infoIdString);
			newsInfoEntity = newsInfoService.selectByPrimaryKey(columnId);

		}
		modelAndView.addObject(newsInfoEntity);
		baseLogger.trace("returnNewsInfoPreviewView()<==");
		return modelAndView;
	}

	@RequestMapping(value = "/approveNewsDraft", method = RequestMethod.POST)
	@ResponseBody
	public Map approveNewsDraft(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("approveNewsDraft()==>");
		try {
			Date currentDate = DateHelper.getCurDate("yyyy-MM-dd HH:mm:ss");
			String newsIdString = request.getParameter("newsId");
			Long newsId = Long.valueOf(newsIdString);
			NewsInfoEntity newsInfo = new NewsInfoEntity();
			newsInfo.setNewsId(newsId);
			newsInfo.setStatus(2); // 状态更改为2
			newsInfo.setModifyId(new Long(222)); // 修改人
			newsInfo.setModifyTime(currentDate); // 修改时间
			newsInfoService.updateByPrimaryKeySelective(newsInfo);
			baseLogger.trace("approveNewsDraft()<==提交成功");
			return jsonResult(true, "提交成功");

		} catch (Exception ex) {
			baseLogger.error(ex.getMessage());
			return jsonResult(false, "提交失败");
		}

	}

	/**
	 * 新闻查询---待发布页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/news_Info_under_release")
	public ModelAndView returnNewsInfoUnderReleaseView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/news/news_info_under_release");
		return modelAndView;
	}

	/**
	 * 新闻查询--待发布的数据(查询状态为2)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/query_news_info_under_release", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllNewsInfoUnderReleaseJsonStr(HttpServletRequest request,
			HttpServletResponse response) {
		baseLogger.trace("queryAllNewsInfoUnderReleaseJsonStr()==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int startNum = pageSize * (pageId - 1);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("infoStatus", 2); // 2:待发布
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		// 显示名查询
		String createTime_start = request.getParameter("createTime_start");
		if (null != createTime_start && !"".equals(createTime_start)) {
			paramMap.put("createTime_start", createTime_start);
		}

		// 显示名查询
		String createTime_end = request.getParameter("createTime_end");
		if (null != createTime_end && !"".equals(createTime_end)) {
			try {
				createTime_end = DateHelper.getAddDay(createTime_end, "DAY", 1, "yyyy-MM-dd");

			} catch (ParseException e) {
				baseLogger.error(e.getMessage());
				return null;
			}
			paramMap.put("createTime_end", createTime_end);
		}

		// 显示名查询
		String releaseTime_start = request.getParameter("releaseTime_start");
		if (null != releaseTime_start && !"".equals(releaseTime_start)) {
			paramMap.put("releaseTime_start", releaseTime_start);
		}

		// 显示名查询
		String releaseTime_end = request.getParameter("releaseTime_end");
		if (null != releaseTime_end && !"".equals(releaseTime_end)) {
			try {
				releaseTime_end = DateHelper.getAddDay(releaseTime_end, "DAY", 1, "yyyy-MM-dd");

			} catch (ParseException e) {
				baseLogger.error(e.getMessage());
				return null;
			}
			paramMap.put("releaseTime_end", releaseTime_end);
		}

		// 栏目
		String columnId = request.getParameter("columnId");
		if (null != columnId && !"".equals(columnId)) {
			paramMap.put("columnId", columnId);
		}

		// 新闻名称
		String infoName = request.getParameter("infoName");
		if (null != infoName && !"".equals(infoName)) {
			paramMap.put("infoName", infoName);
		}

		// 新闻标题
		String infoTitle = request.getParameter("infoTitle");
		if (null != infoTitle && !"".equals(infoTitle)) {
			paramMap.put("infoTitle", infoTitle);
		}

		// 排序字段
		String sortItemString = request.getParameter("sort");
		if ("newsId".equals(sortItemString)) {
			sortItemString = "news_id";
		} else if ("columnId".equals(sortItemString)) {
			sortItemString = "column_id";
		} else if ("rejectReason".equals(sortItemString)) {
			sortItemString = "reject_reason";
		} else if ("columnName".equals(sortItemString)) {
			sortItemString = "column_name";
		} else if ("createId".equals(sortItemString)) {
			sortItemString = "create_id";
		} else if ("createTime".equals(sortItemString)) {
			sortItemString = "create_time";
		} else if ("modifyId".equals(sortItemString)) {
			sortItemString = "modify_id";
		} else if ("modifyTime".equals(sortItemString)) {
			sortItemString = "modify_time";
		} else if ("releaseId".equals(sortItemString)) {
			sortItemString = "release_id";
		} else if ("releaseTime".equals(sortItemString)) {
			sortItemString = "release_time";
		} else if ("modifyId".equals(sortItemString)) {
			sortItemString = "modify_id";
		} else if ("modifyId".equals(sortItemString)) {
			sortItemString = "modify_id";
		}

		paramMap.put("sortItem", sortItemString);
		// 排序方向
		String sortDirectionString = request.getParameter("order");
		if (null == sortDirectionString || "".equals(sortDirectionString)) {
			sortDirectionString = "asc";
		}
		paramMap.put("sortOrder", sortDirectionString);
		Map<String, Object> paginationMap = newsInfoService.selectAllByPagination(paramMap);
		baseLogger.trace("queryAllNewsInfoUnderReleaseJsonStr()<==");
		return paginationMap;
	}

	/**
	 * 新闻查询--待发布的数据（发布）
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/releaseNews", method = RequestMethod.POST)
	@ResponseBody
	public Map releaseNews(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("releaseNews()==>");
		try {
			Date currentDate = DateHelper.getCurDate("yyyy-MM-dd HH:mm:ss");
			String newsIdString = request.getParameter("newsId");
			Long newsId = Long.valueOf(newsIdString);
			NewsInfoEntity newsInfo = new NewsInfoEntity();
			newsInfo.setNewsId(newsId);
			newsInfo.setStatus(3); // 状态更改为3发布

			newsInfo.setReleaseId(new Long(222)); // 修改人
			newsInfo.setReleaseTime(currentDate); // 修改时间
			newsInfoService.updateByPrimaryKeySelective(newsInfo);
			baseLogger.trace("releaseNews()<==提交成功");
			return jsonResult(true, "发布成功");

		} catch (Exception ex) {
			baseLogger.error(ex.getMessage());
			return jsonResult(false, "发布失败");
		}
	}
	
	/**
	 * 新闻查询--待发布的数据(驳回到草稿)
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rejectToDrafts", method = RequestMethod.POST)
	@ResponseBody
	public Map rejectToDrafts(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("rejectToDrafts()==>");
		try {
			String newsIdString = request.getParameter("newsId");
			Long newsId = Long.valueOf(newsIdString);
			NewsInfoEntity newsInfo = new NewsInfoEntity();
			newsInfo.setNewsId(newsId);
			newsInfo.setStatus(1); // 状态更改为2

			newsInfoService.updateByPrimaryKeySelective(newsInfo);
			baseLogger.trace("rejectToDrafts()<==取消成功");
			return jsonResult(true, "取消成功");

		} catch (Exception ex) {
			baseLogger.error(ex.getMessage());
			return jsonResult(false, "取消失败");
		}
	}


	/**
	 * 新闻查询---发布页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/news_Info_release")
	public ModelAndView returnNewsInfoReleaseView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/news/news_info_release");
		return modelAndView;
	}

	/**
	 * 新闻查询--发布页面草稿的数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/query_news_info_release", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllNewsInfoReleaseJsonStr(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("queryAllNewsInfoReleaseJsonStr()==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int startNum = pageSize * (pageId - 1);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("infoStatus", 3); // 1:为新建的草稿新闻
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		// 显示名查询
		String createTime_start = request.getParameter("createTime_start");
		if (null != createTime_start && !"".equals(createTime_start)) {
			paramMap.put("createTime_start", createTime_start);
		}

		// 显示名查询
		String createTime_end = request.getParameter("createTime_end");
		if (null != createTime_end && !"".equals(createTime_end)) {
			try {
				createTime_end = DateHelper.getAddDay(createTime_end, "DAY", 1, "yyyy-MM-dd");

			} catch (ParseException e) {
				baseLogger.error(e.getMessage());
				return null;
			}
			paramMap.put("createTime_end", createTime_end);
		}

		// 显示名查询
		String releaseTime_start = request.getParameter("releaseTime_start");
		if (null != releaseTime_start && !"".equals(releaseTime_start)) {
			paramMap.put("releaseTime_start", releaseTime_start);
		}

		// 显示名查询
		String releaseTime_end = request.getParameter("releaseTime_end");
		if (null != releaseTime_end && !"".equals(releaseTime_end)) {
			try {
				releaseTime_end = DateHelper.getAddDay(releaseTime_end, "DAY", 1, "yyyy-MM-dd");

			} catch (ParseException e) {
				baseLogger.error(e.getMessage());
				return null;
			}
			paramMap.put("releaseTime_end", releaseTime_end);
		}

		// 栏目
		String columnId = request.getParameter("columnId");
		if (null != columnId && !"".equals(columnId)) {
			paramMap.put("columnId", columnId);
		}

		// 新闻名称
		String infoName = request.getParameter("infoName");
		if (null != infoName && !"".equals(infoName)) {
			paramMap.put("infoName", infoName);
		}

		// 新闻标题
		String infoTitle = request.getParameter("infoTitle");
		if (null != infoTitle && !"".equals(infoTitle)) {
			paramMap.put("infoTitle", infoTitle);
		}

		// 排序字段
		String sortItemString = request.getParameter("sort");
		if ("newsId".equals(sortItemString)) {
			sortItemString = "news_id";
		} else if ("columnId".equals(sortItemString)) {
			sortItemString = "column_id";
		} else if ("rejectReason".equals(sortItemString)) {
			sortItemString = "reject_reason";
		} else if ("columnName".equals(sortItemString)) {
			sortItemString = "column_name";
		} else if ("createId".equals(sortItemString)) {
			sortItemString = "create_id";
		} else if ("createTime".equals(sortItemString)) {
			sortItemString = "create_time";
		} else if ("modifyId".equals(sortItemString)) {
			sortItemString = "modify_id";
		} else if ("modifyTime".equals(sortItemString)) {
			sortItemString = "modify_time";
		} else if ("releaseId".equals(sortItemString)) {
			sortItemString = "release_id";
		} else if ("releaseTime".equals(sortItemString)) {
			sortItemString = "release_time";
		} else if ("modifyId".equals(sortItemString)) {
			sortItemString = "modify_id";
		} else if ("modifyId".equals(sortItemString)) {
			sortItemString = "modify_id";
		}

		paramMap.put("sortItem", sortItemString);
		// 排序方向
		String sortDirectionString = request.getParameter("order");
		if (null == sortDirectionString || "".equals(sortDirectionString)) {
			sortDirectionString = "asc";
		}
		paramMap.put("sortOrder", sortDirectionString);
		Map<String, Object> paginationMap = newsInfoService.selectAllByPagination(paramMap);
		baseLogger.trace("queryAllNewsInfoReleaseJsonStr()<==");
		return paginationMap;
	}
	
	/**
	 * 新闻查询--发布页面 驳回到待发布
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rejectToUnderRelease", method = RequestMethod.POST)
	@ResponseBody
	public Map rejectToUnderRelease(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("rejectToUnderRelease()==>");
		try {
			String newsIdString = request.getParameter("newsId");
			Long newsId = Long.valueOf(newsIdString);
			NewsInfoEntity newsInfo = new NewsInfoEntity();
			newsInfo.setNewsId(newsId);
			newsInfo.setStatus(2); // 状态更改为2

			newsInfoService.updateByPrimaryKeySelective(newsInfo);
			baseLogger.trace("rejectToUnderRelease()<==取消成功");
			return jsonResult(true, "取消成功");

		} catch (Exception ex) {
			baseLogger.error(ex.getMessage());
			return jsonResult(false, "取消失败");
		}
	}

}
