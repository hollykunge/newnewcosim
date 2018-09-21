/**
 * 工具选择器 .
 * ToolDialog({callback:function(userIds,fullnames,emails,mobiles){},selectUsers:[{id:'',name:''}]})
 */
function ToolDialog(conf){
	var dialogWidth=650;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	url=__ctx + "/cloud/tool/chooseTools.ht";
	url=url.getNewUrl();
	

	//重新选择的时候，展现上次数据
	var selectUsers="";
	if(conf.selectUserIds && conf. selectUserNames){
		selectUsers={
				selectUserIds:conf.selectUserIds ,
				selectUserNames:conf. selectUserNames
		}
	}	
	var rtn=window.open(url,selectUsers,winArgs);
	
	if(rtn && conf.callback){
		var toolIds=rtn.toolIds;
		var toolNames=rtn.toolNames;
		var toolTypes=rtn.toolTypes;
		var toolAddresses=rtn.toolAddresses;
		
		conf.callback.call(this,toolIds,toolNames,toolTypes,toolAddresses);
	}
}