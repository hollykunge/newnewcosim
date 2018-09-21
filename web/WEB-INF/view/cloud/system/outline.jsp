<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<body level1="任务处理">
<table width="140" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td height="444" align="center" valign="top" style="border: #9ccefd 1px solid; background-color: #f6f7f9; font-size: 14px;">
<c:forEach items="${leftResourcesList}" var="leftResourcesItem">
<table width="138" border="0" cellspacing="0" cellpadding="0"  class="parenttable">
       <tr>
         <td height="33" align="center" valign="middle" class="table_right01">
         <c:choose>
         	<c:when test="${not empty leftResourcesItem.defaultUrl}">
         	<a href="javascript:void(0);" onclick="openFrame('${ctx}${leftResourcesItem.defaultUrl}');" class="link02">${leftResourcesItem.resName}</a>
         	</c:when>
         	<c:otherwise>
         		${leftResourcesItem.resName}
         	</c:otherwise>
         </c:choose>
         </td>
       </tr>
	</table>
<c:forEach items="${leftResourcesItem.children}" var="child"> 
<table width="138" border="0" cellspacing="0" cellpadding="0" class="sontable">	  
  <tr>
    <td width="41" height="35" align="center" valign="middle"><span style="font-family: Verdana, Geneva, sans-serif;"><img src="../images/icon3.jpg" width="13" height="9" /></span></td>
    <td width="97" align="left" valign="middle"><a href="javascript:openFrame('${ctx}/bpmx');" class="link02">${child.resName}</a></td>
  </tr>
 </table>
 </c:forEach>
</c:forEach>
</td></tr>
 </table>
</body>