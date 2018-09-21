<%--
	time:2013-07-09 17:06:02
	desc:edit the cloud_crowdsourcing_require
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 研发众包需求</title>
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
			var frm=$('#crowdsourcingRequireForm').form();
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
						window.location.href = "${ctx}/cloud/crowdsourcingRequire/crowdsourcingRequire/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#crowdsourcingRequireForm').attr('action','apply.ht');
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		$("a.agree").click(function() {
			operatorType=1;
			var tmp = '1';
			/*if(isDirectComlete){//直接一票通过
				tmp = '5';
			}*/
			$('#voteAgree').val(tmp);
			$('#back').val("1");

			$('#crowdsourcingRequireForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		$("a.notAgree").click(function() {
			
			operatorType=2;
			var tmp = '2';
			/*if(isDirectComlete){//直接一票通过
				tmp = '6';
			}*/
			$('#voteAgree').val(tmp);
			$('#back').val("1");
			
			$('#crowdsourcingRequireForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${crowdsourcingRequire.id !=null}">
			        <span class="tbar-label">编辑研发众包需求</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加研发众包需求</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<c:if test="${applyFlag==0}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link apply" id="dataFormStart" href="javascript:void(0)">申请</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht">返回</a></div>
				</c:if>
				<c:if test="${applyFlag==1}">
					<div class="group"><a id="btnAgree" class="link agree">同意</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="btnNotAgree" class="link notAgree">反对</a></div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="crowdsourcingRequireForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">需求编号: </th>
					<td><input type="text" id="code" name="code" value="${crowdsourcingRequire.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">需求名称: </th>
					<td><input type="text" id="requireName" name="requireName" value="${crowdsourcingRequire.requireName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">需求分类: </th>
					<td><input type="text" id="requireType" name="requireType" value="${crowdsourcingRequire.requireType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">需求详细描述: </th>
					<td><input type="text" id="requireDescription" name="requireDescription" value="${crowdsourcingRequire.requireDescription}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">状态: </th>
					<td><input type="text" id="status" name="status" value="${crowdsourcingRequire.status}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">发布模式: </th>
					<td><input type="text" id="publishMode" name="publishMode" value="${crowdsourcingRequire.publishMode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">创建时间: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${crowdsourcingRequire.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">创建人ID: </th>
					<td><input type="text" id="operaterId" name="operaterId" value="${crowdsourcingRequire.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">创建人姓名: </th>
					<td><input type="text" id="operaterName" name="operaterName" value="${crowdsourcingRequire.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">所属企业ID: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${crowdsourcingRequire.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">所属企业名称: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" value="${crowdsourcingRequire.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">流程ID: </th>
					<td><input type="text" id="runId" name="runId" value="${crowdsourcingRequire.runId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">保证金: </th>
					<td><input type="text" id="bail" name="bail" value="${crowdsourcingRequire.bail}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">币种: </th>
					<td><input type="text" id="currency" name="currency" value="${crowdsourcingRequire.currency}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">入围审核要求列表: </th>
					<td><input type="text" id="finalists" name="finalists" value="${crowdsourcingRequire.finalists}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">公告截止日期: </th>
					<td><input type="text" id="announcementDeadline" name="announcementDeadline" value="<fmt:formatDate value='${crowdsourcingRequire.announcementDeadline}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">要求完成时间: </th>
					<td><input type="text" id="requiredCompleteTime" name="requiredCompleteTime" value="<fmt:formatDate value='${crowdsourcingRequire.requiredCompleteTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">附件详情（多个）: </th>
					<td><input type="text" id="attachmentDetail" name="attachmentDetail" value="${crowdsourcingRequire.attachmentDetail}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">研发物品ID: </th>
					<td><input type="text" id="materialId" name="materialId" value="${crowdsourcingRequire.materialId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">研发物品CODE: </th>
					<td><input type="text" id="materialCode" name="materialCode" value="${crowdsourcingRequire.materialCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">研发物品名称: </th>
					<td><input type="text" id="materialName" name="materialName" value="${crowdsourcingRequire.materialName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">研发物品类型: </th>
					<td><input type="text" id="materialType" name="materialType" value="${crowdsourcingRequire.materialType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">研发物品BOM层级: </th>
					<td><input type="text" id="materialBomLevel" name="materialBomLevel" value="${crowdsourcingRequire.materialBomLevel}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">受邀研发组织ID: </th>
					<td><input type="text" id="invitedOrgId" name="invitedOrgId" value="${crowdsourcingRequire.invitedOrgId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">受邀研发组织名称: </th>
					<td><input type="text" id="invitedOrgName" name="invitedOrgName" value="${crowdsourcingRequire.invitedOrgName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">受邀研发人员IDs: </th>
					<td><input type="text" id="invitedUserIds" name="invitedUserIds" value="${crowdsourcingRequire.invitedUserIds}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">受邀所有研发人员姓名: </th>
					<td><input type="text" id="invitedUserNames" name="invitedUserNames" value="${crowdsourcingRequire.invitedUserNames}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">受邀研发组IDs: </th>
					<td><input type="text" id="invitedGroupIds" name="invitedGroupIds" value="${crowdsourcingRequire.invitedGroupIds}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">共享文件夹路径: </th>
					<td><input type="text" id="sharedPath" name="sharedPath" value="${crowdsourcingRequire.sharedPath}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">研发资源路径: </th>
					<td><input type="text" id="resourcePath" name="resourcePath" value="${crowdsourcingRequire.resourcePath}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="crowdsourcingRequireDetail" formType="window" type="sub">
				<tr>
					<td colspan="7">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						研发众包需求 : 研发众包需求附件详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>附件名</th>
					<th>附件分类</th>
					<th>附件说明</th>
					<th>上传者ID</th>
					<th>上传者姓名</th>
					<th>附件管理ID</th>
					<th>操作权限</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${crowdsourcingRequireDetailList}" var="crowdsourcingRequireDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="attachmentName">${crowdsourcingRequireDetailItem.attachmentName}</td>
					    <td style="text-align: center" name="attachmentType">${crowdsourcingRequireDetailItem.attachmentType}</td>
					    <td style="text-align: center" name="attachmentInfo">${crowdsourcingRequireDetailItem.attachmentInfo}</td>
					    <td style="text-align: center" name="uploadId">${crowdsourcingRequireDetailItem.uploadId}</td>
					    <td style="text-align: center" name="uploadName">${crowdsourcingRequireDetailItem.uploadName}</td>
					    <td style="text-align: center" name="attachmentManageId">${crowdsourcingRequireDetailItem.attachmentManageId}</td>
					    <td style="text-align: center" name="operatingAuthority">${crowdsourcingRequireDetailItem.operatingAuthority}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="attachmentName" value="${crowdsourcingRequireDetailItem.attachmentName}"/>
						<input type="hidden" name="attachmentType" value="${crowdsourcingRequireDetailItem.attachmentType}"/>
						<input type="hidden" name="attachmentInfo" value="${crowdsourcingRequireDetailItem.attachmentInfo}"/>
						<input type="hidden" name="uploadId" value="${crowdsourcingRequireDetailItem.uploadId}"/>
						<input type="hidden" name="uploadName" value="${crowdsourcingRequireDetailItem.uploadName}"/>
						<input type="hidden" name="attachmentManageId" value="${crowdsourcingRequireDetailItem.attachmentManageId}"/>
						<input type="hidden" name="operatingAuthority" value="${crowdsourcingRequireDetailItem.operatingAuthority}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="attachmentName"></td>
			    	<td style="text-align: center" name="attachmentType"></td>
			    	<td style="text-align: center" name="attachmentInfo"></td>
			    	<td style="text-align: center" name="uploadId"></td>
			    	<td style="text-align: center" name="uploadName"></td>
			    	<td style="text-align: center" name="attachmentManageId"></td>
			    	<td style="text-align: center" name="operatingAuthority"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="attachmentName" value=""/>
			    	<input type="hidden" name="attachmentType" value=""/>
			    	<input type="hidden" name="attachmentInfo" value=""/>
			    	<input type="hidden" name="uploadId" value=""/>
			    	<input type="hidden" name="uploadName" value=""/>
			    	<input type="hidden" name="attachmentManageId" value=""/>
			    	<input type="hidden" name="operatingAuthority" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${crowdsourcingRequire.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="crowdsourcingRequireDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">附件名: </th>
				<td><input type="text" name="attachmentName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">附件分类: </th>
				<td><input type="text" name="attachmentType" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">附件说明: </th>
				<td><input type="text" name="attachmentInfo" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">上传者ID: </th>
				<td><input type="text" name="uploadId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">上传者姓名: </th>
				<td><input type="text" name="uploadName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">附件管理ID: </th>
				<td><input type="text" name="attachmentManageId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">操作权限: </th>
				<td><input type="text" name="operatingAuthority" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
