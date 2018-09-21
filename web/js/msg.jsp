<%@page import="com.hotent.core.web.ResultMessage"%>
<%@page import="com.hotent.core.web.controller.BaseController"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lg/plugins/ligerMsg.js"></script>
<%
ResultMessage _obj_=(ResultMessage)session.getAttribute(BaseController.Message);
if(_obj_!=null){
	session.removeAttribute(BaseController.Message);
%>
<script type="text/javascript">
$(function(){
	<%
	  if(_obj_.getResult()==ResultMessage.Success){
	%>
		$.ligerDialog.success('<p><font color="green"><%=_obj_.getMessage()%></font></p>',false,function(){
			$.ligerMsg.close();
		});
	
	<%}
	  else{
	%>
		$.ligerDialog.error('<p><font color="red"><%=_obj_.getMessage()%></font></p>',false,function(){
			$.ligerMsg.close();
		});
	<%}%>
});
</script>
<%
} %>

