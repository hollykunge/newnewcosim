package com.hotent.platform.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.lang.StringUtils;
import com.hotent.core.util.AppUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;

public class UserNameTag extends BodyTagSupport {
	
	public int doStartTag() throws JspTagException {
		return EVAL_BODY_BUFFERED;
	}
	
	private Long userId=0L;
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	private String getUserName(){
		if(this.userId==0) return "暂无";
		HttpServletRequest request=(HttpServletRequest) pageContext.getRequest() ;
		//通过用户Id获取用户名称
		SysUserService sysUserService=(SysUserService)AppUtil.getBean("sysUserService");
		ISysUser user=sysUserService.getById(new Long(userId));
		String str="暂无";
		if(user!=null){
			if(StringUtils.isEmpty(user.getFullname())){
				str="<img src='" + request.getContextPath() + "/styles/default/images/bpm/user-16.png'>&nbsp;"+user.getAccount();
			}else{
				str="<img src='" + request.getContextPath() + "/styles/default/images/bpm/user-16.png'>&nbsp;"+user.getFullname();
			}
		}
		return str;
	}
	
	public int doEndTag() throws JspTagException {

		try {
			String str = getUserName();
			pageContext.getOut().print(str);
		} catch (Exception e) {
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}
}
