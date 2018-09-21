

(function($){
	$.fn.extend({
		boxscroll:function(options){
		var isset={
			count:5,//一次显示的索引容器的个数
			width:132,//每个索引容器的宽度
			speed:'normal',
			box:$('#boxscroll'),//移动的容器，是动态可变的宽度
			windowidth:660,//窗口容器，固定不变的宽度
			field:$('#boxscroll div'),//索引容器集合
			//左侧点击按钮,右侧点击按钮,是否有按钮[默认值没有]
			btn:{isbtn:'none',btnleft:$('#move_left'),btnright:$('#move_right')},
			autoplay:false,//是否自动滚动
			speed:500,
			scrolltype:'normal',
			direction:'right' //对象移动的方向[上右下左]
		};
		
		//合并参数对象.若options传入有新值则覆盖iset中对应值,否则使用默认值.
		$.extend(isset,options);
		
		//总共索引个数,可移动次数
		var thiscount =isset.field.size(),movecount;
		
		//总宽度
		var sumwidth=thiscount*isset.width;
		
		//每一次移动的宽度
		var movewidth=isset.width*isset.count;
		
		
		
		
		$.fn.boxscroll.init=function(){
			//如果无按钮移动
			if( isset.btn.isbtn=='none'){
				isset.box.append('<div id="ulbox2" class="introduce_list"></div>');
				$('.introduce_list').wrapAll('<div id="wrapbox"></div>');
				$('.introduce_list').html(isset.box.html());
				$('#ulbox2').after('<p styel="clear:both"></p>')
				$('#wrapbox').wrap('<div id="ulbox"></div>');
				$('#wrapbox').css({width:sumwidth*2,'float':'left',height:'120px','position':'relative'});
				$('#ulbox').css({width:isset.windowidth,'overflow':'hidden',height:'120px','position':'relative'})
			}else{ 
				//带有按钮的
				movecount=new Array(2);
				movecount[0]=Math.floor(sumwidth/movewidth);
				movecount[1]=movecount[0];
				if(sumwidth%movewidth==0) {
					movecount[0]=movecount[0]-1;
					movecount[1]=movecount[0];
				}
			}
		}
		
		$.fn.boxscroll.init();
		
		
		//isset.box.css({'width':sumwidth+'px','position':'relative','left':'0px'});
		//如果每一次移动的长度》移动的容器的长度，则返回
		if(movewidth>=sumwidth) return false;
		
		//生成一个div将移动的容器包裹起来
		
		
		
		var i = thiscount-isset.count;
		
		//isset.btn.btnleft.click(function(){$.fn.boxscroll.btnright()});
		//isset.btnleft.click(function() {})
		//向右移动
		$.fn.boxscroll.btnright=function(){
			if(isset.box.is(":animated")) return false;
			window.clearInterval(timer);
			var nowleft = parseInt(isset.box.css('left').split('px')[0]);
			if(movecount[0]!=0) {
				isset.btnleft.css('cursor','pointer');
				isset.btnright.css('cursor','pointer');
				isset.btnleft.attr('src','skins/btn_left.jpg');
				isset.box.animate({left: (nowleft-movewidth)+'px'},isset.speed,function() {
					//不能再移动了
					if(movecount[0]==1){
						isset.btnright.attr('src','skins/btn_no_right.jpg').css('cursor','default');;
						isset.btnleft.attr('src','skins/btn_left.jpg');
						isset.btnright.parents('a').css('cursor','default');
					}
					movecount[0]=movecount[0]-1;
				});
			} else {
				isset.box.animate({left: '0px'},isset.speed,function() {
					movecount[0]=movecount[1];
				});
			}
		}
		
		$.fn.boxscroll.btnleft=function(){
			//判断元素是否正处于动画状态,让click事件停止
			if(isset.box.is(":animated")||isset.btnleft.css('cursor')=='default') return false;
			window.clearInterval(timer);
			var nowleft =parseInt(isset.box.css('left').split('px')[0]);
			if( movecount[0]!=movecount[1]) {
				isset.btnleft.css('cursor','pointer');
				isset.btnright.css('cursor','pointer');
				isset.btnright.attr('src','skins/btn_right.jpg');
				isset.box.animate({left: (nowleft+movewidth)+'px'}, isset.speed,function() {
					movecount[0]=movecount[0]+1;
					//不能再移动了
					if(movecount[0]==movecount[1]){
						isset.btnleft.attr('src','skins/btn_no_left.jpg').css('cursor','default');
						isset.btnright.attr('src','skins/btn_right.jpg');
						isset.btnleft.parents('a').css('cursor','default');
					}
				});
			}
		}
		
		 
		var timer=null;
		$.fn.autoplay=function(){
			if(!isset.autoplay) return false;
			var obj=$.fn.boxscroll.moveboxinit();
			timer=setInterval(function(){
				$.fn.boxscroll.movebox(obj)
			},20);
		}
		
		
		
		
		//初始化tab,tab1,tab2
		$.fn.boxscroll.moveboxinit=function(){
			id='ulbox';
			var tab=document.getElementById(id);
			var tab1=document.getElementById(id+"1");
			var tab2=document.getElementById(id+"2");
			var threeobj={'tab':tab,'tab1':tab1,'tab2':tab2};
			return threeobj;
		}
		$.fn.boxscroll.movebox=function(obj){
			if(obj.tab2.offsetWidth-obj.tab.scrollLeft<=0){
				obj.tab.scrollLeft-=obj.tab1.offsetWidth;
			}else{
				obj.tab.scrollLeft=obj.tab.scrollLeft+1;
			}
		}
		
		
		$('.introduce_list div').mouseover(function(){
			window.clearInterval(timer);
		})
		
		$('.introduce_list div').mouseleave(function(){		
			$.fn.autoplay();
		})
		
		$.fn.autoplay();
		
		}
	})
})($)



/*
if(tab2.offsetTop-tab.scrollTop<=0|| tab2.offsetTop-tab.scrollTop<=4)//当滚动至demo1与demo2交界时
	tab.scrollTop-=tab1.offsetHeight  //demo跳到最顶端
else{
	tab.scrollTop++
}
*/


function SetHome(obj,vrl){
	
        if($.browser.msie){
			obj.setHomePage(vrl);
			alert(14);
		}else{
			 alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。");

		}
}


function menu(linkId)
{
	var divId  = 'm_'+linkId;
		$('#index_menu div').removeClass('parenton');
	if (document.getElementById(linkId).className == 'off') {
		document.getElementById(linkId).className = 'on';
		$('#'+divId).slideUp('speed');
		$('#'+divId).prev('div[class=parent]').addClass('parenton');
	} else {
		document.getElementById(linkId).className = 'off';
		$('#'+divId).slideDown('speed');
		$('#'+divId).prev('div').removeClass('parenton');
	}
	
	document.getElementById(linkId).blur();
}

$(function(){
	$('#top_commoninfo .navico').mouseover(function(){
			$("#navico").css('border','1px solid #CCC').show();
		})
		
	$('#navico').mouseleave(function(){
		$("#navico").hide();
	})
})