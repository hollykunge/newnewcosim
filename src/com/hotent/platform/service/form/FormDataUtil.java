package com.hotent.platform.service.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.customertable.TableModel;
import com.hotent.core.keygenerator.impl.TimeGenerator;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.DateUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.dao.form.BpmFormFieldDao;
import com.hotent.platform.dao.form.BpmFormHandlerDao;
import com.hotent.platform.dao.form.BpmFormTableDao;
import com.hotent.platform.model.form.BpmFormData;
import com.hotent.platform.model.form.BpmFormField;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.form.PkValue;
import com.hotent.platform.model.form.SqlModel;
import com.hotent.platform.model.form.SubTable;
import com.hotent.platform.model.form.TableRelation;
import com.hotent.platform.service.bpm.thread.TaskUserAssignService;

/**
 * 将json数据转换为FormData对象数据。
 * @author ray
 *
 */
public class FormDataUtil {
	private static Log logger = LogFactory.getLog(FormDataUtil.class);
	
	/**
	 * 传入BpmFormData对象，解析成sqlmodel列表数据。
	 * @param formData
	 * @param actDefId
	 * @param nodeId
	 * @return
	 */
	public static List<SqlModel> parseSql(BpmFormData formData,String actDefId,String nodeId){
		PkValue pkValue=formData.getPkValue();
		boolean isAdd=pkValue.getIsAdd();
		List<SqlModel> list=new ArrayList<SqlModel>();
		String key=TableModel.PK_COLUMN_NAME.toLowerCase();
		if(isAdd){
			//插入数据
			SqlModel sqlModel=getInsert(formData.getTableName(), formData.getMainFields());
			list.add(sqlModel);
			
			//插入子表数据
			for(SubTable subTable:formData.getSubTableList()){
				String tableName=subTable.getTableName();
				List<Map<String,Object>> dataList= subTable.getDataList();
				for(Map<String,Object> row:dataList){
					//获取主键。
					row.put(key, UniqueIdUtil.genId());
					
					SqlModel subSqlModel=getInsert(tableName, row);
					list.add(subSqlModel);
				}
			}
			
		}else{
			//更新主表数据
			SqlModel sqlModel=getUpdate(formData);
			if(sqlModel!=null){
				list.add(sqlModel);
			}
			//处理子表数据。
			for(SubTable subTable:formData.getSubTableList()){
				String tableName=subTable.getTableName();
				BpmFormHandlerDao bpmFormHandlerDao=(BpmFormHandlerDao)AppUtil.getBean(BpmFormHandlerDao.class);
				BpmFormTableDao bpmFormTableDao=(BpmFormTableDao)AppUtil.getBean(BpmFormTableDao.class);
				String name=tableName.replaceFirst(TableModel.CUSTOMER_TABLE_PREFIX, "");
				BpmFormTable table= bpmFormTableDao.getByTableName(name);
				//原来的数据。
				List<Map<String, Object>> subDataList= bpmFormHandlerDao.getByFk(table, pkValue.getValue().toString(), actDefId, nodeId);
				List<Map<String, Object>> curDataList=subTable.getDataList();
				
				//更新插入。
				List<SqlModel> updDelList= getUpdDelByList( tableName, curDataList,subDataList);
				
				//插入数据。
				List<SqlModel> insertList= getInsertByList(tableName,curDataList);
				
				list.addAll(insertList);
				list.addAll(updDelList);
			}
			
		}
		
		return list;
	}
	
	/**
	 * 获取可以插入的数据。
	 * @param tableName
	 * @param list
	 * @return
	 */
	private static List<SqlModel> getInsertByList(String tableName,List<Map<String, Object>> list){
		List<SqlModel> rtnList=new ArrayList<SqlModel>();
		String key=TableModel.PK_COLUMN_NAME.toLowerCase();
		for(Map<String, Object> map:list){
			//数据是否包含pk字段。
			if(map.containsKey(key)){
				String value=map.get(key).toString();
				//包含且值为零。
				if("0".equals(value)){
					Map<String, Object> tmp=map;
					tmp.put(key, UniqueIdUtil.genId());
					
					SqlModel model= getInsert(tableName, tmp);
					rtnList.add(model);
				}
			}
		}
		return rtnList;
		
	}
	
