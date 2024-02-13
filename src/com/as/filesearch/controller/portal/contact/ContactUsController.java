package com.as.filesearch.controller.portal.contact;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.PortalBaseController;

@Controller
@RequestMapping(value = "/portal/contact/contactus")
public class ContactUsController extends PortalBaseController {

	@RequestMapping(value = "/contactusIndex")
	public ModelAndView contactModelAndView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/contact/contactus");
		return modelAndView;
	}
}
