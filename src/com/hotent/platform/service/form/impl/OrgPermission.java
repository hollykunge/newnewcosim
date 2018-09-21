package com.hotent.platform.service.form.impl;

import com.hotent.platform.service.form.IDataPermission;

public class OrgPermission implements IDataPermission {

	@Override
	public String getSql(Long userId) {
		String sql=" and CURENTUSERID_ in (select userid from sys_user_org where orgid in " +
				"(select orgid from sys_user_org where isprimary=1 and userid=" +userId + ")) ";
		return sql;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "查看本组织业务数据";
	}
	
}
