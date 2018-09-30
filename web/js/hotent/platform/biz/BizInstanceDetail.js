
/**
* BizInstanceDetail
*/

Ext.define('mobile.BizInstanceDetail', {
    extend: 'Ext.form.Panel',
    
    name: 'bizInstanceDetail',

    constructor: function (config) {
    	
    	config = config || {};
    	
    	this.taskId = config.taskId;
    	
    	Ext.apply(config,{
    		title:'BizInstance',
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