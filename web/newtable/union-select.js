//级联select:一级select的值发生改变，触发二级的选项改变
//@ sourceURL=union-select.js
$("#select-first").change(function(){
    //清空二级select的旧选项
    $("#select-second").empty();
    //请求二级select选项数据地址
    var targetUrl = $(this).attr("data-getDataUrl");
    //获得一级select的值
    var firstValue = $(this).val();
    //如果一级select的值为null，隐藏二,并返回
    if(firstValue == ''){
        $("#select-second").fadeOut("slow");
        return;
    }

    //根据一级select的值，异步获取数据更新二级的选项
    $.ajax({
        type:'get',
        url:targetUrl,
        data:{
            parentId:firstValue
        },
        async: false,//使用同步的方式,true为异步方式
        cache:false,
        dataType:'json',
        success:function(datas){
            //遍历回传的数据添加到二级select
            $.each(datas, function() {
                var options = '<option value="'+this.userId+'">'+this.fullname+'</option>';
                $("#select-second").append(options);
            });
            // 刷新二级select
            $("#select-second").selectpicker('refresh');
        },
        error:function(){
            console.log("请求当前组织的用户失败！" + "union-select.js");
        }

    });
});