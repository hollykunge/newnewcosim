package com.hotent.platform.service.extform;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.extform.PersonData;
import com.hotent.platform.dao.extform.PersonDataDao;

/**
 *<pre>
 * 对象功能:PERSON_DATA Service类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2013-01-10 09:44:06
 *</pre>
 */
@Service
public class PersonDataService extends BaseService<PersonData>
{
	@Resource
	private PersonDataDao dao;
	
	public PersonDataService()
	{
	}
	
	@Override
	protected IEntityDao<PersonData, Long> getEntityDao() 
	{
		return dao;
	}
	
}