	/**
	 * 获取更新或者删除的SqlModel列表。
	 * @param tableName
	 * @param curList
	 * @param originList
	 * @return
	 */
	private static List<SqlModel> getUpdDelByList(String tableName,List<Map<String, Object>> curList,List<Map<String, Object>> originList){
		List<SqlModel> rtnList=new ArrayList<SqlModel>();
		Map<String,Map<String, Object>> curMap=convertMap(curList);
		Map<String,Map<String, Object>> originMap=convertMap(originList);
		Set<Entry<String, Map<String, Object>>> curSet= curMap.entrySet();
		
		for(Iterator<Entry<String, Map<String, Object>>> it=curSet.iterator();it.hasNext();){
			Entry<String, Map<String, Object>> ent=it.next();
			//原数据包含当前的数据，则更新。
			if(originMap.containsKey(ent.getKey())){
				SqlModel updSqlModel=getUpd(tableName, ent.getValue());
				if(updSqlModel!=null){
					rtnList.add(updSqlModel);
				}
			}
		}
		
		Set<Entry<String, Map<String, Object>>> originSet= originMap.entrySet();
		for(Iterator<Entry<String, Map<String, Object>>> it=originSet.iterator();it.hasNext();){
			Entry<String, Map<String, Object>> ent=it.next();
			//当前数据不包含之前的数据，则需要删除
			if(!curMap.containsKey(ent.getKey())){
				String delSql="delete from " + tableName +" where id=?";
				SqlModel sqlModel=new SqlModel(delSql, new Object[]{ent.getKey()} );
				rtnList.add(sqlModel);	
			}
		}
		return rtnList;
	}
	
	/**
	 * 将列表转化为map对象，不包含新增的数据。
	 * @param list
	 * @return
	 */
	private static Map<String,Map<String, Object>> convertMap(List<Map<String, Object>> list){
		String key=TableModel.PK_COLUMN_NAME.toLowerCase();
		Map<String,Map<String, Object>> rtnMap=new HashMap<String, Map<String,Object>>();
		for(Map<String, Object> map:list){
			if(!map.containsKey(key)) continue;
			String value=map.get(key).toString();
			if("0".equals(value)) continue;
			rtnMap.put(value, map);
		}
		return rtnMap;
	}
	
	
	
	/**
	 * 将json数据解析为BpmFormData。
	 * @param json
	 * @param pkValue
	 * @return
	 * @throws Exception
	 */
	public static  BpmFormData parseJson(String json,PkValue pkValue) throws Exception{
		
		BpmFormTableDao bpmFormTableDao=(BpmFormTableDao)AppUtil.getBean("bpmFormTableDao");
		BpmFormFieldDao bmpFormFieldDao=(BpmFormFieldDao)AppUtil.getBean("bpmFormFieldDao");
		
		JSONObject jsonObj= JSONObject.fromObject(json);
		//主表 main
		JSONObject mainTable=jsonObj.getJSONObject("main");
		
		BpmFormData bpmFormData=new BpmFormData();
		
		long tableId=mainTable.getLong("tableId");
		//主表定义
		BpmFormTable mainTableDef=bpmFormTableDao.getById(tableId);
		
		//获取子表
		List<BpmFormTable> listTable=bpmFormTableDao.getSubTableByMainTableId(tableId);
		
		if(pkValue==null)
			pkValue=getPk(mainTableDef);
		
		bpmFormData.setPkValue(pkValue);
		
		//处理主表
		handleMain(jsonObj,bpmFormData,mainTableDef,bmpFormFieldDao);
		//子表
		handSub(jsonObj,listTable,bmpFormFieldDao,mainTableDef,bpmFormData);
		
		//意见
		handOpinion(bpmFormData, jsonObj);
		
		
		return bpmFormData;
	}
	
