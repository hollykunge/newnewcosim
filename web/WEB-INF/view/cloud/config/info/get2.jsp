
<%--
	time:2013-04-23 11:20:58
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>企业审核信息</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">企业详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<div class="group"><a class="link back" href="javascript:history.back(-1);">返回</a></div>
						<div class="l-bar-separator"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">企业账号:</th>
				<td width="30%">${info.sysOrgInfoId}</td>
				<th width="20%">邮箱:</th>
				<td>${info.email}</td>
			</tr>
			<tr>
				<th width="20%">企业名称:</th>
				<td width="30%">${info.name}</td>
				<th width="20%">企业类型:</th>
				<td width="30%">${info.type}</td>
			</tr>
			<tr>
				<th width="20%">主营行业:</th>
				<td>${info.industry} - ${info.industry2}</td>
				<th width="20%">主营产品:</th>
				<td>${info.product}</td>
			</tr>
			<tr>
				<th width="20%">经营模式:</th>
				<td>
				<c:if test="${info.model=='0'}">
					其他类型企业
				</c:if>
				<c:if test="${info.model=='1'}">
					生产型企业
				</c:if>
				<c:if test="${info.model=='2'}">
					贸易型企业
				</c:if>
				<c:if test="${info.model=='3'}">
					服务型
				</c:if>
				<c:if test="${info.model=='4'}">
					研发型
				</c:if>
				</td>
				<th width="20%">公司规模:</th>
				<td>${info.scale}</td>
			</tr>
			<tr>
				<th width="20%">经营范围:</th>
				<td>${info.manageRange}</td>
				<th width="20%">组织机构代码:</th>
				<td>${info.code}</td>
			</tr>
			<tr>
				<th width="20%">工商注册证明:</th>
				<td>${info.regProve}</td>
				<th width="20%">企业Logo:</th>
				<td>
					<c:if test="${info.logo!='' }">
						<img src="${ctx}${info.logo}" width="120" height="84" />
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td align="left" colspan="4" height="20px;" width="100%"></td>
			</tr>
 			<tr>
				<th width="20%">联系人:</th>
				<td 30%>${info.connecter}</td>
				<th width="20%">手机:</th>
				<td>${info.tel}</td>
			</tr>
			<tr>
 				<th width="20%">座机:</th>
				<td>${info.homephone}</td>
				<th width="20%">传真:</th>
				<td>${info.fax}</td>
			</tr>
 			<tr>
 				<th width="20%">所在国家、省(州)、市(区):</th>
				<td>${info.country}-${info.province}-${info.city}</td>
				<th width="20%">地址:</th>
				<td>${info.address}</td>
			</tr>
			<tr>
				<th width="20%">邮编:</th>
				<td>${info.postcode}</td>
 				<th width="20%">公司网站:</th>
				<td>${info.website}</td>
 			</tr>
			<tr>
				<th width="20%">是否公开:</th>
				<td>
					<c:if test="${info.isPublic == '1'}">
					是
					</c:if>
					<c:if test="${info.isPublic == '0'}">
					否
					</c:if>
				</td>
				<th width="20%">注册时间:</th>
				<td>
				<fmt:formatDate value="${info.registertime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			
 			<tr>
				<td align="left" colspan="4" height="20px;" width="100%"></td>
			</tr>
 			
 			<tr>
				<th width="20%">主要销售区域:</th>
				<td>${info.sellArea}</td>
 				<th width="20%">主要客户群体:</th>
				<td>${info.clients}</td>
 			</tr>
 			<tr>
				<th width="20%">企业占地面积:</th>
				<td>${info.area}</td>
 				<th width="20%">员工人数:</th>
				<td>${info.employees}</td>
 			</tr>
 			<tr>
				<th width="20%">企业品牌:</th>
				<td>${info.brand}</td>
 				<th width="20%">年营业额:</th>
				<td>${info.turnover}</td>
 			</tr>
 			<tr>
				<th width="20%">年出口额:</th>
				<td>${info.exportFore}</td>
 				<th width="20%">年进口额:</th>
				<td>${info.importFore}</td>
 			</tr>
 			<tr>
				<th width="20%">质量管理体系:</th>
				<td>${info.qualityControl}</td>
 				<th width="20%">注册资本:</th>
				<td>${info.regCapital}</td>
 			</tr>
 			<tr>
				<th width="20%">注册地点:</th>
				<td>${info.regAdd}</td>
 				<th width="20%">法人:</th>
				<td>${info.incorporator}</td>
 			</tr>
 			<tr>
				<th width="20%">开户银行:</th>
				<td>${info.openBank}</td>
 				<th width="20%">开户账户:</th>
				<td>${info.openAccount}</td>
 			</tr>
			<tr>
				<th width="20%">公司简介:</th>
				<td colspan="3">${info.info}</td>
			</tr>
			<tr>
				<td align="left" colspan="4" height="20px;" width="100%"></td>
			</tr>
		</table>
		
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
				<tr>
					<td colspan="6" style="text-align: center">资质认证信息</td>
				</tr>
				<tr>
					<th>所属企业</th>
					<th>证书类型</th>
					<th>发证机构</th>
					<th>生效日期</th>
					<th>截止日期</th>
					<th>证书扫描件</th>
				</tr>	
				<c:forEach items="${aptitudeList}" var="aptitudeItem" varStatus="status">
					<tr>
							<input type="hidden" id="id" name="id" value="${aptitudeItem.id}"  class="inputText"/>
							<td style="text-align: center">${aptitudeItem.infoId}</td>					
	
				
							<td style="text-align: center">${aptitudeItem.cateType}</td>					
	
				
							<td style="text-align: center">${aptitudeItem.cateOrg}</td>					
	
				
							<td style="text-align: center"><fmt:formatDate value='${aptitudeItem.inureDate}' 
	
	pattern='yyyy-MM-dd'/></td>								
							<td style="text-align: center">${aptitudeItem.endDate}</td>					
	
				
							<td style="text-align: center">${aptitudeItem.catePic}</td>					
	
				
					</tr>
				</c:forEach>
			</table>
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
 			<tr>
					<th width="20%">审核状态:</th>
					<td colspan="3">
						<c:if test="${info.state==0}">新注册企业</c:if>
						<c:if test="${info.state==1}">未审核</c:if>
						<c:if test="${info.state==2}">审核通过</c:if>
					</td>
			</tr>
			
			
		</table>
		</div>
		
	</div>
</body>
</html>

