package com.casic.cloud.service.crowdsourcing.crowdsourcingResponse;

import java.util.Date;
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
import com.casic.cloud.model.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponse;
import com.casic.cloud.dao.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponseDao;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponseDetail;
import com.casic.cloud.dao.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponseDetailDao;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.webservice.api.ProcessService;

/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_response Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:13:50
 *</pre>
 */
@Service
public class CrowdsourcingResponseService extends BaseService<CrowdsourcingResponse>
{
	@Resource
	private CrowdsourcingResponseDao dao;
	
	@Resource
	private CrowdsourcingResponseDetailDao crowdsourcingResponseDetailDao;
	
	@Resource
	private CrowdsourcingResponseDao crowdsourcingResponseDao;
	@Resource
	private ProcessService processService;
	
	public CrowdsourcingResponseService()
	{
	}
	
	@Override
	protected IEntityDao<CrowdsourcingResponse, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long id){
		crowdsourcingResponseDetailDao.delByMainId(id);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(CrowdsourcingResponse crowdsourcingResponse) throws Exception{
		add(crowdsourcingResponse);
		addSubList(crowdsourcingResponse);
	}
	
	public void updateAll(CrowdsourcingResponse crowdsourcingResponse) throws Exception{
		update(crowdsourcingResponse);
		delByPk(crowdsourcingResponse.getId());
		addSubList(crowdsourcingResponse);
	}
	
	public void addSubList(CrowdsourcingResponse crowdsourcingResponse) throws Exception{
		List<CrowdsourcingResponseDetail> crowdsourcingResponseDetailList=crowdsourcingResponse.getCrowdsourcingResponseDetailList();
		if(BeanUtils.isNotEmpty(crowdsourcingResponseDetailList)){
			for(CrowdsourcingResponseDetail crowdsourcingResponseDetail:crowdsourcingResponseDetailList){
				crowdsourcingResponseDetail.setResponseId(crowdsourcingResponse.getId());
				crowdsourcingResponseDetail.setId(UniqueIdUtil.genId());
				crowdsourcingResponseDetailDao.add(crowdsourcingResponseDetail);
			}
		}
	}
	
	public List<CrowdsourcingResponseDetail> getCrowdsourcingResponseDetailList(Long id) {
		return crowdsourcingResponseDetailDao.getByMainId(id);
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
	
	
	protected CrowdsourcingResponse getFormObject(ProcessCmd cmd) throws Exception {
		 net.sf.json.util.JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
	   	Map<String,String> maps=cmd.getFormDataMap();
	   	if(maps!=null){
	   		String json=maps.get("json");
	   		JSONObject obj = JSONObject.fromObject(json);
	   		Map<String,  Class> map=new HashMap<String,  Class>();
	   		map.put("crowdsourcingResponseDetailList", CrowdsourcingResponseDetail.class);
	   		CrowdsourcingResponse crowdsourcingResponse = (CrowdsourcingResponse)JSONObject.toBean(obj, CrowdsourcingResponse.class,map);
	   		crowdsourcingResponse.setOperaterId(ContextUtil.getCurrentUserId());
	   		return crowdsourcingResponse;
	   	}
		return null;
	   }
	/*
	 * 前置处理器调用函数
	 */
	
	public void saveFromProcess(ProcessCmd cmd) throws Exception{
		
		CrowdsourcingResponse crowdsourcingResponse=getFormObject(cmd);
		Short  d = cmd.getVoteAgree();
		if(d==1){
			try{
				if(crowdsourcingResponse==null) return;
				if(crowdsourcingResponse.getId()==null||crowdsourcingResponse.getId()==0){
					crowdsourcingResponse.setId(UniqueIdUtil.genId());
					addAll(crowdsourcingResponse);			
					System.out.println("保存响应表成功");
				}else{
				    updateAll(crowdsourcingResponse);
				    System.out.println("更新响应表成功");
				}
				
			}catch(Exception e){
				System.out.println("保存响应表失败");
			}
		}
	}
	public List<CrowdsourcingResponse> getBySourceId(Long sourceformCrowdsourcingId){
		
		return crowdsourcingResponseDao.getBySourceId(sourceformCrowdsourcingId);
	}
}
