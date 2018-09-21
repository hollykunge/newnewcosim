<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
        
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<title>业务统计</title>
</head>

<body>
<div id="mainnav2">
	<div id="notice2">
		<ol>
			<li>最新发布商机统计：${chanceCount}条</li>
			<li>最新发生业务量统计：${businessCount}条</li>
		</ol>
	</div>
</div>
</body>
</html>

 