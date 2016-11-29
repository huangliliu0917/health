/**
 * Created by Administrator on 2016/11/29.
 */
var messageApp={};
messageApp.buildList=function(list){
    for(var i=0;i<list.length;i++){
        var message=list[i];
        var html='<a href="/app/showMessage?customerId='+customerId+'&id='+message.id+'" class="weui_media_box weui_media_appmsg"> ' +
            '<div class="weui_media_hd"> <img class="weui_media_appmsg_thumb" src="'+message.pictureUrl+'" alt=""/> ' +
            '</div> <div class="weui_media_bd"> ' +
            '<h4 class="weui_media_title">'+message.title+'</h4> ' +
            '<p class="weui_media_desc">'+message.summary+'</p>' +
            '</div></a>';
        $(".weui_panel_bd").append(html);
    }


};

messageApp.loadList=function(){
    if(isEmpty){
        return;
    }
    $.ajax({
        type: "GET",
        dataType: 'json',
        url: "showMessageList",
        data: {lastId:lastId,customerId:customerId},
        success: function (result) {
            var list=result.list;
            if(list.length<=0){
                isEmpty=true;
            }else {
                lastId=list[list.length-1].id;
                messageApp.buildList(list);
            }
        }
    });
};