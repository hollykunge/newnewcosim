<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/cloud/global.jsp"%>
<html>
<head>

<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="${ctx}/js/jquery/jquery1.3.2.js"></script>
<link href="${ctx}/styles/cloud/biaoge.css" rel="stylesheet"
	type="text/css" />
<title>物品选择</title>

<script language="javascript" type="text/javascript">
	function toRightMore(tId) {
		if (tId == "1") {
			var arr_check = document.getElementById('iframeList').contentWindow.document
					.getElementById('tbody1').getElementsByTagName("tr");
			var arr_check2 = document.getElementById("tbody2").getElementsByTagName("tr");
			var check_length = arr_check.length;
			var check_length2 = arr_check2.length;
			var flag=false;
			for ( var i = 1; i < check_length; i++) {
				if ($(arr_check[i]).find('input:checkbox').attr('checked') == true) {
				  if(check_length2==1){
					document.getElementById("optionId").value =$(arr_check[i]).find('input:checkbox').val();
					document.getElementById("leftRight").value = tId;
					toright(tId);
					}else{
					for ( var j = 1; j < arr_check2.length; j++) {
						if($(arr_check[i]).find('input:checkbox').val()==$(arr_check2[j]).find('input:checkbox').val()){
							flag=true;
						}  
					}
				}
				 if(check_length2>1&&flag==false){
					document.getElementById("optionId").value =$(arr_check[i]).find('input:checkbox').val();
					document.getElementById("leftRight").value = tId;
					toright(tId);
				  }
				}
			}
			if(flag){
				alert("请勿重复选择！");
			}
			 
		} else {
			var arr_check = document.getElementById("tbody2")
					.getElementsByTagName("tr");
			var check_length = arr_check.length;
			for ( var i = 1; i < check_length; i++) {
				if ($(arr_check[i]).find('input:checkbox').attr('checked') == true) {
					document.getElementById("optionId").value =$(arr_check[i]).find('input:checkbox').val();
					document.getElementById("leftRight").value = tId;
					toleft(tId);
					i = i - 1;
					check_length = check_length - 1;
				}
			}
		}
	}

	/**
	 *将选择的项移动到另一table框里
	 */
	function toright(tId) {
		//获取要隐藏域里存储的值
		var optionId = document.getElementById("optionId").value;

		//tbody
		var root = "";
		//获取有单击事件列的里的值
		var trHTML = "";
		//获取移动到的tbody
		var leftRight = document.getElementById("leftRight").value;

		//如果隐藏域没有值或者移动的项已经在指定的tbody里，return
		if (leftRight == null || leftRight == "" || optionId == null
				|| optionId == "") {
			return;
		}
		if ((tId == "2" && leftRight == "1")
				|| (tId == "1" && leftRight == "2")) {
			return;
		}
		//根据移动方向，在对应的tbody内创建行并且获取列值
		var newRow = null;
		var oldRow = null;
		var oldCell = null;
		
		if (tId == "1") {
			root = document.getElementById("tbody2");
			var tagLength = document.getElementById("tbody2")
					.getElementsByTagName("tr").length;
			oldRow = document.getElementById('iframeList').contentWindow.document
					.getElementById("tr1_" + optionId);

			oldCell = oldRow.getElementsByTagName('td');					
			newRow = root.insertRow(tagLength);
			//newRow = $(root).append('<tr id="tr2_" + ' + optionId + ' oldvalue=2></tr>');			
			
			//新建行赋值id
			newRow.setAttribute("id", "tr2_" + optionId);
			newRow.setAttribute("oldvalue", "2");

		} else {
			root = document.getElementById('iframeList').contentWindow.document
					.getElementById("tbody1");
			var tagLength = document.getElementById('iframeList').contentWindow.document
					.getElementById("tbody1").getElementsByTagName("tr").length;
			oldRow = document.getElementById("tr2_" + optionId);
			oldCell = oldRow.getElementsByTagName('td');

			newRow = root.insertRow(tagLength);
			//新建行赋值id
			newRow.setAttribute("id", "tr1_" + optionId);
			newRow.setAttribute("oldvalue", "1");
		}
		trHTML = "<input type='checkbox'  name='check' value='"+optionId+"' onclick='ChkSonClick(\"check\",\"chkAll\")'>";
		
		/*
		var tr = '<td>' + trHTML + '</td>';
			tr+= '<td>' + oldCell[1].innerHTML + '</td>';
			tr+= '<td>' + oldCell[2].innerHTML + '</td>';
			tr+= '<td style="display:none">' + oldCell[3].innerHTML + '</td>';
			tr+= '<td style="display:none">' + oldCell[4].innerHTML + '</td>';
			tr+= '<td style="display:none">' + oldCell[5].innerHTML + '</td>';
			tr+= '<td style="display:none">' + oldCell[6].innerHTML + '</td>';
		
		newRow.append(tr);
		*/
		
		//给列赋值，如果为多列可以在这里更改
		var newCell0 = newRow.insertCell(0);
		var newCell1 = newRow.insertCell(1);
		var newCell2 = newRow.insertCell(2);
		var newCell3 = newRow.insertCell(3);
		var newCell4 = newRow.insertCell(4);
		var newCell5 = newRow.insertCell(5);
		var newCell6 = newRow.insertCell(6);

		newCell0.innerHTML = trHTML;
		newCell1.innerHTML = oldCell[1].innerHTML;
		newCell2.innerHTML = oldCell[2].innerHTML;
		newCell3.innerHTML = oldCell[3].innerHTML;
		newCell4.innerHTML = oldCell[4].innerHTML;
		newCell5.innerHTML = oldCell[5].innerHTML;
		newCell6.innerHTML = oldCell[6].innerHTML;
		
		newCell3.style.display = "none";
		newCell4.style.display = "none";
		newCell5.style.display = "none";
		newCell6.style.display = "none";
	}

	/**
	 *将选择的项移动到另一table框里
	 */
	function toleft(tId) {
		//获取要隐藏域里存储的值
		var optionId = document.getElementById("optionId").value;

		//调用删除原来tbody里内容
		deleterow(optionId, tId);
		//清空隐藏域的值
		clearOption();

	}
	//删除指定的行
	function deleterow(optionId, tId) {
		var trr = document.getElementById("tr" + tId + "_" + optionId);
		document.getElementById("tbody" + tId).removeChild(trr);
	}
	//清空隐藏域的值
	function clearOption() {
		document.getElementById("optionId").value = "";
		document.getElementById("leftRight").value = "";
	}
	 
	//点击确定按钮
	function updateshenpi() {
		var arr_check = document.getElementById("tbody2").getElementsByTagName("tr");
		var check_length = arr_check.length;
		 if(check_length!=1){
			var names = [];
			var codes = [];
			var ids = [];
			var units = [];
			var prices = [];
			var models = [];
			var j = 0;
			for ( var i = 1; i < check_length; i++) {
				//if(arr_check[i].childNodes[0].childNodes[0].checked){
					names[j] =  arr_check[i].childNodes[1].innerHTML;
					codes[j] =  arr_check[i].childNodes[2].innerHTML;
					ids[j] =  arr_check[i].childNodes[3].innerHTML;
					units[j] =  arr_check[i].childNodes[4].innerHTML;
					prices[j] =  arr_check[i].childNodes[5].innerHTML;
					models[j] =  arr_check[i].childNodes[6].innerHTML;
					j++;
				//}				
			}
			window.parent._callBackMyFriends3(names,codes,ids,units,prices,models);	
		}else{
			alert("未选择物品！");
		}		
	}
	
	
	
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


	$(document).ready(function() {
		$('tr').addClass('odd');
		$('tr:even').addClass('even'); //奇偶变色，添加样式 
	});
