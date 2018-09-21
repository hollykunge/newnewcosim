package com.casic.cloud.controller.research.fileSign;

import groovy.io.FileVisitResult;

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

import com.hotent.core.util.DateUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.casic.cloud.model.research.fileSign.FileSign;
import com.casic.cloud.service.research.fileSign.FileSignService;
import com.casic.cloud.service.research.fileVerify.FileVerifyService;
import com.casic.cloud.model.research.fileSign.FileSignInfo;
import com.casic.cloud.model.research.fileVerify.FileVerify;
import com.casic.cloud.model.research.fileVerify.FileVerifyUpload;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.webservice.api.ProcessService;
/**
 *<pre>
 * 对象功能:CLOUD_RESEARCH_FILESIGN 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-18 14:33:03
 *</pre>
 */
@Controller
@RequestMapping("/cloud/research/fileSign/")
public class FileSignController extends BaseController
{
	@Resource
	private FileSignService fileSignService;
	
	@Resource
	private FileVerifyService fileVerifyService;
	
	@Resource
	private ProcessService processService;
	
	@Resource
	private IdentityService identityService;
	
	private final String defKey = "fileVerify1";	//绑定流程定义
	
	/**
	 * 添加或更新CLOUD_RESEARCH_FILESIGN。
	 * @param request
	 * @param response
	 * @param fileSign 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		FileSign fileSign=getFormObject(request);
		try{
			if(fileSign.getId()==null||fileSign.getId()==0){
				fileSign.setId(UniqueIdUtil.genId());
				fileSignService.addAll(fileSign);			
				resultMsg=getText("record.added","CLOUD_RESEARCH_FILESIGN");
			}else{
			    fileSignService.updateAll(fileSign);
				resultMsg=getText("record.updated","CLOUD_RESEARCH_FILESIGN");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 FileSign 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected FileSign getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("fileSignInfoList", FileSignInfo.class);
		FileSign fileSign = (FileSign)JSONObject.toBean(obj, FileSign.class,map);
		
		return fileSign;
    }
	
	/**
	 * 取得CLOUD_RESEARCH_FILESIGN分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<FileSign> list=fileSignService.getAll(new QueryFilter(request,"fileSignItem"));
		ModelAndView mv=this.getAutoView().addObject("fileSignList",list);
		
		return mv;
	}
	
	/**
	 * 删除CLOUD_RESEARCH_FILESIGN
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
			fileSignService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除CLOUD_RESEARCH_FILESIGN及其从表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑CLOUD_RESEARCH_FILESIGN
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		FileSign fileSign=fileSignService.getById(id);
		List<FileSignInfo> fileSignInfoList=fileSignService.getFileSignInfoList(id);
		
		return getAutoView().addObject("fileSign",fileSign)
							.addObject("fileSignInfoList",fileSignInfoList)
							.addObject("returnUrl",returnUrl);
	}
	/**
	 * 	审批文件，参数由文件审批申请自动带入
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("create")
	public ModelAndView create(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		FileVerify fileVerify = fileVerifyService.getById(id);
		FileSign fileSign = new FileSign();
		String orderNo=identityService.doNextId("file_verify");
		fileSign.setCode(orderNo);	
		if(StringUtil.isNotEmpty(fileVerify.getApplicantName())){
			fileSign.setApplicantId(fileVerify.getApplicantId());
			fileSign.setApplicantName(fileVerify.getApplicantName());
		}
		if(StringUtil.isNotEmpty(fileVerify.getReason())){
			fileSign.setReason(fileVerify.getReason());
		}
		if(fileVerify.getTime()!=null){
			fileSign.setCreatetime(fileVerify.getTime());
		}
		fileSign.setVerifyTime(new Date());
		fileSign.setVerifierId(ContextUtil.getCurrentUser().getUserId());
		fileSign.setVerifier(ContextUtil.getCurrentUser().getUsername());
		
		String returnUrl=RequestUtil.getPrePage(request);
		List<FileSignInfo> fileSignInfoList= new ArrayList<FileSignInfo>();
		List<FileVerifyUpload> fileUploadList = fileVerifyService.getFileVerifyUploadList(id);
		
				for (FileVerifyUpload f : fileUploadList) {
					fileSignInfoList.add(new FileSignInfo(f));
				}
				
		
		return getAutoView().addObject("fileSign",fileSign)
				.addObject("fileSignInfoList",fileSignInfoList)
				.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得CLOUD_RESEARCH_FILESIGN明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		FileSign fileSign = fileSignService.getById(id);	
		List<FileSignInfo> fileSignInfoList=fileSignService.getFileSignInfoList(id);
		return getAutoView().addObject("fileSign",fileSign)
							.addObject("fileSignInfoList",fileSignInfoList);
	}
	
	/**
	 * 添加或更新CLOUD_RESEARCH_FILESIGN<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("apply")
	@Action(description = "添加或更新CLOUD_RESEARCH_FILESIGN")
	public void apply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		FileSign fileSign=getFormObject(request);
		long id=RequestUtil.getLong(request,"id");
		try {
			if (fileSign != null) {
				if (fileSign.getId() == null || fileSign.getId() == 0) {
					fileSign.setId(UniqueIdUtil.genId());
					fileSignService.add(fileSign);
					resultMsg = getText("record.added", "CLOUD_RESEARCH_FILESIGN");
				} else {
					fileSignService.update(fileSign);
					resultMsg = getText("record.updated", "CLOUD_RESEARCH_FILESIGN");
				}
				id =fileSign.getId();
			} 

			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setFlowKey(defKey);
			processCmd.setBusinessKey(String.valueOf(id));
			processCmd.setUserAccount(ContextUtil.getCurrentUser().getFullname());
			processService.start(processCmd);

			writeResultMessage(response.getWriter(), "CLOUD_RESEARCH_FILESIGN成功", ResultMessage.Success);
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
