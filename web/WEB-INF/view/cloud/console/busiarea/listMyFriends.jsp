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
			$('#myFriendsList').find(':checkbox:checked').each(function(){
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
			window.parent._callBackMyFriends(friends);			
		})
	})
	
	
	function choseGroup(groupid){
	
	window.location.href = "${ctx}/cloud/console/busiarea/listMyFriends.ht?groupid="+groupid;
 
							
	
}
</script>
<title>选择供应商</title>
</head>
<body>
 
	<div class="panel-body">
	商友分组：<select id="groupid" name="groupid"  onchange="choseGroup(this.value)" style="width: 160px;">
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
		<c:set var="checkAll">
			<input type="checkbox" id="chkall" />
		</c:set>
		
		<display:table name="myFriendsList" id="myFriendsList"
			requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"
			export="true" class="table-grid">
			<display:column title="${checkAll}" media="html" style="width:30px;">
				<input type="checkbox" class="pk" name="id"
					value="${purEnquiryItem.corpEnt}">
			</display:column>			
			<display:column property="corpEnterprise.sysOrgInfoId" title="ID">				
			</display:column>
			<display:column property="corpEnterprise.name" title="企业名称" sortable="true"
				sortName="name"></display:column>
			<display:column property="corpEnterprise.tel" title="电话" sortable="true"
				sortName="tel"></display:column>
			<display:column property="corpEnterprise.address" title="地址" sortable="true"
		 		sortName="address"></display:column>
			<display:column property="corpEnterprise.connecter" title="联系人" sortable="true"
				sortName="address"></display:column>
		</display:table>
		<div class="l-clear"></div>
	</div>
	</div>
		<hotent:paging tableId="myFriendsList" />
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
