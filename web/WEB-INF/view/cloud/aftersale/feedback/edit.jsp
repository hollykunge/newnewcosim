<%--
	time:2013-04-19 11:40:23
	desc:edit the 反馈单
--%>
<%@page import="java.util.Date"%>
<%@page import="com.hotent.core.util.ContextUtil"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑反馈单</title>
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
			var frm=$('#feedbackForm').form();
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
						window.location.href = "${ctx}/cloud/aftersale/feedback/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		 var data_id="";
         var data_name="";
		function userview(){
			
  			CommonDialog("org_info_list",
  			function(data) {
  			//	 $(data).each(function(i){
   			//		var did=data[i].SYS_ORG_INFO_ID;
   			//		var dname=data[i].NAME;
   			//		data_id=data_id+did+",";
   			//		data_name=data_name+dname+",";
   			//	});
   			//	$("#enterprise_id").html();
   			//	$("#enterprise_id").val(data_id);
   			//	$("#e_name").val(data_name);
   			
   			//一个厂商
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
			    <c:when test="${feedback.id !=null}">
			        <span class="tbar-label">编辑反馈单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加反馈单</span>
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
		<form id="feedbackForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			    <input size="35" type="hidden" id="enterprise_id" name="enterprise_id" value="<%=ContextUtil.getCurrentOrg().getOrgId() %>" />
			    <input size="35" type="hidden" id="enterprise_name" name="enterprise_name" value="<%=ContextUtil.getCurrentOrg().getOrgName() %>" />
			    <tr><th style="width:120px;">反馈单号</th><td colspan="3"><input size="35" id="code" name="code" value="${feedback.code }"  class="inputText" validate="{required:true,maxlength:96}"></td></tr>
				<tr><th style="width:120px;">反馈用户</th><td><input size="35" id="fdman" name="fdman" value="<%=ContextUtil.getCurrentUser().getUsername() %>"  class="inputText" validate="{required:false,maxlength:96}"/></td><th style="width:120px;">反馈日期</th><td><input size="35" id="fddate" name="fddate" value="<fmt:formatDate value='<%=new Date() %>' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true,required:false}"/></td></tr>		
				<tr><th style="width:120px;">产品名称</th><td><input size="35" id="prodname" name="prodname" value="${feedback.prodname}"  class="inputText" validate="{required:true,maxlength:96}"/></td><th style="width:120px;">产品型号/编码</th><td><input size="35" id="prodcode" name="prodcode" value="${feedback.prodcode}"  class="inputText" validate="{required:false,maxlength:96}"/></td></tr>
				<tr><th style="width:120px;">购买人</th><td><input size="35" id="purchaseman" name="purchaseman" value="${feedback.purchaseman}"  class="inputText" validate="{required:false,maxlength:96}"/></td><th style="width:120px;">购买日期</th><td><input size="35" id="purchasedate" name="purchasedate" value="<fmt:formatDate value='${feedback.purchasedate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true,required:false}"/></td></tr>
				<tr><th>反馈问题</th><td colspan="3"><textarea class="area" cols="95" rows="5" id="descn" name="descn"  class="inputText" validate="{required:false,maxlength:768}">${feedback.descn}</textarea></td></tr>
				<tr><th>处理结果</th><td colspan="3">
				<select id="result" name="result" class="inputText">
				    <c:remove var="selected"/>
				    <c:if test="${feedback.result=='保修' }">
				    <c:set var="selected" value="selected"></c:set>
				    </c:if>
			      	<option value='保修' ${selected }>保修</option>
			      	<c:remove var="selected"/>
				    <c:if test="${feedback.result=='更换' }">
				    <c:set var="selected" value="selected"></c:set>
				    </c:if>
			      	<option value='更换' ${selected } >更换</option>
			      	<c:remove var="selected"/>
				    <c:if test="${feedback.result=='退货' }">
				    <c:set var="selected" value="selected"></c:set>
				    </c:if>
			      	<option value='退货' ${selected } >退货</option>
			      	<c:remove var="selected"/>
				    <c:if test="${feedback.result=='过保修期' }">
				    <c:set var="selected" value="selected"></c:set>
				    </c:if>
			      	<option value='过保修期' ${selected } >过保修期</option>
			      	<c:remove var="selected"/>
				    <c:if test="${feedback.result=='转服务站修理' }">
				    <c:set var="selected" value="selected"></c:set>
				    </c:if>
			      	<option value='转服务站修理' ${selected }>转服务站修理</option>
			    </select></td></tr>
			    <tr><th style="width:120px;">厂商</th><td>
			    <input size="35" type="hidden" id="en_id" name="purenter_id" value="${feedback.purenter_id }"/>
			    <input size="35" id="en_name" name="purenter_name" value="${feedback.purenter_name}"  readonly="readonly" class="inputText" validate="{required:false,maxlength:32}"/><a href="javascript:void(0)" onclick="userview()"  class="link detail">选择</a></td><th style="width:120px;">状态</th><td><input size="35" id="statu" name="statu" value="待办" readonly="readonly" class="inputText" validate="{required:false,maxlength:24}"/></td></tr>
				
			</table>
			<input size="35" type="hidden" name="id" value="${feedback.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
