<#assign class=table.variable.class>
<#assign classVar=table.variable.classVar> 
<#assign tabComment=table.tableDesc> 
<#assign fieldList=table.fieldList>
<#assign subtables=table.subTableList>

<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>${tabComment}明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<#if defKey?exists>
<script type="text/javascript">
	var tab = null;
	var flowRunId="<#noparse>${</#noparse>${classVar}.flowRunId}";
	$(function (){ 
		var height=$(document.body).height()-65;
		if(!$.isEmpty(flowRunId)){
		    $("#processInfo").ligerTab({height:height,onBeforeSelectTabItem:function(tabid){
		    	var iframe=$("#iframe"+tabid);
		  		if(iframe.length>0){              			
		  			iframe.attr("src",iframe.attr("presrc"));
		  		}
		    }});    
		    $("#taskOpinionDiv").load('<#noparse>${ctx}</#noparse>/platform/bpm/taskOpinion/getList.ht?runId=<#noparse>${</#noparse>${classVar}.flowRunId}&randId='+ Math.random());
		    $("#iframetabitem1").height(height-30);
		    $("#iframetabitem2").height(height-30);
	    }
	});
</script>
<#else>
<script type="text/javascript">
	//放置脚本
</script>
</#if>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">${tabComment}详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<#if defKey?exists>
		<div id="processInfo" class="panel-nav">
			<div title="业务表单明细">
		</#if>
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<#list fieldList as field> 
				<#if field.valueFrom != 2 && field.isHidden == 0>
				<tr>
					<th width="20%">${field.fieldDesc}:</th>
					<#if field.fieldType=="date">
					<td>
					<fmt:formatDate value="<#noparse>${</#noparse>${classVar}.${field.fieldName}}" pattern="yyyy-MM-dd"/>
					</td>
					<#elseif field.controlType == 9>
					<td>
						<div name="div_attachment_container" right="r" >
							<div  class="attachement" ></div>
							<textarea style="display:none" controltype="attachment"  name="scfj" ><#noparse>${</#noparse>${classVar}.${field.fieldName}}</textarea>
						</div>
					</td>
					<#else>
					<td><#noparse>${</#noparse>${classVar}.${field.fieldName}}</td>
					</#if>
				</tr>
				</#if>
				</#list>
			</table>
			<#if subtables?size != 0>
			<#list subtables as subtable>
			 <#assign vars=subtable.variable>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="${vars.classVar}">
				<tr>
					<td colspan="${subtable.fieldList?size}" style="text-align: center">${subtable.tableName} :${subtable.tableDesc}</td>
				</tr>
				<tr>
					<#list subtable.fieldList as field>
					<#if field.valueFrom !=2 && field.isHidden==0>
					<th>${field.fieldDesc}</th>
					</#if>
					</#list>
				</tr>	
				<c:forEach items="<#noparse>${</#noparse>${subtable.variable.classVar}List}" var="${subtable.variable.classVar}Item" varStatus="status">
					<tr>
						<input type="hidden"  name="id" value="<#noparse>${</#noparse>${subtable.variable.classVar}Item.id}"  class="inputText"/>
						<#list subtable.fieldList as field>		
						<#if field.valueFrom !=2 && field.isHidden==0>										
						<#if (field.fieldType=="date")>
							<td style="text-align: center"><fmt:formatDate value='<#noparse>${</#noparse>${subtable.variable.classVar}Item.${field.fieldName}}' pattern='yyyy-MM-dd'/></td>								
						<#else>
							<td style="text-align: center"><#noparse>${</#noparse>${subtable.variable.classVar}Item.${field.fieldName}}</td>								
						</#if>
						</#if>
						</#list>
					</tr>
				</c:forEach>
			</table>
			</#list>
			</#if>
		<#if defKey?exists>
		</div>		
		<c:if test="<#noparse>${not empty </#noparse>${classVar}.flowRunId}">
		<div title="流程明细">
			<iframe id="iframetabitem2" scrolling="no" presrc="<#noparse>${ctx}</#noparse>/platform/bpm/processRun/detail.ht?runId=<#noparse>${</#noparse>${classVar}.flowRunId}"  frameborder="0" ></iframe>
		</div>
		<div title="流程示意图">						
			<iframe id="iframetabitem3" scrolling="no" presrc="<#noparse>${ctx}</#noparse>/platform/bpm/processRun/processImage.ht?runId=<#noparse>${</#noparse>${classVar}.flowRunId}&notShowTopBar=1"  frameborder="0"  ></iframe>						
		</div>
		<div title="流程审批历史" id="taskOpinionDiv">
		</div>
		</c:if>
		</#if>
	</div>
</body>
</html>

