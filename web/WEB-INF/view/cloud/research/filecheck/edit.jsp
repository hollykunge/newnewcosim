<%--
	time:2013-05-21 16:04:50
	desc:edit the cloud_research_filecheck
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 文档审签</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>

<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript">
	
	
	
/* 	function openModel(fileId){
		url = "${ctx}/cloud/research/openModel.ht?fileId="+fileId;
		window.open(url);
	}
	function openFile(fileId){

		url = "${ctx}/cloud/research/office.ht?fileId="+fileId;
		window.open(url);
	} */
	
	
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			
		var frm=$('#resFilecheckForm').form();
		
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		

		$("a.apply").click(function() {
			frm.setData();
			$('#resFilecheckForm').attr('action','apply.ht');
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		
/* 		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/sale/saleQuote/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
			
			
			$("a.agree").click(function() {
				operatorType=1;
				var tmp = '1';
				/*if(isDirectComlete){//直接一票通过
					tmp = '5';
				}*/
				$('#voteAgree').val(tmp);
				$('#back').val("1");

				$('#resFilecheckForm').attr("action","complete.ht");
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
			
	
		$("a.notAgree").click(function() {
			
			operatorType=2;
			var tmp = '2';
			/*if(isDirectComlete){//直接一票通过
				tmp = '6';
			}*/
			$('#voteAgree').val(tmp);
			$('#back').val("1");
			
			$('#resFilecheckForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
     	var userChooser;
		function chooseUsers(a){
			var $table = $(a).parent().parent().parent();
			if( $table.find('input[name="taskName"]').val() == "4-会签" || $table.find('input[name="taskName"]').val() == "5-批准"){
      			alert("该环节只需选取审签企业");
      			return;
      		}
			
			//弹出企业用户选择器
			var urlShow = '${ctx}/cloud/console/busiarea/listUsersByCompAndRole.ht';
			userChooser = $.ligerDialog.open({ url:urlShow, height: 400,width: 600, title :'人员选择器', name:"frameDialog_"});
		}
		
			function _callBackUsers(users){
			var names='',ids='';
			for(var i=0; i<users.length; i++){
				var user = users[i];
				ids += ',' + user[0];
				names += ',' + user[1];
			}
			ids = ids!=''?ids.substring(1):"";
			names = names!=''?names.substring(1):"";
			
			$('.l-dialog-table  input[name="checkUserids"]').val(ids);
			$('.l-dialog-table  input[name="checkUsernames"]').val(names);
		    userChooser.close();
		}
		
		
		var entpriseChooser;
      	function chooseEntprises(a){
      		var $table = $(a).parent().parent().parent();
      		if( $table.find('input[name="taskName"]').val() == "2-校对" || $table.find('input[name="taskName"]').val() == "3-审核"){
      			alert("该环节只需选取本企业审签人");
      			return;
      		}
      		//弹出供应企业选择框
      		var urlShow = '${ctx}/cloud/console/busiarea/listMyFriends.ht';
      		entpriseChooser = $.ligerDialog.open({ url:urlShow, height: 400,width: 600, title :'企业选择器', name:"frameDialog_"});
      	}
      	
      	//商圈列表回调函数
      		//商圈列表回调函数
	function _callBackMyFriends(friends){
		var names='',ids='';
		var i=0;
		if(friends[0]==""){
			i=1;
		}else{
			i=0;
		}
		for(i; i<friends.length; i++){
			var friend = friends[i];
			ids += ',' + friend[0];
			names += ',' + friend[1];
		}
		ids = ids!=''?ids.substring(1):"";
		names = names!=''?names.substring(1):"";
		$('.l-dialog-table  input[name="checkEnterpriseids"]').val(ids);
  		$('.l-dialog-table  input[name="checkEnterprisenames"]').val(names);
  		entpriseChooser.close();
	}
      	
      	
	function add_document(){
		var row = "<tr class=\"subdata\" type=\"subdata\"><td  style=\"text-align: center\" ><input  type=\"checkbox\" name=\"check\" value=\"\"/></td><td style=\"text-align: center\" name=\"fileName\"><span onclick=\"addFile(this);\" class=\"selectFile\"><a  href=\"#\" class=\"link attachement\">选择</a></span></td>"+
    	"<td style=\"text-align: center;width:200px\" name=\"fileComment\"><textarea name=\"fileComment\" style=\"width:200px;height:30px\" value=\"\"/></td>"+
    	"<td style=\"text-align: center\" name=\"fileType\"></td>"+
	    "<td style=\"text-align: center\" name=\"uploadUsername\"><input type=\"text\" name=\"uploadUsername\" value=\"\"/></td>"+
    	"<td style=\"text-align: center\" name=\"uploadTime\"><input type=\"text\" name=\"uploadTime\" value=\"\"/></td>"+								
    	"<td style=\"text-align: center\" name=\"fileUrl\"></td>"+
    	"<input type=\"hidden\" name=\"fileName\" value=\"\"/>"+
    	"<input type=\"hidden\" name=\"fileType\" value=\"\"/>"+
    	"<input type=\"hidden\" name=\"uploadUserid\" value=\"\"/>"+
    	"<input type=\"hidden\" name=\"fileVersion\" value=\"\"/>"+
    	"<input type=\"hidden\" name=\"managementId\" value=\"\"/>"+
    	"</tr>";		
    	$("#resFilecheckDetail").append(row);
	}
	 Date.prototype.format = function(format){
		 /*
		  * eg:format="YYYY-MM-dd hh:mm:ss";
		  */
		 var o = {
		  "M+" :  this.getMonth()+1,  //month
		  "d+" :  this.getDate(),     //day
		  "h+" :  this.getHours(),    //hour
		      "m+" :  this.getMinutes(),  //minute
		      "s+" :  this.getSeconds(), //second
		      "q+" :  Math.floor((this.getMonth()+3)/3),  //quarter
		      "S"  :  this.getMilliseconds() //millisecond
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
	*全选/反选
	*/
	$(function(){
		$("#checkall").click(function(){
			var state = $("#checkall").attr("checked");
			if (state=='checked') {
				 $("[name=check]:checkbox").attr("checked", true);
			}else{
				 $("[name=check]:checkbox").attr("checked", false);
			}
		});
		
	});
	 function checkall() {	
		
		if ($("#checkall").checked){
			 $("[name=check]:checkbox").attr("checked", true);
		}else{
			 $("[name=check]:checkbox").attr("checked", false);
		}	
	} 
	 function delproduct(){
		 
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					$(this).parent().parent().remove();
				}
			});
		}
	 var currentObject=null;
		//添加附件
		function addFile(a){
			currentObject = a;
			FlexUploadDialog({isSingle:true,callback:fileCallback});
		};
		
		var fileType1="doc";
		var fileUrl1;
		function fileCallback(fileId,fileName,filePath) {
			fileType1 = filePath.slice(filePath.lastIndexOf(".")+1);
			fileUrl1 = filePath;
			var currentItem = $(currentObject).closest('tr').html();
			
			var fName = $(currentObject).closest('tr').find("td[name='fileName']");
			var fComment = $(currentObject).closest('tr').find("td[name='fileComment']");
			var fType = $(currentObject).closest('tr').find("td[name='fileType']");
			var fUrl = $(currentObject).closest('tr').find("td[name='fileUrl']");
			var formName = $(currentObject).closest('tr').find("input[name='fileName']");
			var fileType = $(currentObject).closest('tr').find("input[name='fileType']");
			var formUrl = $(currentObject).closest('tr').find("input[name='fileUrl']");
			var formuploadUserid = $(currentObject).closest('tr').find("input[name='uploadUserid']");
			var formuploadUsername = $(currentObject).closest('tr').find("input[name='uploadUsername']");
			var formuploadTime = $(currentObject).closest('tr').find("input[name='uploadTime']");
			var formmanagementId = $(currentObject).closest('tr').find("input[name='managementId']");
			
			var currentuserid= ${currentuserid};
			var currentusername = '${currentusername}';
			/* $('.l-dialog-table input[name="fileType"]').val(fileType);
			$('.l-dialog-table input[name="managementId"]').val(fileId);
			$('.l-dialog-table input[name="fileName"]').val(fileName); */
			fName.append(fileName);
			fType.append(fileType1);
			formName.val(fileName);
			fileType.val(fileType1);
			
			formuploadUserid.val(currentuserid);
			formuploadUsername.val(currentusername);
			var testDate = new Date();
			 var testStr = testDate.format("yyyy-MM-dd hh:mm:ss");
			formuploadTime.val(testStr);
			formmanagementId.val(fileId);
			
			var a = "<a href=\"#\" onclick=\"getFileInfo('${ctx}',"+fileId+")\">获取详情</a> <a href=\"#\" onclick=\"openFile('${ctx}',"+fileId+")\">打开</a> <a href=\"#\" onclick=\"downloadFile('${ctx}',"+fileId+")\">下载</a> ";
			fUrl.append(a);
			
			$(".selectFile").hide();
		}
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-body">
		<form id="resFilecheckForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr style="display: none;">
			        <td><input type="text" name="currentUserIds" value="${resFilecheck.currentUserIds}"  class="inputText" validate="{required:false,maxlength:256 }"/></td>
			    </tr>
				<tr style="display: none;">
			        <td><input type="text" name="currentEnterpIds" value="${resFilecheck.currentEnterpIds}"  class="inputText" validate="{required:false,maxlength:256 }"/></td>
			    </tr>
			    
				<tr>
					<input type="hidden" name="id" value="${resFilecheck.id}" />
					<th width="20%">文档编码: </th>
					<td><input type="text"  readonly class="r" id="code" name="code" value="${resFilecheck.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">制单人: </th>
					<td><input type="text" id="operatorId" name="operatorId" value="${resFilecheck.operatorId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%" >制单人姓名: </th>
					<td><input type="text"  readonly class="r"  id="operatorName" name="operatorName" value="${resFilecheck.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text"  readonly class="r"  id="operateDate" name="operateDate" value="<fmt:formatDate value='${resFilecheck.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">企业ID: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${resFilecheck.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">企业: </th>
					<td><input type="text"  readonly class="r"  id="enterpriseName" name="enterpriseName" value="${resFilecheck.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">文档审签状态: </th>
					<td><input type="text" id="status" name="status" value="${resFilecheck.status}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">run_id: </th>
					<td><input type="text" id="runId" name="runId" value="${resFilecheck.runId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>

			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="resFilecheckDetail" formType="window" type="sub">
				<tr>
					<td colspan="10">
						<div class="group" align="left">
				   			<!-- <a id="btnAdd" class="link add">添加</a> -->
				   			 <a href="javascript:void(0)" onclick="add_document();"   style="text-decoration: none;">
								<img src="${ctx}/images/iconadd.jpg" width="52" height="18" onmouseover="src='${ctx}/images/iconadd2.jpg'" onmouseout="src='${ctx}/images/iconadd.jpg'" style="border: 0;">
							 </a>&nbsp;&nbsp;
								  
							<a href="javascript:void(0)"  onclick="delproduct();"   style="text-decoration: none;">
								<img src="${ctx}/images/icon_del2.jpg" height="18" width="52" onmouseover="src='${ctx}/images/icon_del.jpg'" onmouseout="src='${ctx}/images/icon_del2.jpg'" style="border: 0;">
							 </a>
			    		</div>
			    		<div align="center">
						文档审签 : 文档审签_文件列表
			    		</div>
		    		</td>
				</tr>
				<tr>
				<th><input type="checkbox"  id="checkall" title="全选/反选"/></th>
					<th>文件名</th>
					<th>文件描述</th>
					<th>文件类型</th>
					<th>上传人</th>
					<th>上传时间</th>
					
			       <th>文件操作</th>
		    	<!--	
					<th>上传者ID</th>
					<th>上传人</th>
					<th>上传时间</th>
					<th>文件版本</th>
					<th>管理ID</th>
					  -->
					<!-- <th>操作</th> -->
					
				</tr>
				<c:forEach items="${resFilecheckDetailList}" var="resFilecheckDetailItem" varStatus="status">
				    <tr type="subdata">
				   		 <td  style="text-align: center" ><input  type="checkbox" name="check" /></td>
					    <td style="text-align: center" name="fileName">${resFilecheckDetailItem.fileName}</td>
					    <td style="text-align: center" name="fileComment">${resFilecheckDetailItem.fileComment}</td>
					    <td style="text-align: center" name="fileType">${resFilecheckDetailItem.fileType}</td>
					     <td style="text-align: center" name="uploadUsername">${resFilecheckDetailItem.uploadUsername}</td>
						<td style="text-align: center" name="cuploadTime"><fmt:formatDate value='${resFilecheckDetailItem.uploadTime}' pattern='yyyy-MM-dd'/></td>
				    <td style="text-align: center">
				    	<a href="javascript:void(0)" onclick="getFileInfo('${ctx}','${resFilecheckDetailItem.managementId}')">获取详情</a> <a href="javascript:void(0)" onclick="openFile('${ctx}','${resFilecheckDetailItem.managementId}')">打开</a> <a href="javascript:void(0)" onclick="downloadFile('${ctx}','${resFilecheckDetailItem.managementId}')">下载</a> 
				    </td>
				<!-- 	    <td style="text-align: center" name="uploadUserid">${resFilecheckDetailItem.uploadUserid}</td>
					    <td style="text-align: center" name="uploadUsername">${resFilecheckDetailItem.uploadUsername}</td>
						<td style="text-align: center" name="uploadTime"><fmt:formatDate value='${resFilecheckDetailItem.uploadTime}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="fileVersion">${resFilecheckDetailItem.fileVersion}</td>
					    <td style="text-align: center" name="managementId">${resFilecheckDetailItem.managementId}</td>
					     -->	
			<!-- 		    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td> -->
						 <input type="hidden" name="fileName" value="${resFilecheckDetailItem.fileName}"/>
						<input type="hidden" name="fileComment" value="${resFilecheckDetailItem.fileComment}"/>
						<input type="hidden" name="fileType" value="${resFilecheckDetailItem.fileType}"/>
						<%-- <input type="hidden" name="fileUrl" value="${resFilecheckDetailItem.fileUrl}"/> --%>
						<input type="hidden" name="uploadUserid" value="${resFilecheckDetailItem.uploadUserid}"/>
						<input type="hidden" name="uploadUsername" value="${resFilecheckDetailItem.uploadUsername}"/>
					    <input type="hidden" name="uploadTime" value="<fmt:formatDate value='${resFilecheckDetailItem.uploadTime}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="fileVersion" value="${resFilecheckDetailItem.fileVersion}"/>
						<input type="hidden" name="managementId" value="${resFilecheckDetailItem.managementId}"/>
				    </tr>
				</c:forEach>
				<tr type="append" class="append">
			    	<td style="text-align: center" name="fileName"></td>
			    	<td style="text-align: center" name="fileComment"></td>
			    	<td style="text-align: center" name="fileType"></td>
					<td style="text-align: center" name="fileUrl"></td>
			  <!--   	<td style="text-align: center" name="uploadUserid"></td>
			    	<td style="text-align: center" name="uploadUsername"></td>
					<td style="text-align: center" name="uploadTime"></td>								
			    	<td style="text-align: center" name="fileVersion"></td>
			    	<td style="text-align: center" name="managementId"></td>
			    	  --> 
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	  
			    	<input type="hidden" name="fileName" value=""/>
			    	<input type="hidden" name="fileComment" value=""/>
			    	<input type="hidden" name="fileType" value=""/>
			    	<input type="hidden" name="fileUrl" value=""/>
			    	<input type="hidden" name="uploadUserid" value=""/>
			    	<input type="hidden" name="uploadUsername" value=""/>
			    	<input type="hidden" name="uploadTime" value="" class="inputText date"/>
			    	<input type="hidden" name="fileVersion" value=""/>
			    	<input type="hidden" name="managementId" value=""/>
		 		</tr>
		    </table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="resFilecheckOpinion" formType="window" type="sub">
				<tr>
					<td colspan="11">
						<div class="group" align="left">
				  <!--   			<a id="btnAdd" class="link add">添加</a>         -->
			    		</div>
			    		<div align="center">
						文档审签 : 文档审签_审签意见
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>任务id</th>
					<th>任务名</th>
					<th>审签人</th>
					<th>审签企业</th>
			 		<!-- <th>审签人id</th>
			       	<th>审签企业id</th> -->
			 	 <!--		<th>操作</th>
				<th>审签意见id</th>
					           -->

				</tr>
				<c:forEach items="${resFilecheckOpinionList}" var="resFilecheckOpinionItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="taskId">${resFilecheckOpinionItem.taskId}</td>
					    <td style="text-align: center" name="taskName">${resFilecheckOpinionItem.taskName}</td>
					    <td style="text-align: center" name="checkUsernames">${resFilecheckOpinionItem.checkUsernames}</td>
					    <td style="text-align: center" name="checkEnterprisenames">${resFilecheckOpinionItem.checkEnterprisenames}</td>
				 	  <td style="display: none" name="checkUserids">${resFilecheckOpinionItem.checkUserids}</td>
					    <td style="display: none" name="checkEnterpriseids">${resFilecheckOpinionItem.checkEnterpriseids}</td>
					  <!--   <td style="text-align: center" name="checkOpinionids">${resFilecheckOpinionItem.checkOpinionids}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link edit" >编辑</a>
					    </td>
					     -->
						<input type="hidden" name="taskId" value="${resFilecheckOpinionItem.taskId}"/>
						<input type="hidden" name="taskName" value="${resFilecheckOpinionItem.taskName}"/>
						<input type="hidden" name="checkUsernames" value="${resFilecheckOpinionItem.checkUsernames}"/>
						<input type="hidden" name="checkEnterprisenames" value="${resFilecheckOpinionItem.checkEnterprisenames}"/>
						<input type="hidden" name="checkUserids" value="${resFilecheckOpinionItem.checkUserids}"/>
						<input type="hidden" name="checkEnterpriseids" value="${resFilecheckOpinionItem.checkEnterpriseids}"/>
						<input type="hidden" name="checkOpinionids" value="${resFilecheckOpinionItem.checkOpinionids}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="taskId"></td>
			    	<td style="text-align: center" name="taskName"></td>
			    	<td style="text-align: center" name="checkUsernames"></td>
			    	<td style="text-align: center" name="checkEnterprisenames"></td>
	 		    	<td style="text-align: center" name="checkUserids"></td>
			    	<td style="text-align: center" name="checkEnterpriseids"></td>
		<!--	    	<td style="text-align: center" name="checkOpinionids"></td>		
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	 -->				
			    	<input type="hidden" name="taskId" value=""/>
			    	<input type="hidden" name="taskName" value=""/>
			    	<input type="hidden" name="checkUsernames" value=""/>
			    	<input type="hidden" name="checkEnterprisenames" value=""/>
			    	<input type="hidden" name="checkUserids" value=""/>
			    	<input type="hidden" name="checkEnterpriseids" value=""/>
			    	<input type="hidden" name="checkOpinionids" value=""/>

		 		</tr>
		    </table>

			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="resFilecheckDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">文件名: </th>
				<td><input type="text" name="fileName" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr style="display: none;">
				<th width="20%">文件路径: </th>
				<td><input type="text" name="fileUrl" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">文件描述: </th>
				<td><input type="text" name="fileComment" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">文件类型: </th>
				<td><input type="text" name="fileType" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr style="display: none;">
				<th width="20%">上传者ID: </th>
				<td><input type="text" name="uploadUserid" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr style="display: none;">
				<th width="20%">上传人: </th>
				<td><input type="text" name="uploadUsername" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr style="display: none;">
				<th width="20%">上传时间: </th>
				<td><input type="text" name="uploadTime" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr style="display: none;">
				<th width="20%">文件版本: </th>
				<td><input type="text" name="fileVersion" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr style="display: none;">
				<th width="20%">管理ID: </th>
				<td><input type="text" name="managementId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			
			<tr>
				<th width="20%">文件上传: </th>
				<td><a onclick="AttachMent.addFile1(this);"  href="javascript:;" class="link attachement">选择</a></td>
			</tr>
		</table>
	</form>
	<form id="resFilecheckOpinionForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr style="display: none;">
				<th width="20%">任务id: </th>
				<td><input type="text" name="taskId" value=""  class="inputText" validate="{required:false,maxlength:256 }"/></td>
			</tr>
			<tr>
				<th width="20%">任务名: </th>
				<td><input readonly="readonly" type="text" name="taskName" value=""  class="inputText" validate="{required:false,maxlength:256}"/></td>
			</tr>
			<tr style="display: none;">
				<th width="20%">审签人id: </th>
				<td>
				 <input type="text"  id="user_id" name="checkUserids" value=""  class="inputText" validate="{required:false,maxlength:256 }"/>
				</td>
			</tr>
			<tr>
				<th width="20%">审签人: </th>
			   
				<td><input readonly="readonly" type="text" id="user_name" name="checkUsernames"   value=""  class="inputText" validate="{required:false,maxlength:256}"/><a href="javascript:void(0)" onclick="chooseUsers(this)" class="link detail">选择</a></td>
			</tr>
			
		 	<tr style="display: none">
				<th width="20%">审签企业id: </th>
				<td>
				<input type="text" id="en_id"   name="checkEnterpriseids" value=""  class="inputText" validate="{required:false,maxlength:256 }"/>
				</td>
			</tr>
		    
		          <tr  class="enter"   id="enter_info">
					<th  "width="20%">审签企业: </th>
			    	<td><input readonly="readonly" type="text"  id="en_name" name="checkEnterprisenames" value=""  class="inputText" validate="{required:false,maxlength:256}"/><a href="javascript:void(0)" onclick="chooseEntprises(this);"  class="link detail">选择</a></td>
				  </tr>
				   
	<!-- 	<tr>
				<th width="20%">审签意见id: </th>
				<td></td>
			</tr>  -->	
	         <input type="hidden" name="checkOpinionids" value=""  class="inputText" validate="{required:false,maxlength:256 }"/>
			

		</table>
	</form>
</div>
</body>
</html>
