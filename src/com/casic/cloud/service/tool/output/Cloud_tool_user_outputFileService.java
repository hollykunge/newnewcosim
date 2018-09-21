package com.casic.cloud.service.tool.output;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.tool.output.Cloud_tool_user_outputFile;
import com.casic.cloud.dao.tool.output.Cloud_tool_user_outputFileDao;

/**
 *<pre>
 * 对象功能:cloud_tool_user_outputFile Service类
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-06 18:01:30
 *</pre>
 */
@Service
public class Cloud_tool_user_outputFileService extends BaseService<Cloud_tool_user_outputFile>
{
	@Resource
	private Cloud_tool_user_outputFileDao dao;
	
	
	
	public Cloud_tool_user_outputFileService()
	{
	}
	
	@Override
	protected IEntityDao<Cloud_tool_user_outputFile, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
