package com.hotent.platform.dao;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.UniqueIdUtil;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class ExtFormUserDao
{
	@Resource
	protected JdbcTemplate jdbcTemplate;
	
	/**
	 * post表单的数据的方法
	 * @param cmd
	 * @throws Exception
	 */
	public void add(ProcessCmd cmd) throws Exception
	{
		Map map= cmd.getFormDataMap();
		String name=(String) map.get("name");
		String sex=(String) map.get("sex");
		Long id=UniqueIdUtil.genId();
		String sql = "insert into person(id,name,sex)values("+id+",'"+name+"','"+sex+"')";
		jdbcTemplate.execute(sql);		
		//写回主键
		cmd.setBusinessKey(id.toString());
		
	}	
	
	
	/**
	 * 获取表单数据的方法
	 * @param cmd
	 * @return
	 */
	public Map getById(ProcessCmd cmd){
		
		String sql="select a.* from person a where id="+cmd.getBusinessKey();
		Map map=jdbcTemplate.queryForMap(sql);
		return map;
	}	
	
	
	/**
	 * 流程审批时更新表单的数据方法
	 * @param cmd
	 * @return
	 */
	public void updById(ProcessCmd cmd){
		Map map= cmd.getFormDataMap();
		String name=(String) map.get("name");
		String sex=(String) map.get("sex");
		String id=(String) map.get("id");
		String sql="update  person set name=?,sex=? where id=?";
		jdbcTemplate.update(sql, name,sex,id);
		
	}	
}
