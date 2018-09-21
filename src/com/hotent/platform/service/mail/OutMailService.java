package com.hotent.platform.service.mail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.controller.mail.MailAttachment;
import com.hotent.platform.dao.mail.OutMailDao;
import com.hotent.platform.dao.mail.OutMailUserSetingDao;
import com.hotent.platform.dao.system.SysFileDao;
import com.hotent.platform.model.mail.OutMail;
import com.hotent.platform.model.mail.OutMailUserSeting;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.service.mail.impl.MailService;
import com.hotent.platform.service.mail.model.Mail;
import com.hotent.platform.service.mail.model.MailSetting;
import com.hotent.platform.service.system.SysUserService;
/**
 * 对象功能:外部邮件 Service类
 * 开发公司:
 * 开发人员:zyp
 * 创建时间:2012-04-09 14:16:18
 * 
 * 注意调用时(activation-1.1.jar和mail-1.4.4.jar)，
 * 其(geronimo-activation_1.1_spec-1.1.jar，
 * geronimo-javamail_1.4_spec-1.7.1.jar)在调用javax.mail.Part接口时有冲突，需要删除后组
 */
@Service
public class OutMailService extends BaseService<OutMail>
{
	static Long MAIL_NO_READ=0L;//未读
	static Long MAIL_IS_READ=1L;//已读
	static Integer MAIL_IS_RECEIVE = 1;// 收件箱
	static Integer MAIL_IS_SEND = 2;// 发件箱
	static Integer MAIL_IS_DRAFT = 3;// 草稿箱
	static Integer MAIL_IS_DELETE = 4;// 垃圾箱
	@Resource
	private OutMailDao dao;
	@Resource
	private OutMailUserSetingDao outMailUserSetingDao;
	@Resource
	private OutMailLinkmanService outMailLinkmanService;
	@Resource
	private SysFileDao sysFileDao;
	@Resource
	private SysUserService sysUserService;
	@Override
	protected IEntityDao<OutMail, Long> getEntityDao() 
	{return dao;}
	@Resource
	protected MailService mailService;
	public OutMailService()	{}
	
	/**
	 * 邮件发送
	 * @param outMail
	 * @throws Exception
	 */
	public void emailSend(Mail mail) throws Exception {
		//发送邮件开始
		if (StringUtil.isNotEmpty(mail.getFileIds())){
			String[] fileid_s = mail.getFileIds().split(",");
			List<MailAttachment> files=new ArrayList<MailAttachment>();
			MailAttachment fileTemp=null;
			SysFile sysFile=null;
			for (int i = 0; i < fileid_s.length; i++) {
				fileTemp=new MailAttachment();
				sysFile=sysFileDao.getById(Long.parseLong(fileid_s[i]));
				fileTemp.setFileName(sysFile.getFileName());
				fileTemp.setFilePath(sysFile.getFilePath());
				files.add(fileTemp);
			}
			mail.setFiles(files);
		}
		mailService.sendEmail(mail);
	}
	
	/**
	 * 保存邮件至垃圾箱
	 * @param request
	 * @param outMailUserSeting
	 * @return
	 */
	public void addDump(Long[] lAryId) {
		for(Long l:lAryId){
			OutMail outMail = dao.getById(l);	
			dao.updateTypes(outMail.getMailId(),MAIL_IS_DELETE);
		}
	}
	
	/**
	 * 彻底删除邮件（删除本地邮件信息/邮箱为imap类型同步删除服务器上邮件）
	 * @param lAryId
	 * @param outMailUserSeting
	 * @throws MessagingException
	 */
	public void delEndEmail(Long[] lAryId,OutMailUserSeting outMailUserSeting) throws Exception{
		String[] UIds=new String[lAryId.length];
		for(int i=0;i<lAryId.length;i++){
			long mailId=lAryId[i];
			UIds[i]=getById(mailId).getEmailId();
		}
		if(UIds!=null){
			MailSetting mailSet=getMailSet(ContextUtil.getCurrentUser(), outMailUserSeting);
			mailService.delEndEmail(UIds,mailSet);
		}
		delByIds(lAryId);
	}
	
	/**
	 * 浏览邮件
	 * @param outMail
	 * @param outMailUserSeting
	 * @throws NoSuchProviderException
	 * @throws MessagingException
	 */
	public void emailRead(OutMail outMail)throws NoSuchProviderException, MessagingException {
        outMail.setIsRead(OutMail.Mail_IsRead);
        dao.update(outMail);
	}	
	
