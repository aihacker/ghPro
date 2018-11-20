$.validator.messages = {
    required: "此字段不可为空",
    remote: "Please fix this field.",
    email: "请输入有效的email地址",
    url: "请输入有效的URL",
    date: "请输入有效的日期",
    dateISO: "请输入有效的日期(ISO)",
    number: "请输入有效的数值",
    digits: "只允许输入数字",
    equalTo: "请再次输入相同的值",
    maxlength: "不应超过{0}个字符",
    minlength: "至少需要{0}个字符",
    rangelength: "请输入{0}到{1}字符之间的值",
    range: "请输入{0}到{1}之间的值",
    max: "请输入不大于{0}的值",
    min: "请输入不小于{0}的值",
    telno: "请输入有效的电话号码",
    creditcard: "请输入正确的信用卡卡号!"
};
$.validator.messages.maxLength = $.validator.messages.maxlength;
$.validator.messages.minLength = $.validator.messages.minlength;
$.validator.messages.integer = '请输入整数';

$.validator.methods.maxLength = $.validator.methods.maxlength;
$.validator.methods.minLength = $.validator.methods.minlength;
$.validator.methods.integer = function(value, element, param) {
    if (this.optional(element)) return true;
    value = $.trim(value);
    return Math.floor(value) == value;
};

$.validator.__attributeRules = $.validator.attributeRules;
$.validator.attributeRules = function() {
    var rules = $.validator.__attributeRules.apply(this, arguments);
    if (rules.maxLength && /-1|2147483647|524288/.test(rules.maxLength)) {
        delete rules.maxLength;
    }
    return rules;
};

$.validator.defaults.__onkeyup = $.validator.defaults.onkeyup;
$.validator.defaults.onkeyup = function() {
    $.validator.__skipRemote = true;
    $.validator.defaults.__onkeyup.apply(this, arguments);
    $.validator.__skipRemote = false;
};
$.validator.methods.__remote = $.validator.methods.remote;
$.validator.methods.remote = function(value, element, param) {
    if (!$.validator.__skipRemote) {
        if (param.data) {
            param.data.valueToValidate = value;
        }
        return $.validator.methods.__remote.apply(this, arguments);
    }
    return true;
};
$.validator.defaults.ignore = '.ignore';

//
$.validator.addMethod('telno', function(value, element) {
    return this.optional(element) ||
        /^(\d{6,13}|\d{3}-\d{6,10})$/.test(value);
}, '请输入有效的电话号码');

//
$.validator.addMethod('mobile', function(value, element) {
    return this.optional(element) ||
        /^((13)|(14)|(15)|(17)|(18))\d{9}$/.test(value);
}, '请输入有效的手机号');


//
$.fn.__validate = $.fn.validate;
$.fn.validate = function () {
    var validator = $.data(this[0], 'validator');
    if (validator) {
        return validator;
    }
    validator = $.fn.__validate.apply(this, arguments);
    if (!window.disableRequiredStar) {
        validator.elements().each(function(n, e) {
            if ($(e).rules().required) {
                var $tdLabel = $(e).parent().prev();
                if(!$tdLabel.length) {
                    $tdLabel = $(e).closest('td').prev();
                }
                if ($tdLabel.text().indexOf('*') == -1) {
                    $tdLabel.prepend('<em>*</em>');
                }
            }
        });
    }
    return validator;
};
