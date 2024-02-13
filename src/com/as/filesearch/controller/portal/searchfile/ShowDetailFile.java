package com.as.filesearch.controller.portal.searchfile;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.PortalBaseController;
import com.as.filesearch.base.entity.LoginUser;
import com.as.filesearch.base.entity.SearchArticleEntity;
import com.as.filesearch.base.entity.SearchLableEntity;
import com.as.filesearch.base.entity.SearchTagEntity;
import com.as.filesearch.base.helper.DateHelper;
import com.as.filesearch.entity.FileCommentEntity;
import com.as.filesearch.service.admin.search.IFileCommentEntityService;

@Controller
@RequestMapping(value = "/portal/searchfile/showDetailFile")
public class ShowDetailFile extends PortalBaseController {

	@Resource(name = "fileCommentEntityService")
	private IFileCommentEntityService fileCommentEntityService;

	@RequestMapping(value = "/showDetailDoc", params = "articleId", method = RequestMethod.GET)
	public ModelAndView showDetailDoc(HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		baseLogger.trace("showDetailDoc ==>");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/search/showDetailFile");
		String articleId = req.getParameter("articleId");
		modelAndView.addObject("articleId", articleId);
		// 设置指导案例标志
		String guideCase = req.getParameter("guideCase");
		modelAndView.addObject("guideCase", guideCase);
		// 设置搜索的关键字
		String searchKeyWord = URLDecoder.decode(req.getParameter("keyword"),
				"UTF-8");
		modelAndView.addObject("searchKeyWord", searchKeyWord);
		Map<String, Object> queryResultMap = this.querySearchArticleById(searchKeyWord, articleId);
		String articleContentHtml = "";
		SearchArticleEntity articleList = (SearchArticleEntity) queryResultMap.get("entity");
		List<SearchLableEntity> articleLabelList = (List<SearchLableEntity> )queryResultMap.get("articleLabelList");
		List<SearchTagEntity> articleTagList = (List<SearchTagEntity> )queryResultMap.get("articleTagList");
		if (null != articleList && null != articleList.getArticleContentHtml()) {
			articleContentHtml = String.valueOf(articleList.getArticleContentHtml());
		}
		modelAndView.addObject("articleContentHtml", articleContentHtml);
		modelAndView.addObject("articleCaseNO", (articleList.getArticleCaseNO() == null) ? "" : articleList.getArticleCaseNO());
		modelAndView.addObject("articleCourt", articleList.getArticleCourt() == null ? "" : articleList.getArticleCourt());
		modelAndView.addObject("articleJudgeDate",articleList.getArticleJudgeDate() == null ? "" : articleList.getArticleJudgeDate());
		modelAndView.addObject("articleReason", articleList.getArticleReason() == null ? "" : articleList.getArticleReason());
		modelAndView.addObject("articleType", articleList.getArticleType() == null ? "" : articleList.getArticleType());
		modelAndView.addObject("articleStep", articleList.getArticleStep()== null ? "" : articleList.getArticleStep());
		modelAndView.addObject("articleTitle", articleList.getArticleTitle() == null ? "" : articleList.getArticleTitle());

		modelAndView.addObject("articleLabelList",articleLabelList);
		modelAndView.addObject("articleTagList",articleTagList);

		// 检索文书评论
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 设置搜索的分页大小
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
		} else {
			currentPageNumStr = "0";
		}

		paramMap.put("fileId", articleId);
		paramMap.put("sortOrder", "asc");// 排序方向
		paramMap.put("startNum", startNum);// 起始数据参数
		paramMap.put("pageSize", pageSize);// 分页数据大小
		Map<String, Object> commentResultMap = fileCommentEntityService
				.selectAllByPagination(paramMap);

		modelAndView.addObject("pageSize", pageSize);
		modelAndView.addObject("currentPageNum", currentPageNumStr);
		modelAndView.addObject("commentCount", commentResultMap.get("total"));
		modelAndView.addObject("commentList", commentResultMap.get("rows"));
		baseLogger.trace("showDetailDoc ==<");

		// 当前登录人
		LoginUser currentLoginUser = this.getCurrentLoginUser();
		if (null != currentLoginUser) {
			modelAndView.addObject("currentLoginUserName",
					currentLoginUser.getUsername());
		}

		return modelAndView;
	}

	@RequestMapping(value = "/logoutAction", method = RequestMethod.GET)
	@ResponseBody
	public boolean login(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			this.logoutCurrentLoginUser(request, response);

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/login.html");
			dispatcher.forward(request, response);
			return true;
		} catch (Exception ex) {
			baseLogger.trace(ex.getMessage());
			return false;
		}
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllNewsColumnJsonStr(String articleId ,String commentContent) {
		try {
			FileCommentEntity fileCommentEntity = new FileCommentEntity();
			LoginUser currLoginUser = this.getCurrentLoginUser();
			String currentLoginUserName = currLoginUser.getUsername();
			fileCommentEntity.setCommentBy(currentLoginUserName);
			Date currentDate = DateHelper.getCurDate("yyyy-MM-dd HH:mm:ss");
			fileCommentEntity.setCommentTime(currentDate);
			fileCommentEntity.setFileId(articleId);
			fileCommentEntity.setCommentContent(commentContent);
			fileCommentEntityService.insertSelective(fileCommentEntity);
		} catch (Exception ex) {
			baseLogger.trace(ex.getMessage());
			return jsonResult(false, "发表失败");
		}
		return jsonResult(true, "发表成功");
	}
	
	@RequestMapping(value = "/exportPDF")
	@ResponseBody
	public void exportPDF(HttpServletRequest request,
			HttpServletResponse response,String fileContent,String fileName){
		fileName = "测试.pdf";
		GenerateJudgeDPDF.exportSalesReport(request,response,fileContent,fileName);
	}
	

}
