$(function(){
	var subform={
			tables:{},
			//初始化页面
			init:function(){
				var self=this;
				$("[type='sub']").each(function(){
					var table=$(this);
					var formType=table.attr("formType");
					//添加一行数据
					var row=table.find("[type='append']");
					
					//子表Id
					var tableId=table.attr('id');
					//弹框模式
					if(formType=="window"){
						isPage=false;
						var id=this.id+"Form",
							form=$("#"+id),
							width=form.attr('width')?form.attr('width'):form.width()+50,
							height=form.attr('height')?form.attr('height'):form.height()+70,
							title=form.attr('title')?form.attr('title'):''; 
						table.data('form', '<form>' + $('<div></div>').append(form.clone()).html() + '</form>');
						table.data("formProperty",{title:title,width:width ,height: height});
						form.remove();
					}
					//页内编辑模式
					if(formType="page"){
						
					}
					table.data('row',$('<div></div>').append(row.clone()).html());
					
					row.css('display', 'none').html('');
					self.tables[tableId]=table;
					self.handButton(table);
				});
			},
			//触发click 事件
			handButton:function(table){
				var self=this;
				$(".add",table).click(function(){
					self.add(table);
				});
				
				table.delegate(".del", "click", function(){
					self.delRow(this);
				});   
				
				table.delegate(".edit", "click", function(){
					self.edit(this,table);
				}); 
			},
			
			//添加一行记录  包括 弹框 和 页内两种编辑模式
			add:function(table){
				var self=this, f=table.data("form");
				//弹框模式
				if(f){
					var form=$(f).clone(),formProperty = table.data("formProperty");
					this.openWin({
						title:'添加'+formProperty.title,
						width:formProperty.width,
						height:formProperty.height, 
						form:form,
						callback:function(){
							var frm=$(form).form();
							if(frm.valid()){
								var data=self.getFormData(form);
								var showData=self.getFormShowData(form);
								self.addRow(data,showData,table);
							}
						}
					});
				//页内编辑模式
				}else{
					self._addRow(table);
				}
			},
			
			// 弹框模式 根据表单数据 增加一行
			addRow:function(data,showData,table){
				var row=table.data('row');
				var tr=$(row).clone();
				tr.attr('type','subdata');
				tr.removeAttr('style');
				for(var name in data){
					tr.find("td,input:hidden").each(function(){
						if($(this).is('td')){
							var tdname=$(this).attr('name');
							if(name==tdname){
								$(this).text(showData[name]);
							}
						}
						if($(this).is('input:hidden')){
							var inputname=$(this).attr('name');
							if(name==inputname){
								$(this).val(data[name]);
							}
						}
					});
				}
				table.append(tr);
				$.ligerDialog.hide();
			},
			
			// 弹框模式 触发 编辑点击事件
			edit:function(obj,table){
				var self=this, form=self.setFormData(obj,table),formProperty = table.data("formProperty");
				this.openWin({
					title:'编辑'+formProperty.title,
					width:formProperty.width,
					height:formProperty.height, 
					form:form,
					callback:function(){
						var frm=$(form).form();
						if(frm.valid()){
							self.editRow(obj, form);
						}
					}
				});
				
			},
			
			//页内编辑模式 添加一行
			_addRow:function (table){
				var row=table.data('row');
				var tr=$(row).clone();
				tr.attr('type','subdata');
				tr.removeAttr('style');
				table.append(tr);
			},
			
			//删除一行
			delRow:function(obj){
				$.ligerMessageBox.confirm('提示信息','确认删除吗？',function(rtn) {
					if(rtn) {
						$(obj).closest('[type="subdata"]').remove();		
					}
				});
				
			},
			//弹框模式 根据表单数据 编辑对应的行数据
			editRow:function(obj,form){
				var self=this;
				var data=self.getFormData(form);
				var showData = self.getFormShowData(form);
				//alert(JSON.stringify(data));
				var trObj=$(obj).closest('[type="subdata"]');
				for(var name in data){
					trObj.find("td,input:hidden").each(function(){
						if($(this).is('td')){
							var tdname=$(this).attr('name');
							if(name==tdname){
								$(this).text(showData[name]);
							}
						}
						if($(this).is('input:hidden')){
							var inputname=$(this).attr('name');
							if(name==inputname){
								$(this).val(data[name]);
							}
						}
					});
				}
				$.ligerDialog.hide();
			},
			
			/**
			 * 打开操作数据窗口。
			 * @param conf 窗口配置参数
			 * <pre>
			 * 	conf.title {string}(可选)窗口标题 默认‘编辑’ 
			 * 	conf.width  {number}(可选)窗口宽度 默认400
			 * 	conf.height {number}(可选)窗口高度 默认250
			 * 	conf.form	 {Object}当前窗口对象
			 * 	conf.callback {Object}(必须) 回调函数
			 * </pre>
			 */
			openWin:function(conf){	
				$.ligerDialog.open({ 
					width: conf.width?conf.width:400,
					height:conf.height?conf.height:250,
					title: conf.title?conf.title:'编辑', 
					isResize:true,
					target:conf.form,
					buttons: [{ text: '确定', onclick:function(item,dialog){
						conf.callback(item,dialog);
					}}]
				});
			},
			
			//回填数据至表单
			setFormData:function(obj,table){
				var form=$(table.data('form')).clone();
				var json={};
				//根据当前行 取得数据
				var trObj =$(obj).closest('[type="subdata"]');
				$("input",trObj).each(function(){
						var value=$(this).val();
						var name=$(this).attr('name');
						json[name]=value;
				});
				
				for(var name in json){
					form.find('input:text,textarea,select').each(function() {
						var attrname=$(this).attr('name');
						if(name==attrname){
							if($(this).is('select')){
								$(this).find('option').each(function(){
									if($(this).val()==json[name]){
										$(this).attr('selected','selected');
									}
								});
							}else{
								$(this).val(json[name]);
							}
						}
					});
					form.find('input:checkbox,input:radio').each(function(){
						var attrname=$(this).attr("name");
						var value = '';
						if(name==attrname){
							value=json[name];
						}
						var ary=value.split(',');
						$(this).find('[name='+name+']').each(function(){
							for(var i=0;i<ary.length;i++){
								var v=ary[i];
								if($(this).val()==v){
									$(this).attr("checked","checked");
								}
							}
						});
							
					});
				}
				return form;
			},
			
			/**
			 * 取得表单中的数据
			 */
			getFormData:function(form){
				var data={};
				$(form).find('input:text,textarea,select').each(function() {
					var name=$(this).attr('name');
					var value=$(this).val();
					data[name]=value;
				});
				$(form).find(":checkbox,:radio").each(function(){
					var name=$(this).attr('name');
					var value="";
					$(":checked[name="+name+"]").each(function(){
						if(value==""){
							value=$(this).val();
						}else{
							value+=","+$(this).val();
						}
					});
					data[name]=value;
				});
				return data;
			},
			/**
			 * 取得表单中的展示的数据
			 */
			getFormShowData:function(form){
				var data={};
				$(form).find('input:text,textarea').each(function() {
					var name=$(this).attr('name');
					var value=$(this).val();
					data[name]=value;
				});
				$(form).find('select').each(function() {
						var me = $(this);
						var name= me.attr('name');
						var value= me.find("option:selected").text(); 
						data[name]=value;
				});
				
				$(form).find(":checkbox,:radio").each(function(){
					var name=$(this).attr('name');
					var value="";
					$(":checked[name="+name+"]").each(function(){
						if(value==""){
							value=$(this).val();
						}else{
							value+=","+$(this).val();
						}
					});
					data[name]=value;
				});
				return data;
			}
			
	};
	subform.init();
});
