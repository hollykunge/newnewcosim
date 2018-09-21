package com.casic.cloud.dao.config.orginfation;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.config.orginfation.OrgInfation;
/**
 *<pre>
 * 对象功能:sys_org_info Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 18:08:13
 *</pre>
 */
@Repository
public class OrgInfationDao extends BaseDao<OrgInfation>
{
	@Override
	public Class<?> getEntityClass()
	{
		return OrgInfation.class;
	}

}