package com.hotent.platform.dao.biz;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.BeanUtils;
import com.hotent.platform.model.biz.BizRuVariable;
/**
 *<pre>
 * 对象功能:BIZ_RU_VARIABLE Dao类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-20 14:29:43
 *</pre>
 */
@Repository
public class BizRuVariableDao extends BaseDao<BizRuVariable>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BizRuVariable.class;
	}
	
	public Boolean isInstVarNameExisted(Long bizInstId,String varName){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("bizInstId", bizInstId);
		params.put("varName", varName);
		Integer count  =(Integer) getOne("isInstVarNameExisted",params);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	
	public void delByName(String varName){
		delBySqlKey("delByName", varName);
	}

	public void delByBizInstId(Long bizInstanceId) {
		delBySqlKey("delByBizInstId", bizInstanceId);
	}
	
	public Map<String,Object> getByBizInstId(Long bizInstanceId) throws IOException, ClassNotFoundException {
		Map<String,Object> vars = new HashMap<String, Object>();
		List<BizRuVariable> bizRuVariables = getBySqlKey("getByBizInstId", bizInstanceId);
		for(BizRuVariable bizv:bizRuVariables){
			vars.put(bizv.getVarName(), bizv.getValue());
		}
		return vars;
	}
	
	public Object getByBizInstIdAndName(Long bizInstanceId,String varName) throws IOException, ClassNotFoundException {
		Map<String,Object> params= new HashMap<String, Object>();
		params.put("bizInstId", bizInstanceId);
		params.put("varName", varName);
		List<BizRuVariable> bizRuVariables = getBySqlKey("getByBizInstIdAndName", params);
		if(BeanUtils.isEmpty(bizRuVariables)){
			return null;
		}else{
			return bizRuVariables.get(0).getValue();
		}
	}

}