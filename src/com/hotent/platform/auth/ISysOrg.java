package com.hotent.platform.auth;

import java.util.Date;

/** 组织接口 */
public interface ISysOrg {
	/**
	 *设置组织ID 
	 *
	 *  @param orgId 
	 */
	public void setOrgId(Long orgId);

	/**
	 * 获取组织ID
	 * 
	 * @return
	 */
	public Long getOrgId();

	/**
	 * 设置组织名
	 * 
	 *  @param orgName
	 */
	public void setOrgName(String orgName);

	/**
	 * 获取组织名
	 * 
	 * @return
	 */
	public String getOrgName();
	
	/**
	 * 获取组织描述
	 * @return
	 */
	public String getOrgDesc();
	
	/**
	 * 设置组织描述
	 * @param orgDesc
	 */
	public void setOrgDesc(String orgDesc);

	/**
	 *设置组织级别
	 * 
	 *  @param orgLevel 
	 */
	public void setOrgLevel(Integer orgLevel);
	
	/**
	 * 获取组织级别
	 * 
	 * @return
	 */
	public Integer getOrgLevel();
	
	/**
	 * 获取组织的上下节点路径
	 * @return
	 */
	public String getPath();
	
	/**
	 * 设置组织路径
	 * @param path
	 */
	public void setPath(String path);
	
	/**
	 * 设置部门负责人名称
	 * @param userName
	 */
	public void setOwnUserName(String userName);
	
	/**
	 * 获取部门负责人名称
	 * @return
	 */
	public String getOwnUserName();
	
	/**
	 * 设置负责人ID
	 * @param ownUserId
	 */
	public void setOwnUser(String ownUserId);
	
	/**
	 * 获取负责人ID
	 * @return
	 */
	public String getOwnUser();
	
	/**
	 * 获取上级部门的ID
	 * @return
	 */
	public Long getOrgSupId();
	
	/**
	 * 设置上级部门ID
	 * @param supId
	 */
	public void setOrgSupId(Long supId);
	
	/**
	 * 获取上级部门名称
	 * @return
	 */
	public String getOrgSupName();
	
	/**
	 * 设置上级部门名称
	 * @param supName
	 */
	public void setOrgSupName(String supName);
	
	/**
	 * 获取维度ID
	 * @return
	 */
	public Long getDemId();
	
	/**
	 * 设置维度ID
	 */
	public void setDemId(Long demId);
	
	/**
	 * 获取组织的所属维度名
	 * @return
	 */
	public String getDemName();
	
	/**
	 * 设置组织的维度名
	 * @param demName
	 */
	public void setDemName(String demName);
	
	/**
	 * 设置排序号码
	 * @param sn
	 */
	public void setSn(Long sn);
	
	/**
	 * 获取排序号码
	 * @return
	 */
	public Long getSn();
	
	/**
	 * 获取是否根节点
	 * @return
	 */
	public Short getIsRoot();
	
	/**
	 * 设置是否根节点
	 * @param isRoot
	 */
	public void setIsRoot(Short isRoot);
	
	/**
	 * 获取组织类型
	 * @return
	 */
	public Long getOrgType();
	
	/**
	 * 获取组织来源类型
	 * @return
	 */
	public Short getFromType();
	
	/**
	 * 设置组织来源类型
	 * @param fromType
	 */
	public void setFromType(Short fromType);

	/**
	 * 获取该组织在线人数
	 * @return
	 */
 	public Integer getOnlineNum();
 	
 	/**
 	 * 设置该组织在线人数
 	 * @param onlineNum
 	 */
	public void setOnlineNum(Integer onlineNum);
	
	/**
	 * 设置图标路径
	 * @param iconPath
	 */
	public void setIconPath(String iconPath);
	
	/**
	 * 获取图标路径
	 * @return
	 */
	public String getIconPath();
	/**
	 * 设置 修改人id
	 * @param updateId
	 */
	public void setUpdateId(Long updateId);
	/**
	 * 返回 修改人id
	 * @return
	 */
	public Long getUpdateId();
	/**
	 * 设置创建人id
	 * @param creatorId
	 */
	public void setCreatorId(Long creatorId);
	
	/**
	 * 返回 修改时间
	 * @return
	 */
	public Date getUpdatetime();
	
	public void setUpdatetime(Date updatetime);
	/**
	 * 返回 建立人id
	 * @return
	 */
	public Long getCreatorId();
	/**
	 * 设置是否叶子节点
	 * @param isLeaf
	 */
	public void setIsLeaf(Integer isLeaf);
	/**
	 * 获取是否叶子节点
	 * @return
	 */
	public Integer getIsLeaf();
	public void setOrgStatus(Long orgStatus);
	public Long getOrgStatus();
	public void setIsSystem(Long isSystem);
	public Long getIsSystem();
}