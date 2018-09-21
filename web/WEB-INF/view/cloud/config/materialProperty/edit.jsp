<%--
	time:2013-04-16 18:50:26
	desc:edit the cloud_material_property
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#materialPropertyForm').form();
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
						window.location.href = "${ctx}/cloud/config/materialProperty/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		function preview(){
			CommonDialog("materialClassTree",
			function(data) {
				 
				$("#classId").val(data.id);
				$("#className").val(data.name);
				
				
			});
		}
		
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${materialProperty.id !=null}">
			        <span class="tbar-label">编辑</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加</span>
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
		<form id="materialPropertyForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">所属分类:  <span class="required">*</span></th>
					<td>
						<input type="hidden" id="classId" name="classId" value="${materialProperty.classId}" class="inputText"  />
						<input type="text" id="className" name="className" value="${materialProperty.className}" readonly="readonly" class="inputText"   />
						<a href="javascript:void(0)" onclick="preview()"  class="link detail">选择</a>
					</td>
					
					
				</tr>
				<tr>
					<th width="20%">分类属性名称:  <span class="required">*</span></th>
					<td>
						<input type="text" id="propertyName" name="propertyName" value="${materialProperty.propertyName}"  class="inputText" validate="{required:true,maxlength:96}"  />
						
					</td>
				</tr>
				<tr>
					<th width="20%">属性排序:  <span class="required">*</span></th>
					<td><input type="text" id="propertySequence" name="propertySequence" value="${materialProperty.propertySequence}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">属性类别: </th>
					<td>
						
						<c:remove var="checked"/>
											<c:if test="${materialProperty.propertyType==1}">
												<c:set var="checked" value='checked="checked"'></c:set>
											</c:if>
											<input name="propertyType" value="1" class="radio" id="propertyType" ${checked} type="radio" />
												文本框
												
												
												<c:remove var="checked"/>
											<c:if test="${materialProperty.propertyType==2}">
												<c:set var="checked" value='checked="checked"'></c:set>
											</c:if>	
											<input name="propertyType" value="2" class="radio" id="propertyType" ${checked} type="radio" />
												下拉选
					 	
						 
					</td>
				</tr>
				<div id="Isproperty">
					<tr>
						<th width="20%">属性值: </th>
						<td>
						<input type="text" id="value" name="value" value="${materialProperty.value}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<font color="red" class="required"> 如果属性类别为下拉选，多个值之间请用英文的逗号分隔， 如：香蕉,苹果   属性类别为文本框时，属性值可为空</font>
						</td>
					</tr>
				</div>
			</table>
			<input type="hidden" name="id" value="${materialProperty.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
