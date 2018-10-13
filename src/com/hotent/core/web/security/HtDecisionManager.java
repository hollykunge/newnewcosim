package com.hotent.core.web.security;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Properties;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import com.hotent.core.encrypt.Base64;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.service.system.SubSystemUtil;

/**
 * 系统访问权限决策器，系统有权限则直接返回，没有权限则抛出AccessDeniedException错误。
 * 
 * <pre>
 * 系统根据当前页面的权限判断用户是否能够访问系统页面。
 * 		1.首先判断是否匿名访问，如果配置了匿名访问则直接通过。
 * 		2.判断用户是否已经登录，如果没有登录则返回没有权限页面。
 * 		3.如果是超级管理员直接通过。
 * 		4.页面的角色包括公开访问的角色直接通过。
 * 		5.判断是否有权限访问系统。
 * 		6.判断页面是否有角色授权，如果当前用户有访问页面的角色则直接通过。
 * 		7.没有满足的条件，则抛出错误。
 * </pre>
 * 
 * @author ray
 * 
 */
public class HtDecisionManager implements AccessDecisionManager {

	public Logger logger = LoggerFactory.getLogger(HtDecisionManager.class);

	// 是否初始化
	private static boolean isInit = false;
	// 是否验证通过
	private static int isValid = -3;

	@Resource
	private Properties configproperties;

	/**
	 * 验证是否有效。 0 有效 -1 没有产品key -2 产品key有问题 -3 有效期已过
	 * 
	 * @return
	 */
	private synchronized int validKey() {
		
		return 0;
//		// 已初始化，直接返回验证结果
//		if (isInit) {
//			return isValid;
//		}
//		// 未初始化，设置当前为已经初始化
//		isInit = true;
//		// -1没有设置产品key
//		String productKey = (String) configproperties.get("productKey");
//
//		if (StringUtil.isEmpty(productKey)) {
//			isValid = -1;
//			return -1;
//		}
//
//		try {
//			productKey = Base64.getFromBase64(EncryptUtil.decrypt(productKey.trim()));
//			if (isFirstEvaluate(productKey)) {
//				isValid = 0;
//				return 0;
//			}
//			String[] aryProductKey = productKey.split(",");
//			//正版
//			if (aryProductKey[0].equals("1")) {
//				isValid = 0;
//				return 0;
//			} 
//			//试用版
//			Long startTime = Long.parseLong(aryProductKey[1]);
//			Long stopTime = Long.parseLong(aryProductKey[2]);
//			Long currentTime = System.currentTimeMillis();
//			if (currentTime > startTime && currentTime < stopTime) {
//				isValid = 0;
//				return 0;
//			}
//			// 已过有效期
//			else {
//				isValid = -3;
//				return -3;
//			}
//		}
//		// key 有问题
//		catch (Exception ex) {
//			isValid = -2;
//			return -2;
//		}
	}

	/**
	 * 判断是否是叙作为评估版首次使用。如果是作为评估版首次使用，返回<code>true</code>，否则，返回<code>false</code>
	 * 
	 * @param productKey
	 * @return 作为评估版首次使用，返回<code>true</code>，否则，返回<code>false</code>。
	 */
	private  boolean isFirstEvaluate(String productKey) {
		if(!productKey.trim().equals("http://www.jee-soft.cn/")){
			return false;
		}
		String encryptKey = generateEvaluateKey();
		
		String path=FileUtil.getClassesPath() +"/conf/app.properties".replace("/", File.separator);
		FileUtil.saveProperties(path, "productKey", encryptKey);
		
		return true;
	}

	/**
	 * 生成试用期的密钥
	 * @return 用密钥
	 */
	private String generateEvaluateKey() {
		Long startTime = System.currentTimeMillis();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 60);
		Long endTime = calendar.getTimeInMillis();
		String key = "0," + startTime + "," + endTime;
		try {
			key = EncryptUtil.encrypt(Base64.getBase64(key));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

	private String getMessage(int i) {
		String msg = "";
		switch (i) {
		case -1:
			msg = "没有产品key,请联系<a href='http://www.jee-soft.cn' target='_blank'>云雀</a>购买正式版产品!";
			break;
		case -2:
			msg = "有效期已过,请联系<a href='http://www.jee-soft.cn' target='_blank'>云雀</a>购买正式版产品!";
		case -3:
			msg = "有效期已过,请联系<a href='http://www.jee-soft.cn' target='_blank'>云雀</a>购买正式版产品!";
		default:
			break;
		}
		return msg;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// 匿名访问
		if (configAttributes.contains(SystemConst.ROLE_CONFIG_ANONYMOUS)) {
			return;
		}

		int rtn = validKey();
		if (rtn != 0) {
			String msg = getMessage(rtn);
			throw new AccessDeniedException(msg);
		}

		// 登陆访问
		if (authentication == null) {
			throw new AccessDeniedException("没有登录系统");
		}

		Object principal = authentication.getPrincipal();
		if (principal == null) {
			throw new AccessDeniedException("登录对象为空");
		}

		if (!(principal instanceof ISysUser)) {
			throw new AccessDeniedException("登录对象必须为ISysUser实现类");
		}
		
		ISysUser user = (ISysUser) principal;
		// 获取当前用户的角色。
		Collection<GrantedAuthority> roles = user.getAuthorities();

		String mes = "主系统 >> >  >\nURL:" + object + "\n当前用户拥有角色:" + roles
				+ "\n 当前URL被分配给以下角色:" + configAttributes;

		logger.debug(mes);

		// 超级访问
		if (roles.contains(SystemConst.ROLE_GRANT_SUPER)) {
			return;
		}
		// 公开访问
		if (configAttributes.contains(SystemConst.ROLE_CONFIG_PUBLIC)) {
			return;
		}

		// 判定用户是否有访问当前系统的权限
		SubSystem currentSys = SubSystemUtil.getCurrentSystem(((FilterInvocation) object).getHttpRequest());
//导致全局角色无法访问，先去除，测试有无影响
//		if (currentSys != null) {
//			Long systemId = currentSys.getSystemId();
//			// 获取系统和角色列表的映射。
//			ICache iCache = (ICache) AppUtil.getBean(ICache.class);
//			// 获取角色列表。
//			Set<String> roleSet =(Set<String>)iCache.getByKey(SecurityUtil.SystemRoleMap + systemId);
//			boolean canAccessSystem = false;
//			// 如果当前用户的角色在系统的角色中，就允许访问该系统。
//			for (GrantedAuthority hadRole : roles) {
//				if (roleSet.contains(hadRole.getAuthority())) {
//					canAccessSystem = true;
//					break;
//				}
//			}
//			if (!canAccessSystem) {
//				throw new AccessDeniedException("没有访问该系统的权限!");
//			}
//		}

		// 授权访问
		// 遍历当前用户的角色，如果当前页面对应的角色包含当前用户的某个角色则有权限访问。
		for (GrantedAuthority hadRole : roles) {
			if (configAttributes.contains(new SecurityConfig(hadRole.getAuthority()))) {
				return;
			}
		}

		throw new AccessDeniedException("对不起,你没有访问该页面的权限!");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {

		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {

		return true;
	}

}
