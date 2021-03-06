/**
 * 组织选择器
 * @param conf
 * 
 * conf 参数
 * 
 * orgId：组织ID
 * orgName:组织名称
 * @returns
 */
function OrgDialog(conf)
{
	var dialogWidth=650;
	var dialogHeight=500;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	var url=__ctx + '/platform/system/sysOrg/dialog.ht';
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(conf.callback)
	{
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.orgId,rtn.orgName);
		}
	}
}


/**
 * 用户选择器 .
 * UserDialog({callback:function(userIds,fullnames,emails,mobiles){},selectUsers:[{id:'',name:''}]})
 */
function UserDialog(conf){
	
	var dialogWidth=650;
	var dialogHeight=500;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=__ctx + "/platform/system/sysUser/dialog.ht?isSingle=" + conf.isSingle;
	url=url.getNewUrl();
	

	//重新选择的时候，展现上次数据
	var selectUsers="";
	if(  conf.selectUserIds && conf. selectUserNames){
		selectUsers={
				selectUserIds:conf.selectUserIds ,
				selectUserNames:conf. selectUserNames
		}
	}	
	var rtn=window.showModalDialog(url,selectUsers,winArgs);
	
	if(rtn && conf.callback){
		var userIds=rtn.userIds;
		var fullnames=rtn.fullnames;
		var emails=rtn.emails;
		var mobiles=rtn.mobiles;
		
		conf.callback.call(this,userIds,fullnames,emails,mobiles);
	}
}

/**
 * 这个选择器只用户在流程那里选择人员或部门。
 * 调用方法：
 * 
 * 
 * FlowUserDialog({selectUsers:[{type:'',id:'',name:''}],callback:function(aryType,aryId,aryName){}});
 * selectUsers，表示之前选择的人员，使用json数组来表示。
 * 数据格式:{type:'',id:'',name:''}
 * type:选择的类型。可能的值 user,org,role,pos .
 * id:选择的ID
 * name:显示的名称。
 * 
 * JSON数组：
 * 这个回调函数包括三个参数 ，这三个参数都为数组。
 * objType：返回的类型,可能的值(user,org,role,pos) 。
 * objIds:对象的Id。
 * objNames：对象的名称。
 */
function FlowUserDialog(conf){
	var dialogWidth=650;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=__ctx + "/platform/system/sysUser/flowDialog.ht";
	url=url.getNewUrl();
	//重新选择的时候，展现上次数据,必须传入
	var selectUsers="";
	if(  conf.selectUsers!=undefined && conf.selectUsers!=null && conf.selectUsers!=""){
		selectUsers=conf.selectUsers;
	}
	
	var rtn=window.showModalDialog(url,selectUsers,winArgs);
	if(rtn && conf.callback){
		conf.callback.call(this,rtn.objType,rtn.objIds,rtn.objNames);
	}
}


/**
 * 角色选择器 
 */
function RoleDialog(conf)
{
	var dialogWidth=695;
	var dialogHeight=500;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	var url=__ctx + '/platform/system/sysRole/dialog.ht';
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(conf.callback)
	{
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.roleId,rtn.roleName);
		}
	}
}


/**
 * 岗位选择器
 * @param conf
 * 
 * dialogWidth：对话框高度 650
 * dialogHeight：对话框高度 500
 * 
 * conf.callback
 * 参数：
 * 		posId：岗位ID
 * 		posName:岗位名称
 * @returns
 */
function PosDialog(conf)
{
	var dialogWidth=680;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	var url=__ctx + '/platform/system/position/dialog.ht';
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.posId,rtn.posName);
		}
	}
}

/**
 * 用户选择器 
 */
function UserParamDialog(conf){
	var dialogWidth=650;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	var args={cmpIds:conf.cmpIds,cmpNames:conf.cmpNames};
	var url=__ctx + '/platform/system/sysUserParam/dialog.ht?nodeUserId='+conf.nodeUserId;
	
	var rtn=window.showModalDialog(url,args,winArgs);
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.paramValue1,rtn.paramValue2);
		}
	}
}


/**
 * 用户选择器 
 */
function OrgParamDialog(conf){
	var dialogWidth=650;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	var args={cmpIds:conf.cmpIds,cmpNames:conf.cmpNames};
	var url=__ctx + '/platform/system/sysOrgParam/dialog.ht?nodeUserId='+conf.nodeUserId;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,args,winArgs);
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.paramValue1,rtn.paramValue2);
		}
	}
}


/**
 * 上下级选择器 
 */

function UplowDialog(conf){
	var dialogWidth=650;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	var url=__ctx + '/platform/bpm/bpmNodeUserUplow/dialog.ht';
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.json,rtn.show);
		}
	}
}

/**
 *上级部门类型选择器
 */

function typeSetDialog(conf){
	var dialogWidth=500;
	var dialogHeight=360;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	var args={cmpIds:conf.cmpIds,cmpNames:conf.cmpNames};
	var url=__ctx + '/platform/bpm/bpmDefinition/typeSetDialog.ht';
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,args,winArgs);
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.json,rtn.show);
		}
	}
}
