<%--
	time:2013-04-19 11:18:07
	desc:edit the cloud_manuf_enquiry
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑委外加工询价单</title>
	<%@include file="/commons/include/form.jsp" %>
	<link href="${ctx}/styles/default/css/form.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript">
	
	function _fileCallback(fileId,fileName,filePath) {
		$('.l-dialog-table input[name="craftAttachment"]').val(fileName);
	
	}
	function _fileCallback2(fileId,fileName,filePath) {
		$('.l-dialog-table input[name="bom"]').val(fileName);
	
	}
	
	var dd;
	function add_supps(){
		//弹出供应商物品选择框
		var urlShow = '${ctx}/cloud/console/busiarea/listMyFriends.ht';
		dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 600, title :'企业选择器', name:"frameDialog_"});
	}	
	
	//商圈列表回调函数
	function _callBackMyFriends(friends){
		var names='',ids='';
		var i=0;
		if(friends[0]==""){
			i=1;
		}else{
			i=0;
		}
		for(i; i<friends.length; i++){
			var friend = friends[i];
			ids += ',' + friend[0];
			names += ',' + friend[1];
		}
		ids = ids!=''?ids.substring(1):"";
		names = names!=''?names.substring(1):"";
		$('#e_name').val(names);
		$('#suppEnterpriseIds').val(ids);
		
		dd.close();
	}
	$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#manufEnquiryForm').form();

			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		 function userview(){
	  			CommonDialog("org_info_list",
	  			function(data) {
	   				/* //多个厂商
	  			    $(data).each(function(i){
	  				var did=data[i].id;
	  				alert(did);
	  				var dname=data[i].name;
	  				data_id=data_id+did+",";
	  				data_name=data_name+dname+",";
	  				alert(data_id);
	  				});
	  			 
	  				$("#enterprise_id").val(data_id);
	  				$("#e_name").val(data_name);  
	  				alert(data_id); */

	  				$("#suppEnterpriseIds").val(data.SYS_ORG_INFO_ID);
	  				$("#e_name").val(data.NAME);  
	  				
	  			});
	  		  }
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/manufacture/manufEnquiry/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#manufEnquiryForm').attr('action','apply.ht');
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

			$('#manufEnquiryForm').attr("action","complete.ht");
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
			
			$('#manufEnquiryForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		
		
	/**
	 * 增加物品
	 */
	
	function add_product(){
	
		CommonDialog("warehouse_material",
		function(data) {
			var row=data;
			

			
				$('input[name="materialCode"]').val(row.code);
			
				$('input[name="materialName"]').val(row.name);
				
				
				$('input[name="materialUnit"]').val( row.unit);
			
				
				
				
				
				
			}
			 
			
			);
		}
	
	</script>

</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${manufEnquiry.id !=null}">
			        <span class="tbar-label">编辑委外加工询价单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加委外加工询价单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<c:if test="${applyFlag==0}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link apply" id="dataFormStart" href="javascript:void(0)">申请</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht">返回</a></div>
				</c:if>
				<c:if test="${applyFlag==1}">
					<div class="group"><a id="btnAgree" class="link agree">同意</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="btnNotAgree" class="link notAgree">反对</a></div>
				</c:if>
				<c:if test="${applyFlag==2}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht">返回</a></div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="manufEnquiryForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<input type="hidden" id="operatorId" name="operatorId" value="${manufEnquiry.operatorId}"  class="inputText" validate="{required:true,number:true }"  />
				<input type="hidden" id="enterpriseUserid" name="enterpriseUserid" value="${manufEnquiry.enterpriseUserid}"  class="inputText" validate="{required:true,number:true }"  />
				<input type="hidden" id="enterpriseId" name="enterpriseId" value="${manufEnquiry.enterpriseId}"  class="inputText" validate="{required:true,number:true }"  />
				<input type="hidden" id="sourceformId" name="sourceformId" value="${manufEnquiry.sourceformId}"  class="inputText" validate="{required:false,number:true }"  />
				
				
				
				<tr>
					<th width="20%">单证号: </th>
					<td width="30%"><input type="text" readonly="readonly" id="code" name="code" value="${manufEnquiry.code}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					
					<th width="20%">制单人姓名: </th>
					<td><input type="text" id="operatorName" name="operatorName" value="${manufEnquiry.operatorName}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				
				<!-- 
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operatorId" name="operatorId" value="${manufEnquiry.operatorId}"  class="inputText" validate="{required:true,number:true }"  /></td>
				 -->
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${manufEnquiry.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{required:true,date:true}" /></td>
					
					<th width="20%">企业名: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" value="${manufEnquiry.enterpriseName}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				
				</tr>
				<tr>
					<th width="20%">联系人姓名: </th>
					<td><input type="text" id="enterpriseUsername" name="enterpriseUsername" value="${manufEnquiry.enterpriseUsername}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					
					<th width="20%">发布日期: </th>
					<td><input type="text" id="releaseDate" name="releaseDate" value="<fmt:formatDate value='${manufEnquiry.releaseDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<!-- 
				<tr>
					<th width="20%">联系人ID: </th>
					<td><input type="text" id="enterpriseUserid" name="enterpriseUserid" value="${manufEnquiry.enterpriseUserid}"  class="inputText" validate="{required:true,number:true }"  /></td>
					<th width="20%">企业ID: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${manufEnquiry.enterpriseId}"  class="inputText" validate="{required:true,number:true }"  /></td>
				</tr>
				 -->
				<tr>
					<th width="20%">询价截止日期: </th>
					<td><input type="text" id="offerEnddate" name="offerEnddate" value="<fmt:formatDate value='${manufEnquiry.offerEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">发布模式: </th>
					<td><select  id="releaseModel" name="releaseModel"><option value="公开">公开     </option><option value="公开" selected="selected">邀请     </option></select></td>
				</tr>
				<tr>	
					<th width="20%">运费承担方: </th>
					<td><input type="text" id="freightBearer" name="freightBearer" value="${manufEnquiry.freightBearer}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<th width="20%">运输方式: </th>
					<td><input type="text" id="transportWay" name="transportWay" value="${manufEnquiry.transportWay}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">币种: </th	>
					<td><select id="currencyType" name="currencyType"  class="inputText"><option value="人民币">人民币 </option><option value="美元">美元  </option></select></td>
					<th width="20%">是否带料: </th>
					<td>
						<%--<input type="text" id="isBringmaterial" name="isBringmaterial" value="${manufEnquiry.isBringmaterial}"  class="inputText" validate="{required:false}"  /> --%>
						<select id="isBringmaterial" name="isBringmaterial"  class="inputText" validate="{required:true}" >
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${manufEnquiry.sourceformType}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<th width="20%">加工类型: </th>
					<td><input type="text" id="manufacturingType" name="manufacturingType" value="${manufEnquiry.manufacturingType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				<!--					
					<th width="20%">来源单据ID: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${manufEnquiry.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				  
				  -->
				
				</tr>
				<tr>
					<th width="20%">来源单据编码: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${manufEnquiry.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">是否汇报加工进度: </th>
					<td><select id="isReport" name="isReport" class="inputText" ><option value="1">是</option><option value="0">否  </option></select></td>
				</tr>
				<tr>
					<th width="20%">备注: </th>
					<td><input style="width: 250px" type="text" id="comments" name="comments" value="${manufEnquiry.comments}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
					<th width="20%">选择供应商</th>
					<td>
						<input type="hidden"  name="suppEnterpriseIds" id="suppEnterpriseIds" value="" />
						<input type="text" id="e_name" value="" readonly="readonly" validate="{required:true,maxlength:80}"/>
						<a href="javascript:void(0)" onclick="add_supps()"  class="link detail">选择</a>
					</td>				
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="manufEnquiryDetail" formType="window" type="sub">
				<tr>
					<td colspan="15">
						<div class="group" align="left">
							 	<a id="btnAdd" class="link add">添加</a>
							<!-- <a href="javascript:void(0)" onclick="add_product();">添加</a> -->
							</div>
			    		<div align="center">
						委外加工询价单 :委外加工询价单产品详情
			    		</div>
		    		</td>
				</tr>
				<tr>
				   
					<th>编码</th>
					<th style="display:none">编码规则</th>
					<th>名称</th>
					<th>属性</th>
					<th>单位</th>
					<th>加工数量</th>
					<th>要求交货日期</th>
					<th style="display:none">工艺要求（多选）</th>
					<th>工艺附件</th>
					<th>产品BOM附件</th>
					<th>计划开工时间</th>
					<th>计划完工时间</th>
					<th>计划完工入库率下限</th>
					<th style="display:none">备注</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${manufEnquiryDetailList}" var="manufEnquiryDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materialCode">${manufEnquiryDetailItem.materialCode}</td>
					    <!-- <td style="text-align: center" name="materialCodenotation">${manufEnquiryDetailItem.materialCodenotation}</td> -->
					    <td style="text-align: center" name="materialName">${manufEnquiryDetailItem.materialName}</td>
					    <td style="text-align: center" name="materialAttribute">${manufEnquiryDetailItem.materialAttribute}</td>
					    <td style="text-align: center" name="materialUnit">${manufEnquiryDetailItem.materialUnit}</td>
					    <td style="text-align: center" name="materialNumber">${manufEnquiryDetailItem.materialNumber}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${manufEnquiryDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <!-- <td style="text-align: center" name="crafts">${manufEnquiryDetailItem.crafts}</td> -->
					    <td style="text-align: center" name="craftAttachment">${manufEnquiryDetailItem.craftAttachment}</td>
					    <td style="text-align: center" name="bom">${manufEnquiryDetailItem.bom}</td>
						<td style="text-align: center" name="planStartdate"><fmt:formatDate value='${manufEnquiryDetailItem.planStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="planEnddate"><fmt:formatDate value='${manufEnquiryDetailItem.planEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="planInrate">${manufEnquiryDetailItem.planInrate}</td>
					    <!-- <td style="text-align: center" name="comments">${manufEnquiryDetailItem.comments}</td> -->
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del" >删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="materialCode" value="${manufEnquiryDetailItem.materialCode}"/>
						<input type="hidden" name="materialCodenotation" value="${manufEnquiryDetailItem.materialCodenotation}"/>
						<input type="hidden" name="materialName" value="${manufEnquiryDetailItem.materialName}"/>
						<input type="hidden" name="materialAttribute" value="${manufEnquiryDetailItem.materialAttribute}"/>
						<input type="hidden" name="materialUnit" value="${manufEnquiryDetailItem.materialUnit}"/>
						<input type="hidden" name="materialNumber" value="${manufEnquiryDetailItem.materialNumber}"/>
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${manufEnquiryDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="crafts" value="${manufEnquiryDetailItem.crafts}"/>
						<input type="hidden" name="craftAttachment" value="${manufEnquiryDetailItem.craftAttachment}"/>
						<input type="hidden" name="bom" value="${manufEnquiryDetailItem.bom}"/>
					    <input type="hidden" name="planStartdate" value="<fmt:formatDate value='${manufEnquiryDetailItem.planStartdate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="planEnddate" value="<fmt:formatDate value='${manufEnquiryDetailItem.planEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="planInrate" value="${manufEnquiryDetailItem.planInrate}"/>
						<input type="hidden" name="comments" value="${manufEnquiryDetailItem.comments}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="materialCode"></td>
			    	<td style="text-align: center; display:none" name="materialCodenotation"></td>
			    	<td style="text-align: center" name="materialName"></td>
			    	<td style="text-align: center" name="materialAttribute"></td>
			    	<td style="text-align: center" name="materialUnit"></td>
			    	<td style="text-align: center" name="materialNumber"></td>
					<td style="text-align: center" name="deliveryEnddate"></td>								
			    	<td style="text-align: center; display:none" name="crafts"></td>
			    	<td style="text-align: center" name="craftAttachment"></td>
			    	<td style="text-align: center" name="bom"></td>
					<td style="text-align: center" name="planStartdate"></td>								
					<td style="text-align: center" name="planEnddate"></td>								
			    	<td style="text-align: center" name="planInrate"></td>
			    	<td style="text-align: center; display:none" name="comments"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="materialCode" value=""/>
			    	<input type="hidden" name="materialCodenotation" value=""/>
			    	<input type="hidden" name="materialName" value=""/>
			    	<input type="hidden" name="materialAttribute" value=""/>
			    	<input type="hidden" name="materialUnit" value=""/>
			    	<input type="hidden" name="materialNumber" value=""/>
			    	<input type="hidden" name="deliveryEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="crafts" value=""/>
			    	<input type="hidden" name="craftAttachment" value=""/>
			    	<input type="hidden" name="bom" value=""/>
			    	<input type="hidden" name="planStartdate" value="" class="inputText date"/>
			    	<input type="hidden" name="planEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="planInrate" value=""/>
			    	<input type="hidden" name="comments" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${manufEnquiry.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="manufEnquiryDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">待加工物品编码: </th>
				<td><input type="text" name="materialCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/> <a href="javascript:void(0)" onclick="add_product();"  class="link detail">选择</a></td>
			</tr>
			<!-- 
			<tr>
				<th width="20%">编码规则: </th>
				<td><input type="text" name="materialCodenotation" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			 -->
			<tr>
				<th width="20%">待加工物品名称: </th>
				<td><input type="text" name="materialName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">属性说明: </th>
				<td><input type="text" name="materialAttribute" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="materialUnit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">委托加工数量: </th>
				<td><input type="text" name="materialNumber" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">要求交货日期: </th>
				<td><input type="text" name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">工艺要求（多选）: </th>
				<td><input type="text" name="crafts" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">工艺附件: </th>
				<td><input type="text" name="craftAttachment" value=""  class="inputText" validate="{required:false,maxlength:768}"/><a onclick="AttachMent.addFile1(this);"  href="javascript:;" class="link attachement">选择</a></td>
			</tr>
			<tr>
				<th width="20%">产品BOM附件: </th>
				<td><input type="text" name="bom" value=""  class="inputText" validate="{required:false,maxlength:768}"/> <a onclick="AttachMent.addFile2(this);"  href="javascript:;" class="link attachement">选择</a></td>
			</tr>
			<tr>
				<th width="20%">计划开工时间: </th>
				<td><input type="text" name="planStartdate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">计划完工时间: </th>
				<td><input type="text" name="planEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">计划完工入库率下限: </th>
				<td><input type="text" name="planInrate" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">备注: </th>
				<td><input type="text" name="comments" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
