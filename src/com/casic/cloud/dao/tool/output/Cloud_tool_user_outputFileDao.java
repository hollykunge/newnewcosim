package com.casic.cloud.dao.tool.output;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.tool.output.Cloud_tool_user_outputFile;
/**
 *<pre>
 * 对象功能:cloud_tool_user_outputFile Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-06 18:01:30
 *</pre>
 */
@Repository
public class Cloud_tool_user_outputFileDao extends BaseDao<Cloud_tool_user_outputFile>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Cloud_tool_user_outputFile.class;
	}

}