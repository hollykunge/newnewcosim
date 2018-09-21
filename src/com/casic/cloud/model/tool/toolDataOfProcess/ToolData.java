package com.casic.cloud.model.tool.toolDataOfProcess;

import java.io.Serializable;
import java.util.List;

import com.casic.cloud.model.tool.Cloud_tool_user_file;
import com.casic.cloud.model.tool.Tool;
import com.casic.cloud.model.tool.ToolBpmNode;
import com.casic.cloud.model.tool.ToolUser;
import com.casic.cloud.model.tool.output.Cloud_tool_user_outputFile;

public class ToolData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7113023612316856370L;
	public ToolBpmNode toolBpmNode;
	public Tool tool;
	public List<Cloud_tool_user_outputFile> cloud_tool_user_outputFiles;
	public ToolBpmNode getToolBpmNode() {
		return toolBpmNode;
	}
	public void setToolBpmNode(ToolBpmNode toolBpmNode) {
		this.toolBpmNode = toolBpmNode;
	}
	public List<Cloud_tool_user_outputFile> getCloud_tool_user_outputFiles() {
		return cloud_tool_user_outputFiles;
	}
	public void setCloud_tool_user_outputFiles(
			List<Cloud_tool_user_outputFile> cloud_tool_user_outputFiles) {
		this.cloud_tool_user_outputFiles = cloud_tool_user_outputFiles;
	}
	public Tool getTool() {
		return tool;
	}
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
	
}
