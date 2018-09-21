package com.casic.cloud.model.tool;

import java.util.ArrayList;
import java.util.List;

import com.casic.cloud.model.tool.input.Cloud_tool_user_inputFile;
import com.casic.cloud.model.tool.output.Cloud_tool_user_outputFile;
import com.hotent.core.model.BaseModel;

public class ToolBpmNode extends BaseModel {
	private static final long serialVersionUID = 6633322133580571110L;
	private Long toolNodeId;
	private Long toolUserId;
	private Long setId;
	private String toolName;
	private Integer toolType;
	private String toolAddress;
	private String myToolAddress;
	private String toolDesc;
	private List<Cloud_tool_user_inputFile> inputFiles = new ArrayList<Cloud_tool_user_inputFile>();
	private List<Cloud_tool_user_outputFile> outputFiles = new ArrayList<Cloud_tool_user_outputFile>();
	public String getToolDesc() {
		return toolDesc;
	}

	public void setToolDesc(String toolDesc) {
		this.toolDesc = toolDesc;
	}

	public String getMyToolAddress() {
		return myToolAddress;
	}

	public void setMyToolAddress(String myToolAddress) {
		this.myToolAddress = myToolAddress;
	}

	public Long getToolNodeId() {
		return toolNodeId;
	}

	public void setToolNodeId(Long toolDefId) {
		this.toolNodeId = toolDefId;
	}

	

	public Long getToolUserId() {
		return toolUserId;
	}

	public void setToolUserId(Long toolUserId) {
		this.toolUserId = toolUserId;
	}

	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long defId) {
		this.setId = defId;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public Integer getToolType() {
		return toolType;
	}

	public void setToolType(Integer toolType) {
		this.toolType = toolType;
	}

	public String getToolAddress() {
		return toolAddress;
	}

	public void setToolAddress(String toolDesc) {
		this.toolAddress = toolDesc;
	}

	public String getToolTypeAsString() {
		ToolType tt = ToolType.codeOf(toolType);
		return tt == null ? null : tt.getDescription();
	}

	public List<Cloud_tool_user_inputFile> getInputFiles() {
		return inputFiles;
	}

	public void setInputFiles(List<Cloud_tool_user_inputFile> inputFiles) {
		this.inputFiles = inputFiles;
	}

	public List<Cloud_tool_user_outputFile> getOutputFiles() {
		return outputFiles;
	}

	public void setOutputFiles(List<Cloud_tool_user_outputFile> outputFiles) {
		this.outputFiles = outputFiles;
	}
	
}
