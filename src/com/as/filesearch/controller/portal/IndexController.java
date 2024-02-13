package com.as.filesearch.controller.portal;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.AdminBaseController;
import com.as.filesearch.base.controller.PortalBaseController;

@Controller
@RequestMapping(value="/portal/index")
public class IndexController extends PortalBaseController {
	
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/index");
		return modelAndView;
	}
}
