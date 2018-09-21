<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人主页</title>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>

<!-- *****************************@自动弹出样式************************** -->
<style type="text/css">
/*
 * user auto tips css
 */
.recipients-tips {
	font-family: "宋体",Tahoma, Arial;
	position: absolute;
	background: #eeeeee;
	z-index: 2147483647;
	padding: 5px;
	border: 1px solid #594c6d;
	display: none;
	overflow: auto;
	max-height: 265px;
}

.recipients-tips li a {
	font-size: 14px;
	display: block;
	padding: 2px;
	font-weight: bold;
	cursor: pointer;
	font-family: "宋体",Tahoma, Arial;
	padding-top:10px;
	height:15px;
	line-height:15px;
	width:200px;
}

.autoSelected {
	background: #2a5caa;
	color: #FFFFFF;
	font-family: "宋体",Tahoma, Arial;
}

.panel-page {
	border: 1px #8dc2e3 solid;
	border-top: none
	height: 28px;
	width: 598px;
	margin-top: -5px;
	background: #dff5fd;
	/*background: url(../images/tool_bg.jpg) repeat-x;*/
}
</style>

<!-- ****************************自动弹出的js********************************* -->
<script src="${ctx }/js/cloud/userAutoTips.js"></script>

<!-- ****************************文本输入提示js********************************* -->
<script src="${ctx}/js/jquery/plugins/jquery.textlimit.js" type=text/javascript></script>

