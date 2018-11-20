/**
 * Created by BigDataGPDI on 2016/8/23.
 */

/**
 * 系统菜单的修改
 * */
function sys_menu_css() {
    $('.pages').click(function() {
        var allPages = $('.pages');
        var i;
        for(i=0;i<allPages.length;i++) {
            if($(allPages[i]).siblings('.submenu').css('display') == 'block') {
                $(allPages[i]).siblings('.submenu').hide();
            }
        }
        if($('.pages').siblings('.submenu').css('display') == 'block') {
            $(this).hide();
        }
        if($(this).siblings('.submenu').css('display')=='none'){
            $(this).siblings('.submenu').show();
        }else{
            $(this).siblings('.submenu').hide();
        }
    });
}
/**
 * 系统菜单点击响应事件
 * */
function sys_menu_clicked(e, nodeUrl) {
    //样式修改
    // var a = $(e);
    // $('.submenu').find('a.selected').removeClass('selected');
    // a.addClass('selected');
    $('.nav-pills').find('a').css("background-color","transparent");
    $(e).css("background-color","lightcyan");
    var url;
    if(nodeUrl) {
        url = nodeUrl;
    }
    $('#frameQuery').attr('src', url);
}


