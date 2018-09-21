<%--
	time:2011-11-09 11:20:13
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>添加组织架构</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>	
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysOrg"></script>
	<script type="text/javascript" src="${ctx }/js/lg/plugins/htButtons.js" ></script>
	<script type="text/javascript" src="${ctx }/js/lg/plugins/ligerWindow.js" ></script>
	<script type="text/javascript" src="${ctx }/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			
			var scope="${scope}";
			
			var url="${ctx}/platform/system/sysOrg/get.ht?orgId={0}";
			if(scope=="grade"){
				url="${ctx}/platform/system/sysOrg/getGrade.ht?orgId={0}";
			}			
		});
		
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#sysOrgForm').form();
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
				window.location.href = "${ctx}/cloud/system/enterprises/org/list.ht";
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		<%--
		//在sysOrgEdit.jsp调用，为了弹出页面的拖动范围大些，所以写在父页面了		
		function addClick()
		{
			UserDialog({callback:function(userIds,fullnames){
				$("#ownUser").val(userIds);
				$("#ownUserName").val(fullnames);	
			},isSingle:false});
		};
		
		//清空
		function reSet(){
			$("#ownUser").val("");
			$("#ownUserName").val("");	
		}
		--%>
	</script>
</head>
<body>
<div class="panel">
      <div class="panel-top">
			<div class="tbar-title" style="height:17px !important">
				<c:choose>
					<c:when test="${sysOrg.orgId==null}">添加组织信息</c:when>
					<c:otherwise>编辑组织信息</c:otherwise>  
				</c:choose>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="javascript:void(0);">保存</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="sysOrgForm" method="post" action="save.ht">				
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0"  type="main">
						<input type="hidden" name="demId" value="${demension.demId}"/>
						<tr>
							<th width="20%">组织名称: </th>
							<td><input type="text" id="orgName" name="orgName" value="${sysOrg.orgName}" style="width:255px !important" class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">组织类型: </th>
							<td><select id="orgType" name="orgType" class="select">
											<c:forEach items="${sysOrgTypelist}" var="org" >							 				
								 					 <option value ="${org.id}" <c:if test="${sysOrg.orgType==org.id}">selected="selected"</c:if> >${org.name}</option>												
											</c:forEach> 
			                    	</select>
			                 </td>
						</tr>
						<%--
						<tr>
							<th width="20%">主要负责人:</th>
							<td>
	                        <input type="text" class="inputText" readonly="readonly" style="width:300px" id="ownUserName" value="${sysOrg.ownUserName}" >
						    <a href="javascript:void(0)" onclick="addClick()" class="link get">选择</a>
						    <a href="javascript:void(0)" onclick="reSet()" class="link clean">清空</a>
						    <input  type="hidden" name="ownUser" id="ownUser" value="${sysOrg.ownUser}">
							</td>
						</tr>	
						 --%>
						<tr>
							<th width="20%">组织描述: </th>
							<td><textarea id="orgDesc" name="orgDesc" cols="30" rows="4"  style="width:258px !important">${sysOrg.orgDesc}</textarea></td>
						</tr>		
						<tr>
							<th width="20%">组织状态: </th>
							<td>
								<select id="orgStatus" name="orgStatus" class="select">													 				
								 	<option value ="0" <c:if test="${sysOrg.orgStatus==0}">selected="selected"</c:if> >未审核</option>
								 	<option value ="1" <c:if test="${sysOrg.orgStatus==1}">selected="selected"</c:if> >审核通过</option>
								 	<option value ="2" <c:if test="${sysOrg.orgStatus==2}">selected="selected"</c:if> >审核不通过</option>								
			                    </select>							
							</td>
						</tr>							
					</table>
						
				<input type="hidden" id="orgId" name="orgId" value="${sysOrg.orgId}" />
				<input type="hidden" id="orgSupId" name="orgSupId" value="${sysOrg.orgSupId}"/>
		  </form>
	</div>

</div>

</body>
</html>
