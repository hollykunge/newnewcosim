package com.casic.cloud.service.research.fileSign;

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
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.research.fileSign.FileSign;
import com.casic.cloud.dao.research.fileSign.FileSignDao;
import com.casic.cloud.model.research.fileSign.FileSignInfo;
import com.casic.cloud.dao.research.fileSign.FileSignInfoDao;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.webservice.api.ProcessService;

/**
 *<pre>
 * 对象功能:CLOUD_RESEARCH_FILESIGN Service类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-18 14:33:03
 *</pre>
 */
@Service
public class FileSignService extends BaseService<FileSign>
{
	@Resource
	private FileSignDao dao;
	
	@Resource
	private FileSignInfoDao fileSignInfoDao;
	
	@Resource
	private ProcessService processService;
	
	public FileSignService()
	{
	}
	
	@Override
	protected IEntityDao<FileSign, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long id){
		fileSignInfoDao.delByMainId(id);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(FileSign fileSign) throws Exception{
		add(fileSign);
		addSubList(fileSign);
	}
	
	/**
	 * 取得 FileSign 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected FileSign getFormObject(ProcessCmd cmd) throws Exception {
    	net.sf.json.util.JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    	Map<String,String> maps=cmd.getFormDataMap();
    	if(maps!=null){
    		String json=maps.get("json");
    		JSONObject obj = JSONObject.fromObject(json);
    		Map<String,  Class> map=new HashMap<String,  Class>();
    		map.put("fileSignInfoList", FileSignInfo.class);
    		FileSign fileSign = (FileSign)JSONObject.toBean(obj, FileSign.class,map);
    		//saleQuote.setOperatorId(ContextUtil.getCurrentUser().getUserId());
    		return fileSign;
    	}
		return null;
    }
	
	
	
	public void saveFromProcess(ProcessCmd cmd) throws Exception{
		
		FileSign fileSign=getFormObject(cmd);
	

		// runtimeService.getVariableLocal(cmd.getTaskId(), TaskFork.TAKEN_VAR_NAME);
		// Object  d = taskService.getVariableLocal(cmd.getTaskId(), TaskFork.TAKEN_VAR_NAME);
		//意见  是否同意 0=弃权票 1=同意 2=拒绝
		Short  d = cmd.getVoteAgree();
		if(d==1){
			try{
				if(fileSign==null) return;
				if(fileSign.getId()==null||fileSign.getId()==0){
					fileSign.setId(UniqueIdUtil.genId());
					addAll(fileSign);			
					System.out.println("保存成功");
				}else{
				    updateAll(fileSign);
				    System.out.println("更新成功");
				}
				
			}catch(Exception e){
				System.out.println("保存失败");
			}
		}else{
			System.out.println("弃权/不同意");
		}
	}
	
	public void updateAll(FileSign fileSign) throws Exception{
		update(fileSign);
		delByPk(fileSign.getId());
		addSubList(fileSign);
	}
	
	public void addSubList(FileSign fileSign) throws Exception{
		List<FileSignInfo> fileSignInfoList=fileSign.getFileSignInfoList();
		if(BeanUtils.isNotEmpty(fileSignInfoList)){
			for(FileSignInfo fileSignInfo:fileSignInfoList){
				fileSignInfo.setFilesignId(fileSign.getId());
				fileSignInfo.setId(UniqueIdUtil.genId());
				fileSignInfoDao.add(fileSignInfo);
			}
		}
	}
	
	public List<FileSignInfo> getFileSignInfoList(Long id) {
		return fileSignInfoDao.getByMainId(id);
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
}
