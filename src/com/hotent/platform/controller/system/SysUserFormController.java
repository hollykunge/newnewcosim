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
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;


/**
 * 对象功能:用户表 控制器类 
 */
@Controller
@RequestMapping("/platform/system/sysUser/")
public class SysUserFormController extends BaseFormController
{
	@Resource
	private SysUserService sysUserService;
	@Resource
	private IAuthenticate iAuthenticate;

	/**
	 * 添加或更新用户表。
	 * 
	 * @param request
	 * @param response
	 * @param sysUser
	 *            添加或更新的实体
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新用户表")
	public void save(HttpServletRequest request, HttpServletResponse response, ISysUser sysUser, BindingResult bindResult) throws Exception {

//		ResultMessage resultMessage = validForm("sysUser", sysUser, bindResult, request);
//		// add your custom validation rule here such as below code:
//		// bindResult.rejectValue("name","errors.exist.student",new
//		// Object[]{"jason"},"重复姓名");
//		if (resultMessage.getResult() == ResultMessage.Fail){
//			writeResultMessage(response.getWriter(), resultMessage);
//			return;
//		}
        ResultMessage resultMessage = null;
                String resultMsg = null;

		Long[] aryOrgId = RequestUtil.getLongAry(request, "orgId") ;// 组织Id数组
		Long orgIdPrimary = RequestUtil.getLong(request, "orgIdPrimary");// 主要组织Id
		Long[] aryChargeOrg = RequestUtil.getLongAry(request, "chargeOrgId") ;// 组织Id数组
		Long[] aryPosId =  RequestUtil.getLongAry(request,"posId");// 岗位Id数组
		Long posIdPrimary = RequestUtil.getLong(request, "posIdPrimary");// 主要岗位Id
		Long[] aryRoleId =RequestUtil.getLongAry(request,"roleId");// 角色Id数组
		Integer bySelf=RequestUtil.getInt(request,"bySelf");//是否用户自己编辑
		if(aryOrgId==null || aryOrgId.length==0){
			resultMsg = getText("record.add.fail.user", "请选择所属组织！");
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Fail);
			return;			
		}
		if (BeanUtils.isNotEmpty(aryOrgId)&& orgIdPrimary==0){
			resultMsg = getText("record.add.fail.user", "请选择主组织！");
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Fail);
			return;
		}
		if (BeanUtils.isNotEmpty(aryPosId)&& posIdPrimary==0){
			resultMsg = getText("record.add.fail.user", "请选择主岗位！");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
			return;
		}
		//【HT】
		if(aryRoleId==null || aryRoleId.length==0){
			resultMsg = getText("record.add.fail.user", "请选择所属角色！");
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Fail);
			return;					
		}
		if (sysUser.getUserId() == null){
			boolean isExist = sysUserService.isAccountExist(sysUser.getAccount());
			if(isExist){
				resultMsg = getText("record.add.fail", "该账号已经存在！");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return ;
			}
			
			String enPassword = EncryptUtil.encryptSha256(sysUser.getPassword());
			sysUser.setPassword(enPassword);
			sysUserService.saveUser(bySelf,sysUser,aryOrgId,aryChargeOrg,orgIdPrimary,aryPosId,posIdPrimary,aryRoleId);
			resultMsg = getText("record.added", "用户表");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			
		} 
		else{
			boolean isExist=sysUserService.isAccountExistForUpd(sysUser.getUserId(), sysUser.getAccount());
			if(isExist){
				resultMsg = getText("record.add.fail", "该账号已经存在！");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return ;
			}
			sysUserService.saveUser(bySelf,sysUser,aryOrgId,aryChargeOrg,orgIdPrimary,aryPosId,posIdPrimary,aryRoleId);
			resultMsg = getText("record.updated", "用户表");
			
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Success);
		}

	}

	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * 
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModelAttribute
	protected ISysUser getFormObject(@RequestParam("userId") Long userId, 	Model model) throws Exception{
		logger.debug("enter ISysUser getFormObject here....");
		ISysUser sysUser = null;
		if (userId != null){
			sysUser = sysUserService.getById(userId);
		} 
		else{
			sysUser = iAuthenticate.getNewSysUser();
		}
		return sysUser;
	}

}
