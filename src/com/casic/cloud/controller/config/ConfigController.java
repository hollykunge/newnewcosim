package com.casic.cloud.controller.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.controller.BaseController;
@Controller
@RequestMapping("/cloud/config/")
public class ConfigController extends BaseController{
	@RequestMapping("outline")
	public ModelAndView outline(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = this.getAutoView();
		return mav;
	}
}
