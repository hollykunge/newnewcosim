
/**
* BizSegProcLinkDetail
*/

Ext.define('mobile.BizSegProcLinkDetail', {
    extend: 'Ext.form.Panel',
    
    name: 'bizSegProcLinkDetail',

    constructor: function (config) {
    	
    	config = config || {};
    	
    	this.taskId = config.taskId;
    	
    	Ext.apply(config,{
    		title:'BizSegProcLink',
    		items: [
    			{
	    			xtype: 'fieldset',
		    		items:[
		    			
		    		]
	    		}
    		]
    	});
    	
    	this.callParent([config]);
    }
    
});