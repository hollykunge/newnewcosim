package com.casic.cloud.model.tool;

import com.hotent.core.model.BaseModel;

public class ToolBpmDefinition extends BaseModel {
	private static final long serialVersionUID = 6633322133580571110L;
	private Long toolDefId;
	private Long toolId;
	private Long defId;
	private String toolName;
	private Integer toolType;
	private String toolAddress;

	public Long getToolDefId() {
		return toolDefId;
	}

	public void setToolDefId(Long toolDefId) {
		this.toolDefId = toolDefId;
	}

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public Long getDefId() {
		return defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
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
}
