package com.hotent.platform.service.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.dao.form.BpmFormDefDao;
import com.hotent.platform.dao.form.BpmFormFieldDao;
import com.hotent.platform.dao.form.BpmFormRightsDao;
import com.hotent.platform.dao.form.BpmFormTableDao;
import com.hotent.platform.dao.system.SysUserOrgDao;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormField;
import com.hotent.platform.model.form.BpmFormRights;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.system.PositionService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserOrgService;

/**
 * 对象功能:表单权限 Service类。
 * 存储表单字段，子表，意见等权限。
 * 开发公司:
 * 开发人员:xwy
 * 创建时间:2012-02-10 10:48:16
 */
@Service
public class BpmFormRightsService
{
	@Resource
	private BpmFormRightsDao bpmFormRightsDao;	
	@Resource
	private BpmFormTableService bpmFormTableService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private PositionService positionService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysUserOrgDao sysUserOrgDao;
	@Resource
	private BpmFormDefDao bpmFormDefDao;
	/**
	 * 获取默认的权限数据。
	 * 
	 * @param title 
	 * @param memo
	 * @return
	 */
	private JSONObject getPermissionJson(String title,String memo){
		String permissionJson="";
		permissionJson="{title:'"+title+"',memo:'"+memo+"',read:{type:'everyone',id:'', fullname:''},write:{type:'everyone',id:'', fullname:''}}";
		return JSONObject.fromObject(permissionJson);
	}
	
	/**
	 * 保存表单权限。
	 * @param formDefId		表单KEY
	 * @param permission	权限JSON数据。
	 * @throws Exception
	 */
	public void save(long formKey, JSONObject permission) throws Exception {
		JSONArray fieldPermissions = permission.getJSONArray("field");
		JSONArray tablePermissions = permission.getJSONArray("subtable");
		JSONArray opinionPermissions = permission.getJSONArray("opinion");
		List<BpmFormRights> list=new ArrayList<BpmFormRights>();
		//表单字段权限。
		bpmFormRightsDao.delByFormDefId(formKey);
		//添加字段的权限
		if(BeanUtils.isNotEmpty(fieldPermissions)){
			for(Object obj : fieldPermissions) {
				String json=obj.toString();
				JSONObject jsonObj=(JSONObject)obj;
				String name = (String)jsonObj.get("title");
				
				BpmFormRights bpmFormRights = new BpmFormRights(UniqueIdUtil.genId(), formKey, name, json,BpmFormRights.FieldRights);
				list.add(bpmFormRights);
			}
		}
		//子表权限。
		if(BeanUtils.isNotEmpty(tablePermissions)){
			for(Object obj : tablePermissions) {
				String json=obj.toString();
				JSONObject jsonObj=(JSONObject)obj;
				String name = (String)jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(UniqueIdUtil.genId(), formKey, name, json,BpmFormRights.TableRights);
				list.add(bpmFormRights);
			}
		}
		//表单意见权限。
		if(BeanUtils.isNotEmpty(opinionPermissions)){
			for( Object obj :  opinionPermissions) {
				String json=obj.toString();
				JSONObject jsonObj=(JSONObject)obj;
				String name = (String)jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(UniqueIdUtil.genId(), formKey, name, json,BpmFormRights.OpinionRights);
				list.add(bpmFormRights);
			}
		}
		for(BpmFormRights right:list){
			bpmFormRightsDao.add(right);
		}
	}
	
