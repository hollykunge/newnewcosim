/**
 * 
 */
package com.casic.cloud.pub.util;

/**
 * 从前台转义
 * @author Administrator
 *
 */
public class HtmlUtil {

	/**
	 * 将textarea传入的值转化成html代码
	 * @param text
	 * @return
	 */
	public static String encode(String text){
		if(text==null)
			return null;
		
		text = text.replaceAll(">", "&gt;");
		text = text.replaceAll("<", "&lt;");
		text = text.replaceAll("\n", "<br/>");
		
		return text;
	}
}
