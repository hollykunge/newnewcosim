package com.casic.cloud.controller.config.orginfation;

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

import com.casic.cloud.model.config.orginfation.OrgInfation;
import com.casic.cloud.service.config.orginfation.OrgInfationService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:sys_org_info 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 18:08:13
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/orginfation/")
public class OrgInfationController extends BaseController
{
	@Resource
	private OrgInfationService OrgInfationService;
	
	
	/**
	 * 添加或更新sys_org_info。
	 * @param request
	 * @param response
	 * @param OrgInfation 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新sys_org_info")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		OrgInfation OrgInfation=getFormObject(request);
		try{
			if(OrgInfation.getSysOrgInfoId()==null||OrgInfation.getSysOrgInfoId()==0){
				OrgInfation.setSysOrgInfoId(UniqueIdUtil.genId());
				OrgInfationService.add(OrgInfation);
				resultMsg=getText("record.added","sys_org_info");
			}else{
			    OrgInfationService.update(OrgInfation);
				resultMsg=getText("record.updated","sys_org_info");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 OrgInfation 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected OrgInfation getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		OrgInfation OrgInfation = (OrgInfation)JSONObject.toBean(obj, OrgInfation.class);
		
		return OrgInfation;
    }
	
	/**
	 * 取得sys_org_info分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看sys_org_info分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<OrgInfation> list=OrgInfationService.getAll(new QueryFilter(request,"OrgInfationItem"));
		ModelAndView mv=this.getAutoView().addObject("OrgInfationList",list);
		
		return mv;
	}
	
	/**
	 * 删除sys_org_info
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除sys_org_info")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "sysOrgInfoId");
			OrgInfationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除sys_org_info成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑sys_org_info
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑sys_org_info")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long sysOrgInfoId=ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		//Long sysOrgInfoId=RequestUtil.getLong(request,"sysOrgInfoId");
		String returnUrl=RequestUtil.getPrePage(request);
		OrgInfation OrgInfation=OrgInfationService.getById(sysOrgInfoId);
		
		return getAutoView().addObject("OrgInfation",OrgInfation).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得sys_org_info明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看sys_org_info明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long sysOrgInfoId=RequestUtil.getLong(request,"sysOrgInfoId");
		OrgInfation OrgInfation = OrgInfationService.getById(sysOrgInfoId);	
		return getAutoView().addObject("OrgInfation", OrgInfation);
	}
	
}
