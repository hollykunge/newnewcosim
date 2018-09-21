<%@ page contentType="text/html" pageEncoding="UTF-8"
import="java.net.URLDecoder"	import="java.sql.*,a.DBoper,java.io.*,a.table"%>
<%@ page import="cn.edu.buaa.model.*,cn.edu.buaa.service.*" %>
<HTML>
	<BODY>
		<%
			request.setCharacterEncoding("utf-8");
			String test = URLDecoder.decode(request.getParameter("xml"), "UTF-8").replaceAll("&#xa;", "");
			//String xml = (String) request.getParameter("xml");
			System.out.println(test);
			int i=1;
			i++;
				out.print("<script>alert('数据添加成功！');window.location='DataQuery_zx.jsp';</script>");


		%>
	</BODY>
</HTML>
