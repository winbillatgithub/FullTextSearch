package com.as.filesearch.controller.portal.news;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.PortalBaseController;
import com.as.filesearch.base.entity.LoginUser;
import com.as.filesearch.base.helper.ContextHelper;
import com.as.filesearch.entity.NewsColumnEntity;
import com.as.filesearch.entity.NewsInfoEntity;
import com.as.filesearch.service.admin.news.INewsColumnEntityService;
import com.as.filesearch.service.admin.news.INewsInfoEntityService;

@Controller
@RequestMapping(value = "/portal/news/newsDetail")
public class NewsDetailController extends PortalBaseController {
	@Resource(name = "newsColumnEntityService")
	private INewsColumnEntityService newsColumnEntityService;

	@Resource(name = "newsInfoService")
	private INewsInfoEntityService newsInfoService;

	@RequestMapping(value = "/index")
	public ModelAndView newsDetail(HttpServletRequest req, HttpServletResponse response, HttpSession session) {

		try {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("portal/news/newsDetail");

			LoginUser currentLoginUser = this.getCurrentLoginUser();
			if (null == currentLoginUser) {
				modelAndView.addObject("currentLoginUser", currentLoginUser);
			}

			// 加载有效的新闻栏目
			List<NewsColumnEntity> newsColumnList = newsColumnEntityService.selectAllDistinctNewsColumns();
			modelAndView.addObject("newsColumnList", newsColumnList);
			// 加载新闻的类容
			NewsInfoEntity newsInfo = new NewsInfoEntity();
			String newsIdString = req.getParameter("newsId");
			if (null != newsIdString && !"".equals(newsIdString)) {
				newsInfo = newsInfoService.selectByPrimaryKey(Long.parseLong(newsIdString));
			}
			modelAndView.addObject("newsDetail", newsInfo);
			
			return modelAndView;
		} catch (Exception ex) {
			ex.printStackTrace();
			baseLogger.error(ex.getMessage());
			return null;
		}

	}

	
}