	/**
	 * 保存任务节点权限。
	 * @param actDefId		流程定义id
	 * @param nodeId		流程节点id
	 * @param formDefId		表单定义id
	 * @param permission	权限JSON数据。
	 * @throws Exception
	 */
	public void save(String actDefId,String nodeId, long formKey, JSONObject permission) throws Exception {
		JSONArray fieldPermissions = permission.getJSONArray("field");
		JSONArray tablePermissions = permission.getJSONArray("subtable");
		JSONArray opinionPermissions = permission.getJSONArray("opinion");
		List<BpmFormRights> list=new ArrayList<BpmFormRights>();
		//表单字段权限。
		bpmFormRightsDao.delByFlowFormNodeId(actDefId,nodeId);
		if(BeanUtils.isNotEmpty(fieldPermissions)){
			for(Object obj : fieldPermissions) {
				String json=obj.toString();
				JSONObject jsonObj=(JSONObject)obj;
				String name = (String)jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(UniqueIdUtil.genId(), formKey, name, json,BpmFormRights.FieldRights);
				bpmFormRights.setActDefId(actDefId);
				bpmFormRights.setNodeId(nodeId);
				list.add(bpmFormRights);
			}
		}
		//子表权限。
		if(BeanUtils.isNotEmpty(tablePermissions)){
			for(Object obj : tablePermissions) {
				String json=obj.toString();
				JSONObject jsonObj=(JSONObject)obj;
				String name = (String)jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(UniqueIdUtil.genId(), formKey, name, json,BpmFormRights.TableRights);
				bpmFormRights.setActDefId(actDefId);
				bpmFormRights.setNodeId(nodeId);
				list.add(bpmFormRights);
			}
		}
		//表单意见权限。
		if(BeanUtils.isNotEmpty(opinionPermissions)){
			for( Object obj :  opinionPermissions) {
				String json=obj.toString();
				JSONObject jsonObj=(JSONObject)obj;
				String name = (String)jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(UniqueIdUtil.genId(), formKey, name, json,BpmFormRights.OpinionRights);
				bpmFormRights.setActDefId(actDefId);
				bpmFormRights.setNodeId(nodeId);
				list.add(bpmFormRights);
			}
		}
		for(BpmFormRights right:list){
			bpmFormRightsDao.add(right);
		}
	}
	
	/**
	 * 根据流程定义id，任务节点id和表单id获取权限数据。
	 * @param actDefId		流程定义ID
	 * @param nodeId		任务节点
	 * @param formKey		表单定义ID
	 * @return
	 */
	public  Map<String,List<JSONObject>> getPermissionByFormNode(String actDefId,String nodeId,Long formKey){
		Map<String,List<JSONObject>> map=new HashMap<String, List<JSONObject>>();
		List<BpmFormRights> rightList= bpmFormRightsDao.getByFlowFormNodeId(actDefId, nodeId);
		if(rightList.size()==0){
			rightList = bpmFormRightsDao.getByFormDefId(formKey);
			if(BeanUtils.isEmpty(rightList)){
				BpmFormDef bpmFormDef =bpmFormDefDao.getDefaultVersionByFormKey(formKey);
				map= getPermissionByFormKey(bpmFormDef);
				return map;
			}
		}
		List<JSONObject> fieldJsonList=new ArrayList<JSONObject>();
		List<JSONObject> tableJsonList=new ArrayList<JSONObject>();
		List<JSONObject> opinionJsonList=new ArrayList<JSONObject>();
		for(BpmFormRights rights:rightList){
			switch(rights.getType()){
				case BpmFormRights.FieldRights:
					fieldJsonList.add(JSONObject.fromObject(rights.getPermission()));
					break;
				case BpmFormRights.TableRights:
					tableJsonList.add(JSONObject.fromObject(rights.getPermission()));
					break;
				case BpmFormRights.OpinionRights:
					opinionJsonList.add(JSONObject.fromObject(rights.getPermission()));
					break;
			}
		}
		map.put("field", fieldJsonList);
		map.put("table", tableJsonList);
		map.put("opinion", opinionJsonList);
		return map;
	
	}
	
