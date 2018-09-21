package com.casic.cloud.controller.callSoft;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.tool.ToolBpmNode;

import com.casic.cloud.service.tool.ToolBpmNodeService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.bpm.BpmNodeSet;

/**
 *<pre>
 * 对象功能:本地软件调用 控制器类
 * 开发公司：北京航空航天大学
 * 开发人员:ml
 * 创建时间:2013-04-19 16:51:56
 *</pre>
 */
@Controller
@RequestMapping("/cloud/callSoft/")
public class CallSoftController extends BaseController{
	@Resource
	private ToolBpmNodeService toolBpmNodeService;
//	@RequestMapping("callSoft")
//	@Action(description="调用浏览器端本地软件")
//	public void callSoft(HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		//System.out.println("callSoft~~~~~~~~~~~~~~~");
//		//获取session中的inputParas
//		String inputParas = (String) request.getSession().getAttribute("inputParas");
//		//清除inputParas
//		request.getSession().removeAttribute("inputParas");
//		//动态生成localUrl
//		String localUrl = "cosim://";
//		
//		String soft_config_id = request.getParameter("id");
//		localUrl+="soft_config_id="+soft_config_id;
//		//先不管
//		String invokeParas = request.getParameter("invokeParas");
//		if(invokeParas!=null){
//			localUrl+="&invokeParas="+invokeParas;
//		}else{
//			localUrl+="&invokeParas={}";
//		}
//		
//		//接上输入参数
//		localUrl+=inputParas;
//		System.out.println(localUrl);
//		response.sendRedirect(localUrl);
//	}
	
	
	
	@RequestMapping("callSoft")
	@Action(description="调用浏览器端本地软件")
	public void callSoft(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//String url = "cosim://soft_config_id=10000022190001&h=112&w=22&invokeParas={25,2,3}";
		//获取session中的inputParas
		String inputParas = (String) request.getSession().getAttribute("inputParas");
		//清除inputParas
//		request.getSession().removeAttribute("inputParas");
		//动态生成localUrl
		String localUrl = "cosim://";
		
		String toolNodeId = request.getParameter("toolNodeId");
		localUrl+="toolNodeId="+toolNodeId;
		//先不管
		String invokeParas = request.getParameter("invokeParas");
		if(invokeParas!=null){
			localUrl+="&invokeParas="+invokeParas;
		}else{
			localUrl+="&invokeParas={}";
		}
		
		//接上输入参数
		localUrl+=inputParas+"&inputFileId="+request.getParameter("inputFileId")+"&type=1";
		//System.out.println(localUrl);
		localUrl = localUrl.replaceAll("null", "");
		System.out.println(localUrl);
//		System.out.println("???");
		//response.sendRedirect(localUrl);
		response.getWriter().write("<script>window.open('"+localUrl+"');window.close();</script>");
		response.getWriter().flush();
	}
	
//	@RequestMapping("list")
//	@Action(description="转到选择该用户可使用的软件的页面")
//	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		//动态生成localUrl
//		//String localUrl = "cosim://";
//		//这时候还没得到
//		//String soft_config_id = request.getParameter("soft_config_id");
//		//先不管
//		//String invokeParas = request.getParameter("invokeParas");
//		
//		//localUrl+="soft_config_id="+soft_config_id+"&invokeParas="+invokeParas;
//		
//		String inputParas = "";
//		//解析输入参数
//		Map<String, String[]> parasMap = request.getParameterMap();
//		Set<String> parasSet = parasMap.keySet();
//		Iterator<String> iterator = parasSet.iterator();
//		while (iterator.hasNext()) {
//			String key = iterator.next();
//			String[] values = parasMap.get(key);
//			if(values!=null&&values.length>0){
//				if(key.matches(".*:.*")){
//					String[] strs = key.split(":", 0);
//					inputParas+="&"+strs[strs.length-1]+"=";
//					inputParas+=values[0];
//				}
//			}
//		}
//		//将inputParas放入session
//		request.getSession().setAttribute("inputParas", inputParas);
//		List<SoftConfig> list=softConfigService.getAll(new QueryFilter(request,"softConfigItem"));
//		ModelAndView mv=this.getAutoView().addObject("softConfigList",list);
//		//System.out.println("soft list~~~~~~~~"+inputParas);
//		return mv;
//	}

}
