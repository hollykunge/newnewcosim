package com.casic.cloud.controller.crowdsourcing.crowdsourcingResponse;

import java.util.ArrayList;
import java.util.Date;
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

import com.casic.cloud.model.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequire;
import com.casic.cloud.model.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireDetail;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponse;
import com.casic.cloud.service.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireService;
import com.casic.cloud.service.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponseService;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponseDetail;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.webservice.api.ProcessService;
/**
 *<pre>
 * 对象功能:需求响应表 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:13:50
 *</pre>
 */
@Controller
@RequestMapping("/cloud/crowdsourcing/crowdsourcingResponse/")
public class CrowdsourcingResponseController extends BaseController
{
	@Resource
	private CrowdsourcingResponseService crowdsourcingResponseService;
	@Resource
	private CrowdsourcingRequireService crowdsourcingRequireService;
	@Resource
	private ProcessService processService;
	@Resource
	private IdentityService identityService;
	private final String defKey = "crowdresearch";	//绑定流程定义
	
	/**
	 * 添加或更新需求响应表。
	 * @param request
	 * @param response
	 * @param crowdsourcingResponse 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新需求响应表")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CrowdsourcingResponse crowdsourcingResponse=getFormObject(request);
		try{
			if(crowdsourcingResponse.getId()==null||crowdsourcingResponse.getId()==0){
				crowdsourcingResponse.setId(UniqueIdUtil.genId());
				crowdsourcingResponseService.addAll(crowdsourcingResponse);			
				resultMsg=getText("record.added","需求响应表");
			}else{
			    crowdsourcingResponseService.updateAll(crowdsourcingResponse);
				resultMsg=getText("record.updated","需求响应表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CrowdsourcingResponse 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CrowdsourcingResponse getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("crowdsourcingResponseDetailList", CrowdsourcingResponseDetail.class);
		CrowdsourcingResponse crowdsourcingResponse = (CrowdsourcingResponse)JSONObject.toBean(obj, CrowdsourcingResponse.class,map);
		
		return crowdsourcingResponse;
    }
	
	/**
	 * 取得需求响应表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看需求响应表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CrowdsourcingResponse> list=crowdsourcingResponseService.getAll(new QueryFilter(request,"crowdsourcingResponseItem"));
		ModelAndView mv=this.getAutoView().addObject("crowdsourcingResponseList",list);
		
		return mv;
	}
	
	/**
	 * 删除需求响应表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除需求响应表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			crowdsourcingResponseService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除需求响应表及其从表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑需求响应表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑需求响应表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CrowdsourcingResponse crowdsourcingResponse=crowdsourcingResponseService.getById(id);
		List<CrowdsourcingResponseDetail> crowdsourcingResponseDetailList=crowdsourcingResponseService.getCrowdsourcingResponseDetailList(id);
		
		return getAutoView().addObject("crowdsourcingResponse",crowdsourcingResponse)
							.addObject("crowdsourcingResponseDetailList",crowdsourcingResponseDetailList)
							.addObject("returnUrl",returnUrl);
	}
	@RequestMapping("create")
	@Action(description="编辑需求响应表")
	public ModelAndView create(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);		
		CrowdsourcingRequire crowdsourcingRequire = crowdsourcingRequireService.getById(id);
		List<CrowdsourcingRequireDetail> crowdsourcingRequireDetails = crowdsourcingRequireService.getCrowdsourcingRequireDetailList(id);
		
		CrowdsourcingResponse crowdsourcingResponse=new CrowdsourcingResponse();
		crowdsourcingResponse.setCode(identityService.doNextId("crowdsourcing_code"));
		crowdsourcingResponse.setOperateDate(new Date());
		crowdsourcingResponse.setOperaterId(ContextUtil.getCurrentUser().getUserId());
		crowdsourcingResponse.setOperaterName(ContextUtil.getCurrentUser().getFullname());
		crowdsourcingResponse.setOperaterEnterpId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		crowdsourcingResponse.setOperaterEnterpName(ContextUtil.getCurrentOrgInfoFromSession().getName());
		crowdsourcingResponse.setSourceformCrowdsourcingId(crowdsourcingRequire.getId());
		crowdsourcingResponse.setSourceformCrowdsourcingCode(crowdsourcingRequire.getCode());
		crowdsourcingResponse.setMaterialName(crowdsourcingRequire.getMaterialName());
		crowdsourcingResponse.setMaterialCode(crowdsourcingRequire.getMaterialCode());
		crowdsourcingResponse.setMaterialBomLevel(crowdsourcingRequire.getMaterialBomLevel());
		crowdsourcingResponse.setMaterialId(crowdsourcingRequire.getMaterialId());
		
		List<CrowdsourcingResponseDetail> crowdsourcingResponseDetailList= new ArrayList<CrowdsourcingResponseDetail>();
		
		for(CrowdsourcingRequireDetail crowdsourcingRequireDetail:crowdsourcingRequireDetails){
			crowdsourcingResponseDetailList.add(new CrowdsourcingResponseDetail(crowdsourcingRequireDetail));
		}
		
		return getAutoView().addObject("crowdsourcingResponse",crowdsourcingResponse)
							.addObject("crowdsourcingResponseDetailList",crowdsourcingResponseDetailList)
							.addObject("returnUrl",returnUrl)
							.addObject("applyFlag",2);
	}
	/**
	 * 取得需求响应表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看需求响应表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CrowdsourcingResponse crowdsourcingResponse = crowdsourcingResponseService.getById(id);	
		List<CrowdsourcingResponseDetail> crowdsourcingResponseDetailList=crowdsourcingResponseService.getCrowdsourcingResponseDetailList(id);
		return getAutoView().addObject("crowdsourcingResponse",crowdsourcingResponse)
							.addObject("crowdsourcingResponseDetailList",crowdsourcingResponseDetailList);
	}
	
	/**
	 * 添加或更新需求响应表<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("apply")
	@Action(description = "添加或更新需求响应表")
	public void apply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		CrowdsourcingResponse crowdsourcingResponse=getFormObject(request);
		long id=RequestUtil.getLong(request,"id");
		try {
			if (crowdsourcingResponse != null) {
				if (crowdsourcingResponse.getId() == null || crowdsourcingResponse.getId() == 0) {
					crowdsourcingResponse.setId(UniqueIdUtil.genId());
					crowdsourcingResponseService.add(crowdsourcingResponse);
					resultMsg = getText("record.added", "需求响应表");
				} else {
					crowdsourcingResponseService.update(crowdsourcingResponse);
					resultMsg = getText("record.updated", "需求响应表");
				}
				id =crowdsourcingResponse.getId();
			} 

			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setFlowKey(defKey);
			processCmd.setBusinessKey(String.valueOf(id));
			processCmd.setUserAccount(ContextUtil.getCurrentUser().getFullname());
			processService.start(processCmd);

			writeResultMessage(response.getWriter(), "需求响应表成功", ResultMessage.Success);
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
}
