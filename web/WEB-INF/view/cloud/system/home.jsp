<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>平台管理主页</title>
<%@ include file="/commons/cloud/meta.jsp"%>
<!-- *****************************@自动弹出样式************************** -->
<style type="text/css">
/*
 * user auto tips css
 */
.recipients-tips {
	font-family: Tahoma, Arial;
	position: absolute;
	background: #f6f7f9;
	z-index: 2147483647;
	padding: 2px;
	border: 2px solid #e0e0f0;
	display: none;
}

.recipients-tips li a {
	display: block;
	padding: 2px 5px;
	color: #333;
	cursor: pointer;
	font-family: Tahoma, Arial;
}

.recipients-tips li a em {
	font-weight: 700;
	color: #000;
	font-family: Tahoma, Arial;
}

.autoSelected {
	background: #dfdfdf;
	font-family: Tahoma, Arial;
}
</style>

<!-- ****************************自动弹出的js********************************* -->
<script src="${ctx }/js/cloud/userAutoTips.js"></script>

<script type="text/javascript">
	$(function() {
		userAutoTips({
			id : 'message'
		});
	})

	function accept(receviedId, bid, sendId) {
		$.ajax({
			type : 'POST',
			url : "${ctx}/busarea/area/accept.ht",
			data : {
				receviedId : receviedId,
				bid : bid,
				sendId : sendId,
			},
			dataType : "json",
			success : function(data) {
				if (data.result == 1) {
					alert('已经添加成功');
				} else if (data.result == 2) {
					alert('商友添加成功，请勿重复提交');
				} else if (data.result == 5) {
					alert('已经拒绝对方为商友，不能加为商友，请等待对方重新申请加入');
				} else if (data.result == 6) {
					alert('自己不能同意自己发出的添加商友申请，等待对方处理');
				}
			}
		});
	}

	function refuse(receviedId, bid, sendId) {
		$.ajax({
			type : 'POST',
			url : "${ctx}/busarea/area/refuse.ht",
			data : {
				receviedId : receviedId,
				bid : bid,
				sendId : sendId,
			},
			dataType : "json",
			success : function(data) {
				if (data.result == 0) {
					alert('已经拒绝对方成为商友');
				} else if (data.result == 3) {
					alert('商友已经拒绝成功，请勿重复提交');
				} else if (data.result == 4) {
					alert('对方已经是商友，不能拒绝，可选择删除商友');
				} else if (data.result == 7) {
					alert('自己不能拒绝自己发出的添加商友申请，等待对方处理');
				}
			}
		});
	}

	function sendMessage() {
		$.ajax({
			type : 'POST',
			url : "${ctx}/console/sendMessage.ht",
			data : {
				eid : '${enterprise.id}',
				message : $('#message').val()
			},
			dataType : "json",
			success : function(data) {
				if (data.result == 1) {
					freshMyMessage();
					$('#message').val('');
				} else if (data.result == 0) {
					alert('请确定发送消息的对象是您的商友');
				} else if (data.result == 2) {
					alert('发送消息不能为空');
				}
			}
		});
	}

	$(function() {
		//$('body').loading();
		//freshMyMessage();
	});

	function freshMyMessage() {
		$('#content').html('');
		$('body').loading();
		$
				.ajax({
					type : 'POST',
					url : "${ctx}/demo/message/readMyMessage.ht",
					data : {
						pi : '${param.pi}'
					},
					dataType : "json",
					success : function(rows) {
						$(rows)
								.each(
										function(index, row) {
											var str = '<div class="content_01"><div class="content_02">';
											str += '<a href="javascript:void(0)"></a>'
													+ row.title + '</div>';
											str += '<div>'
													+ row.content
													+ '</div><div class="content_03">'
													+ row.sendTime
													+ '</div></div>';

											$('#content').append(str);
										});
						$('body').loaded();
					}
				});
	}
