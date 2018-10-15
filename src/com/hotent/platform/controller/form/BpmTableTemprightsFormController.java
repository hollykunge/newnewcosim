package com.hotent.platform.controller.form;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.util.RequestUtil;

import com.hotent.platform.model.form.BpmTableTemprights;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.form.BpmTableTemprightsService;

/**
 * 对象功能:查看业务数据模板的权限信息 控制器类 开发公司:广州宏天软件有限公司 开发人员:heyifan 创建时间:2012-05-28 09:04:06
 */
@Controller
@RequestMapping("/platform/form/bpmTableTemprights/")
public class BpmTableTemprightsFormController extends BaseFormController {
	@Resource
	private BpmTableTemprightsService bpmTableTemprightsService;

	/**
	 * 添加或更新查看业务数据模板的权限信息。
	 * 
	 * @param request
	 * @param response
	 * @param bpmTableTemprights
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新查看业务数据模板的权限信息")
	public void save(HttpServletRequest request, HttpServletResponse response, BpmTableTemprights bpmTableTemprights, BindingResult bindResult)
			throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		int type = RequestUtil.getInt(request, "type");
		String[] rightType = request.getParameterValues("rightType");
		String[] ownerId = request.getParameterValues("ownerId");
		String[] ownerName = request.getParameterValues("ownerName");

		try {
			bpmTableTemprightsService.saveRights(id, type, rightType, ownerId, ownerName);
			writeResultMessage(response.getWriter(), "设置权限成功!", ResultMessage.Success);
		} catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"设置权限失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * 
	 * @param ID
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModelAttribute
	protected BpmTableTemprights getFormObject(@RequestParam("id") Long id, Model model) throws Exception {
		logger.debug("enter BpmTableTemprights getFormObject here....");
		BpmTableTemprights bpmTableTemprights = null;
		if (id != null) {
			bpmTableTemprights = bpmTableTemprightsService.getById(id);
		} else {
			bpmTableTemprights = new BpmTableTemprights();
		}
		return bpmTableTemprights;
	}

}
