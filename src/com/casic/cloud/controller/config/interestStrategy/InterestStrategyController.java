package com.casic.cloud.controller.config.interestStrategy;

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

import com.casic.cloud.model.config.interestStrategy.InterestStrategy;
import com.casic.cloud.service.config.interestStrategy.InterestStrategyService;
import com.casic.cloud.model.config.interestStrategy.InterestStrategyDetail;
import com.casic.cloud.model.config.priceStrategy.PriceStrategy;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.webservice.api.ProcessService;
/**
 *<pre>
 * 对象功能:cloud_interest_strategy 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-09-24 17:29:56
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/interestStrategy/")
public class InterestStrategyController extends BaseController
{
	@Resource
	private InterestStrategyService interestStrategyService;
	
	@Resource
	private ProcessService processService;
	private final String defKey = "interestStrategy";	//绑定流程定义
	
	/**
	 * 添加或更新cloud_interest_strategy。
	 * @param request
	 * @param response
	 * @param interestStrategy 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_interest_strategy")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		InterestStrategy interestStrategy=getFormObject(request);
		try{
			if(interestStrategy.getId()==null||interestStrategy.getId()==0){
				interestStrategy.setId(UniqueIdUtil.genId());
				interestStrategyService.addAll(interestStrategy);			
				resultMsg=getText("record.added","cloud_interest_strategy");
			}else{
			    interestStrategyService.updateAll(interestStrategy);
				resultMsg=getText("record.updated","cloud_interest_strategy");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 InterestStrategy 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected InterestStrategy getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("interestStrategyDetailList", InterestStrategyDetail.class);
		InterestStrategy interestStrategy = (InterestStrategy)JSONObject.toBean(obj, InterestStrategy.class,map);
		
		return interestStrategy;
    }
	
	/**
	 * 取得cloud_interest_strategy分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_interest_strategy分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<InterestStrategy> list=interestStrategyService.getAll(new QueryFilter(request,"interestStrategyItem"));
		ModelAndView mv=this.getAutoView().addObject("interestStrategyList",list);
		
		return mv;
	}
	
	/**
	 * 删除cloud_interest_strategy
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_interest_strategy")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			interestStrategyService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除cloud_interest_strategy及其从表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑cloud_interest_strategy
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_interest_strategy")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		InterestStrategy interestStrategy=interestStrategyService.getById(id);
		List<InterestStrategyDetail> interestStrategyDetailList=interestStrategyService.getInterestStrategyDetailList(id);
		
		return getAutoView().addObject("interestStrategy",interestStrategy)
							.addObject("interestStrategyDetailList",interestStrategyDetailList)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得cloud_interest_strategy明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_interest_strategy明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		InterestStrategy interestStrategy = interestStrategyService.getById(id);	
		List<InterestStrategyDetail> interestStrategyDetailList=interestStrategyService.getInterestStrategyDetailList(id);
		return getAutoView().addObject("interestStrategy",interestStrategy)
							.addObject("interestStrategyDetailList",interestStrategyDetailList);
	}
	
	/**
	 * 添加或更新cloud_interest_strategy<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("apply")
	@Action(description = "添加或更新cloud_interest_strategy")
	public void apply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		InterestStrategy interestStrategy=getFormObject(request);
		long id=RequestUtil.getLong(request,"id");
		try {
			if (interestStrategy != null) {
				if (interestStrategy.getId() == null || interestStrategy.getId() == 0) {
					interestStrategy.setId(UniqueIdUtil.genId());
					interestStrategyService.add(interestStrategy);
					resultMsg = getText("record.added", "cloud_interest_strategy");
				} else {
					interestStrategyService.update(interestStrategy);
					resultMsg = getText("record.updated", "cloud_interest_strategy");
				}
				id =interestStrategy.getId();
			} 

			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setFlowKey(defKey);
			processCmd.setBusinessKey(String.valueOf(id));
			processCmd.setUserAccount(ContextUtil.getCurrentUser().getFullname());
			processService.start(processCmd);

			writeResultMessage(response.getWriter(), "cloud_interest_strategy成功", ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 执行下一步<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("complete")
	@Action(description = "执行下一步")
	public void complete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String taskId = RequestUtil.getString(request, "taskId");
		String voteAgree = RequestUtil.getString(request, "voteAgree");
		try {
			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setTaskId(taskId);
			processCmd.setUserAccount(ContextUtil.getCurrentUser().getFullname());
			processCmd.setVoteAgree(new Short(voteAgree));
			processService.doNext(processCmd);

			writeResultMessage(response.getWriter(), "执行成功" + "1", ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listInterest")
	@Action(description="查看cloud_price_strategy分页列表")
	public ModelAndView listInsterest(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String type = RequestUtil.getString(request, "type");
		QueryFilter queryFilter = new QueryFilter(request,"interestStrategyItem");
		Long enterpriseId= RequestUtil.getLong(request, "currentEntid");
		if(enterpriseId==0){
			 enterpriseId= ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		}
		Long materialId = RequestUtil.getLong(request, "materialId");
		queryFilter.getFilters().put("enterpriseId", enterpriseId);
		queryFilter.getFilters().put("materialId", materialId);
		List<InterestStrategy> list=interestStrategyService.getAll(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("interestStrategyList",list).addObject("type", type);
		return mv;
	}
	/**
	 * 	编辑cloud_interest_strategy
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("editInterest")
	@Action(description="编辑cloud_interest_strategy")
	public ModelAndView editInterest(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		InterestStrategy interestStrategy=interestStrategyService.getById(id);
		List<InterestStrategyDetail> interestStrategyDetailList=interestStrategyService.getInterestStrategyDetailList(id);
		
		return getAutoView().addObject("interestStrategy",interestStrategy)
							.addObject("interestStrategyDetailList",interestStrategyDetailList)
							.addObject("returnUrl",returnUrl);
	}
}
