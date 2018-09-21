<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>物品管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">

function add(catalog_id){
	if(catalog_id==0){
		$.ligerMessageBox.alert("提示信息","未增加分类，请先在分类管理中增加分类！");
		return;
		
	}else{
		window.parent.location.href = "${ctx}/cloud/config/material/toAdd.ht?catalog_id="+catalog_id;
		
	}
	
	
	
}

</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物品管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					 
					 
					 <div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="javascript:add('${catalog_id}');">添加</a></div>
					 
				</div>	
			</div>
			 
		</div>
		<div class="panel-body">
	     
		    <display:table name="materialList" id="materialItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				 
				<display:column property="name" title="物品名称" sortable="true" sortName="name" maxLength="80"></display:column>
				
				<display:column property="code" title="物品代码" sortable="true" sortName="code"></display:column>
				<display:column property="model" title="物品规格" sortable="true" sortName="model"></display:column>
				<display:column property="price" title="物品价格" sortable="true" sortName="price"></display:column>
				<display:column  title="发布时间" sortable="true" sortName="publishdate">
					<fmt:formatDate value="${materialItem.publishdate}" pattern="yyyy-MM-dd"/>
				</display:column>
				 
			</display:table>
			<hotent:paging tableId="materialItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


