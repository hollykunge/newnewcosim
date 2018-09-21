package com.hotent.core.bpm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.platform.dao.bpm.BpmDao;
import com.hotent.platform.dao.bpm.BpmDefinitionDao;
import com.hotent.platform.model.bpm.BpmDefinition;

/**
 * 流程定义节点Cache,系统维护两份Cache,一份是activiti本身维护的Cache，主要是用于流程跳转用，由Activiti本身引擎使用，
 * 而另一套流程定义的Cache则由 本系统维护，用于取得每个节点的前后连接及父子关系等 。
 * 
 * @author csx
 * 
 */
public class NodeCache {
	private static final Log logger = LogFactory.getLog(NodeCache.class);
	/**
	 * 键为流程定义ID。 值为流程节点map对象。 map 的键为 节点id，值为节点对象。
	 */
	private static Map<String, Map<String, FlowNode>> actNodesMap = new HashMap<String, Map<String, FlowNode>>();

	public void add(String actDefId, Map<String, FlowNode> map) {
		actNodesMap.put(actDefId, map);
	}

	/**
	 * 根据流程定义id获取流程的节点。
	 * 
	 * <pre>
	 * 	1.首先去缓存中查找如果找到直接返回该流程的节点集合。
	 *  2.不存在直接在流程中查询xml并解析成节点结合，并放到缓存中进行返回。
	 * </pre>
	 * 
	 * @param actDefId
	 * @return
	 */
	public static synchronized Map<String, FlowNode> getByActDefId(
			String actDefId) {
		if (actNodesMap.containsKey(actDefId)) {
			return actNodesMap.get(actDefId);
		} else {
			Map<String, FlowNode> map = readFromXml(actDefId);
			actNodesMap.put(actDefId, map);
			return map;
		}
	}

	/**
	 * 获取流程的起始节点。
	 * 
	 * @param actDefId
	 *            流程定义ID。
	 * @return 返回流程的节点。
	 */
	public static FlowNode getStartNode(String actDefId) {
		// 确保流程取得流程定义节点。
		getByActDefId(actDefId);
		Map<String, FlowNode> nodeMap = actNodesMap.get(actDefId);
		return getStartNode(nodeMap);
	}

	/**
	 * 获取任务节点的起始节点。
	 * 
	 * @param nodeMap
	 * @return
	 */
	public static FlowNode getStartNode(Map<String, FlowNode> nodeMap) {
		for (FlowNode flowNode : nodeMap.values()) {
			if ("startEvent".equals(flowNode.getNodeType())
					&& flowNode.getParentNode() == null) {
				return flowNode;
			}
		}
		return null;
	}

	/**
	 * 获取流程的结束节点。
	 * 
	 * @param actDefId
	 *            流程定义ID。
	 * @return 返回流程的节点。
	 */
	public static List<FlowNode> getEndNode(String actDefId) {
		// 确保流程取得流程定义节点。
		getByActDefId(actDefId);
		Map<String, FlowNode> nodeMap = actNodesMap.get(actDefId);
		return getEndNode(nodeMap);
	}

	/**
	 * 获取流程的结束节点
	 * 
	 * @param nodeMap
	 * @return
	 */
	private static List<FlowNode> getEndNode(Map<String, FlowNode> nodeMap) {
		List<FlowNode> flowNodeList = new ArrayList<FlowNode>();
		for (FlowNode flowNode : nodeMap.values()) {
			if ("endEvent".equals(flowNode.getNodeType())
					&& BeanUtils.isEmpty(flowNode.getNextFlowNodes())) {
				flowNodeList.add(flowNode);
			}
		}
		return flowNodeList;
	}

