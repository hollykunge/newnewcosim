<%--
	time:2013-06-19 12:09:32
	desc:edit the cloud_service_task
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑现场技术服务单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#maitainTaskForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
		  function userview(){
			CommonDialog("feedBackList",
			function(data) {
				$("#ac_id").val(data.id);
				$("#ac_name").val(data.fdman);
				
			});
				
		  }
          
		$(function(){
			
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
			
		})
		
		
// 		function showResponse(responseText) {
// 			var obj = new com.hotent.form.ResultMessage(responseText);
// 			if (obj.isSuccess()) {
// 				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
// 					if(rtn){
// 						window.location.href = "${ctx}/cloud/aftersale/maitainTask/edit.ht";
// 					}else{
// 						window.location.href = "${ctx}/cloud/aftersale/maitainTask/list.ht";
// 					}
// 				});
// 			} else {
// 				$.ligerMessageBox.error("提示信息",obj.getMessage());
// 			}
// 		}
		
		
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
			
			CommonDialog("materialListOnly",
			function(data) {
				$("#prod_name").val(data.name);
				$("#prod_code").val(data.code);
				$("#prod_model").val(data.model);
			});
			
		}
		
      	
      	function preview_ware(obj){
      		CommonDialog("ware_task_details",
      			function(data){
      				$(obj).closest("form").find('input[name="prodname"]').val(data.productname);
      				$(obj).closest("form").find('input[name="prodcode"]').val(data.productcode);
      				$(obj).closest("form").find('input[name="specifications"]').val(data.specifications);
      				$(obj).closest("form").find('input[name="nums_max"]').val(data.nums);
      				$(obj).closest("form").find('input[name="units"]').val(data.units);
      				

      			/*$(":input[name='prodname']").val(data.productname);
      			$(":input[name='prodcode']").val(data.productcode);
      			$(":input[name='specifications']").val(data.specifications);
      			$(":input[name='nums']").val(data.nums);
      			$(":input[name='units']").val(data.units);
      			*/
      		});
      		
      	}
      	
      	
		function isChecked(){
			var val=$('input:radio[name="isEnsure"]:checked').val();
			if(val == '否'){
				$("#div_show").show();
				
			}else if(val=='是'){
				$("#div_show").hide();
			}
			
		}
		$(function(){
			var seq =1;
			
			$("#btnAdd").click(function(){
				$(":input[name='seq']").val(seq++);
			});
			
		});
		
		
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${maitainTask.id !=null}">
			        <span class="tbar-label">编辑现场技术服务单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加现场技术服务单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
		</div>
	</div>
	
	
	
	<div class="panel-body">
		<form id="maitainTaskForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			       	<tr>
					<td >
			        <input   type="hidden" id="enterpriseId" name="enterpriseId" value="${maitainTask.enterpriseId }" />
					<input   type="hidden" id="enterpriseName" name="enterpriseName" value="${maitainTask.enterpriseName }" />
					<input	 type="hidden" id="runId" name="runId" readonly class="r" value="${maitainTask.runId}"    />
					<input   type="hidden" id="flowtype" name="flowtype" value="现场技术服务"    />
					</td>
					</tr>
					<tr>
						<th style="width:120px;">任务单号</th>
						<td ><input size="35" id="code" name="code" value="${maitainTask.code}"  class="r" readonly="readonly" validate="{required:true,maxlength:36}"></td>
						<th style="width:120px;">是否过保</th>
						<td >
							<c:remove var="checked"/> 
							<c:if test="${maitainTask.isEnsure=='是'}"> 
								<c:set var="checked" value='checked="checked"'></c:set> 
							</c:if>	
							<input type="radio" name="isEnsure" ${checked} value="是" onclick="isChecked();" >是 
							<c:remove var="checked"/> 
							<c:if test="${maitainTask.isEnsure=='否'}"> 
								<c:set var="checked" value='checked="checked"'></c:set> 
							</c:if>	
							<input type="radio" name="isEnsure" ${checked} value="否" onclick="isChecked();">否 
						</td>
						
					</tr>
					<tr>
						<th style="width:120px;">反馈用户</th>
						<td><input size="35" type="hidden" id="user_id" name="feedbackId" value="${maitainTask.feedbackId}"   />
						<input   type="hidden" id="stype" name="stype" value="0"/>
						<input size="35" type="text" id="user_name" name="feedbackName" class="r" readonly="readonly" value="${maitainTask.feedbackName}"   class="r" readonly="readonly" validate="{required:true,maxlength:96}"/></td>
						<th style="width:120px;">物品编码</th>
						<td><input size="35" id="prod_code" name="prodcode" value="${maitainTask.prodcode}"  class="r" readonly="readonly" validate="{required:true,maxlength:96}"/>
						<!--<a href="javascript:void(0)" onclick="preview()" class="link detail">请选择</a>-->
						</td>
					</tr>				
					<tr>
						<th style="width:120px;">物品名称</th>
						<td><input size="35" id="prod_name" name="prodname" value="${maitainTask.prodname}"  class="r" readonly="readonly" validate="{required:true,maxlength:96}"/>
						</td>
						<th style="width:120px;">物品型号</th>
						<td><input size="35" id="prod_model" name="prodmodel" value="${maitainTask.prodmodel}"  class="r" readonly="readonly" validate="{required:true,maxlength:96}" /></td>
					</tr>				
					
					<tr>
						<th style="width:120px;">维修日期</th>
						<td><input size="35" type="text" id="tdate" name="tdate" value="<fmt:formatDate value='${maitainTask.tdate }' pattern='yyyy-MM-dd'/>" class="r" readonly="readonly" validate="{date:true}"></td>
						<th>维修人员</th><td >
						<span style="display: none;" id="div_show">
			            <input size="35" type="hidden" id="ac_id" name="accendantId"  />
						<input size="35" type="text" id="ac_name"  name="accendantName"  value="${maitainTask.accendantName}"  readonly="readonly" class="r" readonly="readonly" />
						<a href="javascript:void(0)" onclick="selUsers()"  class="link detail">选择</a>
						</span>
						</td>
						
					</tr>
					
						<tr>
						<th style="width:120px;">客户反映时间</th>
						<td><input size="35" type="text" id="adate" name="adate" value="<fmt:formatDate value='${maitainTask.adate }' pattern='yyyy-MM-dd'/>" class="r" readonly="readonly" validate="{date:true}"></td>
						<th>预计维修时间</th><td >
						<input size="35" id="ptate" name="ptate" value="${maitainTask.ptate}"  class="r" readonly="readonly" validate="{required:false,maxlength:16}"/>
						</td>
						
					</tr>
					<tr>
						<th>问题描述</th>
						<td colspan="3"><textarea  id="descn" cols="95" rows="5" name="descn"  class="r" readonly="readonly" validate="{required:false,maxlength:96}">${maitainTask.descn}</textarea></td>
					</tr>
					<tr>
						<th>备注</th><td colspan="3">
						<textarea id="remark" cols="95" rows="5" name="remark"  class="r" readonly="readonly" validate="{maxlength:768}">${maitainTask.remark}</textarea></td>
					</tr>
					
					<!-- <tr><td>质检意见</td><td colspan="3"><textarea class="area"></textarea></td></tr>
					<tr><td>售后主管意见</td><td colspan="3"><textarea class="area"></textarea></td></tr> -->
					
			</table>
		
			
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="maitainTaskDetail" formType="window" type="sub">
				<tr>
					<td colspan="13">
					 
			    		<div align="center">
						售后服务领料单
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>序号</th>
				<th>物品编码</th>
				<th>物品名称</th>
				<th>物品规格</th>
				<th>单位</th>
				<th>数量</th>
				<th>单价</th>
				<th>总价</th>
				<th>维修项目</th>
				<th>领料人</th>
				</tr>
				<c:forEach items="${maitainTaskDetailList}" var="maitainTaskDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="seq">${maitainTaskDetailItem.seq}</td>
					    <td style="text-align: center" name="prodcode">${maitainTaskDetailItem.prodcode}</td>
					    <td style="text-align: center" name="prodname">${maitainTaskDetailItem.prodname}</td>
					    <td style="text-align: center" name="specifications">${maitainTaskDetailItem.specifications}</td>
					    <td style="text-align: center" name="units">${maitainTaskDetailItem.units}</td>
					    <td style="text-align: center" name="nums">${maitainTaskDetailItem.nums}</td>
					    <td style="text-align: center" name="price">${maitainTaskDetailItem.price}</td>
					     <td style="text-align: center" name="sumprice">${maitainTaskDetailItem.sumprice}</td>
					    <td style="text-align: center" name="taskProject">${maitainTaskDetailItem.taskProject}</td>
					    <%-- <td style="text-align: center" name="client">${maitainTaskDetailItem.client}</td>
					    <td style="text-align: center" name="cashCode">${maitainTaskDetailItem.cashCode}</td>
					    <td style="text-align: center" name="serCode">${maitainTaskDetailItem.serCode}</td>
					    <td style="text-align: center" name="addper">${maitainTaskDetailItem.addper}</td> --%>
					    <td style="text-align: center" name="getper">${maitainTaskDetailItem.getper}</td>
					    
						<input type="hidden" name="seq" value="${maitainTaskDetailItem.seq}"/>
						<input type="hidden" name="prodcode" value="${maitainTaskDetailItem.prodcode}"/>
						<input type="hidden" name="prodname" value="${maitainTaskDetailItem.prodname}"/>
						<input type="hidden" name="specifications" value="${maitainTaskDetailItem.specifications}"/>
						<input type="hidden" name="units" value="${maitainTaskDetailItem.units}"/>
						<input type="hidden" name="nums" value="${maitainTaskDetailItem.nums}"/>
						<input type="hidden" name="price" value="${maitainTaskDetailItem.price}"/>
						<input type="hidden" name="sumprice" value="${maitainTaskDetailItem.sumprice}"/>
						<input type="hidden" name="taskProject" value="${maitainTaskDetailItem.taskProject}"/>
						<%-- <input type="hidden" name="client" value="${maitainTaskDetailItem.client}"/>
						<input type="hidden" name="cashCode" value="${maitainTaskDetailItem.cashCode}"/>
						<input type="hidden" name="serCode" value="${maitainTaskDetailItem.serCode}"/>
						<input type="hidden" name="addper" value="${maitainTaskDetailItem.addper}"/> --%>
						<input type="hidden" name="getper" value="${maitainTaskDetailItem.getper}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    		<td style="text-align: center" name="seq"></td>
			    	<td style="text-align: center" name="prodcode"></td>
			    	<td style="text-align: center" name="prodname"></td>
			    	<td style="text-align: center" name="specifications"></td>
			    	<td style="text-align: center" name="units"></td>
			    	<td style="text-align: center" name="nums"></td>
			    	<td style="text-align: center" name="price"></td>
			    	 	<td style="text-align: center" name="sumprice"></td>
			    	<td style="text-align: center" name="taskProject"></td>
			    	<!-- <td style="text-align: center" name="client"></td>
			    	<td style="text-align: center" name="cashCode"></td>
			    	<td style="text-align: center" name="serCode"></td>
			    	<td style="text-align: center" name="addper"></td> -->
			    	<td style="text-align: center" name="getper"></td>
			     
			    	<input type="hidden" name="seq" value=""/>
			    	<input type="hidden" name="prodcode" value=""/>
			    	<input type="hidden" name="prodname" value=""/>
			    	<input type="hidden" name="specifications" value=""/>
			    	<input type="hidden" name="units" value=""/>
			    	<input type="hidden" name="nums" value=""/>
			    	<input type="hidden" name="price" value=""/>
			    	<input type="hidden" name="sumprice" value=""/>
			    	<input type="hidden" name="taskProject" value=""/>
			    	<!-- <input type="hidden" name="client" value=""/>
			    	<input type="hidden" name="cashCode" value=""/>
			    	<input type="hidden" name="serCode" value=""/>
			    	<input type="hidden" name="addper" value=""/> -->
			    	<input type="hidden" name="getper" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${maitainTask.id}" />
		</form>
		
	</div>
	<form id="maitainTaskDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">序号: </th>
				<td><input type="text" name="seq" value=""  class="r" readonly="readonly" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">物品名称: </th>
				<td><input type="text" name="prodname" value=""  class="r" readonly="readonly" validate="{required:false,maxlength:108}"/>
				<a href="javascript:void(0)" onclick="preview_ware(this);" class="link detail">请选择</a>
				</td>
				
			</tr>
			<tr>
				<th width="20%">物品编码: </th>
				<td><input type="text" name="prodcode" value=""  class="r" readonly="readonly" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">物品规格: </th>
				<td><input type="text" name="specifications" value=""  class="r" readonly="readonly" validate	="{required:false,maxlength:144}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="units" value=""  class="r" readonly="readonly" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">数量: </th>
				<td><input type="text" name="nums" value=""  class="r" readonly="readonly" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">维修项目: </th>
				<td><input type="text" name="taskProject" value=""  class="r" readonly="readonly" validate="{required:false,maxlength:144}"/></td>
			</tr>
			<tr>
				<th width="20%">客户: </th>
				<td><input type="text" name="client" value=""  class="r" readonly="readonly" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">现金单号: </th>
				<td><input type="text" name="cashCode" value=""  class="r" readonly="readonly" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">维修单号: </th>
				<td><input type="text" name="serCode" value=""  class="r" readonly="readonly" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">发料人: </th>
				<td><input type="text" name="addper" value=""  class="r" readonly="readonly" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">领料人: </th>
				<td><input type="text" name="getper" value=""  class="r" readonly="readonly" validate="{required:false,maxlength:108}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
