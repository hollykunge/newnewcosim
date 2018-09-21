<%--
	time:2013-04-19 11:18:08
	desc:edit the cloud_manuf_optimize
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 委外加工优选单</title>
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
			var frm=$('#manufOptimizeForm').form();
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
						window.location.href = "${ctx}/cloud/manufacture/manufOptimize/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#manufOptimizeForm').attr('action','apply.ht');
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

			$('#manufOptimizeForm').attr("action","complete.ht");
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
			
			$('#manufOptimizeForm').attr("action","complete.ht");
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
			    <c:when test="${manufOptimize.id !=null}">
			        <span class="tbar-label">编辑委外加工优选单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加委外加工优选单</span>
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
				<c:if test="${applyFlag==2}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht">返回</a></div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="manufOptimizeForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td><input type="text" id="code" name="code" value="${manufOptimize.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operatorId" name="operatorId" value="${manufOptimize.operatorId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">制单人姓名: </th>
					<td><input type="text" id="operatorName" name="operatorName" value="${manufOptimize.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${manufOptimize.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">中标企业ID: </th>
					<td><input type="text" id="selectedenterpId" name="selectedenterpId" value="${manufOptimize.selectedenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">中标企业: </th>
					<td><input type="text" id="selectedenterpName" name="selectedenterpName" value="${manufOptimize.selectedenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">中标企业联系人ID: </th>
					<td><input type="text" id="selectedenterpUserid" name="selectedenterpUserid" value="${manufOptimize.selectedenterpUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">中标企业联系人: </th>
					<td><input type="text" id="selectedenterpUsername" name="selectedenterpUsername" value="${manufOptimize.selectedenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">中标委外报价单ID: </th>
					<td><input type="text" id="quoteformId" name="quoteformId" value="${manufOptimize.quoteformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">中标委外报价单CODE: </th>
					<td><input type="text" id="quoteformCode" name="quoteformCode" value="${manufOptimize.quoteformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">结果说明: </th>
					<td><input type="text" id="resultNote" name="resultNote" value="${manufOptimize.resultNote}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				
					<th width="20%">运费承担方: </th>
					<td><input type="text" id="freightBearer" name="freightBearer" value="${manufOptimize.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">运输方式: </th>
					<td><input type="text" id="transportWay" name="transportWay" value="${manufOptimize.transportWay}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				
					<th width="20%">币种: </th>
					<td><input type="text" id="currencyType" name="currencyType" value="${manufOptimize.currencyType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">总金额: </th>
					<td><input type="text" id="sumPrice" name="sumPrice" value="${manufOptimize.sumPrice}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">预付款: </th>
					<td><input type="text" id="advancePay" name="advancePay" value="${manufOptimize.advancePay}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">预付款说明: </th>
					<td><input type="text" id="advancepayNote" name="advancepayNote" value="${manufOptimize.advancepayNote}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				
					<th width="20%">是否带料: </th>
					<td><input type="text" id="isBringmaterial" name="isBringmaterial" value="${manufOptimize.isBringmaterial}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">加工类型: </th>
					<td><input type="text" id="manufacturingType" name="manufacturingType" value="${manufOptimize.manufacturingType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${manufOptimize.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据号: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${manufOptimize.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">来源单据CODE: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${manufOptimize.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">询价发布日期: </th>
					<td><input type="text" id="releaseDate" name="releaseDate" value="<fmt:formatDate value='${manufOptimize.releaseDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				
					<th width="20%">询价企业ID: </th>
					<td><input type="text" id="enquiryenterpId" name="enquiryenterpId" value="${manufOptimize.enquiryenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">询价企业名称: </th>
					<td><input type="text" id="enquiryenterpName" name="enquiryenterpName" value="${manufOptimize.enquiryenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">询价企业联系人ID: </th>
					<td><input type="text" id="enquiryenterpUserid" name="enquiryenterpUserid" value="${manufOptimize.enquiryenterpUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">询价企业联系人: </th>
					<td><input type="text" id="enquiryenterpUsername" name="enquiryenterpUsername" value="${manufOptimize.enquiryenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">是否汇报加工进度: </th>
					<td><input type="text" id="isReport" name="isReport" value="${manufOptimize.isReport}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">备注: </th>
					<td><input type="text" id="comments" name="comments" value="${manufOptimize.comments}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="manufOptimizeDetail" formType="window" type="sub">
				<tr>
					<td colspan="6">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						委外加工优选单 : 委外加工优选单详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>报价企业ID</th>
					<th>报价企业名</th>
					<th>报价单ID</th>
					<th>报价单CODE</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${manufOptimizeDetailList}" var="manufOptimizeDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="quoteenterpId">${manufOptimizeDetailItem.quoteenterpId}</td>
					    <td style="text-align: center" name="quoteenterpName">${manufOptimizeDetailItem.quoteenterpName}</td>
					    <td style="text-align: center" name="quoteformId">${manufOptimizeDetailItem.quoteformId}</td>
					    <td style="text-align: center" name="quoteformCode">${manufOptimizeDetailItem.quoteformCode}</td>
					    <td style="text-align: center" name="comments">${manufOptimizeDetailItem.comments}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="quoteenterpId" value="${manufOptimizeDetailItem.quoteenterpId}"/>
						<input type="hidden" name="quoteenterpName" value="${manufOptimizeDetailItem.quoteenterpName}"/>
						<input type="hidden" name="quoteformId" value="${manufOptimizeDetailItem.quoteformId}"/>
						<input type="hidden" name="quoteformCode" value="${manufOptimizeDetailItem.quoteformCode}"/>
						<input type="hidden" name="comments" value="${manufOptimizeDetailItem.comments}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="quoteenterpId"></td>
			    	<td style="text-align: center" name="quoteenterpName"></td>
			    	<td style="text-align: center" name="quoteformId"></td>
			    	<td style="text-align: center" name="quoteformCode"></td>
			    	<td style="text-align: center" name="comments"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="quoteenterpId" value=""/>
			    	<input type="hidden" name="quoteenterpName" value=""/>
			    	<input type="hidden" name="quoteformId" value=""/>
			    	<input type="hidden" name="quoteformCode" value=""/>
			    	<input type="hidden" name="comments" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${manufOptimize.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="manufOptimizeDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">报价企业ID: </th>
				<td><input type="text" name="quoteenterpId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">报价企业名: </th>
				<td><input type="text" name="quoteenterpName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">报价单ID: </th>
				<td><input type="text" name="quoteformId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">报价单CODE: </th>
				<td><input type="text" name="quoteformCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">备注: </th>
				<td><input type="text" name="comments" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
