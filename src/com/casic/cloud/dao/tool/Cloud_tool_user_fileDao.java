package com.casic.cloud.dao.tool;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.tool.Cloud_tool_user_file;
/**
 *<pre>
 * 对象功能:cloud_tool_user_file Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-03 09:47:37
 *</pre>
 */
@Repository
public class Cloud_tool_user_fileDao extends BaseDao<Cloud_tool_user_file>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Cloud_tool_user_file.class;
	}

}