package com.casic.cloud.controller.crowdsourcing.crowdsourcingRequire;

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
import com.casic.cloud.service.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireService;
import com.casic.cloud.model.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireDetail;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.webservice.api.ProcessService;
/**
 *<pre>
 * 对象功能:研发众包需求 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:06:02
 *</pre>
 */
@Controller
@RequestMapping("/cloud/crowdsourcing/crowdsourcingRequire/")
public class CrowdsourcingRequireController extends BaseController
{
	@Resource
	private CrowdsourcingRequireService crowdsourcingRequireService;
	
	@Resource
	private ProcessService processService;
	private final String defKey = "crowdresearch";	//绑定流程定义
	@Resource
	private IdentityService identityService;
	/**
	 * 添加或更新研发众包需求。
	 * @param request
	 * @param response
	 * @param crowdsourcingRequire 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新研发众包需求")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CrowdsourcingRequire crowdsourcingRequire=getFormObject(request);
		try{
			if(crowdsourcingRequire.getId()==null||crowdsourcingRequire.getId()==0){
				crowdsourcingRequire.setId(UniqueIdUtil.genId());
				crowdsourcingRequireService.addAll(crowdsourcingRequire);			
				resultMsg=getText("record.added","研发众包需求");
			}else{
			    crowdsourcingRequireService.updateAll(crowdsourcingRequire);
				resultMsg=getText("record.updated","研发众包需求");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CrowdsourcingRequire 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CrowdsourcingRequire getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("crowdsourcingRequireDetailList", CrowdsourcingRequireDetail.class);
		CrowdsourcingRequire crowdsourcingRequire = (CrowdsourcingRequire)JSONObject.toBean(obj, CrowdsourcingRequire.class,map);
		
		return crowdsourcingRequire;
    }
	
	/**
	 * 取得研发众包需求分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看研发众包需求分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CrowdsourcingRequire> list=crowdsourcingRequireService.getAll(new QueryFilter(request,"crowdsourcingRequireItem"));
		ModelAndView mv=this.getAutoView().addObject("crowdsourcingRequireList",list);
		
		return mv;
	}
	
	/**
	 * 删除研发众包需求
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除研发众包需求")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			crowdsourcingRequireService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除研发众包需求及其从表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑研发众包需求
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑研发众包需求")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CrowdsourcingRequire crowdsourcingRequire=crowdsourcingRequireService.getById(id);
		List<CrowdsourcingRequireDetail> crowdsourcingRequireDetailList=crowdsourcingRequireService.getCrowdsourcingRequireDetailList(id);
		
		return getAutoView().addObject("crowdsourcingRequire",crowdsourcingRequire)
							.addObject("crowdsourcingRequireDetailList",crowdsourcingRequireDetailList)
							.addObject("returnUrl",returnUrl);
	}
	@RequestMapping("create")
	@Action(description="编辑研发众包需求")
	public ModelAndView create(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CrowdsourcingRequire crowdsourcingRequire=crowdsourcingRequireService.getById(id);
		if(crowdsourcingRequire==null){
			crowdsourcingRequire = new CrowdsourcingRequire();
			crowdsourcingRequire.setCode(identityService.doNextId("crowdsourcing_code"));
			crowdsourcingRequire.setOperateDate(new Date());
			crowdsourcingRequire.setOperaterId(ContextUtil.getCurrentUser().getUserId());
			crowdsourcingRequire.setOperaterName(ContextUtil.getCurrentUser().getFullname());
			crowdsourcingRequire.setEnterpriseId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
			crowdsourcingRequire.setEnterpriseName(ContextUtil.getCurrentOrgInfoFromSession().getName());
			crowdsourcingRequire.setResourcePath("http://60.195.252.27:8080/cloud/hpc/application_th.xml?_service=fluent_normal&_username=jhadmin&_password=jhadmin");
			crowdsourcingRequire.setStatus("1-创建需求表");
		}
		List<CrowdsourcingRequireDetail> crowdsourcingRequireDetailList=crowdsourcingRequireService.getCrowdsourcingRequireDetailList(id);
		
		return getAutoView().addObject("crowdsourcingRequire",crowdsourcingRequire)
							.addObject("crowdsourcingRequireDetailList",crowdsourcingRequireDetailList)
							.addObject("returnUrl",returnUrl)
							.addObject("applyFlag",2);
	}
	/**
	 * 取得研发众包需求明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看研发众包需求明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CrowdsourcingRequire crowdsourcingRequire = crowdsourcingRequireService.getById(id);	
		List<CrowdsourcingRequireDetail> crowdsourcingRequireDetailList=crowdsourcingRequireService.getCrowdsourcingRequireDetailList(id);
		return getAutoView().addObject("crowdsourcingRequire",crowdsourcingRequire)
							.addObject("crowdsourcingRequireDetailList",crowdsourcingRequireDetailList);
	}
	
	/**
	 * 添加或更新研发众包需求<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("apply")
	@Action(description = "添加或更新研发众包需求")
	public void apply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		CrowdsourcingRequire crowdsourcingRequire=getFormObject(request);
		long id=RequestUtil.getLong(request,"id");
		try {
			if (crowdsourcingRequire != null) {
				if (crowdsourcingRequire.getId() == null || crowdsourcingRequire.getId() == 0) {
					crowdsourcingRequire.setId(UniqueIdUtil.genId());
					crowdsourcingRequireService.add(crowdsourcingRequire);
					resultMsg = getText("record.added", "研发众包需求");
				} else {
					crowdsourcingRequireService.update(crowdsourcingRequire);
					resultMsg = getText("record.updated", "研发众包需求");
				}
				id =crowdsourcingRequire.getId();
			} 

			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setFlowKey(defKey);
			processCmd.setBusinessKey(String.valueOf(id));
			processCmd.setUserAccount(ContextUtil.getCurrentUser().getFullname());
			processService.start(processCmd);

			writeResultMessage(response.getWriter(), "研发众包需求成功", ResultMessage.Success);
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
