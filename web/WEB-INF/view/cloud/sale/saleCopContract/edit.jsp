<%--
	time:2013-04-26 14:32:25
	desc:edit the cloud_sale_copcontract
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 营销合同</title>
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
			var frm=$('#saleCopContractForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
/* 		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/saleCopContract/saleCopContract/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
		$("a.apply").click(function() {
			frm.setData();
			$('#saleCopContractForm').attr('action','apply.ht');
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

			$('#saleCopContractForm').attr("action","complete.ht");
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
			
			$('#saleCopContractForm').attr("action","complete.ht");
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
			    <c:when test="${saleCopContract.id !=null}">
			        <span class="tbar-label">编辑营销合同</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加营销合同</span>
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
		<form id="saleCopContractForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td><input type="text" id="code" name="code" value="${saleCopContract.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${saleCopContract.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operaterId" name="operaterId" value="${saleCopContract.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">制单人: </th>
					<td><input type="text" id="operaterName" name="operaterName" value="${saleCopContract.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">合作营销区域: </th>
					<td><input type="text" id="cooperationArea" name="cooperationArea" value="${saleCopContract.cooperationArea}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">合作开始时间: </th>
					<td><input type="text" id="cooperationStarttime" name="cooperationStarttime" value="<fmt:formatDate value='${saleCopContract.cooperationStarttime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">合作截止时间: </th>
					<td><input type="text" id="cooperationEndtime" name="cooperationEndtime" value="<fmt:formatDate value='${saleCopContract.cooperationEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">库存上报时间间隔: </th>
					<td><input type="text" id="reportstockTimespace" name="reportstockTimespace" value="${saleCopContract.reportstockTimespace}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">销售订单上报时间间隔: </th>
					<td><input type="text" id="reportsalesTimespace" name="reportsalesTimespace" value="${saleCopContract.reportsalesTimespace}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">信用额度下限(金额）: </th>
					<td><input type="text" id="creditEndline" name="creditEndline" value="${saleCopContract.creditEndline}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">附件: </th>
					<td><input type="text" id="attachment" name="attachment" value="${saleCopContract.attachment}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">供应企业ID: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${saleCopContract.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">供应企业: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" value="${saleCopContract.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">供应企业联系人ID: </th>
					<td><input type="text" id="enterpriseUserid" name="enterpriseUserid" value="${saleCopContract.enterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">供应企业联系人: </th>
					<td><input type="text" id="enterpriseUsername" name="enterpriseUsername" value="${saleCopContract.enterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">合作企业ID: </th>
					<td><input type="text" id="coopenterpId" name="coopenterpId" value="${saleCopContract.coopenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">合作企业: </th>
					<td><input type="text" id="coopenterpName" name="coopenterpName" value="${saleCopContract.coopenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">合作企业联系人ID: </th>
					<td><input type="text" id="coopenterpUserid" name="coopenterpUserid" value="${saleCopContract.coopenterpUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">合作企业联系人: </th>
					<td><input type="text" id="coopenterpUsername" name="coopenterpUsername" value="${saleCopContract.coopenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="saleCopContractDetail" formType="window" type="sub">
				<tr>
					<td colspan="12">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						营销合同 : 营销合同详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>物品ID</th>
					<th>物品</th>
					<th>编码</th>
					<th>编码参考标准</th>
					<th>分类</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>数量</th>
					<th>信用额度（数量）</th>
					<th>单价</th>
					<th>库存下限</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${saleCopContractDetailList}" var="saleCopContractDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materielId">${saleCopContractDetailItem.materielId}</td>
					    <td style="text-align: center" name="materielName">${saleCopContractDetailItem.materielName}</td>
					    <td style="text-align: center" name="materielCode">${saleCopContractDetailItem.materielCode}</td>
					    <td style="text-align: center" name="materielCodenote">${saleCopContractDetailItem.materielCodenote}</td>
					    <td style="text-align: center" name="materielLev">${saleCopContractDetailItem.materielLev}</td>
					    <td style="text-align: center" name="attributeInfo">${saleCopContractDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${saleCopContractDetailItem.unit}</td>
					    <td style="text-align: center" name="materielNum">${saleCopContractDetailItem.materielNum}</td>
					    <td style="text-align: center" name="creditLevel">${saleCopContractDetailItem.creditLevel}</td>
					    <td style="text-align: center" name="prise">${saleCopContractDetailItem.prise}</td>
					    <td style="text-align: center" name="stockEndline">${saleCopContractDetailItem.stockEndline}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="materielId" value="${saleCopContractDetailItem.materielId}"/>
						<input type="hidden" name="materielName" value="${saleCopContractDetailItem.materielName}"/>
						<input type="hidden" name="materielCode" value="${saleCopContractDetailItem.materielCode}"/>
						<input type="hidden" name="materielCodenote" value="${saleCopContractDetailItem.materielCodenote}"/>
						<input type="hidden" name="materielLev" value="${saleCopContractDetailItem.materielLev}"/>
						<input type="hidden" name="attributeInfo" value="${saleCopContractDetailItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${saleCopContractDetailItem.unit}"/>
						<input type="hidden" name="materielNum" value="${saleCopContractDetailItem.materielNum}"/>
						<input type="hidden" name="creditLevel" value="${saleCopContractDetailItem.creditLevel}"/>
						<input type="hidden" name="prise" value="${saleCopContractDetailItem.prise}"/>
						<input type="hidden" name="stockEndline" value="${saleCopContractDetailItem.stockEndline}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="materielId"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="text-align: center" name="materielCodenote"></td>
			    	<td style="text-align: center" name="materielLev"></td>
			    	<td style="text-align: center" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="materielNum"></td>
			    	<td style="text-align: center" name="creditLevel"></td>
			    	<td style="text-align: center" name="prise"></td>
			    	<td style="text-align: center" name="stockEndline"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="materielId" value=""/>
			    	<input type="hidden" name="materielName" value=""/>
			    	<input type="hidden" name="materielCode" value=""/>
			    	<input type="hidden" name="materielCodenote" value=""/>
			    	<input type="hidden" name="materielLev" value=""/>
			    	<input type="hidden" name="attributeInfo" value=""/>
			    	<input type="hidden" name="unit" value=""/>
			    	<input type="hidden" name="materielNum" value=""/>
			    	<input type="hidden" name="creditLevel" value=""/>
			    	<input type="hidden" name="prise" value=""/>
			    	<input type="hidden" name="stockEndline" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${saleCopContract.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="saleCopContractDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">物品ID: </th>
				<td><input type="text" name="materielId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">物品: </th>
				<td><input type="text" name="materielName" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">编码: </th>
				<td><input type="text" name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">编码参考标准: </th>
				<td><input type="text" name="materielCodenote" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">分类: </th>
				<td><input type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">属性说明: </th>
				<td><input type="text" name="attributeInfo" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="unit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">数量: </th>
				<td><input type="text" name="materielNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">信用额度（数量）: </th>
				<td><input type="text" name="creditLevel" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">单价: </th>
				<td><input type="text" name="prise" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">库存下限: </th>
				<td><input type="text" name="stockEndline" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
