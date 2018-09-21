<#assign package=table.variable.package>
<#assign class=table.variable.class>
<#assign classVar=table.variable.classVar>
<#assign package=table.variable.package>
<#assign comment=table.tableDesc>
<#assign fieldList=table.fieldList>
<#assign subTableList=table.subTableList>
<#assign hasScript=0>
<#assign hasIdentity=0>
<#list fieldList as field>
<#if field.valueFrom==1|| field.valueFrom==2>
	<#assign hasScript=1>
<#elseif field.valueFrom==3>
	<#assign hasIdentity=1>
</#if>
</#list>

package com.hotent.${system}.controller.${package};

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
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.${system}.model.${package}.${class};
import com.hotent.${system}.service.${package}.${class}Service;
<#if subTableList?size != 0>
	<#list subTableList as subtable>
import com.hotent.${system}.model.${subtable.variable.package}.${subtable.variable.class};
	</#list>
</#if>
import com.hotent.core.web.ResultMessage;
<#if hasScript==1>
import com.hotent.core.engine.GroovyScriptEngine;
</#if>
<#if hasIdentity==1>
import com.hotent.platform.service.system.IdentityService;
</#if>
<#if defKey?exists>
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.webservice.impl.ProcessServiceImpl;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import com.hotent.platform.service.bpm.ProcessRunService;
</#if>
/**
 * 对象功能:${comment} 控制器类
 */
