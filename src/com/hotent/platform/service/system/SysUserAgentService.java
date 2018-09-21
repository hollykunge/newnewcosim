package com.hotent.platform.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.platform.dao.bpm.BpmAgentDao;
import com.hotent.platform.dao.system.SysUserAgentDao;
import com.hotent.platform.model.bpm.BpmAgent;
import com.hotent.platform.model.system.SysUserAgent;

/**
 * 对象功能:SYS_USER_AGENT Service类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2011-12-27 11:54:23
 */
@Service
public class SysUserAgentService extends BaseService<SysUserAgent>
{
	@Resource
	private SysUserAgentDao dao;
	
	@Resource
	private BpmAgentDao bpmAgentDao;
	
	public SysUserAgentService()
	{
	}
	
	@Override
	protected IEntityDao<SysUserAgent, Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 添加对像及人员
	 * @param position
	 * @param upList
	 */
	public void add(SysUserAgent sysUserAgent,List<BpmAgent> upList){
		this.add(sysUserAgent);
		if(sysUserAgent.getIsall().intValue()!=SysUserAgent.IS_ALL_FLAG && upList!=null&&upList.size()>0){
			//假如授权不是全权代理则加流程关联,否则不加
			for(BpmAgent up:upList){
				up.setAgentid(sysUserAgent.getAgentid());
				up.setAgentuserid(sysUserAgent.getAgentuserid());
				up.setTouserid(sysUserAgent.getTouserid());
				bpmAgentDao.add(up);
			}
		}
	}
	
	/**
	 *修改对像及人员
	 * @param position
	 * @param upList
	 */
	public void update(SysUserAgent sysUserAgent,List<BpmAgent> upList){
		this.update(sysUserAgent);
		bpmAgentDao.delByAgentId(sysUserAgent.getAgentid());
		if(sysUserAgent.getIsall().intValue()!=SysUserAgent.IS_ALL_FLAG && upList!=null && upList.size()>0){
			//假如授权不是全权代理则加流程关联,否则不加
			for(BpmAgent up:upList){
				up.setAgentid(sysUserAgent.getAgentid());
				up.setAgentuserid(sysUserAgent.getAgentuserid());
				up.setTouserid(sysUserAgent.getTouserid());
				bpmAgentDao.add(up);
			}
		}
	}

	public List<SysUserAgent> getByTouserId(Long userId) {
		return dao.getByTouserId(userId);
	}
}
