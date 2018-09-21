<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 
<%@ include file="/commons/include/html_doctype.html"%>
<%@ include file="/commons/cloud/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>模型在线浏览</title>
    <%@include file="/commons/cloud/meta.jsp"%>
</head>
<body>
    <form id="form1" runat="server">
    <div id="DIV1">
    
<object classid='clsid:B6FCC215-D303-11D1-BC6C-0000C078797F' width="100%" & height="100%" >
<param name=SRC value=<%=request.getParameter("fileUrl") %> ></param>
</object>

    </div>
    <table border="0" align="center"><input type="button" value="返回" onclick="history.go(-1)" /></table>
    </form>
</body>
</html>
