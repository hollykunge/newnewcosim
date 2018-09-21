package com.casic.cloud.dao.research.filecheck;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.casic.cloud.model.research.filecheck.ResFilecheckOpinion;
/**
 *<pre>
 * 对象功能:cloud_research_filecheck_opinions Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-21 16:04:50
 *</pre>
 */
@Repository
public class ResFilecheckOpinionDao extends BaseDao<ResFilecheckOpinion>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ResFilecheckOpinion.class;
	}

	public List<ResFilecheckOpinion> getByMainId(Long filecheckId) {
		return this.getBySqlKey("getResFilecheckOpinionList", filecheckId);
	}
	
	public void delByMainId(Long filecheckId) {
		this.delBySqlKey("delByMainId", filecheckId);
	}
}