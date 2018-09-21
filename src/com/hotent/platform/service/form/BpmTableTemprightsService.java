package com.hotent.platform.service.form;

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
import com.hotent.platform.model.bpm.BpmDefRights;
import com.hotent.platform.model.form.BpmTableTemprights;
import com.hotent.platform.dao.form.BpmTableTemprightsDao;

/**
 * 对象功能:查看业务数据模板的权限信息 Service类 开发公司: 开发人员:heyifan 创建时间:2012-05-28
 * 09:04:06
 */
@Service
public class BpmTableTemprightsService extends BaseService<BpmTableTemprights> {
	@Resource
	private BpmTableTemprightsDao dao;

	public BpmTableTemprightsService() {
	}

	/**
	 * 获取该业务数据模板的授权信息
	 * 
	 * @param templateId
	 * @return
	 */
	public Map<String, Map<String, String>> getRights(Long id, int assignType) {
		Map<String, Map<String, String>> rightsMap = new HashMap<String, Map<String, String>>();
		List<BpmTableTemprights> list = null;
		if (assignType == BpmTableTemprights.SEARCH_TYPE_DEF) {
			list = dao.getByTemplateId(id);
		} else {
			list = dao.getByCategoryId(id);
		}		
		List<BpmTableTemprights> user = new ArrayList<BpmTableTemprights>();
		List<BpmTableTemprights> role = new ArrayList<BpmTableTemprights>();
		List<BpmTableTemprights> org = new ArrayList<BpmTableTemprights>();
		for (BpmTableTemprights rights : list) {
			switch (rights.getRightType()) {
			case BpmDefRights.RIGHT_TYPE_USER:
				user.add(rights);
				break;
			case BpmDefRights.RIGHT_TYPE_ROLE:
				role.add(rights);
				break;
			default:
				org.add(rights);
				break;
			}
		}
		Map<String, String> userMap = coverList2Map(user);
		Map<String, String> roleMap = coverList2Map(role);
		Map<String, String> orgMap = coverList2Map(org);
		rightsMap.put("user", userMap);
		rightsMap.put("role", roleMap);
		rightsMap.put("org", orgMap);
		return rightsMap;
	}

	private Map<String, String> coverList2Map(List<BpmTableTemprights> list) {
		Map<String, String> m = new HashMap<String, String>();
		if (BeanUtils.isEmpty(list))
			return m;

		String ownerId = "";
		String ownerName = "";
		for (BpmTableTemprights r : list) {
			ownerId += r.getOwnerId() + ",";
			ownerName += r.getOwnerName() + ",";
		}
		if (ownerId.length() > 0)
			ownerId = ownerId.substring(0, ownerId.length() - 1);
		if (ownerName.length() > 0)
			ownerName = ownerName.substring(0, ownerName.length() - 1);
		m.put("ownerId", ownerId);
		m.put("ownerName", ownerName);

		return m;
	}

	/**
	 * 保存授权信息
	 * 
	 * @param templateId
	 * @param rightType
	 * @param ownerId
	 * @param ownerName
	 * @throws Exception
	 */
	public void saveRights(Long id, int assignType, String[] rightType, String[] ownerId, String[] ownerName) throws Exception {
		if(assignType==BpmDefRights.SEARCH_TYPE_DEF){
			dao.delByTemplateId(id);
		}
		else{
			dao.delByCategoryId(id);
		}		
		List<BpmTableTemprights> rightList = coverTypeRights(id,assignType ,rightType, ownerId, ownerName);
		add(rightList);
	}

	/**
	 * 添加权限。
	 * 
	 * @param rightList
	 */
	public void add(List<BpmTableTemprights> rightList) {
		if (rightList == null || rightList.size() == 0)
			return;
		for (BpmTableTemprights r : rightList) {
			dao.add(r);
		}
	}

	private List<BpmTableTemprights> coverTypeRights(Long assignId, int assignType,String[] rightType, String[] ownerId, String[] ownerName) throws Exception {

		if(ownerId==null||ownerId.length==0)return null;
		
		List<BpmTableTemprights> list=new ArrayList<BpmTableTemprights>();
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
				
				BpmTableTemprights bpmTableTemprights=new BpmTableTemprights();
				bpmTableTemprights.setId(UniqueIdUtil.genId());
				if(assignType==BpmTableTemprights.SEARCH_TYPE_DEF){
					bpmTableTemprights.setTemplateId(assignId);
					bpmTableTemprights.setSearchType(BpmTableTemprights.SEARCH_TYPE_DEF);
				}
				else{
					bpmTableTemprights.setCategoryId(assignId);
					bpmTableTemprights.setSearchType(BpmTableTemprights.SEARCH_TYPE_GLT);
				}
				bpmTableTemprights.setRightType(new Short(right));
				bpmTableTemprights.setOwnerId(new Long(id));
				bpmTableTemprights.setOwnerName(name);
				list.add(bpmTableTemprights);
			}			
		}
		return list;
	}

	@Override
	protected IEntityDao<BpmTableTemprights, Long> getEntityDao() {
		return dao;
	}
}
