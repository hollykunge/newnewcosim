<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8" import="com.hotent.platform.model.system.Resources"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="skinStyle" value="${skinStyle}" />
<head>
    <title>平台后台管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
	
	
	<f:link href="Aqua/css/ligerui-all.css"></f:link>
	<f:link href="web.css"></f:link>
	<f:link href="index.css"></f:link>
	<f:link href="select.css"></f:link>
	
    
    
    <link rel="stylesheet" href="${ctx}/js/tree/v35/zTreeStyle.css" type="text/css" />    
	<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
   	<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>
   	<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
	<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/base.js"  ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js"  ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js"  ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js"  ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenu.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js"  ></script>
    <script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"  ></script>
    <script type="text/javascript">
		    if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
				  top.location='<%=request.getContextPath()%>/platform/console/main.ht';
			}
    
            var tab = null;
            var tree = null;
            var ctxPath=__ctx;
          
            var setting = {
            		view: {showLine: true,nameIsHTML: true},
            		data: {
						key : {name: "resName"},
						simpleData: {enable: true,idKey: "resId",pIdKey: "parentId"}
					},
            		callback: {onClick: zTreeOnClick}
            };

            $(function (){
                //布局
                $("#layoutMain").ligerLayout({topHeight :80, leftWidth: 180, height: '100%', onHeightChanged: heightChanged });
				//取得layout的高度
                var height = $(".l-layout-center").height();
                $("#leftTree").height(height-45);
                //Tab
                $("#framecenter").ligerTab({ height: height});
				//获取tab的引用
                tab = $("#framecenter").ligerGetTabManager();
				//加载菜单
				loadMenu();
                //隐藏加载对话框
                $("#pageloading").hide();
 
                $("#menuPanel").delegate("a.menuItem", "click", function(){
                    var id=$(this).attr("id");
                    loadTree(id);
                    $(this).siblings().removeClass("menuItem_hover").end().addClass("menuItem_hover");
                    jQuery.setCookie("selectTab",id);
                });
                
                //更多操作的初始化
                var button = $('#loginButton');
        	    var box = $('#loginBox');
        	    var shade=$('#shadeEm');
        	    button.mouseup(function(login) {
        	        box.toggle();
        	        shade.toggle();
        	        button.toggleClass('active');        	  
        		});
        		$(this).mouseup(function(login) {
        			if ($(login.target).hasClass("more")){
        				var prehref=$(login.target).attr('prehref');
        				if(prehref)
       						addToTab(prehref,$(login.target).text(),$(login.target).attr('resid'));
        				button.removeClass('active');
        				box.hide();
        				shade.hide();
        			}
        		});
        		button.blur(function(){
        			setTimeout(function(){
        				box.hide();
        				shade.hide();
        				button.removeClass('active');	
        			},200);
        		});
            });

			//布局大小改变的时候通知tab，面板改变大小
            function heightChanged(options){
            	$("iframe").each(function(){
            		if($(this).attr("name")!=undefined){
            			$(this).height(options.middleHeight-35);
            		}
            	});
				$("#leftTree").height(options.middleHeight-40);
            }
		      
            var aryTreeData=null;
            //返回根节点
            function getRootNodes(){
            	var nodes=new Array();
            	for(var i=0;i<aryTreeData.length;i++){
            		var node=aryTreeData[i];
            		if(node.parentId==0){
            			nodes.push(node);
            		}
            	}
            	return nodes;
            };
            //初始化菜单滚动按钮
            function initRollButton(){	
        		var pWidth = $("div.menuParent").width(),sWidth = $("div.menuPanel").width();
        		if(sWidth<=0)return;		
        		var left = pWidth - sWidth;
        		if (left <= 0) {
        			$(".nav_left").show();
        			$(".nav_right").show();
        		}
        	};
            //加载菜单面板
            function loadMenu(){
                $("#leftTree").empty();
            	//一次性加载
				$.post("${ctx}/platform/console/getSysRolResTreeData.ht",
					 function(result){
						aryTreeData=result;
						for(var i=0;i<result.length;i++){
            				var node=result[i];
            			}
						//获取根节点，加载顶部按钮菜单。
						var headers=getRootNodes();
						var len=headers.length;
						var menuContainer=$("#menuPanel");
						for(var i=0;i<len;i++){
	            			var head=headers[i];
	            			var menuItemHtml=getMenuItem(head);
	            			menuContainer.append($(menuItemHtml));
	            		}
						initRollButton();
						if(len>0){
							var selectTab=jQuery.getCookie("selectTab");
							var obj= $("#" +selectTab);
							if(selectTab && obj.length>0){
								$("#" +selectTab).addClass("menuItem_hover");
								loadTree(selectTab);
							}
							else{
								var head=headers[0];
								var resId=head.resId;
								$("#" +resId).addClass("menuItem_hover");
								loadTree(resId);
							}
						}
					});
            }
            
            function loadTree(resId){
            	var nodes=new Array();
    			getChildByParentId(resId,nodes);
    			var zTreeObj =$.fn.zTree.init($("#leftTree"), setting, nodes);

                var depth = 1; // 默认展开节点层级
    			var nodes = zTreeObj.getNodesByFilter(function(node){
    				return (node.level==depth);
    			});
    			
				if(nodes.length>0){
					for(var idx=0;idx<nodes.length;idx++){
						zTreeObj.expandNode(nodes[idx],false,false);
					}
				}
            }
            
            //加载菜单项
            function getMenuItem(node){
            	var str='<a class="menuItem" id="'+node.resId+'">';
           		if(node.icon!="null" && node.icon!=""){
           			str+='<img src="'+node.icon+'" />';
           		}
           		str+='<span >'+node.resName+'</span></a>';
           		return str;
			}
            
            function getChildByParentId(parentId,nodes){
            	for(var i=0;i<aryTreeData.length;i++){
            		var node=aryTreeData[i];
            		if(node.parentId==parentId){
            			nodes.push(node);
            			getChildByParentId(node.resId,nodes);
            		}
            	}
            };
            
            //处理点击事件
            function zTreeOnClick(event, treeId, treeNode) {
            	var url= treeNode.defaultUrl;
            	if(url!=null && url!='' && url!='null'){
	            	if(!url.startWith("http",false)) url=ctxPath +url;
	            	//扩展了tab方法。
	            	addToTab(url,treeNode.resName,treeNode.resId,treeNode.icon);
            	}
            };
            
            //添加到tab或者刷新
            function addToTab(url,txt,id,icon){
            	if(tab.isTabItemExist(id)){
            		tab.selectTabItem(id);
            		tab.reload(id);
            	}
            	else{
            		tab.addTabItem({ tabid:id,text:txt,url:url,icon:icon});
            	}
            };

          	//切换系统
          	function saveCurrentSys(){
            	var systemId=$("#setSubSystem").val();
        		var form=new com.hotent.form.Form();
        		form.creatForm("form", "${ctx}/platform/console/saveCurrSys.ht");
        		form.addFormEl("systemId", systemId);
        		form.submit();
			}
    		
          	// firefox下切换tab的高度处置
          	$(window).resize(function() {
          		initRollButton();
          		if(navigator.userAgent.indexOf("Firefox")>0){
	       	    	setTimeout( 
	       	    	function(){
	                     $.each($(".l-tab-content-item"),function(idx,obj) {
	                     	if($(this).attr("style").indexOf("hidden")>0){
	                     		$(this).css({"height":"0px"});
	                     	}
	                     });
	       	    	}, 500);
          		}
          	});
     </script> 
