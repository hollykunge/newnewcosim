<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>统计报表管理</title>
<%@include file="/commons/include/get.jsp" %>
<link href="${ctx}/js/fusionChartsV3.3.1/Gallery/assets/ui/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/fusionChartsV3.3.1/Gallery/assets/prettify/prettify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/fusionChartsV3.3.1/Charts/FusionCharts.js"></script>
<script type="text/javascript" src="${ctx}/js/fusionChartsV3.3.1/Gallery/assets/prettify/prettify.js"></script>
<script type="text/javascript" src="${ctx}/js/fusionChartsV3.3.1/Gallery/assets/ui/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/fusionChartsV3.3.1/Gallery/assets/ui/js/lib.js" ></script>
<!--[if IE 6]>
    <script type="text/javascript" src="${ctx}/js/fusionChartsV3.3.1/Gallery/assets/ui/js/DD_belatedPNG_0.0.8a-min.js"></script>
    <script>
      /* select the element name, css selector, background etc */
      DD_belatedPNG.fix('img');

      /* string argument can be any CSS selector */
    </script>
<p>&nbsp;</p>
<P align="center"></P>
<![endif]-->

<script>
//判断是否柱状图显示还是曲线显示
function rendarChart(){
	if (GALLERY_RENDERER && GALLERY_RENDERER.search(/javascript|flash/i)==0)  FusionCharts.setCurrentRenderer(GALLERY_RENDERER);
	var random = Math.random();
	var chart = new FusionCharts("${ctx}/js/fusionChartsV3.3.1/Charts/MSLine.swf", random, "560", "400", "0", "0");
	//chart.setXMLData( dataString );
	var url="reportBusinessByMonthWithJson.ht?year=" + $("#year").val();
	chart.setJSONUrl(url);
	//chart.setJSONData(jsonString);
	chart.render("chartdiv");
}

$(function(){
	rendarChart();
});
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">业务量统计列表</span>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post">
					<div class="row" style="font-size:12px;">						
						<span class="label">年份：</span>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
        	<div id="chartdiv" align="center">月度业务量统计</div>
		</div>	
	</div> <!-- end of panel -->
</body>
</html>


