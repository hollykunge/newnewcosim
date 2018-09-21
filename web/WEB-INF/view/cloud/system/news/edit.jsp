<%--
	time:2013-04-13 07:29:19
	desc:edit the 新闻公告
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>新闻公告</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	
	 
<!-- ueditor -->
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css" />

	<script type="text/javascript">
		$(function() {
			var editor = new baidu.editor.ui.Editor({
				minFrameHeight : 260,
				initialContent : ''
			});
			editor.render("txtHtml");
			
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#newsForm').form();
			$("a.save").click(function() {
			if(frm.valid()){
					$('#content').val(editor.getContent());
					frm.setData();
					frm.ajaxForm(options);
					form.submit();
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/system/news/list.ht";
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
		    <c:choose>
			    <c:when test="${news.id !=null}">
			        <span class="tbar-label">编辑新闻公告</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加新闻公告</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="newsForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="5%">标题: </th>
					<td ><input type="text" id="title" name="title" value="${news.title}" style="width:90%;"  class="inputText" validate="{required:true,maxlength:600}"  /></td>
				</tr>
				<tr>
					 <th width="5%">内容: </th>
					<td >
						<div id="editor" position="center"  style="overflow:auto;height:100%;">
							<textarea id="txtHtml" name="html">${fn:escapeXml(news.content)}</textarea>							
						</div>
					</td>
				</tr>				
			</table>
			<input id="content" name="content" type="hidden"></input>
			<input type="hidden" name="id" value="${news.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
