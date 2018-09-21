package com.casic.cloud.casSessionListener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.jasig.cas.client.validation.Assertion;

public class CasSessionListener implements HttpSessionAttributeListener,HttpSessionListener{

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		HttpSession session = se.getSession();
		Object object = session.getAttribute("_const_cas_assertion_");
		if (object instanceof Assertion && object != null) {
//			System.out.println("add!!!!!!!!!!!!!!!");
//			session.setMaxInactiveInterval(10);
		}
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
//		HttpSession session = se.getSession();
//		Object object = session.getAttribute("_const_cas_assertion_");
//		System.out.println(se.getName());
//		if (object instanceof Assertion && object != null) {
//			session.invalidate();
//		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println(se.getSession()+":::::::::::::session create");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println(se.getSession()+":::::::::::::session过期了");
		
	}

}
