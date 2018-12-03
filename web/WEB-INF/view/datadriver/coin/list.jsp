<%--
  Created by IntelliJ IDEA.
  User: holly
  Date: 2018/9/23
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>积分列表</title>
    <%@include file="/commons/include/get.jsp" %>
</head>
<body>
<div class="panel">
    <div class="panel-top">
        <div class="tbar-title">
            <span class="tbar-label">积分列表</span>
        </div>
        <div class="panel-toolbar">
            <div class="toolBar">
                <div class="group"><a class="link search" id="btnSearch">查询</a></div>
                <div class="group"><a class="link del" action="del.ht">删除</a></div>
            </div>
            <div class="toolBar" style="float: right;">
                <div class="group"><a class="link search"
                                      href="${ctx}/datadriver/exchange/getMonthRankByType.ht?scoreType=quanju">兑换全局币</a>
                </div>
                <div class="l-bar-separator"></div>
                <div class="group"><a class="link add"
                                      href="${ctx}/datadriver/exchange/getMonthRankByType.ht?scoreType=gongxian">兑换贡献币</a>
                </div>
                <div class="l-bar-separator"></div>
                <div class="group"><a class="link search"
                                      href="${ctx}/datadriver/exchange/getMonthRankByType.ht?scoreType=qiushi">兑换求实币</a>
                </div>
                <div class="l-bar-separator"></div>
                <div class="group"><a class="link detail"
                                      href="${ctx}/datadriver/exchange/getMonthRankByType.ht?scoreType=chuangxin">兑换创新币</a>
                </div>
            </div>
        </div>
        <div class="panel-search">
            <form id="searchForm" method="post" action="list.ht">
                <div class="row">
                    <span class="label">用户名:</span><input type="text" name="Q_userName_SL" class="inputText"
                                                          style="width: 5%;" value="${param['Q_userName_SL']}"/>
                    <span class="label">组织:</span><input type="text" name="Q_orgName_SL" class="inputText"
                                                         style="width: 5%;" value="${param['Q_orgName_SL']}"/>
                    <span class="label">积分动作:</span><input type="text" name="Q_scoreAction_SL" class="inputText"
                                                           style="width: 5%;" value="${param['Q_scoreAction_SL']}"/>
                    <span class="label">积分下限:</span><input type="text" name="Q_scoreTotalLow_L" class="inputText"
                                                           style="width: 5%;" value="${param['Q_scoreTotalLow_L']}"/>
                    <span class="label">积分上限:</span><input type="text" name="Q_scoreTotalHigh_L" class="inputText"
                                                           style="width: 5%;" value="${param['Q_scoreTotalHigh_L']}"/>
                    <span class="label">创建时间:</span><input type="text" name="Q_crtTime_SL" class="inputText"
                                                           style="width: 9%;" value="${param['Q_crtTime_SL']}"/>
                    <span class="label">更新时间:</span><input type="text" name="Q_udpTime_SL" class="inputText"
                                                           style="width: 9%;" value="${param['Q_udpTime_SL']}"/>
                    <span class="label">积分类型:</span>
                    <select name="Q_scoreType_S" class="select" value="${param['Q_scoreType_S']}">
                        <option value="">全部</option>
                        <option value="quanju" <c:if test="${param['Q_scoreType_S'] == 'quanju'}">selected</c:if>>全局
                        </option>
                        <option value="qiushi" <c:if test="${param['Q_scoreType_S'] == 'qiushi'}">selected</c:if>>求实
                        </option>
                        <option value="fengxian" <c:if test="${param['Q_scoreType_S'] == 'fengxian'}">selected</c:if>>
                            奉献
                        </option>
                        <option value="chuangxin" <c:if test="${param['Q_scoreType_S'] == 'chuangxin'}">selected</c:if>>
                            创新
                        </option>
                    </select>
                </div>
            </form>
        </div>
    </div>
    <div class="panel-body">
        <c:set var="checkAll">
            <input type="checkbox" id="chkall"/>
        </c:set>
        <display:table name="scoreList" id="scoreItem" requestURI="list.ht" sort="external" cellpadding="1"
                       cellspacing="1" export="true" class="table-grid">
            <display:column title="${checkAll}" media="html" style="width:30px; text-align: center;">
                <input type="checkbox" class="pk" name="id" value="${scoreItem.id}">
            </display:column>
            <display:column property="userName" title="用户名" sortable="true" sortName="user_name" maxLength="80"
                            style="text-align: center;"></display:column>
            <display:column property="orgName" title="组织" sortable="true" sortName="org_name" maxLength="80"
                            style="text-align: center;"></display:column>
            <display:column property="scoreType" title="积分类型" sortable="true" sortName="score_type"
                            style="text-align: center;"></display:column>
            <display:column property="scoreAction" title="积分动作" sortable="true" sortName="score_action"
                            style="text-align: center;"></display:column>
            <display:column property="scoreTotal" title="积分总量" sortable="true" sortName="score_total" maxLength="80"
                            style="text-align: center;"></display:column>
            <display:column title="创建时间" sortable="true" sortName="crt_time">
                <fmt:formatDate value="${scoreItem.crtTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </display:column>
            <display:column title="更新时间" sortable="true" sortName="udp_time">
                <fmt:formatDate value="${scoreItem.udpTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </display:column>
            <display:column title="管理" media="html" style="width:260px; text-align: center;">
                <a href="del.ht?id=${scoreItem.id}" class="link del">删除</a>
                <a href="edit.ht?id=${scoreItem.id}" class="link edit">编辑</a>
                <a href="detail.ht?id=${scoreItem.id}" class="link detail">明细</a>
            </display:column>
        </display:table>
        <hotent:paging tableId="scoreItem"/>
    </div><!-- end of panel-body -->
</div> <!-- end of panel -->
</body>
</html>
