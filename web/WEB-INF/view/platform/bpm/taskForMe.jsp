<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>我的待办任务</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript">
function executeTask(taskId){
	 var url="${ctx}/platform/bpm/taskInfo/toStart.ht?taskId="+taskId;
	 jQuery.openFullWindow(url);
}
//更改任务执行用户
function changeTaskUser(taskId,taskName){
	//显示用户选择器
	UserDialog({
		isSingle:true,
		callback:function(userId,fullname){
			if(userId=='' || userId==null || userId==undefined) return;
			var url='${ctx}/platform/bpm/taskInfo/delegate.ht';
			var params= {taskId:taskId,userId:userId };
			$.post(url,params,function(responseText){
				var obj=new com.hotent.form.ResultMessage(responseText);
				//成功
				if(obj.isSuccess()){
					$.ligerMessageBox.success('提示信息','任务交办成功!',function(){
			    		 location.reload();	 
			    	 });
				}
				else{
					$.ligerMessageBox.error('提示信息','任务交办失败!');
				}
			});
		}
	});
}
</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading" style="background:#76B0EA">
			<div class="panel-title" style="color:#ffffff;font-weight:bold;font-size:15px;">
			任务管理列表
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="forMe.ht">
					<div class="row">
						<span class="label">流程定义名称:</span><input type="text" name="Q_processName_SL"  class="inputText" style="width:13%;" value="${param['Q_processName_SL']}"/>
						<span class="label">事项名称:</span><input type="text" name="Q_subject_SL"  class="inputText" style="width:13%;" value="${param['Q_subject_SL']}"/>
						<span class="label">任务名称:</span><input type="text" name="Q_name_SL"  class="inputText" style="width:13%;" value="${param['Q_name_SL']}"/>
						
					</div>
				</form>
			</div>
		</div>
		<div class="panel-footer">
			
			
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="taskList" id="taskItem" requestURI="forMe.ht" sort="external" cellpadding="1" cellspacing="1"  class="table table-bordered table-hover">
					<display:column title="${checkAll}" media="html" style="width:30px;" class="active">
						  	<input type="checkbox" class="pk" name="id" value="${taskItem.id}">
					</display:column>
					<display:column property="subject" title="事项名称" style="text-align:left" class="active"></display:column>
					<display:column property="processName" title="流程定义名称" style="text-align:left" class="active"></display:column>
					<display:column property="name" title="任务名称" sortable="true" sortName="name_" style="text-align:left" class="active"></display:column>
					<display:column title="所属人" sortable="true" sortName="owner_" style="text-align:left" class="active">
						<f:userName userId="${taskItem.owner}"/>
					</display:column>
					<display:column title="执行人" sortable="true" sortName="assignee_" style="text-align:left" class="active">
						<f:userName userId="${taskItem.assignee}"/>
					</display:column>
					<display:column title="创建时间" sortable="true" sortName="create_time_" class="active">
						<fmt:formatDate value="${taskItem.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column title="到期时间" sortable="true" sortName="due_date_" class="active">
						<fmt:formatDate value="${taskItem.dueDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column title="管理" media="html" style="width:230px;" class="active">	
						<a href="detail.ht?taskId=${taskItem.id}" class="link detail" title="明细">明细</a>				
						<c:choose>
							<c:when test="${not empty taskItem.assignee && empty taskItem.executionId && taskItem.description=='通知任务' }">
								<a href="javascript:executeTask(${taskItem.id})" class="link run" title="主办">主办</a>
								<!-- <a href="${ctx}/platform/bpm/taskInfo/toStart.ht?taskId=${taskItem.id}" class="link run" title="主办">主办</a> -->
							</c:when>
							<c:when test="${not empty taskItem.assignee && not empty taskItem.executionId }">
								<a href="javascript:executeTask(${taskItem.id})" class="link run" title="主办">主办</a>
								<!-- <a href="${ctx}/platform/bpm/taskInfo/toStart.ht?taskId=${taskItem.id}" class="link run" title="主办">主办</a> -->
								<c:if test="${not empty candidateMap[taskItem.id] }">
								       		<a href="unlock.ht?taskId=${taskItem.id}" class="link unlock" title="解锁">解锁</a>
								</c:if> 
								  
								<a href="javascript:changeTaskUser(${taskItem.id},'${taskItem.name}')" class="link goForward" title="交办">交办</a>
							</c:when>
							<c:otherwise >
								<a href="claim.ht?taskId=${taskItem.id}" class="link lock" title="锁定">锁定</a>
							</c:otherwise>
						</c:choose>	
					</display:column>
				</display:table>
				<hotent:paging tableId="taskItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