	/**
	 * 根据表ID和表单id获取表表单的权限。
	 * @param tableId		数据表id
	 * @param formDefId		表单定义ID
	 * @return
	 */
	public Map<String,List<JSONObject>> getPermissionByTableFormKey(Long tableId,Long formKey){
		Map<String,List<JSONObject>> map=new HashMap<String, List<JSONObject>>();
		//有表单的定义
		if(formKey>0){
			BpmFormDef bpmFormDef =bpmFormDefDao.getDefaultVersionByFormKey(formKey);
			//根据表单key获取表单权限列表。
			List<BpmFormRights> rightList = bpmFormRightsDao.getByFormDefId(formKey);
			//权限列表为空并且表单是通过设计生成的情况。
			if(BeanUtils.isEmpty(rightList)){
				map= getPermissionByFormKey(bpmFormDef);
			}
			//已经设置表单的权限。
			else{
				List<JSONObject> fieldJsonList=new ArrayList<JSONObject>();
				List<JSONObject> tableJsonList=new ArrayList<JSONObject>();
				List<JSONObject> opinionJsonList=new ArrayList<JSONObject>();
				for(BpmFormRights rights:rightList){
					switch(rights.getType()){
						case BpmFormRights.FieldRights:
							fieldJsonList.add(JSONObject.fromObject(rights.getPermission()));
							break;
						case BpmFormRights.TableRights:
							tableJsonList.add(JSONObject.fromObject(rights.getPermission()));
							break;
						case BpmFormRights.OpinionRights:
							opinionJsonList.add(JSONObject.fromObject(rights.getPermission()));
							break;
					}
				}
				map.put("field", fieldJsonList);
				map.put("table", tableJsonList);
				map.put("opinion", opinionJsonList);
			}
		}
		//还没有生成表单，没有定义表单权限。
		//则根据表定义获取表单的权限。
		//这里还缺少意见表单权限。
		else{
			BpmFormTable bpmFormTable=bpmFormTableService.getTableById(tableId);
			List<BpmFormField> fieldList= bpmFormTable.getFieldList();
			List<JSONObject> fieldJsonList=new ArrayList<JSONObject>();
			for(BpmFormField field:fieldList){
				JSONObject permission=getPermissionJson(field.getFieldName(), field.getFieldDesc());
				fieldJsonList.add(permission);
			}
			List<JSONObject> tableJsonList=new ArrayList<JSONObject>();
			List<BpmFormTable> tableList=bpmFormTable.getSubTableList();
			for(BpmFormTable table:tableList){
				JSONObject permission=getPermissionJson(table.getTableName(), table.getTableDesc());
				tableJsonList.add(permission);
			}
			map.put("field", fieldJsonList);
			map.put("table", tableJsonList);
		}
		return map;
	}
	
	/**
	 * 获取通过表单设定定义表单的权限数据。
	 * <pre>
	 * 通过设计定义的表单，
	 * 如果没有还没有设置过权限，那么权限信息可以通过解析表单获取。
	 * </pre>
	 * @param formKey
	 * @return
	 */
	public Map<String,List<JSONObject>> getPermissionByFormKey(BpmFormDef bpmFormDef){
		Map<String,List<JSONObject>> map=new HashMap<String, List<JSONObject>>();
		//获取模版。
		String html=bpmFormDef.getHtml();
		
		Long tableId=bpmFormDef.getTableId();
		//读取表。
		BpmFormTable bpmFormTable=bpmFormTableService.getTableById(tableId);
		//主表字段权限
		List<BpmFormField> fieldList= bpmFormTable.getFieldList();
		List<JSONObject> fieldJsonList=new ArrayList<JSONObject>();
		for(BpmFormField field:fieldList){
			JSONObject permission=getPermissionJson(field.getFieldName(), field.getFieldDesc());
			fieldJsonList.add(permission);
		}
		map.put("field", fieldJsonList);
		
		//子表权限。
		List<JSONObject> tableJsonList=new ArrayList<JSONObject>();
		List<BpmFormTable> tableList=bpmFormTable.getSubTableList();
		
		for(BpmFormTable table:tableList){
			JSONObject permission=getPermissionJson(table.getTableName(), table.getTableDesc());
			tableJsonList.add(permission);
		}
		
		map.put("table", tableJsonList);
		
		//意见权限。
		List<JSONObject> opinionJsonList=new ArrayList<JSONObject>();
		Map<String,String> opinionMap=FormUtil.parseOpinion(html);
		Set<Entry< String,String>> set= opinionMap.entrySet();
		for(Iterator<Entry< String,String>> it=set.iterator();it.hasNext();){
			Entry< String,String> tmp=it.next();
			JSONObject permission=getPermissionJson(tmp.getKey(), tmp.getValue());
			opinionJsonList.add(permission);
		}
		map.put("opinion", opinionJsonList);
			
		
		return map;
	}
	
