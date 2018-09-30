<%@page import="com.hotent.core.web.controller.BaseController"%>
<%@page import="com.hotent.core.web.ResultMessage"%>
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
		$.ligerMsg.correct('<p><font color="green"><%=_obj_.getMessage()%></font></p>',false,function(){
			$.ligerMsg.close();
		});
	
	<%}
	  else{
	%>
		$.ligerMsg.warn('<p><font color="red"><%=_obj_.getMessage()%></font></p>',false,function(){
			$.ligerMsg.close();
		});
	<%}%>
});
</script>
<%
} %>

