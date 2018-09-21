package com.hotent.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 字符串操作类
 * 
 */
public class StringUtil {
	/**
	 * 对字符串 escape 编码
	 * 
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (i = 0; i < src.length(); i++) {

			j = src.charAt(i);

			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}
	
	public static String replaceVariable(String template,Map<String,String> map) throws Exception
	{
		Pattern regex = Pattern.compile("\\{(.*?)\\}");
		Matcher regexMatcher = regex.matcher(template);
		while (regexMatcher.find()) {
			String key=regexMatcher.group(1);
			String toReplace=regexMatcher.group(0);
			String value=(String)map.get(key);
			if(value!=null)
				template=template.replace(toReplace, value);
			else
				throw new Exception("没有找到["+ key +"]对应的变量值，请检查表变量配置!");
		}  
		
		return template;
	}

	/**
	 * 对编码的字符串解码
	 * 
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 判断指定的内容是否存在
	 * @param content 内容
	 * @param begin 开始标签
	 * @param end 结束标签
	 * @return
	 */
	public static boolean isExist(String content,String begin,String end)
	{
		String tmp=content.toLowerCase();
		begin=begin.toLowerCase();
		end=end.toLowerCase();
		int beginIndex=tmp.indexOf(begin);
		int endIndex=tmp.indexOf(end);
		if(beginIndex!=-1  && endIndex!=-1 && beginIndex<endIndex)
			return true;
		return false;
	}

	/**
	 * 去掉前面的指定字符
	 * 
	 * @param toTrim
	 * @param trimStr
	 * @return
	 */
	public static String trimPrefix(String toTrim, String trimStr) {
		while (toTrim.startsWith(trimStr)) {
			toTrim = toTrim.substring(trimStr.length());
		}
		return toTrim;
	}

	/**
	 * 删除后面指定的字符
	 * 
	 * @param toTrim
	 * @param trimStr
	 * @return
	 */
	public static String trimSufffix(String toTrim, String trimStr) {
		while (toTrim.endsWith(trimStr)) {
			toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
		}
		return toTrim;
	}

	/**
	 * 删除指定的字符
	 * 
	 * @param toTrim
	 * @param trimStr
	 * @return
	 */
	public static String trim(String toTrim, String trimStr) {
		return trimSufffix(trimPrefix(toTrim, trimStr), trimStr);
	}

	/**
	 * 编码html
	 * 
	 * @param content
	 * @return
	 */
	public static String escapeHtml(String content) {
		return StringEscapeUtils.escapeHtml(content);
	}