	/**
	 * 根据用户获得权限。
	 * <pre>
	 * field：{"NAME": "w", "SEX": "r"}
	 * table：{"TABLE1": "r", "TABLE2": "w"}
	 * opinion：{"领导意见": "w", "部门意见": "r"}
	 * </pre>
	 * @param formDefId
	 * @param userId
	 * @return
	 * @throws DocumentException
	 */
	public Map<String, Map<String, String>> getByFormKeyAndUserId(Long formKey, Long userId,String actDefId,String nodeId)  {
		List<BpmFormRights> rightList = null;
		//如果流程定义id和任务节点id不为空那么获取节点的权限。
		if(StringUtil.isNotEmpty(actDefId) && StringUtil.isNotEmpty(nodeId)){
			rightList = bpmFormRightsDao.getByFlowFormNodeId(actDefId, nodeId);
		}
		if(BeanUtils.isEmpty(rightList)){
			rightList = bpmFormRightsDao.getByFormDefId(formKey);
		}
		List<ISysRole> roles = sysRoleService.getByUserId(userId);
		List<Position> positions = positionService.getByUserId(userId);
		List<ISysOrg> orgs = sysOrgService.getOrgsByUserId(userId);
		List<SysUserOrg> ownOrgs = sysUserOrgDao.getChargeByUserId(userId);
		
		Map<String, Map<String, String>> permissions = new HashMap<String, Map<String, String>>();
		
		Map<String, String> fieldPermission = new HashMap<String, String>();
		Map<String, String> tablePermission = new HashMap<String, String>();
		Map<String, String> opinionPermission = new HashMap<String, String>();
		
		
		for(BpmFormRights rights : rightList) {
			JSONObject permission=JSONObject.fromObject(rights.getPermission());
			String name=rights.getName().toLowerCase();
			//取得权限
			String right=getRight(permission, roles, positions, orgs, ownOrgs,userId);
			
			switch(rights.getType()){
				case BpmFormRights.FieldRights:
					fieldPermission.put(name, right);
					break;
				case BpmFormRights.TableRights:
					tablePermission.put(name, right);
					break;
				case BpmFormRights.OpinionRights:
					opinionPermission.put(name, right);
					break;
			}
		}
		permissions.put("field", fieldPermission);
		permissions.put("table", tablePermission);
		permissions.put("opinion", opinionPermission);
	
		return permissions;
	}
	
	/**
	 * 获取表单只读权限	 
	 * @param formKey 表单key
	 * @return
	 */
	public Map<String, Map<String, String>> getByFormKey(BpmFormDef bpmFormDef)  {
		
		Map<String, Map<String, String>> permissions = new HashMap<String, Map<String, String>>();		
		Map<String, String> fieldPermission = new HashMap<String, String>();
		Map<String, String> tablePermission = new HashMap<String, String>();
		Map<String, String> opinionPermission = new HashMap<String, String>();
		String right="r";
		
		String html=bpmFormDef.getHtml();		
		Long tableId=bpmFormDef.getTableId();
		//读取表。
		BpmFormTable bpmFormTable=bpmFormTableService.getTableById(tableId);
		//主表字段权限
		List<BpmFormField> fieldList= bpmFormTable.getFieldList();
		for(BpmFormField field:fieldList){
			fieldPermission.put(field.getFieldName().toLowerCase(), right);
		}
		permissions.put("field", fieldPermission);
		
		//子表权限。
		List<BpmFormTable> tableList=bpmFormTable.getSubTableList();
		
		for(BpmFormTable table:tableList){
			tablePermission.put(table.getTableName().toLowerCase(), right);
		}
		permissions.put("table", tablePermission);
		
		//意见权限。
		Map<String,String> opinionMap=FormUtil.parseOpinion(html);
		Set<Entry< String,String>> set= opinionMap.entrySet();
		for(Iterator<Entry< String,String>> it=set.iterator();it.hasNext();){
			Entry< String,String> tmp=it.next();
			opinionPermission.put(tmp.getKey().toLowerCase(), right);
		}
		permissions.put("opinion", opinionPermission);
		return permissions;
	}	
	
