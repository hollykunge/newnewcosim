<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<%@ include file="/commons/cloud/meta.jsp"%>
</head>
<body>
${path}
    <form id="form1" runat="server">
    <div id="DIV1">
    
<object classid='clsid:B6FCC215-D303-11D1-BC6C-0000C078797F' width="100%" & height="100%" >
<param name=SRC value="${path}" ></param>
</object>

    </div>
    <table border="0" align="center"><input type="button" value="返回" onclick="history.go(-1)" /></table>
    </form>
</body>
</html>
