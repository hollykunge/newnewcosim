<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@include file="/commons/cloud/global.jsp" %>
<html>
<head>
<title>物品映射关系管理</title>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script>
$(function(){
	$('#friends').change(function(){
		var tgtEntId = $(this).val();
		location.href='?tgtEntId=' + tgtEntId;
	});
})

//点击映射
function btnOk(){
	//获取本企业所选
	var srcRows = getTableRow('srcMaterialsItem');
	var tgtRows = getTableRow('tgtMaterialsItem');
	
	if(srcRows.length==0){
		alert('请选择本企业物品');
		return;
	}
	
	if(tgtRows.length==0){
		alert('请选择目标企业映射物品');
		return;
	}
	
	var srcRow = srcRows[0];
	var tgtRow = tgtRows[0];
	
	var flag = false;
	//判断是否有记录
	$('#mappingSettings').find('tr').each(function(){
		var $tr = $(this);
		var tds = $tr.find('td');
		if($(tds[3]).text() == srcRow[3]){
			alert('该物品已选【' + srcRow[3] + '】');
			flag = true;
		}
	});
	
	//添加行
	if(!flag){
		appendRow(srcRow[0],srcRow[1],srcRow[2],srcRow[3],srcRow[4],tgtRow[0],tgtRow[1],tgtRow[2],tgtRow[3],tgtRow[4]);
	}
}

//增加行
function appendRow(srcEntId, srcEntName, srcMaterialId, srcMaterialCode, srcMeterialName, tgtEntId, tgtEntName, tgtMaterialId, tgtMaterialCode, tgtMaterialName){
	var str = '<tr>';
	str += '<td style="display:none">' + createInput('srcEntIds', srcEntId) + srcEntId + '</td>';
	str += '<td>' + createInput('srcEntNames',srcEntName) + srcEntName + '</td>';	
	str += '<td style="display:none">' + createInput('srcMaterialIds',srcMaterialId) + srcMaterialId + '</td>';
	str += '<td>' + createInput('srcMaterialCodes',srcMaterialCode) + srcMaterialCode + '</td>';
	str += '<td>' + createInput('srcMeterialNames',srcMeterialName) +  srcMeterialName + '</td>';
	str += '<td style="display:none">' + createInput('tgtEntIds',tgtEntId) +  tgtEntId + '</td>';
	str += '<td>' + createInput('tgtEntNames',tgtEntName) +  tgtEntName + '</td>';
	str += '<td style="display:none">' + createInput('tgtMaterialIds',tgtMaterialId) +  tgtMaterialId + '</td>';
	str += '<td>' + createInput('tgtMaterialCodes',tgtMaterialCode) +  tgtMaterialCode + '</td>';
	str += '<td>' + createInput('tgtMaterialNames',tgtMaterialName) +  tgtMaterialName + '</td>';
	str += '<td><a href="javascript:void(0);" onClick="removeRow(this);">删除</a></td>';
	str += '</tr>';
	$('#rows').append(str);
}

//删除行
function removeRow(input){
	var $tr = $(input).parent().parent();
	$tr.remove();
}

//创建隐藏Input
function createInput(name, value){
	return '<input type="hidden" name="' + name + '" value="' + value + '"/>';
}

//获取表格所在行数据
function getTableRow(tableId){
	var rows = [];
	var j = 0;
	$('#'+tableId).find(':radio:checked').each(function(){
		var $tr = $(this).parent().parent();
		var tds = $tr.find('td');
		var row = [];
		
		//企业Id		
		row[0] = $tr.find('input[id=entId]').val();
		//企业名称		
		row[1] = $(tds[1]).text();
		//物品Id
		row[2] = $tr.find('input[id=mId]').val();
		//物品编码
		row[3] = $(tds[2]).text();
		//物品名称
		row[4] = $(tds[3]).text();
		
		rows[j++] = row;
	});
	
	return rows;
}

//保存映射关系
function saveMappings(){
	//判断是否有记录
	var trs = $('#mappingSettings').find('tr');
	if(trs.length==1){
		alert('还未配置映射关系');
		return;
	}
	
	var options={};
	if(showResponse){
		options.success=showResponse;
	}
	var frm=$('#warehouseMaterialMappingForm').form();
			
	frm.setData();
	frm.ajaxForm(options);
	if(frm.valid()){
		form.submit();
	}
}

