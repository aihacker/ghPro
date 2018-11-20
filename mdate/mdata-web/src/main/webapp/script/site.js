function _is_form_dirty(form)
{
    var el, opt, hasDefault, i = 0, j;
    while (el = form.elements[i++]) {
        switch (el.type) {
            case 'text' :
            case 'textarea' :
            case 'hidden' :
                if (el.value != el.defaultValue) return true;
                break;
            case 'checkbox' :
            case 'radio' :
                if (el.checked != el.defaultChecked) return true;
                break;
            case 'select-one' :
            case 'select-multiple' :
                j = 0,hasDefault = false;
                while (opt = el.options[j++])
                    if (opt.defaultSelected) hasDefault = true;
                j = hasDefault ? 0 : 1;
                while (opt = el.options[j++])
                    if (opt.selected != opt.defaultSelected) return true;
                break;
        }
    }
    return false;
}

function _reset(clear) {
    var evt = window.event;
    var $frm = $(evt.srcElement).parents('form:first');
    if (typeof(clear) == 'undefined') {
        clear = !_is_form_dirty($frm[0]);
    }
    if (evt.shiftKey || clear) {
        $frm.clearForm();
        $frm.find('select').each(function(n) {
            if(this.selectedIndex == -1 && this.options.length && !this.options[0].value) {
                this.selectedIndex = 0;
            }
        });
    }
    else {
        $frm.resetForm();
    }
    return false;
}

function returnUrl(uri) {
    if(uri.indexOf('/') == -1) {
        var tUri = location.pathname;
        var tPos = tUri.lastIndexOf('/');
        uri = tUri.substr(0, tPos + 1) + uri;
    }
    var url = _loadUrl(uri);
    window.location = url;
}

if (window.$) {
    $(function() {
        _saveUrl();
        _updateReturnUrlFields();
    });
}

function _updateReturnUrlFields() {
    $(':hidden[name=returnUrl]').each(function(index) {
        var uri = this.value;
        this.value = _loadUrl(uri);
    });
}

function _loadUrl(uri) {
    if (uri.charAt(0) != '/' && uri.indexOf('http:') == -1) {
        var path = location.pathname;
        uri = path.substring(0, path.lastIndexOf('/') + 1) + uri;
    }
    var urls = sessionStorage.urls || '';//(top.window.name || '');
    var pos = urls.indexOf('\n' + uri);
    if (pos == -1) {
        return uri;
    }
    var pos2 = urls.indexOf('\n', pos + uri.length + 1);
    if (pos2 == -1) {
        alert('something wrong');
    }
    var url = urls.substring(pos + 1, pos2);
    return url;
}

function _saveUrl() {  //\n(url)\n(url)\n
    var url = location.href;
    var slashPos = url.indexOf('/', 'http://'.length + 1);
    url = url.substr(slashPos);
    if(url.indexOf('?') == -1) {
        //unnecessary
        return;
    }

    var qs = location.search;
    qs = qs && qs.substr(1);
    var urls = sessionStorage.urls || '\n';// top.window.name || '';
    var uri = window.uri || location.pathname;
    var pos = urls.indexOf('\n' + uri);
    if (pos == -1) {
        urls = '\n' + url + urls;
    }
    else {
        var pos2 = urls.indexOf('\n', pos + uri.length + 1);
        if (pos2 == -1) {
            alert('something wrong');
        }
        urls = '\n' + url + urls.substring(0, pos) + urls.substr(pos2);
    }
    //top.window.name = urls;
    sessionStorage.urls = urls;
}

//
$(function() {

    $('.nav-tree').on('click', 'a', function(e) {
        var $a = $(this);
        if($a.data('raw')) {
            return;
        }
        e.preventDefault();
        var url = $a.attr('href');
        if(url == '#') {
            return;
        }
        //alert($a.attr('href'));
        returnUrl(url);
    });

});