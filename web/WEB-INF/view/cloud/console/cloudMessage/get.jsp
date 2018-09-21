
<%--
	time:2013-04-19 13:32:10
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>CLOUD_MESSAGE明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">CLOUD_MESSAGE详细信息</span>
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
				<th width="20%">sender_id:</th>
				<td>${cloudMessage.senderId}</td>
			</tr>
 
			<tr>
				<th width="20%">sendent_id:</th>
				<td>${cloudMessage.sendentId}</td>
			</tr>
 
			<tr>
				<th width="20%">receiver_id:</th>
				<td>${cloudMessage.receiverId}</td>
			</tr>
 
			<tr>
				<th width="20%">receiveent_id:</th>
				<td>${cloudMessage.receiveentId}</td>
			</tr>
 
			<tr>
				<th width="20%">sendtime:</th>
				<td>
				<fmt:formatDate value="${cloudMessage.sendtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">outtime:</th>
				<td>
				<fmt:formatDate value="${cloudMessage.outtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">result:</th>
				<td>${cloudMessage.result}</td>
			</tr>
 
			<tr>
				<th width="20%">link:</th>
				<td>${cloudMessage.link}</td>
			</tr>
 
			<tr>
				<th width="20%">type:</th>
				<td>${cloudMessage.type}</td>
			</tr>
 
			<tr>
				<th width="20%">content:</th>
				<td>${cloudMessage.content}</td>
			</tr>
 
			<tr>
				<th width="20%">source_id:</th>
				<td>${cloudMessage.sourceId}</td>
			</tr>
 
			<tr>
				<th width="20%">title:</th>
				<td>${cloudMessage.title}</td>
			</tr>
 
			<tr>
				<th width="20%">readtime:</th>
				<td>
				<fmt:formatDate value="${cloudMessage.readtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

