package com.hotent.platform.controller.mail.impl;

import javax.annotation.Resource;

import com.hotent.platform.controller.mail.IMailHandler;
import com.hotent.platform.dao.mail.OutMailDao;
import com.hotent.platform.model.mail.OutMail;
/**
 * 对象功能:外部邮件结合数据库处理类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:james
 * 创建时间:2012-04-27 10:39:29
 */
public class MailHandler implements IMailHandler {
	static Long MAIL_NO_READ=0L;//未读
	static Long MAIL_IS_READ=1L;//已读
	static Integer MAIL_IS_RECEIVE = 1;// 收件箱
	static Integer MAIL_IS_SEND = 2;// 发件箱
	static Integer MAIL_IS_DRAFT = 3;// 草稿箱
	static Integer MAIL_IS_DELETE = 4;// 删除箱
	@Resource
	private OutMailDao dao;
	
	/**
	 * 判断系统是否在存在邮件UID
	 */
	public boolean isUidExist(String uid) {
		return dao.getByEmailId(uid);
	}
	
	/**
	 * 保存和同步邮件时保存数据到数据库中
	 */
	public void saveEmail(OutMail outMail) throws  Exception {
		dao.add(outMail);
	}
}


