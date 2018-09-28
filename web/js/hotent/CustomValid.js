/**
 * jquery自定义表单验证插件
 * 使用方法：
 * 在需要做验证的输入框，单选框，多选框，下拉框中加入validate属性
 * validate:写法如下：
 * {required:true,email:true,maxLength:50}
 * 如：
 * <input type="text" name="username" value="" validate="{required:true,maxlength:50}"/>
 * 注意一组单选框，或多选框  只需在其中一个input标记中 加入validate 属性
 * 如：
 * <input type="checkbox" name="a" value="1" validate="{required:true}" tipId="errorA"/>
 * <input type="checkbox" name="a" value="2" />
 * <input type="checkbox" name="a" value="3" />
 * 
 * tipId:错误信息显示的容器ID,设置了这个属性后，错误信息会显示到该标签中。
 * <input type="text" name="name" validate="{reqired:true}" tipId="errorA"/><label id="errorA"></label>
 * 
 * 
 * 调用方式:
 * 
 * $("a.save").click(function(){
 *			var rtn=$("#shipOrderForm").form().valid();
 *			if(rtn){
 *				$("#shipOrderForm").submit();
 *			}
 *	});
 *	同时也可以扩展验证的规则
 *	var rtn=$("#form").form({
 *		//扩展验证规则 追加到已有的规则中
 *		rules:[{
 *			//规则名称
 *			name:"QQ",
 *			//判断方法  返回 true 或false
 *			rule:function(v){
 *			},
 *			//错误的提示信息
 *			msg:""
 *		
 *		}],
 *		//显示的错误信息样式 element 当前验证的元素，msg：错误信息
 *		errorPlacement:function(element,msg){
 *		},
 *		//成功后的样式 element 当前验证的元素
 *		success:function(element){
 *		}}).valid()
 */
