package com.casic.cloud.controller.config.aptitude;

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

import com.casic.cloud.model.config.aptitude.Aptitude;
import com.casic.cloud.service.config.aptitude.AptitudeService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:sys_org_info_aptitude 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-06 16:34:55
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/aptitude/")
public class AptitudeController extends BaseController
{
	@Resource
	private AptitudeService aptitudeService;
	
	
	/**
	 * 添加或更新sys_org_info_aptitude。
	 * @param request
	 * @param response
	 * @param Aptitude 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新sys_org_info_aptitude")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		Aptitude aptitude=getFormObject(request);
		try{
			if(aptitude.getId()==null||aptitude.getId()==0){
				aptitude.setId(UniqueIdUtil.genId());
				aptitudeService.add(aptitude);
				resultMsg=getText("record.added","sys_org_info_aptitude");
			}else{
				aptitudeService.update(aptitude);
				resultMsg=getText("record.updated","sys_org_info_aptitude");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 Aptitude 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected Aptitude getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Aptitude aptitude = (Aptitude)JSONObject.toBean(obj, Aptitude.class);
		
		return aptitude;
    }
	
	/**
	 * 取得sys_org_info_aptitude分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看sys_org_info_aptitude分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Aptitude> list=aptitudeService.getAll(new QueryFilter(request,"AptitudeItem"));
		ModelAndView mv=this.getAutoView().addObject("AptitudeList",list);
		
		return mv;
	}
	
	/**
	 * 删除sys_org_info_aptitude
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除sys_org_info_aptitude")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			aptitudeService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除sys_org_info_aptitude成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑sys_org_info_aptitude
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑sys_org_info_aptitude")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		Aptitude aptitude=aptitudeService.getById(id);
		
		return getAutoView().addObject("aptitude",aptitude).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得sys_org_info_aptitude明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看sys_org_info_aptitude明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		Aptitude aptitude = aptitudeService.getById(id);	
		return getAutoView().addObject("aptitude", aptitude);
	}
	
}
