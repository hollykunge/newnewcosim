package com.hotent.platform.controller.form;



import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotent.core.customertable.TableModel;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.Pingyin;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.form.BpmFormField;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.form.BpmFormTableService;

import edu.emory.mathcs.backport.java.util.Arrays;



/**
 * 对象功能:自定义表 控制器类 
 * 开发公司:广州宏天软件有限公司 
 * 开发人员:xwy 
 * 创建时间:2011-11-30 14:29:22
 */
@Controller
@RequestMapping("/platform/form/bpmFormTable/")
public class BpmFormTableFormController extends BaseFormController
{
	@Resource
	private BpmFormTableService bpmFormTableService;
	
	

	
	/**
	 * 添加自定义表。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveTable")
	public void saveTable(HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String tableJson=request.getParameter("table");
		String fieldsJson=request.getParameter("fields");
		JSONArray aryJson=JSONArray.fromObject(fieldsJson);
		BpmFormTable table = (BpmFormTable) JSONObject.toBean(JSONObject.fromObject(tableJson), BpmFormTable.class);
		BpmFormField[] fields = (BpmFormField[]) JSONArray.toArray(JSONArray.fromObject(fieldsJson),BpmFormField.class);
		table.setTableName(StringUtil.trim(table.getTableName()," "));
		for(int i=0;i<fields.length;i++){
			BpmFormField field=fields[i];
			field.setFieldName(StringUtil.trim(field.getFieldName()," "));
			JSONObject jsonObj=(JSONObject)aryJson.get(i);
			if(jsonObj.containsKey("ctlProperty")){
				String str=jsonObj.getString("ctlProperty");
				field.setCtlProperty(str);
			}
		}
		List<BpmFormField> list=Arrays.asList(fields);
		table.setFieldList(list);
		String msg="";
		try{
			
			if(table.getTableId()==0){
				if (bpmFormTableService.isTableNameExisted(table.getTableName())){
					msg = "表名已存在";
					writeResultMessage(response.getWriter(), msg, ResultMessage.Fail);
					return;
				}
				
				int rtn= bpmFormTableService.addFormTable(table);
				if(rtn==-1){
					msg = "不要输入字段【"+TableModel.CUSTOMER_COLUMN_CURRENTUSERID+"】,该字段为保留字段!";
					writeResultMessage(response.getWriter(), msg, ResultMessage.Fail);
					return;
				}
				msg = getText("record.added", "自定义表");
				writeResultMessage(response.getWriter(), msg, ResultMessage.Success);
			}
			else{
				boolean isExist=bpmFormTableService.isTableNameExistedForUpd(table.getTableId(), table.getTableName());
				if(isExist){
					msg = "输入的表名在系统中已经存在!";
					writeResultMessage(response.getWriter(), msg, ResultMessage.Fail);
					return;
				}
				
				int rtn=bpmFormTableService.upd(table);
				if(rtn==-1){
					msg = "不要输入字段【"+TableModel.CUSTOMER_COLUMN_CURRENTUSERID+"】,该字段为保留字段!";
					writeResultMessage(response.getWriter(), msg, ResultMessage.Fail);
					return;
				}
				else if(rtn==-2){
					msg = "自定义数据表中已经有数据，字段不能设置为非空，请检查添加的字段!";
					writeResultMessage(response.getWriter(), msg, ResultMessage.Fail);
					return;
				}
				else if(rtn==0){
					 msg = getText("record.updated", "自定义表");
					 writeResultMessage(response.getWriter(), msg, ResultMessage.Success);
				}
			}
		}
		catch(Exception ex){
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"保存自定义表失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}
	
	
	@RequestMapping("saveExtTable")
	public void saveExtTable(HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String tableJson=request.getParameter("table");
		String fieldsJson=request.getParameter("fields");

		BpmFormTable table = (BpmFormTable) JSONObject.toBean(JSONObject.fromObject(tableJson), BpmFormTable.class);
		BpmFormField[] fields = (BpmFormField[]) JSONArray.toArray(JSONArray.fromObject(fieldsJson),BpmFormField.class);
		List<BpmFormField> list=Arrays.asList(fields) ;
		String msg="";
		try{
			
			if(table.getTableId()==0){
				String tableName=table.getTableName();
				String dsAlias=table.getDsAlias();
				if (bpmFormTableService.isTableNameExternalExisted(tableName,dsAlias)){
					msg = "表名已存在";
					writeResultMessage(response.getWriter(), msg, ResultMessage.Fail);
					return;
				}
				bpmFormTableService.addExt(table, list);
				msg = getText("record.added", "外部表定义表");
				writeResultMessage(response.getWriter(), msg, ResultMessage.Success);
			}
			else{
				
				int rtn=bpmFormTableService.upd(table);
				msg="该表已定义表单，不能在进行修改!";
				if(rtn==0){
					 msg = getText("record.updated", "自定义表");
				}
				writeResultMessage(response.getWriter(), msg, ResultMessage.Success);
			}
		}
		catch(Exception ex){
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"保存自定义表失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}
	/**
	 * 通过流程定义标题自动生成流程KEY
	 * @param request
	 * @return flowkey
	 * @throws Exception
	 */
	@RequestMapping("getFieldKey")
	public void getFieldKey(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String subject=RequestUtil.getString(request, "subject");
		if(StringUtil.isEmpty(subject))return ;
		String msg="";
		String pingyin =Pingyin.getFirstSpell(subject); 
		msg = pingyin;
		writeResultMessage(response.getWriter(), msg, ResultMessage.Success);	
	}

	/**
	 * 通过流程定义标题自动生成流程KEY
	 * @param request
	 * @return flowkey
	 * @throws Exception
	 */
	@RequestMapping("getTableKey")
	public void getTableKey(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String tableName=RequestUtil.getString(request, "subject");
		Long tableId=RequestUtil.getLong(request, "tableId");
		String msg="";
		String pingyin =Pingyin.getFirstSpell(tableName); 
		msg = pingyin;	
		try{			
			if(tableId==0){
				if (bpmFormTableService.isTableNameExisted(pingyin)){
					msg = "表名已存在";
					writeResultMessage(response.getWriter(), msg, ResultMessage.Fail);
					return;
				}
			}
			else{
				boolean isExist=bpmFormTableService.isTableNameExistedForUpd(tableId, pingyin);
				if(isExist){
					msg = "输入的表名在系统中已经存在!";
					writeResultMessage(response.getWriter(), msg, ResultMessage.Fail);
					return;
				}
			}
			writeResultMessage(response.getWriter(), msg, ResultMessage.Success);	
		}
		catch(Exception ex){
			writeResultMessage(response.getWriter(), ex.getMessage(), ResultMessage.Fail);
		}
	}
}
