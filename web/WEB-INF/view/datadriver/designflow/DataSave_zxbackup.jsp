<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8"
         import="java.net.URLDecoder" import="java.util.Iterator" import="org.jdom.output.XMLOutputter"
         import="org.jdom.output.Format" import="org.jdom.Element" import="org.jdom.Document"
         import="org.jdom.input.SAXBuilder" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.*" %>
<html lang="zh-CN">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
<BODY>
<%
    request.setCharacterEncoding("utf-8");
    String test = URLDecoder.decode(request.getParameter("xml"), "UTF-8").replaceAll("\n", "");
    System.out.println(test);
    Reader in = new StringReader(test);
    Document doc = (new SAXBuilder()).build(in);

    Element mxGraphmodel = doc.getRootElement();
    Element root = mxGraphmodel.getChild("root");
    String projectID = root.getChild("Layer").getAttributeValue("projectId");
    java.util.List task = root.getChildren("Task");
    for (Iterator i = task.iterator(); i.hasNext(); ) {
        Element el = (Element) i.next();

        System.out.println(el.getAttribute("label"));
        el.setAttribute("oracleid", "111");
    }
    System.out.println(doc.toString());
    Format format = Format.getCompactFormat();
    format.setEncoding("utf-8");
    format.setIndent(" ");
    XMLOutputter xmlOutputter = new XMLOutputter();
    java.io.StringWriter a = new java.io.StringWriter();
    xmlOutputter.output(doc, a);
    String str = a.toString();
    System.out.println(str);
    int i = 1;
    i++;
    out.print("<script>alert('数据添加成功！');window.location='DataQuery_zx.jsp';</script>");
%>
</BODY>
</HTML>
