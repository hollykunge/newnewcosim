package com.hotent.platform.service.bpm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.bpm.BpmDefRightsDao;
import com.hotent.platform.dao.system.GlobalTypeDao;
import com.hotent.platform.model.bpm.BpmDefRights;
import com.hotent.platform.model.system.GlobalType;

/**
 * 对象功能:流程定义权限明细 Service类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2012-03-19 09:00:53
 */
@Service
public class BpmDefRightsService extends BaseService<BpmDefRights>
{
	@Resource
	private BpmDefRightsDao dao;
	@Resource
	private GlobalTypeDao globalTypeDao;
	
	public BpmDefRightsService()
	{
	}
	@Override
	protected IEntityDao<BpmDefRights, Long> getEntityDao() 
	{
		return dao;
	}
	/**
	 * 根据流程定义ID和权限类型互获取权限数据。
	 * @param defId
	 * @param rightType
	 * @return
	 */
	public List<BpmDefRights> getDefRight(Long defId,Short rightType){
		return dao.getDefRight(defId,rightType);
	}
	
	/**
	 * 根据分类和权限类型获取权限数据。
	 * @param typeId
	 * @param rightType
	 * @return
	 */
	public List<BpmDefRights> getTypeRight(Long typeId,Short rightType){
		return dao.getTypeRight(typeId,rightType);
	}
	
	/**
	 * 获取流程权限。
	 * @param assignId	流程定义id或流程分类ID,具体由assignType决定
	 * @param assignType	0，流程定义ID,1,流程分类ID。
	 * @return
	 */
	public Map<String,Map<String, String>> getRights(String assignId,int assignType){
		Map<String,Map<String, String>> rightsMap=new HashMap<String, Map<String,String>>();
		List<BpmDefRights> list=null;
		if(assignType==BpmDefRights.SEARCH_TYPE_DEF){
			list =dao.getByDefKey(assignId);
		}
		else{
			list = new ArrayList();
			GlobalType gt=globalTypeDao.getById(new Long(assignId));
			String nodePath=gt.getNodePath();
			List<GlobalType> globalTypelist= globalTypeDao.getByNodePath(nodePath);
			for(GlobalType glbtype:globalTypelist){
				list.addAll(dao.getByTypeId(glbtype.getTypeId()));
			}
		}
		
		List<BpmDefRights> user=new ArrayList<BpmDefRights>();
		Map users = new HashMap();
		List<BpmDefRights> role=new ArrayList<BpmDefRights>();
		Map roles = new HashMap();
		List<BpmDefRights> org=new ArrayList<BpmDefRights>();
		Map orgs = new HashMap();
		for(BpmDefRights rights:list){
			if(assignType==BpmDefRights.SEARCH_TYPE_GLT){
				if(!assignId.equals(rights.getFlowTypeId()+"")){
					continue;
				}
			}
			switch (rights.getRightType()) {
				case BpmDefRights.RIGHT_TYPE_USER:
					if(!users.containsKey(rights.getOwnerId())){
						users.put(rights.getOwnerId(), rights.getOwnerId());
						user.add(rights);
					}
					break;
				case BpmDefRights.RIGHT_TYPE_ROLE:
					if(!roles.containsKey(rights.getOwnerId())){
						roles.put(rights.getOwnerId(), rights.getOwnerId());
						role.add(rights);
					}
					break;
				default:
					if(!orgs.containsKey(rights.getOwnerId())){
						orgs.put(rights.getOwnerId(), rights.getOwnerId());
						org.add(rights);
					}
					break;
			}
		}
		Map<String,String> userMap=coverList2Map(user);
		Map<String,String> roleMap=coverList2Map(role);
		Map<String,String> orgMap=coverList2Map(org);
		rightsMap.put("user", userMap);
		rightsMap.put("role", roleMap);
		rightsMap.put("org", orgMap);
		return rightsMap;
	}
	
	
	/**
	 * 将流程权限列表转换成为map对象。
	 * 
	 * @param list
	 * @return
	 */
	public Map<String,String> coverList2Map(List<BpmDefRights> list){
		Map<String,String> m=new HashMap<String,String>();
		if(BeanUtils.isEmpty(list)) return m;
		
		String ownerId ="";
		String ownerName="";
		for(BpmDefRights r:list){
			ownerId+=r.getOwnerId()+",";
			ownerName+=r.getOwnerName()+",";
		}
		if(ownerId.length()>0)ownerId=ownerId.substring(0,ownerId.length()-1);
		if(ownerName.length()>0)ownerName=ownerName.substring(0,ownerName.length()-1);
		m.put("ownerId", ownerId);
		m.put("ownerName", ownerName);
		
		return m;
	}
	
