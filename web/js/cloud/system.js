//*****************************************
//*Event
//*****************************************
var Evt = {
	NoBubble:function(e)
	{
		e&&e.stopPropagation?e.stopPropagation():event.cancelBubble=true;
	},
	Top:function(e)
	{
		return (e||event).clientY;
	},
	Left:function(e)
	{
		return (e||event).clientX;
	}
}

//******************************************
//*xml
//******************************************
var Xml = {
	First:function(o,key)//第一个节点值
	{
		return $T(o,key)[0]&&$T(o,key)[0].firstChild?$T(o,key)[0].firstChild.nodeValue:"";
	}
}

//******************************************
//String扩展
//******************************************
String.prototype.trim = function()
{
	var str = this;
	var m = str.match(/^\s*(\S+(\s+\S+)*)\s*$/);
	return (m == null) ? "" : m[1];
}
String.prototype.strip = function()
{
    return this.replace(/<\/?[^>]+>/gi, '').trim();
}
String.prototype.escapeHTML = function()
{
    var div = document.createElement('div');
    var text = document.createTextNode(this);
    div.appendChild(text);
    return div.innerHTML;
}
String.prototype.unescapeHTML = function()
{
    var div = document.createElement('div');
    div.innerHTML = this.stripTags();
    return div.childNodes[0].nodeValue;
}
String.prototype.escapeEx = function()
{
    return escape(this).replace(/\+/g,"%2b");
}
String.prototype.replaceAll = function(a,b)
{
	return this.replace(new RegExp(a.replace(/([\(\)\[\]\{\}\^\$\+\-\*\?\.\"\'\|\/\\])/g,"\\$1"),"g"),b);
}
String.prototype.indexOfEx = function()
{
	var bi = arguments[arguments.length - 1];
	var thisObj = this;
	var idx = 0;
	if(typeof(arguments[arguments.length - 2]) == 'number')
	{
		idx = arguments[arguments.length - 2];
		thisObj = this.substr(idx);
	}
	var re = new RegExp(arguments[0],bi?'i':'');
	var r = thisObj.match(re);
	return r==null?-1:r.index + idx;
}
String.prototype.padLeft = function(str,n)
{
	var result = this;
	if(this.length<n)
	for(var i=0;i<n-this.length;i++)
		result = str+result;
	return result;
}

//******************************************
//Array扩展
//******************************************
Array.prototype.indexOf = function(obj)//返回一个对象在Array中的位置
{
    var result = -1;
    for(var i = 0; i < this.length; i++)
	{
        if(this[i] == obj)
		{
            result = i;
            break;
        }
    }
    return result;
}
Array.prototype.contains = function(obj)//检查一个对象是否包含在Array中
{
    return this.indexOf(obj) > -1;
}
Array.prototype.add = function(obj)//添加一个对象
{
    if(!(this.contains(obj)))
	{
        this[this.length] = obj;
    }
}
Array.prototype.remove = function(obj)//删除一个对象
{
    if(this.contains(obj))
	{
        var index = this.indexOf(obj);
        for(var i = index; i < this.length - 1; i++)
		{
            this[i] = this[i + 1];
        }
        this.length--;
    }
}
Array.prototype.clear = function()//清空数组
{
	this.splice(0,this.length);
}
Array.prototype.value = function(s,l)//设置默认值
{
	if(l)this.length=l;
	for(var i = 0; i < this.length; i++)
	{
		this[i] = s;
	}
}

//**********************************************************
//*StringBuilder
//**********************************************************
function StringBuilder()
{
	this._arr = new Array();
	this.add = function()
	{
		for(var i = 0;i<arguments.length;i++)
			this._arr.push(arguments[i]);
	}
	this.toString = function()
	{
		return this._arr.join("");
	}
}

function openWindow(locaction,info,width,height,top,left){
	info = info || '浏览';
	width = width || 600;
	height = height || 500;
	top = top || (document.documentElement.clientHeight/2 - 100);
	left = left || (document.documentElement.clientWidth/2 - 200);
  window.open(locaction,info,'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,width='+ width +',height=' + height + ',left=' + left + ',top=' + top);
}

function openMaxWindow(locaction,info){
    window.open(locaction,info,'toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=yes,directories=no,width='+ document.documentElement.clientWidth +',height=' + document.documentElement.clientHeight + ',left=0,top=0');
}

/** 
 * 对象是否定义
 */
function isDefined(obj) {
	return typeof(obj) == "undefined" ? false : (obj == null ? false : true);
}

/** 
 * 解析URL参数
 */
function getQueryString(name){
    var url = location.href;
    url = url.substring(url.indexOf("?")+1);
    var paras = url.split("&");
    var params = {};
    for(var i=0;i<paras.length;i++){
        var str = paras[i];
        var l = str.indexOf("=");
        params[str.substring(0,l)] = str.substring(l+1); 
    }
    var value = params[name];
    if(typeof(value) == "undefined")
        return "";
    
    return value;
}

/** 
 * 将字符串转化成json
 */
function Json(str){
	return eval('('+str+')');
}

//**********************************************************
//*Map
//**********************************************************
function ClassMap() {
	this.map = new Array();
  
	var struct = function(key, value){
		this.key = key;
		this.value = value;
 	};
 
	this.lookUp = function (key){
		for (var i = 0; i < this.map.length; i++){
			if(this.map[i].key === key ){
    			return this.map[i].value;
  			}
   		}  
   		return null;
 	};
  
	this.setAt = function (key, value){  
		for (var i = 0; i < this.map.length; i++){
			if(this.map[i].key === key ){
				this.map[i].value = value;
				return;
			}
		}   
		this.map[this.map.length] = new struct(key,value); 
	};
  
	this.removeKey = function removeKey(key){
   		var v;
		for (var i = 0; i < this.map.length; i++){
  			v = this.map.pop();
			if ( v.key === key ) continue;
    		this.map.unshift(v);
   		}
	};
  
	this.getCount = function(){
		return this.map.length;
	};
 
	this.isEmpty = function(){
		return this.map.length <= 0;
	}
}