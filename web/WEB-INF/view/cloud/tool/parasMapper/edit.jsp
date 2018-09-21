<%--
	time:2014-03-01 17:44:17
	desc:edit the cloud_tool_user_parasMapper
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑工具参数映射</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#cloud_tool_user_parasMapperForm').form();
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
					
					if(!rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/tool/parasMapper/list.ht?cloudToolUserId=<%=request.getAttribute("cloudToolUserId") %>";
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
			    <c:when test="${cloud_tool_user_parasMapper.id !=null}">
			        <span class="tbar-label">编辑工具参数映射</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加工具参数映射</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht?cloudToolUserId=<%=request.getAttribute("cloudToolUserId") %>">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="cloud_tool_user_parasMapperForm" method="post" action="save.ht">
		<input type="hidden" id="cloudToolUserId" name="cloudToolUserId" value="<%=request.getAttribute("cloudToolUserId") %>"  class="inputText" validate="{required:true,number:true }"  />
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			<!-- 
			
			<tr>
					<th width="20%">用户工具id:  <span class="required">*</span></th>
					<td><input type="text" id="cloudToolUserId" name="cloudToolUserId" value="<%=request.getAttribute("cloudToolUserId") %>"  class="inputText" validate="{required:true,number:true }"  /></td>
				</tr>
			 -->
				
				
				<tr>
					<th width="20%">参数名称:  <span class="required">*</span></th>
					<td><input type="text" id="name" name="name" value="${cloud_tool_user_parasMapper.name}"  class="inputText" validate="{required:true,maxlength:765}"  /></td>
				</tr>
				<tr>
					<th width="20%">参数表单映射名:  <span class="required">*</span></th>
					<td><input type="text" id="formMapperName" name="formMapperName" value="${cloud_tool_user_parasMapper.formMapperName}"  class="inputText" validate="{required:true,maxlength:765}"  /></td>
				</tr>
				<tr>
					<th width="20%">参数中文名:  <span class="required">*</span></th>
					<td><input type="text" id="chineseMapperName" name="chineseMapperName" value="${cloud_tool_user_parasMapper.chineseMapperName}"  class="inputText" validate="{required:true,maxlength:765}"  /></td>
				</tr>
				<tr>
					<th width="20%">参数分类:  <span class="required">*</span></th>
					<td><input type="text" id="type" name="type" value="${cloud_tool_user_parasMapper.type}"  class="inputText" validate="{required:true,maxlength:765}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${cloud_tool_user_parasMapper.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
