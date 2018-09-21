<%--
	time:2013-05-06 16:34:55
	desc:edit the sys_org_info_aptitude
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 sys_org_info_aptitude</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#aptitudeForm').form();
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
						window.location.href = "${ctx}/cloud/aptitude/aptitude/list.ht";
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
			    <c:when test="${aptitude.id !=null}">
			        <span class="tbar-label">编辑sys_org_info_aptitude</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加sys_org_info_aptitude</span>
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
		<form id="aptitudeForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">INFO_ID: </th>
					<td><input type="text" id="infoId" name="infoId" value="${aptitude.infoId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">CATE_TYPE: </th>
					<td><input type="text" id="cateType" name="cateType" value="${aptitude.cateType}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">CATE_ORG: </th>
					<td><input type="text" id="cateOrg" name="cateOrg" value="${aptitude.cateOrg}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">INURE_DATE: </th>
					<td><input type="text" id="inureDate" name="inureDate" value="<fmt:formatDate value='${aptitude.inureDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">END_DATE: </th>
					<td><input type="text" id="endDate" name="endDate" value="${aptitude.endDate}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">CATE_PIC: </th>
					<td><input type="text" id="catePic" name="catePic" value="${aptitude.catePic}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${aptitude.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
