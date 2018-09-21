<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>首页</title>
<%@include file="/commons/cloud/meta.jsp"%>

<!-- cloudTag -->
<link href="${ctx}/styles/cloudTag/style.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/cloudTag/cloudTag.js" type="text/javascript"></script>

<script type="text/javascript">
	function view(id, type) {
		location.href = "${ctx}/cloud/config/businessDevchase/indexview.ht?id="+ id + "&type=" + type;
		return false;
	}
	
	function toIndustry(name){
		$('#industry').val(name);
		$('#frm_ind').submit();
	}
</script>
</head>
<body>
<div id="all">
	<%@include file="/commons/cloud/top.jsp"%>
	
	<!-- 主页面 开始 -->
	<div id="indexcontent">
		<!--主页面-左侧内容 开始 -->
		<div class="index_left">
			<!--幻灯片-->
			<div id="ppt">
				<div class="slides_container">
					<a href="javascript:void(0)"><img src="testimg/banner_01.jpg"/></a>
					<a href="javascript:void(0)"><img src="testimg/banner_02.jpg"/></a>
					<a href="javascript:void(0)"><img src="testimg/banner_03.jpg"/></a>
					<a href="javascript:void(0)"><img src="testimg/banner_04.jpg"/></a>
				</div>
			</div>
			
			<!--在线商机 开始-->
			<div id="onlinechange" class="blue_box">
				<div id="onlinemenu">
					<h2 class="bluetitel">在线商机<a href="javascript:void(0)" class="more">&raquo;更多</a></h2>
					<ul class="ui_tabs_nav">
						<!--每个菜单对应的more连接-->
						<li><a class="on" href="javascript:void(0)" link="${ctx}/cloud/config/business/moreBusinessChance.ht?type=5"><!--把更多连接刚在link=""中--><span>研发商机</span></a></li>
						<li><a href="javascript:"  link="${ctx}/cloud/config/business/moreBusinessChance.ht?type=1"><span>采购商机</span></a></li>
						<li><a href="javascript:"  link="${ctx}/cloud/config/business/moreBusinessChance.ht?type=3"><span>生产商机</span></a></li>
						<li><a href="javascript:"  link="${ctx}/cloud/config/business/moreBusinessChance.ht?type=2"><span>营销商机</span></a></li>                
						<li><a href="javascript:"  link="${ctx}/cloud/config/business/moreBusinessChance.ht?type=4"><span>服务商机</span></a></li> 
					</ul>
					<div class="clear"></div>
				</div>
				
				<!--切换容器1 研发商机-->
				<div id="tabbox_1" class="tabbox" style="display:block">
					<ul>
						<c:forEach items="${developmentlist}" var="developmentlist" varStatus="s">
						<li>
							<div class="proimg">
								<a href="javascript:void(0)" target="_blank">
									<img src="${ctx}/${developmentlist.image}" onError="this.src='${ctx}/images/product_img05.jpg';" 
									width="80" height="80" />
								</a>
							</div>
							<div class="prodesc">
								<h2>
									<a href="javascript:void(0)"	onclick="view('${developmentlist.id}','${developmentlist.type}');" class="ctitle" title="${developmentlist.classid}-${developmentlist.name}">
										<ap:textTip value="${developmentlist.classid}-${developmentlist.name}" max="10" more=".."/>										
									</a>	
									<a href="${ctx}/cloud/console/enterprise.ht?EntId=${developmentlist.companyId}" class="cname" title="${developmentlist.companyName}">
										<ap:textTip value="${developmentlist.companyName}" max="8" more=".."/>
									</a>
								</h2>
								<p>开始时间：<fmt:formatDate value="${developmentlist.startTime}" pattern="yyyy-MM-dd" /> 
								 	截止时间：<fmt:formatDate value="${developmentlist.endTime}" pattern="yyyy-MM-dd" />
								</p>
								<p>研发要求：<ap:textTip value="${developmentlist.yfhbzzyq}" max="16" more=".."/> </p>
								<div class="country">
									<img src="${ctx}/${developmentlist.flaglogo}" width="14" height="12" />
									${developmentlist.country}：${developmentlist.province}${developmentlist.city}
								</div>
							</div><p class="clear"></p>
						</li>
						</c:forEach>
						<p class="clear"></p>
					</ul>
					<p class="clear"></p>
				</div>
				
				<!--切换容器2 采购商机-->
				<div id="tabbox_2" class="tabbox">
					<ul>
						<c:forEach items="${purchaselist}" var="purchaseList" varStatus="s">
						<li>
							<div class="proimg">
								<a href="javascript:void(0)" target="_blank">
									<img src="${ctx}/${purchaseList.image}"
										onError="this.src='${ctx}/images/product_img01.jpg';"
										width="80" height="80" />
								</a>
							</div>
							<div class="prodesc">
								<h2>
									<a href="javascript:void(0)" onclick="view('${purchaseList.id}','${purchaseList.type}');" class="cname"
										name="${purchaseList.id}" title="${purchaseList.classid}-${purchaseList.name}">
										<ap:textTip value="${purchaseList.classid}-${purchaseList.name}" max="10" more=".."/>
									</a>
									<a href="${ctx}/cloud/console/enterprise.ht?EntId=${purchaseList.companyId}" class="ctitle" title="${purchaseList.companyName}">											
										<ap:textTip value="${purchaseList.companyName}" max="8" more=".."/>
									</a>
								</h2>
								<p>开始时间: <fmt:formatDate value="${purchaseList.startTime}" pattern="yyyy-MM-dd" />
									截止时间: <fmt:formatDate value="${purchaseList.endTime}" pattern="yyyy-MM-dd" /> 
								</p>
								<p>采购数量：${purchaseList.purnum}个</p>
								<div class="country">
									<img src="${ctx}/${purchaseList.flaglogo}" width="14" height="12" />
									${purchaseList.country}：${purchaseList.province}${purchaseList.city}
								</div>
							</div><p class="clear"></p>
						</li>
						</c:forEach>
						<p class="clear"></p>
					</ul>
					<p class="clear"></p>
				</div>
				
				<!--切换容器3 生产商机-->
				<div id="tabbox_3" class="tabbox">
					<ul>
						<c:forEach items="${producelist}" var="producelist" varStatus="s">
						<li>
							<div class="proimg">
								<img src="${ctx}/${producelist.image}"
										onError="this.src='${ctx }/images/product_img03.jpg';"
										width="80" height="80" />
							</div>
							<div class="prodesc">
								<h2>
									<a href="javascript:void(0)" class="cname"
										onclick="view('${producelist.id}','${producelist.type}');"
										name="${producelist.id}" title="${producelist.classid}-${producelist.name}">
										<ap:textTip value="${producelist.classid}-${producelist.name}" max="10" more=".."/>										
									</a>
									<a href="${ctx}/cloud/console/enterprise.ht?EntId=${producelist.companyId}" class="ctitle">											
										<ap:textTip value="${producelist.companyName}" max="8" more=".."/>
									</a>
								</h2>
								<p>开始时间: <fmt:formatDate value="${producelist.startTime}" pattern="yyyy-MM-dd" />
									截止时间: <fmt:formatDate value="${producelist.endTime}" pattern="yyyy-MM-dd" /></p>
								<p>生产要求：<ap:textTip value="${producelist.scgyyq}" max="16" more=".."/> </p>
								<div class="country">
									<img src="${ctx}/${producelist.flaglogo}" width="14" height="12" />
										${producelist.country}：${producelist.province}${producelist.city}
								</div>
							</div><p class="clear"></p>
						</li>
						</c:forEach>
						<p class="clear"></p>
					</ul>
					<p class="clear"></p>
				</div>
				
				<!--切换容器4 营销商机-->
				<div id="tabbox_4" class="tabbox">
					<ul>
						<c:forEach items="${marketinglist}" var="marketinglist" varStatus="s">
						<li>
							<div class="proimg">
								<img src="${ctx}/${marketinglist.image}"
										onError="this.src='${ctx }/images/product_img02.jpg';"
										width="80" height="80" />
							</div>
							<div class="prodesc">
								<h2>
									<a href="javascript:void(0)" onclick="view('${marketinglist.id}','${marketinglist.type}');" name="${marketinglist.id}" class="cname" title="${marketinglist.classid}-${marketinglist.name}">
										<ap:textTip value="${marketinglist.classid}-${marketinglist.name}" max="10" more=".."/>
									</a>
									<a href="${ctx}/cloud/console/enterprise.ht?EntId=${marketinglist.companyId}" class="ctitle">
										<ap:textTip value="${marketinglist.companyName}" max="8" more=".."/>
									</a>
								</h2>
								<p>开始时间: <fmt:formatDate value="${marketinglist.startTime}" pattern="yyyy-MM-dd" />
									截止时间: <fmt:formatDate value="${marketinglist.endTime}" pattern="yyyy-MM-dd" /> 
								</p>
								<p>代理区域：${marketinglist.dlqy} </p>
								<div class="country">
									<img src="${ctx}/${marketinglist.flaglogo}" width="14" height="12" />
										${marketinglist.country}：${marketinglist.province}${marketinglist.city}
								</div>
							</div><p class="clear"></p>
						</li>
						</c:forEach>
						<p class="clear"></p>
					</ul>
					<p class="clear"></p>
				</div>
				
				<!--切换容器5 服务商机-->
				<div id="tabbox_5" class="tabbox">
					<ul>
						<c:forEach items="${servelist}" var="servelist" varStatus="s">
						<li>
							<div class="proimg">
							<img src="${ctx}/${servelist.image}" onError="this.src='${ctx }/images/product_img04.jpg';"
								width="80" height="80" />
							</div>
							<div class="prodesc">
								<h2>
									<a href="javascript:void(0)" onclick="view('${servelist.id}','${servelist.type}');"
										name="${servelist.id}" class="cname">
										<ap:textTip value="${servelist.classid}-${servelist.name}" max="10" more=".."/>
									</a>
									<a href="${ctx}/cloud/console/enterprise.ht?EntId=${servelist.companyId}"
										class="ctitle">
										<ap:textTip value="${servelist.companyName}" max="8" more=".."/>
									</a>
								</h2>
								<p>
									开始时间: <fmt:formatDate value="${servelist.startTime}" pattern="yyyy-MM-dd" /> 
									截止时间: <fmt:formatDate value="${servelist.endTime}" pattern="yyyy-MM-dd" /> 
								</p>
								<p>服务区域：${servelist.fwqy}</p>
								<div class="country">
									<img src="${ctx}/${servelist.flaglogo}" width="14" height="12" />
										${servelist.country}：${servelist.province}${servelist.city}
								</div>
							</div><p class="clear"></p>
						</li>
						</c:forEach>
						<p class="clear"></p>
					</ul>
					<p class="clear"></p>
				</div>
			</div>
			<!--在线商机 结束-->
			
			
		</div>
		<!--主页面-左侧内容 结束 -->
		
		<!--主页面-右侧内容 开始 -->
		<div class="index_right">
			<div class="blue_box" id="notice_list">
				<h2 class="bluetitel"><a href="${ctx}/cloud/system/news/more.ht" class="more">更多</a>新闻公告</h2>
				<div id="scrollbox" style=" margin-top:15px; height:141px; overflow:hidden;position:relative;">
				<ul id="scrollbox1">
					<c:forEach items="${newsList}" var="newsItem" varStatus="s">				
					<li>
						<span><fmt:formatDate value="${newsItem.publishtime}" pattern="yyyy-MM-dd" /></span> 
						<a href="${ctx}/cloud/system/news/get.ht?id=${newsItem.id}" class="link02" title="${newsItem.title}">${newsItem.title}</a>
					</li>
					</c:forEach>
				</ul>
				<ul id="scrollbox2"></ul>
				</div>
			</div>
			
			<!--在线能力-->
			<div class="blue_box" id="able_list">
				<h2 class="bluetitel"><a href="${ctx}/cloud/config/capabilityClass/listClasses.ht" class="more_right">更多</a>在线能力</h2>
				<ul>
					<li>研发能力：<font>${ycapabilityCount}</font></li>
					<li>生产能力：<font>${scapabilityCount}</font></li>
					<li>贸易能力：<font>${mcapabilityCount}</font> </li>
					<li>服务能力：<font>${fcapabilityCount}</font></li>
					<p class="clear"></p>
				</ul>
			</div>
			
			<!--行业动态-->
			<div class="blue_box" id="hangye">
				<h2 class="bluetitel"><a href="javascript:void(0)" class="more">更多</a>行业动态</h2>
				<div id="divCloudTag" style="width: 252px; height: 189px; font-size: 12px; border: #9ccefd 1px solid; background-color: #f6f7f9; margin: 0px; padding: 0; position: relative; overflow: hidden; padding-top: 10px; padding-bottom: 10px; list-style-type: none;">
					<a target="_blank" class="red">电气机械及器材制造业</a>
					<a target="_blank" class="green">通信设备、计算机及其他电子设备制造业</a>
					<a target="_blank" class="blue">仪器仪表及文化、办公用机械制造业</a>
					<a target="_blank" class="purple">金属制造业</a>
					<a target="_blank" class="red">通用设备制造业</a>
					<a target="_blank" class="green">工艺品及其他制造业</a>
					<a target="_blank" class="blue">服务业</a>
					<a target="_blank" class="purple">专用设备</a>
					<a target="_blank" class="red">纺织业</a>
					<a target="_blank" class="green">家具制造业</a>
					<a target="_blank" class="blue">塑料制品业</a>
					<a target="_blank" class="purple">非金属矿物制品</a>
				</div>
			</div>
			
		</div>
		<div class="clear"></div>
		<!--主页面-右侧内容 结束 -->
		
		<!--在线资源-->
		<div id="online_source" class="blue_box">
			<h2 class="bluetitel"><a href="javascript:void(0)" class="more">更多</a>在线资源</h2>
			<div id="source_left">
				<p>计算资源：206万亿次</p>
				<p>软件资源：12类</p>
				<p>存储资源：500TB</p>
			</div>
			<div id="source_right">
				<div id="ulbox1" class="introduce_list">
					<div><a href='${ctx}/cloud/cloudResource/resourceManagement/showResource.ht?type=computing&hpc=http://182.50.3.217:8080&resourceID=10000024250028' >
						<img src="${ctx}/images/rollPic/img_02.jpg" width=120 height=80	border=0>
						<br>天智平台计算中心</a>
					</div>
					<div>
						<a href='${ctx}/cloud/cloudResource/resourceManagement/showResource.ht?type=computing&hpc=http://202.112.132.185:8080&resourceID=10000024250029'>
						<img src="${ctx}/images/rollPic/img_01.jpg" width=120 height=80	border=0>
						<br/>北航计算中心</a>
					</div>
					<div>
						<a href="javascript:void(0)" onclick="window.open('http://www.citycloudstore.com/SCS/html/index.html')">
						<img src="${ctx}/images/rollPic/img_25.JPG" width=120 height=80	border=0>
						<br>曙光云商城</a>
					</div>
					<div><a href='${ctx}/cloud/cloudResource/resourceManagement/showResource.ht?type=software&resourceID=111' >
						<img src="${ctx}/images/rollPic/img_03.jpg" width=120 height=80 border=0>
						<br>Fluent</a>
					</div>
					<div><a href='${ctx}/cloud/cloudResource/resourceManagement/showResource.ht?type=software_3d&resourceID=111'>
						<img src="${ctx}/images/rollPic/img_04.jpg" width=120 height=80 border=0>
						<br>UG NX</a>
					</div>
					<div><a href='${ctx}/cloud/cloudResource/resourceManagement/showResource.ht?type=software_3d&resourceID=111'>
						<img src="${ctx}/images/rollPic/img_10.jpg" width=120 height=80 border=0>
						<br>CATIA</a>
					</div>
					<div><a href='${ctx}/cloud/cloudResource/resourceManagement/showResource.ht?type=software&resourceID=10000024250003'>
						<img src="${ctx}/images/rollPic/img_05.jpg" width=120 height=80	border=0>
						<br>CPMD</a>
					</div>
					<div>
						<a href="javascript:void(0)" onclick="window.open('http://182.50.3.222/Citrix/XenApp/loading.htm')">
						<img src="${ctx}/images/rollPic/img_06.jpg" width=120 height=80	border=0>
						<br>Citrix</a>
					</div>
				<%-- 	<div>
						<a href="javascript:void(0)" onclick="window.open('http://www.fseport.gov.cn')">
						<img src="${ctx}/images/rollPic/img_07.jpg" width=120 height=80	border=0>
						<br>佛山电子口岸</a>
					</div> --%>
					<div>
						<a href='${ctx}/cloud/cloudResource/resourceManagement/showResource.ht?type=software&resourceID=10000024250003'>
						<img src="${ctx}/images/rollPic/img_08.jpg" width=120 height=80	border=0>
						<br>NWCHEM</a>
					</div>					
					<p class="clear"></p>
				</div>
			</div>
			<p class="clear"></p>
			<script type="text/javascript">
			$(function(){
				$('#ulbox1').boxscroll({
					count:6,//一次显示的索引容器的个数
					width:122,//每个索引容器的宽度
					box:$('#ulbox1'),//移动的容器，是动态可变的宽度
					windowidth:840,//窗口容器，固定不变的宽度
					field:$('#ulbox1 div'),//索引容器集合
					autoplay:true//自动滚动
					}
				)
			});
		</script>
			</div>
		</div>
		
		<!--最新加入-->
		<div class="index_left">
			<div id="last" class="blue_box">
				<form id="frm_ind" action="${ctx}/cloud/system/enterprises/search_ind.ht" method="post">
					<input type="hidden" name="industry" id="industry"></input>
					<h2 class="bluetitel"><a href="javascript:void(0)" class="more">更多</a>最新加入</h2>
					<div>
						<ul>
							<c:forEach items="${infoList}" var="c1" begin="0" end="4">
		       	  				<li><a href="javascript:toIndustry('${c1.industry}')" title="${c1.industry}">[${c1.industry}]</a> <a href="${ctx}/cloud/console/enterprise.ht?EntId=${c1.sysOrgInfoId}" class="link02" target="_blank" title="${c1.name}">${c1.name}</a></li>
		       	  			</c:forEach>    
		       	  			<c:forEach items="${infoList}" var="c2" begin="5" end="9">
		       	  				<li><a href="javascript:toIndustry('${c1.industry}')" title="${c2.industry}">[${c2.industry}]</a> <a href="${ctx}/cloud/console/enterprise.ht?EntId=${c2.sysOrgInfoId}" class="link02" target="_blank" title="${c2.name}">${c2.name}</a></li>
		       	  			</c:forEach> 
							<p class="clear"></p>
						</ul>
					</div>
				</form>
			</div>
		</div>
		
		<!--合作单位-->
		<div class="index_right">
			<div id="partner" class="blue_box">
				<h2 class="bluetitel">合作单位</h2>
				<ul>
        	  		<li><a href="http://www.oemchina.net/cn/default.aspx">深圳市加工贸易企业协会</a></li>
		            <li><a href="http://www.china-cloud.com">云基地中云网</a></li>
		            <li><a href="http://www.chinaskycloud.com">天云融创 </a></li>
        		    <li><a href="${ctx}/car_mainpage.ht">中国汽车工业协会</a></li>		            
        		    <li><a href="http://56xh.org/" class="link02">广东省物流与供应链协会 </a></li>
		        </ul>
			</div>
		</div>
		<div class="clear"></div>
		<br />
		<!--主体内容结束-->
	</div>
<!--all结束-->
</div>

<%@include file="/commons/cloud/foot.jsp"%>
</body>
</html>
