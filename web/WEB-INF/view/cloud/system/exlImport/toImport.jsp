<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>	
	<%@include file="/commons/include/html_doctype.html"%>
	<%@ include file="/commons/cloud/meta.jsp"%>
	<%@include file="/commons/include/form.jsp"%>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
	<title>导入信息</title>	
	<script>
		var options = {};
		var dd = null;
		if (showResponse) {
			options.success = showResponse;
		}
		
		function importExl(ff,table,key,sheetNum){
			dd = $.ligerDialog.waitting('正在导入,请等待...');
			var str = '${ctx}/cloud/system/exlImport/importExl.ht?table=' + table + '&key='+key + '&sheetNum=' + sheetNum;
			$('#'+ff).attr('action',str);
			var frm = $('#'+ff).form();
			frm.ajaxForm(options);
			frm.submit();
		}
		
		function showResponse(responseText) {			
			var obj = new com.hotent.form.ResultMessage(responseText);
			dd.close();
			if (obj.isSuccess()) {
				$.ligerMessageBox.alert("提示信息",	obj.getMessage());
			} else {
				$.ligerMessageBox.error("提示信息", obj.getMessage());
			}
		}
		
		//导入企业信息
		function importCompany(ff){
			dd = $.ligerDialog.waitting('正在导入,请等待...');
			var frm = $('#'+ff).form();
			frm.ajaxForm(options);
			frm.submit();
		}
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">数据导入列表</span>
			</div>
			<div class="panel-search">
				<form id="f1"  method="post" enctype="multipart/form-data">
					国别信息导入：<input type="file" name="excel"/>
					<input type="button" onclick="importExl('f1','cloud_sys_dic','id',0);" value="导入" />
				</form>
				
				<form id="f2" method="post" enctype="multipart/form-data">
					物品类别导入：<input type="file" name="excel"/>
					<input type="button" onclick="importExl('f2','CLOUD_MATERIAL_CLASS','id',1);" value="导入" />
				</form>
				
				
				<form id="f21" method="post" enctype="multipart/form-data">
					物品自定义类：<input type="file" name="excel"/>
					<input type="button" onclick="importExl('f21','cloud_material_catalog','id',0);" value="导入" />
				</form>
				
				<form id="f22" method="post" enctype="multipart/form-data">
					物品信息导入：<input type="file" name="excel"/>
					<input type="button" onclick="importExl('f22','cloud_material','id',0);" value="导入" />
				</form>
				
				
				<form id="f23" method="post" enctype="multipart/form-data">
					物品库存导入：<input type="file" name="excel"/>
					<input type="button" onclick="importExl('f23','cloud_warehouse_accounts','id',0);" value="导入" />
				</form>
				
				<form id="f3" method="post" enctype="multipart/form-data">
					能力类别导入：<input type="file" name="excel"/>
					<input type="button" onclick="importExl('f3','CLOUD_CAPABILITY_CLASS','id',1);" value="导入" />
				</form>
				
				<form id="f4" method="post" enctype="multipart/form-data">
					企业类型导入：<input type="file" name="excel"/>
					<input  type="button"  onclick="importExl('f4','SYS_DIC','dicId',0);" value="导入" />
				</form>
				
				<form id="f5" method="post" enctype="multipart/form-data">
					经营范围导入：<input type="file" name="excel"/>
					<input  type="button"  onclick="importExl('f5','SYS_DIC','dicId',0);" value="导入" />
				</form>
				
				<form id="f6" action="${ctx}/cloud/system/exlImport/importCompany.ht" method="post" action="" enctype="multipart/form-data">
					企业批量导入：<input type="file" name="excel"/>
					<input  type="button"  onclick="importCompany('f6')" value="导入" />
				</form>
			</div>
		</div>
	</div>
</body>
</html>