</script>
</head>
<body>
	<input type="hidden" id="optionId" value="" />
	<input type="hidden" id="leftRight" value="" />
	<table style="width: 100%; height: 350px;" border="0" cellspacing="0"
		cellpadding="0" class="table_1">
		<tr>
			<td style="background: #fff" colspan="3">
				<div id="div" class="t_r" style="width:300px;height:350px;">
					<table width="275" border="0" cellspacing="0" cellpadding="0" class="table_2"
						id="hacker">
						<iframe src="${ctx}/cloud/config/material/iframeList.ht?catalog_id=${catalog_id}"
							id="iframeList" style="width: 275px; height: 330px; border: 0;"
							frameborder="0" scrolling="auto" allowTransparency="false">
						</iframe>
					</table>

				</div>
				<div class="t_m" style="float:left;width:30px;height:350px;">
					<br /> <br /> <br /> <br /> <input type="submit" name="Submit"
						value="&gt;&gt;" class="input_1" onClick="toRightMore('1');" /><br />
					<br /> <br /> <br /> <input type="submit" name="Submit"
						value="&lt;&lt;" class="input_1" onClick="toRightMore('2');"><br />
					<br />
				</div>
				<div id="div" class="t_r" style="width:250px;height:350px;float:left">
					<table width="235px;" border="0" cellspacing="0" cellpadding="0"
						class="table_2" id="hacker">

						<div class="panel">
							<div class="panel-top">
								<div class="tbar-title">
									<span class="tbar-label">已选物品列表</span>
								</div>
							</div>
							<div class="panel-body">
								<tbody id="tbody2">

									<tr>
										<td id="tr_1" width="5">
										<INPUT name="chkAll" id="chkAll" title="全选/反选" onClick="ChkAllClick('check','chkAll')" type="checkbox" />

										</td>
										<td id="tr_1" >物品名称</td>
										<td id="tr_1" width="30%">物品编码</td>

										<td id="tr_1" style="display: none;">ID</td>
										<td id="tr_1" style="display: none;">单位</td>
    						       		<td id="tr_1" style="display: none;">单价</td>
    						       		<td id="tr_1" style="display: none;">物品规格</td>
									</tr>
								</tbody>
							</div>
							<!-- end of panel-body -->
						</div>
						<!-- end of panel -->
					</table>
				</div>
			</td>
		</tr>
		<tr style="background: #fff">
			<td colspan="3" align="center" style="height:30px;">
			<input	type="button" name="Submit1" value="确定" class="input_2"
				onclick="updateshenpi();" />&nbsp;&nbsp;&nbsp;  </td>
		</tr>
	</table>
</body>
</html>

