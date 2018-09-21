package com.hotent.platform.service.mail.impl;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.UIDFolder;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.CertUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.controller.mail.IMailHandler;
import com.hotent.platform.controller.mail.MailAttachment;
import com.hotent.platform.model.mail.EmailAddress;
import com.hotent.platform.model.mail.OutMail;
import com.hotent.platform.service.mail.model.Mail;
import com.hotent.platform.service.mail.model.MailSetting;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;
/**
 * 对象功能:外部邮件处理 Service类
 * 开发公司:
 * 开发人员:zyp
 * 创建时间:2012-04-27 10:39:29
 */
public class MailService   {
	private Logger logger=LoggerFactory.getLogger(MailService.class);
	static Long MAIL_NO_READ=0L;//未读
	static Long MAIL_IS_READ=1L;//已读
	static Integer MAIL_IS_RECEIVE = 1;// 收件箱
	static String sendType = "smtp";
	
	private IMailHandler mailHandler;
     
    private String dateformat = "yy-MM-dd HH:mm"; //默认的日前显示格式   

	public void setMailHandler(IMailHandler mailHandler) {
		this.mailHandler = mailHandler;
	}

	/**
	 * 获得OutMail实体
	 * @param message
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public OutMail getOutMail(MimeMessage message,MailSetting mailSet)throws Exception{
		OutMail bean =new OutMail();
		Date sentDate = null;
		if (message.getSentDate() != null) {
			sentDate = message.getSentDate();
		} else {
			sentDate = new Date();
		}
		//邮件发送时间
		bean.setMailDate(sentDate);
		bean.setSetId(mailSet.getId());
		long mailId=UniqueIdUtil.genId();
		bean.setMailId(mailId);
		bean.setTitle(getSubject(message));
		//取得邮件内容
		StringBuffer bodytext=new StringBuffer();
		getMailContent(message,bodytext);
		
		bean.setContent(bodytext.toString());
		//发件人
		bean.setSenderAddresses(getFrom(1,message));
		bean.setSenderName(getFrom(2,message));
		//接受者
		bean.setReceiverAddresses(getMailAddress2("to",1,message));
		bean.setReceiverNames(getMailAddress2("to",2,message));
		//暗送者
		bean.setBcCAddresses(getMailAddress2("bcc",1,message));
		bean.setBcCAnames(getMailAddress2("bcc",2,message));
		//抄送者
		bean.setCcAddresses(getMailAddress2("cc",1,message));
		bean.setCcNames(getMailAddress2("cc",2,message));
		bean.setTypes(MAIL_IS_RECEIVE);
		bean.setIsRead(OutMail.Mail_IsNotRead);
		bean.setUserId(ContextUtil.getCurrentUserId());
		return bean;
	}
	
	/**
	 * 同步下载服务器上的邮件
	 * @throws Exception
	 */
	public void emailSync(MailSetting mailSet) throws Exception {
		
		String mailType=mailSet.getReceiverType();
		String receiverhost=mailSet.getReceiverHost();
		String receiverport=mailSet.getReceiverPort();
		Properties props=getProperty(mailType,receiverhost,receiverport);
		URLName urln = new URLName(mailType,receiverhost,Integer.parseInt(receiverport), null,mailSet.getMailAddress()  
	        		,mailSet.getMailPass());
		Session session = Session.getInstance(props, null);
        Store store = session.getStore(urln);  
        store.connect();  
        Folder folder = store.getFolder("INBOX");  
        folder.open(Folder.READ_ONLY); 
        OutMail bean=null;
        try {
			if(folder instanceof POP3Folder){
				POP3Folder inbox=(POP3Folder)folder;
		        FetchProfile profile = new FetchProfile();// 感兴趣的信息
		        profile.add(UIDFolder.FetchProfileItem.UID);// 邮件标识id
		        profile.add(FetchProfile.Item.ENVELOPE); 
				Message messages[] = inbox.getMessages();  
		        inbox.fetch(messages, profile);
		        for (int i = 0; i < messages.length; i++) {  
		        	MimeMessage message=(MimeMessage) messages[i];
		            System.out.println("=========pop3 subject==="+getSubject(message));
	                if(!mailHandler.isUidExist(inbox.getUID(messages[i]))){
						bean=getOutMail(message,mailSet);
						bean.setEmailId(inbox.getUID(message));
	            		mailHandler.saveEmail(bean);
	            		System.out.println("已下载邮件"+bean.getTitle());
						System.gc();
	            	}
		        } 
			}else if(folder instanceof IMAPFolder){
				IMAPFolder inbox=(IMAPFolder)folder;
		        FetchProfile profile = new FetchProfile();// 感兴趣的信息
		        profile.add(UIDFolder.FetchProfileItem.UID);// 邮件标识id
				Message messages[] = inbox.getMessages();  
		        inbox.fetch(messages, profile);
		        for (int i = 0; i < messages.length; i++) {  
		        	MimeMessage message=(MimeMessage) messages[i];
		            System.out.println("=========imap subject==="+getSubject(message));
		            if(!mailHandler.isUidExist(""+inbox.getUID(messages[i]))){
            			bean=getOutMail(message,mailSet);
            			bean.setEmailId(""+inbox.getUID(message));
	            		mailHandler.saveEmail(bean);
	            		System.out.println("已下载邮件"+bean.getTitle());
						System.gc();
		            }
		        } 
			}
        }  finally {
        	folder.close(false);
        	store.close();
    	}
	}
	
