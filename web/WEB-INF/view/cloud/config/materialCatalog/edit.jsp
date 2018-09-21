<%--
	time:2013-06-17 11:41:35
	desc:edit the 自定义分类
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 自定义分类</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#materialCatalogForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
				var id = $("#id").val();
				
					 var totalLevel = $('.category-list').size();
						for(var i=1; i<(totalLevel+1); i++){
							var $ul = $('.category-list[level=' + i + ']>li>a.select');
							 
							 
							  
							if($ul.text()==""){
								
								
								$.ligerMessageBox.alert("提示信息",  "有物品分类未选中!");
								return;
							}
							 
							
						}
						 
						//选中最后一级分类
						var $ul = $('.category-list[level=' + totalLevel + ']>li>a.select');
						var id = $ul.parent().attr('o-id');
						 
						 document.getElementById("typeId").value= id;
						 
						 document.getElementById("typeName").value= $('.category-list[level=3]>li>a.select').text();
						 	 
				
				
				
				
				
				
					form.submit();
				}
			});
			
			
			
			//初始化第一级
			$.ajax({
				type : 'post',
				url : '${ctx}/cloud/config/materialclass/getClasses.ht',
				dataType: 'json',				
				success : function(data){
					var rows=data.list;
					 
					if(rows.length){
						for(var i=0;i<rows.length;i++){
							var row = rows[i];
							$('.category-list[level=1]').append('<li o-id=' + row.id + '><a href="javascript:void(0);" onclick="change(this, '+row.id+',1)" class="parent">' + row.name + '</a></li>');
						};
					}
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
						window.location.href = "${ctx}/cloud/config/materialCatalog/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		
		
			function preview(){
			CommonDialog("materialCatalogTreeOwer",
			function(data) {
				 
				$("#parentId").val(data.id);
				$("#parentName").val(data.name);
				
				
			});
		}
		
		
	 
		
		
		//select选中事件
		function change(a, id,level){
			//选中背景
			$(a).parent().parent().find('.select').removeClass('select');
			$(a).toggleClass('select');
			
			//删除以后所有元素
			for(var i=(level+1); i<4; i++){
				var s = '.category-list[level=' + i + ']';	
			 
					$(s).remove();
				 
			}
			
			//判断是否有下级目录
			$.ajax({
					type : 'post',
					url : '${ctx}/cloud/config/materialclass/getClasses.ht?id='+id,
					dataType: 'json',
					success : function(data){
						var rows=data.list;
						if(rows.length){//动态生成下级目录	
							//当前元素
							var currentUL = '.category-list[level=' + level+ ']';
							//下一元素
							var nextUL = '.category-list[level=' + (level+1) + ']';
							
							//当前元素对象
							var $srcUL = $(currentUL);
							
							 
								$srcUL.after('<ul class="category-list" level="' + (level+1) + '"></ul>');
						 
							
							//目标元素对象
							var $targetUL = $(nextUL);
							$targetUL.empty();
							for(var i=0;i<rows.length;i++){
								var row = rows[i];
								 
								$targetUL.append('<li o-id=' + row.id + '><a href="javascript:void(0);" onclick="change(this, ' + row.id + ', ' +  (level+1) + ')" class="parent">' + row.name + '</a></li>');
							}
						}
						
					 
					}
				})
		}
		
	</script>
	
	
	<style type="text/css">
/* 分类选择 */
.category-columns{overflow: hidden;zoom: 1;}
.category-columns .category-list{margin-left:0; padding-left:0;text-align:left;float: left;width: 150px;height: 200px;background-color: white;border: 1px solid #e0e0e0;overflow-x: hidden;overflow-y: scroll;}
.category-columns .category-list li{height: 22px;line-height: 22px;overflow: hidden;}
.category-columns .category-list a{display: block;height: 22px;padding-left: 10px;color: #454545;text-decoration: none;}
.category-columns .category-list a:hover{background-color: #d7d7d7;}
.category-columns .category-list a.select{background: #9ab8d6 !important;color: #fff !important;}

</style>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${materialCatalog.id !=null}">
			        <span class="tbar-label">编辑自定义分类</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加自定义分类</span>
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
		<form id="materialCatalogForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			<tr>
					<th width="20%">分类名称: </th>
					<td><input type="text" id="name" name="name" value="${materialCatalog.name}"  class="inputText" validate="{required:true,maxlength:10}"  /></td>
				</tr>
				<tr>
					<th width="20%">分类编码: </th>
					<td><input type="text" id="code" name="code" value="${materialCatalog.code}"  class="inputText" validate="{required:false,maxlength:10}"  /></td>
				</tr>
				
			
			 
				
				<tr>
					<th width="20%">所属父级: </th>
					<td>
					<input type="hidden" id="parentId" name="parentId" value="${materialCatalog.parentId}"  class="inputText"    />
					<input type="text" id="parentName" name="parentName" value="${materialCatalog.parentName}" readonly="readonly"  class="inputText"    />
					<a href="javascript:void(0)" onclick="preview()"  class="link detail">选择</a>
					
					</td>
				</tr>
				
			 
			<tr>
					<th width="20%">所属大类: </th>
					<td>
					<div class="choose-category-way" style="">
							<div class="category-columns" id="category-columns" defaults="0">
								<ul class="category-list" level="1">
									
								</ul>
							</div>
						</div>
					
					
					</td>
				</tr>
					<tr>
					<th width="20%">分类述描: </th>
					<td>
				 
					
					<textarea id="descn" name="descn" cols="50" rows="3" validate="{required:false,maxlength:100}" >${materialCatalog.descn}</textarea>
					</td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${materialCatalog.id}" />
			<input type="hidden" name="entId" value="${materialCatalog.entId}" />
			<input type="hidden" name="entName" value="${materialCatalog.entName}" />
			<input type="hidden" id="typeId" name="typeId" value="${materialCatalog.typeId}">
			<input type="hidden" id="typeName" name="typeName" value="${materialCatalog.typeName}">
			 
			
		</form>
		
	</div>
</div>
</body>
</html>
