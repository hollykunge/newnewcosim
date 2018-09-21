<%--
	time:2013-04-19 11:40:23
	desc:edit the 更换单
--%>
<%@page import="java.util.Date"%>
<%@page import="com.hotent.core.util.ContextUtil"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 更换单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#exChangeForm').form();
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
						window.location.href = "${ctx}/cloud/aftersale/exchange/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		function preview(){
			
		    CommonDialog("prod_list",
			function(data) {
				$(".l-dialog-table input[name='prodname']").val(data.name);
				$(".l-dialog-table input[name='prodcode']").val(data.code);
				$(".l-dialog-table input[name='price']").val(data.price);
			    price=data.price;
			});
		}
		
		//求出合计

	function getSumPrice(obj) {
		var price=$(obj).closest("form").find('input[name="price"]').val();
		var orderNum=$(obj).closest("form").find('input[name="nums"]').val();
		if(price!=""&&orderNum!=""){
			var a=orderNum*price;
			price=parseFloat(price);
			$(obj).closest("form").find('input[name="sum"]').val(a.toFixed(2));
			$(obj).closest("form").find('input[name="price"]').val(price.toFixed(2));
		}
	}	

		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${exChange.id !=null}">
			        <span class="tbar-label">编辑更换单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加更换单</span>
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
		<form id="exChangeForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			         <input size="35" type="hidden" id="enterprise_id" name="enterprise_id" value="${exChange.enterprise_id }" />
					 <input size="35" type="hidden" id="enterprise_name" name="enterprise_name" value="${exChange.enterprise_name }" />
					 
					 <tr><th style="width:120px;">更换单号</th><td colspan="3"><input size="35" id="code" name="code" value="${exChange.code}"  class="inputText" validate="{required:true,maxlength:96}"/></td></tr>
					<tr><th>制单人</th><td><input size="35" id="operator" name="operator" value="${exChange.operator }"  class="inputText" validate="{required:false,maxlength:96}"/></td><th style="width:120px;">制单日期</th><td><input size="35" id="operatedate" name="operatedate" value="<fmt:formatDate value='${exChange.operatedate }' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:false,required:false}"/></td></tr>
					<tr><th style="width:120px;">更换原因</th><td colspan="3"><textarea class="area" cols="95" rows="3" id="reason" name="reason"  class="inputText" validate="{required:false,maxlength:768}">${exChange.reason}</textarea></td></tr>
							
			        <%-- 
			        <tr><th>来源单据ID</th><td><input size="35" id="sourceid" name="sourceid" value="${exChange.sourceid}"  class="inputText" validate="{required:false,number:true }"/></td><th style="width:120px;">来源单据号</th><td><input size="35" id="sourcecode" name="sourcecode" value="${exChange.sourcecode}"  class="inputText" validate="{required:false,maxlength:96}"/></td></tr>
					<tr><th style="width:120px;">来源单据类型</th><td colspan="3"><input size="35" id="sourcetype" name="sourcetype" value="${exChange.sourcetype}"  class="inputText" validate="{required:false,maxlength:36}"/></td></tr> 
					--%>
				    
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="exChangeDetail" formType="window" type="sub">
				<tr>
					<td colspan="8">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						更换单明细
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>序号</th>
					<th>产品编码</th>
					<th>产品名称</th>
					<th>更换价格</th>
					<th>更换数量</th>
					<th>更换等级</th>
					<th>合计</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${exChangeDetailList}" var="exChangeDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="seq">${exChangeDetailItem.seq}</td>
					    <td style="text-align: center" name="prodcode">${exChangeDetailItem.prodcode}</td>
					    <td style="text-align: center" name="prodname">${exChangeDetailItem.prodname}</td>
					    <td style="text-align: center" name="price">${exChangeDetailItem.price}</td>
					    <td style="text-align: center" name="nums">${exChangeDetailItem.nums}</td>
					    <td style="text-align: center" name="rank">${exChangeDetailItem.rank}</td>
					    <td style="text-align: center" name="sum">${exChangeDetailItem.sum}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" id="del" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input size="35" type="hidden" name="seq" value="${exChangeDetailItem.seq}"/>
						<input size="35" type="hidden" name="prodcode" value="${exChangeDetailItem.prodcode}"/>
						<input size="35" type="hidden" name="prodname" value="${exChangeDetailItem.prodname}"/>
						<input size="35" type="hidden" name="price" value="${exChangeDetailItem.price}"/>
						<input size="35" type="hidden" name="nums" value="${exChangeDetailItem.nums}"/>
						<input size="35" type="hidden" name="rank" value="${exChangeDetailItem.rank}"/>
						<input size="35" type="hidden" name="sum" value="${exChangeDetailItem.sum}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="seq"></td>
			    	<td style="text-align: center" name="prodcode"></td>
			    	<td style="text-align: center" name="prodname"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="nums"></td>
			    	<td style="text-align: center" name="rank"></td>
			    	<td style="text-align: center" name="sum"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input size="35" type="hidden" name="seq" value=""/>
			    	<input size="35" type="hidden" name="prodcode" value=""/>
			    	<input size="35" type="hidden" name="prodname" value=""/>
			    	<input size="35" type="hidden" name="price" value=""/>
			    	<input size="35" type="hidden" name="nums" value=""/>
			    	<input size="35" type="hidden" name="rank" value=""/>
			    	<input size="35" type="hidden" name="sum" value=""/>
		 		</tr>
		    </table>
			<input size="35" type="hidden" name="id" value="${exChange.id}" />
		</form>
	</div>
	<form id="exChangeDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">序号:  <span class="required"></span></th>
				<td><input size="35" type="text" id="seq" name="seq" value=""  class="inputText" validate="{required:false,maxlength:36 }"/></td>
			</tr>
			<tr>
				<th width="20%">产品编码:  <span class="required"></span></th>
				<td><input size="35" type="text" name="prodcode" value="" readonly="readonly"  class="inputText" validate="{required:true,maxlength:96}"/>
				<a href="javascript:void(0)" onclick="preview()"  class="link detail">请选择</a>
				</td>
			</tr>
			<tr>
				<th width="20%">产品名称:  <span class="required"></span></th>
				<td><input size="35" type="text" name="prodname" value=""  class="inputText" validate="{required:true,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">更换价格:  <span class="required"></span></th>
				<td><input size="35" type="text" id="price" name="price" value=""  class="inputText" validate="{required:true,number:true}" onblur="getSumPrice(this);"/></td>
			</tr>
			<tr>
				<th width="20%">更换数量:  <span class="required"></span></th>
				<td><input size="35" type="text" id="nums" name="nums" value=""  class="inputText" validate="{required:true,number:true }" onblur="getSumPrice(this);"/></td>
			</tr>
			<tr>
				<th width="20%">更换等级:  <span class="required"></span></th>
				<td>
				<select name="rank" >
					<option value="新品">新&nbsp;&nbsp;品</option>
					<option value="旧品">旧&nbsp;&nbsp;品</option>
				</select>
			</tr>
			<tr>
				<th width="20%">合计:  <span class="required"></span></th>
				<td><input size="35" type="text" id="sum" name="sum" value=""  class="inputText" validate="{required:false,number:true}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
