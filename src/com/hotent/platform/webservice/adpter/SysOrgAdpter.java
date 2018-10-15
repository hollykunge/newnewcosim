package com.hotent.platform.webservice.adpter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.model.system.SysOrg;

public class SysOrgAdpter extends XmlAdapter<SysOrg,ISysOrg>{
	@Override
	public SysOrg marshal(ISysOrg arg0) throws Exception {
		return (SysOrg)arg0;
	}

	@Override
	public ISysOrg unmarshal(SysOrg arg0) throws Exception {
		return (ISysOrg)arg0;
	}
}
