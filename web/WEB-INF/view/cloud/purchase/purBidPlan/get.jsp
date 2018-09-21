
<%--
	time:2013-05-18 12:36:15
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>采购招标公告明细</title>
<%@include file="/commons/include/getById.jsp"%>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript">
		$(function(){
			//放置脚本
			var link="${purBidPlan.attachment}";
			link=link.split(";");
			for(var i=0;i<link.length-1;i++){
				var subs=link[i].split(",");
				var tr=getRow(subs[0],subs[1]);
				$("#attachmentTable").append(tr);
			}
		});
		
		function getRow(fileId,fileName){
			var tr = '<tr class="'+fileId+'">';
			tr += '<td>';
			tr += '	<span class="attach">&ensp;&ensp; </span>&nbsp;&nbsp;';
			//tr += '	<input type="hidden" class="pk" name="attachments" value="' + filePath + '"/> ';
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
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">采购招标公告详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
						<a class="link print" onclick="window.print();">打印</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">招标编号:</th>
				<td>${purBidPlan.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${purBidPlan.operatedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 			<!--  
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${purBidPlan.operaterId}</td>
			</tr>
 			-->
			<tr>
				<th width="20%">制单人姓名:</th>
				<td>${purBidPlan.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据编号:</th>
				<td>${purBidPlan.sourceformCode}</td>
			</tr>
 			<!--  
			<tr>
				<th width="20%">来源单据ID:</th>
				<td>${purBidPlan.sourceformId}</td>
			</tr>
 			-->
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${purBidPlan.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">发布日期:</th>
				<td>
				<fmt:formatDate value="${purBidPlan.releasedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">发布模式:</th>
				<td>${purBidPlan.releaseModel}</td>
			</tr>
 
			<tr>
				<th width="20%">招标截止日期:</th>
				<td>
				<fmt:formatDate value="${purBidPlan.bidEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">招标处理截止日期:</th>
				<td>
				<fmt:formatDate value="${purBidPlan.doobidEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">运费承担方:</th>
				<td>${purBidPlan.freightBearer}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${purBidPlan.currencyType}</td>
			</tr>
 
			<tr>
				<th width="20%">审核状态:</th>
				<td>${purBidPlan.status}</td>
			</tr>
 			<tr>
				<th width="20%">审核结果说明:</th>
				<td>${purBidPlan.statusNote}</td>
			</tr>
			<!-- 
			<tr>
				<th width="20%">采购方联系人ID:</th>
				<td>${purBidPlan.purchcontactorId}</td>
			</tr>
 			 -->
			<tr>
				<th width="20%">采购方联系人:</th>
				<td>${purBidPlan.purchcontactorName}</td>
			</tr>
 
			<tr>
				<th width="20%">采购方联系人电话:</th>
				<td>${purBidPlan.purchcontactorPhone}</td>
			</tr>
 			<!-- 
			<tr>
				<th width="20%">采购企业ID:</th>
				<td>${purBidPlan.enterpriseId}</td>
			</tr>
 			 -->
			<tr>
				<th width="20%">采购企业:</th>
				<td>${purBidPlan.enterpriseName}</td>
			</tr>
 			<!-- 
			<tr>
				<th width="20%">service_enterprise:</th>
				<td>${purBidPlan.serviceEnterprise}</td>
			</tr>
 
			<tr>
				<th width="20%">link:</th>
				<td>${purBidPlan.link}</td>
			</tr>
 			 -->
			<tr>
				<th width="20%">附件:</th>
				<td>
					<div>
						<table  width="145" name="attachmentTable"    id="attachmentTable" class="table-grid table-list"  cellpadding="2" cellspacing="2">
						</table>
					</div>
				<!-- ${purBidPlan.attachment} -->
				</td>
				
			</tr>
 
			<tr>
				<th width="20%">保证金:</th>
				<td>${purBidPlan.margins}</td>
			</tr>
			<tr>
				<th style="width:120px">资质要求说明（逗号分隔）:</th>
				<td colspan="3">${purBidPlan.qualifications}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="8" style="text-align: center">采购招标公告 :采购招标公告详情</td>
			</tr>
			<tr>
				<th style="display:none">物品ID</th>
				<th>物品名</th>
				<th>物品编码</th>
				<th style="display:none">物品分类</th>
				<th>物品规格</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>订单数</th>
				<th>单价</th>
				<th>要求截止时间</th>
			</tr>	
			<c:forEach items="${purBidPlanDetailList}" var="purBidPlanDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${purBidPlanDetailItem.id}"  class="inputText"/>
						<!-- <td style="text-align: center">${purBidPlanDetailItem.materielId}</td> -->								
						<td style="text-align: center">${purBidPlanDetailItem.materielName}</td>								
						<td style="text-align: center">${purBidPlanDetailItem.materielCode}</td>								
						<td style="display:none">${purBidPlanDetailItem.materielLev}</td>									
						<td style="text-align: center">${purBidPlanDetailItem.model}</td>								
						<td style="text-align: center">${purBidPlanDetailItem.attributeInfo}</td>								
						<td style="text-align: center">${purBidPlanDetailItem.unit}</td>								
						<td style="text-align: center">${purBidPlanDetailItem.orderNum}</td>								
						<td style="text-align: center">${purBidPlanDetailItem.price}</td>								
						<td style="text-align: center"><fmt:formatDate value='${purBidPlanDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
				</tr>
			</c:forEach>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="6" style="text-align: center">采购招标公告 :采购招标公告_企业资质</td>
			</tr>
			<tr>
			    <th>编号</th>
				<th>供应企业ID</th>
				<th>供应企业</th>
				<th>资质附件</th>
				<th>资质项</th>
				<th>资质类型</th>
				<th>说明</th>
			</tr>	
			<c:forEach items="${purBidPlanCertificateList}" var="purBidPlanCertificateItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${purBidPlanCertificateItem.id}"  class="inputText"/>
						<td style="text-align: center">${purBidPlanCertificateItem.suppenterpriseId}</td>								
						<td style="text-align: center">${purBidPlanCertificateItem.suppenterpriseName}</td>								
						<td style="text-align: center">${purBidPlanCertificateItem.attract}</td>								
						<td style="text-align: center">${purBidPlanCertificateItem.name}</td>								
						<td style="text-align: center">${purBidPlanCertificateItem.type}</td>								
						<td style="text-align: center">${purBidPlanCertificateItem.note}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

