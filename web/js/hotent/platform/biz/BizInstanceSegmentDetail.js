
/**
* BizInstanceSegmentDetail
*/

Ext.define('mobile.BizInstanceSegmentDetail', {
    extend: 'Ext.form.Panel',
    
    name: 'bizInstanceSegmentDetail',

    constructor: function (config) {
    	
    	config = config || {};
    	
    	this.taskId = config.taskId;
    	
    	Ext.apply(config,{
    		title:'BizInstanceSegment',
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