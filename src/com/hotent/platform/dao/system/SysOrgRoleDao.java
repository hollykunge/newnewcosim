package com.hotent.platform.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.SysOrgRole;
/**
 * 对象功能:组织角色授权信息 Dao类
 * 开发公司:宏天
 * 开发人员:hotent
 * 创建时间:2012-10-30 09:55:49
 */
@Repository
public class SysOrgRoleDao extends BaseDao<SysOrgRole>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return SysOrgRole.class;
	}
	
	public boolean getCountByOrgidRoleid(Long orgId,Long roleId){		
		Map map=new HashMap();
		map.put("orgId", orgId);
		map.put("roleId", roleId);
		List<SysOrgRole> roles = this.getBySqlKey("getCountByOrgidRoleid", map);
		return roles.size() > 0;
	}

	public List<SysOrgRole> getRolesByOrgId(Long orgId){
		return this.getBySqlKey("getRolesByOrgId",orgId);
	}
	
	
}