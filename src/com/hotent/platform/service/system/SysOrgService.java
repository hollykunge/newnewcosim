package com.hotent.platform.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysOrgTypeDao;
import com.hotent.platform.dao.system.SysUserOrgDao;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgType;
import com.hotent.platform.model.system.SysUserOrg;

/**
 * 对象功能:组织架构SYS_ORG Service类
 * 开发公司:
 * 开发人员:pkq
 * 创建时间:2011-11-09 11:20:13
 */
@Service
public class SysOrgService {
	@Resource
	private SysUserOrgDao sysUserOrgDao;
	@Resource
	private SysOrgTypeDao sysOrgTypeDao;
	@Resource
	private IAuthenticate iAuthenticate;
	@Resource
	private SysOrgDao sysOrgDao;

	public SysOrgService() {
	}
	
	public void add(ISysOrg org){
		iAuthenticate.add(org);
	}
	
	public void delByIds(Long[] ids){
		iAuthenticate.delByIds(ISysOrg.class, ids);
	}
	
	public void update(ISysOrg org){
		iAuthenticate.update(org);
	}
	
	public ISysOrg getById(Long id){
		return iAuthenticate.getOrgByOrgId(id);
	}
	
	public List<ISysOrg> getAll()	{
		return iAuthenticate.getAllOrgs();
	}
	
	public List<ISysOrg> getAll(QueryFilter queryFilter){
		return iAuthenticate.queryOrg(queryFilter);
	}

	/* *
	 * 对象功能：根据条件查询组织
	 */
	public List<ISysOrg> getOrgByOrgId(QueryFilter queryFilter) {
		return iAuthenticate.queryOrg(queryFilter);
	}

	/**
	 * 通过维度取得组织（没有维度ID则获取所有组织）
	 * 
	 * @param demId
	 * @return
	 */
	public List<ISysOrg> getOrgsByDemIdOrAll(Long demId) {
		return iAuthenticate.getOrgByDemId(demId);		
	}
	
	/**
	 * 根据维度查找组织节点
	 * @param demId
	 * @return 返回Map<Long,ISysOrg>其中key为组织ID
	 */
	public Map getOrgMapByDemId(Long demId) {
		String userNameStr = "";
		String userNameCharge = "";
		Map<Long, ISysOrg> orgMap = new HashMap<Long, ISysOrg>();
		List<ISysOrg> list = iAuthenticate.getOrgByDemId(demId);
		for (ISysOrg sysOrg : list) {
			List<SysUserOrg> userlist = sysUserOrgDao.getByOrgId(sysOrg.getOrgId());
			for (SysUserOrg userOrg : userlist) {
				if (userNameStr.isEmpty()) {
					userNameStr = userOrg.getUserName();
				} else {
					userNameStr = userNameStr + "," + userOrg.getUserName();
				}
				String isCharge = "";
				if (BeanUtils.isNotEmpty(userOrg.getIsCharge())) {
					isCharge = userOrg.getIsCharge().toString();
				}
				// 为主要负责人
				if (SysUserOrg.CHARRGE_YES.equals(isCharge)) {
					if (userNameCharge.isEmpty()) {
						userNameCharge = userOrg.getUserName();
					} else {
						userNameCharge = userNameCharge + "," + userOrg.getUserName();
					}
				}
			}

			sysOrg.setOwnUserName(userNameCharge);
			if (sysOrg.getOrgSupId()!=null && sysOrg.getOrgSupId() != 0)
				orgMap.put(sysOrg.getOrgId(), sysOrg);
		}
		return orgMap;
	}

	
	public void delById(Long id) {
		ISysOrg sysOrg=iAuthenticate.getOrgByOrgId(id);
		iAuthenticate.delOrgByPath(sysOrg.getPath());
	}

	public List<ISysOrg> getOrgsByUserId(Long userId) {
		return iAuthenticate.getOrgsUserIn(userId);
	}

