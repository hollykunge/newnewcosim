package com.casic.cloud.controller.system.enterprise.org;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.annotion.Action;
import com.hotent.core.cache.ICache;
import com.hotent.core.model.OnlineUser;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysUserOrgDao;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.model.system.Seal;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysOrgParam;
import com.hotent.platform.model.system.SysOrgType;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysOrgParamService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysOrgTypeService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 对象功能:组织架构SYS_ORG 控制器类 
 * 开发公司:
 * 开发人员:pkq 
 * 创建时间:2011-11-09 11:20:13
 */
@Controller
@RequestMapping("/cloud/system/enterprise/org")
public class SystemOrgController extends BaseController {
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private DemensionService demensionService;
	@Resource
	private SysUserOrgDao sysUserOrgDao;
	@Resource
	Properties configproperties;
	@Resource
	private SysUserOrgService sysUserOrgService;
	@Resource
	private ICache iCache;
	@Resource
	private SysOrgTypeService sysOrgTypeService;
	@Resource
	private IAuthenticate iAuthenticate;
	@Resource
	private SysOrgParamService sysOrgParamService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysOrgInfoService sysOrgInfoService;
	
	/**
	 * 取得维度下拉
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDem")
	@ResponseBody
	public List<Demension> getDem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return demensionService.getAll();
	}

	/**
	 * 组织对话框的展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selector")
	public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "sysOrgItem", false);
		Long orgId = RequestUtil.getLong(request, "orgId");
		Long demId = RequestUtil.getLong(request, "demId");
		if (demId != 0) {
			filter.addFilter("demId", demId);
		}
		ISysOrg org = sysOrgService.getById(orgId);
		if (org != null) {
			filter.addFilter("path",StringUtil.isNotEmpty( org.getPath())?("%"+ org.getPath()+"%"):org.getPath());
		}
		List<ISysOrg> sysOrgList = sysOrgService.getAll(filter);

		String orgName = RequestUtil.getString(request, "orgName");
		if (StringUtil.isEmpty(orgName)) {
			sysOrgList = sysOrgService.coverTreeList(org != null ? org.getOrgId() : 1, sysOrgList);
		}
		ModelAndView mv = this.getAutoView().addObject("sysOrgList", sysOrgList);
		return mv;
	}

	@RequestMapping("dialog")
	public ModelAndView dialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Demension> demensionList = demensionService.getAll();
		ModelAndView mv = this.getAutoView().addObject("demensionList", demensionList);

		return mv;
	}

	/**
	 * 取得组织框架信息
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Demension> demensionList = demensionService.getAll();
		ModelAndView mv = this.getAutoView().addObject("demensionList", demensionList);

		return mv;
	}
	
	/**
	 * 企业对话框的展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("compSelector")
	public ModelAndView compSelector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysOrgInfo> list=sysOrgInfoService.getAll(new QueryFilter(request,"compItem"));
		ModelAndView mv=this.getAutoView().addObject("compList",list);
		return mv;
	}

	@RequestMapping("compDialog")
	public ModelAndView compDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getAutoView();
		return mv;
	}
	
	/**
	 * 查询所有组织架构
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("search")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "sysOrgItem");
		List<ISysOrg> sysOrgList = sysOrgService.getAll(filter);
		ModelAndView mv = this.getAutoView().addObject("sysOrgList", sysOrgList);

		return mv;
	}

	/**
	 * 取得组织架构分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("view")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long orgId = RequestUtil.getLong(request, "orgId");
		ISysOrg sysOrg = sysOrgService.getById(orgId);
		String path = "";
		String paramPath = "";
		if (sysOrg != null) {
			path = sysOrg.getPath();
			paramPath = path.replace(".", ",");
		}
		ModelAndView mv = this.getAutoView().addObject("orgId", orgId).addObject("path", path).addObject("paramPath", paramPath);
		return mv;
	}

	/**
	 * 取得下级组织架构分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listById")
	public ModelAndView listById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getAutoView();
		mv.addObject("action", "global");
		return getListByOrgId(request,mv);
	}
	
	@RequestMapping("listGradeById")
	public ModelAndView listGradeById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("action", "grade");
		mv.setViewName("/platform/system/sysOrgListById.jsp");
		return getListByOrgId(request,mv);
	}
	
	private ModelAndView getListByOrgId(HttpServletRequest request,ModelAndView mv) throws Exception{
		Long orgId = RequestUtil.getLong(request, "orgId");
		ISysOrg sysOrg = sysOrgService.getById(orgId);
		
		if (sysOrg == null)
			return mv.addObject("sysOrg", sysOrg);
		else {
			String path = sysOrg.getPath();
			QueryFilter filter = new QueryFilter(request, "sysOrgItem");
			filter.getFilters().put("path",StringUtil.isNotEmpty( path)?(path+"%"):"");
			List<ISysOrg> list = sysOrgService.getOrgByOrgId(filter);
			return mv.addObject("sysOrgList", list).addObject("orgId", orgId).addObject("sysOrg", 1);
		}
	}

	@RequestMapping("edit")
	@Action(description = "编辑组织架构")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv=getAutoView();
		mv.addObject("scope", "global");
		return getEditMv(request,mv);
	}
	
	@RequestMapping("editGrade")
	@Action(description = "编辑组织架构")
	public ModelAndView editGrade(HttpServletRequest request) throws Exception {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/platform/system/sysOrgEdit.jsp");
		mv.addObject("scope", "grade");
		return getEditMv(request,mv);
	}
	
	/**
	 * 获取编辑界面的modelandview
	 * @param request
	 * @param mv
	 * @return
	 */
	private ModelAndView getEditMv(HttpServletRequest request,ModelAndView mv){
		Long demId = RequestUtil.getLong(request, "demId", 0);
		Long orgId = RequestUtil.getLong(request, "orgId");
		String action = RequestUtil.getString(request, "action");
		ISysOrg sysOrg = null;
		Long parentOrgId=0L;
		// 当前维度
		Demension demension = demensionService.getById(demId);
		List<SysOrgType> sysOrgTypelist=sysOrgTypeService.getByDemId(demId);
		List<SysOrgType> returnSysOrgTypelist=new LinkedList<SysOrgType>();
		SysOrgType subSysOrgType=null;
		if ("add".equals(action)) {// 新增
			sysOrg = iAuthenticate.getNewSysOrg();
			ISysOrg supSysOrg = sysOrgService.getById(orgId);
			
			if (supSysOrg == null) { // 从维度上新建组织
				sysOrg.setOrgSupId(demId);
				returnSysOrgTypelist=sysOrgTypelist;
			} else {
				supSysOrg = sysOrgService.getById(orgId);
				sysOrg.setOrgSupId(supSysOrg.getOrgId());
				sysOrg.setOrgSupName(supSysOrg.getOrgName());
				Long supSysOrgId=supSysOrg.getOrgType();
				if(supSysOrgId!=null){
					 subSysOrgType=sysOrgTypeService.getById(supSysOrg.getOrgType());
				}		
			}
		} else {// 编辑
			sysOrg = sysOrgService.getById(orgId);
			ISysOrg charge = sysUserOrgService.getChageNameByOrgId(orgId);
			sysOrg.setOwnUser(charge.getOwnUser());
			sysOrg.setOwnUserName(charge.getOwnUserName());
			 parentOrgId=sysOrg.getOrgSupId()==null?0L:sysOrg.getOrgSupId();
			
			if(sysOrg.getOrgType()!=null)
				subSysOrgType=sysOrgTypeService.getById(sysOrg.getOrgType());
		
			 if(subSysOrgType==null){
				 
				 ISysOrg parentOrg= sysOrgService.getParentWithType(sysOrg);  //取得有分类的父极节点
				 if(parentOrg!=null)
					 subSysOrgType=sysOrgTypeService.getById(parentOrg.getOrgType());
				 else
					 returnSysOrgTypelist=sysOrgTypelist;
			 }
		}
		if(subSysOrgType!=null && !parentOrgId.equals(1L)){
			for(int i=0;i<sysOrgTypelist.size();i++){
				if(subSysOrgType.getLevels()<=sysOrgTypelist.get(i).getLevels())
					returnSysOrgTypelist.add(sysOrgTypelist.get(i));
			}
		}
		else if(parentOrgId.equals(1L)){
			returnSysOrgTypelist=sysOrgTypelist;
		}
			
		return mv.addObject("sysOrg", sysOrg)
				.addObject("demension", demension)
				.addObject("action", action)
				.addObject("sysOrgTypelist",returnSysOrgTypelist);
	}


