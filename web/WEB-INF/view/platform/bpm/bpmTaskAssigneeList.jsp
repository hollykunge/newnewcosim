<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>我交办的任务</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	//任务收回
	function back(id){
		//检查当前任务的上一步是否为当前任务的执行人员，若不是，不允许追回
		var url="${ctx}/platform/bpm/bpmTaskAssignee/back.ht?id="+ id;
		$.post(url,function(data){
			var jsonResult=eval("(" +data +")");
			if(jsonResult.result==1){
				$.ligerMessageBox.success('提示信息','任务成功的被收回！',function(){
					window.location.reload();
				});
			}
			else{
				$.ligerMessageBox.error('操作失败','任务已经完成或正在处理，不能进行收回！');
			}
	    });
	}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">我交办的任务</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">任务交办给:</span><input type="text" name="Q_assigneeName_S"  class="inputText" value="${param['Q_assigneeName_S']}"/>
						<span class="label">任务名称:</span><input type="text" name="Q_taskName_SL"  class="inputText" value="${param['Q_taskName_SL']}"/>
						<span class="label">任务执行状态:</span>
						<select type="text" name="Q_taskStatus_S" value="${param['Q_taskStatus_S']}">
							<option value="" >全部</option>
							<option value="0" <c:if test="${param['Q_taskStatus_S'] == 0}">selected</c:if>>待执行</option>
							<option value="1" <c:if test="${param['Q_taskStatus_S'] == 1}">selected</c:if>>已执行</option>
						</select>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
		    <display:table name="bpmTaskAssigneeList" id="bpmTaskAssigneeItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column property="assigneeName" title="任务交办给" sortable="true" sortName="assigneeName"></display:column>
				<display:column property="subject" title="流程定义标题" sortable="true" sortName="subject"></display:column>
				<display:column property="taskName" title="任务名称" sortable="true" sortName="taskName"></display:column>
				<display:column  title="交办日期" sortable="true" sortName="assigneeTime"   style="width:180px">
					<fmt:formatDate value="${bpmTaskAssigneeItem.assigneeTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="任务执行状态" sortable="true">
					<c:choose>
						<c:when test="${bpmTaskAssigneeItem.taskStatus==0}">
							<span class="red">待执行</span>
						</c:when>
						<c:when test="${bpmTaskAssigneeItem.taskStatus==1}">
							<span class="green">已执行</span>
						</c:when>
					</c:choose>
				</display:column>
				<display:column property="memo" title="备注" sortable="true" sortName="memo"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="${ctx}/platform/bpm/processRun/get.ht?runId=${bpmTaskAssigneeItem.runId}" class="link detail">明细</a>
					<c:if test="${bpmTaskAssigneeItem.taskStatus==0}">
					<a href="javascript:void(0)" onclick="back(${bpmTaskAssigneeItem.id})" class="link back">收回</a>
					</c:if>
				</display:column>
			</display:table>
			<hotent:paging tableId="bpmTaskAssigneeItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


