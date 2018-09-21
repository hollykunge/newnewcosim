package com.hotent.platform.service.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;

import com.hotent.core.cache.ICache;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.ResourcesUrlExt;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SystemConst;

/**
 * 主要用于系统权限资源缓存。
 * <pre>
 * 	1.系统的url和角色映射。
 *  2.系统的url和参数列表进行映射。
 * 	4.系统和角色进行映射。
 *  3.系统的功能和角色映射。
 * </pre>
 * @author ray
 *
 */
public class SecurityUtil {
	
	public static String FunctionRoleMap="functionRoleMap_";
	
	public static String UrlRoleMap="urlRoleMap_";
	
	public static String UrlParaMap="urlParaMap_";
	
	public static String SystemRoleMap="systemRoleMap_";
	
	public static String UserRole="userRole_";
	
	public static String UserOrgRole="userOrgRole_";
	
	public static String OrgRole="orgRole_";
	
	public static String SystemFlag="systemFlag_";

	/**
	 * 加载本地子系统的所有资源。
	 * @param sysRoleService
	 * @param subSystemService
	 */
	public static void loadRes(){
		SubSystemService subSystemService=(SubSystemService)AppUtil.getBean(SubSystemService.class);
		List<SubSystem> sysList=subSystemService.getLocalSystem();
    	if(sysList==null || sysList.size()==0) return;
    	for(SubSystem sys:sysList){
			Long systemId=sys.getSystemId();
			loadRes( systemId);
		}
	}
	
	
	public static Map<String, Collection<ConfigAttribute>> getUrlRoleMap(Long systemId){
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		Map<String, Collection<ConfigAttribute>> roleMap=(Map<String, Collection<ConfigAttribute>>)iCache.getByKey(SecurityUtil.UrlRoleMap + systemId);
		return roleMap;
	}
	
	
	public static Map<String, Set<String>> getUrlParaMap(Long systemId){
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		Map<String, Set<String>> paraMap=(Map<String, Set<String>>) iCache.getByKey(SecurityUtil.UrlParaMap + systemId);
		return paraMap;
	}
	
	
	
	/**
	 * 根据系统Id删除缓存后，重新根据系统id加入缓存。
	 * @param systemId
	 */
	public static void removeCacheBySystemId(Long systemId){
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		iCache.delByKey(FunctionRoleMap+systemId);
		iCache.delByKey(UrlRoleMap+systemId);
		iCache.delByKey(UrlParaMap+systemId);
		iCache.delByKey(SystemRoleMap+systemId);
		
		
		iCache.delByKey(SystemFlag+systemId);

		
		loadRes(systemId);
	}
	
	/**
	 * 将系统 的功能和角色列表加入到映射中。
	 * @param systemId		系统id。
	 * @param funcRoleList	功能和角色映射列表。
	 */
	private static void putFuncRoleList(Long systemId, List<ResourcesUrlExt> funcRoleList){
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		String urlParaMapKey=FunctionRoleMap + systemId;
		Map<String, Collection<ConfigAttribute>> functionRole=getResources(funcRoleList);
		if(BeanUtils.isNotEmpty(functionRole)){
			iCache.add(urlParaMapKey,functionRole);
		}
		
	}
	
	
	 /**
     * 转化功能与角色的关系。
     * <pre>
     * 	将功能和角色对象列表转化为功能对角色列表的映射。
     * 	List&lt;ResourcesUrlExt> 转换为
     *  Map&lt;功能,角色集合>
     * </pre>
     * @param funcRoleList
     * @return
     */
	 private static Map<String, Collection<ConfigAttribute>> getResources(List<ResourcesUrlExt> funcRoleList){
	    	if(BeanUtils.isEmpty(funcRoleList)) return null;
			Map<String, Collection<ConfigAttribute>>  functionRole=new HashMap<String, Collection<ConfigAttribute>>();
			for(ResourcesUrlExt table:funcRoleList){
				if(table==null) continue;
				String function=(String) table.getFunc();
				String role=(String)table.getRole();
				if(StringUtil.isEmpty(function))
					continue;
				
				function=function.trim();
				if(functionRole.containsKey(function)){
					if(StringUtil.isNotEmpty(role))
						functionRole.get(function).add(new SecurityConfig(role));
				}else{
					Collection<ConfigAttribute> collectoin=new HashSet<ConfigAttribute>();
					if(StringUtil.isNotEmpty(role))
						collectoin.add(new SecurityConfig(role));
					functionRole.put(function, collectoin);
				}
			}
			return functionRole;
		}
	
	/**
	 * 根据系统id加载资源。
	 * @param sysRoleService
	 * @param subSystemService
	 * @param systemId
	 */
	public static  void loadRes(Long systemId){
		SysRoleService sysRoleService=(SysRoleService)AppUtil.getBean(SysRoleService.class);
		List<ResourcesUrlExt> urlList=sysRoleService.getUrlRightMap(systemId);
		List<ISysRole> listRole= sysRoleService.getBySystemId(systemId);
		List<ResourcesUrlExt> funcRoleList=sysRoleService.getFunctionRoleList(systemId);
		
		putResources(systemId,urlList);
		putSystemRole(systemId,listRole);
		putFuncRoleList(systemId,funcRoleList);
	}
	
