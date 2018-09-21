<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_interest_strategy管理</title>
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
				<span class="tbar-label">返利策略管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<!-- <div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div> -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<!-- <span class="label">供应商id:</span><input type="text" name="Q_enterpriseId_SL"  class="inputText" />
						<span class="label">经销商id:</span><input type="text" name="Q_coopenterpId_SL"  class="inputText" /> -->
						<span class="label">供应商名称:</span><input type="text" name="Q_enterpriseName_SL"  class="inputText" />
						<span class="label">经销商名称:</span><input type="text" name="Q_coopenterpName_SL"  class="inputText" />
						<!-- <span class="label">物品id:</span><input type="text" name="Q_materialId_SL"  class="inputText" />
						<span class="label">物品名称:</span><input type="text" name="Q_materialName_SL"  class="inputText" />
						<span class="label">数量上限:</span><input type="text" name="Q_capsNumber_SL"  class="inputText" />
						<span class="label">数量下限:</span><input type="text" name="Q_lowerNumber_SL"  class="inputText" />
						<span class="label">返利值:</span><input type="text" name="Q_interest_SL"  class="inputText" /> -->
					</div>
					
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="interestStrategyList" id="interestStrategyItem" requestURI="listInsterest.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;text-align:center;">
			  		<input type="checkbox" class="pk" name="id" value="${interestStrategyItem.id}">
				</display:column>
				<%-- <display:column property="enterpriseId" title="供应商id" sortable="true" sortName="enterprise_id" style="display:none;"></display:column> --%>
				<display:column style="text-align:center;" property="enterpriseName" title="供应商名称" sortable="true" sortName="enterprise_name" maxLength="80"></display:column>
				<%-- <display:column property="coopenterpId" title="经销商id" sortable="true" sortName="coopenterp_id" style="display:none;"></display:column> --%>
				<display:column style="text-align:center;" property="coopenterpName" title="代理商名称" sortable="true" sortName="coopenterp_name" maxLength="80"></display:column>
				<%-- <display:column property="materialId" title="物品id" sortable="true" sortName="material_id" style="display:none;"></display:column> --%>
				<display:column style="text-align:center;" property="materialName" title="物品名称" sortable="true" sortName="material_name" maxLength="80"></display:column>
				<c:if test="${type!='view'}">
					<display:column title="管理" media="html" style="width:180px;text-align:center;">
						<a href="editInterest.ht?id=${interestStrategyItem.id}" class="link edit">设定返利值</a>
						<a href="get.ht?id=${interestStrategyItem.id}&runid=${interestStrategyItem.runid}&status=${interestStrategyItem.runStatus}" class="link detail">明细</a>
					</display:column>
				</c:if>
				<c:if test="${type=='view'}">
					<display:column title="返利值查询" media="html" style="width:180px;text-align:center;">
						<a href="get.ht?id=${interestStrategyItem.id}&runid=${interestStrategyItem.runid}&status=${interestStrategyItem.runStatus}" class="link detail">查询</a>
					</display:column>
				</c:if>
			</display:table>
			<hotent:paging tableId="interestStrategyItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


