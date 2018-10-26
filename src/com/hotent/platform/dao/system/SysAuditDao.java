/**
 * 对象功能:系统日志 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:csx
 * 创建时间:2011-11-26 11:35:04
 */
package com.hotent.platform.dao.system;

import com.hotent.core.web.query.QueryFilter;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.SysAudit;

import java.util.List;

@Repository
public class SysAuditDao extends BaseDao<SysAudit>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return SysAudit.class;
	}

	public List<SysAudit> getAuditByFullName(QueryFilter fullName){
        List<SysAudit> sysAudits = this.getBySqlKey("getByUserNameAndRole", fullName);
        return sysAudits;
    }
}