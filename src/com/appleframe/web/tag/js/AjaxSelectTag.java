package com.appleframe.web.tag.js;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.appleframe.web.tag.HtmlTag;

/**
 * 从数据库中直接读取成select,
 * 前提是必须配置数据库模板
 */
@SuppressWarnings("unchecked")
public class AjaxSelectTag extends HtmlTag{
	/** 源元素 **/
	private String srcElement;
	
	/** 目标元素 **/
	private String targetElement; 
	
	/** post地址 **/
	private String url;
	
	/** Option的Value值 **/
	private String value = "itemValue";
	
	/** Option的Text值 **/
	private String text = "itemName";
		
	/** 选中的值 **/
	private String selectedValue = "";
	
	private static final long serialVersionUID = 141243768956987958L;
	
	public int doStartTag() throws JspException{
		String str = "";
		str = "<script>" +
			  "$(function(){" +
    		  		"$('#" + srcElement + "').change(function(){" +
    		  			"$.ajax({" +
	    		  			"type : 'post'," +
	    		  			"dataType : 'json'," +
	    		  			"url : '" + url + "'," +
	    		  			"data : {value : $(this).val() }," +
	    		  			"success : function(dics){" +
	    					"var rows=dics;" +
//	    		  			"if(rows.length==0){" +
//	    		  				"$('#" + targetElement + "').parent().append('<input type=\"text\"/>');" +
//	    		  			"}" +		    				
//	    		  			"$('#" + targetElement + "').html('<option value=\"\" selected>==请选择==</option>');" +
							"$('#" + targetElement + "').html('');" +
		    				"for(var i=0; i<rows.length; i++){" +
		    					"var s = '';";
								if(!selectedValue.equals("")){
									str += "if(rows[i]." + value + "==" + selectedValue + "){" +
										"s=selected" +
									"}";
								}
								str += "$('#" + targetElement + "').append('<option ' + s + 'value=\"' +  rows[i]." + value + " + '\">' + rows[i]." + text + " + '</option>');" +
		    				"}" +
		    				"$('#" + targetElement + "').trigger('change');" +
	    				"}" +
	    			"});" +
	    		"});" +
    		"})" +
    		"</script>";
		try {
			pageContext.getOut().println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	public int doEndTag() throws JspException{
		selectedValue = "";
		return EVAL_PAGE;
	}

	public String getSrcElement() {
		return srcElement;
	}

	public void setSrcElement(String srcElement) {
		this.srcElement = srcElement;
	}

	public String getTargetElement() {
		return targetElement;
	}

	public void setTargetElement(String targetElement) {
		this.targetElement = targetElement;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
}
