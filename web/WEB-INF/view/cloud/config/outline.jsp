<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>

<body level1="基础设置 ">
<table width="140" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td height="444" align="center" valign="top" style="border: #9ccefd 1px solid; background-color: #f6f7f9; font-size: 14px;"><table width="138" border="0" cellspacing="0" cellpadding="0"  class="parenttable">
       <tr>
         <td height="33" align="center" valign="middle" class="table_right01"><br>物品设置</td>
       </tr>
</table>
<table width="138" border="0" cellspacing="0" cellpadding="0" class="sontable">			  
 
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/materialcatalog/list.ht')" class="link02">物品目录</a></td>
  </tr>
    
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/material/list_classes.ht');" class="link02">添加物品</a></td>
  </tr>
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/material/list.ht');" class="link02">已发布物品</a></td>
  </tr>
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/material/list.ht');" class="link02">物品草稿箱</a></td>
  </tr>  
    
   <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/materialclass/list.ht')" class="link02">物品分类</a></td>
  </tr> 
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/materialProperty/list.ht')" class="link02">属性分类</a></td>
  </tr> 
   
</table>
<div style=" width:130px; height:10px;"></div>
<table width="138" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td height="33" align="center" valign="middle" class="table_right01">能力设置</td>
   </tr>
</table>
<table width="138" border="0" cellspacing="0" cellpadding="0" class="sontable">		
 
 <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/capabilityClass/list.ht');" class="link02">能力分类</a></td>
  </tr> 
   <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/capabilityProperty/list.ht');" class="link02">属性分类</a></td>
  </tr>
  
   <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/capability/list.ht');"class="link02">已发布能力</a></td>
  </tr>   
    <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/capability/listCg.ht');"class="link02">能力草稿</a></td>
  </tr>  	  
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/cloud/config/capability/list.ht');"class="link02">能力发布</a></td>
  </tr>    
 </table>
<div style=" width:130px; height:10px;"></div>
<table width="138" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td height="33" align="center" valign="middle" class="table_right01">组织管理</td>
   </tr>
</table>
<table width="138" border="0" cellspacing="0" cellpadding="0" class="sontable">
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/auth/enterprise!info.action');" class="link02">企业信息</a></td>
  </tr>			  
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/platform/system/sysOrg/list.ht');" class="link02">部门管理</a></td>
  </tr>
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/platform/system/sysUser/list.ht');" class="link02">员工管理</a></td>
  </tr>
</table>
</body>