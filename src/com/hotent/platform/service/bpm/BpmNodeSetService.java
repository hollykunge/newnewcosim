package com.hotent.platform.service.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.util.BeanUtil;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.bpm.BpmNodeSetDao;
import com.hotent.platform.dao.form.BpmFormRightsDao;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.ProcessRun;

/**
 * 对象功能:BpmNodeSetService类 开发公司: 开发人员:cjj 创建时间:2011-12-06 13:41:44
 */
@Service
public class BpmNodeSetService extends BaseService<BpmNodeSet> {
	@Resource
	private BpmNodeSetDao dao;

	@Resource
	private BpmFormRightsDao bpmFormRightsDao;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	public BpmNodeSetService() {
	}

	@Override
	protected IEntityDao<BpmNodeSet, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 保存节点配置。
	 * 
	 * @param nodeList
	 * @throws Exception
	 */
	public void save(Long defId, List<BpmNodeSet> nodeList) throws Exception {
		dao.delByStartGlobalDefId(defId);
		for (BpmNodeSet node : nodeList) {
			if (node.getSetId() == null) {
				node.setSetId(UniqueIdUtil.genId());
				dao.add(node);
			} else {
				dao.update(node);
				// 表单类型
				if (node.getFormType() == 0) {
					if (!(node.getOldFormKey() == 0)) {
						if ((node.getFormKey().equals(node.getOldFormKey())))
							continue;
						// 原来定义过表单权限，并且新定义的在线表单和原定义的表单不一致。
						// 删除原节点的权限定义
						bpmFormRightsDao.delByFlowFormNodeId(
								node.getActDefId(), node.getNodeId());
					}
				} else {
					// 设置其他表单类型时，清空权限设置
					bpmFormRightsDao.delByFlowFormNodeId(node.getActDefId(),
							node.getNodeId());
				}
			}
		}
	}

	/**
	 * 根据流程设置ID取流程节点设置
	 * 
	 * @param defId
	 * @return
	 */
	public List<BpmNodeSet> getByDefId(Long defId) {
		return dao.getByDefId(defId);
	}

	/**
	 * 根据流程设置ID取流程所有的节点设置
	 * 
	 * @param defId
	 * @return
	 */
	public List<BpmNodeSet> getAllByDefId(Long defId) {
		return dao.getAllByDefId(defId);
	}

	/**
	 * 根据流程定义id和节点id获取BpmNodeSet对象。
	 * 
	 * @param defId
	 *            流程定义ID
	 * @param nodeId
	 *            节点ID
	 * @return
	 */
	public BpmNodeSet getByDefIdNodeId(Long defId, String nodeId) {
		return dao.getByDefIdNodeId(defId, nodeId);
	}

	/**
	 * 根据流程定义获取流程节点设置对象。
	 * 
	 * @param defId
	 * @return
	 */
	public Map<String, BpmNodeSet> getMapByDefId(Long defId) {

		return dao.getMapByDefId(defId);
	}

	/**
	 * 通过流程发布ID及节点id获取流程设置节点实体
	 * 
	 * @param deployId
	 * @param nodeId
	 * @return
	 */
	public BpmNodeSet getByActDefIdNodeId(String actDefId, String nodeId) {
		return dao.getByActDefIdNodeId(actDefId, nodeId);
	}

	/**
	 * 取得某个流程定义中对应的某个节点为汇总节点的配置
	 * 
	 * @param actDefId
	 * @param joinTaskKey
	 * @return
	 */
	public BpmNodeSet getByActDefIdJoinTaskKey(String actDefId,
			String joinTaskKey) {
		return dao.getByActDefIdJoinTaskKey(actDefId, joinTaskKey);
	}

	/**
	 * 根据流程定义Id和 表单类型查询 默认表单和起始表单。
	 * 
	 * @param defId
	 * @param setType
	 *            值为(1，开始表单，2，全局表单)
	 * @return
	 */
	public BpmNodeSet getBySetType(Long defId, Short setType) {
		return dao.getBySetType(defId, setType);
	}

	/**
	 * 根据流程定义获取当前的表单数据。
	 * 
	 * @param actDefId
	 * @return
	 */
	public List getByActDefId(String actDefId) {
		return dao.getByActDefId(actDefId);
	}

	/**
	 * 通过定义ID及节点Id更新isJumpForDef字段的设置
	 * 
	 * @param nodeId
	 * @param actDefId
	 * @param isJumpForDef
	 */
	public void updateIsJumpForDef(String nodeId, String actDefId,
			Short isJumpForDef) {
		dao.updateIsJumpForDef(nodeId, actDefId, isJumpForDef);
	}

	/**
	 * 获取当前任务节点的 url明细
	 * 
	 * @param actDefId
	 * @param nodeId
	 * @return
	 */
	// public String getDetailUrl(String actDefId, String nodeId,ProcessRun
	// processRun,String ctxPath) {
	// String detailUrl="";
	// BpmNodeSet bpmNodeSet=dao.getByActDefIdNodeId(actDefId, nodeId);
	// if(BpmNodeSet.FORM_TYPE_ONLINE==bpmNodeSet.getFormType()&&
	// bpmNodeSet.getFormDefId()==null){
	// return "";
	// }
	//
	// if(StringUtil.isNotEmpty(bpmNodeSet.getDetailUrl())){
	// detailUrl=bpmNodeSet.getDetailUrl();
	// }
	//
	// if(StringUtil.isEmpty(detailUrl)){
	// BpmDefinition bpmDefintion=bpmDefinitionService.getByActDefId(actDefId);
	// BpmNodeSet gloabalNodeSet=dao.getBySetType(bpmDefintion.getDefId(),
	// BpmNodeSet.SetType_GloabalForm);
	// if(BeanUtils.isNotEmpty(gloabalNodeSet)){
	// if(StringUtil.isNotEmpty(gloabalNodeSet.getDetailUrl())){
	// detailUrl=gloabalNodeSet.getDetailUrl();
	// }
	// }
	// }
	// if(StringUtil.isNotEmpty(detailUrl)&&
	// StringUtil.isNotEmpty(processRun.getBusinessKey())){
	// detailUrl=detailUrl.replaceFirst(BpmConst.FORM_PK_REGEX,
	// processRun.getBusinessKey());
	// if(!detailUrl.startsWith("http")){
	// detailUrl=ctxPath+detailUrl;
	// }
	// }
	// return detailUrl;
	// }

}
