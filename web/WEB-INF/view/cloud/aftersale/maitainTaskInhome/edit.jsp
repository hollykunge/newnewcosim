<%@include file="/commons/cloud/checkCompany.jsp"%>
<%--
	time:2013-04-17 15:21:31
	desc:edit the 维修单
--%>
<%@page import="java.util.Date"%>
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
						window.location.href = "${ctx}/cloud/aftersale/maitainTaskInhome/edit.ht";
					}else{
					window.location.href = "${ctx}/cloud/aftersale/maitainTaskInhome/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		var add_supps_window;
        function preview(){
        //弹出供应商物品选择框
    		var urlShow = '${ctx}/cloud/config/material/selectMaterialTree.ht';
    		add_supps_window = $.ligerDialog.open({ url:urlShow, height: 480,width: 850, title :'物品选择器', name:"frameDialog_"});
		}
		
			//商圈列表回调函数
    	function _callBackMaterialTrees(names,codes,ids,units,prices,models ){
    	 
    		 
    		
				$("#prodname").val(names[0].trim());
				$("#prodcode").val(codes[0].trim());
				$("#prodmodel").val(models[0].trim());
    		add_supps_window.close();
    	}
		
		
          
        var dd;
      	function selSups(){
      		//弹出供应商物品选择框
      		var urlShow = '${ctx}/cloud/console/busiarea/listMyFriends.ht';
      		dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 800, title :'企业选择器', name:"frameDialog_"});
      	}
      	
      	var dd1;
      	function selRoleBySups(){
      		//弹出供应商物品选择框
      		var urlShow = '${ctx}/cloud/console/busiarea/selectRolesByFriends.ht';
      		dd1 = $.ligerDialog.open({ url:urlShow, height: 400,width: 800, title :'企业选择器', name:"frameDialog1_"});
      	}
      	function _callBackSelectedRoles(friends){
			var friend;
      		for(var i=0; i<friends.length; i++){
				friend=friends[i];
      		}
      		$("#accendantId").val(friend.roleId);
			$("#accendantName").val(friend.roleName);
      		$("#en_id").val(friend.companyId);
			$("#en_name").val(friend.companyName);
      		dd1.close();
      	}
      	//商圈列表回调函数
      	function _callBackMyFriends(friends){
      		var names='',ids='';
      		for(var i=0; i<friends.length; i++){
      			var friend = friends[i];
      			ids += ',' + friend[0];
      			names += ',' + friend[1];
      		}
      		ids = ids!=''?ids.substring(1):"";
      		names = names!=''?names.substring(1):"";
      		
      		$("#en_id").val(ids);
			$("#en_name").val(names);
      		dd.close();
      	}
          
		$(function(){
			$("#feedback_user").click(function(){
		        CommonDialog("feedBackList",
				function(data) {
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
		})
		
	
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${maitainTask.id !=null}">
			        <span class="tbar-label">编辑客户报修单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">填写客户报修单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存并发起</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
				<!-- <div class="group"><a class="link detail" id="feedback_user" href="javascript:void(0)"><font color="red">选择用户反馈</font></a></div> -->
				
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="taskMForm" method="post" action="save2.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			     	     	<tr>
			<td >
			        <input   type="hidden" id="enterpriseId" name="enterpriseId" value="${maitainTask.enterpriseId }" />
					<input   type="hidden" id="enterpriseName" name="enterpriseName" value="${maitainTask.enterpriseName }" />
					<input type="hidden" id="runId" name="runId" readonly class="r" value="${maitainTask.runId}"    />
					<input   type="hidden" id="flowtype" name="flowtype" value="上门维修服务"    />
					</td>
					</tr>
					<tr>
						<th style="width:120px;">任务单号</th>
						<td><input size="35" id="code" name="code" value="${maitainTask.code}"  class="inputText" validate="{required:true,maxlength:36}"></td>
						<th style="width:120px;">反馈用户</th>
						<td>
						<input size="35" type="hidden" id="user_id" readonly="readonly" name="feedbackId" value="${maitainTask.feedbackId}"   />
						<input   type="hidden" id="stype" name="stype" value="1"/>
						<input size="35" type="text" id="user_name" readonly="readonly" name="feedbackName" class="r" value="${maitainTask.feedbackName}"   class="inputText" validate="{required:true,maxlength:96}"/>
					</tr>
					<tr>
						<th style="width:120px;">产品名称</th>
						<td><input size="35" id="prodname"  readonly="readonly" name="prodname" value="${maitainTask.prodname}"  class="r" validate="{required:false,maxlength:96}"/></td>
						<th style="width:120px;">产品编码</th>
						<td><input size="35" id="prodcode" readonly="readonly" name="prodcode" value="${maitainTask.prodcode}"  class="r" validate="{required:true,maxlength:96}"/>
						<a href="javascript:void(0)" onclick="preview()"  class="link detail">请选择</a>
						</td>
					</tr>				
					<tr>
						<th style="width:120px;">产品型号</th>
						<td><input size="35" id="prodmodel" readonly="readonly" name="prodmodel" value="${maitainTask.prodmodel}"  class="r" validate="{required:false,maxlength:96}" /></td>
						<th>维修日期</th>
						<td><input size="35" type="text" id="tdate" name="tdate" value="<fmt:formatDate value='<%=new Date() %>' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}"></td>
					</tr>
					<tr>
						<th>维修厂商</th>
						<td>
						<input size="35" type="hidden" id="en_id" name="purenterpId" value="${maitainTask.purenterpId}"    />
						<input size="35" type="text" id="en_name" name="purenterName" value="${maitainTask.purenterName}"  readonly="readonly" class="inputText" validate="{required:true,maxlength:300}"  />
						<a href="javascript:void(0)" onclick="selSups()"  class="link detail">选择</a>
						<!--<a href="javascript:void(0)" onclick="selRoleBySups()"  class="link detail">选择</a>-->
						</td>
						<!--<th>维修人员</th>
						<td>
						<input type="hidden" size="35" id="accendantId" name="accendantId" value="${maitainTask.accendantId}"/>
						<input size="35" type="text" id="accendantName" name="accendantName" value="${maitainTask.accendantName}"  readonly="readonly" class="inputText" validate="{required:true,maxlength:300}"  /></td>
						-->
					</tr>				
					<tr>
						<th>问题描述</th>
						<td colspan="3"><textarea  id="descn" cols="95" rows="5" name="descn"  class="inputText" validate="{required:false,maxlength:96}">${maitainTask.descn}</textarea></td>
					</tr>
					<tr>
						<th>备注</th><td colspan="3">
						<textarea id="remark" cols="95" rows="5" name="remark"  class="inputText" validate="{maxlength:768}">${maitainTask.remark}</textarea></td>
					</tr>
			</table>
				<input size="35" type="hidden" name="id" value="${maitainTask.id}" />
				<input size="35" type="hidden" id="back" name="back" value=""/>
				<input size="35" type="hidden" name="formData" id="formData" />
				<input size="35" type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
				<input size="35" type="hidden" id="taskId" name="taskId" value="${taskId}"/>
				
		</form>
		
	</div>
</div>
</body>
</html>