<script type="text/javascript">
	$(function() {
		userAutoTips({
			id : 'message'
		});
		
		$("#message").textlimit('#msgTip',140);  
		$("#message").keypress(function(e){
			if(e.ctrlKey==true && e.keyCode==13){//发送消息
				sendMessage();
			}
		});
	})

	function accept(receviedId, bid, sendEntId) {
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/busiarea/accept.ht",
			data : {
				receviedId : receviedId,
				bid : bid,
				sendEntId : sendEntId,
			},
			dataType : "json",
			success : function(data) {
				if (data && data.acceptOK == "true") {
					$.ligerMessageBox.alert('商友已经添加成功');
				} else if (data && data.isFriend == "true") {
					$.ligerMessageBox.alert('对方已是商友');
				} else if (data && data.myself == "true") {
					$.ligerMessageBox.alert('自己不能通过添加申请');
				} else if (data && data.alreadyRefuse == "true") {
					$.ligerMessageBox.alert('已经拒绝添加，请等待对方重新申请添加');
				}
			}
		});
	}

	function refuse(receviedId, bid, sendEntId) {
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/busiarea/refuse.ht",
			data : {
				receviedId : receviedId,
				bid : bid,
				sendEntId : sendEntId,
			},
			dataType : "json",
			success : function(data) {
				if (data && data.refuseOK == "true") {
					alert('拒绝添加对方为商友');
				} else if (data && data.isFriend == "true") {
					alert('对方已是商友');
				} else if (data && data.myself == "true") {
					alert('自己不能拒绝添加申请');
				} else if (data && data.alreadyRefuse == "true") {
					alert('已经拒绝对方为商友，请勿重复提交拒绝');
				}
			}
		});
	}

	function acceptAndAdd(receviedId, bid, sendEntId) {
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/busiarea/acceptAndAdd.ht",
			data : {
				receviedId : receviedId,
				bid : bid,
				sendEntId : sendEntId,
			},
			dataType : "json",
			success : function(data) {
				if (data && data.acceptAndAddOK == "true") {
					alert('同意对方添加，并添加对方为商友');
				} else if (data && data.isFriend == "true") {
					alert('对方已是商友');
				} else if (data && data.myself == "true") {
					alert('自己不能通过和添加');
				} else if (data && data.alreadyRefuse == "true") {
					alert('已经拒绝对方为商友，请勿重复提交拒绝');
				}
			}
		});
	}
 
	function sendMessage() {
		if($('#message').val().length==0||$('#message').val()==null){
			$.ligerMessageBox.alert("消息不能为空！");
			 return ;
		}
		
		var waitingDialog = $.ligerDialog.waitting('正在保存中,请稍候...');
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/busiarea/sendMessage.ht",
			data : {
				eid : '${myCompany.sysOrgInfoId}',
				message : $('#message').val()
			},
			dataType : "json",
			success : function(data) {
				waitingDialog.close();
				if (data.sendMessageOK == "true") {					
					window.location = "${ctx}/cloud/console/home.ht"
					$('#message').val('');
				} else if (data.NoCorpEnt == "true") {
					$.ligerMessageBox.alert('请检查发送对象是否为商友');
				}
			}
		});
	}

	/* 	$(function() {
	 $('body').loading();
	 freshMyMessage();
	 });
	 */
	function freshMyMessage() {
		$('#content').html('');
		$('body').loading();
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/home.ht",

			dataType : "json",

		});
	}
	 
	 //待办任务弹出框提醒
	 $(function(){
		 Date.prototype.format = function(format){
			 var o = {
			 "M+" : this.getMonth()+1, //month
			 "d+" : this.getDate(), //day
			 "h+" : this.getHours(), //hour
			 "m+" : this.getMinutes(), //minute
			 "s+" : this.getSeconds(), //second
			 "q+" : Math.floor((this.getMonth()+3)/3), //quarter
			 "S" : this.getMilliseconds() //millisecond
			 } 
			 
			 if(/(y+)/.test(format)) {
			 	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
			 }

			 for(var k in o) {
			 if(new RegExp("("+ k +")").test(format)) {
			 	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
			 }
			 }
			 return format; 
		 }
		
		 /**
		 $.ajax({
		 	method : 'post',
		 	url : 'getMyTask.ht',
		 	success : function(data){
		 		if(data && data.length>0){
			 		f_tip(data);
		 		}
		 	}
		 })**/
		 
		 $.ajax({
		 	method : 'post',
		 	url : 'getMyMessage.ht',
		 	success : function(data){
		 		if(data && data.length>0){
			 		f_tip1(data);
		 		}
		 	}
		 })
	 })
	 
	 var tip;
     var num = 0;
     //待办任务
     function f_tip(rows) {
         if (!tip) {
        	 var $d = $('<div class="msg"></div>');
        	 var $u = $d.append('<ul></ul>');
        	 for(var i=0; i<rows.length; i++){
        		var row = rows[i];
        		var t = row.createTime;
        		t = new Date(t);
        		t = t.format('yyyy-MM-dd hh:mm:ss');
        	 	$u.append('<li><a href="${ctx}/platform/bpm/taskInfo/toStart.ht?taskId=' + row.id + '" target="_blank">' + row.subject + ' ' + t + '</a></li>');
        	 }
        	 var h = 70 + rows.length * 15;
             tip = $.ligerDialog.tip({ 
            	 title: '待办任务', 
            	 content: $d.html(), 
            	 height: h, 
            	 width: 400, 
            	 left:0});
         }
         else {
             var visabled = tip.get('visible');
             if (!visabled) tip.show();
             tip.set('content', '内容改变' + num++);
         }
     }
     
   //待办消息
   var tip1;
   var num1 = 0;
   function f_tip1(rows) {
         if (!tip1) {
        	 var $d = $('<div class="msg"></div>');
        	 var $u = $d.append('<ul></ul>');
        	 for(var i=0; i<rows.length; i++){
        		var row = rows[i];
        		var t = row.sendTime;
        		t = new Date(t);
        		t = t.format('yyyy-MM-dd hh:mm:ss');
        	 	$u.append('<li><a href="javascript:ddOpen(\'${ctx}/platform/system/messageRead/list.ht?messageId=' + row.id + '\');">' + row.subject + ' ' + t + '</a></li>');
        	 }
        	 var h = 70 + rows.length * 15;
             tip1 = $.ligerDialog.tip({ title: '待办消息', content: $d.html(), height: h, width: 400, isDrag : true });
         }
         else {
             var visabled = tip1.get('visible');
             if (!visabled) tip1.show();
             tip1.set('content', '内容改变' + num1++);
         }
     }
   
   function ddOpen(url){
	   $.ligerDialog.open({ 
		   	url : url, 
		   	height: 400, 
		   	width: 600, 
		   	title :'消息详细', 
		   	name:"frameDialog_"});
   }
