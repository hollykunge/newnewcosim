<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/commons/cloud/meta.jsp"%>
<link href="${ctx}/skins/main.css" rel="stylesheet"/>
<script src="${ctx}/skins/jquery.js" type="text/javascript"></script>
<script src="${ctx}/skins/main.js" type="text/javascript"></script>
<title>3D打印服务</title>

<script>
function viewBigImage(a){
	var img = $(a).parent().find('img');
	var src = img.attr('src');
	$('#ii').attr('src',src);
}

function beginPrint(){
	location.href='${ctx}/cloud/cloudresource/3dprint.ht?fileId=10000023420001';
}

function addNums(){
	$('#nums').val($('#nums').val()*1 + 1);
	$("#price").text('￥ ' + $('#nums').val()*1000);
}

function divNums(){
	if($('#nums').val() != 1){
		$('#nums').val($('#nums').val()*1 -1);
		$("#price").text('￥ ' + $('#nums').val()*1000);
	}
}
</script>
</head>
	
<body>
<div id="all">			
<%@include file="/commons/cloud/top2.jsp"%>	
		
	<div id="detail_list" class="bggraybox">
		<div class="title"><a href="javascript:void(0)">模型制作</a><a href="javascript:void(0)"></a></div>
		<ul>
			<li>
				<div  style=" padding-left:350px; float:left;">
					<div style="float:left;"><img src="${ctx}/skins/complete.png" width="48" height="48" /></div><div style="padding-top:15px; float:left; width:400px; font-size:18px; font-weight:bold; padding-left:20px;">打印申请提交成功!
					</div>
				</div>
				<p class="clear"></p>
			</li>
		</ul>
		<!--底部灰色背景-->
	  <div class="bgbox_gray"></div>
		<script type="text/javascript">
			$('#detail_list li:last').css('margin-bottom','2px');
		</script>
		
	</div>
</div>

	<br /><br /><br /><br /><br /><br />
</div>
	
    
<%@include file="/commons/cloud/foot2.jsp"%>
</body>
</html>
