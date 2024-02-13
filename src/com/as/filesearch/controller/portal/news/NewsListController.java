package com.as.filesearch.controller.portal.news;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.PortalBaseController;
import com.as.filesearch.entity.NewsColumnEntity;
import com.as.filesearch.entity.NewsInfoEntity;
import com.as.filesearch.service.admin.news.INewsColumnEntityService;
import com.as.filesearch.service.admin.news.INewsInfoEntityService;

@Controller
@RequestMapping(value = "/portal/news/newsList")
public class NewsListController extends PortalBaseController {
	@Resource(name = "newsColumnEntityService")
	private INewsColumnEntityService newsColumnEntityService;

	@Resource(name = "newsInfoService")
	private INewsInfoEntityService newsInfoService;

	/**
	 * 请求的主新闻列表页面
	 * 
	 * @param req
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView newsList(HttpServletRequest req, HttpServletResponse response) throws IOException {
		baseLogger.trace("newsList()==>");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/news/newsList");
		Map<String, Object> paramMap = new HashMap<String, Object>();

		// 加载有效的新闻栏目
		List<NewsColumnEntity> newsColumnList = newsColumnEntityService.selectAllDistinctNewsColumns();
		modelAndView.addObject("newsColumnList", newsColumnList);

		// 设置搜索的分页大小
		int pageSize = 10;
		String pageSizeStr = req.getParameter("pageSize");
		if (null != pageSizeStr && !"".equals(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);

		}
		modelAndView.addObject("pageSize", pageSize);
		// 设置搜索的起始条目
		int startNum = 0;
		String currentPageNumStr = req.getParameter("pageId");
		if (null != currentPageNumStr && !"".equals(currentPageNumStr)) {
			int currentPageNum = Integer.parseInt(currentPageNumStr);
			startNum = currentPageNum * pageSize;
		} else {
			currentPageNumStr = "0";
		}
		modelAndView.addObject("currentPageNum", currentPageNumStr);
		
		
		String columnIdString = req.getParameter("columnId");
		if(null!=columnIdString && !"".equals(columnIdString)){
			for(NewsColumnEntity newsColumnItem : newsColumnList){
				if(columnIdString.equals(String.valueOf(newsColumnItem.getColumnId()))){
					modelAndView.addObject("columnId", columnIdString);
					modelAndView.addObject("columnName" ,newsColumnItem.getColumnName());
					break;
				}
			}
			
			paramMap.put("columnId", columnIdString);
			
		}

		// 设置搜索的关键字
		String searchKeyWord = req.getParameter("keyword");
		if (null != searchKeyWord && !"".equals(searchKeyWord) && !"undefined".equals(searchKeyWord)) {
			String encodeSearchKeyWords = URLDecoder.decode(searchKeyWord, "UTF-8");
			encodeSearchKeyWords = encodeSearchKeyWords.trim();
			// 排序字段
			paramMap.put("keyWords", encodeSearchKeyWords);
			modelAndView.addObject("searchKeyWord", encodeSearchKeyWords);
		}

		

		// 排序字段
		paramMap.put("sortItem", "release_time");
		// 排序方向
		paramMap.put("sortOrder", "desc");
		
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);

		Map<String, Object> paginationMap = newsInfoService.selectAllByPagination_Portal(paramMap);
		modelAndView.addObject("totalNewsSize", paginationMap.get("total"));
		modelAndView.addObject("newsList", paginationMap.get("rows"));
		baseLogger.trace("newsList()<==");
		return modelAndView;
	}


}
