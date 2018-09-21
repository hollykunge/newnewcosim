<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源搜索列表</title>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/get.jsp"%>

<link href="${ctx}/styles/cloud/softlist_style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<script type="text/javascript">
	$(function(){
		$("#batjob").click(function(){
			$("#mainframe").attr("src","${ctx}/cloud/cloudResource/resourceManagement/showAllResource.ht?mode=bat");
			return false;
		});
		$("#interactjob").click(function(){
			$("#mainframe").attr("src","${ctx}/cloud/cloudResource/resourceManagement/showAllResource.ht?mode=interact");
			return false;
		});
		$("#jobManage").click(function(){
			$("#mainframe").attr("src","http://60.195.252.27:8080/cloud/hpc/com.portal.services_th.xml?_uri=//com.cloud.grid/list.jobs&EF_GRIDML_COMPRESS=yes&_username=jhadmin&_password=jhadmin");
			return false;
		});
		$("#dataManage").click(function(){
			$("#mainframe").attr("src","http://60.195.252.27:8080/cloud/hpc/com.data.system_th.xml?_uri=//com.data.system/list.spoolers&_ui=&_countHiddenFiles=false&_username=jhadmin&_password=jhadmin");
			return false;
		});
	});
</script>
</head>

<body>
<div id="all">
	<!-- 顶部浮动层  开始 -->
	<%@include file="/commons/cloud/top.jsp"%>

	<!-- 主导航  结束 -->
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
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="/bpmx3_ht2y/images/icon3.jpg"  width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle">
    <div class="sq11">
    <a id="batjob" href="javascript:void(0)" target="mainframe" class="link02">批作业应用</a>
    </div>
    </td>
  </tr>
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="/bpmx3_ht2y/images/icon3.jpg"   width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle">
        <div class="sq12">
    <a id="interactjob" href="javascript:void(0)" target="mainframe" class="link02">交互式应用</a></td>
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
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="/bpmx3_ht2y/images/icon3.jpg"   width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle">
  <div class="sq21">
    <a id="jobManage" href="javascript:void(0)" class="link02">作业管理</a>
    </div>
    </td>
  </tr>
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="/bpmx3_ht2y/images/icon3.jpg"   width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle">
  <div class="sq22">
    <a id="dataManage" href="javascript:void(0)" class="link02">数据管理</a>
    </div>
</td>
  </tr>
 </table>

 </table>
    </div>
    <div style="margin:0 auto; width:865px; float:right;height:100%;">
     <iframe  id="mainframe" name="mainframe" border="0"  src="${ctx}/cloud/cloudResource/resourceManagement/search.ht?searchinput=${searchinput}" style="width:100%;height:640px;overflow-x:hidden" frameborder="0" scrolling="auto"></iframe>
   	 </div>
    </div>
 
<!-- 页面主体  结束 -->
</div>
</div>

<!-- 底部版权区  开始 -->
<%@ include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