	/**
	 * 系统资源是否缓存。
	 * @param systemId
	 * @return
	 */
	public static boolean isResCached(Long systemId){
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		Map<String, Collection<ConfigAttribute>> map=(Map<String, Collection<ConfigAttribute>> )iCache.getByKey(UrlRoleMap + systemId);
		if(map==null)
			return false;
		return true;
	}
	
	/**
	 * url和参数，url和角色进行映射起来。
	 * @param systemId
	 * @param urlList
	 */
	@SuppressWarnings("rawtypes")
	private  static void putResources(Long systemId,List<ResourcesUrlExt> urlList){
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		String urlRoleMapKey=UrlRoleMap + systemId;
		String urlParaMapKey=UrlParaMap + systemId;
		//URL 和角色列表映射。
		Map<String, Collection<ConfigAttribute>> 	urlRoleMap=new HashMap<String, Collection<ConfigAttribute>>();
		//url和参数列表的映射
		Map<String, Set<String>> urlParaMap	=new HashMap<String, Set<String>>();
		
		if(BeanUtils.isEmpty(urlList)) return ;
		
		for(ResourcesUrlExt resource:urlList){
			if(resource==null) continue;
			String fullUrl=resource.getUrl();
			String role=resource.getRole();
		
			if(StringUtil.isEmpty(fullUrl)) continue;
			fullUrl=fullUrl.trim();
			
			String parameter="";
			String url=fullUrl;
			//有参数 地址如下 add.ht?name=hotent。
			if(fullUrl.indexOf("?")>-1){
				String[] aryUrl=fullUrl.split("\\?");
				url=aryUrl[0];
				parameter=aryUrl[1];
			}
			
			//参数处理
			//url ： 对应参数的SET集合。
			if(urlParaMap.containsKey(url)){
				Set<String> paramList=urlParaMap.get(url);
				paramList.add(parameter);
			}
			else{
				Set<String> paramList=new HashSet<String>();
				paramList.add(parameter);
				urlParaMap.put(url, paramList);
			}
			
			//角色处理
			//url : 对应角色集合。
			if(urlRoleMap.containsKey(fullUrl)){
				Collection<ConfigAttribute> roleList=urlRoleMap.get(fullUrl);
				if(StringUtil.isNotEmpty(role))
					roleList.add(new SecurityConfig(role));
			}
			else{
				Collection<ConfigAttribute> collectoin=new HashSet<ConfigAttribute>();
				if(StringUtil.isNotEmpty(role))
					collectoin.add(new SecurityConfig(role));
				urlRoleMap.put(fullUrl, collectoin);
			}
			
		}
		
		if(BeanUtils.isNotEmpty(urlRoleMap))
			iCache.add(urlRoleMapKey,urlRoleMap);
		if(BeanUtils.isNotEmpty(urlParaMap))
			iCache.add(urlParaMapKey,urlParaMap);
		
	}
	
	
	/**
	 * 添加系统和角色的关系映射。
	 * 系统id ： 角色set集合。
	 * @param systemId
	 */
	private static void putSystemRole(Long systemId,List<ISysRole> listRole){
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		String systemRoleKey=SystemRoleMap + systemId;
		//URL 和角色列表映射。
		Set<String> roleSet=new HashSet<String>();
		for(ISysRole role: listRole){
			roleSet.add(role.getAlias());
		}
		iCache.add(systemRoleKey, roleSet);
	}
	
	/**
	 * 根据用户ID删除对应的角色映射缓存。
	 * @param account
	 */
	@SuppressWarnings("unchecked")
	public static void removeUserRoleCache(Long userId){
		ICache cache=(ICache) AppUtil.getBean(ICache.class);
		String key=UserRole + userId;
		cache.delByKey(key);
	}
	
	/**
	 * 根据orgId删除对应的角色映射缓存。
	 * @param orgId
	 */
	public static void removeOrgRoleCache(Long orgId){
		ICache cache=(ICache) AppUtil.getBean(ICache.class);
		String key=OrgRole + orgId;
		cache.delByKey(key);
	}
	
	/**
	 * 将所有的缓存都清除。
	 */
	@SuppressWarnings("unchecked")
	public static void removeAll(){
		ICache cache=(ICache) AppUtil.getBean(ICache.class);
		cache.clearAll();
	}
	
	/**
	 * 根据系统和功能别名判断是否有权限访问。
	 * @param systemId
	 * @param function
	 * @return
	 */
	public static boolean hasFuncPermission(Long systemId, String function){
		ICache cache=(ICache) AppUtil.getBean(ICache.class);
		Map<String, Collection<ConfigAttribute>> functionRole=(Map<String, Collection<ConfigAttribute>>)cache.getByKey(FunctionRoleMap + systemId);
		ISysUser currentUser= ContextUtil.getCurrentUser();
		//超级管理员
		if(currentUser.getAuthorities().contains(SystemConst.ROLE_GRANT_SUPER)){
			return true;
		}
		//当功能在系统功能表中，匹配当前用户的角色是否在功能的角色列表中。
		else if(functionRole!=null&&functionRole.containsKey(function)){
			Collection<ConfigAttribute> configAttributes=functionRole.get(function);
			for(GrantedAuthority hadRole:currentUser.getAuthorities()){
	        	if(configAttributes.contains(new SecurityConfig(hadRole.getAuthority()))){  
	                return true;
	            }
	        }
			return false;
	    }else{
			return true;
	    }
    }

}
