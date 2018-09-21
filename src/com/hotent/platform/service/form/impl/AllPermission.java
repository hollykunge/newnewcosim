package com.hotent.platform.service.form.impl;

import com.hotent.platform.service.form.IDataPermission;

public class AllPermission implements IDataPermission {

	@Override
	public String getSql(Long userId) {
		return "";
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "查看全部业务数据";
	}

}
