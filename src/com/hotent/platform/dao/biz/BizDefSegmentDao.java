package com.hotent.platform.dao.biz;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.platform.model.biz.BizDefSegment;
/**
 *<pre>
 * 对象功能:业务环节 Dao类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Repository
public class BizDefSegmentDao extends BaseDao<BizDefSegment>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BizDefSegment.class;
	}

	public List<BizDefSegment> getByMainId(Long bizDefId) {
		return this.getBySqlKey("getBizDefSegmentList", bizDefId);
	}
	
	public void delByMainId(Long bizDefId) {
		this.delBySqlKey("delByMainId", bizDefId);
	}
}