<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@ include file="/commons/cloud/global.jsp"%>
<html>
<head>
<title>CLOUD_MATERIAL管理</title>
<%@ include file="/commons/cloud/meta.jsp"%>

<link href="${ctx}/styles/cloud/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/cloud/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		$(function() {
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
						
						//总层数
						var totalLevel = $('.category-list').size();
						
						//更新显示
						var str = '<p>您选择的类目是：';
						for(var i=1; i<(totalLevel+1); i++){
							str += '<b>' + $('.category-list[level=' + i + ']>li>a.select').text() + '</b><span>&gt;</span>';
						}												
						str += "</p>";
						$('#current-category').html(str);
					}
				})
		}
		
		function tonext(){
			
			 
			 	 var totalLevel = $('.category-list').size();
			for(var i=1; i<(totalLevel+1); i++){
				var $ul = $('.category-list[level=' + i + ']>li>a.select');
				 
				if($ul.text()==""){
					alert('有物品分类未选中');
					return;
				}
			}
			//选中最后一级分类
			var $ul = $('.category-list[level=' + totalLevel + ']>li>a.select');
			var id = $ul.parent().attr('o-id');
			
			
			
			document.getElementById("levltype").value= id;
			document.getElementById("levlseq").value= totalLevel;
			document.getElementById("levl1").value= $('.category-list[level=1]>li>a.select').text();
			document.getElementById("levl2").value= $('.category-list[level=2]>li>a.select').text();
			document.getElementById("levl3").value= $('.category-list[level=3]>li>a.select').text();
			document.getElementById("levl4").value= $('.category-list[level=4]>li>a.select').text();
			document.getElementById("typeClass").value= $('.category-list[level='+ totalLevel +']>li>a.select').text();
			$("#toaddform").submit();		
		}
			
</script>

</head>
<body>
		<!-- 页面主体开始 -->
		<form id="toaddform"  action="${ctx}/cloud/config/material/toAdd.ht" method="post" >
		<table width="868" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div style="margin: 0">
						<div class="title">
							<h3>
								发布产品
							</h3>
							<ul class="path">
								<li class="active">
									<span>1</span>选择类目
								</li>
								<li>
									<span>2</span>填写产品详情
									
								</li>
								<li>
									<span>3</span>提交成功，等待审核
								</li>
							</ul>
						</div>
						<h4 class="note-cate">
							选择类目
							<span>合适的类目，利于用户搜索到您的产品信息。</span>
						</h4>
						<ul class="choose-tab" id="choose-tab">
							<li class="active">
								<a href="javascript:void(0)" hidefocus="true">全部类目</a>
							</li>
						</ul>

						<div class="choose-category-way" style="">
							<div class="category-columns" id="category-columns" defaults="0">
								<ul class="category-list" level="1">
									
								</ul>
							</div>
						</div>
						<div class="choose-category-way" style="display: none;">
							<ul class="recent-used-category" id="recent-used-category">
							</ul>
						</div>
						<div class="current-category" id="current-category">
						</div>
								<input type="hidden" id="typeClass" value="" name="typeClass">
								<input type="hidden" id="levltype" value="" name="levltype">
								<input type="hidden" id="levlseq" value=""  name="levlseq">
								<input type="hidden" id="levl1" value=""  name="levl1">
								<input type="hidden" id="levl2" value=""  name="levl2">
								<input type="hidden" id="levl3" value=""  name="levl3">
								<input type="hidden" id="levl4" value=""  name="levl4">
						<span class="btn-next" style="display: block; margin: 0 auto;">
								
								<button type="button" id="category-next" onclick="tonext();">
									下一步，填写物品信息
								</button>
						</span>
								
						
					</div>
					</div>
				</td>
			</tr>
		</table>
		</form>
		
</body>

</html>