	/**
	 * 
	 * 表单提交的数据格式。
	 * <pre>
	 * {
	 *		main: {
	 *			tableId: 123456789,
	 *			tableName: 'TB',
	 *			fields:{"字段1":"1","字段2":"2"}
	 *		},
	 *		sub: [
	 *			{
	 *				tableName: 'TB',
	 *				fields: [
	 *					{"字段1":"1","字段2":"2"},
	 *					{"字段1":"3","字段2":"4"}
	 *				]
	 *			},
	 *			{
	 *				tableName: 'TB',
	 *				fields: [
	 *					{"字段1":"1","字段2":"2"},
	 *					{"字段1":"3","字段2":"4"}
	 *				]
	 *			},
	 *		],
	 *		opinion: [
	 *			{name:"意见表单名1",value:"意见"},
	 *			{name:"意见表单名1",value:"意见"}
	 *		]
	 *	}
     *</pre>
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	public static  BpmFormData parseJson(String json) throws Exception{
		return parseJson( json,null);
	}
	
	
	
	/**
	 * 处理主表的数据
	 * @param jsonObj
	 * @param bpmFormData
	 * @param mainTableDef
	 * @param bmpFormFieldDao
	 * @return
	 * @throws Exception
	 */
	private static void handleMain(JSONObject jsonObj,BpmFormData bpmFormData,BpmFormTable mainTableDef,
			BpmFormFieldDao bmpFormFieldDao) throws Exception{

		//主表 main
		JSONObject mainTable=jsonObj.getJSONObject("main");
		
		long tableId=mainTableDef.getTableId();
		
		List<BpmFormField> mainFields=bmpFormFieldDao.getByTableIdContainHidden(tableId);
		
		Map<String, BpmFormField> mainFieldTypeMap= convertFieldToMap(mainFields);
		//是否外部表
		int isExternal=mainTableDef.getIsExternal();
		//表前缀
		String tablePrefix=(isExternal==1)?"":TableModel.CUSTOMER_TABLE_PREFIX;
		//列前缀
		String colPrefix=(isExternal==1)?"":TableModel.CUSTOMER_COLUMN_PREFIX;
		//主键
		//PkValue pkValue=getPk(mainTableDef, bpmFormData.getMainFields());
		//主表
		String mainTableName=mainTableDef.getTableName();
		//主表字段
		JSONObject mainFieldJson=mainTable.getJSONObject("fields");
		//主表
		bpmFormData.setTableId(tableId);
		bpmFormData.setTableName(tablePrefix + mainTableName);
	
		//将主表JSON转换成map数据。
		Map<String, Object> mainFiledsData=handleRow(colPrefix, mainFieldTypeMap, mainFieldJson);
		//添加主表数据
		bpmFormData.addMainFields(mainFiledsData);
		
		PkValue pkValue=bpmFormData.getPkValue();
		
		bpmFormData.addMainFields(pkValue.getName(), pkValue.getValue());
		//只有在添加的时候才进行计算。
		if(pkValue.getIsAdd()){
			//获取需要通过脚本结算的字段。
			List<BpmFormField> mapFormField=getFieldsFromScript(mainFields);
			//通过脚本引擎计算字段。
			Map<String, Object> map= caculateField(colPrefix,mapFormField,bpmFormData.getMainFields());
			
			bpmFormData.addMainFields(map);
		}
		//设置主键数据
//		bpmFormData.setPkValue(pkValue);
		//设置流程变量
		Map<String, Object> variables=getVariables(mainFieldJson, mainFields);
		bpmFormData.setVariables(variables);
		
		List<TaskExecutor> formUsers = getFormUsers(colPrefix,mainFields,bpmFormData.getMainFields());
		TaskUserAssignService.setFormUsers(formUsers);
//		return pkValue;
	}
	
	/**
	 * 获取流程变量。
	 * @param jsonObject
	 * @param list
	 * @return
	 */
	private static Map<String, Object> getVariables(JSONObject jsonObject,List<BpmFormField> list ){
		Map<String,Object> map=new HashMap<String, Object>();
		Map<String,BpmFormField> fieldsMap= convertFieldToMap(list);
		for(Iterator<String> it=jsonObject.keys();it.hasNext();){
			String key=it.next();
			String lowerKey=key.toLowerCase();
			BpmFormField field=fieldsMap.get(lowerKey);
			if(field!=null && field.getIsFlowVar()==1){
				String value=(String)jsonObject.get(key);
				Object obj= convertType(value, field);
				map.put(key, obj);
			}
		}
		return map;
	}
	
