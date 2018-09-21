package com.hotent.platform.service.form;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.customertable.TableModel;
import com.hotent.core.db.JdbcDao;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.TaskOpinionDao;
import com.hotent.platform.dao.form.BpmFormFieldDao;
import com.hotent.platform.dao.form.BpmFormHandlerDao;
import com.hotent.platform.dao.form.BpmFormTableDao;
import com.hotent.platform.model.bpm.ProRunTblPk;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.form.BpmFormData;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormField;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.form.BpmTableTemplate;
import com.hotent.platform.model.form.SubTable;
import com.hotent.platform.service.system.IdentityService;

import freemarker.template.TemplateException;

/**
 * 对象功能:自定义表单数据处理Service类 
 * 开发公司:
 * 开发人员:xwy 
 * 创建时间:2011-12-22 11:07:56
 */
@Service
public class BpmFormHandlerService {
	protected Logger logger = LoggerFactory.getLogger(BpmFormHandlerService.class);
	//
	@Resource
	private BpmFormHandlerDao dao;

	@Resource
	private BpmFormFieldService bpmFormFieldService;

	@Resource
	private IdentityService identityService;
	@Resource
	private BpmFormRightsService bpmFormRightsService;
	@Resource
	private BpmFormControlService bpmFormControlService;
	@Resource
	private TaskOpinionDao taskOpinionDao;
	@Resource
	private FreemarkEngine freemarkEngine;
	@Resource
	private BpmFormFieldDao bpmFormFieldDao; 
	@Resource
	private BpmFormTableDao bpmFormTableDao;
	@Resource
	private JdbcDao jdbcDao;

	@Resource
	private GroovyScriptEngine  groovyScriptEngine;
	@Resource
	private PermissionSelector permissionSelector;

	//流程实例分隔符
	private String InstanceIdSplitor="#instanceId#";
	//流程示意图替换符
	private String FlowChartSplitor = "(?s)<div[^>]*?\\s+name=\"editable-input\"\\s+class=\"flowchart\">(.*?)</div>";
	private String FlowChartSplitor_IE = "(?s)<div[^>]*?\\s+class=\"flowchart\"\\s+name=\"editable-input\">(.*?)</div>";

	/**
	 * 根据表单定义，用户ID，主键获取表单的html代码。
	 * 
	 * <pre>
	 * 实现流程：
	 * 1.获取表单的模版。
	 * 2.判断主键是否为空。
	 * 		1.主键为空。
	 * 			实例化BpmFormData数据。
	 * 			1.判断字段值来源是流水号。
	 * 				如果是流水号,则生成流水号。
	 * 			2.判断字段值是来自脚本。
	 * 				通过脚本计算得出字段的值。
	 * 		2.主键不为空。
	 * 			根据主键获取表单的数据。
	 * 
	 * 3.将数据和字段权限给模版引擎解析，生成html。
	 * </pre>
	 * 
	 * @param bpmFormDef 	表单定义对象。
	 * @param processRun 	流程运行实例对象。
	 * @param userId 		用户ID。
	 * @param pkValue  		主键值。
	 * @return
	 * @throws Exception
	 */
	public String obtainHtml(BpmFormDef bpmFormDef, ProcessRun processRun, Long userId, String nodeId,String ctxPath,Map<String, Object> variables) throws Exception {
		String pkValue = processRun.getBusinessKey();
		Long tableId= processRun.getTableId();
		String instanceId = processRun.getActInstId();
		String actDefId = processRun.getActDefId();
		Map<Long,ProRunTblPk> tableAndPk = null;
		
		if(variables!=null){
			//找出当前的主键值
			tableAndPk = (Map<Long,ProRunTblPk>)variables.get(BpmConst.TABLE_AND_PK);
		}
		return doObtainHtml(bpmFormDef, userId, pkValue, tableId,instanceId, actDefId, nodeId,ctxPath,tableAndPk);

	}

