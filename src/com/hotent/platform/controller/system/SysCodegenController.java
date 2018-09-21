package com.hotent.platform.controller.system;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.customertable.BaseTableMeta;
import com.hotent.core.customertable.SqlTypeConst;
import com.hotent.core.customertable.TableModel;
import com.hotent.core.customertable.impl.Db2TableMeta;
import com.hotent.core.customertable.impl.MySqlTableMeta;
import com.hotent.core.customertable.impl.OracleTableMeta;
import com.hotent.core.customertable.impl.SqlServerTableMeta;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.form.BpmFormField;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.system.SysCodeTemplate;
import com.hotent.platform.service.form.BpmFormFieldService;
import com.hotent.platform.service.form.BpmFormTableService;
import com.hotent.platform.service.system.SysCodeTemplateService;
/**
 * 对象功能:基于自定义表的代码生成器 控制器类
 * 开发公司:
 * 开发人员:zyp
 * 创建时间:2012-12-19 15:38:01
 */
@Controller
@RequestMapping("/platform/system/sysCodegen/")
public class SysCodegenController  extends BaseController  {
	@Resource
	private BpmFormTableService bpmFormTableService;
	@Resource
	private SysCodeTemplateService sysCodeTemplateService;
	@Resource
	private BpmFormFieldService bpmFormFieldService;
	
