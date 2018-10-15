package com.hotent.platform.controller.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.ProRunTblPkDao;
import com.hotent.platform.model.bpm.ProRunTblPk;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormDialog;
import com.hotent.platform.model.form.BpmFormTemplate;
import com.hotent.platform.model.form.BpmTableTemplate;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.service.form.BpmFormHandlerService;
import com.hotent.platform.service.form.BpmFormTemplateService;
import com.hotent.platform.service.form.BpmTableTemplateService;
import com.hotent.platform.service.form.IDataPermission;
import com.hotent.platform.service.form.PermissionSelector;
import com.hotent.platform.service.system.GlobalTypeService;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;

/**
 * 对象功能:查看表格业务数据的模板 控制器类 开发公司:广州宏天软件有限公司 开发人员:heyifan 创建时间:2012-05-22 14:58:08
 */
@Controller
@RequestMapping("/platform/form/bpmTableTemplate/")
public class BpmTableTemplateController extends BaseController {
	private static String DEFAULT_ORDER_SEQ="DESC";
	private static String DEFAULT_SORT_FIELD="CREATETIME";
	@Resource
	private BpmTableTemplateService bpmTableTemplateService;
	@Resource
	private BpmFormTemplateService bpmFormTemplateService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private BpmFormHandlerService bpmFormHandlerService;
	@Resource
	private FreemarkEngine freemarkEngine;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private BpmFormDefService bpmFormDefService;
	@Resource
	private PermissionSelector permissionSelector;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private ProRunTblPkDao proRunTblPkDao;

