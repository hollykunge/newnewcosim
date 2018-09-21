<%--
	time:2013-04-16 17:21:17
	desc:edit the cloud_business_chance
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_business_chance</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	
	<script type="text/javascript">
		$(function() {
			
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#businessChanceForm').form();
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
						var t =$("#type").val();
					//	alert(t)
						if(t=="采购商机"){
						window.location.href = "${ctx}/cloud/config/business/purchaseList.ht";
						}else if(t=="营销商机"){
							window.location.href = "${ctx}/cloud/config/business/marketingList.ht";
						}else if(t=="生产商机"){
							window.location.href = "${ctx}/cloud/config/business/produceList.ht";
						}else if(t=="服务商机"){
							window.location.href = "${ctx}/cloud/config/business/serveList.ht";
						}else if(t=="研发商机"){
							window.location.href = "${ctx}/cloud/config/business/developmentList.ht";
						}
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
	 

		function isture(){
			
			var id = $("#id").val();
			if(id!=null&&id!=""){
				var src = $("#image").val();
				 var item = $('<img src="'+src+'" width="80" height="84" />');
				$("#picView").append(item);
				 
			}
		}
		
		function change_state(a){
			if(a==1){
				$("#publishState").val("审核通过");
			}else {
				$("#publishState").val("审核未通过");
			}
			
		}

	</script>
</head>
<body  onload="isture();">
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${businessChance.id !=null}">
			        <span class="tbar-label">编辑商机</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加商机</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group">
			 
			   
			      <a class="link back" href="getAllList.ht">返回</a>
			     
			  		   
		  
				
				
				</div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="businessChanceForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			<tr>
					<th width="20%">商机名称:  <span class="required">*</span></th>
					<td><input type="text" id="name" name="name" value="${businessChance.name}"   readonly="readonly"  class="inputText" validate="{required:true,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">商机内容:  <span class="required">*</span></th>
					<td><textarea id="content" name="content"   class="inputText" readonly="readonly"  validate="{required:true,maxlength:384}"  >${businessChance.content} </textarea></td>
					
				</tr>
				<tr>
					<th width="20%">开始时间:  <span class="required">*</span></th>
					<td><input type="text" id="startTime" name="startTime" readonly="readonly"  value="<fmt:formatDate value='${businessChance.startTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true,required:true}" /></td>
				</tr>
				<tr>
					<th width="20%">结束时间:  <span class="required">*</span></th>
					<td><input type="text" id="endTime" name="endTime" readonly="readonly"  value="<fmt:formatDate value='${businessChance.endTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true,required:true}" /></td>
				</tr>
				<tr>
					<th width="20%">商机类型:  <span class="required">*</span></th>
					<td>
					   <input type="text" id="type" name="type" value="${businessChance.type}" readonly="readonly"  class="inputText" validate="{required:true,maxlength:384}"  /></td>
					     
														</td>
				</tr>
				<tr>
					<th width="20%">图片: </th>
					       <td>
					       <input id="image" name="image" type="hidden" value="${businessChance.image}">
					       <img src="${ctx}${businessChance.image}" onError="this.src='${ctx}${businessChance.image}';" width="80" height="84" />
								</td> 

				</tr>
				<tr>
		            	<c:if test="${businessChance.type=='采购商机'}">
		            		<th width="20%">采购数量: </th>
		            	</c:if>
		               <c:if test="${businessChance.type=='营销商机'}">
		            		 <th width="20%">代理区域: </th>
		            	</c:if>
		            	<c:if test="${businessChance.type=='生产商机'}">
		            		  <th width="20%">生产要求: </th>
		            	</c:if>
		            	<c:if test="${businessChance.type=='服务商机'}">
		            		 <th width="20%">服务区域: </th>
		            	</c:if>
		            	<c:if test="${businessChance.type=='研发商机'}">
		            		<th width="20%">研发要求: </th> 
		            	</c:if>
		            	 
		            	<td> <input type="text" id="properties" name="properties" readonly="readonly"  value="${businessChance.properties}"  class="inputText" validate="{required:true,maxlength:384}"  /></td>
		        </tr>
	
		        <tr>
					 <th width="20%">审核状态:  <span class="required">*</span></th>
					<td>
					<input type="hidden" id="publishState" name="publishState"  />
				                 
														<input type="radio" name="a" onclick="change_state(this.value)" class="radio" value="1"  />审核通过
													
														<input type="radio" name="a" class="radio" onclick="change_state(this.value)" value="2" />审核未通过
					
					</td>
				</tr>
		
			</table>
			
			<input type="hidden" id="operateTime" name="operateTime" value="<fmt:formatDate value='${businessChance.operateTime}' pattern='yyyy-MM-dd'/>" class="inputText date"  />
			<input type="hidden" id="id" name="id" value="${businessChance.id}" />
			<input type="hidden" id="userId" name="userId" value="${businessChance.userId}"  class="inputText"   />
			<input type="hidden" id="companyId" name="companyId" value="${businessChance.companyId}"  class="inputText"  />
		</form>
		
	</div>
</div>
</body>
</html>
