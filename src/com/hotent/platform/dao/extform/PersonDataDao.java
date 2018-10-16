package com.hotent.platform.dao.extform;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.extform.PersonData;
/**
 *<pre>
 * 对象功能:PERSON_DATA Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2013-01-10 09:44:06
 *</pre>
 */
@Repository
public class PersonDataDao extends  BaseDao<PersonData>
{
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return PersonData.class;
	}
	
	/**
	 * 添加数据
	 * @param cmd
	 * @throws Exception
	 */
	public void add(ProcessCmd cmd) throws Exception
	{
		PersonData personData = new PersonData();
		Map map= cmd.getFormDataMap();
		Long id=UniqueIdUtil.genId();
		personData.setId(id);
		personData.setUsername(map.get("username").toString());
		personData.setIdcard(map.get("idcard").toString());
		this.add(personData);
		//写回主键
		cmd.setBusinessKey(id.toString());
	}
	
	/**
	 * 更新数据
	 * @param cmd
	 * @throws Exception
	 */
	public void updateById(ProcessCmd cmd) throws Exception
	{
		Map map= cmd.getFormDataMap();
	
		PersonData personData = this.getById(new Long( cmd.getBusinessKey()));
		personData.setUsername(map.get("username").toString());
		personData.setIdcard(map.get("idcard").toString());
		this.update(personData);
	}
	
}