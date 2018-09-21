<%@include file="/commons/cloud/checkCompany.jsp"%>
<%--
	time:2013-04-23 09:39:31
	desc:edit the 返厂维修单
--%>
<%@page import="java.util.Date"%>
<%@page import="com.hotent.core.util.ContextUtil"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 返厂维修单</title>
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
			var frm=$('#returntaskForm').form();
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
						window.location.href = "${ctx}/cloud/aftersale/returntask/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#returntaskForm').attr('action','apply.ht');
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		$("a.agree").click(function() {
			operatorType=1;
			var tmp = '1';
			/*if(isDirectComlete){//直接一票通过
				tmp = '5';
			}*/
			$('#voteAgree').val(tmp);
			$('#back').val("1");

			$('#returntaskForm').attr("action","complete.ht");
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
			
			$('#returntaskForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
  		  
  		var dd;
      	function selSups(){
      		//弹出供应商物品选择框
      		var urlShow = '${ctx}/cloud/console/busiarea/listMyFriends.ht';
      		dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 600, title :'企业选择器', name:"frameDialog_"});
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
      	

		
        function preview(){
			CommonDialog("prod_list",
			function(data) {
				$(".l-dialog-table input[name='prodname']").val(data.name);
				$(".l-dialog-table input[name='unit']").val(data.unit);
				$(".l-dialog-table input[name='price']").val(data.price);
				$(".l-dialog-table input[name='prodcdoe']").val(data.code);
			});
		}
        $(function(){
        	var code=1;
        	$("#btnAdd").click(function(){
        		$(".l-dialog-table input[name='code']").val(code++);
        	});
        })
        
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${returntask.id !=null}">
			        <span class="tbar-label">编辑返厂维修单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加返厂维修单</span>
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
		<form id="returntaskForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			    <input size="35" type="hidden" name="currentpurenterid" value="${returntask.currentpurenterid }"/>
			    
			    <tr>
				    <th style="width:120px;">返厂维修单号:</th>
				    <td colspan="3"><input size="35" id="code" name="code" value="${returntask.code}"  class="inputText" validate="{required:true,maxlength:96}" ></td>
				</tr>    
				<tr>
					<th style="width:120px;">制单人： </th>
					<td><input size="35" type="text" id="operator" name="operator" value="${returntask.operator }"  class="inputText" validate="{required:false,maxlength:96}" /></td>
					<th style="width:120px;">制单时间：</th>
					<td><input size="35" id="operatedate" name="operatedate" value="<fmt:formatDate value='${returntask.operatedate }' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>		
				<tr>
				    <th style="width:120px;">返厂厂商: </th>
				    <td><input size="35" type="hidden" id="en_id" name="purenterid" value="${returntask.purenterid}"   />
					<input size="35" type="text" id="en_name" name="purenter_name" readonly="readonly" value="${returntask.purenter_name}"  class="inputText" validate="{required:true,maxlength:96}"  />
					<a href="javascript:void(0)" onclick="selSups()"  class="link detail">选择</a>
					</td>
					<th style="width:120px;">送修厂: </th>
				    <td><input size="35" type="text" id="currentpurenter" name="currentpurenter" value="${returntask.currentpurenter }"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>				
				<%--
				<tr>
				    <th style="width:120px;">完成时间: </th><td><input size="35" id="enddata" name="enddata" value="<fmt:formatDate value='${returntask.enddata}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}"/></td>
				</tr> 
				--%>
				<tr>
					<th style="width:120px;">返厂原因: </th>
					<td colspan="3"><textarea id="reason" rows="3" cols="95" name="reason"   class="inputText" validate="{required:false,maxlength:900}" >${returntask.reason}</textarea></td>
				</tr>				
				<tr>
					<th style="width:120px;">备注：</th>
					<td colspan="3"><textarea rows="3" cols="95" id="remark" name="remark"  class="inputText" validate="{required:false,maxlength:108}" >${returntask.remark}</textarea></td>
				<tr>
				
				
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="returntaskDetail" formType="window" type="sub">
				<tr>
					<td colspan="7">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						返厂维修单明细
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>序号</th>
					<th>产品编码</th>
					<th>产品名称</th>
					<th>规格型号</th>
					<th>产品数量</th>
					<th>计量单位</th>
					<th>参考价格</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${returntaskDetailList}" var="returntaskDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="code">${returntaskDetailItem.code}</td>
					    <td style="text-align: center" name="prodcdoe">${returntaskDetailItem.prodcdoe}</td>
					    <td style="text-align: center" name="prodname">${returntaskDetailItem.prodname}</td>
					    <td style="text-align: center" name="model">${returntaskDetailItem.model}</td>
					    <td style="text-align: center" name="nums">${returntaskDetailItem.nums}</td>
					    <td style="text-align: center" name="unit">${returntaskDetailItem.unit}</td>
					    <td style="text-align: center" name="price">${returntaskDetailItem.price}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input size="35" type="hidden" name="code" value="${returntaskDetailItem.code}"/>
						<input size="35" type="hidden" name="prodcdoe" value="${returntaskDetailItem.prodcdoe}"/>
						<input size="35" type="hidden" name="prodname" value="${returntaskDetailItem.prodname}"/>
						<input size="35" type="hidden" name="model" value="${returntaskDetailItem.model}"/>
                        <input size="35" type="hidden" name="nums" value="${returntaskDetailItem.nums}"/>
						<input size="35" type="hidden" name="unit" value="${returntaskDetailItem.unit}"/>
						<input size="35" type="hidden" name="price" value="${returntaskDetailItem.price}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="code"></td>
			    	<td style="text-align: center" name="prodcdoe"></td>
			    	<td style="text-align: center" name="prodname"></td>
			    	<td style="text-align: center" name="model"></td>
			    	<td style="text-align: center" name="nums"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input size="35" type="hidden" name="code" value=""/>
			    	<input size="35" type="hidden" name="prodcdoe" value=""/>
			    	<input size="35" type="hidden" name="prodname" value=""/>
			    	<input size="35" type="hidden" name="model" value=""/>
			    	<input size="35" type="hidden" name="nums" value=""/>
			    	<input size="35" type="hidden" name="unit" value=""/>
			    	<input size="35" type="hidden" name="price" value=""/>
		 		</tr>
		    </table>
			<input size="35" type="hidden" name="id" value="${returntask.id}" />
			<input size="35" type="hidden" id="back" name="back" value=""/>
			<input size="35" type="hidden" name="formData" id="formData" />
			<input size="35" type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input size="35" type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="returntaskDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">序号: </th>
				<td><input size="35" type="text" name="code" value="${code }"  class="inputText" validate="{required:false,maxlength:36}"/></td>
			</tr>
			<tr>
				<th width="20%">产品编码: </th>
				<td><input size="35" type="text" name="prodcdoe" value=""  readonly="readonly" class="inputText" validate="{required:true,maxlength:96}"/>
				<a href="javascript:void(0)" onclick="preview()" class="link detail">请选择</a>
				</td>
			</tr>
			<tr>
				<th width="20%">产品名称: </th>
				<td><input size="35" type="text"  name="prodname" value=""  class="inputText" validate="{required:false,maxlength:96}"/>
				</td>
			</tr>
			<tr>
				<th width="20%">规格型号: </th>
				<td><input size="35" type="text" name="model" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">产品数量: </th>
				<td><input size="35" type="text" name="nums" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">计量单位: </th>
				<td><input size="35" type="text" name="unit" value=""  class="inputText" validate="{required:false,maxlength:36}"/></td>
			</tr>
			<tr>
				<th width="20%">参考价格: </th>
				<td><input size="35" type="text" name="price" value=""  class="inputText" validate="{required:true,number:true}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
