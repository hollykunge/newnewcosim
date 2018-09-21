package com.hotent.platform.service.system;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.ResourcesDao;
import com.hotent.platform.dao.system.ResourcesUrlDao;
import com.hotent.platform.dao.system.RoleResourcesDao;
import com.hotent.platform.dao.system.SubSystemDao;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.ResourcesUrl;
import com.hotent.platform.model.system.RoleResources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SystemConst;

/**
 * 对象功能:子系统资源 Service类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2011-12-05 17:00:54
 */
@Service
public class ResourcesService extends BaseService<Resources>
{
	@Resource
	private ResourcesDao resourcesDao;
	@Resource
	private ResourcesUrlDao resourcesUrlDao;
	@Resource
	private SubSystemDao subSystemDao;
	@Resource
	private RoleResourcesDao roleResourcesDao;
	
	
	public ResourcesService(){
	}
	
	@Override
	protected IEntityDao<Resources, Long> getEntityDao() 
	{
		return resourcesDao;
	}
	
	/**
	 * 添加资源。
	 * @param resources
	 * @param aryName
	 * @param aryUrl
	 * @throws Exception
	 */
	public Long addRes(Resources resources,String[] aryName,String[] aryUrl) throws Exception{
		Long resId=UniqueIdUtil.genId();
		resources.setResId(resId);
		String path="";
		Long parentId=resources.getParentId();
		Resources parentRes=resourcesDao.getById(parentId);
		if(BeanUtils.isNotEmpty(parentRes)){
			if(StringUtil.isNotEmpty(parentRes.getPath())){
				path=parentRes.getPath()+":"+resId;
			}
		}else{
			path=resId.toString();
		}
		resources.setPath(path);
		resourcesDao.add(resources);
		
		if(BeanUtils.isEmpty(aryName)) return resId;
	
		for(int i=0;i<aryName.length;i++){
			String url=aryUrl[i];
			if(StringUtil.isEmpty(url)) continue;
			ResourcesUrl resouceUrl=new ResourcesUrl();
			resouceUrl.setResId(resId);
			resouceUrl.setResUrlId(UniqueIdUtil.genId());
			resouceUrl.setName(aryName[i]);
			resouceUrl.setUrl(url);
			resourcesUrlDao.add(resouceUrl);
		}
		return resId;
	}
	
	/**
	 * 更新资源。
	 * @param resources
	 * @param aryName
	 * @param aryUrl
	 * @throws Exception
	 */
	public void updRes(Resources resources,String[] aryName,String[] aryUrl) throws Exception{
		Long resId=resources.getResId();
		String path="";
		Long parentId=resources.getParentId();
		Resources parentRes=resourcesDao.getById(parentId);
		if(BeanUtils.isNotEmpty(parentRes)){
			if(StringUtil.isNotEmpty(parentRes.getPath())){
				path=parentRes.getPath()+":"+resId;
			}
		}else{
			path=resId.toString();
		}
		resources.setPath(path);
		resourcesDao.update(resources);
		//删除资源的url。
		resourcesUrlDao.delByResId(resId);
		
		if(BeanUtils.isEmpty(aryName)) return;
		
		for(int i=0;i<aryName.length;i++){
			String url=aryUrl[i];
			if(StringUtil.isEmpty(url))	continue;
			ResourcesUrl resouceUrl=new ResourcesUrl();
			resouceUrl.setResId(resId);
			resouceUrl.setResUrlId(UniqueIdUtil.genId());
			resouceUrl.setName(aryName[i]);
			resouceUrl.setUrl(url);
			resourcesUrlDao.add(resouceUrl);
		}
	}
	
	/**
	 * 根据系统id查询
	 * @param systemId	系统id
	 * @return
	 */
	public List<Resources> getBySystemId(long systemId){
		List<Resources> resourcesList= resourcesDao.getBySystemId(systemId);
		
		return resourcesList;
	}
	

