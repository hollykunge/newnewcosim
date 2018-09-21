<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<f:link href="Aqua/css/bootstrap.css"></f:link>
<script type="text/javascript">
	function showXmlWindow(obj){
		var url="";
		if($(obj).text().trim()=='BPMN-XML'){
			url="${ctx}/platform/bpm/bpmDefinition/bpmnXml.ht?defId=${bpmDefinition.defId}";
		}else{
			url="${ctx}/platform/bpm/bpmDefinition/designXml.ht?defId=${bpmDefinition.defId}";
		}
		url=url.getNewUrl();
		window.open(url);
	}
</script>
<div class="panel-heading" style="background:#76B0EA">
	<div class="panel-title" style="color:#ffffff;font-weight:bold;font-size:15px;">
		流程定义管理->${bpmDefinition.subject }
		
	</div>
	</div>
	<div class="panel-body">
		
		<div class="toolBar">
			<div class="group"><a class="link xml-bpm"  onclick="showXmlWindow(this);">BPMN-XML</a></div>
			<div class="l-bar-separator"></div>
			<div class="group"><a class="link xml-design" onclick="showXmlWindow(this);">DESIGN-XML</a></div>
			<div class="l-bar-separator"></div>
			<div class="group"><a class="link back" href="${ctx}/platform/bpm/bpmDefinition/list.ht">返回</a></div>
		</div>	
	</div>