	/**
	 * 取得某个用户所在的组织ID字符串
	 * 
	 * @param userId
	 * @return 返回格式如1,2
	 */
	public String getOrgIdsByUserId(Long userId) {
		StringBuffer sb = new StringBuffer();
		List<ISysOrg> orgList = iAuthenticate.getOrgsUserIn(userId);
		for (ISysOrg org : orgList) {
			sb.append(org.getOrgId() + ",");
		}
		if (orgList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 转达化数据格式,把List<ISysOrg>转化为Map<Long,List<ISysOrg>>。
	 * 
	 * @param rootId
	 * @param instList
	 * @return
	 */
	private Map<Long, List<ISysOrg>> coverTreeData(Long rootId, List<ISysOrg> instList) {
		Map<Long, List<ISysOrg>> dataMap = new HashMap<Long, List<ISysOrg>>();
		dataMap.put(rootId.longValue(), new ArrayList<ISysOrg>());
		if (instList != null && instList.size() > 0) {
			for (ISysOrg sysOrg : instList) {
				long parentId = sysOrg.getOrgSupId().longValue();
				if (dataMap.get(parentId) == null) {
					dataMap.put(parentId, new ArrayList<ISysOrg>());
				}
				dataMap.get(parentId).add(sysOrg);
			}
		}
		return dataMap;
	}

	/**
	 * 转化数所格式,将原如list 重新按树形结构排序
	 * 
	 * @param rootId
	 * @param instList
	 * @return
	 */
	public List<ISysOrg> coverTreeList(Long rootId, List<ISysOrg> instList) {

		Map<Long, List<ISysOrg>> dataMap = coverTreeData(rootId, instList);

		List<ISysOrg> list = new ArrayList<ISysOrg>();

		list.addAll(getChildList(rootId, dataMap));

		return list;
	}

	/**
	 * 转化数所格式,将原如list 重新按树形结构排序
	 * 
	 * @param parentId
	 * @param dataMap
	 * @return
	 */
	private List<ISysOrg> getChildList(Long parentId, Map<Long, List<ISysOrg>> dataMap) {
		List<ISysOrg> list = new ArrayList<ISysOrg>();

		List<ISysOrg> orgList = dataMap.get(parentId.longValue());
		if (orgList != null && orgList.size() > 0) {
			for (ISysOrg sysOrg : orgList) {
				list.add(sysOrg);
				List<ISysOrg> childList = getChildList(sysOrg.getOrgId(), dataMap);
				list.addAll(childList);
			}
		}
		return list;
	}

	public List<ISysOrg> getByUserIdAndDemId(Long userId, Long demId) {
		return iAuthenticate.getOrgByUserIdAndDemId(userId, demId);
	}

	

	/**
	 *拖动组织进行排序。
	 * @param targetId 目标组织ID
	 * @param dragId 	拖动的组织ID
	 * @param moveType 拖动类型 (prev,next,inner);
	 */
	public void move(Long targetId, Long dragId, String moveType) {
		ISysOrg target = iAuthenticate.getOrgByOrgId(targetId);
		ISysOrg dragged = iAuthenticate.getOrgByOrgId(dragId);
		
		if(!target.getDemId().equals(dragged.getDemId()))
			return;
			
		String nodePath=dragged.getPath();
		//根据拖动节点的路径找到其下所有的子组织。
		List<ISysOrg> list=iAuthenticate.getOrgByPath(nodePath);
		
		for(ISysOrg org:list){
			//向目标节点的前后拖动。
			if ("prev".equals(moveType) || "next".equals(moveType)) {
				String targetPath=target.getPath();
				String parentPath=targetPath.endsWith(".")?targetPath.substring(0,targetPath.length()-1):targetPath;
				//这个路径尾部带 "." 。
				parentPath=parentPath.substring(0,parentPath.lastIndexOf(".")+1) ;
				
				if(org.getOrgId().equals(dragId)){
					org.setOrgSupId(target.getOrgSupId());
					org.setPath(parentPath + dragId +".");
				}
				else{
					String path = org.getPath();
					String tmpPath =parentPath + dragId +"." +   path.replaceAll(nodePath, "");
					org.setPath(tmpPath);
				}
				
				if ("prev".equals(moveType)) {
					org.setSn(target.getSn() - 1);
				} else {
					org.setSn(target.getSn() + 1);
				}
			}
			//作为目标节点的子节点。
			else{
				//如果是被拖动的节点。
				////需改父节点
				if(org.getOrgId().equals(dragId)){
					//修改拖动的分类对象
					org.setOrgSupId(targetId);
					// 修改nodepath
					org.setPath(target.getPath() + org.getOrgId() + ".");					
				} else {
					// 带点的路径
					String path = org.getPath();
					// 替换父节点的路径。
					String tmpPath = path.replaceAll(nodePath, "");
					// 新的父节路径
					String targetPath = target.getPath();
					// 新的父节点 +拖动的节点id + 后缀
					String tmp = targetPath + dragged.getOrgId() + "." + tmpPath;
					org.setPath(tmp);					
				}
			}
			iAuthenticate.update(org);
		}
	}
	
	public static void main(String[] args) {
		String path="1.2.3.";
		path=path.endsWith(".")?path.substring(0,path.length()-1):path;
		String subPath=path.substring(0,path.lastIndexOf(".")+1) ;
		System.out.println(subPath);
	}
	
	/**
	 * 添加组织。
	 * @param sysOrg
	 * @throws Exception
	 */
	public void addOrg(ISysOrg sysOrg) throws Exception{
		//添加组织
		iAuthenticate.add(sysOrg);
		
		String ownerId=sysOrg.getOwnUser();
		if(StringUtil.isEmpty(ownerId)) return ;
		//添加组织负责人。
		String[] aryUserId = ownerId.split(",");
		for (int i = 0; i < aryUserId.length; i++) {
			String userId=aryUserId[i];
			if(StringUtil.isEmpty(userId)) continue;
			Long lUserId=Long.parseLong(userId);
			SysUserOrg sysUserOrg = new SysUserOrg();
			sysUserOrg.setUserOrgId(UniqueIdUtil.genId());
			sysUserOrg.setOrgId(sysOrg.getOrgId());
			sysUserOrg.setIsCharge(SysUserOrg.CHARRGE_YES);
			sysUserOrg.setUserId(lUserId);
			sysUserOrgDao.updNotPrimaryByUserId(lUserId);
			sysUserOrg.setIsPrimary(SysUserOrg.PRIMARY_YES);
			sysUserOrgDao.add(sysUserOrg);
		}
	}
	
	/**
	 * 更新组织数据。
	 * @param sysOrg
	 * @throws Exception
	 */
	public void updOrg(ISysOrg sysOrg) throws Exception{
		Long orgId=sysOrg.getOrgId();
		//添加组织
		iAuthenticate.update(sysOrg);
		//删除组织负责人。
		sysUserOrgDao.delChargeByOrgId(orgId);
		
		String ownerId=sysOrg.getOwnUser();
		if(StringUtil.isEmpty(ownerId)) return ;
		//添加组织负责人。
		String[] aryUserId = ownerId.split(",");
		for (int i = 0; i < aryUserId.length; i++) {
			String userId=aryUserId[i];
			if(StringUtil.isEmpty(userId)) continue;
			Long lUserId=Long.parseLong(userId);
			SysUserOrg sysUserOrg = new SysUserOrg();
			sysUserOrg.setUserOrgId(UniqueIdUtil.genId());
			sysUserOrg.setOrgId(orgId);
			sysUserOrg.setIsCharge(SysUserOrg.CHARRGE_YES);
			sysUserOrg.setUserId(lUserId);
			sysUserOrgDao.updNotPrimaryByUserId(lUserId);
			sysUserOrg.setIsPrimary(SysUserOrg.PRIMARY_YES);
			sysUserOrgDao.add(sysUserOrg);
		}
	}
	
	/**
	 * 根据用户取得主组织。
	 * @param userId
	 * @return
	 */
	public ISysOrg getPrimaryOrgByUserId(Long userId){
		return iAuthenticate.getPrimaryOrgByUserId(userId);
	}
	
	/**
	 * 根据路径得到组织集合 
	 * @param path
	 * @return
	 */
	public List<ISysOrg> getByOrgPath(String path){	
		return iAuthenticate.getOrgByPath(path);
	}
	
	
	/**
	 * 获取组织最近一级带有类型的父节点 
	 * @param path
	 * @return
	 */
	public ISysOrg getParentWithType(ISysOrg sysOrg){		
		Long parentOrgId=sysOrg.getOrgSupId();
		if(parentOrgId.equals(Long.parseLong("1"))) return null;
		ISysOrg parentOrg=iAuthenticate.getOrgByOrgId(parentOrgId);
		if(parentOrg==null ) return null;		
		if(parentOrg.getOrgType()!=null && sysOrgTypeDao.getById(parentOrg.getOrgType())!=null){
			return parentOrg;
		}	else{
			parentOrg=getParentWithType(parentOrg);
		}			
		return parentOrg;
	}
	/**
	 * 根据指定组织和组织类型，往上找到与指定组织类型相同或比指定组织类型更大一级的父组织 
	 * @param sysOrg 当前组织
	 * @return sysOrgType 要查找的组织类型
	 */
	public ISysOrg   getParentWithTypeLevel(ISysOrg sysOrg,SysOrgType	sysOrgType){	
		 //获取指定部门类型的类型记录
		ISysOrg parentOrg=getParentWithType(sysOrg);  //查找所需的父级部门		
		if(parentOrg==null) return parentOrg;
		SysOrgType	currentSysOrgType=sysOrgTypeDao.getById(parentOrg.getOrgType());
		//如果返回的类型比指定类型还小的话，继续查询
		if(currentSysOrgType!=null && sysOrgType.getLevels()<currentSysOrgType.getLevels()){  
			parentOrg=getParentWithTypeLevel(parentOrg,sysOrgType);	
		}
		return parentOrg;
	}
	
	/**
	 * 根据用户ID获取默认的的组织。
	 * @param userId
	 * @return
	 */
	public ISysOrg getDefaultOrgByUserId(Long userId){
		
		List<SysUserOrg>  list= sysUserOrgDao.getOrgByUserId(userId);
		Long orgId=0L;
		//个人不属于任何一个部门。
		if(BeanUtils.isEmpty(list)) return null;
		if(list.size()==1) {
			orgId=list.get(0).getOrgId();
		}else{
			//获取主要的组织。
			for(SysUserOrg userOrg:list){
				if(userOrg.getIsPrimary()==1){
					orgId=userOrg.getOrgId();
					break;
				}
			}
			//没有获取到主组织，从列表中获取一个组织作为当前组织。
			if(orgId==0L) orgId=list.get(0).getOrgId();
		}
		return  iAuthenticate.getOrgByOrgId(orgId);
	}
	
	//更新sn
	public void updSn(Long orgId, long sn) {
		iAuthenticate.updSn(orgId,sn);
		
	}
	/**
	 * 根据上级组织ID获取组织列表。
	 * @param userId
	 * @return
	 */
	public List<ISysOrg> getOrgByOrgSupId(Long orgSupId){
		List<ISysOrg> list= iAuthenticate.getOrgByOrgSupId(orgSupId);
		return list;
	}
	
	/**
	 * 根据上级组织ID获取此上级组织下level级组织列表，level为配置参数。
	 * @param orgSupId
	 * @param demId
	 * @return 
	 */
	public List<ISysOrg> getOrgByOrgSupIdAndLevel(Long orgSupId){
		List<ISysOrg> ChildList= iAuthenticate.getOrgByOrgSupId(orgSupId);
		Properties  prop = (Properties)AppUtil.getBean("configproperties");
		int level = Integer.parseInt(prop.getProperty("orgExpandLevel", "0"));
		int childSize=ChildList.size();
		if(level==0){
			for(int i=0;i<childSize;i++){
				List<ISysOrg> MoreList=getOrgByOrgSupIdAndLevel(ChildList.get(i).getOrgId());
				ChildList.addAll(MoreList);
			}
		} 
		if(level>1){
			level--;
			for(int i=0;i<childSize;i++){
				List<ISysOrg> MoreList = getOrgByOrgSupIdAndLevel(ChildList.get(i).getOrgId(),level);
				ChildList.addAll(MoreList)	;
			}
		}
		return ChildList;
	}
	
	
	/**
	 * 根据上级组织ID获取此上级组织下level级组织列表，level为配置参数。
	 * @param orgSupId
	 * @param demId
	 * @return 
	 */
	public List<ISysOrg> getOrgByOrgSupIdAndLevel(Long orgSupId,Long compId){
		List<ISysOrg> ChildList= iAuthenticate.getOrgByOrgSupId(orgSupId, compId);
		Properties  prop = (Properties)AppUtil.getBean("configproperties");
		int level = Integer.parseInt(prop.getProperty("orgExpandLevel", "0"));
		int childSize=ChildList.size();
		if(level==0){
			for(int i=0;i<childSize;i++){
				List<ISysOrg> MoreList=getOrgByOrgSupIdAndLevel(ChildList.get(i).getOrgId(),compId);
				ChildList.addAll(MoreList);
			}
		} 
		if(level>1){
			level--;
			for(int i=0;i<childSize;i++){
				List<ISysOrg> MoreList = getOrgByOrgSupIdAndLevel(ChildList.get(i).getOrgId(),level,compId);
				ChildList.addAll(MoreList)	;
			}
		}
		return ChildList;
	}

	public List<ISysOrg> getOrgByOrgSupIdAndLevel(Long orgSupId,int level) {
		List<ISysOrg> ChildList = new ArrayList<ISysOrg>();
		if(level>0){
			ChildList= iAuthenticate.getOrgByOrgSupId(orgSupId);
			level--;
			int childSize=ChildList.size();
			for(int i=0;i<childSize;i++){
				List<ISysOrg> MoreList=getOrgByOrgSupIdAndLevel(ChildList.get(i).getOrgId(),level);
				ChildList.addAll(MoreList)	;
			}
		}
		return ChildList;
	}
	
	public List<ISysOrg> getOrgByOrgSupIdAndLevel(Long orgSupId,int level,Long compId) {
		List<ISysOrg> ChildList = new ArrayList<ISysOrg>();
		if(level>0){
			ChildList= iAuthenticate.getOrgByOrgSupId(orgSupId);
			level--;
			int childSize=ChildList.size();
			for(int i=0;i<childSize;i++){
				List<ISysOrg> MoreList=getOrgByOrgSupIdAndLevel(ChildList.get(i).getOrgId(),level);
				ChildList.addAll(MoreList)	;
			}
		}
		return ChildList;
	}
	
	public SysOrg getOrgBySn(Long sn){
		return iAuthenticate.getOrgBySn(sn);
	}

}

