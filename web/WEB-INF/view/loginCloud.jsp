<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/cloud/global.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>登录页面</title>
    <%@include file="/commons/cloud/meta.jsp" %>
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet"/>
    <link href="${ctx}/newtable/bootstrap-responsive.min.css" rel="stylesheet"/>
    <link href="${ctx}/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="${ctx}/loginStyle/form-elements.css" rel="stylesheet"/>
    <link href="${ctx}/loginStyle/style.css" rel="stylesheet"/>
    <%--<link href="${ctx}/skins/validform.css" rel="stylesheet"/>--%>
    <%--<link type="text/css" rel="stylesheet" href="${ctx}/styles/default/css/login.css"/>--%>
    <script type="text/javascript">
        if (top != this) {//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
            top.location = '<%=request.getContextPath()%>/loginCloud.ht';
        }
        $(document).ready(function () {
            $('[data-toggle="popover"]').popover()
            if (${not empty errMsg})
                $("#isRight").append('<div class="alert alert-danger" role="alert">${errMsg}</div>');
        });

        function submitForm() {
            var loginform = document.forms["loginform"];
            loginform.submit();

        }


    </script>
</head>
<body>
<div class="top-content">
    <div class="inner-bg">
        <div class="container">

            <div class="row">
                <div class="col-xs-7">
                    <div class="well" style="height: 700px">
                        <h2><strong>提示：</strong></h2>
                        <p><h3>建议使用Chrome浏览器，以获得更好的体验</h3></p>
                        <br>
                        <h2><strong>更新内容：</strong></h2>
                        <p><h3>更新内容现已移到<strong>用户帮助中心</strong>。</h3></p>

                    </div>
                </div>
                <div class="col-xs-5 text">
                    <h1><strong>二部协同设计平台</strong></h1>
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>平台登录</h3>
                            <p>请输入您的用户名和密码:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" action="${ctx}/loginCloudPost.ht" method="post" class="login-form"
                              id="loginform">
                            <div class="form-group">
                                <label class="sr-only" for="form-username">用户名</label>
                                <input type="text" name="account" placeholder="用户名"
                                       class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">密 码</label>
                                <input type="password" name="password" placeholder="密 码"
                                       class="form-password form-control" id="form-password">
                            </div>
                            <button type="submit" class="btn btn-primary" onclick="submitForm()">进入</button>
                            <input type="hidden" name="orgSn" id="orgSn" value="100"/>
                        </form>
                    </div>

                    <h3>问题反馈:</h3>
                    <div class="social-login-buttons">
                        <a class="btn btn-link-1 btn-link-1-facebook" href="javascript:void(0)"
                           data-container="body"
                           data-trigger="focus" data-toggle="popover" data-placement="top"
                           data-content="办公室：27446 联系人：杜宇坤">
                            <i class="glyphicon glyphicon-phone-alt"></i> 电话
                        </a>
                        <a class="btn btn-link-1 btn-link-1-twitter" href="javascript:void(0)" data-container="body"
                           data-trigger="focus" data-toggle="popover" data-placement="top"
                           data-content="别着急，信息中心攻城狮正玩命加班中...">
                            <i class="glyphicon glyphicon-comment"></i> 消息
                        </a>
                        <a class="btn btn-link-1 btn-link-1-google-plus" href="javascript:void(0)"
                           data-container="body"
                           data-trigger="focus" data-toggle="popover" data-placement="top"
                           data-content="内网邮件发送：王准忠">
                            <i class="glyphicon glyphicon-envelope"></i> 邮件
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
<script src="${ctx}/newtable/bootstrap.js"></script>
<script src="${ctx}/loginStyle/js/jquery.backstretch.min.js"></script>
<script src="${ctx}/loginStyle/js/scripts.js"></script>
</html>
