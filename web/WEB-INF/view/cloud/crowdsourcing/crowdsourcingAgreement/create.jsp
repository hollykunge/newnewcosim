<%--
	time:2013-07-09 16:59:12
	desc:edit the cloud_crowdsourcing_agreement
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 研发众包合同</title>
	<%@include file="/commons/include/form.jsp" %>
	<style type="text/css">
		.myth{
		    background-color: #EBF5FF;
		    border: 1px solid #7BABCF;
		    font-size: 12px;
		    width:20%;
		    height: 32px;
		    padding-right: 6px;
		    text-align: right;
		}
	</style>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#crowdsourcingAgreementForm').form();
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
						window.location.href = "${ctx}/cloud/crowdsourcingAgreement/crowdsourcingAgreement/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
		$("a.apply").click(function() {
			frm.setData();
			$('#crowdsourcingAgreementForm').attr('action','apply.ht');
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

			$('#crowdsourcingAgreementForm').attr("action","complete.ht");
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
			
			$('#crowdsourcingAgreementForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		function selectedProvider(quoteId){
			$.ajax({
				   type: "POST",
				   url:  "${ctx}/cloud/crowdsourcing/crowdsourcingAgreement/selectedProvider.ht",
				   data: "quoteId=" + quoteId,
				   success: function(msg){
				    	$('#receiveOrgId').val(msg.receiveOrgId);
				    	$('#receiveOrgName').val(msg.receiveOrgName);
				    	$('#receiverId').val(msg.receiverId);
				    	$('#receiverName').val(msg.receiverName);
				    	$('#agreePrice').val(msg.agreePrice);
				   },
				   error:function(){
					   alert("error");
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
			    <c:when test="${crowdsourcingAgreement.id !=null}">
			        <span class="tbar-label">编辑研发众包合同</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加研发众包合同</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		
	    
		<div class="panel-toolbar" style="display:none">
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
		<form id="crowdsourcingAgreementForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
<!-- 					<th width="20%">制单人ID: </th> -->
					<input type="hidden" id="operaterId" name="operaterId" value="${crowdsourcingAgreement.operaterId}"  class="inputText" validate="{required:false,number:true }"  />
				
<!-- 					<th width="20%">制单企业ID: </th> -->
					<input type="hidden" id="operaterEnterpId" name="operaterEnterpId" value="${crowdsourcingAgreement.operaterEnterpId}"  class="inputText" validate="{required:false,number:true }"  />
				
<!-- 					<th width="20%">来源众包需求表ID: </th> -->
					<input type="hidden" id="sourceformCrowdsourcingId" name="sourceformCrowdsourcingId" value="${crowdsourcingAgreement.sourceformCrowdsourcingId}"  class="inputText" validate="{required:false,number:true }"  />
				
<!-- 					<th width="20%">接包方组织ID: </th> -->
					<input type="hidden" id="receiveOrgId" name="receiveOrgId" value="${crowdsourcingAgreement.receiveOrgId}"  class="inputText" validate="{required:false,number:true }"  />
			
<!-- 					<th width="20%">接包人ID: </th> -->
					<input type="hidden" id="receiverId" name="receiverId" value="${crowdsourcingAgreement.receiverId}"  class="inputText" validate="{required:false,number:true }"  />
				
<!-- 					<th width="20%">研发物品ID: </th> -->
					<input type="hidden" id="materialId" name="materialId" value="${crowdsourcingAgreement.materialId}"  class="inputText" validate="{required:false,number:true }"  />
				
				</tr>
				<tr>
					<input type="hidden" name="id" value="${crowdsourcingAgreement.id}" />
					<th width="20%">合同单号: </th>
					<td><input type="text" id="code" name="code" value="${crowdsourcingAgreement.code}" readonly="readonly" class="inputText" validate="{required:false,maxlength:192}"  /></td>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${crowdsourcingAgreement.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">制单人名称: </th>
					<td><input type="text" id="operaterName" name="operaterName" value="${crowdsourcingAgreement.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">制单企业名称: </th>
					<td><input type="text" id="operaterEnterpName" name="operaterEnterpName" value="${crowdsourcingAgreement.operaterEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">发布状态: </th>
					<td><input type="text" id="publishStatus" name="publishStatus" value="${crowdsourcingAgreement.publishStatus}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">接包方签署状态: </th>
					<td><input type="text" id="signedState" name="signedState" value="${crowdsourcingAgreement.signedState}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">签署意见说明: </th>
					<td><input type="text" id="signedOpinion" name="signedOpinion" value="${crowdsourcingAgreement.signedOpinion}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">来源众包需求表编号: </th>
					<td><input type="text" id="sourceformCrowdsourcingCode" name="sourceformCrowdsourcingCode" value="${crowdsourcingAgreement.sourceformCrowdsourcingCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">接包方组织名称: </th>
					<td><input type="text" id="receiveOrgName" name="receiveOrgName" value="${crowdsourcingAgreement.receiveOrgName}"  readonly="readonly"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">接包人名称: </th>
					<td><input type="text" id="receiverName" name="receiverName" value="${crowdsourcingAgreement.receiverName}"  readonly="readonly"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">协议价格: </th>
					<td><input type="text" id="agreePrice" name="agreePrice" value="${crowdsourcingAgreement.agreePrice}"  readonly="readonly"  class="inputText" validate="{required:false}"  /></td>
					<th width="20%">币种: </th>
					<td><input type="text" id="currency" name="currency" value="${crowdsourcingAgreement.currency}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">研发物品编号: </th>
					<td><input type="text" id="materialCode" name="materialCode" value="${crowdsourcingAgreement.materialCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">研发物品名称: </th>
					<td><input type="text" id="materialName" name="materialName" value="${crowdsourcingAgreement.materialName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">研发物品分类: </th>
					<td><input type="text" id="materialType" name="materialType" value="${crowdsourcingAgreement.materialType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">研发任务完成时间: </th>
					<td><input type="text" id="completeTime" name="completeTime" value="<fmt:formatDate value='${crowdsourcingAgreement.completeTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">研发物品bom层级: </th>
					<td><input type="text" id="materialBomLevel" name="materialBomLevel" value="${crowdsourcingAgreement.materialBomLevel}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="crowdsourcingAgreementDetail" formType="window" type="sub">
				<tr>
					<td colspan="8">
						<!-- <div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div> -->
			    		<div align="center">
						合同附件详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>附件名</th>
					<th>附件分类</th>
					<th>附件说明</th>
					<!-- <th>上传者ID</th> -->
					<th>上传者姓名</th>
					<!-- <th>附件管理ID</th> -->
					<!-- <th>操作权限</th> -->
					<!-- <th>操作</th> -->
				</tr>
				<c:forEach items="${crowdsourcingAgreementDetailList}" var="crowdsourcingAgreementDetailItem" varStatus="status">
				    <tr type="subdata">
				   		<c:if test="${crowdsourcingAgreementDetailItem.operatingAuthority=='0'}">
				    		<td style="text-align: center" name="attachmentName"> ${crowdsourcingAgreementDetailItem.attachmentName}&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getFileInfo('${ctx}',${crowdsourcingAgreementDetailItem.attachmentManageId});">查看信息</a></td>
				    	</c:if>
				    	<c:if test="${crowdsourcingAgreementDetailItem.operatingAuthority=='1'}">
				    		<td style="text-align: center" name="attachmentName"> ${crowdsourcingAgreementDetailItem.attachmentName}&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getFileInfo('${ctx}',${crowdsourcingAgreementDetailItem.attachmentManageId});">查看信息</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:openFile('${ctx}',${crowdsourcingAgreementDetailItem.attachmentManageId});">在线浏览</a></td>
				    	</c:if>
				    	<c:if test="${crowdsourcingAgreementDetailItem.operatingAuthority=='2'}">
				    		<td style="text-align: center" name="attachmentName"> ${crowdsourcingAgreementDetailItem.attachmentName}&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getFileInfo('${ctx}',${crowdsourcingAgreementDetailItem.attachmentManageId});">查看信息</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:openFile('${ctx}',${crowdsourcingAgreementDetailItem.attachmentManageId});">在线浏览</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:downloadFile('${ctx}',${crowdsourcingAgreementDetailItem.attachmentManageId});">下载</a></td>
				    	</c:if>
					    <%-- <td style="text-align: center" name="attachmentName">${crowdsourcingAgreementDetailItem.attachmentName}&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getFileInfo('${ctx}',${crowdsourcingResponseDetailItem.attachmentManageId});">查看信息</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:openFile('${ctx}',${crowdsourcingResponseDetailItem.attachmentManageId});">在线浏览</a></td> --%>
					    <td style="text-align: center" name="attachmentType">${crowdsourcingAgreementDetailItem.attachmentType}</td>
					    <td style="text-align: center" name="attachmentInfo">${crowdsourcingAgreementDetailItem.attachmentInfo}</td>
					    <td style="display: none;" name="uploadId">${crowdsourcingAgreementDetailItem.uploadId}</td>
					    <td style="text-align: center" name="uploadName">${crowdsourcingAgreementDetailItem.uploadName}</td>
					    <td style="display: none;" name="attachmentManageId">${crowdsourcingAgreementDetailItem.attachmentManageId}</td>
					    <%-- <td style="text-align: center" name="operatingAuthority">${crowdsourcingAgreementDetailItem.operatingAuthority}</td> --%>
					    <!-- <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td> -->
						<input type="hidden" name="attachmentName" value="${crowdsourcingAgreementDetailItem.attachmentName}"/>
						<input type="hidden" name="attachmentType" value="${crowdsourcingAgreementDetailItem.attachmentType}"/>
						<input type="hidden" name="attachmentInfo" value="${crowdsourcingAgreementDetailItem.attachmentInfo}"/>
						<input type="hidden" name="uploadId" value="${crowdsourcingAgreementDetailItem.uploadId}"/>
						<input type="hidden" name="uploadName" value="${crowdsourcingAgreementDetailItem.uploadName}"/>
						<input type="hidden" name="attachmentManageId" value="${crowdsourcingAgreementDetailItem.attachmentManageId}"/>
						<input type="hidden" name="operatingAuthority" value="${crowdsourcingAgreementDetailItem.operatingAuthority}"/>
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

			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<fieldset>
	
	<!-- 选择中标研发企业或个人 -->	
	<legend>报价企业</legend>
	<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="purOptimizeDetail" formType="window" type="sub">			
		<c:forEach items="${crowdsourcingResponseList}"  var="crowdsourcingResponse">
			<tr><td colspan="6">
			            设置该价格为中标价格:
			    <input type="radio"  name="isCheck" value="${crowdsourcingResponse.id}" onclick="selectedProvider(${crowdsourcingResponse.id})" validate="{required:true}">
			</td>
			</tr>
				<tr>
					<td class="myth">单证号:</th>
					<td>${crowdsourcingResponse.code}</td>
					<td class="myth">制单日期:</th>
					<td>
						<fmt:formatDate value="${crowdsourcingResponse.operateDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td class="myth">报价个人:</th>
					<td><a target="_blank" href="${ctx}/cloud/reg/register/getUser.ht?userId=${crowdsourcingResponse.operaterId}">${crowdsourcingResponse.operaterName}</a></td>	
						
				</tr>
				<tr>
					<td class="myth">报价企业:</th>
					<td>${crowdsourcingResponse.operaterEnterpName}</td>	
					<td class="myth">报价:</th>
					<td>${crowdsourcingResponse.quote}</td>					
					<td class="myth">预计完成时间:</th>
					<td><fmt:formatDate value='${crowdsourcingResponse.forecastCompleteTime}' pattern='yyyy-MM-dd'/></td>
				</tr>
				
		</c:forEach>
	</table>
			
	</fieldset>
	
	<form id="crowdsourcingAgreementDetailForm" style="display:none">
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
