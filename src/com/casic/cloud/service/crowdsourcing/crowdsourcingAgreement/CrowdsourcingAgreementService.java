package com.casic.cloud.service.crowdsourcing.crowdsourcingAgreement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreement;
import com.casic.cloud.dao.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreementDao;
import com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreementDetail;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponse;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponseDetail;
import com.casic.cloud.dao.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreementDetailDao;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.webservice.api.ProcessService;

/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_agreement Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 16:59:12
 *</pre>
 */
@Service
public class CrowdsourcingAgreementService extends BaseService<CrowdsourcingAgreement>
{
	@Resource
	private CrowdsourcingAgreementDao dao;
	
	@Resource
	private CrowdsourcingAgreementDetailDao crowdsourcingAgreementDetailDao;
	
	@Resource
	private ProcessService processService;
	
	@Resource
	private TaskOpinionService taskOpinionService;
	
	public CrowdsourcingAgreementService()
	{
	}
	
	@Override
	protected IEntityDao<CrowdsourcingAgreement, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long id){
		crowdsourcingAgreementDetailDao.delByMainId(id);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(CrowdsourcingAgreement crowdsourcingAgreement) throws Exception{
		add(crowdsourcingAgreement);
		addSubList(crowdsourcingAgreement);
	}
	
	public void updateAll(CrowdsourcingAgreement crowdsourcingAgreement) throws Exception{
		update(crowdsourcingAgreement);
		delByPk(crowdsourcingAgreement.getId());
		addSubList(crowdsourcingAgreement);
	}
	
	public void addSubList(CrowdsourcingAgreement crowdsourcingAgreement) throws Exception{
		List<CrowdsourcingAgreementDetail> crowdsourcingAgreementDetailList=crowdsourcingAgreement.getCrowdsourcingAgreementDetailList();
		if(BeanUtils.isNotEmpty(crowdsourcingAgreementDetailList)){
			for(CrowdsourcingAgreementDetail crowdsourcingAgreementDetail:crowdsourcingAgreementDetailList){
				crowdsourcingAgreementDetail.setAgreementId(crowdsourcingAgreement.getId());
				crowdsourcingAgreementDetail.setId(UniqueIdUtil.genId());
				crowdsourcingAgreementDetailDao.add(crowdsourcingAgreementDetail);
			}
		}
	}
	
	public List<CrowdsourcingAgreementDetail> getCrowdsourcingAgreementDetailList(Long id) {
		return crowdsourcingAgreementDetailDao.getByMainId(id);
	}
	
	
	public CrowdsourcingAgreement getBySourceId(Long id){		
		return dao.getBySourceId(id);
	}
	
	/**
	 * 设置 cmd 启动流程
	 * @param defKey 流程定义key
	 * @param businessKey  数据id
	 * @return
	 */
	public void setCmdtoStart(String defKey, String businessKey) throws Exception{
		ProcessCmd processCmd = new ProcessCmd();
		processCmd.setFlowKey(defKey);
		processCmd.setBusinessKey(businessKey);
		processService.start(processCmd);
	}
	
	/**
	 * 设置 cmd 完成任务
	 * @param taskId 任务id
	 * @param voteAgree 流程动作
	 * @return
	 */
	public void setCmdtoComplete(String taskId, String voteAgree) throws Exception{
		ProcessCmd processCmd = new ProcessCmd();
		processCmd.setTaskId(taskId);
		processCmd.setVoteAgree(new Short(voteAgree));
		processService.doNext(processCmd);
	}
	
	protected CrowdsourcingAgreement getFormObject(ProcessCmd cmd) throws Exception {
		 net.sf.json.util.JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
	   	Map<String,String> maps=cmd.getFormDataMap();
	   	if(maps!=null){
	   		String json=maps.get("json");
	   		JSONObject obj = JSONObject.fromObject(json);
	   		Map<String,  Class> map=new HashMap<String,  Class>();
	   		map.put("crowdsourcingAgreementDetailList", CrowdsourcingAgreementDetail.class);
	   		CrowdsourcingAgreement crowdsourcingAgreement = (CrowdsourcingAgreement)JSONObject.toBean(obj, CrowdsourcingAgreement.class,map);
	   		crowdsourcingAgreement.setOperaterId(ContextUtil.getCurrentUserId());
	   		return crowdsourcingAgreement;
	   	}
		return null;
	   }
	/*
	 * 前置处理器调用函数
	 */
	
	public void saveFromProcess(ProcessCmd cmd) throws Exception{
		
		CrowdsourcingAgreement crowdsourcingAgreement=getFormObject(cmd);
		Short  d = cmd.getVoteAgree();
				
		if(d==1){
			try{
				if(crowdsourcingAgreement==null) return;
				if(crowdsourcingAgreement.getId()==null||crowdsourcingAgreement.getId()==0){
					crowdsourcingAgreement.setId(UniqueIdUtil.genId());
					addAll(crowdsourcingAgreement);			
					System.out.println("保存合同表成功");
				}else{
				    updateAll(crowdsourcingAgreement);
				    System.out.println("更新合同表成功");
				}
				
			}catch(Exception e){
				System.out.println("保存合同表失败");
			}
		}
	}
}
