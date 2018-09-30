
/**
* BizInstanceSegmentForm
*/

Ext.define('mobile.BizInstanceSegmentForm', {
    extend: 'Ext.form.Panel',
    
    name: 'bizInstanceSegmentForm',

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
    },
    
    formSubmit:function(config){
		var voteAgree = config.voteAgree;
		var formpanel = config.formpanel;
		formpanel.submit({
		    url: 'platform/biz/bizInstanceSegment/save.ht',
	        params: {
				json:'{voteAgree:'+voteAgree+'}'
	        },
	        method: 'POST',
	        success: function(form,action,response) 
	        {
	        	var obj = Ext.util.JSON.decode(response);
	        },
	        failure: function(form,action,response)
	        {
				var obj = Ext.util.JSON.decode(response);
				Ext.Msg.alert('', obj.msg);
	        }
		});
	}
    
});