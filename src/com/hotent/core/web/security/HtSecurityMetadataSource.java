package com.hotent.core.web.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import com.hotent.core.cache.ICache;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SubSystemUtil;
import com.hotent.platform.service.system.SysRoleService;

/**
 * 实现的功能。
 * <pre>
 * 1.系统初始化时，构建系统的url和角色映射。
 * 2.并根据当前的url取出url具有的角色权限。
 * 3.实现了 BeanPostProcessor接口，保证SysRoleService，SubSystemService，ICache能在初始化时进行注入，并调用其获取系统资源。
 * </pre>
 * @author ray
 */
public class HtSecurityMetadataSource implements FilterInvocationSecurityMetadataSource ,BeanPostProcessor {
	
	private  SysRoleService sysRoleService;
	 
	private  SubSystemService subSystemService;
	
	private ICache iCache;
	 
	
	
    /**具有匿名访问权限的url*/
	private  HashSet<String> anonymousUrls=new HashSet<String>();
   
	/**
	 * 设置具有匿名访问权限的url
	 * @param anonymousUrls
	 */
	public  void setAnonymousUrls(HashSet<String> anonymousUrls) {
		this.anonymousUrls = anonymousUrls;
	}

	
	
	

    /**
     * 取出匹配的URL。
     * 1.遍历参数，检查当前参数列表是否有空的参数。
     * 2.地址栏参数为空的情况下。
     * 		1.如果参数列表中有参数为空的情况则返回url。
     *  	2.否则返回为空串。
     * 3.循环检查参数列表，空的参数跳过。
     * 	判断地址栏参数是否包含参数列表中的参数，如包含则返回 url +"?" + parameter.
     *  遍历完毕后，如没有返回，则判断当前参数列表中是否有为空的参数。
     *  有则返回url，否则返回空串。
     * @param url	无参数的url
     * @param params	该url对应的参数列表
     * @param queryString	地址栏参数
     * @return
     */
    private String getUrl(String url, Set<String> params,String queryString){
    	
		boolean hasEmpty=false;
		//判断是否有空的参数。
		for(String parameter:params){
			if(StringUtil.isEmpty( parameter))
				hasEmpty=true;
		}
		//url没有参数的情况的处理
		if(StringUtil.isEmpty(queryString)) {
			if(hasEmpty)
				return url;
			else
				return "";
		}
		//url有参数的情况处理
		for(String parameter:params){
			if(StringUtil.isEmpty(parameter)) continue;
			Set<String> set=getParamsSet( parameter);
			Set<String> queryStringSet=getParamsSet(queryString);
			//是否地址栏参数包括当前所有的参数。
			//则构建url。
			if(queryStringSet.containsAll(set)){
				url=url+"?" + parameter;
				return url;
			}
		}
		if(hasEmpty) return url;
		return "";
    }

    /**
     * 将参数name=a&type=1分解为set对象。
     * <pre>
     * 	分解结果为set集合：
     * 		name=a
     * 		type=1
     * </pre>
     * @param parameter
     * @return
     */
    private Set<String> getParamsSet(String parameter){
    	Set<String> set=new HashSet<String>();
    	String[] aryPara= parameter.split("&");
    	for(String para:aryPara){
    		if(para.indexOf("=")>-1)
    			set.add(para);
    	}
    	return set;
    }
    
    /**
     * 根据当前的URL返回该url的角色集合。
     * 1.如果当前的URL在匿名访问的URL集合当中时，在当前的角色中添加匿名访问的角色(SysRole.ROLE_CONFIG_ANONYMOUS)。
     * 2.如果当前系统不存在的情况，给当前用户添加一个公共访问的角色(SysRole.ROLE_CONFIG_PUBLIC)。
     * 3.url 和角色映射，url和参数映射，给当前用户添加一个公共的角色(SysRole.ROLE_CONFIG_PUBLIC)。
     * 
     * @param url
     */
    public Collection<ConfigAttribute> getAttributes(Object object)throws IllegalArgumentException {
    	
		Collection<ConfigAttribute> configAttribute =new HashSet<ConfigAttribute>();
		
		FilterInvocation filterInvocation=((FilterInvocation)object);
    	HttpServletRequest request=filterInvocation.getRequest();
    	
    	String url = request.getRequestURI();
    	url=removeCtx(url,request.getContextPath());
    	//匿名访问的URL
    	if(anonymousUrls.contains(url)){
    		configAttribute.add(SystemConst.ROLE_CONFIG_ANONYMOUS);
    		return configAttribute;
    	}
    	//获取url参数
    	String queryString=request.getQueryString();
        //获取系统id
    	Long systemId=SubSystemUtil.getCurrentSystemId(request);
    	//系统id不存在，增加一个public角色
    	if(systemId==null ) {
    		configAttribute.add(SystemConst.ROLE_CONFIG_PUBLIC);
    		return configAttribute;
    	};
    	
    	
    	boolean isCached=SecurityUtil.isResCached(systemId);
    	//没有缓存则重新加载。
    	if(!isCached){
    		SecurityUtil.loadRes(systemId);
    	}
    
    	Map<String, Collection<ConfigAttribute>> roleMap=SecurityUtil.getUrlRoleMap(systemId);
    	Map<String, Set<String>> paraMap=SecurityUtil.getUrlParaMap(systemId) ;
    	
    	if(roleMap==null || paraMap==null) {
    		configAttribute.add(SystemConst.ROLE_CONFIG_PUBLIC);
    		return configAttribute;
    	};
    
    	if(!paraMap.containsKey(url)){
    		configAttribute.add(SystemConst.ROLE_CONFIG_PUBLIC);
    		return configAttribute;
    	}
    
		//获取参数对应的URL
		Set<String> params=paraMap.get(url);
		//根据参数获取URL，判断url是否在数据库中存在。
		String urlParams=getUrl( url,  params, queryString);
		//判断url参数为空
		if("".equals(urlParams)){
			configAttribute.add(SystemConst.ROLE_CONFIG_PUBLIC);
		}
		else {
			//根据url获取用户角色。
			configAttribute= roleMap.get(urlParams);
		}
    	return configAttribute;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
    
    /**
     * 返回系统中所有为url分配了的权限
     */
    public Collection<ConfigAttribute> getAllConfigAttributes() {
    	return null;
    }
    

	
	
	
	/**
	 * 获取当前URL
	 * @param url
	 * @param contextPath
	 * @return
	 */
	private static String removeCtx(String url,String contextPath){
		url=url.trim();
		if(StringUtil.isEmpty(contextPath)) return url;
		if(StringUtil.isEmpty(url)) return "";
		if(url.startsWith(contextPath)){
			url=url.replaceFirst(contextPath, "");
		}
		return url;
	}
	
	/**
	 * 保证资源只被初始化一次。
	 */
	boolean isInit=false;
	
	/**
	 * 保证service的注入。
	 * 获取系统资源。
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if(bean instanceof SysRoleService){
			this.sysRoleService=(SysRoleService)bean;
		}
		if(bean instanceof SubSystemService){
			this.subSystemService=(SubSystemService)bean;
		}
		
		if(bean instanceof ICache){
			this.iCache=(ICache)bean;
		}
		
		if(this.sysRoleService!=null && this.subSystemService!=null && iCache!=null ){
			if(!isInit){
				SecurityUtil.loadRes();
				isInit=true;
			}
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		
		return bean;
	}


	

}
