<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.hotent.platform.model.bpm.BpmDefRights"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>流程定义扩展管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<f:link href="Aqua/css/bootstrap.css"></f:link>
	<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerWindow.js" ></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ImportExportXmlUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/tabOperator.js" ></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowRightDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js" ></script>
	<script type="text/javascript">
	  
	
	// 导出流程定义
	function exportXml(){	
		var bpmDefIds = ImportExportXml.getChkValue('pk');
		if (bpmDefIds ==''){
			$.ligerMessageBox.warn('提示信息','还没有选择,请选择一项流程定义!');
			return ;
		}

		var url=__ctx + "/platform/bpm/bpmDefinition/export.ht?bpmDefIds="+bpmDefIds;
		ImportExportXml.open({url:url,height:400});
	}

	
	//导入流程定义
	function importXml(){
		var url=__ctx + "/platform/bpm/bpmDefinition/import.ht";
		ImportExportXml.open({url:url});
	}
	
	
	 
</script>
</head>
<body>
	<div class="panel panel-default" style="margin-bottom:0px;">
		<div class="panel-heading" style="background:#76B0EA">

			<div class="panel-title" style="color:#ffffff;font-weight:bold;font-size:15px;">流程定义管理</div>
		</div>
		<div class="panel-body">
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link search" id="btnSearch">查询</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link del"  action="del.ht">删除</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link flowDesign" onclick="window.open('design.ht')">新建流程</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link download"  href="javascript:void(0)" onclick="exportXml()">导出</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link upload"  href="javascript:void(0)" onclick="importXml()">导入</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link reload"  href="javascript:void(0)" onclick="window.location.reload()">刷新</a>
					</div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">标题:</span>
						<input type="text" name="Q_subject_SL"  class="inputText" style="width:13%;" value="${param['Q_subject_SL']}"/>

						<span class="label">创建时间:</span>
						<input type="text" name="Q_createtime_DL"  class="inputText date" style="width:13%;" value="${param['Q_createtime_DL']}"/>
						<span class="label">至</span>
						<input name="Q_endcreatetime_DG" class="inputText date" style="width:13%;" value="${param['Q_endcreatetime_DG']}"/>

					</div>
				</form>
			</div>
		</div>
		<div class="panel-footer">
			<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
			<display:table name="bpmDefinitionList" id="bpmDefinitionItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table table-bordered table-hover">
				<display:column title="${checkAll}" media="html" style="width:30px;" class="active">
					<input type="checkbox" class="pk" name="defId" value="${bpmDefinitionItem.defId}"></display:column>
				<display:column property="subject" title="标题" sortable="true" sortName="subject" class="active"></display:column>

				<display:column title="创建时间" sortable="true" sortName="createtime" class="active">
					<fmt:formatDate value="${bpmDefinitionItem.createtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="启用状态" sortable="true" sortName="disableStatus" class="active">
					<c:choose>
						<c:when test="${bpmDefinitionItem.disableStatus eq 1}">
							<span class="red">禁用</span>
						</c:when>
						<c:when test="${bpmDefinitionItem.disableStatus eq 0}">
							<span class="green">启用</span>
						</c:when>
						<c:otherwise>
							<span class="red">未设定</span>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column title="管理" media="html"  style="width:220px;" class="active">
					<f:a alias="delProcess" href="del.ht?defId=${bpmDefinitionItem.defId}" css="link del" >删除</f:a>
					<f:a alias="flex" href="design.ht?defId=${bpmDefinitionItem.defId}" target="_blank" css="link flowDesign" >流程修改</f:a>
					<c:if test="${bpmDefinitionItem.status==1}">
						<f:a alias="setBpm" href="detail.ht?defId=${bpmDefinitionItem.defId}" css="link setting" >设置</f:a>
						<f:a alias="startProcess" href="javascript:void(0)" onclick="FlowUtil.startFlow(${bpmDefinitionItem.defId},'${bpmDefinitionItem.actDefId}')" css="link run" >启动</f:a>
					</c:if>
					<f:a alias="grantProcess" href="javascript:FlowRightDialog(${bpmDefinitionItem.defId},0,'${bpmDefinitionItem.defKey}')" css="link grant" >授权</f:a>
					<c:if test="${bpmDefinitionItem.status==0}">
						<f:a alias="publishProcess" href="deploy.ht?defId=${bpmDefinitionItem.defId}" css="link deploy" >发布</f:a>
					</c:if>
					<c:if test="${bpmDefinitionItem.status==1}">
						<f:a alias="setDisableStatus" css="link lock" href="updateDisableStatus.ht?defId=${bpmDefinitionItem.defId}&disableStatus=${bpmDefinitionItem.disableStatus}" >
							<c:choose>
								<c:when test="${bpmDefinitionItem.disableStatus eq 0}">禁用</c:when>
								<c:when test="${bpmDefinitionItem.disableStatus eq 1}">启用</c:when>
							</c:choose>
						</f:a>
					</c:if>
				</display:column>
			</display:table>
			<hotent:paging tableId="bpmDefinitionItem"/>

		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>