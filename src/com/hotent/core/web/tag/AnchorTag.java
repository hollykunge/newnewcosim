package com.hotent.core.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SubSystemUtil;
 


/**
 * 功能权限标签。
 * <br>
 * 功能：这个标签配合权限分配，可以实现页面上的按钮是否可以点击操作，将权限操作控制到按钮。
 * <ul>
 *  <li>标签中 有个别名属性，系统根据该别名控制当前用户是否有该操作的的权限。</li>
 *  <li>标签的使用需要在tld文件和web.xml中进行配置。</li>
 *  <li>权限标签的写法:<f:a alias="site_add" css="add" href="addSite1.ht">;添加&lt;/f:a&gt;</li>
 * </ul>
 */
@SuppressWarnings("serial")
public class AnchorTag extends BodyTagSupport {
	
	private Log logger = LogFactory.getLog(AnchorTag.class);
	
	private String css;
	private String alias;
	private String name;
	private String id;
	private String href;
	private String action;
	private String onclick;
	private String target;

	public void setTarget(String target) {
		this.target = target;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setAction(String action) {
		this.action = action;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_BUFFERED;
	}
	
	private String getOutput() throws Exception
	{
		Long systemId=SubSystemUtil.getCurrentSystemId((HttpServletRequest) pageContext.getRequest());
		//判断别名是否可以访问。
		boolean canAccess=SecurityUtil.hasFuncPermission(systemId, alias);
		
		String body = this.getBodyContent().getString();
		StringBuffer content = new StringBuffer("<a ");
		if(id != null) {
			content.append("id=\"" + id + "\" ");
		}
		if(name != null) {
			content.append("name=\"" + name + "\" ");
		}		
		if(target != null){
			content.append("target=\"" + target + "\" ");
		}
		
		//可以访问的情况。
		if(canAccess) {
			if(css != null) {
				content.append(" class=\"" + css + "\" ");
			}
			if(href != null) {
				content.append(" href=\"" + href + "\" ");
			}
			if(action != null) {
				content.append(" action=\"" + action + "\" ");
			}
		} 
		else {
			if(css != null) {
				content.append(" class=\"" + css + " disabled\" ");
			} else {
				content.append(" class=\"disabled\" ");
			}
		}
		if(onclick != null) {
			content.append("onclick=\"" + onclick + "\" ");
		}
		content.append(">");
		content.append(body);
		content.append("</a>");
		
		return content.toString();
	}
	

	public int doEndTag() throws JspTagException {

		try {
			String str = getOutput();
			pageContext.getOut().print(str);
		} catch (Exception e) {
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}

}
