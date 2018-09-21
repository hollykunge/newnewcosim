<%--
	time:2014-03-06 18:01:30
	desc:edit the cloud_tool_user_outputFile
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_tool_user_outputFile</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#cloud_tool_user_outputFileForm').form();
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
						window.location.href = "${ctx}/cloud/tool/output/list.ht?cloudToolUserId=<%=request.getAttribute("cloudToolUserId") %>";
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
			    <c:when test="${cloud_tool_user_outputFile.id !=null}">
			        <span class="tbar-label">编辑工具输出文件</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加工具输出文件</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht?cloudToolUserId=<%=request.getAttribute("cloudToolUserId")%>">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="cloud_tool_user_outputFileForm" method="post" action="save.ht">
		<input type="hidden" id="cloudToolUserId" name="cloudToolUserId" value="<%=request.getAttribute("cloudToolUserId") %>"  class="inputText" validate="{required:true,number:true }"  />
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				
				<tr>
					<th width="20%">输出文件地址: </th>
					<td><input type="text" id="outputaddress" name="outputaddress" value="${cloud_tool_user_outputFile.outputaddress}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${cloud_tool_user_outputFile.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
