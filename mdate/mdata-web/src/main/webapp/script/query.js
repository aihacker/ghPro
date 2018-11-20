/**
 * Created by zzl on 2015/7/5.
 */

var operateUri = 'operate.do';

function page(pageNo) {
    if(typeof pageNo != 'number' || !(pageNo >= 1)) {
        alert('请输入有效的页码');
        return;
    }
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

    loadUrl(url);


}

function loadUrl(url) {
    if(url.indexOf('/dataoperate/intelligence/subject/')!=-1){
        var index = url.indexOf('.html?');
        var params = url.substring(index+6,url.length);
        var http = _home+'/dataoperate/intelligence/subject/query2.html?'+'classId='+classId+'&'+params;
        var SubjectQueryName = sessionStorage.getItem("SubjectQueryName");
        var SubjectStatus = sessionStorage.getItem("SubjectStatus");
        var SubjectPageSize = sessionStorage.getItem("SubjectPageSize");
        if(SubjectQueryName != '' && SubjectQueryName != null){
            http += '&name=' + SubjectQueryName;
        }
        if(SubjectStatus != '' && SubjectStatus != null){
            http += '&status=' + SubjectStatus;
        }
        if(SubjectPageSize != '' && SubjectPageSize != null && params.indexOf("pageSize") == -1){
            http += '&pageSize=' + SubjectPageSize;
        }
        $("#subject_iframe").load(http);
        return;
    }
    if(url.indexOf('/dataoperate/intelligence/template/')!=-1){
        var index = url.indexOf('.html?');
        var params = url.substring(index+6,url.length);
        var http = _home+'/dataoperate/intelligence/template/query2.html?'+'classId='+classId+'&'+params;
        var TemplatePageSize = sessionStorage.getItem("TemplatePageSize");
        var TemplateQueryTemplateName = sessionStorage.getItem("TemplateQueryTemplateName");
        var TemplateQuerySubjectName = sessionStorage.getItem("TemplateQuerySubjectName");
        if(TemplateQueryTemplateName != '' && TemplateQueryTemplateName != null){
            http += '&tempName=' + TemplateQueryTemplateName;
        }
        if(TemplateQuerySubjectName != '' && TemplateQuerySubjectName != null){
            http += '&subjectName=' + TemplateQuerySubjectName;
        }
        if(TemplatePageSize != '' && TemplatePageSize != null && params.indexOf("pageSize") == -1){
            http += '&pageSize=' + TemplatePageSize;
        }
        $("#template_iframe").load(http);
        return;
    }
    if(url.indexOf('/dataoperate/intelligence/fetch/')!=-1){
        var index = url.indexOf('.html?');
        var params = url.substring(index+6,url.length);
        var http = _home+'/dataoperate/intelligence/fetch/query2.html?'+'templateId='+templateId+'&'+params;
        var FetchPageSize = sessionStorage.getItem("FetchPageSize");
        if(FetchPageSize != '' && FetchPageSize != null && params.indexOf("pageSize") == -1){
            http += '&pageSize=' + FetchPageSize;
        }
        $("#fetch_iframe").load(http);
        return;
    }
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
