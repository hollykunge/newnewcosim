package com.hotent.platform.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.annotion.Action;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.ldap.model.LdapUser;
import com.hotent.core.ldap.until.UserHelper;
import com.hotent.core.model.OnlineUser;
import com.hotent.core.util.AppUtil;
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
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.UserPositionDao;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.model.system.SysUserParam;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.model.system.UserRole;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.ldap.LdapUserService;
import com.hotent.platform.service.ldap.SysOrgSyncService;
import com.hotent.platform.service.ldap.SysUserSyncService;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserParamService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;

/**
 * 对象功能:用户表 控制器类 开发公司: 开发人员:csx 创建时间:2011-11-28 10:17:09
 */
@Controller
@RequestMapping("/platform/system/sysUser/")
public class SysUserController extends BaseController {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private DemensionService demensionService;
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private SysUserParamService sysUserParamService;
	@Resource
	private SysUserOrgService sysUserOrgService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private UserPositionDao userPositionDao;
	@Resource
	private LdapUserService ldapUserService;
	@Resource
	private SysOrgSyncService sysOrgSyncService;
	@Resource
	private SysUserSyncService sysUserSyncService;
	@Resource
	private SysOrgDao sysOrgDao;
	@Resource
	private IAuthenticate iAuthenticate;	
	@Resource
	Properties configproperties;
	private final String defaultUserImage = "commons/image/default_image_male.jpg";

