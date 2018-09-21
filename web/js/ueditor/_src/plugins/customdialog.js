/**
 * 自定义对话框
 * @function
 * @name baidu.editor.execCommands
 * @param    {String}    cmdName     cmdName="customdialog"
 */
UE.commands['customdialog'] = {	
	execCommand : function(cmdName) {
		var me=this;
		if(!me.ui._dialogs['customdialogDialog']){
			 iframeUrl ="~/dialogs/dialog/dialog.html";
             title = me.options.labelMap['customdialog'] || '';
             
			var dialog = new baidu.editor.ui.Dialog( utils.extend({
                iframeUrl: me.ui.mapUrl(iframeUrl),
                editor: me,
                className: 'edui-for-customdialog',
                title: title
            },{
                buttons: [{
                    className: 'edui-okbutton',
                    label: '确认',
                    onclick: function (){
                        dialog.close(true);
                    }
                }, {
                    className: 'edui-cancelbutton',
                    label: '取消',
                    onclick: function (){
                        dialog.close(false);
                    }
                }]
            }));

			me.ui._dialogs["customdialogDialog"] = dialog;
			dialog.render();
			dialog.open();			
		}
		else{
			me.ui._dialogs['customdialogDialog'].open();
		}
	},
	queryCommandState : function() {
		return 0;
	}
}