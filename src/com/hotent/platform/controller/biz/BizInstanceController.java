package com.hotent.platform.controller.biz;

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
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.hotent.platform.dao.biz.BizDefSegmentDao;
import com.hotent.platform.model.biz.BizDef;
import com.hotent.platform.model.biz.BizDefSegment;
import com.hotent.platform.model.biz.BizInstance;
import com.hotent.platform.model.biz.BizInstanceSegment;
import com.hotent.platform.service.biz.BizDefService;
import com.hotent.platform.service.biz.BizInstanceSegmentService;
import com.hotent.platform.service.biz.BizInstanceService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:业务实例 控制器类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Controller
@RequestMapping("/platform/biz/bizInstance/")
public class BizInstanceController extends BaseController
{
	@Resource
	private BizInstanceService bizInstanceService;
	
	@Resource 
	private BizInstanceSegmentService bizInstanceSegmentService;
	@Resource
	private BizDefService bizDefService;
	@Resource 
	private BizDefSegmentDao bizDefSegmentDao;
	
	
	/**
	 * 添加或更新业务实例。
	 * @param request
	 * @param response
	 * @param bizInstance 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新业务实例")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		BizInstance bizInstance=getFormObject(request);
		try{
			if(bizInstance.getBizInstanceId()==null||bizInstance.getBizInstanceId()==0){
				bizInstance.setBizInstanceId(UniqueIdUtil.genId());
				bizInstanceService.add(bizInstance);
				resultMsg=getText("record.added","业务实例");
			}else{
			    bizInstanceService.update(bizInstance);
				resultMsg=getText("record.updated","业务实例");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 BizInstance 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected BizInstance getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		BizInstance bizInstance = (BizInstance)JSONObject.toBean(obj, BizInstance.class);
		
		return bizInstance;
    }
	
	/**
	 * 取得业务实例分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看业务实例分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<BizInstance> list=bizInstanceService.getAll(new QueryFilter(request,"bizInstanceItem"));
		ModelAndView mv=this.getAutoView().addObject("bizInstanceList",list);
		
		return mv;
	}
	
	/**
	 * 删除业务实例
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除业务实例")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "bizInstanceId");
			bizInstanceService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除业务实例成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑业务实例
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑业务实例")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long bizInstanceId=RequestUtil.getLong(request,"bizInstanceId");
		String returnUrl=RequestUtil.getPrePage(request);
		BizInstance bizInstance=bizInstanceService.getById(bizInstanceId);
		
		return getAutoView().addObject("bizInstance",bizInstance).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得业务实例明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看业务实例明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long bizInstanceId=RequestUtil.getLong(request,"bizInstanceId");
		BizInstance bizInstance = bizInstanceService.getById(bizInstanceId);
		List<BizInstanceSegment> bizInstanceSegments = bizInstanceSegmentService.getByBizInstanceId(bizInstance.getBizInstanceId());
		bizInstance.setBizInstanceSegmentList(bizInstanceSegments);
		BizDef bizDef = bizDefService.getById(bizInstance.getBizDefId());
		List<BizDefSegment> bizDefSegments = bizDefSegmentDao.getByMainId(bizDef.getBizDefId());
		bizDef.setBizDefSegmentList(bizDefSegments);
		return getAutoView().addObject("bizInstance", bizInstance)
				.addObject("bizDef", bizDef);
	}
	
}