	/**
	 * 根据表单定义，用户ID，主键获取表单的html代码。
	 * 
	 * <pre>
	 * 实现流程：
	 * 1.获取表单的模版。
	 * 2.判断主键是否为空。
	 * 		1.主键为空。
	 * 			实例化BpmFormData数据。
	 * 			1.判断字段值来源是流水号。
	 * 				如果是流水号,则生成流水号。
	 * 			2.判断字段值是来自脚本。
	 * 				通过脚本计算得出字段的值。
	 * 			3.如果是日期控件，或者日期。
	 * 				默认显示时间的话，根据日期格式获取当前日期。
	 * 		2.主键不为空。
	 * 			根据主键获取表单的数据。
	 * 
	 * 3.将数据和字段权限给模版引擎解析，生成html。
	 * </pre>
	 * @param bpmFormDef 自定义表单对象
	 * @param userId     用户Id
	 * @param pkValue    主键值
	 * @param instanceId ACT流程实例ID
	 * @param actDefId   ACT流程定义ID
	 * @param nodeId     
	 * @return
	 * @throws Exception
	 */
	public String doObtainHtml(BpmFormDef bpmFormDef, Long userId, String pkValue,Long tableId, String instanceId, String actDefId, String nodeId,String ctxPath,Map<Long, ProRunTblPk> proRunTblPkMap) throws Exception {
		Long currentTableId = bpmFormDef.getTableId();
		String previousPkValue = pkValue;
		ProRunTblPk currentProRunTblPk = null;
		//【HT】
		//上一个节点表名不为空，并且和现在这个表名不同
		boolean isNeedFillData = false;
		//上一个表不为空 + 上一个主键不为空 + 两表名不同 
		if(tableId!=null && StringUtil.isNotEmpty(pkValue) && currentTableId!=tableId){
			if(BeanUtils.isNotEmpty(proRunTblPkMap)){
				currentProRunTblPk = proRunTblPkMap.get(currentTableId);
			}
			if(currentProRunTblPk==null){//表示不存在记录，需要填充
				isNeedFillData = true;
				previousPkValue = null;
			}
		}
		
		String tempStr=bpmFormDef.getTemplate();
		String template = "<input id='tableId' name='tableId' type='hidden' value='" + currentTableId +"'/>" + tempStr ;
		
		Long formKey = bpmFormDef.getFormKey();
		//实例化BpmFormData数据
		BpmFormData data = doGetBpmFormData(currentTableId,previousPkValue,instanceId,actDefId,nodeId);
		//需要填充
		if(isNeedFillData){
			//遍历所有表
			for(Iterator<Long> it = proRunTblPkMap.keySet().iterator();it.hasNext();){
				//主表ID
				Long iTableId = it.next();
				ProRunTblPk iPkValue = proRunTblPkMap.get(iTableId);
				//不是当前的表单才处理
				if(iTableId!=currentTableId && iPkValue!=null){
					//取得变量。取主表和子表的变量
					Map<String, Object> variables = new HashMap<String,Object>();
					//查询表定义
					List<BpmFormField> fieldList = this.bpmFormFieldService.getByTableId(iTableId);
					//查询该表的该条数据
					for(BpmFormField field:fieldList){
						if(field.getIsFlowVar()!=null && field.getIsFlowVar().intValue()==1){
							BpmFormData iData = doGetBpmFormData(iTableId,iPkValue.getPkId().toString(),instanceId,actDefId,nodeId);
							for(Iterator<String> itMainField = iData.getMainFields().keySet().iterator();itMainField.hasNext();){
								String name = itMainField.next();								
								if(name.equals(field.getFieldName())){
									variables.put(name, iData.getMainFields().get(name));
								}
							}
						}
					}
					
					//取得数据
					Map<String, Object> mainFields = data.getMainFields();
					Map<String, String> getOptions = data.getOptions();					
					
//					Map<String, SubTable> subTables = data.getSubTableMap();
					//填充数据
					if(mainFields!=null){
						replaceValue(variables,mainFields);
					}
					if(getOptions!=null){
						replaceValueForString(variables,getOptions);
					}
					/**
					if(subTables!=null){
						for(Iterator<String> itSub = subTables.keySet().iterator();itSub.hasNext();){
							String keySub = itSub.next();
							SubTable subTable = subTables.get(keySub);
							if(subTable.getDataList()!=null && subTable.getDataList().size()>0){
								for(Map<String,Object> dataOne:subTable.getDataList()){
									replaceValue(variables,dataOne);
								}
							}
						}
					}
					*/
				}
			}
			
			//TODO
			////ht:raise add b
			//当前所有的子表信息
			List<BpmFormTable>  curSubBpmFormTables = bpmFormTableDao.getSubTableByMainTableId(currentTableId);
			//上一个表的主表键值
			ProRunTblPk lastProRunTblPk =  proRunTblPkMap.get(tableId);
			if(lastProRunTblPk!=null){
			//上一个表的子表键值
			Map<Long,List<ProRunTblPk>> subProRunTblPkMap = lastProRunTblPk.getSubProRunTblPkMap();
			Long[] keySets = subProRunTblPkMap.keySet().toArray(new Long[]{});
			//如果当前表存在子表
			if(BeanUtils.isNotEmpty(curSubBpmFormTables) && BeanUtils.isNotEmpty(keySets)){
				//取上个表的表信息
//				List<BpmFormTable>  subBpmFormTables =  bpmFormTableDao.getSubTableByMainTableId(tableId);
				//上个表的主键信息				
				int min = Math.min(curSubBpmFormTables.size(),subProRunTblPkMap.size());
				for(int i = 0;i<min ;i++){
					//当前子表的表信息
					BpmFormTable curSubBpmFormTable = curSubBpmFormTables.get(i);
					//与当前子表对应的上个表的子键值
					Long lastSubTableId = keySets[i];
					List<ProRunTblPk> lastSubProRunTblPks = subProRunTblPkMap.get(lastSubTableId);
					if(BeanUtils.isEmpty(lastSubProRunTblPks)){
						continue;
					}
					//当前子表字段信息
					List<BpmFormField> curSubFieldList = this.bpmFormFieldService.getByTableId(curSubBpmFormTable.getTableId());
					SubTable subTable =new SubTable();
					subTable.setTableName(curSubBpmFormTable.getTableName());
					subTable.setPkName(curSubBpmFormTable.getPkField());
					
//					BpmFormTable lastSubBpmFormTable = bpmFormTableDao.getById(lastSubTableId);
					Map<String, Object> subVariables = new HashMap<String,Object>();
					//查询表定义
					List<BpmFormField> subFieldList = this.bpmFormFieldService.getByTableId(lastSubTableId);
					if(BeanUtils.isNotEmpty(subFieldList)){
					//查询该表的多条数据
						for(ProRunTblPk pk:lastSubProRunTblPks){
							for(BpmFormField field:subFieldList){
								if(field.getIsFlowVar()!=null && field.getIsFlowVar().intValue()==1){
									BpmFormData iData = doGetBpmFormData(lastSubTableId,pk.getPkId().toString(),instanceId,actDefId,nodeId);
									if(iData==null){
										break;
									}
									for(Iterator<String> itMainField = iData.getMainFields().keySet().iterator();itMainField.hasNext();){
										String name = itMainField.next();								
										if(name.equals(field.getFieldName())){
											subVariables.put(name, iData.getMainFields().get(name));
										}
									}
								}
							}
							
							Map<String,Object> fieldData = new HashMap<String, Object>();
							List<String> fieldsNames = new ArrayList<String>();
							for(BpmFormField field:curSubFieldList){
								fieldData.put(field.getFieldName(), null);
								fieldsNames.add(field.getFieldName());
							}
							replaceValue(subVariables,fieldData);
							if(!isEmptyMap(fieldData,fieldsNames)){
								subTable.getDataList().add(fieldData);
								data.getSubTableMap().put(subTable.getTableName(),subTable );
								data.getSubTableList().add(subTable);
							}
						}
					}
				}
			}
			}
			////ht:raise add e
			//TODO
		}
		
		Map<String, Map> model = new HashMap<String, Map>();
		model.put("main", data.getMainFields());
		model.put("opinion", data.getOptions());
		model.put("sub", data.getSubTableMap());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("model", model);
		// 传入控制器的service，用于在模版中解析字段。
		map.put("service", bpmFormControlService);
		// 传入字段的权限。
		if(userId>0)
			map.put("permission", bpmFormRightsService.getByFormKeyAndUserId(formKey, userId, actDefId, nodeId));
		else
			map.put("permission", bpmFormRightsService.getByFormKey(bpmFormDef));
		//兼容之前生成的模版。
		map.put("table", new HashMap());
		
		String output = freemarkEngine.parseByStringTemplate(map, template);
		
		String tabTitle = bpmFormDef.getTabTitle();
		// 有分页的情况。
		if (tabTitle.indexOf(BpmFormDef.PageSplitor ) > -1) {
			output = getTabHtml(tabTitle, output);
		}
		//替换流程实例分隔符。
		output=output.replace(InstanceIdSplitor, instanceId);
		//流程图解析 		
		if(instanceId!=""){
			String repStr="<iframe src=\""+ctxPath+"/platform/bpm/processRun/processImage.ht?processInstanceId="+instanceId+"&notShowTopBar=0\" name=\"flowchart\" id=\"flowchart\" marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\" scrolling=\"no\" width=\"100%\"></iframe>";
			output=output.replaceAll(FlowChartSplitor, repStr).replaceAll(FlowChartSplitor_IE, repStr);
		}
		else if(actDefId!=""){
			String repStr="<iframe src=\""+ctxPath+"/platform/bpm/bpmDefinition/flowImg.ht?actDefId="+actDefId+"\"  name=\"flowchart\" id=\"flowchart\" marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\" scrolling=\"no\" width=\"100%\"></iframe>";
			output=output.replaceAll(FlowChartSplitor, repStr).replaceAll(FlowChartSplitor_IE, repStr);
		}
		return output;
	}
	private void replaceValue(Map<String, Object> origins,Map<String,Object> dests){
		for(Iterator<String> itOrigin = origins.keySet().iterator();itOrigin.hasNext();){
			String key = itOrigin.next();
			Object value = origins.get(key);	
			dests.put(key, value);
		}
	}
	
