package com.hotent.platform.controller.system;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
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
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmAgent;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.system.SysUserAgent;
import com.hotent.platform.service.bpm.BpmAgentService;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.system.SysUserAgentService;

/**
 * 对象功能:SYS_USER_AGENT 控制器类 开发公司: 开发人员:csx 创建时间:2011-12-27 11:54:23
 */
@Controller
@RequestMapping("/platform/system/sysUserAgent/")
public class SysUserAgentFormController extends BaseFormController {
	@Resource
	private SysUserAgentService sysUserAgentService;
	@Resource
	private BpmAgentService bpmAgentService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;

	/**
	 * 添加或更新SYS_USER_AGENT。
	 * 
	 * @param request
	 * @param response
	 * @param sysUserAgent
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新SYS_USER_AGENT")
	public void save(HttpServletRequest request, HttpServletResponse response, SysUserAgent sysUserAgent, BindingResult bindResult) throws Exception {

		ResultMessage resultMessage = validForm("sysUserAgent", sysUserAgent, bindResult, request);
		// add your custom validation rule here such as below code:
		// bindResult.rejectValue("name","errors.exist.student",new
		// Object[]{"jason"},"重复姓名");
		if (resultMessage.getResult() == ResultMessage.Fail) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		List<BpmAgent> bpmAgentList = getBpmAgents(request);
		if (sysUserAgent.getAgentid() == null) {
			ISysUser su = ContextUtil.getCurrentUser();
			sysUserAgent.setAgentuserid(su.getUserId());
			sysUserAgent.setAgentid(UniqueIdUtil.genId());
			sysUserAgentService.add(sysUserAgent,bpmAgentList);
			resultMsg = getText("record.added", "用户代理");
		} else {
			sysUserAgentService.update(sysUserAgent,bpmAgentList);
			resultMsg = getText("record.updated", "用户代理");
		}
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
	}

	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * 
	 * @param agentid
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModelAttribute
	protected SysUserAgent getFormObject(@RequestParam("agentid") Long agentid, Model model) throws Exception {
		logger.debug("enter SysUserAgent getFormObject here....");
		SysUserAgent sysUserAgent = null;
		if (agentid != null) {
			sysUserAgent = sysUserAgentService.getById(agentid);
		} else {
			sysUserAgent = new SysUserAgent();
		}
		return sysUserAgent;
	}

	protected List<BpmAgent> getBpmAgents(HttpServletRequest request) throws Exception {

		List<BpmAgent> list = new ArrayList<BpmAgent>();

		String[] agentId = request.getParameterValues("agentId");
		String[] defKey = request.getParameterValues("defKey");
		
		if (defKey != null && defKey.length > 0) {
			for (int i = 0; i < defKey.length; i++) {
				if (StringUtils.isNotEmpty(defKey[i])) {
					BpmAgent bpmAgent = null;
					if(agentId!=null && StringUtils.isNotEmpty(agentId[i])){
						bpmAgent = bpmAgentService.getById(new Long(agentId[i]));
					}else{
						bpmAgent = new BpmAgent();
						bpmAgent.setId(UniqueIdUtil.genId());
					}
					BpmDefinition bpmDefinition = bpmDefinitionService.getMainByDefKey(defKey[i]);
					bpmAgent.setDefKey(defKey[i]);
					bpmAgent.setSubject(bpmDefinition.getSubject());
					bpmAgent.setActdefid(bpmDefinition.getActDefId());
					list.add(bpmAgent);
				}
			}
		}

		return list;
	}

}
