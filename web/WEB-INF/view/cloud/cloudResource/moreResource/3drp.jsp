<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/commons/cloud/meta.jsp"%>
<link href="${ctx}/skins/main.css" rel="stylesheet"/>
<script src="${ctx}/skins/jquery.js" type="text/javascript"></script>
<script src="${ctx}/skins/main.js" type="text/javascript"></script>
<title>3D打印服务</title>

<script>
function viewBigImage(a){
	var img = $(a).parent().find('img');
	var src = img.attr('src');
	$('#ii').attr('src',src);
}

function beginPrint(){
	alert("请先安装通用模型浏览工具");
	// location.href='${ctx}/cloud/cloudResource/resourceManagement/moreResource.ht?type=3dprint&fileId=10000023420001';
}

function addNums(){
	$('#nums').val($('#nums').val()*1 + 1);
	$("#price").text('￥ ' + $('#nums').val()*1000);
}

function divNums(){
	if($('#nums').val() != 1){
		$('#nums').val($('#nums').val()*1 -1);
		$("#price").text('￥ ' + $('#nums').val()*1000);
	}
}
</script>
</head>
	
<body>
<div id="all">			
<%@include file="/commons/cloud/top.jsp"%>	
	<div id="model" class="bggraybox">
		<div class="title"><a href="javascript:void(0)">模型制作</a></div>
		<ul>
			<li>
				<div class="left">
				  <div class="left_desc">
				    <h5>
				      <input type="file" name="fileField" id="fileField" />
                    </h5>
                    <h5>
                      <input type="image" src="${ctx}/skins/btn_upload.jpg" onclick="beginPrint();"/>
                    </h5>
					</div>
				</div>
			  <div class="right">
					<h5>&nbsp;&nbsp;材料</h5>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
					    <td width="56%" align="left" valign="middle"><table width="250" border="0" cellspacing="0" cellpadding="0">
					      <tr>
					        <td height="80" align="center" valign="middle"><a href="javascript:void(0);" onClick="viewBigImage(this);"><img src="${ctx}/images/3drp/15.jpg" width="50" height="50" / class="model_imglink"></a></td>
					        <td height="80" align="center" valign="middle"><a href="javascript:void(0);" onClick="viewBigImage(this);"><img src="${ctx}/images/3drp/44.jpg" width="50" height="50" / class="model_imglink" /></a></td>
					        <td height="80" align="center" valign="middle"><a href="javascript:void(0);" onClick="viewBigImage(this);"><img src="${ctx}/images/3drp/201208021455152760_thumbs.jpg" width="50" height="50" / class="model_imglink" /></a></td>
				          </tr>
					      <tr>
					        <td height="80" align="center" valign="middle"><a href="javascript:void(0);" onClick="viewBigImage(this);"><img src="${ctx}/images/3drp/201208021457055728_thumbs.jpg" width="50" height="50" / class="model_imglink" /></a></td>
					        <td height="80" align="center" valign="middle"><a href="javascript:void(0);" onClick="viewBigImage(this);"><img src="${ctx}/images/3drp/201208021501088853_thumbs.jpg" width="50" height="50" / class="model_imglink" /></a></td>
					        <td height="80" align="center" valign="middle"><a href="javascript:void(0);" onClick="viewBigImage(this);"><img src="${ctx}/images/3drp/201208021502122291_thumbs.jpg" width="50" height="50" / class="model_imglink" /></a></td>
				          </tr>
					      <tr>
					        <td height="80" align="center" valign="middle"><a href="javascript:void(0);" onClick="viewBigImage(this);"><img src="${ctx}/images/3drp/201208021502479166_thumbs.jpg" width="50" height="50" / class="model_imglink" /></a></td>
					        <td height="80" align="center" valign="middle"><a href="javascript:void(0);" onClick="viewBigImage(this);"><img src="${ctx}/images/3drp/201208021503190885_thumbs.jpg" width="50" height="50" / class="model_imglink" /></a></td>
					        <td height="80" align="center" valign="middle"><a href="javascript:void(0);" onClick="viewBigImage(this);"><img src="${ctx}/images/3drp/201208021506517603_thumbs.jpg" width="50" height="50" / class="model_imglink" /></a></td>
				          </tr>
					      <tr>
					        <td height="30" colspan="3" align="center" valign="middle">&nbsp;</td>
				          </tr>
				        </table></td>
					    <td width="9%" align="center" valign="middle"><img src="${ctx}/skins/modelimg02.jpg" width="8" height="15" /></td>
					    <td width="35%" align="left" valign="middle"><table width="150" border="0" cellspacing="0" cellpadding="0">
					      <tr>
					        <td height="20" align="left" valign="middle" class="model_tablefont">价格</td>
				          </tr>
					      <tr>
					        <td height="40" align="left" valign="middle"><img src="${ctx}/skins/model_jg.jpg" width="15" height="19" /><img src="${ctx}/skins/model_jg.jpg" width="15" height="19" /><img src="${ctx}/skins/model_jg.jpg" width="15" height="19" /><img src="${ctx}/skins/model_jg2.jpg" width="15" height="19" /><img src="${ctx}/skins/model_jg2.jpg" width="15" height="19" /><img src="${ctx}/skins/model_jg2.jpg" width="15" height="19" /></td>
				          </tr>
					      <tr>
					        <td height="20" align="left" valign="middle" class="model_tablefont">强度</td>
				          </tr>
					      <tr>
					        <td height="40" align="left" valign="middle"><img src="${ctx}/skins/model_qd.jpg" width="15" height="19" /><img src="${ctx}/skins/model_qd.jpg" width="15" height="19" /><img src="${ctx}/skins/model_qd.jpg" width="15" height="19" /><img src="${ctx}/skins/model_qd2.jpg" width="15" height="19" /><img src="${ctx}/skins/model_qd2.jpg" width="15" height="19" /><img src="${ctx}/skins/model_qd2.jpg" width="15" height="19" /></td>
				          </tr>
					      <tr>
					        <td height="20" align="left" valign="middle" class="model_tablefont">细节</td>
				          </tr>
					      <tr>
					        <td height="40" align="left" valign="middle"><img src="${ctx}/skins/model_xj.jpg" width="15" height="19" /><img src="${ctx}/skins/model_xj.jpg" width="15" height="19" /><img src="${ctx}/skins/model_xj.jpg" width="15" height="19" /><img src="${ctx}/skins/model_xj2.jpg" width="15" height="19" /><img src="${ctx}/skins/model_xj2.jpg" width="15" height="19" /><img src="${ctx}/skins/model_xj2.jpg" width="15" height="19" /></td>
				          </tr>
				        </table></td>
				      </tr>
				  </table>
                <h5>&nbsp;&nbsp;材料文字</h5>
                  
			    <table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
					    <td width="56%" height="106" align="center" valign="middle"><img id="ii" src="${ctx}/testimg/modelimg2.jpg" width="190" height="100" /></td>
					    <td width="9%" align="center" valign="middle">&nbsp;</td>
					    <td width="35%" align="left" valign="middle"><input type="image" src="${ctx}/skins/btn_model01.jpg"/>
				          <br />
				          <br />
				        <input type="image" src="${ctx}/skins/btn_model02.jpg"/></td>
				    </tr>
			    </table>
				  <p>&nbsp;</p>
				</div>
				<p class="clear"></p>
			</li>
			<li>
              <div class="bom">
				  <div class="bom_desc">
				    <h5>颜色</h5>
                    <h5>
                    <a href="javascript:void(0);"><div class="model_color" style="background-color:red;"></div></a>
                    <a href="javascript:void(0);"><div class="model_color" style="background-color:blue;"></div></a>
                    <a href="javascript:void(0);"><div class="model_color" style="background-color:green;"></div></a>
                    <a href="javascript:void(0);"><div class="model_color" style="background-color:pink;"></div></a>
                    <a href="javascript:void(0);"><div class="model_color" style="background-color:black;"></div></a>
                    <a href="javascript:void(0);"><div class="model_color" style="background-color:yellow;"></div></a>
                    <a href="javascript:void(0);"><div class="model_color" style="background-color:orange;"></div></a>
                    <a href="javascript:void(0);"><div class="model_color" style="background-color:indigo;"></div></a>
                    </h5>
					</div>
				</div>
                
                <div class="bom">
				  <div class="bom_desc">
				    <h5>打印规格</h5>
                    <h4>mm x mm x mm</h4>
                    <h4>缩放比例:<input type="text" size="3" value="100"/>
                      % 
                        <input type="submit" name="button" id="button" value="确定" />
                      <br />
                       &nbsp;<br />
                    </h4>
				  </div>
				</div>
                
                <div class="pom">
				  <div class="pom_desc">
				    <h5>数量</h5>
                    <h4><input type="image" src="${ctx}/skins/jian.jpg" onclick="divNums();"/> 
                      <input id="nums" type="text" size="6" class="model_form" value="1"> 
                      <input type="image" src="${ctx}/skins/jia.jpg" onclick="addNums();"/></h4>
                    <h4>
                      <br />
                       &nbsp;<br />
                    </h4>
				  </div>
				</div>
                
                <div class="pom">
				  <div class="pom_desc">
				    <h5>价格</h5>
                   <!--  <h3 id="price">￥&nbsp;1000</h3> -->
                    <h4>
                      
                      <br />
                       &nbsp;<br />
                    </h4>
				  </div>
				</div>
                
                <div class="pom">
				  <div class="pom_desc">
				    <h5>&nbsp;</h5>
                    <h4>
<%--                     <input type="image" src="${ctx}/skins/btn_buy.jpg"/> --%>
                      
                      <br />
                       &nbsp;<br />
                    </h4>
				  </div>
				</div>
			  <p class="clear"></p>
			</li>
			
		</ul>
		<!--底部灰色背景-->
	  <div class="bgbox_gray"></div>
		<script type="text/javascript">
			$('#detail_list li:last').css('margin-bottom','2px');
		</script>
		
	</div>
</div>

	<br /><br /><br /><br /><br /><br />
</div>
	
    
<%@include file="/commons/cloud/foot2.jsp"%>
</body>
</html>
