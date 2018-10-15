/**
 * 对象功能:系统角色表 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:csx
 * 创建时间:2011-11-18 16:24:10
 */
package com.hotent.platform.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.page.PageBean;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.model.system.SysRole;

@Repository
public class SysRoleDao extends BaseDao<ISysRole>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return SysRole.class;
	}
	/**
	 * 获取参数
	 * @param systemId
	 * @param pb
	 * @return
	 */
	public List<ISysRole> getBySystemId(Long systemId,PageBean pb)
	{
		return getBySqlKey("getBySystemId", systemId, pb);
	}
	/**
	 * 判断角色名称是否存在。
	 * @param roleName
	 * @return
	 */
    public boolean isExistRoleAlias(String alias){
    	Integer count=(Integer)this.getOne("isExistRoleAlias", alias);
    	return count>0;
    }
    
    /**
     * 根据别名和角色id判断是否重复。
     * @param alias 角色别名
     * @param roleId	角色id
     * @return
     */
    @SuppressWarnings("unchecked")
	public boolean isExistRoleAliasForUpd(String alias,Long roleId){
    	Map map=new HashMap();
    	map.put("alias", alias);
    	map.put("roleId", roleId);
    	Integer count=(Integer)this.getOne("isExistRoleAliasForUpd", map);
    	return count>0;
    }
    
    /**
     * 查询所有角色及子系统
     * @param queryFilter
     * @return
     */
	public List<ISysRole> getRole(QueryFilter queryFilter){
		return getBySqlKey("getRole", queryFilter);
	}

	/**
	 * 根据UserId取得系统角色
	 * @param userId
	 * @return
	 */
	public List<ISysRole> getByUserId(Long userId)
	{
		return getBySqlKey("getByUserId", userId);
	}
	
	/**
	 * 根据组织ID获取角色
	 * @param orgId
	 * @return
	 */
	public List<ISysRole> getByOrgId(Long orgId){
		return getBySqlKey("getByOrgId",orgId);
	}
	
	/**
     * 获取所有子系统的角色
     * @param queryFilter
     * @return
     */
	public List<ISysRole> getRoleTree(QueryFilter queryFilter)
	{
		String sqlKey="getRoleTree_"+getDbType();
		return getBySqlKey(sqlKey, queryFilter);
	}
	
	
	/**
	 * 根据系统id查询系统的角色。
	 * @param systemId
	 * @return
	 */
	public List<ISysRole> getBySystemId(Long systemId){
		List<ISysRole> list=this.getBySqlKey("getBySystemId", systemId);
		return list;
	}	
	
	/**
	 * 根据系统id和角色名称查询子系统角色列表。
	 * @param systemId
	 * @param roleName
	 * @return
	 */
	public List<ISysRole> getRoleBySystemId(Long systemId,String roleName){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("systemId", systemId);
		map.put("roleName", StringUtils.isNotEmpty(roleName)?("%"+roleName+"%"):"" );
		List<ISysRole> list=this.getBySqlKey("getRoleBySystemId", map);
		return list;
	}	
	
	
	
	/**
	 * 根据组织ID查询可以授权的组织。
	 * @param orgId
	 * @return
	 */
	public List<ISysRole> getManageRolesByOrgId(Long orgId){
		List<ISysRole> list=this.getBySqlKey("getManageRolesByOrgId", orgId);
		return list;
	}
	
	////ht:raise add b
	public ISysRole getByAlias(String alias){
		return this.getUnique("getByAlias", alias);
	}
	
	////ht:raise add e
}