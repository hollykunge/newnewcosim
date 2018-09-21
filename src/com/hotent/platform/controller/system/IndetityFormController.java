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

import com.hotent.platform.model.system.Identity;

import com.hotent.platform.service.system.IdentityService;

/**
 * 对象功能:流水号生成 控制器类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2012-02-03 14:40:59
 */
@Controller
@RequestMapping("/platform/system/indetity/")
public class IndetityFormController extends BaseFormController
{
	@Resource
	private IdentityService identityService;
	
	/**
	 * 添加或更新流水号生成。
	 * @param request
	 * @param response
	 * @param identity 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新流水号生成")
	public void save(HttpServletRequest request, HttpServletResponse response, Identity identity,BindingResult bindResult) throws Exception
	{
		
		ResultMessage resultMessage=validForm("indetity", identity, bindResult, request);
		//add your custom validation rule here such as below code:
		//bindResult.rejectValue("name","errors.exist.student",new Object[]{"jason"},"重复姓名");
		if(resultMessage.getResult()==ResultMessage.Fail)
		{
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg=null;
		if(identity.getId()==0L){
			boolean rtn=identityService.isAliasExisted(identity.getAlias());
			if(rtn){
				writeResultMessage(response.getWriter(),"别名已经存在!",ResultMessage.Fail);
				return;
			}
			else{
				identity.setId(UniqueIdUtil.genId());
				identity.setCurDate(IdentityService.getCurDate());
				identityService.add(identity);
				resultMsg=getText("record.added","流水号生成");
			}
		}else{
			boolean rtn=identityService.isAliasExistedByUpdate(identity);
			if(rtn){
				writeResultMessage(response.getWriter(),"别名已经存在!",ResultMessage.Fail);
				return;
			}
			identityService.update(identity);
			resultMsg=getText("record.updated","流水号生成");
		}
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected Identity getFormObject(@RequestParam("id") Long id,Model model) throws Exception {
		logger.debug("enter Indetity getFormObject here....");
		Identity indetity=null;
		if(id>0){
			indetity=identityService.getById(id);
		}else{
			indetity= new Identity();
		}
		return indetity;
    }

}
