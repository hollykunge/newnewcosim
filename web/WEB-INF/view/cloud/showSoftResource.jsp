<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/commons/cloud/meta.jsp"%>
<title>行业软件资源</title>
<script type="text/javascript">
	$(document).ready(function(){
		var returnURL="${returnURL}";
		if(returnURL.length > 0 ){
			    $(".sq11").click(function(){
			    	$("#mainframe").attr("src","${ctx}/cloud/cloudresource/softList.ht?softType=${softType}&VM_IP_Address=${VM_IP_Address}");
			    });
			    $(".sq12").click(function(){
			    	$("#mainframe").attr("src","${ctx}/cloud/cloudresource/interactAppList.ht?softType=${softType}&VM_IP_Address=${VM_IP_Address}");
				});
				
				$(".sq21").click(function(){
					$("#mainframe").attr("src","${ctx}"+returnURL);
				});
				
				$(".sq22").click(function(){
					$("#mainframe").attr("src","${ctx}"+returnURL);
				});
				
				 $(".sq4").click(function(){
					 $("#mainframe").attr("src","${ctx}"+returnURL);
				});
		}else{
			    $(".sq11").click(function(){
	/*				$("#mainframe").attr("src","${VM_IP_Address}"+"/cloud/hpc/efviews_desktop_th.xml?_uri=//efviews_desktop/config");*/
					$("#mainframe").attr("src","${ctx}/cloud/cloudresource/softList.ht?softType=${softType}&VM_IP_Address=${VM_IP_Address}");
			    });
				
				$(".sq12").click(function(){
					$("#mainframe").attr("src","${ctx}/cloud/cloudresource/interactAppList.ht?softType=${softType}&VM_IP_Address=${VM_IP_Address}");
				});
				$(".sq21").click(function(){
					$("#mainframe").attr("src","${VM_IP_Address}"+"/cloud/hpc/com.portal.services_th.xml?_uri=//com.cloud.grid/list.jobs&EF_GRIDML_COMPRESS=yes&_username=jhadmin&_password=jhadmin");
				});
				
				$(".sq22").click(function(){
					$("#mainframe").attr("src","${VM_IP_Address}"+"/cloud/hpc/com.data.system_th.xml?_uri=//com.data.system/list.spoolers&_ui=&_countHiddenFiles=false&_username=jhadmin&_password=jhadmin");
				});
				
				 $(".sq4").click(function(){
						$("#mainframe").attr("src","${VM_IP_Address}"+"/cloud/hpc/com.cluster.monitor.th.xml?_uri=//com.cluster.monitor/list.hosts&_username=jhadmin&_password=jhadmin");
				});	
		}
	});
</script>
</head>
<body >
<%-- <iframe id="asdf"  src="${VM_IP_Address}"+"/cloud/hpc/com.portal.services.xml?_uri=//com.portal.services/login&_username=jhadmin&_password=jhadmin" width="500px" height="500px"></iframe>
  --%><!-- <script language="javascript">
function loginSubmit()
{
	var loginform=document.forms["loginform"];
	loginform._username.value="${userName}";
	loginform._password.value="${userPw}";
	var returnURL="${returnURL}";
	if(returnURL.length > 0 ){
	//	alert("未登录");
	}
	else{
	//	alert("正在进行单点登录");
		loginform.submit();
	}
}
</script> -->

<%-- <form action="${loginURL}" target="hiddenIframe" method="post" name="loginform">
<input name="_username" type="hidden" />
<input name="_password" type="hidden" />
</form>
<iframe  src="" id="hiddenIframe" name="hiddenIframe" style="display:none;"></iframe> --%>
<div id="all">
<%@include file="/commons/cloud/top.jsp"%>

<!-- 页面主体  开始 -->
<div id="main">
	<div style="margin:0 auto; width:150px; float:left; ">
<table width="140" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td height="630px" align="center" valign="top" style="border: #9ccefd 1px solid; background-color: #f6f7f9; font-size: 14px;">
     <table width="138" border="0" cellspacing="0" cellpadding="0"  class="parenttable">
       <tr>
         <td height="33" align="center" valign="middle" class="table_right01">
         	<div class="lmenu">
         		<h2>软件资源应用</h2>
         	</div>
         </td>
       </tr>
	</table>
<table width="138" border="0" cellspacing="0" cellpadding="0" class="sontable">	  
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="${ctx}/images/icon3.jpg"  width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle">
    <div class="sq11">
    <a href="javascript:void(0)" class="link02">批作业应用</a>
    </div>
    </td>
  </tr>
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="${ctx}/images/icon3.jpg"   width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle">
        <div class="sq12">
    <a href="javascript:void(0)" class="link02">交互式应用</a></td>
        </div>
  </tr>  
 </table>

   <table width="138" border="0" cellspacing="0" cellpadding="0" class="parenttable">
     <tr>
       <td height="33" align="center" valign="middle" class="table_right01">
       	<div class="lmenu">
         		<h2>资源监控管理</h2>
         	</div>
       </td>
     </tr>
   </table>
   <table width="138" border="0" cellspacing="0" cellpadding="0" class="sontable">	  
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="${ctx}/images/icon3.jpg"   width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle">
  <div class="sq21">
    <a href="javascript:void(0)" class="link02">作业管理</a>
    </div>
    </td>
  </tr>
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="${ctx}/images/icon3.jpg"   width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle">
  <div class="sq22">
    <a href="javascript:void(0)" class="link02">数据管理</a>
    </div>
</td>
  </tr>
 </table>

 </table>
    </div>
    <div style="margin:0 auto; width:865px; float:right;height:100%;">
     <iframe  id="mainframe" border="0"  src="${ctx}/cloud/cloudresource/softList.ht?softType=${softType}&VM_IP_Address=${VM_IP_Address}" style="width:100%;height:640px;overflow-x:hidden" frameborder="0" scrolling="auto"></iframe>
   	 </div>
    </div>
 
<!-- 页面主体  结束 -->
</div>

<div style="width:100%;height:40px;clear:both; padding-top:20px;" />
<!-- 底部版权区  开始 -->
<%@include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
