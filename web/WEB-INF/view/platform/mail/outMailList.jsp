<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>邮件</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js"></script>
<script type="text/javascript">
	$(function(){
		$("div.group > a.link.del").unbind("click");
		delSelect();
	});
	function emailsync(type){
		var outMailSetId=$('#outMailSetId').val();
		if(type!=1){
			$.ligerMessageBox.warn("提示信息","请先选择一个邮箱的收件箱!");
		}else{
			$.ligerDialog.waitting("正在同步,请耐心等候...");
			$.post("sync.ht",{id:outMailSetId,types:type},function(data){
				$.ligerDialog.closeWaitting();
				var obj=new com.hotent.form.ResultMessage(data);
				if(obj.isSuccess()){
					$.ligerMessageBox.success("成功",obj.getMessage(),function(){
						window.location.href="list.ht?id="+outMailSetId+"&types=1";
					});
				}else{
					
					$.ligerDialog.err('出错信息',"同步邮件出错!",obj.getMessage());
				}
				
			});
		}};
	
	function delSelect(){
		$("div.group > a.link.del").click(function(){
			var outMailSetId=$("#outMailSetId").val();
			var type=$("#types").val();
			var tip="";
			var action=$(this).attr("action");
			var $aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
			if($aryId.length==0){
				$.ligerMessageBox.warn("请选择记录！");
				return false;
			}
			var delId="";
			var len=$aryId.length;
			$aryId.each(function(i){
				var obj=$(this);
				if(i<len-1){
					delId+=obj.val() +",";
				}else{
					delId+=obj.val();
				}
			});
			var url=action +"?mailId=" +delId +"&outMailSetId="+outMailSetId+"&types="+type;
			if(type==1){
				tip="确认要将邮件移至垃圾箱？";
			}else{
				tip='确认要彻底删除邮件吗？';
			}
			$.ligerMessageBox.confirm('提示信息',tip,function(rtn) {
				if(rtn) {
					var form=new com.hotent.form.Form();
					form.creatForm("form", action);
					form.addFormEl("mailId", delId);
					form.addFormEl("outMailSetId",outMailSetId);
					form.addFormEl("types",type);
					form.submit();
				}
			});
			return false;
		});
	}
		
	function emailedit(type){
		var defaultMail='${outMailUserSet.userName}';
		if(defaultMail==null||defaultMail.trim()==""){
			$.ligerMessageBox.confirm("提示信息","请先设置默认邮箱<br/>或者在'邮箱配置管理里设置'!",function(rtn){
				if(rtn){
					window.location.href="../../mail/outMailUserSeting/edit.ht";
				}
			});
		}else{
			window.location.href="edit.ht";
		}
	}
</script>
</head>
<body>
	<input name="outMailSetId" id="outMailSetId" type="hidden" value="${outMailUserSet.id}"/>
	<input name="types" id="types" type="hidden" value="${types}"/>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">${outMailUserSet.userName}(${outMailUserSet.mailAddress})
					<c:choose>
						<c:when test="${types==1}">收件箱</c:when>
						<c:when test="${types==2}">发件箱</c:when>
						<c:when test="${types==3}">草稿箱</c:when>
						<c:otherwise>垃圾箱</c:otherwise>
					</c:choose> 
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link addMessage " href="javascript:void(0)"  onclick="emailedit(${types})">写邮件</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link reload" onclick="emailsync(${types})"" id="emailsync">同步邮件</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  id="deleteBtn" action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">主题:</span><input type="text"  width="500" name="Q_title_SL"  class="inputText"  value="${param['Q_title_SL']}"/>
						<span class="label">日期 从:</span> <input  name="Q_beginmailDate_DL"  class="inputText date" value="${param['Q_beginmailDate_DL']}"/>
						<span class="label">至: </span><input  name="Q_endmailDate_DG" class="inputText date" value="${param['Q_endmailDate_DG']}"/>
					    <c:if test="${types==1}">
					    <span class="label">是否已读:</span>
                           <select name="Q_isRead_S" value="${param['Q_isRead_S']}">
	                            <option value="">全部</option>
	                            <option value="1" <c:if test="${param['Q_isRead_S'] == 1}">selected</c:if>>已读</option>
	                            <option value="0" <c:if test="${param['Q_isRead_S'] == 0}">selected</c:if>>未读</option>
                           </select></c:if>                                                																							
					</div>
					<input type="hidden" name="id" value="${outMailSetId}"/>
					<input type="hidden" name="types" value="${types}"/>
				</form>
			</div>
		</div>
		<div class="panel-body">
				<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="outMailList" id="outMailItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="mailId" value="${outMailItem.mailId}">
					</display:column>
					<display:column property="senderAddresses" title="发件人" sortable="true" sortName="senderAddresses" style="width:80px;"></display:column>
					<display:column property="title" title="主题" sortable="true" sortName="title" style="width:380px;"></display:column>
					<display:column  title="日期" sortable="true" sortName="mailDate"  style="width:50px;">
						<fmt:formatDate value="${outMailItem.mailDate}" pattern="yyyy-MM-dd"/>
					</display:column>
					<c:if test="${types==1}">
						<display:column  title="是否已读" sortable="true" sortName="isRead" style="text-align:center;width:30px;">
					      <c:choose>
						   <c:when test="${outMailItem.isRead==0 }">
							<span class="red">未读</span>
						   </c:when>
						   <c:otherwise>
						   	<span class="green">已读</span>
						   </c:otherwise>
					       </c:choose>
				         </display:column>
			         </c:if>
					<display:column title="管理" media="html" style="width:180px;text-align:center">
						<c:choose>
						 	<c:when test="${outMailItem.types==4 }">
								<a href="del.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}&types=${outMailItem.types}" id="delEnd" class="link del">彻底删除</a>
							</c:when>
							<c:when test="${outMailItem.types==3 }">
								<a href="edit.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}&types=${outMailItem.types}" id="delEnd" class="link edit">编辑</a>
								<a href="del.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}&types=${outMailItem.types}" class="link del">删除</a>
							</c:when>
							<c:otherwise>
								<a href="del.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}&types=${outMailItem.types}" class="link del">删除</a>
							</c:otherwise>
						</c:choose>
						<a href="get.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}" class="link preview">查看</a>
					</display:column>
				</display:table>
				<hotent:paging tableId="outMailItem"/>
			
		</div>				
	</div>
</body>
</html>


