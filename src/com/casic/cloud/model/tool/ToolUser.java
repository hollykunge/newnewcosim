package com.casic.cloud.model.tool;

import java.util.ArrayList;
import java.util.List;

import com.casic.cloud.model.tool.input.Cloud_tool_user_inputFile;
import com.casic.cloud.model.tool.output.Cloud_tool_user_outputFile;
import com.hotent.core.model.BaseModel;

public class ToolUser extends BaseModel {
	private static final long serialVersionUID = -7226050314795707050L;
	private Long toolUserId;
	private Long toolId;
	private Long userId;
	private String toolName;
	private Integer toolType;
	private String toolAddress;
	private String myToolAddress;
	private List<Cloud_tool_user_inputFile> inputFiles = new ArrayList<Cloud_tool_user_inputFile>();
	private List<Cloud_tool_user_outputFile> outputFiles = new ArrayList<Cloud_tool_user_outputFile>();
	private List<Cloud_tool_user_parasMapper> parasMappers = new ArrayList<Cloud_tool_user_parasMapper>();
	public Long getToolUserId() {
		return toolUserId;
	}

	public void setToolUserId(Long toolUserId) {
		this.toolUserId = toolUserId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
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

	
	public String getMyToolAddress() {
		return myToolAddress;
	}

	public void setMyToolAddress(String myToolAddress) {
		this.myToolAddress = myToolAddress;
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

	public List<Cloud_tool_user_parasMapper> getParasMappers() {
		return parasMappers;
	}

	public void setParasMappers(List<Cloud_tool_user_parasMapper> parasMappers) {
		this.parasMappers = parasMappers;
	}
	
}
