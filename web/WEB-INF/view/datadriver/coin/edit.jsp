<%--
  Created by IntelliJ IDEA.
  User: holly
  Date: 2018/9/23
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title>积分编辑</title>
    <%@include file="/commons/include/form.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript">
        $(function() {
            var options={};
            if(showResponse){
                options.success=showResponse;
            }
            var frm=$('#scoreForm').form();
            $("a.save").click(function() {
                if(frm.valid()){
                    frm.setData();
                    frm.ajaxForm(options);
                    form.submit();
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
                        window.location.href = "${ctx}/datadriver/coin/list.ht";
                    }
                });
            } else {
                $.ligerMessageBox.error("提示信息",obj.getMessage());
            }
        }

    </script>
</head>
<body>
<div class="panel">
    <div class="panel-top">
        <div class="tbar-title">
            <span class="tbar-label">编辑积分</span>
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
        <form id="scoreForm" method="post" action="save.ht">
            <table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
                <tr><th style="width:120px;">用户名</th><td colspan="3"><input size="35" id="userName" name="userName" value="${bizDef.userName}"  class="inputText" disabled="disabled" validate="{required:true,maxlength:96}"></td></tr>
                <tr><th style="width:120px;">积分类型</th><td><input size="35" id="scoreType" name="scoreType" value="${bizDef.scoreType}"  class="inputText" validate="{required:true,maxlength:96}"/></td></tr>
                <tr><th style="width:120px;">积分总量</th><td><input size="35" id="scoreTotal" name="scoreTotal" value="${bizDef.scoreTotal}"  class="inputText" validate="{required:true,maxlength:96}"/></td></tr>
            </table>
            <input size="35" type="hidden" name="id" value="${bizDef.id}" />
            <input size="35" type="hidden" name="userId" value="${bizDef.userId}" />
            <input size="35" type="hidden" name="scoreAction" value="${bizDef.scoreAction}" />
            <input size="35" type="hidden" name="userName" value="${bizDef.userName}" />
            <input size="35" type="hidden" name="crtTime" value="${bizDef.crtTime}" />
            <input size="35" type="hidden" name="udpTime" value="${bizDef.udpTime}" />
            <input size="35" type="hidden" name="orgName" value="${bizDef.orgName}" />
            <input size="35" type="hidden" name="orgId" value="${bizDef.orgId}" />
        </form>

    </div>
</div>
</body>
</html>
