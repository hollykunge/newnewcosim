<%--
	time:2013-04-16 17:21:17
	desc:edit the cloud_business_chance
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%@ include file="/commons/cloud/meta.jsp"%>

<html>
<head>
<title>商机发布</title>
<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>


 
<!-- ueditor -->
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css" />
<style type="text/css">
/* 分类选择 */
.category-columns{overflow: hidden;zoom: 1;}
.category-columns .category-list{margin-left:0; padding-left:0;text-align:left;float: left;width: 150px;height: 200px;background-color: white;border: 1px solid #e0e0e0;overflow-x: hidden;overflow-y: scroll;}
.category-columns .category-list li{height: 22px;line-height: 22px;overflow: hidden;}
.category-columns .category-list a{display: block;height: 22px;padding-left: 10px;color: #454545;text-decoration: none;}
.category-columns .category-list a:hover{background-color: #d7d7d7;}
.category-columns .category-list a.select{background: #9ab8d6 !important;color: #fff !important;}

</style>
<script type="text/javascript">
var manager = null;
		$(function() {
			var editor = new baidu.editor.ui.Editor({
				minFrameHeight : 200,
				initialContent : ''
			});
			editor.render("txtHtml");
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
			})
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#businessPproducechaseForm').form();
			$("a.save").click(function() {
				frm.setData();
				$('#content').val(editor.getContent());
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
						 
						document.getElementById("classid").value= id;
						
						document.getElementById("levl1").value= $('.category-list[level=1]>li>a.select').text();
						document.getElementById("levl2").value= $('.category-list[level=2]>li>a.select').text();
						document.getElementById("levl3").value= $('.category-list[level=3]>li>a.select').text();
						 document.getElementById("classid").value= $('.category-list[level=3]>li>a.select').text();
						 manager = $.ligerDialog.waitting('正在保存中,请稍候...');
					form.submit();
				}
			});
		});
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
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			manager.close();
			if (obj.isSuccess()) {
		$.ligerMessageBox.alert("提示信息", "商机已提交、请等待管理员审核");
			 
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		
	//上传图片	
	var dd = null;
	function selectIcon(){
		var urlShow = '${ctx}/cloud/pub/image/toUpload.ht';
		dd = $.ligerDialog.open({ url:urlShow, height: 350,width: 500, title :'图片上传器', name:"frameDialog_"});
	};

	 
		
			function _callbackImageUploadSucess(src){
			dd.close();
				$("#image").val(src);
					 var item = $('<img src="${ctx}'+src+'" width="80" height="84" />');
					$("#picView").append(item);
				 
			 
		}
	 
			
			
			
		 
		
	</script>
						
</head>
<body >
	<div class="panel">
				<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${businessPproducechase.id !=null}">
			        <span class="tbar-label">编辑生产商机</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加生产商机</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<c:if test="${info.state == 2}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				 </c:if>
				 <c:if test="${info.state != 2}">
					<div class="group">你的企业还未通过审核，不允许发布商机！</div>
				 </c:if>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="businessPproducechaseForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				 <tr>
					<th width="20%">商机名称: </th>
					<td><input type="text" id="name" name="name" value="${businessPproducechase.name}"  class="inputText" style="width:550px;" validate="{required:true,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">开始时间: </th>
					<td><input type="text" id="startTime" name="startTime" value="<fmt:formatDate value='${businessPproducechase.startTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">结束时间: </th>
					<td><input type="text" id="endTime" name="endTime" value="<fmt:formatDate value='${businessPproducechase.endTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">生产物品分类: </th>
					<td>
					<div class="choose-category-way" style="">
							<div class="category-columns" id="category-columns" defaults="0">
								<ul class="category-list" level="1">
									
								</ul>
							</div>
						</div>
					
					${businessPproducechase.classid}
					</td>
				</tr>
				<tr>
					<th width="20%">图片: </th>
					<td>
					<input id="image" name="image" type="hidden"
									value="${businessPproducechase.image}"> <a
									href="javascript:selectIcon();" class="btn-ctrl"
									id="addProductPic" js_tukubtn="true">添加图片</a>
									<div class="addproduct-pic" id="picView">
									<c:if test="${businessPproducechase.image!=null }">
												<img src="${ctx}${businessPproducechase.image}"  onError="this.src='${ctx}/images/product_img03.jpg'" width="80" height="84" />
											</c:if>
									</div>
					</td>
				</tr>
				<tr>
					<th width="20%">生产工艺要求: </th>
					<td>
					<textarea rows="3" cols="100"  id="scgyyq" name="scgyyq" style="width:550px;">${businessPproducechase.scgyyq}</textarea>
					</td>
				</tr>
				 
				<tr>
					<th width="20%">生产规模: </th>
					<td>
					<textarea rows="3" cols="100"   id="scgm" name="scgm" style="width:550px;">${businessPproducechase.scgm}</textarea>
					</td>
				</tr>
				<tr>
					<th width="20%">关键设备要求: </th>
					<td>
					<textarea rows="3" cols="100"   id="gjsbyq" name="gjsbyq"  style="width:550px;">${businessPproducechase.gjsbyq}</textarea>
					</td>
				</tr>
				<tr>
					<th width="20%">产品质量要求: </th>
					<td>
					<textarea rows="3" cols="100"   id="cpzlyq" name="cpzlyq"  style="width:550px;">${businessPproducechase.cpzlyq}</textarea>
					</td>
				</tr>
				<tr>
					<th width="20%">合作加工企业资质要求: </th>
					
					
					<td>
					<textarea rows="3" cols="100"  id="hzjgqyzzyq" name="hzjgqyzzyq"   style="width:550px;">${businessPproducechase.hzjgqyzzyq}</textarea>
					</td>
				</tr>
				<tr>
					<th width="20%">商机内容: </th>
					<td>
						<div id="editor" position="center" style="overflow: auto; height: 100%;">
						  	<textarea id="txtHtml" name="html">${fn:escapeXml(businessPproducechase.content)}</textarea>
					    </div>
                    </td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${businessPproducechase.id}" />
			<input type="hidden" id="operateTime" name="operateTime"
							value="<fmt:formatDate value='${businessPproducechase.operateTime}' pattern='yyyy-MM-dd'/>"class="inputText date" /> 
		 
			<input type="hidden" id="publishState" name="publishState" value="未审核" />
			<input type="hidden" id="userId" name="userId" value="${businessPproducechase.userId}"  />
			<input type="hidden" id="companyId" name="companyId" value="${businessPproducechase.companyId}"  />
							<input type="hidden" id="userName" name="userName" value="${businessPproducechase.userName}"  />
			<input type="hidden" id="companyName" name="companyName" value="${businessPproducechase.companyName}"  />
			<input id="content" name="content" type="hidden"></input>
			<input type="hidden" id="classid" name="classid" value="${businessPproducechase.classid}"    />
			<input type="hidden" id="levl1" name="levl1" value="">
					            	<input type="hidden" id="levl2" name="levl2" value="">
					            	<input type="hidden" id="levl3" name="levl3" value="">
		</form>
		
	</div>
 
</body>
</html>
