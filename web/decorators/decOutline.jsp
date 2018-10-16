<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@include file="/commons/cloud/global.jsp" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<html lang="zh-CN" style="height: 100%">
<head>
    <title>协同设计</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/newtable/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/newtable/jquery.js"></script>
    <script src="${ctx}/newtable/bootstrap.js"></script>
    <script>
        $(function () {
            var isClient;
            try {
                isClient = JSInteraction.isme();
                document.getElementById("mainframe").style.width = "100%";
                document.getElementById("mainframe").style.height = "100%";
                document.getElementById("mainframe").style.margin = "0px";
            }
            catch (e) {
                $("#mainframe").height(getHeight());
                $("#mainframe").css("padding-top", "65px");
                console.log(e)
            }
            $(".nav li a").each(function (index) {
                $this = $(this);
                if ($this[0].href == String(window.location)) {
                    $this.addClass("active");
                }
            });
        });

        function getHeight() {
            return $(window).height() - $('.navbar').outerHeight();
        }
    </script>
</head>
<body style="height: 100%">
<%@include file="/commons/cloud/top_console.jsp" %>
<iframe src="main.ht" frameborder="0" scrolling="no" id="mainframe" style="width: 100%;"></iframe>

</body>
</html>
