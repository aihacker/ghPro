/**
 * Created by jyf on 2017/4/24.
 */
$(function () {
    var url = home + '/module/region_manage.json';
    pub.postJSON(url,{action:'get'},function(json){
        var data = json.data;
        var max = 0;
        var html = $('.st_tree');
        if(data.length>0){
            max = data[0].max;
        }
        for(var n=1;n<=3;n++){
            data.forEach(function(row,i){
                if(row.grade==n){
                    var parent = html.find('.'+row.parentId);
                    if(parent.attr('class')!=undefined){
                        parent.append('<li id="'+row.regionId+'" grade="'+row.grade+'" code="'+row.regionCode+'"><a href="#">'+row.regionName+'</a></li>');
                    }else{
                        html.find('#'+row.parentId).after('<ul class="'+row.parentId+'">' +
                        '<li id="'+row.regionId+'" grade="'+row.grade+'"  code="'+row.regionCode+'"><a href="#">'+row.regionName+'</a></li></ul>');
                    }

                }
            });
        }

        $(".st_tree").MenuTree({
            click: function (a) {
                $(".st_tree").find('li a').removeClass('checked');
                $(a).addClass('checked');
                $('.context').hide().eq(0).show();
                $('.header a').removeClass('checked').eq(0).addClass('checked');
                var $div = $('.update');
                $div.find('.name').val($(a).text());
                $div.find('.code').val($(a).parent().attr('code'));
                $('#area').attr('parent_id',$(a).parent().attr('id')).attr('grade',$(a).parent().attr('grade'));
            }
        });

        $('.header a').click(function(){
            $(this).siblings('a').removeClass('checked');
            $(this).attr('class','checked');
            var index = $(this).index();
            var context = $('.context');
            if(index==2){
                var area = $('#area').attr('parent_id');
                if(area==0){
                    return;
                }
                if(confirm("确定要删除数据吗？"))
                {
                    pub.postJSON(url,{action:'delete',id:area},function(json){
                        if($('#'+area).siblings('li').attr('id')==undefined){
                            var parent = $('#'+area).parent();
                            parent.remove();
                            $('#'+area).remove();
                        }else{
                            $('#'+area).remove();
                            $('.'+area).remove();
                        }
                    });
                }
            }else{
                context.hide().eq(index).show();
            }
        });

        $('#addNew').click(function(){
            var parentId = $('#area').attr('parent_id');
            var grade = $('#area').attr('grade');
            var add = $('.add');
            var newRegion = add.find('.name').val();
            var newCode = add.find('.code').val();
            if(newRegion ==''){
                alert("区域名称不能为空");
                return;
            }
            var data = {
                action:'save',
                regionName:newRegion,
                parentId:parentId,
                regionCode:newCode,
                grade:Number(grade)+1
            };
            pub.postJSON(url,data,function(json){
                add.find('.name').val('');
                add.find('.code').val('');
                var id = json.data;
                var parent = html.find('.'+parentId);
                if(parent.attr('class')!=undefined){
                    parent.append('<li id="'+id+'" grade="'+(Number(grade)+1)+'"' +
                    ' code="'+newCode+'">' +
                    '<a href="#">'+newRegion+'</a>' +
                    '</li>');
                }else{
                    html.find('#'+parentId).after('<ul class="'+parentId+'">' +
                    '<li id="'+id+'" grade="'+grade+'"  code="'+newCode+'">' +
                    '<a href="#">'+newRegion+'</a></li>' +
                    '</ul>');
                }
                $(".st_tree").MenuTree({
                    click: function (a) {
                        $(".st_tree").find('li a').removeClass('checked');
                        $(a).addClass('checked');
                        $('.context').hide().eq(0).show();
                        $('.header a').removeClass('checked').eq(0).addClass('checked');
                        var $div = $('.update');
                        $div.find('.name').val($(a).text());
                        $div.find('.code').val($(a).parent().attr('code'));
                        $('#area').attr('parent_id',$(a).parent().attr('id')).attr('grade',$(a).parent().attr('grade'));
                    }
                });

                alert("新增成功");
            });
        });

        $('#update').click(function(){
            var update = $('.update');
            var parentId = $('#area').attr('parent_id');
            var regionName = update.find('.name').val();
            var regionCode = update.find('.code').val();
            if(parentId==0){
                return;
            }
            var data = {
                action:'update',
                regionId:parentId,
                regionName:regionName,
                regionCode:regionCode
            };
            pub.postJSON(url,data,function(json){
                $('#'+parentId).find('a').text(regionName);
                $('#'+parentId).attr('code',regionCode);
                alert('修改成功');
            })
        });


    });

});