package com.casic.cloud.controller.tool;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.tool.Cloud_tool_user_parasMapper;
import com.casic.cloud.model.tool.ToolUser;
import com.casic.cloud.model.tool.input.Cloud_tool_user_inputFile;
import com.casic.cloud.model.tool.output.Cloud_tool_user_outputFile;
import com.casic.cloud.service.tool.Cloud_tool_user_parasMapperService;
import com.casic.cloud.service.tool.ToolUserService;
import com.casic.cloud.service.tool.input.Cloud_tool_user_inputFileService;
import com.casic.cloud.service.tool.output.Cloud_tool_user_outputFileService;
import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;

@Controller
@RequestMapping("/cloud/toolUser/")
public class ToolUserController extends BaseController {
	@Resource
	private ToolUserService toolUserService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private Cloud_tool_user_inputFileService inputFileService;
	@Resource
	private Cloud_tool_user_outputFileService outputFileService;
	@Resource
	private Cloud_tool_user_parasMapperService parasMapperService;

	/**
	 * 取得已发布分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看映射分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long userId = RequestUtil.getLong(request, "userId");
		QueryFilter queryFilter = new QueryFilter(request, "toolUserItem", true);
		List<ToolUser> list = toolUserService.getAll(queryFilter);
		mv.addObject("toolUserList", list).addObject("userId", userId);

		return mv;
	}

	/**
	 * 删除映射公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除映射")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "toolUserId");
			toolUserService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除工具用户映射成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败"
					+ ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping(value = { "add" })
	@Action(description = "添加或更新映射")
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long userId = RequestUtil.getLong(request, "userId");
		Long toolIds[] = RequestUtil.getLongAryByStr(request, "toolIds");
		toolUserService.add(userId, toolIds);
		response.sendRedirect(RequestUtil.getPrePage(request));
	}

	@RequestMapping(value = { "chooseToolUsers" })
	public ModelAndView chooseTools(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		return mv;
	}

	@RequestMapping("toolUserList")
	@Action(description = "查看工具分页列表")
	public ModelAndView toolList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "toolItem");
		queryFilter.addFilter("userId", ContextUtil.getCurrentUserId());
		List<ToolUser> list = toolUserService.getAll(queryFilter);
		ModelAndView mv = this.getAutoView().addObject("toolList", list);
		return mv;
	}

	/**
	 * 编辑cloud_tool_user
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑cloud_tool_user")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long toolUserId = RequestUtil.getLong(request, "toolUserId");
		String returnUrl = RequestUtil.getPrePage(request);
		ToolUser toolUser = toolUserService.getById(toolUserId);

		return getAutoView().addObject("toolUser", toolUser).addObject(
				"returnUrl", returnUrl);
	}

	/**
	 * 添加或更新cloud_tool_user。
	 * 
	 * @param request
	 * @param response
	 * @param toolUser
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新cloud_tool_user")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resultMsg = null;
		ToolUser toolUser = getFormObject(request);
		try {
			if (toolUser.getToolUserId() == null
					|| toolUser.getToolUserId() == 0) {
				toolUser.setToolUserId(UniqueIdUtil.genId());
				toolUserService.add(toolUser);
				resultMsg = getText("record.added", "cloud_tool_user");
			} else {
				toolUserService.update(toolUser);
				resultMsg = getText("record.updated", "cloud_tool_user");
			}
			writeResultMessage(response.getWriter(), resultMsg,
					ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得 ToolUser 实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ToolUser getFormObject(HttpServletRequest request)
			throws Exception {

		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher((new String[] { "yyyy-MM-dd" })));

		String json = RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);

		ToolUser toolUser = (ToolUser) JSONObject.toBean(obj, ToolUser.class);

		return toolUser;
	}

	/**
	 * 取得用户表分页列表 根据企业过滤用户
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("shareUserList")
	@Action(description = "查看用户表分页列表", operateType = "用户操作")
	public ModelAndView shareUserList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long userId = RequestUtil.getLong(request, "userId");
		ISysOrg sysOrg = ContextUtil.getCurrentOrgFromSession();
		QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
		queryFilter.addFilter("orgSn", sysOrg.getSn());
		List<ISysUser> list = sysUserService.getUserByQuery(queryFilter);

		ModelAndView mv = this
				.getAutoView()
				.addObject("sysUserList", list)
				.addObject("userId", userId)
				.addObject("cloudToolUserId",
						RequestUtil.getLong(request, "cloudToolUserId"));

		return mv;
	}

	/**
	 * 在后台复制工具配置给另一个用户的方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("shareTool")
	@Action(description = "查看用户表分页列表", operateType = "用户操作")
	public ModelAndView shareTool(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// the user who wants the tool
		Long userId = RequestUtil.getLong(request, "userId");
		Long cloudToolUserId = RequestUtil.getLong(request, "cloudToolUserId");
		ToolUser toolUser = toolUserService.getById(cloudToolUserId);
		String message = "复制成功";
		// cloud_tool_user
		ToolUser newToolUser = new ToolUser();
		newToolUser.setToolUserId(UniqueIdUtil.genId());
		newToolUser.setUserId(userId);
		newToolUser.setToolId(toolUser.getToolId());
		newToolUser.setMyToolAddress(toolUser.getMyToolAddress());
		try {
			toolUserService.add(newToolUser);
			// input
			QueryFilter inputFilter = new QueryFilter(request);
			inputFilter.addFilter("cloudToolUserId", cloudToolUserId);
			List<Cloud_tool_user_inputFile> inputFiles = inputFileService
					.getAll(inputFilter);
			for (Cloud_tool_user_inputFile inputFile : inputFiles) {
				Cloud_tool_user_inputFile newInputFile = new Cloud_tool_user_inputFile();
				newInputFile.setId(UniqueIdUtil.genId());
				newInputFile.setCloudToolUserId(newToolUser.getToolUserId());
				newInputFile.setGenerateStrategy(inputFile.getGenerateStrategy());
				newInputFile.setInputaddress(inputFile.getInputaddress());
				newInputFile.setType(inputFile.getType());
				try {
					inputFileService.add(newInputFile);
				} catch (Exception e) {
					e.printStackTrace();
					message = "复制用户工具输入文件表失败";
				}
				
			}
			// output
			QueryFilter outputFilter = new QueryFilter(request);
			outputFilter.addFilter("cloudToolUserId", cloudToolUserId);
			List<Cloud_tool_user_outputFile> outputFiles = outputFileService
					.getAll(outputFilter);
			for (Cloud_tool_user_outputFile outputFile : outputFiles) {
				Cloud_tool_user_outputFile newOutputFile = new Cloud_tool_user_outputFile();
				newOutputFile.setId(UniqueIdUtil.genId());
				newOutputFile.setCloudToolUserId(newToolUser.getToolUserId());
				newOutputFile.setOutputaddress(outputFile.getOutputaddress());
				try {
					outputFileService.add(newOutputFile);
				} catch (Exception e) {
					e.printStackTrace();
					message = "复制用户工具输出文件表失败";
				}
				
			}
			// paras
			QueryFilter paraFilter = new QueryFilter(request);
			paraFilter.addFilter("cloudToolUserId", cloudToolUserId);
			List<Cloud_tool_user_parasMapper> parasMappers = parasMapperService
					.getAll(paraFilter);
			for (Cloud_tool_user_parasMapper parasMapper : parasMappers) {
				Cloud_tool_user_parasMapper newParaMapper = new Cloud_tool_user_parasMapper();
				newParaMapper.setId(UniqueIdUtil.genId());
				newParaMapper.setCloudToolUserId(newToolUser.getToolUserId());
				newParaMapper.setName(parasMapper.getName());
				newParaMapper.setChineseMapperName(parasMapper
						.getChineseMapperName());
				newParaMapper.setFormMapperName(parasMapper.getFormMapperName());
				try {
					parasMapperService.add(newParaMapper);
				} catch (Exception e) {
					e.printStackTrace();
					message = "复制用户工具参数表失败";
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "复制用户工具表失败";
		}

		
		

		response.getWriter().write(message);
		return null;
	}

}
