/**
 * 
 */
package com.casic.cloud.pub.util;

/**
 * 判断邮箱
 * @author Administrator
 *
 */
public class MailUtil {

	/**
	 * 跳转到邮箱登录页面
	 * @param text
	 * @return
	 */
	public static String toMailLogin(String email){
		if(email==null)
			return "";
		if(email.contains("@163"))
			return "http://mail.163.com";
		if(email.contains("@126"))
			return "http://mail.126.com";
		if(email.contains("@sina"))
			return "http://mail.sina.com";
		if(email.contains("@sohu"))
			return "http://mail.sohu.com";
		if(email.contains("@gmail"))
			return "http://mail.google.com";
		if(email.contains("@hotmail"))
			return "http://mail.hotmail.com";
		if(email.contains("@qq"))
			return "http://mail.qq.com";
		if(email.contains("@gmail"))
			return "http://mail.google.com";
		
		return "";
	}
}
