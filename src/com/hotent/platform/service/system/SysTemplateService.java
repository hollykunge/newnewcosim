package com.hotent.platform.service.system;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.platform.model.system.SysTemplate;
import com.hotent.platform.dao.system.SysTemplateDao;

/**
 * 对象功能:模版管理 Service类
 * 开发公司:
 * 开发人员:pkq
 * 创建时间:2011-12-28 14:04:30
 */
@Service
public class SysTemplateService extends BaseService<SysTemplate>
{
	@Resource
	private SysTemplateDao dao;
	
	public SysTemplateService()
	{
	}
	
	@Override
	protected IEntityDao<SysTemplate, Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 设置默认模板
	 * @param id
	 */
	public void setDefault(long id,Integer tempType){
		dao.setNotDefaultByTempType(tempType);
		dao.setDefault(id);
	}
	
	/**
	 * 获取某类型模板的默认模板(没有默认则取系统模板)
	 * @param tempType
	 * @return
	 */
	public SysTemplate getDefaultByType(Integer tempType)
	{
		SysTemplate sysTemplate = dao.getDefaultByType(tempType);
		if(sysTemplate==null)
			sysTemplate=dao.getSystemByType(tempType);
		return sysTemplate;
	}
}
