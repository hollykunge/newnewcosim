<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 
<%@ include file="/commons/include/html_doctype.html"%>
<%@ include file="/commons/cloud/global.jsp"%>
 
<head>
<title></title>
<%@include file="/commons/cloud/meta.jsp"%>
<!-- ui-tabs -->
<link title="index" name="styleTag" rel="stylesheet" type="text/css" href="${ctx}/styles/default/css/web.css"></link>
<link href="${ctx}/js/lg/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/tree/v35/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/default/css/form.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/js/util/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/base.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/htDicCombo.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenuBar.js" ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenu.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTextBox.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTip.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMessageBox.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMsg.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js" ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>
<script type="text/javascript" src="${ctx}/js/calendar/My97DatePicker/WdatePicker.js" ></script>

<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/rule.js"></script>

<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CustomForm.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/SubtablePermission.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/OfficeControl.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/OfficePlugin.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormData.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/ImageQtip.js" ></script>

</head>
<body >
<% String fileId=request.getParameter("fileId"); %>

<script type="text/javascript">

	$(function(){
		var obj=new OfficeControl();
		obj.renderTo("office",{fileId:<%=fileId%>});
	});
	
</script>
<div id="office" style="width:100%;height:100%"></div>
</body>
</html>