	private boolean isEmptyMap(Map<String,Object> map,List<String> fieldNames){
		for(String fn:fieldNames){
			if(map.get(fn)!=null){
				return false;
			}
		}
		return true;
	}
	
	private void replaceValueForString(Map<String, Object> origins,Map<String,String> dests){
		for(Iterator<String> itOrigin = origins.keySet().iterator();itOrigin.hasNext();){
			String key = itOrigin.next();
			Object value = origins.get(itOrigin);		
			if(value!=null)
				dests.put(key, value.toString());
		}
	}
	/**
	 * 根据表ID，主键值，流程实例Id 获取bpmFormData 实例
	 * @param tableId
	 * @param pkValue
	 * @param instanceId
	 * @return
	 * @throws Exception
	 */
	public BpmFormData doGetBpmFormData(Long tableId,String pkValue,String instanceId,String actDefId,String nodeId) throws Exception{
		BpmFormData data = null;
		if (StringUtil.isNotEmpty(pkValue)) {
			data = dao.getByKey(tableId, pkValue,actDefId,nodeId);
		}
		if (data!=null) {
			// 根据主键和表取出数据填充BpmFormData。
			data = dao.getByKey(tableId, pkValue,actDefId,nodeId);
			// 获取表单的意见。
			if (StringUtil.isNotEmpty(instanceId)) {
				Map<String, String> formOptions = getFormOptionsByInstance(instanceId);
				if (BeanUtils.isNotEmpty(formOptions)) {
					data.setOptions(formOptions);
				}
			}
		}else {
			data = new BpmFormData();
			// 获取流水号和脚本计算结果
			Map<String, Object> resultMap = new HashMap<String, Object>();
//			List<BpmFormField> list = bpmFormFieldService.getByTableId(tableId);
			List<BpmFormField> list = bpmFormFieldDao.getByTableIdContainHidden(tableId);
			ISysUser user = ContextUtil.getCurrentUser();
			ISysOrg org = ContextUtil.getCurrentOrg();
			for (BpmFormField field : list) {
				String fieldName = field.getFieldName().toLowerCase();
				// 值来源为流水号。
				if (field.getValueFrom() == BpmFormField.VALUE_FROM_IDENTITY) {
					String id = identityService.doNextId(field.getIdentity());
					resultMap.put(fieldName, id);
				}
				// 值来源为脚本。
				else if (field.getValueFrom() == BpmFormField.VALUE_FROM_SCRIPT_SHOW) {
					Object result = FormUtil.calcuteField(field.getScript(), data.getMainFields(), TableModel.CUSTOMER_COLUMN_PREFIX);
					resultMap.put(fieldName, result);
				}
				//计算默认日期数据
				else if(field.getControlType()==15 || field.getFieldType().equals("date")){
					String prop=field.getCtlProperty();
					//{"format":"yyyy-MM-dd","displayDate":1,"condition":"like"}
					if(StringUtil.isNotEmpty(prop)){
						try{
							JSONObject jsonObject=JSONObject.fromObject(prop);
							if(jsonObject.containsKey("displayDate")){
								String format=jsonObject.getString("format");
								String displayDate=jsonObject.getString("displayDate");
								if(displayDate.equals("1")){
									resultMap.put(fieldName, TimeUtil.getDateString(format));
								}
							}
						}
						catch(Exception ex){
							logger.debug(ex.getMessage());
						}
					}
				}
				// 用户选择器默认当前用户
				else if (field.getControlType() == 4) {
					String prop=field.getCtlProperty();
					if(StringUtil.isNotEmpty(prop)){
						JSONObject jsonObject=JSONObject.fromObject(prop);
						if(jsonObject.containsKey("showCurUser")){
							String showCurUser = JSONObject.fromObject(prop).getString("showCurUser");
							if(showCurUser.equals("1")){
								if(field.getIsHidden()==1){
									resultMap.put(fieldName, user.getUserId());
								}else{
									resultMap.put(fieldName, user.getFullname());
								}
							}
						}
						
					}
				}
				
			}
			// 将流水号和脚本计算结果加入data
			data.setMainFields(resultMap);
		}
		
		return data;
	}
	
	
	
