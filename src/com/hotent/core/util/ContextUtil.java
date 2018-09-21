package com.hotent.core.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hotent.core.cache.ICache;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.bpm.thread.TaskThreadService;
import com.hotent.platform.service.bpm.thread.TaskUserAssignService;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysPaurService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 取得当前用户登录时的上下文环境，一般用于获取当前登录的用户
 * @author csx
 *
 */
public class ContextUtil {
	private static Logger logger=LoggerFactory.getLogger(ContextUtil.class);
	private static ThreadLocal<ISysUser> curUser=new ThreadLocal<ISysUser>();
	//当前组织。
	private static ThreadLocal<ISysOrg> curOrg=new ThreadLocal<ISysOrg>();
	
	//当前组织
	private static ThreadLocal<SysOrgInfo> curOrgInfo=new ThreadLocal<SysOrgInfo>();
	
	public static final String CurrentOrg="CurrentOrg_";
	
	/**
	 * 取得当前登录的用户。
	 * <pre>
	 * 1.首先尝试从线程变量中获取获取当前用户，线程变量的是通过setCurrentUserAccount方法进行设置。
	 * 
	 * 2.如果没有获取到则从登录用户中进行获取。
	 * <pre>
	 * @return
	 */
	public static ISysUser getCurrentUser(){
		//通过setCurrentUserAccount设置的用户。
		if(curUser.get()!=null){
			ISysUser user=curUser.get();
			return user;
		}
		ISysUser sysUser=null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication auth = securityContext.getAuthentication();
            if (auth != null) {
                Object principal = auth.getPrincipal();
                if (principal instanceof ISysUser) {
                	sysUser=(ISysUser)principal;
                }
            } 
        }
        
