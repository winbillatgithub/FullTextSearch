package com.as.filesearch.controller.portal.searchfile;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.as.filesearch.base.controller.PortalBaseController;
import com.as.filesearch.base.entity.SearchArticleCourtEntity;
import com.as.filesearch.base.entity.SearchArticleDateEntity;
import com.as.filesearch.base.entity.SearchArticleEntity;
import com.as.filesearch.base.entity.SearchArticleTypeEntity;
import com.as.filesearch.base.entity.SearchLableEntity;
import com.as.filesearch.base.entity.SearchTagEntity;


@Controller
@RequestMapping(value = "/portal/searchfile/searchFile")
public class SearchFile extends PortalBaseController {

	@RequestMapping(value = "/searchFile", method = RequestMethod.GET)
	public ModelAndView searchFile(HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		ModelAndView modelAndView = doSearch(req, response);
		modelAndView.setViewName("portal/search/search");
		return modelAndView;
	}

	@RequestMapping(value = "/typical", method = RequestMethod.GET)
	public ModelAndView searchTypicalCase(HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		Map<String, String> map= new HashMap<String, String>();
		map.put("guideCase", "true");
		map.put("keyword", req.getParameter("keyword"));
		return new ModelAndView(new RedirectView("docList.html"), map);
	}

	@RequestMapping(value = "/cause", method = RequestMethod.GET)
	public ModelAndView searchCause(HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		ModelAndView modelAndView = doSearch(req, response);
		modelAndView.setViewName("portal/search/searchCause");
		return modelAndView;
	}

	@RequestMapping(value = "/litigant", method = RequestMethod.GET)
	public ModelAndView searchLitigant(HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		ModelAndView modelAndView = doSearch(req, response);
		modelAndView.setViewName("portal/search/searchLitigant");
		return modelAndView;
	}

	@RequestMapping(value = "/court", method = RequestMethod.GET)
	public ModelAndView searchCourt(HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		ModelAndView modelAndView = doSearch(req, response);
		modelAndView.setViewName("portal/search/searchCourt");
		return modelAndView;
	}

