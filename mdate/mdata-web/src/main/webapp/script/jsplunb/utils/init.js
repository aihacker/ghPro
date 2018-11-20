/**
 * Created by jyf on 2017/4/1.
 */

function init_icon(){

    $('.chuli-con-a').on('click',function(){
        var componentType = $(this).attr('component_type');
        if(componentType==''||componentType==undefined){
            return;
        }

        var $div = $(this).find('div');

        var url = $div.css('background-image');
        console.log(url);

        var i = url.indexOf('/css/');
        var url2 = home+url.substring(i,url.length-2);
        if(url.indexOf('.png")')==-1){
            url2 = home+url.substring(i,url.length-1);
        }
        var style = 'left:10px;top:'+Math.floor(Math.random()*250+1)+'px;background-image:url('
            +url2
            +');background-position: '
            +$div.css('background-position')
            +';';
        var process_name = $(this).find('p').text();
        id += 1;
        console.log(style);
        var info =  {
            "id":'n'+id,
            "flow_id": 1,
            "icon": '',
            "process_name": process_name,
            "process_to": '',
            "style": style,
            "componentType":componentType,
            "componentId":''
        };
        $iframe.add(info);
    });

    $('#sure').click(function(){
        var info = $iframe.save();
        var processData = [];
        $.each(info,function(a,row){
            var obj = {
                id:row.id,
                process_to:row.process_to,
                style:row.style,
                process_name:row.process_name,
                componentType:row.componentType,
                componentId:row.componentId,
                table_schema:row.table_schema
            };
            processData.push(obj);
        });
        console.log(JSON.stringify(processData));
        if(confirm("是否要配置该信息")){
            pub.postJSON(json_url,{action:'updateGendataTaskJsonById',componentId:flowId,json:JSON.stringify(processData)},function(json){
                alert("配置成功");
                window.location = home+'/dataoperate/data/show.html?id='+flowId;
            })
        }

    });


}


