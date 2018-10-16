package com.hotent.platform.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysOrgInfo;
/**
 *<pre>
 * 对象功能:SYS_ORG_INFO Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2013-04-11 13:34:44
 *</pre>
 */
@Repository
public class SysOrgInfoDao extends BaseDao<SysOrgInfo>
{
	@Override
	public Class<?> getEntityClass()
	{
		return SysOrgInfo.class;
	}
	public List<SysOrgInfo> getSysOrgInfo(){
		return this.getBySqlKey("getLastNews");
	}
	
	/**
	 * 根据搜索输入的企业名字获得企业列表
	 * @param queryFilter
	 * @return
	 */
	public List<SysOrgInfo> getAll(QueryFilter queryFilter) {
		return this.getBySqlKey("getAll",queryFilter);
	}
	
	/**
	 * 根据输入企业名字获取企业
	 * @param name
	 * @return
	 */
	public List<SysOrgInfo> getByName(String name){
		Map params=new HashMap();
		params.put("name", name);
		return getBySqlKey("getByName", params);
	}
	
	
}