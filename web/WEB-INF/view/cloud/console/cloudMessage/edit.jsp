<%--
	time:2013-04-19 13:32:10
	desc:edit the CLOUD_MESSAGE
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 CLOUD_MESSAGE</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#cloudMessageForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/cloudMessage/cloudMessage/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${cloudMessage.id !=null}">
			        <span class="tbar-label">编辑CLOUD_MESSAGE</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加CLOUD_MESSAGE</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="cloudMessageForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">sender_id: </th>
					<td><input type="text" id="senderId" name="senderId" value="${cloudMessage.senderId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">sendent_id: </th>
					<td><input type="text" id="sendentId" name="sendentId" value="${cloudMessage.sendentId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">receiver_id: </th>
					<td><input type="text" id="receiverId" name="receiverId" value="${cloudMessage.receiverId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">receiveent_id: </th>
					<td><input type="text" id="receiveentId" name="receiveentId" value="${cloudMessage.receiveentId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">sendtime: </th>
					<td><input type="text" id="sendtime" name="sendtime" value="<fmt:formatDate value='${cloudMessage.sendtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">outtime: </th>
					<td><input type="text" id="outtime" name="outtime" value="<fmt:formatDate value='${cloudMessage.outtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">result: </th>
					<td><input type="text" id="result" name="result" value="${cloudMessage.result}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">link: </th>
					<td><input type="text" id="link" name="link" value="${cloudMessage.link}"  class="inputText" validate="{required:false,maxlength:765}"  /></td>
				</tr>
				<tr>
					<th width="20%">type: </th>
					<td><input type="text" id="type" name="type" value="${cloudMessage.type}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">content: </th>
					<td><input type="text" id="content" name="content" value="${cloudMessage.content}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">source_id: </th>
					<td><input type="text" id="sourceId" name="sourceId" value="${cloudMessage.sourceId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">title: </th>
					<td><input type="text" id="title" name="title" value="${cloudMessage.title}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">readtime: </th>
					<td><input type="text" id="readtime" name="readtime" value="<fmt:formatDate value='${cloudMessage.readtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${cloudMessage.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
