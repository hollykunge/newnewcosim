package com.hotent.platform.dao.bpm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.BeanUtils;
import com.hotent.platform.model.bpm.ProRunTblPk;
/**
 *<pre>
 * 对象功能:BPM_PRO_RUN_TBL_PK Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:Raise
 * 创建时间:2013-04-16 09:54:27
 *</pre>
 */
@Repository
public class ProRunTblPkDao extends BaseDao<ProRunTblPk>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ProRunTblPk.class;
	}
	public Map<Long,ProRunTblPk> getByRunId(Long runId){
		Map<Long,ProRunTblPk> map = new HashMap<Long,ProRunTblPk>();
		List<ProRunTblPk> tblPkList = this.getMainByRunId(runId);
		for(ProRunTblPk main:tblPkList){
			List<ProRunTblPk> subProRunTblPks = this.getSubByRunIdAndMain(runId,main.getTableId(),main.getPkId());
			for(ProRunTblPk sub:subProRunTblPks){
				if(main.getSubProRunTblPkMap().containsKey(sub.getTableId())){
					main.getSubProRunTblPkMap().get(sub.getTableId()).add(sub);
				}else{
					List<ProRunTblPk> proRunTblPks = new ArrayList<ProRunTblPk>();
					proRunTblPks.add(sub);
					main.getSubProRunTblPkMap().put(sub.getTableId(), proRunTblPks);
				}
			}
			map.put(main.getTableId(), main);
		}
		return map;
	} 
	
	/**
	 * @param runId
	 * @return
	 */
	public List<ProRunTblPk> getMainByRunId(Long runId){
		return this.getBySqlKey("getMainByRunId", runId);
	}

	/**
	 * 
	 * @param runId
	 * @param mainTableId
	 * @param mainPk
	 * @return
	 */
	public List<ProRunTblPk> getSubByRunIdAndMain(Long runId,Long mainTableId,Long mainPk){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mainTableId", mainTableId);
		params.put("runId", runId);
		params.put("mainPkId", mainPk);
		return this.getBySqlKey("getSubByRunIdAndMain", params);
	}

	
	/**
	 * 级联添加
	 * @param pro
	 */
	public void addCascade(ProRunTblPk pro){
		add(pro);
		for(Entry<Long, List<ProRunTblPk>> entry : pro.getSubProRunTblPkMap().entrySet()){
			for(ProRunTblPk sub:entry.getValue()){
				add(sub);
			}
		}
	}
}