	private ModelAndView doSearch(HttpServletRequest req, HttpServletResponse response) 
			throws IOException {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/search/search");			

		Map<String, Object> params = new HashMap<String, Object>();
		//
		//设置法庭搜索  guidecase:true/false
		String guideCase = req.getParameter("guideCase");
		if(null!=guideCase && !"".equals(guideCase)){
			params.put("guideCase", guideCase);
			modelAndView.addObject("guideCase", guideCase);
		} else {
			modelAndView.addObject("guideCase", "");
		}
		//
		// 设置搜索的关键字
		String searchKeyWord = req.getParameter("keyword") ;
		if(null!=searchKeyWord && !"".equals(searchKeyWord)){
			searchKeyWord = URLDecoder.decode( searchKeyWord,"UTF-8");
			searchKeyWord = searchKeyWord.trim();
			params.put("searchKeyWord", searchKeyWord);
			modelAndView.addObject("searchKeyWord", searchKeyWord);
		}
		//
		// 设置搜索的排序方式
		String sortDirection = req.getParameter("sortDirection");
		if (null == sortDirection || "".equals(sortDirection)) {
			sortDirection = "asc";
		}
		//
		//设置搜索的文书类型
		String articleType = req.getParameter("articleType");
		if(null!=articleType && !"".equals(articleType)){
			articleType = URLDecoder.decode( articleType,"UTF-8");
			params.put("articleType", articleType);
			modelAndView.addObject("articleType", articleType);
		}
		//
		//设置法庭搜索
		String articleCourt = req.getParameter("articleCourt");
		if(null!=articleCourt && !"".equals(articleCourt)){
			articleCourt = URLDecoder.decode( articleCourt,"UTF-8");
			params.put("articleCourt", articleCourt);
			modelAndView.addObject("articleCourt", articleCourt);
		}
		//
		//设置审判年份
		String judgeYear = req.getParameter("judgeYear");
		if(null!=judgeYear && !"".equals(judgeYear)){
			params.put("judgeYear", judgeYear);
			modelAndView.addObject("judgeYear", judgeYear);
		}
		//
		//设置案由
		String cause = req.getParameter("cause");
		if(null!=cause && !"".equals(cause)){
			cause = URLDecoder.decode( cause,"UTF-8");
			params.put("cause", cause);
			modelAndView.addObject("cause", cause);
		}
		//
		//设置法官
		String judge = req.getParameter("judge");
		if(null!=judge && !"".equals(judge)){
			judge = URLDecoder.decode( judge,"UTF-8");
			params.put("judge", judge);
			modelAndView.addObject("judge", judge);
		}
		//
		//设置当事人
		String litigant = req.getParameter("litigant");
		if(null!=litigant && !"".equals(litigant)){
			litigant = URLDecoder.decode( litigant,"UTF-8");
			params.put("litigant", litigant);
			modelAndView.addObject("litigant", litigant);
		}
		//
		//设置当事人原告
		String executor = req.getParameter("executor");
		if(null!=executor && !"".equals(executor)){
			executor = URLDecoder.decode( executor,"UTF-8");
			params.put("executor", executor);
			modelAndView.addObject("executor", executor);
		}
		//
		//设置当事人被告
		String executed = req.getParameter("executed");
		if(null!=executed && !"".equals(executed)){
			executed = URLDecoder.decode( executed,"UTF-8");
			params.put("executed", executed);
			modelAndView.addObject("executed", executed);
		}
		//
		//设置第三人
		String thirdpart = req.getParameter("thirdpart");
		if(null!=thirdpart && !"".equals(thirdpart)){
			thirdpart = URLDecoder.decode( thirdpart,"UTF-8");
			params.put("thirdpart", thirdpart);
			modelAndView.addObject("thirdpart", thirdpart);
		}
		//
		//设置律师
		String lawyer = req.getParameter("lawyer");
		if(null!=lawyer && !"".equals(lawyer)){
			lawyer = URLDecoder.decode( lawyer,"UTF-8");
			params.put("lawyer", lawyer);
			modelAndView.addObject("lawyer", lawyer);
		}
		//
		//设置律所
		String lawfirm = req.getParameter("lawfirm");
		if(null!=lawfirm && !"".equals(lawfirm)){
			lawfirm = URLDecoder.decode( lawfirm,"UTF-8");
			params.put("lawfirm", lawfirm);
			modelAndView.addObject("lawfirm", lawfirm);
		}
		//
		//设置法定代表人
		String legalPerson = req.getParameter("legalPerson");
		if(null!=legalPerson && !"".equals(legalPerson)){
			legalPerson = URLDecoder.decode( legalPerson,"UTF-8");
			params.put("legalPerson", legalPerson);
			modelAndView.addObject("legalPerson", legalPerson);
		}
		//
		//设置分组标签
		String label = req.getParameter("label");
		if(null!=label && !"".equals(label)){
			label = URLDecoder.decode( label,"UTF-8");
			params.put("label", label);
			modelAndView.addObject("label", label);
		}
		//
		//设置环助码
		String tag = req.getParameter("tag");
		if(null!=tag && !"".equals(tag)){
			tag = URLDecoder.decode( tag,"UTF-8");
			params.put("tag", tag);
			modelAndView.addObject("tag", tag);
		}

		// 设置搜索的页大小
		int pageSize = 10;
		String pageSizeStr = req.getParameter("pageSize");
		if (null != pageSizeStr && !"".equals(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		// 设置搜索的起始条目
		int startNum = 0;
		String currentPageNumStr = req.getParameter("pageId");
		if (null != currentPageNumStr && !"".equals(currentPageNumStr)) {
			int currentPageNum = Integer.parseInt(currentPageNumStr);
			startNum = currentPageNum * pageSize;
		}else{
			currentPageNumStr = "0";
		}

		// 显示搜索的内容
		//if (null != searchKeyWord && !"".equals(searchKeyWord)) {
		if (!params.isEmpty()) {	// Modified by winbill, it will be executed whenever params exist. 0924
			Map<String, Object> queryResultMap =  this.querySearchArticleEntities(startNum, pageSize,params);
			long totalSize = Long.parseLong(String.valueOf(queryResultMap.get("totalSize")));
			List<SearchArticleEntity> articleList = (List<SearchArticleEntity>)queryResultMap.get("dataList");
			List<SearchArticleTypeEntity> articleTypeList = (List<SearchArticleTypeEntity>) queryResultMap.get("articleTypeList");
			List<SearchArticleCourtEntity> articleCourtList = (List<SearchArticleCourtEntity>)queryResultMap.get("articleCourtList");
			List<SearchArticleDateEntity> articleDateList = (List<SearchArticleDateEntity> )queryResultMap.get("articleDateList");
			List<SearchLableEntity> articleLabelList = (List<SearchLableEntity> )queryResultMap.get("articleLabelList");
			List<SearchTagEntity> articleTagList = (List<SearchTagEntity> )queryResultMap.get("articleTagList");
			modelAndView.addObject("searchArticleList", articleList);
			modelAndView.addObject("articleTotalSize", totalSize);
			modelAndView.addObject("pageSize", pageSize);
			modelAndView.addObject("currentPageNum",currentPageNumStr);
			modelAndView.addObject("articleTypeList",articleTypeList);
			modelAndView.addObject("articleCourtList",articleCourtList);
			modelAndView.addObject("articleDateList",articleDateList);
			modelAndView.addObject("articleLabelList",articleLabelList);
			modelAndView.addObject("articleTagList",articleTagList);
		} else {
			modelAndView.addObject("articleTotalSize", 0);
			modelAndView.addObject("pageSize", 10);
			modelAndView.addObject("currentPageNum", 0);
		}
		params = null;
//		if (null != guideCase && !"".equals(guideCase)) {
//			modelAndView.setViewName("redirect:portal/search/search?guideCase=true");
//		} else {
//			modelAndView.setViewName("portal/search/search");			
//		}

		return modelAndView;
	}
}
