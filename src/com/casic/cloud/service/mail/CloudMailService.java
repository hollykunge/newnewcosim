package com.casic.cloud.service.mail;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.cloud.model.reg.register.Register;
import com.hotent.platform.model.mail.OutMail;
import com.hotent.platform.model.mail.OutMailUserSeting;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.mail.OutMailService;
import com.hotent.platform.service.mail.OutMailUserSetingService;

/**
 * 发送邮箱
 * @author Administrator
 *
 */
@Service
public class CloudMailService {
	@Resource
	private OutMailService outMailService;
	
	@Resource
	private OutMailUserSetingService outMailUserSetingService;
	
	/**
	 * 企业注册成功后发送邮件
	 * @param sysOrgInfo
	 * @param sysOrg
	 */
	public void sendSuccessfulRegMessage(SysOrgInfo sysOrgInfo, SysOrg sysOrg, String password, String context, String serverPath){
		String content = 
				"<div class=\"n2success\">" +
					"<div class=\"n2-1success\">" +
						"<div class=\"n2-2\">" +
							"<div class=\"j01-2success\">恭喜您: 注册成功！您可以直接登录平台,请牢记以下信息<br />" +
								"企业账号 : " + sysOrgInfo.getSysOrgInfoId() + "<br />" +
								"企业管理员账号 : " + sysOrg.getSysUser().getShortAccount() + "<br />" +
								"企业管理员密码 : " + password + 
							"</div>" +
							"<div class=\"j01-4\">" +
								"<div class=\"msg_normalsuccess\">" +
									"请点击<a href=\"" + serverPath + "" + context + "\">此处</a>进行登录！" +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>" +
				"</div>";
		sendMessage(sysOrgInfo.getEmail(),"恭喜您，成为平台新注册用户", content, context);
	}
	/**
	 * 企业注册成功后发送邮件
	 * @param sysOrgInfo
	 * @param sysOrg
	 */
	public void sendRegMessage(SysOrgInfo sysOrgInfo, String shortAccount, String password, String context, String serverPath){
		String content = 
				"<div class=\"n2success\">" +
					"<div class=\"n2-1success\">" +
						"<div class=\"n2-2\">" +
							"<div class=\"j01-2success\">恭喜您: 密码重置成功！您可以直接登录平台,请牢记以下信息<br />" +
								"企业账号 : " + sysOrgInfo.getSysOrgInfoId() + "<br />" +
								"企业管理员账号 : " + shortAccount + "<br />" +
								"企业管理员密码 : " + password + 
							"</div>" +
							"<div class=\"j01-4\">" +
								"<div class=\"msg_normalsuccess\">" +
									"请点击<a href=\"" + serverPath + "" + context + "\">此处</a>进行登录！" +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>" +
				"</div>";
		sendMessage(sysOrgInfo.getEmail(),"恭喜您，密码已经重置！", content, context);
	}
	
	public void sendSuccessfulRegMessageRegister(SysOrgInfo sysOrgInfo,Register register, String context, String serverPath){
		String content = 
				"<div class=\"n2success\">" +
					"<div class=\"n2-1success\">" +
						"<div class=\"n2-2\">" +
							"<div class=\"j01-2success\">恭喜您: 添加成功！<br />" +
								"邮箱 : " + register.getEmail() + "<br/>"+
							"姓名："+register.getName()+"<br/>"+
							"身份证号："+register.getIdentity()+"<br/>"+
							"手机号码："+register.getTellphone()+"<br>"+
							"获奖证书："+register.getCredential()+"<br/>"+
							"作品介绍："+register.getIntroduce()+ "<br />"+
							"</div>" +
							"<div class=\"j01-4\">" +								
							"</div>" +
						"</div>" +
					"</div>" +
				"</div>";
		sendMessage(register.getEmail(),"恭喜您，添加成功", content, context);
	}
	
	/**
	 * 发送邮件
	 * @param receiveAddress
	 * @param content
	 */
	public void sendMessage(String receiveAddress, String title, String content, String context){
		//获取平台管理员的邮箱设置
		OutMailUserSeting outMailUserSeting=outMailUserSetingService.getByIsDefault(1);
		
		//邮件内容
		OutMail outMail = new OutMail();
		outMail.setMailDate(new Date());
		outMail.setSenderName(outMailUserSeting.getUserName());
		outMail.setIsReply(0);
		outMail.setUserId(1L);
		outMail.setSetId(outMailUserSeting.getId());
		outMail.setReceiverAddresses(receiveAddress);
		outMail.setCcAddresses(outMailUserSeting.getMailAddress());
		outMail.setTitle(title);
		outMail.setContent(content);
		outMail.setTypes(2);
		outMail.setSetId(outMailUserSeting.getId());
		outMail.setMailId(0L);
		outMail.setSenderName("云制造平台");
		outMail.setSenderAddresses(outMailUserSeting.getMailAddress());		
		try {
			outMailService.sendMail(outMail, 1, 0, 0, context);
		} catch (Exception e) {
			System.out.println("邮件发送失败" + e.getMessage());
		}
	}
}
