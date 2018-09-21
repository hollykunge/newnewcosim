<%--
	time:2012-02-20 09:25:52
	desc:edit the 加班情况
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 加班情况</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx }/js/lg/plugins/htButtons.js" ></script>
	<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerWindow.js" ></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js" ></script>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=overTime"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			if(${overTime.id ==null}){
				valid(showRequest,showResponse,1);
			}else{
				valid(showRequest,showResponse);
			}
			$("a.save").click(function() {
				$('#overTimeForm').submit(); 
			});
		});
		
        var __ctx='${ctx}';
        // 用户选择器
        function showUserDlg()
        {
 	        UserDialog({
 		        callback:function(userIds,userNames){
 		        	$('#userId').attr('value',userIds);
 		        	$('#userName').attr('value',userNames);
 		        }
 	        });
        }
		
		function setStime(){
            window.setTimeout( function (){ $("#startTime").focus(); }, 0);
            window.setTimeout( function (){ $("#endTime").focus(); }, 0);
            window.setTimeout( function (){ $("#userName").focus(); }, 0);
		}
		
		function setEtime(){
            window.setTimeout( function (){ $("#endTime").focus(); }, 0);
            window.setTimeout( function (){ $("#startTime").focus(); }, 0);
            window.setTimeout( function (){ $("#userName").focus(); }, 0);
		}
        
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
		        <c:when test="${overTime.id !=null}">
		            <span class="tbar-label">编辑加班情况</span>
		        </c:when>
		        <c:otherwise>
		            <span class="tbar-label">添加加班情况</span>
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
			
				<form id="overTimeForm" method="post" action="save.ht">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">标题: </th>
							<td>
								<input type="text" id="subject" name="subject" 
									value="${overTime.subject}" class="inputText"  size="40"/>
							</td>
						</tr>
						<tr>
							<th width="20%">用户名称: </th>
							<td>
								<ul>
									<li style="float: left;">
										<input type="text" id="userName" name="userName" readonly="readonly"
											value="${overTime.userName}"  class="inputText"/>
										<input type="hidden" id="userId" name="userId" value="${overTime.userId}" />
									</li>
									<li style="float: left;">&nbsp;</li>
									<li style="float: left;">
										
										<a href='#' class='button'  onclick="showUserDlg()" ><span >...</span></a>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th width="20%">开始时间: </th>
							<td><input type="text" id="startTime" name="startTime" 
								value="<fmt:formatDate value='${overTime.startTime}' pattern='yyyy-MM-dd HH:mm:ss' type='both' />" 
								class="inputText datetime" onchange="setStime()" datetype="datetime"/></td>
						</tr>
						<tr>
							<th width="20%">结束时间: </th>
							<td><input type="text" id="endTime" name="endTime" 
								value="<fmt:formatDate value='${overTime.endTime}' pattern='yyyy-MM-dd HH:mm:ss' type='both' />" 
								class="inputText datetime" onchange="setEtime()" datetype="datetime" /></td>
						</tr>
						<tr>
							<th width="20%">工作日类型变更: </th>
							<td>
								<select id="workType" name="workType">
									<c:forEach var="type" items="${typelist}">
										<c:if test="${overTime.workType==type.typeId}">
											<option selected="selected" value="${type.typeId}">${type.typeName}</option>
										</c:if>
										<c:if test="${overTime.workType!=type.typeId}">
											<option value="${type.typeId}">${type.typeName}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th width="20%">描述:</th>
							<td>
								<textarea rows="3" cols="60" name="memo">${overTime.memo}</textarea>
							</td>
						</tr>
						
					</table>
					<input type="hidden" name="id" value="${overTime.id}" />
				</form>
			
	</div>
</div>
</body>
</html>
