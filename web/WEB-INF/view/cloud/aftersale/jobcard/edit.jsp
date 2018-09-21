<%--
	time:2013-04-19 11:40:23
	desc:edit the 维修工作单
--%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="com.hotent.core.util.ContextUtil"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 维修工作单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#jobCardForm').form();
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
						window.location.href = "${ctx}/cloud/aftersale/jobcard/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		$(function(){
			
			var seq=1;
			$("#btnAdd").click(function(){
				$(":input[name='seq']").val(seq++);
			});
		});
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${jobCard.id !=null}">
			        <span class="tbar-label">编辑维修工作单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加维修工作单</span>
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
		<form id="jobCardForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			        <input size="35" type="hidden" id="enterprise_id" name="enterprise_id" value="<%=ContextUtil.getCurrentOrg().getOrgId() %>" />
					<input size="35" type="hidden" id="enterprise_name" name="enterprise_name" value="<%=ContextUtil.getCurrentOrg().getOrgName() %>" />
				<tr>
					<th style="width:120px">维修任务ID:  <span class="required"></span></th>
					<td><input size="35" type="text" id="taskid" name="taskid" value="${jobCard.taskid}"  class="inputText" validate="{required:true,number:true }"  /></td>
					<th style="width:120px">产品编码:  <span class="required"></span></th>
					<td><input size="35" type="text" id="prodcode" name="prodcode" value="${jobCard.prodcode}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				<tr>	
					<th style="width:120px">产品名称:  <span class="required"></span></th>
					<td><input size="35" type="text" id="prodname" name="prodname" value="${jobCard.prodname}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<th style="width:120px">产品型号:  <span class="required"></span></th>
					<td><input size="35" type="text" id="prodmodel" name="prodmodel" value="${jobCard.prodmodel}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">制单时间:  <span class="required"></span></th>
					<td><input size="35" id="operatedate" name="operatedate" value="<fmt:formatDate value='${jobCard.operatedate}' pattern='yyyy-MM-dd'/>"  class="inputText date" validate="{date:false,required:false}"/></td>	
					<th style="width:120px">检修措施:  <span class="required"></span></th>
					<td><input size="35" id="measure" name="measure"  value="${jobCard.measure}" class="inputText" validate="{required:false,maxlength:300}" /></td>
				</tr>
				
				<tr>
					<th style="width:120px">问题描述:  <span class="required"></span></th>
					<td colspan="3"><textarea cols="95" rows="3" id="descn" name="descn"  class="inputText" validate="{required:false,maxlength:96}" >${jobCard.descn} </textarea></td>
				</tr>
				<tr>
					<th style="width:120px">故障分类:  <span class="required"></span></th>
					<td colspan="3"><%@ include file="type.jsp" %></td>
				</tr>
				<tr>
					<th style="width:120px">故障原因:  <span class="required"></span></th>
					<td colspan="3"><textarea cols="95" rows="3" id="reason" name="reason"   class="inputText" validate="{required:false,maxlength:300}" >${jobCard.reason}</textarea></td>
				</tr>
				<tr>
					<th style="width:120px">工作内容:  <span class="required"></span></th>
					<td colspan="3"><textarea cols="95" rows="3" id="content" name="content"   class="inputText" validate="{required:false,maxlength:765}" >${jobCard.content}</textarea></td>
				</tr>
				<tr>
					<th style="width:120px">维修人员:  <span class="required"></span></th>
					<td><input size="35" type="text" id="man" name="man" value="${jobCard.man}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">维修工时:  <span class="required"></span></th>
					<td><input size="35" type="text" id="manhours" name="manhours" value="${jobCard.manhours}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="jobCardDetail" formType="window" type="sub">
				<tr>
					<td colspan="6">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						维修工作单明细
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>维修任务ID</th>
					<th>序号</th>
					<th>配件名称</th>
					<th>配件编号</th>
					<th>数量</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${jobCardDetailList}" var="jobCardDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="taskid">${jobCardDetailItem.taskid}</td>
					    <td style="text-align: center" name="seq">${jobCardDetailItem.seq}</td>
					    <td style="text-align: center" name="prodname">${jobCardDetailItem.prodname}</td>
					    <td style="text-align: center" name="prodcode">${jobCardDetailItem.prodcode}</td>
					    <td style="text-align: center" name="nums">${jobCardDetailItem.nums}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input size="35" type="hidden" name="taskid" value="${jobCardDetailItem.taskid}"/>
						<input size="35" type="hidden" name="seq" value="${jobCardDetailItem.seq}"/>
						<input size="35" type="hidden" name="prodname" value="${jobCardDetailItem.prodname}"/>
						<input size="35" type="hidden" name="prodcode" value="${jobCardDetailItem.prodcode}"/>
						<input size="35" type="hidden" name="nums" value="${jobCardDetailItem.nums}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="taskid"></td>
			    	<td style="text-align: center" name="seq"></td>
			    	<td style="text-align: center" name="prodname"></td>
			    	<td style="text-align: center" name="prodcode"></td>
			    	<td style="text-align: center" name="nums"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input size="35" type="hidden" name="taskid" value=""/>
			    	<input size="35" type="hidden" name="seq" value=""/>
			    	<input size="35" type="hidden" name="prodname" value=""/>
			    	<input size="35" type="hidden" name="prodcode" value=""/>
			    	<input size="35" type="hidden" name="nums" value=""/>
		 		</tr>
		    </table>
			<input size="35" type="hidden" name="id" value="${jobCard.id}" />
		</form>
		
	</div>
	<form id="jobCardDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th style="width:120px">维修任务ID:  <span class="required"></span></th>
				<td><input size="35" type="text" name="taskid" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">序号:  <span class="required"></span></th>
				<td><input size="35" type="text" name="seq" value=""  class="inputText" validate="{required:true,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">配件名称:  <span class="required"></span></th>
				<td><input size="35" type="text" name="prodname" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th style="width:120px">配件编号:  <span class="required"></span></th>
				<td><input size="35" type="text" name="prodcode" value=""  class="inputText" validate="{required:false,maxlength:36}"/></td>
			</tr>
			<tr>
				<th style="width:120px">数量:  <span class="required"></span></th>
				<td><input size="35" type="text" name="nums" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
