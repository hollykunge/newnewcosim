package com.casic.cloud.controller.research.filecheck;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;


import com.casic.cloud.model.research.filecheck.ResFilecheck;
import com.casic.cloud.service.research.filecheck.ResFilecheckService;
import com.casic.cloud.model.research.filecheck.ResFilecheckDetail;
import com.casic.cloud.model.research.filecheck.ResFilecheckOpinion;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.webservice.api.ProcessService;
/**
 *<pre>
 * 对象功能:文档审查 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-21 16:04:50
 *</pre>
 */
@Controller
@RequestMapping("/cloud/research/filecheck/")
public class ResFilecheckController extends BaseController
{
	@Resource
	private ResFilecheckService resFilecheckService;
	@Resource
	private IdentityService identityService;
	@Resource
	private ProcessService processService;
	private final String defKey = "checkFile";	//绑定流程定义
	
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private RuntimeService runtimeService;

	
	/**
	 * 添加或更新文档审查。
	 * @param request
	 * @param response
	 * @param resFilecheck 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		ResFilecheck resFilecheck=getFormObject(request);
		try{
			if(resFilecheck.getId()==null||resFilecheck.getId()==0){
				resFilecheck.setId(UniqueIdUtil.genId());
				//设置第一个会签任务的执行人				
				resFilecheckService.addAll(resFilecheck);			
				resultMsg=getText("record.added","文档审查");
			}else{
			    resFilecheckService.updateAll(resFilecheck);
				resultMsg=getText("record.updated","文档审查");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 ResFilecheck 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected ResFilecheck getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("resFilecheckDetailList", ResFilecheckDetail.class);
		map.put("resFilecheckOpinionList", ResFilecheckOpinion.class);
		ResFilecheck resFilecheck = (ResFilecheck)JSONObject.toBean(obj, ResFilecheck.class,map);
		
		return resFilecheck;
    }
	
	/**
	 * 取得文档审查分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		
		QueryFilter queryFilter = new QueryFilter(request,"resFilecheckItem");
		queryFilter.getFilters().put("operatorId", ContextUtil.getCurrentUser().getUserId());
		
		List<ResFilecheck> list=resFilecheckService.getAll(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("resFilecheckList",list);
		
		return mv;
	}
	
	/**
	 * 删除文档审查
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			resFilecheckService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除文档审查及其从表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑文档审查
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("create")
	public ModelAndView create(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		ResFilecheck resFilecheck=resFilecheckService.getById(id);
		List<ResFilecheckDetail> resFilecheckDetailList=new ArrayList<ResFilecheckDetail>();	
		List<ResFilecheckOpinion> resFilecheckOpinionList=new ArrayList<ResFilecheckOpinion>();
		if(resFilecheck==null){
			resFilecheck=new ResFilecheck();
			String orderNo=identityService.doNextId("ResFileCheckNo");
			//自动生成询价单编码、制单人、采购企业
			resFilecheck.setCode(orderNo);			
			resFilecheck.setOperatorId(ContextUtil.getCurrentUser().getUserId());
			resFilecheck.setOperatorName(ContextUtil.getCurrentUser().getFullname());
			resFilecheck.setEnterpriseId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
			resFilecheck.setEnterpriseName(ContextUtil.getCurrentOrgInfoFromSession().getName());
			resFilecheck.setOperateDate(new Date());
			
			
			List<BpmNodeSet> nodes =bpmNodeSetService.getAllByDefId(bpmDefinitionService.getMainByDefKey("checkFile").getDefId());
			
			//存储后续审签节点的定义,即跳过第一个节点
			for(int i=1;i<nodes.size();i++){
				BpmNodeSet node = nodes.get(i);
				ResFilecheckOpinion op = new ResFilecheckOpinion();
				op.setTaskId(node.getNodeId());
				op.setTaskName(node.getNodeName());
				resFilecheckOpinionList.add(op);
			}
			resFilecheck.setResFilecheckOpinionList(resFilecheckOpinionList);
			
			
		}else{
			resFilecheckDetailList=resFilecheckService.getResFilecheckDetailList(id);
			resFilecheckOpinionList=resFilecheckService.getResFilecheckOpinionList(id);
			
			List<ProcessTask> tasks = processService.getTasksByRunId(resFilecheck.getRunId());
			if( "2-校对".equals(tasks.get(0).getName() ) ){
				//设置下一步执行人，本企业人员
				//resFilecheck.setCurrentEnterpIds(resFilecheckService.getResFilecheckOpinionList(id).get(1).getCheckEnterpriseids());
				resFilecheck.setCurrentUserIds(resFilecheckService.getResFilecheckOpinionList(id).get(1).getCheckUserids());
			}
			if( "3-审核".equals(tasks.get(0).getName() ) ){
				resFilecheck.setCurrentEnterpIds(resFilecheckService.getResFilecheckOpinionList(id).get(2).getCheckEnterpriseids());
				//resFilecheck.setCurrentUserIds(resFilecheckService.getResFilecheckOpinionList(id).get(2).getCheckUserids());
			}
			if( "4-会签".equals(tasks.get(0).getName() ) ){
				resFilecheck.setCurrentEnterpIds(resFilecheckService.getResFilecheckOpinionList(id).get(3).getCheckEnterpriseids());
				//resFilecheck.setCurrentUserIds(resFilecheckService.getResFilecheckOpinionList(id).get(3).getCheckUserids());
			}
			
			
		}
		return getAutoView().addObject("resFilecheck",resFilecheck)
				.addObject("resFilecheckDetailList",resFilecheckDetailList)
				.addObject("resFilecheckOpinionList",resFilecheckOpinionList)
				.addObject("returnUrl",returnUrl)
				.addObject("applyFlag",2);
	}
	
	

	
	/**
	 * 	编辑文档审查
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		ResFilecheck resFilecheck=resFilecheckService.getById(id);
		List<ResFilecheckDetail> resFilecheckDetailList=new ArrayList<ResFilecheckDetail>();	
		List<ResFilecheckOpinion> resFilecheckOpinionList=new ArrayList<ResFilecheckOpinion>();
		if(resFilecheck==null){
			resFilecheck=new ResFilecheck();
			String orderNo=identityService.doNextId("ResFileCheckNo");
			//自动生成询价单编码、制单人、采购企业
			resFilecheck.setCode(orderNo);			
			resFilecheck.setOperatorId(ContextUtil.getCurrentUser().getUserId());
			resFilecheck.setOperatorName(ContextUtil.getCurrentUser().getFullname());
			resFilecheck.setEnterpriseId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
			resFilecheck.setEnterpriseName(ContextUtil.getCurrentOrgInfoFromSession().getName());
			resFilecheck.setOperateDate(new Date());
		
		
			List<BpmNodeSet> nodes =bpmNodeSetService.getAllByDefId(bpmDefinitionService.getMainByDefKey("checkFile").getDefId());
		    
			//存储后续审签节点的定义,即跳过第一个节点
			for(int i=1;i<nodes.size();i++){
				BpmNodeSet node = nodes.get(i);
				ResFilecheckOpinion op = new ResFilecheckOpinion();
				op.setTaskId(node.getNodeId());
				op.setTaskName(node.getNodeName());
				resFilecheckOpinionList.add(op);
			}
			resFilecheck.setResFilecheckOpinionList(resFilecheckOpinionList);
			
			
		}else{
		    resFilecheckDetailList=resFilecheckService.getResFilecheckDetailList(id);
		    resFilecheckOpinionList=resFilecheckService.getResFilecheckOpinionList(id);
		    
		   List<ProcessTask> tasks = processService.getTasksByRunId(resFilecheck.getRunId());
		   if( "2-校对".equals(tasks.get(0).getName() ) ){
			   //设置下一步执行人，本企业人员
			   //resFilecheck.setCurrentEnterpIds(resFilecheckService.getResFilecheckOpinionList(id).get(1).getCheckEnterpriseids());
			   resFilecheck.setCurrentUserIds(resFilecheckService.getResFilecheckOpinionList(id).get(1).getCheckUserids());
		   }
		   if( "3-审核".equals(tasks.get(0).getName() ) ){
			   resFilecheck.setCurrentEnterpIds(resFilecheckService.getResFilecheckOpinionList(id).get(2).getCheckEnterpriseids());
			   resFilecheck.setCurrentUserIds(resFilecheckService.getResFilecheckOpinionList(id).get(2).getCheckUserids());
		   }
	
		   if( "4-会签".equals(tasks.get(0).getName() ) ){
			   resFilecheck.setCurrentEnterpIds(resFilecheckService.getResFilecheckOpinionList(id).get(3).getCheckEnterpriseids());
			   resFilecheck.setCurrentUserIds(resFilecheckService.getResFilecheckOpinionList(id).get(3).getCheckUserids());
		   }
		   if( "5-标准审查".equals(tasks.get(0).getName() ) ){
			   resFilecheck.setCurrentEnterpIds(resFilecheckService.getResFilecheckOpinionList(id).get(4).getCheckEnterpriseids());
			   resFilecheck.setCurrentUserIds(resFilecheckService.getResFilecheckOpinionList(id).get(4).getCheckUserids());
		   }
		   
		   
		}
		return getAutoView().addObject("resFilecheck",resFilecheck)
							.addObject("resFilecheckDetailList",resFilecheckDetailList)
							.addObject("resFilecheckOpinionList",resFilecheckOpinionList)
							.addObject("returnUrl",returnUrl)
							.addObject("currentuserid",ContextUtil.getCurrentUser().getUserId())
							.addObject("currentusername",ContextUtil.getCurrentUser().getFullname())
							.addObject("applyFlag",2);
	}

	

	/**
	 * 取得文档审查明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		ResFilecheck resFilecheck = resFilecheckService.getById(id);	
		List<ResFilecheckDetail> resFilecheckDetailList=resFilecheckService.getResFilecheckDetailList(id);
		List<ResFilecheckOpinion> resFilecheckOpinionList=resFilecheckService.getResFilecheckOpinionList(id);
		return getAutoView().addObject("resFilecheck",resFilecheck)
							.addObject("resFilecheckDetailList",resFilecheckDetailList)
							.addObject("resFilecheckOpinionList",resFilecheckOpinionList);
	}
	
	/**
	 * 添加或更新文档审查<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("apply")
	@Action(description = "添加或更新文档审查")
	public void apply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		ResFilecheck resFilecheck=getFormObject(request);
		long id=RequestUtil.getLong(request,"id");
		try {
			if (resFilecheck != null) {
				if (resFilecheck.getId() == null || resFilecheck.getId() == 0) {
					resFilecheck.setId(UniqueIdUtil.genId());
					resFilecheckService.add(resFilecheck);
					resultMsg = getText("record.added", "文档审查");
				} else {
					resFilecheckService.update(resFilecheck);
					resultMsg = getText("record.updated", "文档审查");
				}
				id =resFilecheck.getId();
			} 

			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setFlowKey(defKey);
			processCmd.setBusinessKey(String.valueOf(id));
			processCmd.setUserAccount(ContextUtil.getCurrentUser().getFullname());
			processService.start(processCmd);

			writeResultMessage(response.getWriter(), "文档审查成功", ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 执行下一步<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("complete")
	@Action(description = "执行下一步")
	public void complete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String taskId = RequestUtil.getString(request, "taskId");
		String voteAgree = RequestUtil.getString(request, "voteAgree");
		try {
			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setTaskId(taskId);
			processCmd.setUserAccount(ContextUtil.getCurrentUser().getFullname());
			processCmd.setVoteAgree(new Short(voteAgree));
			processService.doNext(processCmd);

			writeResultMessage(response.getWriter(), "执行成功" + "1", ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	
	/**
	 * 	文件预发布
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("start_prepublish")
	public ModelAndView start_prepublish(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		ResFilecheck resFilecheck=resFilecheckService.getById(id);
		List<ResFilecheckDetail> resFilecheckDetailList=new ArrayList<ResFilecheckDetail>();	
		List<ResFilecheckOpinion> resFilecheckOpinionList=new ArrayList<ResFilecheckOpinion>();
		if(resFilecheck==null){
			resFilecheck=new ResFilecheck();
			String orderNo=identityService.doNextId("ResFileCheckNo");
			//自动生成询价单编码、制单人、采购企业
			resFilecheck.setCode(orderNo);			
			resFilecheck.setOperatorId(ContextUtil.getCurrentUser().getUserId());
			resFilecheck.setOperatorName(ContextUtil.getCurrentUser().getFullname());
			resFilecheck.setEnterpriseId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
			resFilecheck.setEnterpriseName(ContextUtil.getCurrentOrgInfoFromSession().getName());
			resFilecheck.setOperateDate(new Date());
			
			//		List<BpmNodeSet> nodes =bpmNodeSetService.getAllByDefId(bpmDefinitionService.getMainByDefKey("checkFile").getDefId());
			List<BpmNodeSet> nodes =bpmNodeSetService.getAllByDefId(bpmDefinitionService.getMainByDefKey("pre_publish").getDefId());
			
			//存储后续审签节点的定义,即跳过第一个节点
			for(int i=1;i<nodes.size();i++){
				BpmNodeSet node = nodes.get(i);
				ResFilecheckOpinion op = new ResFilecheckOpinion();
				op.setTaskId(node.getNodeId());
				op.setTaskName(node.getNodeName());
				resFilecheckOpinionList.add(op);
			}
			resFilecheck.setResFilecheckOpinionList(resFilecheckOpinionList);
			
			
		}else{
			resFilecheckDetailList=resFilecheckService.getResFilecheckDetailList(id);
			resFilecheckOpinionList=resFilecheckService.getResFilecheckOpinionList(id);
			
			List<ProcessTask> tasks = processService.getTasksByRunId(resFilecheck.getRunId());
			if( "2-校对".equals(tasks.get(0).getName() ) ){
				//设置下一步执行人，本企业人员
				//resFilecheck.setCurrentEnterpIds(resFilecheckService.getResFilecheckOpinionList(id).get(1).getCheckEnterpriseids());
				resFilecheck.setCurrentUserIds(resFilecheckService.getResFilecheckOpinionList(id).get(1).getCheckUserids());
			}
			if( "3-审核".equals(tasks.get(0).getName() ) ){
				resFilecheck.setCurrentEnterpIds(resFilecheckService.getResFilecheckOpinionList(id).get(2).getCheckEnterpriseids());
				//resFilecheck.setCurrentUserIds(resFilecheckService.getResFilecheckOpinionList(id).get(2).getCheckUserids());
			}
			if( "4-会签".equals(tasks.get(0).getName() ) ){
				resFilecheck.setCurrentEnterpIds(resFilecheckService.getResFilecheckOpinionList(id).get(3).getCheckEnterpriseids());
				//resFilecheck.setCurrentUserIds(resFilecheckService.getResFilecheckOpinionList(id).get(3).getCheckUserids());
			}
		}
		return getAutoView().addObject("resFilecheck",resFilecheck)
				.addObject("resFilecheckDetailList",resFilecheckDetailList)
				.addObject("resFilecheckOpinionList",resFilecheckOpinionList)
				.addObject("returnUrl",returnUrl)
				.addObject("applyFlag",2);
	}
	
	
	
	/**
	 * 	编辑文档审查
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit_prepublish")
	public ModelAndView edit_prepublish(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		ResFilecheck resFilecheck=resFilecheckService.getById(id);
		List<ResFilecheckDetail> resFilecheckDetailList=new ArrayList<ResFilecheckDetail>();	
		if(resFilecheck==null){
			resFilecheck=new ResFilecheck();
			String orderNo=identityService.doNextId("ResFileCheckNo");
			//自动生成询价单编码、制单人、采购企业
			resFilecheck.setCode(orderNo);			
			resFilecheck.setOperatorId(ContextUtil.getCurrentUser().getUserId());
			resFilecheck.setOperatorName(ContextUtil.getCurrentUser().getFullname());
			resFilecheck.setEnterpriseId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
			resFilecheck.setEnterpriseName(ContextUtil.getCurrentOrgInfoFromSession().getName());
			resFilecheck.setOperateDate(new Date());
			
			
			List<BpmNodeSet> nodes =bpmNodeSetService.getAllByDefId(bpmDefinitionService.getMainByDefKey("pre_publish").getDefId());
			
			
			
		}else{
			resFilecheckDetailList=resFilecheckService.getResFilecheckDetailList(id);
			
			
			
		}
		return getAutoView().addObject("resFilecheck",resFilecheck)
				.addObject("resFilecheckDetailList",resFilecheckDetailList)
				.addObject("returnUrl",returnUrl)
				.addObject("currentuserid",ContextUtil.getCurrentUser().getUserId())
				.addObject("currentusername",ContextUtil.getCurrentUser().getFullname())
				.addObject("applyFlag",2);
	}
	
	
	/**
	 * 取得文档审查分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_prepublish")
	public ModelAndView list_prepublish(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		
		QueryFilter queryFilter = new QueryFilter(request,"resFilecheckItem");
		queryFilter.getFilters().put("operatorId", ContextUtil.getCurrentUser().getUserId());
		
		List<ResFilecheck> list=resFilecheckService.getAll(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("resFilecheckList",list);
		
		return mv;
	}
	
	/**
	 * 添加或更新文档审查。
	 * @param request
	 * @param response
	 * @param resFilecheck 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save_prepublish")
	public void save_prepublish(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		ResFilecheck resFilecheck=getFormObject(request);
		try{
			if(resFilecheck.getId()==null||resFilecheck.getId()==0){
				resFilecheck.setId(UniqueIdUtil.genId());
				//设置第一个会签任务的执行人				
				resFilecheckService.addAll_prepublish(resFilecheck);			
				resultMsg=getText("record.added","文档审查");
			}else{
			    resFilecheckService.updateAll(resFilecheck);
				resultMsg=getText("record.updated","文档审查");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	
}