	/**
	 * 根据系统id和父节点id获取资源节点。
	 * <pre>
	 * 1.根据父节点id获取资源获取到即返回。
	 * 2.如果获取不到则根据子系统相关数据构建资源根节点进行返回。
	 * </pre>
	 * @param parentId
	 * @return
	 */
	public Resources getParentResourcesByParentId(long systemId,long parentId) {
		Resources parent = resourcesDao.getById(parentId);
		if(parent!=null) return parent;
	
		SubSystem sys=subSystemDao.getById(systemId);
		
		parent = new Resources();
		parent.setResId(Resources.ROOT_ID);
		parent.setParentId(Resources.ROOT_PID);
		parent.setSn(0);
		parent.setSystemId(systemId);
		
		parent.setAlias(sys.getAlias());
		
		parent.setIsDisplayInMenu(Resources.IS_DISPLAY_IN_MENU_Y);
		parent.setIsFolder(Resources.IS_FOLDER_Y);
		parent.setIsOpen(Resources.IS_OPEN_Y);
		parent.setResName(sys.getSysName());
		
		return parent;
		
	}
	
	/**
	 * 根据资源id查询在列表中他所有的子节点。
	 * @param resId
	 * @param allRes
	 * @return
	 */
	private List<Resources> getChildsByResId(Long resId,List<Resources> allRes){
		List<Resources> rtnList=new ArrayList<Resources>();
		for(Iterator<Resources> it=allRes.iterator();it.hasNext();){
			Resources res=it.next();
			if(!res.getParentId().equals(resId)) continue;
		
			rtnList.add(res);
			recursiveChilds(res.getResId(),rtnList,allRes);
			it.remove();
		}
		return rtnList;
	}
	
	/**
	 * 递归查找节点。
	 * @param resId
	 * @param rtnList
	 * @param allRes
	 */
	private void recursiveChilds(Long resId,List<Resources> rtnList,List<Resources> allRes){
		for(Iterator<Resources> it=allRes.iterator();it.hasNext();){
			Resources res=it.next();
			if(!res.getParentId().equals(resId)) continue;
			rtnList.add(res);
			recursiveChilds(res.getResId(),rtnList,allRes);
			it.remove();
		}
	}
	

	/**
	 * 删除资源将删除其下所有的资源节点。
	 */
	public void delById(Long resId){
		//所属系统
		Resources res=resourcesDao.getById(resId);
		List<Resources> allRes=resourcesDao.getBySystemId(res.getSystemId());
		List<Resources> allChilds=getChildsByResId(resId,allRes);
		
		for(Iterator<Resources> it=allChilds.iterator();it.hasNext();){
			Resources resources=it.next();
			Long childId=resources.getResId();
			//删除关联的URL
			resourcesUrlDao.delByResId(childId);
			//删除url关联的角色
			roleResourcesDao.delByResId(childId);
			//删除资源自身
			resourcesDao.delById(childId);
		}
		
		//删除关联的URL
		resourcesUrlDao.delByResId(resId);
		//删除url关联的角色
		roleResourcesDao.delByResId(resId);
		//删除主键
		resourcesDao.delById(resId);
	}
	
	
	/**
	 * 获取系统的资源，并把某角色拥有的资源做一个选择标记。
	 * @param systemId		系统id。
	 * @param roleId		角色id。
	 * @return
	 */
	public List<Resources> getBySysRolResChecked(Long systemId,Long roleId){
		List<Resources>  resourcesList=resourcesDao.getBySystemId(systemId);
		List<RoleResources> roleResourcesList=roleResourcesDao.getBySysAndRole(systemId,roleId);
		
		Set<Long> set=new HashSet<Long> ();
		
		if(BeanUtils.isNotEmpty(roleResourcesList)){
			for(RoleResources rores:roleResourcesList){
				set.add(rores.getResId());
			}
		}
		
		if(BeanUtils.isNotEmpty(resourcesList)){
			for(Resources res:resourcesList){
				if(set.contains(res.getResId())){
					res.setChecked(Resources.IS_CHECKED_Y);
				}else{
					res.setChecked(Resources.IS_CHECKED_N);
				}
			}
		}
		return resourcesList;
	}
	
