<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>返厂维修单管理</title>
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
				<span class="tbar-label">返厂维修单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<!-- 
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
					 -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">返厂维修单号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<!-- <span class="label">用户反馈单号:</span><input type="text" name="Q_feedbackid_SL"  class="inputText" /> -->
						<span class="label">维修厂商:</span><input type="text" name="Q_purenter_name_SL"  class="inputText" />
						<span class="label">完成时间 从:</span> <input  name="Q_beginenddata_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endenddata_DG" class="inputText date" />
						<!-- <span class="label">送修厂:</span><input type="text" name="Q_currentpurenter_SL"  class="inputText" />
						<span class="label">制单时间 从:</span> <input  name="Q_beginoperatedate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperatedate_DG" class="inputText date" />
						<span class="label">制单人:</span><input type="text" name="Q_operator_SL"  class="inputText" />
						<span class="label">制单日期:</span><input type="text" name="Q_operatordata_SL"  class="inputText" />
						<span class="label">返厂原因:</span><input type="text" name="Q_reason_SL"  class="inputText" />
						<span class="label">备注:</span><input type="text" name="Q_remark_SL"  class="inputText" /> -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="returntaskList" id="returntaskItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${returntaskItem.id}">
				</display:column>
				<display:column property="code" title="返厂维修单号" sortable="true" sortName="code"></display:column>
				<%-- <display:column property="feedbackid" title="用户反馈单号" sortable="true" sortName="feedbackid"></display:column> --%>
				<display:column property="purenter_name" title="维修厂商" sortable="true" sortName="purenter_name"></display:column>
				<%-- <display:column  title="完成时间" sortable="true" sortName="enddata"> 
					<fmt:formatDate value="${returntaskItem.enddata}" pattern="yyyy-MM-dd"/>
				</display:column>
				--%>
				<display:column property="currentpurenter" title="送修厂" sortable="true" sortName="currentpurenter"></display:column>
				<display:column  title="制单时间" sortable="true" sortName="operatedate">
					<fmt:formatDate value="${returntaskItem.operatedate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<%-- <display:column property="operator" title="制单人" sortable="true" sortName="operator"></display:column>
				<display:column property="reason" title="返厂原因" sortable="true" sortName="reason" maxLength="80"></display:column> 
				<display:column property="remark" title="备注" sortable="true" sortName="remark"></display:column>--%>
				<display:column title="管理" media="html" style="width:180px">
					<a href="get.ht?id=${returntaskItem.id}" class="link detail">明细</a>
					<a href="${ctx}/platform/bpm/processRun/get.ht?runId=${returntaskItem.runId}" class="link detail">查看流程</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="returntaskItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


