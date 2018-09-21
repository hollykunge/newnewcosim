package com.hotent.platform.controller.biz;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.core.annotion.Action;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.bpm.util.BpmUtil;

import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.hotent.platform.dao.form.BpmFormTableDao;
import com.hotent.platform.model.biz.BizDef;
import com.hotent.platform.service.biz.BizCmd;
import com.hotent.platform.service.biz.BizDefService;
import com.hotent.platform.service.biz.BizInstanceService;
import com.hotent.platform.service.bpm.BpmAgentService;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmFormRunService;
import com.hotent.platform.service.bpm.BpmNodeButtonService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmNodeSignService;
import com.hotent.platform.service.bpm.BpmNodeUserService;
import com.hotent.platform.service.bpm.BpmRunLogService;
import com.hotent.platform.service.bpm.BpmTaskAssigneeService;
import com.hotent.platform.service.bpm.ExecutionStackService;
import com.hotent.platform.service.bpm.TaskApprovalItemsService;
import com.hotent.platform.service.bpm.TaskSignDataService;
import com.hotent.platform.service.bpm.TaskUserService;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.service.form.BpmFormHandlerService;
import com.hotent.platform.service.system.SysUserAgentService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.model.biz.BizDefSegment;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeButton;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.FormModel;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:业务定义，如邀标采购这样的大业务。 控制器类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Controller
@RequestMapping("/platform/biz/bizDef/")
public class BizDefController extends BaseController
{
	@Resource
	private BizDefService bizDefService;
	@Resource
	private BizInstanceService bizInstanceService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource 
	private BpmFormRunService bpmFormRunService;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private TaskSignDataService taskSignDataService;
	@Resource
	private BpmNodeSignService bpmNodeSignService;
	@Resource
	private ExecutionStackService executionStackService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUserAgentService sysUserAgentService;
	@Resource
	private BpmAgentService bpmAgentService;
	@Resource
	private BpmFormHandlerService bpmFormHandlerService;

	@Resource
	private BpmFormDefService bpmFormDefService;

	@Resource
	private BpmNodeUserService bpmNodeUserService;

	@Resource
	private TaskUserService taskUserService;

	@Resource
	private TaskApprovalItemsService taskAppItemService;
	
	@Resource
	private BpmFormTableDao bpmFormTableDao;

	@Resource
	private BpmNodeButtonService bpmNodeButtonService;

	@Resource
	private BpmRunLogService bpmRunLogService;

