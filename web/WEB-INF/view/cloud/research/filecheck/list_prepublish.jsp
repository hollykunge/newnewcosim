<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>预发布流程管理</title>
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
				<span class="tbar-label">预发布流程管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="${ctx}/cloud/research/filecheck/start_prepublish.ht">添加</a></div> 
					
					<%-- <a class="link add" href="javascript:void(0)" onclick="window.open('${ctx}/cloud/research/filecheck/create.ht')">添加</a></div> --%>
					 
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">审批号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单人姓名:</span><input type="text" name="Q_operatorName_SL"  class="inputText" />
						 
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="resFilecheckList" id="resFilecheckItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${resFilecheckItem.id}">
				</display:column>
				<display:column property="code" title="文档编码" sortable="true" sortName="code"></display:column>
				 
				<display:column property="operatorName" title="制单人" sortable="true" sortName="operator_name"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${resFilecheckItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
			 
				<display:column property="enterpriseName" title="企业" sortable="true" sortName="enterprise_name"></display:column>
				<display:column property="status" title="文档审签状态" sortable="true" sortName="status"></display:column>
				 
				<display:column title="管理" media="html" style="width:180px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${resFilecheckItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${resFilecheckItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${resFilecheckItem.id}">
					</c:if>
					<a href="get.ht?id=${resFilecheckItem.id}&runid=${resFilecheckItem.runid}&status=${resFilecheckItem.runStatus}" class="link detail">明细</a>
					<a href="${ctx}/platform/bpm/processRun/userImage.ht?action=process&action=process&runId=${resFilecheckItem.runId}" class="link detail">流程示意图</a>					
				</display:column>
			</display:table>
			<hotent:paging tableId="resFilecheckItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


