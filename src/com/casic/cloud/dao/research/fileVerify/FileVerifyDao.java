package com.casic.cloud.dao.research.fileVerify;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.research.fileVerify.FileVerify;
/**
 *<pre>
 * 对象功能:CLOUD_RESEARCH_VERIFY Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-14 21:16:24
 *</pre>
 */
@Repository
public class FileVerifyDao extends BaseDao<FileVerify>
{
	@Override
	public Class<?> getEntityClass()
	{
		return FileVerify.class;
	}

}