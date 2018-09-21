$(function(){
		$.ajax({
		  type:"post",
		  url:"${ctx}/news/init.action",
		  dataType:"json",
		  success:function(returndata,status){
		    	$(returndata).each(function(i){
		    	    var html="<li><span class='date'>"+returndata[i].publishtime+"</span><a href='${ctx}/news/newsContent.action?id="+returndata[i].id+"' class='link02'>"+returndata[i].title+"</a></li>";
		    	    $("#news").append(html);
		    	});
	     }
		 });
	});