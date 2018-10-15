package com.hotent.platform.auth;

/** 角色接口 */
public interface ISysRole {
	/**
	 * 设置角色ID
	 * 
	 * @param roleId 角色ID 
	 */
	public void setRoleId(Long roleId);

	/**
	 * 获取角色ID
	 * 
	 * @return 角色ID
	 */
	public Long getRoleId();

	/**
	 * 设置角色名
	 * 
	 * @param roleName 角色名
	 */
	public void setRoleName(String roleName);

	/**
	 * 获取角色名
	 * 
	 * @return 角色名
	 */
	public String getRoleName();
	
	/**
	 * 获取角色别名
	 * @return
	 */
	public String getAlias();
	
	/**
	 * 设置角色别名
	 * @param alias
	 */
	public void setAlias(String alias);
	
	/**
	 * 获取角色所属系统ID
	 * @return
	 */
	public Long getSystemId();
	
	/**
	 * 设置角色所属系统ID
	 * @param systemId
	 */
	public void setSystemId(Long systemId);
	
	/**
	 * 设置备注
	 * @param memo
	 */
	public void setMemo(String memo);
	
	/**
	 * 获取备注
	 * @return
	 */
	public String getMemo();
	
	/**
	 * 克隆对象
	 * @return
	 */
	public Object clone();
	
	/**
	 * 设置角色是否启用
	 * @param enabled
	 */
	public void setEnabled(Short enabled);
	
	/**
	 * 获取角色是否启用
	 * @return
	 */
	public Short getEnabled();
	/**
	 * 设置是否父节点
	 * @param isParent
	 */
	public void setIsParent(String isParent);
	/**
	 * 获取是否父节点
	 * @return
	 */
	public String getIsParent();
}