	/**
	 * 对设计的表单进行解析。
	 * <pre>
	 * 生成实际的表单html。
	 * </pre>
	 * @param title
	 * @param parseResult
	 * @return
	 * @throws Exception
	 */
	public String obtainHtml(String title, ParseReult parseResult,Map<String, Map<String, String>> permission) throws Exception {
		String template = "<input id='tableId' name='tableId' type='hidden' value='tableId'/>" +parseResult.getTemplate();
		
		BpmFormTable bpmFormTable=parseResult.getBpmFormTable();
		
		if(BeanUtils.isEmpty(permission)){
			permission=new HashMap<String, Map<String, String>>();
			
			Map<String, String> fieldPermission = new HashMap<String, String>();
			Map<String, String> tablePermission = new HashMap<String, String>();
			Map<String, String> opinionPermission = new HashMap<String, String>();
			
			permission.put("field", fieldPermission);
			permission.put("table", tablePermission);
			permission.put("opinion", opinionPermission);
		}
		
		
		BpmFormData	data = new BpmFormData();
		// 获取流水号和脚本计算结果
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ISysUser user = ContextUtil.getCurrentUser();
		ISysOrg org = ContextUtil.getCurrentOrg();
		List<BpmFormField> list = bpmFormTable.getFieldList();
		for (BpmFormField field : list) {
			String fieldName = field.getFieldName().toLowerCase();
			// 值来源为流水号。
			if (field.getValueFrom() == BpmFormField.VALUE_FROM_IDENTITY) {
				String id = identityService.doNextId(field.getIdentity());
				resultMap.put(fieldName, id);
			}
			// 值来源为脚本。
			else if (field.getValueFrom() == BpmFormField.VALUE_FROM_SCRIPT_SHOW) {
				Object result = FormUtil.calcuteField(field.getScript(), data.getMainFields(), TableModel.CUSTOMER_COLUMN_PREFIX);
				resultMap.put(fieldName, result);
			}
			//计算默认日期数据
			else if(field.getControlType()==15 || field.getFieldType().equals("date")){
				String prop=field.getCtlProperty();
				//{"format":"yyyy-MM-dd","displayDate":1,"condition":"like"}
				if(StringUtil.isNotEmpty(prop)){
					try{
						JSONObject jsonObject=JSONObject.fromObject(prop);
						String format=jsonObject.getString("format");
						String displayDate=jsonObject.getString("displayDate");
						if(displayDate.equals("1")){
							resultMap.put(fieldName, TimeUtil.getDateString(format));
						}
					}
					catch(Exception ex){}
				}
			}
			
			// 用户选择器默认当前用户
			else if (field.getControlType() == 4) {
				String prop=field.getCtlProperty();
				if(StringUtil.isNotEmpty(prop)){
					JSONObject jsonObj = JSONObject.fromObject(prop);
					if(jsonObj.containsKey("showCurUser")){
						String showCurUser = jsonObj.getString("showCurUser");
						if(showCurUser.equals("1")){
							if(field.getIsHidden()==1){
								resultMap.put(fieldName, user.getUserId());
							}else{
								resultMap.put(fieldName, user.getFullname());
							}
						}
					}
				}
			}
			
			// 组织选择器默认当前组织
			else if (field.getControlType() == 6) {
				String prop=field.getCtlProperty();
				if(StringUtil.isNotEmpty(prop)){
					JSONObject jsonObj = JSONObject.fromObject(prop);
					if(jsonObj.containsKey("showCurOrg")){
						String showCurOrg = jsonObj.getString("showCurOrg");
						if(showCurOrg.equals("1")){
							if(field.getIsHidden()==1){
								resultMap.put(fieldName, org.getOrgId());
							}else{
								resultMap.put(fieldName, org.getOrgName());
							}
						}
					}
				}
			}
			
		}
		// 将流水号和脚本计算结果加入data
		data.setMainFields(resultMap);
		

		Map<String, Map> model = new HashMap<String, Map>();
		model.put("main", data.getMainFields());
		model.put("opinion", data.getOptions());
		model.put("sub", data.getSubTableMap());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("model", model);
		// 传入控制器的service，用于在模版中解析字段。
		map.put("service", bpmFormControlService);
		// 传入字段的权限。
		map.put("permission", permission);
		//兼容之前生成的模版。
		map.put("table", new HashMap());
		
		String output = freemarkEngine.parseByStringTemplate(map, template);

		
		// 有分页的情况。
		if (title.indexOf(BpmFormDef.PageSplitor ) > -1) {
			output = getTabHtml(title, output);
		}
	
		return output;
	}

