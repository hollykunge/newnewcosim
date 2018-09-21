
/**
* CloudUseResDetail
*/

Ext.define('mobile.CloudUseResDetail', {
    extend: 'Ext.form.Panel',
    
    name: 'cloudUseResDetail',

    constructor: function (config) {
    	
    	config = config || {};
    	
    	this.taskId = config.taskId;
    	
    	Ext.apply(config,{
    		title:'CloudUseRes',
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