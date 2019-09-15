/**
 * Created by WIN on 2017/2/18.
 */
var totalRows = 0;
var currentPage = 1;//当前页数
var totalPage = 1;//总页数
var rowsForShow = 10;//每页显示多少行

/*显示总的记录数量*/
function showTotalCount(total) {
    totalRows = total
    $("#totalCount").text(total);
    // document.getElementById("totalCount").textContent=total;
}

function formatTime(time){
//   格式：yyyy-MM-dd hh:mm:ss
    var date = new Date(time);
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() < 10? '0'+date.getDate()+ ' ': date.getDate() + ' ';
    h = date.getHours()< 10? '0'+date.getHours() + ':': date.getHours() + ':';
    m = date.getMinutes()<10? '0' + date.getMinutes()+':':date.getMinutes()+ ':';
    s = date.getSeconds()< 10? '0'+date.getSeconds():date.getSeconds();
    return Y+M+D+h+m+s;
}


/*主动调用方法*/

/*获取数据(json)*/
function getData(url, info) {
    info["start"] = (currentPage - 1) * rowsForShow;
    info["num"] = rowsForShow;
    var result= "";
    $.ajax({
        url: url,
        data: info,
        async:false,//这里选择异步为false，那么这个程序执行到这里的时候会暂停，等待
                    //数据加载完成后才继续执行
        success: function (rs) {
            if (rs.ok) {
                result = rs
            } else {
                alert(rs.msg);
                return
            }
        }
    });
    totalPage = Math.ceil(totalRows / rowsForShow);
    $("#span-currentPage").text(currentPage);
    $("#span-totalPage").text(totalPage);
    return result;
}


/*监听方法（被动）*/
/*点击下一页*/
$("#a-next").click(function () {
    if (currentPage == totalPage){
        alert("当前已是最后一页了");
        return;
    }
    currentPage++;
    var data = getData(url, info).data;
    setHtml(data);
});

/*点击上一页*/
$("#a-last").click(function () {
    if (1 == currentPage){
        alert("当前已是第一页了");
        return;
    }
    currentPage--;
    var data = getData(url, info).data;
    setHtml(data);
});

/*更改每页显示多少行*/
$("#select-for-show").change(function () {
    rowsForShow = $(this).children("option:selected").text();
    totalPage = Math.ceil(totalRows / rowsForShow);
    currentPage = 1;
    var data = getData(url, info).data;
    setHtml(data);
});

/*首页*/
$("#a-fist-page").click(function () {
    currentPage = 1
    var data = getData(url, info).data;
    setHtml(data);
});

/*末页*/
$("#a-end-page").click(function () {
    currentPage = totalPage;
    var data = getData(url, info).data;
    setHtml(data);
});

/*页面跳转*/
$("#btn-page-go").click(function () {
    currentPage = $("#input-page-go").val();
    if (currentPage > totalPage){
        currentPage = totalPage;
    }
    var data = getData(url, info).data;
    setHtml(data);
});