	/**
	 * 删除组织及其所有子组织
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("orgdel")
	public void orgdel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long orgId = RequestUtil.getLong(request, "orgId");
			sysOrgService.delById(orgId);
			//删除单位的缓存数据。
			iCache.delByKey(SecurityUtil.OrgRole + orgId);
			message = new ResultMessage(ResultMessage.Success, "删除组织及其所有子组织成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除组织及其所有子组织失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 取得组织架构明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv=this.getAutoView();
		mv.addObject("action", "global");
		return getByOrgId(request,mv);

	}
	
	private ModelAndView getByOrgId(HttpServletRequest request,ModelAndView mv) throws Exception{
		long orgId = RequestUtil.getLong(request, "orgId");
		List<SysOrgType> sysOrgTypelist=null;
		String ownerName = "";
		ISysOrg sysOrg = sysOrgService.getById(orgId);
		if (sysOrg != null) {
			ISysOrg charge = sysUserOrgService.getChageNameByOrgId(orgId);
			Long demId = sysOrg.getDemId();
			sysOrgTypelist=sysOrgTypeService.getByDemId(demId);
			if (sysOrg.getDemId() != 0) {
				sysOrg.setDemName(demensionService.getById(demId).getDemName());
				ownerName = charge.getOwnUserName();
			
			}
		}

		return mv.addObject("sysOrg", sysOrg)
				.addObject("userNameCharge", ownerName)
				.addObject("orgId", orgId)
				.addObject("sysOrgTypelist",sysOrgTypelist);
	}
	
	
	@RequestMapping("getGrade")
	public ModelAndView getGrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv=new ModelAndView();
		mv.addObject("action", "grade");
		mv.setViewName("/platform/system/sysOrgGet.jsp");
		return getByOrgId(request,mv);
	}

	/**
	 * 取得组织架构明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("type")
	public ModelAndView type(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String flag = RequestUtil.getString(request, "flag");
		long orgId = RequestUtil.getLong(request, "orgId");
		String userNameStr = "";
		String userNameCharge = "";

		List<SysUserOrg> userlist = sysUserOrgDao.getByOrgId(orgId);
		for (SysUserOrg userOrg : userlist) {
			if (userNameStr.isEmpty()) {
				userNameStr = userOrg.getUserName();
			} else {
				userNameStr = userNameStr + "," + userOrg.getUserName();
			}
			String isCharge = "";
			if (BeanUtils.isNotEmpty(userOrg.getIsCharge())) {
				isCharge = userOrg.getIsCharge().toString();
			}
			// 为主要负责人
			if (SysUserOrg.CHARRGE_YES.equals(isCharge)) {
				if (userNameCharge.isEmpty()) {
					userNameCharge = userOrg.getUserName().toString();
				} else {
					userNameCharge = userNameCharge + "," + userOrg.getUserName();
				}
			}
		}
		ISysOrg po = sysOrgService.getById(orgId);
		return this.getAutoView().addObject("sysOrg", po).addObject("userNameStr", userNameStr).addObject("userNameCharge", userNameCharge)
				.addObject("orgId", orgId).addObject("flag", flag);

	}

	/**
	 * 将维度或全部、未分配组织等添加为组织树的一个节点
	 * 
	 * @param demId
	 * @return
	 * @throws Exception
	 */
	private ISysOrg getRootSysOrg(Long demId, String orgName) throws Exception {
		
		ISysOrg org = iAuthenticate.getNewSysOrg();
		org.setOrgId(demId);
		org.setOrgSupId(0L);
		org.setPath(demId.toString());
		org.setDemId(demId);
		org.setIsRoot((short) 1);
		org.setOrgName(orgName);
		return org;
	}

