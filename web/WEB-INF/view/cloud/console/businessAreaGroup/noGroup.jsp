<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的商圈</title>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
<link href="../css/main.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/cloud/FusionCharts.js" type="text/javascript"></script>
 
  
<script type="text/javascript">


function choseGroup(groupid,id){
	$.ajax({
											type : 'POST',
											url : "${ctx}/cloud/console/busiarea/choseGroup.ht",
											data : {
												groupid : groupid,
												id : id
												
											},
											dataType : "json",
											success : function(data) {
												
												if (data.choseGroup == "true") {
													alert('操作成功');
													//window.location = "${ctx}/cloud/console/busiarea/busiarea.ht"
												} else if (data.result == 0) {
													alert('操作失败');
												}
											},
											error : function(data) {
												alert('处理错误，请检查');
											}
										});
							
	
}
	function delFriend(busiareaId) {
		//如果未登录，提示登录信息
		$.ligerMessageBox
				.confirm(
						'确认框',
						'您确认要将此商友删除吗?',
						function(r) {
							if (r) {
								$
										.ajax({
											type : 'POST',
											url : "${ctx}/cloud/console/busiarea/delFriend.ht",
											data : {
												busiareaId : busiareaId
											},
											dataType : "json",
											success : function(data) {
											 
												if (data.delFriend == "true") {
													alert('商友已经删除');
													window.location = "${ctx}/cloud/console/busiarea/busiarea.ht"
												} else if (data.result == 0) {
													alert('不是商友，无法删除');
												}
											},
											error : function(data) {
												alert('处理错误，请检查');
											}
										});
							}
						});
	}
</script>
  

</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    
			   
			   
			        <span class="tbar-label">未分组列表</span>
			     			   
		     
		</div>
		 
	</div>
	<div class="panel-body">
	 
	 
	 
		 		<div id="msg_list2">
					<ul>
					<c:forEach items="${busiareaList }" var="friends">
						<li>
							<div class="avatar2"><a href="${ctx}/cloud/console/enterprise.ht?EntId=${friends.corpEnterprise.sysOrgInfoId} "
														class="link04"> <img src="#"
														onError="this.src='${ctx}/images/default-chance.jpg'"
														width="80" height="80" />
													</a></div>
							<div class="msg_right">
								<p class="msg_tt"><a href="${ctx}/cloud/console/enterprise.ht?EntId=${friends.corpEnterprise.sysOrgInfoId} "
														class="link04">${friends.corpEnterprise.name}</a></p>
                                <P class="msg_tt">公司地址:${friends.corpEnterprise.address} 
                                <span style="float:right;"><img src="${ctx }/images/icon_del2.jpg" width="52"
													height="18" onmouseover="src='${ctx}/images/icon_del.jpg';"
													onmouseout="src='${ctx}/images/icon_del2.jpg';"
													style="border: 0;" onClick="delFriend('${friends.id}');"/></span>
													</P>
							</div>
							<div class="rightbtn">
								<p>商友分组：<select id="groupid" name="groupid"  onchange="choseGroup(this.value,'${friends.id}')">
																<option value=""> 未分组</option>
								 
															 <c:forEach items="${businessAreaGroupList}" var="c">
															  
															 <c:if test="${c.id==friends.groupid}">
															 	<option value="${c.id}" selected="selected "> ${c.groupname} </option>
															  </c:if>
															 <c:if test="${c.id!=friends.groupid}">
															 	<option value="${c.id}"  > ${c.groupname}</option>
															  </c:if>
															</c:forEach>
														</select></p>
							</div>
							<p class="clear"></p>
						</li>
					</c:forEach>
					</ul>
				</div>

					<div id="pub_tab" class="pub_tab" 
							style="width: 100%; height: 25px; border-top-width: 0px;">
							 
								<hotent:paging tableId="busiareaList" />
							 
						</div>			 
	</div>
	 
</div>
</body>
</html>
