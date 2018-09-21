package com.hotent.platform.model.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hotent.core.model.BaseModel;
import com.hotent.core.util.AppUtil;
import com.hotent.platform.dao.form.BpmFormTableDao;
import com.hotent.platform.model.form.BpmFormData;
import com.hotent.platform.model.form.BpmFormField;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.form.SubTable;
import com.hotent.platform.service.form.BpmFormFieldService;
import com.hotent.platform.service.form.BpmFormHandlerService;
import com.hotent.platform.service.system.SysRoleService;

public class MobileFormData{
	private	long tableId;
	private String tableName;
	private String tableDesc;
	private boolean extForm;
	private boolean emptyForm;
	private String fields;
	private String formEditUrl;
	private String	formDetailUrl;
	private boolean signTask;
	private Map<String, String> options=new HashMap<String, String>();
	
	private List<MobileSubTableData> subTableList=new ArrayList<MobileSubTableData>();
	public MobileFormData(){
		
	}
	public long getTableId() {
		return tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}
	
	public List<MobileSubTableData> getSubTableList() {
		return subTableList;
	}
	public void setSubTableList(List<MobileSubTableData> subTableList) {
		this.subTableList = subTableList;
	}
	public String getTableName() {
		return tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}
	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getFormEditUrl() {
		return formEditUrl;
	}
	public void setFormEditUrl(String formEditUrl) {
		this.formEditUrl = formEditUrl;
	}
	public String getFormDetailUrl() {
		return formDetailUrl;
	}
	public void setFormDetailUrl(String formDetailUrl) {
		this.formDetailUrl = formDetailUrl;
	}
	public boolean isExtForm() {
		return extForm;
	}
	public void setExtForm(boolean extForm) {
		this.extForm = extForm;
	}
	public boolean isEmptyForm() {
		return emptyForm;
	}
	public void setEmptyForm(boolean emptyForm) {
		this.emptyForm = emptyForm;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	
	public Map<String, String> getOptions() {
		return options;
	}
	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
	
	public boolean isSignTask() {
		return signTask;
	}
	public void setSignTask(boolean signTask) {
		this.signTask = signTask;
	}
	
	public void setFormData(Long tableId,String pkValue,String actInstId) throws Exception{
		BpmFormTableDao bpmFormTableDao=(BpmFormTableDao)AppUtil.getBean(BpmFormTableDao.class);
		BpmFormFieldService bpmFormFieldService=(BpmFormFieldService)AppUtil.getBean(BpmFormFieldService.class);
		BpmFormHandlerService bpmFormHandlerService=(BpmFormHandlerService)AppUtil.getBean(BpmFormHandlerService.class);
		BpmFormTable table = bpmFormTableDao.getById(tableId);
		//根据主表ID取出主表字段列表
		List<BpmFormField> mainFieldList=bpmFormFieldService.getByTableId(tableId);
		//取出表单数据
		BpmFormData bpmFormData=bpmFormHandlerService.doGetBpmFormData(tableId, pkValue, actInstId,null,null);
		setTableId(table.getTableId());
		setTableName(table.getTableDesc());
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<mainFieldList.size();i++){
			BpmFormField field=mainFieldList.get(i);
			//字段是否支持手机客户端访问
			if(field.getIsAllowMobile()==(short)1){
				String fieldName=field.getFieldName();
				String fieldDesc=field.getFieldDesc();
				String value="";
				if(!bpmFormData.getMainFields().isEmpty()){
					value=bpmFormData.getMainFields().get(fieldName).toString();
				}
				sb.append("{'key':'").append(fieldName).append("','label':'").append(fieldDesc).append("','value':'").append(value).append("'},");
			}
		}
		if(sb.length()!=0){
			setFields("["+sb.substring(0, sb.lastIndexOf(","))+"]");
		}
		setOptions(bpmFormData.getOptions());
		//手机表单子表
		List<MobileSubTableData> subDataList=new ArrayList<MobileSubTableData>();
		//子表数据列表
		List<SubTable> subTableDataList=bpmFormData.getSubTableList();
		//根据主表Id获取子表列表
		List<BpmFormTable> subTableList=bpmFormTableDao.getSubTableByMainTableId(tableId);
		for(int i=0;i<subTableList.size();i++){
			BpmFormTable subTableForm=subTableList.get(i);
			SubTable subTable=subTableDataList.get(i);
			//构建手机表单子表
			MobileSubTableData subData=new MobileSubTableData();
			//子表字段
			Map<String,String> subFields=null;
			//子表 表名
			subData.setTableName(subTableForm.getTableName());
			//子表 表描述
			subData.setTableDesc(subTableForm.getTableDesc());
			//子表 ID
			subData.setTableId(subTableForm.getTableId()); 
			
			//子表数据
			List<Map<String,Object>> subtableDataList=subTable.getDataList();
			List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
			List<Map<String,String>> subMobileFields = new ArrayList<Map<String,String>>();
			List<BpmFormField> subFieldList=bpmFormFieldService.getByTableId(subTableForm.getTableId());
			for(BpmFormField subField:subFieldList){
				if(subField.getIsAllowMobile()==(short)1){
					subFields=new HashMap<String, String>();
					subFields.put("fieldKey",subField.getFieldName());
					subFields.put("fieldVal",subField.getFieldDesc());
					subMobileFields.add(subFields);
				}
			}
			subData.setFields(subMobileFields);
			for(Map<String,Object> data:subtableDataList){
				Map<String,Object> dataMap=new HashMap<String, Object>();
				for(BpmFormField subField:subFieldList){
					if(subField.getIsAllowMobile()==(short)1){
						dataMap.put(subField.getFieldName(), data.get(subField.getFieldName()));
					}
				}
				dataList.add(dataMap);
			}
			subData.setDataList(dataList);
			subDataList.add(subData);
		}
		setSubTableList(subDataList);
	}

}
