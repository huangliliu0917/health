/**
 * Created by Administrator on 2016/11/29.
 */
var treatmentApp={};
treatmentApp.buildList=function(list){
    if(list.length<=0){
        $(".weui_panel_bd").append('<h4 class="weui_media_title" style="text-align: center">暂无健康管理报告</h4>');
    }
    for(var i=0;i<list.length;i++){
        var treatment=list[i];
        var html='<a href="/app/showFormList?customerId='+customerId+'&id='+treatment.id+'" class="weui_media_box weui_media_appmsg weui_panel_ft"> ' +
            '<div class="weui_media_bd"> <h4 class="weui_media_title">'+treatment.name+'</h4> ' +
            '<p class="weui_media_desc">'+treatment.date+'</p> ' +
            '</div> <span class="weui_cell_ft"></span> </a>';
        $(".weui_panel_bd").append(html);
    }


};

treatmentApp.loadList=function(){
    $.ajax({
        type: "GET",
        dataType: 'json',
        url: "showTreatmentList",
        data: {customerId:customerId},
        success: function (result) {
            var list=result.list;
            treatmentApp.buildList(list);
        }
    });
};