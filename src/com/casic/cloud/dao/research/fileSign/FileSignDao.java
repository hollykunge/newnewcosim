package com.casic.cloud.dao.research.fileSign;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.research.fileSign.FileSign;
/**
 *<pre>
 * 对象功能:CLOUD_RESEARCH_FILESIGN Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-18 14:33:03
 *</pre>
 */
@Repository
public class FileSignDao extends BaseDao<FileSign>
{
	@Override
	public Class<?> getEntityClass()
	{
		return FileSign.class;
	}

}