	/**
	 * 发送邮件
	 * @param outMail 发送邮件信息
	 * @param outMailUserSeting 发送人配置信息
	 * @throws Exception
	 */
	public void sendEmail(Mail mail)throws Exception{
		logger.debug("send start...");
		//取得通道session
		Session session=emailConn(mail);
		Transport transport =null;
		logger.debug("connetion session:" + session);
		//邮件主题信息
		try{
			MimeMessage message = new MimeMessage(session);
			emailBase(mail, message);//获取邮件第一部分内容(发件人，收件人，抄送人，暗送人)
			BodyPart contentPart = new MimeBodyPart();// 内容
			Multipart multipart = new MimeMultipart();
			contentPart.setHeader("Content-Transfer-Encoding", "base64");
			//邮件正文(第二部分邮件的内容及附件)
			contentPart.setContent(mail.getContent(), "text/html;charset=utf-8");
			message.setSubject(mail.getSubject(), "utf-8");
			message.setText("utf-8", "utf-8");
			message.setSentDate(mail.getSendDate() == null ? new Date():mail.getSendDate());
			multipart.addBodyPart(contentPart);// 邮件正文
			message.setContent(multipart);
			//添加附件
			if(mail.getFiles()!=null){
				for(MailAttachment bean:mail.getFiles()){
					if(bean!=null){
						File file = new File(AppUtil.getAppAbsolutePath()+ "/" + bean.getFilePath());// 取得路径生成文件
						BodyPart messageBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(file);
						messageBodyPart.setDataHandler(new DataHandler(source));
						messageBodyPart.setFileName(MimeUtility.encodeWord(bean.getFileName(), "UTF-8", "Q"));
						multipart.addBodyPart(messageBodyPart);
					}
				}																			
			}
			message.setContent(multipart);
			message.saveChanges();
			transport = session.getTransport(sendType);
			transport.connect(mail.getSenderHost(),mail.getSenderAddress(), mail.getPassword());
			transport.sendMessage(message,message.getAllRecipients());
		}finally{
			transport.close();
		}
		logger.debug("send end");
	}
	
	/**
	 * /删除远程邮件
	 * @param lAryId
	 * @param outMailUserSeting
	 * @return
	 * @throws MessagingException
	 */
	public void delEndEmail(String[] lAryUId,MailSetting mailSet) throws Exception{
		for(String uid:lAryUId){
			String mailType=mailSet.getReceiverType();
			String host=mailSet.getReceiverHost();
			String port=mailSet.getReceiverPort();
			Properties props=getProperty(mailType, host, port);
			URLName urln=new URLName(mailType,host,Integer.parseInt(port), null,mailSet.getMailAddress() 
	        		,mailSet.getMailAddress());
			Session session = Session.getInstance(props, null);
	        Store store = session.getStore(urln);  
	        store.connect(); 
	        Folder folder = store.getFolder("INBOX");  
	        folder.open(Folder.READ_WRITE); 
	        if(folder instanceof IMAPFolder){
		        try {
			        Message messages[] = folder.getMessages();  
			        IMAPFolder inbox=(IMAPFolder)folder;
			        FetchProfile profile = new FetchProfile();// 感兴趣的信息
			        profile.add(UIDFolder.FetchProfileItem.UID);// 邮件标识id
			        inbox.fetch(messages, profile);
			        for (int i = 0; i < messages.length; i++) {  
			            String subject=getSubject((MimeMessage)(messages[i]));
			            if(uid.equals(""+inbox.getUID(messages[i]))){
			            	messages[i].setFlag(Flags.Flag.DELETED, true);//设置已删除状态为true
			                System.out.println("已删除邮件"+subject);
			            }
			        } 
		        }  finally {
		        	folder.close(false);
		        	store.close();
		    	}
	        }
		}
	}
	