	/**
	 * 根据用户获取菜单数据。
	 * 
	 * @param sys	子系统
	 * @param user	用户
	 * @return
	 */
	public List<Resources> getSysMenu(SubSystem sys,ISysUser user){
		Long systemId=sys.getSystemId();
		Collection<GrantedAuthority> auths= (Collection<GrantedAuthority>) ContextUtil.getCurrentUser().getAuthorities();
		List<Resources> resourcesList=new ArrayList<Resources>();

		//是否是超级管理员
		if(auths!=null&&auths.size()>0&&auths.contains(SystemConst.ROLE_GRANT_SUPER)){
			resourcesList=resourcesDao.getSuperMenu(systemId);
		}else{
			String roles="";
			for(GrantedAuthority role:auths){
				roles+="'"+role.getAuthority()+"'";
				roles+=",";
			}
			int index=roles.lastIndexOf(",");
			if(index>=0){
				roles = roles.substring(0,index);
			}
			if(StringUtil.isEmpty(roles)){
				roles="''";
			}
			resourcesList=resourcesDao.getNormMenuByRole(systemId,roles);
		}
		
		short isLocal=sys.getIsLocal()==null?1:sys.getIsLocal().shortValue();
		//外地系统
		if(isLocal==SubSystem.isLocal_N){
			//前缀+外地址
			for(Resources res:resourcesList){
				res.setDefaultUrl(sys.getDefaultUrl()+res.getDefaultUrl());
			}
		}
		return resourcesList;
	}
	
	public List<Resources> getCloudMenu(SubSystem sys,ISysUser user){
		Long systemId=sys.getSystemId();
		Collection<GrantedAuthority> auths= (Collection<GrantedAuthority>) ContextUtil.getCurrentUser().getAuthorities();
		List<Resources> resourcesList=new ArrayList<Resources>();

		String roles="";
		for(GrantedAuthority role:auths){
			roles+="'"+role.getAuthority()+"'";
			roles+=",";
		}
		int index=roles.lastIndexOf(",");
		if(index>=0){
			roles = roles.substring(0,index);
		}
		if(StringUtil.isEmpty(roles)){
			roles="''";
		}
		resourcesList=resourcesDao.getCloudMenuByRole(systemId,roles);		
		return resourcesList;
	}
	public List<Resources> getCloudTopMenu(SubSystem sys,ISysUser user){
		List<Resources> topResourcesList=new ArrayList<Resources>();
		List<Resources> resourcesList= this.getCloudMenu(sys, user);
		for(Resources resources:resourcesList){
			if(resources.getParentId().longValue()==0L){
				topResourcesList.add(resources);
			}
		}
		return topResourcesList;
	}
	
	public List<Resources> getChildrenByParentId(Long parentId,boolean includeGrand){
		List<Resources> resourcesList = this.resourcesDao.getByParentId(parentId);
		if(includeGrand){
			for(Resources resources:resourcesList){
				List<Resources> subResourcesList = this.resourcesDao.getByParentId(resources.getResId());
				resources.setChildren(subResourcesList);
			}
		}
		return resourcesList;
	}
	
	/**
	 * 判断别名在该系统中是否存在。
	 * @param systemId	系统id
	 * @param alias		系统别名
	 * @return
	 */
	public Integer isAliasExists(Resources resources){
		Long systemId=resources.getSystemId();
		String alias=resources.getAlias();
		return resourcesDao.isAliasExists(systemId, alias);
	}
	
	
	/**
	 * 判断别名是否存在。
	 * @param systemId
	 * @param resId
	 * @param alias
	 * @return
	 */
	public Integer isAliasExistsForUpd(Resources resources){
		Long systemId=resources.getSystemId();
		String alias=resources.getAlias();
		Long resId=resources.getResId();
		return resourcesDao.isAliasExistsForUpd(systemId, resId, alias);
	}
	