	/**
	 * 根据流程实例取得流程的意见。
	 * 
	 * @param instanceId
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	private Map<String, String> getFormOptionsByInstance(String instanceId) throws IOException, TemplateException {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, List<TaskOpinion>> taskMap = new HashMap<String, List<TaskOpinion>>();
		List<TaskOpinion> list = taskOpinionDao.getFormOptionsByInstance(instanceId);
		for (TaskOpinion option : list) {
			if (StringUtil.isNotEmpty(option.getFieldName())) {
				String fieldName = option.getFieldName().toLowerCase();
				if (taskMap.containsKey(fieldName)) {
					List<TaskOpinion> opinionList = taskMap.get(fieldName);
					opinionList.add(option);
				} else {
					List<TaskOpinion> opinionList = new ArrayList<TaskOpinion>();
					opinionList.add(option);
					taskMap.put(fieldName, opinionList);
				}
			}
		}
		Set<Map.Entry<String, List<TaskOpinion>>> set = taskMap.entrySet();
		for (Iterator<Map.Entry<String, List<TaskOpinion>>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, List<TaskOpinion>> entry = (Map.Entry<String, List<TaskOpinion>>) it.next();
			List<TaskOpinion> optionList = entry.getValue();
			String options = "";
			for (TaskOpinion opinion : optionList) {
				Map model = new HashMap();
				model.put("opinion", opinion);
				options += freemarkEngine.mergeTemplateIntoString("opinion.ftl", model);
			}
			map.put(entry.getKey(), options);
		}
		return map;
	}

	/**
	 * 获取tab的html。
	 * 
	 * @param tabTitle
	 * @param html
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	private String getTabHtml(String tabTitle, String html) throws TemplateException, IOException {
		String[] aryTitle = tabTitle.split(BpmFormDef.PageSplitor);
		String[] aryHtml = html.split(BpmFormDef.PageSplitor);

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < aryTitle.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", aryTitle[i]);
			map.put("html", aryHtml[i] );
			list.add(map);
		}
		String formPath = BpmFormTemplateService.getFormTemplatePath() + "tab.ftl";
		String tabTemplate = FileUtil.readFile(formPath);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tabList", list);
		String output = freemarkEngine.parseByStringTemplate(map, tabTemplate);
		return output;
	}

	/**
	 * 处理动态表单数据
	 * 
	 * @param bpmFormData
	 * @throws Exception
	 */
	public void handFormData(BpmFormData bpmFormData,String actDefId,String nodeId) throws Exception {
		dao.handFormData(bpmFormData, actDefId, nodeId);
	}