	/**
	 * 同步远程邮件，进行选择性下载
	 * @param outMailUserSeting
	 * @throws Exception
	 */
	public void emailSync(ISysUser user,OutMailUserSeting outMailUserSeting) throws Exception {
		MailSetting mailSet=getMailSet(user, outMailUserSeting);
		mailService.emailSync(mailSet);
	}
	
    /**
     * 邮箱树形列表的json数据
     * @param request
     * @param list
     * @param listTemp
     * @param listEnd
     * @throws Exception 
     */
	public List<OutMailUserSeting> getMailTreeData(Long userId) throws Exception {
		List<OutMailUserSeting> list=outMailUserSetingDao.getMailByUserId(userId);
		List<OutMailUserSeting> temp=new ArrayList<OutMailUserSeting>();
		OutMailUserSeting omus=null;
		for(OutMailUserSeting beanTemp:list){
			beanTemp.setParentId(0L);
			long id=beanTemp.getId();
			temp.add(beanTemp);
		    for(int i=0;i<4;i++){
		    	omus=new OutMailUserSeting();
		    	if(i==0){ 
		    		omus.setUserName("收件箱("+getCount(id,MAIL_IS_RECEIVE)+")");
			    	omus.setTypes(MAIL_IS_RECEIVE);
		    	}else if(i==1){
		    		omus.setUserName("发件箱("+getCount(id,MAIL_IS_SEND)+")");
			    	omus.setTypes(MAIL_IS_SEND);
		    	}else if(i==2){
		    		omus.setUserName("草稿箱("+getCount(id,MAIL_IS_DRAFT)+")");
			    	omus.setTypes(MAIL_IS_DRAFT);
		    	}else {
		    		omus.setUserName("垃圾箱("+getCount(id,MAIL_IS_DELETE)+")");
			    	omus.setTypes(MAIL_IS_DELETE);
			    }
				omus.setId(UniqueIdUtil.genId());
				omus.setParentId(beanTemp.getId());
			    temp.add(omus);
		    }
		}
		return temp;
	}
	
	/**
	 * 获取邮箱的分类邮件，如收件箱，发件箱，草稿箱
	 * @param queryFilter
	 * @return
	 */
	public List<OutMail> getFolderList(QueryFilter queryFilter){
		return dao.getFolderList(queryFilter);
	}
	
	/**
	 * 获取邮箱的分类邮件数
	 * @param address
	 * @param type
	 * @param userId
	 * @return
	 */
	private int getCount(long id,int type){
		return dao.getFolderCount(id, type);
	}
	
	/**
	 * 得到用户默认邮箱中的邮件列表
	 * @param queryFilter
	 * @return
	 */
	public List<OutMail> getDefaultMailList(QueryFilter queryFilter) {
		return dao.getDefaultMailList(queryFilter);
	}
	
	/**
	 * 发送邮件,保存邮件信息至本地,添加/更新最近联系人
	 * @param outMail
	 * @param outMailFiles
	 * @param fileIds
	 * @throws Exception
	 */
	public void sendMail(OutMail outMail,long userId,long mailId,int isReply,String context) throws Exception {
		OutMailUserSeting outMailUserSeting=outMailUserSetingDao.getById(outMail.getSetId());
		Mail mail=getMail(outMail, outMailUserSeting);
		//发送邮件
		emailSend(mail);
		if(mailId==0||isReply==1){
			outMail.setMailId(UniqueIdUtil.genId());
			addSendMail(outMail,context);
		}else{
			dao.updateTypes(mailId, 2);
		}
		//添加 所有收件人为最近联系人
		outMailLinkmanService.addLinkMan(outMail,userId);
	}
	
	
	/**
	 * 下载邮件附件
	 * @param response
	 * @param filepath
	 */
	public void downLoadAttach(HttpServletResponse response, long fileId,String filepath)throws IOException {
		String filename="";
		File[] files=FileUtil.getFiles(filepath);
		for(File file:files){
			int index=file.getName().indexOf((fileId+"").trim());
			if(index!=-1){
				filename=file.getName();
				break;
			}
		}
		FileUtil.downLoadFile(response, filepath+File.separator+filename, filename);
	}
	
