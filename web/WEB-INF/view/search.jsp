<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ include file="/commons/include/html_doctype.html"%>
<%@ include file="/commons/cloud/global.jsp"%>
<style>
.header_input{
	width:475px; height:18px; font-size:14px; color:#999; padding:4px; line-height:22px;
}
.header_input1{
	width:373px; height:18px; font-size:14px; color:#999; padding:4px; line-height:22px;
}
.sel{
   color: #999;font-size:14px;
}
.null{display: none;font-size:14px;}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源搜索</title>
<%@include file="/commons/cloud/meta.jsp"%>
<!-- ui-tabs -->
<link href="${ctx}/styles/cloud/ui_tabs.css" rel="stylesheet"
	type="text/css" />
<script src="${ctx}/js/cloud/ui_core.js" type="text/javascript"></script>
<script src="${ctx}/js/cloud/ui_tabs.js" type="text/javascript"></script>
<script type="text/javascript">
var svalue="搜索企业";
$(document).ready(function(){	
		 $(".sel").addClass("null");
		$("#resource").click(function(){
		    $(".header_input").removeClass("header_input1");
		    $(".sel").addClass("null");
			$(".header_input").val("搜索资源");
			$("#search1 form").attr("action","${ctx}/cloud/config/cloudSrc/search.ht");
			svalue = "搜索资源";
		});
		$("#ability").click(function(){
		    $(".header_input").removeClass("header_input1");
		    $(".sel").addClass("null");
		  
			$(".header_input").val("搜索能力");
			$("#search1 form").attr("action","${ctx}/cloud/config/capability/search.ht");
			svalue = "搜索能力";
		});
		$("#chance").click(function(){
		    $(".header_input").addClass("header_input1"); 
	        $(".sel").removeClass("null");
			$(".header_input").val("搜索商机");
			$("#search1 form").attr("action","${ctx}/cloud/config/business/search.ht");
			svalue = "搜索商机";
		});
		$("#company").click(function(){
			$(".header_input").removeClass("header_input1");
			$(".header_input").addClass("header_input");
		    $(".sel").addClass("null");
			$(".header_input").val("搜索企业");
			$("#search1 form").attr("action","${ctx}/cloud/system/enterprises/search.ht");
			svalue = "搜索企业";
		});
		$(".header_input").focus(function(){
		    $(".sel").addClass("null");
		   
			var type = $(".header_input").attr("value");
			if (type==svalue){
				this.value='';
				this.className='header_input';
			}
			if(svalue == "搜索商机"){
				$(".sel").removeClass("null");
				$(".header_input").addClass("header_input1");
			}
			 
			  
		});
		
		$(".header_input").blur(function(){
			var type = $(".header_input").attr("value");
			if (type==''){
				this.value=svalue;
			}
		});
		
		$("#company").addClass("tab_menu_focus");
		$(".tab_menu").click(function(){
			var index = $(".tab_menu").index($(this));
			$(".tab_menu").removeClass("tab_menu_focus");
			$(".tab_menu:eq("+index+")").addClass("tab_menu_focus");
		});
		
	});
	
</script>
</head>
<body>
<!-- 顶部浮动层  开始 -->
<div id="status_bar">
  <div class="status_bar_01">
    	<div class="status_bar_02"><a href="${ctx }/index.ht" class="link01">平台首页</a></div>
    <div class="status_bar_02">欢迎您，请<a href="${ctx }/loginCloud.ht" class="link01">登录</a> │ <a href="${ctx }/reg.ht" class="link01">注册</a></div>
    <div class="status_bar_03"><a href="${ctx }/contactus.jsp" class="link01">联系客服</a></div>
   <%--  <div class="status_bar_03"><a href="${ctx }/help.chm" class="link01">帮助中心</a></div> --%>
  </div>
</div>
<div class="clear_25"></div>
<!-- 顶部浮动层  结束 -->
<!-- header  开始 -->
<div id="header"></div>
<div class="clear_10"></div>
<!-- header  结束 -->
<div style="margin:0 auto; width:1024px; text-align:center;">
  <div class="logo"><a href="javascript:void(0)"></a></div>
</div>
<!-- 页面主体  开始 -->
<div id="main" style="text-align:center; padding-top:60px;"><a href="${ctx }/index.ht"><img src="${ctx }/images/tianhe_logo.png" width="194" height="42" longdesc="返回首页" /style="border:0" /></a></div>
<!-- 页面主体  结束 -->
<div class="clear_10"></div>
<div id="main" style=" padding-right:430px; _padding-right:215px; padding-top:15px;">
  <div class="serch">
      <div class="serch_01">
      	<ul class="tab">
      		<li class="tab_menu" id="company">企业</li>
        	<li class="tab_menu" id="chance">商机</li>        
        	<li class="tab_menu" id="ability">能力</li>
         	<li class="tab_menu" id="resource">资源</li> 
        </ul>
      </div>
      <div id="search1" class="serch_02">
	      <form action="${ctx}/cloud/system/enterprises/search.ht" method="post" name="form1">
	        <span class="serch_03">
		        <select class="sel" name="select" style="font-size:14px; color:#999; padding:4px; line-height:22px; border:#1f73b9 2px solid;">
		        	<option value="caigou">采购商机</option>
		        	<option value="yingxiao">营销商机</option>
		        	<option value="shengchan">生产商机</option>
		        	<option value="fuwu">服务商机</option>
		        	<option value="yanfa">研发商机</option>
		        </select>
		        <input class="header_input" value="搜索企业" name="username">
		    </span>
	        <span class="serch_04"><input type="submit" class="header_btn" id="button" value="" border="0" /></span>
	      </form>
      </div>
    </div>
</div>
<!-- 底部版权区  开始 -->
<%@include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