</script>
<script src="${ctx}/js/cloud/FusionCharts.js" type="text/javascript"></script>
</head>
<body>

<div id="all">
	<%@include file="/commons/cloud/top_console.jsp"%>
	
	<!-- 商圈 开始 -->
		<!--个人主页-->
	<div id="myhome">
		<div id="home_left">
			<!--左侧菜单-->
			<%@include file="/commons/cloud/console_left.jsp"%>
			
			<script type="text/javascript">
				/*字数统计*/
				var maxstrlen = 135;
				 
				function checkWord(c) {
					len = maxstrlen;
					var str = c.value;
					myLen = getStrleng(str);
					var wck = document.getElementById("countword");
					if (myLen > len * 2) {
						c.value = str.substring(0, i + 1);
					}
					else {
						wck.innerHTML = Math.floor((len * 2 - myLen) / 2);
					}
				}
				function getStrleng(str) {
					myLen = 0;
					i = 0;
					for (; (i < str.length) && (myLen <= maxstrlen * 2); i++) {
						if (str.charCodeAt(i) > 0 && str.charCodeAt(i) < 128)
							myLen++;
						else
							myLen += 2;
					}
					return myLen;
				}
				
			</script>
			<div id="my_center">
				<div id="chat">
					<table>
						<tr>
							<td style="padding-left:10px;width:485px">
							<textarea onkeyup="checkWord(this)"  onkeydown="getStrleng(this)" name="message" id="message" class="sq_textarea"></textarea></td>
							<td align="left"><a href="javascript:void(0)"><img src="${ctx }/images/btn_pub01.jpg" width="87"
							height="89" onmouseover="src='${ctx }/images/btn_pub02.jpg';"
							onmouseout="src='${ctx }/images/btn_pub01.jpg'"
							style="border: 0;" onclick="sendMessage();"></a></td>
						</tr>
					</table>
                    <p class="vertical">按Ctrl+Enter组合键可发送消息，剩余<font id="countword">135</font>字符</p>
				</div>
				
				<!--留言列表-->
				<div id="msg_list">
					<ul>
					<c:forEach items="${cloudMessages}" var="cloudMessages">
						<li>
							<div class="avatar"><a href="javascript:void(0)"><img src="${ctx}${cloudMessages.sendEnt.logo}" onError="this.src='${ctx}/images/default-chance.jpg'" width="50" height="50" style="border: 1px solid #6D6D6F;" /></a></div>
							<div class="msg_right">
								<p><a href="${ctx}/cloud/console/enterprise.ht?EntId=${cloudMessages.sendentId }"></a></p>
                                <P class="msg_tt">${cloudMessages.title}</P>
                                <P class="msg_tt_one">${cloudMessages.content}</P>
							</div>
							<p class="clear"></p>
						</li>
					</c:forEach>
					</ul>
				</div>
				<div style="height:26px; background:#DFF5FD; border:1px solid #9CCEFD; border-top:0">
				<hotent:paging tableId="cloudMessageList" showPageSize="false"/>
				</div>
			</div>
		</div>
		<%@include file="/commons/cloud/console_right.jsp"%>
	
	
<!--all结束-->
</div>
</div>

<div style="width:100%;height:40px;clear:both; padding-top:20px;" />	

	<!--设置左侧菜单背景图片为透明--->
	<!--[if IE 6]>
	<script src="skins/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script>
	<script type="text/javascript">
	  DD_belatedPNG.fix('#leftmenu a','background');
	</script>
	<![endif]-->
	<!-- 商圈结束 -->

	<!-- 底部版权区  开始 -->
	<%@ include file="/commons/cloud/foot.jsp"%>
	
	<!-- 底部版权区  结束 -->
</body>
</html>
