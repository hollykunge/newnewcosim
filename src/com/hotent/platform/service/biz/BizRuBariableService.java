package com.hotent.platform.service.biz;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.biz.BizRuVariable;
import com.hotent.platform.dao.biz.BizRuVariableDao;

/**
 *<pre>
 * 对象功能:BIZ_RU_VARIABLE Service类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-20 14:29:43
 *</pre>
 */
@Service
public class BizRuBariableService extends BaseService<BizRuVariable>
{
	@Resource
	private BizRuVariableDao dao;
	
	public BizRuBariableService()
	{
	}
	
	@Override
	protected IEntityDao<BizRuVariable, Long> getEntityDao() 
	{
		return dao;
	}
	
	
	/**
	 *  添加业务变量
	 * @param bizInstId
	 * @param bizInstSegmentId
	 * @param varName
	 * @param value
	 * @throws IOException
	 */
	public void addVariable(Long bizInstId,Long bizInstSegmentId,String varName,Object value) throws IOException{
		if(dao.isInstVarNameExisted(bizInstId,varName)){
			dao.delByName(varName);
		}
		BizRuVariable bizRuVariable = new BizRuVariable();
		bizRuVariable.setBizInstId(bizInstId);
		bizRuVariable.setBizInstSegId(bizInstSegmentId);
		bizRuVariable.setValue(value);
		bizRuVariable.setVarId(UniqueIdUtil.genId());
		bizRuVariable.setVarName(varName);
		bizRuVariable.setVarType(value.getClass().getName());
		dao.add(bizRuVariable);
	}
	
	/**
	 *  添加业务变量
	 * @param bizInstId
	 * @param bizInstSegmentId
	 * @param variables
	 * @throws IOException
	 */
	public void addVariable(Long bizInstId,Long bizInstSegmentId,Map<String,Object> variables) throws IOException{
		for(Entry<String, Object> entry:variables.entrySet()){
			String varName = entry.getKey();
			Object value = entry.getValue();
			if(value==null){
				return;
			}
			if(dao.isInstVarNameExisted(bizInstId,varName)){
				dao.delByName(varName);
			}
			BizRuVariable bizRuVariable = new BizRuVariable();
			bizRuVariable.setBizInstId(bizInstId);
			bizRuVariable.setBizInstSegId(bizInstSegmentId);
			bizRuVariable.setValue(value);
			bizRuVariable.setVarId(UniqueIdUtil.genId());
			bizRuVariable.setVarName(varName);
			bizRuVariable.setVarType(value.getClass().getName());
			dao.add(bizRuVariable);
		}
	}

	/**
	 * 根据业务实例ID，删除相应业务实例所有的业务变量
	 * @param bizInstanceId
	 */
	public void delByBizInstId(Long bizInstanceId) {
		dao.delByBizInstId(bizInstanceId);
	}
	
	
	/**
	 * 获取业务变量
	 * @param bizInstanceId
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Map<String,Object> getByBizInstId(Long bizInstanceId) throws IOException, ClassNotFoundException {
		return dao.getByBizInstId(bizInstanceId);
	}
	
	/**
	 * 添加业务变量
	 * @param bizInstanceId
	 * @param varName
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object getByBizInstIdAndName(Long bizInstanceId,String varName) throws IOException, ClassNotFoundException {
		return dao.getByBizInstIdAndName(bizInstanceId, varName);
	}
}
