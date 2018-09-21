<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script>
var isIE=!(navigator.userAgent.indexOf('MSIE')==-1);

function ieVer(){
	var browser=navigator.appName;
	var b_version=navigator.appVersion; 
	var version=b_version.split(";"); 
	var trim_Version=version[1].replace(/[ ]/g,""); 
	if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0"){ 
		return 6;
	} 
	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0"){ 
		return 7;
	} 
	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE8.0"){ 
		return 8;
	} 
	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE9.0"){ 
		return 9;
	}
	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE10.0"){ 
		return 10;
	}
	
	return 10;
}

if(isIE){	
	if(ieVer() < 7){
		alert('您的浏览器版本太低，为了更好的使用本平台，请使用IE8以上版本的浏览器。');
	}
}
</script>