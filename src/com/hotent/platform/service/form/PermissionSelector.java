package com.hotent.platform.service.form;

import java.util.LinkedHashMap;

public class PermissionSelector {

	
	private LinkedHashMap<Integer, IDataPermission> permissionMap=new LinkedHashMap<Integer, IDataPermission>();

	public LinkedHashMap<Integer, IDataPermission> getPermissionMap() {
		return permissionMap;
	}

	public void setPermissionMap(
			LinkedHashMap<Integer, IDataPermission> permissionMap) {
		this.permissionMap = permissionMap;
	}
	
	/**
	 * 获取权限接口。
	 * @param i
	 * @return
	 */
	public IDataPermission getByKey(Integer i){
		return this.permissionMap.get(i);
	}
	
	
}