	/**
	 * 取得用户表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看用户表分页列表",operateType="用户操作")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId = RequestUtil.getLong(request, "userId");
		
		QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
		List<ISysUser> list = sysUserService.getUserByQuery(queryFilter);

		ModelAndView mv = this.getAutoView().addObject("sysUserList", list).addObject("userId", userId);

		return mv;
	}

	/**
	 * 删除用户表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除用户表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "userId");
			sysUserService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除用户成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除用户失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description = "编辑用户表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv= getAutoView();
		mv.addObject("action", "global");
		List<Demension>demensionList=demensionService.getAll();
		String returnUrl=RequestUtil.getPrePage(request);
		return getEditMv(request,mv).addObject("returnUrl", returnUrl).addObject("demensionList", demensionList);
	}
	
	@RequestMapping("editGrade")
	@Action(description = "编辑用户表")
	public ModelAndView editGrade(HttpServletRequest request) throws Exception {
		ModelAndView mv= new ModelAndView();
		mv.setViewName("/platform/system/sysUserEdit.jsp");
		mv.addObject("action", "grade");
		return getEditMv(request,mv);
	}
	
	public ModelAndView getEditMv(HttpServletRequest request,ModelAndView mv){
		Long orgId=RequestUtil.getLong(request, "orgId");
		String returnUrl = RequestUtil.getPrePage(request);
		Long userId = RequestUtil.getLong(request, "userId");
		int bySelf = RequestUtil.getInt(request, "bySelf");
		
		ISysUser sysUser = null;
		if (userId != 0) {
			sysUser = sysUserService.getById(userId);
			List<UserRole> roleList = userRoleService.getByUserId(userId);
			List<UserPosition> posList = userPositionDao.getByUserId(userId);
			List<SysUserOrg> orgList = sysUserOrgService.getOrgByUserId(userId);
			mv.addObject("roleList", roleList).addObject("posList", posList).addObject("orgList", orgList);
		} else {
			sysUser = iAuthenticate.getNewSysUser();
			if(orgId>0){
				List<SysUserOrg> orgList=new ArrayList<SysUserOrg>();
				SysUserOrg userOrg=new SysUserOrg();
				ISysOrg sysOrg=  sysOrgDao.getById(orgId);
				userOrg.setOrgId(orgId);
				userOrg.setOrgName(sysOrg.getOrgName());
				userOrg.setIsPrimary(SysUserOrg.PRIMARY_YES);
				userOrg.setIsCharge(SysUserOrg.CHARRGE_NO);
				orgList.add(userOrg);
				mv.addObject("orgList", orgList);
			}
		}
		String pictureLoad = defaultUserImage;
		if (sysUser != null) {
			if (StringUtil.isNotEmpty(sysUser.getPicture())) {
				pictureLoad = sysUser.getPicture();
			}
		}
		return mv.addObject("sysUser", sysUser)
				.addObject("userId", userId)
				.addObject("returnUrl", returnUrl)
				.addObject("pictureLoad", pictureLoad)
				.addObject("bySelf",bySelf);
	}

	@RequestMapping("modifyPwdView")
	@Action(description = "修改密码")
	public ModelAndView modifyPwdView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long userId = RequestUtil.getLong(request, "userId");
		ISysUser sysUser = sysUserService.getById(userId);
		return getAutoView().addObject("sysUser", sysUser).addObject("userId",userId);
	}
	
	@RequestMapping("modifyPwd")
	@Action(description = "修改密码")
	public void modifyPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String primitivePassword =  RequestUtil.getString(request, "primitivePassword");
		String enPassword=EncryptUtil.encryptSha256(primitivePassword);
		String newPassword = RequestUtil.getString(request, "newPassword");
		Long userId = RequestUtil.getLong(request, "userId");
		ISysUser sysUser = sysUserService.getById(userId);
		String password=sysUser.getPassword();
		if(StringUtil.isEmpty( newPassword)||StringUtil.isEmpty( primitivePassword)){
			writeResultMessage(response.getWriter(), "输入的密码不能为空", ResultMessage.Fail);	
		}
		else if(!enPassword.equals(password)){
			writeResultMessage(response.getWriter(), "你输入的原始密码不正确", ResultMessage.Fail);		
		}

		else if(primitivePassword.equals(newPassword)){
			writeResultMessage(response.getWriter(), "你修改的密码和原始密码相同", ResultMessage.Fail);
		}
		else{
			try {
				sysUserService.updPwd(userId, newPassword);
				writeResultMessage(response.getWriter(),"修改密码成功", ResultMessage.Success);
			} catch (Exception ex) {
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"修改密码失败:" + str);
					response.getWriter().print(resultMessage);
				} else {
					String message = ExceptionUtil.getExceptionMessage(ex);
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
					response.getWriter().print(resultMessage);
				}
			}
		}
	}

	
	@RequestMapping("resetPwdView")
	@Action(description = "重置密码")
	public ModelAndView resetPwdView(HttpServletRequest request) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		Long userId = RequestUtil.getLong(request, "userId");
		ISysUser sysUser = sysUserService.getById(userId);
		return getAutoView().addObject("sysUser", sysUser).addObject("userId", userId).addObject("returnUrl", returnUrl);
	}

	@RequestMapping("resetPwd")
	@Action(description = "重置密码")
	public void resetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String password = RequestUtil.getString(request, "password");
		Long userId = RequestUtil.getLong(request, "userId");
		try {
			sysUserService.updPwd(userId, password);
			writeResultMessage(response.getWriter(), "重置密码成功!", ResultMessage.Success);
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"重置密码失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	@RequestMapping("editStatusView")
	@Action(description = "设置用户状态")
	public ModelAndView editStatusView(HttpServletRequest request) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		Long userId = RequestUtil.getLong(request, "userId");
		ISysUser sysUser = sysUserService.getById(userId);
		return getAutoView().addObject("sysUser", sysUser).addObject("userId", userId).addObject("returnUrl", returnUrl);
	}

	@RequestMapping("editStatus")
	@Action(description = "设置用户状态")
	public void editStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId = RequestUtil.getLong(request, "userId");
		int isLock = RequestUtil.getInt(request, "isLock");
		int status = RequestUtil.getInt(request, "status");
		try {
			sysUserService.updStatus(userId, (short) status, (short) isLock);
			writeResultMessage(response.getWriter(), "修改用户状态成功!", ResultMessage.Success);
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"修改用户状态失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 取得用户表明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看用户表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long userId = RequestUtil.getLong(request, "userId");
		String canReturn=RequestUtil.getString(request, "canReturn");
		ISysUser sysUser = sysUserService.getById(userId);
		String pictureLoad = defaultUserImage;
		if (sysUser != null) {
			if (StringUtil.isNotEmpty(sysUser.getPicture())) {
				pictureLoad = sysUser.getPicture();
			}
		}
		List<UserRole> roleList = userRoleService.getByUserId(userId);
		List<UserPosition> posList = userPositionDao.getByUserId(userId);
		List<SysUserOrg> orgList = sysUserOrgService.getOrgByUserId(userId);

		List<SysUserParam> userParamList = sysUserParamService.getByUserId(userId);

		return getAutoView().addObject("sysUser", sysUser).addObject("roleList", roleList).addObject("posList", posList).addObject("orgList", orgList)
				.addObject("pictureLoad", pictureLoad).addObject("userParamList", userParamList).addObject("canReturn",canReturn);
	}

	/**
	 * 取得用户表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dialog")
	public ModelAndView dialog(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mv = this.getAutoView();

		List<Demension> demensionList = demensionService.getAll();
		mv.addObject("demensionList", demensionList);
		List<SubSystem> subSystemList = subSystemService.getAll();
		mv.addObject("subSystemList", subSystemList);

		String isSingle = RequestUtil.getString(request, "isSingle", "false");
		mv.addObject("isSingle", isSingle);
		
		return mv;
	}
	
	/**
	 * 取得用户表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("companyDialog")
	public ModelAndView companyDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mv = this.getAutoView();

		List<Demension> demensionList = demensionService.getAll();
		mv.addObject("demensionList", demensionList);
		List<SubSystem> subSystemList = subSystemService.getAll();
		mv.addObject("subSystemList", subSystemList);

		String isSingle = RequestUtil.getString(request, "isSingle", "false");
		mv.addObject("isSingle", isSingle);
		
		long companyId = RequestUtil.getLong(request, "companyId", 0);
		mv.addObject("companyId", companyId);
		
		return mv;
	}
	
	@RequestMapping("flowDialog")
	public ModelAndView flowDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = this.getAutoView();
		List<Demension> demensionList = demensionService.getAll();
		mv.addObject("demensionList", demensionList);
		List<SubSystem> subSystemList = subSystemService.getAll();
		
		mv.addObject("isSingle", "false");
		mv.addObject("subSystemList", subSystemList);
		return mv;
	}

	@RequestMapping("selector")
	public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ISysUser> list = null;
		ModelAndView result = getAutoView();
		String searchBy = RequestUtil.getString(request, "searchBy");
		QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
		
		/**
		 * modifyBy hotent.xianggang b 添加过滤条件
		 */
		ISysUser currentUser=ContextUtil.getCurrentUser();
		Long currentUserOrgId=currentUser.getOrgId();
		////remove orgId 从而能将所有的人添加到岗位 而非仅仅当前部门的人
		queryFilter.addFilter("orgId", currentUserOrgId);
		/**
		 * modifyBy hotent.xianggang e 
		 */
		
