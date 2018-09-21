package com.hotent.platform.service.form;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.hotent.core.util.StringUtil;



/**
 * 对象功能:控件Service类。
 * 用于在freemaker模版中使用。
 * 开发公司:
 * 开发人员:xwy
 * 创建时间:2012-02-10 10:48:16
 */
@Service
public class BpmFormControlService
{
	/**
	 * 显示主表字段值，用于模版中使用。
	 * @param fieldName	字段名称
	 * @param html		字段html
	 * @param model		数据对象
	 * @param permission	权限对象
	 * @return
	 */
	public String getField(String fieldName,  String html, Map<String, Map<String, Map>> model, Map<String, Map<String, String>> permission) {
		fieldName=fieldName.toLowerCase();
		//取得值
		Object object = model.get("main").get(fieldName);
		String value = "";
		if(object != null) {
			value = object.toString();
		}
		//可写权限
		if(permission.get("field") ==null ||  permission.get("field").get(fieldName) == null || "w".equals(permission.get("field").get(fieldName))) {
			return html.replaceAll("#value", value);
		} else if ("r".equals(permission.get("field").get(fieldName))) { 
			return value;
		} else {
			return "";
		}
	}
	
	/**
	 * 显示选择器的选择按钮
	 * @param fieldName 字段名称
	 * @param html		字段html
	 * @param model		数据对象
	 * @param permission 权限对象
	 * @return
	 */
	public String getLink(String fieldName,  String html, Map<String, Map<String, Map>> model, Map<String, Map<String, String>> permission){
		fieldName=fieldName.toLowerCase();
		//可写权限
		if(permission.get("field") ==null ||  permission.get("field").get(fieldName) == null || "w".equals(permission.get("field").get(fieldName))) {
			return html;
		} 
		else {
			return "";
		}
	}
	
	/**
	 * 显示主表字段中的隐藏字段
	 * @param fieldName 字段名称
	 * @param html      字段html
	 * @param model		数据对象
	 * @param permission 权限对象
	 * @return
	 */
	public String getHiddenField(String fieldName,  String html, Map<String, Map<String, Map>> model, Map<String, Map<String, String>> permission) {
		
		fieldName=fieldName.toLowerCase();
		//取得值
		Object object = model.get("main").get(fieldName);
		String value = "";
		if(object != null) {
			value = object.toString();
		}
		//可写权限
		if(permission.get("field") ==null ||  permission.get("field").get(fieldName) == null || "w".equals(permission.get("field").get(fieldName))) {
			return html.replaceAll("#value", value);
		}
		//只读和其他情况下不返回值
		else {
			return "";
		}
	}
	
	/**
	 * 获取子表权限。
	 * @param tableName
	 * @param permission
	 * @return
	 */
	public String getSubTablePermission(String tableName, Map<String, Map<String, String>> permission){
		tableName=tableName.toLowerCase();
		 Map map=(Map)permission.get("table");
		 String right="w";
		 if(map.containsKey(tableName)){
			 right=(String)map.get(tableName);
		 }
		return right;
	}
	
	/**
	 * 获取字段的权限
	 * @param fieldName 字段名称
	 * @param model  数据对象
	 * @param permission 权限对象
	 * @return
	 */
	public String getFieldRight(String fieldName,  Map<String, Map<String, String>> permission) {
		fieldName=fieldName.toLowerCase();
		//可写权限
		if(permission.get("field").get(fieldName) == null || "w".equals(permission.get("field").get(fieldName))) {
			return "w";
		} else if ("r".equals(permission.get("field").get(fieldName))) {
			return "r";
		} else {
			return "no";
		}
	}
	
	/**
	 * 将#value替换为字段的值。
	 * @param fieldName 字段名称
	 * @param html 字段html
	 * @param model 数据对象
	 * @return
	 */
	public String getFieldValue(String fieldName,  Map<String, Map<String, Map>> model) {
		fieldName=fieldName.toLowerCase();
		//取得值
		Object object = model.get("main").get(fieldName);
		String value = "";
		if(object != null) {
			value = object.toString();
		}
		return value;
	}
	
	
	
	
	/**
	 * 显示意见，用于模版中使用。
	 * @param opinionName  
	 * @param html       字段html
	 * @param model      数据对象
	 * @param permission 权限对象
	 * @return
	 */
	public String getOpinion(String opinionName, String html, Map<String, Map<String, Map>> model, Map<String, Map<String,String>> permission) {
		opinionName=opinionName.toLowerCase();
		
		Object object = model.get("opinion").get(opinionName);
		if(object==null){
			object=model.get("opinion").get("opinion:" +opinionName);
		}
		
		String value = "";
		if(object != null) {
			value = object.toString();
		}
		Map<String, String> opinion= permission.get("opinion");
		String rights=opinion.get(opinionName);
		if(rights==null){
			rights=opinion.get("opinion:" +opinionName);
		}
		
		if(opinion == null || rights==null || "w".equals(rights)) {
			return value +html;
			//return html.replaceAll("#value", value);
		} else if ("r".equals(rights)) {
			return value;
		} else {
			return "";
		}
	}
	
	/**
	 * 返回rdo和checkbox控件
	 * 
	 * @param fieldName
	 * @param html
	 * @param ctlVal
	 * @param model
	 * @param permission
	 * @return
	 */
	public String getRdoChkBox(String fieldName,String html, String ctlVal, Map<String, Map<String, Map>> model, Map<String, Map<String, String>> permission) {
		Object object = model.get("main").get(fieldName.toLowerCase());
		String value = "";
		if(object != null) {
			value = object.toString();
		}
		if(permission.get("field").get(fieldName) == null || "w".equals(permission.get("field").get(fieldName))) {
			html=html.replaceAll("(?s)disabled=\\s*\"?\\s*disabled\\s*\"?", "");
			html = getHtml(html, ctlVal, value);
			return html;
		} else if ("r".equals(permission.get("field").get(fieldName))) {
			html = getHtml(html, ctlVal, value);
			return html;
			
		} else {
			return "";
		}
	}
	
	/**
	 * 在子表中选中下checkbox和radio
	 * @param fieldName
	 * @param html
	 * @param ctlVal
	 * @param table
	 * @return
	 */
	public String getRdoChkBox(String fieldName,String html, String ctlVal,Map<String, Object> table) {
		String value =(String) table.get(fieldName.toLowerCase());
		
		html = getHtml(html, ctlVal, value);
		return html;
		
	}
	
	/**
	 * 替换html。
	 * @param html	html的代码
	 * @param ctlVal	控件代表的值。
	 * @param value		当前字段的值。
	 * @return
	 */
	private String getHtml(String html, String ctlVal, String value) {
		//还没有选择任何的字段
		if(StringUtil.isEmpty(value)){
			html=html.replaceAll("chk=\"?1\"?", "");
		}
		else{
			html=html.replace("checked=\"checked\"","");
			if(value.contains(ctlVal)){
				html=html.replaceAll("chk=\"?1\"?","checked=\"checked\"");
			}
			else{
				html=html.replaceAll("chk=\"?1\"?", "");
			}
		}
		return html;
	}
}
