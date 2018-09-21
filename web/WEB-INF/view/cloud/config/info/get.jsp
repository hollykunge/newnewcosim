<%--
	time:2013-04-23 11:20:58
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>审核信息</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#InfoForm').form();
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
				$.ligerMessageBox.confirm("提示信息", "审核成功", function(rtn) {
					if(rtn){
						this.close();
						window.location.href = "${ctx}/cloud/config/info/list_wsh.ht";
					}else{
						window.location.href = "${ctx}/cloud/config/info/list_wsh.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${info.sysOrgInfoId !=null}">
			        <span class="tbar-label">企业信息审核</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">企业信息审核</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="javascript:history.back(-1);">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
			<form id="InfoForm" method="post" action="dep.ht">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">审核:</th>
					<td colspan="3">
					<input type="radio" name="state" value="2" checked="checked"/>审核通过
					<input type="radio" name="state" value="0" />审核未通过
				</tr>
				</table>
				<input type="hidden" name="sysOrgInfoId" value="${info.sysOrgInfoId}" />
			</form>
	
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
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
				<c:if test="${info.model==0}">
					其他类型企业
				</c:if>
				<c:if test="${info.model==1}">
					生产型企业
				</c:if>
				<c:if test="${info.model==2}">
					贸易型企业
				</c:if>
				<c:if test="${info.model==3}">
					服务型
				</c:if>
				<c:if test="${info.model==4}">
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
					<c:if test="${info.isPublic == 1}">
					是
					</c:if>
					<c:if test="${info.isPublic == 0}">
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
			<tr>
				<td colspan="4" width="">
						
				</td>
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
			
		
		
	</div>
</div>
</body>
</html>
