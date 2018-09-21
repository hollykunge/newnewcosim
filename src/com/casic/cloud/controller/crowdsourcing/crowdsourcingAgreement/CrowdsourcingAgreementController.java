package com.casic.cloud.controller.crowdsourcing.crowdsourcingAgreement;

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
import org.springframework.web.bind.annotation.ResponseBody;

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

import com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreement;
import com.casic.cloud.service.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreementService;
import com.casic.cloud.service.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireService;
import com.casic.cloud.service.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponseService;
import com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreementDetail;
import com.casic.cloud.model.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequire;
import com.casic.cloud.model.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireDetail;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponse;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponseDetail;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.webservice.api.ProcessService;
/**
 *<pre>
 * 对象功能:研发众包合同 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 16:59:12
 *</pre>
 */
@Controller
@RequestMapping("/cloud/crowdsourcing/crowdsourcingAgreement/")
public class CrowdsourcingAgreementController extends BaseController
{
	@Resource
	private CrowdsourcingAgreementService crowdsourcingAgreementService;
	@Resource
	private CrowdsourcingRequireService crowsourcingRequireService;
	@Resource
	private CrowdsourcingRequireService crowdsourcingRequireService;
	@Resource
	private CrowdsourcingResponseService crowdsourcingResponseService;
	@Resource
	private IdentityService identityService;
	
