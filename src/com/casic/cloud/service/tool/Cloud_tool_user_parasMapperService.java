package com.casic.cloud.service.tool;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.tool.Cloud_tool_user_parasMapper;
import com.casic.cloud.dao.tool.Cloud_tool_user_parasMapperDao;

/**
 *<pre>
 * 对象功能:cloud_tool_user_parasMapper Service类
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-01 17:11:23
 *</pre>
 */
@Service
public class Cloud_tool_user_parasMapperService extends BaseService<Cloud_tool_user_parasMapper>
{
	@Resource
	private Cloud_tool_user_parasMapperDao dao;
	
	
	
	public Cloud_tool_user_parasMapperService()
	{
	}
	
	@Override
	protected IEntityDao<Cloud_tool_user_parasMapper, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<Cloud_tool_user_parasMapper> getByToolUserId(long toolUserId){
		return dao.getByToolUserId(toolUserId);
	}
	
	
}