<style type="text/css"> 
    html,body{ padding:0px; margin:0; width:100%;height:100%;overflow: hidden;}  
    #pageloading{position:absolute; left:0px; top:0px; background:white url('${ctx}/styles/default/images/loading.gif') no-repeat center; width:100%; height:100%; height:700px; z-index:99999;}
    #top{color:White;height: 80px;}
	#top a{color:white;}
	
 </style>
</head>
<body style="padding:0px;">  
<div id="pageloading"></div>
	<%@include file="main_top.jspf" %>
  <div id="layoutMain" style="width:100%">
        <div position="left" title="<img src='${ctx}/styles/default/images/icon/home.png' >  ${currentSystem.sysName }"> 
	      	<ul id='leftTree' class='ztree' style="overflow:auto;height: 100%" ></ul>
        </div>	 
        
        <div position="center" id="framecenter"> 
            <div tabid="home" title="我的主页" style="height:300px" >
            	<c:if test="${not empty currentSystem.homePage }">
            		<c:choose>
            			<c:when test="${currentSystem.isLocal==1}">
            				<iframe frameborder="0" name="home" src="${ctx}${currentSystem.homePage}"></iframe>
            			</c:when>
            			<c:otherwise>
            				<iframe frameborder="0" name="home" src="${currentSystem.defaultUrl}${currentSystem.homePage}"></iframe>
            			</c:otherwise>
            		</c:choose>
            	</c:if>
            </div> 
        </div> 
    </div>    
</body>
</html>
