package com.hotent.platform.controller.extform;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.hotent.platform.model.extform.PersonData;
import com.hotent.platform.service.extform.PersonDataService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:PERSON_DATA 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2013-01-10 09:44:06
 */
@Controller
@RequestMapping("/platform/extform/personData/")
public class PersonDataController extends BaseController
{
	@Resource
	private PersonDataService personDataService;
	
	/**
	 * 添加或更新PERSON_DATA。
	 * @param request
	 * @param response
	 * @param personData 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新PERSON_DATA")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;
		PersonData personData=getFormObject(request);
		try{
			if(personData.getId()==null||personData.getId()==0){
				personData.setId(UniqueIdUtil.genId());
				personDataService.add(personData);
				resultMsg=getText("record.added","PERSON_DATA");
			}else{
			    personDataService.update(personData);
				resultMsg=getText("record.updated","PERSON_DATA");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 PersonData 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected PersonData getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		PersonData personData = (PersonData)JSONObject.toBean(obj, PersonData.class);
		
		return personData;
    }
	
	/**
	 * 取得PERSON_DATA分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看PERSON_DATA分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<PersonData> list=personDataService.getAll(new QueryFilter(request,"personDataItem"));
		ModelAndView mv=this.getAutoView().addObject("personDataList",list);
		
		return mv;
	}
	
	/**
	 * 删除PERSON_DATA
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除PERSON_DATA")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "userid");
			personDataService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除PERSON_DATA成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑PERSON_DATA
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑PERSON_DATA")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		PersonData personData=personDataService.getById(id);
		
		return getAutoView().addObject("personData",personData).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得PERSON_DATA明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看PERSON_DATA明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		PersonData personData = personDataService.getById(id);	
		return getAutoView().addObject("personData", personData);
	}
}
