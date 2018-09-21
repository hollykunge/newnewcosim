<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商机列表</title>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/get.jsp" %>
 
<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/cloud/my_tabs.css" rel="stylesheet" type="text/css" />
 
 

<script type="text/javascript">
	 
		function view(id){
			location.href = "${ctx}/cloud/console/enterprise.ht?EntId="+id;
		return false;
	}	
</script>


</head>

<body>
	<%@include file="/commons/cloud/top.jsp"%>
 	<div id="main">
 		<c:forEach items="${infoList}" var="enterprises" >
	 		<div id="tab01">
	 			<div class="tab01_1">
					<div class="tab01_2">
						<div class="tab01_3"><img src="${ctx}${enterprises.logo}" onError="this.src='${ctx}/images/default-chance.jpg'" width="80" height="84" /></div>
						<div class="tab01_4"><span class="tab02_7_span">主营：</span><span class="grey2">${enterprises.industry } - ${enterprises.industry2	 }</span></div>
						<div class="tab01_5"><span class="grey">${enterprises.info}</span></div>
					</div>
		
    			</div>
				<div class="tab01_1">
					<div class="tab02_2">
						<div class="tab02_4"><a href="javascript:view('${enterprises.sysOrgInfoId}');" class="link">${enterprises.name }</a> </div>
						<div class="tab02_5">
							<div class="tab02-07"><span class="tab02_7_span">联系人：</span></div>
			  				<div class="tab02-08"><span class="tab02_8_span">
			  				
			  				
			  				<c:if test="${enterprises.isPublic==1}">
									        	${enterprises.connecter }
										    </c:if>
										    <c:if test="${enterprises.isPublic==0}">
									        	未公开
										    </c:if>
			  				</span></div>
						</div>
						<div class="tab02_5">
							<div class="tab02-07"><span class="tab02_7_span">联系电话：</span></div>
			  				<div class="tab02-08"><span class="tab02_8_span">
			  				
			  				<c:if test="${enterprises.isPublic==1}">
									        	${enterprises.tel }
										    </c:if>
										   <c:if test="${enterprises.isPublic==0}">
									        	未公开
										    </c:if>
			  				</span></div>
						</div>
						<div class="tab02_5">
							<div class="tab02-07"><span class="tab02_7_span">公司地址：</span></div>
			  				<div class="tab02-08"><span class="tab02_8_span">${enterprises.address }</span></div>
						</div>
					</div>
		
    			</div>
	 		</div>
 		</c:forEach>
 		<div class="pub_tab">
			<div class="fenye">
				   <hotent:paging tableId="infoItem"/>
			</div>
		</div>
 	</div>
	
	
	
	<!-- 底部版权区  开始 -->
	<%@ include file="/commons/cloud/foot.jsp"%>
	<!-- 底部版权区  结束 -->
</body>
</html>
