<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>能力列表</title>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/get.jsp" %>
</head>

<body>
<div id="all">
<!-- 顶部浮动层  开始 -->
<%@include file="/commons/cloud/top.jsp"%>
 
<!-- list开始 -->
<div id="detail_list" class="bggraybox">
		<div class="title"><a href="javascript:void(0)">在线能力列表</a></div>
		<ul>
		<c:forEach items="${capabilityList}" var="c1">
			<li>
				<div class="left">
					<img src="${ctx}${c1.pic}" onError="this.src='${ctx}/images/default-chance.jpg'" width="80" height="84" />
					<div class="left_desc">
						<h5><a href="capabilityDetail.ht?id=${c1.id }">${c1.name }</a></h5>
						<p>${c1.info}</p>
					
					</div>
				</div>
				<div class="right">
					<h5><a href="${ctx}/cloud/console/enterprise.ht?EntId=${c1.entId}">${c1.entName}</a></h5>
					<p>${c1.entinfo.address}</p>
					<p>主营：${c1.entinfo.industry }</p>
				</div>
				<p class="clear"></p>
			</li>
		</c:forEach>
		</ul>
		<!--底部灰色背景 分页-->
		<div class="bgbox_gray">
		<hotent:paging tableId="capabilityItem"/>
		</div>
		<script type="text/javascript">
			$('#detail_list li:last').css('margin-bottom','2px');
		</script>
		
	</div>
<!-- list结束 -->
</div>

<!-- 底部版权区  开始 -->
<%@ include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
