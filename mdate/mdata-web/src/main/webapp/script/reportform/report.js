function amount(name) {

    $.ajax({
        dataType: 'json',
        url: 'query.json',
        data: {action: 'getModel', supper_name: name},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var datas = result.data;
                var str = "";
                for (var i = 0; i < datas.num.length; i++) {
                    var valu = datas.num[i];
                    str += "<tr>" +
                        "<td>"+(1+i)+"</td>"+
                        "<td>" + valu.supplier_name + "</td>" +
                        "<td>" + valu.contract_code + "</td>" +
                        "<td>" + valu.contract_name + "</td>" +
                        "<td>" + valu.contract_type+ "</td>" +
                        "<td>" + valu.contract_amount + "</td>" +
                        "<td>" + valu.undertake_dept + "</td>" +
                        "<td>" + valu.purchase_way + "</td>" +
                        "<td>" + valu.purchase_time + "</td>" +
                        "</tr>"
                }
                $('#message').empty().append(str);//插入数据
                $('#myModal').modal();//调用模态框
                $("#myTable").trigger("update","");//清楚上一次方法里的缓存数据,更新表格
               /* $("#myTable").tablesorter();*/
                setTimeout(function () {//设置缓存
                    $("#myTable").tablesorter({ //禁止某列排序
                        headers:{
                            0:{
                                sorter:false
                            },
                            1:{
                                sorter:false
                            },
                            2:{
                                sorter:false
                            },
                            3:{
                                sorter:false
                            }
                        },
                        debug:true
                    });
                },1000);//延迟1秒
            }
        },
        error: function () {
            $("#errorMsg").html("数据加载出错");
        }
    });
}

