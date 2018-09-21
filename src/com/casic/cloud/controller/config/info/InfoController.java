package com.casic.cloud.controller.config.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.config.aptitude.Aptitude;
import com.casic.cloud.model.config.info.Info;
import com.casic.cloud.service.config.info.InfoService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.Dictionary;
import com.hotent.platform.service.system.DictionaryService;
import com.hotent.platform.service.system.SysUserService;
/**
 *<pre>
 * 对象功能:企业 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 19:28:40
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/info")
public class InfoController extends BaseController
{
	@Resource
	private InfoService infoService;
	@Resource
	private DictionaryService dicService;
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource 
	private SysUserService sysUserService;
	
	/**
	 * 添加或更新企业。
	 * @param request
	 * @param response
	 * @param info 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新企业")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		Info info=getFormObject(request);
		info.setFlaglogo(sysUserService.getFlagLogoByCountry(info.getCountry()));
		info.setInfo(RequestUtil.getString(request,"info"));
		try{
			
			if(info.getSysOrgInfoId()==null||info.getSysOrgInfoId()==0){
				info.setSysOrgInfoId(UniqueIdUtil.genId());
				info.setRegistertime(new Date());
				infoService.addAll(info);
				resultMsg=getText("record.added","企业信息");
			}else{
				infoService.updateAll(info);
				resultMsg=getText("record.updated","企业信息");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 发布
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("dep")
	@Action(description="审核")
	public void dep(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String resultMsg=null;		
		Info info=getFormObject(request);
		
		Long id = info.getSysOrgInfoId();
		Short state = info.getState();
		Info inf =  infoService.getById(id);
		
		try {
			if(id!=null||id!=0 &&  state!=null ){
				inf.setState(state) ;
				infoService.update(inf);
				
			}else{
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);	
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
			
		}
		
	}
	
	
	/**
	 * 取得 Info 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected Info getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("aptitudeList", Aptitude.class);
		
		Info info = (Info)JSONObject.toBean(obj, Info.class, map);
		
		return info;
    }
	
	/**
	 * 取得企业分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看企业分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
        QueryFilter queryFilter = new QueryFilter(request,"infoItem");
		
		String sqlKey = "getAll";
		queryFilter.getPageBean().setPagesize(10);
		List<Info> list=infoService.getAllInfos(sqlKey, queryFilter);
		
		ModelAndView mv=this.getAutoView().addObject("infoList",list);
		
		return mv;
	}
	
	
	/**
	 * 取得企业分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_xzc")
	@Action(description="查看企业分页列表")
	public ModelAndView list_xzc(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter = new QueryFilter(request,"InfoItem");
		String sqlKey = "getAllXzc";
		List<Info> list=infoService.getAllInfos(sqlKey, queryFilter);
		ModelAndView mv=this.getAutoView().addObject("InfoList",list);
		
		return mv;
		
	}
	
	/**
	 * 取得企业分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_wsh")
	@Action(description="查看企业分页列表")
	public ModelAndView list_wsh(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter = new QueryFilter(request,"InfoItem");
		
		String sqlKey = "getAllWsh";
		
		List<Info> list=infoService.getAllInfos(sqlKey, queryFilter);
		
		ModelAndView mv=this.getAutoView().addObject("InfoList",list);
		
		return mv;
		
	}
	
	/**
	 * 取得企业分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_ysh")
	@Action(description="查看企业分页列表")
	public ModelAndView list_ysh(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter = new QueryFilter(request,"InfoItem");
		
		String sqlKey = "getAllYsh";
		
		List<Info> list=infoService.getAllInfos(sqlKey, queryFilter);
		
		ModelAndView mv=this.getAutoView().addObject("InfoList",list);
		
		return mv;
		
	}
	
	
	
	/**
	 * 删除企业
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除企业")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "sysOrgInfoId");
			infoService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除企业成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	
	/**
	 * 删除企业
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("updateState")
	@Action(description="更新企业状态")
	public void updateState(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		short state = RequestUtil.getShort(request, "state",(short)1);
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "sysOrgInfoId");
			for(long id : lAryId){
				Info info = infoService.getById(id);
				info.setState(state);
				infoService.update(info);
			}
			message=new ResultMessage(ResultMessage.Success, "更新企业成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "更新失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 更新企业状态为新注册
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("updateStateToXZC")
	@Action(description="更新企业状态为新注册")
	public void updateStateToXZC(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		short state = 0;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "sysOrgInfoId");
			for(long id : lAryId){
				Info info = infoService.getById(id);
				info.setState(state);
				infoService.update(info);
			}
			message=new ResultMessage(ResultMessage.Success, "更新企业成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "更新失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 更新企业状态为未审核
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("updateStateToWSH")
	@Action(description="更新企业状态为未审核")
	public void updateStateToWSH(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		short state = 1;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "sysOrgInfoId");
			for(long id : lAryId){
				Info info = infoService.getById(id);
				info.setState(state);
				infoService.update(info);
			}
			message=new ResultMessage(ResultMessage.Success, "更新企业成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "更新失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 更新企业状态为已审核
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("updateStateToYSH")
	@Action(description="更新企业状态为已审核")
	public void updateStateToYSH(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		short state = 2;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "sysOrgInfoId");
			for(long id : lAryId){
				Info info = infoService.getById(id);
				info.setState(state);
				infoService.update(info);
			}
			message=new ResultMessage(ResultMessage.Success, "更新企业成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "更新失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑企业
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑企业")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long sysOrgInfoId=ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		//Long sysOrgInfoId=RequestUtil.getLong(request,"sysOrgInfoId");
		String returnUrl=RequestUtil.getPrePage(request);
		Info info=infoService.getById(sysOrgInfoId);
		
		List<Aptitude> aptitudeList=infoService.getAptitudeList(sysOrgInfoId);
		
		return getAutoView().addObject("info",info).addObject("aptitudeList",aptitudeList).addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得企业明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看企业明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long sysOrgInfoId=RequestUtil.getLong(request,"sysOrgInfoId");
		Info info = infoService.getById(sysOrgInfoId);	
		List<Aptitude> aptitudeList=infoService.getAptitudeList(sysOrgInfoId);
		return getAutoView().addObject("info", info).addObject("aptitudeList",aptitudeList);
	}
	
	
	/**
	 * 取得企业明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get2")
	@Action(description="查看企业明细")
	public ModelAndView get2(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long sysOrgInfoId=RequestUtil.getLong(request,"sysOrgInfoId");
		Info info = infoService.getById(sysOrgInfoId);	
		List<Aptitude> aptitudeList=infoService.getAptitudeList(sysOrgInfoId);
		return getAutoView().addObject("info", info).addObject("aptitudeList",aptitudeList);
	}
	
	
	/**
	 * ajax获取级联数组
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCascadeJsonData")
	@Action(description="查询物品分类")
	@ResponseBody
	public List<Dictionary> getCascadeJsonData(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Map<String, Object> map=new HashMap<String, Object>();
		String optvalue = RequestUtil.getString(request, "value");
		
		List<Map<String,Object>> list = jdbcTemplate.queryForList("select c.* from sys_dic c inner join sys_dic p where c.parentId=p.dicId and p.itemValue=?", optvalue);
		List<Dictionary> dics = new ArrayList();
		
		for(Map<String,Object> m : list){
			Dictionary dic = new Dictionary();
			dic.setDicId(Long.parseLong(m.get("dicId").toString()));
			dic.setItemKey(m.get("itemKey").toString());
			dic.setItemName(m.get("itemName").toString());
			dic.setItemValue(m.get("itemValue").toString());
			dics.add(dic);
		}
		
		
		return dics;
	}
	
	
}
