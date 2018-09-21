<%--
	time:2014-03-06 18:01:30
	desc:edit the cloud_tool_user_inputFile
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 工具输入文件</title>
	<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<link title="index" name="styleTag" rel="stylesheet" type="text/css" href="${ctx }/styles/default/css/web.css"></link>
<link href="${ctx }/js/lg/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/js/tree/v35/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/styles/default/css/form.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx }/js/tree/v35/jquery.ztree.excheck-3.5.min.js"></script>

<script type="text/javascript" src="${ctx }/js/lg/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="${ctx }/js/lg/plugins/htDicCombo.js"></script>
<script type="text/javascript" src="${ctx }/js/lg/plugins/ligerResizable.js" ></script>

	
	<script type="text/javascript">
		$(function() {
			
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#cloud_tool_user_inputFileForm').form();
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
						window.location.href = "${ctx}/cloud/tool/input/list.ht?cloudToolUserId=<%=request.getAttribute("cloudToolUserId") %>";
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
			    <c:when test="${cloud_tool_user_inputFile.id !=null}">
			        <span class="tbar-label">编辑 工具输入文件</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加 工具输入文件</span>
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
		<form id="cloud_tool_user_inputFileForm" method="post" action="save.ht">
		<input type="hidden" id="cloudToolUserId" name="cloudToolUserId" value="<%=request.getAttribute("cloudToolUserId") %>"  class="inputText" validate="{required:true,number:true }"  />
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				
				<tr>
					<th width="20%">输入文件地址: </th>
					<td><input type="text" id="inputaddress" name="inputaddress" value="${cloud_tool_user_inputFile.inputaddress}"  class="inputText" validate="{required:false,maxlength:512}"  /></td>
				</tr>
				
				<!-- 
				
				
				 -->
				
				<tr>
					<th width="20%">输入文件类型: </th>
					<td><input type="text" id="type" name="type" value="${cloud_tool_user_inputFile.type}"  class="dicComboTree" nodekey="srlx" validate="{required:false,maxlength:512}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">输入文件生成方案: </th>
					<td>
					<select id="generateStrategy" name="generateStrategy">
					<option>${cloud_tool_user_inputFile.generateStrategy}</option>
					<option>参数化生成</option>
					<option>本地上传</option>
					</select>
					
					
					
					
					
					</td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${cloud_tool_user_inputFile.id}" />
			
		</form>
		
	</div>
</div>
</body>
</html>
