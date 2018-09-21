<%--
	time:2013-04-16 17:21:17
	desc:edit the营销商机
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑营销商机</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
 
<!-- ueditor -->
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css" />
	<script type="text/javascript">
	var manager = null;
		$(function() {
		
			$(".radio").click(function(){
				if($(this).attr("value")=='审核未通过'){
					$("#div_show").show();
				}else{
					$("#div_show").hide();
				}
			});
		
			var editor = new baidu.editor.ui.Editor({
				minFrameHeight : 200
			});
			editor.render("txtHtml");
			 
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#businessMarketingchaseForm').form();
			$("a.save").click(function() {
				frm.setData();
				 $('#content').val(editor.getContent());
				frm.ajaxForm(options);
				if(frm.valid()){
					 	manager = $.ligerDialog.waitting('正在保存中,请稍候...');
					form.submit();
				}
			});
		});
		 
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			manager.close();
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", "审核成功,是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
					 
					window.location.href = "${ctx}/cloud/config/businessMarketingchase/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
	 
			
			
			
		 
		
	</script>
</head>
<body >
	 
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${businessMarketingchase.id !=null}">
			        <span class="tbar-label">编辑营销商机</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加营销商机</span>
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
		<form id="businessMarketingchaseForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				  <tr>
					 <th width="20%">审核状态:  <span class="required">*</span></th>
					<td>
					 
				                 
														<input type="radio" name="publishState"   class="radio" value="审核通过" checked="checked" />审核通过
													
														<input type="radio" name="publishState"   class="radio"   value="审核未通过" />审核未通过
					
					</td>
				</tr>
				</tr>
				
					<tr>
						<th width="20%">审核原因: </th>
					<td>
						<textarea rows="3" cols="60" id="publishInfo" style="width:550px;" name="publishInfo">${businessMarketingchase.publishInfo}</textarea>
						<span id="div_show" style="display: none;"><font color="red">请填写未通过原因</font></span>
					</td>
					</tr>
				<tr>
				<tr>
					<th width="20%">采购物品分类: </th>
				<td><input type="text" id="classid" name="classid" value="${businessMarketingchase.classid}"  class="inputText" readonly="readonly"   /></td>
				</tr>
				<tr>
					<th width="20%">商机名称: </th>
					<td><input type="text" id="name" name="name" value="${businessMarketingchase.name}"  style="width:550px;" class="inputText" readonly="readonly"  /></td>
				</tr>
				<tr>
					<th width="20%">开始时间: </th>
					<td><input type="text" id="startTime" name="startTime" value="<fmt:formatDate value='${businessMarketingchase.startTime}' pattern='yyyy-MM-dd'/>" class="inputText" readonly="readonly"  /></td>
				</tr>
				<tr>
					<th width="20%">结束时间: </th>
					<td><input type="text" id="endTime" name="endTime" value="<fmt:formatDate value='${businessMarketingchase.endTime}' pattern='yyyy-MM-dd'/>" class="inputText " readonly="readonly"  /></td>
				</tr>
				<tr>
					<th width="20%">图片: </th>
				 
					
					
						<td>
				 
			    
					       
					       <input id="image" name="image" type="hidden"
									value="${businessMarketingchase.image}">  
									<div class="addproduct-pic" id="picView" >
									<c:if test="${businessMarketingchase.image!=null }">
												<img src="${ctx}${businessMarketingchase.image}" onError="this.src='${ctx}/images/product_img02.jpg'" width="80" height="84" />
											</c:if>
									
									</div>
					</td>
				</tr>
				 
				 
				<tr>
					<th width="20%">代理区域: </th>
					<td>${businessMarketingchase.dlqy}--${businessMarketingchase.dlqy2}</td>
				</tr>
			 
				<tr>
					<th width="20%">代理时间要求: </th>
					<td>
					<textarea rows="3" cols="100"   id="dlsjyq" name="dlsjyq" style="width:550px;">${businessMarketingchase.dlsjyq}</textarea>
					</td>
				</tr>
				<tr>
					<th width="20%">营销伙伴资质要求: </th>
					
					<td>
					<textarea rows="3" cols="100"   id="yxhbzzyq" name="yxhbzzyq"  style="width:550px;">${businessMarketingchase.yxhbzzyq}</textarea>
					</td>
				</tr>
				
				
				<tr>
					<th width="20%">商机内容: </th>
					<td>
						<div id="editor" position="center" style="overflow: auto; height: 100%;">
						  	<textarea id="txtHtml" name="html">${fn:escapeXml(businessMarketingchase.content)}</textarea>
					    </div>
                    </td>
				</tr>
			
			</table>
				<input type="hidden" id="operateTime" name="operateTime"
							value="<fmt:formatDate value='${businessMarketingchase.operateTime}' pattern='yyyy-MM-dd'/>"class="inputText date" /> 
			<input type="hidden" name="id" value="${businessMarketingchase.id}" />
			<input type="hidden" id="userId" name="userId" value="${businessMarketingchase.userId}"  />
			<input type="hidden" id="companyId" name="companyId" value="${businessMarketingchase.companyId}"  />
				<input type="hidden" id="userName" name="userName" value="${businessMarketingchase.userName}"  />
			<input type="hidden" id="companyName" name="companyName" value="${businessMarketingchase.companyName}"  />
			<input id="content" name="content" type="hidden" value="${fn:escapeXml(businessMarketingchase.content)}"></input>
			<input type="hidden" id="classid" name="classid" value="${businessMarketingchase.classid}"    />
			<input type="hidden" id="levl1" name="levl1" value="">
					            	<input type="hidden" id="levl2" name="levl2" value="">
					            	<input type="hidden" id="levl3" name="levl3" value="">
					            	
					            	<input type="hidden" id="dlqy" name="dlqy" value="${businessMarketingchase.dlqy}"    />
					            	<input type="hidden" id="dlqy2" name="dlqy2" value="${businessMarketingchase.dlqy2}"    />
		</form>
		
	</div>
</div>
 
</body>
</html>