(function($) {
	$.extend($.fn, {
		// 表单初始化，可以添加自定义规则，出错处理和成功后的处理。
		form : function(conf) {
			if (conf) {
				if (conf.errorPlacement) {
					this.errorPlacement = conf.errorPlacement;
				};
				if (conf.rules) {
					for (var i = 0, len = conf.rules.length; i < len; i++) {
						this.addRule(conf.rules[i]);
					}
				};
				if (conf.success) {
					this.success = conf.success;
				};
				if (conf.excludes) {
					this.excludes = conf.excludes;
				}
			}
			var form = this;
			form.delegate("[validate]", "blur", function() {
						form.handValidResult(this);
					});
			form.delegate("[validate]", "focus", function() {
						form.success(this);
					});
					
			//处理验证ckeditor
			$("[validate].ckeditor",form).each(function(){
				var me= $(this),name = me.attr("name");
				setTimeout(function(){//等待ckeditor渲染完成再进行处理
					var editor=  CKEDITOR.instances[name],ck=me.next();
					if(editor){
						editor.on( 'blur', function(){
							form.handValidResult(me);
						});
						editor.on( "focus", function(){
							form.success(me);
						});
					}
				},1000);
				
			})	
		
			return this;
		},
		// 添加验证规则。
		// 扩展规则和现有的规则名称相同，则覆盖，否则添加。
		addRule : function(rule) {
			var len = this.rules.length;
			for (var i = 0; i < len; i++) {
				var r = this.rules[i];
				if (rule.name == r.name) {
					this.rules[i] = rule;
					return;
				}
			}
			this.rules.push(rule);
		},
		/**
		 * 判断元素是否在不需要校验的范围内。
		 */
		isInNotValid : function(obj) {
			if (!this.excludes)
				return false;
			var scope = $(this.excludes, this);
			var aryInput = $(
					"input:text,input:hidden,textarea,select,input:checkbox,input:radio",
					scope);
			for (var i = 0, len = aryInput.length; i < len; i++) {
				var tmp = aryInput.get(i);
				if (obj == tmp) {
					return true;
				}
			}
			return false;
		},
		// 对所有有validate表单控件进行验证。
		valid : function() {
			var _v = true, form = this;

			$('[validate]', form).each(function() {
						var rtn = form.handValidResult(this);
						if (!rtn)
							_v = false;
					});
			return _v;
		},
		// 显示表单处理结果
		handValidResult : function(obj) {
			// 是否在不需要验证的范围内，在的话就不需要验证。
			if (this.isInNotValid(obj))
				return true;
			var msg = this.validEach(obj);
			this.success(obj);
			if (msg != '') {
				this.errorPlacement(obj, msg);
				return false;
			} else {
				this.success(obj);
				return true;
			}
		},
		// 验证单个控件。
		validEach : function(obj) {
			var element = $(obj), rules = this.rules, _valid = true, validRule = element
					.attr('validate'), value = "", name = element.attr("name");
			// 处理单选框和多选框
			if (element.is(":checkbox,:radio")) {
				$(":checked[name='" + name + "']").each(function() {
							if (value == "") {
								value = $(this).val();
							} else {
								value += "," + $(this).val();
							}
						});
			} else if (element.hasClass("ckeditor") ) {// 处理ckeditor编辑器
				var editor=  CKEDITOR.instances[name];
				if(editor)
					value = editor.getData();
			}else {
				value = element.val();
			}
			// 处理值
			value = value == null ? "" : value.trim();

			// 获取json。
			var json = eval('(' + validRule + ')');
			var isRequired = json.required;
			// 非必填的字段且值为空 那么直接返回成功。
			if ((isRequired == false || isRequired == undefined) && value == "")
				return "";
			// 遍历json规则。
			for (var name in json) {
				var validvalue = json[name];

				for (var m = 0; m < rules.length; m++) {
					// 取得验证规则。
					var rule = rules[m];
					if (name.toLowerCase() != rule.name.toLowerCase())
						continue;
					// 验证规则如下：
					// email:true,url:true.
					if (validvalue === true || validvalue === false) {
						if (!rule.rule(value) && validvalue == true) {
							_valid = false;
						}
					}
					// maxLength:50
					else {
						_valid = rule.rule(value, validvalue);
					}
					if (!_valid) {

						return String.format(rule.msg, validvalue);
					}
				}
			}
			return "";
		},
		// 错误显示位置。
		errorPlacement : function(element, msg) {
			var errorId = $(element).attr("tipId");
			if (errorId) {
				$('#' + errorId).append($('<label class="error">' + msg
						+ '</label>'));
				return;
			}
			$(element).parent().append($('<label class="error">' + msg
					+ '</label>'));
		},
		// 验证成功时，删除错误提示信息。
		success : function(element) {
			var errorId = $(element).attr("tipId");
			if (errorId) {
				$('#' + errorId).find('label.error').remove();
				return;
			}
			$(element).parent().find('label.error').remove();
		},

		// 内置的规则。
		rules : [{
					name : "required",
					rule : function(v) {
						if (v == "" || v.length == 0)
							return false;
						return true;
					},
					msg : "必填"
				}, {
					name : "number",
					rule : function(v) {
						return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/
								.test(v.trim());
					},
					msg : "请输入一个合法的数字"
				}, {
					name : "variable",
					rule : function(v) {

						return /^[a-z_0-9]*$/gi.test(v.trim());
					},
					msg : "只能是字母和下划线"
				}, {
					name : "minLength",
					rule : function(v, b) {
						return (v.length >= b);
					},
					msg : "长度不少于{0}"
				}, {
					name : "maxLength",
					rule : function(v, b) {
						return (v.trim().length <= b);
					},
					msg : "长度不超过{0}"
				}, {
					name : "rangeLength",
					rule : function(v, args) {

						return (v.trim().length >= args[0] && v.trim().length <= args[1]);
					},
					msg : "长度必须在{0}之{1}间"
				}, {
					name : "email",
					rule : function(v) {
						return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i
								.test(v.trim());
					},
					msg : "请输入一合法的邮箱地址"
				}, {
					name : "url",
					rule : function(v) {
						return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
								.test(v.trim());
					},
					msg : "请输入一合法的网址"
				}, {
					name : "date",
					rule : function(v) {
						var re = /^[\d]{4}-[\d]{1,2}-[\d]{1,2}\s*[\d]{1,2}:[\d]{1,2}:[\d]{1,2}|[\d]{4}-[\d]{1,2}-[\d]{1,2}|[\d]{1,2}:[\d]{1,2}:[\d]{1,2}$/g
								.test(v.trim());
						return re;
					},
					msg : "请输入一合法的日期"
				}, {
					name : "digits",
					rule : function(v) {
						return /^\d+$/.test(v.trim());
					},
					msg : "请输入整数"
				}, {
					name : "equalTo",
					rule : function(v, b) {
						var a = $("#" + b).val();
						return (v.trim() == a.trim());
					},
					msg : "两次输入不等"
				}, {
					name : "range",
					rule : function(v, args) {
						this.msg = String.format(this.msg, args[0], args[1]);
						return v <= args[1] && v >= args[0];
					},
					msg : "请输入在{0}到{1}范围之内数字"
				}, {
					// 判断数字整数位
					name : "maxIntLen",
					rule : function(v, b) {
						this.msg = String.format(this.msg, b);
						return (v + '').split(".")[0].length <= b;
					},
					msg : "整数位最大长度为{0}"
				}, {
					// 判断数字小数位
					name : "maxDecimalLen",
					rule : function(v, b) {
						this.msg = String.format(this.msg, b);
						return (v + '').replace(/^[^.]*[.]*/, '').length <= b;
					},
					msg : "小数位最大长度为{0}"
				}, {
					// 判断日期范围
					name : "dateRangeStart",
					rule : function(v, b) {
						var end = $("#" + b).val();
						return daysBetween(v, end);
					},
					msg : "开始日期必须小于或等于结束日期"
				}, {
					// 判断日期范围
					name : "dateRangeEnd",
					rule : function(v, b) {
						var start = $("#" + b).val();
						return daysBetween(start, v);
					},
					msg : "开始日期必须小于或等于结束日期"
				}]
	});

})(jQuery);
