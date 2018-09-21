
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业列表</title>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/get.jsp" %>
 
<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/cloud/my_tabs.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	function addFriend(corpEntID) {
		//如果未登录，提示登录信息
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/busiarea/beFriend.ht",
			data : {
				corpEntID : corpEntID
			},
			dataType : "json",
			success : function(data) {
				if(data && data.waitForAccept=="true"){
					alert('添加商友请求已经发送，等待对方审核');	
				}else if(data && data.isWaiting=="true"){
					alert('已经发出添加商友申请，请勿重复提交添加申请');	
				}else if(data && data.isFriend=="true"){
					alert('对方已是商友，请勿重复添加');	
				}else if(data && data.isMyself=="true"){
					alert('不能添加自己为商友');	
				}else if(data && data.notLogin=="true"){
					alert('您还未登录，请先登录系统');
					window.location = "${ctx}/loginCloud.ht";
				}
			},
			error : function(data){
				alert('错误');
				
			},
		});
	}
</script>
</head>

<body>
<div id="all">
<!-- 顶部浮动层  开始 -->
<%@include file="/commons/cloud/top.jsp"%>
 
<!-- list 开始 -->
<div id="detail_list" class="bggraybox">
		<div class="title"><a href="javascript:void(0)">企业列表</a></div>
		<ul>
		<c:forEach items="${sysOrgInfoList}" var="c1">
			<li>
				<div class="left">
					<img src="${ctx }${c1.logo}"
							onError="this.src='${ctx}/images/no_picture.gif'" width="80"
							height="80" />
					<div class="left_desc">
						<h5><a href="${ctx }/cloud/console/enterprise.ht?EntId=${c1.sysOrgInfoId }">${c1.name }</a></h5>
						<p>${c1.address}</p>
					
					</div>
				</div>
				<div class="right">
					<h5></h5>
					<p style="float:right; padding-right:40px;"><img src="${ctx}/images/icon_add.jpg" width="70" height="18"
							onmouseover="src='${ctx}/images/icon_add2.jpg';"
							onmouseout="src='${ctx}/images/icon_add.jpg'" style="border: 0;"
							onClick="addFriend('${c1.sysOrgInfoId }');" /></p>
					<p></p>
				</div>
				<p class="clear"></p>
			</li>
		</c:forEach>
		</ul>
		<!--底部灰色背景-->
		<div class="bgbox_gray"><hotent:paging tableId="sysOrgInfoItem"/></div>
		<script type="text/javascript">
			$('#detail_list li:last').css('margin-bottom','2px');
		</script>
		
	</div>
<!-- list 结束 -->
</div>

<!-- 底部版权区  开始 -->
<%@ include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
