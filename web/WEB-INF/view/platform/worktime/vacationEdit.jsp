<%--
	time:2012-02-20 09:25:49
	desc:edit the 法定假期设置
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑 法定假期设置</title>
<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript"
	src="${ctx}/servlet/ValidJs?form=vacation"></script>
<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			if(${vacation.id ==null}){
				valid(showRequest,showResponse,1);
			}else{
				valid(showRequest,showResponse);
			}
			$("a.save").click(function() {
				$('#vacationForm').submit(); 
			});
		});
		
		function setStime(){
            window.setTimeout( function (){ $("#statTime").focus(); }, 0);
            window.setTimeout( function (){ $("#endTime").focus(); }, 0);
            window.setTimeout( function (){ $("#name").focus(); }, 0);
		}
		
		function setEtime(){
            window.setTimeout( function (){ $("#endTime").focus(); }, 0);
            window.setTimeout( function (){ $("#statTime").focus(); }, 0);
            window.setTimeout( function (){ $("#name").focus(); }, 0);
		}
		
	</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<c:choose>
					<c:when test="${vacation.id !=null}">
						<span class="tbar-label">编辑法定假期设置</span>
					</c:when>
					<c:otherwise>
						<span class="tbar-label">添加法定假期设置</span>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">

			<form id="vacationForm" method="post" action="save.ht">
				<table class="table-detail" cellpadding="0" cellspacing="0"
					border="0">
					<tr>
						<th width="20%">假日名称:</th>
						<td><input type="text" id="name" name="name"
							value="${vacation.name}" class="inputText" /></td>
					</tr>
					<tr>
						<th width="20%">年份:</th>
						<td><c:if test="${vacation.years==null}">
								<input type="text" id="years" name="years" value="${year}"
									class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy'})" />
							</c:if> <c:if test="${vacation.years!=null}">
								<input type="text" id="years" name="years"
									value="${vacation.years}" class="Wdate"
									onfocus="WdatePicker({dateFmt:'yyyy'})" />
							</c:if></td>
					</tr>
					<tr>
						<th width="20%">开始时间:</th>
						<td><input type="text" id="statTime" name="statTime"
							value="<fmt:formatDate value='${vacation.statTime}' pattern='yyyy-MM-dd'/>"
							class="inputText date" onchange="setStime()" /></td>
					</tr>
					<tr>
						<th width="20%">结束时间:</th>
						<td><input type="text" id="endTime" name="endTime"
							value="<fmt:formatDate value='${vacation.endTime}' pattern='yyyy-MM-dd'/>"
							class="inputText date" onchange="setEtime()" /></td>
					</tr>
				</table>
				<input type="hidden" name="id" value="${vacation.id}" />
			</form>

		</div>
	</div>
</body>
</html>
