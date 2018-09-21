<%--
	time:2013-04-17 15:21:31
	desc:edit the 维修单
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>添加维修单</title>
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
			var frm=$('#taskMForm').form();
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
						window.location.href = "${ctx}/cloud/aftersale/taskm/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
          function preview(){
			CommonDialog("feedBackList",
			function(data) {
				$("#user_id").val(data.id);
				$("#user_name").val(data.fdman);
				
			});
		  }
          var data_id="";
          var data_name="";
          function userview(){
  			CommonDialog("org_info_list",
  			function(data) {
  				//多个厂商
  			    //$(data).each(function(i){
  				//	var did=data[i].id;
  				//	var dname=data[i].name;
  				//	data_id=data_id+did+",";
  				//	data_name=data_name+dname+",";
  				//});
  				//$("#enterprise_id").val(data_id);
  				//$("#e_name").val(data_name);
  				$("#en_id").val(data.SYS_ORG_INFO_ID);
  				$("#en_name").val(data.NAME);
  				
  			});
  		  }
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${taskM.id !=null}">
			        <span class="tbar-label">编辑维修单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加维修单</span>
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
		<form id="taskMForm" method="post" action="${ctx}/cloud/aftersale/taskm/save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			        <input size="35" type="hidden" id="enterprise_id" name="enterprise_id" value="${feedback.enterprise_id }" />
					<input size="35" type="hidden" id="enterprise_name" name="enterprise_name" value="${feedback.enterprise_name }" />
				<tr>
					<th style="width:120px">任务单号:  <span class="required"></span></th>
					<td colspan="3"><input size="35" type="text" id="code" name="code" value="${feedback.code }"  class="inputText" validate="{required:true,maxlength:36}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">反馈用户:  <span class="required"></span></th>
					<td>
					<input size="35" type="hidden" id="user_id" name="feedback_id" value="${feedback.id}" />
					<input size="35" type="text" id="user_name" name="feedback_name" class="inputText" value="${feedback.fdman}" readonly="readonly"/>
					<a href="javascript:void(0)" onclick="preview()"  class="link detail">选择</a>
					</td>
					<th style="width:120px">产品编码:  <span class="required"></span></th>
					<td><input size="35" type="text" id="prodcode" name="prodcode" value="${feedback.prodcode}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">产品名称:  <span class="required"></span></th>
					<td><input size="35" type="text" id="prodname" name="prodname" value="${feedback.prodname}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<th style="width:120px">产品型号:  <span class="required"></span></th>
					<td><input size="35" type="text" id="prodmodel" name="prodmodel" value="${feedback.prodcode}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">问题描述:  <span class="required"></span></th>
					<td colspan="3"><textarea cols="95" rows="5" id="descn" name="descn" class="inputText" validate="{required:true,maxlength:96}" >${feedback.descn}</textarea></td>
				</tr>
				<tr>
					<th style="width:120px">维修厂商:  <span class="required"></span></th>
					<td>
					<input size="35" type="hidden" id="en_id" name="purenterp_id"  value="${feedback.purenter_id }"  />
					<input size="35" type="text" id="en_name" name="purenter_name"  value="${feedback.purenter_name}"  readonly="readonly" class="inputText" validate="{required:true,maxlength:300}"  />
					<a href="javascript:void(0)" onclick="userview()"  class="link detail">选择</a>
					</td>
				</tr>
				<%-- <tr>
					<th style="width:120px">备注:  <span class="required"></span></th>
					<td><input size="35" type="text" id="remark" name="remark" value="${taskM.remark}"  class="inputText" validate="{required:true,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">质检意见:  <span class="required"></span></th>
					<td><input size="35" type="text" id="qualityopinion" name="qualityopinion" value="${taskM.qualityopinion}"  class="inputText" validate="{required:true,maxlength:300}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">售后主管意见:  <span class="required"></span></th>
					<td><input size="35" type="text" id="serviceopinion" name="serviceopinion" value="${taskM.serviceopinion}"  class="inputText" validate="{required:true,maxlength:300}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">状态:  <span class="required"></span></th>
					<td><input size="35" type="text" id="statu" name="statu" value="${feedback.statu}"  class="inputText" validate="{required:true,maxlength:24}"  /></td>
				</tr> --%>
			</table>
			<input size="35" type="hidden" name="id" value="${taskM.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
