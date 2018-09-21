package com.hotent.platform.controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.ResourcesUrl;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.ResourcesUrlService;

/**
 * 对象功能:资源URL 控制器类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2011-12-05 17:00:58
 */
@Controller
@RequestMapping("/platform/system/resourcesUrl/")
public class ResourcesUrlController extends BaseController
{
	@Resource
	private ResourcesUrlService resourcesUrlService;
	@Resource
	private ResourcesService resourcesService;
	
	@RequestMapping("edit")
	@Action(description="编辑资源URL")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long resId=RequestUtil.getLong(request,"resId",0);
		String returnUrl=RequestUtil.getString(request, "returnUrl", RequestUtil.getPrePage(request));
		List<ResourcesUrl> resourcesUrlList= resourcesUrlService.getByResId(resId);
		Resources resources=resourcesService.getById(resId);
		return getAutoView()
		.addObject("resourcesUrlList",resourcesUrlList)
		.addObject("returnUrl", returnUrl)
		.addObject("resources", resources);
	}
	
	

	/**
	 * 添加或更新系统岗位表。
	 * @param request
	 * @param response
	 * @param position 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("upd")
	@Action(description="添加或更新资源URL")
	public void upd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintWriter out=response.getWriter();
		long resId=RequestUtil.getLong(request, "resId",0);
		String[] name=request.getParameterValues("name");
		String[] url=request.getParameterValues("url");     
	//	String[] parameter=request.getParameterValues("parameter");
		if(resId!=0){
			List<ResourcesUrl> resourcesUrlList=new ArrayList<ResourcesUrl>();	
			if(name!=null){
				for(int i=0;i<name.length;i++){
					ResourcesUrl resUrl=new ResourcesUrl();
					resUrl.setResUrlId(UniqueIdUtil.genId());
					resUrl.setResId(resId);
					String nameTemp=name[i];
					if(nameTemp!=null)nameTemp=nameTemp.trim();
					resUrl.setName(nameTemp);
					String urlTemp=url[i];
					if(urlTemp!=null)urlTemp=urlTemp.trim();
					resUrl.setUrl(urlTemp);
				
					resourcesUrlList.add(resUrl);
				}
				
			}
			resourcesUrlService.update(resId,resourcesUrlList);
			String defaultUrl=RequestUtil.getSecureString(request, "defaultUrl","");
			if(defaultUrl!=null&&!defaultUrl.equals("")){
				Resources res=resourcesService.getById(resId);
				res.setDefaultUrl(defaultUrl.trim());
				resourcesService.update(res);
			}
			
		}
		ResultMessage message=new ResultMessage(ResultMessage.Success,"编辑资源URL成功");
		out.print(message.toString());
	}

}
