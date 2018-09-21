package com.hotent.platform.service.form;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.XmlBeanUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.dao.bpm.BpmNodeSetDao;
import com.hotent.platform.dao.form.BpmFormDefDao;
import com.hotent.platform.dao.form.BpmFormRightsDao;
import com.hotent.platform.dao.form.BpmTableTemplateDao;
import com.hotent.platform.dao.system.GlobalTypeDao;
import com.hotent.platform.model.bpm.BpmFormRun;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.FormModel;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormRights;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.form.BpmTableTemplate;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.service.bpm.BpmFormRunService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.xml.form.BpmFormDefXml;
import com.hotent.platform.xml.form.BpmFormDefXmlList;
import com.hotent.platform.xml.table.BpmFormTableXml;
import com.hotent.platform.xml.util.MsgUtil;

/**
 * 对象功能:BPM_FORM_DEF Service类 开发公司: 开发人员:xwy 创建时间:2011-12-22 11:07:56
 */
@Service
public class BpmFormDefService extends BaseService<BpmFormDef> {
	@Resource
	private BpmFormDefDao dao;
	@Resource
	private BpmFormRightsService bpmFormRightsService;

	@Resource
	private BpmFormHandlerService bpmFormHandlerService;

	@Resource
	private BpmFormRunService bpmFormRunService;

	@Resource
	private BpmTableTemplateDao bpmTableTemplateDao;

	@Resource
	private BpmFormTableService bpmFormTableService;

	@Resource
	private BpmFormRightsDao bpmFormRightsDao;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@Resource
	private BpmService bpmService;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private TaskService taskService;
	@Resource
	private BpmNodeSetDao bpmNodeSetDao;
	
	@Resource
	private GlobalTypeDao globalTypeDao;

	public BpmFormDefService() {
	}

	@Override
	protected IEntityDao<BpmFormDef, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 获得已发布版本数量
	 * 
	 * @param formKey
	 *            在表单版本中使用
	 * @return
	 */
	public Integer getCountByFormKey(Long formKey) {
		return dao.getCountByFormKey(formKey);
	}

	/**
	 * 获得默认版本
	 * 
	 * @param formKey
	 *            在表单版本中使用
	 * @return
	 */
	public BpmFormDef getDefaultVersionByFormKey(Long formKey) {
		return dao.getDefaultVersionByFormKey(formKey);
	}

	/**
	 * 根据formkey查询所有的表单定义版本。
	 * 
	 * @param formKey
	 *            在表单版本中使用
	 * @return
	 */
	public List<BpmFormDef> getByFormKey(Long formKey) {
		return dao.getByFormKey(formKey);
	}

	/**
	 * 增加表单定义。
	 * 
	 * @param bpmFormDef
	 *            自定义表单对象
	 * @throws Exception
	 */
	public void addForm(BpmFormDef bpmFormDef, JSONObject jsonObject)
			throws Exception {
		long id = UniqueIdUtil.genId();
		bpmFormDef.setFormDefId(id);
		bpmFormDef.setFormKey(id);
		bpmFormDef.setVersionNo(1);
		bpmFormDef.setIsDefault((short) 1);
		bpmFormDef.setIsPublished((short) 0);
		dao.add(bpmFormDef);
		// 保存表单权限
		bpmFormRightsService.save(id, jsonObject);
	}

	/**
	 * 更新表单及权限。
	 * 
	 * @param bpmFormDef
	 *            自定义表单对象
	 * @throws Exception
	 */
	public void updateForm(BpmFormDef bpmFormDef, JSONObject jsonObject)
			throws Exception {
		Long formKey = bpmFormDef.getFormKey();
		// 更新table
		dao.update(bpmFormDef);
		// 保存表单权限
		bpmFormRightsService.save(formKey, jsonObject);
	}

	/**
	 * 发布
	 * 
	 * @param formDefId
	 *            自定义表单Id
	 * @param operator
	 *            发布人
	 * @throws Exception
	 */
	public void publish(Long formDefId, String operator) throws Exception {
		// 设为已发布
		BpmFormDef formDef = dao.getById(formDefId);

		formDef.setIsPublished((short) 1);
		formDef.setPublishedBy(operator);
		formDef.setPublishTime(new Date());
		dao.update(formDef);

	}

