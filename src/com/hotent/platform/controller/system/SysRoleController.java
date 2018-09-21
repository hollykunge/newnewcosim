package com.hotent.platform.controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysRoleService;

/**
 * 对象功能:系统角色表 控制器类 开发公司: 开发人员:csx 创建时间:2011-11-28 11:39:03
 */
@Controller
@RequestMapping("/platform/system/sysRole/")
public class SysRoleController extends BaseController {
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private IAuthenticate iAuthenticate;

	/**
	 * 角色对话框的展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selector")
	public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "sysRoleItem");
		queryFilter.getPageBean().setPagesize(10);
		List<ISysRole> list = sysRoleService.getAll(queryFilter);
		ModelAndView mv = this.getAutoView().addObject("sysRoleList", list);
		return mv;
	}

	/**
	 * 取得系统角色表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看系统角色表分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ISysRole> list = sysRoleService.getRoleList(new QueryFilter(request, "sysRoleItem"));
		ModelAndView mv = this.getAutoView().addObject("sysRoleList", list);

		return mv;
	}

	/**
	 * 删除系统角色表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除系统角色表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "roleId");
			sysRoleService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除系统角色成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除系统角色失败:" + e.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("copy")
	@Action(description = "编辑系统角色表")
	public ModelAndView copy(HttpServletRequest request) throws Exception {
		Long roleId = RequestUtil.getLong(request, "roleId");

		ISysRole sysRole = sysRoleService.getById(roleId);
		Long systemId = sysRole.getSystemId();
		// 如果系统id不为空，则将角色别名系统前缀替换掉。
		if (systemId != null) {
			SubSystem subSystem = subSystemService.getById(systemId);
			String sysAlias = subSystem.getAlias();
			String roleAlias = sysRole.getAlias().replaceFirst(sysAlias + "_", "");
			sysRole.setAlias(roleAlias);
		}
		return getAutoView().addObject("sysRole", sysRole);
	}

	/**
	 * 编辑系统角色
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑系统角色表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long roleId = RequestUtil.getLong(request, "roleId");
		String returnUrl = RequestUtil.getPrePage(request);
		ISysRole sysRole = null;
		if (roleId != 0) {
			sysRole = sysRoleService.getById(roleId);
			Long systemId = sysRole.getSystemId();
			// 如果系统id不为空，则将角色别名系统前缀替换掉。
			if (systemId != null) {
				SubSystem subSystem = subSystemService.getById(systemId);
				String sysAlias = subSystem.getAlias();
				String roleAlias = sysRole.getAlias().replaceFirst(sysAlias + "_", "");
				sysRole.setAlias(roleAlias);
			}
		} else {
			sysRole = iAuthenticate.getNewSysRole();
		}
		List<SubSystem> list = subSystemService.getActiveSystem();
		return getAutoView().addObject("sysRole", sysRole).addObject("subsystemList", list).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得系统角色表明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看系统角色表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "roleId");
		ISysRole sysRole = sysRoleService.getById(id);
		SubSystem subsystem = null;
		if (sysRole.getSystemId() != null) {
			subsystem = subSystemService.getById(sysRole.getSystemId());
		}

		return getAutoView().addObject("sysRole", sysRole).addObject("subsystem", subsystem);
	}

	/**
	 * 复制角色
	 * 
	 * @param request
	 * @param response
	 * @param po
	 * @throws Exception
	 */
	@RequestMapping("copyRole")
	@Action(description = "复制角色")
	public void copyRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		long roleId = RequestUtil.getLong(request, "roleId");

		String roleName = RequestUtil.getString(request, "newRoleName");
		String alias = RequestUtil.getString(request, "newAlias");
		long newRoleId = UniqueIdUtil.genId();

		ISysRole sysRole = sysRoleService.getById(roleId);
		Long systemId = sysRole.getSystemId();
		if (systemId != null) {
			SubSystem subSystem = subSystemService.getById(systemId);
			alias = subSystem.getAlias() + "_" + alias;
		}

		boolean rtn = sysRoleService.isExistRoleAlias(alias);
		if (rtn) {
			writeResultMessage(writer, "输入的别名已存在", ResultMessage.Fail);
			return;
		}

