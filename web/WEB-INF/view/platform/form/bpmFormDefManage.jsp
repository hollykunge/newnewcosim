<%@page import="com.hotent.platform.model.system.GlobalType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.hotent.platform.model.bpm.BpmDefRights"%>
<%@include file="/commons/include/html_doctype.html"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>表单定义管理</title>
	<%@include file="/commons/include/form.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/tree/v35/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/GlobalType.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormDefMenu.js"></script>
	<style type="text/css">
	.tree-title{overflow:hidden;width:100%;}
	html,body{overflow: visible;} 
</style>
	<script type="text/javascript">
		var catKey="<%=GlobalType.CAT_FORM%>";
		var curMenu=null;
		
		var globalType=new GlobalType(catKey,"glTypeTree",{onClick:onClick,onRightClick:zTreeOnRightClick,expandByDepth:1});
		var formDefMenu=new FormDefMenu();
				
		function onClick(treeNode){
			var url="${ctx}/platform/form/bpmFormDef/list.ht";
			if(treeNode.isRoot==undefined){
				var typeId=treeNode.typeId;
				url+="?categoryId="+typeId;
			}
			$("#defFrame").attr("src",url);
		}
				
		function hiddenMenu(){
			if(curMenu){
				curMenu.hide();
			}
		}
		
		$(function (){
		  	//布局
		    $("#defLayout").ligerLayout({ leftWidth:210,height: '100%',allowLeftResize:false});
		  	$(document).click(hiddenMenu);
			globalType.loadGlobalTree();
		});
		        
		function handler(item){
		 	hiddenMenu();
		 	var txt=item.text;
		 	switch(txt){
		 		case "增加分类":
		 			globalType.openGlobalTypeDlg(true);
		 			break;
		 		case "编辑分类":
		 			globalType.openGlobalTypeDlg(false);
		 			break;
		 		case "删除分类":
		 			globalType.delNode();
		 			break;
		 	}
		}
		/**
		 * 树右击事件
		 */
		function zTreeOnRightClick(event, treeId, treeNode) {
			if (treeNode) {
				globalType.currentNode=treeNode;
				globalType.glTypeTree.selectNode(treeNode);
				curMenu=formDefMenu.getMenu(treeNode, handler);
				curMenu.show({ top: event.pageY, left: event.pageX });
			}
		};
		//展开收起
		function treeExpandAll(type){
			globalType.treeExpandAll(type);
		};

		// function reinitIframe(){
  //               var iframe = document.getElementById("defFrame");
  //               iframe.height = 600;
  //               try{
  //               var bHeight = iframe.contentWindow.document.body.scrollHeight;
  //               var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
  //               var height = Math.max(bHeight, dHeight);
                
  //               iframe.height = height;
  //               $("#defcenter").height(height);
  //               $("#defconfcenter").height(height);
  //               $("#defconfleft").height(height);
  //               $("#defLayout").height(height);              

  //               // IframeHeight = height;
  //               // $("#all").height(IallHeight - $("#manager_right").height() + IframeHeight - 50);
                
  //               }catch (ex){}
  //           }
  //       window.setInterval("reinitIframe()", 500);
        function SetWinHeight(obj) 
            { 
                var win=obj; 
                if (document.getElementById) 
                { 
                    if (win && !window.opera) 
                    { 
                        if (win.contentDocument && win.contentDocument.body.offsetHeight) 
                            win.height = win.contentDocument.body.offsetHeight + 10; 
                        else if(win.Document && win.Document.body.scrollHeight) 
                            win.height = win.Document.body.scrollHeight + 10; 
                    }
                    // console.log("第二层iframe的高度为" + win.height);
                    // window.parent.document.getElementById("manager_right").height = win.height;
                    $("#defcenter").height(win.height);
                    $("#defconfcenter").height(win.height);
                    // $("#defconfleft").height(win.height);
                    $("#defLayout").height(win.height);
                    $("#all").height(win.height);     
                } 
            } 
	</script>
</head>
<body>
	<div class="panel" id="all">
		<div id="defLayout" class="panel-top">

			<div position="left" title="表单分类管理" style="overflow: auto;float:left;width:100%">
				<div class="tree-toolbar">
					<span class="toolBar">
						<div class="group">
							<a class="link reload" id="treeFresh" href="javascript:globalType.loadGlobalTree();">刷新</a>
						</div>
						<div class="l-bar-separator"></div>
						<div class="group">
							<a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)">展开</a>
						</div>
						<div class="l-bar-separator"></div>
						<div class="group">
							<a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a>
						</div>
					</span>
				</div>
				<ul id="glTypeTree" class="ztree"></ul>
			</div>

			<div position="center" id="defcenter">

				<iframe id="defFrame" frameborder="0" scrolling="no" src="${ctx}/platform/form/bpmFormDef/list.ht" 
                style="width:100%;" onload="Javascript:SetWinHeight(this)"></iframe>
			</div>
		</div>
	</div>
</body>
</html>