function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/warehouse/warehouseMaterialMapping/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物品映射关系管理</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link add" id="btnUpd" onClick="saveMappings();">保存映射关系</a>
					</div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">所属企业:</span>
						<ap:selectList name="friends" id="friends" value="friends" selectedValue="${tgtEntId}">
							<ap:option value="0">--请选择商友--</ap:option>
							<ap:option value="corpEnterprise.sysOrgInfoId" text="corpEnterprise.name"></ap:option>
						</ap:selectList>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			<div style="width:100%;float:left;">
				<!-- 本企业物品 -->
				<div style="float:left;width:48%;border:1px solid #eee;">
					本企业					
					<display:table name="srcMaterialsList" id="srcMaterialsItem" requestURI="mappingSettings.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
						<display:column title="${checkAll}" media="html" style="width:30px;">
					  		<input type="radio" class="pk" id="mId" name="rr" value="${srcMaterialsItem.id}">
					  		<input type="hidden" id="entId" value="${srcMaterialsItem.entId}">
						</display:column>
						<display:column property="entName" title="企业" sortable="true" sortName="entId"></display:column>
						<display:column property="code" title="物品编码" sortable="true" sortName="code"></display:column>
						<display:column property="name" title="物品名称" sortable="true" sortName="name" maxLength="80"></display:column>
						<display:column property="typeClass" title="所属分类" sortable="true" sortName="levlType"></display:column>				
					</display:table>
					<hotent:paging tableId="srcMaterialsItem" showExplain="false"/>
				</div>
				
				<div style="float:left;width:48%;margin-left:5px;border:1px solid #eee;">
					目标企业
					<!-- 目标企业物品 -->
					<display:table name="tgtMaterialsList" id="tgtMaterialsItem" requestURI="mappingSettings.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
						<display:column title="${checkAll}" media="html" style="width:30px;">
					  		<input type="radio" class="pk" id="mId" name="dd" value="${tgtMaterialsItem.id}">
					  		<input type="hidden" id="entId" value="${tgtMaterialsItem.entId}">
						</display:column>
						<display:column property="entName" title="企业" sortable="true" sortName="entId"></display:column>
						<display:column property="code" title="物品编码" sortable="true" sortName="code"></display:column>
						<display:column property="name" title="物品名称" sortable="true" sortName="name" maxLength="80"></display:column>
						<display:column property="typeClass" title="所属分类" sortable="true" sortName="levlType"></display:column>				
					</display:table>
					<hotent:paging tableId="tgtMaterialsItem" showExplain="false"/>
				</div>
			</div>
			
			<div style="height:0px;clear:both;width：100%"></div>
			
			<div class="l-dialog-buttons">
				<div class="l-dialog-buttons-inner">
					<div class="l-dialog-btn" style="margin-left:350px;float:left;">
						<div class="l-dialog-btn-l"></div>
						<div class="l-dialog-btn-r"></div>
						<div class="l-dialog-btn-inner" id="ok" onClick="btnOk();">设置</div>
					</div>
				</div>
			</div>
			
			<div style="border:1px solid #eee;margin-top:15px;">
				物品映射关系设置
				<form id="warehouseMaterialMappingForm" method="post" action="saveMappings.ht">
				<table cellspacing="1" cellpadding="1" class="table-grid table-list" 
					id="mappingSettings">
					<thead>
						<tr>
							<th style="display:none">企业Id</th>
							<th>企业名称</th>
							<th style="display:none">物品Id</th>
							<th>物品编码</th>
							<th>物品名称</th>
							<th style="display:none">映射企业Id</th>
							<th>映射企业</th>
							<th style="display:none">映射物品Id</th>
							<th>映射物品编码</th>
							<th>映射物品名称</th>
							<th>管理</th>
						</tr>
					</thead>
					<tbody id="rows">
					</tbody>
				</table>
				</form>
			</div>

			<!-- 本企业映射表  -->
			<div style="border:1px solid #eee;margin-top:15px;">
				已有物品映射关系
			    <display:table name="warehouseMaterialMappingList" id="warehouseMaterialMappingItem" requestURI="mappingSettings.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
					<display:column property="srcProdCode" title="物品编码" sortable="true" sortName="SRC_PROD_CODE"></display:column>
					<display:column property="srcProdName" title="物品名称" sortable="true" sortName="SRC_PROD_NAME"></display:column>
					<display:column property="tgtEntName" title="企业名称" sortable="true" sortName="TGT_ENT_NAME"></display:column>
					<display:column property="tgtProdCode" title="映射物品编码" sortable="true" sortName="TGT_PROD_CODE"></display:column>
					<display:column property="tgtProdName" title="映射物品名称" sortable="true" sortName="TGT_PROD_NAME"></display:column>
				</display:table>
				<hotent:paging tableId="warehouseMaterialMappingItem"/>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>