<%--
	time:2013-07-09 17:13:50
	desc:edit the cloud_crowdsourcing_response
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 需求响应表</title>
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
			var frm=$('#crowdsourcingResponseForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
		/* function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/crowdsourcingResponse/crowdsourcingResponse/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
		$("a.apply").click(function() {
			frm.setData();
			$('#crowdsourcingResponseForm').attr('action','apply.ht');
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
			    <c:when test="${crowdsourcingResponse.id !=null}">
			        <span class="tbar-label">编辑需求响应表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加需求响应表</span>
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
		<form id="crowdsourcingResponseForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">表单CODE: </th>
					<td><input type="text" id="code" name="code" value="${crowdsourcingResponse.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
<!-- 				</tr>
								<tr> -->
					<th width="20%">制表日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${crowdsourcingResponse.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				<tr style="display:none;">
					<th width="20%" >制表人ID: </th>
					<td><input type="text" id="operaterId" name="operaterId" value="${crowdsourcingResponse.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
 				</tr>
 				
 				
				<tr> 
					<th width="20%">制表企业名称: </th>
					<td><input type="text" id="operaterEnterpName" name="operaterEnterpName" value="${crowdsourcingResponse.operaterEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
<!-- 				</tr>				
				<tr> -->
					<th width="20%">制表人名称: </th>
					<td><input type="text" id="operaterName" name="operaterName" value="${crowdsourcingResponse.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr style="display:none;">
					<th width="20%">制表企业ID: </th>
					<td><input type="text" id="operaterEnterpId" name="operaterEnterpId" value="${crowdsourcingResponse.operaterEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
	

				<tr>
					<th width="20%">发布状态: </th>
					<td><input type="text" id="publishStatus" name="publishStatus" value="${crowdsourcingResponse.publishStatus}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
<!-- 				</tr>
				<tr> -->
					<th width="20%">来源众包需求表CODE: </th>
					<td><input type="text" id="sourceformCrowdsourcingCode" name="sourceformCrowdsourcingCode" value="${crowdsourcingResponse.sourceformCrowdsourcingCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr style="display:none;">
					<th width="20%">来源众包需求表ID: </th>
					<td><input type="text" id="sourceformCrowdsourcingId" name="sourceformCrowdsourcingId" value="${crowdsourcingResponse.sourceformCrowdsourcingId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
								
				<tr style="dispaly:none;">
					<th width="20%">研发物品ID: </th>
					<td><input type="text" id="materialId" name="materialId" value="${crowdsourcingResponse.materialId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				
				<tr>
					<th width="20%">研发物品CODE: </th>
					<td><input type="text" id="materialCode" name="materialCode" value="${crowdsourcingResponse.materialCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
<!-- 				</tr>
				<tr> -->
					<th width="20%">研发物品名称: </th>
					<td><input type="text" id="materialName" name="materialName" value="${crowdsourcingResponse.materialName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">研发物品分类: </th>
					<td><input type="text" id="materialType" name="materialType" value="${crowdsourcingResponse.materialType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
<!-- 				</tr>
				<tr> -->
					<th width="20%">研发物品BOM层级: </th>
					<td><input type="text" id="materialBomLevel" name="materialBomLevel" value="${crowdsourcingResponse.materialBomLevel}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">币种: </th>
					<td><input type="text" id="currency" name="currency" value="${crowdsourcingResponse.currency}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
<!-- 				</tr>
				<tr> -->
					<th width="20%">报价: </th>
					<td><input type="text" id="quote" name="quote" value="${crowdsourcingResponse.quote}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">预计完成时间: </th>
					<td><input type="text" id="forecastCompleteTime" name="forecastCompleteTime" value="<fmt:formatDate value='${crowdsourcingResponse.forecastCompleteTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
			</table>
			
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="crowdsourcingResponseDetail" formType="window" type="sub">
				<tr>
					<td colspan="7">
						<!-- <div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div> -->
			    		<div align="center">
						需求响应表 : 需求响应详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>附件名</th>
					<th>附件分类</th>
					<th>附件说明</th>
					<!-- <th>上传者ID</th> -->
					<th>上传者姓名</th>
					<th>附件管理ID</th>
					<!-- <th>操作权限</th> -->
					<!-- <th>操作</th> -->
				</tr>
				<c:forEach items="${crowdsourcingResponseDetailList}" var="crowdsourcingResponseDetailItem" varStatus="status">
				    <tr type="subdata">
				    	<c:if test="${crowdsourcingResponseDetailItem.operatingAuthority==0}">
				    		<td style="text-align: center" name="attachmentName">${crowdsourcingResponseDetailItem.attachmentName}&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getFileInfo('${ctx}',${crowdsourcingResponseDetailItem.attachmentManageId});">查看信息</a></td>
				    	</c:if>
				    	<c:if test="${crowdsourcingResponseDetailItem.operatingAuthority==1}">
				    		<td style="text-align: center" name="attachmentName">${crowdsourcingResponseDetailItem.attachmentName}&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getFileInfo('${ctx}',${crowdsourcingResponseDetailItem.attachmentManageId});">查看信息</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:openFile('${ctx}',${crowdsourcingResponseDetailItem.attachmentManageId});">在线浏览</a></td>
				    	</c:if>
				    	<c:if test="${crowdsourcingResponseDetailItem.operatingAuthority==2}">
				    		<td style="text-align: center" name="attachmentName">${crowdsourcingResponseDetailItem.attachmentName}&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getFileInfo('${ctx}',${crowdsourcingResponseDetailItem.attachmentManageId});">查看信息</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:openFile('${ctx}',${crowdsourcingResponseDetailItem.attachmentManageId});">在线浏览</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:downloadFile('${ctx}',${crowdsourcingResponseDetailItem.attachmentManageId});">下载</a></td>
				    	</c:if>
					    <td style="text-align: center" name="attachmentType">${crowdsourcingResponseDetailItem.attachmentType}</td>
					    <td style="text-align: center" name="attachmentInfo">${crowdsourcingResponseDetailItem.attachmentInfo}</td>
					    <td style="text-align: center;display:none;" name="uploadId">${crowdsourcingResponseDetailItem.uploadId}</td>
					    <td style="text-align: center" name="uploadName">${crowdsourcingResponseDetailItem.uploadName}</td>
					    <td style="text-align: center" name="attachmentManageId">${crowdsourcingResponseDetailItem.attachmentManageId}</td>
					    <td style="text-align: center;display:none;" name="operatingAuthority">${crowdsourcingResponseDetailItem.operatingAuthority}</td>
					    <!-- <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td> -->
						<input type="hidden" name="attachmentName" value="${crowdsourcingResponseDetailItem.attachmentName}"/>
						<input type="hidden" name="attachmentType" value="${crowdsourcingResponseDetailItem.attachmentType}"/>
						<input type="hidden" name="attachmentInfo" value="${crowdsourcingResponseDetailItem.attachmentInfo}"/>
						<input type="hidden" name="uploadId" value="${crowdsourcingResponseDetailItem.uploadId}"/>
						<input type="hidden" name="uploadName" value="${crowdsourcingResponseDetailItem.uploadName}"/>
						<input type="hidden" name="attachmentManageId" value="${crowdsourcingResponseDetailItem.attachmentManageId}"/>
						<input type="hidden" name="operatingAuthority" value="${crowdsourcingResponseDetailItem.operatingAuthority}"/>
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
			<input type="hidden" name="id" value="${crowdsourcingResponse.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
		</form>
		
	</div>
	<form id="crowdsourcingResponseDetailForm" style="display:none">
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
			<tr style="dispaly:none;">
				<th width="20%">上传者ID: </th>
				<td><input type="text" name="uploadId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">上传者姓名: </th>
				<td><input type="text" name="uploadName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr style="display:none;">
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