@Controller
@RequestMapping("/${system}/${package}/${classVar}/")
public class ${class}Controller extends BaseController
{
	@Resource
	private ${class}Service ${classVar}Service;
	<#if hasScript==1>
	@Resource
	private GroovyScriptEngine engine;
	</#if>
	<#if hasIdentity==1>
	@Resource
	private IdentityService identityService;
	</#if>
	<#if defKey?exists>
	@Resource
	private ProcessServiceImpl processService;
	@Resource
	private BpmService bpmService;
	@Resource
	private ProcessRunService processRunService;
	</#if>
	/**
	 * 添加或更新${comment}。
	 * @param request
	 * @param response
	 * @param ${classVar} 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新${comment}")
	public void save(HttpServletRequest request, HttpServletResponse response,${class} ${classVar}) throws Exception
	{
		String resultMsg=null;		
		try{
			if(${classVar}.getId()==null){
				Long id=UniqueIdUtil.genId();
				${classVar}.setId(id);
				<#if subTableList?size != 0>
				${classVar}Service.addAll(${classVar});			
				<#else>
				${classVar}Service.add(${classVar});
				</#if>
				resultMsg=getText("record.added","${comment}");
			}else{
			    <#if subTableList?size != 0>
			    ${classVar}Service.updateAll(${classVar});
			    <#else>
			    ${classVar}Service.update(${classVar});
			    </#if>
				resultMsg=getText("record.updated","${comment}");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得${comment}分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看${comment}分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<${class}> list=${classVar}Service.getAll(new QueryFilter(request,"${classVar}Item"));
		ModelAndView mv=this.getAutoView().addObject("${classVar}List",list);
		
		return mv;
	}
	
	/**
	 * 删除${comment}
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除${comment}")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			<#if subTableList?size != 0>
			${classVar}Service.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除${comment}及其从表成功!");
			<#else>
			</#if>
			${classVar}Service.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除${comment}成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑${comment}
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑${comment}")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		<#if defKey?exists>
		String taskId=RequestUtil.getString(request, "taskId");
		TaskEntity taskInfo=null;
		boolean isAllowBack=false;
		boolean isSignTask=false;
		if(StringUtil.isNotEmpty(taskId)){
			taskInfo=bpmService.getTask(taskId);
			isAllowBack=processService.getIsAllowBask(taskId);
			isSignTask=bpmService.isSignTask(taskInfo);
		}
		</#if>
		String returnUrl=RequestUtil.getPrePage(request);
		${class} ${classVar}=${classVar}Service.getById(id);
		<#if hasScript==1||hasIdentity==1>
		if(BeanUtils.isEmpty(${classVar})){
			${classVar}=new ${class}();
		<#list fieldList as field>
		<#if field.valueFrom!=0>
		<#if (field.valueFrom==1||field.valueFrom==2)>
			String ${field.fieldName}_script="${field.script?trim}";
			${classVar}.set${field.fieldName?cap_first}(engine.executeObject(${field.fieldName}_script, null).toString());
		<#elseif (field.valueFrom==3)>
			String ${field.fieldName}_id=identityService.nextId("${field.identity}");
			${classVar}.set${field.fieldName?cap_first}(${field.fieldName}_id);
		</#if>
		</#if>
		<#if field.fieldType=="date">
			<#if field.isCurrentDateStr==1>
			${classVar}.set${field.fieldName?cap_first}(new Date());
			</#if>
		</#if>
		</#list>
		}
		</#if>
		<#if subTableList?size != 0>
		    <#list subTableList as subtable>
		    <#assign vars=subtable.variable>
		List<${subtable.variable.class}> ${subtable.variable.classVar}List=${classVar}Service.get${vars.classVar?cap_first}List(id);
		    </#list>
		</#if>
		
		return getAutoView().addObject("${classVar}Item",${classVar})
		<#if subTableList?size != 0>
		    <#list subTableList as subtable>
							.addObject("${subtable.variable.classVar}List",${subtable.variable.classVar}List)
		    </#list>
		</#if>
		<#if defKey?exists>
							.addObject("taskId", taskId)
							.addObject("taskInfo", taskInfo)
							.addObject("isAllowBack", isAllowBack)
							.addObject("isSignTask", isSignTask)
		</#if>
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得${comment}明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看${comment}明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		${class} ${classVar} = ${classVar}Service.getById(id);	
		<#if subTableList?size != 0>
		    <#list subTableList as subtable>
		    <#assign vars=subtable.variable>
		List<${vars.class}> ${vars.classVar}List=${classVar}Service.get${vars.classVar?cap_first}List(id);
		    </#list>
		return getAutoView().addObject("${classVar}",${classVar})
		    <#list subTableList as subtable>
							.addObject("${subtable.variable.classVar}List",${subtable.variable.classVar}List)<#if !subtable_has_next>;</#if>
		    </#list>
		<#else>
		return getAutoView().addObject("${classVar}", ${classVar});
		</#if>	
	}
	
<#if defKey?exists>
	/**
	 * 启动流程
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("run")
	@Action(description="启动流程")
	public void run(HttpServletRequest request, HttpServletResponse response,${class} ${classVar}) throws Exception
	{
		long id=RequestUtil.getLong(request,"id",0L);
		ProcessCmd processCmd=new ProcessCmd();
		processCmd.setFlowKey("${defKey}");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				${classVar}=${classVar}Service.getById(id);
				processCmd.setBusinessKey(Long.toString(id));
				ProcessRun processRun=processService.start(processCmd);
				${classVar}.setFlowRunId(processRun.getRunId());
				${classVar}Service.update(${classVar});
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				${classVar}.setId(genId);
				ProcessRun processRun=processService.start(processCmd);
				${classVar}.setFlowRunId(processRun.getRunId());
				${classVar}Service.add(${classVar});
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, "启动流程失败"));
		}
	}
	
	/**
	 * 办理任务
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("doNext")
	@Action(description="办理任务")
	public void doNext(HttpServletRequest request, HttpServletResponse response,${class} ${classVar}) throws Exception
	{
		String taskId=RequestUtil.getString(request, "taskId");
		int voteAgree=RequestUtil.getInt(request, "voteAgree");
		ProcessRun processRun=processRunService.getById(${classVar}.getFlowRunId());
		ProcessCmd processCmd=new ProcessCmd();
		if(voteAgree==3){
			processCmd.setBack(1);
		}else if(voteAgree==4){
			processCmd.setBack(2);
		}else{
			processCmd.setVoteAgree((short)voteAgree);
		}
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		processCmd.setTaskId(taskId);
		try {
			processService.doNext(processCmd);
			${classVar}Service.update(${classVar});
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "任务办理成功"));
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, "任务办理失败"));
		}
	}
</#if>
}