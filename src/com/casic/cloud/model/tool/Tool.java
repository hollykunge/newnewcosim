package com.casic.cloud.model.tool;

import java.util.Date;

import com.hotent.core.model.BaseModel;

public class Tool extends BaseModel {
	private static final long serialVersionUID = 6682068380583220907L;
	private Long id;
	private String toolName;
	private Integer toolType;
	private String toolAddress;
	private String toolDesc;
	private Date createtime;
	private Date updatetime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToolAddress() {
		return toolAddress;
	}

	public void setToolAddress(String toolAddress) {
		this.toolAddress = toolAddress;
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

	public String getToolTypeAsString() {
		ToolType tt = ToolType.codeOf(toolType);
		return tt == null ? null : tt.getDescription();
	}

	public void setToolType(Integer toolType) {
		this.toolType = toolType;
	}

	public String getToolDesc() {
		return toolDesc;
	}

	public void setToolDesc(String toolDesc) {
		this.toolDesc = toolDesc;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
