package com.hotent.platform.model.bpm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 对象功能:InnoDB free: 7168 kB Model对象 开发公司: 开发人员:cjj 创建时间:2011-12-08
 * 09:21:29
 */
@XmlRootElement(name = "bpmNodeUser")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeUser extends BaseModel implements Cloneable {
	/**
	 * 发起人=0
	 */
	public static final short ASSIGN_TYPE_START_USER = 0;
	/**
	 * 用户=1
	 */
	public static final short ASSIGN_TYPE_USER = 1;
	/**
	 * 角色=2
	 */
	public static final short ASSIGN_TYPE_ROLE = 2;
	/**
	 * 组织=3
	 */
	public static final short ASSIGN_TYPE_ORG = 3;
	/**
	 * 组织负责人=4
	 */
	public static final short ASSIGN_TYPE_ORG_CHARGE = 4;
	/**
	 * 岗位=5
	 */
	public static final short ASSIGN_TYPE_POS = 5;
	/**
	 * 上下级=6
	 */
	public static final short ASSIGN_TYPE_UP_LOW = 6;
	/**
	 * 用户属性=7
	 */
	public static final short ASSIGN_TYPE_USER_ATTR = 7;
	/**
	 * 组织属性=8
	 */
	public static final short ASSIGN_TYPE_ORG_ATTR = 8;
	/**
	 * 本部门=9 即与发起人相同部门
	 */
	public static final short ASSIGN_TYPE_SAME_DEP = 9;
	/**
	 * 跟某个节相同执行人
	 */
	public static final short ASSIGN_TYPE_SAME_NODE = 10;
	/**
	 * 发起人的直属领导
	 */
	public static final short ASSIGN_TYPE_DIRECT_LED = 11;
	/**
	 * 脚本
	 */
	public static final short ASSIGN_TYPE_SCRIPT = 12;
	/**
	 * 上个任务执行人的直属领导(组织)
	 */
	public static final short ASSIGN_TYPE_PREUSER_ORG_LEADER=13;
	/**
	 * 发起人的领导
	 */
	public static final short ASSIGN_TYPE_STARTUSER_LEADER=14;
	/**
	 * 上个任务执行人的领导
	 */
	public static final short ASSIGN_TYPE_PREVUSER_LEADER=15;
	
	/**
	 * 执行者部门的上级类型部门的负责人
	 */
	public static final short ASSIGN_TYPE_PREVTYPEUSER_LEADER=16;
	/**
	 * 运算类型为 or
	 */
	public static final short COMP_TYPE_OR = 0;
	/**
	 * 运算类型为 and
	 */
	public static final short COMP_TYPE_AND = 1;
	/**
	 * 运算类型为 exclude
	 */
	public static final short COMP_TYPE_EXCLUDE = 2;
	/**
	 * 分配人员参与流程
	 */
	public static final short ASSIGN_USE_TYPE_PARTICIPATION=0;
	/**
	 * 分配人员接收通知
	 */
	public static final short ASSIGN_USE_TYPE_NOTIFY=1;
	
	/**
	 * 任务参与者。
	 */
	public static final String USER_TYPE_PARTICIPATION="PARTICIPATION";
	
	/**
	 * 通知任务执行人。
	 */
	public static final String USER_TYPE_NOTIFY="NOTIFY";

	// nodeUserId
	@XmlAttribute
	protected Long nodeUserId;
	// setId
	@XmlAttribute
	protected Long setId;
	// nodeId
	@XmlAttribute
	protected String nodeId;
	// 指派人员类型
	@XmlAttribute
	protected Short assignType;
	//分配人员用途类型(0,任务分配人，1，任务通知人）
	@XmlAttribute
	protected Short assignUseType=0;
	// actDefId
	@XmlAttribute
	protected String actDefId;
	//
	@XmlAttribute
	protected String cmpIds;
	//
	@XmlAttribute
	protected String cmpNames;
	// 运算类型
	@XmlAttribute
	protected Short compType;
	//
	@XmlAttribute
	protected Integer sn;
	//
	@XmlAttribute
	protected Long conditionId;

	public void setNodeUserId(Long nodeUserId) {
		this.nodeUserId = nodeUserId;
	}

	/**
	 * 返回 nodeUserId
	 * 
	 * @return
	 */
	public Long getNodeUserId() {
		return nodeUserId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	/**
	 * 返回 setId
	 * 
	 * @return
	 */
	public Long getSetId() {
		return setId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * 返回 nodeId
	 * 
	 * @return
	 */
	public String getNodeId() {
		return nodeId;
	}

	public void setAssignType(Short assignType) {
		this.assignType = assignType;
	}

	/**
	 * 返回 指派人员类型
	 * 
	 * @return
	 */
	public Short getAssignType() {
		return assignType;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	/**
	 * 返回 actDefId
	 * 
	 * @return
	 */
	public String getActDefId() {
		return actDefId;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeUser)) {
			return false;
		}
		BpmNodeUser rhs = (BpmNodeUser) object;
		return new EqualsBuilder().append(this.nodeUserId, rhs.nodeUserId).append(this.setId, rhs.setId).append(this.nodeId, rhs.nodeId)
				.append(this.assignType, rhs.assignType).append(this.actDefId, rhs.actDefId).append(this.cmpIds, rhs.cmpIds)
				.append(this.conditionId, rhs.conditionId).append(this.cmpNames, rhs.cmpNames).isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.nodeUserId).append(this.setId).append(this.nodeId).append(this.assignType)
				.append(this.actDefId).append(this.cmpIds)	.append(this.conditionId).append(this.cmpNames).toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("nodeUserId", this.nodeUserId).append("setId", this.setId).append("nodeId", this.nodeId)
				.append("assignType", this.assignType).append("actDefId", this.actDefId).append("cmpIds", this.cmpIds).append("cmpNames", this.cmpNames)				
					.append("conditionId",this.conditionId).toString();
	}

	public String getCmpIds() {
		return cmpIds;
	}

	public void setCmpIds(String cmpIds) {
		this.cmpIds = cmpIds;
	}

	public String getCmpNames() {
		return cmpNames;
	}

	public void setCmpNames(String cmpNames) {
		this.cmpNames = cmpNames;
	}

	public Short getCompType() {
		return compType;
	}

	public void setCompType(Short compType) {
		this.compType = compType;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Short getAssignUseType() {
		return assignUseType==null?0:this.assignUseType;
	}

	public void setAssignUseType(Short assignUseType) {
		this.assignUseType = assignUseType;
	}
	
	public Long getConditionId() {
		return conditionId;
	}

	public void setConditionId(Long conditionId) {
		this.conditionId = conditionId;
	}

	public Object clone() {
		BpmNodeUser obj = null;
		try {
			obj = (BpmNodeUser) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

}