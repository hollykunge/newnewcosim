package com.hotent.platform.service.biz;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.biz.BizDef;
import com.hotent.platform.dao.biz.BizDefDao;
import com.hotent.platform.model.biz.BizDefSegment;
import com.hotent.platform.dao.biz.BizDefSegmentDao;

/**
 *<pre>
 * 对象功能:业务定义，如邀标采购这样的大业务。 Service类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Service
public class BizDefService extends BaseService<BizDef>
{
	@Resource
	private BizDefDao dao;
	
	@Resource
	private BizDefSegmentDao bizDefSegmentDao;
	
	
	public BizDefService()
	{
	}
	
	@Override
	protected IEntityDao<BizDef, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long bizDefId){
		bizDefSegmentDao.delByMainId(bizDefId);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(BizDef bizDef) throws Exception{
		add(bizDef);
		addSubList(bizDef);
	}
	
	public void updateAll(BizDef bizDef) throws Exception{
		update(bizDef);
		delByPk(bizDef.getBizDefId());
		addSubList(bizDef);
	}
	
	public void addSubList(BizDef bizDef) throws Exception{
		List<BizDefSegment> bizDefSegmentList=bizDef.getBizDefSegmentList();
		if(BeanUtils.isNotEmpty(bizDefSegmentList)){
			for(BizDefSegment BizDefSegment:bizDefSegmentList){
				BizDefSegment.setBizDefId(bizDef.getBizDefId());
				BizDefSegment.setBizDefSegmentId(UniqueIdUtil.genId());
				bizDefSegmentDao.add(BizDefSegment);
			}
		}
	}
	
	/**
	 * 根据业务定义ID，取得相应业务所有的业务环节
	 * @param bizDefId
	 * @return
	 */
	public List<BizDefSegment> getBizDefSegmentList(Long bizDefId) {
		return bizDefSegmentDao.getByMainId(bizDefId);
	}

	/**
	 * 根据业务编号，取得相应业务最大版本号
	 * @param bizDefNo
	 * @return
	 */
	public Short getMaxVersionNoByNo(String bizDefNo) {
		return dao.getMaxVersionNoByNo(bizDefNo);
	}
	
	
}