	public void saveRights(String assignId,int assignType, String[] rightType,String[] ownerId,
			String[] ownerName, int isChange) throws Exception{
		if(assignType==BpmDefRights.SEARCH_TYPE_DEF){
			dao.delByDefKey(assignId);
		}
		else{
			if(isChange==1){
				GlobalType gt=globalTypeDao.getById(new Long(assignId));
				String nodePath=gt.getNodePath();
				List<GlobalType> globalTypelist= globalTypeDao.getByNodePath(nodePath);
				for(GlobalType glbtype:globalTypelist){
					dao.delByTypeId(glbtype.getTypeId());
				}
			}else{
				dao.delByTypeId(new Long(assignId));
			}
		}
		List<BpmDefRights> rightList=coverTypeRights(assignId,assignType, rightType, ownerId, ownerName, isChange);
		add(rightList);
	}
	
	/**
	 * 添加权限。
	 * @param rightList
	 */
	public void add(List<BpmDefRights> rightList){
		if(rightList==null||rightList.size()==0)return;
		for(BpmDefRights r:rightList){
			dao.add(r);
		}
	}
	
	private List<BpmDefRights> coverTypeRights(String assignId,int assignType, String[] rightType,String[] ownerId,
			String[] ownerName, int isChange) throws Exception{
		
		if(ownerId==null||ownerId.length==0)return null;
	
		List<BpmDefRights> list=new ArrayList<BpmDefRights>();
		//对权限类型进行循环。
		for(int i=0;i<rightType.length;i++){
			String right=rightType[i];
			String[] ids=ownerId[i].split(",");
			String[] names=ownerName[i].split(",");
			if(BeanUtils.isEmpty(ids)) continue;
			
			for(int j=0;j<ids.length;j++){
				String id=ids[j];
				String name=names[j];
				if(StringUtil.isEmpty(id)) continue;

				if(assignType==BpmDefRights.SEARCH_TYPE_DEF){
					BpmDefRights defRight=new BpmDefRights();
					defRight.setId(UniqueIdUtil.genId());
					defRight.setDefKey(assignId);
					defRight.setSearchType(BpmDefRights.SEARCH_TYPE_DEF);
					defRight.setRightType(new Short(right));
					defRight.setOwnerId(new Long(id));
					defRight.setOwnerName(name);
					list.add(defRight);
				}
				else{
					if(isChange==1){
						GlobalType gt=globalTypeDao.getById(new Long(assignId));
						String nodePath=gt.getNodePath();
						List<GlobalType> globalTypelist= globalTypeDao.getByNodePath(nodePath);
						for(GlobalType glbtype:globalTypelist){
							BpmDefRights defRight=new BpmDefRights();
							defRight.setId(UniqueIdUtil.genId());
							defRight.setFlowTypeId(glbtype.getTypeId());
							defRight.setSearchType(BpmDefRights.SEARCH_TYPE_GLT);
							defRight.setRightType(new Short(right));
							defRight.setOwnerId(new Long(id));
							defRight.setOwnerName(name);
							list.add(defRight);
						}
					}else{
						BpmDefRights defRight=new BpmDefRights();
						defRight.setId(UniqueIdUtil.genId());
						defRight.setFlowTypeId(new Long(assignId));
						defRight.setSearchType(BpmDefRights.SEARCH_TYPE_GLT);
						defRight.setRightType(new Short(right));
						defRight.setOwnerId(new Long(id));
						defRight.setOwnerName(name);
						list.add(defRight);
					}
				}
			}
			
		}
		return list;
	}
	
}