	/**
	 * 处理子表数据。
	 * @param jsonObj
	 * @param subTableMap
	 * @param bmpFormFieldDao
	 * @param mainTableDef
	 * @param bpmFormData
	 * @param pkValue
	 * @throws Exception 
	 */
	private static void handSub(JSONObject jsonObj,List<BpmFormTable> listTable,
			BpmFormFieldDao bmpFormFieldDao,BpmFormTable mainTableDef,BpmFormData bpmFormData) throws Exception{
		Map<String, Long> subTableMap= convertTable(listTable);
		Map<String,BpmFormTable> formTableMap= convertTableMap(listTable);
		
		//是否外部表
		int isExternal=mainTableDef.getIsExternal();
		//表前缀
		String tablePrefix=(isExternal==1)?"":TableModel.CUSTOMER_TABLE_PREFIX;
		//列前缀
		String colPrefix=(isExternal==1)?"":TableModel.CUSTOMER_COLUMN_PREFIX;
		//子表
		JSONArray arySub=jsonObj.getJSONArray("sub");
		//子表
		for(int i=0;i<arySub.size();i++){
			SubTable subTable=new SubTable();
			JSONObject subTableObj=arySub.getJSONObject(i);
			String subName=subTableObj.getString("tableName").toLowerCase();
			Long subTableId=subTableMap.get(subName);
			if(subTableId==null ) continue;
				
			BpmFormTable bpmFormTable=formTableMap.get(subName);
			
			List<BpmFormField> subTableFields=bmpFormFieldDao.getByTableId(subTableId);
			Map<String,BpmFormField> subTableTypeMap= convertFieldToMap(subTableFields);
			//获取需要计算的脚本数据。
			List<BpmFormField> scriptFields=getFieldsFromScript(subTableFields);
			
			subTable.setTableName(tablePrefix +subName);
			
			if(isExternal==1){
				TableRelation tableRelation= mainTableDef.getTableRelation();
				Map<String, String> mapRelation= tableRelation.getRelations();
				String fk=mapRelation.get(subName);
				String pk=bpmFormTable.getPkField();
				subTable.setPkName(pk);
				subTable.setFkName(fk);
			}
			else{
				subTable.setPkName(TableModel.PK_COLUMN_NAME);
				subTable.setFkName(TableModel.FK_COLUMN_NAME);
			}
			
			
			JSONArray arySubFields=subTableObj.getJSONArray("fields");
			for(int j=0;j<arySubFields.size();j++){
				JSONObject subFieldObj= arySubFields.getJSONObject(j);
				Map<String, Object> subRow = handleRow(colPrefix,subTableTypeMap, subFieldObj);
				//计算脚本字段。
				Map<String, Object> map= caculateField(colPrefix,scriptFields,subRow);
				
				subRow.putAll(map);
				//处理主键数据
				handFkRow(mainTableDef,bpmFormTable,subRow,bpmFormData.getPkValue());
				//处理需要计算的数据。
				subTable.addRow(subRow);
				
				List<TaskExecutor> formUsers = getFormUsers(colPrefix,subTableFields,subRow);
				TaskUserAssignService.addFormUsers(formUsers);
			}
			bpmFormData.addSubTable(subTable);
		}
	}
	
	
	/**
	 * 处理意见数据。
	 * @param bpmFormData
	 * @param jsonObj
	 */
	private static void handOpinion(BpmFormData bpmFormData,  JSONObject jsonObj){
		//意见
		JSONArray aryOpinion=jsonObj.getJSONArray("opinion");
		//意见 opinion
		for(int i=0;i<aryOpinion.size();i++){
			JSONObject opinion=aryOpinion.getJSONObject(i);
			String formName=opinion.getString("name");
			String value=opinion.getString("value");
			bpmFormData.addOpinion(formName, value);
		}
	}
	
