package com.hotent.platform.controller.mail;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.mail.OutMailUserSeting;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.mail.OutMailUserSetingService;

/**
 * 对象功能:邮箱设置 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2012-04-09 13:43:51
 */
@Controller
@RequestMapping("/platform/mail/outMailUserSeting/")
public class OutMailUserSetingController extends BaseController
{
	@Resource
	private OutMailUserSetingService outMailUserSetingService;
	
	/**
	 * 取得当前用户的外部邮箱分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看外部邮箱分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=new QueryFilter(request, "outMailUserSetingItem");
		long userId=ContextUtil.getCurrentUserId();
		queryFilter.getFilters().put("userId", userId);
		List<OutMailUserSeting> list=outMailUserSetingService.getAllByUserId(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("outMailUserSetingList",list);
		return mv;
	}
	
	/**
	 * 删除邮箱
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除外部邮箱")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			outMailUserSetingService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除邮箱成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 编辑邮箱
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑邮箱")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		OutMailUserSeting outMailUserSeting=null;
		if(id!=0){
			 outMailUserSeting= outMailUserSetingService.getById(id);
		}else{
			outMailUserSeting=new OutMailUserSeting();
		}
		return getAutoView().addObject("outMailUserSeting",outMailUserSeting).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得邮箱明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看外部邮箱明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		OutMailUserSeting outMailUserSeting = outMailUserSetingService.getById(id);		
		return getAutoView().addObject("outMailUserSeting", outMailUserSeting);
	}
	
	/**
	 * 测试接收/发送 服务器连接情况
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("test")
	@Action(description="测试邮箱连接情况")
	public void test(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		long id=RequestUtil.getLong(request, "id");
		
		ResultMessage resultMessage=null;
		OutMailUserSeting outMailUserSeting=outMailUserSetingService.getById(id);
		if(id==0){
			outMailUserSeting=getForm(request);
		}
		try{
			outMailUserSetingService.testConnection(outMailUserSeting);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "测试通过!"));
		}catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"测试通过失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		} 
	}
	
	/**
	 * 更改默认邮箱设置;
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("setDefault")
	@Action(description="设置默认邮箱")
	public void setupDefault(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		long id=RequestUtil.getLong(request, "id");
		OutMailUserSeting outMailUserSeting=outMailUserSetingService.getById(id);
		outMailUserSeting.setIsDefault(1);
		try{
			outMailUserSetingService.setDefault(outMailUserSeting);
			message=new ResultMessage(ResultMessage.Success, "设置成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "设置失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 得到实体对象
	 * @param request
	 * @return
	 */
	public OutMailUserSeting getForm(HttpServletRequest request){
		OutMailUserSeting outMailUserSeting=new OutMailUserSeting();
		String address=RequestUtil.getString(request, "address");
		String password=RequestUtil.getString(request, "password");
		String smtpHost=RequestUtil.getString(request, "smtpHost");
		String smtpPort=RequestUtil.getString(request, "smtpPort");
		String popHost=RequestUtil.getString(request, "popHost");
		String popPort=RequestUtil.getString(request,"popPort");
		String imapHost=RequestUtil.getString(request, "imapHost");
		String imapPort=RequestUtil.getString(request, "imapPort");
		String mailType=RequestUtil.getString(request, "mailType");
		outMailUserSeting.setMailType(mailType);
		outMailUserSeting.setMailAddress(address);
		outMailUserSeting.setMailPass(password);
		outMailUserSeting.setSmtpHost(smtpHost);
		outMailUserSeting.setSmtpPort(smtpPort);
		outMailUserSeting.setPopHost(popHost);
		outMailUserSeting.setPopPort(popPort);
		outMailUserSeting.setImapHost(imapHost);
		outMailUserSeting.setImapPort(imapPort);
		return outMailUserSeting;
	}
}
