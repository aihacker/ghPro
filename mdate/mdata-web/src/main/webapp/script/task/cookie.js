/**
 * Created by htd on 2017-08-15.
 */
var hrefUrl = document.referrer;
if (hrefUrl.indexOf("query") > 0) {
    //console.log(hrefUrl);
    checkCookie(hrefUrl);
}

function returnPage(the) {
    //console.log(the);
    var url = getCookie('hrefUrl');
    //console.log(url);
    //返回大于等于0的整数值，若不包含"Text"则返回"-1。
    if (url.indexOf("query") > 0) {
        //console.log("包含");
        the.href = url;
    } else {
        //console.log("不包含");
        the.href = document.referrer;
    }
}


function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}
//设置cookie
function setCookie(c_name, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
}
//检查是否已设置cookie
function checkCookie(url) {
    var href = getCookie('hrefUrl');
    if (href != null && href != "") {
        document.cookie = "hrefUrl="+href+"; expires=0";
        setCookie('hrefUrl', url, 365);
    } else {
        if (url != null && url != "") {
            setCookie('hrefUrl', url, 365);
        }
    }
}
