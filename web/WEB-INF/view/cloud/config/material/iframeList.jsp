<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ include file="/commons/include/html_doctype.html"%>
<html>
	<head>
		<%@include file="/commons/include/get.jsp"%>
		<link href="${ctx}/styles/cloud/biaoge.css" rel="stylesheet"
			type="text/css" />
		<style>
			.fixTd{
				width:100px;
				height:15px;
				overflow:hidden;
				text-overflow:ellipsis;
				white-space:nowrap; 
			}
		</style>
		<title>物品选择</title>


		<script language="javascript">
// --列头全选框被单击---
function ChkAllClick(sonName, cbAllId){
    var arrSon = document.getElementsByName(sonName);
 var cbAll = document.getElementById(cbAllId);
 var tempState=cbAll.checked;
 for(i=0;i<arrSon.length;i++) {
  if(arrSon[i].checked!=tempState)
           arrSon[i].click();
 }
}

// --子项复选框被单击---
function ChkSonClick(sonName, cbAllId) {
 var arrSon = document.getElementsByName(sonName);
 var cbAll = document.getElementById(cbAllId);
 for(var i=0; i<arrSon.length; i++) {
     if(!arrSon[i].checked) {
     cbAll.checked = false;
     return;
     }
 }
 cbAll.checked = true;
}

// --反选被单击---
function ChkOppClick(sonName){
 var arrSon = document.getElementsByName(sonName);
 for(i=0;i<arrSon.length;i++) {
  arrSon[i].click();
 }
}

//回车事件
function keyPress(btn,event){
	if(event.keyCode==13){
		$('#ff').submit();
	}	
}
</script>
</head>
<body style="overflow-x:hidden;">
		<div class="panel">
			<div class="panel-top">
				<div class="tbar-title">
					<span class="tbar-label">待选物品列表</span>
				</div>
			</div>
			<div class="panel-body">

				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr style="background: #fff">
						<td colspan="3" align="left" style="height: 30px;">
							<form id="ff" action="iframeList.ht" method="post">
								物品名称:
								<input tabindex="1" type="text" name="name"
									onKeyPress="keyPress(this,event);" style="width: 120px;">
								<input tabindex="3" type="submit" value="查询"
									style="width: 50px;" />
								<br />
								物品编码:
								<input tabindex="2" type="text" name="code"
									onKeyPress="keyPress(this,event);" style="width: 120px;">
							</form>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="table_2" id="hacker">
					<tbody id="tbody1">
						<tr id="tr1_1">
							<td id="tr_1" width="5">
								<INPUT name="chkAll" id="chkAll" title="全选/反选"
									onClick="ChkAllClick('check','chkAll')" type="checkbox" />
							</td>
							<td id="tr_1">
								物品名称
							</td>
							<td id="tr_1" width="30%">
								物品编码
							</td>
							<td id="tr_1" style="display: none;">
								ID
							</td>
							<td id="tr_1" style="display: none;">
								单位
							</td>
							<td id="tr_1" style="display: none;">
								单价
							</td>
							<td id="tr_1" style="display: none;">
								物品规格
							</td>
						</tr>


						<c:forEach items="${materialList }" var="c1">
							<tr id="tr1_${c1.id }" oldvalue='1'>
								<td>
									<input type="checkbox" name="check" value="${c1.id}"
										onclick="ChkSonClick('check','chkAll')">
								</td>
								<td>
									${c1.name}
								</td>
								<td>
									${c1.code }
								</td>

								<td style="display: none;">
									${c1.id }
								</td>
								<td style="display: none;">
									${c1.unit }
								</td>
								<td style="display: none;">
									${c1.price }
								</td>
								<td id="tr_1" style="display: none;">
									${c1.model }
								</td>
							</tr>
						</c:forEach>


					</tbody>

				</table>
				<hotent:paging tableId="materialItem" showExplain="false" showPageSize="false"/>
				<input type="hidden" id="optionId" value="" />
				<input type="hidden" id="leftRight" value="" />
			</div>
			<!-- end of panel-body -->
		</div>
		<!-- end of panel -->

	</body>

</html>