	/**
	 * 设为默认版本。
	 * 
	 * @param formDefId
	 *            自定义表单Id
	 * @param formKey
	 *            在表单版本使用
	 */
	public void setDefaultVersion(Long formDefId, Long formKey) {
		dao.setDefaultVersion(formKey, formDefId);
	}

	/**
	 * 根据表单定义id创建新的表单版本。 表单定义ID
	 * 
	 * @param formDefId
	 *            自定义表单Id
	 * @throws Exception
	 */
	public void newVersion(Long formDefId) throws Exception {
		BpmFormDef formDef = dao.getById(formDefId);
		Integer rtn = dao.getMaxVersionByFormKey(formDef.getFormKey());
		Long newFormDefId = UniqueIdUtil.genId();
		// 创建新的版本
		BpmFormDef newVersion = (BpmFormDef) formDef.clone();
		newVersion.setFormDefId(newFormDefId);
		newVersion.setIsDefault((short) 0);
		newVersion.setIsPublished((short) 0);
		newVersion.setPublishedBy("");

		newVersion.setVersionNo(rtn + 1);
		dao.add(newVersion);
		// 拷贝表单权限

	}

	/**
	 * 添加复制的表单，包括表单权限信息
	 * 
	 * @param bpmFormDef
	 * @param oldFormkey
	 */
	public void copyForm(BpmFormDef bpmFormDef, Long oldFormkey) {
		dao.add(bpmFormDef);
		Long formKey = bpmFormDef.getFormKey();
		if (bpmFormDef.getDesignType() == 0) {
			List<BpmFormRights> list = bpmFormRightsDao
					.getByFormDefId(oldFormkey);
			for (BpmFormRights bpmFormRights : list) {
				Long newId = UniqueIdUtil.genId();
				bpmFormRights.setId(newId);
				bpmFormRights.setFormDefId(formKey);
				bpmFormRightsDao.add(bpmFormRights);
			}
		}
	}

	/**
	 * 根据BpmFormRun取得表单。 表单分为： 1.在线表单。 2.url表单。
	 * 
	 * @param bpmNodeSet
	 * @param actDefId
	 * @param nodeId
	 * @param instanceId
	 * @param userId
	 * @param ctxPath
	 * @param bussinessKey
	 * @return
	 * @throws Exception
	 */
	public FormModel doGetForm(ProcessRun processRun, String nodeId, Long userId,
			String ctxPath, Map<String, Object> variables) throws Exception {
		String instanceId = processRun.getActInstId();
		String actDefId = processRun.getActDefId();
		Long defId = processRun.getDefId();

		BpmFormRun bpmFormRun = bpmFormRunService.getByInstanceAndNode(
				instanceId, nodeId);

		FormModel formModel = new FormModel();
		// 运行时存在。
		if (bpmFormRun != null) {
			Long formDefId = bpmFormRun.getFormdefId();
			BpmFormDef bpmFormDef = dao.getById(formDefId);
			if (bpmFormDef != null) {
				String formHtml = bpmFormHandlerService.obtainHtml(bpmFormDef,
						processRun, userId, nodeId, ctxPath,variables);
				formModel.setFormHtml(formHtml);
			}
			return formModel;
		}

		BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId,
				nodeId);
		if (isFormEmpty(bpmNodeSet)) {
			bpmNodeSet = bpmNodeSetService.getBySetType(defId,
					BpmNodeSet.SetType_GloabalForm);
		}
		if (bpmNodeSet == null)
			return formModel;
		// 获取在线表单
		if (BpmConst.OnLineForm.equals(bpmNodeSet.getFormType())) {
			BpmFormDef bpmFormDef = dao.getDefaultPublishedByFormKey(bpmNodeSet
					.getFormKey());
			String formHtml = bpmFormHandlerService.obtainHtml(bpmFormDef,
					processRun, userId, nodeId, ctxPath,variables);
			formModel.setFormHtml(formHtml);
		} else {
			// 获取流程实例ID
			String bussinessKey = processRun.getBusinessKey();
			String formUrl = bpmNodeSet.getFormUrl();
			String detailUrl = bpmNodeSet.getDetailUrl();
			if (StringUtil.isNotEmpty(formUrl)
					&& StringUtil.isNotEmpty(bussinessKey)) {
				formUrl = getFormUrl(formUrl, bussinessKey, variables, ctxPath);
				formModel.setFormUrl(formUrl);
				formModel.setFormType(BpmConst.UrlForm);
			}
			if (StringUtil.isNotEmpty(detailUrl)
					&& StringUtil.isNotEmpty(bussinessKey)) {
				detailUrl = getFormUrl(detailUrl, bussinessKey, variables,
						ctxPath);
				formModel.setDetailUrl(detailUrl);
			}
		}

