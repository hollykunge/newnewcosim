<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
<script type="text/javascript">
	$(function(){
		$('#ok').click(function(){
			var friends = [];
			var j = 0;
			$('#users').find(':checkbox:checked').each(function(){
				var $tr = $(this).parent().parent();
				var tds = $tr.find('td');
				var friend = [];
				for(var i=0; i<tds.length; i++){					
					if(i!=0){						
						var td = $(tds[i]).text();						
						friend[i-1] = td;
					}
				}
				friends[j++] = friend;
			});
			window.parent._callBackUsers(friends);			
		})
	})
</script>
<title>选择人员</title>
</head>
<body>
	<div class="panel-body">
		<c:set var="checkAll">
			<input type="checkbox" id="chkall" />
		</c:set>
		<display:table name="users" id="users"
			requestURI="getByCompAndRole.ht" sort="external" cellpadding="1" cellspacing="1"
			export="false" class="table-grid">
			<display:column title="${checkAll}" media="html" style="width:30px;">
				<input type="checkbox" class="pk" name="id"
					value="${userId}">
			</display:column>			
			<display:column property="userId" title="用户ID">				
			</display:column>
			<display:column property="fullname" title="用户名" sortable="true"
				sortName="name"></display:column>
			<display:column property="status" title="状态" sortable="true"
				sortName="tel"></display:column>
			<display:column property="mobile" title="联系方式" sortable="true"
				sortName="address"></display:column>
		</display:table>
		<div class="l-clear"></div>
	</div>
	<div class="l-dialog-buttons" style="">
			<div class="l-dialog-buttons-inner">
			<div class="l-dialog-btn">
				<div class="l-dialog-btn-l"></div>
				<div class="l-dialog-btn-r"></div>
				<div class="l-dialog-btn-inner" id="ok">确定</div>
			</div></div>
		</div>
</body>
</html>
