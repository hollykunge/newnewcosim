<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="home_right">
			<!--会员信息-->
			<div class="blue_box" id="memberinfobox">
				<h2 class="bluetitel"><%-- ${ctx}/console/enterprise.ht?EntId=${info.sysOrgInfoId } --%>
						<a
							href="${ctx }/cloud/console/enterprise.ht?EntId=${info.sysOrgInfoId } "
							class="link04">${info.name }</a>
				</h2>
				<span><a href="javascript:void(0)"><img src="${ctx }${info.logo}"
							onError="this.src='${ctx}/images/default-chance.jpg'" width="80"
							height="80" /></a></span>
				<p><a href="javascript:void(0)">人员： ${userName }
						</a></p>
				<p><a href="javascript:void(0)">角色：设计师
						</a></p>
			</div>
			
			<div class="blue_box" id="notice_list">
				<h2 class="bluetitel"><a href="${ctx}/cloud/system/news/more.ht" class="more">更多</a>新闻公告</h2>
				<ul>
				<c:forEach items="${newsList}" var="newsItem" varStatus="s">
					<li style="margin-bottom:6px;"><span><fmt:formatDate value="${newsItem.publishtime}"
								pattern="yyyy-MM-dd" /></span> <a href="${ctx}/cloud/system/news/get.ht?id=${newsItem.id}"
						 title="${newsItem.title}">${newsItem.title}</a></li>
				</c:forEach>
				</ul>
			</div>
			
			<!--最近使用资源-->
			<div class="blue_box" id="nearly_use">
				<h2 class="bluetitel"><a href="${ctx }/cloud/console/visitMore.ht?entId=${enterprise.sysOrgInfoId }" class="more">更多</a>最近使用的资源</h2>
				<ul>
				<c:forEach items="${cloudUseResList }" var="cloudUseResList">
					<li style="margin-bottom:5px;"><a href="javascript:void(0)"><img src="${ctx}/images/rollPic/${cloudUseResList.resName }_small.gif" onError="this.src='${ctx}/images/default-chance.jpg'" width="50" height="50"   style="border:0;"></a><a href="${cloudUseResList.resLink }"  target="_blank">${cloudUseResList.resName }</a>
						   <span><fmt:formatDate value="${cloudUseResList.resTime }" pattern="yyyy-MM-dd" /></span></li>
				</c:forEach>
				</ul>
			</div>
			
		</div>
			
			
			<div class="clear_10"></div>
