<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
<title>在线商机列表</title>
</head>
<script type="text/javascript">
	function view(id, type) {
		location.href = "${ctx }/cloud/config/businessDevchase/indexview.ht?id="
				+ id + "&type=" + type;
		return false;
	}
</script>
<body>
	<div id="all">
		<%@include file="/commons/cloud/top.jsp"%>

		<div id="zxsj" class="bggraybox">
			<div class="title">
				<a href="javascript:void(0)">在线商机</a>
			</div>
			<ul>
				<li><c:forEach items="${businessChanceList}"
						var="businessChance" varStatus="s">
						<div class="left">
							<table width="400" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="101" rowspan="2" align="left" valign="top"><img
										src="${ctx}${businessChance.image}" width="100" height="100"
										onError="this.src='${ctx}/images/default-chance.jpg';" /></td>
									<td width="299" height="25" align="left" valign="middle"><a
										href="javascript:void(0)"
										onclick="view('${businessChance.id}','${businessChance.type}');"
										name="${businessChance.id}">${businessChance.name}</a></span> <span
										class="grey2"><a
											href="${ctx}/cloud/console/enterprise.ht?EntId=${businessChance.companyId}"
											class="link_zxsj">${businessChance.companyName}</a></td>
								</tr>
								<tr>
									<td align="left" valign="middle" class="zxsj_box03">开始时间：<fmt:formatDate
											value="${businessChance.startTime}" pattern="yyyy-MM-dd" />
										截止时间：<fmt:formatDate value="${businessChance.endTime}"
											pattern="yyyy-MM-dd" /></br>
				     			<c:if test="${businessChance.type=='生产商机'}">
                            	 生产要求：${businessChance.properties} 
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='采购商机'}">
                            	 采购数量：${businessChance.properties} 
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='营销商机'}">
                            	 代理区域：${businessChance.properties} 
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='服务商机'}">
                            	 服务区域：${businessChance.properties} 
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='研发商机'}">
                            	 研发要求：${businessChance.properties} 
                            	 </c:if>
                            	 </br>商机类型：${businessChance.type}
                            	 </td>
								</tr>
							</table>
						</div>
					</c:forEach>

					<p class="clear"></p></li>
			</ul>

			<!--底部灰色背景-->
			<div class="bgbox_gray">
				<hotent:paging tableId="businessChanceItem" showPageSize="false" />
			</div>
			<script type="text/javascript">
				$('#detail_list li:last').css('margin-bottom', '2px');
			</script>

		</div>
	</div>
	<%@include file="/commons/cloud/foot.jsp"%>
</body>
</html>