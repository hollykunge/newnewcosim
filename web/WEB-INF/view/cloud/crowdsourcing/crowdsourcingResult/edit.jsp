<%--
	time:2013-07-09 17:15:19
	desc:edit the cloud_crowdsourcing_result
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_crowdsourcing_result</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function(){	
		var resultName="${crowdsourcingResult.resultName}";
		var resultAttachmentIds="${crowdsourcingResult.resultAttachmentIds}";
		var nameArr = resultName.split(",");
		var idArr = resultAttachmentIds.split(",");
		$("#resultName").val(resultName);
		for(var i=0;i<nameArr.length;i++){
			var tr=getRow(idArr[i],nameArr[i]);
			$("#attachmentTable").prepend(tr);
		}
		
	});
	
	function getRow(fileId,fileName){
		var tr = '<tr class="'+fileId+'">';
		tr += '<td>';
		tr += '	<span class="attach">&ensp;&ensp; </span>&nbsp;&nbsp;';
		tr += '	<a href="javascript:void(0)" onclick=javascript:downloadFile('+fileId+'); >'+fileName+'</a> ';
		tr += '</td>';
		tr += '<td><a href="javascript:void(0)" onclick=javascript:downloadFile('+fileId+'); ><span class="link-btn link-remove">查看 </span></a></td>';
		tr += '</tr>';
		return tr;
	}
	
	function downloadFile(fileId){
		var url = "${ctx}/platform/system/sysFile/download.ht?fileId="+fileId;
		window.open(url);
	}
	
		
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#crowdsourcingResultForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
	
		$("a.apply").click(function() {
			frm.setData();
			$('#crowdsourcingResultForm').attr('action','apply.ht');
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

			$('#crowdsourcingResultForm').attr("action","complete.ht");
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
			
			$('#crowdsourcingResultForm').attr("action","complete.ht");
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
			    <c:when test="${crowdsourcingResult.id !=null}">
			        <span class="tbar-label">编辑cloud_crowdsourcing_result</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_crowdsourcing_result</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		
	    <div class="panel-toolbar" style="display:none">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back " href="list.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="crowdsourcingResultForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">表单编号 </th>
					<td>
					
			<input type="hidden" name="id" value="${crowdsourcingResult.id}" />
					<input type="text" id="code" name="code" value="${crowdsourcingResult.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${crowdsourcingResult.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
<!-- 					<th width="20%">提交人ID: </th> -->
					<input type="hidden" id="operaterId" name="operaterId" value="${crowdsourcingResult.operaterId}"  class="inputText" validate="{required:false,number:true }"  />
				<input type="hidden" id="resultName" name="resultName"   value="${crowdsourcingResult.resultName}"   />
<!-- 					<th width="20%">提交人企业ID: </th> -->
					<input type="hidden" id="operaterEnterpId" name="operaterEnterpId" value="${crowdsourcingResult.operaterEnterpId}"  class="inputText" validate="{required:false,number:true }"  />
				
<!-- 					<th width="20%">来源众包需求表ID: </th> -->
					<input type="hidden" id="sourceformCrowdsourcingId" name="sourceformCrowdsourcingId" value="${crowdsourcingResult.sourceformCrowdsourcingId}"  class="inputText" validate="{required:false,number:true }"  />
				
<!-- 					<th width="20%">来源合同ID: </th> -->
					
					<input type="hidden" id="sourceformAgreementId" name="sourceformAgreementId" value="${crowdsourcingResult.sourceformAgreementId}"  class="inputText" validate="{required:false,number:true }"  />
				
<!-- 					<th width="20%">来源合同CODE: </th> -->
					<input type="hidden" id="sourceformAgreementCode" name="sourceformAgreementCode" value="${crowdsourcingResult.sourceformAgreementCode}"  class="inputText" validate="{required:false,maxlength:96}"  />
				
<!--					<th width="20%">结果附件IDs: </th>-->
					<input type="hidden" id="resultAttachmentIds" name="resultAttachmentIds" value="${crowdsourcingResult.resultAttachmentIds}"  class="inputText" validate="{required:false,maxlength:384}"  />
				
				</tr>
				<tr>
					<th width="20%">提交人名称: </th>
					<td><input type="text" id="operaterName" readonly class="r" name="operaterName" value="${crowdsourcingResult.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">提交人企业名称: </th>
					<td><input type="text" id="operaterEnterpName" readonly class="r" name="operaterEnterpName" value="${crowdsourcingResult.operaterEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr></tr>
				<tr>
					<th width="20%">来源众包需求表编号: </th>
					<td><input type="text" id="sourceformCrowdsourcingCode" readonly class="r" name="sourceformCrowdsourcingCode" value="${crowdsourcingResult.sourceformCrowdsourcingCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">状态: </th>
					<td><input type="text" id="state" name="state" readonly class="r" value="${crowdsourcingResult.state}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">审核类型: </th>
					<td><input type="text" id="auditType" name="auditType" readonly class="r" value="${crowdsourcingResult.auditType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">审核意见: </th>
					<td><input type="text" id="auditOpinion" name="auditOpinion" value="${crowdsourcingResult.auditOpinion}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">结果包说明: </th>
					<td><input type="text" id="resultInfo" name="resultInfo" readonly class="r" value="${crowdsourcingResult.resultInfo}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
					<th width="20%">结果包名称: </th>
					<td>
					<div>
						<table  width="145" name="attachmentTable"   id="attachmentTable" class="table-grid table-list"  cellpadding="2" cellspacing="2">
						</table>
					</div>
					</td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="crowdsourcingResultDetail" formType="window" type="sub">
				<tr>
					<td colspan="8">
						<div class="group" align="left" style="display:none">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						研发结果历史详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>结果包名称</th>
					<th>结果包说明</th>
					<th>审核类型</th>
					<th>审核意见</th>
					<th style="display:none">结果附件IDs</th>
					<th>提交时间</th>
					<th>审核时间</th>
					<th style="display:none">操作</th>
				</tr>
				<c:forEach items="${crowdsourcingResultDetailList}" var="crowdsourcingResultDetailItem" varStatus="status">
				    <tr type="subdata">
					    <%-- <td style="text-align: center" name="resultName"><a href="javascript:void(0)" onclick=javascript:downloadFile('${crowdsourcingResultDetailItem.resultAttachmentIds}'); >${crowdsourcingResultDetailItem.resultName}<span class="link-btn link-remove">查看 </span></a></td> --%>
					   <td style="text-align: center;white-space:nowrap;" name="resultName">
			    			<c:forEach items='${fn:split(crowdsourcingResultDetailItem.resultName,",")}' var='d' varStatus="s">
			    				&nbsp;&nbsp;&nbsp;&nbsp;${d}&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getFileInfo('${ctx}',${fn:split(crowdsourcingResultDetailItem.resultAttachmentIds,",")[s.index]});">查看信息</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:openFile('${ctx}',${fn:split(crowdsourcingResultDetailItem.resultAttachmentIds,",")[s.index]}));">在线浏览</a>
			    			</c:forEach>
				    	</td>
					    <td style="text-align: center" name="resultInfo">${crowdsourcingResultDetailItem.resultInfo}</td>
					    <td style="text-align: center" name="auditType">${crowdsourcingResultDetailItem.auditType}</td>
					    <td style="text-align: center" name="auditOpinion">${crowdsourcingResultDetailItem.auditOpinion}</td>
					    <td style="display:none" name="resultAttachmentIds">${crowdsourcingResultDetailItem.resultAttachmentIds}</td>
						<td style="text-align: center" name="submitTime"><fmt:formatDate value='${crowdsourcingResultDetailItem.submitTime}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="auditTime"><fmt:formatDate value='${crowdsourcingResultDetailItem.auditTime}' pattern='yyyy-MM-dd'/></td>								
					    <td style="display:none">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="resultName" value="${crowdsourcingResultDetailItem.resultName}"/>
						<input type="hidden" name="resultInfo" value="${crowdsourcingResultDetailItem.resultInfo}"/>
						<input type="hidden" name="auditType" value="${crowdsourcingResultDetailItem.auditType}"/>
						<input type="hidden" name="auditOpinion" value="${crowdsourcingResultDetailItem.auditOpinion}"/>
						<input type="hidden" name="resultAttachmentIds" value="${crowdsourcingResultDetailItem.resultAttachmentIds}"/>
					    <input type="hidden" name="submitTime" value="<fmt:formatDate value='${crowdsourcingResultDetailItem.submitTime}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="auditTime" value="<fmt:formatDate value='${crowdsourcingResultDetailItem.auditTime}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="resultName"></td>
			    	<td style="text-align: center" name="resultInfo"></td>
			    	<td style="text-align: center" name="auditType"></td>
			    	<td style="text-align: center" name="auditOpinion"></td>
			    	<td style="display:none" name="resultAttachmentIds"></td>
					<td style="text-align: center" name="submitTime"></td>								
					<td style="text-align: center" name="auditTime"></td>								
			    	<td style="display:none">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="resultName" value=""/>
			    	<input type="hidden" name="resultInfo" value=""/>
			    	<input type="hidden" name="auditType" value=""/>
			    	<input type="hidden" name="auditOpinion" value=""/>
			    	<input type="hidden" name="resultAttachmentIds" value=""/>
			    	<input type="hidden" name="submitTime" value="" class="inputText date"/>
			    	<input type="hidden" name="auditTime" value="" class="inputText date"/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${crowdsourcingResult.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="crowdsourcingResultDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">结果包名称: </th>
				<td><input type="text" name="resultName" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr>
				<th width="20%">结果包说明: </th>
				<td><input type="text" name="resultInfo" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr>
				<th width="20%">审核类型: </th>
				<td><input type="text" name="auditType" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">审核意见: </th>
				<td><input type="text" name="auditOpinion" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr style="display:none">
				<th width="20%">结果附件IDs: </th>
				<td><input type="text" name="resultAttachmentIds" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr>
				<th width="20%">提交时间: </th>
				<td><input type="text" name="submitTime" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">审核时间: </th>
				<td><input type="text" name="auditTime" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
		</table>
	</form>
</div>
</body>
</html>