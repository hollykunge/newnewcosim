package com.hotent.core.bpm.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.dao.bpm.BpmProStatusDao;
import com.hotent.platform.model.bpm.BpmProStatus;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.IFlowStatus;
import com.hotent.platform.service.bpm.ProcessRunService;

/**
 * 产生流程图的servlet。<br>
 * 
 * <pre>
 * 要求传入名称为deployId参数。
 * 在web.xml中配置：
 * &lt;servlet>
 *       &lt;servlet-name>bpmImageServlet&lt;/servlet-name>
 *       &lt;servlet-class>com.hotent.core.bpm.servlet.BpmImageServlet&lt;/servlet-class>
 *    &lt;/servlet>
 * &lt;servlet-mapping>
 *   	&lt;servlet-name>bpmImageServlet&lt;/servlet-name>
 *   	&lt;url-pattern>/bpmImage&lt;/url-pattern>
 *   &lt;/servlet-mapping>
 *   
 *   页面使用方法如下：
 *   &lt;img src="${ctx}/bpmImage?deployId=**" />
 *   &lt;img src="${ctx}/bpmImage?taskId=**" />
 *   &lt;img src="${ctx}/bpmImage?processInstanceId=**" />
 *   &lt;img src="${ctx}/bpmImage?definitionId=**" />
 * </pre>
 * 
 * @author csx.
 * 
 */
@SuppressWarnings("serial")
public class BpmImageServlet extends HttpServlet {
	private BpmService bpmService = (BpmService) AppUtil.getBean("bpmService");
	private IFlowStatus iFlowStatus = (IFlowStatus) AppUtil
			.getBean("iFlowStatus");

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String deployId = RequestUtil.getString(request, "deployId");
		String taskId = RequestUtil.getString(request, "taskId");
		String processInstanceId = RequestUtil.getString(request,"processInstanceId");
		String definitionId = RequestUtil.getString(request, "definitionId");
		String runId = request.getParameter("runId");
		// 生成图片
		InputStream is = null;
		//根据流程deployId产生图片
		if (StringUtil.isNotEmpty(deployId)) {
			String bpmnXml = bpmService.getDefXmlByDeployId(deployId);
			is = ProcessDiagramGenerator.generatePngDiagram(bpmnXml);
		}
		//流程定义id
		else if (StringUtils.isNotEmpty(definitionId)) {
			String bpmnXml = bpmService.getDefXmlByProcessDefinitionId(definitionId);
			is = ProcessDiagramGenerator.generatePngDiagram(bpmnXml);

		}
		//流程任务id
		else if (StringUtil.isNotEmpty(taskId)) {
			String bpmnXml = bpmService.getDefXmlByProcessTaskId(taskId);
			TaskEntity taskEntity = bpmService.getTask(taskId);
			Map<String,String> highLightList = iFlowStatus.getStatusByInstanceId(new Long( taskEntity.getProcessInstanceId()));
			is = ProcessDiagramGenerator.generateDiagram(bpmnXml,highLightList, "png");
		}
		//流程实例ID
		else if (StringUtils.isNotEmpty(processInstanceId)) {
			String bpmnXml = bpmService.getDefXmlByProcessProcessInanceId(processInstanceId);
			if (bpmnXml == null) {
				ProcessRunService processRunService = (ProcessRunService) AppUtil.getBean(ProcessRunService.class);
				ProcessRun processRun = processRunService.getByActInstanceId(processInstanceId);
				bpmnXml = bpmService.getDefXmlByDeployId(processRun.getActDefId());
			}
			///raise update by raise -B///
			//List<BpmProStatus> highLightList = bpmProStatusDao.getByActInstanceId(processInstanceId);
//			is = ProcessDiagramGenerator.generateDiagram(bpmnXml,highLightList, "png");
			IFlowStatus flowStatus=(IFlowStatus) AppUtil.getBean(IFlowStatus.class);
			Map<String,String>  highLightMap = flowStatus.getStatusByInstanceId(Long.parseLong(processInstanceId));
			is = ProcessDiagramGenerator.generateDiagram(bpmnXml,highLightMap, "png");
			///raise update by raise -E///
		}
		//流程运行id
		else if (StringUtils.isNotEmpty(runId)) {
			ProcessRunService processRunService = (ProcessRunService) AppUtil.getBean(ProcessRunService.class);
			ProcessRun processRun = processRunService.getById(new Long(runId));
			processInstanceId=processRun.getActInstId();
			String bpmnXml = bpmService.getDefXmlByProcessProcessInanceId(processRun.getActInstId());
			if (bpmnXml == null) {
				bpmnXml = bpmService.getDefXmlByDeployId(processRun.getActDefId());
			}
			///raise update by raise -B///
			//List<BpmProStatus> highLightList = bpmProStatusDao.getByActInstanceId(processInstanceId);
//			is = ProcessDiagramGenerator.generateDiagram(bpmnXml,highLightList, "png");
			IFlowStatus flowStatus=(IFlowStatus) AppUtil.getBean(IFlowStatus.class);
			Map<String,String>  highLightMap = flowStatus.getStatusByInstanceId(Long.parseLong(processInstanceId));
			is = ProcessDiagramGenerator.generateDiagram(bpmnXml,highLightMap, "png");
			///raise update by raise -E///
		}

