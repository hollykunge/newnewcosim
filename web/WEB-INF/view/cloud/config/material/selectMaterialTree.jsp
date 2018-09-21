<%@page import="com.casic.cloud.model.config.materialCatalog.MaterialCatalog"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>物品分类管理</title>
	<%@include file="/commons/include/form.jsp" %>
	<base target="_self"/> 
	<link rel="stylesheet" href="${ctx }/js/tree/v35/zTreeStyle.css" type="text/css" />
	<script type="text/javascript"	src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.excheck-3.5.min.js"></script> 
	<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.exedit-3.5.min.js"></script> 
	<script type="text/javascript">
		//树节点是否可点击
		var treeNodelickAble=true; 
		$(function()
		{
			loadTree();
			layout();
			 
		});
	 
		//刷新
		function refreshNode(){
			var selectNode=getSelectNode();
			reAsyncChild(selectNode);
		};
		
		//布局
		function layout(){
			$("#layout").ligerLayout( {
				leftWidth : 180,
				height:"98%",
				onHeightChanged: heightChanged,
				allowLeftResize:false
			});
			//取得layout的高度
	        var height = $(".l-layout-center").height();
	        $("#positionTree").height(height-60);
		};
		//布局大小改变的时候通知tab，面板改变大小
	    function heightChanged(options){
	     	$("#positionTree").height(options.middleHeight - 60);
	    };

		//树
		var positionTree;
		//加载树
		function loadTree(){
			
			var setting = {
				data: {
					key : {
						
						name: "name",
						title: "name"
					},
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId",
						rootPId: <%=MaterialCatalog.ROOT_PID%>
					}
				},
				async: {
					enable: true,
					url:"${ctx}/cloud/config/materialCatalog/getChildTreeData.ht",
					autoParam:["id","parentId"],
					dataFilter: filter
				},
				callback:{
					onClick: zTreeOnLeftClick,
					beforeClick:zTreeBeforeClick,
					onAsyncSuccess: zTreeOnAsyncSuccess
				}
			};
			positionTree=$.fn.zTree.init($("#positionTree"), setting);
			treeNodelickAble=true;
		};
	
		//刷新节点
		function reAsyncChild(targetNode){
			var posId=targetNode.posId;
			if(posId==<%=MaterialCatalog.ROOT_ID%>){
				loadTree();
			}else{
				positionTree = $.fn.zTree.getZTreeObj("positionTree");
				positionTree.reAsyncChildNodes(targetNode, "refresh", true);
			}
			treeNodelickAble=true;
		};
		
		//判断是否为子结点,以改变图标	
		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			if(treeNode){
		  	 var children=treeNode.children;
			  	 if(children.length==0){
			  		treeNode.isParent=true;
			  		positionTree = $.fn.zTree.getZTreeObj("positionTree");
			  		positionTree.updateNode(treeNode);
			  	 }
			}
		};
		
		//过滤节点,默认为父节点,以改变图标	
		function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				for (var i=0, l=childNodes.length; i<l; i++) {
					if(!childNodes[i].isParent){
						childNodes[i].isParent = true;
						  
					}else{
					} 
				}
				return childNodes;
		};
		 
		//左击前
		function zTreeBeforeClick(treeId, treeNode, clickFlag){
			return treeNodelickAble;
		};
	 
	 
		//左击
		function zTreeOnLeftClick(event, treeId, treeNode){
			var returnUrl="${ctx}/cloud/config/material/myMaterialList.ht?catalog_id="+treeNode.id;
			 
			$("#listFrame").attr("src",returnUrl);
		};
	 
		//展开收起
		function treeExpandAll(type){
			positionTree = $.fn.zTree.getZTreeObj("positionTree");
			positionTree.expandAll(type);
		};
		//异步加载展开
		function treeExpand() {
	 
			posTree = $.fn.zTree.getZTreeObj("positionTree");
			var treeNodes = posTree.transformToArray(posTree.getNodes());
			for(var i=1;i<treeNodes.length;i++){
			 
				if(treeNodes[i].children){
					posTree.expandNode(treeNodes[i], true, false, false);
				}
			}
		};
	 
		//选择岗位
		function getSelectNode()
		{
			positionTree = $.fn.zTree.getZTreeObj("positionTree");
			var nodes  = positionTree.getSelectedNodes();
			var node   = nodes[0];
			 
			return node;
		}
		
		function _callBackMyFriends3(names,codes,ids,units,prices,models){
    		 window.parent._callBackMaterialTrees( names,codes,ids,units,prices,models);
    	}
	</script>

<style type="text/css">
html,body{ padding:0px; margin:0; width:100%;height:100%;overflow: hidden;}
.tree-title{overflow:hidden;width:8000px;}
</style>

</head>

<body>

<div id="layout">
	<div position="left" title="物品分类管理">
		<div class="tree-toolbar">
			<span class="toolBar">
				<div class="group"><a class="link reload" id="treeFresh" href="javascript:loadTree();">刷新</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link expand" id="treeExpandAll" href="javascript:treeExpand()">展开</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a></div>
			</span>
		</div>
		<div id="positionTree" class="ztree"></div>
	</div>
	
	<div position="center">
		<iframe id="listFrame" src=""  frameborder="no" width="100%" height="100%"></iframe>
	</div>
</div>
</body>
</html>

