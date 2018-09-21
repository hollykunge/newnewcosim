package com.hotent.platform.service.mail;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.dao.mail.OutMailUserSetingDao;
import com.hotent.platform.model.mail.OutMailUserSeting;
import com.hotent.platform.service.mail.impl.MailService;
import com.hotent.platform.service.mail.model.Mail;

/**
 * 对象功能:邮箱设置 Service类
 * 开发公司:
 * 开发人员:zyp
 * 创建时间:2012-04-09 13:43:51
 */
@Service
public class OutMailUserSetingService extends BaseService<OutMailUserSeting>
{
	@Resource
	private OutMailUserSetingDao dao;
	
	@Resource
	protected MailService mailService;
	
	public OutMailUserSetingService()
	{
	}
	
	@Override
	protected IEntityDao<OutMailUserSeting, Long> getEntityDao() 
	{
		return dao;
	}
    
	/**
	 * 测试外部邮箱的smtp，pop3/imap配置是否通过;
	 * @param outMailUserSeting
	 * @throws Exception
	 */
	public void testConnection(OutMailUserSeting outMailUserSeting) throws Exception{
		Mail mail=new Mail();
		mail.setSenderHost(outMailUserSeting.getSmtpHost());
		mail.setSenderPort(outMailUserSeting.getSmtpPort());
		mail.setReceiverType(outMailUserSeting.getMailType());
		mail.setSenderAddress(outMailUserSeting.getMailAddress());
		mail.setPassword(outMailUserSeting.getMailPass());
		mail.setSender(outMailUserSeting.getUserName());
		if("pop3".equals(outMailUserSeting.getMailType())){
			mail.setReceiverHost(outMailUserSeting.getPopHost());
			mail.setReceiverPort(outMailUserSeting.getPopPort());
		}else{
			mail.setReceiverHost(outMailUserSeting.getImapHost());
			mail.setReceiverPort(outMailUserSeting.getImapPort());
		}
		//测试发送邮件服务器连接
		mailService.connectSmtp(mail);
		//测试接收邮件服务器连接
		mailService.connectReciever(mail);
		
	}
	
	/**
	 * 设置默认邮箱
	 * @param outMailUserSeting
	 * @throws Exception
	 */
	public void setDefault(OutMailUserSeting outMailUserSeting)throws Exception{
		OutMailUserSeting mail=dao.getByIsDefault(ContextUtil.getCurrentUserId());
		mail.setIsDefault(0);
		dao.updateDefault(mail);
		dao.updateDefault(outMailUserSeting);
	}
	
	/**
	 * 验证设置的邮箱地址的唯一性
	 * @param outMailUserSeting
	 * @return
	 * @throws Exception
	 */
	public boolean isExistMail(long id,OutMailUserSeting outMailUserSeting)throws Exception{
		String address=outMailUserSeting.getMailAddress();
		int result=dao.getCountByAddress(address);
		if(id!=0L){
			OutMailUserSeting mail=getById(id);
			if(result!=0&&!address.equals(mail.getMailAddress()))
				return true;
			return false;
		}
		if(result!=0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据邮箱地址返回相应的邮箱配置实体
	 * @param address
	 * @return
	 */
	public OutMailUserSeting getMailByAddress(String address) {
		return dao.getMailByAddress(address);
	}
	
	/**
	 * 获取用户的默认邮箱
	 * @param userId
	 * @return
	 */
	public OutMailUserSeting getByIsDefault(long userId) {
		return dao.getByIsDefault(userId);
	}	
	
	/**
	 * 获取当前用户的邮箱列表
	 * @param userId
	 * @return
	 */
	public List<OutMailUserSeting> getMailByUserId(Long userId) {
		return dao.getMailByUserId(userId);
	}
	
	/**
	 * 获取当前用户的邮箱分页列表
	 * @param queryFilter
	 * @return
	 */
	public List<OutMailUserSeting> getAllByUserId(QueryFilter queryFilter) {
		return dao.getAllByUserId(queryFilter);
	}

	/**
	 * 获取用户的邮件数
	 * @param userId
	 * @return
	 */
	public int getCountByUserId(long userId) {
		return dao.getCountByUserId(userId);
	}
}

