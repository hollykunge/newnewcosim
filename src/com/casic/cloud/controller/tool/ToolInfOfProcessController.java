package com.casic.cloud.controller.tool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.tool.Cloud_tool_user_file;
import com.casic.cloud.model.tool.Tool;
import com.casic.cloud.model.tool.ToolBpmNode;
import com.casic.cloud.model.tool.ToolUser;
import com.casic.cloud.model.tool.output.Cloud_tool_user_outputFile;
import com.casic.cloud.model.tool.toolDataOfProcess.ToolData;
import com.casic.cloud.model.tool.toolDataOfProcess.ToolDataOfNode;
import com.casic.cloud.model.tool.toolDataOfProcess.ToolDataOfProcess;
import com.casic.cloud.service.tool.ToolBpmNodeService;
import com.casic.cloud.service.tool.ToolService;
import com.casic.cloud.service.tool.ToolUserService;
import com.casic.cloud.service.tool.output.Cloud_tool_user_outputFileService;
import com.casic.cloud.toolEnvironment.util.FileUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.bpm.graph.ShapeMeta;
import com.hotent.core.bpm.model.FlowNode;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.ProcessRunService;

@Controller
@RequestMapping("/cloud/toolInfOfProcess/")
public class ToolInfOfProcessController extends BaseController {
	private Log logger = LogFactory.getLog(ToolInfOfProcessController.class);
	@Resource
	private ToolService toolService;
	@Resource
	private ToolUserService toolUserService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource 
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private ToolBpmNodeService toolBpmNodeService;
	@Resource
	private Cloud_tool_user_outputFileService cloud_tool_user_outputFileService;
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
	@Action(description = "查看工具分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "toolItem");
		List<Tool> list = toolService.getAll(queryFilter);
		
		ModelAndView mv = this.getAutoView().addObject("toolList", list);
		return mv;
	}

