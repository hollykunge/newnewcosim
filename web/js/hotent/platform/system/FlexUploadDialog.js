/**
 * 附件上传选择窗口。
 * dialogWidth：窗口宽度，默认值700
 * dialogWidth：窗口宽度，默认值500
 * callback：回调函数
* 回调参数如下：
 * key:参数key
 * name:参数名称
 * 使用方法如下：
 * 
 * FlexUploadDialog({isSingle:false,callback:picCallBack{
 *		//回调函数处理
 *	}});
 * @param conf
 */
function FlexUploadDialog(conf) {
	if(!conf) conf={};
	var isSingle=conf.isSingle?1:0;
	var url=__ctx + "/platform/system/sysFile/dialog.ht?isSingle="+isSingle;
	var dialogWidth=700;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth:"+conf.dialogWidth+"px;dialogHeight:"+conf.dialogHeight
		+"px;help:" + conf.help +";status:" + conf.status +";scroll:" + conf.scroll +";center:" +conf.center;
	if (!conf.isSingle)conf.isSingle = false;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(rtn!=undefined){
		if(conf.callback){
			var fileIds=rtn.fileIds;
			var fileNames=rtn.fileNames;
			var filePaths=rtn.filePaths;
			var extPath=rtn.extPath;
			
			conf.callback.call(this,fileIds,fileNames,filePaths,extPath);
		}
	}
}