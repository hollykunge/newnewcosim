package com.hotent.platform.service.form.impl;

import com.hotent.platform.service.form.IDataPermission;

public class SelfPermission implements IDataPermission {

	@Override
	public String getSql(Long userId) {
		
		return " and CURENTUSERID_ = " + userId;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "查看个人业务数据";
	}

}
