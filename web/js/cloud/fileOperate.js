function openModel(perpath,fileId){
		url = ""+perpath+"/cloud/research/openModel.ht?fileId="+fileId;
		window.open(url);
	}
function openFile(perpath,fileId){

	url = ""+perpath+"/cloud/research/openFile.ht?fileId="+fileId;
	window.open(url);
}
function downloadFile(perpath,fileId){
	var url = ""+perpath+"/platform/system/sysFile/download.ht?fileId="+fileId;
	window.open(url);
}
function getFileInfo(perpath,fileId){
	var url = ""+perpath+"/platform/system/sysFile/get.ht?fileId="+fileId;
	window.open(url);
}
	