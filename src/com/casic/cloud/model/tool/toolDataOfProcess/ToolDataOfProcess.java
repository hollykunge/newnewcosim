package com.casic.cloud.model.tool.toolDataOfProcess;

import java.io.Serializable;
import java.util.List;

import com.hotent.platform.model.bpm.BpmDefinition;

public class ToolDataOfProcess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2818511404572887569L;
	public BpmDefinition bpmDefinition;
	public List<ToolDataOfNode> toolDataOfNodes;
	public BpmDefinition getBpmDefinition() {
		return bpmDefinition;
	}
	public void setBpmDefinition(BpmDefinition bpmDefinition) {
		this.bpmDefinition = bpmDefinition;
	}
	public List<ToolDataOfNode> getToolDataOfNodes() {
		return toolDataOfNodes;
	}
	public void setToolDataOfNodes(List<ToolDataOfNode> toolDataOfNodes) {
		this.toolDataOfNodes = toolDataOfNodes;
	}
	

}