	/**
	 * 获取所有自定义表数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getTableData")
	public List<BpmFormTable> getTableData(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String tableName=RequestUtil.getString(request, "tableName","");
		List<BpmFormTable> tableList=new ArrayList<BpmFormTable>();
		List<BpmFormTable> mainTables=bpmFormTableService.getMainTables(tableName);
		BpmFormTable table=new BpmFormTable();
		table.setTableId(0L);
		table.setTableDesc("自定义表");
		table.setParentId(1L);
		table.setIsRoot((short)1);
		tableList.add(table);
		for(BpmFormTable mainTable:mainTables){
			mainTable.setParentId(table.getTableId());
			if(StringUtil.isEmpty(mainTable.getTableDesc())){
				mainTable.setTableDesc(mainTable.getTableName());
			}
			List<BpmFormTable> subTableList=bpmFormTableService.getSubTableByMainTableId(mainTable.getTableId());
			for(BpmFormTable subTable:subTableList){
				subTable.setParentId(mainTable.getTableId());
				if(StringUtil.isEmpty(subTable.getTableDesc())){
					subTable.setTableDesc(subTable.getTableName());
				}
				tableList.add(subTable);
			}
			tableList.add(mainTable);
		}
		return tableList;
	}
	
	@RequestMapping("detail")
	public ModelAndView genDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long tableId=RequestUtil.getLong(request, "tableId");
		List<SysCodeTemplate> templateList=sysCodeTemplateService.getAllTemps();
		return getAutoView().addObject("templateList",templateList);
	}
	
	@RequestMapping("codegen")
	public void codegen(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//文件相关参数
		String[] templateIds=request.getParameterValues("templateId");
		int override=RequestUtil.getInt(request, "override");
		String defKey=RequestUtil.getString(request, "defKey");
		//自定义表相关
		String[] tableNames=request.getParameterValues("tableName");
		String[] classNames=request.getParameterValues("className");
		String[] classVars=request.getParameterValues("classVar");
		String[] packageNames=request.getParameterValues("packageName");
		
		List<BpmFormTable>list=getTableModels(tableNames, classNames, classVars, packageNames);
		try {
			for(BpmFormTable model:list){
				genCode(model,templateIds, override,defKey);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "自定义表生成代码成功"));
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, "自定义表生成代码失败"));
		}
	}
	
	private List<BpmFormTable> getTableModels(String[] tableNames,String[] classNames,String[] classVars,String[] packageNames){
		
		List<BpmFormTable> list=new ArrayList<BpmFormTable>();
		List<BpmFormTable> subtables=new ArrayList<BpmFormTable>();
		for(int i=0;i<tableNames.length;i++){
			String tableName=tableNames[i];
			String className=classNames[i];
			String classVar=classVars[i];
			String packageName=packageNames[i];
			BpmFormTable bpmFormTable=bpmFormTableService.getByTableName(tableName);
			Map<String,String> tableVars=sysCodeTemplateService.getTableVars();
			Map<String,String>variable=new HashMap<String, String>();
			Set<String> set=tableVars.keySet();
			Iterator<String>iter=set.iterator();
			while(iter.hasNext()){
				String varName=iter.next();
				if("类名".equals(tableVars.get(varName))){
					variable.put(varName,className);
				}else if("包名".equals(tableVars.get(varName))){
					variable.put(varName,packageName);
				}else if("变量名".equals(tableVars.get(varName))){
					variable.put(varName,classVar);
				}
			}
			bpmFormTable.setVariable(variable);
			Long tableId=bpmFormTable.getTableId();
			List<BpmFormField> fieldList=bpmFormFieldService.getByTableId(tableId);
			//字段值来源为脚本计算时，脚本去掉换行处理
			for(BpmFormField field:fieldList){
				String script="";
				for(String s:field.getScript().split("\n")){
					script+=s.trim();
				}
				field.setScript(script);
			}
			bpmFormTable.setFieldList(fieldList);
			if(bpmFormTable.getIsMain()!=1){
				subtables.add(bpmFormTable);
			}
			list.add(bpmFormTable);
		}
		for(BpmFormTable subtable:subtables){
			System.out.println(subtable.getTableId());
			System.out.println(subtable.getMainTableId());
			for(BpmFormTable table:list){
				System.out.println(table.getTableId());
				System.out.println((table.getIsMain()==1)&&(table.getTableId().equals(subtable.getMainTableId())));
				if((table.getIsMain()==1)&&(table.getTableId().equals(subtable.getMainTableId()))){
					table.getSubTableList().add(subtable);
				}
			}
		}
		return list;
	}
	

	private void genCode(BpmFormTable table, String[] templateIds,int override,String defKey) throws Exception {
		String basePath=sysCodeTemplateService.getVariables().get("baseDir");
		String system=sysCodeTemplateService.getVariables().get("system");
		for(int i=0;i<templateIds.length;i++){
			Long templateId=Long.parseLong(templateIds[i]);
			SysCodeTemplate template=sysCodeTemplateService.getById(templateId);
			Map<String,String> variables=table.getVariable();
			variables.put("system", system);
			String tableName="W_"+table.getTableName().toUpperCase();
			Short isAppend=template.getIsAppend();
			String startTag=template.getStartTag();
			String endTag=template.getEndTag();
			String insertTag=template.getInsertTag();
			String fileName=template.getFileName();
			String path=basePath+File.separator+template.getFileDir();
			int isSub=template.getIsSub();
			
			Map<String,Object> model=new HashMap<String, Object>();
			model.put("table", table);
			model.put("system", system);
			if(StringUtil.isNotEmpty(defKey)){
				model.put("defKey", defKey);
			}
			if(table.getIsMain()!=1){
				if(isSub==0){
					continue;
				}
			}
			String templateStr=template.getHtml();
			String html="";
			FreemarkEngine freemarkEngine=new FreemarkEngine();
			if(StringUtil.isNotEmpty(template.getMacroAlias())){
				SysCodeTemplate macroTemp=sysCodeTemplateService.getByTemplateAlias(template.getMacroAlias());
				html=freemarkEngine.parseByStringTemplate(model,macroTemp.getHtml()+templateStr);
			}else{
				html=freemarkEngine.parseByStringTemplate(model,templateStr);
			}
			String fileStr=path+File.separator+fileName;
			String filePath=StringUtil.replaceVariable(fileStr, variables);
			
			if(isAppend!=null&&isAppend==1){
				startTag=StringUtil.replaceVariable(startTag, tableName);
				endTag=StringUtil.replaceVariable(endTag, tableName);
				appendFile(filePath,html,override,startTag,endTag,insertTag);
			}else{
				addFile(filePath,html,override);
			}
			System.out.println("----------文件："+StringUtil.replaceVariable(fileName, variables)+"生成成功！");
		}
		
	}
	
	

	private void addFile(String filePath, String html, int override) {
		File newFile=new File(filePath);
		if(newFile.exists()){
			if(override==1){
				FileUtil.deleteFile(filePath);
				FileUtil.writeFile(filePath, html);
			}else{
				return;
			}
		}else{
			FileUtil.writeFile(filePath, html);
		}
	}

	private void appendFile(String filePath, String html, int override,
			String startTag, String endTag,String insertTag) {
		if(StringUtil.isNotEmpty(startTag) && StringUtil.isNotEmpty(endTag)){
			html=startTag.trim() +"\r\n" + html  +"\r\n" + endTag.trim();
		}
		String content="";
		//文件存在
		File newFile=new File(filePath);
		if(newFile.exists()){
			content=FileUtil.readFile(filePath);
			if(StringUtil.isExist(content, startTag, endTag)){
				if(override==1){
					content=content.substring(0, content.indexOf(startTag))+html;
				}
			}else{
				if(content.indexOf(insertTag)!=-1){
					String[] aryContent=content.split(insertTag);
					content=aryContent[0] + html + "\r\n" + insertTag + aryContent[1];
				}
			}
		}
		FileUtil.writeFile(filePath, content);
		
	}
}
