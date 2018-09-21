package com.hotent.platform.controller.system;

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
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.hotent.platform.model.system.SysCodeTemplate;
import com.hotent.platform.service.system.SysCodeTemplateService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:自定义表代码模版 控制器类
 * 开发公司:
 * 开发人员:zyp
 * 创建时间:2012-12-19 15:38:01
 */
@Controller
@RequestMapping("/platform/system/sysCodeTemplate/")
public class SysCodeTemplateController extends BaseController
{
	@Resource
	private SysCodeTemplateService sysCodeTemplateService;
	
	/**
	 * 添加或更新自定义表代码模版。
	 * @param request
	 * @param response
	 * @param sysCodeTemplate 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新自定义表代码模版")
	public void save(HttpServletRequest request, HttpServletResponse response,SysCodeTemplate sysCodeTemplate) throws Exception
	{
		String resultMsg=null;		
		try{
			if(sysCodeTemplate.getId()==null){
				Long id=UniqueIdUtil.genId();
				sysCodeTemplate.setId(id);
				sysCodeTemplateService.add(sysCodeTemplate);
				resultMsg=getText("record.added","自定义表代码模版");
			}else{
			    sysCodeTemplateService.update(sysCodeTemplate);
				resultMsg=getText("record.updated","自定义表代码模版");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	
	/**
	 * 取得自定义表代码模版分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看自定义表代码模版分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<SysCodeTemplate> list=sysCodeTemplateService.getAll(new QueryFilter(request,"sysCodeTemplateItem"));
		ModelAndView mv=this.getAutoView().addObject("sysCodeTemplateList",list);
		
		return mv;
	}
	
	/**
	 * 删除自定义表代码模版
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除自定义表代码模版")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");

			sysCodeTemplateService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除自定义表代码模版成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑自定义表代码模版
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑自定义表代码模版")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");

		String returnUrl=RequestUtil.getPrePage(request);
		SysCodeTemplate sysCodeTemplate=sysCodeTemplateService.getById(id);
		
		return getAutoView().addObject("sysCodeTemplate",sysCodeTemplate).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得自定义表代码模版明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看自定义表代码模版明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");

		SysCodeTemplate sysCodeTemplate = sysCodeTemplateService.getById(id);	
		return getAutoView().addObject("sysCodeTemplate", sysCodeTemplate);
	}
	
	/**
	 * 取得初始化模板信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("init")
	@Action(description="初始化模板",operateType="自定义表单模板")
	public void init(HttpServletRequest request,HttpServletResponse response)throws Exception
	{	
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			sysCodeTemplateService.initAllTemplate();
			message=new ResultMessage(ResultMessage.Success, "初始化表单模板成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			message=new ResultMessage(ResultMessage.Fail, "初始化表单模板失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
}
