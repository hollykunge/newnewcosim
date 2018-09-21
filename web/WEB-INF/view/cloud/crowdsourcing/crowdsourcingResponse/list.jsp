<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>需求响应表管理</title>
<%@include file="/commons/include/get.jsp" %>

<script type="text/javascript">
	$(function() {
		$("a.apply").click(function() {
			$.ajax({
				type : "POST",
				url : 'apply.ht?id='+$('#userVacateId').val(),
				success : function(res) {
					var result = eval('('+res+')');
					$.ligerMessageBox.success('提示信息',result.message);
					window.location.href = "list.ht";
				},
				error : function(res) {
					$.ligerMessageBox.error('提示信息',result.message);
				}
			});
		});
	});
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">需求响应表管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">表单CODE:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制表人ID:</span><input type="text" name="Q_operaterId_SL"  class="inputText" />
						<span class="label">制表人名称:</span><input type="text" name="Q_operaterName_SL"  class="inputText" />
						<span class="label">制表企业ID:</span><input type="text" name="Q_operaterEnterpId_SL"  class="inputText" />
						<span class="label">制表企业名称:</span><input type="text" name="Q_operaterEnterpName_SL"  class="inputText" />
						<span class="label">制表日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<span class="label">发布状态:</span><input type="text" name="Q_publishStatus_SL"  class="inputText" />
						<span class="label">来源众包需求表ID:</span><input type="text" name="Q_sourceformCrowdsourcingId_SL"  class="inputText" />
						<span class="label">来源众包需求表CODE:</span><input type="text" name="Q_sourceformCrowdsourcingCode_SL"  class="inputText" />
						<span class="label">研发物品ID:</span><input type="text" name="Q_materialId_SL"  class="inputText" />
						<span class="label">研发物品CODE:</span><input type="text" name="Q_materialCode_SL"  class="inputText" />
						<span class="label">研发物品名称:</span><input type="text" name="Q_materialName_SL"  class="inputText" />
						<span class="label">研发物品分类:</span><input type="text" name="Q_materialType_SL"  class="inputText" />
						<span class="label">研发物品BOM层级:</span><input type="text" name="Q_materialBomLevel_SL"  class="inputText" />
						<span class="label">币种:</span><input type="text" name="Q_currency_SL"  class="inputText" />
						<span class="label">报价:</span><input type="text" name="Q_quote_SL"  class="inputText" />
						<span class="label">预计完成时间 从:</span> <input  name="Q_beginforecastCompleteTime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endforecastCompleteTime_DG" class="inputText date" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="crowdsourcingResponseList" id="crowdsourcingResponseItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${crowdsourcingResponseItem.id}">
				</display:column>
				<display:column property="code" title="表单CODE" sortable="true" sortName="CODE"></display:column>
				<display:column property="operaterId" title="制表人ID" sortable="true" sortName="OPERATER_ID"></display:column>
				<display:column property="operaterName" title="制表人名称" sortable="true" sortName="OPERATER_NAME"></display:column>
				<display:column property="operaterEnterpId" title="制表企业ID" sortable="true" sortName="OPERATER_ENTERP_ID"></display:column>
				<display:column property="operaterEnterpName" title="制表企业名称" sortable="true" sortName="OPERATER_ENTERP_NAME"></display:column>
				<display:column  title="制表日期" sortable="true" sortName="OPERATE_DATE">
					<fmt:formatDate value="${crowdsourcingResponseItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="publishStatus" title="发布状态" sortable="true" sortName="PUBLISH_STATUS"></display:column>
				<display:column property="sourceformCrowdsourcingId" title="来源众包需求表ID" sortable="true" sortName="SOURCEFORM_CROWDSOURCING_ID"></display:column>
				<display:column property="sourceformCrowdsourcingCode" title="来源众包需求表CODE" sortable="true" sortName="SOURCEFORM_CROWDSOURCING_CODE"></display:column>
				<display:column property="materialId" title="研发物品ID" sortable="true" sortName="MATERIAL_ID"></display:column>
				<display:column property="materialCode" title="研发物品CODE" sortable="true" sortName="MATERIAL_CODE"></display:column>
				<display:column property="materialName" title="研发物品名称" sortable="true" sortName="MATERIAL_NAME"></display:column>
				<display:column property="materialType" title="研发物品分类" sortable="true" sortName="MATERIAL_TYPE"></display:column>
				<display:column property="materialBomLevel" title="研发物品BOM层级" sortable="true" sortName="MATERIAL_BOM_LEVEL"></display:column>
				<display:column property="currency" title="币种" sortable="true" sortName="CURRENCY"></display:column>
				<display:column property="quote" title="报价" sortable="true" sortName="QUOTE"></display:column>
				<display:column  title="预计完成时间" sortable="true" sortName="FORECAST_COMPLETE_TIME">
					<fmt:formatDate value="${crowdsourcingResponseItem.forecastCompleteTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:180px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${crowdsourcingResponseItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${crowdsourcingResponseItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${crowdsourcingResponseItem.id}">
					</c:if>
					<a href="get.ht?id=${crowdsourcingResponseItem.id}&runid=${crowdsourcingResponseItem.runid}&status=${crowdsourcingResponseItem.runStatus}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="crowdsourcingResponseItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


