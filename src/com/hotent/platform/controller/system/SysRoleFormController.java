package com.hotent.platform.controller.system;

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
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.dao.system.SubSystemDao;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.UserRole;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.UserRoleService;

/**
 * 对象功能:系统角色表 控制器类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2011-11-28 11:39:03
 */
@Controller
@RequestMapping("/platform/system/sysRole/")
public class SysRoleFormController extends BaseFormController
{
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SubSystemDao subSystemDao;
	@Resource
	private IAuthenticate iAuthenticate;
	
	/**
	 * 添加或更新系统角色表。
	 * @param request
	 * @param response
	 * @param sysRole 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新系统角色表")
	public void save(HttpServletRequest request, HttpServletResponse response, ISysRole sysRole,BindingResult bindResult) throws Exception
	{
		Long systemId=RequestUtil.getLong(request, "systemId");
		String systemAlias="";
		if(systemId>0){
			SubSystem subSystem=subSystemDao.getById(systemId);
			systemAlias=subSystem.getAlias() +"_";
		}
		ResultMessage resultMessage=validForm("sysRole", sysRole, bindResult, request);
		if(resultMessage.getResult()==ResultMessage.Fail){
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg=null;
		if(sysRole.getRoleId()==null){
			String tmpAlias=sysRole.getAlias();
			String alias=systemAlias +tmpAlias;
			//判断别名是否存在。
			boolean isExist=sysRoleService.isExistRoleAlias(alias);
			if(!isExist){
				sysRole.setRoleId(UniqueIdUtil.genId());
				sysRole.setAlias(alias);
				sysRoleService.add(sysRole);
				resultMsg=getText("record.added","系统角色");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			}else{
				resultMsg=getText("角色别名：["+tmpAlias+"]已存在");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
			}
		}else{
			String tmpAlias=sysRole.getAlias();
			String alias=systemAlias +tmpAlias;
			Long roleId=sysRole.getRoleId();
			boolean isExist=sysRoleService.isExistRoleAliasForUpd(alias, roleId);
			if(isExist){
				resultMsg=getText("角色别名：["+tmpAlias+"]已存在");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
			}
			else{
				sysRole.setAlias(alias);
				sysRoleService.update(sysRole);
				resultMsg=getText("record.updated","系统角色");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			}
		}
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param roleId
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected ISysRole getFormObject(@RequestParam("roleId") Long roleId,Model model) throws Exception {
		logger.debug("enter ISysRole getFormObject here....");
		ISysRole sysRole=null;
		if(roleId!=null){
			sysRole=sysRoleService.getById(roleId);
		}else{
			sysRole= iAuthenticate.getNewSysRole();
		}
		return sysRole;
    }

}