		if (SystemConst.SEARCH_BY_ONL.equals(searchBy)) {
			String demId = RequestUtil.getString(request, "path");
			if (demId.equals("-1")) {//未分配组织的用户
				list=sysUserService.getUserNoOrg(queryFilter);
			} else{
				list = sysUserService.getDistinctUserByOrgPath(queryFilter);
			}
			list = sysUserService.getOnlineUser(list);
		} else if (SystemConst.SEARCH_BY_ORG.equals(searchBy)) {
			list = sysUserService.getDistinctUserByOrgPath(queryFilter);
		} else if (SystemConst.SEARCH_BY_POS.equals(searchBy)) {
			list = sysUserService.getDistinctUserByPosPath(queryFilter);
		} else if (SystemConst.SEARCH_BY_ROL.equals(searchBy)) {
			list = sysUserService.getUserByRoleId(queryFilter);
		} else if (SystemConst.SEARCH_BY_ENTERPRIES.equals(searchBy)){//modifyBy hotent.xianggang
			list = sysUserService.getUserByEnterprise(queryFilter);//modifyBy hotent.xianggang
		}else {
			list = sysUserService.getUserByQuery(queryFilter);
		}
		result.addObject("sysUserList", list);

		String isSingle = RequestUtil.getString(request, "isSingle", "false");
		result.addObject("isSingle", isSingle);

