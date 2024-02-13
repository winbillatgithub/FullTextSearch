package com.as.filesearch.controller.admin.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.as.filesearch.base.helper.ContextHelper;
import com.as.filesearch.base.helper.DateHelper;
import com.as.filesearch.entity.NewsColumnEntity;
import com.as.filesearch.service.admin.news.INewsColumnEntityService;

@Controller
@RequestMapping(value = "/admin/news/manageNewsColumn")
public class ManageNewsColumnController extends AdminBaseController {
	@Resource(name = "newsColumnEntityService")
	private INewsColumnEntityService newsColumnEntityService;

	/**
	 * 返回新闻栏目的主查询页面
	 * @return
	 */
	@RequestMapping(value = "/news_column")
	public ModelAndView returnNewsColumnView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/news/news_column");
		return modelAndView;
	}

	/**
	 * 查询新闻栏目的内容
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/query_news_column", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllNewsColumnJsonStr(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("queryAllNewsColumnJsonStr()==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int startNum = pageSize * (pageId - 1);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		// 显示名查询
		String columnNameString = request.getParameter("columnName");
		if (null != columnNameString && !"".equals(columnNameString)) {
			paramMap.put("columnName", columnNameString);
		}
		// 性别查询
		String statusString = request.getParameter("status");
		if (null != statusString && !"".equals(statusString)) {
			paramMap.put("status", statusString);
		}

		// 排序字段
		String sortItemString = request.getParameter("sort");
		if ("columnId".equals(sortItemString)) {
			sortItemString = "column_Id";
		} else if ("columnName".equals(sortItemString)) {
			sortItemString = "column_Name";
		} else if ("status".equals(sortItemString)) {
			sortItemString = "status";
		} else if ("createId".equals(sortItemString)) {
			sortItemString = "create_Id";
		} else if ("createTime".equals(sortItemString)) {
			sortItemString = "create_Time";
		} else if ("modifyId".equals(sortItemString)) {
			sortItemString = "modify_Id";
		} else if ("modifyTime".equals(sortItemString)) {
			sortItemString = "modify_Time";
		}

		paramMap.put("sortItem", sortItemString);
		// 排序方向
		String sortDirectionString = request.getParameter("order");
		if (null == sortDirectionString || "".equals(sortDirectionString)) {
			sortDirectionString = "asc";
		}
		paramMap.put("sortOrder", sortDirectionString);
		Map<String, Object> paginationMap = newsColumnEntityService.selectAllByPagination(paramMap);
		baseLogger.trace("queryAllNewsColumnJsonStr()<==");
		return paginationMap;
	}

	/**
	 * 返回新闻栏目的编辑弹出页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/news_column_detail")
	public ModelAndView returnNewsColumnDetailView(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("returnNewsColumnDetailView()==>");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/news/news_column_detail");
		String columnIdString = request.getParameter("columnId");
		NewsColumnEntity newsColumnEntity = new NewsColumnEntity();
		if (null != columnIdString && !"".equals(columnIdString)) {
			Long columnId = Long.valueOf(columnIdString);
			newsColumnEntity = newsColumnEntityService.selectByPrimaryKey(columnId);

		}
		modelAndView.addObject(newsColumnEntity);
		baseLogger.trace("returnNewsColumnDetailView()<==");
		return modelAndView;
	}

	/**
	 * 保存新闻栏目的编辑
	 * @param session
	 * @param request
	 * @param response
	 * @param newsColumn
	 * @return
	 */
	@RequestMapping(value = "/saveNewsColumn", method = RequestMethod.POST)
	@ResponseBody
	public Map saveNewsColumnDetail(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			NewsColumnEntity newsColumn) {
		baseLogger.trace("saveNewsColumnDetail()==>");
		try {
			Date currentDate = DateHelper.getCurDate("yyyy-MM-dd HH:mm:ss");
			Long columnId = newsColumn.getColumnId();
			if (columnId != null) {
				newsColumn.setModifyId(new Long(222)); // 修改人
				newsColumn.setModifyTime(currentDate); // 修改时间
				newsColumnEntityService.updateByPrimaryKeySelective(newsColumn);
				baseLogger.trace("saveNewsColumnDetail()<==修改成功");
				return jsonResult(true, "保存成功");
			} else {
				newsColumn.setCreateTime(currentDate);
				newsColumn.setModifyTime(currentDate);
			
				newsColumn.setCreateId(new Long(111)); // 创建人
				newsColumn.setModifyId(new Long(111)); // 修改人
				newsColumn.setStatus(1);

				newsColumnEntityService.insertSelective(newsColumn);
				baseLogger.trace("saveNewsColumnDetail()<==增加成功");
				return jsonResult(true, "增加成功");
			}

		} catch (Exception ex) {
			baseLogger.error(ex.getMessage());
			return jsonResult(false, "保存失败");
		}

	}

	/**
	 * 删除新闻栏目
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteNewsColumn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteNewsColumn(Long[] ids) {
		baseLogger.trace("deleteNewsColumn()==>");
		try {
			newsColumnEntityService.deleteByPrimaryKeys(ids);
			baseLogger.trace("deleteNewsColumn()<==删除成功");
			return jsonResult(true, "删除成功");
		} catch (Exception e) {
			baseLogger.error(e.getMessage());
			baseLogger.trace("deleteNewsColumn()<==删除失败");
			return jsonResult(false, "删除失败");
		}
	}

	@RequestMapping(value = "/getAllDistinctNewsColumns")
	@ResponseBody
	public List<NewsColumnEntity> getAllDistinctNewsColumns(){
		baseLogger.trace("getAllDistinctNewsColumns()==>");
		List<NewsColumnEntity> list=new ArrayList<NewsColumnEntity>();
		try {
			list= newsColumnEntityService.selectAllDistinctNewsColumns();
		} catch (Exception e) {
			baseLogger.error(e.getMessage());
		}
		baseLogger.trace("getAllDistinctNewsColumns()<==");
		return list;
	}
}
