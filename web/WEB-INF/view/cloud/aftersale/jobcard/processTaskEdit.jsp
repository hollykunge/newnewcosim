<%--
	time:2013-04-19 11:40:23
	desc:edit the 维修工作单
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑维修工作单</title>
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
			var frm=$('#jobCardForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
		
		function fo_price(){
      		var p = $("#manmoney").val();
      		if(p == null || p == ""){
      			var price = 0 - 0;
      			$("#manmoney").val(price.toFixed(2));
      		}else{
      			var price = parseFloat(p);
      			$("#manmoney").val(price.toFixed(2));
      		}
      	}	
      	
		
		function price_foalt(obj){
      				var p=$(obj).closest("tr").find('input[name="price"]').val();
      				var price = parseFloat(p);
      				if(p == null || p == ""){
      					p = 0 - 0;
      					$(obj).closest("tr").find('input[name="price"]').val(p.toFixed(2));
      				}else{
      					$(obj).closest("tr").find('input[name="price"]').val(price.toFixed(2));
      				}
      				
					
      			}
		
		  $(function(){
				var seq=1;
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
			    <c:when test="${jobCard.id !=null}">
			        <span class="tbar-label">编辑维修工作单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加维修工作单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="${ctx}/cloud/aftersale/maitainTask/get_m.ht?id=${taskM.id}" target="_blank">查看维修单</a></div>
				<!-- <div class="group">
					<a class="link close" href="javascript:window.close();">关闭</a>
				</div> -->
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="jobCardForm" method="post" action="complete.ht">	
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				  <input size="35" type="hidden" id="enterprise_id" name="enterprise_id" value="${jobCard.enterprise_id }" />
					<input size="35" type="hidden" id="enterprise_name" name="enterprise_name" value="${jobCard.enterprise_name }" />
				<tr>
					<th style="width:120px">任务编码:  <span class="required"></span></th>
					<td ><input size="35" type="text" id="taskid"  readonly="readonly" name="taskid" value="${taskM.id}" class="r" validate="{required:true,maxlength:96}" /></td>
					<th style="width:120px">维修人员:  <span class="required"></span></th>
					<td><input size="35" type="text"  readonly="readonly" id="man" name="man" value="${jobCard.man}"  class="r" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<%--<input size="35" type="text" id="prodcode" name="prodcode" value="${taskM.prodcode}"  class="inputText" validate="{required:true,maxlength:96}"  />
				<input size="35" type="text" id="prodname" name="prodname" value="${taskM.prodname}"  class="inputText" validate="{required:true,maxlength:96}"  />
				<input size="35" type="text" id="prodmodel" name="prodmodel" value="${taskM.prodmodel}"  class="inputText" validate="{required:true,maxlength:96}"  />
				--%>
				<%-- <tr>
					<th style="width:120px">产品编码:  <span class="required"></span></th>
					<td><input size="35" type="text" id="prodcode" name="prodcode" value="${taskM.prodcode}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<th style="width:120px">产品名称:  <span class="required"></span></th>
					<td><input size="35" type="text" id="prodname" name="prodname" value="${taskM.prodname}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">产品型号:  <span class="required"></span></th>
					<td><input size="35" type="text" id="prodmodel" name="prodmodel" value="${taskM.prodmodel}"  class="inputText" validate="{required:true,maxlength:96}"  /></td> 
				--%>
				<tr>
					<th style="width:120px">检修措施:  <span class="required"></span></th>
					<td><input size="35" type="text" readonly="readonly" id="measure" name="measure" value="${jobCard.measure}"  class="r" validate="{required:false,maxlength:300}"  /></td>
					<th style="width:120px">制单时间:  <span class="required"></span></th>
					<td><input size="35" id="operatedate" readonly="readonly" name="operatedate" value="<fmt:formatDate value='${jobCard.operatedate}' pattern='yyyy-MM-dd'/>"  class="r" validate="{date:false,required:false}"/></td>
				</tr>
				<tr>
					<th style="width:120px">故障分类:  <span class="required"></span></th>
					<td colspan="3">
					<input size="35" type="text" readonly="readonly" id="type" name="type" value="${jobCard.type}"  class="r" validate="{required:false,maxlength:300}"  />
					
					</td>
				</tr>
				<tr>
					
					<th style="width:120px">维修工时(/小时):  <span class="required"></span></th>
					<td><input size="35"  readonly="readonly" type="text" id="manhours" name="manhours" value="${jobCard.manhours}"  class="r" validate="{required:true,number:true}"  /></td>
					<th style="width:120px">差旅费(/元):  <span class="required"></span></th>
					<td >
					
					<input size="35"  readonly="readonly" type="text"   name="clmoney"  value="${jobCard.clmoney}"  class="r"   />
					
					 
					</td>
				</tr>
				<tr>
					<th style="width:120px">维修费用(/元):  <span class="required"></span></th>
					<td ><input size="35"  readonly="readonly"  type="text" id="manmoney" name="manmoney"  value="${jobCard.manmoney}"  class="r" validate="{required:true,number:true}"  /></td>
					<th style="width:120px">总金额(/元):  <span class="required"></span></th>
					<td ><input size="35" readonly="readonly" type="text" id="sumprice" name="sumprice"  value="${jobCard.sumprice}"  class="r"    />
					 
					
					</td>
				</tr>
				<tr>
					<th style="width:120px">问题描述:  <span class="required"></span></th>
					<td ><textarea cols="50" rows="3" type="text" id="descn" name="descn" readonly="readonly"  class="r" validate="{required:false,maxlength:96}" >${taskM.descn}</textarea></td>
					<th style="width:120px">故障原因:  <span class="required"></span></th>
					<td colspan="3"><textarea cols="50" rows="3" type="text" id="reason" name="reason"  readonly="readonly"  class="r" validate="{required:false,maxlength:300}" >${jobCard.reason}</textarea></td>
				</tr>
				<tr>
					<th style="width:120px">维修结果:  <span class="required"></span></th>
					<td ><textarea cols="50" rows="3" type="text" id="wxjg" name="wxjg"  readonly="readonly"  class="r" validate="{required:false,maxlength:300}" >${jobCard.wxjg}</textarea></td>
				 
					<th style="width:120px">遗留问题:  <span class="required"></span></th>
					<td ><textarea cols="50" rows="3" type="text" id="qtwt" name="qtwt"  readonly="readonly"  class="r" validate="{required:false,maxlength:300}" >${jobCard.qtwt}</textarea></td>
			 <tr>
					<th style="width:120px">客户评价:  <span class="required"></span></th>
					<td ><textarea cols="50" rows="3" type="text" id="khpj" name="khpj"  readonly="readonly"  class="r" validate="{required:false,maxlength:300}" >${jobCard.khpj}</textarea></td>
				 
					<th style="width:120px">工作内容:  <span class="required"></span></th>
					<td ><textarea cols="50" rows="3" type="text" id="content" name="content"   readonly="readonly"  class="r" validate="{required:false,maxlength:765}" >${jobCard.content}</textarea></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="jobCardDetail" formType="window" type="sub">
				<tr>
					<td colspan="6">
						
			    		<div align="center">
							维修工作单明细
			    		</div>
		    		</td>
				</tr>
				<tr>
				 
					
					<th>序号</th>
					<th>备件名称</th>
					<th>备件编号</th>
					<th>备件价格</th>
					<th>数量</th>
					<th>总金额</th>
				</tr>
				<c:forEach items="${jobCardDetailList}" var="jobCardDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="seq">${jobCardDetailItem.seq}</td>
					    <td style="text-align: center" name="prodname">${jobCardDetailItem.prodname}</td>
					    <td style="text-align: center" name="prodcode">${jobCardDetailItem.prodcode}</td>
					    <td style="text-align: center" name="price">${jobCardDetailItem.price}</td>
					    <td style="text-align: center" name="nums">${jobCardDetailItem.nums}</td>
					     <td style="text-align: center" name="sumprice">${jobCardDetailItem.sumprice}</td>
						<input size="35" type="hidden" name="seq" value="${jobCardDetailItem.seq}"/>
						<input size="35" type="hidden" name="prodname" value="${jobCardDetailItem.prodname}"/>
						<input size="35" type="hidden" name="prodcode" value="${jobCardDetailItem.prodcode}"/>
						<input size="35" type="hidden" name="price" value="${jobCardDetailItem.price}"/>
						<input size="35" type="hidden" name="nums" value="${jobCardDetailItem.nums}"/>
						<input size="35" type="hidden" name="sumprice" value="${jobCardDetailItem.sumprice}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="seq"></td>
			    	<td style="text-align: center" name="prodname"></td>
			    	<td style="text-align: center" name="prodcode"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="nums"></td>
			    	<input size="35" type="hidden" name="seq" value=""/>
			    	<input size="35" type="hidden" name="prodname" value=""/>
			    	<input size="35" type="hidden" name="prodcode" value=""/>
			    	<input size="35" type="hidden" name="price" value=""/>
			    	<input size="35" type="hidden" name="nums" value=""/>
		 		</tr>
		    </table>
		     <table class="table-grid table-list" cellpadding="1" cellspacing="1" id="jobCardtlDetail" formType="window" type="sub">
				<tr>
					<td colspan="7">
						 
			    		<div align="center">
							退料单明细
			    		</div>
		    		</td>
				</tr>
				<tr>
				 
					<th>序号</th>
					<th>备件名称</th>
					<th>备件编号</th>
					<th>备件价格</th>
					<th>数量</th>
					<th>总金额</th>
				</tr>
				 <c:forEach items="${jobCardtlDetailList}" var="jobCardDetailtlItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="seq">${jobCardDetailtlItem.seq}</td>
					    <td style="text-align: center" name="prodname">${jobCardDetailtlItem.prodname}</td>
					    <td style="text-align: center" name="prodcode">${jobCardDetailtlItem.prodcode}</td>
					    <td style="text-align: center" name="price1">${jobCardDetailtlItem.price1}</td>
					    <td style="text-align: center" name="nums1">${jobCardDetailtlItem.nums1}</td>
					    <td style="text-align: center" name="sumprice1">${jobCardDetailtlItem.sumprice1}</td>
					    
					    
						<input  type="hidden" name="seq" value="${jobCardDetailtlItem.seq}"/>
			    		<input  type="hidden" name="prodname" value="${jobCardDetailtlItem.prodname}"/>
			    		<input  type="hidden" name="prodcode" value="${jobCardDetailtlItem.prodcode}"/>
			    		<input  type="hidden" name="price1" value="${jobCardDetailtlItem.price1}"/>
			    		<input  type="hidden" name="nums1" value="${jobCardDetailtlItem.nums1}"/>
			    		<input  type="hidden" name="sumprice1" value="${jobCardDetailtlItem.sumprice1}"/>
						
						
						
						
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="seq"></td>
			    	<td style="text-align: center" name="prodname"></td>
			    	<td style="text-align: center" name="prodcode"></td>
			    	<td style="text-align: center" name="price1"></td>
			    	<td style="text-align: center" name="nums1"></td>
			    	<td style="text-align: center" name="sumprice1"></td>
			     
			    	<input size="35" type="hidden" name="seq" value=""/>
			    	<input size="35" type="hidden" name="prodname" value=""/>
			    	<input size="35" type="hidden" name="prodcode" value=""/>
			    	<input size="35" type="hidden" name="price1" value=""/>
			    	<input size="35" type="hidden" name="nums1" value=""/>
			    	<input size="35" type="hidden" name="sumprice1" value=""/>
		 		</tr>
		    </table>
			<input size="35" type="hidden" name="id" value="${jobCard.id}" />
		</form>
		
	</div>
	<form id="jobCardDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<!-- <tr>
				<th style="width:120px">维修任务ID:  <span class="required"></span></th>
				<td><input size="35" type="text" name="taskid" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr> -->
			<tr>
				<th style="width:120px">序号:  <span class="required"></span></th>
				<td><input size="35" type="text" name="seq" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">备件名称:  <span class="required"></span></th>
				<td><input size="35" type="text" name="prodname" value=""  class="inputText" validate="{required:true,maxlength:96}"/></td>
			</tr>
			<tr>
				<th style="width:120px">备件编号:  <span class="required"></span></th>
				<td><input size="35" type="text" name="prodcode" value=""  class="inputText" validate="{required:true,maxlength:36}"/></td>
			</tr>
			<tr>
				<th style="width:120px">备件价格:  <span class="required"></span></th>
				<td><input size="35" type="text" name="price" value=""  class="inputText"  validate="{required:true,number:true}"/></td>
			</tr>
			<tr>
				<th style="width:120px">数量:  <span class="required"></span></th>
				<td><input size="35" type="text" name="nums" value=""  class="inputText" validate="{required:true,number:true }"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