	/**
	 * 获取权限。
	 * @param jsonObject 数据格式为：{'title':'orderId','memo':'订单ID','read':{'type':'everyone','id':'', 'fullname':''},'write':{'type':'everyone','id':'', 'fullname':''}}
	 * @param roles 用户所属的角色
	 * @param positions 用户所属的岗位
	 * @param orgs	用户所在的组织
	 * @param ownOrgs	组织负责人
	 * @param userId	用户ID
	 * @return
	 */
	private String getRight(JSONObject jsonObject, List<ISysRole> roles, 
			List<Position> positions, List<ISysOrg> orgs, 
			List<SysUserOrg> ownOrgs,Long userId) {
		String right = "";
		if(hasRight(jsonObject, "write", roles, positions, orgs, ownOrgs,userId)) {
			right = "w";
		} else if(hasRight(jsonObject, "read", roles, positions, orgs, ownOrgs,userId)) {
			right = "r";
		}
		return right;
	}
	
	/**
	 * 判断是否有权限。
	 * @param permission	权限JSONOjbect
	 * @param mode			读或写 (write,read)
	 * @param roles			角色列表
	 * @param positions		岗位
	 * @param orgs			组织
	 * @param ownOrgs		组织负责人
	 * @param userId		用户ID
	 * @return
	 */
	private boolean hasRight(JSONObject permission, String mode, List<ISysRole> roles, 
			List<Position> positions, List<ISysOrg> orgs,
			List<SysUserOrg> ownOrgs,Long userId) {
		boolean hasRight = false;
		JSONObject  node = permission.getJSONObject(mode);
		String type = node.get("type").toString();
		String id = node.get("id").toString();
		if("none".equals(type)){
			return false;
		}
		if ("everyone".equals(type)) {
			return true;
		}
		//指定用户
		if("user".equals(type)) {
			hasRight = contain( id,userId.toString());
			return hasRight;
		}
		//指定角色
		else if ("role".equals(type)) {
			for(ISysRole role : roles) {
				if(contain( id,role.getRoleId().toString())) {
					return true;
				}
			}
		}
		//指定组织
		else if ("org".equals(type)) {
			for(ISysOrg org : orgs) {
				if(contain (id,org.getOrgId().toString())) {
					return true;
				}
			}
		}
		//组织负责人
		else if ("orgMgr".equals(type)) {
			for(SysUserOrg sysUserOrg : ownOrgs) {
				if(contain(id,sysUserOrg.getOrgId().toString())) {
					return true;
				}
			}
		}
		//岗位
		else if ("pos".equals(type)) {
			for(Position position : positions) {
				if(contain(id,position.getPosId().toString())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判定逗号分隔的字符串是否包括指定的字符。
	 * @param ids
	 * @param curId
	 * @return
	 */
	private boolean contain(String ids,String curId){
		if(StringUtil.isEmpty(ids)) return false;
		String[] aryId=ids.split(",");
		for(String id:aryId){
			if(id.equalsIgnoreCase(curId)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据formkey删除表单权限
	 * @param formKey
	 */
	public void deleteByFormKey(Long formKey){
		bpmFormRightsDao.delByFormDefId(formKey);
	}
	
	/**
	 * 根据表id删除表单的权限。
	 * @param tableId
	 */
	public void deleteByTableId(Long tableId){
		bpmFormRightsDao.deleteByTableId(tableId);
	}

	
}