	/**
	 * 根据主键查询列表数据。
	 * 
	 * @param tableId
	 * @param pkValue
	 * @return
	 * @throws Exception
	 */
	public BpmFormData getByKey(long tableId, String pkValue) throws Exception {
		return dao.getByKey(tableId, pkValue);
	}

	/**
	 * 获取业务的分页数据。
	 * @param bpmTableTemplate
	 * @param userId
	 * @param param
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getPage(BpmTableTemplate bpmTableTemplate, Long userId, Map<String, Object> param, PageBean pageBean) throws Exception {
		Long tableId=bpmTableTemplate.getTableId();
		BpmFormTable table = bpmFormTableDao.getById(tableId);
		List<BpmFormField> fieldList= bpmFormFieldDao.getByTableId(tableId);
		
		
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		
		String sql=getSql(bpmTableTemplate, table, fieldList, userId, param);
			
		List<Map<String, Object>> list=jdbcDao.getPage(currentPage, pageSize, sql, param, pageBean);
		
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		//对数据进行处理，去掉字段的前缀
		for (Map<String, Object> maps : list) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			for (Map.Entry<String, Object> map : maps.entrySet()) {
				String key = map.getKey().toLowerCase().replaceFirst(TableModel.CUSTOMER_COLUMN_PREFIX.toLowerCase(), "");
				newMap.put(key, map.getValue());
			}
			retList.add(newMap);
		}
	
		return retList;
	}

	/**
	 * 删除业务数据。
	 * @param id
	 * @param tableName
	 */
	public void delById(String id,String tableName){
		String sql="delete from w_" + tableName + " where id=" + id;
		jdbcDao.upd(sql);
	}
	
