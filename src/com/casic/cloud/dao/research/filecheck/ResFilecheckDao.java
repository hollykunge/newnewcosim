package com.casic.cloud.dao.research.filecheck;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.research.filecheck.ResFilecheck;
/**
 *<pre>
 * 对象功能:cloud_research_filecheck Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-21 16:04:50
 *</pre>
 */
@Repository
public class ResFilecheckDao extends BaseDao<ResFilecheck>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ResFilecheck.class;
	}

}