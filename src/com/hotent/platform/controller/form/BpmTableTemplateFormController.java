package com.hotent.platform.controller.form;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.hotent.core.annotion.Action;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;

import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormField;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.form.BpmFormTemplate;
import com.hotent.platform.model.form.BpmTableTemplate;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.service.form.BpmFormFieldService;
import com.hotent.platform.service.form.BpmFormTableService;
import com.hotent.platform.service.form.BpmFormTemplateService;
import com.hotent.platform.service.form.BpmTableTemplateService;

import freemarker.template.TemplateException;

/**
 * 对象功能:查看表格业务数据的模板 控制器类 
 * 开发公司:广州宏天软件有限公司 
 * 开发人员:heyifan 
 * 创建时间:2012-05-22 14:58:08
 */
@Controller
@RequestMapping("/platform/form/bpmTableTemplate/")
public class BpmTableTemplateFormController extends BaseFormController {
	@Resource
	private BpmTableTemplateService bpmTableTemplateService;
	
	@Resource
	private BpmFormTableService bpmFormTableService;
	
	@Resource
	private BpmFormFieldService bpmFormFieldService;
	
	@Resource
	private BpmFormTemplateService bpmFormTemplateService;

	@Resource
	private FreemarkEngine freemarkEngine;
	
	@Resource
	private BpmFormDefService bpmFormDefService;
	/**
	 * 添加或更新查看表格业务数据的模板。
	 * 
	 * @param request
	 * @param response
	 * @param bpmTableTemplate
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("save")
	@Action(description = "添加或更新查看表格业务数据的模板")
	public void save(HttpServletRequest request, HttpServletResponse response, BpmTableTemplate bpmTableTemplate, BindingResult bindResult) throws Exception {

		ResultMessage resultMessage = validForm("bpmTableTemplate", bpmTableTemplate, bindResult, request);
		if (resultMessage.getResult() == ResultMessage.Fail) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String ctxPath=request.getContextPath();
		String resultMsg = null;
		if (bpmTableTemplate.getId() == 0) {
			bpmTableTemplate.setId(UniqueIdUtil.genId());
			
			genTemplate(bpmTableTemplate,ctxPath);
			
			bpmTableTemplateService.add(bpmTableTemplate);
			resultMsg = getText("record.added", "查看表格业务数据的模板");
		} else {
			bpmTableTemplateService.update(bpmTableTemplate);
			resultMsg = getText("record.updated", "查看表格业务数据的模板");
		}
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
	}
	
	/**
	 * 生成模版。
	 * @param bpmTableTemplate
	 * @throws TemplateException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private void genTemplate(BpmTableTemplate bpmTableTemplate,String ctxPath) throws TemplateException, IOException{
		Long formKey=bpmTableTemplate.getFormKey();
		//主表的处理
		BpmFormDef bpmFormDef=bpmFormDefService.getDefaultVersionByFormKey(formKey);
		
		Long tableId=bpmFormDef.getTableId();
		
		bpmTableTemplate.setTableId(bpmFormDef.getTableId());
		bpmTableTemplate.setCategoryId(bpmFormDef.getCategoryId());
		
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		BpmFormTable table = bpmFormTableService.getById(tableId);
		List<BpmFormField> fields = bpmFormFieldService.getByTableId(tableId);
		List<BpmFormField> conditionFields = getConditionList(fields);
		
		for(BpmFormField bpmformfield:fields){
			//字段全部转换为小写
			bpmformfield.setFieldName(bpmformfield.getFieldName().toLowerCase());
		}			
		fieldsMap.put("formKey", formKey);
		fieldsMap.put("table", table);
		fieldsMap.put("fields", fields);
		fieldsMap.put("conditionFields", conditionFields);
		fieldsMap.put("templateId", bpmTableTemplate.getId());
		fieldsMap.put("ctxPath",ctxPath);
		
		
		//子表处理
		Map<String, Object> subFields = new HashMap<String, Object>();
		List<BpmFormTable> subTableList = new ArrayList<BpmFormTable>();
		List<BpmFormTable> subtables=bpmFormTableService.getSubTableByMainTableId(tableId);
		for(BpmFormTable subtable:subtables){
			List<BpmFormField> subTableFields = bpmFormFieldService.getByTableId(subtable.getTableId());
			//字段全部转换为小写
			for(BpmFormField bpmformfield:subTableFields){
				bpmformfield.setFieldName(bpmformfield.getFieldName().toLowerCase());
			}
			subFields.put(subtable.getTableName(), subTableFields);
			subTableList.add(subtable);
		}
		
		BpmFormTemplate tableTemplateList = bpmFormTemplateService.getById(Long.parseLong(bpmTableTemplate.getHtmlList()));
		String listHtml="";
		if(tableTemplateList!=null)
			listHtml=tableTemplateList.getHtml();
		Map detailMap=new HashMap();
		//主表字段
		detailMap.put("fields", fields);
		//子表字段
		detailMap.put("subFields", subFields);
		//子表对象
		detailMap.put("subTables", subTableList);
		
		String listResult=freemarkEngine.parseByStringTemplate(fieldsMap, listHtml);
		bpmTableTemplate.setHtmlList(listResult);
	}
	
	/**
	 * 返回条件字段列表。
	 * @param fields	表的全部字段。
	 * @return
	 */
	private List<BpmFormField> getConditionList(List<BpmFormField> fields){
		List<BpmFormField> list=new ArrayList<BpmFormField>();
		for(BpmFormField field:fields){
			if(field.getIsQuery()==1 ){
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * 
	 * @param ID
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModelAttribute
	protected BpmTableTemplate getFormObject(@RequestParam("id") Long id, Model model) throws Exception {
		logger.debug("enter BpmTableTemplate getFormObject here....");
		BpmTableTemplate bpmTableTemplate = null;
		if (id != 0) {
			bpmTableTemplate = bpmTableTemplateService.getById(id);
		} else {
			bpmTableTemplate = new BpmTableTemplate();
		}
		return bpmTableTemplate;
	}

}
