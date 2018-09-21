package com.casic.cloud.controller.crowdsourcing.crowdsourcingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreement;
import com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreementDetail;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResult.CrowdsourcingResult;
import com.casic.cloud.service.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreementService;
import com.casic.cloud.service.crowdsourcing.crowdsourcingResult.CrowdsourcingResultService;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResult.CrowdsourcingResultDetail;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.webservice.api.ProcessService;
/**
 *<pre>
 * 对象功能:研发结果记录 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:15:19
 *</pre>
 */
@Controller
@RequestMapping("/cloud/crowdsourcing/crowdsourcingResult/")
public class CrowdsourcingResultController extends BaseController
{
	@Resource
	private CrowdsourcingResultService crowdsourcingResultService;
	@Resource
	private CrowdsourcingAgreementService crowdsourcingAgreementService;
	
	@Resource
	private IdentityService identityService;
	
	@Resource
	private ProcessService processService;
	private final String defKey = "crowdresearch";	//绑定流程定义
	
	/**
	 * 添加或更新研发结果记录。
	 * @param request
	 * @param response
	 * @param crowdsourcingResult 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新研发结果记录")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CrowdsourcingResult crowdsourcingResult=getFormObject(request);
		try{
			if(crowdsourcingResult.getId()==null||crowdsourcingResult.getId()==0){
				crowdsourcingResult.setId(UniqueIdUtil.genId());
				crowdsourcingResultService.addAll(crowdsourcingResult);			
				resultMsg=getText("record.added","研发结果记录");
			}else{
			    crowdsourcingResultService.updateAll(crowdsourcingResult);
				resultMsg=getText("record.updated","研发结果记录");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CrowdsourcingResult 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CrowdsourcingResult getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("crowdsourcingResultDetailList", CrowdsourcingResultDetail.class);
		CrowdsourcingResult crowdsourcingResult = (CrowdsourcingResult)JSONObject.toBean(obj, CrowdsourcingResult.class,map);
		
		return crowdsourcingResult;
    }
	
	/**
	 * 取得研发结果记录分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看研发结果记录分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CrowdsourcingResult> list=crowdsourcingResultService.getAll(new QueryFilter(request,"crowdsourcingResultItem"));
		ModelAndView mv=this.getAutoView().addObject("crowdsourcingResultList",list);
		
		return mv;
	}
	
	/**
	 * 删除研发结果记录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除研发结果记录")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			crowdsourcingResultService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除研发结果记录及其从表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑研发结果记录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑研发结果记录")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"resultId");
		String returnUrl=RequestUtil.getPrePage(request);
		CrowdsourcingResult crowdsourcingResult=crowdsourcingResultService.getById(id);
		List<CrowdsourcingResultDetail> crowdsourcingResultDetailList=crowdsourcingResultService.getCrowdsourcingResultDetailList(id);
		
		return getAutoView().addObject("crowdsourcingResult",crowdsourcingResult)
							.addObject("crowdsourcingResultDetailList",crowdsourcingResultDetailList)
							.addObject("returnUrl",returnUrl);
	}
	
	
	
	@RequestMapping("create")
	@Action(description="编辑研发结果记录")
	public ModelAndView create(HttpServletRequest request) throws Exception
	{
		Long id;
		if(RequestUtil.getString(request,"resultId")==null||RequestUtil.getString(request,"resultId").equals("")||RequestUtil.getString(request,"resultId").equals("{resultId}")){
			id=0L;
		}else{
			id=RequestUtil.getLong(request,"resultId");
		}
		String returnUrl=RequestUtil.getPrePage(request);
		CrowdsourcingResult crowdsourcingResult=crowdsourcingResultService.getById(id);
		List<CrowdsourcingResultDetail> crowdsourcingResultDetailList=crowdsourcingResultService.getCrowdsourcingResultDetailList(id);

		if(crowdsourcingResult==null){
			crowdsourcingResult=new CrowdsourcingResult();
			String orderNo=identityService.doNextId("EnquiryNo");
			crowdsourcingResult.setCode(orderNo);			
			crowdsourcingResult.setOperaterId(ContextUtil.getCurrentUser().getUserId());
			crowdsourcingResult.setOperaterName(ContextUtil.getCurrentUser().getFullname());
			
			crowdsourcingResult.setOperaterEnterpId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
			crowdsourcingResult.setOperaterEnterpName(ContextUtil.getCurrentOrgInfoFromSession().getName());
		}
		
		Long requireid = RequestUtil.getLong(request,"id");
		CrowdsourcingAgreement crowdsourcingAgreement ;
		List<CrowdsourcingAgreementDetail>  crowdsourcingAgreementDetailList= new ArrayList<CrowdsourcingAgreementDetail>();
		if(requireid==0){
			crowdsourcingAgreement = null ;
		}
		else{
			crowdsourcingAgreement = crowdsourcingAgreementService.getBySourceId(requireid);
			crowdsourcingAgreementDetailList = crowdsourcingAgreementService.getCrowdsourcingAgreementDetailList(crowdsourcingAgreement.getId());
		}

		return getAutoView().addObject("crowdsourcingResult",crowdsourcingResult)
							.addObject("crowdsourcingResultDetailList",crowdsourcingResultDetailList)
							.addObject("crowdsourcingAgreementDetailList",crowdsourcingAgreementDetailList)
							.addObject("returnUrl",returnUrl);
	}
	

	@RequestMapping("confirm")
	@Action(description="编辑研发结果记录")
	public ModelAndView confirm(HttpServletRequest request) throws Exception
	{
		Long id;
		if(RequestUtil.getString(request,"resultId")==null||RequestUtil.getString(request,"resultId").equals("")||RequestUtil.getString(request,"resultId").equals("{resultId}")){
			id=ContextUtil.getCurrentUser().getUserId();
		}else{
			id=RequestUtil.getLong(request,"resultId");
		}
		String returnUrl=RequestUtil.getPrePage(request);
		CrowdsourcingResult crowdsourcingResult=crowdsourcingResultService.getById(id);
		List<CrowdsourcingResultDetail> crowdsourcingResultDetailList=crowdsourcingResultService.getCrowdsourcingResultDetailList(id);

		if(crowdsourcingResult==null){
			crowdsourcingResult=new CrowdsourcingResult();
			String orderNo=identityService.doNextId("EnquiryNo");
			crowdsourcingResult.setCode(orderNo);			
			crowdsourcingResult.setOperaterId(ContextUtil.getCurrentUser().getUserId());
			crowdsourcingResult.setOperaterName(ContextUtil.getCurrentUser().getFullname());
			
			crowdsourcingResult.setOperaterEnterpId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
			crowdsourcingResult.setOperaterEnterpName(ContextUtil.getCurrentOrgInfoFromSession().getName());
		}
		return getAutoView().addObject("crowdsourcingResult",crowdsourcingResult)
							.addObject("crowdsourcingResultDetailList",crowdsourcingResultDetailList)
							.addObject("returnUrl",returnUrl);
	}
	/**
	 * 取得研发结果记录明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看研发结果记录明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CrowdsourcingResult crowdsourcingResult = crowdsourcingResultService.getById(id);	
		List<CrowdsourcingResultDetail> crowdsourcingResultDetailList=crowdsourcingResultService.getCrowdsourcingResultDetailList(id);
		return getAutoView().addObject("crowdsourcingResult",crowdsourcingResult)
							.addObject("crowdsourcingResultDetailList",crowdsourcingResultDetailList);
	}
	
	/**
	 * 添加或更新研发结果记录<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("apply")
	@Action(description = "添加或更新研发结果记录")
	public void apply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		CrowdsourcingResult crowdsourcingResult=getFormObject(request);
		long id=RequestUtil.getLong(request,"id");
		try {
			if (crowdsourcingResult != null) {
				if (crowdsourcingResult.getId() == null || crowdsourcingResult.getId() == 0) {
					crowdsourcingResult.setId(UniqueIdUtil.genId());
					crowdsourcingResultService.add(crowdsourcingResult);
					resultMsg = getText("record.added", "研发结果记录");
				} else {
					crowdsourcingResultService.update(crowdsourcingResult);
					resultMsg = getText("record.updated", "研发结果记录");
				}
				id =crowdsourcingResult.getId();
			} 

			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setFlowKey(defKey);
			processCmd.setBusinessKey(String.valueOf(id));
			processCmd.setUserAccount(ContextUtil.getCurrentUser().getFullname());
			processService.start(processCmd);

			writeResultMessage(response.getWriter(), "研发结果记录成功", ResultMessage.Success);
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
	 * 开始预发布流程<br>
	 * 
	 */
	@RequestMapping("start_prepublish")
	public ModelAndView start_prepublish(HttpServletRequest request) throws Exception
	{
		Long id=0L;
		String returnUrl=RequestUtil.getPrePage(request);
		CrowdsourcingResult crowdsourcingResult=crowdsourcingResultService.getById(id);
		List<CrowdsourcingResultDetail> crowdsourcingResultDetailList=crowdsourcingResultService.getCrowdsourcingResultDetailList(id);
		
		if(crowdsourcingResult==null){
			crowdsourcingResult=new CrowdsourcingResult();
			String orderNo=identityService.doNextId("EnquiryNo");
			crowdsourcingResult.setCode(orderNo);			
			crowdsourcingResult.setOperaterId(ContextUtil.getCurrentUser().getUserId());
			crowdsourcingResult.setOperaterName(ContextUtil.getCurrentUser().getFullname());
			
			crowdsourcingResult.setOperaterEnterpId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
			crowdsourcingResult.setOperaterEnterpName(ContextUtil.getCurrentOrgInfoFromSession().getName());
		}
		
		return getAutoView().addObject("crowdsourcingResult",crowdsourcingResult)
				.addObject("crowdsourcingResultDetailList",crowdsourcingResultDetailList)
				.addObject("returnUrl",returnUrl);
	}
	/**
	 * 保存预发布流程<br>
	 * 
	 */
	@RequestMapping("save_prepublish")
	public void save_prepublish(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CrowdsourcingResult crowdsourcingResult=getFormObject(request);
		try{
			if(crowdsourcingResult.getId()==null||crowdsourcingResult.getId()==0){
				crowdsourcingResult.setId(UniqueIdUtil.genId());
				crowdsourcingResultService.startProcess(crowdsourcingResult);			
				resultMsg=getText("record.added","研发结果记录");
			}else{
			    crowdsourcingResultService.updateAll(crowdsourcingResult);
				resultMsg=getText("record.updated","研发结果记录");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
}
