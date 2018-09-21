package com.hotent.platform.model.form;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 对象功能:查看业务数据模板的权限信息 Model对象 开发公司: 开发人员:heyifan 创建时间:2012-05-28
 * 09:04:05
 */
public class BpmTableTemprights extends BaseModel {
	public static final int RIGHT_TYPE_USER = 1; 	//权限类型：用户
	public static final int RIGHT_TYPE_ROLE = 2;	//权限类型：角色
	public static final int RIGHT_TYPE_ORG = 3;	//权限类型：组织

	public static final Short SEARCH_TYPE_DEF = 0;	//搜索类型：默认
	public static final Short SEARCH_TYPE_GLT = 1;	//搜索类型：表单类型

	// 主键
	protected Long id;
	// 模板ID
	protected Long templateId;
	// 权限所有者类型
	protected Short rightType;
	// 拥有者ID
	protected Long ownerId;
	// 拥有者名称
	protected String ownerName;
	// 权限搜索类型
	protected Short searchType;
	//表单分类ID
	protected Long categoryId;

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 返回 主键
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	/**
	 * 返回 模板ID
	 * 
	 * @return
	 */
	public Long getTemplateId() {
		return this.templateId;
	}

	public void setRightType(Short rightType) {
		this.rightType = rightType;
	}

	/**
	 * 返回 权限所有者类型
	 * 
	 * @return
	 */
	public Short getRightType() {
		return this.rightType;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * 返回 拥有者ID
	 * 
	 * @return
	 */
	public Long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * 返回 拥有者名称
	 * 
	 * @return
	 */
	public String getOwnerName() {
		return this.ownerName;
	}

	public void setSearchType(Short searchType) {
		this.searchType = searchType;
	}

	/**
	 * 返回 权限搜索类型
	 * 
	 * @return
	 */
	public Short getSearchType() {
		return this.searchType;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpmTableTemprights)) {
			return false;
		}
		BpmTableTemprights rhs = (BpmTableTemprights) object;
		return new EqualsBuilder().append(this.id, rhs.id).append(this.templateId, rhs.templateId).append(this.rightType, rhs.rightType)
				.append(this.ownerId, rhs.ownerId).append(this.ownerName, rhs.ownerName).append(this.searchType, rhs.searchType).isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.templateId).append(this.rightType).append(this.ownerId)
				.append(this.ownerName).append(this.searchType).toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("templateId", this.templateId).append("rightType", this.rightType)
				.append("ownerId", this.ownerId).append("ownerName", this.ownerName).append("searchType", this.searchType).toString();
	}

}