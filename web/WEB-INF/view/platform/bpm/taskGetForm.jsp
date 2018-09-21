<%@ page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
		<title>流程任务-[${taskInfo.name}]执行</title>
		<%@include file="/commons/include/customForm.jsp" %>
		<script type="text/javascript">
		var taskId='${taskInfo.id}';
		var isExtForm=eval('${isExtForm}');
		$(function(){
			if(isExtForm){
				var formUrl=$('#divExternalForm').attr("formUrl");
				$('#divExternalForm').load(formUrl, function() {});
			}
		});
	</script>	
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">任务明细--${processRun.subject}--${taskInfo.name}</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back" href="${returnUrl}">返回</a></div>
				</div>
			</div>
		</div>
		<c:if test="${action=='taskInfo'}">
			<f:tab curTab="业务表单" tabName="taskInfo"/>
		</c:if>
		<div class="panel-body">
			<form id="frmWorkFlow" method="post" >
				<c:choose>
					<c:when test="${isExtForm==true}">
						<div id="divExternalForm" formUrl="${form}"></div>
					</c:when>
					<c:otherwise>
						${form}
						<input type="hidden" id="formData" name="formData" value=""/>
					</c:otherwise>
				</c:choose>
				<input type="hidden" name="taskId" value="${taskInfo.id}"/>
			</form>
	   </div>
     </div>
   </div>
</body>
</html>