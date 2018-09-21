package com.casic.cloud.model.tool.toolDataOfProcess;

import java.io.Serializable;
import java.util.List;

import com.hotent.platform.model.bpm.BpmNodeSet;

public class ToolDataOfNode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4406709037283305188L;
	public BpmNodeSet bpmNodeSet;
	public List<ToolData> toolDatas;
	public List<ToolData> getToolDatas() {
		return toolDatas;
	}
	public void setToolDatas(List<ToolData> toolDatas) {
		this.toolDatas = toolDatas;
	}
	public BpmNodeSet getBpmNodeSet() {
		return bpmNodeSet;
	}
	public void setBpmNodeSet(BpmNodeSet bpmNodeSet) {
		this.bpmNodeSet = bpmNodeSet;
	}
	
	

}
