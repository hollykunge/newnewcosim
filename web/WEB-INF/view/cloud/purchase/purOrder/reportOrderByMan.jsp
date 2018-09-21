<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>采购订单管理</title>
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
function rendarChart(type){
	type = type || "Column3D6";
	var swf = "Column3D.swf";
	if(type=='Line2')
		swf = "Line.swf";
	
	if (GALLERY_RENDERER && GALLERY_RENDERER.search(/javascript|flash/i)==0)  FusionCharts.setCurrentRenderer(GALLERY_RENDERER);
	var chart = new FusionCharts("${ctx}/js/fusionChartsV3.3.1/Charts/" + swf, type, "560", "400", "0", "0");
	//chart.setXMLData( dataString );
	var param = "";
	if($('#year').val()!=0)
		param += "?year=" + $('#year').val() + "&month=" + $('#month').val();
	
	chart.setJSONUrl( "reportOrderByManWithJson.ht" + param);
	//chart.setJSONData(jsonString);
	chart.render("chartdiv");
}

$(function(){
	rendarChart();
	$('#btnSearch').click(function(){	
		rendarChart($(":input[name=showType]:checked").val());
	});
})
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">采购业务量统计</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar" style="font-size:12px;">
					<div class="group"><a class="link back" id="btnSearch">统计</a></div>
					<input type="radio" value="Column3D6" checked name="showType">柱状图</input>
					<input type="radio" value="Line2" name="showType">线状图</input>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">采购年份:</span>
							<select name="year" id="year">
								<option value="0">请选择年份</option>
								<option>2013</option>
								<option>2014</option>
								<option>2015</option>
								<option>2016</option>
								<option>2017</option>
								<option>2018</option>
								<option>2019</option>
								<option>2020</option>
							</select>
						<span class="label">采购月份:</span> 
							<select name="month" id="month">
								<option>01</option>
								<option>02</option>
								<option>03</option>
								<option>04</option>
								<option>05</option>
								<option>06</option>
								<option>07</option>
								<option>08</option>
								<option>09</option>
								<option>10</option>
								<option>11</option>
								<option>12</option>
							</select>					
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
        	<div id="chartdiv" align="center">采购员业务量统计</div>
		</div>
	</div> <!-- end of panel -->
</body>
</html>


