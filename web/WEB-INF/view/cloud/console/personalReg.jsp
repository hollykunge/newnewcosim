<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设计师注册页面</title>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<style type="text/css">
.n256 {
    height: 490px;
</style>

<script type="text/javascript">

	//var istrue = 0;


$(function(){
	
	var indus = $("#industry").val();
	$.ajax({
		type : 'post',
		dataType : 'json',
		url : '${ctx}/cloud/config/info/getCascadeJsonData.ht',
		data : {value : indus },
		success : function(dics){
			var rows=dics;
			$('#industry2').html('');
			var opt ='';
			for(var i=0;i<rows.length;i++){
				var s ='';
				var iv =rows[i].itemValue + '';
				$('#industry2').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
			};
		}
	});
	
	var demo=$(".registerform").Validform({
		tiptype:3,
		label:".label",
		showAllError:true,
		datatype:{
			"ajaxEmail":function(gets,obj,curform,regxp){				
				var regEMail = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
				if(regEMail.test(gets)){					
					$.ajax({//验证唯一性
						type :'post',
						url : '${ctx}/cloud/system/enterprises/emailValid.ht',
						data : {email : gets},
						success : function(istf){
							if(istf=='true'){
								//istrue = 1;
								return true;
							}else{
								$('#email').parent().find('.Validform_checktip').removeClass('Validform_right').addClass('Validform_wrong').text('邮箱已被占用');
								return false;
							}
						},failure : function(){	return false; }
					});	
				}else{
					return false;
				}						
			},
			"ajaxName":function(gets,obj,curform,regxp){	
				if(gets!=''){				
					$.ajax({//验证唯一性
						type :'post',
						url : '${ctx}/cloud/system/enterprises/nameValid.ht',
						data : {name : gets},
						success : function(istf){
							if(istf=='true'){
								//istrue = 1;
								return true;
							}else{
								$('#name').parent().find('.Validform_checktip').removeClass('Validform_right').addClass('Validform_wrong').text('名称已被占用');
								return false;
							}
						},failure : function(){	return false; }
					});	
				}else{
					return false;
				}			
			}
		}
	});
})

function resetFrm(){
	if(confirm('您确定要重置吗?')){
		$('#ff').find('input[type=text]').val('');
		var radioVal=document.getElementsByName("sex");
		radioVal[0].checked=true;
	}	
}

var dd = null;
function selectIcon(type){
	if(type=='logo'){
		var urlShow = '${ctx}/cloud/pub/image/toUpload.ht?_callback=' + type;
		dd = $.ligerDialog.open({ url:urlShow, height: 350,width: 500, title :'图片上传器', name:"frameDialog_",
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.close(); } } ] 
		});
	}else{
		var urlShow = '${ctx}/cloud/pub/personalRegtoUpload.ht?_callback=' + type;
		dd = $.ligerDialog.open({ url:urlShow, height: 130,width: 500, title :'文件上传器', name:"frameDialog_",
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.close(); } } ] 
		});
	}
};

function _callbackImageUploadSucess(src,type){
	dd.close();
	if(type =='introduce'){
		$('#introduce').val(src);
	}else if(type =='credential'){
		$('#credential').val(src);
	}
}
</script>
</head>
<body>
<%@include file="/commons/cloud/top.jsp"%>
<!-- 页面主体  开始 -->
<form id="ff" action="${ctx}/personalRegPost.ht" method="post" class="registerform">
<!-- 页面主体  开始 -->
<div id="main">
	<div class="login_title">设计师注册</div>
    	<div class="n256">
  <div class="n2-1">
    
    <div class="n2-2">
      <div class="j01-1">姓名:</div>
      <div class="j01-4">
        <input name="name" type="text" class="input_008" id="name" value="" datatype="ajaxName"/>
      </div>
	</div>
    
    <div class="n2-2">
      <div class="j01-1">性别:</div>
      <div class="j01-4" style="padding-top: 4px">
        <input type="radio" id="sex" name="sex" value="男"  class="inputText" validate="{required:false,number:true }"  checked/><font size="3.5px">男</font>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" id="sex" name="sex" value="女"  class="inputText" validate="{required:false,number:true }"  /><font size="3.5px">女</font>
      </div>
	</div>
	
    <div class="n2-2">
      <div class="j01-1">注册邮箱:</div>
      <div class="j01-4">
        <input type="text" value="" name="email" id="email" class="inputxt" datatype="ajaxEmail"/>
      </div>
    </div>
    
    <div class="n2-2">
      <div class="j01-1">身份证号码:</div>
      <div class="j01-4">
        <input name="IDnumber" type="text" class="input_008" id="IDnumber" value="" datatype="ajaxName"/>
      </div>
	</div>
    
    <div class="n2-2">
      <div class="j01-1">手机号码:</div>
      <div class="j01-4">
        <input name="cellphone" type="text" class="input_008" id="cellphone" value="" datatype="ajaxName"/>
      </div>
	</div>
	
	<div class="n2-2">
      <div class="j01-1">获奖证书:</div>
      <div class="j01-4">
        <input name="credential" type="text" class="input_008" id="credential" value="" datatype="ajaxName"/>
        <a href="javascript:selectIcon('credential');" class="btn-ctrl"
							id="addProductPic" js_tukubtn="true">添加获奖证书</a>
      </div>
	</div>
	
	<div class="n2-2">
      <div class="j01-1">作品介绍:</div>
      <div class="j01-4">
        <input name="introduce" type="text" class="input_008" id="introduce" value="" datatype="ajaxName"/>
        <a href="javascript:selectIcon('introduce');" class="btn-ctrl"
							id="addProductPic" js_tukubtn="true">添加作品</a>
      </div>
	</div>
    
    
    <div class="n2-2">
      <div class="j01-1-1x1">是否接受服务条款:</div>
      <div class="j01-4" style="padding-top:5px;">
      	<input type="checkbox" name="isAccept" id="isAccept" datatype="*"/>
      	<a href="${ctx}/law.jsp" style="font-size:12px;" target="_blank">查看条款</a>
      </div>
    </div>
<div class="n2-2">
      <div class="j01-1"></div>
      <div class="j01-5"><span class="j02"> &nbsp;&nbsp;
        <input name="button" type="submit" class="btn" id="button" value="提  交" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input name="button" type="button" onClick="resetFrm();" class="btn" id="button" value="重  置" />
      </span></div>
    </div>
  </div>
  <div class="n3">
    <div class="n3-1">
      <div class="n3-2"><span class="txtb2">已经注册过账号了？</span></div>
      <div class="n3-3"><span class="txtb">即刻登录。</span></div>
      <div class="n3-3">
        <input name="button" type="button" onClick="location.href='${ctx}/loginCloud.ht'" class="btn" id="button" value="登  录" />
      </div>
      <div class="n3-4"><img src="${ctx}/images/banner2.jpg" width="335" height="92" /></div>
    </div>
  </div>
</div>
</div>
<!-- 页面主体  结束 -->
</form>
<!-- 页面主体  结束 -->
<%@ include file="/commons/cloud/foot.jsp"%>
</body>
</html>