	/**
	 * 根据流程定义id获取流程是否有外部子流程。
	 * 
	 * @param actDefId
	 *            流程定义ID。
	 * @return
	 */
	public static boolean hasExternalSubprocess(String actDefId) {
		getByActDefId(actDefId);
		Map<String, FlowNode> nodeMap = actNodesMap.get(actDefId);
		for (FlowNode flowNode : nodeMap.values()) {
			if ("callActivity".equals(flowNode.getNodeType())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据流程定义id删除缓存数据。
	 * 
	 * @param actDefId
	 */
	public static void clear(String actDefId) {
		actNodesMap.remove(actDefId);
	}

	/**
	 * 根据流程定义返回流程节点关系。
	 * 
	 * @param actDefId
	 * @return
	 */
	private static Map<String, FlowNode> readFromXml(String actDefId) {
		BpmDao dao = (BpmDao) AppUtil.getBean(BpmDao.class);
		BpmDefinitionDao bpmDefinitionDao = (BpmDefinitionDao) AppUtil
				.getBean(BpmDefinitionDao.class);
		BpmDefinition bpmDefinition = bpmDefinitionDao.getByActDefId(actDefId);
		Long deployId = bpmDefinition.getActDeployId();
		String xml = dao.getDefXmlByDeployId(deployId.toString());
		return parseXml(xml);
	}

	/**
	 * 根据流程键获取流程的xml数据。
	 * 
	 * @param actDefkey
	 * @return
	 */
	private static String getXmlByDefKey(String actDefkey) {
		BpmDao dao = (BpmDao) AppUtil.getBean(BpmDao.class);
		BpmDefinitionDao bpmDefinitionDao = (BpmDefinitionDao) AppUtil
				.getBean(BpmDefinitionDao.class);
		BpmDefinition bpmDefinition = bpmDefinitionDao
				.getByActDefKeyIsMain(actDefkey);
		Long deployId = bpmDefinition.getActDeployId();
		String xml = dao.getDefXmlByDeployId(deployId.toString());
		return xml;
	}

	/**
	 * 解析流程中的所有节点。
	 * 
	 * @param xml
	 * @return
	 */
	private static Map<String, FlowNode> parseXml(String xml) {
		xml = xml.replace(
				"xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document document = Dom4jUtil.loadXml(xml);
		Element el = document.getRootElement();
		Map<String, FlowNode> map = new HashMap<String, FlowNode>();
		Element processEl = (Element) el.selectSingleNode("./process");
		Iterator<Element> its = processEl.elementIterator();

		while (its.hasNext()) {
			Element nodeEl = its.next();
			String nodeType = nodeEl.getName();

			String nodeId = nodeEl.attributeValue("id");
			String nodeName = nodeEl.attributeValue("name");

			// 是否多实例节点
			boolean isMultiInstance = nodeEl
					.selectSingleNode("./multiInstanceLoopCharacteristics") == null ? false
					: true;
			// 节点类型
			// 开始节点，用户任务，并行网关，条件网关，分支网关，结束节点，自动任务节点。
			if ("startEvent".equals(nodeType) || "userTask".equals(nodeType)
					|| "parallelGateway".equals(nodeType)
					|| "inclusiveGateway".equals(nodeType)
					|| "exclusiveGateway".equals(nodeType)
					|| "endEvent".equals(nodeType)
					|| "serviceTask".equals(nodeType)) {
				FlowNode flowNode = new FlowNode(nodeId, nodeName, nodeType,
						isMultiInstance);
				map.put(nodeId, flowNode);
			}
			// 子流程
			else if ("subProcess".equals(nodeType)) {
				// 产生子流程的映射
				FlowNode subProcessNode = new FlowNode(nodeId, nodeName,
						nodeType, new ArrayList<FlowNode>(), isMultiInstance);
				// 将子流程节点放到map对象中。
				map.put(nodeId, subProcessNode);
				// 遍历子流程的所有节点。
				List<Element> subElements = nodeEl.elements();
				for (Element subEl : subElements) {
					String subNodeType = subEl.getName();
					if ("startEvent".equals(subNodeType)
							|| "userTask".equals(subNodeType)
							|| "parallelGateway".equals(subNodeType)
							|| "inclusiveGateway".equals(subNodeType)
							|| "exclusiveGateway".equals(subNodeType)
							|| "endEvent".equals(subNodeType)
							|| "serviceTask".equals(subNodeType)) {
						String subNodeName = subEl.attributeValue("name");
						String subNodeId = subEl.attributeValue("id");
						FlowNode flowNode = new FlowNode(subNodeId,
								subNodeName, subNodeType, false, subProcessNode);
						subProcessNode.getSubFlowNodes().add(flowNode);
						map.put(subNodeId, flowNode);
					}
				}
			}
			// 节点为外部子流程的情况。
			else if ("callActivity".equals(nodeType)) {
				String flowKey = nodeEl.attributeValue("calledElement");
				// 获取子流程的xml数据。
				String subProcessXml = getXmlByDefKey(flowKey);
				// 解析子流程的xml数据。
				Map<String, FlowNode> subChildNodes = parseXml(subProcessXml);
				FlowNode flowNode = new FlowNode(nodeId, nodeName, nodeType,
						isMultiInstance);
				flowNode.setAttribute("subFlowKey", flowKey);
				map.put(nodeId, flowNode);
				// 设置子流程的节点。
				flowNode.setSubProcessNodes(subChildNodes);
			}
		}

		// 遍历流程中节点之间的关联。
		List<Node> seqFlowList = document
				.selectNodes("/definitions/process//sequenceFlow");
		for (int i = 0; i < seqFlowList.size(); i++) {
			Element seqFlow = (Element) seqFlowList.get(i);
			String sourceRef = seqFlow.attributeValue("sourceRef");
			String targetRef = seqFlow.attributeValue("targetRef");

			FlowNode sourceFlowNode = map.get(sourceRef);
			FlowNode targetFlowNode = map.get(targetRef);
			if (sourceFlowNode != null && targetFlowNode != null) {
				logger.debug("sourceRef:" + sourceFlowNode.getNodeName()
						+ " targetRef:" + targetFlowNode.getNodeName());
				if (targetFlowNode.getParentNode() != null) {
					logger.debug("parentNode:"
							+ targetFlowNode.getParentNode().getNodeName());
				}
				// 设置子流程中的第一个任务节点
				if ("startEvent".equals(sourceFlowNode.getNodeType())
						&& sourceFlowNode.getParentNode() != null) {
					sourceFlowNode.setFirstNode(true);
					sourceFlowNode.getParentNode().setSubFirstNode(
							sourceFlowNode);
					// 当子流程为多实例时，也认为子流程内的其第一个任务节点也是为多实例
					if (targetFlowNode.getParentNode().getIsMultiInstance()) {
						targetFlowNode.setIsMultiInstance(true);
					}
				}
				sourceFlowNode.getNextFlowNodes().add(targetFlowNode);
				targetFlowNode.getPreFlowNodes().add(sourceFlowNode);
			}
		}
		return map;
	}

	/**
	 * 解析XMl获得 流程Key
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> parseXmlBySubKey(String xml) {
		xml = xml.replace(
				"xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document document = Dom4jUtil.loadXml(xml);
		Element el = document.getRootElement();
		Element processEl = (Element) el.selectSingleNode("./process");
		if (BeanUtils.isEmpty(processEl))
			return null;
		Iterator<Element> its = processEl.elementIterator();
		Set<String> keySet = new HashSet<String>();
		while (its.hasNext()) {
			Element nodeEl = its.next();
			String nodeType = nodeEl.getName();
			// 节点为外部子流程的情况。
			if ("callActivity".equals(nodeType)) {
				String flowKey = nodeEl.attributeValue("calledElement");
				keySet.add(flowKey);
				// 获取子流程的xml数据。
				String subProcessXml = getXmlByDefKey(flowKey);
				// 解析子流程的xml数据。
				Set<String> kSet = parseXmlBySubKey(subProcessXml);
				for (String key : kSet) {
					keySet.add(key);
				}
			}
		}
		return keySet;
	}

}
