package com.casic.cloud.service.reg.register;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.reg.register.Register;
import com.casic.cloud.dao.reg.register.RegisterDao;

/**
 *<pre>
 * 对象功能:cloud_user_register Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 11:27:33
 *</pre>
 */
@Service
public class RegisterService extends BaseService<Register>
{
	@Resource
	private RegisterDao dao;
	
	
	
	public RegisterService()
	{
	}
	
	@Override
	protected IEntityDao<Register, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<Register> getByIdentity(Long identity){
		return dao.getByIdentity(identity);
	}
	
}