</script>
<script src="${ctx}/js/cloud/FusionCharts.js" type="text/javascript"></script>
</head>
<body>
	<%@include file="/commons/cloud/top_console.jsp"%>
	<!-- 页面主体  开始 -->
	<div id="main">
		<div id="main_left">
			<div id="sq_left">
				<div class="sq_left_img01">
					<div class="sq_btn01">
						<a href="${ctx}/cloud/console/outline.ht" class="linkblue">待办任务</a>
					</div>
					<div class="sq_btn02">
						<a href="javascript:void(0)" class="linkblue"></a>
					</div>
				</div>
				<div class="clear_10"></div>
				<div class="sq_left_img02">
					<div class="sq_btn01">
						<a href="${ctx}/console/outline.ht" class="linkblue">已办未结</a>
					</div>
					<div class="sq_btn02">
						<a href="javascript:void(0)" class="linkblue"></a>
					</div>
				</div>
				<div class="clear_10"></div>
				<div class="sq_left_img03">
					<div class="sq_btn01">
						<a href="${ctx}/console/outline.ht" class="linkblue">已办完结</a>
					</div>
					<div class="sq_btn02">
						<a href="javascript:void(0)" class="linkblue"></a>
					</div>
				</div>
				<div class="clear_10"></div>
				<div class="sq_left_img03">
					<div class="sq_btn01">
						<a href="${ctx}/busarea/chance/outline.ht" class="linkblue">商机发布</a>
					</div>
					<div class="sq_btn02">
						<a href="javascript:void(0)" class="linkblue"></a>
					</div>
				</div>
			</div>
			<div id="sq_middle">
				<div class="pub">
					<div class="pub01">
						<textarea id="message" cols="" rows="" class="pub_input"></textarea>
					</div>
					<div class="pub02">
						<a href="javascript:void(0)"><img src="../images/btn_pub02.jpg" width="87"
							height="87" onmouseover="src='../images/btn_pub02.jpg';"
							onmouseout="src='../images/btn_pub01.jpg'" style="border:0;"
							onclick="sendMessage();">
						</a>
					</div>
				</div>
				<div class="pub_fresh">
					<a href="javascript:void(0)"><img src="../images/icon2.jpg" width="40"
						height="13" style="border:0;">
					</a>
				</div>
				<div class="clear_10"></div>
				<div id="content">
					<div class="content_01">
						<div class="content_02">
							
						</div>
					</div>
					
				</div>
				<div class="clear_10"></div>
				<ap:numberPageTag pageNo="${page.pageNo}"
								url="${ctx}/console/home.ht" total="${page.total}" />
			</div>
		</div>
		<div id="main_right">
			<div class="company">
				<div class="company01">
					<div class="company02">
						<img src="${enterprise.logo }" width="80" height="80" />
					</div>
					<div class="company03">
						<a
							href="${ctx}/console/enterprise.ht?EntId=${enterprise.id } "
							class="link04">${enterprise.name }</a>
					</div>
					<div class="company04">
						<a href="javascript:void(0)" class="link02">联系人：${enterprise.connecter }</a>
					</div>
					<div class="company04">
						<a href="javascript:void(0)" class="link02">电话：${enterprise.telphone }</a>
					</div>
				</div>
			</div> 
			<div class="clear_10"></div>
			<h2>
				新闻公告<a href="${ctx}/news/news_list.ht" class="more_right">更多</a>
			</h2>
			<ul id="news">${newsList }
				<c:forEach items="${newsList}" var="newsItem" varStatus="s">
					<li><span class="date">${fn:substring(newsItem.publishtime,0,10)}</span>
						<a href="${ctx}/news/newsContent.ht?id=${newsItem.id}"
						class="link02">${newsItem.title}</a></li>
				</c:forEach>
			</ul>
			<div class="clear_10"></div>
			<h2>
				在线能力<a href="${ctx}/capability/capabilityClass/listClass.ht"   class="more_right">更多</a>
			</h2>
			<ul class="msg01">
				<li class="msg02"><span class="msg04">生产能力：23</span>
				</li>
				<li class="msg02"><span class="msg04">供应能力：36</span>
				</li>
				<li class="msg02"><span class="msg04">营销能力：36</span>
				</li>
				<li class="msg02"><span class="msg04">服务能力：64</span>
				</li>
				<li class="msg02"><span class="msg04">研发能力：94</span>
				</li>

				  <div align="left" style="text-align: left;" id="chartdiv">
            
            
<script type="text/javascript">
 var myChart = new FusionCharts("../Pie3D.swf", "myChartId", "260", "182");
 myChart.setDataXML("<graph caption='在线能力统计' xAxisName='Month' yAxisName='Units' showNames='0' showValues='0'   bgcolor='#f6f7f9' decimalPrecision='0' formatNumberScale='0'> <set name='生产能力' value='23'  /><set name='供应能力' value='36'  /> <set name='营销能力' value='36'  /><set name='服务能力' value='64'  /><set name='研发能力' value='94'  />  </graph>");
               myChart.render("chartdiv");
 </script>
            </div>
			</ul>
			<div class="clear_10"></div>
			<h2>
				在线资源<a href="${ctx }/resource.jsp"  class="more_right">更多</a>
			</h2>
			<ul class="msg01">
				<li class="msg02"><span class="msg03">计算资源：</span>206万亿次</li>
				<li class="msg02"><span class="msg03">软件资源：</span>120种</li>
				<li class="msg02"><span class="msg03">存储资源：</span>300T</li>
			</ul>
		</div>
	</div>
	<!-- 页面主体  结束 -->
	<!-- 底部版权区  开始 -->
	<%@ include file="/commons/cloud/foot.jsp"%>
	<!-- 底部版权区  结束 -->
</body>
</html>
