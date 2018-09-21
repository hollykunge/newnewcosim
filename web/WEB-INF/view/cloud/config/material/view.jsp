<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>MaterialCatalog列表</title>
	<%@ include file="/commons/cloud/meta.jsp"%>
	<%@include file="/commons/include/getById.jsp"%>
		<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/cloud/global.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/cloud/style.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/cloud/kindeditor.css" rel="stylesheet" type="text/css" />
		<script charset="utf-8" src="${ctx}/js/cloud/jquery-1.2.4b.js"></script>
		 
		<script>
	function addFriend(en) {
		//如果未登录，提示登录信息

		$.ajax( {
			type : 'POST',
			url : "${ctx}/busarea/area/beFriend.action",
			data : {
				en : en
			},
			dataType : "json",
			success : function(data) {
				if (data.result == 0) {
					alert('已发送商友申请，等待对方审核');
				} else if (data.result == 2) {
					alert('已发送商友申请，请耐心等待对方审核');
				} else if (data.result == 1) {
					alert('已建立商友关系，请不要重复发送申请');
				}
			}
		});
	}
</script>

	</head>
	<body>
		<%@include file="/commons/cloud/top.jsp"%>
		<div class="clear_10"></div>
		<table width="1024" border="0" align="center" cellspacing="0"
			cellpadding="0">
			<tr>
				<td>
					<div js_flag="sale-info">


						<div class="product-info-form">
							<form action="" method="post">
								<div class="required-tip">
									<span class="fr"> </span>物品基本信息
								</div>
								<div class="row">
									<div class="label">
										物品标题：
									</div>
									<div class="cell">
										${material.name }
									</div>
									<div id="pronameTip"></div>
								</div>
								<div class="row">
									<div class="label">
										物品编码：
									</div>
									<div class="cell">
										${material.code }
									</div>
									<div id="prokeyTip"></div>
								</div>
								<div class="row" id="J_productProps">
									<div class="label">
										物品属性：
									</div>
									<div id="attrArea" class="product-props">
										<c:forEach items="${mpvs}" var="c1">

											<div class="row">
												<div class="label">
													${c1.propertyId }：
												</div>
												<div class="cell">
													${c1.propertyValue }
												</div>
											</div>

										</c:forEach>

									</div>
								</div>
								<div class="row">
									<div class="label">
										行业参考标准：
									</div>
									<div class="cell">
										${material.industryCode }
									</div>
								</div>

								<div class="row">
									<div class="label">
										物品图片：
									</div>
									<div class="cell" js_picarea="true">
										<div class="addproduct-pic" id="picView">
											<img src="${ctx}${material.pic }"  onError="this.src='${ctx}/images/default-chance.jpg'" width="80" height="84" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="label">
										物品详情：
									</div>
									<div class="cell">
										<div class="postbox" id="postbox">
											${material.info}
										</div>
									</div>
								</div>
								<div class="offer-scan">
									<div class="row pb0">
										<div class="label">
											质量评星：
										</div>
										<div class="star s-0">
											star
										</div>
									</div>

								</div>
								<div class="row">
									<div class="label">
										发布人：
									</div>
									<div class="cell">
										${material.publisher }
									</div>
								</div>
								<div class="row">
									<div class="label">
										发布日期：
									</div>
									<div class="cell">
									 
										<fmt:formatDate value="${material.publishdate}" pattern="yyyy-MM-dd"/>
									</div>
								</div>
							 


							</form>
						</div>
					</div>
					</div>
				</td>
			</tr>
			 
		</table>
       
		<!-- 页面主体  结束 -->
		<!-- 底部版权区  开始 -->
	<%@ include file="/commons/cloud/foot.jsp"%>
		<!-- 底部版权区  结束 -->
	</body>
</html>