		return result;
	}

	/**
	 * 企业用户选择器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectorCompany")
	public ModelAndView selectorCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ISysUser> list = null;
		ModelAndView result = getAutoView();
		String searchBy = RequestUtil.getString(request, "searchBy");
		QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
		
		/**
		 * modifyBy hotent.xianggang b 添加过滤条件
		 */
		ISysUser currentUser=ContextUtil.getCurrentUser();
		long companyId = RequestUtil.getLong(request, "companyId", 0);
		if(companyId == 0)
			companyId = currentUser.getOrgId();
		
		queryFilter.addFilter("orgId", companyId);
		/**
		 * modifyBy hotent.xianggang e 
		 */
		
		if (SystemConst.SEARCH_BY_ONL.equals(searchBy)) {
			String demId = RequestUtil.getString(request, "path");
			if (demId.equals("-1")) {//未分配组织的用户
				list=sysUserService.getUserNoOrg(queryFilter);
			} else{
				list = sysUserService.getDistinctUserByOrgPath(queryFilter);
			}
			list = sysUserService.getOnlineUser(list);
		} else if (SystemConst.SEARCH_BY_ORG.equals(searchBy)) {
			list = sysUserService.getDistinctUserByOrgPath(queryFilter);
		} else if (SystemConst.SEARCH_BY_POS.equals(searchBy)) {
			list = sysUserService.getDistinctUserByPosPath(queryFilter);
		} else if (SystemConst.SEARCH_BY_ROL.equals(searchBy)) {
			list = sysUserService.getUserByRoleId(queryFilter);
		} else if (SystemConst.SEARCH_BY_ENTERPRIES.equals(searchBy)){//modifyBy hotent.xianggang
			list = sysUserService.getUserByEnterprise(queryFilter);//modifyBy hotent.xianggang
		}else {
			list = sysUserService.getUserByQuery(queryFilter);
		}
		result.addObject("sysUserList", list);

		String isSingle = RequestUtil.getString(request, "isSingle", "false");
		result.addObject("isSingle", isSingle);
		result.addObject("companyId", companyId);

		return result;
	}
	
	/**
	 * 获取在线用户树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getTreeData")
	@ResponseBody
	public List<OnlineUser> getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<Long, OnlineUser> onlineUsers = AppUtil.getOnlineUsers();
		List<OnlineUser> onlineList = new ArrayList<OnlineUser>();
		for (Long sessionId : onlineUsers.keySet()) {
			OnlineUser onlineUser = new OnlineUser();
			onlineUser = onlineUsers.get(sessionId);
			onlineList.add(onlineUser);
		}
		return onlineList;
	}
	
	/**
	 * 获取系统中，来自Ad的用户数据。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("syncList")
	public ModelAndView ldapUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean connectable = isLdapConnnectable();
		ModelAndView mv=getAutoView();
		mv.addObject("connectable", connectable);
		if(!connectable){
			return mv;
		}
		QueryFilter queryFilter=new QueryFilter(request, "sysUserMapItem");
		queryFilter.addFilter("fromType", SystemConst.FROMTYPE_AD);

		
		List<ISysUser> sysUserList = sysUserService.getUserByQuery(queryFilter);
//		queryFilter.addFilter("fromType", ISysUser.FROMTYPE_AD_SET);
//		sysUserList.addAll(sysUserService.getUserByQuery(queryFilter));

		List<LdapUser> ldapUserList;

		ldapUserList = ldapUserService.getAll();
		if(ldapUserList==null){
			ldapUserList=new ArrayList<LdapUser>();
		}
		List<Map<String,Object>> userMapList=new ArrayList<Map<String,Object>>();
		for(ISysUser sysUser:sysUserList){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("sysUser", sysUser);
			LdapUser ldapUser=UserHelper.getEqualUserByAccount(sysUser, ldapUserList);		
			if(ldapUser!=null){//AD未删除
				boolean sync = UserHelper.isSysLdapUsrEqualIngoreOrg(sysUser, ldapUser);
				if(sync){
					map.put("sync", 0);//DB与AD同步
				}else{
					map.put("sync", 1);//DB与AD不同步
				}
			}else{//AD已删除
				map.put("sync", -1);
			}
			userMapList.add(map);
		}
		mv.addObject("sysUserMapList", userMapList);
		return mv;
	}
	
	
	/**
	 * 将指定的系统用户在AD中数据同步到系统中，用户在Ad中数据将覆盖用户在系统中的数据。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("syncLdap")
	public void syncLdap(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String preUrl=RequestUtil.getPrePage(request);
		Long userId=RequestUtil.getLong(request, "userId");
		ISysUser sysUser=sysUserService.getById(userId);
		if(sysUser==null){
			message=new ResultMessage(ResultMessage.Fail, "在数据库中未找到用户数据！");
			addMessage(message, request);
			response.sendRedirect(preUrl);
			return;
		}
		String account=sysUser.getAccount();
		LdapUser ldapUser = ldapUserService.getByAccount(account);
		if(ldapUser==null){
			message=new ResultMessage(ResultMessage.Fail, "在AD中未找到用户数据！");
			addMessage(message, request);
			response.sendRedirect(preUrl);
			return;
		}
		if(!UserHelper.isSysLdapUsrEqualIngoreOrg(sysUser, ldapUser)){
			sysUser.setAccount(ldapUser.getsAMAccountName());
			sysUser.setEmail(ldapUser.getMail());
			String givenName= ldapUser.getGivenName();
			String sn = ldapUser.getSn();
			String fullname= (sn==null?"":sn)+(givenName==null?"":givenName);
			fullname=fullname.equals("")?null:fullname;
			sysUser.setFullname(fullname);
			sysUser.setPhone(ldapUser.getHomePhone());
			sysUser.setMobile(ldapUser.getTelephoneNumber());
			sysUser.setStatus(ldapUser.isAccountDisable()?SystemConst.STATUS_NO:SystemConst.STATUS_OK);
		}
		sysUserService.update(sysUser);
		message=new ResultMessage(ResultMessage.Success, "用户数据与AD同步成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	/**
	 * 查看用户在系统与Ad中的数据差异
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getLdapComp")
	public ModelAndView getLdapComp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId=RequestUtil.getLong(request, "userId");
		ISysUser sysUser=sysUserService.getById(userId);
		String account=sysUser.getAccount();
		LdapUser ldapUser = ldapUserService.getByAccount(account);
		ModelAndView mv=getAutoView();
		mv.addObject("sysUser", sysUser);
		mv.addObject("ldapUser", ldapUser);
		return mv;
	}
	
	@RequestMapping("syncToLdap")
	public ModelAndView syncToLdap(HttpServletRequest request, HttpServletResponse response) throws Exception {
		sysUserSyncService.reset();
		sysOrgSyncService.syncLodapToDb(); 
		int syncNum = sysUserSyncService.syncLodapToDb();
		ModelAndView mv=getAutoView();
		mv.addObject("syncNum", syncNum);
		mv.addObject("lastSyncTakeTime", sysUserSyncService.getLastSyncTakeTime());
		mv.addObject("lastSyncTime", sysUserSyncService.getLastSyncTime());
		mv.addObject("newFromLdapUserList", sysUserSyncService.getNewFromLdapUserList());
		mv.addObject("deleteLocalUserList", sysUserSyncService.getDeleteLocalUserList());
		mv.addObject("updateLocalUserList", sysUserSyncService.getUpdateLocalUserList());
		return mv;
	}
	
	public boolean isLdapConnnectable(){
		try{
			LdapTemplate ldapTemplate = (LdapTemplate) AppUtil.getBean(LdapTemplate.class);
			if(ldapTemplate==null){
				return false;
			}else{
				DistinguishedName base = new  DistinguishedName();
				ldapTemplate.list(base);
				return true;
			}
		}catch (Exception e) {
			return false;
		}
	}
}
