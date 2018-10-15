package com.hotent.platform.webservice.adpter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysUser;

public class SysUserAdpter extends XmlAdapter<SysUser,ISysUser>{

	@Override
	public SysUser marshal(ISysUser arg0) throws Exception {
		return (SysUser)arg0;
	}

	@Override
	public ISysUser unmarshal(SysUser arg0) throws Exception {
		return (ISysUser)arg0;
	}

}
