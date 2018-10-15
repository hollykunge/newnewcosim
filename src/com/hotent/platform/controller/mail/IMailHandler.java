package com.hotent.platform.controller.mail;

import com.hotent.platform.model.mail.OutMail;

public interface IMailHandler {
	/**
	 * 判断系统是否在存在邮件UID
	 * @param uid
	 * @return
	 */
	public boolean isUidExist(String uid);
	/**
	 * 保存和同步邮件时保存数据到数据库中
	 * @param outMail
	 * @throws Exception
	 */
	public void saveEmail(OutMail outMail) throws  Exception	;
}
