package com.hotent.platform.dao.biz;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.biz.BizDef;
/**
 *<pre>
 * 对象功能:业务定义，如邀标采购这样的大业务。 Dao类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Repository
public class BizDefDao extends BaseDao<BizDef>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BizDef.class;
	}

	public Short getMaxVersionNoByNo(String bizDefNo) {
		return (Short) getOne("getMaxVersionNoByNo", bizDefNo);
	}

}