<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/cloud/meta.jsp"%>

<link href="${ctx}/styles/cloud/softlist_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function(){
		var returnURL="${returnURL}";
		var isLogin ="${isLogin}";
		if(isLogin == 0 ){
			    $(".sq11").click(function(){
			    	$("#mainframe").attr("src","${ctx}/cloud/cloudResource/resourceManagement/listResourceInstance.ht?resourceID=${resourceID}&mode=bat");
			    });
			    $(".sq12").click(function(){
			    	$("#mainframe").attr("src","${ctx}/cloud/cloudResource/resourceManagement/listResourceInstance.ht?resourceID=${resourceID}&mode=interact");
				});
				
				$(".sq21").click(function(){
					$("#mainframe").attr("src","${ctx}/abc.ht");
				});
				
				$(".sq22").click(function(){
					$("#mainframe").attr("src","${ctx}/abc.ht");
				});
				
				 $(".sq4").click(function(){
					 $("#mainframe").attr("src","${ctx}/abc.ht");
				});
		}else{
			 $(".sq11").click(function(){
			    	$("#mainframe").attr("src","${ctx}/cloud/cloudResource/resourceManagement/listResourceInstance.ht?resourceID=${resourceID}&mode=bat");
			    });
			    $(".sq12").click(function(){
			    	$("#mainframe").attr("src","${ctx}/cloud/cloudResource/resourceManagement/listResourceInstance.ht?resourceID=${resourceID}&mode=interact");
				});
				
				$(".sq21").click(function(){
					$("#mainframe").attr("src","${ctx}/cloud/cloudResource/approvalResource/getResource.ht?resourceUrl=${lsf}/cloud/hpc/com.portal.services_th.xml?_uri=//com.cloud.grid/list.jobs");
				});
				
				$(".sq22").click(function(){
					$("#mainframe").attr("src","${ctx}/cloud/cloudResource/approvalResource/getResource.ht?resourceUrl=${lsf}/cloud/hpc/com.data.system_th.xml?_uri=//com.data.system/list.spoolers");
				});
				
				$(".sq23").click(function(){
					$("#mainframe").attr("src","${ctx}/cloud/cloudResource/approvalResource/getResource.ht?resourceUrl=${lsf}/cloud/hpc/efviews_desktop_th.xml?_uri=//efviews_desktop/efviews.list.sessions");
				});
				
				 $(".sq4").click(function(){
						$("#mainframe").attr("src","${ctx}/cloud/cloudResource/approvalResource/getResource.ht?resourceUrl=${lsf}/cloud/hpc/com.cluster.monitor.th.xml?_uri=//com.cluster.monitor/list.hosts");
				});	
		}
	});
</script>
</head>
<body >

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
    <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="${ctx}/images/icon3.jpg"   width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle">
  <div class="sq23">
    <a href="javascript:void(0)" class="link02">图形管理</a>
    </div>
</td>
  </tr>
 </table>

 </table>
    </div>
    <div style="margin:0 auto; width:865px; float:right;height:100%;">
     <iframe  id="mainframe" border="0"  src="${ctx}/cloud/cloudResource/resourceManagement/listResourceInstance.ht?resourceID=${resourceID}&mode=interact" style="width:100%;height:640px;overflow-x:hidden" frameborder="0" scrolling="auto"></iframe>
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