	/**
	 * 反编码html
	 * 
	 * @param content
	 * @return
	 */
	public static String unescapeHtml(String content) {
		return StringEscapeUtils.unescapeHtml(content);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null)
			return true;
		if (str.trim().equals(""))
			return true;
		return false;
	}
	
	/**
	 * 判断字符串非空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static String replaceVariable(String template,String repaceStr) 
	{
		Pattern regex = Pattern.compile("\\{(.*?)\\}");
		Matcher regexMatcher = regex.matcher(template);
		if (regexMatcher.find()) {
			String toReplace=regexMatcher.group(0);
			template=template.replace(toReplace, repaceStr);
		}
		return template;
	}

	/**
	 * 截取字符串 中文为两个字节，英文为一个字节。 两个英文为一个中文。
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String subString(String str, int len) {
		int strLen = str.length();
		if (strLen < len)
			return str;
		char[] chars = str.toCharArray();
		int cnLen = len * 2;
		String tmp = "";
		int iLen = 0;
		for (int i = 0; i < chars.length; i++) {
			int iChar = (int) chars[i];
			if (iChar <= 128)
				iLen = iLen + 1;
			else
				iLen = iLen + 2;
			if (iLen >= cnLen)
				break;
			tmp += String.valueOf(chars[i]);
		}
		return tmp;
	}

	/**
	 * 判读输入字符是否是数字
	 * @param s
	 * @return
	 */
	public static boolean isNumberic(String s) {
		if(StringUtils.isEmpty(s)) return false;
		boolean rtn=validByRegex("^[-+]{0,1}\\d*\\.{0,1}\\d+$",s);
		if(rtn) return true;
		
		return validByRegex("^0[x|X][\\da-eA-E]+$",s);
	}
	
	/**
	 * 是否是整数。
	 * @param s
	 * @return
	 */
	public static boolean isInteger(String s)
	{
		boolean rtn=validByRegex("^[-+]{0,1}\\d*$",s);
		return rtn;
		
	}
	/**
	 * 是否是电子邮箱
	 * @param s
	 * @return
	 */
	public static boolean isEmail(String s)
	{
		boolean rtn=validByRegex("(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*",s);		
		return rtn;
	}
	
	/**
	 * 手机号码
	 * @param s
	 * @return
	 */
	public static boolean isMobile(String s)
	{
		boolean rtn=validByRegex("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$",s);
		return rtn;
	}
	/**
	 * 电话号码
	 * @param 
	 * @return
	 */
	public static boolean isPhone(String s)
	{
		boolean rtn=validByRegex("(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?",s);
		return rtn;
	}
	/**
	 * 邮编
	 * @param s
	 * @return
	 */
	public static boolean isZip(String s)
	{
		boolean rtn=validByRegex("^[0-9]{6}$",s);
		return rtn;
	}
	
	
	/**
	 * qq号码
	 * @param s
	 * @return
	 */
	public static boolean isQq(String s)
	{
		boolean rtn=validByRegex("^[1-9]\\d{4,9}$",s);
		return rtn;
	}
	
	/**
	 * ip地址
	 * @param s
	 * @return
	 */
	public static boolean isIp(String s)
	{
		boolean rtn=validByRegex("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$",s);
		return rtn;
	}
	/**
	 * 判断是否中文
	 * @param s
	 * @return
	 */
	public static boolean isChinese(String s)
	{
		boolean rtn=validByRegex("^[\u4e00-\u9fa5]+$",s);
		return rtn;
	}
	
	/**
	 * 字符和数字
	 * @param s
	 * @return
	 */
	public static boolean isChrNum(String s)
	{
		boolean rtn=validByRegex("^([a-zA-Z0-9]+)$",s);
		return rtn;
	}
	
	
	
	/**
	 * 判断是否是URL
	 * @param url
	 * @return
	 */
	public static boolean isUrl(String url)
	{
		return validByRegex("(http://|https://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?",url);
	}
	
	/**
	 * 使用正则表达式验证。
	 * @param regex
	 * @param input
	 * @return
	 */
	public static boolean validByRegex(String regex,String input)
	{
		Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE );   
		Matcher regexMatcher = p.matcher(input);
		return regexMatcher.find();
	}
	
	
	
	/**
	 * 判断某个字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 把字符串的第一个字母转为大写
	 * @param newStr
	 * @return
	 */
	public static String makeFirstLetterUpperCase(String newStr) {
		if (newStr.length() == 0)
			return newStr;

		char[] oneChar = new char[1];
		oneChar[0] = newStr.charAt(0);
		String firstChar = new String(oneChar);
		return (firstChar.toUpperCase() + newStr.substring(1));
	}
	
	/**
	 * 把字符串的第一字每转为小写
	 * @param newStr
	 * @return
	 */
	public static String makeFirstLetterLowerCase(String newStr) {
		if (newStr.length() == 0)
			return newStr;

		char[] oneChar = new char[1];
		oneChar[0] = newStr.charAt(0);
		String firstChar = new String(oneChar);
		return (firstChar.toLowerCase() + newStr.substring(1));
	}
	
	/**
	 * 格式化带参数的字符串，如
	 *  /param/detail.ht?a={0}&b={1}
	 * @param message
	 * @param args
	 * @return
	 */
	public static String formatParamMsg(String message,Object... args)
	{
		for(int i=0;i<args.length;i++)
		{
			message=message.replace("{" + i + "}", args[i].toString());
		}
		return message;
	}
	
	/**
	 * 格式化如下字符串  http://www.bac.com?a=${a}&b=${b}
	 * @param message
	 * @param params
	 */
	public static String formatParamMsg(String message,Map params)
	{
		if(params==null) return message;
		Iterator<String> keyIts=params.keySet().iterator();
		while(keyIts.hasNext()){
			String key=keyIts.next();
			Object val=params.get(key);
			if(val!=null){
				message=message.replace("${"+key+"}", val.toString());
			}
		}
		return message;
	}

	/**
	 * 简单的字符串格式化，性能较好。支持不多于10个占位符，从%1开始计算，数目可变。参数类型可以是字符串、Integer、Object，
	 * 甚至int等基本类型
	 * 、以及null，但只是简单的调用toString()，较复杂的情况用String.format()。每个参数可以在表达式出现多次。
	 * 
	 * @param msgWithFormat
	 * @param autoQuote
	 * @param args
	 * @return
	 */
	public static StringBuilder formatMsg(CharSequence msgWithFormat,boolean autoQuote, Object... args) {
		int argsLen = args.length;
		boolean markFound = false;

		StringBuilder sb = new StringBuilder(msgWithFormat);

		if (argsLen > 0) {
			for (int i = 0; i < argsLen; i++) {
				String flag = "%" + (i + 1);
				int idx = sb.indexOf(flag);
				// 支持多次出现、替换的代码
				while (idx >= 0) {
					markFound = true;
					sb.replace(idx, idx + 2, toString(args[i], autoQuote));
					idx = sb.indexOf(flag);
				}
			}

			if (args[argsLen - 1] instanceof Throwable) {
				StringWriter sw = new StringWriter();
				((Throwable) args[argsLen - 1]).printStackTrace(new PrintWriter(sw));
				sb.append("\n").append(sw.toString());
			} else if (argsLen == 1 && !markFound) {
				sb.append(args[argsLen - 1].toString());
			}
		}
		return sb;
	}
	
	public static StringBuilder formatMsg(String msgWithFormat, Object... args) {
		return formatMsg(new StringBuilder(msgWithFormat), true, args);
	}

	public static String toString(Object obj, boolean autoQuote) {
		StringBuilder sb = new StringBuilder();
		if (obj == null) {
			sb.append("NULL");
		} else {
			if (obj instanceof Object[]) {
				for (int i = 0; i < ((Object[]) obj).length; i++) {
					sb.append(((Object[]) obj)[i]).append(", ");
				}
				if (sb.length() > 0) {
					sb.delete(sb.length() - 2, sb.length());
				}
			} else {
				sb.append(obj.toString());
			}
		}
		if (autoQuote
				&& sb.length() > 0
				&& !((sb.charAt(0) == '[' && sb.charAt(sb.length() - 1) == ']') || (sb
						.charAt(0) == '{' && sb.charAt(sb.length() - 1) == '}'))) {
			sb.insert(0, "[").append("]");
		}
		return sb.toString();
	}
    
	public static String returnSpace(String str)
	{
		String space="";
		if(!str.isEmpty())
		{
			String path[]=str.split("\\.");
			for(int i=0;i<path.length-1;i++){
				 space+="&nbsp;&emsp;";	
			}
		}
		return space;
	}
	
	
	
	
	
	/**
	 * 输出明文按sha-256加密后的密文
	 * @param inputStr 明文
	 * @return
	 */
	public static synchronized String encryptSha256(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte digest[] = md.digest(inputStr.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(digest));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static synchronized String encryptMd5(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputStr.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(Integer.toHexString((int) (b & 0xff)));
			}
			
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 把字符串数组转成带，的字符串
	 * @param arr
	 * @return 返回字符串，格式如1,2,3
	 */
	public static String getArrayAsString(List<String> arr){
		if(arr==null || arr.size()==0) return "";
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<arr.size();i++){
			if(i>0)sb.append(",");
			sb.append(arr.get(i));
		}
		return sb.toString();
	}
	/**
	 * 把字符串数组转成带，的字符串
	 * @param arr
	 * @return 返回字符串，格式如1,2,3
	 */
	public static String getArrayAsString(String[]arr){
		if(arr==null || arr.length==0) return "";
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<arr.length;i++){
			if(i>0)sb.append(",");
			sb.append(arr[i]);
		}
		return sb.toString();
	}
	
	/**
	 * 把Set转成带，的字符串
	 * @param Set
	 * @return 返回字符串，格式如1,2,3
	 */
	public static String getSetAsString(Set set){
		if(set==null || set.size()==0 ) return "";
		StringBuffer sb=new StringBuffer();
		int i=0;
		Iterator it=set.iterator();
		while(it.hasNext()){
			if(i++>0) sb.append(",");
			sb.append(it.next().toString());
		}
		return sb.toString();
	}
	

	
	
	/**
	 * 将人名币转成大写。
	 * @param amount
	 * @return
	 */
	public static String hangeToBig(double value) 
    { 
        char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示 
        char[] vunit = { '万', '亿' }; // 段名表示 
        char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示 
        long midVal = (long) (value * 100); // 转化成整形 
        String valStr = String.valueOf(midVal); // 转化成字符串 

        String head = valStr.substring(0, valStr.length() - 2); // 取整数部分 
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分 

        String prefix = ""; // 整数部分转化的结果 
        String suffix = ""; // 小数部分转化的结果 
        // 处理小数点后面的数 
        if (rail.equals("00")) 
        { // 如果小数部分为0 
            suffix = "整"; 
        } 
        else 
        { 
            suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来 
        } 
        // 处理小数点前面的数 
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组 
        char zero = '0'; // 标志'0'表示出现过0 
        byte zeroSerNum = 0; // 连续出现0的次数 
        for (int i = 0; i < chDig.length; i++) 
        { // 循环处理每个数字 
            int idx = (chDig.length - i - 1) % 4; // 取段内位置 
            int vidx = (chDig.length - i - 1) / 4; // 取段位置 
            if (chDig[i] == '0') 
            { // 如果当前字符是0 
                zeroSerNum++; // 连续0次数递增 
                if (zero == '0') 
                { // 标志 
                    zero = digit[0]; 
                } 
                else if (idx == 0 && vidx > 0 && zeroSerNum < 4) 
                { 
                    prefix += vunit[vidx - 1]; 
                    zero = '0'; 
                } 
                continue; 
            } 
            zeroSerNum = 0; // 连续0次数清零 
            if (zero != '0') 
            { // 如果标志不为0,则加上,例如万,亿什么的 
                prefix += zero; 
                zero = '0'; 
            } 
            prefix += digit[chDig[i] - '0']; // 转化该数字表示 
            if (idx > 0) 
                prefix += hunit[idx - 1]; 
            if (idx == 0 && vidx > 0) 
            { 
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿 
            } 
        } 

        if (prefix.length() > 0) 
            prefix += '圆'; // 如果整数部分存在,则有圆的字样 
        return prefix + suffix; // 返回正确表示 
    }
	
	/**
	 * 替换json特殊字符转码
	 * @param str
	 * @return
	 */
	public static String jsonUnescape(String str){
		return str.replace("&quot;", "\"").replace("&nuot;", "\n");
	}		
	
	/**
	 * HTML实体编码转成普通的编码
	 * 
	 * @param dataStr
	 * @return
	 */
	public static String htmlEntityToString(String dataStr) {
		dataStr=dataStr.replace("&apos;", "'")
		.replace("&quot;", "\"")
		.replace("&gt;", ">")
		.replace("&lt;", "<")
		.replace("&amp;", "&");
		
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		
		while (start > -1) {
			int system = 10;// 进制
			if (start == 0) {
				int t = dataStr.indexOf("&#");
				if (start != t){
					start = t;
				}
				if(start>0){
					buffer.append(dataStr.substring(0, start));
				}
			}
			end = dataStr.indexOf(";", start + 2);
			String charStr = "";
			if (end != -1) {
				charStr = dataStr.substring(start + 2, end);
				// 判断进制
				char s = charStr.charAt(0);
				if (s == 'x' || s == 'X') {
					system = 16;
					charStr = charStr.substring(1);
				}
			}
			// 转换
			try {
				char letter = (char) Integer.parseInt(charStr, system);
				buffer.append(new Character(letter).toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			// 处理当前unicode字符到下一个unicode字符之间的非unicode字符
			start = dataStr.indexOf("&#", end);
			if (start - end > 1) {
				buffer.append(dataStr.substring(end + 1, start));
			}

			// 处理最后面的非unicode字符
			if (start == -1) {
				int length = dataStr.length();
				if (end + 1 != length) {
					buffer.append(dataStr.substring(end + 1, length));
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * 把String转成html实体字符
	 * 
	 * @param str
	 * @return
	 */
	public static String stringToHtmlEntity(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			switch (c) {
			case 0x0A:
				sb.append(c);
				break;

			case '<':
				sb.append("&lt;");
				break;

			case '>':
				sb.append("&gt;");
				break;

			case '&':
				sb.append("&amp;");
				break;

			case '\'':
				sb.append("&apos;");
				break;

			case '"':
				sb.append("&quot;");
				break;

			default:
				if ((c < ' ') || (c > 0x7E)) {
					sb.append("&#x");
					sb.append(Integer.toString(c, 16));
					sb.append(';');
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}
	

	public static void main(String[]args){
		String str="${scriptImpl.getStartUserPos(startUser)==\"&#37096;&#38376;&#32463;&#29702;\"}";
		System.out.println(htmlEntityToString(str));
	}
	
	/**
	 * 字符串 编码转换
	 * @param str 字符串
	 * @param from 原來的編碼
	 * @param to 轉換后的編碼
	 * @return
	 */
	public static String encodingString(String str, String from, String to) {
		String result = str;
		try {
			result = new String(str.getBytes(from), to);
		} catch (Exception e) {
			result = str;
		}
		return result;
	}

}
