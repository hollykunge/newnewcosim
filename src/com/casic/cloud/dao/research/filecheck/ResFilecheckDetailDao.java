package com.casic.cloud.dao.research.filecheck;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.casic.cloud.model.research.filecheck.ResFilecheckDetail;
/**
 *<pre>
 * 对象功能:cloud_research_filecheck_fileinfo Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-21 16:04:50
 *</pre>
 */
@Repository
public class ResFilecheckDetailDao extends BaseDao<ResFilecheckDetail>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ResFilecheckDetail.class;
	}

	public List<ResFilecheckDetail> getByMainId(Long filecheckId) {
		return this.getBySqlKey("getResFilecheckDetailList", filecheckId);
	}
	
	public void delByMainId(Long filecheckId) {
		this.delBySqlKey("delByMainId", filecheckId);
	}
}