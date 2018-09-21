<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<title>注册_主页</title>
<link href="${ctx}/skins/validform.css"  rel="stylesheet"/>
<script type="text/javascript">
//var istrue = 0;
$(function(){
	//$(".registerform").Validform();  //就这一行代码！;
	/*$(".registerform").submit(function(){
		alert(istrue)
		if(istrue==0){
			alert("注册信息有误，请验证以后再提交！")
			return false;
		}else{
			return true;
		}
	});*/
	
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
						type : 'post',
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
						type : 'post',
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
	}	
}
</script>

</head>
	
<body>
<div id="all">
	<%@include file="/commons/cloud/top.jsp"%>
	
	<!--注册会员-->
	<div class="bggraybox">
		<h2>注册会员</h2>
		<div  class="userform userform_left">
			<form id="ff" action="${ctx}/regPost.ht" method="post" class="registerform">
			<table>
				<tr>
					<td align="left" width="70"><font>注册邮箱：</font></td>
					<td><input type="text" value="" name="email" id="email" class="text" datatype="ajaxEmail"/></td>	
				</tr>
				<tr>
					<td align="left"><font>企业名称：</font></td>
					<td><input name="name" type="text" class="text" id="name" value="" datatype="ajaxName"/></td>	
				</tr>
				<tr>
					<td align="left"><font>所属行业：</font></td>
					<td>
						<ap:selectDB name="industry" styleClass="input_010" style="width:100px;" id="industry" where="parentId=10000003470025" optionValue="itemValue"
							optionText="itemName" table="SYS_DIC" selectedValue="${info.industry2}">
						</ap:selectDB>
						<ap:ajaxSelect srcElement="industry" url="${ctx}/cloud/config/info/getCascadeJsonData.ht" targetElement="industry2"/>
		
				        <ap:selectDB name="industry2" styleClass="input_010" style="width:100px;" id="industry2" 
				        	where="1!=1" optionValue="itemValue" optionText="itemName" table="SYS_DIC"  selectedValue="${info.industry2}">
						</ap:selectDB>
					</td>	
				</tr>
				<tr>
					<td align="left"><font>所属国家：</font></td>
					<td>
						<ap:selectDB styleClass="input_010" name="country" id="country" where="parentId=10000000180001" optionValue="itemValue" optionText="itemName" table="SYS_DIC"></ap:selectDB>
					</td>	
				</tr>
				<tr>
					<td align="left"><font>企业规模：</font></td>
					<td>
						<ap:selectDB styleClass="input_010" name="scale" id="scale" where="typeId=10000005680006" optionValue="itemValue" optionText="itemName" table="SYS_DIC">
						</ap:selectDB>
					</td>	
				</tr>
				<tr>
					<td align="left"><font>公司地址：</font></td>
					<td><input name="address" type="text" class="text" id="address" value=""  datatype="*"/></td>	
				</tr>
				<tr>
					<td align="left"><font>邮编：</font></td>
					<td><input name="postcode" type="text" class="text" id="postcode" value=""  datatype="p"/></td>	
				</tr>
				<tr>
					<td align="left"><font>联系人：</font></td>
					<td><input name="connecter" type="text" class="text" id="connecter" value="" datatype="*"/></td>	
				</tr>
				<tr>
					<td align="left"><font>手机号码：</font></td>
					<td><input type="text" value="" name="tel" class="text" datatype="m"  /></td>	
				</tr>
				<tr>
					<td align="left"><font>传真号码：</font></td>
					<td><input name="fax" type="text" class="text" id="fax" value="" datatype="*"/></td>	
				</tr>
				<tr>
					<td align="left"><font>座机号码：</font></td>
					<td><input name="homephone" type="text" class="text" id="homephone" value="" datatype="*"/></td>	
				</tr>
				<tr>
					<td colspan="2"><font>是否接受服务条款：</font>
					<input type="checkbox" name="isAccept" id="isAccept" datatype="*"/>
      				<a href="${ctx}/law.jsp" style="font-size:12px;" target="_blank">查看条款</a>
					</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td>
						<input type="image" src="skins/btn_submit.jpg"/>
						<input name="button" type="reset" id="btn_reset" value="重&nbsp;&nbsp;置" style="vertical-align:top">
					</td>
				</tr>
			</table>
			</form>
		</div>
		
		<div class="userform_right">
			<p><strong>已经注册过账号了？</strong></p>
			<p>即刻登录。</p>
			<p><a href="javascript:void(0)" target="_blank"><img src="skins/btn_login.jpg" /></a></p><br />
			<p><img width="336" src="skins/bg_regs.jpg" /></p>
		</div>
		<div class="clear"></div>
	</div>
	
	<br /><br /><br /><br />
<!--all结束-->
</div>
			
<%@ include file="/commons/cloud/foot.jsp"%>
</body>
</html>
