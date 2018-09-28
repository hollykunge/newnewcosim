
/**
* BizDefDetail
*/

Ext.define('mobile.BizDefDetail', {
    extend: 'Ext.form.Panel',
    
    name: 'bizDefDetail',

    constructor: function (config) {
    	
    	config = config || {};
    	
    	this.taskId = config.taskId;
    	
    	Ext.apply(config,{
    		title:'BizDef',
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