	@Resource
	private ProcessService processService;
	private final String defKey = "crowdresearch";	//绑定流程定义
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private RuntimeService runtimeService;
	/**
	 * 添加或更新研发众包合同。
	 * @param request
	 * @param response
	 * @param crowdsourcingAgreement 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新研发众包合同")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CrowdsourcingAgreement crowdsourcingAgreement=getFormObject(request);
		try{
			if(crowdsourcingAgreement.getId()==null||crowdsourcingAgreement.getId()==0){
				crowdsourcingAgreement.setId(UniqueIdUtil.genId());
				crowdsourcingAgreementService.addAll(crowdsourcingAgreement);			
				resultMsg=getText("record.added","研发众包合同");
			}else{
			    crowdsourcingAgreementService.updateAll(crowdsourcingAgreement);
				resultMsg=getText("record.updated","研发众包合同");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CrowdsourcingAgreement 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CrowdsourcingAgreement getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("crowdsourcingAgreementDetailList", CrowdsourcingAgreementDetail.class);
		CrowdsourcingAgreement crowdsourcingAgreement = (CrowdsourcingAgreement)JSONObject.toBean(obj, CrowdsourcingAgreement.class,map);
		
		return crowdsourcingAgreement;
    }
	
	/**
	 * 取得研发众包合同分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看研发众包合同分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CrowdsourcingAgreement> list=crowdsourcingAgreementService.getAll(new QueryFilter(request,"crowdsourcingAgreementItem"));
		ModelAndView mv=this.getAutoView().addObject("crowdsourcingAgreementList",list);
		
		return mv;
	}
	
	/**
	 * 删除研发众包合同
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除研发众包合同")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			crowdsourcingAgreementService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除研发众包合同及其从表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑研发众包合同
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑研发众包合同")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CrowdsourcingAgreement crowdsourcingAgreement=crowdsourcingAgreementService.getById(id);
		List<CrowdsourcingAgreementDetail> crowdsourcingAgreementDetailList=crowdsourcingAgreementService.getCrowdsourcingAgreementDetailList(id);

		return getAutoView().addObject("crowdsourcingAgreement",crowdsourcingAgreement)
							.addObject("crowdsourcingAgreementDetailList",crowdsourcingAgreementDetailList)
							.addObject("returnUrl",returnUrl);
	}
	@RequestMapping("create")
	@Action(description="编辑研发众包合同")
	public ModelAndView create(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		CrowdsourcingRequire cr=crowsourcingRequireService.getById(id);
		String returnUrl=RequestUtil.getPrePage(request);
		List<CrowdsourcingResponse> crowdsourcingResponseList = crowdsourcingResponseService.getBySourceId(id);
		
		//查找是否已经存在		
		CrowdsourcingAgreement crowdsourcingAgreement = crowdsourcingAgreementService.getBySourceId(id);
		
		List<CrowdsourcingAgreementDetail> crowdsourcingAgreementDetailList= new ArrayList<CrowdsourcingAgreementDetail>();
		
		if(crowdsourcingAgreement == null){	
			crowdsourcingAgreement = new CrowdsourcingAgreement();
			crowdsourcingAgreement.setCode(identityService.doNextId("crowdsourcing_code"));			
			crowdsourcingAgreement.setOperaterId(ContextUtil.getCurrentUser().getUserId());
			crowdsourcingAgreement.setOperaterName(ContextUtil.getCurrentUser().getFullname());
			crowdsourcingAgreement.setOperaterEnterpId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
			crowdsourcingAgreement.setOperaterEnterpName(ContextUtil.getCurrentOrgInfoFromSession().getName());
			crowdsourcingAgreement.setOperateDate(new Date());
			crowdsourcingAgreement.setMaterialId(cr.getMaterialId());
			crowdsourcingAgreement.setMaterialCode(cr.getMaterialCode());
			crowdsourcingAgreement.setMaterialName(cr.getMaterialName());
			
			crowdsourcingAgreement.setSourceformCrowdsourcingId(cr.getId());
			crowdsourcingAgreement.setSourceformCrowdsourcingCode(cr.getCode());
			crowdsourcingAgreement.setCompleteTime(cr.getRequiredCompleteTime());
			crowdsourcingAgreement.setCurrency(cr.getCurrency());
			List<CrowdsourcingRequireDetail> crowdsourcingRequireDetails = crowdsourcingRequireService.getCrowdsourcingRequireDetailList(id);
			
			for(CrowdsourcingRequireDetail crowdsourcingRequireDetail:crowdsourcingRequireDetails){
				crowdsourcingAgreementDetailList.add(new CrowdsourcingAgreementDetail(crowdsourcingRequireDetail));
			}
			
			//报价企业或人员列表
			return getAutoView().addObject("crowdsourcingAgreement",crowdsourcingAgreement)
								.addObject("crowdsourcingAgreementDetailList",crowdsourcingAgreementDetailList)
								.addObject("returnUrl",returnUrl)
								.addObject("crowdsourcingResponseList",crowdsourcingResponseList);
		}else{
			
			crowdsourcingAgreementDetailList = crowdsourcingAgreementService.getCrowdsourcingAgreementDetailList(crowdsourcingAgreement.getId());
			return getAutoView().addObject("crowdsourcingAgreement",crowdsourcingAgreement)
					.addObject("crowdsourcingAgreementDetailList",crowdsourcingAgreementDetailList)
					.addObject("returnUrl",returnUrl)
					.addObject("crowdsourcingResponseList",crowdsourcingResponseList);
			
		}

	}
	
	@RequestMapping("issuedAgree")
	@Action(description="编辑研发众包合同")
	public ModelAndView issuedAgree(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		CrowdsourcingRequire cr=crowsourcingRequireService.getById(id);
		String returnUrl=RequestUtil.getPrePage(request);
	
		//查找是否已经存在		
		CrowdsourcingAgreement crowdsourcingAgreement=crowdsourcingAgreementService.getBySourceId(id);
		List<CrowdsourcingAgreementDetail> crowdsourcingAgreementDetailList= new ArrayList<CrowdsourcingAgreementDetail>();
		if(crowdsourcingAgreement == null){		
			crowdsourcingAgreement= new CrowdsourcingAgreement();
		crowdsourcingAgreement.setCode(identityService.doNextId("crowdsourcing_code"));			
		crowdsourcingAgreement.setOperaterId(ContextUtil.getCurrentUser().getUserId());
		crowdsourcingAgreement.setOperaterName(ContextUtil.getCurrentUser().getFullname());
		crowdsourcingAgreement.setOperaterEnterpId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		crowdsourcingAgreement.setOperaterEnterpName(ContextUtil.getCurrentOrgInfoFromSession().getName());
		crowdsourcingAgreement.setOperateDate(new Date());
		ProcessRun processRun = processRunService.getById(cr.getRunId());
		Long responseId =(Long) runtimeService.getVariable(processRun.getActInstId(),"quoteId");
		CrowdsourcingResponse crowdsourcingResponse = crowdsourcingResponseService.getById(responseId);
		crowdsourcingAgreement.setReceiveOrgId(crowdsourcingResponse.getOperaterEnterpId());
		crowdsourcingAgreement.setReceiveOrgName(crowdsourcingResponse.getOperaterEnterpName());
		crowdsourcingAgreement.setReceiverId(crowdsourcingResponse.getOperaterId());
		crowdsourcingAgreement.setReceiverName(crowdsourcingResponse.getOperaterName());
		crowdsourcingAgreement.setAgreePrice(crowdsourcingResponse.getQuote());
		
		crowdsourcingAgreement.setMaterialId(cr.getMaterialId());
		crowdsourcingAgreement.setMaterialCode(cr.getMaterialCode());
		crowdsourcingAgreement.setMaterialName(cr.getMaterialName());
		
		crowdsourcingAgreement.setSourceformCrowdsourcingId(cr.getId());
		crowdsourcingAgreement.setSourceformCrowdsourcingCode(cr.getCode());
		crowdsourcingAgreement.setCompleteTime(cr.getRequiredCompleteTime());
		crowdsourcingAgreement.setCurrency(cr.getCurrency());
		crowdsourcingAgreement.setCompleteTime(cr.getRequiredCompleteTime());
		
		List<CrowdsourcingRequireDetail> crowdsourcingRequireDetails = crowdsourcingRequireService.getCrowdsourcingRequireDetailList(id);
		

		for(CrowdsourcingRequireDetail crowdsourcingRequireDetail:crowdsourcingRequireDetails){
			crowdsourcingAgreementDetailList.add(new CrowdsourcingAgreementDetail(crowdsourcingRequireDetail));
		}
		

		return getAutoView().addObject("crowdsourcingAgreement",crowdsourcingAgreement)
							.addObject("crowdsourcingAgreementDetailList",crowdsourcingAgreementDetailList)
							.addObject("returnUrl",returnUrl);
		}else{
			crowdsourcingAgreementDetailList = crowdsourcingAgreementService.getCrowdsourcingAgreementDetailList(crowdsourcingAgreement.getId());
			return getAutoView().addObject("crowdsourcingAgreement",crowdsourcingAgreement)
					.addObject("crowdsourcingAgreementDetailList",crowdsourcingAgreementDetailList)
					.addObject("returnUrl",returnUrl);
		}
	}
	
	
	@RequestMapping("confirmAgree")
	@Action(description="编辑研发众包合同")
	public ModelAndView confirmAgree(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		CrowdsourcingRequire cr=crowsourcingRequireService.getById(id);
		
		String returnUrl=RequestUtil.getPrePage(request);
		
		CrowdsourcingAgreement crowdsourcingAgreement = crowdsourcingAgreementService.getBySourceId(id);
		List<CrowdsourcingAgreementDetail> crowdsourcingAgreementDetailList= new ArrayList<CrowdsourcingAgreementDetail>();

		crowdsourcingAgreementDetailList = crowdsourcingAgreementService.getCrowdsourcingAgreementDetailList(crowdsourcingAgreement.getId());
				
		return getAutoView().addObject("crowdsourcingAgreement",crowdsourcingAgreement)
							.addObject("crowdsourcingAgreementDetailList",crowdsourcingAgreementDetailList)
							.addObject("returnUrl",returnUrl);
		
	}
	/**
	 * 取得研发众包合同明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看研发众包合同明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CrowdsourcingAgreement crowdsourcingAgreement = crowdsourcingAgreementService.getById(id);	
		List<CrowdsourcingAgreementDetail> crowdsourcingAgreementDetailList=crowdsourcingAgreementService.getCrowdsourcingAgreementDetailList(id);
		return getAutoView().addObject("crowdsourcingAgreement",crowdsourcingAgreement)
							.addObject("crowdsourcingAgreementDetailList",crowdsourcingAgreementDetailList);
	}
	
	/**
	 * 添加或更新研发众包合同<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("apply")
	@Action(description = "添加或更新研发众包合同")
	public void apply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		CrowdsourcingAgreement crowdsourcingAgreement=getFormObject(request);
		long id=RequestUtil.getLong(request,"id");
		try {
			if (crowdsourcingAgreement != null) {
				if (crowdsourcingAgreement.getId() == null || crowdsourcingAgreement.getId() == 0) {
					crowdsourcingAgreement.setId(UniqueIdUtil.genId());
					crowdsourcingAgreementService.add(crowdsourcingAgreement);
					resultMsg = getText("record.added", "研发众包合同");
				} else {
					crowdsourcingAgreementService.update(crowdsourcingAgreement);
					resultMsg = getText("record.updated", "研发众包合同");
				}
				id =crowdsourcingAgreement.getId();
			} 

			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setFlowKey(defKey);
			processCmd.setBusinessKey(String.valueOf(id));
			processCmd.setUserAccount(ContextUtil.getCurrentUser().getFullname());
			processService.start(processCmd);

			writeResultMessage(response.getWriter(), "研发众包合同成功", ResultMessage.Success);
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
	

	 @RequestMapping("selectedProvider")
	 @ResponseBody
	 public Map<String,Object> selectedProvider(HttpServletRequest request,HttpServletResponse response) throws Exception{

	    	Long quoteId=RequestUtil.getLong(request, "quoteId");    	
	    	CrowdsourcingResponse quote = crowdsourcingResponseService.getById(quoteId);
	    	
	    	Long requireID = quote.getSourceformCrowdsourcingId();   	 
			CrowdsourcingRequire crowdsourcingRequire = crowdsourcingRequireService.getById(requireID);

	       	//从需求单中得到当前流程实例 
	    	ProcessRun processRun = processRunService.getById(crowdsourcingRequire.getRunId());   	
	    	//将得到的供应商响应单ID存储为流程变量
	    	runtimeService.setVariable(processRun.getActInstId(), "quoteId", quoteId);
	    	
	        Map<String,Object> map = new HashMap<String,Object>();
	        map.put("receiveOrgId",quote.getOperaterEnterpId());
	        map.put("receiveOrgName",quote.getOperaterEnterpName());
	        map.put("receiverId",quote.getOperaterId());
	        map.put("receiverName",quote.getOperaterName());
	        map.put("agreePrice",quote.getQuote());
	    	//return "{success:true}";
	        return map;
	    }
   
}