        return sysUser;
	}
	/**
	 * 取得当前用户的ID
	 * @return
	 */
	public static Long getCurrentUserId(){
		ISysUser curUser=getCurrentUser();
		if(curUser!=null) return curUser.getUserId();
		return null;
	}
	/**
	 * 设置当前用户账号
	 * @param curUserAccount
	 */
	public static void setCurrentUserAccount(String account){
		SysUserService sysUserService=(SysUserService)AppUtil.getBean("sysUserService");
		ISysUser sysUser=sysUserService.getByAccount(account);
		curUser.set(sysUser);
	}
	/**
	 * 设置当前用户
	 * @param sysUser
	 */
	public static void setCurrentUser(ISysUser sysUser){
		curUser.set(sysUser);
	}
	
	/**
	 * 设置当前组织。
	 * @param orgId
	 */
	public static void setCurrentOrg(Long orgId){
		SysOrgService sysOrgService=(SysOrgService)AppUtil.getBean("sysOrgService");
		ISysOrg sysOrg=sysOrgService.getById(orgId);
		HttpServletRequest request= RequestUtil.getHttpServletRequest();
		HttpServletResponse response= RequestUtil.getHttpServletResponse();
		HttpSession session= request.getSession();
		saveSessionCookie(sysOrg,request,response,session);
	}
	
	/**
	 * 从当前session中设置当前人的组织数据。
	 * <pre>
	 * 首先判断session中是否有组织机构数据。
	 * 1.如果获取为空。
	 * 	    1.从coolie中获取当前组织Id。
	 *      
	 *      判断此id是否为空。
	 *      1.为空的情况。
	 *      	根据当前用户id从数据库获取默认的组织对象.
	 *      
	 *      2.不为空则根据组织ID获取
	 *      	根据组织ID获取组织对象
	 * 2.获取不为空。
	 * 
	 * 判定组织对象是否为空
	 *  不为空则加入到session和cookie中。
	 *  并设置当前session线程变量和缓存。
	 * </pre>
	 */
	public static ISysOrg getCurrentOrgFromSession(){
		HttpServletRequest request= RequestUtil.getHttpServletRequest();
		HttpServletResponse response= RequestUtil.getHttpServletResponse();
		HttpSession session= request.getSession();
		Long userId=getCurrentUserId();
		SysOrgService sysOrgService=(SysOrgService)AppUtil.getBean("sysOrgService");
		//从session中获取。
		ISysOrg sysOrg=(ISysOrg)session.getAttribute(ContextUtil.CurrentOrg);
		if(sysOrg==null){
			//从cookie中获取。
			String currentOrgId= CookieUitl.getValueByName(ContextUtil.CurrentOrg, request);
			if(StringUtil.isEmpty(currentOrgId)) {
				sysOrg = sysOrgService.getDefaultOrgByUserId(userId);
				if(sysOrg!=null){
					CookieUitl.addCookie(ContextUtil.CurrentOrg, sysOrg.getOrgId().toString(), request, response);
				}
			}
			else{
				//从数据库中获取。
				Long orgId=Long.parseLong(currentOrgId);
				sysOrg= sysOrgService.getById(orgId);
			}
		}
		if(sysOrg!=null){
			//设置cookie和sesion。
			saveSessionCookie(sysOrg,request,response,session);
			ContextUtil.setCurrentOrg(sysOrg);
		}
		return sysOrg;
	}
	
	public static SysOrgInfo getCurrentOrgInfoFromSession(){
//		SysOrgInfo sysOrgInfo = curOrgInfo.get();
//		if(sysOrgInfo==null){
//			ISysOrg sysOrg = getCurrentOrgFromSession();
//			SysOrgService sysOrgService=(SysOrgService)AppUtil.getBean("sysOrgService");
//			if(sysOrg!=null){
//				SysOrgInfoService sysOrgInfoService=(SysOrgInfoService)AppUtil.getBean("sysOrgInfoService");
//				sysOrgInfo = sysOrgInfoService.getById(sysOrg.getSn());
//			}
//			if(sysOrgInfo!=null){
//				curOrgInfo.set(sysOrgInfo);
//			}
//		}
		SysOrgInfo sysOrgInfo = null;
		ISysOrg sysOrg = getCurrentOrgFromSession();
		if(sysOrg!=null){
			SysOrgInfoService sysOrgInfoService=(SysOrgInfoService)AppUtil.getBean("sysOrgInfoService");
			sysOrgInfo = sysOrgInfoService.getById(sysOrg.getSn());
		}
		return sysOrgInfo;
	}
	
	/**
	 * 获取当前组织。
	 * <pre>
	 * 从线程中获取当前用户的组织。
	 * </pre>
	 * @return
	 */
	public static ISysOrg getCurrentOrg(){
		ISysOrg sysOrg = curOrg.get();
		return sysOrg;
	}
	
	/**
	 *  获取当前组织ID
	 * @return
	 */
	public static Long getCurrentOrgId(){
		ISysOrg sysOrg = getCurrentOrg();
		if(sysOrg!=null) return sysOrg.getOrgId();
		return null;
	}
	
	/**
	 * 取当前用户皮肤设置。
	 * @return
	 */
	public static String getCurrentUserSkin(){
		String skinStyle="default";
		HttpServletRequest request=RequestUtil.getHttpServletRequest();
		HttpSession session=request.getSession();
		String skin=(String)session.getAttribute("skinStyle");
		if(StringUtil.isNotEmpty(skin)) return skin;
		
		SysPaurService sysPaurService=(SysPaurService)AppUtil.getBean("sysPaurService");
		Long userId = getCurrentUserId();		
		skinStyle=sysPaurService.getCurrentUserSkin(userId);	
		session.setAttribute("skinStyle", skinStyle);
		return skinStyle;
	}
	
	/**
	 * 设置当前组织。
	 * @param sysOrg
	 */
	public static void setCurrentOrg(ISysOrg sysOrg){
		if(sysOrg==null) return;
		curOrg.set(sysOrg);
		
		//将当前人和组织放到缓存中。
		ICache iCache=(ICache)AppUtil.getBean(ICache.class);
		Long userId=ContextUtil.getCurrentUserId();
		if(userId==null) return;
		
		String userKey=ContextUtil.CurrentOrg + userId;
		iCache.add(userKey, sysOrg);
		
	}
	
	/**
	 * 清除当前组织对象。
	 */
	public static void cleanCurrentOrg(){
		curOrg.remove();
	}
	
	/**
	 * 清除当前用户。
	 */
	public static void cleanCurUser(){
		curUser.remove();
	}
	
	/**
	 * 存放到cookie和session。
	 * @param sysOrg
	 * @param request
	 * @param response
	 * @param session
	 */
	private static void saveSessionCookie(ISysOrg sysOrg,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		//放到session。
		session.setAttribute(CurrentOrg, sysOrg);
		//获取组织id。
		Long orgId=sysOrg.getOrgId();
		//添加cookie。
		CookieUitl.addCookie(CurrentOrg, orgId.toString(), request, response);
	}
	
	/**
	 * 从session和cookie中清除当前组织。
	 * @param request
	 * @param response
	 */
	public static void removeCurrentOrg(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession(false);
		if(session!=null){
			session.removeAttribute(CurrentOrg);
		}
		CookieUitl.delCookie(CurrentOrg,  request, response);
	}
	
	/**
	 * 清除所有的线程变量。
	 */
	public static void clearAll(){
		curUser.remove();
		curOrg.remove();
		
		RequestUtil.clearHttpReqResponse();
		TaskThreadService.clearAll();
		TaskUserAssignService.clearAll();
		MessageUtil.clean();
	}
	
	
	
}
