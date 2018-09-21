<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
<script type="text/javascript">
	$(function(){
		//点击确定
		$('#ok').click(function(){
			var rows = getSelectedRoles();
			window.parent._callBackSelectedRoles(rows);				
		})
	})
		
	function choseGroup(groupid){
		window.location.href = "${ctx}/cloud/console/busiarea/listMyFriends.ht?groupid="+groupid;
	}
	
	//点击右移按钮
	function toRight(){
		//所选商友数组
		var friends = [];
		//所选角色数组
		var roles = [];
		
		//获取所选企业商友
		$('#myFriendsList').find(':radio:checked').each(function(){
			//定义一个商友对象
			var friend = {};
			
			//获取所选行和列
			var $tr = $(this).parent().parent();
			var $tds = $tr.find('td');
			
			//给对象赋值
			friend.id = $($tds[0]).find(':radio').val();
			friend.name = $($tds[1]).text();
			
			//将商友对象加入数组
			friends.push(friend);
		});
		
		//获取所选角色
		$('#rolesList').find(':radio:checked').each(function(){
			//定义一个对象
			var role = {};
			
			//获取所选行和列
			var $tr = $(this).parent().parent();
			var $tds = $tr.find('td');
			
			//给对象赋值
			role.id = $($tds[0]).find(':radio').val();
			role.name = $($tds[1]).text();
			
			//将商友对象加入数组
			roles.push(role);
		});
		
		//删除第一条空记录
		if(friends.length > 0 && roles.length > 0){
			var emptyTr = $('#selectedRoles').find('tr:eq(1)');
			if(emptyTr.hasClass('empty')){
				emptyTr.remove();
			}
		}
		
		//将商友和角色加入已选列表
		for(var i = 0; i < friends.length; i++){
			for(var j = 0; j < roles.length; j++){
				var friend = friends[i];
				var role = roles[j];
				
				//重复的不加
				var flag = false;
				var rows = getSelectedRoles();
				for(var k=0; k<rows.length; k++){
					var row = rows[k];
					if(friend.id==row.companyId && role.id==row.roleId){
						flag = true;
						break;
					}
				}
				
				if(!flag){
					$('#selectedRoles').find('#check').remove();
					var tr = '<tr id="check">';
					tr += '<td><input type="checkbox"/>';
						tr += '<input type="hidden" name="companyId" value="' + friend.id + '"/>';
						tr += '<input type="hidden" name="roleId" value="' + role.id + '"/>';
						tr += '</td>';
					tr += '<td>' + friend.name + '</td>';
					tr += '<td>' + role	.name + '</td>';
					tr += '</tr>';
					$('#selectedRoles').append(tr);
				}
			}
		}
	}
	
	//获取所选企业和角色
	function getSelectedRoles(){
		var rows = [];
		
		//获取所选角色
		var i = 0;//jquery 竟然不能用tr:eq，他大爷的，严重的bug
		$('#selectedRoles').find('tr').each(function(){
			if(i > 0){
				//定义一个对象
				var row = {};
				//获取所选行和列
				var $tds = $(this).find('td');
				//给对象赋值
				row.roleId = $($tds[0]).find(':input[name=roleId]').val();
				row.roleName = $($tds[2]).text();
				row.companyId = $($tds[0]).find(':input[name=companyId]').val();
				row.companyName= $($tds[1]).text();
				//将商友对象加入数组
				rows.push(row);
			}
			i++;
		});
		return rows;
	}
	
	//删除选企业和角色
	function toLeft(){
		//获取所选角色
		$('#selectedRoles').find(':checkbox:checked').each(function(){
			var $tr = $(this).parent().parent();
			
			$tr.remove();
		});
	}
	
	
</script>
<title>选择供应商</title>
<style>
	.left{
		float:left;
		width:200;
	}
	
	.middle{
		float:left;
		width:150;
	}
	
	.center{
		float:left;
		width:50px;
		margin-top:50px;
		margin-left:20px;
	}
	
	.right{
		float:left;
		width:300px;
	}
</style>
</head>
<body>
<div class="panel-body">
	
<!-- 企业商友 -->
<div class="left">
	<%--
	商友分组：
	<select id="groupid" name="groupid"  onchange="choseGroup(this.value)" style="width: 160px;">
		<option value=""> 请选择</option>
		<option value=""> 全部 </option>
		<c:forEach items="${businessAreaGroupList}" var="c">
			<c:if test="${c.id==friends.groupid}">
				<option value="${c.id}" selected="selected "> ${c.groupname} </option>
			</c:if>
			<c:if test="${c.id!=friends.groupid}">
				<option value="${c.id}"  > ${c.groupname}</option>
			</c:if>
		</c:forEach>
	</select>
	--%>
	
	<display:table name="myFriendsList" id="myFriendsList"
		requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" 
		class="table-grid" style="width:200px">
		<display:column title="选择" media="html" style="width:30px;">
			<input type="radio" class="pk" name="id"
				value="${myFriendsList.corpEnterprise.sysOrgInfoId}">
		</display:column>		
		<display:column property="corpEnterprise.name" title="企业名称" style="width:100px;">
		</display:column>
	</display:table>	
	<%--
	<hotent:paging tableId="myFriendsList" />
	--%>
</div>
<!-- end of 商友 -->
	
<!-- 角色列表  -->
<div class="middle">
	<display:table name="rolesList" id="rolesList" requestURI="list.ht" 
		sort="external" cellpadding="1" cellspacing="1" 
		style="width:150px" class="table-grid">
		<display:column title="选择" media="html" style="width:30px;">
			<input type="radio" class="pk" name="id1" value="${rolesList.roleId}">
		</display:column>
		<display:column property="roleName" title="角色名称" style="width:100px;"/>			
	</display:table>
</div>
<!-- end of 角色列表 -->
	
<!-- 选择按钮 -->
<div class="center">
	<input type="button" value=">>" onclick="toRight();"/>
	<input type="button" value="<<" onclick="toLeft();"/>
</div>
<!-- end of 选择按钮 -->
	
<!-- 已选角色 -->
<div class="right">
	<display:table name="selectedRoles" id="selectedRoles" requestURI="list.ht" 
		sort="external" cellpadding="1" cellspacing="1" 
		class="table-grid" style="width:300px">
		<display:column title="选择" media="html" style="width:20px;">
			<input type="checkbox" class="pk" name="id">
			<input type="hidden" name="companyId">
			<input type="hidden" name="roleId">
		</display:column>
		<display:column property="" title="企业名称" style="width:100px;"/>
		<display:column property="" title="角色名称" style="width:100px;"/>			
	</display:table>
</div>
<!-- end of 已选角色 -->

<div class="clear"></div>
<div class="l-dialog-buttons" style="">
	<div class="l-dialog-buttons-inner">
		<div class="l-dialog-btn">
			<div class="l-dialog-btn-l"></div>
			<div class="l-dialog-btn-r"></div>
			<div class="l-dialog-btn-inner" id="ok">确定</div>
		</div>
	</div>
</div>

</div>
</body>
</html>
