/**
 * 对象功能:SYS_USER_AGENT Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:csx
 * 创建时间:2011-12-27 11:54:23
 */
package com.hotent.platform.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.SysUserAgent;

@Repository
public class SysUserAgentDao extends BaseDao<SysUserAgent>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return SysUserAgent.class;
	}

	public List<SysUserAgent> getByTouserId(Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("touserid", userId);
		return getBySqlKey("getByTouserId",params);
	}

}