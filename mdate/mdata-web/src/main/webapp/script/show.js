/**
 * Created by zzl on 2015/7/5.
 */
$(function () {
    //var $div = $('div.op');
    //var dx = $('table.form').find('td:first').offset().left - $div.offset().left;
    //$div.css({
    //    'padding-left': dx + 'px',
    //    'text-align': 'left'
    //});

    $.validator.setDefaults({
            errorPlacement: function (error, $el) {
                error.appendTo($el.closest('td'));
            }
        }
    );

    var submitOptions = {
        dataType: 'json',
        url: 'operate.json',
        data: {action: 'save'},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                if(window.submittedCallback) {
                    window.submittedCallback(result);
                }
                else {
                    alert('保存成功');
                    returnUrl('query.html');
                }
            }
            else {
                var msg = result.msg || result.message;
                var errors = result.errors;
                if(!msg && errors) {
                    msg = '';
                    for(var key in errors) {
                        if(!errors.hasOwnProperty(key)) {
                            continue;
                        }
                        if(msg) {
                            msg += '\n';
                        }
                        msg += key + ': ' + errors[key];
                    }
                }
                alert(msg);
            }
        },
        error: function () {
            alert('form submit error..')
        }
    };

    var $form = $('form');
    if(!$form.length) {
        return;
    }
    var validateOptions = $.extend({}, window.validateOptions, {
        submitHandler: function() {
            if(window.onFormSubmit) {
                if(window.onFormSubmit($form[0], submitOptions) === false) {
                    return;
                }
            }
            $form.ajaxSubmit(submitOptions);
        }
    });
    $form.validate(validateOptions);

});


function chooseFile(e) {
    var $button = $(this);
    var w = 440;
    var h = 300;

    var windowLeft = (window.screenX || window.screenLeft);
    var x = windowLeft + $button.offset().left + $button.outerWidth();
    var y = (screen.height * 0.66 / 2) - (h / 2);
    if (x + w > screen.width) {
        x = screen.width - w;
    }
    if (y < 0) {
        y = 0;
    }
    var traits = 'toolbar=no, location=no, status=no, menubar=no, scrollbars=no';
    traits += ',width=' + w + ',height=' + h + ',left=' + x + ',top=' + y;
    var params = {
        title: $button.val()
    };
    var url = _home + '/common/file/upload.html';
    url += '?' + $.param(params);
    window.open(url, '', traits);
    var callback;
    if(typeof e == 'function') {
        callback = e;
    }
    window.fileUploadCallback = callback || function (id, name) {
        var $td = $button.closest('td');
        var $img = $td.find('img');
        $img.attr('src', _home + '/common/file/get.do?id=' + id);
        $td.find('input[type=hidden]').val(id);
    }
}

//
$(function() {
    var $form = $('form');
    if($form.attr('autocomplete') == 'off') {
        if(!$form.children('input[name=somefakename]').length) {
            $form.prepend('<input style="display:none;" type="text" name="somefakename"/>');
        }
    }
});