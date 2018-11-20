$(function () {
    var url = 'jsplumb.json';
    pub.postJson(url,{action:'get',flowId:window.parent.flowId},function(json){

        var data = JSON.parse(json.data);
        if(data==null){
            data = [];
        }
        var MaxIconId = 0;
        $.each(data,function(i,row){
            var id = row.id;
            if(MaxIconId>id){
                MaxIconId=id;
            }
        });
        window.parent.id = Number(MaxIconId);
        var processData = {'total':2,'list':data};

        /*创建流程设计器*/
        var _canvas = $("#flowdesign_canvas").Flowdesign({
            "processData": processData

            /*步骤右键*/
            , processMenus: {
                "delete": function (t) {
                    var activeId = _canvas.getActiveId();//右键当前的ID
                    var id = "#window"+activeId;
                    var icon = $(id);
                    jsPlumb.detachAllConnections(icon, true);
                    icon.remove();
                    window.parent.delete_icon(activeId);
                    //_canvas.refresh();
                },
                "attribute": function (t) {
                    var activeId = _canvas.getActiveId();//右键当前的ID
                    var id = "#window"+activeId;
                    console.log($(id).prop("outerHTML"));
                },
                "check":function(t){
                    var activeId = _canvas.getActiveId();//右键当前的ID
                    if(activeId.indexOf('n')!=-1){
                        activeId = activeId.replace('n','-');
                    }
                    window.parent.check_icon(activeId);
                }
            }
            , fnRepeat: function () {
                alert("步骤连接重复1");//可使用 jquery ui 或其它方式提示

            }
            , fnClick: function (myself) {
                window.parent.show_model(myself);
            }
            , fnDbClick: function () {
                console.log("双击了节点");
            }
        });


        //刷新页面
        function page_reload() {
            location.reload();
        }
        function save(){
            var processInfo = _canvas.getProcessInfo();//连接信息
            return processInfo;
        }
        function clear_canvas(){
            if (_canvas.clear()) {
                console.log("success")
            } else {
                console.log("false");
            }
        }
        function add(info){
            _canvas.addProcess(info);
        }
        function update(){
            location.reload();
        }
        function del(id){

        }
        function update_icon_componentId(icon,componentId){
            var id = "#"+icon;
            $(id).attr('component_id',componentId);
        }
        function update_icon_list(icon,data){
            var id = "#"+icon;
            var ic = $(id);
            for(p in data){
                ic.attr(p,data[p]);
            }
        }
        function getICON(icon){
            return $('#window'+icon);
        }

        window.save = save;
        window.add = add;
        window.clear_canvas = clear_canvas;
        window.update = update;
        window.update_icon_componentId = update_icon_componentId;
        window.update_icon_list = update_icon_list;
        window.getICON = getICON;

    });



});