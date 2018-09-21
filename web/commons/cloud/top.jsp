<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<style>
.header_input{
	width:475px; height:22px; font-size:14px; color:#999; padding:4px; line-height:22px;
}
.header_input1{
	width:373px; height:22px; font-size:14px; color:#999; padding:4px; line-height:22px;
}
.sel{
   color: #999;font-size:14px;height:32px;line-height:32px;
}
.null{display: none;font-size:14px;}
</style>
<style type="text/css"> 

* { 
padding:0; 
margin:0; 
} 

#navigation, #navigation li ul { 
list-style-type:none; 
} 

#navigation { 
margin:20px; 
}
#navigation li { 
float:left; 
text-align:center; 
position:relative; 
} 

#navigation li{ 
display:block; 
width:100px; 
height:40px; 
line-height:40px; 
} 
#subnavigation li{ 
background:#3C85C4; 
font-size:14;
z-index: 100;
position: relative;
text-align: center;
} 
#navigation li ul { 
display:none; 
position:absolute; 
top:25px; 
left:0; 
margin-top:1px; 
width:120px; 
} 
#navigation li ul li ul { 
display:none; 
position:absolute; 
top:0px; 
left:130px; 
margin-top:0; 
margin-left:1px; 
width:120px; 
} 
#subnavigation{
display: none;
width:120px;
padding:5px; 
position: absolute;
}

</style>

<script>
	//搜索
	function changeSearch(type) {
		if (type == 0) {//企业
			$('#form_search').attr('action',
					'${ctx}/cloud/system/enterprises/search.ht');
		} else if (type == 1) {//商机		
			$('#form_search').attr('action',
					'${ctx}/cloud/config/business/search.ht');
		} else if (type == 2) {//能力
			$('#form_search').attr('action',
					'${ctx}/cloud/config/capability/search.ht');
		} else if (type == 3) {//资源
			$('#form_search').attr('action',
					'${ctx}/cloud/config/cloudSrc/search.ht');
		}

	}
</script>

<script type="text/javascript"> 
function displaySubMenu(li) { 
var subMenu = li.getElementsByTagName("ul")[0]; 
subMenu.style.display = "block"; 
} 
function hideSubMenu(li) { 
var subMenu = li.getElementsByTagName("ul")[0]; 
subMenu.style.display = "none"; 
} 
</script>

<div id="head">
	<div id="top_info">
		<span> <a href="${ctx}/cloud/console/home.ht" class="link01">个人主页</a>
			<a href="${ctx}/contactus.jsp" class="link01">联系客服</a> <a
			href="${ctx}/help/help_about01.jsp" class="link01" target="_blank">帮助中心</a>
		</span> <a href="${ctx}/index.ht" class="nav02" id="firstpage">首页</a>
		<c:if test="${not empty SPRING_SECURITY_LAST_USERNAME}">欢迎您，<a
				id="username" href="javascript:void(0)"><sec:authentication
					property="principal.fullname" /></a>，<a href="${ctx}/logout"
				class="link01">注销</a>
		</c:if>
		<c:if test="${empty SPRING_SECURITY_LAST_USERNAME}">请<a
				href="${ctx}/loginCloud.ht" class="link01">登录</a> │ <a
				href="${ctx}/reg.ht" class="link01">注册</a>
		</c:if>

		<!--会员信息框-->
		<div id="userinfobox">
			<a href="javascript:void(0)"><img src="${ctx}/skins/avatar.jpg" /></a>
			<p>
				<a href="javascript:void(0)">会员名称</a>
			</p>
			<p>
				<a href="javascript:void(0)">会员注销</a>
			</p>
		</div>
	</div>
</div>
<p class="clear"></p>

<!--搜索表单开始 -->
<div id="logo_zone">
	<div id="logo_zone_left">
		<a href="javascript:void(0)">
					<img src="${ctx}/testimg/logo6.jpg" />			
		</a>
	</div>

	<p class="clear"></p>
