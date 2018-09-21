/**
 * 统计函数
 * @function
 * @name baidu.editor.execCommands
 * @param    {String}    cmdName     cmdName="insertfunction"
 */
UE.commands['insertfunction'] = {	
	execCommand : function(cmdName) {		
		var el = this.selection.getRange().getClosedNode(),
	 		 	funcInstance={}, funcexp = el.getAttribute("funcexp");
				
		if (funcexp) {
			funcInstance.exp = funcexp;
		}
		funcInstance.targetEl=el;
		this.funcInstance = funcInstance;
		this.ui._dialogs['insertfunctionDialog'].open();
	},
	queryCommandState : function() {
		var el = this.selection.getRange().getClosedNode();
		return el ? 0 : -1;
	}
}