package com.hotent.platform.service.form;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.form.BpmTableTemplate;
import com.hotent.platform.dao.form.BpmTableTemplateDao;
import com.hotent.platform.dao.form.BpmTableTemprightsDao;

/**
 * 对象功能:查看表格业务数据的模板 Service类
 * 开发公司:
 * 开发人员:heyifan
 * 创建时间:2012-05-22 14:58:08
 */
@Service
public class BpmTableTemplateService extends BaseService<BpmTableTemplate>
{
	@Resource
	private BpmTableTemplateDao dao;
	@Resource
	private BpmTableTemprightsDao bpmTableTemprightsDao; 
	
	public BpmTableTemplateService()
	{
	}
	
	public List<BpmTableTemplate> getList(QueryFilter filter){
		return dao.getList(filter);
	}
	
	@Override
	protected IEntityDao<BpmTableTemplate, Long> getEntityDao() 
	{
		return dao;
	}
	
	public void delByIds(Long[] ids){
		if(ids==null||ids.length==0)return;
		for(Long id:ids){
			delByTempId(id);	
		}
	}
	
	private void delByTempId(Long id){
		dao.delById(id);
		bpmTableTemprightsDao.delByTemplateId(id);
	}
	
	/**
	 * 按用户Id及查询参数查找我能访问的所有业务数据模板
	 * @param queryFilter
	 * @return
	 */
	public List<BpmTableTemplate> getByUserIdFilter(QueryFilter queryFilter){
		return dao.getByUserIdFilter(queryFilter);
	}
	
	/**
	 * 根据formKey查询模版列表。
	 * @param formKey	表单的formKey。
	 * @return
	 */
	public List<BpmTableTemplate> getByFormKey(Long formKey){
		return dao.getByFormKey(formKey);
	}
}