	/**
	 * 处理子表行数据的主键和外键。
	 * <pre>
	 * 添加子表的主键和外键。
	 * </pre>
	 * @param mainTabDef		主表定义。
	 * @param bpmFormTable		子表定义。
	 * @param rowData			子表一行数据。
	 * @param pkValue			主键数据。
	 * @throws Exception
	 */
	private static void handFkRow(BpmFormTable mainTabDef,BpmFormTable bpmFormTable, 
			Map<String, Object> rowData,PkValue pkValue) throws Exception{
		int isExternal=bpmFormTable.getIsExternal();
		//外部表数据
		if(isExternal==1){
			PkValue pk=getPk(bpmFormTable);
			if(pk.getValue().toString().equals("-1")){
				logger.debug("外键值不能为-1,请设置主表的主键生成规则!");
				throw new Exception("外键值不能为-1,请设置主表的主键生成规则!");
			}
			rowData.put(pk.getName(), pk.getValue());
			//取得外键关联。
			TableRelation tableRelation = mainTabDef.getTableRelation();
			Map<String,String> relation= tableRelation.getRelations();
			String fk=relation.get(bpmFormTable.getTableName());
			rowData.put(fk, pkValue.getValue());
		}
		//本地表数据
		else{
//			Object pk=new TimeGenerator().nextId();
//			rowData.put(TableModel.PK_COLUMN_NAME, pk);
			rowData.put(TableModel.FK_COLUMN_NAME, pkValue.getValue());
		}
	
	}
	
	/**
	 * 取得值需要结算的字段。
	 * @param list
	 * @return
	 */
	private static List<BpmFormField> getFieldsFromScript(List<BpmFormField> list){
		List<BpmFormField> map=new ArrayList<BpmFormField>();
		for(BpmFormField field:list){
			//通过后台运算
			if(field.getValueFrom()==2)
				map.add(field);
		}
		return map;
	}
	
	/**
	 * 取得作为表单用户的字段值
	 * @param list
	 * @return
	 */
	private static List<TaskExecutor> getFormUsers(String colPrefix,List<BpmFormField> list,Map<String,Object> data){
		List<TaskExecutor> formUsers=new ArrayList<TaskExecutor>();
		for(BpmFormField field:list){
			Map<String,String> property = field.getPropertyMap();
			if(!property.containsKey("isformuser")) continue;
			if(!property.get("isformuser").equals("1"))  continue;
			//作为表单用户的字段
			String idKey = colPrefix + field.getFieldName() +"ID";
			String nameKey = colPrefix + field.getFieldName() ;
			
			//人员单选和人员多选
			if(field.getControlType()==BpmFormField.Selector_User||field.getControlType()==BpmFormField.Selector_UserMulti){
				
				Object userIds=data.get(idKey);
				Object userNames=data.get(nameKey);
				if(userIds==null)continue;
				String[] aryUserId = userIds.toString().split(",");
				String[] aryUserName = userNames.toString().split(",");
				for(int i=0;i<aryUserId.length;i++){
					String userId=aryUserId[i];
					String userName=aryUserName[i];
					if(StringUtil.isNotEmpty(userId)){
						formUsers.add(TaskExecutor.getTaskUser(userId, userName));
					}
				}
				
			}
			//部门选择
			if(field.getControlType()==BpmFormField.Selector_Org){
				Object orgIds=data.get(idKey);
				Object orgNames=data.get(nameKey);
				if(orgIds==null)continue;
				String[] aryOrgId = orgIds.toString().split(",");
				String[] aryOrgName = orgNames.toString().split(",");
				
				for(int i=0;i<aryOrgId.length;i++){
					String orgId=aryOrgId[i];
					if(StringUtil.isNotEmpty(orgId)){
						String orgName=aryOrgName[i];
						formUsers.add(TaskExecutor.getTaskOrg(orgId, orgName));
					}
				}
			}
		}
		return formUsers;
	}
	
	/**
	 * 计算值从脚本的来的值。
	 * @param colPrefix		列前缀。
	 * @param fields		子表字段。
	 * @param data			子表的一行数据。
	 * @return
	 */
	private static Map<String, Object> caculateField(String colPrefix,List<BpmFormField> fields,Map<String, Object> data){
		Map<String, Object> result=new HashMap<String, Object>();
		for(BpmFormField field:fields){
			//实际字段名称
			String name=colPrefix + field.getFieldName();
			//获取字段脚本。
			String script=field.getScript();
			Object value= FormUtil.calcuteField(script, data,colPrefix);
			result.put(name, value);
		}
		return result;
	}

	
	/**
	 * 直接产生新的主键。
	 * @param bpmFormTable
	 * @return
	 * @throws Exception
	 */
	private static PkValue getPk(BpmFormTable bpmFormTable) throws Exception{
		Object pkValue=null;
		String pkField=TableModel.PK_COLUMN_NAME;
		if(bpmFormTable.getIsExternal()==1){
			pkField=bpmFormTable.getPkField();
		}
		//外部表根据规则创建主键。
		if(bpmFormTable.getIsExternal()==1){
			Short keyType=bpmFormTable.getKeyType();
			String keyValue=bpmFormTable.getKeyValue();
			pkValue=FormUtil.getKey(keyType, keyValue);
		}
		else{
			pkValue=new TimeGenerator().nextId();
		}
	
		PkValue pk=new PkValue();
		pk.setIsAdd(true);
		pk.setName(pkField);
		pk.setValue(pkValue);
		return pk;
	}