	/**
	 * 获取访问的SQL。
	 * @param bpmTableTemplate
	 * @param table			表定义
	 * @param fieldList		表字段
	 * @param userId		用户id
	 * @param param			参数map
	 * @return
	 */
	private String getSql(BpmTableTemplate bpmTableTemplate, BpmFormTable table,List<BpmFormField> fieldList, Long userId, Map<String, Object> param){
		String displayFields=getDisplayFields(fieldList);
		String permissionSql=getAuthorTypeSql(bpmTableTemplate.getAuthorType() ,userId);
		String whereSql=getWhere(fieldList,param);
		String orderSql=getOrderBy(param);
		String sql="select "+ displayFields + " from " + TableModel.CUSTOMER_TABLE_PREFIX +  table.getTableName() +" where 1=1 "+ whereSql + permissionSql + orderSql;
		return sql;
	}
	
	/**
	 * 获取需要显示的字段。
	 * <br>
	 * 如果表中有显示的字段就查询显示的字段。
	 * 如果没有就获取表的所有字段。
	 * @param fieldList		字段列表。
	 * @return
	 */
	private String getDisplayFields(List<BpmFormField> fieldList){
		String displayField="";
		for(BpmFormField field:fieldList){
			if(field.getIsList()==1){
				displayField+=TableModel.CUSTOMER_COLUMN_PREFIX +field.getFieldName() +",";
			}
		}
		if(StringUtil.isEmpty(displayField)){
			return " * ";
		}
		return displayField+"flowrunid_,defid_,id";
	}
	
	/**
	 * 根据用户id获取权限的sql语句片段。
	 * @param authorType	查看数据权限类型。
	 *  1，查看全部的数据。
	 * 	2，查看本人的数据。
	 *  4，查看本组织的数据。
	 * @param userId
	 * @return
	 */
	private String getAuthorTypeSql(int authorType, Long userId) {
		IDataPermission permission= permissionSelector.getByKey(authorType);
		String sqlStr=permission.getSql(userId); 
		return sqlStr;
	}
	
	/**
	 * 获取where条件。
	 * @param fieldList
	 * @param params
	 * @return
	 */
	private String getWhere(List<BpmFormField> fieldList,Map<String, Object> params){
		StringBuffer sb=new StringBuffer();
		for (BpmFormField field:fieldList) {
			getStringByField(field,params,sb);
		}
		if(sb.length()>0){
			return  sb.toString();
		}
		return "";
	}
	
	

