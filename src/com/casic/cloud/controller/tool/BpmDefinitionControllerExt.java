package com.casic.cloud.controller.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.tool.ToolBpmNode;
import com.casic.cloud.service.tool.ToolBpmNodeService;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.controller.bpm.BpmDefinitionController;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmNodeSetService;

/**
 * @author FangYun
 * 
 */
@Controller
@RequestMapping(value = { "/platform/bpm/bpmDefinitionExt/" })
public class BpmDefinitionControllerExt extends BpmDefinitionController {
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private ToolBpmNodeService toolBpmNodeService;

	@RequestMapping(value = { "nodeSummary" })
	public ModelAndView nodeSummary(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = super.nodeSummary(request, response);
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		List<BpmNodeSet> nodeSetList = bpmNodeSetService.getByDefId(defId);
		Map<Long, Boolean> toolBpmNodeMap = new HashMap<Long, Boolean>();
		prepareToolBpmNodes(nodeSetList, toolBpmNodeMap, defId);
		modelAndView.setViewName("/platform/bpm/bpmDefinitionNodeSummary.jsp");
		return modelAndView.addObject("toolBpmNodeMap", toolBpmNodeMap);
	}

	private void prepareToolBpmNodes(List<BpmNodeSet> nodeSetList, Map<Long, Boolean> toolBpmNodes, Long defId) {
		List<ToolBpmNode> toolNodes = toolBpmNodeService.getToolBpmNodeByDefId(defId);
		for (BpmNodeSet node : nodeSetList) {
			for (ToolBpmNode tn : toolNodes) {
				if (node.getSetId().equals(tn.getSetId())) {
					toolBpmNodes.put(node.getSetId(), Boolean.valueOf(true));
					break;
				}
			}
		}
	}
}
