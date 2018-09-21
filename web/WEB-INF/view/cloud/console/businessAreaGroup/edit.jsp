<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商圈分组</title>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
<html>
<head>
	<title>编辑 商圈分组</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#businessAreaGroupForm').form();
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
					window.top.location = "${ctx}/cloud/console/busiarea/busiarea.ht"
					} 
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		function delBusiness(id){ 
			if(confirm("确定要删除当前组吗？")){
				$.ajax({
					type : 'post',
					url : '${ctx}/cloud/console/businessAreaGroup/del.ht',
					data : {  id : id },
					dataType : "json",
					success : function(data){
					    
					    	if (data.choseGroup == "true") {
								 alert('操作成功');
								  
								 window.top.location = "${ctx}/cloud/console/busiarea/busiarea.ht"
							  } else if (data.result == 0) {
								 alert('操作失败');
							  }
					}, 
					error : function(data) {
						   alert('处理错误，请检查');
							 }
				})
			
			}
			
		}
		function delFriend(busiareaId) {
			//如果未登录，提示登录信息
			$.ligerMessageBox
					.confirm(
							'确认框',
							'您确认要将此商友删除吗?',
							function(r) {
								if (r) {
									$
											.ajax({
												type : 'POST',
												url : "${ctx}/cloud/console/busiarea/delFriend.ht",
												data : {
													busiareaId : busiareaId
												},
												dataType : "json",
												success : function(data) {
												 
													if (data.delFriend == "true") {
														alert('商友已经删除');
														window.location = "${ctx}/cloud/console/busiarea/busiarea.ht"
													} else if (data.result == 0) {
														alert('不是商友，无法删除');
													}
												},
												error : function(data) {
													alert('处理错误，请检查');
												}
											});
								}
							});
		}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${businessAreaGroup.id !=null}">
			        <span class="tbar-label">编辑分组</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加分组</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
				<c:choose>
			    <c:when test="${businessAreaGroup.id !=null}">
			       <a href="javascript:void(0)"  onclick="delBusiness('${businessAreaGroup.id}')" class="link del">删除</a>
			    </c:when>
			    <c:otherwise>
			        
			    </c:otherwise>			   
		    </c:choose>
					
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="businessAreaGroupForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">组名称: </th>
					<td><input type="text" id="groupname" name="groupname" value="${businessAreaGroup.groupname}"  class="inputText" validate="{required:true,maxlength:8}"  /></td>
				</tr>
				 
			</table>
			<input type="hidden" name="id" value="${businessAreaGroup.id}" />
		</form>
		 
		 
			 		<div id="msg_list2">
					<ul>
					<c:forEach items="${busiareaList }" var="friends">
						<li>
							<div class="avatar2"><a
														href="${ctx}/cloud/console/enterprise.ht?EntId=${friends.corpEnterprise.sysOrgInfoId} "
														class="link04"> <img src="#"
														onError="this.src='${ctx}/images/default-chance.jpg'"
														width="80" height="80" />
													</a></div>
							<div class="msg_right">
								<p class="msg_tt"><a
														href="${ctx}/cloud/console/enterprise.ht?EntId=${friends.corpEnterprise.sysOrgInfoId} "
														class="link04">${friends.corpEnterprise.name}</a></p>
                                <P class="msg_tt">公司地址:${friends.corpEnterprise.address} 
                                <span style="float:right;"><img src="${ctx }/images/icon_del2.jpg" width="52"
													height="18" onmouseover="src='${ctx}/images/icon_del.jpg';"
													onmouseout="src='${ctx}/images/icon_del2.jpg';"
													style="border: 0;" onClick="delFriend('${friends.id}');"/></span>
													</P>
							</div>
							<div class="rightbtn">
								<p>商友分组：<select id="groupid" name="groupid"  onchange="choseGroup(this.value,'${friends.id}')">
																<option value=""> 未分组</option>
								 
															 <c:forEach items="${businessAreaGroupList}" var="c">
															  
															 <c:if test="${c.id==friends.groupid}">
															 	<option value="${c.id}" selected="selected "> ${c.groupname} </option>
															  </c:if>
															 <c:if test="${c.id!=friends.groupid}">
															 	<option value="${c.id}"  > ${c.groupname}</option>
															  </c:if>
															</c:forEach>
														</select></p>
							</div>
							<p class="clear"></p>
						</li>
					</c:forEach>
					</ul>
				</div>
	
		
						<div id="pub_tab" class="pub_tab"
							style="width: 100%; height: 25px; border-top-width: 0px;">
							 
								<hotent:paging tableId="busiareaList" />
							 
						</div>
	</div>
	 
</div>
</body>
</html>
