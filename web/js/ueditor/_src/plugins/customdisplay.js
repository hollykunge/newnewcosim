UE.plugins['customdisplay'] = function() {
	var me = this;
	function deleteIframe() {
		me._iframe && delete me._iframe;
	}
	function deleteCustomDisplay(comp,range){
		
        var p = comp.ownerDocument.createElement('p');
        domUtils.fillNode(me.document,p);
        var pN = comp.parentNode;
        if(pN && pN.getAttribute('dropdrag')){
        	comp = pN;
        }
        comp.parentNode.insertBefore(p, comp);
        domUtils.remove(comp);
        range.setStart(p, 0).setCursor();
	}
	
	/**
	 * 显示Dialog
	 * @param t comName
	 * @returns void
	 */
	function initDialog(t){
		me.curInputType = t;
		title = me.options.labelMap[t] || t ;
        className="edui-for-" + t;
		if(!me.ui._dialogs[t]){
			iframeUrl ="~/dialogs/customdisplay/customdisplay.html";
			var dialog = new baidu.editor.ui.Dialog( utils.extend({
               iframeUrl: me.ui.mapUrl(iframeUrl),
               editor: me,
               className: 'edui-for-'+t,
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
			me.ui._dialogs[t] = dialog;
			dialog.render();			
		}				
		me.ui._dialogs[t].title = title;
		me.ui._dialogs[t].className = className;
		me.ui._dialogs[t].clearContent();
		me.ui._dialogs[t].open();
	};
	
	me.addListener("selectionchange", function() {
		deleteIframe();
	});
	
	//Command: Add/Update CustomDisplay
	me.commands["customdisplay"] = {
		queryCommandState : function() {
			if(this.highlight){
				return -1;
			}
			var range = this.selection.getRange();
			var parentNode = domUtils.findParentByTagName(range.startContainer, 'div', true)
			while(parentNode){
				if(parentNode.getAttribute("comptype")=="custom-display"){
					return 0;
				}
				parentNode = domUtils.findParentByTagName(parentNode, 'div', true)
			}
		},
		execCommand : function(cmdName,value) {
			if('edit'==value){
				  var range = this.selection.getRange(),
	              div = domUtils.findParentByTagName(me.currentSelectedArr.length > 0 ? me.currentSelectedArr[0] : range.startContainer, 'div', true);
				  me.t_currentComp=div;
			}
			initDialog(cmdName);
		}
	};
	//Command:Delete CustomDisplay
	me.commands["deletecustomdisplay"]={
		queryCommandState : function() {
			if(this.highlight){
				return -1;
			}
			var range = this.selection.getRange();
			var parentNode = domUtils.findParentByTagName(range.startContainer, 'div', true)
			while(parentNode){
				if(parentNode.getAttribute("comptype")=="custom-display"){
					return 0;
				}
				parentNode = domUtils.findParentByTagName(parentNode, 'div', true)
			}
		},
		execCommand:function() {
			  var range = this.selection.getRange(),
              div = domUtils.findParentByTagName(me.currentSelectedArr.length > 0 ? me.currentSelectedArr[0] : range.startContainer, 'div', true);
	          deleteCustomDisplay(div,range);
	          reset();
		}
	}
};