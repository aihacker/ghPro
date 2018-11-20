/**
 * Created by xian on 2017/4/25.
 */


(function () {
    var name = sessionStorage.getItem('name');
    var perCodes = sessionStorage.getItem('perCode');
    if(name){
        perCodes = perCodes.toString().replace('[','');
        perCodes = perCodes.toString().replace(']','');
        perCodes = perCodes.toString().split(',');
    }else {
        var url = location.href;
        url = url.toString().split('app/');
        url = url[0];
        location.href= url+"app/";
        return;
    }
    //var percode = '01';
    var codes = [];
    var licArr = $('body').find('li');
    var inputArr = $('body').find('input');
    codes.push(licArr);
   codes.push(inputArr);
    var lis = [];
    for(var i =0;i<perCodes.length;i++){
        if(perCodes[i].toString().trim()=='00'){
            return
        }
    }
    lis = hidePageAndButton(perCodes,codes);
    if(lis){
        showPageAndButton(lis);
    }else {
        //console.log("else-----------------")
        licode = $('body').find('li');
    }

})(jQuery);
function hidePageAndButton(percodes,codes) {
    var lis = [];
    for(var i = 0;i<percodes.length;i++) {
        for(var j = 0;j<codes.length;j++) {
            $(codes[j]).each(function (index, item) {
                var codevalue = $(item).attr('code');
                if ($(item).attr('code') != undefined && $(item).attr('code') != percodes[i].trim()) {
                    $(item).hide();
                } else {
                    lis.push(item);
                }
            })
        }
    }
    return lis;
}
function showPageAndButton(lis) {
    for (var j = 0;j<lis.length;j++){
        $(lis[j]).show();
    }
}
