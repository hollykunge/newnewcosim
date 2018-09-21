package com.casic.cloud.controller.config.enterprise.org;

import java.util.Date;
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
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysOrgService;

/**
 * 对象功能:组织架构 控制器类 开发公司: 开发人员:pkq 创建时间:2011-12-02 10:41:53
 */
@Controller
@RequestMapping("/cloud/config/enterprise/org")
public class EnterpriseOrgFormController extends BaseFormController {
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private IAuthenticate iAuthenticate;

	/**
	 * 添加或更新组织架构。
	 * 
	 * @param request
	 * @param response
	 * @param sysOrg
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新组织架构")
	public void save(HttpServletRequest request, HttpServletResponse response, ISysOrg sysOrg, BindingResult bindResult) throws Exception {
		
		ResultMessage resultMessage = validForm("sysOrg", sysOrg, bindResult, request);
		if (resultMessage.getResult() == ResultMessage.Fail) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		
		
		if (sysOrg.getOrgId() == null) {
			Long orgId=UniqueIdUtil.genId();
			sysOrg.setOrgId(orgId);
			sysOrg.setCreatorId(ContextUtil.getCurrentUserId());
			Long supOrgId=RequestUtil.getLong(request, "orgSupId");
			ISysOrg supOrg = sysOrgService.getById(supOrgId);
			// 若以维度为父节点新建，则设置其Path为维度ID+该组织ID
			if (supOrg == null) {
				sysOrg.setPath(supOrgId + "." + orgId +".");
			} else {
				sysOrg.setPath(supOrg.getPath() + sysOrg.getOrgId() +".");
			}
			sysOrg.setOrgSupId(supOrgId);
			sysOrg.setSn(supOrg.getSn());
			sysOrgService.addOrg(sysOrg);
			
			writeResultMessage(response.getWriter(), "{\"orgId\":\""+ orgId +"\",\"action\":\"add\"}", ResultMessage.Success);
		} else {
			sysOrg.setUpdateId(ContextUtil.getCurrentUserId());
			sysOrgService.updOrg(sysOrg);
			
			writeResultMessage(response.getWriter(), "{\"orgId\":\""+ sysOrg.getOrgId() +"\",\"action\":\"upd\"}", ResultMessage.Success);
		}
		
	}

	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * 
	 * @param orgId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModelAttribute
	protected ISysOrg getFormObject(@RequestParam("orgId") Long orgId, Model model) throws Exception {
		logger.debug("enter ISysOrg getFormObject here....");
		ISysOrg sysOrg = null;
		if (orgId != null) {
			sysOrg = sysOrgService.getById(orgId);
		} else {
			sysOrg = iAuthenticate.getNewSysOrg();
		}
		return sysOrg;
	}

}
