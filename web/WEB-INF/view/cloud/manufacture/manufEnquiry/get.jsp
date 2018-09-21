
<%--
	time:2013-04-19 11:18:07
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>委外加工询价单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">委外加工询价单详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">单证号:</th>
				<td>${manufEnquiry.code}</td>
			</tr>
 <!-- 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${manufEnquiry.operatorId}</td>
			</tr>
 
			<tr>
				<th width="20%">企业ID:</th>
				<td>${manufEnquiry.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">联系人ID:</th>
				<td>${manufEnquiry.enterpriseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据ID:</th>
				<td>${manufEnquiry.sourceformId}</td>
			</tr>
  -->
			<tr>
				<th width="20%">制单人姓名:</th>
				<td>${manufEnquiry.operatorName}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${manufEnquiry.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">企业名:</th>
				<td>${manufEnquiry.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">联系人姓名:</th>
				<td>${manufEnquiry.enterpriseUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">询价截止日期:</th>
				<td>
				<fmt:formatDate value="${manufEnquiry.offerEnddate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">发布日期:</th>
				<td>
				<fmt:formatDate value="${manufEnquiry.releaseDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">发布模式:</th>
				<td>${manufEnquiry.releaseModel}</td>
			</tr>
 
			<tr>
				<th width="20%">运费承担方:</th>
				<td>${manufEnquiry.freightBearer}</td>
			</tr>
 
			<tr>
				<th width="20%">运输方式:</th>
				<td>${manufEnquiry.transportWay}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${manufEnquiry.currencyType}</td>
			</tr>
 
			<tr>
				<th width="20%">是否带料:</th>
				<td>
					<c:if test="${ manufEnquiry.isBringmaterial == 1}">
					             带料加工
						<input type="hidden" id="isBringmaterial" name="isBringmaterial" value="${ manufEnquiry.isBringmaterial}"  class="inputText" validate="{required:false}" readonly="readonly" />
					</c:if>
					<c:if test="${ manufEnquiry.isBringmaterial == '0'}">
						自备材料
						<input type="hidden" id="isBringmaterial" name="isBringmaterial" value="${ manufEnquiry.isBringmaterial}"  class="inputText" validate="{required:false}" readonly="readonly" />
					</c:if>
				</td>
			</tr>
 
			<tr>
				<th width="20%">加工类型:</th>
				<td>${manufEnquiry.manufacturingType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${manufEnquiry.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据编码:</th>
				<td>${manufEnquiry.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">是否汇报加工进度:</th>
				<td>
					<c:if test="${ manufEnquiry.isReport == 1}">
					   	 汇报加工进度
						<input type="hidden" id="isBringmaterial" name="isBringmaterial" value="${ manufEnquiry.isReport}"  class="inputText" validate="{required:false}" readonly="readonly" />
					</c:if>
					<c:if test="${ manufEnquiry.isReport == '0'}">
						不汇报加工进度
						<input type="hidden" id="isBringmaterial" name="isBringmaterial" value="${ manufEnquiry.isReport}"  class="inputText" validate="{required:false}" readonly="readonly" />
					</c:if>
				</td>
			</tr>
 
			<tr>
				<th width="20%">备注:</th>
				<td>${manufEnquiry.comments}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="14" style="text-align: center">委外加工询价单 :委外加工询价单产品详情</td>
			</tr>
			<tr>
				<th>待加工物品编码</th>
				<th style="display:none">编码规则</th>
				<th>待加工物品名称</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>委托加工数量</th>
				<th>要求交货日期</th>
				<th>工艺要求（多选）</th>
				<th>工艺附件</th>
				<th>产品BOM附件</th>
				<th>计划开工时间</th>
				<th>计划完工时间</th>
				<th>计划完工入库率下限</th>
				<th>备注</th>
			</tr>	
			<c:forEach items="${manufEnquiryDetailList}" var="manufEnquiryDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${manufEnquiryDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${manufEnquiryDetailItem.materialCode}</td>								
						<!-- <td style="text-align: center">${manufEnquiryDetailItem.materialCodenotation}</td> -->								
						<td style="text-align: center">${manufEnquiryDetailItem.materialName}</td>								
						<td style="text-align: center">${manufEnquiryDetailItem.materialAttribute}</td>								
						<td style="text-align: center">${manufEnquiryDetailItem.materialUnit}</td>								
						<td style="text-align: center">${manufEnquiryDetailItem.materialNumber}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufEnquiryDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufEnquiryDetailItem.crafts}</td>								
						<td style="text-align: center">${manufEnquiryDetailItem.craftAttachment}</td>								
						<td style="text-align: center">${manufEnquiryDetailItem.bom}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufEnquiryDetailItem.planStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${manufEnquiryDetailItem.planEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufEnquiryDetailItem.planInrate}</td>								
						<td style="text-align: center">${manufEnquiryDetailItem.comments}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

