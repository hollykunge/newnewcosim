<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>协同设计</title>
  <script type="text/javascript">
      function getQueryString(name){
          var value = "";
          var isme = false
          try {
              isme = JSInteraction.isme();
              switch (isme){
                  case 1:
                      value = "/datadriver/personaltask/list.ht";
                      break;
                  case 2:
                      value = "/datadriver/modelcenter/list.ht";
                      break;
                  case 6://任务管理
                      value = "/datadriver/personaltask/list.ht";
                      break;
                  case 4://数据中心
                      value = "/datadriver/datacenter/list.ht";
                      break;
                  case 5://项目管理
                      value = "/datadriver/project/list.ht";
                      break;
                  default:
                      break;
              }

              // value = parent.document.getElementById("ife").contentWindow.location.href;
              // alert(parent.document.getElementById("ife").contentWindow.location.href);
          }
          catch (e) {
              var url = top.location.href;
              url = url.substring(url.indexOf("?")+1);
              var paras = url.split("&");
              var params = {};
              for(var i=0;i<paras.length;i++){
                  var str = paras[i];
                  var l = str.indexOf("=");
                  params[str.substring(0,l)] = str.substring(l+1);
              }

              value = params[name];
              if(typeof(value) == "undefined")
                  return "";
          }

          return value;
      }

      var target = getQueryString('target');
      if(target){
          location.href='${ctx}' + target;
      }
  </script>

</head>
<body>
欢迎页面
</body>
</html>