	/**
	 * 获取组织架构树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getTreeData")
	@ResponseBody
	public List<ISysOrg> getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取维度ID
		Long demId = RequestUtil.getLong(request, "demId", 0);
		// 当前维度
		List<Demension> demens = null;
		long orgId=RequestUtil.getLong(request, "orgId",0);
		List<ISysOrg> orgList = new ArrayList<ISysOrg>();
		if(orgId==0){		
			if (demId != 0) {
				demens = new ArrayList<Demension>();
				demens.add(demensionService.getById(demId));
				orgList = sysOrgService.getOrgByOrgSupIdAndLevel(demId);
			} else {
				demens = demensionService.getAll();
				if(demens.size()>0){
					for(int i=0;i<demens.size();i++){
						orgList.addAll(sysOrgService.getOrgByOrgSupIdAndLevel(demens.get(i).getDemId()));
					}
				}
				
			}
			for (Demension demension : demens) {
				orgList.add(getRootSysOrg(demension.getDemId(), demension.getDemName()));
			}
		}
		for(ISysOrg sysOrg:orgList){
			ISysOrg charge = sysUserOrgService.getChageNameByOrgId(sysOrg.getOrgId());
			sysOrg.setOwnUser(charge.getOwnUser());
			sysOrg.setOwnUserName(charge.getOwnUserName());
			if(sysOrg.getOrgType()==null) continue;
			SysOrgType  tempSysOrgType= sysOrgTypeService.getById(sysOrg.getOrgType());
			if(tempSysOrgType==null ) continue;
			if(tempSysOrgType.getIcon()==null) continue;
			sysOrg.setIconPath(tempSysOrgType.getIcon());
		}
		return orgList;
	}

	/**
	 * 获取组织架构在线用户树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getTreeOnlineData")
	@ResponseBody
	public List<ISysOrg> getTreeOnlineData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<Long, OnlineUser> onlineUsers = AppUtil.getOnlineUsers();
		List<OnlineUser> onlineList = new ArrayList<OnlineUser>();
		for (Long userId : onlineUsers.keySet()) {
			OnlineUser onlineUser = onlineUsers.get(userId);
			ISysOrg sysOrg = sysOrgService.getPrimaryOrgByUserId(onlineUser.getUserId());
			if (sysOrg != null) {
				onlineUser.setOrgId(sysOrg.getOrgId());
				onlineUser.setOrgName(sysOrg.getOrgName());
			}
			onlineList.add(onlineUser);
		}
		String depTreeRootId = configproperties.getProperty("depTreeRootId");
		Long demId = RequestUtil.getLong(request, "demId", 0);
		Demension demension = demensionService.getById(demId);
		Map<Long, ISysOrg> orgMap = sysOrgService.getOrgMapByDemId(demId);
		orgMap.put(0L, getRootSysOrg(-1L, "未分配组织"));// 添加一个未分配组织的项
		for (OnlineUser onlineUser : onlineList) {
			Long onlineUserId = onlineUser.getOrgId();
			ISysOrg sysOrg = orgMap.get(onlineUserId);
			if (sysOrg != null) {
				int onlineNum = sysOrg.getOnlineNum();
				orgMap.get(onlineUserId).setOnlineNum(onlineNum + 1);
			} else {
				orgMap.get(0L).setOnlineNum(orgMap.get(0L).getOnlineNum() + 1);
			}
		}
		List<ISysOrg> orgList = new ArrayList<ISysOrg>();

		for (ISysOrg sysOrg : orgMap.values()) {
			String newName = sysOrg.getOrgName() + "(" + sysOrg.getOnlineNum() + ")";
			sysOrg.setOrgName(newName);
			orgList.add(sysOrg);
		}
		// 指定根节点
		if (!StringUtils.isEmpty(depTreeRootId)) {
			ISysOrg org = getRootSysOrg(demId, "全部");
			if (demension != null)
				org.setOrgName(demension.getDemName());
			orgList.add(org);
		}
		return orgList;
	}

	

	/**
	 * 移动分类数据。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("move")
	@Action(description = "转移分类")
	public void move(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage resultMessage = null;
		PrintWriter out = response.getWriter();
		long targetId = RequestUtil.getLong(request, "targetId", 0);
		long dragId = RequestUtil.getLong(request, "dragId", 0);
		String moveType = RequestUtil.getString(request, "moveType");
		try {
			sysOrgService.move(targetId, dragId, moveType);
			//删除组织的缓存。
			iCache.delByKey(SecurityUtil.OrgRole + dragId);
			
			resultMessage = new ResultMessage(ResultMessage.Success, "转移分类完成");
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"转移分类失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
		out.print(resultMessage);
	}
	
	/**
	 * 取得组织参数列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("paramList")
	@Action(description="取得组织参数表")
	public ModelAndView paramList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv=this.getAutoView();
		mv.addObject("scope", "global");
		return getParamMv(request,mv);	
	}
	
	/**
	 * 取得组织参数表。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("paramGradeList")
	@Action(description="取得组织参数表")
	public ModelAndView paramGradeList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/platform/system/sysOrgParamList.jsp");
		mv.addObject("scope", "grade");
		return getParamMv(request,mv);	
	}
	
	/**
	 * 组织树排序列表。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sortList")
	@Action(description="组织树排序列表")
	public ModelAndView sortList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Long orgId=RequestUtil.getLong(request, "orgId",-1);
		List<ISysOrg> list = new ArrayList<ISysOrg>();
		List<ISysOrg> ISysOrgList = new ArrayList<ISysOrg>();
		ISysOrg sysOrg = sysOrgService.getById(orgId);
		if(sysOrg!=null){
			list=sysOrgService.getByOrgPath(sysOrg.getPath());
			if(list.size()>0){
				for(ISysOrg iSysOrg : list){
					int iSysOrgLength = iSysOrg.getPath().split("\\.").length;
					int sysOrgLength = sysOrg.getPath().split("\\.").length+1;
					if(iSysOrgLength==sysOrgLength){
						ISysOrgList.add(iSysOrg);
					}
				}
			}
		} else {
			// 获取维度ID
			Long demId = RequestUtil.getLong(request, "demId", 0);
			list=sysOrgService.getOrgsByDemIdOrAll(demId);
			if(list.size()>0){
				for(ISysOrg iSysOrg : list){
					int iSysOrgLength = iSysOrg.getPath().split("\\.").length;
					if(iSysOrgLength==2){
						ISysOrgList.add(iSysOrg);
					}
				}
			}
		}
		return getAutoView().addObject("ISysOrgList",ISysOrgList);	
	}
	
	/**
	 * 组织树排序。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sort")
	@Action(description="组织树排序")
	public void sort(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage resultObj=null;
		PrintWriter out = response.getWriter();
		Long[] lAryId =RequestUtil.getLongAryByStr(request, "orgIds");
		if(BeanUtils.isNotEmpty(lAryId)){
			for(int i=0;i<lAryId.length;i++){
				Long orgId=lAryId[i];
				long sn=i+1;
				sysOrgService.updSn(orgId,sn);
			}
		}
		resultObj=new ResultMessage(ResultMessage.Success,"排序分类完成");
		out.print(resultObj);	
	}
	/**
	 * 获取参数列表mv。
	 * @param request
	 * @param mv
	 * @return
	 */
	private ModelAndView getParamMv(HttpServletRequest request,ModelAndView mv){
		Long orgId=RequestUtil.getLong(request, "orgId");
		if(orgId==0){
			mv.addObject("sysOrg",null);
		}else{
			ISysOrg sysOrg= sysOrgService.getById(orgId);
			List<SysOrgParam> list=sysOrgParamService.getListByOrgId(orgId);
			mv.addObject("userParamList",list)
			.addObject("orgId",orgId)
			.addObject("sysOrg",sysOrg);
		}
		return mv;
	}

}
