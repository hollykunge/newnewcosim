
package com.hotent.platform.controller.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.Dictionary;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.DictionaryService;
import com.hotent.platform.service.system.GlobalTypeService;

/**
 * 对象功能:数据字典 控制器类
 * 开发公司:
 * 开发人员:ljf
 * 创建时间:2011-11-23 14:53:34
 */
@Controller
@RequestMapping("/platform/system/dictionary/")
public class DictionaryFormController extends BaseFormController
{
	@Resource
	private DictionaryService dictionaryService;

	
	/**
	 * 添加或更新数据字典.
	 * @param request  HttpServletRequest对象
	 * @param response HttpServletResponse对象
	 * @param dictionary 字典对象，Web表单对象
	 * @param bindResult 命令/表单对象的验证结果对象
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新数据字典")
	public void save(HttpServletRequest request, HttpServletResponse response, Dictionary dictionary,BindingResult bindResult) throws Exception
	{
		PrintWriter out=response.getWriter();
		ResultMessage resultMessage=validForm("dictionary", dictionary, bindResult, request);
		if(resultMessage.getResult()==ResultMessage.Fail){
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		
		Long dicId=dictionary.getDicId();
		Long typeId=dictionary.getTypeId();
		Long parentId=dictionary.getParentId();
		String itemKey=dictionary.getItemKey();
		//添加
		if(dicId==0){
			if(StringUtil.isNotEmpty(itemKey)){
				 boolean rtn= dictionaryService.isItemKeyExists(typeId, itemKey);
				 if(rtn){
					 resultMessage=new ResultMessage(ResultMessage.Fail,"字典关键字已存在");
					 writeResultMessage(out, resultMessage);
					 return;
				 }
			}
			try{
				dicId=UniqueIdUtil.genId();
				dictionary.setDicId(dicId);
				dictionary.setSn(0L);
				//根节点。
				if(parentId.equals(typeId)){
					dictionary.setParentId(typeId);
					dictionary.setNodePath(parentId +"." + dicId +".");
				}
				else{
					Dictionary parentDic=dictionaryService.getById(parentId);
					dictionary.setParentId(parentId);
					dictionary.setNodePath(parentDic.getNodePath() + dicId +".");
				}
				dictionaryService.add(dictionary);
				resultMessage=new ResultMessage(ResultMessage.Success,"添加字典成功!");
				writeResultMessage(response.getWriter(), resultMessage);
			}
			catch (Exception ex){
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					resultMessage = new ResultMessage(ResultMessage.Fail,"添加字典失败:" + str);
					response.getWriter().print(resultMessage);
				} else {
					String message = ExceptionUtil.getExceptionMessage(ex);
					resultMessage = new ResultMessage(ResultMessage.Fail, message);
					response.getWriter().print(resultMessage);
				}
			}
		}
		else{
		
			if(StringUtil.isNotEmpty(itemKey)){
				boolean rtn= dictionaryService.isItemKeyExistsForUpdate(dicId, typeId, itemKey);
				if(rtn){
					resultMessage=new ResultMessage(ResultMessage.Fail,"字典关键字已存在");
					writeResultMessage(out, resultMessage);
					return;
				}
			}
			try{
				dictionaryService.update(dictionary);
				resultMessage=new ResultMessage(ResultMessage.Success,"编辑字典成功!");
				writeResultMessage(response.getWriter(), resultMessage);
			}
			catch (Exception e) {
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					resultMessage = new ResultMessage(ResultMessage.Fail,"编辑字典失败:" + str);
					response.getWriter().print(resultMessage);
				} else {
					String message = ExceptionUtil.getExceptionMessage(e);
					resultMessage = new ResultMessage(ResultMessage.Fail, message);
					response.getWriter().print(resultMessage);
				}
			}
		}
	}
	
	


	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param typeId
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected Dictionary getFormObject(@RequestParam("dicId") Long dicId,Model model) throws Exception {
		Dictionary dictionary=null;
		
		if(dicId!=0){
			dictionary=dictionaryService.getById(dicId);
		}else{
			dictionary= new Dictionary();
		}
		return dictionary;
    }
}
