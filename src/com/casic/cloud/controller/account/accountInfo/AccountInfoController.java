package com.casic.cloud.controller.account.accountInfo;

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

import com.casic.cloud.model.account.accountInfo.AccountInfo;
import com.casic.cloud.service.account.accountInfo.AccountInfoService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:cloud_account_info 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-09-05 10:22:50
 *</pre>
 */
@Controller
@RequestMapping("/cloud/account/accountInfo/")
public class AccountInfoController extends BaseController
{
	@Resource
	private AccountInfoService AccountInfoService;
	
	
	/**
	 * 添加或更新cloud_account_info。
	 * @param request
	 * @param response
	 * @param AccountInfo 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_account_info")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		AccountInfo AccountInfo=getFormObject(request);
		try{
			if(AccountInfo.getId()==null||AccountInfo.getId()==0){
				AccountInfo.setId(UniqueIdUtil.genId());
				AccountInfoService.add(AccountInfo);
				resultMsg=getText("record.added","cloud_account_info");
			}else{
			    AccountInfoService.update(AccountInfo);
				resultMsg=getText("record.updated","cloud_account_info");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 AccountInfo 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected AccountInfo getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));

	String json=RequestUtil.getString(request, "json");
	JSONObject obj = JSONObject.fromObject(json);

	AccountInfo AccountInfo = (AccountInfo)JSONObject.toBean(obj, AccountInfo.class);

	return AccountInfo;
}

	/**
	 * 取得cloud_account_info分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_account_info分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<AccountInfo> list=AccountInfoService.getAll(new QueryFilter(request,"AccountInfoItem"));
		ModelAndView mv=this.getAutoView().addObject("AccountInfoList",list);
		
		return mv;
	}
	
	/**
	 * 删除cloud_account_info
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_account_info")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			AccountInfoService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除cloud_account_info成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑cloud_account_info
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_account_info")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		AccountInfo AccountInfo=AccountInfoService.getById(id);
		//当前企业，用户
		String operator = ContextUtil.getCurrentUser().getFullname();
		Long companyid = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		String comname = ContextUtil.getCurrentOrgInfoFromSession().getName();
		Long userid = ContextUtil.getCurrentUserId();
		
		if(AccountInfo==null){
			AccountInfo = new AccountInfo();
			AccountInfo.setOperateDate(new Date());
			AccountInfo.setOperaterId(userid);
			AccountInfo.setOperaterName(operator);
			AccountInfo.setEnterId(companyid);
			AccountInfo.setEnterName(comname);
		}
		
		return getAutoView().addObject("AccountInfo",AccountInfo).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得cloud_account_info明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_account_info明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		AccountInfo AccountInfo = AccountInfoService.getById(id);	
		return getAutoView().addObject("AccountInfo", AccountInfo);
	}
	
}
