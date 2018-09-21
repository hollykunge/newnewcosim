<%--
	time:2013-06-19 12:09:32
	desc:edit the cloud_service_task
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>编辑 cloud_service_task</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
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
		
// 		function showResponse(responseText) {
// 			var obj = new com.hotent.form.ResultMessage(responseText);
// 			if (obj.isSuccess()) {
// 				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
// 					if(rtn){
// 						this.close();
// 					}else{
// 						window.location.href = "${ctx}/cloud/aftersale/maitainTask/list.ht";
// 					}
// 				});
// 			} else {
// 				$.ligerMessageBox.error("提示信息",obj.getMessage());
// 			}
// 		}
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top" style="display:none">
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
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存并发起</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="maitainTaskForm" method="post" action="complete.ht">
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
						<td colspan="3"><input size="35" readonly class="r" id="code" name="code" value="${maitainTask.code}"  class="inputText" validate="{required:true,maxlength:36}"></td>
					</tr>
					<tr>
						<th style="width:120px;">反馈用户</th>
						<td><input size="35" type="hidden"  readonly class="r" id="user_id" name="feedbackId" value="${maitainTask.feedbackId}"   />
						<input   type="hidden" id="stype" name="stype" value="1"/>
						<input size="35" type="text" id="user_name" class="r" readonly name="feedbackName" class="inputText" value="${maitainTask.feedbackName}"   class="inputText" validate="{required:true,maxlength:96}"/></td>
						<th style="width:120px;">产品编码</th>
						<td><input size="35" id="prodcode" readonly class="r" name="prodcode" value="${maitainTask.prodcode}"  class="inputText" validate="{required:true,maxlength:96}"/>
<!-- 						<a href="javascript:void(0)" onclick="preview()" class="link detail" ">请选择</a> -->
						</td>
					</tr>				
					<tr>
						<th style="width:120px;">产品名称</th>
						<td><input size="35" id="prodname" readonly class="r" name="prodname" value="${maitainTask.prodname}"  class="inputText" validate="{required:true,maxlength:96}"/>
						</td>
						<th style="width:120px;">产品型号</th>
						<td><input size="35" id="prodmodel" readonly class="r" name="prodmodel" value="${maitainTask.prodmodel}"  class="inputText" validate="{required:true,maxlength:96}" /></td>
					</tr>				
					
			 		
					<tr>
						<th style="width:120px;">维修日期</th>
						<td><input size="35" type="text" id="tdate" readonly class="r" name="tdate" value="<fmt:formatDate value='${maitainTask.tdate }' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}"></td>
						<th>维修人员</th><td >
						<!-- 
			            <input size="35" type="hidden" id="ac_id" name="accendantId"  />
						<input size="35" type="text" id="ac_name"  name="accendantName"  value="${maitainTask.accendantName}"  readonly="readonly" class="inputText" validate="{required:true,maxlength:96}"  />
						<a href="javascript:void(0)" onclick="selUsers()"  class="link detail">选择</a> -->
						</td>
						
					</tr>
					<tr>
						<th style="width:120px;">客户反映时间</th>
						<td><input size="35" type="text" id="adate" readonly class="r" name="adate" value="<fmt:formatDate value='${maitainTask.adate }' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}"></td>
						<th>预计维修时间</th><td >
						<input size="35" id="ptate" name="ptate" value="${maitainTask.ptate}"  class="inputText" validate="{required:false,maxlength:16}"/>
						</td>
						
					</tr>
					<tr>
						<th style="width:120px;">报价</th>
						<td>
						<input size="35" id="task_price" readonly class="r" name="taskPrice" value="${maitainTask.taskPrice}"  class="inputText" validate="{required:true,maxlength:96}" />
						
						</td>
						<th style="width:120px;">客户</th>
						<td>
						<input type="hidden" size="35" id="task_empid" name="taskEmpid" value="${maitainTask.taskEmpid}"  class="inputText" validate="{required:true,maxlength:96}" />
						<input type="text" size="35" id="task_empname" readonly class="r" name="taskEmpname" value="${maitainTask.taskEmpname}"  class="inputText" validate="{required:true,maxlength:96}" />
						<input type="hidden" name="id" value="${maitainTask.id}" />
						</td>
					</tr>	
					<tr>
						<th>维修厂商</th>
						<td>
						<input size="35" type="hidden"  name="purenterpId" value="${maitainTask.purenterpId}"    />
						<input size="35" type="text"   name="purenterName" value="${maitainTask.purenterName}"  readonly="readonly" class="r" validate="{required:true,maxlength:300}"  />
						</td>
					</tr>
					<tr>
						<th>问题描述</th>
						<td colspan="3">
						<textarea  id="descn" readonly class="r" cols="95" rows="5" name="descn"  class="inputText" validate="{required:false,maxlength:96}">${maitainTask.descn}</textarea>
						</td>
						
					</tr>	
					<tr>
						<th>备注</th><td colspan="3">
						<textarea id="remark" cols="95" readonly class="r" rows="5" name="remark"  class="inputText" validate="{maxlength:768}">${maitainTask.remark}</textarea></td>
					</tr>
					
					<!-- <tr><td>质检意见</td><td colspan="3"><textarea class="area"></textarea></td></tr>
					<tr><td>售后主管意见</td><td colspan="3"><textarea class="area"></textarea></td></tr> -->
					
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="maitainTaskDetail" formType="window" type="sub">
				<tr>
					<td colspan="13" style="display:none">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						售后服务领料单
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>序号</th>
				<th>产品编码</th>
				<th>产品名称</th>
				<th>产品规格</th>
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
			
		</form>
		
	</div>
	<form id="maitainTaskDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">序号: </th>
				<td><input type="text" name="seq" value=""  class="inputText" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">产品编码: </th>
				<td><input type="text" name="prodcode" value=""  class="inputText" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">产品名称: </th>
				<td><input type="text" name="prodname" value=""  class="inputText" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">产品规格: </th>
				<td><input type="text" name="specifications" value=""  class="inputText" validate="{required:false,maxlength:144}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="units" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">数量: </th>
				<td><input type="text" name="nums" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">维修项目: </th>
				<td><input type="text" name="taskProject" value=""  class="inputText" validate="{required:false,maxlength:144}"/></td>
			</tr>
			<tr>
				<th width="20%">客户: </th>
				<td><input type="text" name="client" value=""  class="inputText" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">现金单号: </th>
				<td><input type="text" name="cashCode" value=""  class="inputText" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">维修单号: </th>
				<td><input type="text" name="serCode" value=""  class="inputText" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">发料人: </th>
				<td><input type="text" name="addper" value=""  class="inputText" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th width="20%">领料人: </th>
				<td><input type="text" name="getper" value=""  class="inputText" validate="{required:false,maxlength:108}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