	/**
	 * 将json转换为Map对象。
	 * @param colPrefix			列前缀。
	 * @param subTableTypeMap	字段和类型映射。
	 * @param subFieldObj		json对象。
	 * @return
	 */
	private static Map<String, Object> handleRow(String colPrefix, Map<String, BpmFormField> fieldTypeMap, JSONObject fieldsJsonObj) {
		Map<String, Object> row=new HashMap<String, Object>();
		//fieldsJsonObj.keys()
		//对字段名称进行遍历
		for(Iterator<String> it=fieldsJsonObj.keys();it.hasNext();){
			String key=it.next();
			Object obj=fieldsJsonObj.get(key);
			String value="";
			if(obj instanceof JSONArray || obj instanceof JSONObject) {
				value=obj.toString();
			}
			else{
				value=(String)obj;
			}
			
			//提交的是主键的情况。
			if(TableModel.PK_COLUMN_NAME.toLowerCase().equals(key)){
				//如果主键为空的情况，表示数据为新增。
				//填入当前人和当前人的组织。
				if(StringUtil.isEmpty(value)){
					value="0";
					row.put(TableModel.CUSTOMER_COLUMN_CURRENTUSERID, ContextUtil.getCurrentUserId());
					Long orgId=0L;
					ISysOrg sysOrg=ContextUtil.getCurrentOrg();
					if(sysOrg!=null){
						orgId=sysOrg.getOrgId();
					}
					row.put(TableModel.CUSTOMER_COLUMN_CURRENTORGID, orgId);
				}
			}
			
			//列为主键的情况。
			if(TableModel.PK_COLUMN_NAME.toLowerCase().equals(key)){
				row.put(key,new Long( value));
			}
			else{
				BpmFormField bpmFormField=fieldTypeMap.get(key.toLowerCase());
				//转换数据类型
				if(bpmFormField!=null){
					Object convertValue=convertType(value, bpmFormField);
					row.put(colPrefix +key, convertValue);
				}
			}
			
		}
		return row;
	}
	
	/**
	 * 转换数据类型。
	 * @param strValue
	 * @param type
	 * @return
	 */
	private static Object convertType(String strValue, BpmFormField formField){
		String type=formField.getFieldType();
		if(StringUtil.isEmpty(strValue)) return null;
		Object value = strValue;
		if (ColumnModel.COLUMNTYPE_DATE.equals(type)){
			value = DateUtil.parseDate((String) strValue);
		}
		else if(ColumnModel.COLUMNTYPE_NUMBER.equals(type)) {
			//属于小数类型。
			if(formField.getDecimalLen()>0){
				value=Double.parseDouble(strValue);
			}
			//整数型的处理。
			else{
				if(formField.getIntLen()<=10){
					value=Integer.parseInt(strValue);
				}
				else{
					value=Long.parseLong(strValue);
				}
			}
		} 
		
		return value;
	}
	
	
	/**
	 * 将字段列表转成字段map。
	 * @param list
	 * @return
	 */
	private static Map<String, BpmFormField> convertFieldToMap(List<BpmFormField>  list){
		Map<String, BpmFormField> map=new HashMap<String, BpmFormField>();
		for(Iterator<BpmFormField> it=list.iterator();it.hasNext();){
			BpmFormField field=it.next();
			map.put(field.getFieldName().toLowerCase(), field);
		}
		return map;
	}

	
	/**
	 * 将列表转换成map。
	 * 键：表名，值：表ID。
	 * @param list
	 * @return
	 */
	private static Map<String, Long> convertTable(List<BpmFormTable> list){
		Map<String,Long> map=new HashMap<String, Long>();
		for(BpmFormTable tb:list){
			map.put(tb.getTableName().toLowerCase(), tb.getTableId());
		}
		return map;
	}
	