	 /**  
     * 获得邮件主题  
     */  
    private String getSubject(MimeMessage mimeMessage) throws Exception {   
    	if(mimeMessage.getSubject()==null){
    		return "";
    	}else{
    		String subject= MimeUtility.decodeText(mimeMessage.getSubject());   
    		return subject;   
    	}
    } 
    
    
	/**  
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，
     * 解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析  
     */  
    private void getMailContent(Part message,StringBuffer bodytext) throws Exception {   
    	String contentType = message.getContentType();
        int nameindex = contentType.indexOf("name");
        boolean conname = false;
        if(nameindex != -1){
            conname = true;
        }
        if(message.isMimeType("text/plain")&&!conname){
            bodytext.append((String)message.getContent());
        }else if(message.isMimeType("text/html")&&!conname){
            bodytext.append((String)message.getContent());
        }else if(message.isMimeType("multipart/*")){
            Multipart multipart = (Multipart) message.getContent();
            
            int count = multipart.getCount();
            Map<String, Part> partMap=new LinkedHashMap<String, Part>();
            
            boolean blnTxt=false;
            boolean blnHtml=false;        
            for(int i=0;i<count;i++){
            	Part tmpPart=multipart.getBodyPart(i);
            	String partType=tmpPart.getContentType();
            	if(tmpPart.isMimeType("text/plain")){
            		partMap.put("text/plain", tmpPart);
            		blnTxt=true;
            	}
            	else if(tmpPart.isMimeType("text/html")){
            		partMap.put("text/html", tmpPart);
            		blnHtml=true;
            	}
            	else{
            		partMap.put(partType, tmpPart);        	
            	}
            }
            if(blnTxt && blnHtml){
            	partMap.remove("text/plain");
            }
            Set<Entry<String, Part>> set=partMap.entrySet();
            for(Iterator<Entry<String, Part>> it=set.iterator();it.hasNext();){
            	getMailContent(it.next().getValue(),bodytext); 
            }
            
        }else if(message.isMimeType("message/rfc822")){
            getMailContent((Part) message.getContent(),bodytext); 
        }
    } 
    
  
    /**
     * 将邮件中的附件保存在本地制定目录下
     * @param filename
     * @param in
     * @return
     */
    public long saveAttach(String filename,InputStream in,Date time,String userAccount)throws Exception{
    	int year=time.getYear()+1900;
    	int month=time.getMonth()+1;
    	int day=time.getDate();
		long fileId=UniqueIdUtil.genId();
    	String filePath=AppUtil.getAppAbsolutePath()+"emailAttachs"+File.separator
    					+userAccount+File.separator+year+File.separator+month+File.separator+day+File.separator
    					+fileId+"."+FileUtil.getFileExt(filename);
    	File file=new File(filePath);
    	FileUtil.createFolderFile(filePath);
		FileUtil.writeFile(filePath, in);
    	return fileId;
    }
    
    /**  
     * 获得发件人的地址和姓名  
     */  
    private String getFrom(int form,MimeMessage mimeMessage) throws Exception {   
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom(); 
        String fromaddr ="";
        if(form==1){
        	String from = address[0].getAddress();   
	        if (from == null){from = "";}
	        fromaddr=from;
        }else{
			String personal = address[0].getPersonal();   
			if (personal == null){personal = "";} 
			fromaddr=personal;
        }
        return fromaddr;
    } 
    
