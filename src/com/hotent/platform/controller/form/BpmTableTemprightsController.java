package com.hotent.platform.controller.form;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.BpmDefRights;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.form.BpmTableTemplate;
import com.hotent.platform.model.form.BpmTableTemprights;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.service.form.BpmTableTemplateService;
import com.hotent.platform.service.form.BpmTableTemprightsService;
import com.hotent.platform.service.system.GlobalTypeService;
import com.hotent.core.web.ResultMessage;

/**
 * 对象功能:查看业务数据模板的权限信息 控制器类 开发公司:广州宏天软件有限公司 开发人员:heyifan 创建时间:2012-05-28 09:04:06
 */
@Controller
@RequestMapping("/platform/form/bpmTableTemprights/")
public class BpmTableTemprightsController extends BaseController {
	@Resource
	private BpmTableTemprightsService bpmTableTemprightsService;
	@Resource
	private BpmTableTemplateService bpmTableTemplateService;
	@Resource
	private GlobalTypeService globalTypeService;

	/**
	 * 取得查看业务数据模板的权限信息分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看查看业务数据模板的权限信息分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		Long id = RequestUtil.getLong(request, "id");
		int type = RequestUtil.getInt(request, "type");
		ModelAndView mv = this.getAutoView();
		Map<String, Map<String, String>> rightsMap = bpmTableTemprightsService.getRights(id, type);
		// 定义
		if (type == BpmDefRights.SEARCH_TYPE_DEF) {
			BpmTableTemplate bpmTableTemplate = bpmTableTemplateService.getById(id);
			mv.addObject("bpmTableTemplate", bpmTableTemplate);
		} else {
			GlobalType globalType = globalTypeService.getById(id);
			mv.addObject("globalType", globalType);
		}
		mv.addObject("rightsMap", rightsMap).addObject("id", id).addObject("type", type);
		return mv;
	}

	/**
	 * 删除查看业务数据模板的权限信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除查看业务数据模板的权限信息")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			bpmTableTemprightsService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除查看业务数据模板的权限信息成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description = "编辑查看业务数据模板的权限信息")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		BpmTableTemprights bpmTableTemprights = null;
		if (id != 0) {
			bpmTableTemprights = bpmTableTemprightsService.getById(id);
		} else {
			bpmTableTemprights = new BpmTableTemprights();
		}
		return getAutoView().addObject("bpmTableTemprights", bpmTableTemprights).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得查看业务数据模板的权限信息明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看查看业务数据模板的权限信息明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		BpmTableTemprights bpmTableTemprights = bpmTableTemprightsService.getById(id);
		return getAutoView().addObject("bpmTableTemprights", bpmTableTemprights);
	}

}
