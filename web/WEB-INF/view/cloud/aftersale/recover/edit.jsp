<%--
	time:2013-05-06 17:14:39
	desc:edit the 回收单
--%>
<%@page import="com.hotent.core.util.ContextUtil"%>
<%@page import="java.util.Date"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 回收单</title>
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
			var frm=$('#recoverForm').form();
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
						window.location.href = "${ctx}/cloud/aftersale/recover/list.ht";
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
					$(".l-dialog-table input[name='unit']").val(data.unit);
					$(".l-dialog-table input[name='price']").val(data.price);
					$(".l-dialog-table input[name='prodcode']").val(data.code);
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
			    <c:when test="${recover.id !=null}">
			        <span class="tbar-label">编辑回收单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加回收单</span>
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
		<form id="recoverForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
					<input size="35" type="hidden" id="enterprise_id" name="enterprise_id" value="${recover.enterprise_id }"  />
					<input size="35" type="hidden" id="enterprise_name" name="enterprise_name" value="${recover.enterprise_name }"   />
				<tr>
					<th style="width:120px">单证号:  <span class="required"></span></th>
					<td colspan="3"><input size="35" type="text" id="code" name="code" value="${recover.code}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">制单人: </th>
					<td><input size="35" type="text" id="operator" name="operator" value="${recover.operator }"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">制单日期: </th>
					<td><input size="35" type="text" id="operatedate" name="operatedate" value="<fmt:formatDate value='${recover.operatedate }' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th style="width:120px">回收原因: </th>
					<td colspan="3"><textarea cols="95" rows="5" id="reason" name="reason"  class="inputText" validate="{required:false,maxlength:300}" >${recover.reason}</textarea></td>
				</tr>
				<%-- <tr>
					<th style="width:120px">来源单据类型: </th>
					<td><input size="35" type="text" id="sourcetype" name="sourcetype" value="${recover.sourcetype}"  class="inputText" validate="{required:false,maxlength:36}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">来源单据ID: </th>
					<td><input size="35" type="text" id="sourceid" name="sourceid" value="${recover.sourceid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th style="width:120px">来源单据号: </th>
					<td><input size="35" type="text" id="sourcecode" name="sourcecode" value="${recover.sourcecode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr> --%>
					
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="recoverDetail" formType="window" type="sub">
				<tr>
					<td colspan="7">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						回收单明细
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>序号</th>
					<th>产品编码</th>
					<th>产品名称</th>
					<th>回收价格</th>
					<th>回收价格</th>
					<th>回收等级</th>
					<th>合计</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${recoverDetailList}" var="recoverDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="seq">${recoverDetailItem.seq}</td>
					    <td style="text-align: center" name="prodcode">${recoverDetailItem.prodcode}</td>
					    <td style="text-align: center" name="prodname">${recoverDetailItem.prodname}</td>
					    <td style="text-align: center" name="price">${recoverDetailItem.price}</td>
					    <td style="text-align: center" name="nums">${recoverDetailItem.nums}</td>
					    <td style="text-align: center" name="rank">${recoverDetailItem.rank}</td>
					    <td style="text-align: center" name="sum">${recoverDetailItem.sum}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input size="35" type="hidden" name="seq" value="${recoverDetailItem.seq}"/>
						<input size="35" type="hidden" name="prodcode" value="${recoverDetailItem.prodcode}"/>
						<input size="35" type="hidden" name="prodname" value="${recoverDetailItem.prodname}"/>
						<input size="35" type="hidden" name="price" value="${recoverDetailItem.price}"/>
						<input size="35" type="hidden" name="nums" value="${recoverDetailItem.nums}"/>
						<input size="35" type="hidden" name="rank" value="${recoverDetailItem.rank}"/>
						<input size="35" type="hidden" name="sum" value="${recoverDetailItem.sum}"/>
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
			<input size="35" type="hidden" name="id" value="${recover.id}" />
		</form>
		
	</div>
	<form id="recoverDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th style="width:120px">序号: </th>
				<td><input size="35" type="text" name="seq" value=""  class="inputText" validate="{required:false,maxlength:108}"/></td>
			</tr>
			<tr>
				<th style="width:120px">产品编码: </th>
				<td><input size="35" type="text" name="prodcode" value="" readonly="readonly"  class="inputText" validate="{required:false,maxlength:108}"/>
				<a href="javascript:void(0)" onclick="preview()" class="link detail">请选择</a>
				</td>
			</tr>
			<tr>
				<th style="width:120px">产品名称: </th>
				<td><input size="35" type="text" name="prodname" value=""  class="inputText" validate="{required:false,maxlength:108}"/>
				</td>
			</tr>
			<tr>
				<th style="width:120px">回收价格: </th>
				<td><input size="35" type="text" id="price" name="price" value=""  class="inputText" validate="{required:true,number:true}" onblur="getSumPrice(this);"/></td>
			</tr>
			<tr>
				<th style="width:120px">回收数量: </th>
				<td><input size="35" type="text" id="nums" name="nums" value=""  class="inputText" validate="{required:true,number:true }" onblur="getSumPrice(this);"/></td>
			</tr>
			<tr>
				<th style="width:120px">回收等级: </th>
				<td>
				<select name="rank">
					<option value="新品">新品</option>
					<option value="旧品">旧品</option>
				</select>
			</tr>
			<tr>
				<th style="width:120px">合计: </th>
				<td><input size="35" type="text" id="sum" name="sum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