	/**
	 * 取得已发布分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toolList")
	@Action(description = "查看工具分页列表")
	public ModelAndView toolList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "toolItem");
		List<Tool> list = toolService.getAll(queryFilter);
		ModelAndView mv = this.getAutoView().addObject("toolList", list);
		return mv;
	}

	/**
	 * 删除工具公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除工具")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			toolService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除工具成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 取得工具明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看工具明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long runId = RequestUtil.getLong(request, "runId");
		ProcessRun processRun = processRunService.getById(runId);
		//流程
		BpmDefinition bpmDefinition = bpmDefinitionService.getById(processRun.getDefId());
		
//		QueryFilter queryFilter = new QueryFilter(request,"bpmNodeSetItem");
//		queryFilter.addFilter("defId", processRun.getDefId());
		//任务节点
		List<BpmNodeSet> nodeSets = bpmNodeSetService.getByDefId(processRun.getDefId());
		ToolDataOfProcess toolDataOfProcess = new ToolDataOfProcess();
		List<ToolDataOfNode> toolDataOfNodes = new ArrayList<ToolDataOfNode>();
		toolDataOfProcess.setBpmDefinition(bpmDefinition);
		toolDataOfProcess.setToolDataOfNodes(toolDataOfNodes);
		//节点的工具
		//int i = 1;
		for(BpmNodeSet nodeSet:nodeSets){
			QueryFilter queryFilter2 = new QueryFilter(request, "toolBpmNodeItem");
			queryFilter2.addFilter("setId", nodeSet.getSetId());
			List<ToolBpmNode> toolBpmNodes = toolBpmNodeService.getAll(queryFilter2);
			ToolDataOfNode toolDataOfNode = new ToolDataOfNode();
			toolDataOfNode.setBpmNodeSet(nodeSet);
			List<ToolData> toolDatas = new ArrayList<ToolData>();
			toolDataOfNode.setToolDatas(toolDatas);
			toolDataOfNodes.add(toolDataOfNode);
			//System.out.println("节点"+i+":");
			//i++;
			for(ToolBpmNode toolBpmNode:toolBpmNodes){
				//System.out.println(toolBpmNode.getToolName()+":"+toolBpmNode.getMyToolAddress());
				//工具的输出
//				QueryFilter queryFilter3 = new QueryFilter(request, "toolUserItem");
//				queryFilter3.addFilter("toolUserId", toolBpmNode.getToolUserId());
				
				ToolUser toolUser = toolUserService.getById(toolBpmNode.getToolUserId());
				Tool tool = toolService.getById(toolUser.getToolId());
				QueryFilter queryFilter3 = new QueryFilter(request);
				queryFilter3.addFilter("cloudToolUserId", toolUser.getToolUserId());
				List<Cloud_tool_user_outputFile> outputFiles = cloud_tool_user_outputFileService.getAll(queryFilter3);
				//set runId for outputfile
				for(Cloud_tool_user_outputFile outputFile:outputFiles){
					outputFile.setRunId(runId);
				}
				ToolData toolData = new ToolData();
				toolData.setCloud_tool_user_outputFiles(outputFiles);
				toolData.setTool(tool);
				toolData.setToolBpmNode(toolBpmNode);
				toolDatas.add(toolData);
				
			}
			
			
		}
		
		return getAutoView().addObject("toolDataOfProcess", toolDataOfProcess).addObject("runId", runId);
	}

	/**
	 * 查看误差曲线
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("errorCurve")
	@Action(description = "查看误差曲线")
	public ModelAndView errorCurve(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long outputFileId = RequestUtil.getLong(request, "outputFileId");
		long runId = RequestUtil.getLong(request, "runId");
		
		return getAutoView().addObject("outputFileId", outputFileId).addObject("runId", runId);

	}
	/**
	 * 查看误差曲线
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("curveImage")
	@Action(description = "查看误差曲线")
	public void curveImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long outputFileId = RequestUtil.getLong(request, "outputFileId");
		Cloud_tool_user_outputFile outputFile = cloud_tool_user_outputFileService.getById(outputFileId);
		String outputFilePath = outputFile.getOutputaddress();
		String runId = RequestUtil.getString(request, "runId");
		File instanceFile = FileUtil.getExistInstanceStartWithRunId(runId, "", new File(outputFilePath), true);
		if(instanceFile!=null){
			outputFilePath = instanceFile.getAbsolutePath();
		}
		//generate image
		if(outputFilePath.endsWith("error.dat")){
			Map<String, String> lgderMap = FileUtil.generateErrorData(outputFilePath, "step=", "lgder=");
			Map<String, String> lgdmaxMap = FileUtil.generateErrorData(outputFilePath, "step=", "lgdmax=");
			XYSeries lgderSeries = new XYSeries("lgder");
			XYSeries lgdmaxSeries = new XYSeries("lgdmax");
			
			//generate dataset for lgder
			Set<String> lgderSet = lgderMap.keySet();
			Iterator<String> lgderIterator = lgderSet.iterator();
			while(lgderIterator.hasNext()){
				String step = lgderIterator.next();
				String value = lgderMap.get(step);
				lgderSeries.add(Double.valueOf(step), Double.valueOf(value));
			}
			//generate dataset for lgdmax
			Set<String> lgdmaxSet = lgdmaxMap.keySet();
			Iterator<String> lgdmaxIterator = lgdmaxSet.iterator();
			while(lgdmaxIterator.hasNext()){
				String step = lgdmaxIterator.next();
				String value = lgdmaxMap.get(step);
				lgdmaxSeries.add(Double.valueOf(step), Double.valueOf(value));
			}
			XYSeriesCollection dataset =new XYSeriesCollection();
			dataset.addSeries(lgderSeries);
			dataset.addSeries(lgdmaxSeries);
			JFreeChart chart = ChartFactory.createXYLineChart("error curve", "step", "error", dataset);
			BufferedImage image = chart.createBufferedImage(600, 400);
			try {
				ImageIO.write(image, "gif", response.getOutputStream());// 输出JPEG格式图片到页面
				response.getOutputStream().flush();// 刷新输出流
				response.getOutputStream().close();// 关闭输出流
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		
		
	}

	/**
	 * 编辑新闻公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑工具")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		Tool tool = toolService.getById(id);
		return getAutoView().addObject("tool", tool).addObject("returnUrl", returnUrl);
	}

	/**
	 * 添加或更新工具。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新工具")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Tool tool = getFormObject(request);
		try {	

			// 当前更新时间
			tool.setUpdatetime(new Date());
			if (tool.getId() == null) {
				tool.setId(UniqueIdUtil.genId());
				tool.setCreatetime(new Date());
				toolService.add(tool);
				resultMsg = getText("record.added", "工具");
			} else {
				toolService.update(tool);
				resultMsg = getText("record.updated", "工具");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得 tool实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Tool getFormObject(HttpServletRequest request) throws Exception {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyyMMddHHmmss" })));
		String json = RequestUtil.getString(request, "json");
		logger.debug("JSON=" + json);
		JSONObject obj = JSONObject.fromObject(json);
		Tool tool = (Tool) JSONObject.toBean(obj, Tool.class);
		return tool;
	}

	@RequestMapping(value = { "chooseTools" })
	public ModelAndView chooseTools(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		return mv;
	}
}
