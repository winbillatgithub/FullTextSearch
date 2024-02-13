package com.as.filesearch.controller.admin;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.AdminBaseController;
import com.as.filesearch.base.helper.ContextHelper;
import com.as.filesearch.base.helper.JsonHelper;
import com.as.filesearch.entity.SysConfigEntity;
import com.as.filesearch.entity.SysMenuEntity;
import com.as.filesearch.service.admin.config.ISysConfigEntityService;
import com.as.filesearch.service.admin.menu.ISysMenuEntityService;

@Controller
@RequestMapping(value = "/admin/adminIndex")
public class AdminIndexController extends AdminBaseController {

	@Resource(name = "sysMenuEntityService")
	private ISysMenuEntityService sysMenuEntityService;

	@RequestMapping(value = "/index")
	public ModelAndView adminIndex() {
		baseLogger.trace("adminIndex()==>进入后台");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/admin_index");
		return modelAndView;
	}

	@RequestMapping(value = "/queryMenu", method = RequestMethod.POST)
	public void queryMenuJsonStr(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("queryMenuJsonStr()==>");
		List<SysMenuEntity> sysMenu = sysMenuEntityService.selectAll();
		String returnString = JsonHelper.beanListToJson(sysMenu);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(returnString);
			out.close();
			baseLogger.trace("queryMenuJsonStr()<==");
		} catch (Exception e) {
			baseLogger.trace("queryMenuJsonStr()==>异常");
			baseLogger.error(e.getMessage());
			e.printStackTrace();
		}

	}

}
