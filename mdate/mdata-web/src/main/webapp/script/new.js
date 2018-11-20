/**
 * Created by zzl on 2015/7/5.
 */

var operateUri = 'operate.do';

function page(pageNo) {
    if ($(document.activeElement).parent().hasClass('disabled')) {
        return;
    }
    if (pageNo == -1) {
        var pageCount = parseInt($('span.pageCount').text());
        pageNo = pageCount;
    }
    var url = pub.setParams(location.href, {
        pageNo: pageNo
    });
//            location = url;
    loadUrl(url);
}

function loadUrl(url) {
    if (location.href == url) {
        location.reload();
    }
    else {
        location = url;
    }
}

window.new_ = function () {
    loadUrl(operateUri + '?action=new_');
};

window.edit = function (id) {
    if (!id) {
        var $chk = $('.check-list').find('td input:checked');
        if ($chk.length != 1) {
            alert('请选择一条记录');
            return;
        }
        id = $chk.val();
    }
    loadUrl(operateUri + '?action=edit&id=' + id);
};

window.del = function (id) {
    var ids = [];
    if (!id) {
        var $chks = $('.check-list').find('td input:checked');
        $chks.each(function() {
            ids.push($(this).val());
        });
    }
    else {
        ids.push(id);
    }
    if (!ids.length) {
        alert('请至少选择一条记录');
        return;
    }
    if (!confirm('确定要删除吗')) {
        return;
    }
    pub.postJson('operate.json', {
            action: 'delete',
            ids: ids
        }, function (result) {
            if (!result.ok) {
                alert(result.msg);
            }
            else {
                location.reload();
            }
        },
        pub.ajaxFailure
    );
};


$(function () {
    $('select.pageSize').change(function () {
        var params = {pageSize: $(this).val()};
        var url = pub.setParams(location.href, params);
        loadUrl(url);
    });

});

//
$(function () {

    var $queryPane = $('.query-pane');
    var $selects = $queryPane.find('select');
    //$selects.kendoDropDownList();
    var $form = $queryPane.find('form');
    $queryPane.find('input[type=reset]').click(function() {
        $form.clearForm();
        //_reset();
        for(var n = 0; n < $selects.length; n++) {
            $($selects[n]).data('kendoDropDownList').value('');
        }

        return false;
    });



    var $resultList = $('.result-list');
    var $tbody = $resultList.find('tbody');
    var $headCheck = $resultList.find('th :checkbox');
    $tbody.on('click', 'tr', function (e) {
        var checkChanged = false;
        var nodeName = e.target.nodeName;
        if (nodeName == 'TD') {
            var chk = $(this).find(':checkbox')[0];
            if (e.ctrlKey) {
                chk.checked = !chk.checked;
            }
            else {
                var $checked = $tbody.find(':checked');
                if (!$checked.length) {
                    chk.checked = true;
                }
                else if ($checked.length > 1) {
                    $checked.prop('checked', false);
                    chk.checked = true;
                }
                else {
                    if ($checked[0] == chk) {
                        chk.checked = false;
                    }
                    else {
                        $checked[0].checked = false;
                        chk.checked = true;
                    }
                }
            }
            checkChanged = true;
        }
        else if (nodeName == 'INPUT') {
            checkChanged = true;
        }
        if (checkChanged) {
            var checkedCount = $tbody.find(':checked').length;
            $headCheck.prop('checked', checkedCount == $tbody.find(':checkbox').length);
            $(document).trigger('checkedChange', [checkedCount]);
        }
    });
    $headCheck.click(function () {
        var $chks = $tbody.find(':checkbox');
        $chks.prop('checked', this.checked);
        $(document).trigger('checkedChange', [this.checked? $chks.length: 0]);
    });
    //$(document).on('checkedChange', function(e, count) {
    //
    //});
    //
 /**
 *jie
 * @param id
 */
    window.add = function (id) {
        var ids = [];
        if (!id) {
            var $chks = $('.check-list').find('td input:checked');
            $chks.each(function() {
                ids.push($(this).val());
            });
        }
        else {
            ids.push(id);
        }
        if (!ids.length) {
            alert('请至少选择一条记录');
            return;
        }
        if (!confirm('确定要推送吗')) {
            return;
        }
        pub.postJson('information_operate.json', {
                action: 'information',
                ids: ids
            }, function (result) {
                if (!result.ok) {
                    alert(result.msg);
                }
                else {
                    location.reload();
                }
            },
            pub.ajaxFailure
        );

    };


    window.del_information = function (id) {
        var ids = [];
        if (!id) {
            var $chks = $('.check-list').find('td input:checked');
            $chks.each(function() {
                ids.push($(this).val());
            });
        }
        else {
            ids.push(id);
        }
        if (!ids.length) {
            alert('请至少选择一条记录');
            return;
        }
        if (!confirm('确定要删除吗')) {
            return;
        }
        pub.postJson('information_operate.json', {
                action: 'delete',
                ids: ids
            }, function (result) {
                if (!result.ok) {
                    alert(result.msg);
                }
                else {
                    location.reload();
                }
            },
            pub.ajaxFailure
        );
    };

});
