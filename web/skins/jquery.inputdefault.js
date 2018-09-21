/*
Name		inputDefault
Version		V1.0
Date		2012/11/8
只需要加载该js,并且设置fs="默认值"，便可以调用inputDefault方法了
*/

(function($){
	$.fn.inputDefault = function(options){
		var defaults = {attrName: 'fs', size:14, bold: false, italic:false, color:'#999'};
		var options = $.extend(defaults, options);
		//查找submit按钮，并且绑定石事件
		var $form=$(this).parents('form');
		var $btn_submit=$form.find('input[type=submit]').length==1 ? $form.find('input[type=submit]'):$form.find('input[type=image]');
		this.each(function(){
			if( $(this).val() == '' ) {
				$(this).val($(this).attr('fs')).css({color:options.color});
				var $this = $(this);
				var text = $this.attr(options.attrName);
				$(this).attr('title',text);
			}
		}).focus(function(){
			if( $(this).val()=="密码" ) {
				$(this).parent().html('<input class="text" type="password" name="password" />');
				$('input[type=password]').focus();
			}

			if($(this).val()==""||$(this).val()==$(this).attr('fs')){
				$(this).val('');
				$(this).css('color','#333');
			}else{
				$(this).css('color','#333');
			}
			
		}).blur(function(){
			if($(this).val()==""||$(this).val()==$(this).attr('fs')){
				$(this).val($(this).attr('fs')).css({color:options.color});
			}else{
				$(this).attr('title','')
			}
		})
		$btn_submit.click(function(){
			$form.find(':text').each(function(){
				if( $(this).val() == $(this).attr('fs') ) $(this).val('');
			});
		})
	}
	
})(jQuery);

//文档加载完后之后直接调用该方法
$(function(){
	$('[fs]').inputDefault();
		
})