		if (is != null) {
			response.setContentType("image/png");
			OutputStream out = response.getOutputStream();

			try {
				byte[] bs = new byte[1024];
				int n = 0;
				while ((n = is.read(bs)) != -1) {
					out.write(bs, 0, n);
				}
				out.flush();
			} catch (Exception ex) {
			} finally {
				is.close();
				out.close();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private List<ActivityImpl> getPoolActivities(String deployId) {
		List<ActivityImpl> activities = new ArrayList<ActivityImpl>();
		String defXml = bpmService.getDefXmlByDeployId(deployId);
		defXml = defXml.replace(
				"xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document document = Dom4jUtil.loadXml(defXml);
		Element root = document.getRootElement();
		List<Element> participants = root
				.selectNodes("//collaboration/participant");
		for (Element participant : participants) {
			String participantId = participant.attributeValue("id");
			String participantRef = participant.attributeValue("processRef");
			Element shap = (Element) root
					.selectSingleNode("//bpmndi:BPMNShape[@bpmnElement='"
							+ participantId + "']");
			Element pool = (Element) root.selectSingleNode("//process[@id='"
					+ participantRef + "']");
			Element bounds = (Element) shap.selectSingleNode("omgdc:Bounds");
			ProcessDefinitionImpl processDefinition = new ProcessDefinitionImpl(
					participantRef);
			ActivityImpl activity = new ActivityImpl(participantRef,
					processDefinition);
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put("name", pool.attributeValue("name"));
			String isHorizontal = shap.attributeValue("isHorizontal");
			if (isHorizontal.equalsIgnoreCase("true")) {
				properties.put("type", "HPool");
			} else {
				properties.put("type", "VPool");
			}
			activity.setProperties(properties);
			int x = Integer.parseInt(bounds.attributeValue("x"));
			int y = Integer.parseInt(bounds.attributeValue("y"));
			int width = Integer.parseInt(bounds.attributeValue("width"));
			int height = Integer.parseInt(bounds.attributeValue("height"));
			activity.setX(x);
			activity.setY(y);
			activity.setWidth(width);
			activity.setHeight(height);
			activities.add(activity);
			// 泳道
			List lanes = pool.selectNodes("laneSet/lane");
			for (Object lane : lanes) {
				String laneId = ((Element) lane).attributeValue("id");
				ProcessDefinitionImpl laneProcessDefinition = new ProcessDefinitionImpl(
						laneId);
				ActivityImpl laneActivity = new ActivityImpl(laneId,
						laneProcessDefinition);
				Element laneShap = (Element) root
						.selectSingleNode("//bpmndi:BPMNShape[@bpmnElement='"
								+ laneId + "']");
				Element laneBounds = (Element) laneShap
						.selectSingleNode("omgdc:Bounds");
				if (isHorizontal.equalsIgnoreCase("true")) {
					properties.put("type", "HLane");
				} else {
					properties.put("type", "VLane");
				}
				laneActivity.setProperties(properties);
				int laneX = Integer.parseInt(laneBounds.attributeValue("x"));
				int laneY = Integer.parseInt(laneBounds.attributeValue("y"));
				int laneWidth = Integer.parseInt(laneBounds
						.attributeValue("width"));
				int laneHeight = Integer.parseInt(laneBounds
						.attributeValue("height"));
				laneActivity.setX(laneX);
				laneActivity.setY(laneY);
				laneActivity.setWidth(laneWidth);
				laneActivity.setHeight(laneHeight);
				activities.add(laneActivity);
			}
		}

		return activities;

	}
}
