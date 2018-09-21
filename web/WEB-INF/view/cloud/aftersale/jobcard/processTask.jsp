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
		/*
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/aftersale/jobcard/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}*/
		function fo_price(){
      		var p = $("#manmoney").val();
      	 
      		if(p!=null&&p!=""&&!isNaN(p)){
      		var price = parseFloat(p);
      		var temp=$("#sumprice_temp").val();
      		var total= $('#sumprice').val();
      			if(total == null || total == ""){
      					total = 0 - 0;
      					 
      				}
      		if(temp == null || temp == ""){
      					temp = 0 - 0;
      					 
      				}
      		var s=parseFloat(total) ;
      		 var a=s-temp+price;
      		   
      		 $("#manmoney").val(price.toFixed(2));
      		 $('#sumprice').val(a.toFixed(2));
      		 $("#sumprice_temp").val(price.toFixed(2));
      		}else{
      		$.ligerMessageBox.alert("提示信息","请输入数字！");
      		
      		}
      		
      	}	
      	
      	
      		function fo_price2(){
      		var p = $("#clmoney").val();
      	 
      		if(p!=null&&p!=""&&!isNaN(p)){
      		var price = parseFloat(p);
      		var temp=$("#clmoney_temp").val();
      		
      		var total= $('#sumprice').val();
      		if(total == null || total == ""){
      					total = 0 - 0;
      					 
      				}
      		 
      		if(temp == null || temp == ""){
      					temp = 0 - 0;
      					 
      				}
      		var s=parseFloat(total) ;
      		 var a=s-temp+price;
      		   
      		 $("#clmoney").val(price.toFixed(2));
      		 $('#sumprice').val(a.toFixed(2));
      		 $("#clmoney_temp").val(price.toFixed(2));
      		}else{
      		$.ligerMessageBox.alert("提示信息","请输入数字！");
      		
      		}
      		
      	}
		
		  $(function(){
				var seq=1;
				$("#btnAdd").click(function(){
					$(":input[name='seq']").val(seq++);
				});
			});
			
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
      			
      			
      			
      			
      			
     function getSumPrice(obj) {
    		var price=$(obj).closest("tr").find('input[name="price"]').val();
    		var nums=$(obj).closest("tr").find('input[name="nums"]').val();
    	 
    		if(!isNaN(price)&&!isNaN(nums)){
    		var total=$('input[name="sumprice"]').val();
    		var sumprice=$(obj).closest("tr").find('input[name="sumprice"]').val();
    			if(sumprice==""||sumprice==null){
    				sumprice=0;
    			}
    			if(total==""||total==null){
    				total=0;
    			}
    			total=total-sumprice;
    			if(price!=""&&nums!=""){
    				var a=nums*price;
    				price=parseFloat(price);
    				$(obj).closest("tr").find('input[name="sumprice"]').val(a.toFixed(2));
    				$(obj).closest("tr").find('input[name="price"]').val(price.toFixed(2));
    				total+=a;
    				$('#sumprice').val(total.toFixed(2));
    				
    			}
    		}else{
    			$.ligerMessageBox.alert("请输入数字!");
    		}
    	}  	
    	
    	
    	  			
     function getSumPrice2(obj) {
    		var price=$(obj).closest("tr").find('input[name="price1"]').val();
    		var nums=$(obj).closest("tr").find('input[name="nums1"]').val();
    	 
    		if(!isNaN(price)&&!isNaN(nums)){
    		var total=$('input[name="sumprice"]').val();
    		 
    		var sumprice=$(obj).closest("tr").find('input[name="sumprice1"]').val();
    		 
    			if(sumprice==""||sumprice==null){
    				sumprice=0;
    			}
    			if(total==""||total==null){
    				total=0;
    			}
    			total=parseFloat(total)+parseFloat(sumprice);
    			 
    			if(price!=""&&nums!=""){
    				var a=nums*price;
    				price=parseFloat(price);
    				$(obj).closest("tr").find('input[name="sumprice1"]').val(a.toFixed(2));
    				$(obj).closest("tr").find('input[name="price1"]').val(price.toFixed(2));
    				total-=a;
    				 
    				$('#sumprice').val(total.toFixed(2));
    			}
    		}else{
    			$.ligerMessageBox.alert("请输入数字!");
    		}
    	}  	  			
      			 	
	  /**
	*全选/反选
	*/
	
	 function checkall() {
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					 $("[name=check]:checkbox").attr('checked', false);
				}else{
					 $("[name=check]:checkbox").attr('checked', true);
				}
			});
	} 
	 
	 
   	
   		function delproduct(){
		 
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					$(this).parent().parent().remove();
						var total=$('input[name="sumprice"]').val();
						total=parseFloat(total);
					if(total!=""&&total!=null){
					var a=$(this).closest("tr").find('input[name="sumprice1"]').val();
					a=parseFloat(a);
						total=total+a;
					}
					$('#sumprice').val(total.toFixed(2));
				}
			});
		}
		
		
			//增加一行
		function add_onesupps(){
		var total=parseInt($("#total").val())+1;
   		var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value=""/></td><td> <input type="hidden"  name="seq" value="'+ parseInt(total) + '"/>'+ total + '</td><td><input type="text" name="prodname" value="" />  </td><td><input type="text" name="prodcode" value="" />  </td>  <td> <input type="text"     name="price1" onblur="getSumPrice2(this);"  value=""/> </td>  <td><input type="text"    name="nums1" onblur="getSumPrice2(this);" value=""/> </td> <td><input type="text"   class="r" readonly="readonly"  name="sumprice1"  value=""/> </td></tr>');
				$("#jobCardtlDetail").append(item);
				$("#total").val(parseInt($("#total").val())+1);
   	}
   	//是否退料
   		function isTl(){
			var val=$('input:radio[name="istl"]:checked').val();
			if(val == '否'){
				$("#div_tableshow").hide();
				 
			}else if(val=='是'){
			$("#div_tableshow").show();
				
			}
			
		}
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
				<!--<div class="group">
					<a class="link close" href="javascript:window.close();">关闭</a>
				</div>-->
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="jobCardForm" method="post" action="complete.ht">			
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			
				
				<tr>
					<th style="width:120px">任务编码:  <span class="required"></span></th>
					<td >
					<input type="hidden" id="total" name="total" value=0  />
					<input size="35" type="hidden" id="enterprise_id" name="enterprise_id" value="${jobCard.enterprise_id }" />
				<input size="35" type="hidden" id="enterprise_name" name="enterprise_name" value="${jobCard.enterprise_name }" />
				<input size="35" type="hidden" id="prodcode" name="prodcode" value="${jobCard.prodcode}"    />
				<input size="35" type="hidden" id="prodname" name="prodname" value="${jobCard.prodname}"    />
				<input size="35" type="hidden" id="prodmodel" name="prodmodel" value="${jobCard.prodmodel}"   />
					
					
					<input size="35" type="text" id="taskid" name="taskid" value="${taskM.id}" class="inputText" validate="{required:true,maxlength:96}" /></td>
					 <th style="width:120px">维修人员:  <span class="required"></span></th>
					<td><input size="35" type="text" id="man" name="man" value="${jobCard.man}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				
			
				<tr>
					<th style="width:120px">检修措施:  <span class="required"></span></th>
					<td><input size="35" type="text" id="measure" name="measure" value="${jobCard.measure}"  class="inputText" validate="{required:false,maxlength:300}"  /></td>
					<th style="width:120px">制单时间:  <span class="required"></span></th>
					<td><input size="35" id="operatedate" name="operatedate" value="<fmt:formatDate value='${jobCard.operatedate}' pattern='yyyy-MM-dd'/>"  class="inputText date" validate="{date:false,required:false}"/></td>
				</tr>
				<tr>
					<th style="width:120px">故障分类:  <span class="required"></span></th>
					<td >
					<input type="checkbox" id="type" name="type" value="设计">设计
					<input type="checkbox" id="type" name="type" value="工艺">工艺
					<input type="checkbox" id="type" name="type" value="生产">生产
					<input type="checkbox" id="type" name="type" value="器材">器材
					<input type="checkbox" id="type" name="type" value="软件">软件
					<input type="checkbox" id="type" name="type" value="管理">管理
					<input type="checkbox" id="type" name="type" value="操作">操作
					<input type="checkbox" id="type" name="type" value="测试设备">测试设备
					<input type="checkbox" id="type" name="type" value="外购(协)件">外购(协)件
					<input type="checkbox" id="type" name="type" value="其他">其他
					</td>
					<th style="width:120px;">是否退料</th>
						<td  >
							<input type="radio" name="istl"  value="是" onclick="isTl();" >是 
							<input type="radio" name="istl"  value="否" checked="checked"  onclick="isTl();">否 
						</td>
				</tr>
				<tr>
					
					<th style="width:120px">维修工时(/小时):  <span class="required"></span></th>
					<td><input size="35" type="text" id="manhours" name="manhours" value="${jobCard.manhours}"  class="inputText" validate="{required:true,number:true}"  /></td>
					<th style="width:120px">差旅费(/元):  <span class="required"></span></th>
					<td >
					
					<input size="35" onblur="fo_price2();" type="text" id="clmoney" name="clmoney"  value="${jobCard.clmoney}"  class="inputText" validate="{required:true,number:true}"  />
					
					<input   type="hidden" id="clmoney_temp"    value="0"       />
					</td>
				</tr>
				<tr>
					<th style="width:120px">工时费(/元):  <span class="required"></span></th>
					<td >
					
					<input size="35" onblur="fo_price();" type="text" id="manmoney" name="manmoney"  value="${jobCard.manmoney}"  class="inputText" validate="{required:true,number:true}"  />
					
					<input   type="hidden" id="sumprice_temp"    value="0"       />
					</td>
					<th style="width:120px">总金额(/元):  <span class="required"></span></th>
					<td ><input size="35" readonly="readonly" type="text" id="sumprice" name="sumprice"  value="${jobCard.sumprice}"  class="r"    />
					
					
					</td>
				</tr>
				 
				<tr>
					<th style="width:120px">问题描述:  <span class="required"></span></th>
					<td ><textarea cols="50" rows="3" type="text" id="descn" name="descn"   class="inputText" validate="{required:false,maxlength:96}" >${taskM.descn}</textarea></td>
					<th style="width:120px">故障原因:  <span class="required"></span></th>
					<td colspan="3"><textarea cols="50" rows="3" type="text" id="reason" name="reason"  class="inputText" validate="{required:false,maxlength:300}" >${jobCard.reason}</textarea></td>
				</tr>
				<tr>
					<th style="width:120px">维修结果:  <span class="required"></span></th>
					<td ><textarea cols="50" rows="3" type="text" id="wxjg" name="wxjg"  class="inputText" validate="{required:false,maxlength:300}" >${jobCard.wxjg}</textarea></td>
				 
					<th style="width:120px">遗留问题:  <span class="required"></span></th>
					<td ><textarea cols="50" rows="3" type="text" id="qtwt" name="qtwt"  class="inputText" validate="{required:false,maxlength:300}" >${jobCard.qtwt}</textarea></td>
			 <tr>
					<th style="width:120px">客户评价:  <span class="required"></span></th>
					<td ><textarea cols="50" rows="3" type="text" id="khpj" name="khpj"  class="inputText" validate="{required:false,maxlength:300}" >${jobCard.khpj}</textarea></td>
				 
					<th style="width:120px">工作内容:  <span class="required"></span></th>
					<td ><textarea cols="50" rows="3" type="text" id="content" name="content"   class="inputText" validate="{required:false,maxlength:765}" >${jobCard.content}</textarea></td>
				</tr>
				
			</table>
			
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="jobCardDetail" formType="window" type="sub">
				<tr>
					<td colspan="7">
						 
			    		<div align="center">
							用料单明细
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
					    <td style="text-align: center" name="price"><input  type="text" name="price" readonly="readonly" class="r" value="${jobCardDetailItem.price}"/></td>
					    <td style="text-align: center" name="nums"><input  type="text" name="nums" onblur="getSumPrice(this);" value="${jobCardDetailItem.nums}"/></td>
					     <td style="text-align: center" name="sumprice"><input   type="text" name="sumprice" readonly="readonly" class="r" value="${jobCardDetailItem.sumprice}"/></td>
					   
						<input size="35" type="hidden" name="seq" value="${jobCardDetailItem.seq}"/>
						<input size="35" type="hidden" name="prodname" value="${jobCardDetailItem.prodname}"/>
						<input size="35" type="hidden" name="prodcode" value="${jobCardDetailItem.prodcode}"/>
						
						
						
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="seq"></td>
			    	<td style="text-align: center" name="prodname"></td>
			    	<td style="text-align: center" name="prodcode"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="nums"></td>
			    	<td style="text-align: center" name="sumprice"></td>
			     
			    	<input size="35" type="hidden" name="seq" value=""/>
			    	<input size="35" type="hidden" name="prodname" value=""/>
			    	<input size="35" type="hidden" name="prodcode" value=""/>
			    	<input size="35" type="hidden" name="price" value=""/>
			    	<input size="35" type="hidden" name="nums" value=""/>
			    	<input size="35" type="hidden" name="sumprice" value=""/>
		 		</tr>
		    </table>
		    
		    
		    <span style="display: none;" id="div_tableshow">
		    <table class="table-grid table-list" cellpadding="1" cellspacing="1" id="jobCardtlDetail" formType="window" type="sub">
				<tr>
					<td colspan="7">
						<div class="group" align="left">
				   			 <a href="javascript:void(0)" onclick="add_onesupps();"   style="text-decoration: none;">
											<img src="${ctx}/images/iconadd.jpg" width="52" height="18"
												onmouseover="src='${ctx}/images/iconadd2.jpg'"
												onmouseout="src='${ctx}/images/iconadd.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
								  
								  <a href="javascript:void(0)"  onclick="delproduct();"   style="text-decoration: none;">
											<img src="${ctx}/images/icon_del2.jpg" height="18" width="52"
												onmouseover="src='${ctx}/images/icon_del.jpg'"
												onmouseout="src='${ctx}/images/icon_del2.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
			    		</div>
			    		<div align="center">
							退料单明细
			    		</div>
		    		</td>
				</tr>
				<tr>
				 <th><input type="checkbox"  id="checkbox" title="全选/反选" onclick="checkall();"/></th>
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
					    <td style="text-align: center" name="prodname"><input   type="text" name="prodname" value="${jobCardDetailtlItem.prodname}"/></td>
					    <td style="text-align: center" name="prodcode"><input   type="text" name="prodcode" value="${jobCardDetailtlItem.prodcode}"/></td>
					    <td style="text-align: center" name="price1"><input  type="text" name="price" readonly="readonly" class="r" value="${jobCardDetailtlItem.price1}"/></td>
					    <td style="text-align: center" name="nums1"><input  type="text" name="nums" onblur="getSumPrice(this);" value="${jobCardDetailtlItem.nums1}"/></td>
					     <td style="text-align: center" name="sumprice1"><input   type="text" name="sumprice" readonly="readonly" class="r" value="${jobCardDetailtlItem.sumprice1}"/></td>
					   
						<input   type="hidden" name="seq" value="${jobCardDetailtlItem.seq}"/>
						
						
						
						
						
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
		    </span>
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
				<td><input size="35" type="text" name="price" onblur="price_foalt(this);" value=""  class="inputText" validate="{required:true,number:true}"/></td>
			</tr>
			<tr>
				<th style="width:120px">数量:  <span class="required"></span></th>
				<td><input size="35" type="text" name="nums" value=""  class="inputText" validate="{required:true,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">总金额:  <span class="required"></span></th>
				<td><input size="35" type="text" name="sumprice" value="" readonly="readonly" class="r"  /></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