</div>
<!--搜索表单结束 -->

<!--主导航菜单 开始-->
<div id="mainnav">
	<ul>
		<!--选中状态为class=on<a class="on" href="javascript:void(0)">首页</a></li>-->
		<li><a href="${ctx}/index.ht" class="nav02" id="firstpage">首页</a></li>
		<li><a
			href="${ctx}/cloud/config/businessDevchase/businessChance_list.ht"
			class="nav02">在线商机</a></li>
		<li><a href="${ctx}/cloud/config/capabilityClass/listClasses.ht"
			class="nav02">在线能力</a></li>
		<li><a
			href="${ctx}/cloud/cloudResource/resourceManagement/resource.ht"
			class="nav02">在线资源</a></li>
		<li onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)"> 
			<a href="javascript:void(0)" class="nav02">在线业务</a>
			<ul id="subnavigation" style="padding-left:0px;"> 
				<li><a href="${ctx }/cloud/console/home.ht" style="font-weight: bold;">协同研发</a></li> 
				<li><a href="${ctx }/cloud/console/home.ht" style="font-weight: bold;">协同采购</a></li> 
				<li><a href="${ctx }/cloud/console/home.ht" style="font-weight: bold;">协同生产</a></li> 
				<li><a href="${ctx }/cloud/console/home.ht" style="font-weight: bold;">协同营销</a></li> 
				<li><a href="${ctx }/cloud/console/home.ht" style="font-weight: bold;">协同服务</a></li>
				<li><a href="http://www.shangtengwang.com" target="_blank" style="font-weight: bold;">在线供应链</a></li>  
			</ul> 
		</li> 	
		<p class="clear"></p>
	</ul>
	<iframe src="${ctx}/topMarquee.ht"
		style="float: right; width: 340px; height: 34px;" frameborder="0"
		scrolling="no" allowTransparency="false"></iframe>

	<script src="${ctx}/skins/slides.min.jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$('#ppt').slides({
				play : 5000,
				pause : 2500,
				generatePagination : true,
				hoverPause : false
			});

			//鼠标点击菜单上的时候
			var cindex = 0, changet;
			$('#onlinemenu .ui_tabs_nav a').click(
					function() {

						var index = $('#onlinemenu .ui_tabs_nav a').index(
								$(this)) * 1 + 1;
						cindex = index - 1;
						$('.tabbox').hide();
						$('#tabbox_' + index).show();
						$('#onlinemenu a.more').attr('href',
								$(this).attr('link'));
					})

			//鼠标放到菜单上面的时候
			$('#onlinemenu .ui_tabs_nav a').mouseover(function() {
				$('#onlinemenu .ui_tabs_nav a').removeClass('on');
				$(this).addClass('on');
				window.clearInterval(changet);
			})

			//鼠标移走的时候
			$('#onlinemenu .ui_tabs_nav a').mouseleave(function() {
				changet = window.setInterval(function() {
					autoshow()
				}, 5000)
			})

			function autoshow() {
				var bcount = $('#onlinechange .tabbox').size();
				var current = $('#onlinemenu .ui_tabs_nav').find('.on');
				if (!current) {
					cindex = 0;
				} else {
					cindex = $('#onlinemenu .ui_tabs_nav a').index(current) + 1;
				}
				$('#onlinemenu .ui_tabs_nav a').removeClass('on');

				var $menua = $('#onlinemenu .ui_tabs_nav a:eq(' + cindex + ')');
				$menua.addClass('on');
				//设置更多的连接
				$('#onlinemenu a.more').attr('href', $menua.attr('link'));

				$('.tabbox').hide();
				$('#tabbox_' + cindex).show();
				if (cindex == bcount) {
					$('#onlinemenu .ui_tabs_nav a:first').addClass('on');
				}
			}
			changet = window.setInterval(function() {
				autoshow()
			}, 5000)
		})
	</script>
</div>
<!-- 主导航菜单 结束 -->