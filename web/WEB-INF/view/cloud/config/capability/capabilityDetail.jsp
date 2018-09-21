<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>能力详细信息</title>
<%@ include file="/commons/cloud/meta.jsp"%>
<!-- 此处注释掉 解决顶部浮动层两边空白问题    <%@include file="/commons/include/getById.jsp"%> -->
<link href="${ctx}/styles/cloud/main.css" rel="stylesheet"
	type="text/css" />
<!-- 此处注释掉 解决样式冲突问题 <link href="${ctx}/styles/cloud/global.css" rel="stylesheet" type="text/css" /> -->
<link href="${ctx}/styles/cloud/style.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/cloud/kindeditor.css" rel="stylesheet"
	type="text/css" />
<script charset="utf-8" src="${ctx}/js/cloud/jquery-1.2.4b.js"></script>

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
				}
			},
			error : function(data){
				alert('您还未登录，请登录后再加商友');
			},
		});
	}
</script>

</head>
<body>
<div id="all">
	<%@include file="/commons/cloud/top.jsp"%>
	
	<!-- 能力详细信息 开始 -->
	<div id="detail2" class="bggraybox">
		<div class="title"><a href="javascript:void(0)">在线能力</a> &gt; <a href="javascript:void(0)">${capability.typeName }</a></div>
		<p style="clear:both"></p>
		
		<p class="w1 bottom_1"><strong>能力基本信息</strong></p>
      <table width="990" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="15" colspan="6" align="left" valign="middle"></td>
    </tr>
  <tr>
    <td width="5%" height="30" align="left" valign="middle">&nbsp;</td>
    <td width="18%" height="30" align="left" valign="middle" class="table_cxx_01">能力分类</td>
    <td width="18%" height="30" align="left" valign="middle" class="table_cxx_01">能力标题</td>
    <td width="25%" align="left" valign="middle" class="table_cxx_01">关键词</td>
    <td width="20%" height="30" align="left" valign="middle" class="table_cxx_01">能力属性</td>
    <td width="14%" align="left" valign="middle" class="table_cxx_01">适用范围</td>
    </tr>
  <tr>
    <td height="30" align="left" valign="middle">&nbsp;</td>
    <td height="30" align="left" valign="middle" class="table_cxx_02">${capability.typeName }</td>
    <td height="30" align="left" valign="middle" class="table_cxx_02">${capability.name }</td>
    <td height="30" align="left" valign="middle" class="table_cxx_02">${capability.prokey }</td>
    <td height="30" align="left" valign="middle" class="table_cxx_02">
<c:forEach items="${propertyValues}" var="c1">

										<div class="row">
											<div class="label">${c1.propertyName }：</div>
											<div class="cell">${c1.propertyValue }</div>
										</div>

									</c:forEach>
</td>
    <td height="30" align="left" valign="middle" class="table_cxx_02">${capability.useScope }</td>
    </tr>
  <tr>
    <td height="30" align="left" valign="middle">&nbsp;</td>
    <td height="30" colspan="5" align="left" valign="middle" class="table_cxx_01">能力图片</td>
    </tr>
  <tr>
    <td height="30" align="left" valign="middle">&nbsp;</td>
    <td colspan="5" align="left" valign="middle" class="table_cxx_02"><img src="${ctx}${capability.pic }" onError="this.src='${ctx}/images/default-chance.jpg'" width="80" height="84" /></td>
    </tr>
    <tr>
    <td height="30" align="left" valign="middle">&nbsp;</td>
    <td height="30" colspan="5" align="left" valign="middle" class="table_cxx_01">能力详情</td>
    </tr>
  <tr>
    <td height="30" align="left" valign="middle">&nbsp;</td>
    <td height="30" colspan="5" align="left" valign="middle" class="table_cxx_02">${capability.info}</td>
    </tr>
      <tr>
    <td width="5%" height="30" align="left" valign="middle">&nbsp;</td>
    <td height="30" colspan="2" align="left" valign="middle" class="table_cxx_01">发布人</td>
    <td width="25%" align="left" valign="middle" class="table_cxx_01">发布日期</td>
    <td height="30" colspan="2" align="left" valign="middle" class="table_cxx_01">企业名称</td>
    </tr>
  <tr>
    <td height="30" align="left" valign="middle">&nbsp;</td>
    <td height="30" colspan="2" align="left" valign="middle" class="table_cxx_02">${capability.publisher }</td>
    <td height="30" align="left" valign="middle" class="table_cxx_02"><fmt:formatDate value="${capability.publishDate}"
										pattern="yyyy-MM-dd" /></td>
    <td height="30" colspan="2" align="left" valign="middle" class="table_cxx_02">
	${capability.entName }
									&nbsp;&nbsp;&nbsp;&nbsp;<img
										src="${ctx}/images/icon_add.jpg" width="70" height="18"
										onmouseover="src='${ctx}/images/icon_add2.jpg';"
										onmouseout="src='${ctx}/images/icon_add.jpg'"
										style="border: 0;" onClick="addFriend('${capability.entId}');">
	</td>
    </tr>
</table>

<p class="w1 bottom_1"><strong>物品基本信息</strong></p>
<table width="990" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="5" colspan="6" align="left" valign="middle"></td>
    </tr>
  <tr>
    <td width="5%" align="left" valign="middle"></td>
    <td colspan="5" align="left" valign="middle" >

	      <table width="100%" class="tt" id="product_list" border="0" align="center" cellspacing="0" cellpadding="0" style="margin-top:10px; margin-bottom:20px;">

					<div class="row">


						<th>名称</th>

						<th>描述</th>
						<th>单位</th>
						<th>价格</th>

					</div>
					<c:forEach items="${materiallist}" var="c2">
						<tr>
							<td><a
								href="${ctx}/cloud/config/material/view.ht?id=${c2.id }">${c2.name}</a>
							</td>

							<td>${c2.info }</td>
							<td>${c2.unit }</td>
							<td>${c2.price }</td>
						</tr>
					</c:forEach>
				</table>

	</td>
  </tr>
  </table>

	</div>
	<!-- 能力详细信息 结束-->
	</div>
	
	<div class="clear_10"></div>

	<!-- 底部版权区  开始 -->
<%@include file="/commons/cloud/foot.jsp"%>    
	<!-- 底部版权区  结束 -->
</body>
</html>