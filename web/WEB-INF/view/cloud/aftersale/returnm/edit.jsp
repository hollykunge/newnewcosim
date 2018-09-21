<%--
	time:2013-04-19 11:40:23
	desc:edit the 退货单
--%>
<%@page import="java.util.Date"%>
<%@page import="com.hotent.core.util.ContextUtil"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 退货单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/OfficeControl.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/OfficePlugin.js"></script>
    
    
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#returnMForm').form();
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
						window.location.href = "${ctx}/cloud/aftersale/returnm/list.ht";
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
			    <c:when test="${returnM.id !=null}">
			        <span class="tbar-label">编辑退货单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加退货单</span>
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
		<form id="returnMForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			        <input size="35" type="hidden" id="enterprise_id" name="enterprise_id" value="${returnM.enterprise_id }" />
					<input size="35" type="hidden" id="enterprise_name" name="enterprise_name" value="${returnM.enterprise_name }" />
					
					<tr><th style="width:120px;">退货单号</th><td colspan="3"><input size="35" id="code" name="code" value="${returnM.code }"  class="inputText" validate="{required:true,maxlength:96}"/></td></tr>
					<tr><%-- <th style="width:120px;">制单人ID</th><td><input size="35" id="operator" name="operator" value="${returnM.operator}"  class="inputText" validate="{required:false,maxlength:96}" /></td> --%>
					<th style="width:120px;">制单人</th><td><input size="35" id="operatename" name="operatename" value="${returnM.operatename }"  class="inputText" validate="{required:false,maxlength:96}"/></td>	
					<th>制单日期</th><td><input size="35" id="operatedate" name="operatedate" value="<fmt:formatDate value='${returnM.operatedate }' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true,required:false}"/></td></tr>
					<%-- <th style="width:120px;">来源单据类型</th><td><input size="35" id="souecetype" name="souecetype" value="${returnM.souecetype}"  class="inputText" validate="{required:false,maxlength:96}"/></td></tr>
			        <tr><th>来源单据ID</th><td><input size="35" id="sourceid" name="sourceid" value="${returnM.sourceid}"  class="inputText" validate="{required:false,number:true }"/></td><th style="width:120px;">来源单据号</th><td><input size="35" id="sourcecode" name="sourcecode" value="${returnM.sourcecode}"  class="inputText" validate="{required:false,maxlength:96}"/></td></tr> --%>
			        
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="returnDetail" formType="window" type="sub">
				<tr>
					<td colspan="8">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						退货单明细
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>序号</th>
					<th>产品编码</th>
					<th>产品名称</th>
					<th>退货单价</th>
					<th>退货数量</th>
					<th>退货等级</th>
					<th>退货原因</th>
					<th>合计</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${returnDetailList}" var="returnDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="seq">${returnDetailItem.seq}</td>
					    <td style="text-align: center" name="prodcode">${returnDetailItem.prodcode}</td>
					    <td style="text-align: center" name="prodname">${returnDetailItem.prodname}</td>
					    <td style="text-align: center" name="price">${returnDetailItem.price}</td>
					    <td style="text-align: center" name="nums">${returnDetailItem.nums}</td>
					    <td style="text-align: center" name="rank">${returnDetailItem.rank}</td>
					    <td style="text-align: center" name="reason">${returnDetailItem.reason}</td>
					    <td style="text-align: center" name="sum">${returnDetailItem.sum}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input size="35" type="hidden" name="seq" value="${returnDetailItem.seq}"/>
						<input size="35" type="hidden" name="prodcode" value="${returnDetailItem.prodcode}"/>
						<input size="35" type="hidden" name="prodname" value="${returnDetailItem.prodname}"/>
						<input size="35" type="hidden" name="price" value="${returnDetailItem.price}"/>
						<input size="35" type="hidden" name="nums" value="${returnDetailItem.nums}"/>
						<input size="35" type="hidden" name="rank" value="${returnDetailItem.rank}"/>
						<input size="35" type="hidden" name="reason" value="${returnDetailItem.reason}"/>
						<input size="35" type="hidden" name="sum" value="${returnDetailItem.sum}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="seq"></td>
			    	<td style="text-align: center" name="prodcode"></td>
			    	<td style="text-align: center" name="prodname"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="nums"></td>
			    	<td style="text-align: center" name="rank"></td>
			    	<td style="text-align: center" name="reason"></td>
			    	<td style="text-align: center" name="sum"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input size="35" type="hidden" name="seq" value="${code }"/>
			    	<input size="35" type="hidden" name="prodcode" value=""/>
			    	<input size="35" type="hidden" name="prodname" value=""/>
			    	<input size="35" type="hidden" name="price" value=""/>
			    	<input size="35" type="hidden" name="nums" value=""/>
			    	<input size="35" type="hidden" name="rank" value=""/>
			    	<input size="35" type="hidden" name="reason" value=""/>
			    	<input size="35" type="hidden" name="sum" value=""/>
		 		</tr>
		    </table>
			<input size="35" type="hidden" name="id" value="${returnM.id}" />
		</form>
		
	</div>
	<form id="returnDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">序号:  <span class="required"></span></th>
				<td><input size="35" type="text" name="seq" value="${code }"  class="inputText" validate="{required:false,maxlength:36 }"/></td>
			</tr>
			<tr>
				<th width="20%">产品编码:  <span class="required"></span></th>
				<td><input size="35" type="text" name="prodcode" value=""  readonly="readonly" class="inputText" validate="{required:true,maxlength:96}"/>
				<a href="javascript:void(0)" onclick="preview()" class="link detail">请选择</a>
				</td>
			</tr>
			<tr>
				<th width="20%">产品名称:  <span class="required"></span></th>
				<td><input size="35" type="text" name="prodname" value=""  class="inputText" validate="{required:true,maxlength:96}"/>
				</td>
			</tr>
			<tr>
				<th width="20%">退货单价:  <span class="required"></span></th>
				<td><input size="35" type="text" id="price" name="price" value=""  class="inputText" validate="{required:true,number:true}" onblur="getSumPrice(this);"/></td>
			</tr>
			<tr>
				<th width="20%">退货数量:  <span class="required"></span></th>
				<td><input size="35" type="text" id="nums" name="nums" value=""  class="inputText" validate="{required:true,number:true }" onblur="getSumPrice(this);"/></td>
			</tr>
			<tr>
				<th width="20%">退货等级:  <span class="required"></span></th>
				<td>
				<select name="rank">
					<option value="新品">新&nbsp;&nbsp;品</option>
					<option value="旧品">旧&nbsp;&nbsp;品</option>
				</select>
			</tr>
			<tr>
				<th width="20%">退货原因:  <span class="required"></span></th>
				<td><textarea class="area" cols="50" rows="3" id="descn" name="reason"  class="inputText" validate="{required:false,maxlength:768}"></textarea></td>	
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
