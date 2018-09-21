<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/commons/cloud/global.jsp"%>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><decorator:title default="个人主页" />-云制造</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>个人主页</title>
	<%@ include file="/commons/cloud/meta.jsp"%>
	<%@	include file="/commons/include/get.jsp"%>
	<!-- *****************************@自动弹出样式************************** -->
	<style type="text/css">
	/*
	 * user auto tips css
	 */
	.recipients-tips {
		font-family: "宋体",Tahoma, Arial;
		position: absolute;
		background: #ffffff;
		z-index: 2147483647;
		padding: 10px;
		border: 2px solid #594c6d;
		display: none;
		overflow: auto;
		max-height: 265px;
	}
	
	.recipients-tips li a {
		font-size: 14px;
		display: block;
		padding: 5px;
		font-weight: bold;
		cursor: pointer;
		font-family: "宋体",Tahoma, Arial;
		padding-top:10px;
		height:27px;
		width:200px;
	}
	
	.autoSelected {
		background: #2a5caa;
		color: #FFFFFF;
		font-family: "宋体",Tahoma, Arial;
	}
	
	.panel-page {
		border: 1px #8dc2e3 solid;
		border-top: none
		height: 28px;
		width: 598px;
		margin-top: -5px;
		background: #dff5fd;
		/*background: url(../images/tool_bg.jpg) repeat-x;*/
	}
	</style>

	<!-- ****************************自动弹出的js********************************* -->
	<script src="${ctx }/js/cloud/userAutoTips.js"></script>

	<script type="text/javascript">
	$(function() {
		userAutoTips({
			id : 'message'
		});
	})

	function accept(receviedId, bid, sendEntId) {
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/busiarea/accept.ht",
			data : {
				receviedId : receviedId,
				bid : bid,
				sendEntId : sendEntId,
			},
			dataType : "json",
			success : function(data) {
				if (data && data.acceptOK == "true") {
					alert('商友已经添加成功');
				} else if (data && data.isFriend == "true") {
					alert('对方已是商友');
				} else if (data && data.myself == "true") {
					alert('自己不能通过添加申请');
				} else if (data && data.alreadyRefuse == "true") {
					alert('已经拒绝添加，请等待对方重新申请添加');
				}
			}
		});
	}

	function refuse(receviedId, bid, sendEntId) {
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/busiarea/refuse.ht",
			data : {
				receviedId : receviedId,
				bid : bid,
				sendEntId : sendEntId,
			},
			dataType : "json",
			success : function(data) {
				if (data && data.refuseOK == "true") {
					alert('拒绝添加对方为商友');
				} else if (data && data.isFriend == "true") {
					alert('对方已是商友');
				} else if (data && data.myself == "true") {
					alert('自己不能拒绝添加申请');
				} else if (data && data.alreadyRefuse == "true") {
					alert('已经拒绝对方为商友，请勿重复提交拒绝');
				}
			}
		});
	}

	function acceptAndAdd(receviedId, bid, sendEntId) {
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/busiarea/acceptAndAdd.ht",
			data : {
				receviedId : receviedId,
				bid : bid,
				sendEntId : sendEntId,
			},
			dataType : "json",
			success : function(data) {
				if (data && data.acceptAndAddOK == "true") {
					alert('同意对方添加，并添加对方为商友');
				} else if (data && data.isFriend == "true") {
					alert('对方已是商友');
				} else if (data && data.myself == "true") {
					alert('自己不能通过和添加');
				} else if (data && data.alreadyRefuse == "true") {
					alert('已经拒绝对方为商友，请勿重复提交拒绝');
				}
			}
		});
	}

	function sendMessage() {
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/busiarea/sendMessage.ht",
			data : {
				eid : '${myCompany.sysOrgInfoId}',
				message : $('#message').val()
			},
			dataType : "json",
			success : function(data) {
				if (data.sendMessageOK == "true") {
					alert('消息发送成功');
					window.location = "${ctx}/cloud/console/home.ht"
					$('#message').val('');
				} else if (data.NoCorpEnt == "true") {
					alert('请检查发送对象是否为商友');
				}
			}
		});
	}

	/* 	$(function() {
	 $('body').loading();
	 freshMyMessage();
	 });
	 */
	function freshMyMessage() {
		$('#content').html('');
		$('body').loading();
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/home.ht",
			dataType : "json",
		});
	}
	</script>
	<script src="${ctx}/js/cloud/FusionCharts.js" type="text/javascript"></script>
</head>
<body>
	<%@include file="/commons/cloud/top_console.jsp"%>
	<!-- 页面主体  开始 -->
	<div id="main">
		<div id="main_left">
			<div id="sq_left">
				<%@include file="/commons/cloud/console_left.jsp"%>
			</div>
			<div id="sq_middle">
				<decorator:body />
			</div>
		</div>
		<div id="main_right">
			<%@include file="/commons/cloud/console_right.jsp"%>
		</div>
	<!-- 页面主体  结束 -->
	<!-- 底部版权区  开始 -->
	<%@ include file="/commons/cloud/foot.jsp"%>
	<!-- 底部版权区  结束 -->
</body>
</html>
