<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的商圈</title>
<%@ include file="/commons/cloud/meta.jsp"%>
<link href="${ctx}/styles/default/css/Aqua/css/ligerui-grid.css"
	rel="stylesheet" type="text/css" />

<style type="text/css">
.panel-page {
	border: 1px #8dc2e3 solid;
	border-top: none;
	height: 28px;
	width: 598px;
	margin-top: -5px;
	background: #dff5fd;
	/*background: url(../images/tool_bg.jpg) repeat-x;*/
}

.zxc {
	width: 80px;
	height: 25px;
	line-height: 25px;
	float: left;
	text-align: center;
	color: #1F73BB;
	cursor: pointer;
	float: left;
	font-size: 12px;
	font-weight: bold;
}
</style>
<script type="text/javascript">
	function choseGroup(groupid, id) {
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
	$(function() {
		$("#content").hide();

		$("#qwe").attr("style", "background-color:#9ccefd;");
		
		//$("#pub_tab").hide();
		$("#qwe").click(function() {
			$(this).attr("style", "background-color:#9ccefd;");
			$("#asd").removeAttr("style");
			$("#sygl").removeAttr("style");
			$("#content").hide();
			$("#msg_list").show();

			$("#pub_tab").show();
		});
		
		//点击商圈动态,动态显示商圈Applet
		$("#asd").click(function() {
			$(this).attr("style", "background-color:#9ccefd;");
			$("#qwe").removeAttr("style");
			
			$("#msg_list").hide();
			$("#content").show();
			
			var s = '<div><applet alt="no jre" codebase="${ctx }/applet" archive="iel.jar" CODE="DataGraphVis.class" WIDTH=560 HEIGHT=455 name="dataGraph">';
				s += '<param name="url" value="${myCompany.sysOrgInfoId }.xml" />';
				s += '</applet>';
				s += '</div>';
			
			$("#content").html(s);
			$("#pub_tab").hide();
		});

	})
</script>


</head>
<body>
	<div id="all">
		<%@include file="/commons/cloud/top_console.jsp"%>

		<!-- 商圈管理 -->
		<div id="myhome">
			<div id="home_left">
				<!--左侧菜单-->
				<%@include file="/commons/cloud/console_left.jsp"%>
				<div id="my_center">
					<div id="chat2">
						<div id="qwe" class="zxc">
							<span>商友列表</span>
						</div>
						
						<!-- &nbsp;&nbsp; -->
						<div id="asd" class="zxc">
							<span>商圈动态</span>
						</div>
					</div>

					<!-- 商友管理列表 -->
					<div id="msg_list">
						<ul>
							<c:forEach items="${friends}" var="friends">
								<li>
									<div class="avatar2">
										<a
											href="${ctx}/cloud/console/enterprise.ht?EntId=${friends.corpEnterprise.sysOrgInfoId} "
											class="link04"> <img src="#"
											onError="this.src='${ctx}/images/default-chance.jpg'"
											width="80" height="80" />
										</a>
									</div>
									<div class="msg_right">
										<p class="msg_tt">
											<a
												href="${ctx}/cloud/console/enterprise.ht?EntId=${friends.corpEnterprise.sysOrgInfoId} ">${friends.corpEnterprise.name}</a>
										</p>
										<P class="msg_tt">
											公司地址:${friends.corpEnterprise.address} <span
												style="float: right;"><img
												src="${ctx }/images/icon_del2.jpg" width="52" height="18"
												onmouseover="src='${ctx}/images/icon_del.jpg';"
												onmouseout="src='${ctx}/images/icon_del2.jpg';"
												style="border: 0;" onClick="delFriend('${friends.id}');" /></span>
										</P>
									</div>
									<div class="rightbtn">
										<p>
											商友分组：<select id="groupid" name="groupid"
												onchange="choseGroup(this.value,'${friends.id}')">
												<option value="">未分组</option>

												<c:forEach items="${businessAreaGroupList}" var="c">

													<c:if test="${c.id==friends.groupid}">
														<option value="${c.id}" selected="selected ">
															${c.groupname}</option>
													</c:if>
													<c:if test="${c.id!=friends.groupid}">
														<option value="${c.id}">${c.groupname}</option>
													</c:if>
												</c:forEach>
											</select>
										</p>
									</div>
									<p class="clear"></p>
								</li>
							</c:forEach>
						</ul>
						<div style="height: 26px; background: #DFF5FD; border: 1px solid #9CCEFD; border-top: 0">
							<hotent:paging tableId="busiareaList" />
						</div>
					</div>

					<div id="content">
						
					</div>

				</div>
			</div>
			<%@include file="/commons/cloud/console_right.jsp"%>
			<!--all结束-->
		</div>
	</div>
	<div style="width: 100%; height: 40px; clear: both; padding-top: 20px;" />
	<!-- 底部版权区  开始 -->
	<div class="clear_25"></div>
	<%@ include file="/commons/cloud/foot.jsp"%>
	<!-- 底部版权区  结束 -->
</body>
</html>