	/**
	 * 根据栏目更多路径获取相应的资源的功能模块
	 * 如果取得的资源不唯一则随意取一功能模块
	 * @param url  
	 * @return
	 */
	public Resources getByUrl(String url) {
		List<Resources>list=resourcesDao.getByUrl(url);
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Resources> getByParentId(Long id){
		return resourcesDao.getByParentId(id);
	}
	
	/**
	 * 对资源进树行拖动。
	 * @param sourceId		原节点。
	 * @param targetId		目标节点。
	 * @param moveType		拖动类型。（inner,prev,next).
	 */
	public void move(Long sourceId,Long targetId,String moveType){
		//"inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
		//成为子节点。
		if("inner".equalsIgnoreCase(moveType)){
			Resources target = resourcesDao.getById(targetId);
			Resources source = resourcesDao.getById(sourceId);
			//将拖动的节点父节点设置为目标节点的ID
			source.setParentId(target.getResId());
			//设置资源移动后的路径
			if(StringUtil.isNotEmpty(target.getPath())){
				source.setPath(target.getPath()+":"+sourceId);
			}else{
				source.setPath(sourceId.toString());
			}
			
			resourcesDao.update(source);
		}
		//成为同级节点。
		else{
			Resources target = resourcesDao.getById(targetId);
			int sn=target.getSn();
			Resources parentRes=resourcesDao.getById(target.getParentId());
			Resources source = resourcesDao.getById(sourceId);
			//设置资源移动后的路径
			if(StringUtil.isNotEmpty(parentRes.getPath())){
				source.setPath(parentRes.getPath()+":"+sourceId);
			}else{
				source.setPath(sourceId.toString());
			}
			
			//父id设为和目标相同
			source.setParentId(target.getParentId());
			//向前排序。
			if("prev".equals(moveType)){
				//修改排序
				source.setSn(sn);
				target.setSn(sn+1);
				resourcesDao.update(source);
				resourcesDao.update(target);
			}
			else{
				source.setSn(sn+1);
				resourcesDao.update(source);
			}
			
		}
	}
	
	/**
	 * 给资源的图标添加上下文的路径。
	 * @param list
	 * @param ctxPath
	 */
	public static void addIconCtxPath(List<Resources> list,String ctxPath){
		for(Iterator<Resources> it=list.iterator();it.hasNext();){
			Resources res=it.next();
			String icon=res.getIcon();
			if(StringUtil.isNotEmpty(icon)){
				res.setIcon(ctxPath+ icon);
			}
		}
	}

	/**
	 * 导出资源
	 * @param resId
	 * @return
	 */
	public String exportXml(long resId) {
		String strXml="";
		Document doc=DocumentHelper.createDocument();
		Element root = doc.addElement("items");	
		addElement(root,resId);
		strXml=doc.asXML();
		return strXml;
	}

	private void addElement(Element root, long resId) {
		List<Resources> list=getByParentId(resId);
		if(BeanUtils.isNotEmpty(list)){
			for(Resources res:list){
				Element element=root.addElement("item");
				setAttribute(res, element);
				addElement(element, res.getResId());
			}
		}
	}
	
	/**
	 * 根据资源实体  属性 设置 元素相对应的属性
	 * @param res
	 * @param element
	 */
	public void setAttribute(Resources res,Element element){
		String url=res.getDefaultUrl();
		if(url!=null && !url.equals("")){
			element.addAttribute("defaultUrl", res.getDefaultUrl());
		}
		element.addAttribute("name", res.getResName());
		element.addAttribute("isDisplayMenu", res.getIsDisplayInMenu().toString());
		element.addAttribute("isFolder", res.getIsFolder().toString());
		element.addAttribute("isOpen", res.getIsOpen().toString());
		element.addAttribute("icon", res.getIcon());
		element.addAttribute("sn", res.getSn().toString());
	}
	
	/**
	 * 导入资源
	 * @param inputStream
	 * @param resId
	 * @param systemId
	 */
	public void importXml(InputStream inputStream, long resId,long systemId) {
		Document doc=Dom4jUtil.loadXml(inputStream);
		Element root = doc.getRootElement();
		addResource(root,resId,systemId);
		
	}

	private void addResource(Element root, long resId,long systemId) {
		List<Element> list=root.elements();
		if(BeanUtils.isNotEmpty(list)){
			for(Element element:list){
				long id=UniqueIdUtil.genId();
				Resources res=new Resources();
				res.setResId(id);
				res.setResName(element.attributeValue("name"));
				res.setIsDisplayInMenu(Short.parseShort(element.attributeValue("isDisplayMenu")));
				res.setIsFolder(Short.parseShort(element.attributeValue("isFolder")));
				res.setIsOpen(Short.parseShort(element.attributeValue("isOpen")));
				res.setIcon(element.attributeValue("icon"));
				String url=element.attributeValue("defaultUrl");
				if(StringUtil.isNotEmpty(url)){
					res.setDefaultUrl(url);
				}
				res.setParentId(resId);
				res.setSystemId(systemId);
				add(res);
				addResource(element,id,systemId);
			}
		}
	}
	
	//更新sn
	public void updSn(Long resId, long sn) {
		resourcesDao.updSn(resId, sn);
		
	}
	
}
