package com.casic.cloud.dao.reg.register;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.console.busiarea.Busiarea;
import com.casic.cloud.model.reg.register.Register;
/**
 *<pre>
 * 对象功能:cloud_user_register Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 11:27:33
 *</pre>
 */
@Repository
public class RegisterDao extends BaseDao<Register>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Register.class;
	}
	
	public List<Register> getByIdentity(Long identity){
		Map params=new HashMap();
		params.put("identity", identity);
		return getBySqlKey("getByIdentity", params);
	}	
}