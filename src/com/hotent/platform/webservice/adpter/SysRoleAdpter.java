package com.hotent.platform.webservice.adpter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.model.system.SysRole;

public class SysRoleAdpter extends XmlAdapter<SysRole,ISysRole>{

	@Override
	public SysRole marshal(ISysRole arg0) throws Exception {
		return (SysRole)arg0;
	}

	@Override
	public ISysRole unmarshal(SysRole arg0) throws Exception {
		return (ISysRole)arg0;
	}	
}
