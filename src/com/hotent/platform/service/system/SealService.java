package com.hotent.platform.service.system;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.platform.model.system.Seal;
import com.hotent.platform.dao.system.SealDao;
import com.hotent.platform.dao.system.SysFileDao;

/**
 * 对象功能:电子印章 Service类
 * 开发公司:
 * 开发人员:raise
 * 创建时间:2012-08-29 11:26:00
 */
@Service
public class SealService extends BaseService<Seal>
{
	@Resource
	private SealDao dao;
	
	@Resource 
	private SysFileDao sysFileDao;
	
	public SealService()
	{
	}
	
	@Override
	protected IEntityDao<Seal, Long> getEntityDao() 
	{
		return dao;
	}
	
	@Override
	public void delByIds(Long[] ids) {
		if(BeanUtils.isEmpty(ids)) return;
		for (Long p : ids){
			Seal seal = this.getById(p);
			delById(p);
			if(seal.getAttachmentId()!=null){
				sysFileDao.delById(seal.getAttachmentId());
			}
		}
	}
}
