<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${enterprise.name }</title>
<%@include file="/commons/cloud/meta.jsp"%>
<style type="text/css">
a{text-decoration:none;color: #1169c2;outline: none;}
a:hover{text-decoration:underline;color:#ff781f;}
.approve
{
  color:red;
}
.info
{
  font-size:12px;padding-left:100px; line-height:20px; padding-top:5px; line-height:24px;
}
</style>

<script type="text/javascript">
	function addFriend(corpEntID) {
		//如果未登录，提示登录信息
		$.ajax({
			type : 'POST',
			url : "${ctx}/cloud/console/busiarea/beFriend.ht",
			data : {
				corpEntID : corpEntID
			},
			dataType : "json",
			success : function(data) {
				if(data && data.waitForAccept=="true"){
					alert('添加商友请求已经发送，等待对方审核');	
				}else if(data && data.isWaiting=="true"){
					alert('已经发出添加商友申请，请勿重复提交添加申请');	
				}else if(data && data.isFriend=="true"){
					alert('对方已是商友，请勿重复添加');	
				}else if(data && data.isMyself=="true"){
					alert('不能添加自己为商友');	
				}
			},
			error : function(data){
				alert('您还未登录，请登录后再加商友');
			},
		});
	}
	function toUrl(url){
		var t_url;
		var u = url.substring(0,7);
		if(u!="http://"){
			t_url ="http://" + url;
		}else{
			t_url=url;
		}
		window.open(t_url);
		
	}
	
</script>

</head>
<body>
<div id="all">
	<%@include file="/commons/cloud/top.jsp"%>
	
	<!--企业banner-->
	<div id="banner">
		<div class="comname">
			<c:if test="${not empty enterprise.website}">
				<a href="javascript:toUrl('${enterprise.website}');"  class="comname_link">${enterprise.name}</a>
			</c:if>
			<c:if test="${empty enterprise.website}">
				${enterprise.name}
			</c:if>
		</div>
		<span class="afriend"><a href="javascript:void(0)" onClick="addFriend('${enterprise.sysOrgInfoId }');"><img src="${ctx}/skins/btn_addfr.jpg" /></a></span>
		<c:if test="${enterprise.showimage=='' || enterprise.showimage == null}">
			<img class="bannerimg" src="${ctx}/skins/cm1.jpg" />
		</c:if>
		<c:if test="${not empty enterprise.showimage}">
			<img class="bannerimg" src="${ctx}${enterprise.showimage}"></img>
		</c:if>
		</div>
	
	<div id="indexcontent">
		<div class="index_left">
			<!--企业简介-->
			<div id="qiyenengli" class="blue_box proinfo">
				<h2 class="bluetitel">企业简介</h2>
				<div class="cmdesc_0916">
                  	<div class="about_img">
                  		<img width="80" height="80" onerror="this.src='${ctx}/images/default-chance.jpg'" src="${ctx}${enterprise.logo}"/>
                  	</div>
			        <div class="cmdesc_content">
						${enterprise.info}
                    	<p>成立时间：<fmt:formatDate value="${enterprise.registertime }" pattern="yyyy年MM月dd日"/><font>[已认证]</font>&nbsp;&nbsp;注册资本：${enterprise.regCapital }<font>[已经认证]</font></p>
						<p>经营范围：${enterprise.manageRange }<font>[已认证]</font>&nbsp;&nbsp;经营地址：${enterprise.sellArea }</p>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			
			<!--认证信息-->
			<div id="renzheng_info" class="blue_box proinfo">
				<h2 class="bluetitel">认证信息</h2>
                <div class="box_vv">
                
				<div class="cmimg">
					<!-- 
					<a href="javascript:void(0)" target="_blank">
						<img src="skins/taocibiaoqian.jpg" /></a>
						-->
					</div>				 
				<div class="cmdesc">
                    <table  id="colspan">
                    	<tr>
                        	<td colspan="2">工商注册信息 ${enterprise.regProve }</td>
                        </tr>
                    	<tr>
                        	<td colspan="2"></td>
                        </tr>
                    	<tr>
                        	<td>公司名称：${enterprise.name } </td>
                            <td> 注册地址：${enterprise.regAdd }</td>
                        </tr>
                    	<tr>
                        	<td>注册资本：${enterprise.regCapital }</td>
                            <td>成立日期：<fmt:formatDate value="${enterprise.registertime }" pattern="yyyy年MM月dd日"/></td>
                        </tr>
                    	<tr>
                        	<td>注册号：  <span>${enterprise.code }</span></td>
                            <td>法定代表人：${enterprise.incorporator }</td>
                        </tr>
                    	<tr>
                        	<td>登记机关：${enterprise.city}机关</td>
                            <td>企业类型：${enterprise.type }</td>
                        </tr>
                    	<tr>
                        	<td>营业期限：<fmt:formatDate value="${enterprise.registertime }" pattern="yyyy年MM月dd日"/> 至 长期</td>
                            <td>年纪检时间：<fmt:formatDate value="${enterprise.registertime }" pattern="yyyy年MM月dd日"/>(最近)</td>
                        </tr> 
                        <tr>
                        	<td>经营范围：${enterprise.manageRange }</td>
                            <td>申请人信息：${enterprise.incorporator }</td>
                        </tr>
                        
                    </table>
				</div>
                </div>
			</div>
			
			
			<div class="clear"></div>
			<!--企业能力-->
			<div id="qiyenengli" class="blue_box proinfo">
				<h2 class="bluetitel"><a href="${ctx}/cloud/config/capability/capabilityList_cur.ht?EntId=${enterprise.sysOrgInfoId }">更多</a>企业能力</h2>
				<ul>
				<c:forEach items="${capabilitylist}" var="capabilitylist">
					<li>
						<div class="cmimg">
							<a href="javascript:void(0)" target="_blank">
							<img src="${ctx}${capabilitylist.pic}" onError="this.src='${ctx}/images/default-chance.jpg'" width="80" height="84" />
							</a>
						</div>
						<div class="cmdesc">
							<p class="cmdesc_zz"><a href="${ctx}/cloud/config/capability/capabilityDetail.ht?id=${capabilitylist.id }">${capabilitylist.name }</a></p>
                            <P class="cmdesc_two">${capabilitylist.info}</P>
						</div><div class="clear"></div>
					</li>
				</c:forEach>
				</ul>
			</div>
			
			
			<!--企业资质-->
			<div id="qiyezizhi" class="blue_box proinfo">
				<h2 class="bluetitel">企业资质</h2>
				<ul>
				<c:forEach items="${aptitudeList}" var="apt">
					<li>
						<div class="cmimg"><a href="javascript:void(0)" target="_blank"><img src="${ctx}${apt.catePic}" onError="this.src='${ctx}/images/default-chance.jpg'" width="80" height="84" /></a></div>
						<div class="cmdesc">
							<p><font>证件类型：</font>${apt.cateType }</p>
							<p><font>发证机构：</font>${apt.cateOrg}</p>
							<p><font>生效日期：</font><fmt:formatDate value="${apt.inureDate}" pattern="yyyy-MM-dd"/></p>
							<p><font>截止日期：</font>${apt.endDate}</p>
						</div><div class="clear"></div>
					</li>
				</c:forEach>
				</ul>
			</div>
			
			<!--详细信息-->
			<div id="detail_info" class="blue_box">
				<h2 class="bluetitel">详细信息</h2>
				<div class="companyinfo">
					<p><font>主营产品：</font>${enterprise.product}</p>   <p><font>主营行业：</font>${enterprise.industry}</p>
					<p><font>经营模式：<c:if test="${enterprise.model=='1'}"> 生产型    </c:if>
						     <c:if test="${enterprise.model=='2'}"> 贸易型    </c:if>
						      <c:if test="${enterprise.model=='3'}"> 服务型 </c:if>
						       <c:if test="${enterprise.model=='4'}"> 研发型    </c:if>
						        <c:if test="${enterprise.model=='0'}"> 其他类型   </c:if></font>生产型</p>  <p><font>企业类型：</font>${enterprise.type}</p>
					<p><font>企业规模：</font>${enterprise.scale }</p> <p><font>经营范围：</font>${enterprise.manageRange }</p>
					<p><font>地址：</font>${enterprise.address}</p> <p><font>邮政编码：</font>${enterprise.postcode }</p>
                    <P><font>公司网站：</font>${enterprise.website }</P> <P><font>邮箱：</font><c:if test="${enterprise.isPublic==1}">${enterprise.email }  </c:if>
						   <c:if test="${enterprise.isPublic==0}">未公开  </c:if></P>
                    <P><font>联系人：</font><c:if test="${enterprise.isPublic==1}">${enterprise.connecter }  </c:if>
						   <c:if test="${enterprise.isPublic==0}">未公开  </c:if></P> <P><font>手机号：</font><c:if test="${enterprise.isPublic==1}"> ${enterprise.tel }  </c:if>
						   <c:if test="${enterprise.isPublic==0}">未公开  </c:if></P>
                    <P><font>固定电话：</font><c:if test="${enterprise.isPublic==1}"> ${enterprise.homephone }  </c:if>
						   <c:if test="${enterprise.isPublic==0}">未公开  </c:if></P> <P><font>传真：</font><c:if test="${enterprise.isPublic==1}"> ${enterprise.fax }  </c:if>
						   <c:if test="${enterprise.isPublic==0}">未公开  </c:if></P>
                    <P><font>主要销售区域：</font>${enterprise.sellArea }</P> <P><font>主要客户群体：${enterprise.clients }</font></P>
                    <P><font>占地面积：${enterprise.area }</font></P> <P><font>员工人数：${enterprise.employees }</font></P>
                    <P><font>年营业额：</font> ${enterprise.turnover }</P> <P><font>年出口额：</font>${enterprise.exportFore }</P>
                    <P><font>年进口额：${enterprise.importFore }</font></P>  <P><font>企业品牌：${enterprise.brand }</font></P>
                    <P><font>质量管理体系：${enterprise.qualityControl }</font></P>
					<h1 class="clear"></h1>
				</div><br />
			</div>
			
		</div>
		<div class="index_right">
			
			<!--会员信息-->
			<div class="blue_box" id="memberinfobox">
				<h2 class="bluetitel"><a href="${ctx }/cloud/console/enterprise.ht?EntId=${enterprise.sysOrgInfoId } " class="link04">${enterprise.name }</a></h2>				
				<span>
					<a href="javascript:void(0)">
						<img src="${ctx }${enterprise.logo}"
							onError="this.src='${ctx}/images/default-chance.jpg'" width="80"
							height="80" />
					</a>
				</span>
				<p>联系人：<c:if test="${enterprise.isPublic==1}">${enterprise.connecter }  </c:if>
						   <c:if test="${enterprise.isPublic==0}">未公开  </c:if></p>
				<p>电话：<c:if test="${enterprise.isPublic==1}">${enterprise.tel }  </c:if>
						   <c:if test="${enterprise.isPublic==0}">未公开  </c:if></p>
			</div>
			
			<!--最近来访-->
			<div class="blue_box" id="visiteuser">
				<h2 class="bluetitel"><a href="${ctx }/cloud/console/visitMore.ht?entId=${enterprise.sysOrgInfoId }">更多</a>最近来访</h2>
				<ul>
				<c:forEach items="${enterpriseVisitedList }" var="enterpriseVisitedList">
					<li>
						<a href="javascript:void(0)" target="_blank"><img src="${ctx }${enterpriseVisitedList.visitEnterprise.logo}"
							onError="this.src='${ctx}/images/default-chance.jpg'" width="50"
							height="50" style="border: 1px solid #6D6D6F;"/></a>
						<p><a href="${ctx }/cloud/console/enterprise.ht?EntId=${enterpriseVisitedList.visitEnterprise.sysOrgInfoId}" class="date3" title="${enterpriseVisitedList.visitEnterprise.name}">
								<ap:textTip value="${enterpriseVisitedList.visitEnterprise.name}" max="12" more=".."/>
							</a></p>
						<p><fmt:formatDate value="${enterpriseVisitedList.visitdate }" pattern="yyyy-MM-dd"/></p>
					</li>
				</c:forEach>
				</ul>
			</div>
			
			
		</div>
		<div class="clear"></div>
	</div>
</div>

		<!-- 底部版权区  开始 -->
		<%@include file="/commons/cloud/foot.jsp"%>
		<!-- 底部版权区  结束 -->
</body>
</html>