	/**
	 * 根据参数配置，上下文参数计算SQL语句。
	 * @param field
	 * @param params
	 * @param sb
	 */
	private void getStringByField(BpmFormField field, Map<String,Object> params,StringBuffer sb){
		String condition=field.getPropertyMap().get("condition");
		String condValFrom=field.getPropertyMap().get("condValFrom");
		String condValue=field.getPropertyMap().get("condValue");
		String fieldName=field.getFieldName();
		String fieldNamePrix=TableModel.CUSTOMER_COLUMN_PREFIX + field.getFieldName();
		Object value=null;
		if(StringUtil.isEmpty(condValFrom)){
			return;
		}
		int conditionType=Integer.valueOf(condValFrom);
		switch(conditionType){
			case 1:
				if(params.containsKey(fieldName)){
					 value=(String)params.get(fieldName);
				}
				break;
			case 2:
				value=condValue;
				break;
			case 3:
				String script=condValue;
				if(StringUtil.isNotEmpty(script)){
					value=groovyScriptEngine.executeObject(script, null);
				}
		}
		if(BeanUtils.isEmpty(value)) return;
	    
		if(field.getFieldType().equals(ColumnModel.COLUMNTYPE_VARCHAR)){
		   if(condition.equals("=")){
			  sb.append( " and " +fieldNamePrix +"='" + value.toString() +"' ");
		   }
		   else if(condition.equals("like")){
			   sb.append(" and " +fieldNamePrix +" like '%" + value.toString() +"%' ");
		   }
		   else {
			   sb.append(" and " +fieldNamePrix +" like '" + value.toString() +"%' ");
		   }
	    }
	    else if (field.getFieldType().equals(ColumnModel.COLUMNTYPE_DATE)){
		   if(condition.equals("=")){
			   sb.append(" and " + fieldNamePrix +"=:" + fieldName +" ");
			   if(!params.containsKey(fieldName)){
				   params.put(fieldName, value);
			   }
		   }
		   else if(condition.equals(">=")){
			   if(conditionType==1){
				   if(params.containsKey("start" + fieldName)){
					   sb.append(" and " + fieldNamePrix +">=:start" + fieldName +" ");
			   	   }
			   }
			   else{
				   sb.append(" and " + fieldNamePrix +">=:start" + fieldName +" ");
				   params.put("start" + fieldName, value);
			   }
		   }
		   else if(condition.equals("<=")){
			   if(conditionType==1){
				   if(params.containsKey("end" + fieldName)){
					   sb.append(" and " + fieldNamePrix +"<=:end" + fieldName +" ");
			   	   }
			   }
			   else{
				   sb.append(" and " + fieldNamePrix +"<=:end" + fieldName +" ");
				   params.put("end" + fieldName, value);
			   }
		   }
	    }
	    else{
	    	if(conditionType==1){
	    		if(params.containsKey(fieldName)){
	    			sb.append(" and " + fieldNamePrix +condition+":" + fieldName +" ");
	    		}
	    	}
	    	else{
	    		sb.append(" and " + fieldNamePrix +condition+":" + fieldName +" ");
	    		params.put(fieldName, value);
	    	}
	    }
	}
	
	
	
	/**
	 * 获取order by语句。
	 * @param fieldList
	 * @param params
	 * @return
	 */
	private String getOrderBy(Map<String, Object> params){
		String orderbySql=" ORDER BY ";
		if(params.containsKey("sortField")){
			String fieldNamePrix;
			String sortField=(String) params.get("sortField");
			if(sortField.equalsIgnoreCase("CREATETIME")){
				fieldNamePrix=sortField;
			}else{
				fieldNamePrix=TableModel.CUSTOMER_COLUMN_PREFIX +sortField;
			}
			
			orderbySql +=fieldNamePrix;
			if(params.containsKey("orderSeq")){
				String orderSeq=(String) params.get("orderSeq");
				if(orderSeq.equalsIgnoreCase("desc")){
					orderbySql +=" DESC";
				}else{
					orderbySql +=" ASC";
				}
			}
		}else{
			orderbySql+="CREATETIME DESC";
		}
		
		
		return orderbySql;

	}
}
