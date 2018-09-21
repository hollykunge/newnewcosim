package com.hotent.platform.service.bpm;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.platform.model.bpm.BpmAgent;
import com.hotent.platform.model.system.SysUserAgent;
import com.hotent.platform.dao.bpm.BpmAgentDao;
import com.hotent.platform.dao.system.SysUserAgentDao;

/**
 * 对象功能:流程代理 Service类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2012-01-07 17:31:48
 */
@Service
public class BpmAgentService extends BaseService<BpmAgent>
{
	@Resource
	private BpmAgentDao dao;
	@Resource
	private SysUserAgentDao sysUserAgentDao;
	public BpmAgentService()
	{
	}
	
	public void add(ProcessCmd cmd){
		
	}
	
	@Override
	protected IEntityDao<BpmAgent, Long> getEntityDao() 
	{
		return dao;
	}

	public List<BpmAgent> getByAgentId(Long agentid) {
		return dao.getByAgentId(agentid);
	}
	
	public List<String> getNotInByAgentId(Long agentid){
		return dao.getNotInByAgentId(agentid);
	}
	
	public void delByActDefId(String actDefId){
		List<BpmAgent> list = dao.getByActDefId(actDefId);
		if(list.size()>0){
			dao.delByActDefId(actDefId);
			for(BpmAgent bpmAgent:list){
				List<BpmAgent> agentList = dao.getByAgentId(bpmAgent.getAgentid());
				if(agentList.size()==0){
					sysUserAgentDao.delById(bpmAgent.getAgentid());
				}
			}
		}
	}
}
