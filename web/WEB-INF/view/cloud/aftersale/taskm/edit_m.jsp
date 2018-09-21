<%@include file="/commons/cloud/checkCompany.jsp"%>
<%--
	time:2013-04-17 15:21:31
	desc:edit the 维修单
--%>
<%@page import="com.hotent.core.util.ContextUtil"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 维修单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#taskMForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
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
						window.location.href = "${ctx}/cloud/aftersale/taskm/list_m.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
        function userview(){
			CommonDialog("feedBackList",
			function(data) {
				$("#ac_id").val(data.id);
				$("#ac_name").val(data.fdman);
				
			});
				
		  }
          
		/* $(function(){
			
			$("#feedback_user").click(function(){
				
			        CommonDialog("feedBackList",
					function(data) {
			        	alert(data.id);
						$("#user_id").val(data.id);
						$("#user_name").val(data.fdman);
					$.ajax({
						type: "POST",
					    url:  "${ctx}/cloud/aftersale/feedback/getFeedbackByUserId.ht",
					    data: {"fdman":data.fdman},
				         success: function(returndata){
				           var feedback=returndata.feedback;
				           $("#prodcode").val(feedback.prodcode);
				           $("#prodname").val(feedback.prodname);
				           $("#prodmodel").val(feedback.prodname);
				           $("#descn").val(feedback.descn);
				         }
				         
				      }); 
			        }); 
			});
			
		}) */
		
			var add_supps;
    	function add_supps(){
    		//弹出供应商物品选择框
    		var urlShow = '${ctx}/cloud/config/material/myMaterialTree.ht';
    		add_supps = $.ligerDialog.open({ url:urlShow, height: 400,width: 700, title :'物品选择器', name:"frameDialog_"});
    	}	
    	
    	//商圈列表回调函数
    	function _callBackMyFriends(friend ){
    			$("#prodcode").val(friend[0]); 
    			$("#prodname").val(friend[1]); 
    		add_supps.close();
    	}
		
		var dd;
      	function selUsers(){
      		//弹出企业用户选择器
      		var urlShow = '${ctx}/cloud/console/busiarea/listUsersByCompAndRole.ht?role=aftersaleman';
      		dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 600, title :'人员选择器', name:"frameDialog_"});
      	}
      	
      	//商圈列表回调函数
      	function _callBackUsers(users){
      		var names='',ids='';
      		for(var i=0; i<users.length; i++){
      			var user = users[i];
      			ids += ',' + user[0];
      			names += ',' + user[1];
      		}
      		ids = ids!=''?ids.substring(1):"";
      		names = names!=''?names.substring(1):"";
      		
      		$("#ac_id").val(ids);
			$("#ac_name").val(names);
      		dd.close();
      	}
      	
      	function preview(){
			
			CommonDialog("prod_list",
			function(data) {
				$("#prodname").val(data.name);
				//$(":input[name='unit']").val(data.unit);
				//$(":input[name='price']").val(data.price);
				$("#prodcode").val(data.code);
				//$("#prodmodel").val(data.code);
			});
			
		}
      	
      	
      	
      
    	
    	
   
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${taskM.id !=null}">
			        <span class="tbar-label">编辑维修任务单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">填写维修任务单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存并发起</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list_m.ht">返回</a></div>
				<!--
				<div class="group"><a class="link detail" id="feedback_user" href="javascript:void(0)"><font color="red">选择用户反馈</font></a></div>
				-->
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="taskMForm" method="post" action="save_m.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			        <input size="35" type="hidden" id="enterprise_id" name="enterprise_id" value="${taskM.enterprise_id }" />
					<input size="35" type="hidden" id="enterprise_name" name="enterprise_name" value="${taskM.enterprise_name }" />
					<tr>
						<th style="width:120px;">任务单号</th>
						<td colspan="3"><input size="35" id="code" name="code" value="${taskM.code}"  class="inputText" validate="{required:true,maxlength:36}"></td>
					</tr>
					<tr>
						<th style="width:120px;">反馈用户</th>
						<td><input size="35" type="hidden" id="user_id" name="feedback_id" value="${taskM.feedback_id}"   />
						<input size="35" type="text" id="user_name" name="feedback_name" class="inputText" value="${taskM.feedback_name}"   class="inputText" validate="{required:true,maxlength:96}"/></td>
						<th style="width:120px;">产品编码</th>
						<td><input size="35" id="prodcode" name="prodcode" value="${taskM.prodcode}"  class="inputText" validate="{required:true,maxlength:96}"/>
						<a href="javascript:void(0)" onclick="add_supps()" class="link detail">请选择</a>
						</td>
					</tr>				
					<tr>
						<th style="width:120px;">产品名称</th>
						<td><input size="35" id="prodname" name="prodname" value="${taskM.prodname}"  class="inputText" validate="{required:true,maxlength:96}"/>
						</td>
						<th style="width:120px;">产品型号</th>
						<td><input size="35" id="prodmodel" name="prodmodel" value="${taskM.prodmodel}"  class="inputText" validate="{required:true,maxlength:96}" /></td>
					</tr>				
					<tr>
						<th>问题描述</th>
						<td colspan="3"><textarea  id="descn" cols="95" rows="5" name="descn"  class="inputText" validate="{required:false,maxlength:96}">${taskM.descn}</textarea></td>
					</tr>
					<tr>
						<th>维修人员</th><td >
			            <input size="35" type="hidden" id="ac_id" name="accendant_id"  />
						<input size="35" type="text" id="ac_name"  name="accendant_name"  value="${taskM.accendant_name}"  readonly="readonly" class="inputText" validate="{required:true,maxlength:96}"  />
						<a href="javascript:void(0)" onclick="selUsers()"  class="link detail">选择</a>
						</td>
						<th style="width:120px;">维修日期</th>
						<td><input size="35" type="text" id="tdate" name="tdate" value="<fmt:formatDate value='${taskM.tdate }' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}"></td>
					</tr>
					<tr>
						<th>备注</th><td colspan="3">
						<textarea id="remark" cols="95" rows="5" name="remark"  class="inputText" validate="{maxlength:768}">${taskM.remark}</textarea></td>
					</tr>
					
					<!-- <tr><td>质检意见</td><td colspan="3"><textarea class="area"></textarea></td></tr>
					<tr><td>售后主管意见</td><td colspan="3"><textarea class="area"></textarea></td></tr> -->
					
			</table>
				<input size="35" type="hidden" name="id" value="${taskM.id}" />
				<input size="35" type="hidden" id="back" name="back" value=""/>
				<input size="35" type="hidden" name="formData" id="formData" />
				<input size="35" type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
				<input size="35" type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
</div>
</body>
</html>
