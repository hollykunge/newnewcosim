<%--
	time:2013-04-16 18:08:14
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 sys_org_info</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	
	<script type="text/javascript">
		$(function() {
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#OrgInfationForm').form();
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
				$.ligerMessageBox.confirm("修改成功信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/config/orginfation/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		<script>
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${OrgInfation.sysOrgInfoId !=null}">
			        <span class="tbar-label">编辑企业信息</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">编辑企业信息</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="OrgInfationForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">公司名称: </th>
					<td><input type="text" id="name" name="name" value="${OrgInfation.name}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">公司邮箱: </th>
					<td><input type="text" id="email" name="email" value="${OrgInfation.email}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
					<tr>
					<th width="20%">公司编码: </th>
					<td><input type="text" id="code" name="code" value="${OrgInfation.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
					<tr>
					<th width="20%">公司类型: </th>
					   <td>     
	                  				   
					   <ap:selectDB name="type" id="type"
														where="key='ENTERPRISE_TYPE'" optionValue="value"
														optionText="value" table="cloud_sys_dic"
														selectedValue="${OrgInfation.type}">
										</ap:selectDB>
					
					
					</td>
				</tr>
				
				
				<tr>
					<th width="20%">经营模式: </th>
				             	<td>
													<label for="type_6">
													     <c:remove var="checked"/>
												         <c:if test="${OrgInfation.model==1}">
												         <c:set var="checked" value='checked="checked"'></c:set>
												         </c:if>
														<input name="model" value="1" class="radio" ${checked} id="model" type="radio" />
														生产型
													</label>
													<label for="type_7">
													    <c:remove var="checked"/>
													     <c:if test="${OrgInfation.model==3}">
												       <c:set var="checked" value='checked="checked"'></c:set>
												       </c:if>
														<input name="model" value="3" class="radio" id="model" ${checked} type="radio" />
														贸易型
													</label>
													<label for="type_8">
													<c:remove var="checked"/>
													 <c:if test="${OrgInfation.model==4}">
												       <c:set var="checked" value='checked="checked"'></c:set>
												     </c:if>
														<input name="model" value="4" class="radio" id="model" ${checked} type="radio" />
														服务型
													</label>
													<label for="type_9">
													<c:remove var="checked"/>
													<c:if test="${OrgInfation.model==5}">
												       <c:set var="checked" value='checked="checked"'></c:set>
												    </c:if>
														<input name="model" value="5" class="radio" id="model" ${checked} type="radio" />
														其他类型
													</label>
												</td>
				</tr>
				<tr>
					<th width="20%">主营行业: </th>
						<td>
						  <input type="text" id="type" name="type" value="${OrgInfation.industry}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					</tr>
				<tr>
					<th width="20%">INDUSTRY2: </th>
					<td>  <input type="text" id="industry2" name="industry2" value="${OrgInfation.industry2}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>					
				</tr>
				<tr>
					<th width="20%">公司规模: </th>
					<td><select name="scale" class="text" id="scale" datatype="*"  value="${OrgInfation.scale}">
															<option>
																10人以下
															</option>
															<option>
																10-20人
															</option>
															<option>
																20-50人
															</option>
															<option>
																50-100人
															</option>
															<option>
																100-500人
															</option>
															<option>
																500人以上
															</option>
														</select></td>
				</tr>
			
				
				<tr>
					<th width="20%">主营产品: </th>
					<td>
				 <input type="text" id="product" name="product" value="${OrgInfation.product}"  class="inputText" validate="{required:false,maxlength:192}"  /> 	
				</td>
					
					
					
				</tr>
				<tr>
					<th width="20%">公司网址: </th>
					<td><input type="text" id="website" name="website" value="${OrgInfation.website}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">邮编: </th>
					<td><input type="text" id="postcode" name="postcode" value="${OrgInfation.postcode}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				
					<th width="20%">公司LOGO: </th>
					<td><input type="text" id="logo" name="logo" value="${OrgInfation.logo}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">会员类型: </th>
					<td><input type="text" id="member" name="member" value="${OrgInfation.member}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">公司简介: </th>
						<td>
						<div id="editor" position="center"  style="overflow:auto;height:100%;">
							<textarea id="txtHtml" name="html">${fn:escapeXml(OrgInfation.info)}</textarea>							
						</div>
					</td>
				</tr>
				<tr>
					<th width="20%">国家: </th>
					<td><input type="text" id="country" name="country" value="${OrgInfation.country}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">省: </th>
					<td><input type="text" id="province" name="province" value="${OrgInfation.province}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">市: </th>
					<td><input type="text" id="city" name="city" value="${OrgInfation.city}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">公司地址: </th>
					<td><input type="text" id="address" name="address" value="${OrgInfation.address}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
			<tr>
					<th width="20%">联系人: </th>
					<td><input type="text" id="connecter" name="connecter" value="${OrgInfation.connecter}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">手机: </th>
					<td><input type="text" id="tel" name="tel" value="${OrgInfation.tel}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">传真: </th>
					<td><input type="text" id="fax" name="fax" value="${OrgInfation.fax}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">座机: </th>
					<td><input type="text" id="homephone" name="homephone" value="${OrgInfation.homephone}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
				
				<tr>
					<th width="20%">注册时间: </th>
					<td><input type="text" id="registertime" name="registertime" value="<fmt:formatDate value='${OrgInfation.registertime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">是否公开: </th>
					<td>
					        <c:remove var="checked"/>
												         <c:if test="${OrgInfation.isPublic==1}">
												         <c:set var="checked" value='checked="checked"'></c:set>
												      </c:if>
														<input type="radio"  name="isPublic" ${checked} class="radio" value="1" />是
														<c:remove var="checked"/>
												         <c:if test="${OrgInfation.isPublic==0}">
												         <c:set var="checked" value='checked="checked"'></c:set>
												      </c:if>
														<input type="radio" name="isPublic" class="radio" ${checked} value="0"/>否
				</tr>
			</table>
			<input type="hidden" name="sysOrgInfoId" value="${OrgInfation.sysOrgInfoId}" />
		</form>
		
	</div>
</div>
</body>
</html>
