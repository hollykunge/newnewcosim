package com.hotent.platform.web.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import com.hotent.core.cache.ICache;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.platform.auth.ISysOrg;

/**
 * 对session进行监控，设置当前线程的组织数据。
 * @author ray
 *
 */
public class CurrentProfileListener implements HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		if(ContextUtil.CurrentOrg.equals(event.getName())){
			ContextUtil.setCurrentOrg((ISysOrg) event.getValue());
			ICache iCache=(ICache)AppUtil.getBean(ICache.class);
			String userId=ContextUtil.CurrentOrg + ContextUtil.getCurrentUserId();
			iCache.add(userId, event.getValue());
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		if(ContextUtil.CurrentOrg.equals(event.getName())){
			ContextUtil.cleanCurrentOrg();
			ICache iCache=(ICache)AppUtil.getBean(ICache.class);			
			String userId=ContextUtil.CurrentOrg + ContextUtil.getCurrentUserId();
			iCache.delByKey(userId);
		}
	}

	/**
	 * 替换session。需要从session中重新获取。
	 * 不能使用event.getValue();
	 */
	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		if(ContextUtil.CurrentOrg.equals(event.getName())){
			HttpSession session=event.getSession();
			ISysOrg sysOrg=(ISysOrg) session.getAttribute(ContextUtil.CurrentOrg );
			ContextUtil.setCurrentOrg(sysOrg);
			ICache iCache=(ICache)AppUtil.getBean(ICache.class);
			String userId=ContextUtil.CurrentOrg + ContextUtil.getCurrentUserId();
			iCache.add(userId, sysOrg);
		}
	}

}
