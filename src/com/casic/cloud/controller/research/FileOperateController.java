package com.casic.cloud.controller.research;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
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


import com.casic.cloud.model.research.filecheck.ResFilecheck;
import com.casic.cloud.service.research.filecheck.ResFilecheckService;
import com.casic.cloud.model.research.filecheck.ResFilecheckDetail;
import com.casic.cloud.model.research.filecheck.ResFilecheckOpinion;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.service.system.SysFileService;
import com.hotent.platform.webservice.api.ProcessService;
/**
 *<pre>
 * 对象功能:文档操作 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-21 16:04:50
 *</pre>
 */
@Controller


@RequestMapping("/cloud/research/")
public class FileOperateController extends BaseController
{
	@Resource
	private SysFileService sysFileService;

	
	/**
	 * 添加或更新文档审查。
	 * @param request
	 * @param response
	 * @param resFilecheck 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openFile")
	public ModelAndView openFile(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		Long id = Long.valueOf(request.getParameter("fileId"));
		SysFile file = sysFileService.getById(id);
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		String path = basePath+file.getFilePath();
		ModelAndView mv=this.getAutoView().addObject("path",path)
				.addObject("id", id)
				.addObject("type", file.getExt());
		
		return mv;
	}
	@RequestMapping("openModel")
	public ModelAndView openModel(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		return openFile(request,response);
	}
	
}