		return formModel;
	}

	private String getFormUrl(String formUrl, String bussinessKey,
			Map<String, Object> variables, String ctxPath) {
		String url = formUrl.replaceFirst(BpmConst.FORM_PK_REGEX, bussinessKey);
		if (variables != null)
			url = getUrlByVariables(url, variables);
		if (!formUrl.startsWith("http")) {
			url = ctxPath + url;
		}
		return url;
	}

	private boolean isFormEmpty(BpmNodeSet bpmNodeSet) {
		if (bpmNodeSet == null) {
			return true;
		}
		short formType = bpmNodeSet.getFormType();
		Long formKey = bpmNodeSet.getFormKey();
		// 没有设置表单的情况
		if (formType == -1) {
			return true;
		}
		// 在线表单的情况
		if (formType == 0) {
			if (formKey == null || formKey == 0) {
				return true;
			}
		}
		// url表单的情况。
		else {
			String formUrl = bpmNodeSet.getFormUrl();
			if (StringUtil.isEmpty(formUrl)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据任务id 获取API调用的跳转URL 。
	 * 
	 * @param taskId
	 * @param defId
	 * @param nodeId
	 * @param businessKey
	 * @param ctxPath
	 * @return
	 */
	public String getFormUrl(String taskId, Long defId, String nodeId,
			String businessKey, String ctxPath) {
		String formUrl = "";

		BpmNodeSet nodeSet = bpmNodeSetService.getByDefIdNodeId(defId, nodeId);
		if (nodeSet != null) {
			formUrl = nodeSet.getFormUrl();
		}
		if (StringUtil.isEmpty(formUrl)) {
			BpmNodeSet node = bpmNodeSetService.getBySetType(defId,
					BpmNodeSet.SetType_GloabalForm);
			formUrl = node.getFormUrl();
		}
		formUrl = formUrl.replaceFirst(BpmConst.FORM_PK_REGEX, businessKey)
				.replaceFirst("\\{taskId\\}", taskId);
		if (!formUrl.startsWith("http")) {
			formUrl = ctxPath + formUrl;
		}
		return formUrl;

	}

	/**
	 * 替换地址
	 * orderNo={orderNo}
	 * @param url
	 * @param variables
	 * @return
	 */
	private String getUrlByVariables(String url, Map<String, Object> variables) {
		Pattern regex = Pattern.compile("\\{(.*?)\\}");
		Matcher regexMatcher = regex.matcher(url);
		while (regexMatcher.find()) {
			String toreplace = regexMatcher.group(0);
			String varName = regexMatcher.group(1);
			if (!variables.containsKey(varName))
				continue;
			url = url.replace(toreplace, variables.get(varName).toString());
		}
		return url;
	}

	/**
	 * 取得发布的表单。
	 * 
	 * @param queryFilter
	 * @return
	 */
	public List<BpmFormDef> getPublished(QueryFilter queryFilter) {
		return dao.getPublished(queryFilter);
	}

	/**
	 * 根据表单key获取默认的表单定义。
	 * 
	 * @param formKey
	 * @return
	 */
	public BpmFormDef getDefaultPublishedByFormKey(Long formKey) {
		return dao.getDefaultPublishedByFormKey(formKey);
	}

	/**
	 * 判断表单是否已经被使用。
	 * 
	 * @param formKey
	 *            表单key
	 * @return
	 */
	public int getFlowUsed(Long formKey) {
		int rtn = dao.getFlowUsed(formKey);
		return rtn;
	}

	/**
	 * 根据formkey删除数据。
	 * 
	 * <pre>
	 * 	如果表已经生成并且表单是通过设计器进行设计的那么将删除所创建的表。
	 * </pre>
	 * 
	 * @param formKey
	 * @throws SQLException
	 */
	public void delByFormKey(Long formKey) throws SQLException {
		BpmFormDef bpmFormDef = dao.getDefaultVersionByFormKey(formKey);
		Long tableId = bpmFormDef.getTableId();
		// 删除表单权限
		bpmFormRightsService.deleteByTableId(tableId);
		// 先删除表单，后判断是否还有表单使用该表
		dao.delByFormKey(formKey);
		bpmTableTemplateDao.delByFormKey(formKey);
		// tableId大于零并且有表单生成。
		if (tableId > 0 && bpmFormDef.getDesignType() == 1) {
			BpmFormTable bpmFormTable = bpmFormTableService
					.getTableById(tableId);
			// 是否还有表单使用该表
			boolean tableHasForm = dao.isTableHasFormDef(tableId);
			if (bpmFormTable != null && !tableHasForm) {
				bpmFormTableService.dropTable(bpmFormTable);
				bpmFormTableService.delTable(bpmFormTable);
			}
		}
	}

	/**
	 * 保存表单。
	 * 
	 * <pre>
	 * 	1.表单输入新创建的表单。
	 * 		1.保存表单。
	 * 		
	 *  2.表单未发布。
	 *  	1.保存表单。
	 *  	
	 *  3.表单已经发布的情况，表单已经发布，数据库表已经创建。
	 *  	1.保存表单。
	 *  	2.表单是否有其他的表单定义情况。
	 *  		1.相同的表不止对应一个表单的情况，对表做更新处理。
	 *  		2.没有数据的情况，表删除重建。
	 * </pre>
	 * 
	 * @param bpmFormdef
	 * @param bpmFormTable
	 * @throws Exception
	 */
	public void saveForm(BpmFormDef bpmFormdef, BpmFormTable bpmFormTable,
			boolean isPublish) throws Exception {
		if (bpmFormdef.getFormDefId() == 0) {
			Long formDefId = UniqueIdUtil.genId();
			bpmFormdef.setFormDefId(formDefId);
			bpmFormdef.setFormKey(formDefId);
			bpmFormdef.setDesignType(BpmFormDef.DesignType_CustomDesign);
			bpmFormdef.setIsDefault((short) 1);
			bpmFormdef.setVersionNo(1);
			Long tableId = 0L;
			if (isPublish) {
				tableId = bpmFormTableService.saveTable(bpmFormTable);
				bpmFormdef.setIsPublished((short) 1);
				bpmFormdef.setPublishTime(new Date());
			} else {
				bpmFormdef.setIsPublished((short) 0);
				bpmFormdef.setPublishedBy("");
			}
			bpmFormdef.setTableId(tableId);
			dao.add(bpmFormdef);
		} else {
			// 当前为发布或者表单已经分布。
			if (isPublish || bpmFormdef.getIsPublished() == 1) {
				Long tableId = bpmFormdef.getTableId();
				bpmFormTable.setTableId(tableId);
				tableId = bpmFormTableService.saveTable(bpmFormTable);
				bpmFormdef.setTableId(tableId);
				bpmFormdef.setIsPublished((short) 1);
				bpmFormdef.setPublishTime(new Date());
			}
			dao.update(bpmFormdef);
		}
	}

	/**
	 * 获取现有表单Id函数
	 * 
	 * @param nodeSet
	 * @return
	 */
	public Long getCurrentTableId(BpmNodeSet nodeSet) {
		Long formId = 0L;
		BpmFormDef bpmFormDef;
		// 节点挂钩表单不为空时取节点表单
		if (nodeSet.getFormType().equals(Short.parseShort("0"))) {
			bpmFormDef = dao.getDefaultVersionByFormKey(nodeSet.getFormKey());
			if (bpmFormDef != null) {
				formId = bpmFormDef.getFormDefId();
			}
		} else { // 节点表单为空时取全局表单
			BpmNodeSet globalForm = bpmNodeSetDao.getBySetType(
					nodeSet.getDefId(), BpmNodeSet.SetType_GloabalForm);
			if (globalForm != null) {
				bpmFormDef = dao.getDefaultVersionByFormKey(globalForm
						.getFormKey());
				if (bpmFormDef != null) {
					formId = bpmFormDef.getFormDefId();
				}
			}
		}
		return formId;
	}

	/**
	 * 
	 * 导出表单XML
	 * 
	 * <pre>
	 * 1.导出流程定义
	 * 2.导出流程定义权限
	 * 3.导出数据模板
	 * </pre>
	 * 
	 * @param formDefIds
	 * @param map 是否导出的Map列表
	 * @return
	 * @throws Exception
	 */
	public String exportXml(Long[] formDefIds, Map<String, Boolean> map) throws Exception {
		BpmFormDefXmlList bpmFormDefXmls = new BpmFormDefXmlList();
		List<BpmFormDefXml> list = new ArrayList<BpmFormDefXml>();
		for (int i = 0; i < formDefIds.length; i++) {
			BpmFormDef bpmFormDef = dao.getById(formDefIds[i]);
			BpmFormDefXml bpmFormDefXml = exportBpmFormDef(bpmFormDef,
					BpmFormDef.IS_DEFAULT,map);
			list.add(bpmFormDefXml);
		}
		bpmFormDefXmls.setBpmFormDefXmlList(list);
		return XmlBeanUtil.marshall(bpmFormDefXmls, BpmFormDefXmlList.class);
	}

	public Map<String, Boolean> getDefaultExportMap(Map<String, Boolean> map){
		if(BeanUtils.isEmpty(map)){
			 map = new HashMap<String, Boolean>();
			map.put("bpmFormDef", true);
			map.put("bpmFormTable", false);
			map.put("bpmFormDefOther", true);
			map.put("bpmFormRights",true);
			map.put("bpmTableTemplate", true);
		}
		return map;
	}
	
	/**
	 * 导出表单的信息
	 * 
	 * @param bpmFormDef
	 *            表单
	 * @param isDefault
	 *            是否是默认 默认则要导出其它表单和模板
	 * @param map  是否导出的Map列表
	 * @return
	 */
	public BpmFormDefXml exportBpmFormDef(BpmFormDef bpmFormDef, Short isDefault, Map<String, Boolean> map) {
		BpmFormDefXml bpmFormDefXml = new BpmFormDefXml();
		// 表单
		bpmFormDefXml.setBpmFormDef(bpmFormDef);
		Long formDefId = bpmFormDef.getFormDefId();
		Long formKey = bpmFormDef.getFormKey();
		
		if (isDefault.shortValue() == BpmFormDef.IS_DEFAULT.shortValue()) {
			//导出对应的表
			if(map.get("bpmFormTable"))
				exportBpmFormTableXml(bpmFormDef,bpmFormDefXml);
			
			if (BeanUtils.isNotEmpty(formKey)) {
				// 导出自定义表单 非默认版本
				if(map.get("bpmFormDefOther"))
					 exportBpmFormDefOther(formKey,map,bpmFormDefXml);
				// 数据模板
				if(map.get("bpmTableTemplate"))
						 exportBpmTableTemplate(formKey,bpmFormDefXml);
			}
		}

		if (BeanUtils.isNotEmpty(formDefId)) {
			// 表单权限
			 if(map.get("bpmFormRights"))
				 exportBpmFormRights(formDefId,bpmFormDefXml);
		
		}
		return bpmFormDefXml;
	}
	
	/**
	 * 导出对于的表
	 * @param bpmFormDef
	 * @param bpmFormDefXml
	 */
	private void exportBpmFormTableXml(BpmFormDef bpmFormDef,
			BpmFormDefXml bpmFormDefXml) {
		if(BeanUtils.isEmpty(bpmFormDef.getTableId()))
			return;
		BpmFormTable formTable = bpmFormTableService.getById(bpmFormDef.getTableId());
		BpmFormTableXml bpmFormTableXml = bpmFormTableService.exportTable(formTable,null);
		bpmFormDefXml.setBpmFormTableXml(bpmFormTableXml);
	}


	/**
	 * 导出其它版本的自定义表单
	 * @param formKey
	 * @param map
	 * @param bpmFormDefXml
	 */
	private void exportBpmFormDefOther(Long formKey, Map<String, Boolean> map, BpmFormDefXml bpmFormDefXml) {
		List<BpmFormDef> formDefList = dao.getByFormKeyIsDefault(
				formKey,BpmFormDef.IS_NOT_DEFAULT);
		if (BeanUtils.isEmpty(formDefList)) return ;
		
		List<BpmFormDefXml> list = new ArrayList<BpmFormDefXml>();
		for (BpmFormDef formDef : formDefList) {
			BpmFormDefXml formDefXml = exportBpmFormDef(formDef,
					BpmFormDef.IS_NOT_DEFAULT,map);
			list.add(formDefXml);
		}
		bpmFormDefXml.setBpmFormDefXmlList(list);
	}
	
	/**
	 * 导出表单权限
	 * @param formDefId
	 * @param bpmFormDefXml
	 */
	private void exportBpmFormRights(Long formDefId, BpmFormDefXml bpmFormDefXml) {
		List<BpmFormRights> bpmFormRightsList = bpmFormRightsDao
				.getByFormDefId(formDefId);
		if (BeanUtils.isNotEmpty(bpmFormRightsList))
			bpmFormDefXml.setBpmFormRightsList(bpmFormRightsList);
	}

	
	/**
	 * 导出数据模板
	 * @param formKey
	 * @param bpmFormDefXml
	 */
	private void exportBpmTableTemplate(Long formKey,
			BpmFormDefXml bpmFormDefXml) {
		List<BpmTableTemplate> bpmTableTemplateList = bpmTableTemplateDao
				.getByFormKey(formKey);
		if (BeanUtils.isNotEmpty(bpmTableTemplateList)) 
			bpmFormDefXml.setBpmTableTemplateList(bpmTableTemplateList);
		
	}


	/**
	 * 导入xml
	 * <pre>
	 * 1.导入流程定义
	 * 2.导入流程定义权限
	 * 3.导入数据模板
	 * </pre>
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public void importXml(InputStream inputStream) throws Exception {
		Document doc = Dom4jUtil.loadXml(inputStream);
		Element root = doc.getRootElement();
		// 验证格式是否正确
		this.checkXMLFormat(root);

		String xmlStr = root.asXML();
		BpmFormDefXmlList bpmFormDefXmlList = (BpmFormDefXmlList) XmlBeanUtil
				.unmarshall(xmlStr, BpmFormDefXmlList.class);
		List<BpmFormDefXml> list = bpmFormDefXmlList.getBpmFormDefXmlList();
		for (BpmFormDefXml bpmFormDefXml : list) {
			this.importBpmFormDef(bpmFormDefXml, BpmFormDef.IS_DEFAULT);
			MsgUtil.addSplit();
		}
	}

	/**
	 * 检查XML格式是否正确
	 * 
	 * @param root
	 * @param msg
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void checkXMLFormat(Element root) throws Exception {
		String msg = "导入文件格式不对";
		if (!root.getName().equals("form"))
			throw new Exception(msg);
		List<Element> itemLists = root.elements();
		for (Element elm : itemLists) {
			if (!elm.getName().equals("formDefs"))
				throw new Exception(msg);
		}
	}

	/**
	 * 导出表单信息
	 * 
	 * @param bpmFormDefXml
	 * @param formKey 
	 * @param formKey
	 * @param isDefault
	 * @return
	 * @throws Exception
	 */
	public void importBpmFormDef(BpmFormDefXml bpmFormDefXml, Short isDefault)
			throws Exception {

		BpmFormDef bpmFormDef = bpmFormDefXml.getBpmFormDef();
		Long formDefId = bpmFormDef.getFormDefId();
		Long formKey = bpmFormDef.getFormKey();
		BpmFormDef def = dao.getById(formDefId);
		if (BeanUtils.isNotEmpty(def)) {
			MsgUtil.addMsg(MsgUtil.WARN, "自定义表单已经存在,表单ID："+formDefId+",该记录终止导入!");
			return;
		}
		
		// 导入表单信息
		this.importBpmFormDef(bpmFormDef);
		// 导入其它版本, 表单模板
		if (isDefault.shortValue() == BpmFormDef.IS_DEFAULT.shortValue()) {
			BpmFormTableXml bpmFormTableXml = bpmFormDefXml.getBpmFormTableXml();
			if (BeanUtils.isNotEmpty(bpmFormTableXml)) {
				bpmFormTableService.importBpmFormTable(bpmFormTableXml,  new Long(0));
			}
			
			// 导入其它版本
			List<BpmFormDefXml> list = bpmFormDefXml.getBpmFormDefXmlList();
			if (BeanUtils.isNotEmpty(list)) {
				for (BpmFormDefXml formDefXml : list) {
					// 导入其它版本
					this.importBpmFormDef(formDefXml, BpmFormDef.IS_NOT_DEFAULT);
				}
			}
			// 表单模板
			List<BpmTableTemplate> bpmTableTemplateList = bpmFormDefXml
					.getBpmTableTemplateList();
			if (BeanUtils.isNotEmpty(bpmTableTemplateList)) {
				for (BpmTableTemplate bpmTableTemplate : bpmTableTemplateList) {
					this.importBpmTableTemplate(bpmTableTemplate, formKey);
				}
			}
		}

		// 表单权限
		List<BpmFormRights> bpmFormRightsList = bpmFormDefXml
				.getBpmFormRightsList();
		if (BeanUtils.isNotEmpty(bpmFormRightsList)) {
			for (BpmFormRights bpmFormRights : bpmFormRightsList) {
				this.importBpmFormRights(bpmFormRights, formDefId);
			}
		}

	}

	/**
	 * 导入的表单信息保存
	 * 
	 * @param bpmFormDef
	 * @param tableMap
	 * @param msg
	 * @return
	 */
	private void importBpmFormDef(BpmFormDef bpmFormDef) throws Exception {
		// 设置分类
		this.setCategoryId(bpmFormDef);
		// 设置tableId
		this.setTableId(bpmFormDef);
		bpmFormDef.setIsPublished(BpmFormDef.IS_NOT_PUBLISHED);
		bpmFormDef.setPublishedBy(null);
		bpmFormDef.setPublishTime(null);
		dao.add(bpmFormDef);
		MsgUtil.addMsg(MsgUtil.SUCCESS, "自定义表单:"+bpmFormDef.getSubject()+",该记录成功导入!");
	}

	/**
	 * 设置表ID
	 * 
	 * @param bpmFormDef
	 * @param tableMap
	 * @return
	 */
	private void setTableId(BpmFormDef bpmFormDef) {
		if (BeanUtils.isEmpty(bpmFormDef.getTableId()))
			return ;

		BpmFormTable bpmFormTable = bpmFormTableService.getById(bpmFormDef.getTableId());
		if (BeanUtils.isEmpty(bpmFormTable))
			bpmFormDef.setTableId(null);
		
	}

	/**
	 * 设置分类
	 * 
	 * @param bpmFormDef
	 * @return
	 */
	private void setCategoryId(BpmFormDef bpmFormDef) {
		if (BeanUtils.isEmpty(bpmFormDef.getCategoryId()))
			return ;
		GlobalType globalType = globalTypeDao.getById(bpmFormDef
				.getCategoryId());
		if (BeanUtils.isEmpty(globalType))
			bpmFormDef.setCategoryId(null);
		
	}

	/**
	 * 保存 表单权限
	 * 
	 * @param bpmFormRights
	 * @param formDefId
	 * @param msg
	 * @return
	 */
	private void importBpmFormRights(BpmFormRights bpmFormRights, Long formDefId)
			throws Exception {
		BpmFormRights formRights = bpmFormRightsDao.getById(bpmFormRights.getId());
		if (BeanUtils.isNotEmpty(formRights)) {
			MsgUtil.addMsg(MsgUtil.WARN, "表单权限已经存在,表单权限ID："+bpmFormRights.getId()+",该记录终止导入!");
			return;
		}
		bpmFormRights.setFormDefId(formDefId);
		bpmFormRightsDao.add(bpmFormRights);
		MsgUtil.addMsg(MsgUtil.SUCCESS, " 表单权限:"+bpmFormRights.getName()+",该记录成功导入!");
	}

	/**
	 * 保存 数据模板
	 * 
	 * @param bpmTableTemplate
	 * @param long1
	 * @param msg
	 * @return
	 */
	private void importBpmTableTemplate(BpmTableTemplate bpmTableTemplate,
			Long formKey) throws Exception {
		BpmTableTemplate tableTemplate = bpmTableTemplateDao.getById(bpmTableTemplate.getId());
		if (BeanUtils.isNotEmpty(tableTemplate)) {
			MsgUtil.addMsg(MsgUtil.WARN, " 数据模板已经存在,数据模板ID："+tableTemplate.getId()+",该记录终止导入!");
			return;
		}
		bpmTableTemplate.setFormKey(formKey);
		bpmTableTemplateDao.add(bpmTableTemplate);
		MsgUtil.addMsg(MsgUtil.SUCCESS, " 数据模板:"+bpmTableTemplate.getTemplateName()+",该记录成功导入!");
	}

}
