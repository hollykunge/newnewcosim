/**
 * 
 */
package com.casic.cloud.model.taskfile;

import com.hotent.core.model.BaseModel;
import com.hotent.platform.model.bpm.BpmNodeSet;

/**
 * @author FangYun
 * 
 */
public class TaskFileNode extends BaseModel {
	private static final long serialVersionUID = 8296805642959078661L;
	private Long taskfileNodeId;
	private Long taskfileId;
	private Long setId;
	private BpmNodeSet node;
	private boolean hasRight = true;

	public TaskFileNode() {
		super();
	}

	public TaskFileNode(Long taskfileNodeId, Long taskfileId, Long setId, boolean hasRight) {
		super();
		this.taskfileNodeId = taskfileNodeId;
		this.taskfileId = taskfileId;
		this.setId = setId;
		this.hasRight = hasRight;
	}

	public TaskFileNode(Long taskfileNodeId, Long taskfileId, BpmNodeSet node, boolean hasRight) {
		super();
		this.taskfileNodeId = taskfileNodeId;
		this.taskfileId = taskfileId;
		this.node = node;
		this.setId = this.node.getSetId();
		this.hasRight = hasRight;
	}

	public BpmNodeSet getNode() {
		return node;
	}

	public void setNode(BpmNodeSet node) {
		this.node = node;
	}

	public boolean isHasRight() {
		return hasRight;
	}

	public void setHasRight(boolean hasRight) {
		this.hasRight = hasRight;
	}

	public Long getTaskfileNodeId() {
		return taskfileNodeId;
	}

	public void setTaskfileNodeId(Long taskfileNodeId) {
		this.taskfileNodeId = taskfileNodeId;
	}

	public Long getTaskfileId() {
		return taskfileId;
	}

	public void setTaskfileId(Long taskfileId) {
		this.taskfileId = taskfileId;
	}

	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}
}
