package com.casic.cloud.controller.research.fileVerify;

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

import com.casic.cloud.model.research.fileVerify.FileVerify;
import com.casic.cloud.service.research.fileVerify.FileVerifyService;
import com.casic.cloud.model.research.fileVerify.FileVerifyResult;
import com.casic.cloud.model.research.fileVerify.FileVerifyUpload;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.webservice.api.ProcessService;
/**
 *<pre>
 * 对象功能:CLOUD_RESEARCH_VERIFY 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-14 21:16:24
 *</pre>
 */
@Controller
@RequestMapping("/cloud/research/fileVerify/")
public class FileVerifyController extends BaseController
{
	@Resource
	private FileVerifyService fileVerifyService;
	@Resource
	private IdentityService identityService;
	@Resource
	private ProcessService processService;
	private final String defKey = "wdsqlc";	//绑定流程定义
	
	/**
	 * 添加或更新CLOUD_RESEARCH_VERIFY。
	 * @param request
	 * @param response
	 * @param fileVerify 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		FileVerify fileVerify=getFormObject(request);
		try{
			if(fileVerify.getId()==null||fileVerify.getId()==0){
				fileVerify.setId(UniqueIdUtil.genId());
				
				fileVerifyService.addAll(fileVerify);			
				resultMsg=getText("record.added","CLOUD_RESEARCH_VERIFY");
			}else{
			    fileVerifyService.updateAll(fileVerify);
				resultMsg=getText("record.updated","CLOUD_RESEARCH_VERIFY");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 FileVerify 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected FileVerify getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("fileVerifyResultList", FileVerifyResult.class);
		map.put("fileVerifyUploadList", FileVerifyUpload.class);
		FileVerify fileVerify = (FileVerify)JSONObject.toBean(obj, FileVerify.class,map);
		
		return fileVerify;
    }
	
	/**
	 * 取得CLOUD_RESEARCH_VERIFY分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<FileVerify> list=fileVerifyService.getAll(new QueryFilter(request,"fileVerifyItem"));
		ModelAndView mv=this.getAutoView().addObject("fileVerifyList",list);
		
		return mv;
	}
	
	/**
	 * 删除CLOUD_RESEARCH_VERIFY
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
			fileVerifyService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除CLOUD_RESEARCH_VERIFY及其从表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑CLOUD_RESEARCH_VERIFY
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		FileVerify fileVerify=fileVerifyService.getById(id);
		
		
		if(fileVerify==null){
			fileVerify=new FileVerify();
			String orderNo=identityService.doNextId("file_verify");
			//自动生成询价单编码、制单人、采购企业
			fileVerify.setCode(orderNo);			
			fileVerify.setApplicantId(ContextUtil.getCurrentUser().getUserId());
			fileVerify.setApplicantName(ContextUtil.getCurrentUser().getFullname());
			fileVerify.setCreatetime(new Date());
			
		}
		
		
		List<FileVerifyResult> fileVerifyResultList=fileVerifyService.getFileVerifyResultList(id);
		List<FileVerifyUpload> fileVerifyUploadList=fileVerifyService.getFileVerifyUploadList(id);
		
		return getAutoView().addObject("fileVerify",fileVerify)
							.addObject("fileVerifyResultList",fileVerifyResultList)
							.addObject("fileVerifyUploadList",fileVerifyUploadList)
							.addObject("returnUrl",returnUrl);
	}

	
	/**
	 * 	编辑CLOUD_RESEARCH_VERIFY
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("view")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		FileVerify fileVerify=fileVerifyService.getById(id);
		
		List<FileVerifyResult> fileVerifyResultList=fileVerifyService.getFileVerifyResultList(id);
		List<FileVerifyUpload> fileVerifyUploadList=fileVerifyService.getFileVerifyUploadList(id);
		
		return getAutoView().addObject("fileVerify",fileVerify)
							.addObject("fileVerifyResultList",fileVerifyResultList)
							.addObject("fileVerifyUploadList",fileVerifyUploadList)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得CLOUD_RESEARCH_VERIFY明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		FileVerify fileVerify = fileVerifyService.getById(id);	
		List<FileVerifyResult> fileVerifyResultList=fileVerifyService.getFileVerifyResultList(id);
		List<FileVerifyUpload> fileVerifyUploadList=fileVerifyService.getFileVerifyUploadList(id);
		return getAutoView().addObject("fileVerify",fileVerify)
							.addObject("fileVerifyResultList",fileVerifyResultList)
							.addObject("fileVerifyUploadList",fileVerifyUploadList);
	}
	
	/**
	 * 添加或更新CLOUD_RESEARCH_VERIFY<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("apply")
	@Action(description = "添加或更新CLOUD_RESEARCH_VERIFY")
	public void apply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		FileVerify fileVerify=getFormObject(request);
		long id=RequestUtil.getLong(request,"id");
		try {
			if (fileVerify != null) {
				if (fileVerify.getId() == null || fileVerify.getId() == 0) {
					fileVerify.setId(UniqueIdUtil.genId());
					fileVerifyService.add(fileVerify);
					resultMsg = getText("record.added", "CLOUD_RESEARCH_VERIFY");
				} else {
					fileVerifyService.update(fileVerify);
					resultMsg = getText("record.updated", "CLOUD_RESEARCH_VERIFY");
				}
				id =fileVerify.getId();
			} 

			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setFlowKey(defKey);
			processCmd.setBusinessKey(String.valueOf(id));
			processCmd.setUserAccount(ContextUtil.getCurrentUser().getFullname());
			processService.start(processCmd);

			writeResultMessage(response.getWriter(), "CLOUD_RESEARCH_VERIFY成功", ResultMessage.Success);
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