	@Resource
	private BpmTaskAssigneeService bpmTaskAssigneeService;
	/**
	 * 添加或更新业务定义，如邀标采购这样的大业务。。
	 * @param request
	 * @param response
	 * @param bizDef 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新业务定义，如邀标采购这样的大业务。")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		BizDef bizDef=getFormObject(request);
		Boolean newVersion = RequestUtil.getBoolean(request, "newVersion",true);
		List<BizDefSegment> bizDefSegmentList = bizDef.getBizDefSegmentList();
		for(int i=0;i<bizDefSegmentList.size();i++){
			bizDefSegmentList.get(i).setSortOrder((long)i+1);
		}
		try{
			if(bizDef.getBizDefId()==null||bizDef.getBizDefId()==0){
				bizDef.setBizDefId(UniqueIdUtil.genId());
				bizDefService.addAll(bizDef);			
				resultMsg=getText("record.added","业务定义");
			}else{
				if(newVersion){
					BizDef old = bizDefService.getById(bizDef.getBizDefId());
					Short maxVersionNo = bizDefService.getMaxVersionNoByNo(old.getBizDefNo());
					old.setIsMain(BizDef.MAIN_VERSION_N);
					bizDefService.updateAll(old);
					bizDef.setBizDefNo(old.getBizDefNo());
					bizDef.setVersionNo((short)(maxVersionNo+1));
					bizDef.setBizDefId(UniqueIdUtil.genId());
					bizDefService.addAll(bizDef);			
				}else{
					bizDefService.updateAll(bizDef);
				}
				resultMsg=getText("record.updated","业务定义");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 BizDef 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected BizDef getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("bizDefSegmentList", BizDefSegment.class);
		BizDef bizDef = (BizDef)JSONObject.toBean(obj, BizDef.class,map);
		
		return bizDef;
    }
	
	/**
	 * 取得业务定义，如邀标采购这样的大业务。分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看业务定义，如邀标采购这样的大业务。分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<BizDef> list=bizDefService.getAll(new QueryFilter(request,"bizDefItem"));
		ModelAndView mv=this.getAutoView().addObject("bizDefList",list)
										  .addObject("DefDef_STATUS_ENABLE",BizDef.STATUS_ENABLE)
										  .addObject("DefDef_STATUS_DISABLE",BizDef.STATUS_DISABLE);
		
		return mv;
	}
	
	/**
	 * 删除业务定义，如邀标采购这样的大业务。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除业务定义，如邀标采购这样的大业务。")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "bizDefId");
			bizDefService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除业务定义，如邀标采购这样的大业务。及其从表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑业务定义，如邀标采购这样的大业务。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑业务定义，如邀标采购这样的大业务。")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long bizDefId=RequestUtil.getLong(request,"bizDefId");
		String returnUrl=RequestUtil.getPrePage(request);
		BizDef bizDef=bizDefService.getById(bizDefId);
		List<BizDefSegment> bizDefSegmentList=bizDefService.getBizDefSegmentList(bizDefId);
		
		return getAutoView().addObject("bizDef",bizDef)
							.addObject("bizDefSegmentList",bizDefSegmentList)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得业务定义，如邀标采购这样的大业务。明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看业务定义，如邀标采购这样的大业务。明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long bizDefId=RequestUtil.getLong(request,"bizDefId");
		BizDef bizDef = bizDefService.getById(bizDefId);	
		List<BizDefSegment> bizDefSegmentList=bizDefService.getBizDefSegmentList(bizDefId);
		bizDef.setBizDefSegmentList(bizDefSegmentList);
		return getAutoView().addObject("bizDef",bizDef)
							.addObject("bizDefSegmentList",bizDefSegmentList);
	}
	
	/**
	 * 创建业务流程实例。
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("start")
	@Action(description="创建新的业务流程实例 ")
	public void start(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintWriter out = response.getWriter();

		try{
			Long bizDefId=RequestUtil.getLong(request,"bizDefId");

			ProcessCmd processCmd = BpmUtil.getProcessCmd(request);
			processCmd.setCurrentUserId(ContextUtil.getCurrentUserId().toString());

			BizCmd bizCmd = new BizCmd();
			bizCmd.setStartUser(ContextUtil.getCurrentUser());
			bizCmd.setCurrentUser(ContextUtil.getCurrentUser());

			bizCmd.setProcessCmd(processCmd);
			bizInstanceService.startBizInstance(bizDefId, bizCmd);
			ResultMessage resultMessage = new ResultMessage(
					ResultMessage.Success, "启动流程成功!");
			out.print(resultMessage);
		}catch(Exception ex){
			ex.printStackTrace();
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(
						ResultMessage.Fail, "创建业务实例失败:\r\n" + str);
				out.print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(
						ResultMessage.Fail, message);
				out.print(resultMessage);
			}
		}
	}
	
	
	/**
	 * 跳转到启动流程页面。<br/>
	 * 
	 * <pre>
	 * 传入参数流程定义id：defId。 
	 * 实现方法： 
	 * 1.根据流程对应ID查询流程定义。 
	 * 2.获取流程定义的XML。
	 * 3.获取流程定义的第一个任务节点。
	 * 4.获取任务节点的流程表单定义。 
	 * 5.显示启动流程表单页面。
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("toStart")
	@Action(description = "跳至启动流程页面")
	public ModelAndView toStart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long bizDefId=RequestUtil.getLong(request,"bizDefId");
		BizDef bizDef = bizDefService.getById(bizDefId);
		List<BizDefSegment> bizDefSegmentList = bizDefService.getBizDefSegmentList(bizDefId);
		bizDef.setBizDefSegmentList(bizDefSegmentList);
		BizDefSegment firstBizDefSegment = bizDef.getFirstDefSegment();

		String businessKey = RequestUtil.getString(request, "businessKey", "");

		Map paraMap = RequestUtil.getParameterValueMap(request, false, false);
		paraMap.remove("businessKey");
		paraMap.remove("defId");

		BpmDefinition bpmDefinition = bpmDefinitionService.getMainByDefKey(firstBizDefSegment.getActDefKey());
		if (bpmDefinition.getDisableStatus()==BpmDefinition.DISABLEStATUS_DA)
			throw new Exception("该流程已经被禁用");

		
		String actDefId = bpmDefinition.getActDefId();
		Long defId = bpmDefinition.getDefId();

		short toFirstNode = bpmDefinition.getToFirstNode();
		// 获取表单节点
		BpmNodeSet bpmNodeSet = bpmFormRunService.getStartBpmNodeSet(actDefId,
				toFirstNode);

		String ctxPath = request.getContextPath();
		FormModel formModel = getForm(bpmNodeSet, businessKey, actDefId,
				ctxPath);

		// 是外部表单
		boolean isFormEmpty = formModel.isFormEmpty();
		Boolean isExtForm = formModel.getFormType() > 0;
		String form = "";
		if (isExtForm) {
			form = formModel.getFormUrl();
		} else if (formModel.getFormType() == 0) {
			form = formModel.getFormHtml();
		}

		// 获取按钮
//		Map<String, List<BpmNodeButton>> mapButton = bpmNodeButtonService
//				.getMapByStartForm(defId);

		ModelAndView mv = getAutoView()
				.addObject("bizDef",bizDef)
				.addObject("bpmDefinition", bpmDefinition)
				.addObject("form", form).addObject("defId", defId)
				.addObject("isExtForm", isExtForm)
				.addObject("isFormEmpty", isFormEmpty)
				.addObject("businessKey", businessKey)
				.addObject("paraMap", paraMap);

		return mv;
	}
	
	
	/**
	 * 取得起始表单。
	 * 
	 * @param bpmNodeSet
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private FormModel getForm(BpmNodeSet bpmNodeSet, String businessKey,
			String actDefId, String ctxPath) throws Exception {
		FormModel formModel = new FormModel();
		if (bpmNodeSet == null)
			return formModel;
		if (bpmNodeSet.getFormType() == BpmConst.OnLineForm) {
			Long formKey = bpmNodeSet.getFormKey();
			if (formKey != null && formKey > 0) {
				BpmFormDef bpmFormDef = bpmFormDefService
						.getDefaultPublishedByFormKey(formKey);
				if (bpmFormDef != null) {
					BpmFormTable bpmFormTable = bpmFormTableDao.getById(bpmFormDef.getTableId());
					bpmFormDef.setTableName(bpmFormTable.getTableName());

					String formHtml = bpmFormHandlerService.doObtainHtml(bpmFormDef, ContextUtil.getCurrentUserId(),businessKey, bpmFormDef.getTableId(),"", actDefId, "", ctxPath,null);
					formModel.setFormHtml(formHtml);
				}
			}
		} else {
			String formUrl = bpmNodeSet.getFormUrl();
			// 替换主键。
			formUrl = formUrl.replaceFirst(BpmConst.FORM_PK_REGEX, businessKey);
			if (!formUrl.startsWith("http")) {
				formUrl = ctxPath + formUrl;
			}
			formModel.setFormType(BpmConst.UrlForm);
			formModel.setFormUrl(formUrl);
		}
		return formModel;
	}
	
	
	/**
	 * 根据业务定义id获取流程定义 并判断是否有开始节点表单的配置。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCanDirectStart")
	@ResponseBody
	public boolean getCanDirectStart(HttpServletRequest request)
			throws Exception {
		Long bizDefId=RequestUtil.getLong(request,"bizDefId");
		BizDef bizDef = bizDefService.getById(bizDefId);
		List<BizDefSegment> bizDefSegmentList = bizDefService.getBizDefSegmentList(bizDefId);
		bizDef.setBizDefSegmentList(bizDefSegmentList);
		BizDefSegment firstBizDefSegment = bizDef.getFirstDefSegment();
		BpmDefinition bpmDefinition = bpmDefinitionService.getMainByDefKey(firstBizDefSegment.getActDefKey());
		Long defId = bpmDefinition.getDefId();
		boolean rtn = bpmFormRunService.getCanDirectStart(defId);
		return rtn;
	}
}
