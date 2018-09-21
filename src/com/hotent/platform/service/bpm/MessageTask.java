package com.hotent.platform.service.bpm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.*;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.Message;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.MessageService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.jms.MessageProducer;
import com.hotent.core.model.InnerMessage;
import com.hotent.core.model.MailModel;
import com.hotent.core.model.SmsMobile;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;

/**
 * 发送消息任务任务。
 * 
 * @author ray
 * 
 */
public class MessageTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		ExecutionEntity ent = (ExecutionEntity) execution;
		// 获取消息参数
		String nodeId = ent.getActivityId();
		String actDefId = ent.getProcessDefinitionId();
		
		MessageService messageService = (MessageService) AppUtil.getBean("messageService");
		Map<Integer, Message> dataMap = messageService.getMapByActDefIdNodeId(actDefId, nodeId);
		//判断是否有消息
		if(BeanUtils.isEmpty(dataMap)) return ;
		
		
		Map<String,Object> varMap= execution.getVariables();
		Long startUserId=Long.parseLong(execution.getVariable(BpmConst.StartUser).toString());
		
		ProcessRunService processRunService = (ProcessRunService) AppUtil.getBean("processRunService");
		String instanceId= execution.getProcessInstanceId();
		ProcessRun processRun = processRunService.getByActInstanceId(instanceId);
		String subject=processRun.getSubject();
		
		
		
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("actDefId",actDefId);
		params.put("nodeId",nodeId);
		params.put("startUserId",startUserId);
		params.put("subject",subject);
	

		List list = getSendList(dataMap,params,	varMap);
		
		MessageProducer messageProducer = (MessageProducer) AppUtil.getBean("messageProducer");
		if(messageProducer!=null){
			for (int i = 0; i < list.size(); i++) {
				messageProducer.send(list.get(i));
			}
		}
	}

	private List getSendList(Map<Integer, Message> dataMap,Map<String,Object> param,Map<String,Object> varsMap) {
		List list = new ArrayList();
		
		ISysUser currentUser=ContextUtil.getCurrentUser();
		if(currentUser==null){
			currentUser=new SysUser();
			currentUser.setFullname("系统");
			currentUser.setUserId(0L);
		}
		
		Long startUserId=(Long)param.get("startUserId");
		String subject=param.get("subject").toString();
		
		Message mailMessage = dataMap.get(Message.MAIL_TYPE);
		if (mailMessage != null) {
			String receiver=mailMessage.getReceiver();
			String starter="";
			if(mailMessage.getSendToStartUser()==1){
				SysUserService sysUserService = (SysUserService) AppUtil.getBean("sysUserService");
				ISysUser startUser=sysUserService.getById(startUserId);
				
				if(startUser!=null&&startUser.getEmail()!=null ){
					starter=startUser.getFullname()+"("+startUser.getEmail()+")";
					if(StringUtil.isEmpty(receiver)){
						receiver=starter;
					}
					else{
						receiver+="," +starter;
					}
				}
			}
			if(StringUtil.isNotEmpty(receiver)){
				mailMessage.setReceiver(receiver);
				List<MailModel> mailModelList = getMailModel(mailMessage,subject,varsMap,currentUser);
				list.addAll(mailModelList);
			}
		}

		Message mobileMessage = dataMap.get(Message.MOBILE_TYPE);
		if (mobileMessage != null) {
			String receiver=mobileMessage.getReceiver();
			String starter="";
			if(mobileMessage.getSendToStartUser()==1){
				SysUserService sysUserService = (SysUserService) AppUtil.getBean("sysUserService");
				ISysUser startUser=sysUserService.getById(startUserId);
				if(startUser!=null && startUser.getPhone()!=null){
					starter=startUser.getFullname()+"("+startUser.getPhone()+")";
					if(StringUtil.isEmpty(receiver)){
						receiver=starter;
					}
					else{
						receiver+="," +starter;
					}
				}
			}
			if(StringUtil.isNotEmpty(receiver)){
				mobileMessage.setReceiver(receiver);
				List<SmsMobile> smsList = getMobileModel(mobileMessage,subject,varsMap,currentUser);
				list.addAll(smsList);
			}
		}
		
		Message innerMessage = dataMap.get(Message.INNER_TYPE);
		if (innerMessage != null) {
//			String receiver=mailMessage.getReceiver();
			String receiver=innerMessage.getReceiver();
			String starter="";
			if(innerMessage.getSendToStartUser()==1){
				SysUserService sysUserService = (SysUserService) AppUtil.getBean("sysUserService");
				ISysUser startUser=sysUserService.getById(startUserId);
				starter=startUser.getFullname()+"("+startUserId+")";
				if(StringUtil.isEmpty(receiver)){
					receiver=starter;
				}
				else{
					receiver+="," +starter;
				}
			}
			if(StringUtil.isNotEmpty(receiver)){
				innerMessage.setReceiver(receiver);
				List<InnerMessage> innerMesageList = getInnerModel(innerMessage,subject,varsMap,currentUser);
				list.addAll(innerMesageList);
			}
			
		}
		return list;
	}

	/**
	 * 发送邮件信息队列
	 * 
	 * @param
	 * @param
	 * @throws
	 */
	public List<MailModel> getMailModel(Message messageModel,String subject,Map<String ,Object> varsMap,ISysUser currentUser) {
	  Date sendDate = new Date();
       List<MailModel> messageList=new ArrayList<MailModel>();
		
		String receiver=messageModel.getReceiver();
		Map<String,String> mapReceiver=splitString(receiver);
		Set<Entry<String, String>> set= mapReceiver.entrySet();
		
		for(Iterator<Entry<String, String>> it=set.iterator();it.hasNext();){
			Entry<String, String> ent=it.next();
			String toEmail=ent.getKey();
			String toName=ent.getValue();
			
			String content=messageModel.getContent().replace("${收件人}", toName).replace("${发件人}", currentUser.getFullname()).replace("${事项名称}", subject);
			content=replaceVars(content, varsMap);
			
			MailModel mailModel = new MailModel();
			
			if(StringUtil.isNotEmpty(messageModel.getSubject())){
				subject=messageModel.getSubject();
			}
			
			mailModel.setSubject(subject);
			mailModel.setContent(content);
		
			mailModel.setTo(new String[]{ toEmail});
			//mailModel.setFrom(messageModel.getFromUser());
			mailModel.setSendDate(sendDate);
			// 发送至队列
			messageList.add(mailModel);
		}
		return messageList;
	}

	/**
	 * 发送手机短信队列
	 * 
	 * @param
	 * @param
	 * @throws
	 */
	public List<SmsMobile> getMobileModel(Message messageModel,String subject,Map<String ,Object> varsMap,ISysUser currentUser) {
		List<SmsMobile> messageList=new ArrayList<SmsMobile>();
		
		String receiver=messageModel.getReceiver();
		Map<String,String> mapReceiver=splitString(receiver);
		Set<Entry<String, String>> set= mapReceiver.entrySet();
		
		for(Iterator<Entry<String, String>> it=set.iterator();it.hasNext();){
			Entry<String, String> ent=it.next();
			String mobileNo=ent.getKey();
			String userName=ent.getValue();
			
			String content=messageModel.getContent().replace("${收件人}", userName).replace("${发件人}", currentUser.getFullname()).replace("${事项名称}", subject);
			//替换变量。
			content=replaceVars(content, varsMap);
			
			Date sendDate = new Date();
			
			SmsMobile smsMobile = new SmsMobile();
			smsMobile.setPhoneNumber(mobileNo);
			smsMobile.setSmsContent(content);
			smsMobile.setUserName(messageModel.getFromUser());
			smsMobile.setSendTime(sendDate);
			
			messageList.add(smsMobile);
		}
		return messageList;
	}

	/**
	 * 发送内部消息队列
	 * 
	 * @param
	 * @param
	 * @throws
	 */
	private List<InnerMessage> getInnerModel(Message messageModel,String subject,Map<String ,Object> varsMap,ISysUser currentUser) {
		List<InnerMessage> messageList=new ArrayList<InnerMessage>();
		String receiver=messageModel.getReceiver();
		Map<String,String> mapReceiver=splitString(receiver);
		Set<Entry<String, String>> set= mapReceiver.entrySet();
		
		for(Iterator<Entry<String, String>> it=set.iterator();it.hasNext();){
			Entry<String, String> ent=it.next();
			String userId=ent.getKey();
			String userName=ent.getValue();
			String content=messageModel.getContent().replace("${收件人}",userName).replace("${发件人}", currentUser.getFullname()).replace("${事项名称}", subject);
			//替换变量。
			content=replaceVars(content, varsMap);
			
			Date sendDate = new Date();
			InnerMessage innerMessage = new InnerMessage();
			
			if(StringUtil.isNotEmpty(messageModel.getSubject())){
				subject=messageModel.getSubject();
			}
			
			innerMessage.setSubject(subject);
			innerMessage.setContent(content);
			innerMessage.setTo(userId);
			innerMessage.setToName(userName);
			innerMessage.setFrom(currentUser.getUserId().toString());//设置系统消息发送人ID为0
			innerMessage.setFromName(currentUser.getFullname());
			innerMessage.setSendDate(sendDate);
			messageList.add(innerMessage);
		}
		return messageList;
	}
	
	private String replaceVars(String content,Map<String, Object> vars){
		for(Entry<String, Object> entry:vars.entrySet()){
			String hold="${"+entry.getKey()+"}";
			content=content.replace(hold, entry.getValue()==null?"":entry.getValue().toString());
		}
		return content;
	}


	/**
	 * 返回一个长度为2数组。
	 * ary[0]存放姓名,使用逗号分隔。
	 * ary[1]存放ID
	 * @param message
	 * @return
	 */
	private Map<String,String> splitString(String message) {		
		if(message==null) return null;
		String[] strs = message.split(",");
		Pattern pattern = Pattern.compile("(.*)\\((.*)\\)");
		Map<String,String> map=new HashMap<String, String>();
		for (String str : strs) {
			Matcher match = pattern.matcher(str);
			if (match.find()) {
				map.put(match.group(2), match.group(1));
			}
		}
		return map;
	}
}
