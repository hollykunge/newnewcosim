<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>邮件</title>
	<%@include file="/commons/include/form.jsp" %>
    <link rel="stylesheet" href="${ctx }/js/tree/v35/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=outMail"></script>
	<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/mail/MailDef.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js"></script>
	<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<style type="text/css">
	html,body{ padding:0px; margin:0; width:100%;height:100%;overflow: hidden;} 
	.focus {background-color: #FFCCFF;}
	.tree-title{overflow:hidden;width:8000px;}
	.ztree{overflow: auto;}
	.link-btn {
	cursor: pointer;
	display: inline-block;
	padding: 0 4px 1px 20px;
	}
	.link-remove {
		background: url(${ctx}/styles/default/images/button/remove.png) 0px 3px no-repeat !important;
		text-decoration: none;
	}
	</style>
	<script type="text/javascript">
		$(function() {
			loadTree();
			layout();
			function showRequest(formData, jqForm, options){return true;} 
			OutMailDef.getEditor();
			var editor=CKEDITOR.instances.content;
			
			valid(showRequest,showResponse);
			$("a.run").click(function() {
				var rtn=$("#outMailForm").valid();
				if(!rtn) return;
				$.ligerDialog.waitting("正在发送,请您耐心等待...");
				var content=editor.getData();
				$('#types').val(2);
				$('#isReply').val(0);
				$("#content").attr('value', content);
				$('#outMailForm').submit();
			});
			$('#dataFormDraft').click(function() {
				var rtn=$("#outMailForm").valid();
				if(!rtn) return;
				var content=editor.getData();
				$("#content").attr('value',content);
				$('#types').val(3);
				$('#isReply').val(0);
				$('#outMailForm').submit(); 
			});
			$("input[address='true']").click(changeClass);
		});
		
		function showResponse(responseText){
			var type=$('#types').val();
			var obj=new com.hotent.form.ResultMessage(responseText);
			$.ligerDialog.closeWaitting();
			var setId=$("select option:selected").attr('address');
			if(obj.isSuccess()){//成功
				$.ligerMessageBox.success("成功",obj.getMessage(),function(){
					window.location.href="${ctx}/platform/mail/outMail/list.ht?id="+setId+"&&types="+type; 		
				});
		    }else{//失败
		    	$.ligerDialog.error(obj.getMessage());
		    }
		};
		
        //重 置
        function reset(obj) {
			$('#receiverAddresses').val('');
			$('#ccAddresses').val('');
			$('#bcCAddresses').val('');
			$('#title').val('');
			$('#fileIds').val('');
		}
        
		//布局
		function layout(){
			$("#layout").ligerLayout( {
				leftWidth : 230,
				onHeightChanged: heightChanged
			});
			//取得layout的高度
	        var height = $(".l-layout-center").height();
	        $("#linkmanTree").height(height-60);
		};
		
		//布局大小改变的时候通知tab，面板改变大小
	    function heightChanged(options){
	     	$("#linkmanTree").height(options.middleHeight - 60);
	    };
	    
		//树
		var linkmanTree;
		
		//加载树
		function loadTree(){
			var setting = {
				data: {
					key : {
						name: "linkAddress"
					},
					simpleData: {
						enable: true,
						idKey: "linkId"
					}
				},
				view:{
					showIcon:false
				},
				callback:{
					onClick: zTreeOnLeftClick
				}
			};
			$.post("${ctx}/platform/mail/outMailLinkman/getOutMailLinkmanData.ht",function(result){
					linkmanTree= $.fn.zTree.init($("#linkmanTree"), setting, result);}
			);
		};
		
		//左击
		function zTreeOnLeftClick(event, treeId, treeNode){
			var txtAddress=$("input.focus[address='true']");
			if(txtAddress.length==0){
				 $.ligerMessageBox.warn("提示信息","请选择要填入的地址");
				 return;
			}
			var address=treeNode.linkAddress;
			address=address.substring(address.indexOf('(')+1,address.indexOf(')'));
			var thistemp=txtAddress.val();
			if(thistemp==''){
				txtAddress.val(address);
			}else{
				var arrtemp=thistemp.split(',');
				for(var i=0;i<arrtemp.length;i++){
					if(arrtemp[i]==address){
						txtAddress.val(thistemp); break;
					}else{
						txtAddress.val(thistemp+","+address);
					}
				}
			}
		};
		
		//改变样式
		function changeClass() {
			var obj=$(this);
			var aryAddress=$("input[address='true']");
			aryAddress.attr('class', 'inputText');
			obj.attr('class', "focus");
		};
		
		//添加附件
		function addFile(){
			FlexUploadDialog({isSingle:false,callback:fileCallback});
		};
		
		function fileCallback(fileIds,fileNames,filePaths)
		{
			var arrPath;
			if(filePaths=="") return ;
			arrPath=filePaths.split(",");
			var arrfileId;
			if(fileIds=="") return ;
			arrfileId=fileIds.split(",");
			var arrfileName;
			if(fileNames=="") return ;
			arrfileName=fileNames.split(",");
			
			for(var i=0;i<arrfileName.length;i++){
				var tr=getRow(arrfileId[i],arrPath[i],arrfileName[i]);
				$("#sysFileList").append(tr);	
			}
			$("#fileIds").val(fileIds);
		};
		
		
		function delRow(fileId){
			$("tr ."+fileId).remove();
		};
		
		function getRow(fileId,filePath,fileName){
			var tr = '<tr class="'+fileId+'">';
			tr += '<td>';
			tr += '	<span class="attach">&ensp;&ensp; </span>&nbsp;&nbsp;';
			tr += '	<input type="hidden" class="pk" name="filePath" value="' + filePath + '"/> ';
			tr += '	<a href="'+__ctx+"/"+filePath+'">'+fileName+'</a> ';
			tr += '</td>';
			tr += '<td><a onclick=javascript:delRow('+fileId+'); ><span class="link-btn link-remove">&ensp; </span></a> </td>';
			tr += '</tr>';
			return tr;
		}
	</script>
</head>
<body>  
	<div id="layout">
		<div position="right" title="最近联系人">
			<div id="linkmanTree" class="ztree"></div>
		</div>
		<div id="editor" position="center"  style="overflow: auto;">
			<div position="center">
				<div class="panel">
					<div class="panel-top">
						<div class="tbar-title">
							<span class="tbar-label">新建邮件</span>
						</div>
						<div class="panel-toolbar">
							<div class="toolBar">
							    <div class="group"><a class="link run" id="dataFormSave">发送邮件</a></div>
								<div class="l-bar-separator"></div>
							    <div class="group"><a class="link save" id="dataFormDraft">保存草稿</a></div>
								<div class="l-bar-separator"></div>
							    <div class="group"><a class="link undo" id="dataFormRest" onclick="reset(this)">重置</a></div>
							</div>
						</div>
					</div>
					<div class="panel-body">
						<form id="outMailForm" method="post" action="send.ht" >
							<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<th width="15%">发件人:<span class="required">*</span></th>
									<td>
								    <select id="senderAddresses"  name="senderAddresses" style="width: 35%;">
										<c:forEach items="${outMailUserSetingList}" var="mail">
										    <option value="${mail.mailAddress}" address="${mail.id}" <c:if test="${mail.isDefault==1}">selected='selected'</c:if>>${mail.mailAddress}</option>
									    </c:forEach>
									</select>
								</tr>
								<tr>
									<th width="15%">收件人:<span class="required">*</span></th>
									<td height="45" ><input type="text" address="true"  id="receiverAddresses" name="receiverAddresses" value="${outMail.receiverAddresses}"  class="inputText" style="width: 80%;"/><br>(注：可以直接单击最近联系人列表，也可手动输入，多个收件人以","号分割如两个收件人：aaa@163.com,bbb@163.com)</td>
								</tr>
								<tr>
									<th width="15%">抄送人: </th>
									<td><input type="text" address="true"  id="ccAddresses" name="ccAddresses" value="${outMail.ccAddresses}"  class="inputText"  style="width: 80%;"/></td>
								</tr>
								<tr>
									<th width="15%">暗送人: </th>
									<td><input type="text" address="true"  id="bcCAddresses" name="bcCAddresses" value="${outMail.bcCAddresses}"  class="inputText"  style="width: 80%;"/></td>
								</tr>
								<tr>
									<th width="15%">主题: </th>
									<td><input type="text" id="title" name="title" value="${outMail.title}"  class="inputText"  style="width: 80%;"/></td>
								</tr>
								<tr>
									<th width="15%">附件: </th>
									<td>
									<table width="145" id="sysFileList" class="table-grid table-list" id="0" cellpadding="1" cellspacing="1"></table>
									<a href="javascript:void(0)" onclick="addFile()">添加附件</a></td>
								</tr>
								<tr>
									<td colspan="2">
									<textarea  name="content" id="content" >${outMail.content}</textarea>
									</td>
								</tr>
							</table>
						
							<input type="hidden" name="fileIds" id="fileIds" value="${outMail.fileIds}" />
							<input type="hidden" name="mailId" value="${outMail.mailId}" />
							<input name="types" id="types" type="hidden" value="${mail.mailAddress}"/> 
							<input type="hidden" id="isReply" name="isReply" value="${mail.isReply}"/>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
