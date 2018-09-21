package com.casic.cloud.service.config.info;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.casic.cloud.dao.config.aptitude.AptitudeDao;
import com.casic.cloud.dao.config.info.InfoDao;
import com.casic.cloud.model.config.aptitude.Aptitude;
import com.casic.cloud.model.config.info.Info;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.service.system.SysUserService;

/**
 *<pre>
 * 对象功能:sys_org_info Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 19:28:40
 *</pre>
 */
@Service
public class InfoService extends BaseService<Info>
{
	@Resource
	private InfoDao dao;
	
	@Resource
	private AptitudeDao aptitudeDao;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private ProcessEngine processEngine; 
	
	public InfoService()
	{
	}
	
	@Override
	protected IEntityDao<Info, Long> getEntityDao() 
	{
		return dao;
	}

	public List<Info> getLastInfo() {
		// TODO Auto-generated method stub
		return dao.getLastInfo();
	}
	
	public List<Info> getChildren(String sqlKey,Map<String,Object> params){
		return dao.getChildren(sqlKey, params);
		
	}
	
	public List<Info> getAllInfos(String sqlKey,QueryFilter queryFilter){
		return dao.getAllInfos(sqlKey, queryFilter);
		
	}
	
	public boolean isEmailValid(String email){
		return dao.getAllByEmail(email).size() > 0;
	}
	
	public List<Aptitude> getAptitudeList(Long sysOrgInfoId) {
		return aptitudeDao.getAptitudeList("getAptitudeList", sysOrgInfoId);
	}
	
	public void addSubList(Info info) throws Exception{
		List<Aptitude> aptitudeList=info.getAptitudeList();
		if(BeanUtils.isNotEmpty(aptitudeList)){
			for(Aptitude aptitude:aptitudeList){
				aptitude.setInfoId(info.getSysOrgInfoId());
				aptitude.setId(UniqueIdUtil.genId());
				aptitudeDao.add(aptitude);
			}
		}
	}
	
	private void delByPk(Long sysOrgInfoId){
		aptitudeDao.delBySqlKey("delByFid", sysOrgInfoId);
	}
	
	public void updateAll(Info info) throws Exception{
		//根据国家填写国旗
		info.setFlaglogo(sysUserService.getFlagLogoByCountry(info.getCountry()));
		update(info);
		delByPk(info.getSysOrgInfoId());
		addSubList(info);
	}
	
	/**
	 * 删除企业,真正的删除
	 * 此操作将删除所有和企业相关的数据
	 * @param lAryId
	 */
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){
			jdbcTemplate.execute("call proc_org_remove(" + id + ")");
			
			//最后删除企业,删除索引
			dao.delById(id);
		}
	}
	
	public void addAll(Info info) throws Exception{
		add(info);
		addSubList(info);
	}
}
