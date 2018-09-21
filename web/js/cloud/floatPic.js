var isIE=!(navigator.userAgent.indexOf('MSIE')==-1);
var news;
var curNew=0;
var timer;

function ieVer(){
	var browser=navigator.appName;
	var b_version=navigator.appVersion; 
	var version=b_version.split(";"); 
	var trim_Version=version[1].replace(/[ ]/g,""); 
	if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0"){ 
		return 6;
	} 
	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0"){ 
		return 7;
	} 
	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE8.0"){ 
		return 8;
	} 
	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE9.0"){ 
		return 9;
	}
	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE10.0"){ 
		return 10;
	}
	
	return 10;
}

function init()
{
	var div=document.getElementById("NewsPic");
	if(!div)return;
	var nav=document.createElement("DIV");
	nav.className="Nav";
	var nodes;
	if(isIE){
		if(ieVer()>8){
			nodes=childrenNodes(div.childNodes);
		}else{
			nodes=div.childNodes;
		}
	}else{
	    nodes=childrenNodes(div.childNodes);
	}
	
	news=new Array(nodes.length);
	var count = 0;
	if(isIE){//IE浏览器
		if(ieVer()>8){
			count = nodes.length-1;
		}else{
			count = nodes.length-2;
		}
	}	
	else{//其他浏览器
		count = nodes.length-1;
	}
	for(var i=count;i>=0;i--){
	    var element=nodes[i];
	    
		news[i]={};
		news[i].Element=element;
		if(isIE){
			if(ieVer()>8){
				news[i].Text=element.getAttribute("title");
				news[i].Url=element.getAttribute("href");
			}else{
				news[i].Text=element.title;
				news[i].Url=element.href;	
			}
		}else{
			news[i].Text=element.getAttribute("title");
			news[i].Url=element.getAttribute("href");
		}
		
		var n=document.createElement("span");
		n.innerHTML="<a herf=\"javascript:;\" onclick=\"javascript:curNew="+(i-1)+";change();\">"+(i+1)+"</a>";
		if(i==curNew)n.className="Cur";
		nav.appendChild(n);
		
		news[i].LinkElement=n;
	}
	div.appendChild(nav);
	curNew--;
	change();

}
function childrenNodes(node)
{
    var c=new Array();
    for(var i=0;i<node.length;i++)
    {
        if(node[i].nodeName.toLowerCase()=="a")
            c.push(node[i]);
    }
    return c;
}
function change()
{
    var div=document.getElementById("NewsPic");
    var text=document.getElementById("NewsPicTxt");
    if(!div)return;
    curNew=curNew+1;
    if(curNew>=news.length)curNew=0;    
    for(var i=0;i<=news.length-1;i++){
        if(i==curNew){        	
        	if(news[i].Element.style){
        		news[i].Element.style.display="block";
        		news[i].Element.style.visibility="visible";
        	}
            news[i].LinkElement.className="Cur";
            text.innerHTML="<a href=\""+news[i].Url+"\" title=\""+news[i].Text+"\" target=\"_blank\">"+news[i].Text+"</a>";
        }else{ 
        	if(news[i].Element.style){
        		news[i].Element.style.visibility="hidden";
        		news[i].Element.style.display="none";
        	}
            news[i].LinkElement.className="Normal";
        }
	}
    if(timer)window.clearTimeout(timer);
    timer=window.setTimeout("change()",6000);
}