	/**
	 * 取得查看表格业务数据的模板分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看表格业务数据的模板分页列表", operateType = "业务数据模板管理")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "bpmTableTemplateItem");
		Long typeId = RequestUtil.getLong(request, "typeId", 0);
		List<BpmTableTemplate> list = getList(filter, typeId);
		Map<Integer,IDataPermission> permissionMap= permissionSelector.getPermissionMap();
		
		ModelAndView mv = getAutoView().addObject("bpmTableTemplateList", list)
										.addObject("permissionMap",permissionMap);
		return mv;
	}

	@RequestMapping("myList")
	@Action(description = "查看我的业务数据模板", operateType = "业务数据模板管理")
	public ModelAndView myList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "bpmTableTemplateItem");
		Long typeId = RequestUtil.getLong(request, "typeId", 0);
		List<BpmTableTemplate> list = getList(filter, typeId);
		ModelAndView mv = getAutoView().addObject("bpmTableTemplateList", list);
		return mv;
	}

	private List<BpmTableTemplate> getList(QueryFilter filter, Long typeId) {
		if (typeId != 0) {
			GlobalType globalType = globalTypeService.getById(typeId);
			if (globalType != null) {
				filter.getFilters().put("nodePath", globalType.getNodePath()+"%");
			}
		}
		ISysUser curUser = ContextUtil.getCurrentUser();
		// 取得当前用户所有的角色Ids
		String roleIds = sysRoleService.getRoleIdsByUserId(curUser.getUserId());
		if (StringUtils.isNotEmpty(roleIds)) {
			filter.addFilter("roleIds", roleIds);
		}
		// 取得当前组织
		String orgIds = sysOrgService.getOrgIdsByUserId(curUser.getUserId());
		if (StringUtils.isNotEmpty(orgIds)) {
			filter.addFilter("orgIds", orgIds);
		}
		// 非超级管理员需要按权限过滤
		if (!curUser.getAuthorities().contains(SystemConst.ROLE_GRANT_SUPER)) {
			Long userId = curUser.getUserId();
			filter.getFilters().put("userId", userId);
			// 根据业务数据授权获取业务数据模板列表
			return bpmTableTemplateService.getByUserIdFilter(filter);
		} else {
			return bpmTableTemplateService.getList(filter);
		}
	}

	/**
	 * 删除查看表格业务数据的模板
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除查看表格业务数据的模板", operateType = "业务数据模板管理")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			bpmTableTemplateService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success,
					"删除查看表格业务数据的模板成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败:"
					+ ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description = "编辑查看表格业务数据的模板", operateType = "业务数据模板管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		
		String returnUrl = RequestUtil.getPrePage(request);
		BpmTableTemplate bpmTableTemplate = new BpmTableTemplate();
		if (id != 0) {
			bpmTableTemplate = bpmTableTemplateService.getById(id);
		} else {
			Long formKey = RequestUtil.getLong(request, "formKey");
			BpmFormDef bpmFormDef= bpmFormDefService.getDefaultVersionByFormKey(formKey);
			bpmTableTemplate.setTemplateName(bpmFormDef.getSubject());
			bpmTableTemplate.setFormKey(formKey);
			
		}		
		Map<Integer,IDataPermission> permissionMap= permissionSelector.getPermissionMap();
	
		List<BpmFormTemplate> listTemplate = bpmFormTemplateService.getListTemplate();
		return getAutoView().addObject("bpmTableTemplate", bpmTableTemplate)
				.addObject("returnUrl", returnUrl)
				.addObject("listTemplate", listTemplate)
				.addObject("permissionMap", permissionMap);
	}

	/**
	 * 查看查看表格业务数据 字表信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("detail")
	@Action(description = "查看查看表格业务数据 字表信息", operateType = "业务数据模板管理")
	public ModelAndView detail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long formKey = RequestUtil.getLong(request, "formKey",0);
		Long flowRunId = RequestUtil.getLong(request, "flowRunId",0);
		String istanceId = "";
		Map<Long, ProRunTblPk> proRunTblPkMap = null;
		if(flowRunId>0){
			ProcessRun processRun=processRunService.getById(flowRunId);
			if(processRun!=null){
				istanceId=processRun.getActInstId();
				proRunTblPkMap =proRunTblPkDao.getByRunId(processRun.getRunId());
			}
		}
		String pkValue = RequestUtil.getString(request, "pkValue");
		String ctxPath=request.getContextPath();
		BpmFormDef bpmFormDef=bpmFormDefService.getDefaultVersionByFormKey(formKey);		
		
		String html=bpmFormHandlerService.doObtainHtml(bpmFormDef, 0L, pkValue,bpmFormDef.getTableId(), istanceId, "", "", ctxPath,proRunTblPkMap);
		return getAutoView().addObject("html", html);
	}

	/**
	 * 查看表单的业务数据。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("get")
	@Action(description = "查看表单的业务数据", operateType = "业务数据模板管理")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long id = RequestUtil.getLong(request, "id");
		int curPage=RequestUtil.getInt(request, BpmFormDialog.Page, 1);
		int pageSize=RequestUtil.getInt(request, BpmFormDialog.PageSize, 20);
		String sortField=RequestUtil.getString(request, "sortField", DEFAULT_SORT_FIELD);
		String orderSeq=RequestUtil.getString(request, "orderSeq", DEFAULT_ORDER_SEQ);
		String newSortField=RequestUtil.getString(request, "newSortField");
		String nextUrl = RequestUtil.getUrl(request);
//		System.out.println(nextUrl);
		if(newSortField.equals(sortField)){
			if(orderSeq.equals("ASC")){
				orderSeq="DESC";
			}else{
				orderSeq="ASC";
			}
		}
		if(!StringUtil.isEmpty(newSortField)){
			sortField=newSortField;
		}
		
		Map<String,Object> parameters=new HashMap<String, Object>();
		parameters.put("id",id);
		parameters.put(BpmFormDialog.Page, curPage);
		parameters.put(BpmFormDialog.PageSize, pageSize);
		parameters.put("sortField", StringUtil.isEmpty(newSortField)?sortField:newSortField);
		parameters.put("orderSeq", orderSeq);
		nextUrl = addParametersToUrl(nextUrl, parameters);

		BpmTableTemplate bpmTableTemplate = bpmTableTemplateService.getById(id);
		//获取参数。
		Map<String, Object> params = RequestUtil.getQueryMap(request);

		params.put("sortField", sortField);
		params.put("orderSeq", orderSeq);

		
		
		
		PageBean pageBean = new PageBean(curPage,pageSize);
	
		Long curUserId = ContextUtil.getCurrentUserId();
		List<Map<String, Object>> data = bpmFormHandlerService.getPage(bpmTableTemplate, curUserId, params, pageBean);
		
		// 构建分页代码
		Map pageModel = new HashMap();
		pageModel.put("tableIdCode", "");
		pageModel.put("pageBean", pageBean);
		pageModel.put("showExplain", true);
		pageModel.put("showPageSize", true);
		pageModel.put("baseHref", nextUrl);
		String pageHtml = freemarkEngine.mergeTemplateIntoString("page.ftl",pageModel);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", data);
		map.put("pageHtml", pageHtml);
		map.put("templateId", id);
		map.put("preUrl", nextUrl);
		map.put("sortField",sortField);
		map.put("orderSeq",orderSeq);
		
		String html = freemarkEngine.parseByStringTemplate(map,bpmTableTemplate.getHtmlList());

		return getAutoView().addObject("html", html);
	}
	
	
	private String addParametersToUrl(String url,Map<String, Object> params){
		StringBuffer sb=new StringBuffer();
		int idx1=url.indexOf("?");
		if(idx1>0){
			sb.append(url.substring(0, idx1));
		}
		sb.append("?");
		
		for(Entry<String, Object> entry:params.entrySet()){
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("&");
		}
		return sb.substring(0, sb.length()-1);
	}
	
	/**
	 * 根据表单key查询相关的业务表单模版。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("formKey")
	public ModelAndView formKey(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String returnUrl=RequestUtil.getPrePage(request);
		Long formKey=RequestUtil.getLong(request, "formKey");
	
		Map<Integer,IDataPermission> permissionMap= permissionSelector.getPermissionMap();
		List<BpmTableTemplate> list= bpmTableTemplateService.getByFormKey(formKey);
		ModelAndView mv= getAutoView()
				.addObject("bpmTableTemplateList", list)
			
				.addObject("returnUrl", returnUrl) 
				.addObject("formKey", formKey)
				.addObject("returnUrl", returnUrl)
				.addObject("permissionMap", permissionMap);
		return mv;
	}
	
	

}
