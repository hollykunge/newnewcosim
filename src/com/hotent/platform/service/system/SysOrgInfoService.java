package com.hotent.platform.service.system;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.platform.dao.system.SysOrgInfoDao;
import com.hotent.platform.model.system.SysOrgInfo;

/**
 *<pre>
 * 对象功能:SYS_ORG_INFO Service类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2013-04-11 13:34:44
 *</pre>
 */
@Service
public class SysOrgInfoService extends BaseService<SysOrgInfo>
{
	@Resource
	private SysOrgInfoDao dao;
	
	
	
	public SysOrgInfoService()
	{
	}
	
	@Override
	protected IEntityDao<SysOrgInfo, Long> getEntityDao() 
	{
		return dao;
	}
	
	
	/**
	 * 根据企业名获取企业信息
	 * @param name
	 * @return
	 */
	public SysOrgInfo getByName(String name){
		List<SysOrgInfo> list = dao.getByName(name);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
