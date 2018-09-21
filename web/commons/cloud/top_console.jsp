<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<style>
    #project_search {
        background-color: #007fcf !important;
        border: 1px solid #0065ab !important;
        color: #d6d6d6 !important;
    }
</style>

<script type="text/javascript">
    $(document).ready(function() {

        var value = "";
        var isme = ""
        try {
            isme   = JSInteraction.isme();
            alert(isme);
            if (JSInteraction.isme()) {

                $("#navbariwork").hide();
            }
        }
        catch (e) {
alert(11111);
        }
    });
</script>
<!--主导航菜单 开始-->
<nav id="navbariwork" class="navbar navbar-inverse navbar-fixed-top" style="margin-bottom: 5px">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">协同设计平台</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${ctx}/cloud/console/outline.ht?target=/datadriver/personaltask/mydashboard.ht"><strong style="color: orange; font-size: 40px">Cosim</strong></a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form class="navbar-form navbar-left">
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon3">当前项目</span>
                    <input type="text" class="form-control" id="project_search" aria-describedby="basic-addon3" placeholder="搜索">
                </div>
            </form>

            <ul class="nav navbar-nav">
                <%--<li><a href="${ctx}/cloud/console/home.ht">主页</a></li>--%>
                <c:forEach items="${resourcesList}" var="resourcesItem">
                    <c:if test="${resourcesItem.parentId==0}">
                        <li>
                            <c:choose>
                                <c:when test="${empty resourcesItem.defaultUrl}">
                                    <a href="${ctx}/cloud/console/outline.ht?resId=${resourcesItem.resId}">${resourcesItem.resName}</a>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${fn:indexOf(resourcesItem.defaultUrl,'?')!=-1}">
                                            <a href="${ctx}${resourcesItem.defaultUrl}&resId=${resourcesItem.resId}">${resourcesItem.resName}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${ctx}${resourcesItem.defaultUrl}?resId=${resourcesItem.resId}">${resourcesItem.resName}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
            <%--<form class="navbar-form navbar-right">--%>
            <%--<div class="form-group">--%>
            <%--<input type="text" class="form-control" placeholder="Search">--%>
            <%--</div>--%>
            <%--<button type="submit" class="btn btn-default">搜索</button>--%>
            <%--</form>--%>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <c:if test="${not empty SPRING_SECURITY_LAST_USERNAME}"><a id="username"
                                                                               href="javascript:void(0)"
                                                                               class="dropdown-toggle"
                                                                               data-toggle="dropdown" role="button"
                                                                               aria-haspopup="true"
                                                                               aria-expanded="false">
                        <sec:authentication property="principal.fullname"/> <span class="caret"></span></a></c:if>
                    <ul class="dropdown-menu">
                        <li><a href="${ctx}/loginCloud.ht"><span class="glyphicon glyphicon-log-out"></span> 注销</a></li>
                        <li><a href="${ctx}/cloud/system/news/more.ht"><span class="glyphicon glyphicon-info-sign"></span> 帮助</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<!-- 主导航菜单 结束 -->