		ISysRole newRole = (ISysRole) sysRole.clone();
		newRole.setRoleId(newRoleId);
		newRole.setAlias(alias);
		newRole.setRoleName(roleName);

		try {
			sysRoleService.copyRole(newRole, roleId);
			writeResultMessage(writer, "复制角色成功!", ResultMessage.Success);
		} catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"复制角色失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 获取角色树。
	 * 
	 * <pre>
	 * ISysRole 的systemId 
	 * 1. 0代表是子系统。
	 * 2. 1代表全局角色。
	 * 3. 其他为子系统的角色。
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getTreeData")
	@ResponseBody
	public List  getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//List<ISysRole> rolList = sysRoleService.getRoleTree(new QueryFilter(request, "sysRole", false));
		List<ISysRole> rolList =new ArrayList<ISysRole>();
		List<ISysRole> trolList=sysRoleService.getAll();		
		for(ISysRole role:trolList){
			if(role.getMemo().equals("内置角色")){
				continue;
			}else{
				rolList.add(role);
			}
		}
		ISysRole rol = null;
		
		List  treeList=new ArrayList();
		//【HT】注释
//		//全局   父节点 {0,0，全局角色,true}
//		rol=iAuthenticate.getNewSysRole();
//		rol.setRoleId(new Long(0));
//		rol.setSystemId(new Long(0));//相当于pid
//		rol.setRoleName("全局角色");
//		rol.setIsParent("true");//是否父节点,不加这个，无自己点的时候，父节点不是文件夹样式
//		treeList.add(rol);
//	
//		//全局   子节点
//		for(ISysRole sysRole:rolList){
//			//全局变量
//			if( sysRole.getSystemId()==null ||sysRole.getSystemId().equals("")){
//				sysRole.setSystemId(0L);
//				//{sysRole.getRoleId().toString(),"0",sysRole.getRoleName()}
//				treeList.add(sysRole);
//			}
//		}
		
		//循环子系统，{子系统角色id,"-1","子系统1"}
		List<SubSystem> sublist = subSystemService.getActiveSystem();
		Long i=-1L;
		for(SubSystem subSys:sublist){
			 i--;//子系统 角色 pid，-1,-2，-3，
			 rol=iAuthenticate.getNewSysRole();
			 rol.setRoleId(i);
			 rol.setSystemId(-9999999999L);//子系统pid
			 rol.setRoleName(subSys.getSysName());
			 //子系统父节点
			 treeList.add(rol);
			//{String.valueOf(i),  "-9999999999",subSys.getSysName()}
			
			List<ISysRole> rolList2=sysRoleService.getAll();
			for(ISysRole sysRole2:rolList2){//子系统添加 角色子节点
						if(sysRole2.getMemo().equals("内置角色")){
								continue;
						}
				if(sysRole2.getSystemId()!=null &&subSys.getSystemId()==sysRole2.getSystemId()){
					sysRole2.setSystemId( i);
					//所有子系统的子节点
					treeList.add(sysRole2);
					//{String.valueOf(sysRole2.getSystemId()),  String.valueOf(i),sysRole2.getRoleName()}	
				}
			}
		}
		return treeList;
	}

	/**
	 * 取得系统角色表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAll")
	@ResponseBody
	public List<ISysRole> getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ISysRole> list = sysRoleService.getAll(new QueryFilter(request, false));
		return list;
	}

	/**
	 * 禁用或启用角色
	 * 
	 * @param request
	 * @param response
	 * @param po
	 * @throws Exception
	 */
	@RequestMapping("runEnable")
	@Action(description = "禁用或启用角色")
	public void runEnableRole(HttpServletRequest request, HttpServletResponse response) throws Exception {

		long roleId = RequestUtil.getLong(request, "roleId");
	
		ISysRole sysRole = sysRoleService.getById(roleId);
		if(sysRole.getEnabled().equals((short)1)){
			sysRole.setEnabled((short) 0);
		}
		else{
			sysRole.setEnabled((short) 1);
		}
		sysRoleService.update(sysRole);
		//删除所有的缓存。
		SecurityUtil.removeAll();
		response.sendRedirect(RequestUtil.getPrePage(request));
	}

}

