package com.casic.cloud.service.tool.input;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.tool.input.Cloud_tool_user_inputFile;
import com.casic.cloud.dao.tool.input.Cloud_tool_user_inputFileDao;

/**
 *<pre>
 * 对象功能:cloud_tool_user_inputFile Service类
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-06 18:01:30
 *</pre>
 */
@Service
public class Cloud_tool_user_inputFileService extends BaseService<Cloud_tool_user_inputFile>
{
	@Resource
	private Cloud_tool_user_inputFileDao dao;
	
	
	
	public Cloud_tool_user_inputFileService()
	{
	}
	
	@Override
	protected IEntityDao<Cloud_tool_user_inputFile, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
