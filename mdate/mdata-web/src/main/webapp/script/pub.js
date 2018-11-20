/**
 * Created with IntelliJ IDEA.
 * User: zzl
 * Date: 2015/6/23
 * To change this template use File | Settings | File Templates.
 */
window.pub = {

    postJson0: function (url, data, success, failure) {

        $.ajax(url, {
            type: 'POST',
            dataType: 'json',
            data: data,
            success: success,
            error: failure,
            timeout: failure,
            traditional: true
        });

    },

    jsonFailure: function(json) {
        alert(json.msg);
    },

    postJson: function(url, data, success, failure) {
        return pub.ajax('POST', url, data, success, failure);
    },

    postJSON: function(url, data, success, failure) {
        return pub.ajax('POST', url, data, success, failure);
    },

    getJSON: function(url, data, success, failure) {
        return pub.ajax('GET', url, data, success, failure);
    },

    ajax: function (method, url, data, success, failure) {
        if(!failure) {
            failure = pub.jsonFailure
        }
        var $loading = $('#loading');
        $loading.show();
        $.ajax(url, {
            type: method,
            dataType: 'json',
            data: data,
            success: function(json) {
                if(json.ok) {
                    success(json);
                }
                else {
                    failure(json);
                }
            },
            error: function(xhr, status, err) {
                failure({
                    ok: false,
                    msg: status + ': ' + err
                });
            },
            traditional: true,
            complete: function() {
                $loading.hide();
            }
        });
    },

    ajaxFailure: function() {
        alert('failed..');
    },

    getParam: function(name) {
        return pub.parseParams()[name];
    },

    parseParams: function (str) {
        if(str === undefined) {
            str = window.location.search;
        }
        if (typeof str !== 'string') {
            return {};
        }

        str = str.trim().replace(/^(\?|#|&)/, '');

        if (!str) {
            return {};
        }

        return str.split('&').reduce(function (ret, param) {
            var parts = param.replace(/\+/g, ' ').split('=');
            var key = parts[0];
            var val = parts[1];

            key = decodeURIComponent(key);
            // missing `=` should be `null`:
            // http://w3.org/TR/2012/WD-url-20120524/#collect-url-parameters
            val = val === undefined ? null : decodeURIComponent(val);

            if (!ret.hasOwnProperty(key)) {
                ret[key] = val;
            } else if (Array.isArray(ret[key])) {
                ret[key].push(val);
            } else {
                ret[key] = [ret[key], val];
            }

            return ret;
        }, {});
    },

    parseParamsToArray: function(qs) {
        var result = [];
        var sParams = qs.split('&');
        for(var n = 0; n < sParams.length; n++) {
            var sParam = sParams[n];
            var parts = sParam.replace(/\+/g, ' ').split('=');
            var name = parts[0];
            if(!name) {
                continue;
            }
            var val = parts[1];

            name = decodeURIComponent(name);
            val = val === undefined ? null : decodeURIComponent(val);
            result.push({
                name: name,
                value: val
            });
        }
        return result;
    },

    setParams: function (url, newParams) {
        var hash = undefined;
        var hashPos = url.lastIndexOf('#');
        if(hashPos != -1) {
            hash = url.substr(hashPos + 1);
            url = url.substr(0, hashPos);
        }

        var qPos = url.indexOf('?');
        var qs = '';
        if (qPos == -1) {
            url += '?';
        }
        else {
            qs = url.substr(qPos + 1);
            url = url.substr(0, qPos + 1);
        }
        //var params = pub.parseParams(qs);
        //$.extend(params, newParams);
        //url += $.param(params);
        var params = pub.parseParamsToArray(qs);
        var n;
        for(var name in newParams) {
            if(!newParams.hasOwnProperty(name)) {
                continue;
            }
            var value = newParams[name];
            var replaced = false;
            var removed = false;
            //for(n = 0; n < params.length; n++) {
            for(n = params.length - 1; n >= 0; --n) {
                if(params[n].name == name) {
                    if(value === null) {
                        params.splice(n, 1);
                        removed = true;
                    }
                    else if(replaced) {
                        //unshift?
                    }
                    else {
                        params[n].value = value;
                        replaced = true;
                    }
                }
            }
            if(!replaced && !removed && value !== null) {
                params.push({
                    name: name,
                    value: value
                });
            }
        }
        var newQs = '';
        for(n = 0; n < params.length; n++) {
            if(n > 0) {
                newQs += '&';
            }
            var param = params[n];
            var sParam = param.name + '=' + encodeURIComponent(param.value);
            newQs += sParam;
        }
        url += newQs;
        if(hash !== undefined) {
            url += '#' + hash;
        }
        return url;
    },

    formatTime: function(date) {
        var yyyy = date.getFullYear().toString();
        var mm = (date.getMonth()+1).toString(); // getMonth() is zero-based
        var dd  = date.getDate().toString();
        var sDate = yyyy + '-' + (mm[1]?mm: '0'+mm[0]) + '-' +
            (dd[1]?dd: '0'+dd[0]); // padding

        var hh = date.getHours().toString();
        var nn = date.getMinutes().toString();
        var ss = date.getSeconds().toString();
        var sTime = (hh[1]? hh: '0' + hh[0]) + ':' +
            (nn[1]? nn: '0' + nn) + ':' + (ss[1]? ss: '0' + ss);

        return sDate + ' ' + sTime;
    },

    fillCombo: function(el, items, nullItem) {
        var fragment = document.createDocumentFragment();
        var opt;
        if(nullItem !== undefined) {
            opt = document.createElement('option');
            if(typeof(nullItem) === 'object') {
                opt.value = nullItem.id;
                opt.innerHTML = nullItem.text;
            }
            else {
                opt.value = '';
                opt.innerHTML = nullItem;
            }
            fragment.appendChild(opt);
        }
        for(var n = 0; n < items.length; n++) {
            var item = items[n];
            opt = document.createElement('option');
            opt.innerHTML = item.text;
            opt.value = item.id;
            fragment.appendChild(opt);
        }
        el.innerHTML = '';
        el.appendChild(fragment);
    },

    lastOf: function(arr) {
        if(!arr || !arr.length) {
            return undefined;
        }
        return arr[arr.length - 1];
    },

    firstOf: function(arr) {
        if(!arr || !arr.length) {
            return undefined;
        }
        return arr[0];
    }

};