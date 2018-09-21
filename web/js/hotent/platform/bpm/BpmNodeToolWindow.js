function BpmNodeToolWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/cloud/toolBpmNode/list.ht?&setId=" + conf.setId +"&defId=" + conf.defId;

	var dialogWidth=800;
	var dialogHeight=380;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:1,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.open(url,"",winArgs);
	if (conf.callback) {
		conf.callback.call(this,rtn);
	}
}