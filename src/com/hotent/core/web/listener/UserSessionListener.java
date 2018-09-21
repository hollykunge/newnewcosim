package com.hotent.core.web.listener;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import com.hotent.core.model.OnlineUser;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;

/**
 * 监听用户登录事件和会话过期事件。
 * 管理在线用户情况。
 * @author csx
 *
 */
public class UserSessionListener implements HttpSessionAttributeListener{

	
	/**
	 * 进入系统,添加在线用户
	 */
	public void attributeAdded(HttpSessionBindingEvent event) {
		
		if("SPRING_SECURITY_LAST_USERNAME".equals(event.getName())){
			ISysUser user=ContextUtil.getCurrentUser();
			if(user==null){
				return;
			}
			Long userId=user.getUserId();
			if(!AppUtil.getOnlineUsers().containsKey(userId)){
				OnlineUser onlineUser=new OnlineUser();
				
				onlineUser.setUserId(user.getUserId());
				onlineUser.setUsername(user.getUsername());
				ISysOrg org=ContextUtil.getCurrentOrg();
				if(org!=null){
					onlineUser.setOrgId(org.getOrgId());
					onlineUser.setOrgName(org.getOrgName());
				}
				
				AppUtil.getOnlineUsers().put(user.getUserId(),onlineUser);
				
			}
		}
	}

	/**
	 * 退出系统，或者系统超时时+-删除在线用户
	 */
	public void attributeRemoved(HttpSessionBindingEvent event) {
		if("SPRING_SECURITY_LAST_USERNAME".equals(event.getName())){
			ISysUser user=ContextUtil.getCurrentUser();
			
			if(user!=null){
				AppUtil.getOnlineUsers().remove(user.getUserId());
			}
		
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println(event.getName());
		
	}

	


}
