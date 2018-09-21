package com.casic.cloud.controller.tool;

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

import com.casic.cloud.model.tool.Cloud_tool_user_file;
import com.casic.cloud.service.tool.Cloud_tool_user_fileService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:cloud_tool_user_file 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-03 09:47:37
 *</pre>
 */
@Controller
@RequestMapping("/cloud/tool/file/")
public class Cloud_tool_user_fileController extends BaseController
{
	@Resource
	private Cloud_tool_user_fileService cloud_tool_user_fileService;
	
	
	/**
	 * 添加或更新cloud_tool_user_file。
	 * @param request
	 * @param response
	 * @param cloud_tool_user_file 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_tool_user_file")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		Cloud_tool_user_file cloud_tool_user_file=getFormObject(request);
		request.setAttribute("cloudToolUserId", request.getParameter("cloudToolUserId"));
		try{
			if(cloud_tool_user_file.getId()==null||cloud_tool_user_file.getId()==0){
				cloud_tool_user_file.setId(UniqueIdUtil.genId());
				cloud_tool_user_fileService.add(cloud_tool_user_file);
				resultMsg=getText("record.added","cloud_tool_user_file");
			}else{
			    cloud_tool_user_fileService.update(cloud_tool_user_file);
				resultMsg=getText("record.updated","cloud_tool_user_file");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 Cloud_tool_user_file 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected Cloud_tool_user_file getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Cloud_tool_user_file cloud_tool_user_file = (Cloud_tool_user_file)JSONObject.toBean(obj, Cloud_tool_user_file.class);
		
		return cloud_tool_user_file;
    }
	
	/**
	 * 取得cloud_tool_user_file分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_tool_user_file分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Cloud_tool_user_file> list=cloud_tool_user_fileService.getAll(new QueryFilter(request,"cloud_tool_user_fileItem"));
		ModelAndView mv=this.getAutoView().addObject("cloud_tool_user_fileList",list).addObject("cloudToolUserId", request.getParameter("cloudToolUserId"));
		
		return mv;
	}
	
	/**
	 * 删除cloud_tool_user_file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_tool_user_file")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		request.setAttribute("cloudToolUserId", request.getParameter("cloudToolUserId"));
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			cloud_tool_user_fileService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除cloud_tool_user_file成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑cloud_tool_user_file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_tool_user_file")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		Cloud_tool_user_file cloud_tool_user_file=cloud_tool_user_fileService.getById(id);
		
		return getAutoView().addObject("cloud_tool_user_file",cloud_tool_user_file).addObject("returnUrl", returnUrl).addObject("cloudToolUserId", request.getParameter("cloudToolUserId"));
	}

	/**
	 * 取得cloud_tool_user_file明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_tool_user_file明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		Cloud_tool_user_file cloud_tool_user_file = cloud_tool_user_fileService.getById(id);	
		return getAutoView().addObject("cloud_tool_user_file", cloud_tool_user_file).addObject("cloudToolUserId", request.getParameter("cloudToolUserId"));
	}
	
}