    /**
     * 获得邮件的收件人信息包拯邮箱地址,邮箱名称
     * @param type
     * @param form  1:得到邮箱地址；2:得到邮箱名称
     * @return
     * @throws Exception
     */
    private String getMailAddress2(String type,int form,MimeMessage mimeMessage) throws Exception {   
        String mailaddr = "";   
        String addtype = type.toUpperCase();   
        InternetAddress[] address = null;   
        if (addtype.equals("TO") || addtype.equals("CC")|| addtype.equals("BCC")){   
            if(addtype.equals("TO")){   
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);   
            }else if(addtype.equals("CC")) {   
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);   
            }else{   
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);   
            }   
            if (address != null) {   
                for (int i = 0; i < address.length; i++) { 
                	String compositeto ="";
                	if(form==1){
                    String email = address[i].getAddress(); 
                    if (email == null){email = "";}   
                    else {email = MimeUtility.decodeText(email);}   
                    compositeto =email;}
                	else{
                    String personal = address[i].getPersonal();   
                    if (personal == null){personal = "";}   
                    else {   
                    personal = MimeUtility.decodeText(personal);}   
                    compositeto =personal;}
                    mailaddr += "," + compositeto;   
                }
                if(mailaddr.length()>0){
                	mailaddr = mailaddr.substring(1);   
                }
            }   
        } else {   
            throw new Exception("Error emailaddr type!");   
        }   
        return mailaddr;   
    }
    
	
	/**
	 * 邮件连接
	 * @param username
	 * @param password
	 * @param smtpHost
	 * @param smtpPort
	 * @return
	 */
	public Session emailConn(Mail mail) {
		final String username = mail.getSenderAddress();
		final String password = mail.getPassword();
		String smtpHost = mail.getSenderHost();
		String smtpPort = mail.getSenderPort();
		
		String smtpAuth = "true";
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", smtpHost);
		props.setProperty("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", smtpAuth);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);
		File cert = CertUtil.get(smtpHost, Integer.parseInt(smtpPort));
		if (cert != null) {
			logger.debug("ssl connection...");
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			System.setProperty("javax.net.ssl.trustStore", cert.getAbsolutePath());
			props.put("javax.net.ssl.trustStore", cert.getAbsolutePath());// 证书
		} else {
			final String TLS_FACTORY = "javax.net.SocketFactory";
			int gmail=smtpHost.trim().indexOf("gmail");
	    	int live=smtpHost.trim().indexOf("live");
 	    	if(gmail!=-1||live!=-1){
	    		props.put("mail.smtp.starttls.enable", "true");
	    	}
			props.setProperty("mail.smtp.socketFactory.class", TLS_FACTORY);
			logger.debug("plaintext connection or tls connection...");
		}
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		return session;
	}
	
	/**
	 * 发送邮件基本信息
	 * @param outMail
	 * @param bean
	 * @param message
	 * @throws Exception
	 * @throws MessagingException
	 */
	public static void emailBase(Mail mail,MimeMessage message) throws Exception, MessagingException {
		// 添加发件人
		InternetAddress aa=toInternetAddress(mail.getSender(),mail.getSenderAddress());
		message.setFrom(aa);
		//收件人列表
		String receiverAddresses=mail.getReceiverAddress();
		EmailAddress emailAddress=new EmailAddress();
		List<EmailAddress> receiverAddressesel=new ArrayList<EmailAddress>();
		if(!receiverAddresses.equals("")){
			String[] receiverAddressesl=receiverAddresses.split(",");
			for(String id:receiverAddressesl){
				emailAddress=new EmailAddress();
				emailAddress.setAddress(id);
				emailAddress.setName(id);
				receiverAddressesel.add(emailAddress);
			}
		}
		//抄送人列表
		String ccAddresses=mail.getCcAddresses();
		List<EmailAddress> ccAddressesel=new ArrayList<EmailAddress>();
		if(ccAddresses!=null&&!ccAddresses.equals("")){
			String[] ccAddressessl=ccAddresses.split(",");
			for(String id:ccAddressessl){
				emailAddress=new EmailAddress();
				emailAddress.setAddress(id);
				emailAddress.setName(id);
				ccAddressesel.add(emailAddress);
			}
		}
		//暗送人列表
		String bcCAddresses=mail.getBcCAddresses();
		List<EmailAddress> bcCAddressesslel=new ArrayList<EmailAddress>();
		if(bcCAddresses!=null&&!bcCAddresses.equals("")){
			String[] bcCAddressessl=bcCAddresses.split(",");
			for(String id:bcCAddressessl){
				emailAddress=new EmailAddress();
				emailAddress.setAddress(id);
				emailAddress.setName(id);
				bcCAddressesslel.add(emailAddress);
			}
		}
		// 添加收件人
		InternetAddress address[] = getAddressByType(receiverAddressesel);
		if (address != null)
			message.addRecipients(Message.RecipientType.TO, address);
		// 添加抄送人
		if (ccAddressesel != null && ccAddressesel.size() > 0) {
			address = getAddressByType(ccAddressesel);
			if (address != null)
				message.addRecipients(Message.RecipientType.CC, address);
		}
		// 添加暗送人
		if (bcCAddressesslel != null && bcCAddressesslel.size() > 0) {
			address = getAddressByType(bcCAddressesslel);
			if (address != null)
				message.addRecipients(Message.RecipientType.BCC, address);
		}
	}
	
	/**
	 * 测试接收邮件服务器连接情况
	 * @param outMailUserSeting
	 * @throws MessagingException 
	 * @throws Exception
	 */
	public void connectReciever(Mail mail) throws Exception {
		//取得session通道
		String mailType=mail.getReceiverType();
		String host=mail.getReceiverHost();
		String port=mail.getReceiverPort();
		Properties props=getProperty(mailType,host,port);
		URLName urln = new URLName(mailType,host,Integer.parseInt(port), null,mail.getSenderAddress(),mail.getPassword());
		Session session = Session.getInstance(props, null);
		//创建连接
		Store store=null;
		try {
			store = session.getStore(urln);
			store.connect();
		}finally{
			store.close();
		}
	}
	
	/**
	 * 测试发送邮件smtp服务器连接情况
	 * @param outMailUserSeting
	 * @throws Exception
	 */
	public void connectSmtp(Mail mail)throws Exception{
		// 取得通道session
		Session session=emailConn(mail);
		//创建smtp连接
		Transport transport=null;
		try {
			transport = session.getTransport(sendType);
			transport.connect(mail.getSenderHost(),mail.getSenderAddress(), mail.getPassword());
		}finally{
			transport.close();
		}
	}
	
	/**
	 * 邮件回复
	 * @param outMail
	 * @return
	 */
	public StringBuffer emailReply(OutMail outMail) {
		StringBuffer newSubject = new StringBuffer("<br><br><hr>");
		newSubject.append("<br>----<strong> 回复邮件</strong>----");
		newSubject.append("<br><strong>发件人</strong>:" + outMail.getSenderName());
		newSubject.append("<br><strong>发送时间</strong>:" +TimeUtil.getDateTimeString(outMail.getMailDate()));
		newSubject.append("<br><strong>收件人</strong>:"+ outMail.getReceiverNames());
		String copyToNames = outMail.getCcNames();
		if ( copyToNames != null&&!copyToNames.equals("")) {
			newSubject.append("<br><strong>抄送人</strong>:" + copyToNames);
		}
		newSubject.append("<br><strong>主题</strong>:" + outMail.getTitle());
		newSubject.append("<br><strong>内容</strong>:<br><br>"+ outMail.getContent());
		return newSubject;
	}
	
	/**
	 * 将地地址址转化为 可输送的网络地址
	 */
	private static InternetAddress toInternetAddress(String name,String address) throws Exception {
		if (name != null && !name.trim().equals("")) {
			return new InternetAddress(address, MimeUtility.encodeWord(	name, "utf-8", "Q"));
		}
		return new InternetAddress(address);
	}
	
	/**
	 * 将地地址址转化为 可输送的网络地址
	 */
	private static InternetAddress[] getAddressByType(List<EmailAddress> list)
			throws Exception {
		if (list != null) {
			InternetAddress address[] = new InternetAddress[list.size()];
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).toInternetAddress() != null) {
					address[i] = list.get(i).toInternetAddress();
				}
			}
			return address;
		}
		return null;
	}
	
	/**  
     * 获得邮件发送日期  
     */  
	private String getSentDate(MimeMessage mimeMessage) throws Exception {   
        Date sentdate = mimeMessage.getSentDate();   
        SimpleDateFormat format = new SimpleDateFormat(dateformat);   
        return format.format(sentdate);   
    } 
    
    //从接收邮件服务器上接收邮件 包括gmail msn，hotmail,live邮箱Properties配置
    private Properties getProperty(String type,String host,String port){
    	Properties prop=new Properties();
    	if("pop3".equals(type)){
    		prop.put("mail.pop3.socketFactory.fallback", "false");
        	prop.put("mail.pop3.port", port);
        	prop.put("mail.pop3.socketFactory.port",port);
        	prop.put("mail.pop3.host", host); 
        	prop.put("mail.smtp.starttls.enable", "true");
        	int gmail=host.trim().indexOf("gmail");
        	int live=host.trim().indexOf("live");
        	if(gmail!=-1||live!=-1){
        		prop.put("mail.pop3.socketFactory.class",  "javax.net.ssl.SSLSocketFactory");
        	}
    	}else{
    		prop.put("mail.imap.socketFactory.fallback","false");
    		prop.put("mail.imap.port",port);
    		prop.put("mail.imap.socketFactory.port",port);
    		prop.put("mail.imap.host", host);
    		prop.put("mail.store.protocol","imap"); 
    		int gmail=host.trim().indexOf("gmail");
        	int live=host.trim().indexOf("live");
        	if(gmail!=-1||live!=-1){
        		prop.put("mail.imap.socketFactory.class",  "javax.net.ssl.SSLSocketFactory");
        	}
    	}
    	return prop;
    }
}
