package com.hotent.platform.dao.biz;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.biz.BizDefSegment;
import com.hotent.platform.model.biz.BizInstanceSegment;
/**
 *<pre>
 * 对象功能:业务环节实例 Dao类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Repository
public class BizInstanceSegmentDao extends BaseDao<BizInstanceSegment>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BizInstanceSegment.class;
	}
	public List<BizInstanceSegment> getByMainId(Long bizInstanceId) {
		return this.getBySqlKey("getByMainId", bizInstanceId);
	}
	
	public void delByMainId(Long bizInstanceId) {
		this.delBySqlKey("delByMainId", bizInstanceId);
	}

}