	/**
	 * 将列表定义转换成Map对象。
	 * @param list
	 * @return
	 */
	private static Map<String, BpmFormTable> convertTableMap(List<BpmFormTable> list){
		Map<String,BpmFormTable> map=new HashMap<String, BpmFormTable>();
		for(BpmFormTable tb:list){
			map.put(tb.getTableName().toLowerCase(), tb);
		}
		return map;
	}
	
	/**
	 * 取得插入的数据的sqlmodel对象。
	 * @param tableName
	 * @param mapData
	 * @return
	 */
	private static SqlModel getInsert(String tableName,Map<String,Object> mapData){
		
		
		
		StringBuffer fieldNames = new StringBuffer();
		StringBuffer params = new StringBuffer();
		final List<Object> values = new ArrayList<Object>();
		
		for (Entry<String, Object> entry : mapData.entrySet()){
			fieldNames.append(entry.getKey()).append(",");
			params.append("?,");
			values.add(entry.getValue());
		}
		StringBuffer sql = new StringBuffer();
		
		sql.append(" INSERT INTO ");
		sql.append(tableName);
		sql.append("(");
		sql.append(fieldNames.substring(0, fieldNames.length() - 1));
		sql.append(")");
		sql.append(" VALUES (");
		sql.append(params.substring(0, params.length() - 1));
		sql.append(")");
	
		
		SqlModel sqlModel=new SqlModel(sql.toString(), values.toArray());
		return sqlModel;
	}
	
	/**
	 * 获取更新的数据model。
	 * @param tableName
	 * @param mapData
	 * @return
	 */
	private static SqlModel getUpd(String tableName,Map<String,Object> mapData){
		final List<Object> values = new ArrayList<Object>();
		
		String key=TableModel.PK_COLUMN_NAME.toLowerCase();
		
		String pkValue=mapData.get(key).toString();
	
		
		
		StringBuffer set = new StringBuffer();
		
		for (Entry<String, Object> entry : mapData.entrySet()){
			if(!key.equals(entry.getKey())){
				set.append(entry.getKey()).append("=?,");
				values.add(entry.getValue());
			}
		}
		if(values.size()==0) return null;
		// sql
		StringBuffer sql = new StringBuffer();
		
		sql.append(" update ");
		sql.append(tableName);
		sql.append(" set ");
		sql.append(set.substring(0, set.length() - 1));
		sql.append(" where ");
		sql.append(key);
		sql.append("=?");
		values.add(pkValue);
		SqlModel sqlModel=new SqlModel(sql.toString(), values.toArray());
					
		return sqlModel;
	}
	
	
	/**
	 * 获取主表的更新的sqlmodel对象。
	 * <pre>
	 * 只是更新客户端提交的json数据，如果表单中没有提交任何数据则不更新。
	 * </pre>
	 * @param bpmFormData
	 * @return
	 */
	private static SqlModel getUpdate(BpmFormData bpmFormData){
		PkValue pk=bpmFormData.getPkValue();
		String tableName=bpmFormData.getTableName();
		Map<String,Object> mapData=bpmFormData.getMainCommonFields();
		StringBuffer set = new StringBuffer();
	
		List<Object> values = new ArrayList<Object>();
		for (Entry<String, Object> entry : mapData.entrySet()){
			set.append(entry.getKey()).append("=?,");
			values.add(entry.getValue());
		}
		if(values.size()==0) return null;
		
		StringBuffer sql = new StringBuffer();
		if(set.length() > 0) {
			// sql
			sql.append(" update ");
			sql.append(tableName);
			sql.append(" set ");
			sql.append(set.substring(0, set.length() - 1));
			sql.append(" where ");
			sql.append(pk.getName() );
			sql.append("=?");
			values.add(pk.getValue());
		}
		SqlModel sqlModel=new SqlModel(sql.toString(), values.toArray());
		return sqlModel;
	}
	
}
