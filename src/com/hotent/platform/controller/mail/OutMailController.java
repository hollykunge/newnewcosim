package com.hotent.platform.controller.mail;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.mail.OutMail;
import com.hotent.platform.model.mail.OutMailUserSeting;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.mail.OutMailService;
import com.hotent.platform.service.mail.OutMailUserSetingService;
import com.hotent.platform.service.mail.impl.MailService;

/**
 * 对象功能:邮件 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2012-04-09 14:16:18
 */
@Controller
@RequestMapping("/platform/mail/outMail/")
public class OutMailController extends BaseController
{
	@Resource
	private OutMailService outMailService;
	@Resource
	private OutMailUserSetingService outMailUserSetingService;	
	@Resource
	private MailService mailService;
	
	/**
	 * 取得邮件分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看邮件分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		long userId=ContextUtil.getCurrentUserId();
		OutMailUserSeting defaultMail=outMailUserSetingService.getByIsDefault(userId);
		long outMailSetId=RequestUtil.getLong(request, "id",defaultMail.getId());
		int type=RequestUtil.getInt(request, "types",1);
		OutMailUserSeting mail=outMailUserSetingService.getById(outMailSetId);
		
		QueryFilter queryFilter=new QueryFilter(request, "outMailItem");
		Map<String,Object> filter=queryFilter.getFilters();
		filter.put("userId", userId);
		filter.put("setId",outMailSetId);
		filter.put("types", type);
		List<OutMail> list=outMailService.getFolderList(queryFilter);
		
		ModelAndView mv=this.getAutoView().addObject("outMailList",list)
										.addObject("outMailSetId",outMailSetId)
										.addObject("outMailUserSet", mail)
										.addObject("types", type);
		return mv;
	}
	
	/**
	 * 
	 * 邮箱同步处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sync")
	@Action(description="同步邮件列表")
	public void sync(HttpServletRequest request,HttpServletResponse response) throws Exception 
	{	
		List<OutMail> list=new ArrayList<OutMail>();
		long id=RequestUtil.getLong(request, "id");
		OutMailUserSeting outMailUserSeting=outMailUserSetingService.getById(id);
		try {
			outMailService.emailSync(ContextUtil.getCurrentUser(),outMailUserSeting);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "同步邮件成功"));
		} catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"同步邮件失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}	
	
	/**
	 * 删除邮件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除邮件")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long[] lAryId =RequestUtil.getLongAryByStr(request, "mailId");
		String preUrl= RequestUtil.getPrePage(request);
		long outMailSetId=RequestUtil.getLong(request,"outMailSetId");
		int type=RequestUtil.getInt(request, "types");
		
		OutMailUserSeting outMailUserSeting=outMailUserSetingService.getById(outMailSetId);
		ResultMessage message=null;
		try{
			String mailType=outMailUserSeting.getMailType();
			if(type==4){
				outMailService.delEndEmail(lAryId, outMailUserSeting);
				if(mailType.trim().equals("imap")){
					message=new ResultMessage(ResultMessage.Success, "成功删除本地及服务器上邮件!");
				}else{
					message=new ResultMessage(ResultMessage.Success, "成功删除本地上邮件!");
				}
			}else if(type==3||type==2){//直接删除本地草稿箱/发件箱中的邮件
				outMailService.delByIds(lAryId);
				message=new ResultMessage(ResultMessage.Success, "成功删除本地上邮件!");
			}else{//将收件箱与发件箱邮件移至垃圾箱
				outMailService.addDump(lAryId);
				message=new ResultMessage(ResultMessage.Success, "成功将邮件移至垃圾箱");
			}
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 无邮件时的提示信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("warn")
	@Action(description="提醒")
	public ModelAndView warn(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int count=outMailUserSetingService.getCountByUserId(ContextUtil.getCurrentUserId());
		return getAutoView().addObject("count", count);
	}
	
	/**
	 * 回复邮件
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("reply")
	@Action(description="回复邮件")
	public ModelAndView reply(HttpServletRequest request) throws Exception{
		Long mailId=RequestUtil.getLong(request, "mailId");
		int isReply=RequestUtil.getInt(request, "isReply");
		Long outMailSetId=RequestUtil.getLong(request, "outMailSetId");
		OutMailUserSeting outMailUserSeting=outMailUserSetingService.getById(outMailSetId);
		
		OutMail outMail=outMailService.getOutMailReply(mailId);
		return getAutoView().addObject("outMail",outMail)
							.addObject("outMailUserSeting", outMailUserSeting)
							.addObject("outMailSetId", outMailSetId);
	}
	
	/**
	 * 取得邮件明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看邮件明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long mailId=RequestUtil.getLong(request,"mailId");
		OutMail outMail = outMailService.getById(mailId);
		int type=outMail.getTypes();
		long outMailSetId=RequestUtil.getLong(request,"outMailSetId");
		if(type==1){
			outMailService.emailRead(outMail);
		}
		return getAutoView().addObject("outMail", outMail)
							.addObject("outMailSetId", outMailSetId);
	}
	
	/**
	 * 邮件编辑
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="新建邮件")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long mailId=RequestUtil.getLong(request,"mailId");
		long userId=ContextUtil.getCurrentUserId();
		String returnUrl=RequestUtil.getPrePage(request);
		OutMail outMail=null;
		if(mailId==0){
			outMail=new OutMail();
		}else{
			outMail= outMailService.getById(mailId);
		}
		List<OutMailUserSeting> list=outMailUserSetingService.getMailByUserId(userId);
		return getAutoView().addObject("outMail",outMail).addObject("returnUrl", returnUrl).addObject("outMailUserSetingList",list);
	}	
	
	/**
	 * 获得邮箱树形列表的json数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMailTreeData")
	@ResponseBody
	public List<OutMailUserSeting> getMailTreeData(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		Long userId=ContextUtil.getCurrentUserId();
		
		List<OutMailUserSeting> list=outMailService.getMailTreeData(userId);
		return list;
	}
	
	/**
	 * 获取邮件接收服务器类型的json数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getRecieveServerTypeData")
	@ResponseBody
	public String getRecieveServerType(HttpServletRequest request,HttpServletResponse response){
		long id=RequestUtil.getLong(request, "outMailSetId");
		OutMailUserSeting outMailUserSeting=outMailUserSetingService.getById(id);
		String type=outMailUserSeting.getMailType();
		return type;
	}
	
	/**
	 * 下载邮件附件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("downLoadAttach")
	@Action(description="下载邮件附件")
	public void downLoadAttach(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long mailId=RequestUtil.getLong(request, "mailId");
		String preUrl= RequestUtil.getPrePage(request);
		long setId=RequestUtil.getLong(request, "outMailSetId");
		OutMailUserSeting outMailUserSeting=outMailUserSetingService.getById(setId);
		OutMail outMail=outMailService.getById(mailId);
		Date time=outMail.getMailDate();
		String username=outMailUserSeting.getUserName();
		long fileId=RequestUtil.getLong(request, "fileId");
		int year=time.getYear()+1900;
		int month=time.getMonth()+1;
		int day=time.getDate();
		String filepath=AppUtil.getAppAbsolutePath()+"emailAttachs"+File.separator+username+File.separator+year+File.separator+month+File.separator+day;
		try {
			outMailService.downLoadAttach(response,fileId,filepath);
		} catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"文件下载失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}
	
	/**
	 * 发送系统错误报告至公司邮箱
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("sendError")
	@Action(description="发送系统错误报告")
	public void sendError(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String errorMsg=RequestUtil.getString(request, "errorMsg");
		String errorUrl=RequestUtil.getString(request, "errorUrl");
		String content="<div>"+errorUrl+"</div><br><div>"+errorMsg+"</div>";
		
		OutMailUserSeting outUser=new OutMailUserSeting();
		outUser.setSmtpPort(configproperties.getProperty("port"));
		outUser.setSmtpHost(configproperties.getProperty("host"));
		String recieveAdress=configproperties.getProperty("recieveAdress");
		String sendMail=configproperties.getProperty("sendMail");
		outUser.setMailAddress(sendMail);
		outUser.setMailPass("htsoft");
		outUser.setUserName("BPMX3错误中心");
		try {
			outMailService.sendError(content,recieveAdress,outUser);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "发送错误报告成功"));
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"发送错误报告失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
		
	}
}