	/**
	 * 得到用于回复页面显示信息
	 * @param mailId
	 * @return
	 */
	public OutMail getOutMailReply(Long mailId) {
		OutMail outMail=getById(mailId);
		StringBuffer newSubject = mailService.emailReply(outMail);
		outMail.setContent(newSubject.toString());
		outMail.setIsReply(OutMail.Mail_IsReplay);
		outMail.setTitle("回复:" + outMail.getTitle());
		return outMail;
	}
	
	
	public void addSendMail(OutMail outMail,String context) {
		String fileIds=outMail.getFileIds();
		if(fileIds==null||fileIds==""){
			add(outMail);
		}else{
			String content=outMail.getContent();
			String[] fileIdAry=fileIds.split(",");
			content=content+"<br><hr><h2>附件"+fileIdAry.length+"</h2>";
			for(String fileId:fileIdAry){
				long id=Long.parseLong(fileId);
				SysFile file=sysFileDao.getById(id);
				String fileName=file.getFileName()+"."+file.getExt();
				Date time=file.getCreatetime();
				int year=time.getYear()+1900;
				int month=time.getMonth()+1;
				String filePath=context+"/attachFiles"+"/"+"temp"+"/"+year+"/"+month+"/"+fileId+"."+file.getExt();
				content=content+"<div style='font-size:15px;'><font color='green'>"+fileName+"</font>&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+filePath+"'>查看</a></div>";
			}
			outMail.setContent(content);
			add(outMail);
		}
	}
	
	/**
	 * 获取Mail 实例
	 * @param outMail
	 * @param outMailUserSeting
	 * @return
	 */
	private Mail getMail(OutMail outMail,OutMailUserSeting outMailUserSeting){
		Mail mail=new Mail();
		if(BeanUtils.isNotEmpty(outMail)){
			mail.setSender(outMail.getSenderName());
			mail.setSenderAddress(outMail.getSenderAddresses());
			mail.setReceiverAddress(outMail.getReceiverAddresses());
			if(StringUtil.isNotEmpty(outMail.getBcCAddresses())){
				mail.setBcCAddresses(outMail.getBcCAddresses());
			}
			if(StringUtil.isNotEmpty(outMail.getCcAddresses())){
				mail.setCcAddresses(outMail.getCcAddresses());
			}
			if(StringUtil.isNotEmpty(outMail.getFileIds())){
				mail.setFileIds(outMail.getFileIds());
			}
			mail.setContent(outMail.getContent());
			mail.setSubject(outMail.getTitle());
		}
		if(BeanUtils.isNotEmpty(outMailUserSeting)){
			mail.setPassword(outMailUserSeting.getMailPass());
			mail.setSenderHost(outMailUserSeting.getSmtpHost());
			mail.setSenderPort(outMailUserSeting.getSmtpPort());
		}
		return mail;
	}
	
	private MailSetting getMailSet(ISysUser user,OutMailUserSeting outMailUserSeting){
		MailSetting mailSet=new MailSetting();
		mailSet.setId(outMailUserSeting.getId());
		mailSet.setMailAddress(outMailUserSeting.getMailAddress());
		mailSet.setMailPass(outMailUserSeting.getMailPass());
		mailSet.setUserAccount(outMailUserSeting.getUserName());
		mailSet.setUserName(user.getFullname());
		String type=outMailUserSeting.getMailType();
		mailSet.setReceiverType(type);
		if("pop3".equals(type)){
			mailSet.setReceiverHost(outMailUserSeting.getPopHost());
			mailSet.setReceiverPort(outMailUserSeting.getPopPort());
		}else{
			mailSet.setReceiverHost(outMailUserSeting.getImapHost());
			mailSet.setReceiverPort(outMailUserSeting.getImapPort());
		}
		return mailSet;
		
	}
	
	/**
	 * 发送系统错误报告至公司邮箱
	 * @param content
	 * @param recieveAdress
	 * @param mail
	 * @throws Exception
	 */
	public void sendError(String errorMsg, String recieveAdress, OutMailUserSeting outMailUserSeting)throws Exception {
		OutMail outMail=new OutMail();
		outMail.setContent(errorMsg);
		outMail.setTitle("BPMX3错误报告！");
		
		outMail.setSenderAddresses(outMailUserSeting.getMailAddress());
		outMail.setReceiverAddresses(recieveAdress);
		
		Mail mail=getMail(outMail, outMailUserSeting);
		mailService.sendEmail(mail);
		
	}
}
