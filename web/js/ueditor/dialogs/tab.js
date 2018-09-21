window.onload = function () {
    initEvtHandler( "tabMenu" );
};

function initEvtHandler( conId ) {
    var tabMenu = $G( conId );
    for ( var i = 0, j = 0; i < tabMenu.childNodes.length; i++ ) {
        var tabObj = tabMenu.childNodes[i];
        if ( tabObj.nodeType == 1 ) {
            domUtils.on( tabObj, "click", (function ( index ) {
                return function () {
                    switchTab( index );
                };
            })( j ) );
            j++;
        }
    }
    switchTab( 0 );
};

function switchTab( index ) {
    //获取呈现元素句柄数组
    var tabMenu = $G( "tabMenu" ).getElementsByTagName( "div" ),
            tabContent = $G( "tabContent" ).getElementsByTagName( "div" ),
            i = 0, L = tabMenu.length;
    //隐藏所有呈现元素
    for ( ; i < L; i++ ) {
        tabMenu[i].className = "";
        tabContent[i].style.display = "none";
    }
    //显示对应呈现元素
    tabMenu[index].className = "on";
    tabContent[index].style.display = "block";
};