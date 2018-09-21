<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/commons/cloud/global.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><decorator:title default="个人主页" />-云制造</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>个人主页</title>
	<%@ include file="/commons/cloud/meta.jsp"%>  
</head>
<body>  
	<div class="pub">
		<div class="pub01">
			<textarea id="message" cols="" rows="" class="pub_input"></textarea>
		</div>
		<div class="pub02">
			<a href="javascript:void(0)"><img src="${ctx }/images/btn_pub01.jpg" width="87"
				height="89" onmouseover="src='${ctx }/images/btn_pub02.jpg';"
				onmouseout="src='${ctx }/images/btn_pub01.jpg'"
				style="border: 0;" onclick="sendMessage();"></a>
		</div>
	</div>
	<div class="clear_10"></div>
	<div id="content">
		<div class="content_02">
			<table id="cloudMessageList">
				<c:forEach items="${cloudMessages}" var="cloudMessage">
					<div class="content_sqfriend">
						<div class="sq_friend_01list" style="padding-bottom: 20px">
							<div class="sq_list02" style="padding-top:5px;padding-right:10px">
								<a href="${ctx}/cloud/console/enterprise.ht?EntId=${cloudMessage.sendentId }">
								<img src="${ctx}${cloudMessage.sendEnt.logo}" onError="this.src='${ctx}/images/default-chance.jpg'" width="50" height="50" style="border: 1px solid #6D6D6F;" />
								</a>
							</div>
							<div class="sq_friend_03list">
								<a>${cloudMessage.title} </a>
							</div>
							<div class="sq_friend_04list">${cloudMessage.content}</div>
						</div>
					</div>
				</c:forEach>
			</table>
		</div>
	</div>
	<div class="pub_tab"
		style="width: 600px; height: 25px; border-top-width: 0px;">
		<div class="fenye" style="width: 590px;">
			<hotent:paging tableId="cloudMessageList" />
		</div>
	</div>
</body>
</html>