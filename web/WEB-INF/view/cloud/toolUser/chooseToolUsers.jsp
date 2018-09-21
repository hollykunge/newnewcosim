<%@page pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>选择用户 </title>
<%@include file="/commons/include/form.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/tree/v35/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" 	src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#defLayout").ligerLayout({
			 leftWidth: 430,
			 rightWidth: 200,
			 bottomHeight :40,
			 height: '100%',
			 allowBottomResize:false,
			 allowLeftCollapse:false,
			 allowRightCollapse:false,
			 minLeftWidth:300,
			 allowLeftResize:false
		});
		handleSelects();
	});
			function dellAll() {
				$("#toolList").empty();
			};
			function del(obj) {
				var tr = $(obj).parents("tr");
				$(tr).remove();
			};
			
			function add(data) {
				var aryTmp=data.split("#");
				var userId=aryTmp[0];
				var len= $("#user_" + userId).length;
				if(len>0) return;
				var aryData=['<tr id="user_'+userId+'">',
					'<td>',
					'<input type="hidden" class="pk" name="userData" value="'+data +'"> ',
					aryTmp[1],
					'</td>',
					'<td><a onclick="javascript:del(this);" class="link del" ></a> </td>',
					'</tr>'];
				$("#toolList").append(aryData.join(""));
			};
		
			function selectMulti(obj) {
				if ($(obj).attr("checked") == "checked"){
					var data = $(obj).val();
					add(data);
				}	
			};
		
			function selectAll(obj) {
				var state = $(obj).attr("checked");
				var rtn=state == undefined?false:true;
				checkAll(rtn);
			};
		
			function checkAll(checked) {
				$("#toolListFrame").contents().find("input[type='checkbox'][class='pk']").each(function() {
					$(this).attr("checked", checked);
					if (checked) {
						var data = $(this).val();
						add(data);
					}
				});
			};
			
			function selectTool(){
				var chIds;
				chIds = $("#toolList").find(":input[name='userData']");
				
				var aryToolIds=new Array();
				var aryToolNames=new Array();
				var aryToolTypes=new Array();
				var aryToolAddresses=new Array();
				
				$.each(chIds,function(i,ch){
					var aryTmp=$(ch).val().split("#");
					aryToolIds.push(aryTmp[0]);
					aryToolNames.push(aryTmp[1]);
					aryToolTypes.push(aryTmp[2]);
					aryToolAddresses.push(aryTmp[3]);
				});
				
				var obj={toolIds:aryToolIds.join(","),toolNames:aryToolNames.join(","),
						toolTypes:aryToolTypes.join(","),toolAddresses:aryToolAddresses.join(",")};
				
				window.returnValue=obj;
				window.close();
			}
			
			var handleSelects=function(){
				var selectUsers=window.dialogArguments ;
				if(selectUsers.selectUserIds && selectUsers.selectUserNames){
					var ids=selectUsers.selectUserIds.split(","); 
					var names=selectUsers.selectUserNames.split(","); 
					for(var i=0;i<ids.length;i++){
						add(ids[i]+"#"+names[i]+"##");
					}
				}
			}		
	</script>

<style type="text/css">
.ztree {
	overflow: auto;
}

.label {
	color: #6F8DC6;
	text-align: right;
	padding-right: 6px;
	padding-left: 0px;
	font-weight: bold;
}
html { overflow-x: hidden; }
</style>
</head>
<body>
	<div id="defLayout">
		<div position="center">
			<div id="centerTitle" class="l-layout-header">全部工具</div>
			<iframe id="toolListFrame" name="toolListFrame" height="90%" width="100%" frameborder="0" src="${ctx}/cloud/toolUser/toolUserList.ht"></iframe>
		</div>
		<div position="right" title="<a onclick='javascript:dellAll();' class='link del'>清空 </a>"
			style="overflow-y: auto;">
			<table width="145" id="toolList" class="table-grid table-list" 	id="0" cellpadding="1" cellspacing="1">
			</table>
		</div>
		<div position="bottom"  class="bottom" >
			<a href="javascript:void(0)" class="button"  onclick="selectTool()" ><span class="icon ok"></span><span >选择</span></a>
			<a href="javascript:void(0)" class="button" style="margin-left:10px;"  onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
		</div>
	</div>
</body>
</html>