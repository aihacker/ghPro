/*!
 * Copyright 2017 Telerik AD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
(function (f, define) {
    define('kendo.core', ['jquery'], f);
}(function () {
    var __meta__ = {
        id: 'core',
        name: 'Core',
        category: 'framework',
        description: 'The core of the Kendo framework.'
    };
    (function ($, window, undefined) {
        var kendo = window.kendo = window.kendo || { cultures: {} }, extend = $.extend, each = $.each, isArray = $.isArray, proxy = $.proxy, noop = $.noop, math = Math, Template, JSON = window.JSON || {}, support = {}, percentRegExp = /%/, formatRegExp = /\{(\d+)(:[^\}]+)?\}/g, boxShadowRegExp = /(\d+(?:\.?)\d*)px\s*(\d+(?:\.?)\d*)px\s*(\d+(?:\.?)\d*)px\s*(\d+)?/i, numberRegExp = /^(\+|-?)\d+(\.?)\d*$/, FUNCTION = 'function', STRING = 'string', NUMBER = 'number', OBJECT = 'object', NULL = 'null', BOOLEAN = 'boolean', UNDEFINED = 'undefined', getterCache = {}, setterCache = {}, slice = [].slice;
        kendo.version = '2017.3.702'.replace(/^\s+|\s+$/g, '');
        function Class() {
        }
        Class.extend = function (proto) {
            var base = function () {
                }, member, that = this, subclass = proto && proto.init ? proto.init : function () {
                    that.apply(this, arguments);
                }, fn;
            base.prototype = that.prototype;
            fn = subclass.fn = subclass.prototype = new base();
            for (member in proto) {
                if (proto[member] != null && proto[member].constructor === Object) {
                    fn[member] = extend(true, {}, base.prototype[member], proto[member]);
                } else {
                    fn[member] = proto[member];
                }
            }
            fn.constructor = subclass;
            subclass.extend = that.extend;
            return subclass;
        };
        Class.prototype._initOptions = function (options) {
            this.options = deepExtend({}, this.options, options);
        };
        var isFunction = kendo.isFunction = function (fn) {
            return typeof fn === 'function';
        };
        var preventDefault = function () {
            this._defaultPrevented = true;
        };
        var isDefaultPrevented = function () {
            return this._defaultPrevented === true;
        };
        var Observable = Class.extend({
            init: function () {
                this._events = {};
            },
            bind: function (eventName, handlers, one) {
                var that = this, idx, eventNames = typeof eventName === STRING ? [eventName] : eventName, length, original, handler, handlersIsFunction = typeof handlers === FUNCTION, events;
                if (handlers === undefined) {
                    for (idx in eventName) {
                        that.bind(idx, eventName[idx]);
                    }
                    return that;
                }
                for (idx = 0, length = eventNames.length; idx < length; idx++) {
                    eventName = eventNames[idx];
                    handler = handlersIsFunction ? handlers : handlers[eventName];
                    if (handler) {
                        if (one) {
                            original = handler;
                            handler = function () {
                                that.unbind(eventName, handler);
                                original.apply(that, arguments);
                            };
                            handler.original = original;
                        }
                        events = that._events[eventName] = that._events[eventName] || [];
                        events.push(handler);
                    }
                }
                return that;
            },
            one: function (eventNames, handlers) {
                return this.bind(eventNames, handlers, true);
            },
            first: function (eventName, handlers) {
                var that = this, idx, eventNames = typeof eventName === STRING ? [eventName] : eventName, length, handler, handlersIsFunction = typeof handlers === FUNCTION, events;
                for (idx = 0, length = eventNames.length; idx < length; idx++) {
                    eventName = eventNames[idx];
                    handler = handlersIsFunction ? handlers : handlers[eventName];
                    if (handler) {
                        events = that._events[eventName] = that._events[eventName] || [];
                        events.unshift(handler);
                    }
                }
                return that;
            },
            trigger: function (eventName, e) {
                var that = this, events = that._events[eventName], idx, length;
                if (events) {
                    e = e || {};
                    e.sender = that;
                    e._defaultPrevented = false;
                    e.preventDefault = preventDefault;
                    e.isDefaultPrevented = isDefaultPrevented;
                    events = events.slice();
                    for (idx = 0, length = events.length; idx < length; idx++) {
                        events[idx].call(that, e);
                    }
                    return e._defaultPrevented === true;
                }
                return false;
            },
            unbind: function (eventName, handler) {
                var that = this, events = that._events[eventName], idx;
                if (eventName === undefined) {
                    that._events = {};
                } else if (events) {
                    if (handler) {
                        for (idx = events.length - 1; idx >= 0; idx--) {
                            if (events[idx] === handler || events[idx].original === handler) {
                                events.splice(idx, 1);
                            }
                        }
                    } else {
                        that._events[eventName] = [];
                    }
                }
                return that;
            }
        });
        function compilePart(part, stringPart) {
            if (stringPart) {
                return '\'' + part.split('\'').join('\\\'').split('\\"').join('\\\\\\"').replace(/\n/g, '\\n').replace(/\r/g, '\\r').replace(/\t/g, '\\t') + '\'';
            } else {
                var first = part.charAt(0), rest = part.substring(1);
                if (first === '=') {
                    return '+(' + rest + ')+';
                } else if (first === ':') {
                    return '+$kendoHtmlEncode(' + rest + ')+';
                } else {
                    return ';' + part + ';$kendoOutput+=';
                }
            }
        }
        var argumentNameRegExp = /^\w+/, encodeRegExp = /\$\{([^}]*)\}/g, escapedCurlyRegExp = /\\\}/g, curlyRegExp = /__CURLY__/g, escapedSharpRegExp = /\\#/g, sharpRegExp = /__SHARP__/g, zeros = [
                '',
                '0',
                '00',
                '000',
                '0000'
            ];
        Template = {
            paramName: 'data',
            useWithBlock: true,
            render: function (template, data) {
                var idx, length, html = '';
                for (idx = 0, length = data.length; idx < length; idx++) {
                    html += template(data[idx]);
                }
                return html;
            },
            compile: function (template, options) {
                var settings = extend({}, this, options), paramName = settings.paramName, argumentName = paramName.match(argumentNameRegExp)[0], useWithBlock = settings.useWithBlock, functionBody = 'var $kendoOutput, $kendoHtmlEncode = kendo.htmlEncode;', fn, parts, idx;
                if (isFunction(template)) {
                    return template;
                }
                functionBody += useWithBlock ? 'with(' + paramName + '){' : '';
                functionBody += '$kendoOutput=';
                parts = template.replace(escapedCurlyRegExp, '__CURLY__').replace(encodeRegExp, '#=$kendoHtmlEncode($1)#').replace(curlyRegExp, '}').replace(escapedSharpRegExp, '__SHARP__').split('#');
                for (idx = 0; idx < parts.length; idx++) {
                    functionBody += compilePart(parts[idx], idx % 2 === 0);
                }
                functionBody += useWithBlock ? ';}' : ';';
                functionBody += 'return $kendoOutput;';
                functionBody = functionBody.replace(sharpRegExp, '#');
                try {
                    fn = new Function(argumentName, functionBody);
                    fn._slotCount = Math.floor(parts.length / 2);
                    return fn;
                } catch (e) {
                    throw new Error(kendo.format('Invalid template:\'{0}\' Generated code:\'{1}\'', template, functionBody));
                }
            }
        };
        function pad(number, digits, end) {
            number = number + '';
            digits = digits || 2;
            end = digits - number.length;
            if (end) {
                return zeros[digits].substring(0, end) + number;
            }
            return number;
        }
        (function () {
            var escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g, gap, indent, meta = {
                    '\b': '\\b',
                    '\t': '\\t',
                    '\n': '\\n',
                    '\f': '\\f',
                    '\r': '\\r',
                    '"': '\\"',
                    '\\': '\\\\'
                }, rep, toString = {}.toString;
            if (typeof Date.prototype.toJSON !== FUNCTION) {
                Date.prototype.toJSON = function () {
                    var that = this;
                    return isFinite(that.valueOf()) ? pad(that.getUTCFullYear(), 4) + '-' + pad(that.getUTCMonth() + 1) + '-' + pad(that.getUTCDate()) + 'T' + pad(that.getUTCHours()) + ':' + pad(that.getUTCMinutes()) + ':' + pad(that.getUTCSeconds()) + 'Z' : null;
                };
                String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function () {
                    return this.valueOf();
                };
            }
            function quote(string) {
                escapable.lastIndex = 0;
                return escapable.test(string) ? '"' + string.replace(escapable, function (a) {
                    var c = meta[a];
                    return typeof c === STRING ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                }) + '"' : '"' + string + '"';
            }
            function str(key, holder) {
                var i, k, v, length, mind = gap, partial, value = holder[key], type;
                if (value && typeof value === OBJECT && typeof value.toJSON === FUNCTION) {
                    value = value.toJSON(key);
                }
                if (typeof rep === FUNCTION) {
                    value = rep.call(holder, key, value);
                }
                type = typeof value;
                if (type === STRING) {
                    return quote(value);
                } else if (type === NUMBER) {
                    return isFinite(value) ? String(value) : NULL;
                } else if (type === BOOLEAN || type === NULL) {
                    return String(value);
                } else if (type === OBJECT) {
                    if (!value) {
                        return NULL;
                    }
                    gap += indent;
                    partial = [];
                    if (toString.apply(value) === '[object Array]') {
                        length = value.length;
                        for (i = 0; i < length; i++) {
                            partial[i] = str(i, value) || NULL;
                        }
                        v = partial.length === 0 ? '[]' : gap ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']' : '[' + partial.join(',') + ']';
                        gap = mind;
                        return v;
                    }
                    if (rep && typeof rep === OBJECT) {
                        length = rep.length;
                        for (i = 0; i < length; i++) {
                            if (typeof rep[i] === STRING) {
                                k = rep[i];
                                v = str(k, value);
                                if (v) {
                                    partial.push(quote(k) + (gap ? ': ' : ':') + v);
                                }
                            }
                        }
                    } else {
                        for (k in value) {
                            if (Object.hasOwnProperty.call(value, k)) {
                                v = str(k, value);
                                if (v) {
                                    partial.push(quote(k) + (gap ? ': ' : ':') + v);
                                }
                            }
                        }
                    }
                    v = partial.length === 0 ? '{}' : gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' : '{' + partial.join(',') + '}';
                    gap = mind;
                    return v;
                }
            }
            if (typeof JSON.stringify !== FUNCTION) {
                JSON.stringify = function (value, replacer, space) {
                    var i;
                    gap = '';
                    indent = '';
                    if (typeof space === NUMBER) {
                        for (i = 0; i < space; i += 1) {
                            indent += ' ';
                        }
                    } else if (typeof space === STRING) {
                        indent = space;
                    }
                    rep = replacer;
                    if (replacer && typeof replacer !== FUNCTION && (typeof replacer !== OBJECT || typeof replacer.length !== NUMBER)) {
                        throw new Error('JSON.stringify');
                    }
                    return str('', { '': value });
                };
            }
        }());
        (function () {
            var dateFormatRegExp = /dddd|ddd|dd|d|MMMM|MMM|MM|M|yyyy|yy|HH|H|hh|h|mm|m|fff|ff|f|tt|ss|s|zzz|zz|z|"[^"]*"|'[^']*'/g, standardFormatRegExp = /^(n|c|p|e)(\d*)$/i, literalRegExp = /(\\.)|(['][^']*[']?)|(["][^"]*["]?)/g, commaRegExp = /\,/g, EMPTY = '', POINT = '.', COMMA = ',', SHARP = '#', ZERO = '0', PLACEHOLDER = '??', EN = 'en-US', objectToString = {}.toString;
            kendo.cultures['en-US'] = {
                name: EN,
                numberFormat: {
                    pattern: ['-n'],
                    decimals: 2,
                    ',': ',',
                    '.': '.',
                    groupSize: [3],
                    percent: {
                        pattern: [
                            '-n %',
                            'n %'
                        ],
                        decimals: 2,
                        ',': ',',
                        '.': '.',
                        groupSize: [3],
                        symbol: '%'
                    },
                    currency: {
                        name: 'US Dollar',
                        abbr: 'USD',
                        pattern: [
                            '($n)',
                            '$n'
                        ],
                        decimals: 2,
                        ',': ',',
                        '.': '.',
                        groupSize: [3],
                        symbol: '$'
                    }
                },
                calendars: {
                    standard: {
                        days: {
                            names: [
                                'Sunday',
                                'Monday',
                                'Tuesday',
                                'Wednesday',
                                'Thursday',
                                'Friday',
                                'Saturday'
                            ],
                            namesAbbr: [
                                'Sun',
                                'Mon',
                                'Tue',
                                'Wed',
                                'Thu',
                                'Fri',
                                'Sat'
                            ],
                            namesShort: [
                                'Su',
                                'Mo',
                                'Tu',
                                'We',
                                'Th',
                                'Fr',
                                'Sa'
                            ]
                        },
                        months: {
                            names: [
                                'January',
                                'February',
                                'March',
                                'April',
                                'May',
                                'June',
                                'July',
                                'August',
                                'September',
                                'October',
                                'November',
                                'December'
                            ],
                            namesAbbr: [
                                'Jan',
                                'Feb',
                                'Mar',
                                'Apr',
                                'May',
                                'Jun',
                                'Jul',
                                'Aug',
                                'Sep',
                                'Oct',
                                'Nov',
                                'Dec'
                            ]
                        },
                        AM: [
                            'AM',
                            'am',
                            'AM'
                        ],
                        PM: [
                            'PM',
                            'pm',
                            'PM'
                        ],
                        patterns: {
                            d: 'M/d/yyyy',
                            D: 'dddd, MMMM dd, yyyy',
                            F: 'dddd, MMMM dd, yyyy h:mm:ss tt',
                            g: 'M/d/yyyy h:mm tt',
                            G: 'M/d/yyyy h:mm:ss tt',
                            m: 'MMMM dd',
                            M: 'MMMM dd',
                            s: 'yyyy\'-\'MM\'-\'ddTHH\':\'mm\':\'ss',
                            t: 'h:mm tt',
                            T: 'h:mm:ss tt',
                            u: 'yyyy\'-\'MM\'-\'dd HH\':\'mm\':\'ss\'Z\'',
                            y: 'MMMM, yyyy',
                            Y: 'MMMM, yyyy'
                        },
                        '/': '/',
                        ':': ':',
                        firstDay: 0,
                        twoDigitYearMax: 2029
                    }
                }
            };
            function findCulture(culture) {
                if (culture) {
                    if (culture.numberFormat) {
                        return culture;
                    }
                    if (typeof culture === STRING) {
                        var cultures = kendo.cultures;
                        return cultures[culture] || cultures[culture.split('-')[0]] || null;
                    }
                    return null;
                }
                return null;
            }
            function getCulture(culture) {
                if (culture) {
                    culture = findCulture(culture);
                }
                return culture || kendo.cultures.current;
            }
            kendo.culture = function (cultureName) {
                var cultures = kendo.cultures, culture;
                if (cultureName !== undefined) {
                    culture = findCulture(cultureName) || cultures[EN];
                    culture.calendar = culture.calendars.standard;
                    cultures.current = culture;
                } else {
                    return cultures.current;
                }
            };
            kendo.findCulture = findCulture;
            kendo.getCulture = getCulture;
            kendo.culture(EN);
            function formatDate(date, format, culture) {
                culture = getCulture(culture);
                var calendar = culture.calendars.standard, days = calendar.days, months = calendar.months;
                format = calendar.patterns[format] || format;
                return format.replace(dateFormatRegExp, function (match) {
                    var minutes;
                    var result;
                    var sign;
                    if (match === 'd') {
                        result = date.getDate();
                    } else if (match === 'dd') {
                        result = pad(date.getDate());
                    } else if (match === 'ddd') {
                        result = days.namesAbbr[date.getDay()];
                    } else if (match === 'dddd') {
                        result = days.names[date.getDay()];
                    } else if (match === 'M') {
                        result = date.getMonth() + 1;
                    } else if (match === 'MM') {
                        result = pad(date.getMonth() + 1);
                    } else if (match === 'MMM') {
                        result = months.namesAbbr[date.getMonth()];
                    } else if (match === 'MMMM') {
                        result = months.names[date.getMonth()];
                    } else if (match === 'yy') {
                        result = pad(date.getFullYear() % 100);
                    } else if (match === 'yyyy') {
                        result = pad(date.getFullYear(), 4);
                    } else if (match === 'h') {
                        result = date.getHours() % 12 || 12;
                    } else if (match === 'hh') {
                        result = pad(date.getHours() % 12 || 12);
                    } else if (match === 'H') {
                        result = date.getHours();
                    } else if (match === 'HH') {
                        result = pad(date.getHours());
                    } else if (match === 'm') {
                        result = date.getMinutes();
                    } else if (match === 'mm') {
                        result = pad(date.getMinutes());
                    } else if (match === 's') {
                        result = date.getSeconds();
                    } else if (match === 'ss') {
                        result = pad(date.getSeconds());
                    } else if (match === 'f') {
                        result = math.floor(date.getMilliseconds() / 100);
                    } else if (match === 'ff') {
                        result = date.getMilliseconds();
                        if (result > 99) {
                            result = math.floor(result / 10);
                        }
                        result = pad(result);
                    } else if (match === 'fff') {
                        result = pad(date.getMilliseconds(), 3);
                    } else if (match === 'tt') {
                        result = date.getHours() < 12 ? calendar.AM[0] : calendar.PM[0];
                    } else if (match === 'zzz') {
                        minutes = date.getTimezoneOffset();
                        sign = minutes < 0;
                        result = math.abs(minutes / 60).toString().split('.')[0];
                        minutes = math.abs(minutes) - result * 60;
                        result = (sign ? '+' : '-') + pad(result);
                        result += ':' + pad(minutes);
                    } else if (match === 'zz' || match === 'z') {
                        result = date.getTimezoneOffset() / 60;
                        sign = result < 0;
                        result = math.abs(result).toString().split('.')[0];
                        result = (sign ? '+' : '-') + (match === 'zz' ? pad(result) : result);
                    }
                    return result !== undefined ? result : match.slice(1, match.length - 1);
                });
            }
            function formatNumber(number, format, culture) {
                culture = getCulture(culture);
                var numberFormat = culture.numberFormat, decimal = numberFormat[POINT], precision = numberFormat.decimals, pattern = numberFormat.pattern[0], literals = [], symbol, isCurrency, isPercent, customPrecision, formatAndPrecision, negative = number < 0, integer, fraction, integerLength, fractionLength, replacement = EMPTY, value = EMPTY, idx, length, ch, hasGroup, hasNegativeFormat, decimalIndex, sharpIndex, zeroIndex, hasZero, hasSharp, percentIndex, currencyIndex, startZeroIndex, start = -1, end;
                if (number === undefined) {
                    return EMPTY;
                }
                if (!isFinite(number)) {
                    return number;
                }
                if (!format) {
                    return culture.name.length ? number.toLocaleString() : number.toString();
                }
                formatAndPrecision = standardFormatRegExp.exec(format);
                if (formatAndPrecision) {
                    format = formatAndPrecision[1].toLowerCase();
                    isCurrency = format === 'c';
                    isPercent = format === 'p';
                    if (isCurrency || isPercent) {
                        numberFormat = isCurrency ? numberFormat.currency : numberFormat.percent;
                        decimal = numberFormat[POINT];
                        precision = numberFormat.decimals;
                        symbol = numberFormat.symbol;
                        pattern = numberFormat.pattern[negative ? 0 : 1];
                    }
                    customPrecision = formatAndPrecision[2];
                    if (customPrecision) {
                        precision = +customPrecision;
                    }
                    if (format === 'e') {
                        return customPrecision ? number.toExponential(precision) : number.toExponential();
                    }
                    if (isPercent) {
                        number *= 100;
                    }
                    number = round(number, precision);
                    negative = number < 0;
                    number = number.split(POINT);
                    integer = number[0];
                    fraction = number[1];
                    if (negative) {
                        integer = integer.substring(1);
                    }
                    value = groupInteger(integer, 0, integer.length, numberFormat);
                    if (fraction) {
                        value += decimal + fraction;
                    }
                    if (format === 'n' && !negative) {
                        return value;
                    }
                    number = EMPTY;
                    for (idx = 0, length = pattern.length; idx < length; idx++) {
                        ch = pattern.charAt(idx);
                        if (ch === 'n') {
                            number += value;
                        } else if (ch === '$' || ch === '%') {
                            number += symbol;
                        } else {
                            number += ch;
                        }
                    }
                    return number;
                }
                if (negative) {
                    number = -number;
                }
                if (format.indexOf('\'') > -1 || format.indexOf('"') > -1 || format.indexOf('\\') > -1) {
                    format = format.replace(literalRegExp, function (match) {
                        var quoteChar = match.charAt(0).replace('\\', ''), literal = match.slice(1).replace(quoteChar, '');
                        literals.push(literal);
                        return PLACEHOLDER;
                    });
                }
                format = format.split(';');
                if (negative && format[1]) {
                    format = format[1];
                    hasNegativeFormat = true;
                } else if (number === 0) {
                    format = format[2] || format[0];
                    if (format.indexOf(SHARP) == -1 && format.indexOf(ZERO) == -1) {
                        return format;
                    }
                } else {
                    format = format[0];
                }
                percentIndex = format.indexOf('%');
                currencyIndex = format.indexOf('$');
                isPercent = percentIndex != -1;
                isCurrency = currencyIndex != -1;
                if (isPercent) {
                    number *= 100;
                }
                if (isCurrency && format[currencyIndex - 1] === '\\') {
                    format = format.split('\\').join('');
                    isCurrency = false;
                }
                if (isCurrency || isPercent) {
                    numberFormat = isCurrency ? numberFormat.currency : numberFormat.percent;
                    decimal = numberFormat[POINT];
                    precision = numberFormat.decimals;
                    symbol = numberFormat.symbol;
                }
                hasGroup = format.indexOf(COMMA) > -1;
                if (hasGroup) {
                    format = format.replace(commaRegExp, EMPTY);
                }
                decimalIndex = format.indexOf(POINT);
                length = format.length;
                if (decimalIndex != -1) {
                    fraction = number.toString().split('e');
                    if (fraction[1]) {
                        fraction = round(number, Math.abs(fraction[1]));
                    } else {
                        fraction = fraction[0];
                    }
                    fraction = fraction.split(POINT)[1] || EMPTY;
                    zeroIndex = format.lastIndexOf(ZERO) - decimalIndex;
                    sharpIndex = format.lastIndexOf(SHARP) - decimalIndex;
                    hasZero = zeroIndex > -1;
                    hasSharp = sharpIndex > -1;
                    idx = fraction.length;
                    if (!hasZero && !hasSharp) {
                        format = format.substring(0, decimalIndex) + format.substring(decimalIndex + 1);
                        length = format.length;
                        decimalIndex = -1;
                        idx = 0;
                    }
                    if (hasZero && zeroIndex > sharpIndex) {
                        idx = zeroIndex;
                    } else if (sharpIndex > zeroIndex) {
                        if (hasSharp && idx > sharpIndex) {
                            idx = sharpIndex;
                        } else if (hasZero && idx < zeroIndex) {
                            idx = zeroIndex;
                        }
                    }
                    if (idx > -1) {
                        number = round(number, idx);
                    }
                } else {
                    number = round(number);
                }
                sharpIndex = format.indexOf(SHARP);
                startZeroIndex = zeroIndex = format.indexOf(ZERO);
                if (sharpIndex == -1 && zeroIndex != -1) {
                    start = zeroIndex;
                } else if (sharpIndex != -1 && zeroIndex == -1) {
                    start = sharpIndex;
                } else {
                    start = sharpIndex > zeroIndex ? zeroIndex : sharpIndex;
                }
                sharpIndex = format.lastIndexOf(SHARP);
                zeroIndex = format.lastIndexOf(ZERO);
                if (sharpIndex == -1 && zeroIndex != -1) {
                    end = zeroIndex;
                } else if (sharpIndex != -1 && zeroIndex == -1) {
                    end = sharpIndex;
                } else {
                    end = sharpIndex > zeroIndex ? sharpIndex : zeroIndex;
                }
                if (start == length) {
                    end = start;
                }
                if (start != -1) {
                    value = number.toString().split(POINT);
                    integer = value[0];
                    fraction = value[1] || EMPTY;
                    integerLength = integer.length;
                    fractionLength = fraction.length;
                    if (negative && number * -1 >= 0) {
                        negative = false;
                    }
                    number = format.substring(0, start);
                    if (negative && !hasNegativeFormat) {
                        number += '-';
                    }
                    for (idx = start; idx < length; idx++) {
                        ch = format.charAt(idx);
                        if (decimalIndex == -1) {
                            if (end - idx < integerLength) {
                                number += integer;
                                break;
                            }
                        } else {
                            if (zeroIndex != -1 && zeroIndex < idx) {
                                replacement = EMPTY;
                            }
                            if (decimalIndex - idx <= integerLength && decimalIndex - idx > -1) {
                                number += integer;
                                idx = decimalIndex;
                            }
                            if (decimalIndex === idx) {
                                number += (fraction ? decimal : EMPTY) + fraction;
                                idx += end - decimalIndex + 1;
                                continue;
                            }
                        }
                        if (ch === ZERO) {
                            number += ch;
                            replacement = ch;
                        } else if (ch === SHARP) {
                            number += replacement;
                        }
                    }
                    if (hasGroup) {
                        number = groupInteger(number, start + (negative && !hasNegativeFormat ? 1 : 0), Math.max(end, integerLength + start), numberFormat);
                    }
                    if (end >= start) {
                        number += format.substring(end + 1);
                    }
                    if (isCurrency || isPercent) {
                        value = EMPTY;
                        for (idx = 0, length = number.length; idx < length; idx++) {
                            ch = number.charAt(idx);
                            value += ch === '$' || ch === '%' ? symbol : ch;
                        }
                        number = value;
                    }
                    length = literals.length;
                    if (length) {
                        for (idx = 0; idx < length; idx++) {
                            number = number.replace(PLACEHOLDER, literals[idx]);
                        }
                    }
                }
                return number;
            }
            var groupInteger = function (number, start, end, numberFormat) {
                var decimalIndex = number.indexOf(numberFormat[POINT]);
                var groupSizes = numberFormat.groupSize.slice();
                var groupSize = groupSizes.shift();
                var integer, integerLength;
                var idx, parts, value;
                var newGroupSize;
                end = decimalIndex !== -1 ? decimalIndex : end + 1;
                integer = number.substring(start, end);
                integerLength = integer.length;
                if (integerLength >= groupSize) {
                    idx = integerLength;
                    parts = [];
                    while (idx > -1) {
                        value = integer.substring(idx - groupSize, idx);
                        if (value) {
                            parts.push(value);
                        }
                        idx -= groupSize;
                        newGroupSize = groupSizes.shift();
                        groupSize = newGroupSize !== undefined ? newGroupSize : groupSize;
                        if (groupSize === 0) {
                            parts.push(integer.substring(0, idx));
                            break;
                        }
                    }
                    integer = parts.reverse().join(numberFormat[COMMA]);
                    number = number.substring(0, start) + integer + number.substring(end);
                }
                return number;
            };
            var round = function (value, precision) {
                precision = precision || 0;
                value = value.toString().split('e');
                value = Math.round(+(value[0] + 'e' + (value[1] ? +value[1] + precision : precision)));
                value = value.toString().split('e');
                value = +(value[0] + 'e' + (value[1] ? +value[1] - precision : -precision));
                return value.toFixed(Math.min(precision, 20));
            };
            var toString = function (value, fmt, culture) {
                if (fmt) {
                    if (objectToString.call(value) === '[object Date]') {
                        return formatDate(value, fmt, culture);
                    } else if (typeof value === NUMBER) {
                        return formatNumber(value, fmt, culture);
                    }
                }
                return value !== undefined ? value : '';
            };
            kendo.format = function (fmt) {
                var values = arguments;
                return fmt.replace(formatRegExp, function (match, index, placeholderFormat) {
                    var value = values[parseInt(index, 10) + 1];
                    return toString(value, placeholderFormat ? placeholderFormat.substring(1) : '');
                });
            };
            kendo._extractFormat = function (format) {
                if (format.slice(0, 3) === '{0:') {
                    format = format.slice(3, format.length - 1);
                }
                return format;
            };
            kendo._activeElement = function () {
                try {
                    return document.activeElement;
                } catch (e) {
                    return document.documentElement.activeElement;
                }
            };
            kendo._round = round;
            kendo._outerWidth = function (element, includeMargin) {
                return $(element).outerWidth(includeMargin || false) || 0;
            };
            kendo._outerHeight = function (element, includeMargin) {
                return $(element).outerHeight(includeMargin || false) || 0;
            };
            kendo.toString = toString;
        }());
        (function () {
            var nonBreakingSpaceRegExp = /\u00A0/g, exponentRegExp = /[eE][\-+]?[0-9]+/, shortTimeZoneRegExp = /[+|\-]\d{1,2}/, longTimeZoneRegExp = /[+|\-]\d{1,2}:?\d{2}/, dateRegExp = /^\/Date\((.*?)\)\/$/, offsetRegExp = /[+-]\d*/, FORMATS_SEQUENCE = [
                    [],
                    [
                        'G',
                        'g',
                        'F'
                    ],
                    [
                        'D',
                        'd',
                        'y',
                        'm',
                        'T',
                        't'
                    ]
                ], STANDARD_FORMATS = [
                    [
                        'yyyy-MM-ddTHH:mm:ss.fffffffzzz',
                        'yyyy-MM-ddTHH:mm:ss.fffffff',
                        'yyyy-MM-ddTHH:mm:ss.fffzzz',
                        'yyyy-MM-ddTHH:mm:ss.fff',
                        'ddd MMM dd yyyy HH:mm:ss',
                        'yyyy-MM-ddTHH:mm:sszzz',
                        'yyyy-MM-ddTHH:mmzzz',
                        'yyyy-MM-ddTHH:mmzz',
                        'yyyy-MM-ddTHH:mm:ss',
                        'yyyy-MM-dd HH:mm:ss',
                        'yyyy/MM/dd HH:mm:ss'
                    ],
                    [
                        'yyyy-MM-ddTHH:mm',
                        'yyyy-MM-dd HH:mm',
                        'yyyy/MM/dd HH:mm'
                    ],
                    [
                        'yyyy/MM/dd',
                        'yyyy-MM-dd',
                        'HH:mm:ss',
                        'HH:mm'
                    ]
                ], numberRegExp = {
                    2: /^\d{1,2}/,
                    3: /^\d{1,3}/,
                    4: /^\d{4}/
                }, objectToString = {}.toString;
            function outOfRange(value, start, end) {
                return !(value >= start && value <= end);
            }
            function designatorPredicate(designator) {
                return designator.charAt(0);
            }
            function mapDesignators(designators) {
                return $.map(designators, designatorPredicate);
            }
            function adjustDST(date, hours) {
                if (!hours && date.getHours() === 23) {
                    date.setHours(date.getHours() + 2);
                }
            }
            function lowerArray(data) {
                var idx = 0, length = data.length, array = [];
                for (; idx < length; idx++) {
                    array[idx] = (data[idx] + '').toLowerCase();
                }
                return array;
            }
            function lowerLocalInfo(localInfo) {
                var newLocalInfo = {}, property;
                for (property in localInfo) {
                    newLocalInfo[property] = lowerArray(localInfo[property]);
                }
                return newLocalInfo;
            }
            function parseExact(value, format, culture) {
                if (!value) {
                    return null;
                }
                var lookAhead = function (match) {
                        var i = 0;
                        while (format[idx] === match) {
                            i++;
                            idx++;
                        }
                        if (i > 0) {
                            idx -= 1;
                        }
                        return i;
                    }, getNumber = function (size) {
                        var rg = numberRegExp[size] || new RegExp('^\\d{1,' + size + '}'), match = value.substr(valueIdx, size).match(rg);
                        if (match) {
                            match = match[0];
                            valueIdx += match.length;
                            return parseInt(match, 10);
                        }
                        return null;
                    }, getIndexByName = function (names, lower) {
                        var i = 0, length = names.length, name, nameLength, matchLength = 0, matchIdx = 0, subValue;
                        for (; i < length; i++) {
                            name = names[i];
                            nameLength = name.length;
                            subValue = value.substr(valueIdx, nameLength);
                            if (lower) {
                                subValue = subValue.toLowerCase();
                            }
                            if (subValue == name && nameLength > matchLength) {
                                matchLength = nameLength;
                                matchIdx = i;
                            }
                        }
                        if (matchLength) {
                            valueIdx += matchLength;
                            return matchIdx + 1;
                        }
                        return null;
                    }, checkLiteral = function () {
                        var result = false;
                        if (value.charAt(valueIdx) === format[idx]) {
                            valueIdx++;
                            result = true;
                        }
                        return result;
                    }, calendar = culture.calendars.standard, year = null, month = null, day = null, hours = null, minutes = null, seconds = null, milliseconds = null, idx = 0, valueIdx = 0, literal = false, date = new Date(), twoDigitYearMax = calendar.twoDigitYearMax || 2029, defaultYear = date.getFullYear(), ch, count, length, pattern, pmHour, UTC, matches, amDesignators, pmDesignators, hoursOffset, minutesOffset, hasTime, match;
                if (!format) {
                    format = 'd';
                }
                pattern = calendar.patterns[format];
                if (pattern) {
                    format = pattern;
                }
                format = format.split('');
                length = format.length;
                for (; idx < length; idx++) {
                    ch = format[idx];
                    if (literal) {
                        if (ch === '\'') {
                            literal = false;
                        } else {
                            checkLiteral();
                        }
                    } else {
                        if (ch === 'd') {
                            count = lookAhead('d');
                            if (!calendar._lowerDays) {
                                calendar._lowerDays = lowerLocalInfo(calendar.days);
                            }
                            if (day !== null && count > 2) {
                                continue;
                            }
                            day = count < 3 ? getNumber(2) : getIndexByName(calendar._lowerDays[count == 3 ? 'namesAbbr' : 'names'], true);
                            if (day === null || outOfRange(day, 1, 31)) {
                                return null;
                            }
                        } else if (ch === 'M') {
                            count = lookAhead('M');
                            if (!calendar._lowerMonths) {
                                calendar._lowerMonths = lowerLocalInfo(calendar.months);
                            }
                            month = count < 3 ? getNumber(2) : getIndexByName(calendar._lowerMonths[count == 3 ? 'namesAbbr' : 'names'], true);
                            if (month === null || outOfRange(month, 1, 12)) {
                                return null;
                            }
                            month -= 1;
                        } else if (ch === 'y') {
                            count = lookAhead('y');
                            year = getNumber(count);
                            if (year === null) {
                                return null;
                            }
                            if (count == 2) {
                                if (typeof twoDigitYearMax === 'string') {
                                    twoDigitYearMax = defaultYear + parseInt(twoDigitYearMax, 10);
                                }
                                year = defaultYear - defaultYear % 100 + year;
                                if (year > twoDigitYearMax) {
                                    year -= 100;
                                }
                            }
                        } else if (ch === 'h') {
                            lookAhead('h');
                            hours = getNumber(2);
                            if (hours == 12) {
                                hours = 0;
                            }
                            if (hours === null || outOfRange(hours, 0, 11)) {
                                return null;
                            }
                        } else if (ch === 'H') {
                            lookAhead('H');
                            hours = getNumber(2);
                            if (hours === null || outOfRange(hours, 0, 23)) {
                                return null;
                            }
                        } else if (ch === 'm') {
                            lookAhead('m');
                            minutes = getNumber(2);
                            if (minutes === null || outOfRange(minutes, 0, 59)) {
                                return null;
                            }
                        } else if (ch === 's') {
                            lookAhead('s');
                            seconds = getNumber(2);
                            if (seconds === null || outOfRange(seconds, 0, 59)) {
                                return null;
                            }
                        } else if (ch === 'f') {
                            count = lookAhead('f');
                            match = value.substr(valueIdx, count).match(numberRegExp[3]);
                            milliseconds = getNumber(count);
                            if (milliseconds !== null) {
                                milliseconds = parseFloat('0.' + match[0], 10);
                                milliseconds = kendo._round(milliseconds, 3);
                                milliseconds *= 1000;
                            }
                            if (milliseconds === null || outOfRange(milliseconds, 0, 999)) {
                                return null;
                            }
                        } else if (ch === 't') {
                            count = lookAhead('t');
                            amDesignators = calendar.AM;
                            pmDesignators = calendar.PM;
                            if (count === 1) {
                                amDesignators = mapDesignators(amDesignators);
                                pmDesignators = mapDesignators(pmDesignators);
                            }
                            pmHour = getIndexByName(pmDesignators);
                            if (!pmHour && !getIndexByName(amDesignators)) {
                                return null;
                            }
                        } else if (ch === 'z') {
                            UTC = true;
                            count = lookAhead('z');
                            if (value.substr(valueIdx, 1) === 'Z') {
                                checkLiteral();
                                continue;
                            }
                            matches = value.substr(valueIdx, 6).match(count > 2 ? longTimeZoneRegExp : shortTimeZoneRegExp);
                            if (!matches) {
                                return null;
                            }
                            matches = matches[0].split(':');
                            hoursOffset = matches[0];
                            minutesOffset = matches[1];
                            if (!minutesOffset && hoursOffset.length > 3) {
                                valueIdx = hoursOffset.length - 2;
                                minutesOffset = hoursOffset.substring(valueIdx);
                                hoursOffset = hoursOffset.substring(0, valueIdx);
                            }
                            hoursOffset = parseInt(hoursOffset, 10);
                            if (outOfRange(hoursOffset, -12, 13)) {
                                return null;
                            }
                            if (count > 2) {
                                minutesOffset = parseInt(minutesOffset, 10);
                                if (isNaN(minutesOffset) || outOfRange(minutesOffset, 0, 59)) {
                                    return null;
                                }
                            }
                        } else if (ch === '\'') {
                            literal = true;
                            checkLiteral();
                        } else if (!checkLiteral()) {
                            return null;
                        }
                    }
                }
                hasTime = hours !== null || minutes !== null || seconds || null;
                if (year === null && month === null && day === null && hasTime) {
                    year = defaultYear;
                    month = date.getMonth();
                    day = date.getDate();
                } else {
                    if (year === null) {
                        year = defaultYear;
                    }
                    if (day === null) {
                        day = 1;
                    }
                }
                if (pmHour && hours < 12) {
                    hours += 12;
                }
                if (UTC) {
                    if (hoursOffset) {
                        hours += -hoursOffset;
                    }
                    if (minutesOffset) {
                        minutes += -minutesOffset;
                    }
                    value = new Date(Date.UTC(year, month, day, hours, minutes, seconds, milliseconds));
                } else {
                    value = new Date(year, month, day, hours, minutes, seconds, milliseconds);
                    adjustDST(value, hours);
                }
                if (year < 100) {
                    value.setFullYear(year);
                }
                if (value.getDate() !== day && UTC === undefined) {
                    return null;
                }
                return value;
            }
            function parseMicrosoftFormatOffset(offset) {
                var sign = offset.substr(0, 1) === '-' ? -1 : 1;
                offset = offset.substring(1);
                offset = parseInt(offset.substr(0, 2), 10) * 60 + parseInt(offset.substring(2), 10);
                return sign * offset;
            }
            function getDefaultFormats(culture) {
                var length = math.max(FORMATS_SEQUENCE.length, STANDARD_FORMATS.length);
                var patterns = culture.calendar.patterns;
                var cultureFormats, formatIdx, idx;
                var formats = [];
                for (idx = 0; idx < length; idx++) {
                    cultureFormats = FORMATS_SEQUENCE[idx];
                    for (formatIdx = 0; formatIdx < cultureFormats.length; formatIdx++) {
                        formats.push(patterns[cultureFormats[formatIdx]]);
                    }
                    formats = formats.concat(STANDARD_FORMATS[idx]);
                }
                return formats;
            }
            kendo.parseDate = function (value, formats, culture) {
                if (objectToString.call(value) === '[object Date]') {
                    return value;
                }
                var idx = 0;
                var date = null;
                var length;
                var tzoffset;
                if (value && value.indexOf('/D') === 0) {
                    date = dateRegExp.exec(value);
                    if (date) {
                        date = date[1];
                        tzoffset = offsetRegExp.exec(date.substring(1));
                        date = new Date(parseInt(date, 10));
                        if (tzoffset) {
                            tzoffset = parseMicrosoftFormatOffset(tzoffset[0]);
                            date = kendo.timezone.apply(date, 0);
                            date = kendo.timezone.convert(date, 0, -1 * tzoffset);
                        }
                        return date;
                    }
                }
                culture = kendo.getCulture(culture);
                if (!formats) {
                    formats = getDefaultFormats(culture);
                }
                formats = isArray(formats) ? formats : [formats];
                length = formats.length;
                for (; idx < length; idx++) {
                    date = parseExact(value, formats[idx], culture);
                    if (date) {
                        return date;
                    }
                }
                return date;
            };
            kendo.parseInt = function (value, culture) {
                var result = kendo.parseFloat(value, culture);
                if (result) {
                    result = result | 0;
                }
                return result;
            };
            kendo.parseFloat = function (value, culture, format) {
                if (!value && value !== 0) {
                    return null;
                }
                if (typeof value === NUMBER) {
                    return value;
                }
                value = value.toString();
                culture = kendo.getCulture(culture);
                var number = culture.numberFormat, percent = number.percent, currency = number.currency, symbol = currency.symbol, percentSymbol = percent.symbol, negative = value.indexOf('-'), parts, isPercent;
                if (exponentRegExp.test(value)) {
                    value = parseFloat(value.replace(number['.'], '.'));
                    if (isNaN(value)) {
                        value = null;
                    }
                    return value;
                }
                if (negative > 0) {
                    return null;
                } else {
                    negative = negative > -1;
                }
                if (value.indexOf(symbol) > -1 || format && format.toLowerCase().indexOf('c') > -1) {
                    number = currency;
                    parts = number.pattern[0].replace('$', symbol).split('n');
                    if (value.indexOf(parts[0]) > -1 && value.indexOf(parts[1]) > -1) {
                        value = value.replace(parts[0], '').replace(parts[1], '');
                        negative = true;
                    }
                } else if (value.indexOf(percentSymbol) > -1) {
                    isPercent = true;
                    number = percent;
                    symbol = percentSymbol;
                }
                value = value.replace('-', '').replace(symbol, '').replace(nonBreakingSpaceRegExp, ' ').split(number[','].replace(nonBreakingSpaceRegExp, ' ')).join('').replace(number['.'], '.');
                value = parseFloat(value);
                if (isNaN(value)) {
                    value = null;
                } else if (negative) {
                    value *= -1;
                }
                if (value && isPercent) {
                    value /= 100;
                }
                return value;
            };
        }());
        function getShadows(element) {
            var shadow = element.css(kendo.support.transitions.css + 'box-shadow') || element.css('box-shadow'), radius = shadow ? shadow.match(boxShadowRegExp) || [
                    0,
                    0,
                    0,
                    0,
                    0
                ] : [
                    0,
                    0,
                    0,
                    0,
                    0
                ], blur = math.max(+radius[3], +(radius[4] || 0));
            return {
                left: -radius[1] + blur,
                right: +radius[1] + blur,
                bottom: +radius[2] + blur
            };
        }
        function wrap(element, autosize) {
            var browser = support.browser, percentage, outerWidth = kendo._outerWidth, outerHeight = kendo._outerHeight;
            if (!element.parent().hasClass('k-animation-container')) {
                var width = element[0].style.width, height = element[0].style.height, percentWidth = percentRegExp.test(width), percentHeight = percentRegExp.test(height);
                percentage = percentWidth || percentHeight;
                if (!percentWidth && (!autosize || autosize && width)) {
                    width = autosize ? outerWidth(element) + 1 : outerWidth(element);
                }
                if (!percentHeight && (!autosize || autosize && height)) {
                    height = outerHeight(element);
                }
                element.wrap($('<div/>').addClass('k-animation-container').css({
                    width: width,
                    height: height
                }));
                if (percentage) {
                    element.css({
                        width: '100%',
                        height: '100%',
                        boxSizing: 'border-box',
                        mozBoxSizing: 'border-box',
                        webkitBoxSizing: 'border-box'
                    });
                }
            } else {
                var wrapper = element.parent('.k-animation-container'), wrapperStyle = wrapper[0].style;
                if (wrapper.is(':hidden')) {
                    wrapper.show();
                }
                percentage = percentRegExp.test(wrapperStyle.width) || percentRegExp.test(wrapperStyle.height);
                if (!percentage) {
                    wrapper.css({
                        width: autosize ? outerWidth(element) + 1 : outerWidth(element),
                        height: outerHeight(element),
                        boxSizing: 'content-box',
                        mozBoxSizing: 'content-box',
                        webkitBoxSizing: 'content-box'
                    });
                }
            }
            if (browser.msie && math.floor(browser.version) <= 7) {
                element.css({ zoom: 1 });
                element.children('.k-menu').width(element.width());
            }
            return element.parent();
        }
        function deepExtend(destination) {
            var i = 1, length = arguments.length;
            for (i = 1; i < length; i++) {
                deepExtendOne(destination, arguments[i]);
            }
            return destination;
        }
        function deepExtendOne(destination, source) {
            var ObservableArray = kendo.data.ObservableArray, LazyObservableArray = kendo.data.LazyObservableArray, DataSource = kendo.data.DataSource, HierarchicalDataSource = kendo.data.HierarchicalDataSource, property, propValue, propType, propInit, destProp;
            for (property in source) {
                propValue = source[property];
                propType = typeof propValue;
                if (propType === OBJECT && propValue !== null) {
                    propInit = propValue.constructor;
                } else {
                    propInit = null;
                }
                if (propInit && propInit !== Array && propInit !== ObservableArray && propInit !== LazyObservableArray && propInit !== DataSource && propInit !== HierarchicalDataSource && propInit !== RegExp) {
                    if (propValue instanceof Date) {
                        destination[property] = new Date(propValue.getTime());
                    } else if (isFunction(propValue.clone)) {
                        destination[property] = propValue.clone();
                    } else {
                        destProp = destination[property];
                        if (typeof destProp === OBJECT) {
                            destination[property] = destProp || {};
                        } else {
                            destination[property] = {};
                        }
                        deepExtendOne(destination[property], propValue);
                    }
                } else if (propType !== UNDEFINED) {
                    destination[property] = propValue;
                }
            }
            return destination;
        }
        function testRx(agent, rxs, dflt) {
            for (var rx in rxs) {
                if (rxs.hasOwnProperty(rx) && rxs[rx].test(agent)) {
                    return rx;
                }
            }
            return dflt !== undefined ? dflt : agent;
        }
        function toHyphens(str) {
            return str.replace(/([a-z][A-Z])/g, function (g) {
                return g.charAt(0) + '-' + g.charAt(1).toLowerCase();
            });
        }
        function toCamelCase(str) {
            return str.replace(/\-(\w)/g, function (strMatch, g1) {
                return g1.toUpperCase();
            });
        }
        function getComputedStyles(element, properties) {
            var styles = {}, computedStyle;
            if (document.defaultView && document.defaultView.getComputedStyle) {
                computedStyle = document.defaultView.getComputedStyle(element, '');
                if (properties) {
                    $.each(properties, function (idx, value) {
                        styles[value] = computedStyle.getPropertyValue(value);
                    });
                }
            } else {
                computedStyle = element.currentStyle;
                if (properties) {
                    $.each(properties, function (idx, value) {
                        styles[value] = computedStyle[toCamelCase(value)];
                    });
                }
            }
            if (!kendo.size(styles)) {
                styles = computedStyle;
            }
            return styles;
        }
        function isScrollable(element) {
            if (element && element.className && typeof element.className === 'string' && element.className.indexOf('k-auto-scrollable') > -1) {
                return true;
            }
            var overflow = getComputedStyles(element, ['overflow']).overflow;
            return overflow == 'auto' || overflow == 'scroll';
        }
        function scrollLeft(element, value) {
            var webkit = support.browser.webkit;
            var mozila = support.browser.mozilla;
            var el = element instanceof $ ? element[0] : element;
            var isRtl;
            if (!element) {
                return;
            }
            isRtl = support.isRtl(element);
            if (value !== undefined) {
                if (isRtl && webkit) {
                    el.scrollLeft = el.scrollWidth - el.clientWidth - value;
                } else if (isRtl && mozila) {
                    el.scrollLeft = -value;
                } else {
                    el.scrollLeft = value;
                }
            } else {
                if (isRtl && webkit) {
                    return el.scrollWidth - el.clientWidth - el.scrollLeft;
                } else {
                    return Math.abs(el.scrollLeft);
                }
            }
        }
        (function () {
            support._scrollbar = undefined;
            support.scrollbar = function (refresh) {
                if (!isNaN(support._scrollbar) && !refresh) {
                    return support._scrollbar;
                } else {
                    var div = document.createElement('div'), result;
                    div.style.cssText = 'overflow:scroll;overflow-x:hidden;zoom:1;clear:both;display:block';
                    div.innerHTML = '&nbsp;';
                    document.body.appendChild(div);
                    support._scrollbar = result = div.offsetWidth - div.scrollWidth;
                    document.body.removeChild(div);
                    return result;
                }
            };
            support.isRtl = function (element) {
                return $(element).closest('.k-rtl').length > 0;
            };
            var table = document.createElement('table');
            try {
                table.innerHTML = '<tr><td></td></tr>';
                support.tbodyInnerHtml = true;
            } catch (e) {
                support.tbodyInnerHtml = false;
            }
            support.touch = 'ontouchstart' in window;
            var docStyle = document.documentElement.style;
            var transitions = support.transitions = false, transforms = support.transforms = false, elementProto = 'HTMLElement' in window ? HTMLElement.prototype : [];
            support.hasHW3D = 'WebKitCSSMatrix' in window && 'm11' in new window.WebKitCSSMatrix() || 'MozPerspective' in docStyle || 'msPerspective' in docStyle;
            support.cssFlexbox = 'flexWrap' in docStyle || 'WebkitFlexWrap' in docStyle || 'msFlexWrap' in docStyle;
            each([
                'Moz',
                'webkit',
                'O',
                'ms'
            ], function () {
                var prefix = this.toString(), hasTransitions = typeof table.style[prefix + 'Transition'] === STRING;
                if (hasTransitions || typeof table.style[prefix + 'Transform'] === STRING) {
                    var lowPrefix = prefix.toLowerCase();
                    transforms = {
                        css: lowPrefix != 'ms' ? '-' + lowPrefix + '-' : '',
                        prefix: prefix,
                        event: lowPrefix === 'o' || lowPrefix === 'webkit' ? lowPrefix : ''
                    };
                    if (hasTransitions) {
                        transitions = transforms;
                        transitions.event = transitions.event ? transitions.event + 'TransitionEnd' : 'transitionend';
                    }
                    return false;
                }
            });
            table = null;
            support.transforms = transforms;
            support.transitions = transitions;
            support.devicePixelRatio = window.devicePixelRatio === undefined ? 1 : window.devicePixelRatio;
            try {
                support.screenWidth = window.outerWidth || window.screen ? window.screen.availWidth : window.innerWidth;
                support.screenHeight = window.outerHeight || window.screen ? window.screen.availHeight : window.innerHeight;
            } catch (e) {
                support.screenWidth = window.screen.availWidth;
                support.screenHeight = window.screen.availHeight;
            }
            support.detectOS = function (ua) {
                var os = false, minorVersion, match = [], notAndroidPhone = !/mobile safari/i.test(ua), agentRxs = {
                        wp: /(Windows Phone(?: OS)?)\s(\d+)\.(\d+(\.\d+)?)/,
                        fire: /(Silk)\/(\d+)\.(\d+(\.\d+)?)/,
                        android: /(Android|Android.*(?:Opera|Firefox).*?\/)\s*(\d+)\.(\d+(\.\d+)?)/,
                        iphone: /(iPhone|iPod).*OS\s+(\d+)[\._]([\d\._]+)/,
                        ipad: /(iPad).*OS\s+(\d+)[\._]([\d_]+)/,
                        meego: /(MeeGo).+NokiaBrowser\/(\d+)\.([\d\._]+)/,
                        webos: /(webOS)\/(\d+)\.(\d+(\.\d+)?)/,
                        blackberry: /(BlackBerry|BB10).*?Version\/(\d+)\.(\d+(\.\d+)?)/,
                        playbook: /(PlayBook).*?Tablet\s*OS\s*(\d+)\.(\d+(\.\d+)?)/,
                        windows: /(MSIE)\s+(\d+)\.(\d+(\.\d+)?)/,
                        tizen: /(tizen).*?Version\/(\d+)\.(\d+(\.\d+)?)/i,
                        sailfish: /(sailfish).*rv:(\d+)\.(\d+(\.\d+)?).*firefox/i,
                        ffos: /(Mobile).*rv:(\d+)\.(\d+(\.\d+)?).*Firefox/
                    }, osRxs = {
                        ios: /^i(phone|pad|pod)$/i,
                        android: /^android|fire$/i,
                        blackberry: /^blackberry|playbook/i,
                        windows: /windows/,
                        wp: /wp/,
                        flat: /sailfish|ffos|tizen/i,
                        meego: /meego/
                    }, formFactorRxs = { tablet: /playbook|ipad|fire/i }, browserRxs = {
                        omini: /Opera\sMini/i,
                        omobile: /Opera\sMobi/i,
                        firefox: /Firefox|Fennec/i,
                        mobilesafari: /version\/.*safari/i,
                        ie: /MSIE|Windows\sPhone/i,
                        chrome: /chrome|crios/i,
                        webkit: /webkit/i
                    };
                for (var agent in agentRxs) {
                    if (agentRxs.hasOwnProperty(agent)) {
                        match = ua.match(agentRxs[agent]);
                        if (match) {
                            if (agent == 'windows' && 'plugins' in navigator) {
                                return false;
                            }
                            os = {};
                            os.device = agent;
                            os.tablet = testRx(agent, formFactorRxs, false);
                            os.browser = testRx(ua, browserRxs, 'default');
                            os.name = testRx(agent, osRxs);
                            os[os.name] = true;
                            os.majorVersion = match[2];
                            os.minorVersion = match[3].replace('_', '.');
                            minorVersion = os.minorVersion.replace('.', '').substr(0, 2);
                            os.flatVersion = os.majorVersion + minorVersion + new Array(3 - (minorVersion.length < 3 ? minorVersion.length : 2)).join('0');
                            os.cordova = typeof window.PhoneGap !== UNDEFINED || typeof window.cordova !== UNDEFINED;
                            os.appMode = window.navigator.standalone || /file|local|wmapp/.test(window.location.protocol) || os.cordova;
                            if (os.android && (support.devicePixelRatio < 1.5 && os.flatVersion < 400 || notAndroidPhone) && (support.screenWidth > 800 || support.screenHeight > 800)) {
                                os.tablet = agent;
                            }
                            break;
                        }
                    }
                }
                return os;
            };
            var mobileOS = support.mobileOS = support.detectOS(navigator.userAgent);
            support.wpDevicePixelRatio = mobileOS.wp ? screen.width / 320 : 0;
            support.hasNativeScrolling = false;
            if (mobileOS.ios || mobileOS.android && mobileOS.majorVersion > 2 || mobileOS.wp) {
                support.hasNativeScrolling = mobileOS;
            }
            support.delayedClick = function () {
                if (support.touch) {
                    if (mobileOS.ios) {
                        return true;
                    }
                    if (mobileOS.android) {
                        if (!support.browser.chrome) {
                            return true;
                        }
                        if (support.browser.version < 32) {
                            return false;
                        }
                        return !($('meta[name=viewport]').attr('content') || '').match(/user-scalable=no/i);
                    }
                }
                return false;
            };
            support.mouseAndTouchPresent = support.touch && !(support.mobileOS.ios || support.mobileOS.android);
            support.detectBrowser = function (ua) {
                var browser = false, match = [], browserRxs = {
                        edge: /(edge)[ \/]([\w.]+)/i,
                        webkit: /(chrome)[ \/]([\w.]+)/i,
                        safari: /(webkit)[ \/]([\w.]+)/i,
                        opera: /(opera)(?:.*version|)[ \/]([\w.]+)/i,
                        msie: /(msie\s|trident.*? rv:)([\w.]+)/i,
                        mozilla: /(mozilla)(?:.*? rv:([\w.]+)|)/i
                    };
                for (var agent in browserRxs) {
                    if (browserRxs.hasOwnProperty(agent)) {
                        match = ua.match(browserRxs[agent]);
                        if (match) {
                            browser = {};
                            browser[agent] = true;
                            browser[match[1].toLowerCase().split(' ')[0].split('/')[0]] = true;
                            browser.version = parseInt(document.documentMode || match[2], 10);
                            break;
                        }
                    }
                }
                return browser;
            };
            support.browser = support.detectBrowser(navigator.userAgent);
            support.detectClipboardAccess = function () {
                var commands = {
                    copy: document.queryCommandSupported ? document.queryCommandSupported('copy') : false,
                    cut: document.queryCommandSupported ? document.queryCommandSupported('cut') : false,
                    paste: document.queryCommandSupported ? document.queryCommandSupported('paste') : false
                };
                if (support.browser.chrome) {
                    commands.paste = false;
                    if (support.browser.version >= 43) {
                        commands.copy = true;
                        commands.cut = true;
                    }
                }
                return commands;
            };
            support.clipboard = support.detectClipboardAccess();
            support.zoomLevel = function () {
                try {
                    var browser = support.browser;
                    var ie11WidthCorrection = 0;
                    var docEl = document.documentElement;
                    if (browser.msie && browser.version == 11 && docEl.scrollHeight > docEl.clientHeight && !support.touch) {
                        ie11WidthCorrection = support.scrollbar();
                    }
                    return support.touch ? docEl.clientWidth / window.innerWidth : browser.msie && browser.version >= 10 ? ((top || window).document.documentElement.offsetWidth + ie11WidthCorrection) / (top || window).innerWidth : 1;
                } catch (e) {
                    return 1;
                }
            };
            support.cssBorderSpacing = typeof docStyle.borderSpacing != 'undefined' && !(support.browser.msie && support.browser.version < 8);
            (function (browser) {
                var cssClass = '', docElement = $(document.documentElement), majorVersion = parseInt(browser.version, 10);
                if (browser.msie) {
                    cssClass = 'ie';
                } else if (browser.mozilla) {
                    cssClass = 'ff';
                } else if (browser.safari) {
                    cssClass = 'safari';
                } else if (browser.webkit) {
                    cssClass = 'webkit';
                } else if (browser.opera) {
                    cssClass = 'opera';
                } else if (browser.edge) {
                    cssClass = 'edge';
                }
                if (cssClass) {
                    cssClass = 'k-' + cssClass + ' k-' + cssClass + majorVersion;
                }
                if (support.mobileOS) {
                    cssClass += ' k-mobile';
                }
                if (!support.cssFlexbox) {
                    cssClass += ' k-no-flexbox';
                }
                docElement.addClass(cssClass);
            }(support.browser));
            support.eventCapture = document.documentElement.addEventListener;
            var input = document.createElement('input');
            support.placeholder = 'placeholder' in input;
            support.propertyChangeEvent = 'onpropertychange' in input;
            support.input = function () {
                var types = [
                    'number',
                    'date',
                    'time',
                    'month',
                    'week',
                    'datetime',
                    'datetime-local'
                ];
                var length = types.length;
                var value = 'test';
                var result = {};
                var idx = 0;
                var type;
                for (; idx < length; idx++) {
                    type = types[idx];
                    input.setAttribute('type', type);
                    input.value = value;
                    result[type.replace('-', '')] = input.type !== 'text' && input.value !== value;
                }
                return result;
            }();
            input.style.cssText = 'float:left;';
            support.cssFloat = !!input.style.cssFloat;
            input = null;
            support.stableSort = function () {
                var threshold = 513;
                var sorted = [{
                        index: 0,
                        field: 'b'
                    }];
                for (var i = 1; i < threshold; i++) {
                    sorted.push({
                        index: i,
                        field: 'a'
                    });
                }
                sorted.sort(function (a, b) {
                    return a.field > b.field ? 1 : a.field < b.field ? -1 : 0;
                });
                return sorted[0].index === 1;
            }();
            support.matchesSelector = elementProto.webkitMatchesSelector || elementProto.mozMatchesSelector || elementProto.msMatchesSelector || elementProto.oMatchesSelector || elementProto.matchesSelector || elementProto.matches || function (selector) {
                var nodeList = document.querySelectorAll ? (this.parentNode || document).querySelectorAll(selector) || [] : $(selector), i = nodeList.length;
                while (i--) {
                    if (nodeList[i] == this) {
                        return true;
                    }
                }
                return false;
            };
            support.pushState = window.history && window.history.pushState;
            var documentMode = document.documentMode;
            support.hashChange = 'onhashchange' in window && !(support.browser.msie && (!documentMode || documentMode <= 8));
            support.customElements = 'registerElement' in window.document;
            var chrome = support.browser.chrome;
            support.msPointers = !chrome && window.MSPointerEvent;
            support.pointers = !chrome && window.PointerEvent;
            support.kineticScrollNeeded = mobileOS && (support.touch || support.msPointers || support.pointers);
        }());
        function size(obj) {
            var result = 0, key;
            for (key in obj) {
                if (obj.hasOwnProperty(key) && key != 'toJSON') {
                    result++;
                }
            }
            return result;
        }
        function getOffset(element, type, positioned) {
            if (!type) {
                type = 'offset';
            }
            var offset = element[type]();
            var result = {
                top: offset.top,
                right: offset.right,
                bottom: offset.bottom,
                left: offset.left
            };
            if (support.browser.msie && (support.pointers || support.msPointers) && !positioned) {
                var sign = support.isRtl(element) ? 1 : -1;
                result.top -= window.pageYOffset - document.documentElement.scrollTop;
                result.left -= window.pageXOffset + sign * document.documentElement.scrollLeft;
            }
            return result;
        }
        var directions = {
            left: { reverse: 'right' },
            right: { reverse: 'left' },
            down: { reverse: 'up' },
            up: { reverse: 'down' },
            top: { reverse: 'bottom' },
            bottom: { reverse: 'top' },
            'in': { reverse: 'out' },
            out: { reverse: 'in' }
        };
        function parseEffects(input) {
            var effects = {};
            each(typeof input === 'string' ? input.split(' ') : input, function (idx) {
                effects[idx] = this;
            });
            return effects;
        }
        function fx(element) {
            return new kendo.effects.Element(element);
        }
        var effects = {};
        $.extend(effects, {
            enabled: true,
            Element: function (element) {
                this.element = $(element);
            },
            promise: function (element, options) {
                if (!element.is(':visible')) {
                    element.css({ display: element.data('olddisplay') || 'block' }).css('display');
                }
                if (options.hide) {
                    element.data('olddisplay', element.css('display')).hide();
                }
                if (options.init) {
                    options.init();
                }
                if (options.completeCallback) {
                    options.completeCallback(element);
                }
                element.dequeue();
            },
            disable: function () {
                this.enabled = false;
                this.promise = this.promiseShim;
            },
            enable: function () {
                this.enabled = true;
                this.promise = this.animatedPromise;
            }
        });
        effects.promiseShim = effects.promise;
        function prepareAnimationOptions(options, duration, reverse, complete) {
            if (typeof options === STRING) {
                if (isFunction(duration)) {
                    complete = duration;
                    duration = 400;
                    reverse = false;
                }
                if (isFunction(reverse)) {
                    complete = reverse;
                    reverse = false;
                }
                if (typeof duration === BOOLEAN) {
                    reverse = duration;
                    duration = 400;
                }
                options = {
                    effects: options,
                    duration: duration,
                    reverse: reverse,
                    complete: complete
                };
            }
            return extend({
                effects: {},
                duration: 400,
                reverse: false,
                init: noop,
                teardown: noop,
                hide: false
            }, options, {
                completeCallback: options.complete,
                complete: noop
            });
        }
        function animate(element, options, duration, reverse, complete) {
            var idx = 0, length = element.length, instance;
            for (; idx < length; idx++) {
                instance = $(element[idx]);
                instance.queue(function () {
                    effects.promise(instance, prepareAnimationOptions(options, duration, reverse, complete));
                });
            }
            return element;
        }
        function toggleClass(element, classes, options, add) {
            if (classes) {
                classes = classes.split(' ');
                each(classes, function (idx, value) {
                    element.toggleClass(value, add);
                });
            }
            return element;
        }
        if (!('kendoAnimate' in $.fn)) {
            extend($.fn, {
                kendoStop: function (clearQueue, gotoEnd) {
                    return this.stop(clearQueue, gotoEnd);
                },
                kendoAnimate: function (options, duration, reverse, complete) {
                    return animate(this, options, duration, reverse, complete);
                },
                kendoAddClass: function (classes, options) {
                    return kendo.toggleClass(this, classes, options, true);
                },
                kendoRemoveClass: function (classes, options) {
                    return kendo.toggleClass(this, classes, options, false);
                },
                kendoToggleClass: function (classes, options, toggle) {
                    return kendo.toggleClass(this, classes, options, toggle);
                }
            });
        }
        var ampRegExp = /&/g, ltRegExp = /</g, quoteRegExp = /"/g, aposRegExp = /'/g, gtRegExp = />/g;
        function htmlEncode(value) {
            return ('' + value).replace(ampRegExp, '&amp;').replace(ltRegExp, '&lt;').replace(gtRegExp, '&gt;').replace(quoteRegExp, '&quot;').replace(aposRegExp, '&#39;');
        }
        var eventTarget = function (e) {
            return e.target;
        };
        if (support.touch) {
            eventTarget = function (e) {
                var touches = 'originalEvent' in e ? e.originalEvent.changedTouches : 'changedTouches' in e ? e.changedTouches : null;
                return touches ? document.elementFromPoint(touches[0].clientX, touches[0].clientY) : e.target;
            };
            each([
                'swipe',
                'swipeLeft',
                'swipeRight',
                'swipeUp',
                'swipeDown',
                'doubleTap',
                'tap'
            ], function (m, value) {
                $.fn[value] = function (callback) {
                    return this.bind(value, callback);
                };
            });
        }
        if (support.touch) {
            if (!support.mobileOS) {
                support.mousedown = 'mousedown touchstart';
                support.mouseup = 'mouseup touchend';
                support.mousemove = 'mousemove touchmove';
                support.mousecancel = 'mouseleave touchcancel';
                support.click = 'click';
                support.resize = 'resize';
            } else {
                support.mousedown = 'touchstart';
                support.mouseup = 'touchend';
                support.mousemove = 'touchmove';
                support.mousecancel = 'touchcancel';
                support.click = 'touchend';
                support.resize = 'orientationchange';
            }
        } else if (support.pointers) {
            support.mousemove = 'pointermove';
            support.mousedown = 'pointerdown';
            support.mouseup = 'pointerup';
            support.mousecancel = 'pointercancel';
            support.click = 'pointerup';
            support.resize = 'orientationchange resize';
        } else if (support.msPointers) {
            support.mousemove = 'MSPointerMove';
            support.mousedown = 'MSPointerDown';
            support.mouseup = 'MSPointerUp';
            support.mousecancel = 'MSPointerCancel';
            support.click = 'MSPointerUp';
            support.resize = 'orientationchange resize';
        } else {
            support.mousemove = 'mousemove';
            support.mousedown = 'mousedown';
            support.mouseup = 'mouseup';
            support.mousecancel = 'mouseleave';
            support.click = 'click';
            support.resize = 'resize';
        }
        var wrapExpression = function (members, paramName) {
                var result = paramName || 'd', index, idx, length, member, count = 1;
                for (idx = 0, length = members.length; idx < length; idx++) {
                    member = members[idx];
                    if (member !== '') {
                        index = member.indexOf('[');
                        if (index !== 0) {
                            if (index == -1) {
                                member = '.' + member;
                            } else {
                                count++;
                                member = '.' + member.substring(0, index) + ' || {})' + member.substring(index);
                            }
                        }
                        count++;
                        result += member + (idx < length - 1 ? ' || {})' : ')');
                    }
                }
                return new Array(count).join('(') + result;
            }, localUrlRe = /^([a-z]+:)?\/\//i;
        extend(kendo, {
            widgets: [],
            _widgetRegisteredCallbacks: [],
            ui: kendo.ui || {},
            fx: kendo.fx || fx,
            effects: kendo.effects || effects,
            mobile: kendo.mobile || {},
            data: kendo.data || {},
            dataviz: kendo.dataviz || {},
            drawing: kendo.drawing || {},
            spreadsheet: { messages: {} },
            keys: {
                INSERT: 45,
                DELETE: 46,
                BACKSPACE: 8,
                TAB: 9,
                ENTER: 13,
                ESC: 27,
                LEFT: 37,
                UP: 38,
                RIGHT: 39,
                DOWN: 40,
                END: 35,
                HOME: 36,
                SPACEBAR: 32,
                PAGEUP: 33,
                PAGEDOWN: 34,
                F2: 113,
                F10: 121,
                F12: 123,
                NUMPAD_PLUS: 107,
                NUMPAD_MINUS: 109,
                NUMPAD_DOT: 110
            },
            support: kendo.support || support,
            animate: kendo.animate || animate,
            ns: '',
            attr: function (value) {
                return 'data-' + kendo.ns + value;
            },
            getShadows: getShadows,
            wrap: wrap,
            deepExtend: deepExtend,
            getComputedStyles: getComputedStyles,
            webComponents: kendo.webComponents || [],
            isScrollable: isScrollable,
            scrollLeft: scrollLeft,
            size: size,
            toCamelCase: toCamelCase,
            toHyphens: toHyphens,
            getOffset: kendo.getOffset || getOffset,
            parseEffects: kendo.parseEffects || parseEffects,
            toggleClass: kendo.toggleClass || toggleClass,
            directions: kendo.directions || directions,
            Observable: Observable,
            Class: Class,
            Template: Template,
            template: proxy(Template.compile, Template),
            render: proxy(Template.render, Template),
            stringify: proxy(JSON.stringify, JSON),
            eventTarget: eventTarget,
            htmlEncode: htmlEncode,
            isLocalUrl: function (url) {
                return url && !localUrlRe.test(url);
            },
            expr: function (expression, safe, paramName) {
                expression = expression || '';
                if (typeof safe == STRING) {
                    paramName = safe;
                    safe = false;
                }
                paramName = paramName || 'd';
                if (expression && expression.charAt(0) !== '[') {
                    expression = '.' + expression;
                }
                if (safe) {
                    expression = expression.replace(/"([^.]*)\.([^"]*)"/g, '"$1_$DOT$_$2"');
                    expression = expression.replace(/'([^.]*)\.([^']*)'/g, '\'$1_$DOT$_$2\'');
                    expression = wrapExpression(expression.split('.'), paramName);
                    expression = expression.replace(/_\$DOT\$_/g, '.');
                } else {
                    expression = paramName + expression;
                }
                return expression;
            },
            getter: function (expression, safe) {
                var key = expression + safe;
                return getterCache[key] = getterCache[key] || new Function('d', 'return ' + kendo.expr(expression, safe));
            },
            setter: function (expression) {
                return setterCache[expression] = setterCache[expression] || new Function('d,value', kendo.expr(expression) + '=value');
            },
            accessor: function (expression) {
                return {
                    get: kendo.getter(expression),
                    set: kendo.setter(expression)
                };
            },
            guid: function () {
                var id = '', i, random;
                for (i = 0; i < 32; i++) {
                    random = math.random() * 16 | 0;
                    if (i == 8 || i == 12 || i == 16 || i == 20) {
                        id += '-';
                    }
                    id += (i == 12 ? 4 : i == 16 ? random & 3 | 8 : random).toString(16);
                }
                return id;
            },
            roleSelector: function (role) {
                return role.replace(/(\S+)/g, '[' + kendo.attr('role') + '=$1],').slice(0, -1);
            },
            directiveSelector: function (directives) {
                var selectors = directives.split(' ');
                if (selectors) {
                    for (var i = 0; i < selectors.length; i++) {
                        if (selectors[i] != 'view') {
                            selectors[i] = selectors[i].replace(/(\w*)(view|bar|strip|over)$/, '$1-$2');
                        }
                    }
                }
                return selectors.join(' ').replace(/(\S+)/g, 'kendo-mobile-$1,').slice(0, -1);
            },
            triggeredByInput: function (e) {
                return /^(label|input|textarea|select)$/i.test(e.target.tagName);
            },
            onWidgetRegistered: function (callback) {
                for (var i = 0, len = kendo.widgets.length; i < len; i++) {
                    callback(kendo.widgets[i]);
                }
                kendo._widgetRegisteredCallbacks.push(callback);
            },
            logToConsole: function (message, type) {
                var console = window.console;
                if (!kendo.suppressLog && typeof console != 'undefined' && console.log) {
                    console[type || 'log'](message);
                }
            }
        });
        var Widget = Observable.extend({
            init: function (element, options) {
                var that = this;
                that.element = kendo.jQuery(element).handler(that);
                that.angular('init', options);
                Observable.fn.init.call(that);
                var dataSource = options ? options.dataSource : null;
                if (dataSource) {
                    options = extend({}, options, { dataSource: {} });
                }
                options = that.options = extend(true, {}, that.options, options);
                if (dataSource) {
                    options.dataSource = dataSource;
                }
                if (!that.element.attr(kendo.attr('role'))) {
                    that.element.attr(kendo.attr('role'), (options.name || '').toLowerCase());
                }
                that.element.data('kendo' + options.prefix + options.name, that);
                that.bind(that.events, options);
            },
            events: [],
            options: { prefix: '' },
            _hasBindingTarget: function () {
                return !!this.element[0].kendoBindingTarget;
            },
            _tabindex: function (target) {
                target = target || this.wrapper;
                var element = this.element, TABINDEX = 'tabindex', tabindex = target.attr(TABINDEX) || element.attr(TABINDEX);
                element.removeAttr(TABINDEX);
                target.attr(TABINDEX, !isNaN(tabindex) ? tabindex : 0);
            },
            setOptions: function (options) {
                this._setEvents(options);
                $.extend(this.options, options);
            },
            _setEvents: function (options) {
                var that = this, idx = 0, length = that.events.length, e;
                for (; idx < length; idx++) {
                    e = that.events[idx];
                    if (that.options[e] && options[e]) {
                        that.unbind(e, that.options[e]);
                    }
                }
                that.bind(that.events, options);
            },
            resize: function (force) {
                var size = this.getSize(), currentSize = this._size;
                if (force || (size.width > 0 || size.height > 0) && (!currentSize || size.width !== currentSize.width || size.height !== currentSize.height)) {
                    this._size = size;
                    this._resize(size, force);
                    this.trigger('resize', size);
                }
            },
            getSize: function () {
                return kendo.dimensions(this.element);
            },
            size: function (size) {
                if (!size) {
                    return this.getSize();
                } else {
                    this.setSize(size);
                }
            },
            setSize: $.noop,
            _resize: $.noop,
            destroy: function () {
                var that = this;
                that.element.removeData('kendo' + that.options.prefix + that.options.name);
                that.element.removeData('handler');
                that.unbind();
            },
            _destroy: function () {
                this.destroy();
            },
            angular: function () {
            },
            _muteAngularRebind: function (callback) {
                this._muteRebind = true;
                callback.call(this);
                this._muteRebind = false;
            }
        });
        var DataBoundWidget = Widget.extend({
            dataItems: function () {
                return this.dataSource.flatView();
            },
            _angularItems: function (cmd) {
                var that = this;
                that.angular(cmd, function () {
                    return {
                        elements: that.items(),
                        data: $.map(that.dataItems(), function (dataItem) {
                            return { dataItem: dataItem };
                        })
                    };
                });
            }
        });
        kendo.dimensions = function (element, dimensions) {
            var domElement = element[0];
            if (dimensions) {
                element.css(dimensions);
            }
            return {
                width: domElement.offsetWidth,
                height: domElement.offsetHeight
            };
        };
        kendo.notify = noop;
        var templateRegExp = /template$/i, jsonRegExp = /^\s*(?:\{(?:.|\r\n|\n)*\}|\[(?:.|\r\n|\n)*\])\s*$/, jsonFormatRegExp = /^\{(\d+)(:[^\}]+)?\}|^\[[A-Za-z_]+\]$/, dashRegExp = /([A-Z])/g;
        function parseOption(element, option) {
            var value;
            if (option.indexOf('data') === 0) {
                option = option.substring(4);
                option = option.charAt(0).toLowerCase() + option.substring(1);
            }
            option = option.replace(dashRegExp, '-$1');
            value = element.getAttribute('data-' + kendo.ns + option);
            if (value === null) {
                value = undefined;
            } else if (value === 'null') {
                value = null;
            } else if (value === 'true') {
                value = true;
            } else if (value === 'false') {
                value = false;
            } else if (numberRegExp.test(value)) {
                value = parseFloat(value);
            } else if (jsonRegExp.test(value) && !jsonFormatRegExp.test(value)) {
                value = new Function('return (' + value + ')')();
            }
            return value;
        }
        function parseOptions(element, options) {
            var result = {}, option, value;
            for (option in options) {
                value = parseOption(element, option);
                if (value !== undefined) {
                    if (templateRegExp.test(option)) {
                        if (typeof value === 'string') {
                            value = kendo.template($('#' + value).html());
                        } else {
                            value = element.getAttribute(option);
                        }
                    }
                    result[option] = value;
                }
            }
            return result;
        }
        kendo.initWidget = function (element, options, roles) {
            var result, option, widget, idx, length, role, value, dataSource, fullPath, widgetKeyRegExp;
            if (!roles) {
                roles = kendo.ui.roles;
            } else if (roles.roles) {
                roles = roles.roles;
            }
            element = element.nodeType ? element : element[0];
            role = element.getAttribute('data-' + kendo.ns + 'role');
            if (!role) {
                return;
            }
            fullPath = role.indexOf('.') === -1;
            if (fullPath) {
                widget = roles[role];
            } else {
                widget = kendo.getter(role)(window);
            }
            var data = $(element).data(), widgetKey = widget ? 'kendo' + widget.fn.options.prefix + widget.fn.options.name : '';
            if (fullPath) {
                widgetKeyRegExp = new RegExp('^kendo.*' + role + '$', 'i');
            } else {
                widgetKeyRegExp = new RegExp('^' + widgetKey + '$', 'i');
            }
            for (var key in data) {
                if (key.match(widgetKeyRegExp)) {
                    if (key === widgetKey) {
                        result = data[key];
                    } else {
                        return data[key];
                    }
                }
            }
            if (!widget) {
                return;
            }
            dataSource = parseOption(element, 'dataSource');
            options = $.extend({}, parseOptions(element, widget.fn.options), options);
            if (dataSource) {
                if (typeof dataSource === STRING) {
                    options.dataSource = kendo.getter(dataSource)(window);
                } else {
                    options.dataSource = dataSource;
                }
            }
            for (idx = 0, length = widget.fn.events.length; idx < length; idx++) {
                option = widget.fn.events[idx];
                value = parseOption(element, option);
                if (value !== undefined) {
                    options[option] = kendo.getter(value)(window);
                }
            }
            if (!result) {
                result = new widget(element, options);
            } else if (!$.isEmptyObject(options)) {
                result.setOptions(options);
            }
            return result;
        };
        kendo.rolesFromNamespaces = function (namespaces) {
            var roles = [], idx, length;
            if (!namespaces[0]) {
                namespaces = [
                    kendo.ui,
                    kendo.dataviz.ui
                ];
            }
            for (idx = 0, length = namespaces.length; idx < length; idx++) {
                roles[idx] = namespaces[idx].roles;
            }
            return extend.apply(null, [{}].concat(roles.reverse()));
        };
        kendo.init = function (element) {
            var roles = kendo.rolesFromNamespaces(slice.call(arguments, 1));
            $(element).find('[data-' + kendo.ns + 'role]').addBack().each(function () {
                kendo.initWidget(this, {}, roles);
            });
        };
        kendo.destroy = function (element) {
            $(element).find('[data-' + kendo.ns + 'role]').addBack().each(function () {
                var data = $(this).data();
                for (var key in data) {
                    if (key.indexOf('kendo') === 0 && typeof data[key].destroy === FUNCTION) {
                        data[key].destroy();
                    }
                }
            });
        };
        function containmentComparer(a, b) {
            return $.contains(a, b) ? -1 : 1;
        }
        function resizableWidget() {
            var widget = $(this);
            return $.inArray(widget.attr('data-' + kendo.ns + 'role'), [
                'slider',
                'rangeslider'
            ]) > -1 || widget.is(':visible');
        }
        kendo.resize = function (element, force) {
            var widgets = $(element).find('[data-' + kendo.ns + 'role]').addBack().filter(resizableWidget);
            if (!widgets.length) {
                return;
            }
            var widgetsArray = $.makeArray(widgets);
            widgetsArray.sort(containmentComparer);
            $.each(widgetsArray, function () {
                var widget = kendo.widgetInstance($(this));
                if (widget) {
                    widget.resize(force);
                }
            });
        };
        kendo.parseOptions = parseOptions;
        extend(kendo.ui, {
            Widget: Widget,
            DataBoundWidget: DataBoundWidget,
            roles: {},
            progress: function (container, toggle) {
                var mask = container.find('.k-loading-mask'), support = kendo.support, browser = support.browser, isRtl, leftRight, webkitCorrection, containerScrollLeft;
                if (toggle) {
                    if (!mask.length) {
                        isRtl = support.isRtl(container);
                        leftRight = isRtl ? 'right' : 'left';
                        containerScrollLeft = container.scrollLeft();
                        webkitCorrection = browser.webkit ? !isRtl ? 0 : container[0].scrollWidth - container.width() - 2 * containerScrollLeft : 0;
                        mask = $('<div class=\'k-loading-mask\'><span class=\'k-loading-text\'>' + kendo.ui.progress.messages.loading + '</span><div class=\'k-loading-image\'/><div class=\'k-loading-color\'/></div>').width('100%').height('100%').css('top', container.scrollTop()).css(leftRight, Math.abs(containerScrollLeft) + webkitCorrection).prependTo(container);
                    }
                } else if (mask) {
                    mask.remove();
                }
            },
            plugin: function (widget, register, prefix) {
                var name = widget.fn.options.name, getter;
                register = register || kendo.ui;
                prefix = prefix || '';
                register[name] = widget;
                register.roles[name.toLowerCase()] = widget;
                getter = 'getKendo' + prefix + name;
                name = 'kendo' + prefix + name;
                var widgetEntry = {
                    name: name,
                    widget: widget,
                    prefix: prefix || ''
                };
                kendo.widgets.push(widgetEntry);
                for (var i = 0, len = kendo._widgetRegisteredCallbacks.length; i < len; i++) {
                    kendo._widgetRegisteredCallbacks[i](widgetEntry);
                }
                $.fn[name] = function (options) {
                    var value = this, args;
                    if (typeof options === STRING) {
                        args = slice.call(arguments, 1);
                        this.each(function () {
                            var widget = $.data(this, name), method, result;
                            if (!widget) {
                                throw new Error(kendo.format('Cannot call method \'{0}\' of {1} before it is initialized', options, name));
                            }
                            method = widget[options];
                            if (typeof method !== FUNCTION) {
                                throw new Error(kendo.format('Cannot find method \'{0}\' of {1}', options, name));
                            }
                            result = method.apply(widget, args);
                            if (result !== undefined) {
                                value = result;
                                return false;
                            }
                        });
                    } else {
                        this.each(function () {
                            return new widget(this, options);
                        });
                    }
                    return value;
                };
                $.fn[name].widget = widget;
                $.fn[getter] = function () {
                    return this.data(name);
                };
            }
        });
        kendo.ui.progress.messages = { loading: 'Loading...' };
        var ContainerNullObject = {
            bind: function () {
                return this;
            },
            nullObject: true,
            options: {}
        };
        var MobileWidget = Widget.extend({
            init: function (element, options) {
                Widget.fn.init.call(this, element, options);
                this.element.autoApplyNS();
                this.wrapper = this.element;
                this.element.addClass('km-widget');
            },
            destroy: function () {
                Widget.fn.destroy.call(this);
                this.element.kendoDestroy();
            },
            options: { prefix: 'Mobile' },
            events: [],
            view: function () {
                var viewElement = this.element.closest(kendo.roleSelector('view splitview modalview drawer'));
                return kendo.widgetInstance(viewElement, kendo.mobile.ui) || ContainerNullObject;
            },
            viewHasNativeScrolling: function () {
                var view = this.view();
                return view && view.options.useNativeScrolling;
            },
            container: function () {
                var element = this.element.closest(kendo.roleSelector('view layout modalview drawer splitview'));
                return kendo.widgetInstance(element.eq(0), kendo.mobile.ui) || ContainerNullObject;
            }
        });
        extend(kendo.mobile, {
            init: function (element) {
                kendo.init(element, kendo.mobile.ui, kendo.ui, kendo.dataviz.ui);
            },
            appLevelNativeScrolling: function () {
                return kendo.mobile.application && kendo.mobile.application.options && kendo.mobile.application.options.useNativeScrolling;
            },
            roles: {},
            ui: {
                Widget: MobileWidget,
                DataBoundWidget: DataBoundWidget.extend(MobileWidget.prototype),
                roles: {},
                plugin: function (widget) {
                    kendo.ui.plugin(widget, kendo.mobile.ui, 'Mobile');
                }
            }
        });
        deepExtend(kendo.dataviz, {
            init: function (element) {
                kendo.init(element, kendo.dataviz.ui);
            },
            ui: {
                roles: {},
                themes: {},
                views: [],
                plugin: function (widget) {
                    kendo.ui.plugin(widget, kendo.dataviz.ui);
                }
            },
            roles: {}
        });
        kendo.touchScroller = function (elements, options) {
            if (!options) {
                options = {};
            }
            options.useNative = true;
            return $(elements).map(function (idx, element) {
                element = $(element);
                if (support.kineticScrollNeeded && kendo.mobile.ui.Scroller && !element.data('kendoMobileScroller')) {
                    element.kendoMobileScroller(options);
                    return element.data('kendoMobileScroller');
                } else {
                    return false;
                }
            })[0];
        };
        kendo.preventDefault = function (e) {
            e.preventDefault();
        };
        kendo.widgetInstance = function (element, suites) {
            var role = element.data(kendo.ns + 'role'), widgets = [], i, length;
            if (role) {
                if (role === 'content') {
                    role = 'scroller';
                }
                if (suites) {
                    if (suites[0]) {
                        for (i = 0, length = suites.length; i < length; i++) {
                            widgets.push(suites[i].roles[role]);
                        }
                    } else {
                        widgets.push(suites.roles[role]);
                    }
                } else {
                    widgets = [
                        kendo.ui.roles[role],
                        kendo.dataviz.ui.roles[role],
                        kendo.mobile.ui.roles[role]
                    ];
                }
                if (role.indexOf('.') >= 0) {
                    widgets = [kendo.getter(role)(window)];
                }
                for (i = 0, length = widgets.length; i < length; i++) {
                    var widget = widgets[i];
                    if (widget) {
                        var instance = element.data('kendo' + widget.fn.options.prefix + widget.fn.options.name);
                        if (instance) {
                            return instance;
                        }
                    }
                }
            }
        };
        kendo.onResize = function (callback) {
            var handler = callback;
            if (support.mobileOS.android) {
                handler = function () {
                    setTimeout(callback, 600);
                };
            }
            $(window).on(support.resize, handler);
            return handler;
        };
        kendo.unbindResize = function (callback) {
            $(window).off(support.resize, callback);
        };
        kendo.attrValue = function (element, key) {
            return element.data(kendo.ns + key);
        };
        kendo.days = {
            Sunday: 0,
            Monday: 1,
            Tuesday: 2,
            Wednesday: 3,
            Thursday: 4,
            Friday: 5,
            Saturday: 6
        };
        function focusable(element, isTabIndexNotNaN) {
            var nodeName = element.nodeName.toLowerCase();
            return (/input|select|textarea|button|object/.test(nodeName) ? !element.disabled : 'a' === nodeName ? element.href || isTabIndexNotNaN : isTabIndexNotNaN) && visible(element);
        }
        function visible(element) {
            return $.expr.filters.visible(element) && !$(element).parents().addBack().filter(function () {
                return $.css(this, 'visibility') === 'hidden';
            }).length;
        }
        $.extend($.expr[':'], {
            kendoFocusable: function (element) {
                var idx = $.attr(element, 'tabindex');
                return focusable(element, !isNaN(idx) && idx > -1);
            }
        });
        var MOUSE_EVENTS = [
            'mousedown',
            'mousemove',
            'mouseenter',
            'mouseleave',
            'mouseover',
            'mouseout',
            'mouseup',
            'click'
        ];
        var EXCLUDE_BUST_CLICK_SELECTOR = 'label, input, [data-rel=external]';
        var MouseEventNormalizer = {
            setupMouseMute: function () {
                var idx = 0, length = MOUSE_EVENTS.length, element = document.documentElement;
                if (MouseEventNormalizer.mouseTrap || !support.eventCapture) {
                    return;
                }
                MouseEventNormalizer.mouseTrap = true;
                MouseEventNormalizer.bustClick = false;
                MouseEventNormalizer.captureMouse = false;
                var handler = function (e) {
                    if (MouseEventNormalizer.captureMouse) {
                        if (e.type === 'click') {
                            if (MouseEventNormalizer.bustClick && !$(e.target).is(EXCLUDE_BUST_CLICK_SELECTOR)) {
                                e.preventDefault();
                                e.stopPropagation();
                            }
                        } else {
                            e.stopPropagation();
                        }
                    }
                };
                for (; idx < length; idx++) {
                    element.addEventListener(MOUSE_EVENTS[idx], handler, true);
                }
            },
            muteMouse: function (e) {
                MouseEventNormalizer.captureMouse = true;
                if (e.data.bustClick) {
                    MouseEventNormalizer.bustClick = true;
                }
                clearTimeout(MouseEventNormalizer.mouseTrapTimeoutID);
            },
            unMuteMouse: function () {
                clearTimeout(MouseEventNormalizer.mouseTrapTimeoutID);
                MouseEventNormalizer.mouseTrapTimeoutID = setTimeout(function () {
                    MouseEventNormalizer.captureMouse = false;
                    MouseEventNormalizer.bustClick = false;
                }, 400);
            }
        };
        var eventMap = {
            down: 'touchstart mousedown',
            move: 'mousemove touchmove',
            up: 'mouseup touchend touchcancel',
            cancel: 'mouseleave touchcancel'
        };
        if (support.touch && (support.mobileOS.ios || support.mobileOS.android)) {
            eventMap = {
                down: 'touchstart',
                move: 'touchmove',
                up: 'touchend touchcancel',
                cancel: 'touchcancel'
            };
        } else if (support.pointers) {
            eventMap = {
                down: 'pointerdown',
                move: 'pointermove',
                up: 'pointerup',
                cancel: 'pointercancel pointerleave'
            };
        } else if (support.msPointers) {
            eventMap = {
                down: 'MSPointerDown',
                move: 'MSPointerMove',
                up: 'MSPointerUp',
                cancel: 'MSPointerCancel MSPointerLeave'
            };
        }
        if (support.msPointers && !('onmspointerenter' in window)) {
            $.each({
                MSPointerEnter: 'MSPointerOver',
                MSPointerLeave: 'MSPointerOut'
            }, function (orig, fix) {
                $.event.special[orig] = {
                    delegateType: fix,
                    bindType: fix,
                    handle: function (event) {
                        var ret, target = this, related = event.relatedTarget, handleObj = event.handleObj;
                        if (!related || related !== target && !$.contains(target, related)) {
                            event.type = handleObj.origType;
                            ret = handleObj.handler.apply(this, arguments);
                            event.type = fix;
                        }
                        return ret;
                    }
                };
            });
        }
        var getEventMap = function (e) {
                return eventMap[e] || e;
            }, eventRegEx = /([^ ]+)/g;
        kendo.applyEventMap = function (events, ns) {
            events = events.replace(eventRegEx, getEventMap);
            if (ns) {
                events = events.replace(eventRegEx, '$1.' + ns);
            }
            return events;
        };
        var on = $.fn.on;
        function kendoJQuery(selector, context) {
            return new kendoJQuery.fn.init(selector, context);
        }
        extend(true, kendoJQuery, $);
        kendoJQuery.fn = kendoJQuery.prototype = new $();
        kendoJQuery.fn.constructor = kendoJQuery;
        kendoJQuery.fn.init = function (selector, context) {
            if (context && context instanceof $ && !(context instanceof kendoJQuery)) {
                context = kendoJQuery(context);
            }
            return $.fn.init.call(this, selector, context, rootjQuery);
        };
        kendoJQuery.fn.init.prototype = kendoJQuery.fn;
        var rootjQuery = kendoJQuery(document);
        extend(kendoJQuery.fn, {
            handler: function (handler) {
                this.data('handler', handler);
                return this;
            },
            autoApplyNS: function (ns) {
                this.data('kendoNS', ns || kendo.guid());
                return this;
            },
            on: function () {
                var that = this, ns = that.data('kendoNS');
                if (arguments.length === 1) {
                    return on.call(that, arguments[0]);
                }
                var context = that, args = slice.call(arguments);
                if (typeof args[args.length - 1] === UNDEFINED) {
                    args.pop();
                }
                var callback = args[args.length - 1], events = kendo.applyEventMap(args[0], ns);
                if (support.mouseAndTouchPresent && events.search(/mouse|click/) > -1 && this[0] !== document.documentElement) {
                    MouseEventNormalizer.setupMouseMute();
                    var selector = args.length === 2 ? null : args[1], bustClick = events.indexOf('click') > -1 && events.indexOf('touchend') > -1;
                    on.call(this, {
                        touchstart: MouseEventNormalizer.muteMouse,
                        touchend: MouseEventNormalizer.unMuteMouse
                    }, selector, { bustClick: bustClick });
                }
                if (typeof callback === STRING) {
                    context = that.data('handler');
                    callback = context[callback];
                    args[args.length - 1] = function (e) {
                        callback.call(context, e);
                    };
                }
                args[0] = events;
                on.apply(that, args);
                return that;
            },
            kendoDestroy: function (ns) {
                ns = ns || this.data('kendoNS');
                if (ns) {
                    this.off('.' + ns);
                }
                return this;
            }
        });
        kendo.jQuery = kendoJQuery;
        kendo.eventMap = eventMap;
        kendo.timezone = function () {
            var months = {
                Jan: 0,
                Feb: 1,
                Mar: 2,
                Apr: 3,
                May: 4,
                Jun: 5,
                Jul: 6,
                Aug: 7,
                Sep: 8,
                Oct: 9,
                Nov: 10,
                Dec: 11
            };
            var days = {
                Sun: 0,
                Mon: 1,
                Tue: 2,
                Wed: 3,
                Thu: 4,
                Fri: 5,
                Sat: 6
            };
            function ruleToDate(year, rule) {
                var date;
                var targetDay;
                var ourDay;
                var month = rule[3];
                var on = rule[4];
                var time = rule[5];
                var cache = rule[8];
                if (!cache) {
                    rule[8] = cache = {};
                }
                if (cache[year]) {
                    return cache[year];
                }
                if (!isNaN(on)) {
                    date = new Date(Date.UTC(year, months[month], on, time[0], time[1], time[2], 0));
                } else if (on.indexOf('last') === 0) {
                    date = new Date(Date.UTC(year, months[month] + 1, 1, time[0] - 24, time[1], time[2], 0));
                    targetDay = days[on.substr(4, 3)];
                    ourDay = date.getUTCDay();
                    date.setUTCDate(date.getUTCDate() + targetDay - ourDay - (targetDay > ourDay ? 7 : 0));
                } else if (on.indexOf('>=') >= 0) {
                    date = new Date(Date.UTC(year, months[month], on.substr(5), time[0], time[1], time[2], 0));
                    targetDay = days[on.substr(0, 3)];
                    ourDay = date.getUTCDay();
                    date.setUTCDate(date.getUTCDate() + targetDay - ourDay + (targetDay < ourDay ? 7 : 0));
                }
                return cache[year] = date;
            }
            function findRule(utcTime, rules, zone) {
                rules = rules[zone];
                if (!rules) {
                    var time = zone.split(':');
                    var offset = 0;
                    if (time.length > 1) {
                        offset = time[0] * 60 + Number(time[1]);
                    }
                    return [
                        -1000000,
                        'max',
                        '-',
                        'Jan',
                        1,
                        [
                            0,
                            0,
                            0
                        ],
                        offset,
                        '-'
                    ];
                }
                var year = new Date(utcTime).getUTCFullYear();
                rules = jQuery.grep(rules, function (rule) {
                    var from = rule[0];
                    var to = rule[1];
                    return from <= year && (to >= year || from == year && to == 'only' || to == 'max');
                });
                rules.push(utcTime);
                rules.sort(function (a, b) {
                    if (typeof a != 'number') {
                        a = Number(ruleToDate(year, a));
                    }
                    if (typeof b != 'number') {
                        b = Number(ruleToDate(year, b));
                    }
                    return a - b;
                });
                var rule = rules[jQuery.inArray(utcTime, rules) - 1] || rules[rules.length - 1];
                return isNaN(rule) ? rule : null;
            }
            function findZone(utcTime, zones, timezone) {
                var zoneRules = zones[timezone];
                if (typeof zoneRules === 'string') {
                    zoneRules = zones[zoneRules];
                }
                if (!zoneRules) {
                    throw new Error('Timezone "' + timezone + '" is either incorrect, or kendo.timezones.min.js is not included.');
                }
                for (var idx = zoneRules.length - 1; idx >= 0; idx--) {
                    var until = zoneRules[idx][3];
                    if (until && utcTime > until) {
                        break;
                    }
                }
                var zone = zoneRules[idx + 1];
                if (!zone) {
                    throw new Error('Timezone "' + timezone + '" not found on ' + utcTime + '.');
                }
                return zone;
            }
            function zoneAndRule(utcTime, zones, rules, timezone) {
                if (typeof utcTime != NUMBER) {
                    utcTime = Date.UTC(utcTime.getFullYear(), utcTime.getMonth(), utcTime.getDate(), utcTime.getHours(), utcTime.getMinutes(), utcTime.getSeconds(), utcTime.getMilliseconds());
                }
                var zone = findZone(utcTime, zones, timezone);
                return {
                    zone: zone,
                    rule: findRule(utcTime, rules, zone[1])
                };
            }
            function offset(utcTime, timezone) {
                if (timezone == 'Etc/UTC' || timezone == 'Etc/GMT') {
                    return 0;
                }
                var info = zoneAndRule(utcTime, this.zones, this.rules, timezone);
                var zone = info.zone;
                var rule = info.rule;
                return kendo.parseFloat(rule ? zone[0] - rule[6] : zone[0]);
            }
            function abbr(utcTime, timezone) {
                var info = zoneAndRule(utcTime, this.zones, this.rules, timezone);
                var zone = info.zone;
                var rule = info.rule;
                var base = zone[2];
                if (base.indexOf('/') >= 0) {
                    return base.split('/')[rule && +rule[6] ? 1 : 0];
                } else if (base.indexOf('%s') >= 0) {
                    return base.replace('%s', !rule || rule[7] == '-' ? '' : rule[7]);
                }
                return base;
            }
            function convert(date, fromOffset, toOffset) {
                if (typeof fromOffset == STRING) {
                    fromOffset = this.offset(date, fromOffset);
                }
                if (typeof toOffset == STRING) {
                    toOffset = this.offset(date, toOffset);
                }
                var fromLocalOffset = date.getTimezoneOffset();
                date = new Date(date.getTime() + (fromOffset - toOffset) * 60000);
                var toLocalOffset = date.getTimezoneOffset();
                return new Date(date.getTime() + (toLocalOffset - fromLocalOffset) * 60000);
            }
            function apply(date, timezone) {
                return this.convert(date, date.getTimezoneOffset(), timezone);
            }
            function remove(date, timezone) {
                return this.convert(date, timezone, date.getTimezoneOffset());
            }
            function toLocalDate(time) {
                return this.apply(new Date(time), 'Etc/UTC');
            }
            return {
                zones: {},
                rules: {},
                offset: offset,
                convert: convert,
                apply: apply,
                remove: remove,
                abbr: abbr,
                toLocalDate: toLocalDate
            };
        }();
        kendo.date = function () {
            var MS_PER_MINUTE = 60000, MS_PER_DAY = 86400000;
            function adjustDST(date, hours) {
                if (hours === 0 && date.getHours() === 23) {
                    date.setHours(date.getHours() + 2);
                    return true;
                }
                return false;
            }
            function setDayOfWeek(date, day, dir) {
                var hours = date.getHours();
                dir = dir || 1;
                day = (day - date.getDay() + 7 * dir) % 7;
                date.setDate(date.getDate() + day);
                adjustDST(date, hours);
            }
            function dayOfWeek(date, day, dir) {
                date = new Date(date);
                setDayOfWeek(date, day, dir);
                return date;
            }
            function firstDayOfMonth(date) {
                return new Date(date.getFullYear(), date.getMonth(), 1);
            }
            function lastDayOfMonth(date) {
                var last = new Date(date.getFullYear(), date.getMonth() + 1, 0), first = firstDayOfMonth(date), timeOffset = Math.abs(last.getTimezoneOffset() - first.getTimezoneOffset());
                if (timeOffset) {
                    last.setHours(first.getHours() + timeOffset / 60);
                }
                return last;
            }
            function moveDateToWeekStart(date, weekStartDay) {
                if (weekStartDay !== 1) {
                    return addDays(dayOfWeek(date, weekStartDay, -1), 4);
                }
                return addDays(date, 4 - (date.getDay() || 7));
            }
            function calcWeekInYear(date, weekStartDay) {
                var firstWeekInYear = new Date(date.getFullYear(), 0, 1, -6);
                var newDate = moveDateToWeekStart(date, weekStartDay);
                var diffInMS = newDate.getTime() - firstWeekInYear.getTime();
                var days = Math.floor(diffInMS / MS_PER_DAY);
                return 1 + Math.floor(days / 7);
            }
            function weekInYear(date, weekStartDay) {
                var prevWeekDate = addDays(date, -7);
                var nextWeekDate = addDays(date, 7);
                var weekNumber = calcWeekInYear(date, weekStartDay);
                if (weekNumber === 0) {
                    return calcWeekInYear(prevWeekDate, weekStartDay) + 1;
                }
                if (weekNumber === 53 && calcWeekInYear(nextWeekDate, weekStartDay) > 1) {
                    return 1;
                }
                return weekNumber;
            }
            function getDate(date) {
                date = new Date(date.getFullYear(), date.getMonth(), date.getDate(), 0, 0, 0);
                adjustDST(date, 0);
                return date;
            }
            function toUtcTime(date) {
                return Date.UTC(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
            }
            function getMilliseconds(date) {
                return toInvariantTime(date).getTime() - getDate(toInvariantTime(date));
            }
            function isInTimeRange(value, min, max) {
                var msMin = getMilliseconds(min), msMax = getMilliseconds(max), msValue;
                if (!value || msMin == msMax) {
                    return true;
                }
                if (min >= max) {
                    max += MS_PER_DAY;
                }
                msValue = getMilliseconds(value);
                if (msMin > msValue) {
                    msValue += MS_PER_DAY;
                }
                if (msMax < msMin) {
                    msMax += MS_PER_DAY;
                }
                return msValue >= msMin && msValue <= msMax;
            }
            function isInDateRange(value, min, max) {
                var msMin = min.getTime(), msMax = max.getTime(), msValue;
                if (msMin >= msMax) {
                    msMax += MS_PER_DAY;
                }
                msValue = value.getTime();
                return msValue >= msMin && msValue <= msMax;
            }
            function addDays(date, offset) {
                var hours = date.getHours();
                date = new Date(date);
                setTime(date, offset * MS_PER_DAY);
                adjustDST(date, hours);
                return date;
            }
            function setTime(date, milliseconds, ignoreDST) {
                var offset = date.getTimezoneOffset();
                var difference;
                date.setTime(date.getTime() + milliseconds);
                if (!ignoreDST) {
                    difference = date.getTimezoneOffset() - offset;
                    date.setTime(date.getTime() + difference * MS_PER_MINUTE);
                }
            }
            function setHours(date, time) {
                date = new Date(kendo.date.getDate(date).getTime() + kendo.date.getMilliseconds(time));
                adjustDST(date, time.getHours());
                return date;
            }
            function today() {
                return getDate(new Date());
            }
            function isToday(date) {
                return getDate(date).getTime() == today().getTime();
            }
            function toInvariantTime(date) {
                var staticDate = new Date(1980, 1, 1, 0, 0, 0);
                if (date) {
                    staticDate.setHours(date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
                }
                return staticDate;
            }
            return {
                adjustDST: adjustDST,
                dayOfWeek: dayOfWeek,
                setDayOfWeek: setDayOfWeek,
                getDate: getDate,
                isInDateRange: isInDateRange,
                isInTimeRange: isInTimeRange,
                isToday: isToday,
                nextDay: function (date) {
                    return addDays(date, 1);
                },
                previousDay: function (date) {
                    return addDays(date, -1);
                },
                toUtcTime: toUtcTime,
                MS_PER_DAY: MS_PER_DAY,
                MS_PER_HOUR: 60 * MS_PER_MINUTE,
                MS_PER_MINUTE: MS_PER_MINUTE,
                setTime: setTime,
                setHours: setHours,
                addDays: addDays,
                today: today,
                toInvariantTime: toInvariantTime,
                firstDayOfMonth: firstDayOfMonth,
                lastDayOfMonth: lastDayOfMonth,
                weekInYear: weekInYear,
                getMilliseconds: getMilliseconds
            };
        }();
        kendo.stripWhitespace = function (element) {
            if (document.createNodeIterator) {
                var iterator = document.createNodeIterator(element, NodeFilter.SHOW_TEXT, function (node) {
                    return node.parentNode == element ? NodeFilter.FILTER_ACCEPT : NodeFilter.FILTER_REJECT;
                }, false);
                while (iterator.nextNode()) {
                    if (iterator.referenceNode && !iterator.referenceNode.textContent.trim()) {
                        iterator.referenceNode.parentNode.removeChild(iterator.referenceNode);
                    }
                }
            } else {
                for (var i = 0; i < element.childNodes.length; i++) {
                    var child = element.childNodes[i];
                    if (child.nodeType == 3 && !/\S/.test(child.nodeValue)) {
                        element.removeChild(child);
                        i--;
                    }
                    if (child.nodeType == 1) {
                        kendo.stripWhitespace(child);
                    }
                }
            }
        };
        var animationFrame = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function (callback) {
            setTimeout(callback, 1000 / 60);
        };
        kendo.animationFrame = function (callback) {
            animationFrame.call(window, callback);
        };
        var animationQueue = [];
        kendo.queueAnimation = function (callback) {
            animationQueue[animationQueue.length] = callback;
            if (animationQueue.length === 1) {
                kendo.runNextAnimation();
            }
        };
        kendo.runNextAnimation = function () {
            kendo.animationFrame(function () {
                if (animationQueue[0]) {
                    animationQueue.shift()();
                    if (animationQueue[0]) {
                        kendo.runNextAnimation();
                    }
                }
            });
        };
        kendo.parseQueryStringParams = function (url) {
            var queryString = url.split('?')[1] || '', params = {}, paramParts = queryString.split(/&|=/), length = paramParts.length, idx = 0;
            for (; idx < length; idx += 2) {
                if (paramParts[idx] !== '') {
                    params[decodeURIComponent(paramParts[idx])] = decodeURIComponent(paramParts[idx + 1]);
                }
            }
            return params;
        };
        kendo.elementUnderCursor = function (e) {
            if (typeof e.x.client != 'undefined') {
                return document.elementFromPoint(e.x.client, e.y.client);
            }
        };
        kendo.wheelDeltaY = function (jQueryEvent) {
            var e = jQueryEvent.originalEvent, deltaY = e.wheelDeltaY, delta;
            if (e.wheelDelta) {
                if (deltaY === undefined || deltaY) {
                    delta = e.wheelDelta;
                }
            } else if (e.detail && e.axis === e.VERTICAL_AXIS) {
                delta = -e.detail * 10;
            }
            return delta;
        };
        kendo.throttle = function (fn, delay) {
            var timeout;
            var lastExecTime = 0;
            if (!delay || delay <= 0) {
                return fn;
            }
            var throttled = function () {
                var that = this;
                var elapsed = +new Date() - lastExecTime;
                var args = arguments;
                function exec() {
                    fn.apply(that, args);
                    lastExecTime = +new Date();
                }
                if (!lastExecTime) {
                    return exec();
                }
                if (timeout) {
                    clearTimeout(timeout);
                }
                if (elapsed > delay) {
                    exec();
                } else {
                    timeout = setTimeout(exec, delay - elapsed);
                }
            };
            throttled.cancel = function () {
                clearTimeout(timeout);
            };
            return throttled;
        };
        kendo.caret = function (element, start, end) {
            var rangeElement;
            var isPosition = start !== undefined;
            if (end === undefined) {
                end = start;
            }
            if (element[0]) {
                element = element[0];
            }
            if (isPosition && element.disabled) {
                return;
            }
            try {
                if (element.selectionStart !== undefined) {
                    if (isPosition) {
                        element.focus();
                        var mobile = support.mobileOS;
                        if (mobile.wp || mobile.android) {
                            setTimeout(function () {
                                element.setSelectionRange(start, end);
                            }, 0);
                        } else {
                            element.setSelectionRange(start, end);
                        }
                    } else {
                        start = [
                            element.selectionStart,
                            element.selectionEnd
                        ];
                    }
                } else if (document.selection) {
                    if ($(element).is(':visible')) {
                        element.focus();
                    }
                    rangeElement = element.createTextRange();
                    if (isPosition) {
                        rangeElement.collapse(true);
                        rangeElement.moveStart('character', start);
                        rangeElement.moveEnd('character', end - start);
                        rangeElement.select();
                    } else {
                        var rangeDuplicated = rangeElement.duplicate(), selectionStart, selectionEnd;
                        rangeElement.moveToBookmark(document.selection.createRange().getBookmark());
                        rangeDuplicated.setEndPoint('EndToStart', rangeElement);
                        selectionStart = rangeDuplicated.text.length;
                        selectionEnd = selectionStart + rangeElement.text.length;
                        start = [
                            selectionStart,
                            selectionEnd
                        ];
                    }
                }
            } catch (e) {
                start = [];
            }
            return start;
        };
        kendo.compileMobileDirective = function (element, scope) {
            var angular = window.angular;
            element.attr('data-' + kendo.ns + 'role', element[0].tagName.toLowerCase().replace('kendo-mobile-', '').replace('-', ''));
            angular.element(element).injector().invoke([
                '$compile',
                function ($compile) {
                    $compile(element)(scope);
                    if (!/^\$(digest|apply)$/.test(scope.$$phase)) {
                        scope.$digest();
                    }
                }
            ]);
            return kendo.widgetInstance(element, kendo.mobile.ui);
        };
        kendo.antiForgeryTokens = function () {
            var tokens = {}, csrf_token = $('meta[name=csrf-token],meta[name=_csrf]').attr('content'), csrf_param = $('meta[name=csrf-param],meta[name=_csrf_header]').attr('content');
            $('input[name^=\'__RequestVerificationToken\']').each(function () {
                tokens[this.name] = this.value;
            });
            if (csrf_param !== undefined && csrf_token !== undefined) {
                tokens[csrf_param] = csrf_token;
            }
            return tokens;
        };
        kendo.cycleForm = function (form) {
            var firstElement = form.find('input, .k-widget').first();
            var lastElement = form.find('button, .k-button').last();
            function focus(el) {
                var widget = kendo.widgetInstance(el);
                if (widget && widget.focus) {
                    widget.focus();
                } else {
                    el.focus();
                }
            }
            lastElement.on('keydown', function (e) {
                if (e.keyCode == kendo.keys.TAB && !e.shiftKey) {
                    e.preventDefault();
                    focus(firstElement);
                }
            });
            firstElement.on('keydown', function (e) {
                if (e.keyCode == kendo.keys.TAB && e.shiftKey) {
                    e.preventDefault();
                    focus(lastElement);
                }
            });
        };
        (function () {
            function postToProxy(dataURI, fileName, proxyURL, proxyTarget) {
                var form = $('<form>').attr({
                    action: proxyURL,
                    method: 'POST',
                    target: proxyTarget
                });
                var data = kendo.antiForgeryTokens();
                data.fileName = fileName;
                var parts = dataURI.split(';base64,');
                data.contentType = parts[0].replace('data:', '');
                data.base64 = parts[1];
                for (var name in data) {
                    if (data.hasOwnProperty(name)) {
                        $('<input>').attr({
                            value: data[name],
                            name: name,
                            type: 'hidden'
                        }).appendTo(form);
                    }
                }
                form.appendTo('body').submit().remove();
            }
            var fileSaver = document.createElement('a');
            var downloadAttribute = 'download' in fileSaver && !kendo.support.browser.edge;
            function saveAsBlob(dataURI, fileName) {
                var blob = dataURI;
                if (typeof dataURI == 'string') {
                    var parts = dataURI.split(';base64,');
                    var contentType = parts[0];
                    var base64 = atob(parts[1]);
                    var array = new Uint8Array(base64.length);
                    for (var idx = 0; idx < base64.length; idx++) {
                        array[idx] = base64.charCodeAt(idx);
                    }
                    blob = new Blob([array.buffer], { type: contentType });
                }
                navigator.msSaveBlob(blob, fileName);
            }
            function saveAsDataURI(dataURI, fileName) {
                if (window.Blob && dataURI instanceof Blob) {
                    dataURI = URL.createObjectURL(dataURI);
                }
                fileSaver.download = fileName;
                fileSaver.href = dataURI;
                var e = document.createEvent('MouseEvents');
                e.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
                fileSaver.dispatchEvent(e);
                setTimeout(function () {
                    URL.revokeObjectURL(dataURI);
                });
            }
            kendo.saveAs = function (options) {
                var save = postToProxy;
                if (!options.forceProxy) {
                    if (downloadAttribute) {
                        save = saveAsDataURI;
                    } else if (navigator.msSaveBlob) {
                        save = saveAsBlob;
                    }
                }
                save(options.dataURI, options.fileName, options.proxyURL, options.proxyTarget);
            };
        }());
        kendo.proxyModelSetters = function proxyModelSetters(data) {
            var observable = {};
            Object.keys(data || {}).forEach(function (property) {
                Object.defineProperty(observable, property, {
                    get: function () {
                        return data[property];
                    },
                    set: function (value) {
                        data[property] = value;
                        data.dirty = true;
                    }
                });
            });
            return observable;
        };
    }(jQuery, window));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.fx', ['kendo.core'], f);
}(function () {
    var __meta__ = {
        id: 'fx',
        name: 'Effects',
        category: 'framework',
        description: 'Required for animation effects in all Kendo UI widgets.',
        depends: ['core']
    };
    (function ($, undefined) {
        var kendo = window.kendo, fx = kendo.effects, each = $.each, extend = $.extend, proxy = $.proxy, support = kendo.support, browser = support.browser, transforms = support.transforms, transitions = support.transitions, scaleProperties = {
                scale: 0,
                scalex: 0,
                scaley: 0,
                scale3d: 0
            }, translateProperties = {
                translate: 0,
                translatex: 0,
                translatey: 0,
                translate3d: 0
            }, hasZoom = typeof document.documentElement.style.zoom !== 'undefined' && !transforms, matrix3dRegExp = /matrix3?d?\s*\(.*,\s*([\d\.\-]+)\w*?,\s*([\d\.\-]+)\w*?,\s*([\d\.\-]+)\w*?,\s*([\d\.\-]+)\w*?/i, cssParamsRegExp = /^(-?[\d\.\-]+)?[\w\s]*,?\s*(-?[\d\.\-]+)?[\w\s]*/i, translateXRegExp = /translatex?$/i, oldEffectsRegExp = /(zoom|fade|expand)(\w+)/, singleEffectRegExp = /(zoom|fade|expand)/, unitRegExp = /[xy]$/i, transformProps = [
                'perspective',
                'rotate',
                'rotatex',
                'rotatey',
                'rotatez',
                'rotate3d',
                'scale',
                'scalex',
                'scaley',
                'scalez',
                'scale3d',
                'skew',
                'skewx',
                'skewy',
                'translate',
                'translatex',
                'translatey',
                'translatez',
                'translate3d',
                'matrix',
                'matrix3d'
            ], transform2d = [
                'rotate',
                'scale',
                'scalex',
                'scaley',
                'skew',
                'skewx',
                'skewy',
                'translate',
                'translatex',
                'translatey',
                'matrix'
            ], transform2units = {
                'rotate': 'deg',
                scale: '',
                skew: 'px',
                translate: 'px'
            }, cssPrefix = transforms.css, round = Math.round, BLANK = '', PX = 'px', NONE = 'none', AUTO = 'auto', WIDTH = 'width', HEIGHT = 'height', HIDDEN = 'hidden', ORIGIN = 'origin', ABORT_ID = 'abortId', OVERFLOW = 'overflow', TRANSLATE = 'translate', POSITION = 'position', COMPLETE_CALLBACK = 'completeCallback', TRANSITION = cssPrefix + 'transition', TRANSFORM = cssPrefix + 'transform', BACKFACE = cssPrefix + 'backface-visibility', PERSPECTIVE = cssPrefix + 'perspective', DEFAULT_PERSPECTIVE = '1500px', TRANSFORM_PERSPECTIVE = 'perspective(' + DEFAULT_PERSPECTIVE + ')', directions = {
                left: {
                    reverse: 'right',
                    property: 'left',
                    transition: 'translatex',
                    vertical: false,
                    modifier: -1
                },
                right: {
                    reverse: 'left',
                    property: 'left',
                    transition: 'translatex',
                    vertical: false,
                    modifier: 1
                },
                down: {
                    reverse: 'up',
                    property: 'top',
                    transition: 'translatey',
                    vertical: true,
                    modifier: 1
                },
                up: {
                    reverse: 'down',
                    property: 'top',
                    transition: 'translatey',
                    vertical: true,
                    modifier: -1
                },
                top: { reverse: 'bottom' },
                bottom: { reverse: 'top' },
                'in': {
                    reverse: 'out',
                    modifier: -1
                },
                out: {
                    reverse: 'in',
                    modifier: 1
                },
                vertical: { reverse: 'vertical' },
                horizontal: { reverse: 'horizontal' }
            };
        kendo.directions = directions;
        extend($.fn, {
            kendoStop: function (clearQueue, gotoEnd) {
                if (transitions) {
                    return fx.stopQueue(this, clearQueue || false, gotoEnd || false);
                } else {
                    return this.stop(clearQueue, gotoEnd);
                }
            }
        });
        if (transforms && !transitions) {
            each(transform2d, function (idx, value) {
                $.fn[value] = function (val) {
                    if (typeof val == 'undefined') {
                        return animationProperty(this, value);
                    } else {
                        var that = $(this)[0], transformValue = value + '(' + val + transform2units[value.replace(unitRegExp, '')] + ')';
                        if (that.style.cssText.indexOf(TRANSFORM) == -1) {
                            $(this).css(TRANSFORM, transformValue);
                        } else {
                            that.style.cssText = that.style.cssText.replace(new RegExp(value + '\\(.*?\\)', 'i'), transformValue);
                        }
                    }
                    return this;
                };
                $.fx.step[value] = function (fx) {
                    $(fx.elem)[value](fx.now);
                };
            });
            var curProxy = $.fx.prototype.cur;
            $.fx.prototype.cur = function () {
                if (transform2d.indexOf(this.prop) != -1) {
                    return parseFloat($(this.elem)[this.prop]());
                }
                return curProxy.apply(this, arguments);
            };
        }
        kendo.toggleClass = function (element, classes, options, add) {
            if (classes) {
                classes = classes.split(' ');
                if (transitions) {
                    options = extend({
                        exclusive: 'all',
                        duration: 400,
                        ease: 'ease-out'
                    }, options);
                    element.css(TRANSITION, options.exclusive + ' ' + options.duration + 'ms ' + options.ease);
                    setTimeout(function () {
                        element.css(TRANSITION, '').css(HEIGHT);
                    }, options.duration);
                }
                each(classes, function (idx, value) {
                    element.toggleClass(value, add);
                });
            }
            return element;
        };
        kendo.parseEffects = function (input, mirror) {
            var effects = {};
            if (typeof input === 'string') {
                each(input.split(' '), function (idx, value) {
                    var redirectedEffect = !singleEffectRegExp.test(value), resolved = value.replace(oldEffectsRegExp, function (match, $1, $2) {
                            return $1 + ':' + $2.toLowerCase();
                        }), effect = resolved.split(':'), direction = effect[1], effectBody = {};
                    if (effect.length > 1) {
                        effectBody.direction = mirror && redirectedEffect ? directions[direction].reverse : direction;
                    }
                    effects[effect[0]] = effectBody;
                });
            } else {
                each(input, function (idx) {
                    var direction = this.direction;
                    if (direction && mirror && !singleEffectRegExp.test(idx)) {
                        this.direction = directions[direction].reverse;
                    }
                    effects[idx] = this;
                });
            }
            return effects;
        };
        function parseInteger(value) {
            return parseInt(value, 10);
        }
        function parseCSS(element, property) {
            return parseInteger(element.css(property));
        }
        function keys(obj) {
            var acc = [];
            for (var propertyName in obj) {
                acc.push(propertyName);
            }
            return acc;
        }
        function strip3DTransforms(properties) {
            for (var key in properties) {
                if (transformProps.indexOf(key) != -1 && transform2d.indexOf(key) == -1) {
                    delete properties[key];
                }
            }
            return properties;
        }
        function normalizeCSS(element, properties) {
            var transformation = [], cssValues = {}, lowerKey, key, value, isTransformed;
            for (key in properties) {
                lowerKey = key.toLowerCase();
                isTransformed = transforms && transformProps.indexOf(lowerKey) != -1;
                if (!support.hasHW3D && isTransformed && transform2d.indexOf(lowerKey) == -1) {
                    delete properties[key];
                } else {
                    value = properties[key];
                    if (isTransformed) {
                        transformation.push(key + '(' + value + ')');
                    } else {
                        cssValues[key] = value;
                    }
                }
            }
            if (transformation.length) {
                cssValues[TRANSFORM] = transformation.join(' ');
            }
            return cssValues;
        }
        if (transitions) {
            extend(fx, {
                transition: function (element, properties, options) {
                    var css, delay = 0, oldKeys = element.data('keys') || [], timeoutID;
                    options = extend({
                        duration: 200,
                        ease: 'ease-out',
                        complete: null,
                        exclusive: 'all'
                    }, options);
                    var stopTransitionCalled = false;
                    var stopTransition = function () {
                        if (!stopTransitionCalled) {
                            stopTransitionCalled = true;
                            if (timeoutID) {
                                clearTimeout(timeoutID);
                                timeoutID = null;
                            }
                            element.removeData(ABORT_ID).dequeue().css(TRANSITION, '').css(TRANSITION);
                            options.complete.call(element);
                        }
                    };
                    options.duration = $.fx ? $.fx.speeds[options.duration] || options.duration : options.duration;
                    css = normalizeCSS(element, properties);
                    $.merge(oldKeys, keys(css));
                    element.data('keys', $.unique(oldKeys)).height();
                    element.css(TRANSITION, options.exclusive + ' ' + options.duration + 'ms ' + options.ease).css(TRANSITION);
                    element.css(css).css(TRANSFORM);
                    if (transitions.event) {
                        element.one(transitions.event, stopTransition);
                        if (options.duration !== 0) {
                            delay = 500;
                        }
                    }
                    timeoutID = setTimeout(stopTransition, options.duration + delay);
                    element.data(ABORT_ID, timeoutID);
                    element.data(COMPLETE_CALLBACK, stopTransition);
                },
                stopQueue: function (element, clearQueue, gotoEnd) {
                    var cssValues, taskKeys = element.data('keys'), retainPosition = !gotoEnd && taskKeys, completeCallback = element.data(COMPLETE_CALLBACK);
                    if (retainPosition) {
                        cssValues = kendo.getComputedStyles(element[0], taskKeys);
                    }
                    if (completeCallback) {
                        completeCallback();
                    }
                    if (retainPosition) {
                        element.css(cssValues);
                    }
                    return element.removeData('keys').stop(clearQueue);
                }
            });
        }
        function animationProperty(element, property) {
            if (transforms) {
                var transform = element.css(TRANSFORM);
                if (transform == NONE) {
                    return property == 'scale' ? 1 : 0;
                }
                var match = transform.match(new RegExp(property + '\\s*\\(([\\d\\w\\.]+)')), computed = 0;
                if (match) {
                    computed = parseInteger(match[1]);
                } else {
                    match = transform.match(matrix3dRegExp) || [
                        0,
                        0,
                        0,
                        0,
                        0
                    ];
                    property = property.toLowerCase();
                    if (translateXRegExp.test(property)) {
                        computed = parseFloat(match[3] / match[2]);
                    } else if (property == 'translatey') {
                        computed = parseFloat(match[4] / match[2]);
                    } else if (property == 'scale') {
                        computed = parseFloat(match[2]);
                    } else if (property == 'rotate') {
                        computed = parseFloat(Math.atan2(match[2], match[1]));
                    }
                }
                return computed;
            } else {
                return parseFloat(element.css(property));
            }
        }
        var EffectSet = kendo.Class.extend({
            init: function (element, options) {
                var that = this;
                that.element = element;
                that.effects = [];
                that.options = options;
                that.restore = [];
            },
            run: function (effects) {
                var that = this, effect, idx, jdx, length = effects.length, element = that.element, options = that.options, deferred = $.Deferred(), start = {}, end = {}, target, children, childrenLength;
                that.effects = effects;
                deferred.then($.proxy(that, 'complete'));
                element.data('animating', true);
                for (idx = 0; idx < length; idx++) {
                    effect = effects[idx];
                    effect.setReverse(options.reverse);
                    effect.setOptions(options);
                    that.addRestoreProperties(effect.restore);
                    effect.prepare(start, end);
                    children = effect.children();
                    for (jdx = 0, childrenLength = children.length; jdx < childrenLength; jdx++) {
                        children[jdx].duration(options.duration).run();
                    }
                }
                for (var effectName in options.effects) {
                    extend(end, options.effects[effectName].properties);
                }
                if (!element.is(':visible')) {
                    extend(start, { display: element.data('olddisplay') || 'block' });
                }
                if (transforms && !options.reset) {
                    target = element.data('targetTransform');
                    if (target) {
                        start = extend(target, start);
                    }
                }
                start = normalizeCSS(element, start);
                if (transforms && !transitions) {
                    start = strip3DTransforms(start);
                }
                element.css(start).css(TRANSFORM);
                for (idx = 0; idx < length; idx++) {
                    effects[idx].setup();
                }
                if (options.init) {
                    options.init();
                }
                element.data('targetTransform', end);
                fx.animate(element, end, extend({}, options, { complete: deferred.resolve }));
                return deferred.promise();
            },
            stop: function () {
                $(this.element).kendoStop(true, true);
            },
            addRestoreProperties: function (restore) {
                var element = this.element, value, i = 0, length = restore.length;
                for (; i < length; i++) {
                    value = restore[i];
                    this.restore.push(value);
                    if (!element.data(value)) {
                        element.data(value, element.css(value));
                    }
                }
            },
            restoreCallback: function () {
                var element = this.element;
                for (var i = 0, length = this.restore.length; i < length; i++) {
                    var value = this.restore[i];
                    element.css(value, element.data(value));
                }
            },
            complete: function () {
                var that = this, idx = 0, element = that.element, options = that.options, effects = that.effects, length = effects.length;
                element.removeData('animating').dequeue();
                if (options.hide) {
                    element.data('olddisplay', element.css('display')).hide();
                }
                this.restoreCallback();
                if (hasZoom && !transforms) {
                    setTimeout($.proxy(this, 'restoreCallback'), 0);
                }
                for (; idx < length; idx++) {
                    effects[idx].teardown();
                }
                if (options.completeCallback) {
                    options.completeCallback(element);
                }
            }
        });
        fx.promise = function (element, options) {
            var effects = [], effectClass, effectSet = new EffectSet(element, options), parsedEffects = kendo.parseEffects(options.effects), effect;
            options.effects = parsedEffects;
            for (var effectName in parsedEffects) {
                effectClass = fx[capitalize(effectName)];
                if (effectClass) {
                    effect = new effectClass(element, parsedEffects[effectName].direction);
                    effects.push(effect);
                }
            }
            if (effects[0]) {
                effectSet.run(effects);
            } else {
                if (!element.is(':visible')) {
                    element.css({ display: element.data('olddisplay') || 'block' }).css('display');
                }
                if (options.init) {
                    options.init();
                }
                element.dequeue();
                effectSet.complete();
            }
        };
        extend(fx, {
            animate: function (elements, properties, options) {
                var useTransition = options.transition !== false;
                delete options.transition;
                if (transitions && 'transition' in fx && useTransition) {
                    fx.transition(elements, properties, options);
                } else {
                    if (transforms) {
                        elements.animate(strip3DTransforms(properties), {
                            queue: false,
                            show: false,
                            hide: false,
                            duration: options.duration,
                            complete: options.complete
                        });
                    } else {
                        elements.each(function () {
                            var element = $(this), multiple = {};
                            each(transformProps, function (idx, value) {
                                var params, currentValue = properties ? properties[value] + ' ' : null;
                                if (currentValue) {
                                    var single = properties;
                                    if (value in scaleProperties && properties[value] !== undefined) {
                                        params = currentValue.match(cssParamsRegExp);
                                        if (transforms) {
                                            extend(single, { scale: +params[0] });
                                        }
                                    } else {
                                        if (value in translateProperties && properties[value] !== undefined) {
                                            var position = element.css(POSITION), isFixed = position == 'absolute' || position == 'fixed';
                                            if (!element.data(TRANSLATE)) {
                                                if (isFixed) {
                                                    element.data(TRANSLATE, {
                                                        top: parseCSS(element, 'top') || 0,
                                                        left: parseCSS(element, 'left') || 0,
                                                        bottom: parseCSS(element, 'bottom'),
                                                        right: parseCSS(element, 'right')
                                                    });
                                                } else {
                                                    element.data(TRANSLATE, {
                                                        top: parseCSS(element, 'marginTop') || 0,
                                                        left: parseCSS(element, 'marginLeft') || 0
                                                    });
                                                }
                                            }
                                            var originalPosition = element.data(TRANSLATE);
                                            params = currentValue.match(cssParamsRegExp);
                                            if (params) {
                                                var dX = value == TRANSLATE + 'y' ? +null : +params[1], dY = value == TRANSLATE + 'y' ? +params[1] : +params[2];
                                                if (isFixed) {
                                                    if (!isNaN(originalPosition.right)) {
                                                        if (!isNaN(dX)) {
                                                            extend(single, { right: originalPosition.right - dX });
                                                        }
                                                    } else {
                                                        if (!isNaN(dX)) {
                                                            extend(single, { left: originalPosition.left + dX });
                                                        }
                                                    }
                                                    if (!isNaN(originalPosition.bottom)) {
                                                        if (!isNaN(dY)) {
                                                            extend(single, { bottom: originalPosition.bottom - dY });
                                                        }
                                                    } else {
                                                        if (!isNaN(dY)) {
                                                            extend(single, { top: originalPosition.top + dY });
                                                        }
                                                    }
                                                } else {
                                                    if (!isNaN(dX)) {
                                                        extend(single, { marginLeft: originalPosition.left + dX });
                                                    }
                                                    if (!isNaN(dY)) {
                                                        extend(single, { marginTop: originalPosition.top + dY });
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (!transforms && value != 'scale' && value in single) {
                                        delete single[value];
                                    }
                                    if (single) {
                                        extend(multiple, single);
                                    }
                                }
                            });
                            if (browser.msie) {
                                delete multiple.scale;
                            }
                            element.animate(multiple, {
                                queue: false,
                                show: false,
                                hide: false,
                                duration: options.duration,
                                complete: options.complete
                            });
                        });
                    }
                }
            }
        });
        fx.animatedPromise = fx.promise;
        var Effect = kendo.Class.extend({
            init: function (element, direction) {
                var that = this;
                that.element = element;
                that._direction = direction;
                that.options = {};
                that._additionalEffects = [];
                if (!that.restore) {
                    that.restore = [];
                }
            },
            reverse: function () {
                this._reverse = true;
                return this.run();
            },
            play: function () {
                this._reverse = false;
                return this.run();
            },
            add: function (additional) {
                this._additionalEffects.push(additional);
                return this;
            },
            direction: function (value) {
                this._direction = value;
                return this;
            },
            duration: function (duration) {
                this._duration = duration;
                return this;
            },
            compositeRun: function () {
                var that = this, effectSet = new EffectSet(that.element, {
                        reverse: that._reverse,
                        duration: that._duration
                    }), effects = that._additionalEffects.concat([that]);
                return effectSet.run(effects);
            },
            run: function () {
                if (this._additionalEffects && this._additionalEffects[0]) {
                    return this.compositeRun();
                }
                var that = this, element = that.element, idx = 0, restore = that.restore, length = restore.length, value, deferred = $.Deferred(), start = {}, end = {}, target, children = that.children(), childrenLength = children.length;
                deferred.then($.proxy(that, '_complete'));
                element.data('animating', true);
                for (idx = 0; idx < length; idx++) {
                    value = restore[idx];
                    if (!element.data(value)) {
                        element.data(value, element.css(value));
                    }
                }
                for (idx = 0; idx < childrenLength; idx++) {
                    children[idx].duration(that._duration).run();
                }
                that.prepare(start, end);
                if (!element.is(':visible')) {
                    extend(start, { display: element.data('olddisplay') || 'block' });
                }
                if (transforms) {
                    target = element.data('targetTransform');
                    if (target) {
                        start = extend(target, start);
                    }
                }
                start = normalizeCSS(element, start);
                if (transforms && !transitions) {
                    start = strip3DTransforms(start);
                }
                element.css(start).css(TRANSFORM);
                that.setup();
                element.data('targetTransform', end);
                fx.animate(element, end, {
                    duration: that._duration,
                    complete: deferred.resolve
                });
                return deferred.promise();
            },
            stop: function () {
                var idx = 0, children = this.children(), childrenLength = children.length;
                for (idx = 0; idx < childrenLength; idx++) {
                    children[idx].stop();
                }
                $(this.element).kendoStop(true, true);
                return this;
            },
            restoreCallback: function () {
                var element = this.element;
                for (var i = 0, length = this.restore.length; i < length; i++) {
                    var value = this.restore[i];
                    element.css(value, element.data(value));
                }
            },
            _complete: function () {
                var that = this, element = that.element;
                element.removeData('animating').dequeue();
                that.restoreCallback();
                if (that.shouldHide()) {
                    element.data('olddisplay', element.css('display')).hide();
                }
                if (hasZoom && !transforms) {
                    setTimeout($.proxy(that, 'restoreCallback'), 0);
                }
                that.teardown();
            },
            setOptions: function (options) {
                extend(true, this.options, options);
            },
            children: function () {
                return [];
            },
            shouldHide: $.noop,
            setup: $.noop,
            prepare: $.noop,
            teardown: $.noop,
            directions: [],
            setReverse: function (reverse) {
                this._reverse = reverse;
                return this;
            }
        });
        function capitalize(word) {
            return word.charAt(0).toUpperCase() + word.substring(1);
        }
        function createEffect(name, definition) {
            var effectClass = Effect.extend(definition), directions = effectClass.prototype.directions;
            fx[capitalize(name)] = effectClass;
            fx.Element.prototype[name] = function (direction, opt1, opt2, opt3) {
                return new effectClass(this.element, direction, opt1, opt2, opt3);
            };
            each(directions, function (idx, theDirection) {
                fx.Element.prototype[name + capitalize(theDirection)] = function (opt1, opt2, opt3) {
                    return new effectClass(this.element, theDirection, opt1, opt2, opt3);
                };
            });
        }
        var FOUR_DIRECTIONS = [
                'left',
                'right',
                'up',
                'down'
            ], IN_OUT = [
                'in',
                'out'
            ];
        createEffect('slideIn', {
            directions: FOUR_DIRECTIONS,
            divisor: function (value) {
                this.options.divisor = value;
                return this;
            },
            prepare: function (start, end) {
                var that = this, tmp, element = that.element, outerWidth = kendo._outerWidth, outerHeight = kendo._outerHeight, direction = directions[that._direction], offset = -direction.modifier * (direction.vertical ? outerHeight(element) : outerWidth(element)), startValue = offset / (that.options && that.options.divisor || 1) + PX, endValue = '0px';
                if (that._reverse) {
                    tmp = start;
                    start = end;
                    end = tmp;
                }
                if (transforms) {
                    start[direction.transition] = startValue;
                    end[direction.transition] = endValue;
                } else {
                    start[direction.property] = startValue;
                    end[direction.property] = endValue;
                }
            }
        });
        createEffect('tile', {
            directions: FOUR_DIRECTIONS,
            init: function (element, direction, previous) {
                Effect.prototype.init.call(this, element, direction);
                this.options = { previous: previous };
            },
            previousDivisor: function (value) {
                this.options.previousDivisor = value;
                return this;
            },
            children: function () {
                var that = this, reverse = that._reverse, previous = that.options.previous, divisor = that.options.previousDivisor || 1, dir = that._direction;
                var children = [kendo.fx(that.element).slideIn(dir).setReverse(reverse)];
                if (previous) {
                    children.push(kendo.fx(previous).slideIn(directions[dir].reverse).divisor(divisor).setReverse(!reverse));
                }
                return children;
            }
        });
        function createToggleEffect(name, property, defaultStart, defaultEnd) {
            createEffect(name, {
                directions: IN_OUT,
                startValue: function (value) {
                    this._startValue = value;
                    return this;
                },
                endValue: function (value) {
                    this._endValue = value;
                    return this;
                },
                shouldHide: function () {
                    return this._shouldHide;
                },
                prepare: function (start, end) {
                    var that = this, startValue, endValue, out = this._direction === 'out', startDataValue = that.element.data(property), startDataValueIsSet = !(isNaN(startDataValue) || startDataValue == defaultStart);
                    if (startDataValueIsSet) {
                        startValue = startDataValue;
                    } else if (typeof this._startValue !== 'undefined') {
                        startValue = this._startValue;
                    } else {
                        startValue = out ? defaultStart : defaultEnd;
                    }
                    if (typeof this._endValue !== 'undefined') {
                        endValue = this._endValue;
                    } else {
                        endValue = out ? defaultEnd : defaultStart;
                    }
                    if (this._reverse) {
                        start[property] = endValue;
                        end[property] = startValue;
                    } else {
                        start[property] = startValue;
                        end[property] = endValue;
                    }
                    that._shouldHide = end[property] === defaultEnd;
                }
            });
        }
        createToggleEffect('fade', 'opacity', 1, 0);
        createToggleEffect('zoom', 'scale', 1, 0.01);
        createEffect('slideMargin', {
            prepare: function (start, end) {
                var that = this, element = that.element, options = that.options, origin = element.data(ORIGIN), offset = options.offset, margin, reverse = that._reverse;
                if (!reverse && origin === null) {
                    element.data(ORIGIN, parseFloat(element.css('margin-' + options.axis)));
                }
                margin = element.data(ORIGIN) || 0;
                end['margin-' + options.axis] = !reverse ? margin + offset : margin;
            }
        });
        createEffect('slideTo', {
            prepare: function (start, end) {
                var that = this, element = that.element, options = that.options, offset = options.offset.split(','), reverse = that._reverse;
                if (transforms) {
                    end.translatex = !reverse ? offset[0] : 0;
                    end.translatey = !reverse ? offset[1] : 0;
                } else {
                    end.left = !reverse ? offset[0] : 0;
                    end.top = !reverse ? offset[1] : 0;
                }
                element.css('left');
            }
        });
        createEffect('expand', {
            directions: [
                'horizontal',
                'vertical'
            ],
            restore: [OVERFLOW],
            prepare: function (start, end) {
                var that = this, element = that.element, options = that.options, reverse = that._reverse, property = that._direction === 'vertical' ? HEIGHT : WIDTH, setLength = element[0].style[property], oldLength = element.data(property), length = parseFloat(oldLength || setLength), realLength = round(element.css(property, AUTO)[property]());
                start.overflow = HIDDEN;
                length = options && options.reset ? realLength || length : length || realLength;
                end[property] = (reverse ? 0 : length) + PX;
                start[property] = (reverse ? length : 0) + PX;
                if (oldLength === undefined) {
                    element.data(property, setLength);
                }
            },
            shouldHide: function () {
                return this._reverse;
            },
            teardown: function () {
                var that = this, element = that.element, property = that._direction === 'vertical' ? HEIGHT : WIDTH, length = element.data(property);
                if (length == AUTO || length === BLANK) {
                    setTimeout(function () {
                        element.css(property, AUTO).css(property);
                    }, 0);
                }
            }
        });
        var TRANSFER_START_STATE = {
            position: 'absolute',
            marginLeft: 0,
            marginTop: 0,
            scale: 1
        };
        createEffect('transfer', {
            init: function (element, target) {
                this.element = element;
                this.options = { target: target };
                this.restore = [];
            },
            setup: function () {
                this.element.appendTo(document.body);
            },
            prepare: function (start, end) {
                var that = this, element = that.element, outerBox = fx.box(element), innerBox = fx.box(that.options.target), currentScale = animationProperty(element, 'scale'), scale = fx.fillScale(innerBox, outerBox), transformOrigin = fx.transformOrigin(innerBox, outerBox);
                extend(start, TRANSFER_START_STATE);
                end.scale = 1;
                element.css(TRANSFORM, 'scale(1)').css(TRANSFORM);
                element.css(TRANSFORM, 'scale(' + currentScale + ')');
                start.top = outerBox.top;
                start.left = outerBox.left;
                start.transformOrigin = transformOrigin.x + PX + ' ' + transformOrigin.y + PX;
                if (that._reverse) {
                    start.scale = scale;
                } else {
                    end.scale = scale;
                }
            }
        });
        var CLIPS = {
            top: 'rect(auto auto $size auto)',
            bottom: 'rect($size auto auto auto)',
            left: 'rect(auto $size auto auto)',
            right: 'rect(auto auto auto $size)'
        };
        var ROTATIONS = {
            top: {
                start: 'rotatex(0deg)',
                end: 'rotatex(180deg)'
            },
            bottom: {
                start: 'rotatex(-180deg)',
                end: 'rotatex(0deg)'
            },
            left: {
                start: 'rotatey(0deg)',
                end: 'rotatey(-180deg)'
            },
            right: {
                start: 'rotatey(180deg)',
                end: 'rotatey(0deg)'
            }
        };
        function clipInHalf(container, direction) {
            var vertical = kendo.directions[direction].vertical, size = container[vertical ? HEIGHT : WIDTH]() / 2 + 'px';
            return CLIPS[direction].replace('$size', size);
        }
        createEffect('turningPage', {
            directions: FOUR_DIRECTIONS,
            init: function (element, direction, container) {
                Effect.prototype.init.call(this, element, direction);
                this._container = container;
            },
            prepare: function (start, end) {
                var that = this, reverse = that._reverse, direction = reverse ? directions[that._direction].reverse : that._direction, rotation = ROTATIONS[direction];
                start.zIndex = 1;
                if (that._clipInHalf) {
                    start.clip = clipInHalf(that._container, kendo.directions[direction].reverse);
                }
                start[BACKFACE] = HIDDEN;
                end[TRANSFORM] = TRANSFORM_PERSPECTIVE + (reverse ? rotation.start : rotation.end);
                start[TRANSFORM] = TRANSFORM_PERSPECTIVE + (reverse ? rotation.end : rotation.start);
            },
            setup: function () {
                this._container.append(this.element);
            },
            face: function (value) {
                this._face = value;
                return this;
            },
            shouldHide: function () {
                var that = this, reverse = that._reverse, face = that._face;
                return reverse && !face || !reverse && face;
            },
            clipInHalf: function (value) {
                this._clipInHalf = value;
                return this;
            },
            temporary: function () {
                this.element.addClass('temp-page');
                return this;
            }
        });
        createEffect('staticPage', {
            directions: FOUR_DIRECTIONS,
            init: function (element, direction, container) {
                Effect.prototype.init.call(this, element, direction);
                this._container = container;
            },
            restore: ['clip'],
            prepare: function (start, end) {
                var that = this, direction = that._reverse ? directions[that._direction].reverse : that._direction;
                start.clip = clipInHalf(that._container, direction);
                start.opacity = 0.999;
                end.opacity = 1;
            },
            shouldHide: function () {
                var that = this, reverse = that._reverse, face = that._face;
                return reverse && !face || !reverse && face;
            },
            face: function (value) {
                this._face = value;
                return this;
            }
        });
        createEffect('pageturn', {
            directions: [
                'horizontal',
                'vertical'
            ],
            init: function (element, direction, face, back) {
                Effect.prototype.init.call(this, element, direction);
                this.options = {};
                this.options.face = face;
                this.options.back = back;
            },
            children: function () {
                var that = this, options = that.options, direction = that._direction === 'horizontal' ? 'left' : 'top', reverseDirection = kendo.directions[direction].reverse, reverse = that._reverse, temp, faceClone = options.face.clone(true).removeAttr('id'), backClone = options.back.clone(true).removeAttr('id'), element = that.element;
                if (reverse) {
                    temp = direction;
                    direction = reverseDirection;
                    reverseDirection = temp;
                }
                return [
                    kendo.fx(options.face).staticPage(direction, element).face(true).setReverse(reverse),
                    kendo.fx(options.back).staticPage(reverseDirection, element).setReverse(reverse),
                    kendo.fx(faceClone).turningPage(direction, element).face(true).clipInHalf(true).temporary().setReverse(reverse),
                    kendo.fx(backClone).turningPage(reverseDirection, element).clipInHalf(true).temporary().setReverse(reverse)
                ];
            },
            prepare: function (start, end) {
                start[PERSPECTIVE] = DEFAULT_PERSPECTIVE;
                start.transformStyle = 'preserve-3d';
                start.opacity = 0.999;
                end.opacity = 1;
            },
            teardown: function () {
                this.element.find('.temp-page').remove();
            }
        });
        createEffect('flip', {
            directions: [
                'horizontal',
                'vertical'
            ],
            init: function (element, direction, face, back) {
                Effect.prototype.init.call(this, element, direction);
                this.options = {};
                this.options.face = face;
                this.options.back = back;
            },
            children: function () {
                var that = this, options = that.options, direction = that._direction === 'horizontal' ? 'left' : 'top', reverseDirection = kendo.directions[direction].reverse, reverse = that._reverse, temp, element = that.element;
                if (reverse) {
                    temp = direction;
                    direction = reverseDirection;
                    reverseDirection = temp;
                }
                return [
                    kendo.fx(options.face).turningPage(direction, element).face(true).setReverse(reverse),
                    kendo.fx(options.back).turningPage(reverseDirection, element).setReverse(reverse)
                ];
            },
            prepare: function (start) {
                start[PERSPECTIVE] = DEFAULT_PERSPECTIVE;
                start.transformStyle = 'preserve-3d';
            }
        });
        var RESTORE_OVERFLOW = !support.mobileOS.android;
        var IGNORE_TRANSITION_EVENT_SELECTOR = '.km-touch-scrollbar, .km-actionsheet-wrapper';
        createEffect('replace', {
            _before: $.noop,
            _after: $.noop,
            init: function (element, previous, transitionClass) {
                Effect.prototype.init.call(this, element);
                this._previous = $(previous);
                this._transitionClass = transitionClass;
            },
            duration: function () {
                throw new Error('The replace effect does not support duration setting; the effect duration may be customized through the transition class rule');
            },
            beforeTransition: function (callback) {
                this._before = callback;
                return this;
            },
            afterTransition: function (callback) {
                this._after = callback;
                return this;
            },
            _both: function () {
                return $().add(this._element).add(this._previous);
            },
            _containerClass: function () {
                var direction = this._direction, containerClass = 'k-fx k-fx-start k-fx-' + this._transitionClass;
                if (direction) {
                    containerClass += ' k-fx-' + direction;
                }
                if (this._reverse) {
                    containerClass += ' k-fx-reverse';
                }
                return containerClass;
            },
            complete: function (e) {
                if (!this.deferred || e && $(e.target).is(IGNORE_TRANSITION_EVENT_SELECTOR)) {
                    return;
                }
                var container = this.container;
                container.removeClass('k-fx-end').removeClass(this._containerClass()).off(transitions.event, this.completeProxy);
                this._previous.hide().removeClass('k-fx-current');
                this.element.removeClass('k-fx-next');
                if (RESTORE_OVERFLOW) {
                    container.css(OVERFLOW, '');
                }
                if (!this.isAbsolute) {
                    this._both().css(POSITION, '');
                }
                this.deferred.resolve();
                delete this.deferred;
            },
            run: function () {
                if (this._additionalEffects && this._additionalEffects[0]) {
                    return this.compositeRun();
                }
                var that = this, element = that.element, previous = that._previous, container = element.parents().filter(previous.parents()).first(), both = that._both(), deferred = $.Deferred(), originalPosition = element.css(POSITION), originalOverflow;
                if (!container.length) {
                    container = element.parent();
                }
                this.container = container;
                this.deferred = deferred;
                this.isAbsolute = originalPosition == 'absolute';
                if (!this.isAbsolute) {
                    both.css(POSITION, 'absolute');
                }
                if (RESTORE_OVERFLOW) {
                    originalOverflow = container.css(OVERFLOW);
                    container.css(OVERFLOW, 'hidden');
                }
                if (!transitions) {
                    this.complete();
                } else {
                    element.addClass('k-fx-hidden');
                    container.addClass(this._containerClass());
                    this.completeProxy = $.proxy(this, 'complete');
                    container.on(transitions.event, this.completeProxy);
                    kendo.animationFrame(function () {
                        element.removeClass('k-fx-hidden').addClass('k-fx-next');
                        previous.css('display', '').addClass('k-fx-current');
                        that._before(previous, element);
                        kendo.animationFrame(function () {
                            container.removeClass('k-fx-start').addClass('k-fx-end');
                            that._after(previous, element);
                        });
                    });
                }
                return deferred.promise();
            },
            stop: function () {
                this.complete();
            }
        });
        var Animation = kendo.Class.extend({
            init: function () {
                var that = this;
                that._tickProxy = proxy(that._tick, that);
                that._started = false;
            },
            tick: $.noop,
            done: $.noop,
            onEnd: $.noop,
            onCancel: $.noop,
            start: function () {
                if (!this.enabled()) {
                    return;
                }
                if (!this.done()) {
                    this._started = true;
                    kendo.animationFrame(this._tickProxy);
                } else {
                    this.onEnd();
                }
            },
            enabled: function () {
                return true;
            },
            cancel: function () {
                this._started = false;
                this.onCancel();
            },
            _tick: function () {
                var that = this;
                if (!that._started) {
                    return;
                }
                that.tick();
                if (!that.done()) {
                    kendo.animationFrame(that._tickProxy);
                } else {
                    that._started = false;
                    that.onEnd();
                }
            }
        });
        var Transition = Animation.extend({
            init: function (options) {
                var that = this;
                extend(that, options);
                Animation.fn.init.call(that);
            },
            done: function () {
                return this.timePassed() >= this.duration;
            },
            timePassed: function () {
                return Math.min(this.duration, new Date() - this.startDate);
            },
            moveTo: function (options) {
                var that = this, movable = that.movable;
                that.initial = movable[that.axis];
                that.delta = options.location - that.initial;
                that.duration = typeof options.duration == 'number' ? options.duration : 300;
                that.tick = that._easeProxy(options.ease);
                that.startDate = new Date();
                that.start();
            },
            _easeProxy: function (ease) {
                var that = this;
                return function () {
                    that.movable.moveAxis(that.axis, ease(that.timePassed(), that.initial, that.delta, that.duration));
                };
            }
        });
        extend(Transition, {
            easeOutExpo: function (t, b, c, d) {
                return t == d ? b + c : c * (-Math.pow(2, -10 * t / d) + 1) + b;
            },
            easeOutBack: function (t, b, c, d, s) {
                s = 1.70158;
                return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
            }
        });
        fx.Animation = Animation;
        fx.Transition = Transition;
        fx.createEffect = createEffect;
        fx.box = function (element) {
            element = $(element);
            var result = element.offset();
            result.width = kendo._outerWidth(element);
            result.height = kendo._outerHeight(element);
            return result;
        };
        fx.transformOrigin = function (inner, outer) {
            var x = (inner.left - outer.left) * outer.width / (outer.width - inner.width), y = (inner.top - outer.top) * outer.height / (outer.height - inner.height);
            return {
                x: isNaN(x) ? 0 : x,
                y: isNaN(y) ? 0 : y
            };
        };
        fx.fillScale = function (inner, outer) {
            return Math.min(inner.width / outer.width, inner.height / outer.height);
        };
        fx.fitScale = function (inner, outer) {
            return Math.max(inner.width / outer.width, inner.height / outer.height);
        };
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.userevents', ['kendo.core'], f);
}(function () {
    var __meta__ = {
        id: 'userevents',
        name: 'User Events',
        category: 'framework',
        depends: ['core'],
        hidden: true
    };
    (function ($, undefined) {
        var kendo = window.kendo, support = kendo.support, Class = kendo.Class, Observable = kendo.Observable, now = $.now, extend = $.extend, OS = support.mobileOS, invalidZeroEvents = OS && OS.android, DEFAULT_MIN_HOLD = 800, DEFAULT_THRESHOLD = support.browser.msie ? 5 : 0, PRESS = 'press', HOLD = 'hold', SELECT = 'select', START = 'start', MOVE = 'move', END = 'end', CANCEL = 'cancel', TAP = 'tap', RELEASE = 'release', GESTURESTART = 'gesturestart', GESTURECHANGE = 'gesturechange', GESTUREEND = 'gestureend', GESTURETAP = 'gesturetap';
        var THRESHOLD = {
            'api': 0,
            'touch': 0,
            'mouse': 9,
            'pointer': 9
        };
        var ENABLE_GLOBAL_SURFACE = !support.touch || support.mouseAndTouchPresent;
        function touchDelta(touch1, touch2) {
            var x1 = touch1.x.location, y1 = touch1.y.location, x2 = touch2.x.location, y2 = touch2.y.location, dx = x1 - x2, dy = y1 - y2;
            return {
                center: {
                    x: (x1 + x2) / 2,
                    y: (y1 + y2) / 2
                },
                distance: Math.sqrt(dx * dx + dy * dy)
            };
        }
        function getTouches(e) {
            var touches = [], originalEvent = e.originalEvent, currentTarget = e.currentTarget, idx = 0, length, changedTouches, touch;
            if (e.api) {
                touches.push({
                    id: 2,
                    event: e,
                    target: e.target,
                    currentTarget: e.target,
                    location: e,
                    type: 'api'
                });
            } else if (e.type.match(/touch/)) {
                changedTouches = originalEvent ? originalEvent.changedTouches : [];
                for (length = changedTouches.length; idx < length; idx++) {
                    touch = changedTouches[idx];
                    touches.push({
                        location: touch,
                        event: e,
                        target: touch.target,
                        currentTarget: currentTarget,
                        id: touch.identifier,
                        type: 'touch'
                    });
                }
            } else if (support.pointers || support.msPointers) {
                touches.push({
                    location: originalEvent,
                    event: e,
                    target: e.target,
                    currentTarget: currentTarget,
                    id: originalEvent.pointerId,
                    type: 'pointer'
                });
            } else {
                touches.push({
                    id: 1,
                    event: e,
                    target: e.target,
                    currentTarget: currentTarget,
                    location: e,
                    type: 'mouse'
                });
            }
            return touches;
        }
        var TouchAxis = Class.extend({
            init: function (axis, location) {
                var that = this;
                that.axis = axis;
                that._updateLocationData(location);
                that.startLocation = that.location;
                that.velocity = that.delta = 0;
                that.timeStamp = now();
            },
            move: function (location) {
                var that = this, offset = location['page' + that.axis], timeStamp = now(), timeDelta = timeStamp - that.timeStamp || 1;
                if (!offset && invalidZeroEvents) {
                    return;
                }
                that.delta = offset - that.location;
                that._updateLocationData(location);
                that.initialDelta = offset - that.startLocation;
                that.velocity = that.delta / timeDelta;
                that.timeStamp = timeStamp;
            },
            _updateLocationData: function (location) {
                var that = this, axis = that.axis;
                that.location = location['page' + axis];
                that.client = location['client' + axis];
                that.screen = location['screen' + axis];
            }
        });
        var Touch = Class.extend({
            init: function (userEvents, target, touchInfo) {
                extend(this, {
                    x: new TouchAxis('X', touchInfo.location),
                    y: new TouchAxis('Y', touchInfo.location),
                    type: touchInfo.type,
                    useClickAsTap: userEvents.useClickAsTap,
                    threshold: userEvents.threshold || THRESHOLD[touchInfo.type],
                    userEvents: userEvents,
                    target: target,
                    currentTarget: touchInfo.currentTarget,
                    initialTouch: touchInfo.target,
                    id: touchInfo.id,
                    pressEvent: touchInfo,
                    _moved: false,
                    _finished: false
                });
            },
            press: function () {
                this._holdTimeout = setTimeout($.proxy(this, '_hold'), this.userEvents.minHold);
                this._trigger(PRESS, this.pressEvent);
            },
            _hold: function () {
                this._trigger(HOLD, this.pressEvent);
            },
            move: function (touchInfo) {
                var that = this;
                if (that._finished) {
                    return;
                }
                that.x.move(touchInfo.location);
                that.y.move(touchInfo.location);
                if (!that._moved) {
                    if (that._withinIgnoreThreshold()) {
                        return;
                    }
                    if (!UserEvents.current || UserEvents.current === that.userEvents) {
                        that._start(touchInfo);
                    } else {
                        return that.dispose();
                    }
                }
                if (!that._finished) {
                    that._trigger(MOVE, touchInfo);
                }
            },
            end: function (touchInfo) {
                this.endTime = now();
                if (this._finished) {
                    return;
                }
                this._finished = true;
                this._trigger(RELEASE, touchInfo);
                if (this._moved) {
                    this._trigger(END, touchInfo);
                } else {
                    if (!this.useClickAsTap) {
                        this._trigger(TAP, touchInfo);
                    }
                }
                clearTimeout(this._holdTimeout);
                this.dispose();
            },
            dispose: function () {
                var userEvents = this.userEvents, activeTouches = userEvents.touches;
                this._finished = true;
                this.pressEvent = null;
                clearTimeout(this._holdTimeout);
                activeTouches.splice($.inArray(this, activeTouches), 1);
            },
            skip: function () {
                this.dispose();
            },
            cancel: function () {
                this.dispose();
            },
            isMoved: function () {
                return this._moved;
            },
            _start: function (touchInfo) {
                clearTimeout(this._holdTimeout);
                this.startTime = now();
                this._moved = true;
                this._trigger(START, touchInfo);
            },
            _trigger: function (name, touchInfo) {
                var that = this, jQueryEvent = touchInfo.event, data = {
                        touch: that,
                        x: that.x,
                        y: that.y,
                        target: that.target,
                        event: jQueryEvent
                    };
                if (that.userEvents.notify(name, data)) {
                    jQueryEvent.preventDefault();
                }
            },
            _withinIgnoreThreshold: function () {
                var xDelta = this.x.initialDelta, yDelta = this.y.initialDelta;
                return Math.sqrt(xDelta * xDelta + yDelta * yDelta) <= this.threshold;
            }
        });
        function withEachUpEvent(callback) {
            var downEvents = kendo.eventMap.up.split(' '), idx = 0, length = downEvents.length;
            for (; idx < length; idx++) {
                callback(downEvents[idx]);
            }
        }
        var UserEvents = Observable.extend({
            init: function (element, options) {
                var that = this, filter, ns = kendo.guid();
                options = options || {};
                filter = that.filter = options.filter;
                that.threshold = options.threshold || DEFAULT_THRESHOLD;
                that.minHold = options.minHold || DEFAULT_MIN_HOLD;
                that.touches = [];
                that._maxTouches = options.multiTouch ? 2 : 1;
                that.allowSelection = options.allowSelection;
                that.captureUpIfMoved = options.captureUpIfMoved;
                that.useClickAsTap = !options.fastTap && !support.delayedClick();
                that.eventNS = ns;
                element = $(element).handler(that);
                Observable.fn.init.call(that);
                extend(that, {
                    element: element,
                    surface: options.global && ENABLE_GLOBAL_SURFACE ? $(element[0].ownerDocument.documentElement) : $(options.surface || element),
                    stopPropagation: options.stopPropagation,
                    pressed: false
                });
                that.surface.handler(that).on(kendo.applyEventMap('move', ns), '_move').on(kendo.applyEventMap('up cancel', ns), '_end');
                element.on(kendo.applyEventMap('down', ns), filter, '_start');
                if (that.useClickAsTap) {
                    element.on(kendo.applyEventMap('click', ns), filter, '_click');
                }
                if (support.pointers || support.msPointers) {
                    if (support.browser.version < 11) {
                        element.css('-ms-touch-action', 'pinch-zoom double-tap-zoom');
                    } else {
                        element.css('touch-action', options.touchAction || 'none');
                    }
                }
                if (options.preventDragEvent) {
                    element.on(kendo.applyEventMap('dragstart', ns), kendo.preventDefault);
                }
                element.on(kendo.applyEventMap('mousedown', ns), filter, { root: element }, '_select');
                if (that.captureUpIfMoved && support.eventCapture) {
                    var surfaceElement = that.surface[0], preventIfMovingProxy = $.proxy(that.preventIfMoving, that);
                    withEachUpEvent(function (eventName) {
                        surfaceElement.addEventListener(eventName, preventIfMovingProxy, true);
                    });
                }
                that.bind([
                    PRESS,
                    HOLD,
                    TAP,
                    START,
                    MOVE,
                    END,
                    RELEASE,
                    CANCEL,
                    GESTURESTART,
                    GESTURECHANGE,
                    GESTUREEND,
                    GESTURETAP,
                    SELECT
                ], options);
            },
            preventIfMoving: function (e) {
                if (this._isMoved()) {
                    e.preventDefault();
                }
            },
            destroy: function () {
                var that = this;
                if (that._destroyed) {
                    return;
                }
                that._destroyed = true;
                if (that.captureUpIfMoved && support.eventCapture) {
                    var surfaceElement = that.surface[0];
                    withEachUpEvent(function (eventName) {
                        surfaceElement.removeEventListener(eventName, that.preventIfMoving);
                    });
                }
                that.element.kendoDestroy(that.eventNS);
                that.surface.kendoDestroy(that.eventNS);
                that.element.removeData('handler');
                that.surface.removeData('handler');
                that._disposeAll();
                that.unbind();
                delete that.surface;
                delete that.element;
                delete that.currentTarget;
            },
            capture: function () {
                UserEvents.current = this;
            },
            cancel: function () {
                this._disposeAll();
                this.trigger(CANCEL);
            },
            notify: function (eventName, data) {
                var that = this, touches = that.touches;
                if (this._isMultiTouch()) {
                    switch (eventName) {
                    case MOVE:
                        eventName = GESTURECHANGE;
                        break;
                    case END:
                        eventName = GESTUREEND;
                        break;
                    case TAP:
                        eventName = GESTURETAP;
                        break;
                    }
                    extend(data, { touches: touches }, touchDelta(touches[0], touches[1]));
                }
                return this.trigger(eventName, extend(data, { type: eventName }));
            },
            press: function (x, y, target) {
                this._apiCall('_start', x, y, target);
            },
            move: function (x, y) {
                this._apiCall('_move', x, y);
            },
            end: function (x, y) {
                this._apiCall('_end', x, y);
            },
            _isMultiTouch: function () {
                return this.touches.length > 1;
            },
            _maxTouchesReached: function () {
                return this.touches.length >= this._maxTouches;
            },
            _disposeAll: function () {
                var touches = this.touches;
                while (touches.length > 0) {
                    touches.pop().dispose();
                }
            },
            _isMoved: function () {
                return $.grep(this.touches, function (touch) {
                    return touch.isMoved();
                }).length;
            },
            _select: function (e) {
                if (!this.allowSelection || this.trigger(SELECT, { event: e })) {
                    e.preventDefault();
                }
            },
            _start: function (e) {
                var that = this, idx = 0, filter = that.filter, target, touches = getTouches(e), length = touches.length, touch, which = e.which;
                if (which && which > 1 || that._maxTouchesReached()) {
                    return;
                }
                UserEvents.current = null;
                that.currentTarget = e.currentTarget;
                if (that.stopPropagation) {
                    e.stopPropagation();
                }
                for (; idx < length; idx++) {
                    if (that._maxTouchesReached()) {
                        break;
                    }
                    touch = touches[idx];
                    if (filter) {
                        target = $(touch.currentTarget);
                    } else {
                        target = that.element;
                    }
                    if (!target.length) {
                        continue;
                    }
                    touch = new Touch(that, target, touch);
                    that.touches.push(touch);
                    touch.press();
                    if (that._isMultiTouch()) {
                        that.notify('gesturestart', {});
                    }
                }
            },
            _move: function (e) {
                this._eachTouch('move', e);
            },
            _end: function (e) {
                this._eachTouch('end', e);
            },
            _click: function (e) {
                var data = {
                    touch: {
                        initialTouch: e.target,
                        target: $(e.currentTarget),
                        endTime: now(),
                        x: {
                            location: e.pageX,
                            client: e.clientX
                        },
                        y: {
                            location: e.pageY,
                            client: e.clientY
                        }
                    },
                    x: e.pageX,
                    y: e.pageY,
                    target: $(e.currentTarget),
                    event: e,
                    type: 'tap'
                };
                if (this.trigger('tap', data)) {
                    e.preventDefault();
                }
            },
            _eachTouch: function (methodName, e) {
                var that = this, dict = {}, touches = getTouches(e), activeTouches = that.touches, idx, touch, touchInfo, matchingTouch;
                for (idx = 0; idx < activeTouches.length; idx++) {
                    touch = activeTouches[idx];
                    dict[touch.id] = touch;
                }
                for (idx = 0; idx < touches.length; idx++) {
                    touchInfo = touches[idx];
                    matchingTouch = dict[touchInfo.id];
                    if (matchingTouch) {
                        matchingTouch[methodName](touchInfo);
                    }
                }
            },
            _apiCall: function (type, x, y, target) {
                this[type]({
                    api: true,
                    pageX: x,
                    pageY: y,
                    clientX: x,
                    clientY: y,
                    target: $(target || this.element)[0],
                    stopPropagation: $.noop,
                    preventDefault: $.noop
                });
            }
        });
        UserEvents.defaultThreshold = function (value) {
            DEFAULT_THRESHOLD = value;
        };
        UserEvents.minHold = function (value) {
            DEFAULT_MIN_HOLD = value;
        };
        kendo.getTouches = getTouches;
        kendo.touchDelta = touchDelta;
        kendo.UserEvents = UserEvents;
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.draganddrop', [
        'kendo.core',
        'kendo.userevents'
    ], f);
}(function () {
    var __meta__ = {
        id: 'draganddrop',
        name: 'Drag & drop',
        category: 'framework',
        description: 'Drag & drop functionality for any DOM element.',
        depends: [
            'core',
            'userevents'
        ]
    };
    (function ($, undefined) {
        var kendo = window.kendo, support = kendo.support, document = window.document, $window = $(window), Class = kendo.Class, Widget = kendo.ui.Widget, Observable = kendo.Observable, UserEvents = kendo.UserEvents, proxy = $.proxy, extend = $.extend, getOffset = kendo.getOffset, draggables = {}, dropTargets = {}, dropAreas = {}, lastDropTarget, elementUnderCursor = kendo.elementUnderCursor, KEYUP = 'keyup', CHANGE = 'change', DRAGSTART = 'dragstart', HOLD = 'hold', DRAG = 'drag', DRAGEND = 'dragend', DRAGCANCEL = 'dragcancel', HINTDESTROYED = 'hintDestroyed', DRAGENTER = 'dragenter', DRAGLEAVE = 'dragleave', DROP = 'drop';
        function contains(parent, child) {
            try {
                return $.contains(parent, child) || parent == child;
            } catch (e) {
                return false;
            }
        }
        function numericCssPropery(element, property) {
            return parseInt(element.css(property), 10) || 0;
        }
        function within(value, range) {
            return Math.min(Math.max(value, range.min), range.max);
        }
        function containerBoundaries(container, element) {
            var offset = getOffset(container), outerWidth = kendo._outerWidth, outerHeight = kendo._outerHeight, minX = offset.left + numericCssPropery(container, 'borderLeftWidth') + numericCssPropery(container, 'paddingLeft'), minY = offset.top + numericCssPropery(container, 'borderTopWidth') + numericCssPropery(container, 'paddingTop'), maxX = minX + container.width() - outerWidth(element, true), maxY = minY + container.height() - outerHeight(element, true);
            return {
                x: {
                    min: minX,
                    max: maxX
                },
                y: {
                    min: minY,
                    max: maxY
                }
            };
        }
        function checkTarget(target, targets, areas) {
            var theTarget, theFilter, i = 0, targetLen = targets && targets.length, areaLen = areas && areas.length;
            while (target && target.parentNode) {
                for (i = 0; i < targetLen; i++) {
                    theTarget = targets[i];
                    if (theTarget.element[0] === target) {
                        return {
                            target: theTarget,
                            targetElement: target
                        };
                    }
                }
                for (i = 0; i < areaLen; i++) {
                    theFilter = areas[i];
                    if ($.contains(theFilter.element[0], target) && support.matchesSelector.call(target, theFilter.options.filter)) {
                        return {
                            target: theFilter,
                            targetElement: target
                        };
                    }
                }
                target = target.parentNode;
            }
            return undefined;
        }
        var TapCapture = Observable.extend({
            init: function (element, options) {
                var that = this, domElement = element[0];
                that.capture = false;
                if (domElement.addEventListener) {
                    $.each(kendo.eventMap.down.split(' '), function () {
                        domElement.addEventListener(this, proxy(that._press, that), true);
                    });
                    $.each(kendo.eventMap.up.split(' '), function () {
                        domElement.addEventListener(this, proxy(that._release, that), true);
                    });
                } else {
                    $.each(kendo.eventMap.down.split(' '), function () {
                        domElement.attachEvent(this, proxy(that._press, that));
                    });
                    $.each(kendo.eventMap.up.split(' '), function () {
                        domElement.attachEvent(this, proxy(that._release, that));
                    });
                }
                Observable.fn.init.call(that);
                that.bind([
                    'press',
                    'release'
                ], options || {});
            },
            captureNext: function () {
                this.capture = true;
            },
            cancelCapture: function () {
                this.capture = false;
            },
            _press: function (e) {
                var that = this;
                that.trigger('press');
                if (that.capture) {
                    e.preventDefault();
                }
            },
            _release: function (e) {
                var that = this;
                that.trigger('release');
                if (that.capture) {
                    e.preventDefault();
                    that.cancelCapture();
                }
            }
        });
        var PaneDimension = Observable.extend({
            init: function (options) {
                var that = this;
                Observable.fn.init.call(that);
                that.forcedEnabled = false;
                $.extend(that, options);
                that.scale = 1;
                if (that.horizontal) {
                    that.measure = 'offsetWidth';
                    that.scrollSize = 'scrollWidth';
                    that.axis = 'x';
                } else {
                    that.measure = 'offsetHeight';
                    that.scrollSize = 'scrollHeight';
                    that.axis = 'y';
                }
            },
            makeVirtual: function () {
                $.extend(this, {
                    virtual: true,
                    forcedEnabled: true,
                    _virtualMin: 0,
                    _virtualMax: 0
                });
            },
            virtualSize: function (min, max) {
                if (this._virtualMin !== min || this._virtualMax !== max) {
                    this._virtualMin = min;
                    this._virtualMax = max;
                    this.update();
                }
            },
            outOfBounds: function (offset) {
                return offset > this.max || offset < this.min;
            },
            forceEnabled: function () {
                this.forcedEnabled = true;
            },
            getSize: function () {
                return this.container[0][this.measure];
            },
            getTotal: function () {
                return this.element[0][this.scrollSize];
            },
            rescale: function (scale) {
                this.scale = scale;
            },
            update: function (silent) {
                var that = this, total = that.virtual ? that._virtualMax : that.getTotal(), scaledTotal = total * that.scale, size = that.getSize();
                if (total === 0 && !that.forcedEnabled) {
                    return;
                }
                that.max = that.virtual ? -that._virtualMin : 0;
                that.size = size;
                that.total = scaledTotal;
                that.min = Math.min(that.max, size - scaledTotal);
                that.minScale = size / total;
                that.centerOffset = (scaledTotal - size) / 2;
                that.enabled = that.forcedEnabled || scaledTotal > size;
                if (!silent) {
                    that.trigger(CHANGE, that);
                }
            }
        });
        var PaneDimensions = Observable.extend({
            init: function (options) {
                var that = this;
                Observable.fn.init.call(that);
                that.x = new PaneDimension(extend({ horizontal: true }, options));
                that.y = new PaneDimension(extend({ horizontal: false }, options));
                that.container = options.container;
                that.forcedMinScale = options.minScale;
                that.maxScale = options.maxScale || 100;
                that.bind(CHANGE, options);
            },
            rescale: function (newScale) {
                this.x.rescale(newScale);
                this.y.rescale(newScale);
                this.refresh();
            },
            centerCoordinates: function () {
                return {
                    x: Math.min(0, -this.x.centerOffset),
                    y: Math.min(0, -this.y.centerOffset)
                };
            },
            refresh: function () {
                var that = this;
                that.x.update();
                that.y.update();
                that.enabled = that.x.enabled || that.y.enabled;
                that.minScale = that.forcedMinScale || Math.min(that.x.minScale, that.y.minScale);
                that.fitScale = Math.max(that.x.minScale, that.y.minScale);
                that.trigger(CHANGE);
            }
        });
        var PaneAxis = Observable.extend({
            init: function (options) {
                var that = this;
                extend(that, options);
                Observable.fn.init.call(that);
            },
            outOfBounds: function () {
                return this.dimension.outOfBounds(this.movable[this.axis]);
            },
            dragMove: function (delta) {
                var that = this, dimension = that.dimension, axis = that.axis, movable = that.movable, position = movable[axis] + delta;
                if (!dimension.enabled) {
                    return;
                }
                if (position < dimension.min && delta < 0 || position > dimension.max && delta > 0) {
                    delta *= that.resistance;
                }
                movable.translateAxis(axis, delta);
                that.trigger(CHANGE, that);
            }
        });
        var Pane = Class.extend({
            init: function (options) {
                var that = this, x, y, resistance, movable;
                extend(that, { elastic: true }, options);
                resistance = that.elastic ? 0.5 : 0;
                movable = that.movable;
                that.x = x = new PaneAxis({
                    axis: 'x',
                    dimension: that.dimensions.x,
                    resistance: resistance,
                    movable: movable
                });
                that.y = y = new PaneAxis({
                    axis: 'y',
                    dimension: that.dimensions.y,
                    resistance: resistance,
                    movable: movable
                });
                that.userEvents.bind([
                    'press',
                    'move',
                    'end',
                    'gesturestart',
                    'gesturechange'
                ], {
                    gesturestart: function (e) {
                        that.gesture = e;
                        that.offset = that.dimensions.container.offset();
                    },
                    press: function (e) {
                        if ($(e.event.target).closest('a').is('[data-navigate-on-press=true]')) {
                            e.sender.cancel();
                        }
                    },
                    gesturechange: function (e) {
                        var previousGesture = that.gesture, previousCenter = previousGesture.center, center = e.center, scaleDelta = e.distance / previousGesture.distance, minScale = that.dimensions.minScale, maxScale = that.dimensions.maxScale, coordinates;
                        if (movable.scale <= minScale && scaleDelta < 1) {
                            scaleDelta += (1 - scaleDelta) * 0.8;
                        }
                        if (movable.scale * scaleDelta >= maxScale) {
                            scaleDelta = maxScale / movable.scale;
                        }
                        var offsetX = movable.x + that.offset.left, offsetY = movable.y + that.offset.top;
                        coordinates = {
                            x: (offsetX - previousCenter.x) * scaleDelta + center.x - offsetX,
                            y: (offsetY - previousCenter.y) * scaleDelta + center.y - offsetY
                        };
                        movable.scaleWith(scaleDelta);
                        x.dragMove(coordinates.x);
                        y.dragMove(coordinates.y);
                        that.dimensions.rescale(movable.scale);
                        that.gesture = e;
                        e.preventDefault();
                    },
                    move: function (e) {
                        if (e.event.target.tagName.match(/textarea|input/i)) {
                            return;
                        }
                        if (x.dimension.enabled || y.dimension.enabled) {
                            x.dragMove(e.x.delta);
                            y.dragMove(e.y.delta);
                            e.preventDefault();
                        } else {
                            e.touch.skip();
                        }
                    },
                    end: function (e) {
                        e.preventDefault();
                    }
                });
            }
        });
        var TRANSFORM_STYLE = support.transitions.prefix + 'Transform', translate;
        if (support.hasHW3D) {
            translate = function (x, y, scale) {
                return 'translate3d(' + x + 'px,' + y + 'px,0) scale(' + scale + ')';
            };
        } else {
            translate = function (x, y, scale) {
                return 'translate(' + x + 'px,' + y + 'px) scale(' + scale + ')';
            };
        }
        var Movable = Observable.extend({
            init: function (element) {
                var that = this;
                Observable.fn.init.call(that);
                that.element = $(element);
                that.element[0].style.webkitTransformOrigin = 'left top';
                that.x = 0;
                that.y = 0;
                that.scale = 1;
                that._saveCoordinates(translate(that.x, that.y, that.scale));
            },
            translateAxis: function (axis, by) {
                this[axis] += by;
                this.refresh();
            },
            scaleTo: function (scale) {
                this.scale = scale;
                this.refresh();
            },
            scaleWith: function (scaleDelta) {
                this.scale *= scaleDelta;
                this.refresh();
            },
            translate: function (coordinates) {
                this.x += coordinates.x;
                this.y += coordinates.y;
                this.refresh();
            },
            moveAxis: function (axis, value) {
                this[axis] = value;
                this.refresh();
            },
            moveTo: function (coordinates) {
                extend(this, coordinates);
                this.refresh();
            },
            refresh: function () {
                var that = this, x = that.x, y = that.y, newCoordinates;
                if (that.round) {
                    x = Math.round(x);
                    y = Math.round(y);
                }
                newCoordinates = translate(x, y, that.scale);
                if (newCoordinates != that.coordinates) {
                    if (kendo.support.browser.msie && kendo.support.browser.version < 10) {
                        that.element[0].style.position = 'absolute';
                        that.element[0].style.left = that.x + 'px';
                        that.element[0].style.top = that.y + 'px';
                    } else {
                        that.element[0].style[TRANSFORM_STYLE] = newCoordinates;
                    }
                    that._saveCoordinates(newCoordinates);
                    that.trigger(CHANGE);
                }
            },
            _saveCoordinates: function (coordinates) {
                this.coordinates = coordinates;
            }
        });
        function destroyDroppable(collection, widget) {
            var groupName = widget.options.group, droppables = collection[groupName], i;
            Widget.fn.destroy.call(widget);
            if (droppables.length > 1) {
                for (i = 0; i < droppables.length; i++) {
                    if (droppables[i] == widget) {
                        droppables.splice(i, 1);
                        break;
                    }
                }
            } else {
                droppables.length = 0;
                delete collection[groupName];
            }
        }
        var DropTarget = Widget.extend({
            init: function (element, options) {
                var that = this;
                Widget.fn.init.call(that, element, options);
                var group = that.options.group;
                if (!(group in dropTargets)) {
                    dropTargets[group] = [that];
                } else {
                    dropTargets[group].push(that);
                }
            },
            events: [
                DRAGENTER,
                DRAGLEAVE,
                DROP
            ],
            options: {
                name: 'DropTarget',
                group: 'default'
            },
            destroy: function () {
                destroyDroppable(dropTargets, this);
            },
            _trigger: function (eventName, e) {
                var that = this, draggable = draggables[that.options.group];
                if (draggable) {
                    return that.trigger(eventName, extend({}, e.event, {
                        draggable: draggable,
                        dropTarget: e.dropTarget
                    }));
                }
            },
            _over: function (e) {
                this._trigger(DRAGENTER, e);
            },
            _out: function (e) {
                this._trigger(DRAGLEAVE, e);
            },
            _drop: function (e) {
                var that = this, draggable = draggables[that.options.group];
                if (draggable) {
                    draggable.dropped = !that._trigger(DROP, e);
                }
            }
        });
        DropTarget.destroyGroup = function (groupName) {
            var group = dropTargets[groupName] || dropAreas[groupName], i;
            if (group) {
                for (i = 0; i < group.length; i++) {
                    Widget.fn.destroy.call(group[i]);
                }
                group.length = 0;
                delete dropTargets[groupName];
                delete dropAreas[groupName];
            }
        };
        DropTarget._cache = dropTargets;
        var DropTargetArea = DropTarget.extend({
            init: function (element, options) {
                var that = this;
                Widget.fn.init.call(that, element, options);
                var group = that.options.group;
                if (!(group in dropAreas)) {
                    dropAreas[group] = [that];
                } else {
                    dropAreas[group].push(that);
                }
            },
            destroy: function () {
                destroyDroppable(dropAreas, this);
            },
            options: {
                name: 'DropTargetArea',
                group: 'default',
                filter: null
            }
        });
        var Draggable = Widget.extend({
            init: function (element, options) {
                var that = this;
                Widget.fn.init.call(that, element, options);
                that._activated = false;
                that.userEvents = new UserEvents(that.element, {
                    global: true,
                    allowSelection: true,
                    filter: that.options.filter,
                    threshold: that.options.distance,
                    start: proxy(that._start, that),
                    hold: proxy(that._hold, that),
                    move: proxy(that._drag, that),
                    end: proxy(that._end, that),
                    cancel: proxy(that._cancel, that),
                    select: proxy(that._select, that)
                });
                that._afterEndHandler = proxy(that._afterEnd, that);
                that._captureEscape = proxy(that._captureEscape, that);
            },
            events: [
                HOLD,
                DRAGSTART,
                DRAG,
                DRAGEND,
                DRAGCANCEL,
                HINTDESTROYED
            ],
            options: {
                name: 'Draggable',
                distance: kendo.support.touch ? 0 : 5,
                group: 'default',
                cursorOffset: null,
                axis: null,
                container: null,
                filter: null,
                ignore: null,
                holdToDrag: false,
                autoScroll: false,
                dropped: false
            },
            cancelHold: function () {
                this._activated = false;
            },
            _captureEscape: function (e) {
                var that = this;
                if (e.keyCode === kendo.keys.ESC) {
                    that._trigger(DRAGCANCEL, { event: e });
                    that.userEvents.cancel();
                }
            },
            _updateHint: function (e) {
                var that = this, coordinates, options = that.options, boundaries = that.boundaries, axis = options.axis, cursorOffset = that.options.cursorOffset;
                if (cursorOffset) {
                    coordinates = {
                        left: e.x.location + cursorOffset.left,
                        top: e.y.location + cursorOffset.top
                    };
                } else {
                    that.hintOffset.left += e.x.delta;
                    that.hintOffset.top += e.y.delta;
                    coordinates = $.extend({}, that.hintOffset);
                }
                if (boundaries) {
                    coordinates.top = within(coordinates.top, boundaries.y);
                    coordinates.left = within(coordinates.left, boundaries.x);
                }
                if (axis === 'x') {
                    delete coordinates.top;
                } else if (axis === 'y') {
                    delete coordinates.left;
                }
                that.hint.css(coordinates);
            },
            _shouldIgnoreTarget: function (target) {
                var ignoreSelector = this.options.ignore;
                return ignoreSelector && $(target).is(ignoreSelector);
            },
            _select: function (e) {
                if (!this._shouldIgnoreTarget(e.event.target)) {
                    e.preventDefault();
                }
            },
            _start: function (e) {
                var that = this, options = that.options, container = options.container, hint = options.hint;
                if (this._shouldIgnoreTarget(e.touch.initialTouch) || options.holdToDrag && !that._activated) {
                    that.userEvents.cancel();
                    return;
                }
                that.currentTarget = e.target;
                that.currentTargetOffset = getOffset(that.currentTarget);
                if (hint) {
                    if (that.hint) {
                        that.hint.stop(true, true).remove();
                    }
                    that.hint = kendo.isFunction(hint) ? $(hint.call(that, that.currentTarget)) : hint;
                    var offset = getOffset(that.currentTarget);
                    that.hintOffset = offset;
                    that.hint.css({
                        position: 'absolute',
                        zIndex: 20000,
                        left: offset.left,
                        top: offset.top
                    }).appendTo(document.body);
                    that.angular('compile', function () {
                        that.hint.removeAttr('ng-repeat');
                        var scopeTarget = $(e.target);
                        while (!scopeTarget.data('$$kendoScope') && scopeTarget.length) {
                            scopeTarget = scopeTarget.parent();
                        }
                        return {
                            elements: that.hint.get(),
                            scopeFrom: scopeTarget.data('$$kendoScope')
                        };
                    });
                }
                draggables[options.group] = that;
                that.dropped = false;
                if (container) {
                    that.boundaries = containerBoundaries(container, that.hint);
                }
                $(document).on(KEYUP, that._captureEscape);
                if (that._trigger(DRAGSTART, e)) {
                    that.userEvents.cancel();
                    that._afterEnd();
                }
                that.userEvents.capture();
            },
            _hold: function (e) {
                this.currentTarget = e.target;
                if (this._trigger(HOLD, e)) {
                    this.userEvents.cancel();
                } else {
                    this._activated = true;
                }
            },
            _drag: function (e) {
                e.preventDefault();
                var cursorElement = this._elementUnderCursor(e);
                if (this.options.autoScroll && this._cursorElement !== cursorElement) {
                    this._scrollableParent = findScrollableParent(cursorElement);
                    this._cursorElement = cursorElement;
                }
                this._lastEvent = e;
                this._processMovement(e, cursorElement);
                if (this.options.autoScroll) {
                    if (this._scrollableParent[0]) {
                        var velocity = autoScrollVelocity(e.x.location, e.y.location, scrollableViewPort(this._scrollableParent));
                        this._scrollCompenstation = $.extend({}, this.hintOffset);
                        this._scrollVelocity = velocity;
                        if (velocity.y === 0 && velocity.x === 0) {
                            clearInterval(this._scrollInterval);
                            this._scrollInterval = null;
                        } else if (!this._scrollInterval) {
                            this._scrollInterval = setInterval($.proxy(this, '_autoScroll'), 50);
                        }
                    }
                }
                if (this.hint) {
                    this._updateHint(e);
                }
            },
            _processMovement: function (e, cursorElement) {
                this._withDropTarget(cursorElement, function (target, targetElement) {
                    if (!target) {
                        if (lastDropTarget) {
                            lastDropTarget._trigger(DRAGLEAVE, extend(e, { dropTarget: $(lastDropTarget.targetElement) }));
                            lastDropTarget = null;
                        }
                        return;
                    }
                    if (lastDropTarget) {
                        if (targetElement === lastDropTarget.targetElement) {
                            return;
                        }
                        lastDropTarget._trigger(DRAGLEAVE, extend(e, { dropTarget: $(lastDropTarget.targetElement) }));
                    }
                    target._trigger(DRAGENTER, extend(e, { dropTarget: $(targetElement) }));
                    lastDropTarget = extend(target, { targetElement: targetElement });
                });
                this._trigger(DRAG, extend(e, {
                    dropTarget: lastDropTarget,
                    elementUnderCursor: cursorElement
                }));
            },
            _autoScroll: function () {
                var parent = this._scrollableParent[0], velocity = this._scrollVelocity, compensation = this._scrollCompenstation;
                if (!parent) {
                    return;
                }
                var cursorElement = this._elementUnderCursor(this._lastEvent);
                this._processMovement(this._lastEvent, cursorElement);
                var yIsScrollable, xIsScrollable;
                var isRootNode = parent === scrollableRoot()[0];
                if (isRootNode) {
                    yIsScrollable = document.body.scrollHeight > $window.height();
                    xIsScrollable = document.body.scrollWidth > $window.width();
                } else {
                    yIsScrollable = parent.offsetHeight <= parent.scrollHeight;
                    xIsScrollable = parent.offsetWidth <= parent.scrollWidth;
                }
                var yDelta = parent.scrollTop + velocity.y;
                var yInBounds = yIsScrollable && yDelta > 0 && yDelta < parent.scrollHeight;
                var xDelta = parent.scrollLeft + velocity.x;
                var xInBounds = xIsScrollable && xDelta > 0 && xDelta < parent.scrollWidth;
                if (yInBounds) {
                    parent.scrollTop += velocity.y;
                }
                if (xInBounds) {
                    parent.scrollLeft += velocity.x;
                }
                if (this.hint && isRootNode && (xInBounds || yInBounds)) {
                    if (yInBounds) {
                        compensation.top += velocity.y;
                    }
                    if (xInBounds) {
                        compensation.left += velocity.x;
                    }
                    this.hint.css(compensation);
                }
            },
            _end: function (e) {
                this._withDropTarget(this._elementUnderCursor(e), function (target, targetElement) {
                    if (target) {
                        target._drop(extend({}, e, { dropTarget: $(targetElement) }));
                        lastDropTarget = null;
                    }
                });
                this._cancel(this._trigger(DRAGEND, e));
            },
            _cancel: function (isDefaultPrevented) {
                var that = this;
                that._scrollableParent = null;
                this._cursorElement = null;
                clearInterval(this._scrollInterval);
                that._activated = false;
                if (that.hint && !that.dropped) {
                    setTimeout(function () {
                        that.hint.stop(true, true);
                        if (isDefaultPrevented) {
                            that._afterEndHandler();
                        } else {
                            that.hint.animate(that.currentTargetOffset, 'fast', that._afterEndHandler);
                        }
                    }, 0);
                } else {
                    that._afterEnd();
                }
            },
            _trigger: function (eventName, e) {
                var that = this;
                return that.trigger(eventName, extend({}, e.event, {
                    x: e.x,
                    y: e.y,
                    currentTarget: that.currentTarget,
                    initialTarget: e.touch ? e.touch.initialTouch : null,
                    dropTarget: e.dropTarget,
                    elementUnderCursor: e.elementUnderCursor
                }));
            },
            _elementUnderCursor: function (e) {
                var target = elementUnderCursor(e), hint = this.hint;
                if (hint && contains(hint[0], target)) {
                    hint.hide();
                    target = elementUnderCursor(e);
                    if (!target) {
                        target = elementUnderCursor(e);
                    }
                    hint.show();
                }
                return target;
            },
            _withDropTarget: function (element, callback) {
                var result, group = this.options.group, targets = dropTargets[group], areas = dropAreas[group];
                if (targets && targets.length || areas && areas.length) {
                    result = checkTarget(element, targets, areas);
                    if (result) {
                        callback(result.target, result.targetElement);
                    } else {
                        callback();
                    }
                }
            },
            destroy: function () {
                var that = this;
                Widget.fn.destroy.call(that);
                that._afterEnd();
                that.userEvents.destroy();
                this._scrollableParent = null;
                this._cursorElement = null;
                clearInterval(this._scrollInterval);
                that.currentTarget = null;
            },
            _afterEnd: function () {
                var that = this;
                if (that.hint) {
                    that.hint.remove();
                }
                delete draggables[that.options.group];
                that.trigger('destroy');
                that.trigger(HINTDESTROYED);
                $(document).off(KEYUP, that._captureEscape);
            }
        });
        kendo.ui.plugin(DropTarget);
        kendo.ui.plugin(DropTargetArea);
        kendo.ui.plugin(Draggable);
        kendo.TapCapture = TapCapture;
        kendo.containerBoundaries = containerBoundaries;
        extend(kendo.ui, {
            Pane: Pane,
            PaneDimensions: PaneDimensions,
            Movable: Movable
        });
        function scrollableViewPort(element) {
            var root = scrollableRoot()[0], offset, top, left;
            if (element[0] === root) {
                top = root.scrollTop;
                left = root.scrollLeft;
                return {
                    top: top,
                    left: left,
                    bottom: top + $window.height(),
                    right: left + $window.width()
                };
            } else {
                offset = element.offset();
                offset.bottom = offset.top + element.height();
                offset.right = offset.left + element.width();
                return offset;
            }
        }
        function scrollableRoot() {
            return $(kendo.support.browser.chrome ? document.body : document.documentElement);
        }
        function findScrollableParent(element) {
            var root = scrollableRoot();
            if (!element || element === document.body || element === document.documentElement) {
                return root;
            }
            var parent = $(element)[0];
            while (parent && !kendo.isScrollable(parent) && parent !== document.body) {
                parent = parent.parentNode;
            }
            if (parent === document.body) {
                return root;
            }
            return $(parent);
        }
        function autoScrollVelocity(mouseX, mouseY, rect) {
            var velocity = {
                x: 0,
                y: 0
            };
            var AUTO_SCROLL_AREA = 50;
            if (mouseX - rect.left < AUTO_SCROLL_AREA) {
                velocity.x = -(AUTO_SCROLL_AREA - (mouseX - rect.left));
            } else if (rect.right - mouseX < AUTO_SCROLL_AREA) {
                velocity.x = AUTO_SCROLL_AREA - (rect.right - mouseX);
            }
            if (mouseY - rect.top < AUTO_SCROLL_AREA) {
                velocity.y = -(AUTO_SCROLL_AREA - (mouseY - rect.top));
            } else if (rect.bottom - mouseY < AUTO_SCROLL_AREA) {
                velocity.y = AUTO_SCROLL_AREA - (rect.bottom - mouseY);
            }
            return velocity;
        }
        kendo.ui.Draggable.utils = {
            autoScrollVelocity: autoScrollVelocity,
            scrollableViewPort: scrollableViewPort,
            findScrollableParent: findScrollableParent
        };
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.resizable', [
        'kendo.core',
        'kendo.draganddrop'
    ], f);
}(function () {
    var __meta__ = {
        id: 'resizable',
        name: 'Resizable',
        category: 'framework',
        depends: [
            'core',
            'draganddrop'
        ],
        advanced: true
    };
    (function ($, undefined) {
        var kendo = window.kendo, ui = kendo.ui, Widget = ui.Widget, proxy = $.proxy, isFunction = kendo.isFunction, extend = $.extend, HORIZONTAL = 'horizontal', VERTICAL = 'vertical', START = 'start', RESIZE = 'resize', RESIZEEND = 'resizeend';
        var Resizable = Widget.extend({
            init: function (element, options) {
                var that = this;
                Widget.fn.init.call(that, element, options);
                that.orientation = that.options.orientation.toLowerCase() != VERTICAL ? HORIZONTAL : VERTICAL;
                that._positionMouse = that.orientation == HORIZONTAL ? 'x' : 'y';
                that._position = that.orientation == HORIZONTAL ? 'left' : 'top';
                that._sizingDom = that.orientation == HORIZONTAL ? 'outerWidth' : 'outerHeight';
                that.draggable = new ui.Draggable(options.draggableElement || element, {
                    distance: 1,
                    filter: options.handle,
                    drag: proxy(that._resize, that),
                    dragcancel: proxy(that._cancel, that),
                    dragstart: proxy(that._start, that),
                    dragend: proxy(that._stop, that)
                });
                that.userEvents = that.draggable.userEvents;
            },
            events: [
                RESIZE,
                RESIZEEND,
                START
            ],
            options: {
                name: 'Resizable',
                orientation: HORIZONTAL
            },
            resize: function () {
            },
            _max: function (e) {
                var that = this, hintSize = that.hint ? that.hint[that._sizingDom]() : 0, size = that.options.max;
                return isFunction(size) ? size(e) : size !== undefined ? that._initialElementPosition + size - hintSize : size;
            },
            _min: function (e) {
                var that = this, size = that.options.min;
                return isFunction(size) ? size(e) : size !== undefined ? that._initialElementPosition + size : size;
            },
            _start: function (e) {
                var that = this, hint = that.options.hint, el = $(e.currentTarget);
                that._initialElementPosition = el.position()[that._position];
                that._initialMousePosition = e[that._positionMouse].startLocation;
                if (hint) {
                    that.hint = isFunction(hint) ? $(hint(el)) : hint;
                    that.hint.css({ position: 'absolute' }).css(that._position, that._initialElementPosition).appendTo(that.element);
                }
                that.trigger(START, e);
                that._maxPosition = that._max(e);
                that._minPosition = that._min(e);
                $(document.body).css('cursor', el.css('cursor'));
            },
            _resize: function (e) {
                var that = this, maxPosition = that._maxPosition, minPosition = that._minPosition, currentPosition = that._initialElementPosition + (e[that._positionMouse].location - that._initialMousePosition), position;
                position = minPosition !== undefined ? Math.max(minPosition, currentPosition) : currentPosition;
                that.position = position = maxPosition !== undefined ? Math.min(maxPosition, position) : position;
                if (that.hint) {
                    that.hint.toggleClass(that.options.invalidClass || '', position == maxPosition || position == minPosition).css(that._position, position);
                }
                that.resizing = true;
                that.trigger(RESIZE, extend(e, { position: position }));
            },
            _stop: function (e) {
                var that = this;
                if (that.hint) {
                    that.hint.remove();
                }
                that.resizing = false;
                that.trigger(RESIZEEND, extend(e, { position: that.position }));
                $(document.body).css('cursor', '');
            },
            _cancel: function (e) {
                var that = this;
                if (that.hint) {
                    that.position = undefined;
                    that.hint.css(that._position, that._initialElementPosition);
                    that._stop(e);
                }
            },
            destroy: function () {
                var that = this;
                Widget.fn.destroy.call(that);
                if (that.draggable) {
                    that.draggable.destroy();
                }
            },
            press: function (target) {
                if (!target) {
                    return;
                }
                var position = target.position(), that = this;
                that.userEvents.press(position.left, position.top, target[0]);
                that.targetPosition = position;
                that.target = target;
            },
            move: function (delta) {
                var that = this, orientation = that._position, position = that.targetPosition, current = that.position;
                if (current === undefined) {
                    current = position[orientation];
                }
                position[orientation] = current + delta;
                that.userEvents.move(position.left, position.top);
            },
            end: function () {
                this.userEvents.end();
                this.target = this.position = undefined;
            }
        });
        kendo.ui.plugin(Resizable);
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.splitter', ['kendo.resizable'], f);
}(function () {
    var __meta__ = {
        id: 'splitter',
        name: 'Splitter',
        category: 'web',
        description: 'The Splitter widget provides an easy way to create a dynamic layout of resizable and collapsible panes.',
        depends: ['resizable']
    };
    (function ($, undefined) {
        var kendo = window.kendo, ui = kendo.ui, keys = kendo.keys, extend = $.extend, proxy = $.proxy, Widget = ui.Widget, pxUnitsRegex = /^\d+(\.\d+)?px$/i, percentageUnitsRegex = /^\d+(\.\d+)?%$/i, NS = '.kendoSplitter', EXPAND = 'expand', COLLAPSE = 'collapse', CONTENTLOAD = 'contentLoad', ERROR = 'error', RESIZE = 'resize', LAYOUTCHANGE = 'layoutChange', HORIZONTAL = 'horizontal', VERTICAL = 'vertical', MOUSEENTER = 'mouseenter', CLICK = 'click', PANE = 'pane', MOUSELEAVE = 'mouseleave', FOCUSED = 'k-state-focused', KPANE = 'k-' + PANE, PANECLASS = '.' + KPANE;
        function isPercentageSize(size) {
            return percentageUnitsRegex.test(size);
        }
        function isPixelSize(size) {
            return pxUnitsRegex.test(size) || /^\d+$/.test(size);
        }
        function isFluid(size) {
            return !isPercentageSize(size) && !isPixelSize(size);
        }
        function calculateSize(size, total) {
            var output = parseInt(size, 10);
            if (isPercentageSize(size)) {
                output = Math.floor(output * total / 100);
            }
            return output;
        }
        function panePropertyAccessor(propertyName, triggersResize) {
            return function (pane, value) {
                var paneConfig = this.element.find(pane).data(PANE);
                if (arguments.length == 1) {
                    return paneConfig[propertyName];
                }
                paneConfig[propertyName] = value;
                if (triggersResize) {
                    var splitter = this.element.data('kendo' + this.options.name);
                    splitter.resize(true);
                }
            };
        }
        var Splitter = Widget.extend({
            init: function (element, options) {
                var that = this, isHorizontal;
                Widget.fn.init.call(that, element, options);
                that.wrapper = that.element;
                isHorizontal = that.options.orientation.toLowerCase() != VERTICAL;
                that.orientation = isHorizontal ? HORIZONTAL : VERTICAL;
                that._dimension = isHorizontal ? 'width' : 'height';
                that._keys = {
                    decrease: isHorizontal ? keys.LEFT : keys.UP,
                    increase: isHorizontal ? keys.RIGHT : keys.DOWN
                };
                that._resizeStep = 10;
                that._marker = kendo.guid().substring(0, 8);
                that._initPanes();
                that.resizing = new PaneResizing(that);
                that.element.triggerHandler('init' + NS);
            },
            events: [
                EXPAND,
                COLLAPSE,
                CONTENTLOAD,
                ERROR,
                RESIZE,
                LAYOUTCHANGE
            ],
            _addOverlays: function () {
                this._panes().append('<div class=\'k-splitter-overlay k-overlay\' />');
            },
            _removeOverlays: function () {
                this._panes().children('.k-splitter-overlay').remove();
            },
            _attachEvents: function () {
                var that = this, orientation = that.options.orientation;
                that.element.children('.k-splitbar-draggable-' + orientation).on('keydown' + NS, proxy(that._keydown, that)).on('mousedown' + NS, function (e) {
                    e.currentTarget.focus();
                }).on('focus' + NS, function (e) {
                    $(e.currentTarget).addClass(FOCUSED);
                }).on('blur' + NS, function (e) {
                    $(e.currentTarget).removeClass(FOCUSED);
                    if (that.resizing) {
                        that.resizing.end();
                    }
                }).on(MOUSEENTER + NS, function () {
                    $(this).addClass('k-splitbar-' + that.orientation + '-hover');
                }).on(MOUSELEAVE + NS, function () {
                    $(this).removeClass('k-splitbar-' + that.orientation + '-hover');
                }).on('mousedown' + NS, proxy(that._addOverlays, that)).end().children('.k-splitbar').on('dblclick' + NS, proxy(that._togglePane, that)).children('.k-collapse-next, .k-collapse-prev').on(CLICK + NS, that._arrowClick(COLLAPSE)).end().children('.k-expand-next, .k-expand-prev').on(CLICK + NS, that._arrowClick(EXPAND)).end().end();
                $(window).on('resize' + NS + that._marker, proxy(that.resize, that, false));
                $(document).on('mouseup' + NS + that._marker, proxy(that._removeOverlays, that));
            },
            _detachEvents: function () {
                var that = this;
                that.element.children('.k-splitbar-draggable-' + that.orientation).off(NS).end().children('.k-splitbar').off('dblclick' + NS).children('.k-collapse-next, .k-collapse-prev, .k-expand-next, .k-expand-prev').off(NS);
                $(window).off(NS + that._marker);
                $(document).off(NS + that._marker);
            },
            options: {
                name: 'Splitter',
                orientation: HORIZONTAL,
                panes: []
            },
            destroy: function () {
                Widget.fn.destroy.call(this);
                this._detachEvents();
                if (this.resizing) {
                    this.resizing.destroy();
                }
                kendo.destroy(this.element);
                this.wrapper = this.element = null;
            },
            _keydown: function (e) {
                var that = this, key = e.keyCode, resizing = that.resizing, target = $(e.currentTarget), navigationKeys = that._keys, increase = key === navigationKeys.increase, decrease = key === navigationKeys.decrease, pane;
                if (increase || decrease) {
                    if (e.ctrlKey) {
                        pane = target[decrease ? 'next' : 'prev']();
                        if (resizing && resizing.isResizing()) {
                            resizing.end();
                        }
                        if (!pane[that._dimension]()) {
                            that._triggerAction(EXPAND, pane);
                        } else {
                            that._triggerAction(COLLAPSE, target[decrease ? 'prev' : 'next']());
                        }
                    } else if (resizing) {
                        resizing.move((decrease ? -1 : 1) * that._resizeStep, target);
                    }
                    e.preventDefault();
                } else if (key === keys.ENTER && resizing) {
                    resizing.end();
                    e.preventDefault();
                }
            },
            _initPanes: function () {
                var panesConfig = this.options.panes || [];
                var that = this;
                this.element.addClass('k-widget').addClass('k-splitter').children().each(function (i, pane) {
                    if (pane.nodeName.toLowerCase() != 'script') {
                        that._initPane(pane, panesConfig[i]);
                    }
                });
                this.resize();
            },
            _initPane: function (pane, config) {
                pane = $(pane).attr('role', 'group').addClass(KPANE);
                pane.data(PANE, config ? config : {}).toggleClass('k-scrollable', config ? config.scrollable !== false : true);
                this.ajaxRequest(pane);
            },
            ajaxRequest: function (pane, url, data) {
                var that = this, paneConfig;
                pane = that.element.find(pane);
                paneConfig = pane.data(PANE);
                url = url || paneConfig.contentUrl;
                if (url) {
                    pane.append('<span class=\'k-icon k-i-loading k-pane-loading\' />');
                    if (kendo.isLocalUrl(url)) {
                        jQuery.ajax({
                            url: url,
                            data: data || {},
                            type: 'GET',
                            dataType: 'html',
                            success: function (data) {
                                that.angular('cleanup', function () {
                                    return { elements: pane.get() };
                                });
                                pane.html(data);
                                that.angular('compile', function () {
                                    return { elements: pane.get() };
                                });
                                that.trigger(CONTENTLOAD, { pane: pane[0] });
                            },
                            error: function (xhr, status) {
                                that.trigger(ERROR, {
                                    pane: pane[0],
                                    status: status,
                                    xhr: xhr
                                });
                            }
                        });
                    } else {
                        pane.removeClass('k-scrollable').html('<iframe src=\'' + url + '\' frameborder=\'0\' class=\'k-content-frame\'>' + 'This page requires frames in order to show content' + '</iframe>');
                    }
                }
            },
            _triggerAction: function (type, pane) {
                if (!this.trigger(type, { pane: pane[0] })) {
                    this[type](pane[0]);
                }
            },
            _togglePane: function (e) {
                var that = this, target = $(e.target), arrow;
                if (target.closest('.k-splitter')[0] != that.element[0]) {
                    return;
                }
                arrow = target.children('.k-icon:not(.k-resize-handle)');
                if (arrow.length !== 1) {
                    return;
                }
                if (arrow.is('.k-collapse-prev')) {
                    that._triggerAction(COLLAPSE, target.prev());
                } else if (arrow.is('.k-collapse-next')) {
                    that._triggerAction(COLLAPSE, target.next());
                } else if (arrow.is('.k-expand-prev')) {
                    that._triggerAction(EXPAND, target.prev());
                } else if (arrow.is('.k-expand-next')) {
                    that._triggerAction(EXPAND, target.next());
                }
            },
            _arrowClick: function (arrowType) {
                var that = this;
                return function (e) {
                    var target = $(e.target), pane;
                    if (target.closest('.k-splitter')[0] != that.element[0]) {
                        return;
                    }
                    if (target.is('.k-' + arrowType + '-prev')) {
                        pane = target.parent().prev();
                    } else {
                        pane = target.parent().next();
                    }
                    that._triggerAction(arrowType, pane);
                };
            },
            _updateSplitBar: function (splitbar, previousPane, nextPane) {
                var catIconIf = function (iconType, condition) {
                        return condition ? '<div class=\'k-icon ' + iconType + '\' />' : '';
                    }, orientation = this.orientation, draggable = previousPane.resizable !== false && nextPane.resizable !== false, prevCollapsible = previousPane.collapsible, prevCollapsed = previousPane.collapsed, nextCollapsible = nextPane.collapsible, nextCollapsed = nextPane.collapsed;
                splitbar.addClass('k-splitbar k-state-default k-splitbar-' + orientation).attr('role', 'separator').attr('aria-expanded', !(prevCollapsed || nextCollapsed)).removeClass('k-splitbar-' + orientation + '-hover').toggleClass('k-splitbar-draggable-' + orientation, draggable && !prevCollapsed && !nextCollapsed).toggleClass('k-splitbar-static-' + orientation, !draggable && !prevCollapsible && !nextCollapsible).html(catIconIf('k-collapse-prev k-i-arrow-60-up', prevCollapsible && !prevCollapsed && !nextCollapsed && orientation == VERTICAL) + catIconIf('k-collapse-prev k-i-arrow-60-left', prevCollapsible && !prevCollapsed && !nextCollapsed && orientation == HORIZONTAL) + catIconIf('k-expand-prev k-i-arrow-60-down', prevCollapsible && prevCollapsed && !nextCollapsed && orientation == VERTICAL) + catIconIf('k-expand-prev k-i-arrow-60-right', prevCollapsible && prevCollapsed && !nextCollapsed && orientation == HORIZONTAL) + catIconIf('k-resize-handle k-i-hbar', draggable && orientation == VERTICAL) + catIconIf('k-resize-handle k-i-vbar', draggable && orientation == HORIZONTAL) + catIconIf('k-collapse-next k-i-arrow-60-down', nextCollapsible && !nextCollapsed && !prevCollapsed && orientation == VERTICAL) + catIconIf('k-collapse-next k-i-arrow-60-right', nextCollapsible && !nextCollapsed && !prevCollapsed && orientation == HORIZONTAL) + catIconIf('k-expand-next k-i-arrow-60-up', nextCollapsible && nextCollapsed && !prevCollapsed && orientation == VERTICAL) + catIconIf('k-expand-next k-i-arrow-60-left', nextCollapsible && nextCollapsed && !prevCollapsed && orientation == HORIZONTAL));
                if (!draggable && !prevCollapsible && !nextCollapsible) {
                    splitbar.removeAttr('tabindex');
                }
            },
            _updateSplitBars: function () {
                var that = this;
                this.element.children('.k-splitbar').each(function () {
                    var splitbar = $(this), previousPane = splitbar.prevAll(PANECLASS).first().data(PANE), nextPane = splitbar.nextAll(PANECLASS).first().data(PANE);
                    if (!nextPane) {
                        return;
                    }
                    that._updateSplitBar(splitbar, previousPane, nextPane);
                });
            },
            _removeSplitBars: function () {
                this.element.children('.k-splitbar').remove();
            },
            _panes: function () {
                if (!this.element) {
                    return $();
                }
                return this.element.children(PANECLASS);
            },
            _resize: function () {
                var that = this, element = that.element, panes = element.children(PANECLASS), isHorizontal = that.orientation == HORIZONTAL, splitBars = element.children('.k-splitbar'), splitBarsCount = splitBars.length, sizingProperty = isHorizontal ? 'width' : 'height', totalSize = element[sizingProperty]();
                that.wrapper.addClass('k-splitter-resizing');
                if (splitBarsCount === 0) {
                    splitBarsCount = panes.length - 1;
                    panes.slice(0, splitBarsCount).after('<div tabindex=\'0\' class=\'k-splitbar\' data-marker=\'' + that._marker + '\' />');
                    that._updateSplitBars();
                    splitBars = element.children('.k-splitbar');
                } else {
                    that._updateSplitBars();
                }
                splitBars.each(function () {
                    totalSize -= this[isHorizontal ? 'offsetWidth' : 'offsetHeight'];
                });
                var sizedPanesWidth = 0, sizedPanesCount = 0, freeSizedPanes = $();
                panes.css({
                    position: 'absolute',
                    top: 0
                })[sizingProperty](function () {
                    var element = $(this), config = element.data(PANE) || {}, size;
                    element.removeClass('k-state-collapsed');
                    if (config.collapsed) {
                        size = config.collapsedSize ? calculateSize(config.collapsedSize, totalSize) : 0;
                        element.css('overflow', 'hidden').addClass('k-state-collapsed');
                    } else if (isFluid(config.size)) {
                        freeSizedPanes = freeSizedPanes.add(this);
                        return;
                    } else {
                        size = calculateSize(config.size, totalSize);
                    }
                    sizedPanesCount++;
                    sizedPanesWidth += size;
                    return size;
                });
                totalSize -= sizedPanesWidth;
                var freeSizePanesCount = freeSizedPanes.length, freeSizePaneWidth = Math.floor(totalSize / freeSizePanesCount);
                freeSizedPanes.slice(0, freeSizePanesCount - 1).css(sizingProperty, freeSizePaneWidth).end().eq(freeSizePanesCount - 1).css(sizingProperty, totalSize - (freeSizePanesCount - 1) * freeSizePaneWidth);
                var sum = 0, alternateSizingProperty = isHorizontal ? 'height' : 'width', positioningProperty = isHorizontal ? 'left' : 'top', sizingDomProperty = isHorizontal ? 'offsetWidth' : 'offsetHeight';
                if (freeSizePanesCount === 0) {
                    var lastNonCollapsedPane = panes.filter(function () {
                        return !($(this).data(PANE) || {}).collapsed;
                    }).last();
                    lastNonCollapsedPane[sizingProperty](totalSize + lastNonCollapsedPane[0][sizingDomProperty]);
                }
                element.children().css(alternateSizingProperty, element[alternateSizingProperty]()).each(function (i, child) {
                    if (child.tagName.toLowerCase() != 'script') {
                        child.style[positioningProperty] = Math.floor(sum) + 'px';
                        sum += child[sizingDomProperty];
                    }
                });
                that._detachEvents();
                that._attachEvents();
                that.wrapper.removeClass('k-splitter-resizing');
                kendo.resize(panes);
                that.trigger(LAYOUTCHANGE);
            },
            toggle: function (pane, expand) {
                var that = this, paneConfig;
                pane = that.element.find(pane);
                paneConfig = pane.data(PANE);
                if (!expand && !paneConfig.collapsible) {
                    return;
                }
                if (arguments.length == 1) {
                    expand = paneConfig.collapsed === undefined ? false : paneConfig.collapsed;
                }
                paneConfig.collapsed = !expand;
                if (paneConfig.collapsed) {
                    pane.css('overflow', 'hidden');
                } else {
                    pane.css('overflow', '');
                }
                that.resize(true);
            },
            collapse: function (pane) {
                this.toggle(pane, false);
            },
            expand: function (pane) {
                this.toggle(pane, true);
            },
            _addPane: function (config, idx, paneElement) {
                var that = this;
                if (paneElement.length) {
                    that.options.panes.splice(idx, 0, config);
                    that._initPane(paneElement, config);
                    that._removeSplitBars();
                    that.resize(true);
                }
                return paneElement;
            },
            append: function (config) {
                config = config || {};
                var that = this, paneElement = $('<div />').appendTo(that.element);
                return that._addPane(config, that.options.panes.length, paneElement);
            },
            insertBefore: function (config, referencePane) {
                referencePane = $(referencePane);
                config = config || {};
                var that = this, idx = that.wrapper.children('.k-pane').index(referencePane), paneElement = $('<div />').insertBefore($(referencePane));
                return that._addPane(config, idx, paneElement);
            },
            insertAfter: function (config, referencePane) {
                referencePane = $(referencePane);
                config = config || {};
                var that = this, idx = that.wrapper.children('.k-pane').index(referencePane), paneElement = $('<div />').insertAfter($(referencePane));
                return that._addPane(config, idx + 1, paneElement);
            },
            remove: function (pane) {
                pane = $(pane);
                var that = this;
                if (pane.length) {
                    kendo.destroy(pane);
                    pane.each(function (idx, element) {
                        that.options.panes.splice(that.wrapper.children('.k-pane').index(element), 1);
                        $(element).remove();
                    });
                    that._removeSplitBars();
                    if (that.options.panes.length) {
                        that.resize(true);
                    }
                }
                return that;
            },
            size: panePropertyAccessor('size', true),
            min: panePropertyAccessor('min'),
            max: panePropertyAccessor('max')
        });
        ui.plugin(Splitter);
        var verticalDefaults = {
            sizingProperty: 'height',
            sizingDomProperty: 'offsetHeight',
            alternateSizingProperty: 'width',
            positioningProperty: 'top',
            mousePositioningProperty: 'pageY'
        };
        var horizontalDefaults = {
            sizingProperty: 'width',
            sizingDomProperty: 'offsetWidth',
            alternateSizingProperty: 'height',
            positioningProperty: 'left',
            mousePositioningProperty: 'pageX'
        };
        function PaneResizing(splitter) {
            var that = this, orientation = splitter.orientation;
            that.owner = splitter;
            that._element = splitter.element;
            that.orientation = orientation;
            extend(that, orientation === HORIZONTAL ? horizontalDefaults : verticalDefaults);
            that._resizable = new kendo.ui.Resizable(splitter.element, {
                orientation: orientation,
                handle: '.k-splitbar-draggable-' + orientation + '[data-marker=' + splitter._marker + ']',
                hint: proxy(that._createHint, that),
                start: proxy(that._start, that),
                max: proxy(that._max, that),
                min: proxy(that._min, that),
                invalidClass: 'k-restricted-size-' + orientation,
                resizeend: proxy(that._stop, that)
            });
        }
        PaneResizing.prototype = {
            press: function (target) {
                this._resizable.press(target);
            },
            move: function (delta, target) {
                if (!this.pressed) {
                    this.press(target);
                    this.pressed = true;
                }
                if (!this._resizable.target) {
                    this._resizable.press(target);
                }
                this._resizable.move(delta);
            },
            end: function () {
                this._resizable.end();
                this.pressed = false;
            },
            destroy: function () {
                this._resizable.destroy();
                this._resizable = this._element = this.owner = null;
            },
            isResizing: function () {
                return this._resizable.resizing;
            },
            _createHint: function (handle) {
                var that = this;
                return $('<div class=\'k-ghost-splitbar k-ghost-splitbar-' + that.orientation + ' k-state-default\' />').css(that.alternateSizingProperty, handle[that.alternateSizingProperty]());
            },
            _start: function (e) {
                var that = this, splitbar = $(e.currentTarget), previousPane = splitbar.prev(), nextPane = splitbar.next(), previousPaneConfig = previousPane.data(PANE), nextPaneConfig = nextPane.data(PANE), prevBoundary = parseInt(previousPane[0].style[that.positioningProperty], 10), nextBoundary = parseInt(nextPane[0].style[that.positioningProperty], 10) + nextPane[0][that.sizingDomProperty] - splitbar[0][that.sizingDomProperty], totalSize = parseInt(that._element.css(that.sizingProperty), 10), toPx = function (value) {
                        var val = parseInt(value, 10);
                        return (isPixelSize(value) ? val : totalSize * val / 100) || 0;
                    }, prevMinSize = toPx(previousPaneConfig.min), prevMaxSize = toPx(previousPaneConfig.max) || nextBoundary - prevBoundary, nextMinSize = toPx(nextPaneConfig.min), nextMaxSize = toPx(nextPaneConfig.max) || nextBoundary - prevBoundary;
                that.previousPane = previousPane;
                that.nextPane = nextPane;
                that._maxPosition = Math.min(nextBoundary - nextMinSize, prevBoundary + prevMaxSize);
                that._minPosition = Math.max(prevBoundary + prevMinSize, nextBoundary - nextMaxSize);
            },
            _max: function () {
                return this._maxPosition;
            },
            _min: function () {
                return this._minPosition;
            },
            _stop: function (e) {
                var that = this, splitbar = $(e.currentTarget), owner = that.owner;
                owner._panes().children('.k-splitter-overlay').remove();
                if (e.keyCode !== kendo.keys.ESC) {
                    var ghostPosition = e.position, previousPane = splitbar.prev(), nextPane = splitbar.next(), previousPaneConfig = previousPane.data(PANE), nextPaneConfig = nextPane.data(PANE), previousPaneNewSize = ghostPosition - parseInt(previousPane[0].style[that.positioningProperty], 10), nextPaneNewSize = parseInt(nextPane[0].style[that.positioningProperty], 10) + nextPane[0][that.sizingDomProperty] - ghostPosition - splitbar[0][that.sizingDomProperty], fluidPanesCount = that._element.children(PANECLASS).filter(function () {
                            return isFluid($(this).data(PANE).size);
                        }).length;
                    if (!isFluid(previousPaneConfig.size) || fluidPanesCount > 1) {
                        if (isFluid(previousPaneConfig.size)) {
                            fluidPanesCount--;
                        }
                        previousPaneConfig.size = previousPaneNewSize + 'px';
                    }
                    if (!isFluid(nextPaneConfig.size) || fluidPanesCount > 1) {
                        nextPaneConfig.size = nextPaneNewSize + 'px';
                    }
                    owner.resize(true);
                }
                return false;
            }
        };
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.popup', ['kendo.core'], f);
}(function () {
    var __meta__ = {
        id: 'popup',
        name: 'Pop-up',
        category: 'framework',
        depends: ['core'],
        advanced: true
    };
    (function ($, undefined) {
        var kendo = window.kendo, ui = kendo.ui, Widget = ui.Widget, Class = kendo.Class, support = kendo.support, getOffset = kendo.getOffset, outerWidth = kendo._outerWidth, outerHeight = kendo._outerHeight, OPEN = 'open', CLOSE = 'close', DEACTIVATE = 'deactivate', ACTIVATE = 'activate', CENTER = 'center', LEFT = 'left', RIGHT = 'right', TOP = 'top', BOTTOM = 'bottom', ABSOLUTE = 'absolute', HIDDEN = 'hidden', BODY = 'body', LOCATION = 'location', POSITION = 'position', VISIBLE = 'visible', EFFECTS = 'effects', ACTIVE = 'k-state-active', ACTIVEBORDER = 'k-state-border', ACTIVEBORDERREGEXP = /k-state-border-(\w+)/, ACTIVECHILDREN = '.k-picker-wrap, .k-dropdown-wrap, .k-link', MOUSEDOWN = 'down', DOCUMENT_ELEMENT = $(document.documentElement), proxy = $.proxy, WINDOW = $(window), SCROLL = 'scroll', cssPrefix = support.transitions.css, TRANSFORM = cssPrefix + 'transform', extend = $.extend, NS = '.kendoPopup', styles = [
                'font-size',
                'font-family',
                'font-stretch',
                'font-style',
                'font-weight',
                'line-height'
            ];
        function contains(container, target) {
            if (!container || !target) {
                return false;
            }
            return container === target || $.contains(container, target);
        }
        var Popup = Widget.extend({
            init: function (element, options) {
                var that = this, parentPopup;
                options = options || {};
                if (options.isRtl) {
                    options.origin = options.origin || BOTTOM + ' ' + RIGHT;
                    options.position = options.position || TOP + ' ' + RIGHT;
                }
                Widget.fn.init.call(that, element, options);
                element = that.element;
                options = that.options;
                that.collisions = options.collision ? options.collision.split(' ') : [];
                that.downEvent = kendo.applyEventMap(MOUSEDOWN, kendo.guid());
                if (that.collisions.length === 1) {
                    that.collisions.push(that.collisions[0]);
                }
                parentPopup = $(that.options.anchor).closest('.k-popup,.k-group').filter(':not([class^=km-])');
                options.appendTo = $($(options.appendTo)[0] || parentPopup[0] || document.body);
                that.element.hide().addClass('k-popup k-group k-reset').toggleClass('k-rtl', !!options.isRtl).css({ position: ABSOLUTE }).appendTo(options.appendTo).on('mouseenter' + NS, function () {
                    that._hovered = true;
                }).on('wheel' + NS, function (e) {
                    var scrollArea = $(this).find('.k-list').parent();
                    if (scrollArea.scrollTop() === 0 && e.originalEvent.deltaY < 0 || scrollArea.scrollTop() === scrollArea.prop('scrollHeight') - scrollArea.prop('offsetHeight') && e.originalEvent.deltaY > 0) {
                        e.preventDefault();
                    }
                }).on('mouseleave' + NS, function () {
                    that._hovered = false;
                });
                that.wrapper = $();
                if (options.animation === false) {
                    options.animation = {
                        open: { effects: {} },
                        close: {
                            hide: true,
                            effects: {}
                        }
                    };
                }
                extend(options.animation.open, {
                    complete: function () {
                        that.wrapper.css({ overflow: VISIBLE });
                        that._activated = true;
                        that._trigger(ACTIVATE);
                    }
                });
                extend(options.animation.close, {
                    complete: function () {
                        that._animationClose();
                    }
                });
                that._mousedownProxy = function (e) {
                    that._mousedown(e);
                };
                if (support.mobileOS.android) {
                    that._resizeProxy = function (e) {
                        setTimeout(function () {
                            that._resize(e);
                        }, 600);
                    };
                } else {
                    that._resizeProxy = function (e) {
                        that._resize(e);
                    };
                }
                if (options.toggleTarget) {
                    $(options.toggleTarget).on(options.toggleEvent + NS, $.proxy(that.toggle, that));
                }
            },
            events: [
                OPEN,
                ACTIVATE,
                CLOSE,
                DEACTIVATE
            ],
            options: {
                name: 'Popup',
                toggleEvent: 'click',
                origin: BOTTOM + ' ' + LEFT,
                position: TOP + ' ' + LEFT,
                anchor: BODY,
                appendTo: null,
                collision: 'flip fit',
                viewport: window,
                copyAnchorStyles: true,
                autosize: false,
                modal: false,
                adjustSize: {
                    width: 0,
                    height: 0
                },
                animation: {
                    open: {
                        effects: 'slideIn:down',
                        transition: true,
                        duration: 200
                    },
                    close: {
                        duration: 100,
                        hide: true
                    }
                }
            },
            _animationClose: function () {
                var that = this;
                var location = that.wrapper.data(LOCATION);
                that.wrapper.hide();
                if (location) {
                    that.wrapper.css(location);
                }
                if (that.options.anchor != BODY) {
                    that._hideDirClass();
                }
                that._closing = false;
                that._trigger(DEACTIVATE);
            },
            destroy: function () {
                var that = this, options = that.options, element = that.element.off(NS), parent;
                Widget.fn.destroy.call(that);
                if (options.toggleTarget) {
                    $(options.toggleTarget).off(NS);
                }
                if (!options.modal) {
                    DOCUMENT_ELEMENT.unbind(that.downEvent, that._mousedownProxy);
                    that._toggleResize(false);
                }
                kendo.destroy(that.element.children());
                element.removeData();
                if (options.appendTo[0] === document.body) {
                    parent = element.parent('.k-animation-container');
                    if (parent[0]) {
                        parent.remove();
                    } else {
                        element.remove();
                    }
                }
            },
            open: function (x, y) {
                var that = this, fixed = {
                        isFixed: !isNaN(parseInt(y, 10)),
                        x: x,
                        y: y
                    }, element = that.element, options = that.options, animation, wrapper, anchor = $(options.anchor), mobile = element[0] && element.hasClass('km-widget');
                if (!that.visible()) {
                    if (options.copyAnchorStyles) {
                        if (mobile && styles[0] == 'font-size') {
                            styles.shift();
                        }
                        element.css(kendo.getComputedStyles(anchor[0], styles));
                    }
                    if (element.data('animating') || that._trigger(OPEN)) {
                        return;
                    }
                    that._activated = false;
                    if (!options.modal) {
                        DOCUMENT_ELEMENT.unbind(that.downEvent, that._mousedownProxy).bind(that.downEvent, that._mousedownProxy);
                        that._toggleResize(false);
                        that._toggleResize(true);
                    }
                    that.wrapper = wrapper = kendo.wrap(element, options.autosize).css({
                        overflow: HIDDEN,
                        display: 'block',
                        position: ABSOLUTE
                    });
                    if (support.mobileOS.android) {
                        wrapper.css(TRANSFORM, 'translatez(0)');
                    }
                    wrapper.css(POSITION);
                    if ($(options.appendTo)[0] == document.body) {
                        wrapper.css(TOP, '-10000px');
                    }
                    that.flipped = that._position(fixed);
                    animation = that._openAnimation();
                    if (options.anchor != BODY) {
                        that._showDirClass(animation);
                    }
                    element.data(EFFECTS, animation.effects).kendoStop(true).kendoAnimate(animation);
                }
            },
            _location: function (isFixed) {
                var that = this, element = that.element, options = that.options, wrapper, anchor = $(options.anchor), mobile = element[0] && element.hasClass('km-widget');
                if (options.copyAnchorStyles) {
                    if (mobile && styles[0] == 'font-size') {
                        styles.shift();
                    }
                    element.css(kendo.getComputedStyles(anchor[0], styles));
                }
                that.wrapper = wrapper = kendo.wrap(element, options.autosize).css({
                    overflow: HIDDEN,
                    display: 'block',
                    position: ABSOLUTE
                });
                if (support.mobileOS.android) {
                    wrapper.css(TRANSFORM, 'translatez(0)');
                }
                wrapper.css(POSITION);
                if ($(options.appendTo)[0] == document.body) {
                    wrapper.css(TOP, '-10000px');
                }
                that._position(isFixed || {});
                var offset = wrapper.offset();
                return {
                    width: kendo._outerWidth(wrapper),
                    height: kendo._outerHeight(wrapper),
                    left: offset.left,
                    top: offset.top
                };
            },
            _openAnimation: function () {
                var animation = extend(true, {}, this.options.animation.open);
                animation.effects = kendo.parseEffects(animation.effects, this.flipped);
                return animation;
            },
            _hideDirClass: function () {
                var anchor = $(this.options.anchor);
                var direction = ((anchor.attr('class') || '').match(ACTIVEBORDERREGEXP) || [
                    '',
                    'down'
                ])[1];
                var dirClass = ACTIVEBORDER + '-' + direction;
                anchor.removeClass(dirClass).children(ACTIVECHILDREN).removeClass(ACTIVE).removeClass(dirClass);
                this.element.removeClass(ACTIVEBORDER + '-' + kendo.directions[direction].reverse);
            },
            _showDirClass: function (animation) {
                var direction = animation.effects.slideIn ? animation.effects.slideIn.direction : 'down';
                var dirClass = ACTIVEBORDER + '-' + direction;
                $(this.options.anchor).addClass(dirClass).children(ACTIVECHILDREN).addClass(ACTIVE).addClass(dirClass);
                this.element.addClass(ACTIVEBORDER + '-' + kendo.directions[direction].reverse);
            },
            position: function () {
                if (this.visible()) {
                    this.flipped = this._position();
                }
            },
            toggle: function () {
                var that = this;
                that[that.visible() ? CLOSE : OPEN]();
            },
            visible: function () {
                return this.element.is(':' + VISIBLE);
            },
            close: function (skipEffects) {
                var that = this, options = that.options, wrap, animation, openEffects, closeEffects;
                if (that.visible()) {
                    wrap = that.wrapper[0] ? that.wrapper : kendo.wrap(that.element).hide();
                    that._toggleResize(false);
                    if (that._closing || that._trigger(CLOSE)) {
                        that._toggleResize(true);
                        return;
                    }
                    that.element.find('.k-popup').each(function () {
                        var that = $(this), popup = that.data('kendoPopup');
                        if (popup) {
                            popup.close(skipEffects);
                        }
                    });
                    DOCUMENT_ELEMENT.unbind(that.downEvent, that._mousedownProxy);
                    if (skipEffects) {
                        animation = {
                            hide: true,
                            effects: {}
                        };
                    } else {
                        animation = extend(true, {}, options.animation.close);
                        openEffects = that.element.data(EFFECTS);
                        closeEffects = animation.effects;
                        if (!closeEffects && !kendo.size(closeEffects) && openEffects && kendo.size(openEffects)) {
                            animation.effects = openEffects;
                            animation.reverse = true;
                        }
                        that._closing = true;
                    }
                    that.element.kendoStop(true);
                    wrap.css({ overflow: HIDDEN });
                    that.element.kendoAnimate(animation);
                    if (skipEffects) {
                        that._animationClose();
                    }
                }
            },
            _trigger: function (ev) {
                return this.trigger(ev, { type: ev });
            },
            _resize: function (e) {
                var that = this;
                if (support.resize.indexOf(e.type) !== -1) {
                    clearTimeout(that._resizeTimeout);
                    that._resizeTimeout = setTimeout(function () {
                        that._position();
                        that._resizeTimeout = null;
                    }, 50);
                } else {
                    if (!that._hovered || that._activated && that.element.hasClass('k-list-container')) {
                        that.close();
                    }
                }
            },
            _toggleResize: function (toggle) {
                var method = toggle ? 'on' : 'off';
                var eventNames = support.resize;
                if (!(support.mobileOS.ios || support.mobileOS.android)) {
                    eventNames += ' ' + SCROLL;
                }
                this._scrollableParents()[method](SCROLL, this._resizeProxy);
                WINDOW[method](eventNames, this._resizeProxy);
            },
            _mousedown: function (e) {
                var that = this, container = that.element[0], options = that.options, anchor = $(options.anchor)[0], toggleTarget = options.toggleTarget, target = kendo.eventTarget(e), popup = $(target).closest('.k-popup'), mobile = popup.parent().parent('.km-shim').length;
                popup = popup[0];
                if (!mobile && popup && popup !== that.element[0]) {
                    return;
                }
                if ($(e.target).closest('a').data('rel') === 'popover') {
                    return;
                }
                if (!contains(container, target) && !contains(anchor, target) && !(toggleTarget && contains($(toggleTarget)[0], target))) {
                    that.close();
                }
            },
            _fit: function (position, size, viewPortSize) {
                var output = 0;
                if (position + size > viewPortSize) {
                    output = viewPortSize - (position + size);
                }
                if (position < 0) {
                    output = -position;
                }
                return output;
            },
            _flip: function (offset, size, anchorSize, viewPortSize, origin, position, boxSize) {
                var output = 0;
                boxSize = boxSize || size;
                if (position !== origin && position !== CENTER && origin !== CENTER) {
                    if (offset + boxSize > viewPortSize) {
                        output += -(anchorSize + size);
                    }
                    if (offset + output < 0) {
                        output += anchorSize + size;
                    }
                }
                return output;
            },
            _scrollableParents: function () {
                return $(this.options.anchor).parentsUntil('body').filter(function (index, element) {
                    return kendo.isScrollable(element);
                });
            },
            _position: function (fixed) {
                var that = this, element = that.element, wrapper = that.wrapper, options = that.options, viewport = $(options.viewport), zoomLevel = support.zoomLevel(), isWindow = !!(viewport[0] == window && window.innerWidth && zoomLevel <= 1.02), anchor = $(options.anchor), origins = options.origin.toLowerCase().split(' '), positions = options.position.toLowerCase().split(' '), collisions = that.collisions, siblingContainer, parents, parentZIndex, zIndex = 10002, idx = 0, docEl = document.documentElement, length, viewportOffset, viewportWidth, viewportHeight;
                if (options.viewport === window) {
                    viewportOffset = {
                        top: window.pageYOffset || document.documentElement.scrollTop || 0,
                        left: window.pageXOffset || document.documentElement.scrollLeft || 0
                    };
                } else {
                    viewportOffset = viewport.offset();
                }
                if (isWindow) {
                    viewportWidth = window.innerWidth;
                    viewportHeight = window.innerHeight;
                } else {
                    viewportWidth = viewport.width();
                    viewportHeight = viewport.height();
                }
                if (isWindow && docEl.scrollHeight - docEl.clientHeight > 0) {
                    var sign = options.isRtl ? -1 : 1;
                    viewportWidth -= sign * kendo.support.scrollbar();
                }
                siblingContainer = anchor.parents().filter(wrapper.siblings());
                if (siblingContainer[0]) {
                    parentZIndex = Math.max(Number(siblingContainer.css('zIndex')), 0);
                    if (parentZIndex) {
                        zIndex = parentZIndex + 10;
                    } else {
                        parents = anchor.parentsUntil(siblingContainer);
                        for (length = parents.length; idx < length; idx++) {
                            parentZIndex = Number($(parents[idx]).css('zIndex'));
                            if (parentZIndex && zIndex < parentZIndex) {
                                zIndex = parentZIndex + 10;
                            }
                        }
                    }
                }
                wrapper.css('zIndex', zIndex);
                if (fixed && fixed.isFixed) {
                    wrapper.css({
                        left: fixed.x,
                        top: fixed.y
                    });
                } else {
                    wrapper.css(that._align(origins, positions));
                }
                var pos = getOffset(wrapper, POSITION, anchor[0] === wrapper.offsetParent()[0]), offset = getOffset(wrapper), anchorParent = anchor.offsetParent().parent('.k-animation-container,.k-popup,.k-group');
                if (anchorParent.length) {
                    pos = getOffset(wrapper, POSITION, true);
                    offset = getOffset(wrapper);
                }
                offset.top -= viewportOffset.top;
                offset.left -= viewportOffset.left;
                if (!that.wrapper.data(LOCATION)) {
                    wrapper.data(LOCATION, extend({}, pos));
                }
                var offsets = extend({}, offset), location = extend({}, pos), adjustSize = options.adjustSize;
                if (collisions[0] === 'fit') {
                    location.top += that._fit(offsets.top, outerHeight(wrapper) + adjustSize.height, viewportHeight / zoomLevel);
                }
                if (collisions[1] === 'fit') {
                    location.left += that._fit(offsets.left, outerWidth(wrapper) + adjustSize.width, viewportWidth / zoomLevel);
                }
                var flipPos = extend({}, location);
                var elementHeight = outerHeight(element);
                var wrapperHeight = outerHeight(wrapper);
                if (!wrapper.height() && elementHeight) {
                    wrapperHeight = wrapperHeight + elementHeight;
                }
                if (collisions[0] === 'flip') {
                    location.top += that._flip(offsets.top, elementHeight, outerHeight(anchor), viewportHeight / zoomLevel, origins[0], positions[0], wrapperHeight);
                }
                if (collisions[1] === 'flip') {
                    location.left += that._flip(offsets.left, outerWidth(element), outerWidth(anchor), viewportWidth / zoomLevel, origins[1], positions[1], outerWidth(wrapper));
                }
                element.css(POSITION, ABSOLUTE);
                wrapper.css(location);
                return location.left != flipPos.left || location.top != flipPos.top;
            },
            _align: function (origin, position) {
                var that = this, element = that.wrapper, anchor = $(that.options.anchor), verticalOrigin = origin[0], horizontalOrigin = origin[1], verticalPosition = position[0], horizontalPosition = position[1], anchorOffset = getOffset(anchor), appendTo = $(that.options.appendTo), appendToOffset, width = outerWidth(element), height = outerHeight(element), anchorWidth = outerWidth(anchor), anchorHeight = outerHeight(anchor), top = anchorOffset.top, left = anchorOffset.left, round = Math.round;
                if (appendTo[0] != document.body) {
                    appendToOffset = getOffset(appendTo);
                    top -= appendToOffset.top;
                    left -= appendToOffset.left;
                }
                if (verticalOrigin === BOTTOM) {
                    top += anchorHeight;
                }
                if (verticalOrigin === CENTER) {
                    top += round(anchorHeight / 2);
                }
                if (verticalPosition === BOTTOM) {
                    top -= height;
                }
                if (verticalPosition === CENTER) {
                    top -= round(height / 2);
                }
                if (horizontalOrigin === RIGHT) {
                    left += anchorWidth;
                }
                if (horizontalOrigin === CENTER) {
                    left += round(anchorWidth / 2);
                }
                if (horizontalPosition === RIGHT) {
                    left -= width;
                }
                if (horizontalPosition === CENTER) {
                    left -= round(width / 2);
                }
                return {
                    top: top,
                    left: left
                };
            }
        });
        ui.plugin(Popup);
        var stableSort = kendo.support.stableSort;
        var tabKeyTrapNS = 'kendoTabKeyTrap';
        var focusableNodesSelector = 'a[href], area[href], input:not([disabled]), select:not([disabled]), textarea:not([disabled]), button:not([disabled]), iframe, object, embed, [tabindex], *[contenteditable]';
        var TabKeyTrap = Class.extend({
            init: function (element) {
                this.element = $(element);
                this.element.autoApplyNS(tabKeyTrapNS);
            },
            trap: function () {
                this.element.on('keydown', proxy(this._keepInTrap, this));
            },
            removeTrap: function () {
                this.element.kendoDestroy(tabKeyTrapNS);
            },
            destroy: function () {
                this.element.kendoDestroy(tabKeyTrapNS);
                this.element = undefined;
            },
            shouldTrap: function () {
                return true;
            },
            _keepInTrap: function (e) {
                if (e.which !== 9 || !this.shouldTrap() || e.isDefaultPrevented()) {
                    return;
                }
                var elements = this._focusableElements();
                var sortedElements = this._sortFocusableElements(elements);
                var next = this._nextFocusable(e, sortedElements);
                this._focus(next);
                e.preventDefault();
            },
            _focusableElements: function () {
                var elements = this.element.find(focusableNodesSelector).filter(function (i, item) {
                    return item.tabIndex >= 0 && $(item).is(':visible');
                });
                return elements;
            },
            _sortFocusableElements: function (elements) {
                var sortedElements;
                if (stableSort) {
                    sortedElements = elements.sort(function (prev, next) {
                        return prev.tabIndex - next.tabIndex;
                    });
                } else {
                    var attrName = '__k_index';
                    elements.each(function (i, item) {
                        item.setAttribute(attrName, i);
                    });
                    sortedElements = elements.sort(function (prev, next) {
                        return prev.tabIndex === next.tabIndex ? parseInt(prev.getAttribute(attrName), 10) - parseInt(next.getAttribute(attrName), 10) : prev.tabIndex - next.tabIndex;
                    });
                    elements.removeAttr(attrName);
                }
                return sortedElements;
            },
            _nextFocusable: function (e, elements) {
                var count = elements.length;
                var current = elements.index(e.target);
                return elements.get((current + (e.shiftKey ? -1 : 1)) % count);
            },
            _focus: function (element) {
                element.focus();
                if (element.nodeName == 'INPUT' && element.setSelectionRange) {
                    element.setSelectionRange(0, element.value.length);
                }
            }
        });
        ui.Popup.TabKeyTrap = TabKeyTrap;
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.menu', ['kendo.popup'], f);
}(function () {
    var __meta__ = {
        id: 'menu',
        name: 'Menu',
        category: 'web',
        description: 'The Menu widget displays hierarchical data as a multi-level menu.',
        depends: ['popup']
    };
    (function ($, undefined) {
        var kendo = window.kendo, ui = kendo.ui, activeElement = kendo._activeElement, touch = kendo.support.touch && kendo.support.mobileOS, MOUSEDOWN = 'mousedown', CLICK = 'click', DELAY = 30, SCROLLSPEED = 50, extend = $.extend, proxy = $.proxy, each = $.each, template = kendo.template, keys = kendo.keys, Widget = ui.Widget, excludedNodesRegExp = /^(ul|a|div)$/i, NS = '.kendoMenu', IMG = 'img', OPEN = 'open', MENU = 'k-menu', LINK = 'k-link', LAST = 'k-last', CLOSE = 'close', TIMER = 'timer', FIRST = 'k-first', IMAGE = 'k-image', SELECT = 'select', ZINDEX = 'zIndex', ACTIVATE = 'activate', DEACTIVATE = 'deactivate', POINTERDOWN = 'touchstart' + NS + ' MSPointerDown' + NS + ' pointerdown' + NS, pointers = kendo.support.pointers, msPointers = kendo.support.msPointers, allPointers = msPointers || pointers, MOUSEENTER = pointers ? 'pointerenter' : msPointers ? 'MSPointerEnter' : 'mouseenter', MOUSELEAVE = pointers ? 'pointerleave' : msPointers ? 'MSPointerLeave' : 'mouseleave', MOUSEWHEEL = 'DOMMouseScroll' + NS + ' mousewheel' + NS, RESIZE = kendo.support.resize + NS, SCROLLWIDTH = 'scrollWidth', SCROLLHEIGHT = 'scrollHeight', OFFSETWIDTH = 'offsetWidth', OFFSETHEIGHT = 'offsetHeight', POPUP_ID_ATTR = 'group', POPUP_OPENER_ATTR = 'groupparent', DOCUMENT_ELEMENT = $(document.documentElement), KENDOPOPUP = 'kendoPopup', DEFAULTSTATE = 'k-state-default', HOVERSTATE = 'k-state-hover', FOCUSEDSTATE = 'k-state-focused', DISABLEDSTATE = 'k-state-disabled', SELECTEDSTATE = 'k-state-selected', menuSelector = '.k-menu', groupSelector = '.k-menu-group', animationContainerSelector = '.k-animation-container', popupSelector = groupSelector + ',' + animationContainerSelector, allItemsSelector = ':not(.k-list) > .k-item', disabledSelector = '.k-item.k-state-disabled', itemSelector = '.k-item:not(.k-state-disabled)', linkSelector = '.k-item:not(.k-state-disabled) > .k-link', exclusionSelector = ':not(.k-item.k-separator)', nextSelector = exclusionSelector + ':eq(0)', lastSelector = exclusionSelector + ':last', templateSelector = 'div:not(.k-animation-container,.k-list-container)', scrollButtonSelector = '.k-menu-scroll-button', touchPointerTypes = {
                '2': 1,
                'touch': 1
            }, templates = {
                content: template('<div #= contentCssAttributes(item) # tabindex=\'-1\'>#= content(item) #</div>'),
                group: template('<ul class=\'#= groupCssClass(group) #\'#= groupAttributes(group) # role=\'menu\' aria-hidden=\'true\'>' + '#= renderItems(data) #' + '</ul>'),
                itemWrapper: template('<#= tag(item) # class=\'#= textClass(item) #\'#= textAttributes(item) #>' + '#= image(data) ##= sprite(item) ##= text(item) #' + '#= arrow(data) #' + '</#= tag(item) #>'),
                item: template('<li class=\'#= wrapperCssClass(group, item) #\' #= itemCssAttributes(item) # role=\'menuitem\'  #=item.items ? "aria-haspopup=\'true\'": ""#' + '#=item.enabled === false ? "aria-disabled=\'true\'" : \'\'#>' + '#= itemWrapper(data) #' + '# if (item.items) { #' + '#= subGroup({ items: item.items, menu: menu, group: { expanded: item.expanded } }) #' + '# } else if (item.content || item.contentUrl) { #' + '#= renderContent(data) #' + '# } #' + '</li>'),
                scrollButton: template('<span class=\'k-button k-button-icon k-menu-scroll-button k-scroll-#= direction #\' unselectable=\'on\'>' + '<span class=\'k-icon k-i-arrow-60-#= direction #\'></span></span>'),
                image: template('<img #= imageCssAttributes(item) # alt=\'\' src=\'#= item.imageUrl #\' />'),
                arrow: template('<span class=\'#= arrowClass(item, group) #\'></span>'),
                sprite: template('<span class=\'k-sprite #= spriteCssClass #\'></span>'),
                empty: template('')
            }, rendering = {
                wrapperCssClass: function (group, item) {
                    var result = 'k-item', index = item.index;
                    if (item.enabled === false) {
                        result += ' k-state-disabled';
                    } else {
                        result += ' k-state-default';
                    }
                    if (group.firstLevel && index === 0) {
                        result += ' k-first';
                    }
                    if (index == group.length - 1) {
                        result += ' k-last';
                    }
                    if (item.cssClass) {
                        result += ' ' + item.cssClass;
                    }
                    if (item.attr && item.attr.hasOwnProperty('class')) {
                        result += ' ' + item.attr['class'];
                    }
                    if (item.selected) {
                        result += ' ' + SELECTEDSTATE;
                    }
                    return result;
                },
                itemCssAttributes: function (item) {
                    var result = '';
                    var attributes = item.attr || {};
                    for (var attr in attributes) {
                        if (attributes.hasOwnProperty(attr) && attr !== 'class') {
                            result += attr + '="' + attributes[attr] + '" ';
                        }
                    }
                    return result;
                },
                imageCssAttributes: function (item) {
                    var result = '';
                    var attributes = item.imageAttr || {};
                    if (!attributes['class']) {
                        attributes['class'] = IMAGE;
                    } else {
                        attributes['class'] += ' ' + IMAGE;
                    }
                    for (var attr in attributes) {
                        if (attributes.hasOwnProperty(attr)) {
                            result += attr + '="' + attributes[attr] + '" ';
                        }
                    }
                    return result;
                },
                contentCssAttributes: function (item) {
                    var result = '';
                    var attributes = item.contentAttr || {};
                    var defaultClasses = 'k-content k-group k-menu-group';
                    if (!attributes['class']) {
                        attributes['class'] = defaultClasses;
                    } else {
                        attributes['class'] += ' ' + defaultClasses;
                    }
                    for (var attr in attributes) {
                        if (attributes.hasOwnProperty(attr)) {
                            result += attr + '="' + attributes[attr] + '" ';
                        }
                    }
                    return result;
                },
                textClass: function () {
                    return LINK;
                },
                textAttributes: function (item) {
                    return item.url ? ' href=\'' + item.url + '\'' : '';
                },
                arrowClass: function (item, group) {
                    var result = 'k-icon';
                    if (group.horizontal) {
                        result += ' k-i-arrow-60-down';
                    } else {
                        result += ' k-i-arrow-60-right';
                    }
                    return result;
                },
                text: function (item) {
                    return item.encoded === false ? item.text : kendo.htmlEncode(item.text);
                },
                tag: function (item) {
                    return item.url ? 'a' : 'span';
                },
                groupAttributes: function (group) {
                    return group.expanded !== true ? ' style=\'display:none\'' : '';
                },
                groupCssClass: function () {
                    return 'k-group k-menu-group';
                },
                content: function (item) {
                    return item.content ? item.content : '&nbsp;';
                }
            };
        function getEffectDirection(direction, root) {
            direction = direction.split(' ')[!root + 0] || direction;
            return direction.replace('top', 'up').replace('bottom', 'down');
        }
        function parseDirection(direction, root, isRtl) {
            direction = direction.split(' ')[!root + 0] || direction;
            var output = {
                    origin: [
                        'bottom',
                        isRtl ? 'right' : 'left'
                    ],
                    position: [
                        'top',
                        isRtl ? 'right' : 'left'
                    ]
                }, horizontal = /left|right/.test(direction);
            if (horizontal) {
                output.origin = [
                    'top',
                    direction
                ];
                output.position[1] = kendo.directions[direction].reverse;
            } else {
                output.origin[0] = direction;
                output.position[0] = kendo.directions[direction].reverse;
            }
            output.origin = output.origin.join(' ');
            output.position = output.position.join(' ');
            return output;
        }
        function contains(parent, child) {
            try {
                return $.contains(parent, child);
            } catch (e) {
                return false;
            }
        }
        function updateItemClasses(item) {
            item = $(item);
            item.addClass('k-item').children(IMG).addClass(IMAGE);
            item.children('a').addClass(LINK).children(IMG).addClass(IMAGE);
            item.filter(':not([disabled])').addClass(DEFAULTSTATE);
            item.filter('.k-separator').empty().append('&nbsp;');
            item.filter('li[disabled]').addClass(DISABLEDSTATE).removeAttr('disabled').attr('aria-disabled', true);
            if (!item.filter('[role]').length) {
                item.attr('role', 'menuitem');
            }
            if (!item.children('.' + LINK).length) {
                item.contents().filter(function () {
                    return !this.nodeName.match(excludedNodesRegExp) && !(this.nodeType == 3 && !$.trim(this.nodeValue));
                }).wrapAll('<span class=\'' + LINK + '\'/>');
            }
            updateArrow(item);
            updateFirstLast(item);
        }
        function updateArrow(item) {
            item = $(item);
            item.find('> .k-link > .k-menu-expand-arrow[class*=k-i-arrow]:not(.k-sprite)').remove();
            item.filter(':has(.k-menu-group)').children('.k-link:not(:has([class*=k-i-arrow]:not(.k-sprite)))').each(function () {
                var item = $(this), arrowCssClass = getArrowCssClass(item);
                item.append('<span class=\'k-icon' + arrowCssClass + ' k-menu-expand-arrow\'/>');
            });
        }
        function getArrowCssClass(item) {
            var arrowCssClass, parent = item.parent().parent(), isRtl = kendo.support.isRtl(parent);
            if (parent.hasClass(MENU + '-horizontal')) {
                arrowCssClass = ' k-i-arrow-60-down';
            } else {
                if (isRtl) {
                    arrowCssClass = ' k-i-arrow-60-left';
                } else {
                    arrowCssClass = ' k-i-arrow-60-right';
                }
            }
            return arrowCssClass;
        }
        function updateFirstLast(item) {
            item = $(item);
            item.filter('.k-first:not(:first-child)').removeClass(FIRST);
            item.filter('.k-last:not(:last-child)').removeClass(LAST);
            item.filter(':first-child').addClass(FIRST);
            item.filter(':last-child').addClass(LAST);
        }
        function storeItemSelectEventHandler(element, options) {
            var selectHandler = getItemSelectEventHandler(options);
            if (selectHandler) {
                setItemData(element, selectHandler);
            }
            if (options.items) {
                $(element).children('ul').children('li').each(function (i) {
                    storeItemSelectEventHandler(this, options.items[i]);
                });
            }
        }
        function setItemData(element, selectHandler) {
            $(element).children('.k-link').data({ selectHandler: selectHandler });
        }
        function getItemSelectEventHandler(options) {
            var selectHandler = options.select, isFunction = kendo.isFunction;
            if (selectHandler && isFunction(selectHandler)) {
                return selectHandler;
            }
            return null;
        }
        function popupOpenerSelector(id) {
            return id ? 'li[data-groupparent=\'' + id + '\']' : 'li[data-groupparent]';
        }
        function popupGroupSelector(id) {
            return id ? 'ul[data-group=\'' + id + '\']' : 'ul[data-group]';
        }
        function getChildPopups(currentPopup, overflowWrapper) {
            var childPopupOpener = currentPopup.find(popupOpenerSelector());
            var result = [];
            childPopupOpener.each(function (i, opener) {
                opener = $(opener);
                var popupId = opener.data(POPUP_OPENER_ATTR);
                var popup = currentPopup;
                while (popupId) {
                    popup = overflowWrapper.find(popupGroupSelector(popupId) + ':visible');
                    if (popup.length) {
                        result.push(popup);
                    }
                    opener = popup.find(popupOpenerSelector());
                    popupId = opener.data(POPUP_OPENER_ATTR);
                }
            });
            return result;
        }
        function popupParentItem(popupElement, overflowWrapper) {
            var popupId = popupElement.data(POPUP_ID_ATTR);
            return popupId ? overflowWrapper.find(popupOpenerSelector(popupId)) : $([]);
        }
        function itemPopup(item, overflowWrapper) {
            var popupId = item.data(POPUP_OPENER_ATTR);
            return popupId ? overflowWrapper.children(animationContainerSelector).children(popupGroupSelector(popupId)) : $([]);
        }
        function overflowMenuParents(current, overflowWrapper) {
            var parents = [];
            var getParents = function (item) {
                while (item.parentNode && !overflowWrapper.is(item.parentNode)) {
                    parents.push(item.parentNode);
                    item = item.parentNode;
                }
            };
            var elem = current[0] || current;
            getParents(elem);
            var last = parents[parents.length - 1];
            while ($(last).is(animationContainerSelector)) {
                var popupElement = $(last).children('ul');
                elem = popupParentItem(popupElement, overflowWrapper)[0];
                if (!elem) {
                    break;
                }
                parents.push(elem);
                getParents(elem);
                last = parents[parents.length - 1];
            }
            return parents;
        }
        function mousewheelDelta(e) {
            var delta = 0;
            if (e.wheelDelta) {
                delta = -e.wheelDelta / 120;
                delta = delta > 0 ? Math.ceil(delta) : Math.floor(delta);
            }
            if (e.detail) {
                delta = Math.round(e.detail / 3);
            }
            return delta;
        }
        function parentsScroll(current, scrollDirection) {
            var scroll = 0;
            var parent = current.parentNode;
            while (parent && !isNaN(parent[scrollDirection])) {
                scroll += parent[scrollDirection];
                parent = parent.parentNode;
            }
            return scroll;
        }
        function isPointerTouch(e) {
            return allPointers && e.originalEvent.pointerType in touchPointerTypes;
        }
        function isTouch(e) {
            var ev = e.originalEvent;
            return touch && /touch/i.test(ev.type || '');
        }
        function removeSpacesBetweenItems(ul) {
            ul.contents().filter(function () {
                return this.nodeName != 'LI';
            }).remove();
        }
        var Menu = Widget.extend({
            init: function (element, options) {
                var that = this;
                Widget.fn.init.call(that, element, options);
                element = that.wrapper = that.element;
                options = that.options;
                that._initData(options);
                that._updateClasses();
                that._animations(options);
                that.nextItemZIndex = 100;
                that._tabindex();
                that._initOverflow(options);
                that._attachMenuEventsHandlers();
                if (options.openOnClick) {
                    that.clicked = false;
                }
                element.attr('role', 'menubar');
                if (element[0].id) {
                    that._ariaId = kendo.format('{0}_mn_active', element[0].id);
                }
                kendo.notify(that);
            },
            events: [
                OPEN,
                CLOSE,
                ACTIVATE,
                DEACTIVATE,
                SELECT
            ],
            options: {
                name: 'Menu',
                animation: {
                    open: { duration: 200 },
                    close: { duration: 100 }
                },
                orientation: 'horizontal',
                direction: 'default',
                openOnClick: false,
                closeOnClick: true,
                hoverDelay: 100,
                scrollable: false,
                popupCollision: undefined
            },
            _initData: function (options) {
                var that = this;
                if (options.dataSource) {
                    that.angular('cleanup', function () {
                        return { elements: that.element.children() };
                    });
                    that.element.empty();
                    that.append(options.dataSource, that.element);
                    that.angular('compile', function () {
                        return { elements: that.element.children() };
                    });
                }
            },
            _attachMenuEventsHandlers: function () {
                var that = this;
                var element = that.element;
                var options = that.options;
                var overflowWrapper = that._overflowWrapper();
                (overflowWrapper || element).on(POINTERDOWN, itemSelector, proxy(that._focusHandler, that)).on(CLICK + NS, disabledSelector, false).on(CLICK + NS, itemSelector, proxy(that._click, that)).on(POINTERDOWN + ' ' + MOUSEDOWN + NS, '.k-content', proxy(that._preventClose, that)).on(MOUSEENTER + NS, itemSelector, proxy(that._mouseenter, that)).on(MOUSELEAVE + NS, itemSelector, proxy(that._mouseleave, that)).on(MOUSEENTER + NS + ' ' + MOUSELEAVE + NS + ' ' + MOUSEDOWN + NS + ' ' + CLICK + NS, linkSelector, proxy(that._toggleHover, that));
                element.on('keydown' + NS, proxy(that._keydown, that)).on('focus' + NS, proxy(that._focus, that)).on('focus' + NS, '.k-content', proxy(that._focus, that)).on('blur' + NS, proxy(that._removeHoverItem, that)).on('blur' + NS, '[tabindex]', proxy(that._checkActiveElement, that));
                if (overflowWrapper) {
                    overflowWrapper.on(MOUSELEAVE + NS, popupSelector, proxy(that._mouseleavePopup, that)).on(MOUSEENTER + NS, popupSelector, proxy(that._mouseenterPopup, that));
                }
                if (options.openOnClick) {
                    that._documentClickHandler = proxy(that._documentClick, that);
                    $(document).click(that._documentClickHandler);
                }
            },
            _detachMenuEventsHandlers: function () {
                var that = this;
                var overflowWrapper = that._overflowWrapper();
                if (overflowWrapper) {
                    overflowWrapper.off(NS);
                }
                that.element.off(NS);
                if (that._documentClickHandler) {
                    $(document).unbind('click', that._documentClickHandler);
                }
            },
            _initOverflow: function (options) {
                var that = this;
                var isHorizontal = options.orientation == 'horizontal';
                var backwardBtn, forwardBtn;
                if (options.scrollable) {
                    that._openedPopups = {};
                    that._scrollWrapper = that.element.wrap('<div class=\'k-menu-scroll-wrapper ' + options.orientation + '\'></div>').parent();
                    if (isHorizontal) {
                        removeSpacesBetweenItems(that.element);
                    }
                    backwardBtn = $(templates.scrollButton({ direction: isHorizontal ? 'left' : 'up' }));
                    forwardBtn = $(templates.scrollButton({ direction: isHorizontal ? 'right' : 'down' }));
                    backwardBtn.add(forwardBtn).appendTo(that._scrollWrapper);
                    that._initScrolling(that.element, backwardBtn, forwardBtn, isHorizontal);
                    var initialWidth = that.element.outerWidth();
                    var initialCssWidth = that.element[0].style.width;
                    initialCssWidth = initialCssWidth === 'auto' ? '' : initialCssWidth;
                    if (isHorizontal) {
                        $(window).on(RESIZE, kendo.throttle(function () {
                            that._setOverflowWrapperWidth(initialWidth, initialCssWidth);
                            that._toggleScrollButtons(that.element, backwardBtn, forwardBtn, isHorizontal);
                        }, 100));
                    }
                    that._setOverflowWrapperWidth(initialWidth, initialCssWidth);
                    that._toggleScrollButtons(that.element, backwardBtn, forwardBtn, isHorizontal);
                }
            },
            _overflowWrapper: function () {
                return this._scrollWrapper || this._popupsWrapper;
            },
            _setOverflowWrapperWidth: function (initialWidth, initialCssWidth) {
                var that = this;
                var wrapperCssWidth = that._scrollWrapper.css('width');
                that._scrollWrapper.css({ width: '' });
                var wrapperWidth = that._scrollWrapper.outerWidth();
                that._scrollWrapper.css({ width: wrapperCssWidth });
                var menuWidth = that.element.outerWidth();
                var borders = that.element[0].offsetWidth - that.element[0].clientWidth;
                if (menuWidth != wrapperWidth && wrapperWidth > 0) {
                    var width = initialCssWidth ? Math.min(initialWidth, wrapperWidth) : wrapperWidth;
                    that.element.width(width - borders);
                    that._scrollWrapper.width(width);
                }
            },
            _reinitOverflow: function (options) {
                var that = this;
                var overflowChanged = options.scrollable && !that.options.scrollable || !options.scrollable && that.options.scrollable || options.scrollable && that.options.scrollable && options.scrollable.distance != that.options.scrollable.distance || options.orientation != that.options.orientation;
                if (overflowChanged) {
                    that._detachMenuEventsHandlers();
                    that._destroyOverflow();
                    that._initOverflow(options);
                    that._attachMenuEventsHandlers();
                }
            },
            _destroyOverflow: function () {
                var that = this;
                var overflowWrapper = that._overflowWrapper();
                if (overflowWrapper) {
                    overflowWrapper.off(NS);
                    overflowWrapper.find(scrollButtonSelector).off(NS).remove();
                    overflowWrapper.children(animationContainerSelector).each(function (i, popupWrapper) {
                        var ul = $(popupWrapper).children(groupSelector);
                        ul.off(MOUSEWHEEL);
                        var popupParentLi = popupParentItem(ul, overflowWrapper);
                        if (popupParentLi.length) {
                            popupParentLi.append(popupWrapper);
                        }
                    });
                    overflowWrapper.find(popupOpenerSelector()).removeAttr('data-groupparent');
                    overflowWrapper.find(popupGroupSelector()).removeAttr('data-group');
                    that.element.off(MOUSEWHEEL);
                    $(window).off(RESIZE);
                    overflowWrapper.contents().unwrap();
                    that._scrollWrapper = that._popupsWrapper = that._openedPopups = undefined;
                }
            },
            _initScrolling: function (scrollElement, backwardBtn, forwardBtn, isHorizontal) {
                var that = this;
                var scrollable = that.options.scrollable;
                var distance = $.isNumeric(scrollable.distance) ? scrollable.distance : SCROLLSPEED;
                var mouseWheelDistance = distance / 2;
                var backward = '-=' + distance;
                var forward = '+=' + distance;
                var backwardDouble = '-=' + distance * 2;
                var forwardDouble = '+=' + distance * 2;
                var scrolling = false;
                var touchEvents = false;
                var scroll = function (value) {
                    var scrollValue = isHorizontal ? { 'scrollLeft': value } : { 'scrollTop': value };
                    scrollElement.finish().animate(scrollValue, 'fast', 'linear', function () {
                        if (scrolling) {
                            scroll(value);
                        }
                    });
                    that._toggleScrollButtons(scrollElement, backwardBtn, forwardBtn, isHorizontal);
                };
                var mouseenterHandler = function (e) {
                    if (!scrolling && !touchEvents) {
                        scroll(e.data.direction);
                        scrolling = true;
                    }
                };
                var mousedownHandler = function (e) {
                    var scrollValue = isHorizontal ? { 'scrollLeft': e.data.direction } : { 'scrollTop': e.data.direction };
                    touchEvents = isTouch(e) || isPointerTouch(e);
                    scrollElement.stop().animate(scrollValue, 'fast', 'linear', function () {
                        if (!touchEvents) {
                            $(e.currentTarget).trigger(MOUSEENTER);
                        } else {
                            that._toggleScrollButtons(scrollElement, backwardBtn, forwardBtn, isHorizontal);
                            scrolling = true;
                        }
                    });
                    scrolling = false;
                    e.stopPropagation();
                    e.preventDefault();
                };
                backwardBtn.on(MOUSEENTER + NS, { direction: backward }, mouseenterHandler).on(kendo.eventMap.down + NS, { direction: backwardDouble }, mousedownHandler);
                forwardBtn.on(MOUSEENTER + NS, { direction: forward }, mouseenterHandler).on(kendo.eventMap.down + NS, { direction: forwardDouble }, mousedownHandler);
                backwardBtn.add(forwardBtn).on(MOUSELEAVE + NS, function () {
                    scrollElement.stop();
                    scrolling = false;
                    that._toggleScrollButtons(scrollElement, backwardBtn, forwardBtn, isHorizontal);
                });
                scrollElement.on(MOUSEWHEEL, function (e) {
                    if (!e.ctrlKey && !e.shiftKey && !e.altKey) {
                        var wheelDelta = mousewheelDelta(e.originalEvent);
                        var scrollSpeed = Math.abs(wheelDelta) * mouseWheelDistance;
                        var value = (wheelDelta > 0 ? '+=' : '-=') + scrollSpeed;
                        var scrollValue = isHorizontal ? { 'scrollLeft': value } : { 'scrollTop': value };
                        that._closeChildPopups(scrollElement);
                        scrollElement.finish().animate(scrollValue, 'fast', 'linear', function () {
                            that._toggleScrollButtons(scrollElement, backwardBtn, forwardBtn, isHorizontal);
                        });
                        e.preventDefault();
                    }
                });
            },
            _toggleScrollButtons: function (scrollElement, backwardBtn, forwardBtn, horizontal) {
                var currentScroll = horizontal ? scrollElement.scrollLeft() : scrollElement.scrollTop();
                var scrollSize = horizontal ? SCROLLWIDTH : SCROLLHEIGHT;
                var offset = horizontal ? OFFSETWIDTH : OFFSETHEIGHT;
                backwardBtn.toggle(currentScroll !== 0);
                forwardBtn.toggle(currentScroll < scrollElement[0][scrollSize] - scrollElement[0][offset] - 1);
            },
            setOptions: function (options) {
                var animation = this.options.animation;
                this._animations(options);
                options.animation = extend(true, animation, options.animation);
                if ('dataSource' in options) {
                    this._initData(options);
                }
                this._updateClasses();
                this._reinitOverflow(options);
                Widget.fn.setOptions.call(this, options);
            },
            destroy: function () {
                var that = this;
                Widget.fn.destroy.call(that);
                that._detachMenuEventsHandlers();
                that._destroyOverflow();
                kendo.destroy(that.element);
            },
            enable: function (element, enable) {
                this._toggleDisabled(element, enable !== false);
                return this;
            },
            disable: function (element) {
                this._toggleDisabled(element, false);
                return this;
            },
            append: function (item, referenceItem) {
                referenceItem = this.element.find(referenceItem);
                var inserted = this._insert(item, referenceItem, referenceItem.length ? referenceItem.find('> .k-menu-group, > .k-animation-container > .k-menu-group') : null);
                each(inserted.items, function (i) {
                    inserted.group.append(this);
                    updateArrow(this);
                    storeItemSelectEventHandler(this, item[i] || item);
                });
                updateArrow(referenceItem);
                updateFirstLast(inserted.group.find('.k-first, .k-last').add(inserted.items));
                return this;
            },
            insertBefore: function (item, referenceItem) {
                referenceItem = this.element.find(referenceItem);
                var inserted = this._insert(item, referenceItem, referenceItem.parent());
                each(inserted.items, function (i) {
                    referenceItem.before(this);
                    updateArrow(this);
                    updateFirstLast(this);
                    storeItemSelectEventHandler(this, item[i] || item);
                });
                updateFirstLast(referenceItem);
                return this;
            },
            insertAfter: function (item, referenceItem) {
                referenceItem = this.element.find(referenceItem);
                var inserted = this._insert(item, referenceItem, referenceItem.parent());
                each(inserted.items, function (i) {
                    referenceItem.after(this);
                    updateArrow(this);
                    updateFirstLast(this);
                    storeItemSelectEventHandler(this, item[i] || item);
                });
                updateFirstLast(referenceItem);
                return this;
            },
            _insert: function (item, referenceItem, parent) {
                var that = this, items, groups;
                if (!referenceItem || !referenceItem.length) {
                    parent = that.element;
                }
                var plain = $.isPlainObject(item), groupData = {
                        firstLevel: parent.hasClass(MENU),
                        horizontal: parent.hasClass(MENU + '-horizontal'),
                        expanded: true,
                        length: parent.children().length
                    };
                if (referenceItem && !parent.length) {
                    parent = $(Menu.renderGroup({ group: groupData })).appendTo(referenceItem);
                }
                if (plain || $.isArray(item)) {
                    items = $($.map(plain ? [item] : item, function (value, idx) {
                        if (typeof value === 'string') {
                            return $(value).get();
                        } else {
                            return $(Menu.renderItem({
                                group: groupData,
                                item: extend(value, { index: idx })
                            })).get();
                        }
                    }));
                } else {
                    if (typeof item == 'string' && item.charAt(0) != '<') {
                        items = that.element.find(item);
                    } else {
                        items = $(item);
                    }
                    groups = items.find('> ul').addClass('k-menu-group').attr('role', 'menu');
                    items = items.filter('li');
                    items.add(groups.find('> li')).each(function () {
                        updateItemClasses(this);
                    });
                }
                return {
                    items: items,
                    group: parent
                };
            },
            remove: function (element) {
                element = this.element.find(element);
                var that = this, parent = element.parentsUntil(that.element, allItemsSelector), group = element.parent('ul:not(.k-menu)');
                element.remove();
                if (group && !group.children(allItemsSelector).length) {
                    var container = group.parent(animationContainerSelector);
                    if (container.length) {
                        container.remove();
                    } else {
                        group.remove();
                    }
                }
                if (parent.length) {
                    parent = parent.eq(0);
                    updateArrow(parent);
                    updateFirstLast(parent);
                }
                return that;
            },
            open: function (element) {
                var that = this;
                var options = that.options;
                var horizontal = options.orientation == 'horizontal';
                var direction = options.direction;
                var isRtl = kendo.support.isRtl(that.wrapper);
                var overflowWrapper = that._overflowWrapper();
                element = (overflowWrapper || that.element).find(element);
                if (/^(top|bottom|default)$/.test(direction)) {
                    if (isRtl) {
                        direction = horizontal ? (direction + ' left').replace('default', 'bottom') : 'left';
                    } else {
                        direction = horizontal ? (direction + ' right').replace('default', 'bottom') : 'right';
                    }
                }
                var visiblePopups = '>.k-popup:visible,>.k-animation-container>.k-popup:visible';
                var closePopup = function () {
                    var popup = $(this).data(KENDOPOPUP);
                    if (popup) {
                        popup.close(true);
                    }
                };
                element.siblings().find(visiblePopups).each(closePopup);
                if (overflowWrapper) {
                    element.find(visiblePopups).each(closePopup);
                }
                if (that.options.openOnClick) {
                    that.clicked = true;
                }
                element.each(function () {
                    var li = $(this);
                    clearTimeout(li.data(TIMER));
                    li.data(TIMER, setTimeout(function () {
                        var ul = li.find('.k-menu-group:first:hidden');
                        var popup;
                        var overflowPopup;
                        if (!ul[0] && overflowWrapper) {
                            overflowPopup = that._getPopup(li);
                            ul = overflowPopup && overflowPopup.element;
                        }
                        if (ul.is(':visible')) {
                            return;
                        }
                        if (ul[0] && that._triggerEvent({
                                item: li[0],
                                type: OPEN
                            }) === false) {
                            if (!ul.find('.k-menu-group')[0] && ul.children('.k-item').length > 1) {
                                var windowHeight = $(window).height(), setScrolling = function () {
                                        ul.css({
                                            maxHeight: windowHeight - (kendo._outerHeight(ul) - ul.height()) - kendo.getShadows(ul).bottom,
                                            overflow: 'auto'
                                        });
                                    };
                                if (kendo.support.browser.msie && kendo.support.browser.version <= 7) {
                                    setTimeout(setScrolling, 0);
                                } else {
                                    setScrolling();
                                }
                            } else {
                                ul.css({
                                    maxHeight: '',
                                    overflow: ''
                                });
                            }
                            li.data(ZINDEX, li.css(ZINDEX));
                            var nextZindex = that.nextItemZIndex++;
                            li.css(ZINDEX, nextZindex);
                            if (that.options.scrollable) {
                                li.parent().siblings(scrollButtonSelector).css({ zIndex: ++nextZindex });
                            }
                            popup = ul.data(KENDOPOPUP);
                            var root = li.parent().hasClass(MENU), parentHorizontal = root && horizontal, directions = parseDirection(direction, root, isRtl), effects = options.animation.open.effects, openEffects = effects !== undefined ? effects : 'slideIn:' + getEffectDirection(direction, root);
                            if (!popup) {
                                popup = ul.kendoPopup({
                                    activate: function () {
                                        that._triggerEvent({
                                            item: this.wrapper.parent(),
                                            type: ACTIVATE
                                        });
                                    },
                                    deactivate: function (e) {
                                        e.sender.element.removeData('targetTransform').css({ opacity: '' });
                                        that._triggerEvent({
                                            item: this.wrapper.parent(),
                                            type: DEACTIVATE
                                        });
                                    },
                                    origin: directions.origin,
                                    position: directions.position,
                                    collision: options.popupCollision !== undefined ? options.popupCollision : parentHorizontal ? 'fit' : 'fit flip',
                                    anchor: li,
                                    appendTo: overflowWrapper || li,
                                    animation: {
                                        open: extend(true, { effects: openEffects }, options.animation.open),
                                        close: options.animation.close
                                    },
                                    open: proxy(that._popupOpen, that),
                                    close: function (e) {
                                        var li = e.sender.wrapper.parent();
                                        if (overflowWrapper) {
                                            var popupId = e.sender.element.data(POPUP_ID_ATTR);
                                            if (popupId) {
                                                li = (overflowWrapper || that.element).find(popupOpenerSelector(popupId));
                                            }
                                            e.sender.wrapper.children(scrollButtonSelector).hide();
                                        }
                                        if (!that._triggerEvent({
                                                item: li[0],
                                                type: CLOSE
                                            })) {
                                            li.css(ZINDEX, li.data(ZINDEX));
                                            li.removeData(ZINDEX);
                                            if (that.options.scrollable) {
                                                li.parent().siblings(scrollButtonSelector).css({ zIndex: '' });
                                            }
                                            if (touch || allPointers) {
                                                li.removeClass(HOVERSTATE);
                                                that._removeHoverItem();
                                            }
                                        } else {
                                            e.preventDefault();
                                        }
                                    }
                                }).data(KENDOPOPUP);
                            } else {
                                popup = ul.data(KENDOPOPUP);
                                popup.options.origin = directions.origin;
                                popup.options.position = directions.position;
                                popup.options.animation.open.effects = openEffects;
                            }
                            ul.removeAttr('aria-hidden');
                            that._configurePopupOverflow(popup, li);
                            popup.open();
                            that._initPopupScrolling(popup);
                        }
                    }, that.options.hoverDelay));
                });
                return that;
            },
            _configurePopupOverflow: function (popup, popupOpener) {
                var that = this;
                if (that.options.scrollable) {
                    that._wrapPopupElement(popup);
                    if (!popupOpener.attr('data-groupparent')) {
                        var groupId = new Date().getTime();
                        popupOpener.attr('data-groupparent', groupId);
                        popup.element.attr('data-group', groupId);
                    }
                }
            },
            _wrapPopupElement: function (popup) {
                if (!popup.element.parent().is(animationContainerSelector)) {
                    popup.wrapper = kendo.wrap(popup.element, popup.options.autosize).css({
                        overflow: 'hidden',
                        display: 'block',
                        position: 'absolute'
                    });
                }
            },
            _initPopupScrolling: function (popup, isHorizontal, skipMouseEvents) {
                var that = this;
                if (that.options.scrollable && popup.element[0].scrollHeight > popup.element[0].offsetHeight) {
                    that._initPopupScrollButtons(popup, isHorizontal, skipMouseEvents);
                }
            },
            _initPopupScrollButtons: function (popup, isHorizontal, skipMouseEvents) {
                var that = this;
                var scrollButtons = popup.wrapper.children(scrollButtonSelector);
                var animation = that.options.animation;
                var timeout = (animation && animation.open && animation.open.duration || 0) + DELAY;
                setTimeout(function () {
                    if (!scrollButtons.length) {
                        var backwardBtn = $(templates.scrollButton({ direction: isHorizontal ? 'left' : 'up' }));
                        var forwardBtn = $(templates.scrollButton({ direction: isHorizontal ? 'right' : 'down' }));
                        scrollButtons = backwardBtn.add(forwardBtn).appendTo(popup.wrapper);
                        that._initScrolling(popup.element, backwardBtn, forwardBtn, isHorizontal);
                        if (!skipMouseEvents) {
                            scrollButtons.on(MOUSEENTER + NS, function () {
                                var overflowWrapper = that._overflowWrapper();
                                $(getChildPopups(popup.element, overflowWrapper)).each(function (i, p) {
                                    var popupOpener = overflowWrapper.find(popupOpenerSelector(p.data(POPUP_ID_ATTR)));
                                    that.close(popupOpener);
                                });
                            }).on(MOUSELEAVE + NS, function () {
                                setTimeout(function () {
                                    if ($.isEmptyObject(that._openedPopups)) {
                                        that._closeParentPopups(popup.element);
                                    }
                                }, DELAY);
                            });
                        }
                    }
                    that._toggleScrollButtons(popup.element, scrollButtons.first(), scrollButtons.last(), isHorizontal);
                }, timeout);
            },
            _popupOpen: function (e) {
                e.sender.element.children('.' + FOCUSEDSTATE).removeClass(FOCUSEDSTATE);
                if (this.options.scrollable) {
                    this._setPopupHeight(e.sender);
                }
            },
            _setPopupHeight: function (popup, isFixed) {
                var popupElement = popup.element;
                var popups = popupElement.add(popupElement.parent(animationContainerSelector));
                popups.height(popupElement.hasClass(MENU) && this._initialHeight || '');
                var location = popup._location(isFixed);
                var windowHeight = $(window).height();
                var popupOuterHeight = location.height;
                var popupOffsetTop = isFixed ? 0 : Math.max(location.top, 0);
                var scrollTop = isFixed ? 0 : parentsScroll(this._overflowWrapper()[0], 'scrollTop');
                var bottomScrollbar = window.innerHeight - windowHeight;
                var maxHeight = windowHeight - kendo.getShadows(popupElement).bottom + bottomScrollbar;
                var canFit = maxHeight + scrollTop > popupOuterHeight + popupOffsetTop;
                if (!canFit) {
                    var height = Math.min(maxHeight, maxHeight - popupOffsetTop + scrollTop);
                    popups.css({
                        overflow: 'hidden',
                        height: height + 'px'
                    });
                }
            },
            close: function (items, dontClearClose) {
                var that = this;
                var overflowWrapper = that._overflowWrapper();
                var element = overflowWrapper || that.element;
                items = element.find(items);
                if (!items.length) {
                    items = element.find('>.k-item');
                }
                var hasChildPopupsHovered = function (currentPopup) {
                    var result = false;
                    if ($.isEmptyObject(that._openedPopups)) {
                        return result;
                    }
                    $(getChildPopups(currentPopup, overflowWrapper)).each(function (i, popup) {
                        result = !!that._openedPopups[popup.data(POPUP_ID_ATTR).toString()];
                        return !result;
                    });
                    return result;
                };
                var isPopupMouseLeaved = function (opener) {
                    var groupId = opener.data(POPUP_OPENER_ATTR);
                    return !overflowWrapper || !groupId || !that._openedPopups[groupId.toString()];
                };
                items.each(function () {
                    var li = $(this);
                    if (!dontClearClose && that._isRootItem(li)) {
                        that.clicked = false;
                    }
                    clearTimeout(li.data(TIMER));
                    li.data(TIMER, setTimeout(function () {
                        var popup = that._getPopup(li);
                        if (popup && (isPopupMouseLeaved(li) || that._forceClose)) {
                            if (!that._forceClose && hasChildPopupsHovered(popup.element)) {
                                return;
                            }
                            popup.close();
                            popup.element.attr('aria-hidden', true);
                            if (overflowWrapper) {
                                if (that._forceClose && items.last().is(li[0])) {
                                    delete that._forceClose;
                                }
                            }
                        }
                    }, that.options.hoverDelay));
                });
                return that;
            },
            _getPopup: function (li) {
                var that = this;
                var popup = li.find('.k-menu-group:not(.k-list-container):not(.k-calendar-container):first:visible').data(KENDOPOPUP);
                var overflowWrapper = that._overflowWrapper();
                if (!popup && overflowWrapper) {
                    var groupId = li.data(POPUP_OPENER_ATTR);
                    if (groupId) {
                        var popupElement = overflowWrapper.find(popupGroupSelector(groupId));
                        popup = popupElement.data(KENDOPOPUP);
                    }
                }
                return popup;
            },
            _toggleDisabled: function (items, enable) {
                this.element.find(items).each(function () {
                    $(this).toggleClass(DEFAULTSTATE, enable).toggleClass(DISABLEDSTATE, !enable).attr('aria-disabled', !enable);
                });
            },
            _toggleHover: function (e) {
                var target = $(kendo.eventTarget(e) || e.target).closest(allItemsSelector), isEnter = e.type == MOUSEENTER || MOUSEDOWN.indexOf(e.type) !== -1;
                if (!target.parents('li.' + DISABLEDSTATE).length) {
                    target.toggleClass(HOVERSTATE, isEnter || e.type == 'mousedown' || e.type == 'click');
                }
                this._removeHoverItem();
            },
            _preventClose: function () {
                if (!this.options.closeOnClick) {
                    this._closurePrevented = true;
                }
            },
            _checkActiveElement: function (e) {
                var that = this, hoverItem = $(e ? e.currentTarget : this._hoverItem()), target = that._findRootParent(hoverItem)[0];
                if (!this._closurePrevented) {
                    setTimeout(function () {
                        if (!document.hasFocus() || !contains(target, kendo._activeElement()) && e && !contains(target, e.currentTarget)) {
                            that.close(target);
                        }
                    }, 0);
                }
                this._closurePrevented = false;
            },
            _removeHoverItem: function () {
                var oldHoverItem = this._hoverItem();
                if (oldHoverItem && oldHoverItem.hasClass(FOCUSEDSTATE)) {
                    oldHoverItem.removeClass(FOCUSEDSTATE);
                    this._oldHoverItem = null;
                }
            },
            _updateClasses: function () {
                var element = this.element, nonContentGroupsSelector = '.k-menu-init div ul', items;
                element.removeClass('k-menu-horizontal k-menu-vertical');
                element.addClass('k-widget k-reset k-header k-menu-init ' + MENU).addClass(MENU + '-' + this.options.orientation);
                element.find('li > ul').filter(function () {
                    return !kendo.support.matchesSelector.call(this, nonContentGroupsSelector);
                }).addClass('k-group k-menu-group').attr('role', 'menu').attr('aria-hidden', element.is(':visible')).end().find('li > div').addClass('k-content').attr('tabindex', '-1');
                items = element.find('> li,.k-menu-group > li');
                element.removeClass('k-menu-init');
                items.each(function () {
                    updateItemClasses(this);
                });
            },
            _mouseenter: function (e) {
                var that = this;
                var element = $(e.currentTarget);
                var hasChildren = that._itemHasChildren(element);
                var popupId = element.data(POPUP_OPENER_ATTR) || element.parent().data(POPUP_ID_ATTR);
                var pointerTouch = isPointerTouch(e);
                if (popupId) {
                    that._openedPopups[popupId.toString()] = true;
                }
                if (e.delegateTarget != element.parents(menuSelector)[0] && e.delegateTarget != element.parents('.k-menu-scroll-wrapper,.k-popups-wrapper')[0]) {
                    return;
                }
                if ((!that.options.openOnClick || that.clicked) && !touch && !(pointerTouch && that._isRootItem(element.closest(allItemsSelector)))) {
                    if (!contains(e.currentTarget, e.relatedTarget) && hasChildren) {
                        that.open(element);
                    }
                }
                if (that.options.openOnClick && that.clicked || touch) {
                    element.siblings().each(proxy(function (_, sibling) {
                        that.close(sibling, true);
                    }, that));
                }
            },
            _mouseleave: function (e) {
                var that = this;
                var element = $(e.currentTarget);
                var popupOpener = element.data(POPUP_OPENER_ATTR);
                var hasChildren = element.children(animationContainerSelector).length || element.children(groupSelector).length || popupOpener;
                var $window = $(window);
                if (popupOpener) {
                    delete that._openedPopups[popupOpener.toString()];
                }
                if (element.parentsUntil(animationContainerSelector, '.k-list-container,.k-calendar-container')[0]) {
                    e.stopImmediatePropagation();
                    return;
                }
                if (!that.options.openOnClick && !touch && !isPointerTouch(e) && !contains(e.currentTarget, e.relatedTarget || e.target) && hasChildren && !contains(e.currentTarget, kendo._activeElement())) {
                    that.close(element);
                    return;
                }
                if (!e.toElement && !e.relatedTarget || e.clientX < 0 || e.clientY < 0 || e.clientY > $window.height() || e.clientX > $window.width()) {
                    that.close(element);
                }
            },
            _mouseenterPopup: function (e) {
                var that = this;
                var popupElement = $(e.currentTarget);
                if (popupElement.parent().is(animationContainerSelector)) {
                    return;
                }
                popupElement = popupElement.children('ul');
                var popupId = popupElement.data(POPUP_ID_ATTR);
                if (popupId) {
                    that._openedPopups[popupId.toString()] = true;
                }
            },
            _mouseleavePopup: function (e) {
                var that = this;
                var popupElement = $(e.currentTarget);
                if (!isPointerTouch(e) && popupElement.is(animationContainerSelector)) {
                    that._closePopups(popupElement.children('ul'));
                }
            },
            _closePopups: function (rootPopup) {
                var that = this;
                var overflowWrapper = that._overflowWrapper();
                var popupId = rootPopup.data(POPUP_ID_ATTR);
                if (popupId) {
                    delete that._openedPopups[popupId.toString()];
                    var groupParent = overflowWrapper.find(popupOpenerSelector(popupId));
                    setTimeout(function () {
                        if (that.options.openOnClick) {
                            that._closeChildPopups(rootPopup);
                        } else {
                            if ($.isEmptyObject(that._openedPopups)) {
                                var innerPopup = that._innerPopup(rootPopup);
                                that._closeParentPopups(innerPopup);
                            } else {
                                that.close(groupParent, true);
                            }
                        }
                    }, 0);
                }
            },
            _closeChildPopups: function (current) {
                var that = this;
                var overflowWrapper = that._overflowWrapper();
                $(getChildPopups(current, overflowWrapper)).each(function () {
                    var popupOpener = overflowWrapper.find(popupOpenerSelector(this.data(POPUP_ID_ATTR)));
                    that.close(popupOpener, true);
                });
            },
            _innerPopup: function (current) {
                var overflowWrapper = this._overflowWrapper();
                var popups = getChildPopups(current, overflowWrapper);
                return popups[popups.length - 1] || current;
            },
            _closeParentPopups: function (current) {
                var that = this;
                var overflowWrapper = that._overflowWrapper();
                var popupId = current.data(POPUP_ID_ATTR);
                var popupOpener = overflowWrapper.find(popupOpenerSelector(popupId));
                popupId = popupOpener.parent().data(POPUP_ID_ATTR);
                that.close(popupOpener, true);
                while (popupId && !that._openedPopups[popupId]) {
                    if (popupOpener.parent().is(menuSelector)) {
                        break;
                    }
                    popupOpener = overflowWrapper.find(popupOpenerSelector(popupId));
                    that.close(popupOpener, true);
                    popupId = popupOpener.parent().data(POPUP_ID_ATTR);
                }
            },
            _click: function (e) {
                var that = this, openHandle, options = that.options, target = $(kendo.eventTarget(e)), targetElement = target[0], nodeName = target[0] ? target[0].nodeName.toUpperCase() : '', formNode = nodeName == 'INPUT' || nodeName == 'SELECT' || nodeName == 'BUTTON' || nodeName == 'LABEL', link = target.closest('.' + LINK), element = target.closest(allItemsSelector), itemElement = element[0], href = link.attr('href'), childGroup, childGroupVisible, targetHref = target.attr('href'), sampleHref = $('<a href=\'#\' />').attr('href'), isLink = !!href && href !== sampleHref, isLocalLink = isLink && !!href.match(/^#/), isTargetLink = !!targetHref && targetHref !== sampleHref, overflowWrapper = that._overflowWrapper(), shouldCloseTheRootItem;
                while (targetElement && targetElement.parentNode != itemElement) {
                    targetElement = targetElement.parentNode;
                }
                if ($(targetElement).is(templateSelector)) {
                    return;
                }
                if (element.hasClass(DISABLEDSTATE)) {
                    e.preventDefault();
                    return;
                }
                if (!e.handled && that._triggerSelect(target, itemElement) && !formNode) {
                    e.preventDefault();
                }
                e.handled = true;
                childGroup = element.children(popupSelector);
                if (overflowWrapper) {
                    var childPopupId = element.data(POPUP_OPENER_ATTR);
                    if (childPopupId) {
                        childGroup = overflowWrapper.find(popupGroupSelector(childPopupId));
                    }
                }
                childGroupVisible = childGroup.is(':visible');
                shouldCloseTheRootItem = options.openOnClick && childGroupVisible && that._isRootItem(element);
                if (options.closeOnClick && (!isLink || isLocalLink) && (!childGroup.length || shouldCloseTheRootItem)) {
                    element.removeClass(HOVERSTATE).css('height');
                    that._oldHoverItem = that._findRootParent(element);
                    var item = that._parentsUntil(link, that.element, allItemsSelector);
                    that._forceClose = !!overflowWrapper;
                    that.close(item);
                    that.clicked = false;
                    if ('MSPointerUp'.indexOf(e.type) != -1) {
                        e.preventDefault();
                    }
                    return;
                }
                if (isLink && e.enterKey) {
                    link[0].click();
                }
                if ((!that._isRootItem(element) || !options.openOnClick) && !kendo.support.touch && !(isPointerTouch(e) && that._isRootItem(element.closest(allItemsSelector)))) {
                    return;
                }
                if (!isLink && !formNode && !isTargetLink) {
                    e.preventDefault();
                }
                that.clicked = true;
                openHandle = childGroup.is(':visible') ? CLOSE : OPEN;
                if (!options.closeOnClick && openHandle == CLOSE) {
                    return;
                }
                that[openHandle](element);
            },
            _parentsUntil: function (context, top, selector) {
                var overflowWrapper = this._overflowWrapper();
                if (!overflowWrapper) {
                    return context.parentsUntil(top, selector);
                } else {
                    var parents = overflowMenuParents(context, overflowWrapper);
                    var result = [];
                    $(parents).each(function () {
                        var parent = $(this);
                        if (parent.is(top)) {
                            return false;
                        }
                        if (parent.is(selector)) {
                            result.push(this);
                        }
                    });
                    return $(result);
                }
            },
            _triggerSelect: function (target, itemElement) {
                target = target.is('.k-link') ? target : target.closest('.k-link');
                var selectHandler = target.data('selectHandler'), itemSelectEventData;
                if (selectHandler) {
                    itemSelectEventData = this._getEventData(target);
                    selectHandler.call(this, itemSelectEventData);
                }
                var isSelectItemDefaultPrevented = itemSelectEventData && itemSelectEventData.isDefaultPrevented();
                var isSelectDefaultPrevented = this._triggerEvent({
                    item: itemElement,
                    type: SELECT
                });
                return isSelectItemDefaultPrevented || isSelectDefaultPrevented;
            },
            _getEventData: function (target) {
                var eventData = {
                    sender: this,
                    target: target,
                    _defaultPrevented: false,
                    preventDefault: function () {
                        this._defaultPrevented = true;
                    },
                    isDefaultPrevented: function () {
                        return this._defaultPrevented;
                    }
                };
                return eventData;
            },
            _documentClick: function (e) {
                var that = this;
                if (contains((that._overflowWrapper() || that.element)[0], e.target)) {
                    return;
                }
                that.clicked = false;
            },
            _focus: function (e) {
                var that = this, target = e.target, hoverItem = that._hoverItem(), active = activeElement();
                if (target != that.wrapper[0] && !$(target).is(':kendoFocusable')) {
                    e.stopPropagation();
                    $(target).closest('.k-content').closest('.k-menu-group').closest('.k-item').addClass(FOCUSEDSTATE);
                    that.wrapper.focus();
                    return;
                }
                if (active === e.currentTarget) {
                    if (hoverItem.length) {
                        that._moveHover([], hoverItem);
                    } else if (!that._oldHoverItem) {
                        that._moveHover([], that.wrapper.children().first());
                    }
                }
            },
            _keydown: function (e) {
                var that = this, key = e.keyCode, hoverItem = that._oldHoverItem, target, belongsToVertical, hasChildren, isRtl = kendo.support.isRtl(that.wrapper);
                if (e.target != e.currentTarget && key != keys.ESC) {
                    return;
                }
                if (!hoverItem) {
                    hoverItem = that._oldHoverItem = that._hoverItem();
                }
                belongsToVertical = that._itemBelongsToVertival(hoverItem);
                hasChildren = that._itemHasChildren(hoverItem);
                if (key == keys.RIGHT) {
                    target = that[isRtl ? '_itemLeft' : '_itemRight'](hoverItem, belongsToVertical, hasChildren);
                } else if (key == keys.LEFT) {
                    target = that[isRtl ? '_itemRight' : '_itemLeft'](hoverItem, belongsToVertical, hasChildren);
                } else if (key == keys.DOWN) {
                    target = that._itemDown(hoverItem, belongsToVertical, hasChildren);
                } else if (key == keys.UP) {
                    target = that._itemUp(hoverItem, belongsToVertical, hasChildren);
                } else if (key == keys.ESC) {
                    target = that._itemEsc(hoverItem, belongsToVertical);
                } else if (key == keys.ENTER || key == keys.SPACEBAR) {
                    target = hoverItem.children('.k-link');
                    if (target.length > 0) {
                        that._click({
                            target: target[0],
                            preventDefault: function () {
                            },
                            enterKey: true
                        });
                        that._moveHover(hoverItem, that._findRootParent(hoverItem));
                    }
                } else if (key == keys.TAB) {
                    target = that._findRootParent(hoverItem);
                    that._moveHover(hoverItem, target);
                    that._checkActiveElement();
                    return;
                }
                if (target && target[0]) {
                    e.preventDefault();
                    e.stopPropagation();
                }
            },
            _hoverItem: function () {
                return this.wrapper.find('.k-item.k-state-hover,.k-item.k-state-focused').filter(':visible');
            },
            _itemBelongsToVertival: function (item) {
                var menuIsVertical = this.wrapper.hasClass('k-menu-vertical');
                if (!item.length) {
                    return menuIsVertical;
                }
                return item.parent().hasClass('k-menu-group') || menuIsVertical;
            },
            _itemHasChildren: function (item) {
                if (!item || !item.length || !item[0].nodeType) {
                    return false;
                }
                return item.children('ul.k-menu-group, div.k-animation-container').length > 0 || !!item.data(POPUP_OPENER_ATTR) && !!this._overflowWrapper().children(popupGroupSelector(item.data(POPUP_OPENER_ATTR)));
            },
            _moveHover: function (item, nextItem) {
                var that = this, id = that._ariaId;
                if (item.length && nextItem.length) {
                    item.removeClass(FOCUSEDSTATE);
                }
                if (nextItem.length) {
                    if (nextItem[0].id) {
                        id = nextItem[0].id;
                    }
                    nextItem.addClass(FOCUSEDSTATE);
                    that._oldHoverItem = nextItem;
                    if (id) {
                        that.element.removeAttr('aria-activedescendant');
                        $('#' + id).removeAttr('id');
                        nextItem.attr('id', id);
                        that.element.attr('aria-activedescendant', id);
                    }
                    that._scrollToItem(nextItem);
                }
            },
            _findRootParent: function (item) {
                if (this._isRootItem(item)) {
                    return item;
                } else {
                    return this._parentsUntil(item, menuSelector, 'li.k-item').last();
                }
            },
            _isRootItem: function (item) {
                return item.parent().hasClass(MENU);
            },
            _itemRight: function (item, belongsToVertical, hasChildren) {
                var that = this, nextItem, parentItem, overflowWrapper;
                if (item.hasClass(DISABLEDSTATE)) {
                    return;
                }
                if (!belongsToVertical) {
                    nextItem = item.nextAll(nextSelector);
                    if (!nextItem.length) {
                        nextItem = item.prevAll(lastSelector);
                    }
                } else if (hasChildren) {
                    that.open(item);
                    nextItem = that._childPopupElement(item).children().first();
                } else if (that.options.orientation == 'horizontal') {
                    parentItem = that._findRootParent(item);
                    overflowWrapper = that._overflowWrapper();
                    if (overflowWrapper) {
                        var rootPopup = itemPopup(parentItem, overflowWrapper);
                        that._closeChildPopups(rootPopup);
                    }
                    that.close(parentItem);
                    nextItem = parentItem.nextAll(nextSelector);
                }
                if (nextItem && !nextItem.length) {
                    nextItem = that.wrapper.children('.k-item').first();
                } else if (!nextItem) {
                    nextItem = [];
                }
                that._moveHover(item, nextItem);
                return nextItem;
            },
            _itemLeft: function (item, belongsToVertical) {
                var that = this, nextItem, overflowWrapper;
                if (!belongsToVertical) {
                    nextItem = item.prevAll(nextSelector);
                    if (!nextItem.length) {
                        nextItem = item.nextAll(lastSelector);
                    }
                } else {
                    nextItem = item.parent().closest('.k-item');
                    overflowWrapper = that._overflowWrapper();
                    if (!nextItem.length && overflowWrapper) {
                        nextItem = popupParentItem(item.parent(), overflowWrapper);
                    }
                    that.close(nextItem);
                    if (that._isRootItem(nextItem) && that.options.orientation == 'horizontal') {
                        nextItem = nextItem.prevAll(nextSelector);
                    }
                }
                if (!nextItem.length) {
                    nextItem = that.wrapper.children('.k-item').last();
                }
                that._moveHover(item, nextItem);
                return nextItem;
            },
            _itemDown: function (item, belongsToVertical, hasChildren) {
                var that = this, nextItem;
                if (!belongsToVertical) {
                    if (!hasChildren || item.hasClass(DISABLEDSTATE)) {
                        return;
                    } else {
                        that.open(item);
                        nextItem = that._childPopupElement(item).children().first();
                    }
                } else {
                    nextItem = item.nextAll(nextSelector);
                }
                if (!nextItem.length && item.length) {
                    nextItem = item.parent().children().first();
                } else if (!item.length) {
                    nextItem = that.wrapper.children('.k-item').first();
                }
                that._moveHover(item, nextItem);
                return nextItem;
            },
            _itemUp: function (item, belongsToVertical) {
                var that = this, nextItem;
                if (!belongsToVertical) {
                    return;
                } else {
                    nextItem = item.prevAll(nextSelector);
                }
                if (!nextItem.length && item.length) {
                    nextItem = item.parent().children().last();
                } else if (!item.length) {
                    nextItem = that.wrapper.children('.k-item').last();
                }
                that._moveHover(item, nextItem);
                return nextItem;
            },
            _scrollToItem: function (item) {
                var that = this;
                if (that.options.scrollable && item && item.length) {
                    var ul = item.parent();
                    var isHorizontal = ul.hasClass(MENU) ? that.options.orientation == 'horizontal' : false;
                    var scrollDir = isHorizontal ? 'scrollLeft' : 'scrollTop';
                    var getSize = isHorizontal ? kendo._outerWidth : kendo._outerHeight;
                    var currentScrollOffset = ul[scrollDir]();
                    var itemSize = getSize(item);
                    var itemOffset = item[0][isHorizontal ? 'offsetLeft' : 'offsetTop'];
                    var ulSize = getSize(ul);
                    var scrollButtons = ul.siblings(scrollButtonSelector);
                    var scrollButtonSize = scrollButtons.length ? getSize(scrollButtons.first()) : 0;
                    var itemPosition;
                    if (currentScrollOffset + ulSize < itemOffset + itemSize + scrollButtonSize) {
                        itemPosition = itemOffset + itemSize - ulSize + scrollButtonSize;
                    } else if (currentScrollOffset > itemOffset - scrollButtonSize) {
                        itemPosition = itemOffset - scrollButtonSize;
                    }
                    if (!isNaN(itemPosition)) {
                        var scrolling = {};
                        scrolling[scrollDir] = itemPosition;
                        ul.finish().animate(scrolling, 'fast', 'linear', function () {
                            that._toggleScrollButtons(ul, scrollButtons.first(), scrollButtons.last(), isHorizontal);
                        });
                    }
                }
            },
            _itemEsc: function (item, belongsToVertical) {
                var that = this, nextItem;
                if (!belongsToVertical) {
                    return item;
                } else {
                    nextItem = item.parent().closest('.k-item');
                    that.close(nextItem);
                    that._moveHover(item, nextItem);
                }
                return nextItem;
            },
            _childPopupElement: function (item) {
                var popupElement = item.find('.k-menu-group');
                var wrapper = this._overflowWrapper();
                if (!popupElement.length && wrapper) {
                    popupElement = itemPopup(item, wrapper);
                }
                return popupElement;
            },
            _triggerEvent: function (e) {
                var that = this;
                return that.trigger(e.type, {
                    type: e.type,
                    item: e.item
                });
            },
            _focusHandler: function (e) {
                var that = this, item = $(kendo.eventTarget(e)).closest(allItemsSelector);
                if (item.hasClass(DISABLEDSTATE)) {
                    return;
                }
                setTimeout(function () {
                    that._moveHover([], item);
                    if (item.children('.k-content')[0]) {
                        item.parent().closest('.k-item').removeClass(FOCUSEDSTATE);
                    }
                }, 200);
            },
            _animations: function (options) {
                if (options && 'animation' in options && !options.animation) {
                    options.animation = {
                        open: { effects: {} },
                        close: {
                            hide: true,
                            effects: {}
                        }
                    };
                }
            }
        });
        extend(Menu, {
            renderItem: function (options) {
                options = extend({
                    menu: {},
                    group: {}
                }, options);
                var empty = templates.empty, item = options.item;
                return templates.item(extend(options, {
                    image: item.imageUrl ? templates.image : empty,
                    sprite: item.spriteCssClass ? templates.sprite : empty,
                    itemWrapper: templates.itemWrapper,
                    renderContent: Menu.renderContent,
                    arrow: item.items || item.content ? templates.arrow : empty,
                    subGroup: Menu.renderGroup
                }, rendering));
            },
            renderGroup: function (options) {
                return templates.group(extend({
                    renderItems: function (options) {
                        var html = '', i = 0, items = options.items, len = items ? items.length : 0, group = extend({ length: len }, options.group);
                        for (; i < len; i++) {
                            html += Menu.renderItem(extend(options, {
                                group: group,
                                item: extend({ index: i }, items[i])
                            }));
                        }
                        return html;
                    }
                }, options, rendering));
            },
            renderContent: function (options) {
                return templates.content(extend(options, rendering));
            }
        });
        var ContextMenu = Menu.extend({
            init: function (element, options) {
                var that = this;
                Menu.fn.init.call(that, element, options);
                that._marker = kendo.guid().substring(0, 8);
                that.target = $(that.options.target);
                that._popup();
                that._wire();
            },
            _initOverflow: function (options) {
                var that = this;
                if (options.scrollable && !that._overflowWrapper()) {
                    that._openedPopups = {};
                    that._popupsWrapper = (that.element.parent().is(animationContainerSelector) ? that.element.parent() : that.element).wrap('<div class=\'k-popups-wrapper ' + options.orientation + '\'></div>').parent();
                    if (that.options.orientation == 'horizontal') {
                        removeSpacesBetweenItems(that.element);
                    }
                    if (options.appendTo) {
                        options.appendTo.append(that._popupsWrapper);
                    }
                    that._initialHeight = that.element[0].style.height;
                    that._initialWidth = that.element[0].style.width;
                }
            },
            options: {
                name: 'ContextMenu',
                filter: null,
                showOn: 'contextmenu',
                orientation: 'vertical',
                alignToAnchor: false,
                target: 'body'
            },
            events: [
                OPEN,
                CLOSE,
                ACTIVATE,
                DEACTIVATE,
                SELECT
            ],
            setOptions: function (options) {
                var that = this;
                Menu.fn.setOptions.call(that, options);
                that.target.off(that.showOn + NS + that._marker, that._showProxy);
                if (that.userEvents) {
                    that.userEvents.destroy();
                }
                that.target = $(that.options.target);
                if (options.orientation && that.popup.wrapper[0]) {
                    that.popup.element.unwrap();
                }
                that._wire();
                Menu.fn.setOptions.call(this, options);
            },
            destroy: function () {
                var that = this;
                that.target.off(that.options.showOn + NS + that._marker);
                DOCUMENT_ELEMENT.off(kendo.support.mousedown + NS + that._marker, that._closeProxy);
                if (that.userEvents) {
                    that.userEvents.destroy();
                }
                Menu.fn.destroy.call(that);
            },
            open: function (x, y) {
                var that = this;
                x = $(x)[0];
                if (contains(that.element[0], $(x)[0]) || that._itemHasChildren($(x))) {
                    Menu.fn.open.call(that, x);
                } else {
                    if (that._triggerEvent({
                            item: that.element,
                            type: OPEN
                        }) === false) {
                        if (that.popup.visible() && that.options.filter) {
                            that.popup.close(true);
                            that.popup.element.kendoStop(true);
                        }
                        if (y !== undefined) {
                            var overflowWrapper = that._overflowWrapper();
                            if (overflowWrapper) {
                                var offset = overflowWrapper.offset();
                                x -= offset.left;
                                y -= offset.top;
                            }
                            that.popup.wrapper.hide();
                            that._configurePopupScrolling(x, y);
                            that.popup.open(x, y);
                        } else {
                            that.popup.options.anchor = (x ? x : that.popup.anchor) || that.target;
                            that.popup.element.kendoStop(true);
                            that._configurePopupScrolling();
                            that.popup.open();
                        }
                        DOCUMENT_ELEMENT.off(that.popup.downEvent, that.popup._mousedownProxy);
                        DOCUMENT_ELEMENT.on(kendo.support.mousedown + NS + that._marker, that._closeProxy);
                    }
                }
                return that;
            },
            _configurePopupScrolling: function (x, y) {
                var that = this;
                var popup = that.popup;
                var isHorizontal = that.options.orientation == 'horizontal';
                if (that.options.scrollable) {
                    that._wrapPopupElement(popup);
                    popup.element.parent().css({
                        position: '',
                        height: ''
                    });
                    popup.element.css({
                        visibility: 'hidden',
                        display: '',
                        position: ''
                    });
                    if (isHorizontal) {
                        that._setPopupWidth(popup, isNaN(x) ? undefined : {
                            isFixed: true,
                            x: x,
                            y: y
                        });
                    } else {
                        that._setPopupHeight(popup, isNaN(x) ? undefined : {
                            isFixed: true,
                            x: x,
                            y: y
                        });
                    }
                    popup.element.css({
                        visibility: '',
                        display: 'none',
                        position: 'absolute'
                    });
                    that._initPopupScrollButtons(popup, isHorizontal, true);
                    popup.element.siblings(scrollButtonSelector).hide();
                }
            },
            _setPopupWidth: function (popup, isFixed) {
                var popupElement = popup.element;
                var popups = popupElement.add(popupElement.parent(animationContainerSelector));
                popups.width(this._initialWidth || '');
                var location = popup._location(isFixed);
                var windowWidth = $(window).width();
                var popupOuterWidth = location.width;
                var popupOffsetLeft = Math.max(location.left, 0);
                var scrollLeft = isFixed ? 0 : parentsScroll(this._overflowWrapper()[0], 'scrollLeft');
                var shadow = kendo.getShadows(popupElement);
                var maxWidth = windowWidth - shadow.left - shadow.right;
                var canFit = maxWidth + scrollLeft > popupOuterWidth + popupOffsetLeft;
                if (!canFit) {
                    popups.css({
                        overflow: 'hidden',
                        width: maxWidth - popupOffsetLeft + scrollLeft + 'px'
                    });
                }
            },
            close: function () {
                var that = this;
                if (contains(that.element[0], $(arguments[0])[0]) || that._itemHasChildren(arguments[0])) {
                    Menu.fn.close.call(that, arguments[0]);
                } else {
                    if (that.popup.visible()) {
                        if (that._triggerEvent({
                                item: that.element,
                                type: CLOSE
                            }) === false) {
                            that.popup.close();
                            DOCUMENT_ELEMENT.off(kendo.support.mousedown + NS, that._closeProxy);
                            that.unbind(SELECT, that._closeTimeoutProxy);
                        }
                    }
                }
            },
            _showHandler: function (e) {
                var ev = e, offset, that = this, options = that.options;
                if (e.event) {
                    ev = e.event;
                    ev.pageX = e.x.location;
                    ev.pageY = e.y.location;
                }
                if (contains(that.element[0], e.relatedTarget || e.target)) {
                    return;
                }
                that._eventOrigin = ev;
                ev.preventDefault();
                ev.stopImmediatePropagation();
                that.element.find('.' + FOCUSEDSTATE).removeClass(FOCUSEDSTATE);
                if (options.filter && kendo.support.matchesSelector.call(ev.currentTarget, options.filter) || !options.filter) {
                    if (options.alignToAnchor) {
                        that.popup.options.anchor = ev.currentTarget;
                        that.open(ev.currentTarget);
                    } else {
                        that.popup.options.anchor = ev.currentTarget;
                        if (that._targetChild) {
                            offset = that.target.offset();
                            that.open(ev.pageX - offset.left, ev.pageY - offset.top);
                        } else {
                            that.open(ev.pageX, ev.pageY);
                        }
                    }
                }
            },
            _closeHandler: function (e) {
                var that = this, target = $(e.relatedTarget || e.target), sameTarget = target.closest(that.target.selector)[0] == that.target[0], item = target.closest(itemSelector), children = that._itemHasChildren(item), overflowWrapper = that._overflowWrapper(), containment = contains(that.element[0], target[0]) || overflowWrapper && contains(overflowWrapper[0], target[0]);
                that._eventOrigin = e;
                var normalClick = e.which !== 3;
                if (that.popup.visible() && (normalClick && sameTarget || !sameTarget) && (that.options.closeOnClick && !children && containment || !containment)) {
                    if (containment) {
                        this.unbind(SELECT, this._closeTimeoutProxy);
                        that.bind(SELECT, that._closeTimeoutProxy);
                    } else {
                        that.close();
                    }
                }
            },
            _wire: function () {
                var that = this, options = that.options, target = that.target;
                that._showProxy = proxy(that._showHandler, that);
                that._closeProxy = proxy(that._closeHandler, that);
                that._closeTimeoutProxy = proxy(that.close, that);
                if (target[0]) {
                    if (kendo.support.mobileOS && options.showOn == 'contextmenu') {
                        that.userEvents = new kendo.UserEvents(target, {
                            filter: options.filter,
                            allowSelection: false
                        });
                        target.on(options.showOn + NS + that._marker, false);
                        that.userEvents.bind('hold', that._showProxy);
                    } else {
                        if (options.filter) {
                            target.on(options.showOn + NS + that._marker, options.filter, that._showProxy);
                        } else {
                            target.on(options.showOn + NS + that._marker, that._showProxy);
                        }
                    }
                }
            },
            _triggerEvent: function (e) {
                var that = this, anchor = $(that.popup.options.anchor)[0], origin = that._eventOrigin;
                that._eventOrigin = undefined;
                return that.trigger(e.type, extend({
                    type: e.type,
                    item: e.item || this.element[0],
                    target: anchor
                }, origin ? { event: origin } : {}));
            },
            _popup: function () {
                var that = this;
                var overflowWrapper = that._overflowWrapper();
                that._triggerProxy = proxy(that._triggerEvent, that);
                that.popup = that.element.addClass('k-context-menu').kendoPopup({
                    anchor: that.target || 'body',
                    copyAnchorStyles: that.options.copyAnchorStyles,
                    collision: that.options.popupCollision || 'fit',
                    animation: that.options.animation,
                    activate: that._triggerProxy,
                    deactivate: that._triggerProxy,
                    appendTo: overflowWrapper || that.options.appendTo,
                    close: !overflowWrapper ? $.noop : function (e) {
                        $(getChildPopups(e.sender.element, overflowWrapper)).each(function (i, p) {
                            var popup = p.data(KENDOPOPUP);
                            if (popup) {
                                popup.close(true);
                            }
                        });
                    }
                }).data(KENDOPOPUP);
                that._targetChild = contains(that.target[0], that.popup.element[0]);
            }
        });
        ui.plugin(Menu);
        ui.plugin(ContextMenu);
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.window', [
        'kendo.draganddrop',
        'kendo.popup'
    ], f);
}(function () {
    var __meta__ = {
        id: 'window',
        name: 'Window',
        category: 'web',
        description: 'The Window widget displays content in a modal or non-modal HTML window.',
        depends: [
            'draganddrop',
            'popup'
        ],
        features: [{
                id: 'window-fx',
                name: 'Animation',
                description: 'Support for animation',
                depends: ['fx']
            }]
    };
    (function ($, undefined) {
        var kendo = window.kendo, Widget = kendo.ui.Widget, TabKeyTrap = kendo.ui.Popup.TabKeyTrap, Draggable = kendo.ui.Draggable, isPlainObject = $.isPlainObject, activeElement = kendo._activeElement, outerWidth = kendo._outerWidth, outerHeight = kendo._outerHeight, proxy = $.proxy, extend = $.extend, each = $.each, template = kendo.template, BODY = 'body', templates, NS = '.kendoWindow', KWINDOW = '.k-window', KWINDOWTITLE = '.k-window-title', KWINDOWTITLEBAR = KWINDOWTITLE + 'bar', KWINDOWCONTENT = '.k-window-content', KWINDOWRESIZEHANDLES = '.k-resize-handle', KOVERLAY = '.k-overlay', KCONTENTFRAME = 'k-content-frame', LOADING = 'k-i-loading', KHOVERSTATE = 'k-state-hover', KFOCUSEDSTATE = 'k-state-focused', MAXIMIZEDSTATE = 'k-window-maximized', VISIBLE = ':visible', HIDDEN = 'hidden', CURSOR = 'cursor', OPEN = 'open', ACTIVATE = 'activate', DEACTIVATE = 'deactivate', CLOSE = 'close', REFRESH = 'refresh', MINIMIZE = 'minimize', MAXIMIZE = 'maximize', RESIZESTART = 'resizeStart', RESIZE = 'resize', RESIZEEND = 'resizeEnd', DRAGSTART = 'dragstart', DRAGEND = 'dragend', ERROR = 'error', OVERFLOW = 'overflow', DATADOCOVERFLOWRULE = 'original-overflow-rule', ZINDEX = 'zIndex', MINIMIZE_MAXIMIZE = '.k-window-actions .k-i-window-minimize,.k-window-actions .k-i-window-maximize', KPIN = '.k-i-pin', KUNPIN = '.k-i-unpin', PIN_UNPIN = KPIN + ',' + KUNPIN, TITLEBAR_BUTTONS = '.k-window-titlebar .k-window-action', REFRESHICON = '.k-window-titlebar .k-i-refresh', zero = /^0[a-z]*$/i, isLocalUrl = kendo.isLocalUrl;
        function defined(x) {
            return typeof x != 'undefined';
        }
        function constrain(value, low, high) {
            return Math.max(Math.min(parseInt(value, 10), high === Infinity ? high : parseInt(high, 10)), parseInt(low, 10));
        }
        function executableScript() {
            return !this.type || this.type.toLowerCase().indexOf('script') >= 0;
        }
        var Window = Widget.extend({
            init: function (element, options) {
                var that = this, wrapper, offset = {}, visibility, display, position, isVisible = false, content, windowContent, suppressActions = options && options.actions && !options.actions.length, id;
                Widget.fn.init.call(that, element, options);
                options = that.options;
                position = options.position;
                element = that.element;
                content = options.content;
                if (suppressActions) {
                    options.actions = [];
                }
                that.appendTo = $(options.appendTo);
                if (content && !isPlainObject(content)) {
                    content = options.content = { url: content };
                }
                element.find('script').filter(executableScript).remove();
                if (!element.parent().is(that.appendTo) && (position.top === undefined || position.left === undefined)) {
                    if (element.is(VISIBLE)) {
                        offset = element.offset();
                        isVisible = true;
                    } else {
                        visibility = element.css('visibility');
                        display = element.css('display');
                        element.css({
                            visibility: HIDDEN,
                            display: ''
                        });
                        offset = element.offset();
                        element.css({
                            visibility: visibility,
                            display: display
                        });
                    }
                    if (position.top === undefined) {
                        position.top = offset.top;
                    }
                    if (position.left === undefined) {
                        position.left = offset.left;
                    }
                }
                if (!defined(options.visible) || options.visible === null) {
                    options.visible = element.is(VISIBLE);
                }
                wrapper = that.wrapper = element.closest(KWINDOW);
                if (!element.is('.k-content') || !wrapper[0]) {
                    element.addClass('k-window-content k-content');
                    that._createWindow(element, options);
                    wrapper = that.wrapper = element.closest(KWINDOW);
                    that._dimensions();
                }
                that._position();
                if (content) {
                    that.refresh(content);
                }
                if (options.visible) {
                    that.toFront();
                }
                windowContent = wrapper.children(KWINDOWCONTENT);
                that._tabindex(windowContent);
                if (options.visible && options.modal) {
                    that._overlay(wrapper.is(VISIBLE)).css({ opacity: 0.5 });
                }
                wrapper.on('mouseenter' + NS, TITLEBAR_BUTTONS, proxy(that._buttonEnter, that)).on('mouseleave' + NS, TITLEBAR_BUTTONS, proxy(that._buttonLeave, that)).on('click' + NS, '> ' + TITLEBAR_BUTTONS, proxy(that._windowActionHandler, that));
                windowContent.on('keydown' + NS, proxy(that._keydown, that)).on('focus' + NS, proxy(that._focus, that)).on('blur' + NS, proxy(that._blur, that));
                this._resizable();
                this._draggable();
                if (options.pinned && isVisible) {
                    that.pin();
                }
                id = element.attr('id');
                if (id) {
                    id = id + '_wnd_title';
                    wrapper.children(KWINDOWTITLEBAR).children(KWINDOWTITLE).attr('id', id);
                    windowContent.attr({
                        'role': 'dialog',
                        'aria-labelledby': id
                    });
                }
                wrapper.add(wrapper.children('.k-resize-handle,' + KWINDOWTITLEBAR)).on('mousedown' + NS, proxy(that.toFront, that));
                that.touchScroller = kendo.touchScroller(element);
                that._resizeHandler = proxy(that._onDocumentResize, that);
                that._marker = kendo.guid().substring(0, 8);
                $(window).on('resize' + NS + that._marker, that._resizeHandler);
                if (options.visible) {
                    that.trigger(OPEN);
                    that.trigger(ACTIVATE);
                }
                kendo.notify(that);
                if (this.options.modal) {
                    this._tabKeyTrap = new TabKeyTrap(wrapper);
                    this._tabKeyTrap.trap();
                    this._tabKeyTrap.shouldTrap = function () {
                        return windowContent.data('isFront');
                    };
                }
            },
            _buttonEnter: function (e) {
                $(e.currentTarget).addClass(KHOVERSTATE);
            },
            _buttonLeave: function (e) {
                $(e.currentTarget).removeClass(KHOVERSTATE);
            },
            _focus: function () {
                this.wrapper.addClass(KFOCUSEDSTATE);
            },
            _blur: function () {
                this.wrapper.removeClass(KFOCUSEDSTATE);
            },
            _dimensions: function () {
                var wrapper = this.wrapper;
                var options = this.options;
                var width = options.width;
                var height = options.height;
                var maxHeight = options.maxHeight;
                var dimensions = [
                    'minWidth',
                    'minHeight',
                    'maxWidth',
                    'maxHeight'
                ];
                this.title(options.title);
                for (var i = 0; i < dimensions.length; i++) {
                    var value = options[dimensions[i]] || '';
                    if (value != Infinity) {
                        wrapper.css(dimensions[i], value);
                    }
                }
                if (maxHeight != Infinity) {
                    this.element.css('maxHeight', maxHeight);
                }
                if (width) {
                    if (isNaN(width) && width.toString().indexOf('px') < 0) {
                        wrapper.width(width);
                    } else {
                        wrapper.width(constrain(width, options.minWidth, options.maxWidth));
                    }
                } else {
                    wrapper.width('');
                }
                if (height) {
                    if (isNaN(height) && height.toString().indexOf('px') < 0) {
                        wrapper.height(height);
                    } else {
                        wrapper.height(constrain(height, options.minHeight, options.maxHeight));
                    }
                } else {
                    wrapper.height('');
                }
                if (!options.visible) {
                    wrapper.hide();
                }
            },
            _position: function () {
                var wrapper = this.wrapper, position = this.options.position;
                if (position.top === 0) {
                    position.top = position.top.toString();
                }
                if (position.left === 0) {
                    position.left = position.left.toString();
                }
                wrapper.css({
                    top: position.top || '',
                    left: position.left || ''
                });
            },
            _animationOptions: function (id) {
                var animation = this.options.animation;
                var basicAnimation = {
                    open: { effects: {} },
                    close: {
                        hide: true,
                        effects: {}
                    }
                };
                return animation && animation[id] || basicAnimation[id];
            },
            _resize: function () {
                kendo.resize(this.element.children());
            },
            _resizable: function () {
                var resizable = this.options.resizable;
                var wrapper = this.wrapper;
                if (this.resizing) {
                    wrapper.off('dblclick' + NS).children(KWINDOWRESIZEHANDLES).remove();
                    this.resizing.destroy();
                    this.resizing = null;
                }
                if (resizable) {
                    wrapper.on('dblclick' + NS, KWINDOWTITLEBAR, proxy(function (e) {
                        if (!$(e.target).closest('.k-window-action').length) {
                            this.toggleMaximization();
                        }
                    }, this));
                    each('n e s w se sw ne nw'.split(' '), function (index, handler) {
                        wrapper.append(templates.resizeHandle(handler));
                    });
                    this.resizing = new WindowResizing(this);
                }
                wrapper = null;
            },
            _draggable: function () {
                var draggable = this.options.draggable;
                if (this.dragging) {
                    this.dragging.destroy();
                    this.dragging = null;
                }
                if (draggable) {
                    this.dragging = new WindowDragging(this, draggable.dragHandle || KWINDOWTITLEBAR);
                }
            },
            _actions: function () {
                var options = this.options;
                var actions = options.actions;
                var pinned = options.pinned;
                var titlebar = this.wrapper.children(KWINDOWTITLEBAR);
                var container = titlebar.find('.k-window-actions');
                var windowSpecificCommands = [
                    'maximize',
                    'minimize'
                ];
                actions = $.map(actions, function (action) {
                    action = pinned && action.toLowerCase() === 'pin' ? 'unpin' : action;
                    return { name: windowSpecificCommands.indexOf(action.toLowerCase()) > -1 ? 'window-' + action : action };
                });
                container.html(kendo.render(templates.action, actions));
            },
            setOptions: function (options) {
                var cachedOptions = JSON.parse(JSON.stringify(options));
                extend(options.position, this.options.position);
                extend(options.position, cachedOptions.position);
                Widget.fn.setOptions.call(this, options);
                var scrollable = this.options.scrollable !== false;
                this.restore();
                this._dimensions();
                this._position();
                this._resizable();
                this._draggable();
                this._actions();
                if (typeof options.modal !== 'undefined') {
                    var visible = this.options.visible !== false;
                    this._overlay(options.modal && visible);
                }
                this.element.css(OVERFLOW, scrollable ? '' : 'hidden');
            },
            events: [
                OPEN,
                ACTIVATE,
                DEACTIVATE,
                CLOSE,
                MINIMIZE,
                MAXIMIZE,
                REFRESH,
                RESIZESTART,
                RESIZE,
                RESIZEEND,
                DRAGSTART,
                DRAGEND,
                ERROR
            ],
            options: {
                name: 'Window',
                animation: {
                    open: {
                        effects: {
                            zoom: { direction: 'in' },
                            fade: { direction: 'in' }
                        },
                        duration: 350
                    },
                    close: {
                        effects: {
                            zoom: {
                                direction: 'out',
                                properties: { scale: 0.7 }
                            },
                            fade: { direction: 'out' }
                        },
                        duration: 350,
                        hide: true
                    }
                },
                title: '',
                actions: ['Close'],
                autoFocus: true,
                modal: false,
                resizable: true,
                draggable: true,
                minWidth: 90,
                minHeight: 50,
                maxWidth: Infinity,
                maxHeight: Infinity,
                pinned: false,
                scrollable: true,
                position: {},
                content: null,
                visible: null,
                height: null,
                width: null,
                appendTo: 'body',
                isMaximized: false,
                isMinimized: false
            },
            _closable: function () {
                return $.inArray('close', $.map(this.options.actions, function (x) {
                    return x.toLowerCase();
                })) > -1;
            },
            _keydown: function (e) {
                var that = this, options = that.options, keys = kendo.keys, keyCode = e.keyCode, wrapper = that.wrapper, offset, handled, distance = 10, isMaximized = that.options.isMaximized, newWidth, newHeight, w, h;
                if (keyCode == keys.ESC && that._closable()) {
                    that._close(false);
                }
                if (e.target != e.currentTarget || that._closing) {
                    return;
                }
                if (options.draggable && !e.ctrlKey && !isMaximized) {
                    offset = kendo.getOffset(wrapper);
                    if (keyCode == keys.UP) {
                        handled = wrapper.css('top', offset.top - distance);
                    } else if (keyCode == keys.DOWN) {
                        handled = wrapper.css('top', offset.top + distance);
                    } else if (keyCode == keys.LEFT) {
                        handled = wrapper.css('left', offset.left - distance);
                    } else if (keyCode == keys.RIGHT) {
                        handled = wrapper.css('left', offset.left + distance);
                    }
                }
                if (options.resizable && e.ctrlKey && !isMaximized) {
                    if (keyCode == keys.UP) {
                        handled = true;
                        newHeight = wrapper.height() - distance;
                    } else if (keyCode == keys.DOWN) {
                        handled = true;
                        newHeight = wrapper.height() + distance;
                    }
                    if (keyCode == keys.LEFT) {
                        handled = true;
                        newWidth = wrapper.width() - distance;
                    } else if (keyCode == keys.RIGHT) {
                        handled = true;
                        newWidth = wrapper.width() + distance;
                    }
                    if (handled) {
                        w = constrain(newWidth, options.minWidth, options.maxWidth);
                        h = constrain(newHeight, options.minHeight, options.maxHeight);
                        if (!isNaN(w)) {
                            wrapper.width(w);
                            that.options.width = w + 'px';
                        }
                        if (!isNaN(h)) {
                            wrapper.height(h);
                            that.options.height = h + 'px';
                        }
                        that.resize();
                    }
                }
                if (handled) {
                    e.preventDefault();
                }
            },
            _overlay: function (visible) {
                var overlay = this.appendTo.children(KOVERLAY), wrapper = this.wrapper;
                if (!overlay.length) {
                    overlay = $('<div class=\'k-overlay\' />');
                }
                overlay.insertBefore(wrapper[0]).toggle(visible).css(ZINDEX, parseInt(wrapper.css(ZINDEX), 10) - 1);
                return overlay;
            },
            _actionForIcon: function (icon) {
                var iconClass = /\bk-i(-\w+)+\b/.exec(icon[0].className)[0];
                return {
                    'k-i-close': '_close',
                    'k-i-window-maximize': 'maximize',
                    'k-i-window-minimize': 'minimize',
                    'k-i-window-restore': 'restore',
                    'k-i-refresh': 'refresh',
                    'k-i-pin': 'pin',
                    'k-i-unpin': 'unpin'
                }[iconClass];
            },
            _windowActionHandler: function (e) {
                if (this._closing) {
                    return;
                }
                var icon = $(e.target).closest('.k-window-action').find('.k-icon');
                var action = this._actionForIcon(icon);
                if (action) {
                    e.preventDefault();
                    this[action]();
                    return false;
                }
            },
            _modals: function () {
                var that = this;
                var zStack = $(KWINDOW).filter(function () {
                    var dom = $(this);
                    var object = that._object(dom);
                    var options = object && object.options;
                    return options && options.modal && options.visible && options.appendTo === that.options.appendTo && dom.is(VISIBLE);
                }).sort(function (a, b) {
                    return +$(a).css('zIndex') - +$(b).css('zIndex');
                });
                that = null;
                return zStack;
            },
            _object: function (element) {
                var content = element.children(KWINDOWCONTENT);
                var widget = kendo.widgetInstance(content);
                if (widget) {
                    return widget;
                }
                return undefined;
            },
            center: function () {
                var that = this, position = that.options.position, wrapper = that.wrapper, documentWindow = $(window), scrollTop = 0, scrollLeft = 0, newTop, newLeft;
                if (that.options.isMaximized) {
                    return that;
                }
                if (!that.options.pinned) {
                    scrollTop = documentWindow.scrollTop();
                    scrollLeft = documentWindow.scrollLeft();
                }
                newLeft = scrollLeft + Math.max(0, (documentWindow.width() - wrapper.width()) / 2);
                newTop = scrollTop + Math.max(0, (documentWindow.height() - wrapper.height() - parseInt(wrapper.css('paddingTop'), 10)) / 2);
                wrapper.css({
                    left: newLeft,
                    top: newTop
                });
                position.top = newTop;
                position.left = newLeft;
                return that;
            },
            title: function (text) {
                var that = this, wrapper = that.wrapper, options = that.options, titleBar = wrapper.children(KWINDOWTITLEBAR), title = titleBar.children(KWINDOWTITLE), titleBarHeight;
                if (!arguments.length) {
                    return title.html();
                }
                if (text === false) {
                    wrapper.addClass('k-window-titleless');
                    titleBar.remove();
                } else {
                    if (!titleBar.length) {
                        wrapper.prepend(templates.titlebar(options));
                        that._actions();
                        titleBar = wrapper.children(KWINDOWTITLEBAR);
                    } else {
                        title.html(kendo.htmlEncode(text));
                    }
                    titleBarHeight = parseInt(outerHeight(titleBar), 10);
                    wrapper.css('padding-top', titleBarHeight);
                    titleBar.css('margin-top', -titleBarHeight);
                }
                that.options.title = text;
                return that;
            },
            content: function (html, data) {
                var content = this.wrapper.children(KWINDOWCONTENT), scrollContainer = content.children('.km-scroll-container');
                content = scrollContainer[0] ? scrollContainer : content;
                if (!defined(html)) {
                    return content.html();
                }
                this.angular('cleanup', function () {
                    return { elements: content.children() };
                });
                kendo.destroy(this.element.children());
                content.empty().html(html);
                this.angular('compile', function () {
                    var a = [];
                    for (var i = content.length; --i >= 0;) {
                        a.push({ dataItem: data });
                    }
                    return {
                        elements: content.children(),
                        data: a
                    };
                });
                return this;
            },
            open: function () {
                var that = this, wrapper = that.wrapper, options = that.options, showOptions = this._animationOptions('open'), contentElement = wrapper.children(KWINDOWCONTENT), overlay, otherModalsVisible, doc = $(document);
                if (!that.trigger(OPEN)) {
                    if (that._closing) {
                        wrapper.kendoStop(true, true);
                    }
                    that._closing = false;
                    that.toFront();
                    if (options.autoFocus) {
                        that.element.focus();
                    }
                    options.visible = true;
                    if (options.modal) {
                        otherModalsVisible = !!that._modals().length;
                        overlay = that._overlay(otherModalsVisible);
                        overlay.kendoStop(true, true);
                        if (showOptions.duration && kendo.effects.Fade && !otherModalsVisible) {
                            var overlayFx = kendo.fx(overlay).fadeIn();
                            overlayFx.duration(showOptions.duration || 0);
                            overlayFx.endValue(0.5);
                            overlayFx.play();
                        } else {
                            overlay.css('opacity', 0.5);
                        }
                        overlay.show();
                        $(window).on('focus', function () {
                            if (contentElement.data('isFront')) {
                                that.element.focus();
                            }
                        });
                    }
                    if (!wrapper.is(VISIBLE)) {
                        contentElement.css(OVERFLOW, HIDDEN);
                        wrapper.show().kendoStop().kendoAnimate({
                            effects: showOptions.effects,
                            duration: showOptions.duration,
                            complete: proxy(this._activate, this)
                        });
                    }
                }
                if (options.isMaximized) {
                    that._documentScrollTop = doc.scrollTop();
                    that._documentScrollLeft = doc.scrollLeft();
                    that._stopDocumentScrolling();
                }
                if (options.pinned && !that._isPinned) {
                    that.pin();
                }
                return that;
            },
            _activate: function () {
                var scrollable = this.options.scrollable !== false;
                if (this.options.autoFocus) {
                    this.element.focus();
                }
                this.element.css(OVERFLOW, scrollable ? '' : 'hidden');
                kendo.resize(this.element.children());
                this.trigger(ACTIVATE);
            },
            _removeOverlay: function (suppressAnimation) {
                var modals = this._modals();
                var options = this.options;
                var hideOverlay = options.modal && !modals.length;
                var overlay = options.modal ? this._overlay(true) : $(undefined);
                var hideOptions = this._animationOptions('close');
                if (hideOverlay) {
                    if (!suppressAnimation && hideOptions.duration && kendo.effects.Fade) {
                        var overlayFx = kendo.fx(overlay).fadeOut();
                        overlayFx.duration(hideOptions.duration || 0);
                        overlayFx.startValue(0.5);
                        overlayFx.play();
                    } else {
                        this._overlay(false).remove();
                    }
                } else if (modals.length) {
                    this._object(modals.last())._overlay(true);
                }
            },
            _close: function (systemTriggered) {
                var that = this, wrapper = that.wrapper, options = that.options, showOptions = this._animationOptions('open'), hideOptions = this._animationOptions('close'), doc = $(document);
                if (wrapper.is(VISIBLE) && !that.trigger(CLOSE, { userTriggered: !systemTriggered })) {
                    if (that._closing) {
                        return;
                    }
                    that._closing = true;
                    options.visible = false;
                    $(KWINDOW).each(function (i, element) {
                        var contentElement = $(element).children(KWINDOWCONTENT);
                        if (element != wrapper && contentElement.find('> .' + KCONTENTFRAME).length > 0) {
                            contentElement.children(KOVERLAY).remove();
                        }
                    });
                    this._removeOverlay();
                    wrapper.kendoStop().kendoAnimate({
                        effects: hideOptions.effects || showOptions.effects,
                        reverse: hideOptions.reverse === true,
                        duration: hideOptions.duration,
                        complete: proxy(this._deactivate, this)
                    });
                }
                if (that.options.isMaximized) {
                    that._enableDocumentScrolling();
                    if (that._documentScrollTop && that._documentScrollTop > 0) {
                        doc.scrollTop(that._documentScrollTop);
                    }
                    if (that._documentScrollLeft && that._documentScrollLeft > 0) {
                        doc.scrollLeft(that._documentScrollLeft);
                    }
                }
            },
            _deactivate: function () {
                var that = this;
                that.wrapper.hide().css('opacity', '');
                that.trigger(DEACTIVATE);
                if (that.options.modal) {
                    var lastModal = that._object(that._modals().last());
                    if (lastModal) {
                        lastModal.toFront();
                    }
                }
            },
            close: function () {
                this._close(true);
                return this;
            },
            _actionable: function (element) {
                return $(element).is(TITLEBAR_BUTTONS + ',' + TITLEBAR_BUTTONS + ' .k-icon,:input,a');
            },
            _shouldFocus: function (target) {
                var active = activeElement(), element = this.element;
                return this.options.autoFocus && !$(active).is(element) && !this._actionable(target) && (!element.find(active).length || !element.find(target).length);
            },
            toFront: function (e) {
                var that = this, wrapper = that.wrapper, currentWindow = wrapper[0], zIndex = +wrapper.css(ZINDEX), originalZIndex = zIndex, target = e && e.target || null;
                $(KWINDOW).each(function (i, element) {
                    var windowObject = $(element), zIndexNew = windowObject.css(ZINDEX), contentElement = windowObject.children(KWINDOWCONTENT);
                    if (!isNaN(zIndexNew)) {
                        zIndex = Math.max(+zIndexNew, zIndex);
                    }
                    contentElement.data('isFront', element == currentWindow);
                    if (element != currentWindow && contentElement.find('> .' + KCONTENTFRAME).length > 0) {
                        contentElement.append(templates.overlay);
                    }
                });
                if (!wrapper[0].style.zIndex || originalZIndex < zIndex) {
                    wrapper.css(ZINDEX, zIndex + 2);
                }
                that.element.find('> .k-overlay').remove();
                if (that._shouldFocus(target)) {
                    that.element.focus();
                    var scrollTop = $(window).scrollTop(), windowTop = parseInt(wrapper.position().top, 10);
                    if (!that.options.pinned && windowTop > 0 && windowTop < scrollTop) {
                        if (scrollTop > 0) {
                            $(window).scrollTop(windowTop);
                        } else {
                            wrapper.css('top', scrollTop);
                        }
                    }
                }
                wrapper = null;
                return that;
            },
            toggleMaximization: function () {
                if (this._closing) {
                    return this;
                }
                return this[this.options.isMaximized ? 'restore' : 'maximize']();
            },
            restore: function () {
                var that = this;
                var options = that.options;
                var minHeight = options.minHeight;
                var restoreOptions = that.restoreOptions;
                var doc = $(document);
                if (!options.isMaximized && !options.isMinimized) {
                    return that;
                }
                if (minHeight && minHeight != Infinity) {
                    that.wrapper.css('min-height', minHeight);
                }
                that.wrapper.css({
                    position: options.pinned ? 'fixed' : 'absolute',
                    left: restoreOptions.left,
                    top: restoreOptions.top,
                    width: restoreOptions.width,
                    height: restoreOptions.height
                }).removeClass(MAXIMIZEDSTATE).find('.k-window-content,.k-resize-handle').show().end().find('.k-window-titlebar .k-i-window-restore').parent().remove().end().end().find(MINIMIZE_MAXIMIZE).parent().show().end().end().find(PIN_UNPIN).parent().show();
                that.options.width = restoreOptions.width;
                that.options.height = restoreOptions.height;
                that._enableDocumentScrolling();
                if (this._documentScrollTop && this._documentScrollTop > 0) {
                    doc.scrollTop(this._documentScrollTop);
                }
                if (this._documentScrollLeft && this._documentScrollLeft > 0) {
                    doc.scrollLeft(this._documentScrollLeft);
                }
                options.isMaximized = options.isMinimized = false;
                that.resize();
                return that;
            },
            _sizingAction: function (actionId, callback) {
                var that = this, wrapper = that.wrapper, style = wrapper[0].style, options = that.options;
                if (options.isMaximized || options.isMinimized) {
                    return that;
                }
                that.restoreOptions = {
                    width: style.width,
                    height: style.height
                };
                wrapper.children(KWINDOWRESIZEHANDLES).hide().end().children(KWINDOWTITLEBAR).find(MINIMIZE_MAXIMIZE).parent().hide().eq(0).before(templates.action({ name: 'window-restore' }));
                callback.call(that);
                that.wrapper.children(KWINDOWTITLEBAR).find(PIN_UNPIN).parent().toggle(actionId !== 'maximize');
                that.trigger(actionId);
                return that;
            },
            maximize: function () {
                this._sizingAction('maximize', function () {
                    var that = this, wrapper = that.wrapper, position = wrapper.position(), doc = $(document);
                    extend(that.restoreOptions, {
                        left: position.left,
                        top: position.top
                    });
                    wrapper.css({
                        left: 0,
                        top: 0,
                        position: 'fixed'
                    }).addClass(MAXIMIZEDSTATE);
                    this._documentScrollTop = doc.scrollTop();
                    this._documentScrollLeft = doc.scrollLeft();
                    that._stopDocumentScrolling();
                    that.options.isMaximized = true;
                    that._onDocumentResize();
                });
                return this;
            },
            _stopDocumentScrolling: function () {
                var that = this;
                var $body = $('body');
                that._storeOverflowRule($body);
                $body.css(OVERFLOW, HIDDEN);
                var $html = $('html');
                that._storeOverflowRule($html);
                $html.css(OVERFLOW, HIDDEN);
            },
            _enableDocumentScrolling: function () {
                var that = this;
                that._restoreOverflowRule($(document.body));
                that._restoreOverflowRule($('html'));
            },
            _storeOverflowRule: function ($element) {
                var overflowRule = $element.get(0).style.overflow;
                if (overflowRule) {
                    $element.data(DATADOCOVERFLOWRULE, overflowRule);
                }
            },
            _restoreOverflowRule: function ($element) {
                var overflowRule = $element.data(DATADOCOVERFLOWRULE);
                if (overflowRule) {
                    $element.css(OVERFLOW, overflowRule);
                    $element.removeData(DATADOCOVERFLOWRULE);
                } else {
                    $element.css(OVERFLOW, '');
                }
            },
            isMaximized: function () {
                return this.options.isMaximized;
            },
            minimize: function () {
                this._sizingAction('minimize', function () {
                    var that = this;
                    that.wrapper.css({
                        height: '',
                        minHeight: ''
                    });
                    that.element.hide();
                    that.options.isMinimized = true;
                });
                return this;
            },
            isMinimized: function () {
                return this.options.isMinimized;
            },
            pin: function () {
                var that = this, win = $(window), wrapper = that.wrapper, top = parseInt(wrapper.css('top'), 10), left = parseInt(wrapper.css('left'), 10);
                if (!that.options.isMaximized) {
                    wrapper.css({
                        position: 'fixed',
                        top: top - win.scrollTop(),
                        left: left - win.scrollLeft()
                    });
                    wrapper.children(KWINDOWTITLEBAR).find(KPIN).addClass('k-i-unpin').removeClass('k-i-pin');
                    that._isPinned = true;
                    that.options.pinned = true;
                    that.options.draggable = false;
                }
            },
            unpin: function () {
                var that = this, win = $(window), wrapper = that.wrapper, top = parseInt(wrapper.css('top'), 10), left = parseInt(wrapper.css('left'), 10);
                if (!that.options.isMaximized) {
                    wrapper.css({
                        position: '',
                        top: top + win.scrollTop(),
                        left: left + win.scrollLeft()
                    });
                    wrapper.children(KWINDOWTITLEBAR).find(KUNPIN).addClass('k-i-pin').removeClass('k-i-unpin');
                    that._isPinned = false;
                    that.options.pinned = false;
                    that.options.draggable = true;
                }
            },
            _onDocumentResize: function () {
                var that = this, wrapper = that.wrapper, wnd = $(window), zoomLevel = kendo.support.zoomLevel(), w, h;
                if (!that.options.isMaximized) {
                    return;
                }
                var lrBorderWidth = parseInt(wrapper.css('border-left-width'), 10) + parseInt(wrapper.css('border-right-width'), 10);
                var tbBorderWidth = parseInt(wrapper.css('border-top-width'), 10) + parseInt(wrapper.css('border-bottom-width'), 10);
                w = wnd.width() / zoomLevel - lrBorderWidth;
                h = wnd.height() / zoomLevel - parseInt(wrapper.css('padding-top'), 10) - tbBorderWidth;
                wrapper.css({
                    width: w,
                    height: h
                });
                that.options.width = w;
                that.options.height = h;
                that.resize();
            },
            refresh: function (options) {
                var that = this, initOptions = that.options, element = $(that.element), iframe, showIframe, url;
                if (!isPlainObject(options)) {
                    options = { url: options };
                }
                options = extend({}, initOptions.content, options);
                showIframe = defined(initOptions.iframe) ? initOptions.iframe : options.iframe;
                url = options.url;
                if (url) {
                    if (!defined(showIframe)) {
                        showIframe = !isLocalUrl(url);
                    }
                    if (!showIframe) {
                        that._ajaxRequest(options);
                    } else {
                        iframe = element.find('.' + KCONTENTFRAME)[0];
                        if (iframe) {
                            iframe.src = url || iframe.src;
                        } else {
                            element.html(templates.contentFrame(extend({}, initOptions, { content: options })));
                        }
                        element.find('.' + KCONTENTFRAME).unbind('load' + NS).on('load' + NS, proxy(this._triggerRefresh, this));
                    }
                } else {
                    if (options.template) {
                        that.content(template(options.template)({}));
                    }
                    that.trigger(REFRESH);
                }
                element.toggleClass('k-window-iframecontent', !!showIframe);
                return that;
            },
            _triggerRefresh: function () {
                this.trigger(REFRESH);
            },
            _ajaxComplete: function () {
                clearTimeout(this._loadingIconTimeout);
                this.wrapper.find(REFRESHICON).removeClass(LOADING);
            },
            _ajaxError: function (xhr, status) {
                this.trigger(ERROR, {
                    status: status,
                    xhr: xhr
                });
            },
            _ajaxSuccess: function (contentTemplate) {
                return function (data) {
                    var html = data;
                    if (contentTemplate) {
                        html = template(contentTemplate)(data || {});
                    }
                    this.content(html, data);
                    this.element.prop('scrollTop', 0);
                    this.trigger(REFRESH);
                };
            },
            _showLoading: function () {
                this.wrapper.find(REFRESHICON).addClass(LOADING);
            },
            _ajaxRequest: function (options) {
                this._loadingIconTimeout = setTimeout(proxy(this._showLoading, this), 100);
                $.ajax(extend({
                    type: 'GET',
                    dataType: 'html',
                    cache: false,
                    error: proxy(this._ajaxError, this),
                    complete: proxy(this._ajaxComplete, this),
                    success: proxy(this._ajaxSuccess(options.template), this)
                }, options));
            },
            _destroy: function () {
                if (this.resizing) {
                    this.resizing.destroy();
                }
                if (this.dragging) {
                    this.dragging.destroy();
                }
                this.wrapper.off(NS).children(KWINDOWCONTENT).off(NS).end().find('.k-resize-handle,.k-window-titlebar').off(NS);
                $(window).off('resize' + NS + this._marker);
                clearTimeout(this._loadingIconTimeout);
                Widget.fn.destroy.call(this);
                this.unbind(undefined);
                kendo.destroy(this.wrapper);
                this._removeOverlay(true);
            },
            destroy: function () {
                this._destroy();
                this.wrapper.empty().remove();
                this.wrapper = this.appendTo = this.element = $();
            },
            _createWindow: function () {
                var contentHtml = this.element, options = this.options, iframeSrcAttributes, wrapper, isRtl = kendo.support.isRtl(contentHtml);
                if (options.scrollable === false) {
                    contentHtml.css('overflow', 'hidden');
                }
                wrapper = $(templates.wrapper(options));
                iframeSrcAttributes = contentHtml.find('iframe:not(.k-content)').map(function () {
                    var src = this.getAttribute('src');
                    this.src = '';
                    return src;
                });
                wrapper.toggleClass('k-rtl', isRtl).appendTo(this.appendTo).append(contentHtml).find('iframe:not(.k-content)').each(function (index) {
                    this.src = iframeSrcAttributes[index];
                });
                wrapper.find('.k-window-title').css(isRtl ? 'left' : 'right', outerWidth(wrapper.find('.k-window-actions')) + 10);
                contentHtml.css('visibility', '').show();
                contentHtml.find('[data-role=editor]').each(function () {
                    var editor = $(this).data('kendoEditor');
                    if (editor) {
                        editor.refresh();
                    }
                });
                wrapper = contentHtml = null;
            }
        });
        templates = {
            wrapper: template('<div class=\'k-widget k-window\' />'),
            action: template('<a role=\'button\' href=\'\\#\' class=\'k-button k-bare k-button-icon k-window-action\' aria-label=\'#= name #\'>' + '<span class=\'k-icon k-i-#= name.toLowerCase() #\'></span>' + '</a>'),
            titlebar: template('<div class=\'k-window-titlebar k-header\'>&nbsp;' + '<span class=\'k-window-title\'>#: title #</span>' + '<div class=\'k-window-actions\' />' + '</div>'),
            overlay: '<div class=\'k-overlay\' />',
            contentFrame: template('<iframe frameborder=\'0\' title=\'#= title #\' class=\'' + KCONTENTFRAME + '\' ' + 'src=\'#= content.url #\'>' + 'This page requires frames in order to show content' + '</iframe>'),
            resizeHandle: template('<div class=\'k-resize-handle k-resize-#= data #\'></div>')
        };
        function WindowResizing(wnd) {
            var that = this;
            that.owner = wnd;
            that._preventDragging = false;
            that._draggable = new Draggable(wnd.wrapper, {
                filter: '>' + KWINDOWRESIZEHANDLES,
                group: wnd.wrapper.id + '-resizing',
                dragstart: proxy(that.dragstart, that),
                drag: proxy(that.drag, that),
                dragend: proxy(that.dragend, that)
            });
            that._draggable.userEvents.bind('press', proxy(that.addOverlay, that));
            that._draggable.userEvents.bind('release', proxy(that.removeOverlay, that));
        }
        function getPosition(elem) {
            var result = {
                    top: elem.offsetTop,
                    left: elem.offsetLeft
                }, parent = elem.offsetParent;
            while (parent) {
                result.top += parent.offsetTop;
                result.left += parent.offsetLeft;
                var parentOverflowX = $(parent).css('overflowX');
                var parentOverflowY = $(parent).css('overflowY');
                if (parentOverflowY === 'auto' || parentOverflowY === 'scroll') {
                    result.top -= parent.scrollTop;
                }
                if (parentOverflowX === 'auto' || parentOverflowX === 'scroll') {
                    result.left -= parent.scrollLeft;
                }
                parent = parent.offsetParent;
            }
            return result;
        }
        WindowResizing.prototype = {
            addOverlay: function () {
                this.owner.wrapper.append(templates.overlay);
            },
            removeOverlay: function () {
                this.owner.wrapper.find(KOVERLAY).remove();
            },
            dragstart: function (e) {
                var that = this;
                var wnd = that.owner;
                var wrapper = wnd.wrapper;
                that._preventDragging = wnd.trigger(RESIZESTART);
                if (that._preventDragging) {
                    return;
                }
                that.elementPadding = parseInt(wrapper.css('padding-top'), 10);
                that.initialPosition = kendo.getOffset(wrapper, 'position');
                that.resizeDirection = e.currentTarget.prop('className').replace('k-resize-handle k-resize-', '');
                that.initialSize = {
                    width: wrapper.width(),
                    height: wrapper.height()
                };
                that.containerOffset = kendo.getOffset(wnd.appendTo, 'position');
                var offsetParent = wrapper.offsetParent();
                if (offsetParent.is('html')) {
                    that.containerOffset.top = that.containerOffset.left = 0;
                } else {
                    var marginTop = offsetParent.css('margin-top');
                    var marginLeft = offsetParent.css('margin-left');
                    var hasMargin = !zero.test(marginTop) || !zero.test(marginLeft);
                    if (hasMargin) {
                        var wrapperPosition = getPosition(wrapper[0]);
                        var relativeElMarginLeft = wrapperPosition.left - that.containerOffset.left - that.initialPosition.left;
                        var relativeElMarginTop = wrapperPosition.top - that.containerOffset.top - that.initialPosition.top;
                        that._relativeElMarginLeft = relativeElMarginLeft > 1 ? relativeElMarginLeft : 0;
                        that._relativeElMarginTop = relativeElMarginTop > 1 ? relativeElMarginTop : 0;
                        that.initialPosition.left += that._relativeElMarginLeft;
                        that.initialPosition.top += that._relativeElMarginTop;
                    }
                }
                wrapper.children(KWINDOWRESIZEHANDLES).not(e.currentTarget).hide();
                $(BODY).css(CURSOR, e.currentTarget.css(CURSOR));
            },
            drag: function (e) {
                if (this._preventDragging) {
                    return;
                }
                var that = this, wnd = that.owner, wrapper = wnd.wrapper, options = wnd.options, direction = that.resizeDirection, containerOffset = that.containerOffset, initialPosition = that.initialPosition, initialSize = that.initialSize, newWidth, newHeight, windowBottom, windowRight, x = Math.max(e.x.location, 0), y = Math.max(e.y.location, 0);
                if (direction.indexOf('e') >= 0) {
                    newWidth = x - initialPosition.left - containerOffset.left;
                    wrapper.width(constrain(newWidth, options.minWidth, options.maxWidth));
                } else if (direction.indexOf('w') >= 0) {
                    windowRight = initialPosition.left + initialSize.width + containerOffset.left;
                    newWidth = constrain(windowRight - x, options.minWidth, options.maxWidth);
                    wrapper.css({
                        left: windowRight - newWidth - containerOffset.left - (that._relativeElMarginLeft || 0),
                        width: newWidth
                    });
                }
                var newWindowTop = y;
                if (wnd.options.pinned) {
                    newWindowTop -= $(window).scrollTop();
                }
                if (direction.indexOf('s') >= 0) {
                    newHeight = newWindowTop - initialPosition.top - that.elementPadding - containerOffset.top;
                    wrapper.height(constrain(newHeight, options.minHeight, options.maxHeight));
                } else if (direction.indexOf('n') >= 0) {
                    windowBottom = initialPosition.top + initialSize.height + containerOffset.top;
                    newHeight = constrain(windowBottom - newWindowTop, options.minHeight, options.maxHeight);
                    wrapper.css({
                        top: windowBottom - newHeight - containerOffset.top - (that._relativeElMarginTop || 0),
                        height: newHeight
                    });
                }
                if (newWidth) {
                    wnd.options.width = newWidth + 'px';
                }
                if (newHeight) {
                    wnd.options.height = newHeight + 'px';
                }
                wnd.resize();
            },
            dragend: function (e) {
                if (this._preventDragging) {
                    return;
                }
                var that = this, wnd = that.owner, wrapper = wnd.wrapper;
                wrapper.children(KWINDOWRESIZEHANDLES).not(e.currentTarget).show();
                $(BODY).css(CURSOR, '');
                if (wnd.touchScroller) {
                    wnd.touchScroller.reset();
                }
                if (e.keyCode == 27) {
                    wrapper.css(that.initialPosition).css(that.initialSize);
                }
                wnd.trigger(RESIZEEND);
                return false;
            },
            destroy: function () {
                if (this._draggable) {
                    this._draggable.destroy();
                }
                this._draggable = this.owner = null;
            }
        };
        function WindowDragging(wnd, dragHandle) {
            var that = this;
            that.owner = wnd;
            that._preventDragging = false;
            that._draggable = new Draggable(wnd.wrapper, {
                filter: dragHandle,
                group: wnd.wrapper.id + '-moving',
                dragstart: proxy(that.dragstart, that),
                drag: proxy(that.drag, that),
                dragend: proxy(that.dragend, that),
                dragcancel: proxy(that.dragcancel, that)
            });
            that._draggable.userEvents.stopPropagation = false;
        }
        WindowDragging.prototype = {
            dragstart: function (e) {
                var wnd = this.owner, element = wnd.element, actions = element.find('.k-window-actions'), containerOffset = kendo.getOffset(wnd.appendTo);
                this._preventDragging = wnd.trigger(DRAGSTART) || !wnd.options.draggable;
                if (this._preventDragging) {
                    return;
                }
                wnd.initialWindowPosition = kendo.getOffset(wnd.wrapper, 'position');
                wnd.initialPointerPosition = {
                    left: e.x.client,
                    top: e.y.client
                };
                wnd.startPosition = {
                    left: e.x.client - wnd.initialWindowPosition.left,
                    top: e.y.client - wnd.initialWindowPosition.top
                };
                if (actions.length > 0) {
                    wnd.minLeftPosition = outerWidth(actions) + parseInt(actions.css('right'), 10) - outerWidth(element);
                } else {
                    wnd.minLeftPosition = 20 - outerWidth(element);
                }
                wnd.minLeftPosition -= containerOffset.left;
                wnd.minTopPosition = -containerOffset.top;
                wnd.wrapper.append(templates.overlay).children(KWINDOWRESIZEHANDLES).hide();
                $(BODY).css(CURSOR, e.currentTarget.css(CURSOR));
            },
            drag: function (e) {
                if (this._preventDragging) {
                    return;
                }
                var wnd = this.owner;
                var position = wnd.options.position;
                position.top = Math.max(e.y.client - wnd.startPosition.top, wnd.minTopPosition);
                position.left = Math.max(e.x.client - wnd.startPosition.left, wnd.minLeftPosition);
                if (kendo.support.transforms) {
                    $(wnd.wrapper).css('transform', 'translate(' + (e.x.client - wnd.initialPointerPosition.left) + 'px, ' + (e.y.client - wnd.initialPointerPosition.top) + 'px)');
                } else {
                    $(wnd.wrapper).css(position);
                }
            },
            _finishDrag: function () {
                var wnd = this.owner;
                wnd.wrapper.children(KWINDOWRESIZEHANDLES).toggle(!wnd.options.isMinimized).end().find(KOVERLAY).remove();
                $(BODY).css(CURSOR, '');
            },
            dragcancel: function (e) {
                if (this._preventDragging) {
                    return;
                }
                this._finishDrag();
                e.currentTarget.closest(KWINDOW).css(this.owner.initialWindowPosition);
            },
            dragend: function () {
                if (this._preventDragging) {
                    return;
                }
                $(this.owner.wrapper).css(this.owner.options.position).css('transform', '');
                this._finishDrag();
                this.owner.trigger(DRAGEND);
                return false;
            },
            destroy: function () {
                if (this._draggable) {
                    this._draggable.destroy();
                }
                this._draggable = this.owner = null;
            }
        };
        kendo.ui.plugin(Window);
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.data.odata', ['kendo.core'], f);
}(function () {
    var __meta__ = {
        id: 'data.odata',
        name: 'OData',
        category: 'framework',
        depends: ['core'],
        hidden: true
    };
    (function ($, undefined) {
        var kendo = window.kendo, extend = $.extend, odataFilters = {
                eq: 'eq',
                neq: 'ne',
                gt: 'gt',
                gte: 'ge',
                lt: 'lt',
                lte: 'le',
                contains: 'substringof',
                doesnotcontain: 'substringof',
                endswith: 'endswith',
                startswith: 'startswith',
                isnull: 'eq',
                isnotnull: 'ne',
                isempty: 'eq',
                isnotempty: 'ne'
            }, odataFiltersVersionFour = extend({}, odataFilters, { contains: 'contains' }), mappers = {
                pageSize: $.noop,
                page: $.noop,
                filter: function (params, filter, useVersionFour) {
                    if (filter) {
                        filter = toOdataFilter(filter, useVersionFour);
                        if (filter) {
                            params.$filter = filter;
                        }
                    }
                },
                sort: function (params, orderby) {
                    var expr = $.map(orderby, function (value) {
                        var order = value.field.replace(/\./g, '/');
                        if (value.dir === 'desc') {
                            order += ' desc';
                        }
                        return order;
                    }).join(',');
                    if (expr) {
                        params.$orderby = expr;
                    }
                },
                skip: function (params, skip) {
                    if (skip) {
                        params.$skip = skip;
                    }
                },
                take: function (params, take) {
                    if (take) {
                        params.$top = take;
                    }
                }
            }, defaultDataType = { read: { dataType: 'jsonp' } };
        function toOdataFilter(filter, useOdataFour) {
            var result = [], logic = filter.logic || 'and', idx, length, field, type, format, operator, value, ignoreCase, filters = filter.filters;
            for (idx = 0, length = filters.length; idx < length; idx++) {
                filter = filters[idx];
                field = filter.field;
                value = filter.value;
                operator = filter.operator;
                if (filter.filters) {
                    filter = toOdataFilter(filter, useOdataFour);
                } else {
                    ignoreCase = filter.ignoreCase;
                    field = field.replace(/\./g, '/');
                    filter = odataFilters[operator];
                    if (useOdataFour) {
                        filter = odataFiltersVersionFour[operator];
                    }
                    if (operator === 'isnull' || operator === 'isnotnull') {
                        filter = kendo.format('{0} {1} null', field, filter);
                    } else if (operator === 'isempty' || operator === 'isnotempty') {
                        filter = kendo.format('{0} {1} \'\'', field, filter);
                    } else if (filter && value !== undefined) {
                        type = $.type(value);
                        if (type === 'string') {
                            format = '\'{1}\'';
                            value = value.replace(/'/g, '\'\'');
                            if (ignoreCase === true) {
                                field = 'tolower(' + field + ')';
                            }
                        } else if (type === 'date') {
                            if (useOdataFour) {
                                format = '{1:yyyy-MM-ddTHH:mm:ss+00:00}';
                                value = kendo.timezone.apply(value, 'Etc/UTC');
                            } else {
                                format = 'datetime\'{1:yyyy-MM-ddTHH:mm:ss}\'';
                            }
                        } else {
                            format = '{1}';
                        }
                        if (filter.length > 3) {
                            if (filter !== 'substringof') {
                                format = '{0}({2},' + format + ')';
                            } else {
                                format = '{0}(' + format + ',{2})';
                                if (operator === 'doesnotcontain') {
                                    if (useOdataFour) {
                                        format = '{0}({2},\'{1}\') eq -1';
                                        filter = 'indexof';
                                    } else {
                                        format += ' eq false';
                                    }
                                }
                            }
                        } else {
                            format = '{2} {0} ' + format;
                        }
                        filter = kendo.format(format, filter, value, field);
                    }
                }
                result.push(filter);
            }
            filter = result.join(' ' + logic + ' ');
            if (result.length > 1) {
                filter = '(' + filter + ')';
            }
            return filter;
        }
        function stripMetadata(obj) {
            for (var name in obj) {
                if (name.indexOf('@odata') === 0) {
                    delete obj[name];
                }
            }
        }
        extend(true, kendo.data, {
            schemas: {
                odata: {
                    type: 'json',
                    data: function (data) {
                        return data.d.results || [data.d];
                    },
                    total: 'd.__count'
                }
            },
            transports: {
                odata: {
                    read: {
                        cache: true,
                        dataType: 'jsonp',
                        jsonp: '$callback'
                    },
                    update: {
                        cache: true,
                        dataType: 'json',
                        contentType: 'application/json',
                        type: 'PUT'
                    },
                    create: {
                        cache: true,
                        dataType: 'json',
                        contentType: 'application/json',
                        type: 'POST'
                    },
                    destroy: {
                        cache: true,
                        dataType: 'json',
                        type: 'DELETE'
                    },
                    parameterMap: function (options, type, useVersionFour) {
                        var params, value, option, dataType;
                        options = options || {};
                        type = type || 'read';
                        dataType = (this.options || defaultDataType)[type];
                        dataType = dataType ? dataType.dataType : 'json';
                        if (type === 'read') {
                            params = { $inlinecount: 'allpages' };
                            if (dataType != 'json') {
                                params.$format = 'json';
                            }
                            for (option in options) {
                                if (mappers[option]) {
                                    mappers[option](params, options[option], useVersionFour);
                                } else {
                                    params[option] = options[option];
                                }
                            }
                        } else {
                            if (dataType !== 'json') {
                                throw new Error('Only json dataType can be used for ' + type + ' operation.');
                            }
                            if (type !== 'destroy') {
                                for (option in options) {
                                    value = options[option];
                                    if (typeof value === 'number') {
                                        options[option] = value + '';
                                    }
                                }
                                params = kendo.stringify(options);
                            }
                        }
                        return params;
                    }
                }
            }
        });
        extend(true, kendo.data, {
            schemas: {
                'odata-v4': {
                    type: 'json',
                    data: function (data) {
                        data = $.extend({}, data);
                        stripMetadata(data);
                        if (data.value) {
                            return data.value;
                        }
                        return [data];
                    },
                    total: function (data) {
                        return data['@odata.count'];
                    }
                }
            },
            transports: {
                'odata-v4': {
                    read: {
                        cache: true,
                        dataType: 'json'
                    },
                    update: {
                        cache: true,
                        dataType: 'json',
                        contentType: 'application/json;IEEE754Compatible=true',
                        type: 'PUT'
                    },
                    create: {
                        cache: true,
                        dataType: 'json',
                        contentType: 'application/json;IEEE754Compatible=true',
                        type: 'POST'
                    },
                    destroy: {
                        cache: true,
                        dataType: 'json',
                        type: 'DELETE'
                    },
                    parameterMap: function (options, type) {
                        var result = kendo.data.transports.odata.parameterMap(options, type, true);
                        if (type == 'read') {
                            result.$count = true;
                            delete result.$inlinecount;
                        }
                        return result;
                    }
                }
            }
        });
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.data.xml', ['kendo.core'], f);
}(function () {
    var __meta__ = {
        id: 'data.xml',
        name: 'XML',
        category: 'framework',
        depends: ['core'],
        hidden: true
    };
    (function ($, undefined) {
        var kendo = window.kendo, isArray = $.isArray, isPlainObject = $.isPlainObject, map = $.map, each = $.each, extend = $.extend, getter = kendo.getter, Class = kendo.Class;
        var XmlDataReader = Class.extend({
            init: function (options) {
                var that = this, total = options.total, model = options.model, parse = options.parse, errors = options.errors, serialize = options.serialize, data = options.data;
                if (model) {
                    if (isPlainObject(model)) {
                        var base = options.modelBase || kendo.data.Model;
                        if (model.fields) {
                            each(model.fields, function (field, value) {
                                if (isPlainObject(value) && value.field) {
                                    if (!$.isFunction(value.field)) {
                                        value = extend(value, { field: that.getter(value.field) });
                                    }
                                } else {
                                    value = { field: that.getter(value) };
                                }
                                model.fields[field] = value;
                            });
                        }
                        var id = model.id;
                        if (id) {
                            var idField = {};
                            idField[that.xpathToMember(id, true)] = { field: that.getter(id) };
                            model.fields = extend(idField, model.fields);
                            model.id = that.xpathToMember(id);
                        }
                        model = base.define(model);
                    }
                    that.model = model;
                }
                if (total) {
                    if (typeof total == 'string') {
                        total = that.getter(total);
                        that.total = function (data) {
                            return parseInt(total(data), 10);
                        };
                    } else if (typeof total == 'function') {
                        that.total = total;
                    }
                }
                if (errors) {
                    if (typeof errors == 'string') {
                        errors = that.getter(errors);
                        that.errors = function (data) {
                            return errors(data) || null;
                        };
                    } else if (typeof errors == 'function') {
                        that.errors = errors;
                    }
                }
                if (data) {
                    if (typeof data == 'string') {
                        data = that.xpathToMember(data);
                        that.data = function (value) {
                            var result = that.evaluate(value, data), modelInstance;
                            result = isArray(result) ? result : [result];
                            if (that.model && model.fields) {
                                modelInstance = new that.model();
                                return map(result, function (value) {
                                    if (value) {
                                        var record = {}, field;
                                        for (field in model.fields) {
                                            record[field] = modelInstance._parse(field, model.fields[field].field(value));
                                        }
                                        return record;
                                    }
                                });
                            }
                            return result;
                        };
                    } else if (typeof data == 'function') {
                        that.data = data;
                    }
                }
                if (typeof parse == 'function') {
                    var xmlParse = that.parse;
                    that.parse = function (data) {
                        var xml = parse.call(that, data);
                        return xmlParse.call(that, xml);
                    };
                }
                if (typeof serialize == 'function') {
                    that.serialize = serialize;
                }
            },
            total: function (result) {
                return this.data(result).length;
            },
            errors: function (data) {
                return data ? data.errors : null;
            },
            serialize: function (data) {
                return data;
            },
            parseDOM: function (element) {
                var result = {}, parsedNode, node, nodeType, nodeName, member, attribute, attributes = element.attributes, attributeCount = attributes.length, idx;
                for (idx = 0; idx < attributeCount; idx++) {
                    attribute = attributes[idx];
                    result['@' + attribute.nodeName] = attribute.nodeValue;
                }
                for (node = element.firstChild; node; node = node.nextSibling) {
                    nodeType = node.nodeType;
                    if (nodeType === 3 || nodeType === 4) {
                        result['#text'] = node.nodeValue;
                    } else if (nodeType === 1) {
                        parsedNode = this.parseDOM(node);
                        nodeName = node.nodeName;
                        member = result[nodeName];
                        if (isArray(member)) {
                            member.push(parsedNode);
                        } else if (member !== undefined) {
                            member = [
                                member,
                                parsedNode
                            ];
                        } else {
                            member = parsedNode;
                        }
                        result[nodeName] = member;
                    }
                }
                return result;
            },
            evaluate: function (value, expression) {
                var members = expression.split('.'), member, result, length, intermediateResult, idx;
                while (member = members.shift()) {
                    value = value[member];
                    if (isArray(value)) {
                        result = [];
                        expression = members.join('.');
                        for (idx = 0, length = value.length; idx < length; idx++) {
                            intermediateResult = this.evaluate(value[idx], expression);
                            intermediateResult = isArray(intermediateResult) ? intermediateResult : [intermediateResult];
                            result.push.apply(result, intermediateResult);
                        }
                        return result;
                    }
                }
                return value;
            },
            parse: function (xml) {
                var documentElement, tree, result = {};
                documentElement = xml.documentElement || $.parseXML(xml).documentElement;
                tree = this.parseDOM(documentElement);
                result[documentElement.nodeName] = tree;
                return result;
            },
            xpathToMember: function (member, raw) {
                if (!member) {
                    return '';
                }
                member = member.replace(/^\//, '').replace(/\//g, '.');
                if (member.indexOf('@') >= 0) {
                    return member.replace(/\.?(@.*)/, raw ? '$1' : '["$1"]');
                }
                if (member.indexOf('text()') >= 0) {
                    return member.replace(/(\.?text\(\))/, raw ? '#text' : '["#text"]');
                }
                return member;
            },
            getter: function (member) {
                return getter(this.xpathToMember(member), true);
            }
        });
        $.extend(true, kendo.data, {
            XmlDataReader: XmlDataReader,
            readers: { xml: XmlDataReader }
        });
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.data', [
        'kendo.core',
        'kendo.data.odata',
        'kendo.data.xml'
    ], f);
}(function () {
    var __meta__ = {
        id: 'data',
        name: 'Data source',
        category: 'framework',
        description: 'Powerful component for using local and remote data.Fully supports CRUD, Sorting, Paging, Filtering, Grouping, and Aggregates.',
        depends: ['core'],
        features: [
            {
                id: 'data-odata',
                name: 'OData',
                description: 'Support for accessing Open Data Protocol (OData) services.',
                depends: ['data.odata']
            },
            {
                id: 'data-signalr',
                name: 'SignalR',
                description: 'Support for binding to SignalR hubs.',
                depends: ['data.signalr']
            },
            {
                id: 'data-XML',
                name: 'XML',
                description: 'Support for binding to XML.',
                depends: ['data.xml']
            }
        ]
    };
    (function ($, undefined) {
        var extend = $.extend, proxy = $.proxy, isPlainObject = $.isPlainObject, isEmptyObject = $.isEmptyObject, isArray = $.isArray, grep = $.grep, ajax = $.ajax, map, each = $.each, noop = $.noop, kendo = window.kendo, isFunction = kendo.isFunction, Observable = kendo.Observable, Class = kendo.Class, STRING = 'string', FUNCTION = 'function', CREATE = 'create', READ = 'read', UPDATE = 'update', DESTROY = 'destroy', CHANGE = 'change', SYNC = 'sync', GET = 'get', ERROR = 'error', REQUESTSTART = 'requestStart', PROGRESS = 'progress', REQUESTEND = 'requestEnd', crud = [
                CREATE,
                READ,
                UPDATE,
                DESTROY
            ], identity = function (o) {
                return o;
            }, getter = kendo.getter, stringify = kendo.stringify, math = Math, push = [].push, join = [].join, pop = [].pop, splice = [].splice, shift = [].shift, slice = [].slice, unshift = [].unshift, toString = {}.toString, stableSort = kendo.support.stableSort, dateRegExp = /^\/Date\((.*?)\)\/$/;
        var ObservableArray = Observable.extend({
            init: function (array, type) {
                var that = this;
                that.type = type || ObservableObject;
                Observable.fn.init.call(that);
                that.length = array.length;
                that.wrapAll(array, that);
            },
            at: function (index) {
                return this[index];
            },
            toJSON: function () {
                var idx, length = this.length, value, json = new Array(length);
                for (idx = 0; idx < length; idx++) {
                    value = this[idx];
                    if (value instanceof ObservableObject) {
                        value = value.toJSON();
                    }
                    json[idx] = value;
                }
                return json;
            },
            parent: noop,
            wrapAll: function (source, target) {
                var that = this, idx, length, parent = function () {
                        return that;
                    };
                target = target || [];
                for (idx = 0, length = source.length; idx < length; idx++) {
                    target[idx] = that.wrap(source[idx], parent);
                }
                return target;
            },
            wrap: function (object, parent) {
                var that = this, observable;
                if (object !== null && toString.call(object) === '[object Object]') {
                    observable = object instanceof that.type || object instanceof Model;
                    if (!observable) {
                        object = object instanceof ObservableObject ? object.toJSON() : object;
                        object = new that.type(object);
                    }
                    object.parent = parent;
                    object.bind(CHANGE, function (e) {
                        that.trigger(CHANGE, {
                            field: e.field,
                            node: e.node,
                            index: e.index,
                            items: e.items || [this],
                            action: e.node ? e.action || 'itemloaded' : 'itemchange'
                        });
                    });
                }
                return object;
            },
            push: function () {
                var index = this.length, items = this.wrapAll(arguments), result;
                result = push.apply(this, items);
                this.trigger(CHANGE, {
                    action: 'add',
                    index: index,
                    items: items
                });
                return result;
            },
            slice: slice,
            sort: [].sort,
            join: join,
            pop: function () {
                var length = this.length, result = pop.apply(this);
                if (length) {
                    this.trigger(CHANGE, {
                        action: 'remove',
                        index: length - 1,
                        items: [result]
                    });
                }
                return result;
            },
            splice: function (index, howMany, item) {
                var items = this.wrapAll(slice.call(arguments, 2)), result, i, len;
                result = splice.apply(this, [
                    index,
                    howMany
                ].concat(items));
                if (result.length) {
                    this.trigger(CHANGE, {
                        action: 'remove',
                        index: index,
                        items: result
                    });
                    for (i = 0, len = result.length; i < len; i++) {
                        if (result[i] && result[i].children) {
                            result[i].unbind(CHANGE);
                        }
                    }
                }
                if (item) {
                    this.trigger(CHANGE, {
                        action: 'add',
                        index: index,
                        items: items
                    });
                }
                return result;
            },
            shift: function () {
                var length = this.length, result = shift.apply(this);
                if (length) {
                    this.trigger(CHANGE, {
                        action: 'remove',
                        index: 0,
                        items: [result]
                    });
                }
                return result;
            },
            unshift: function () {
                var items = this.wrapAll(arguments), result;
                result = unshift.apply(this, items);
                this.trigger(CHANGE, {
                    action: 'add',
                    index: 0,
                    items: items
                });
                return result;
            },
            indexOf: function (item) {
                var that = this, idx, length;
                for (idx = 0, length = that.length; idx < length; idx++) {
                    if (that[idx] === item) {
                        return idx;
                    }
                }
                return -1;
            },
            forEach: function (callback) {
                var idx = 0, length = this.length;
                for (; idx < length; idx++) {
                    callback(this[idx], idx, this);
                }
            },
            map: function (callback) {
                var idx = 0, result = [], length = this.length;
                for (; idx < length; idx++) {
                    result[idx] = callback(this[idx], idx, this);
                }
                return result;
            },
            reduce: function (callback) {
                var idx = 0, result, length = this.length;
                if (arguments.length == 2) {
                    result = arguments[1];
                } else if (idx < length) {
                    result = this[idx++];
                }
                for (; idx < length; idx++) {
                    result = callback(result, this[idx], idx, this);
                }
                return result;
            },
            reduceRight: function (callback) {
                var idx = this.length - 1, result;
                if (arguments.length == 2) {
                    result = arguments[1];
                } else if (idx > 0) {
                    result = this[idx--];
                }
                for (; idx >= 0; idx--) {
                    result = callback(result, this[idx], idx, this);
                }
                return result;
            },
            filter: function (callback) {
                var idx = 0, result = [], item, length = this.length;
                for (; idx < length; idx++) {
                    item = this[idx];
                    if (callback(item, idx, this)) {
                        result[result.length] = item;
                    }
                }
                return result;
            },
            find: function (callback) {
                var idx = 0, item, length = this.length;
                for (; idx < length; idx++) {
                    item = this[idx];
                    if (callback(item, idx, this)) {
                        return item;
                    }
                }
            },
            every: function (callback) {
                var idx = 0, item, length = this.length;
                for (; idx < length; idx++) {
                    item = this[idx];
                    if (!callback(item, idx, this)) {
                        return false;
                    }
                }
                return true;
            },
            some: function (callback) {
                var idx = 0, item, length = this.length;
                for (; idx < length; idx++) {
                    item = this[idx];
                    if (callback(item, idx, this)) {
                        return true;
                    }
                }
                return false;
            },
            remove: function (item) {
                var idx = this.indexOf(item);
                if (idx !== -1) {
                    this.splice(idx, 1);
                }
            },
            empty: function () {
                this.splice(0, this.length);
            }
        });
        if (typeof Symbol !== 'undefined' && Symbol.iterator && !ObservableArray.prototype[Symbol.iterator]) {
            ObservableArray.prototype[Symbol.iterator] = [][Symbol.iterator];
        }
        var LazyObservableArray = ObservableArray.extend({
            init: function (data, type) {
                Observable.fn.init.call(this);
                this.type = type || ObservableObject;
                for (var idx = 0; idx < data.length; idx++) {
                    this[idx] = data[idx];
                }
                this.length = idx;
                this._parent = proxy(function () {
                    return this;
                }, this);
            },
            at: function (index) {
                var item = this[index];
                if (!(item instanceof this.type)) {
                    item = this[index] = this.wrap(item, this._parent);
                } else {
                    item.parent = this._parent;
                }
                return item;
            }
        });
        function eventHandler(context, type, field, prefix) {
            return function (e) {
                var event = {}, key;
                for (key in e) {
                    event[key] = e[key];
                }
                if (prefix) {
                    event.field = field + '.' + e.field;
                } else {
                    event.field = field;
                }
                if (type == CHANGE && context._notifyChange) {
                    context._notifyChange(event);
                }
                context.trigger(type, event);
            };
        }
        var ObservableObject = Observable.extend({
            init: function (value) {
                var that = this, member, field, parent = function () {
                        return that;
                    };
                Observable.fn.init.call(this);
                this._handlers = {};
                for (field in value) {
                    member = value[field];
                    if (typeof member === 'object' && member && !member.getTime && field.charAt(0) != '_') {
                        member = that.wrap(member, field, parent);
                    }
                    that[field] = member;
                }
                that.uid = kendo.guid();
            },
            shouldSerialize: function (field) {
                return this.hasOwnProperty(field) && field !== '_handlers' && field !== '_events' && typeof this[field] !== FUNCTION && field !== 'uid';
            },
            forEach: function (f) {
                for (var i in this) {
                    if (this.shouldSerialize(i)) {
                        f(this[i], i);
                    }
                }
            },
            toJSON: function () {
                var result = {}, value, field;
                for (field in this) {
                    if (this.shouldSerialize(field)) {
                        value = this[field];
                        if (value instanceof ObservableObject || value instanceof ObservableArray) {
                            value = value.toJSON();
                        }
                        result[field] = value;
                    }
                }
                return result;
            },
            get: function (field) {
                var that = this, result;
                that.trigger(GET, { field: field });
                if (field === 'this') {
                    result = that;
                } else {
                    result = kendo.getter(field, true)(that);
                }
                return result;
            },
            _set: function (field, value) {
                var that = this;
                var composite = field.indexOf('.') >= 0;
                if (composite) {
                    var paths = field.split('.'), path = '';
                    while (paths.length > 1) {
                        path += paths.shift();
                        var obj = kendo.getter(path, true)(that);
                        if (obj instanceof ObservableObject) {
                            obj.set(paths.join('.'), value);
                            return composite;
                        }
                        path += '.';
                    }
                }
                kendo.setter(field)(that, value);
                return composite;
            },
            set: function (field, value) {
                var that = this, isSetPrevented = false, composite = field.indexOf('.') >= 0, current = kendo.getter(field, true)(that);
                if (current !== value) {
                    if (current instanceof Observable && this._handlers[field]) {
                        if (this._handlers[field].get) {
                            current.unbind(GET, this._handlers[field].get);
                        }
                        current.unbind(CHANGE, this._handlers[field].change);
                    }
                    isSetPrevented = that.trigger('set', {
                        field: field,
                        value: value
                    });
                    if (!isSetPrevented) {
                        if (!composite) {
                            value = that.wrap(value, field, function () {
                                return that;
                            });
                        }
                        if (!that._set(field, value) || field.indexOf('(') >= 0 || field.indexOf('[') >= 0) {
                            that.trigger(CHANGE, { field: field });
                        }
                    }
                }
                return isSetPrevented;
            },
            parent: noop,
            wrap: function (object, field, parent) {
                var that = this;
                var get;
                var change;
                var type = toString.call(object);
                if (object != null && (type === '[object Object]' || type === '[object Array]')) {
                    var isObservableArray = object instanceof ObservableArray;
                    var isDataSource = object instanceof DataSource;
                    if (type === '[object Object]' && !isDataSource && !isObservableArray) {
                        if (!(object instanceof ObservableObject)) {
                            object = new ObservableObject(object);
                        }
                        get = eventHandler(that, GET, field, true);
                        object.bind(GET, get);
                        change = eventHandler(that, CHANGE, field, true);
                        object.bind(CHANGE, change);
                        that._handlers[field] = {
                            get: get,
                            change: change
                        };
                    } else if (type === '[object Array]' || isObservableArray || isDataSource) {
                        if (!isObservableArray && !isDataSource) {
                            object = new ObservableArray(object);
                        }
                        change = eventHandler(that, CHANGE, field, false);
                        object.bind(CHANGE, change);
                        that._handlers[field] = { change: change };
                    }
                    object.parent = parent;
                }
                return object;
            }
        });
        function equal(x, y) {
            if (x === y) {
                return true;
            }
            var xtype = $.type(x), ytype = $.type(y), field;
            if (xtype !== ytype) {
                return false;
            }
            if (xtype === 'date') {
                return x.getTime() === y.getTime();
            }
            if (xtype !== 'object' && xtype !== 'array') {
                return false;
            }
            for (field in x) {
                if (!equal(x[field], y[field])) {
                    return false;
                }
            }
            return true;
        }
        var parsers = {
            'number': function (value) {
                return kendo.parseFloat(value);
            },
            'date': function (value) {
                return kendo.parseDate(value);
            },
            'boolean': function (value) {
                if (typeof value === STRING) {
                    return value.toLowerCase() === 'true';
                }
                return value != null ? !!value : value;
            },
            'string': function (value) {
                return value != null ? value + '' : value;
            },
            'default': function (value) {
                return value;
            }
        };
        var defaultValues = {
            'string': '',
            'number': 0,
            'date': new Date(),
            'boolean': false,
            'default': ''
        };
        function getFieldByName(obj, name) {
            var field, fieldName;
            for (fieldName in obj) {
                field = obj[fieldName];
                if (isPlainObject(field) && field.field && field.field === name) {
                    return field;
                } else if (field === name) {
                    return field;
                }
            }
            return null;
        }
        var Model = ObservableObject.extend({
            init: function (data) {
                var that = this;
                if (!data || $.isEmptyObject(data)) {
                    data = $.extend({}, that.defaults, data);
                    if (that._initializers) {
                        for (var idx = 0; idx < that._initializers.length; idx++) {
                            var name = that._initializers[idx];
                            data[name] = that.defaults[name]();
                        }
                    }
                }
                ObservableObject.fn.init.call(that, data);
                that.dirty = false;
                if (that.idField) {
                    that.id = that.get(that.idField);
                    if (that.id === undefined) {
                        that.id = that._defaultId;
                    }
                }
            },
            shouldSerialize: function (field) {
                return ObservableObject.fn.shouldSerialize.call(this, field) && field !== 'uid' && !(this.idField !== 'id' && field === 'id') && field !== 'dirty' && field !== '_accessors';
            },
            _parse: function (field, value) {
                var that = this, fieldName = field, fields = that.fields || {}, parse;
                field = fields[field];
                if (!field) {
                    field = getFieldByName(fields, fieldName);
                }
                if (field) {
                    parse = field.parse;
                    if (!parse && field.type) {
                        parse = parsers[field.type.toLowerCase()];
                    }
                }
                return parse ? parse(value) : value;
            },
            _notifyChange: function (e) {
                var action = e.action;
                if (action == 'add' || action == 'remove') {
                    this.dirty = true;
                }
            },
            editable: function (field) {
                field = (this.fields || {})[field];
                return field ? field.editable !== false : true;
            },
            set: function (field, value, initiator) {
                var that = this;
                var dirty = that.dirty;
                if (that.editable(field)) {
                    value = that._parse(field, value);
                    if (!equal(value, that.get(field))) {
                        that.dirty = true;
                        if (ObservableObject.fn.set.call(that, field, value, initiator) && !dirty) {
                            that.dirty = dirty;
                        }
                    }
                }
            },
            accept: function (data) {
                var that = this, parent = function () {
                        return that;
                    }, field;
                for (field in data) {
                    var value = data[field];
                    if (field.charAt(0) != '_') {
                        value = that.wrap(data[field], field, parent);
                    }
                    that._set(field, value);
                }
                if (that.idField) {
                    that.id = that.get(that.idField);
                }
                that.dirty = false;
            },
            isNew: function () {
                return this.id === this._defaultId;
            }
        });
        Model.define = function (base, options) {
            if (options === undefined) {
                options = base;
                base = Model;
            }
            var model, proto = extend({ defaults: {} }, options), name, field, type, value, idx, length, fields = {}, originalName, id = proto.id, functionFields = [];
            if (id) {
                proto.idField = id;
            }
            if (proto.id) {
                delete proto.id;
            }
            if (id) {
                proto.defaults[id] = proto._defaultId = '';
            }
            if (toString.call(proto.fields) === '[object Array]') {
                for (idx = 0, length = proto.fields.length; idx < length; idx++) {
                    field = proto.fields[idx];
                    if (typeof field === STRING) {
                        fields[field] = {};
                    } else if (field.field) {
                        fields[field.field] = field;
                    }
                }
                proto.fields = fields;
            }
            for (name in proto.fields) {
                field = proto.fields[name];
                type = field.type || 'default';
                value = null;
                originalName = name;
                name = typeof field.field === STRING ? field.field : name;
                if (!field.nullable) {
                    value = proto.defaults[originalName !== name ? originalName : name] = field.defaultValue !== undefined ? field.defaultValue : defaultValues[type.toLowerCase()];
                    if (typeof value === 'function') {
                        functionFields.push(name);
                    }
                }
                if (options.id === name) {
                    proto._defaultId = value;
                }
                proto.defaults[originalName !== name ? originalName : name] = value;
                field.parse = field.parse || parsers[type];
            }
            if (functionFields.length > 0) {
                proto._initializers = functionFields;
            }
            model = base.extend(proto);
            model.define = function (options) {
                return Model.define(model, options);
            };
            if (proto.fields) {
                model.fields = proto.fields;
                model.idField = proto.idField;
            }
            return model;
        };
        var Comparer = {
            selector: function (field) {
                return isFunction(field) ? field : getter(field);
            },
            compare: function (field) {
                var selector = this.selector(field);
                return function (a, b) {
                    a = selector(a);
                    b = selector(b);
                    if (a == null && b == null) {
                        return 0;
                    }
                    if (a == null) {
                        return -1;
                    }
                    if (b == null) {
                        return 1;
                    }
                    if (a.localeCompare) {
                        return a.localeCompare(b);
                    }
                    return a > b ? 1 : a < b ? -1 : 0;
                };
            },
            create: function (sort) {
                var compare = sort.compare || this.compare(sort.field);
                if (sort.dir == 'desc') {
                    return function (a, b) {
                        return compare(b, a, true);
                    };
                }
                return compare;
            },
            combine: function (comparers) {
                return function (a, b) {
                    var result = comparers[0](a, b), idx, length;
                    for (idx = 1, length = comparers.length; idx < length; idx++) {
                        result = result || comparers[idx](a, b);
                    }
                    return result;
                };
            }
        };
        var StableComparer = extend({}, Comparer, {
            asc: function (field) {
                var selector = this.selector(field);
                return function (a, b) {
                    var valueA = selector(a);
                    var valueB = selector(b);
                    if (valueA && valueA.getTime && valueB && valueB.getTime) {
                        valueA = valueA.getTime();
                        valueB = valueB.getTime();
                    }
                    if (valueA === valueB) {
                        return a.__position - b.__position;
                    }
                    if (valueA == null) {
                        return -1;
                    }
                    if (valueB == null) {
                        return 1;
                    }
                    if (valueA.localeCompare) {
                        return valueA.localeCompare(valueB);
                    }
                    return valueA > valueB ? 1 : -1;
                };
            },
            desc: function (field) {
                var selector = this.selector(field);
                return function (a, b) {
                    var valueA = selector(a);
                    var valueB = selector(b);
                    if (valueA && valueA.getTime && valueB && valueB.getTime) {
                        valueA = valueA.getTime();
                        valueB = valueB.getTime();
                    }
                    if (valueA === valueB) {
                        return a.__position - b.__position;
                    }
                    if (valueA == null) {
                        return 1;
                    }
                    if (valueB == null) {
                        return -1;
                    }
                    if (valueB.localeCompare) {
                        return valueB.localeCompare(valueA);
                    }
                    return valueA < valueB ? 1 : -1;
                };
            },
            create: function (sort) {
                return this[sort.dir](sort.field);
            }
        });
        map = function (array, callback) {
            var idx, length = array.length, result = new Array(length);
            for (idx = 0; idx < length; idx++) {
                result[idx] = callback(array[idx], idx, array);
            }
            return result;
        };
        var operators = function () {
            function quote(str) {
                if (typeof str == 'string') {
                    str = str.replace(/[\r\n]+/g, '');
                }
                return JSON.stringify(str);
            }
            function textOp(impl) {
                return function (a, b, ignore) {
                    b += '';
                    if (ignore) {
                        a = '(' + a + ' || \'\').toLowerCase()';
                        b = b.toLowerCase();
                    }
                    return impl(a, quote(b), ignore);
                };
            }
            function operator(op, a, b, ignore) {
                if (b != null) {
                    if (typeof b === STRING) {
                        var date = dateRegExp.exec(b);
                        if (date) {
                            b = new Date(+date[1]);
                        } else if (ignore) {
                            b = quote(b.toLowerCase());
                            a = '((' + a + ' || \'\')+\'\').toLowerCase()';
                        } else {
                            b = quote(b);
                        }
                    }
                    if (b.getTime) {
                        a = '(' + a + '&&' + a + '.getTime?' + a + '.getTime():' + a + ')';
                        b = b.getTime();
                    }
                }
                return a + ' ' + op + ' ' + b;
            }
            function getMatchRegexp(pattern) {
                for (var rx = '/^', esc = false, i = 0; i < pattern.length; ++i) {
                    var ch = pattern.charAt(i);
                    if (esc) {
                        rx += '\\' + ch;
                    } else if (ch == '~') {
                        esc = true;
                        continue;
                    } else if (ch == '*') {
                        rx += '.*';
                    } else if (ch == '?') {
                        rx += '.';
                    } else if ('.+^$()[]{}|\\/\n\r\u2028\u2029\xA0'.indexOf(ch) >= 0) {
                        rx += '\\' + ch;
                    } else {
                        rx += ch;
                    }
                    esc = false;
                }
                return rx + '$/';
            }
            return {
                quote: function (value) {
                    if (value && value.getTime) {
                        return 'new Date(' + value.getTime() + ')';
                    }
                    return quote(value);
                },
                eq: function (a, b, ignore) {
                    return operator('==', a, b, ignore);
                },
                neq: function (a, b, ignore) {
                    return operator('!=', a, b, ignore);
                },
                gt: function (a, b, ignore) {
                    return operator('>', a, b, ignore);
                },
                gte: function (a, b, ignore) {
                    return operator('>=', a, b, ignore);
                },
                lt: function (a, b, ignore) {
                    return operator('<', a, b, ignore);
                },
                lte: function (a, b, ignore) {
                    return operator('<=', a, b, ignore);
                },
                startswith: textOp(function (a, b) {
                    return a + '.lastIndexOf(' + b + ', 0) == 0';
                }),
                doesnotstartwith: textOp(function (a, b) {
                    return a + '.lastIndexOf(' + b + ', 0) == -1';
                }),
                endswith: textOp(function (a, b) {
                    var n = b ? b.length - 2 : 0;
                    return a + '.indexOf(' + b + ', ' + a + '.length - ' + n + ') >= 0';
                }),
                doesnotendwith: textOp(function (a, b) {
                    var n = b ? b.length - 2 : 0;
                    return a + '.indexOf(' + b + ', ' + a + '.length - ' + n + ') < 0';
                }),
                contains: textOp(function (a, b) {
                    return a + '.indexOf(' + b + ') >= 0';
                }),
                doesnotcontain: textOp(function (a, b) {
                    return a + '.indexOf(' + b + ') == -1';
                }),
                matches: textOp(function (a, b) {
                    b = b.substring(1, b.length - 1);
                    return getMatchRegexp(b) + '.test(' + a + ')';
                }),
                doesnotmatch: textOp(function (a, b) {
                    b = b.substring(1, b.length - 1);
                    return '!' + getMatchRegexp(b) + '.test(' + a + ')';
                }),
                isempty: function (a) {
                    return a + ' === \'\'';
                },
                isnotempty: function (a) {
                    return a + ' !== \'\'';
                },
                isnull: function (a) {
                    return '(' + a + ' == null)';
                },
                isnotnull: function (a) {
                    return '(' + a + ' != null)';
                }
            };
        }();
        function Query(data) {
            this.data = data || [];
        }
        Query.filterExpr = function (expression) {
            var expressions = [], logic = {
                    and: ' && ',
                    or: ' || '
                }, idx, length, filter, expr, fieldFunctions = [], operatorFunctions = [], field, operator, filters = expression.filters;
            for (idx = 0, length = filters.length; idx < length; idx++) {
                filter = filters[idx];
                field = filter.field;
                operator = filter.operator;
                if (filter.filters) {
                    expr = Query.filterExpr(filter);
                    filter = expr.expression.replace(/__o\[(\d+)\]/g, function (match, index) {
                        index = +index;
                        return '__o[' + (operatorFunctions.length + index) + ']';
                    }).replace(/__f\[(\d+)\]/g, function (match, index) {
                        index = +index;
                        return '__f[' + (fieldFunctions.length + index) + ']';
                    });
                    operatorFunctions.push.apply(operatorFunctions, expr.operators);
                    fieldFunctions.push.apply(fieldFunctions, expr.fields);
                } else {
                    if (typeof field === FUNCTION) {
                        expr = '__f[' + fieldFunctions.length + '](d)';
                        fieldFunctions.push(field);
                    } else {
                        expr = kendo.expr(field);
                    }
                    if (typeof operator === FUNCTION) {
                        filter = '__o[' + operatorFunctions.length + '](' + expr + ', ' + operators.quote(filter.value) + ')';
                        operatorFunctions.push(operator);
                    } else {
                        filter = operators[(operator || 'eq').toLowerCase()](expr, filter.value, filter.ignoreCase !== undefined ? filter.ignoreCase : true);
                    }
                }
                expressions.push(filter);
            }
            return {
                expression: '(' + expressions.join(logic[expression.logic]) + ')',
                fields: fieldFunctions,
                operators: operatorFunctions
            };
        };
        function normalizeSort(field, dir) {
            if (field) {
                var descriptor = typeof field === STRING ? {
                        field: field,
                        dir: dir
                    } : field, descriptors = isArray(descriptor) ? descriptor : descriptor !== undefined ? [descriptor] : [];
                return grep(descriptors, function (d) {
                    return !!d.dir;
                });
            }
        }
        var operatorMap = {
            '==': 'eq',
            equals: 'eq',
            isequalto: 'eq',
            equalto: 'eq',
            equal: 'eq',
            '!=': 'neq',
            ne: 'neq',
            notequals: 'neq',
            isnotequalto: 'neq',
            notequalto: 'neq',
            notequal: 'neq',
            '<': 'lt',
            islessthan: 'lt',
            lessthan: 'lt',
            less: 'lt',
            '<=': 'lte',
            le: 'lte',
            islessthanorequalto: 'lte',
            lessthanequal: 'lte',
            '>': 'gt',
            isgreaterthan: 'gt',
            greaterthan: 'gt',
            greater: 'gt',
            '>=': 'gte',
            isgreaterthanorequalto: 'gte',
            greaterthanequal: 'gte',
            ge: 'gte',
            notsubstringof: 'doesnotcontain',
            isnull: 'isnull',
            isempty: 'isempty',
            isnotempty: 'isnotempty'
        };
        function normalizeOperator(expression) {
            var idx, length, filter, operator, filters = expression.filters;
            if (filters) {
                for (idx = 0, length = filters.length; idx < length; idx++) {
                    filter = filters[idx];
                    operator = filter.operator;
                    if (operator && typeof operator === STRING) {
                        filter.operator = operatorMap[operator.toLowerCase()] || operator;
                    }
                    normalizeOperator(filter);
                }
            }
        }
        function normalizeFilter(expression) {
            if (expression && !isEmptyObject(expression)) {
                if (isArray(expression) || !expression.filters) {
                    expression = {
                        logic: 'and',
                        filters: isArray(expression) ? expression : [expression]
                    };
                }
                normalizeOperator(expression);
                return expression;
            }
        }
        Query.normalizeFilter = normalizeFilter;
        function compareDescriptor(f1, f2) {
            if (f1.logic || f2.logic) {
                return false;
            }
            return f1.field === f2.field && f1.value === f2.value && f1.operator === f2.operator;
        }
        function normalizeDescriptor(filter) {
            filter = filter || {};
            if (isEmptyObject(filter)) {
                return {
                    logic: 'and',
                    filters: []
                };
            }
            return normalizeFilter(filter);
        }
        function fieldComparer(a, b) {
            if (b.logic || a.field > b.field) {
                return 1;
            } else if (a.field < b.field) {
                return -1;
            } else {
                return 0;
            }
        }
        function compareFilters(expr1, expr2) {
            expr1 = normalizeDescriptor(expr1);
            expr2 = normalizeDescriptor(expr2);
            if (expr1.logic !== expr2.logic) {
                return false;
            }
            var f1, f2;
            var filters1 = (expr1.filters || []).slice();
            var filters2 = (expr2.filters || []).slice();
            if (filters1.length !== filters2.length) {
                return false;
            }
            filters1 = filters1.sort(fieldComparer);
            filters2 = filters2.sort(fieldComparer);
            for (var idx = 0; idx < filters1.length; idx++) {
                f1 = filters1[idx];
                f2 = filters2[idx];
                if (f1.logic && f2.logic) {
                    if (!compareFilters(f1, f2)) {
                        return false;
                    }
                } else if (!compareDescriptor(f1, f2)) {
                    return false;
                }
            }
            return true;
        }
        Query.compareFilters = compareFilters;
        function normalizeAggregate(expressions) {
            return isArray(expressions) ? expressions : [expressions];
        }
        function normalizeGroup(field, dir) {
            var descriptor = typeof field === STRING ? {
                    field: field,
                    dir: dir
                } : field, descriptors = isArray(descriptor) ? descriptor : descriptor !== undefined ? [descriptor] : [];
            return map(descriptors, function (d) {
                return {
                    field: d.field,
                    dir: d.dir || 'asc',
                    aggregates: d.aggregates
                };
            });
        }
        Query.prototype = {
            toArray: function () {
                return this.data;
            },
            range: function (index, count) {
                return new Query(this.data.slice(index, index + count));
            },
            skip: function (count) {
                return new Query(this.data.slice(count));
            },
            take: function (count) {
                return new Query(this.data.slice(0, count));
            },
            select: function (selector) {
                return new Query(map(this.data, selector));
            },
            order: function (selector, dir) {
                var sort = { dir: dir };
                if (selector) {
                    if (selector.compare) {
                        sort.compare = selector.compare;
                    } else {
                        sort.field = selector;
                    }
                }
                return new Query(this.data.slice(0).sort(Comparer.create(sort)));
            },
            orderBy: function (selector) {
                return this.order(selector, 'asc');
            },
            orderByDescending: function (selector) {
                return this.order(selector, 'desc');
            },
            sort: function (field, dir, comparer) {
                var idx, length, descriptors = normalizeSort(field, dir), comparers = [];
                comparer = comparer || Comparer;
                if (descriptors.length) {
                    for (idx = 0, length = descriptors.length; idx < length; idx++) {
                        comparers.push(comparer.create(descriptors[idx]));
                    }
                    return this.orderBy({ compare: comparer.combine(comparers) });
                }
                return this;
            },
            filter: function (expressions) {
                var idx, current, length, compiled, predicate, data = this.data, fields, operators, result = [], filter;
                expressions = normalizeFilter(expressions);
                if (!expressions || expressions.filters.length === 0) {
                    return this;
                }
                compiled = Query.filterExpr(expressions);
                fields = compiled.fields;
                operators = compiled.operators;
                predicate = filter = new Function('d, __f, __o', 'return ' + compiled.expression);
                if (fields.length || operators.length) {
                    filter = function (d) {
                        return predicate(d, fields, operators);
                    };
                }
                for (idx = 0, length = data.length; idx < length; idx++) {
                    current = data[idx];
                    if (filter(current)) {
                        result.push(current);
                    }
                }
                return new Query(result);
            },
            group: function (descriptors, allData) {
                descriptors = normalizeGroup(descriptors || []);
                allData = allData || this.data;
                var that = this, result = new Query(that.data), descriptor;
                if (descriptors.length > 0) {
                    descriptor = descriptors[0];
                    result = result.groupBy(descriptor).select(function (group) {
                        var data = new Query(allData).filter([{
                                field: group.field,
                                operator: 'eq',
                                value: group.value,
                                ignoreCase: false
                            }]);
                        return {
                            field: group.field,
                            value: group.value,
                            items: descriptors.length > 1 ? new Query(group.items).group(descriptors.slice(1), data.toArray()).toArray() : group.items,
                            hasSubgroups: descriptors.length > 1,
                            aggregates: data.aggregate(descriptor.aggregates)
                        };
                    });
                }
                return result;
            },
            groupBy: function (descriptor) {
                if (isEmptyObject(descriptor) || !this.data.length) {
                    return new Query([]);
                }
                var field = descriptor.field, sorted = this._sortForGrouping(field, descriptor.dir || 'asc'), accessor = kendo.accessor(field), item, groupValue = accessor.get(sorted[0], field), group = {
                        field: field,
                        value: groupValue,
                        items: []
                    }, currentValue, idx, len, result = [group];
                for (idx = 0, len = sorted.length; idx < len; idx++) {
                    item = sorted[idx];
                    currentValue = accessor.get(item, field);
                    if (!groupValueComparer(groupValue, currentValue)) {
                        groupValue = currentValue;
                        group = {
                            field: field,
                            value: groupValue,
                            items: []
                        };
                        result.push(group);
                    }
                    group.items.push(item);
                }
                return new Query(result);
            },
            _sortForGrouping: function (field, dir) {
                var idx, length, data = this.data;
                if (!stableSort) {
                    for (idx = 0, length = data.length; idx < length; idx++) {
                        data[idx].__position = idx;
                    }
                    data = new Query(data).sort(field, dir, StableComparer).toArray();
                    for (idx = 0, length = data.length; idx < length; idx++) {
                        delete data[idx].__position;
                    }
                    return data;
                }
                return this.sort(field, dir).toArray();
            },
            aggregate: function (aggregates) {
                var idx, len, result = {}, state = {};
                if (aggregates && aggregates.length) {
                    for (idx = 0, len = this.data.length; idx < len; idx++) {
                        calculateAggregate(result, aggregates, this.data[idx], idx, len, state);
                    }
                }
                return result;
            }
        };
        function groupValueComparer(a, b) {
            if (a && a.getTime && b && b.getTime) {
                return a.getTime() === b.getTime();
            }
            return a === b;
        }
        function calculateAggregate(accumulator, aggregates, item, index, length, state) {
            aggregates = aggregates || [];
            var idx, aggr, functionName, len = aggregates.length;
            for (idx = 0; idx < len; idx++) {
                aggr = aggregates[idx];
                functionName = aggr.aggregate;
                var field = aggr.field;
                accumulator[field] = accumulator[field] || {};
                state[field] = state[field] || {};
                state[field][functionName] = state[field][functionName] || {};
                accumulator[field][functionName] = functions[functionName.toLowerCase()](accumulator[field][functionName], item, kendo.accessor(field), index, length, state[field][functionName]);
            }
        }
        var functions = {
            sum: function (accumulator, item, accessor) {
                var value = accessor.get(item);
                if (!isNumber(accumulator)) {
                    accumulator = value;
                } else if (isNumber(value)) {
                    accumulator += value;
                }
                return accumulator;
            },
            count: function (accumulator) {
                return (accumulator || 0) + 1;
            },
            average: function (accumulator, item, accessor, index, length, state) {
                var value = accessor.get(item);
                if (state.count === undefined) {
                    state.count = 0;
                }
                if (!isNumber(accumulator)) {
                    accumulator = value;
                } else if (isNumber(value)) {
                    accumulator += value;
                }
                if (isNumber(value)) {
                    state.count++;
                }
                if (index == length - 1 && isNumber(accumulator)) {
                    accumulator = accumulator / state.count;
                }
                return accumulator;
            },
            max: function (accumulator, item, accessor) {
                var value = accessor.get(item);
                if (!isNumber(accumulator) && !isDate(accumulator)) {
                    accumulator = value;
                }
                if (accumulator < value && (isNumber(value) || isDate(value))) {
                    accumulator = value;
                }
                return accumulator;
            },
            min: function (accumulator, item, accessor) {
                var value = accessor.get(item);
                if (!isNumber(accumulator) && !isDate(accumulator)) {
                    accumulator = value;
                }
                if (accumulator > value && (isNumber(value) || isDate(value))) {
                    accumulator = value;
                }
                return accumulator;
            }
        };
        function isNumber(val) {
            return typeof val === 'number' && !isNaN(val);
        }
        function isDate(val) {
            return val && val.getTime;
        }
        function toJSON(array) {
            var idx, length = array.length, result = new Array(length);
            for (idx = 0; idx < length; idx++) {
                result[idx] = array[idx].toJSON();
            }
            return result;
        }
        Query.process = function (data, options) {
            options = options || {};
            var query = new Query(data), group = options.group, sort = normalizeGroup(group || []).concat(normalizeSort(options.sort || [])), total, filterCallback = options.filterCallback, filter = options.filter, skip = options.skip, take = options.take;
            if (filter) {
                query = query.filter(filter);
                if (filterCallback) {
                    query = filterCallback(query);
                }
                total = query.toArray().length;
            }
            if (sort) {
                query = query.sort(sort);
                if (group) {
                    data = query.toArray();
                }
            }
            if (skip !== undefined && take !== undefined) {
                query = query.range(skip, take);
            }
            if (group) {
                query = query.group(group, data);
            }
            return {
                total: total,
                data: query.toArray()
            };
        };
        var LocalTransport = Class.extend({
            init: function (options) {
                this.data = options.data;
            },
            read: function (options) {
                options.success(this.data);
            },
            update: function (options) {
                options.success(options.data);
            },
            create: function (options) {
                options.success(options.data);
            },
            destroy: function (options) {
                options.success(options.data);
            }
        });
        var RemoteTransport = Class.extend({
            init: function (options) {
                var that = this, parameterMap;
                options = that.options = extend({}, that.options, options);
                each(crud, function (index, type) {
                    if (typeof options[type] === STRING) {
                        options[type] = { url: options[type] };
                    }
                });
                that.cache = options.cache ? Cache.create(options.cache) : {
                    find: noop,
                    add: noop
                };
                parameterMap = options.parameterMap;
                if (isFunction(options.push)) {
                    that.push = options.push;
                }
                if (!that.push) {
                    that.push = identity;
                }
                that.parameterMap = isFunction(parameterMap) ? parameterMap : function (options) {
                    var result = {};
                    each(options, function (option, value) {
                        if (option in parameterMap) {
                            option = parameterMap[option];
                            if (isPlainObject(option)) {
                                value = option.value(value);
                                option = option.key;
                            }
                        }
                        result[option] = value;
                    });
                    return result;
                };
            },
            options: { parameterMap: identity },
            create: function (options) {
                return ajax(this.setup(options, CREATE));
            },
            read: function (options) {
                var that = this, success, error, result, cache = that.cache;
                options = that.setup(options, READ);
                success = options.success || noop;
                error = options.error || noop;
                result = cache.find(options.data);
                if (result !== undefined) {
                    success(result);
                } else {
                    options.success = function (result) {
                        cache.add(options.data, result);
                        success(result);
                    };
                    $.ajax(options);
                }
            },
            update: function (options) {
                return ajax(this.setup(options, UPDATE));
            },
            destroy: function (options) {
                return ajax(this.setup(options, DESTROY));
            },
            setup: function (options, type) {
                options = options || {};
                var that = this, parameters, operation = that.options[type], data = isFunction(operation.data) ? operation.data(options.data) : operation.data;
                options = extend(true, {}, operation, options);
                parameters = extend(true, {}, data, options.data);
                options.data = that.parameterMap(parameters, type);
                if (isFunction(options.url)) {
                    options.url = options.url(parameters);
                }
                return options;
            }
        });
        var Cache = Class.extend({
            init: function () {
                this._store = {};
            },
            add: function (key, data) {
                if (key !== undefined) {
                    this._store[stringify(key)] = data;
                }
            },
            find: function (key) {
                return this._store[stringify(key)];
            },
            clear: function () {
                this._store = {};
            },
            remove: function (key) {
                delete this._store[stringify(key)];
            }
        });
        Cache.create = function (options) {
            var store = {
                'inmemory': function () {
                    return new Cache();
                }
            };
            if (isPlainObject(options) && isFunction(options.find)) {
                return options;
            }
            if (options === true) {
                return new Cache();
            }
            return store[options]();
        };
        function serializeRecords(data, getters, modelInstance, originalFieldNames, fieldNames) {
            var record, getter, originalName, idx, setters = {}, length;
            for (idx = 0, length = data.length; idx < length; idx++) {
                record = data[idx];
                for (getter in getters) {
                    originalName = fieldNames[getter];
                    if (originalName && originalName !== getter) {
                        if (!setters[originalName]) {
                            setters[originalName] = kendo.setter(originalName);
                        }
                        setters[originalName](record, getters[getter](record));
                        delete record[getter];
                    }
                }
            }
        }
        function convertRecords(data, getters, modelInstance, originalFieldNames, fieldNames) {
            var record, getter, originalName, idx, length;
            for (idx = 0, length = data.length; idx < length; idx++) {
                record = data[idx];
                for (getter in getters) {
                    record[getter] = modelInstance._parse(getter, getters[getter](record));
                    originalName = fieldNames[getter];
                    if (originalName && originalName !== getter) {
                        delete record[originalName];
                    }
                }
            }
        }
        function convertGroup(data, getters, modelInstance, originalFieldNames, fieldNames) {
            var record, idx, fieldName, length;
            for (idx = 0, length = data.length; idx < length; idx++) {
                record = data[idx];
                fieldName = originalFieldNames[record.field];
                if (fieldName && fieldName != record.field) {
                    record.field = fieldName;
                }
                record.value = modelInstance._parse(record.field, record.value);
                if (record.hasSubgroups) {
                    convertGroup(record.items, getters, modelInstance, originalFieldNames, fieldNames);
                } else {
                    convertRecords(record.items, getters, modelInstance, originalFieldNames, fieldNames);
                }
            }
        }
        function wrapDataAccess(originalFunction, model, converter, getters, originalFieldNames, fieldNames) {
            return function (data) {
                data = originalFunction(data);
                if (data && !isEmptyObject(getters)) {
                    if (toString.call(data) !== '[object Array]' && !(data instanceof ObservableArray)) {
                        data = [data];
                    }
                    converter(data, getters, new model(), originalFieldNames, fieldNames);
                }
                return data || [];
            };
        }
        var DataReader = Class.extend({
            init: function (schema) {
                var that = this, member, get, model, base;
                schema = schema || {};
                for (member in schema) {
                    get = schema[member];
                    that[member] = typeof get === STRING ? getter(get) : get;
                }
                base = schema.modelBase || Model;
                if (isPlainObject(that.model)) {
                    that.model = model = base.define(that.model);
                }
                var dataFunction = proxy(that.data, that);
                that._dataAccessFunction = dataFunction;
                if (that.model) {
                    var groupsFunction = proxy(that.groups, that), serializeFunction = proxy(that.serialize, that), originalFieldNames = {}, getters = {}, serializeGetters = {}, fieldNames = {}, shouldSerialize = false, fieldName;
                    model = that.model;
                    if (model.fields) {
                        each(model.fields, function (field, value) {
                            var fromName;
                            fieldName = field;
                            if (isPlainObject(value) && value.field) {
                                fieldName = value.field;
                            } else if (typeof value === STRING) {
                                fieldName = value;
                            }
                            if (isPlainObject(value) && value.from) {
                                fromName = value.from;
                            }
                            shouldSerialize = shouldSerialize || fromName && fromName !== field || fieldName !== field;
                            getters[field] = getter(fromName || fieldName);
                            serializeGetters[field] = getter(field);
                            originalFieldNames[fromName || fieldName] = field;
                            fieldNames[field] = fromName || fieldName;
                        });
                        if (!schema.serialize && shouldSerialize) {
                            that.serialize = wrapDataAccess(serializeFunction, model, serializeRecords, serializeGetters, originalFieldNames, fieldNames);
                        }
                    }
                    that._dataAccessFunction = dataFunction;
                    that.data = wrapDataAccess(dataFunction, model, convertRecords, getters, originalFieldNames, fieldNames);
                    that.groups = wrapDataAccess(groupsFunction, model, convertGroup, getters, originalFieldNames, fieldNames);
                }
            },
            errors: function (data) {
                return data ? data.errors : null;
            },
            parse: identity,
            data: identity,
            total: function (data) {
                return data.length;
            },
            groups: identity,
            aggregates: function () {
                return {};
            },
            serialize: function (data) {
                return data;
            }
        });
        function mergeGroups(target, dest, skip, take) {
            var group, idx = 0, items;
            while (dest.length && take) {
                group = dest[idx];
                items = group.items;
                var length = items.length;
                if (target && target.field === group.field && target.value === group.value) {
                    if (target.hasSubgroups && target.items.length) {
                        mergeGroups(target.items[target.items.length - 1], group.items, skip, take);
                    } else {
                        items = items.slice(skip, skip + take);
                        target.items = target.items.concat(items);
                    }
                    dest.splice(idx--, 1);
                } else if (group.hasSubgroups && items.length) {
                    mergeGroups(group, items, skip, take);
                    if (!group.items.length) {
                        dest.splice(idx--, 1);
                    }
                } else {
                    items = items.slice(skip, skip + take);
                    group.items = items;
                    if (!group.items.length) {
                        dest.splice(idx--, 1);
                    }
                }
                if (items.length === 0) {
                    skip -= length;
                } else {
                    skip = 0;
                    take -= items.length;
                }
                if (++idx >= dest.length) {
                    break;
                }
            }
            if (idx < dest.length) {
                dest.splice(idx, dest.length - idx);
            }
        }
        function flattenGroups(data) {
            var idx, result = [], length, items, itemIndex;
            for (idx = 0, length = data.length; idx < length; idx++) {
                var group = data.at(idx);
                if (group.hasSubgroups) {
                    result = result.concat(flattenGroups(group.items));
                } else {
                    items = group.items;
                    for (itemIndex = 0; itemIndex < items.length; itemIndex++) {
                        result.push(items.at(itemIndex));
                    }
                }
            }
            return result;
        }
        function wrapGroupItems(data, model) {
            var idx, length, group;
            if (model) {
                for (idx = 0, length = data.length; idx < length; idx++) {
                    group = data.at(idx);
                    if (group.hasSubgroups) {
                        wrapGroupItems(group.items, model);
                    } else {
                        group.items = new LazyObservableArray(group.items, model);
                    }
                }
            }
        }
        function eachGroupItems(data, func) {
            for (var idx = 0, length = data.length; idx < length; idx++) {
                if (data[idx].hasSubgroups) {
                    if (eachGroupItems(data[idx].items, func)) {
                        return true;
                    }
                } else if (func(data[idx].items, data[idx])) {
                    return true;
                }
            }
        }
        function replaceInRanges(ranges, data, item, observable) {
            for (var idx = 0; idx < ranges.length; idx++) {
                if (ranges[idx].data === data) {
                    break;
                }
                if (replaceInRange(ranges[idx].data, item, observable)) {
                    break;
                }
            }
        }
        function replaceInRange(items, item, observable) {
            for (var idx = 0, length = items.length; idx < length; idx++) {
                if (items[idx] && items[idx].hasSubgroups) {
                    return replaceInRange(items[idx].items, item, observable);
                } else if (items[idx] === item || items[idx] === observable) {
                    items[idx] = observable;
                    return true;
                }
            }
        }
        function replaceWithObservable(view, data, ranges, type, serverGrouping) {
            for (var viewIndex = 0, length = view.length; viewIndex < length; viewIndex++) {
                var item = view[viewIndex];
                if (!item || item instanceof type) {
                    continue;
                }
                if (item.hasSubgroups !== undefined && !serverGrouping) {
                    replaceWithObservable(item.items, data, ranges, type, serverGrouping);
                } else {
                    for (var idx = 0; idx < data.length; idx++) {
                        if (data[idx] === item) {
                            view[viewIndex] = data.at(idx);
                            replaceInRanges(ranges, data, item, view[viewIndex]);
                            break;
                        }
                    }
                }
            }
        }
        function removeModel(data, model) {
            var idx, length;
            for (idx = 0, length = data.length; idx < length; idx++) {
                var dataItem = data.at(idx);
                if (dataItem.uid == model.uid) {
                    data.splice(idx, 1);
                    return dataItem;
                }
            }
        }
        function indexOfPristineModel(data, model) {
            if (model) {
                return indexOf(data, function (item) {
                    return item.uid && item.uid == model.uid || item[model.idField] === model.id && model.id !== model._defaultId;
                });
            }
            return -1;
        }
        function indexOfModel(data, model) {
            if (model) {
                return indexOf(data, function (item) {
                    return item.uid == model.uid;
                });
            }
            return -1;
        }
        function indexOf(data, comparer) {
            var idx, length;
            for (idx = 0, length = data.length; idx < length; idx++) {
                if (comparer(data[idx])) {
                    return idx;
                }
            }
            return -1;
        }
        function fieldNameFromModel(fields, name) {
            if (fields && !isEmptyObject(fields)) {
                var descriptor = fields[name];
                var fieldName;
                if (isPlainObject(descriptor)) {
                    fieldName = descriptor.from || descriptor.field || name;
                } else {
                    fieldName = fields[name] || name;
                }
                if (isFunction(fieldName)) {
                    return name;
                }
                return fieldName;
            }
            return name;
        }
        function convertFilterDescriptorsField(descriptor, model) {
            var idx, length, target = {};
            for (var field in descriptor) {
                if (field !== 'filters') {
                    target[field] = descriptor[field];
                }
            }
            if (descriptor.filters) {
                target.filters = [];
                for (idx = 0, length = descriptor.filters.length; idx < length; idx++) {
                    target.filters[idx] = convertFilterDescriptorsField(descriptor.filters[idx], model);
                }
            } else {
                target.field = fieldNameFromModel(model.fields, target.field);
            }
            return target;
        }
        function convertDescriptorsField(descriptors, model) {
            var idx, length, result = [], target, descriptor;
            for (idx = 0, length = descriptors.length; idx < length; idx++) {
                target = {};
                descriptor = descriptors[idx];
                for (var field in descriptor) {
                    target[field] = descriptor[field];
                }
                target.field = fieldNameFromModel(model.fields, target.field);
                if (target.aggregates && isArray(target.aggregates)) {
                    target.aggregates = convertDescriptorsField(target.aggregates, model);
                }
                result.push(target);
            }
            return result;
        }
        var DataSource = Observable.extend({
            init: function (options) {
                var that = this, model, data;
                if (options) {
                    data = options.data;
                }
                options = that.options = extend({}, that.options, options);
                that._map = {};
                that._prefetch = {};
                that._data = [];
                that._pristineData = [];
                that._ranges = [];
                that._view = [];
                that._pristineTotal = 0;
                that._destroyed = [];
                that._pageSize = options.pageSize;
                that._page = options.page || (options.pageSize ? 1 : undefined);
                that._sort = normalizeSort(options.sort);
                that._filter = normalizeFilter(options.filter);
                that._group = normalizeGroup(options.group);
                that._aggregate = options.aggregate;
                that._total = options.total;
                that._shouldDetachObservableParents = true;
                Observable.fn.init.call(that);
                that.transport = Transport.create(options, data, that);
                if (isFunction(that.transport.push)) {
                    that.transport.push({
                        pushCreate: proxy(that._pushCreate, that),
                        pushUpdate: proxy(that._pushUpdate, that),
                        pushDestroy: proxy(that._pushDestroy, that)
                    });
                }
                if (options.offlineStorage != null) {
                    if (typeof options.offlineStorage == 'string') {
                        var key = options.offlineStorage;
                        that._storage = {
                            getItem: function () {
                                return JSON.parse(localStorage.getItem(key));
                            },
                            setItem: function (item) {
                                localStorage.setItem(key, stringify(that.reader.serialize(item)));
                            }
                        };
                    } else {
                        that._storage = options.offlineStorage;
                    }
                }
                that.reader = new kendo.data.readers[options.schema.type || 'json'](options.schema);
                model = that.reader.model || {};
                that._detachObservableParents();
                that._data = that._observe(that._data);
                that._online = true;
                that.bind([
                    'push',
                    ERROR,
                    CHANGE,
                    REQUESTSTART,
                    SYNC,
                    REQUESTEND,
                    PROGRESS
                ], options);
            },
            options: {
                data: null,
                schema: { modelBase: Model },
                offlineStorage: null,
                serverSorting: false,
                serverPaging: false,
                serverFiltering: false,
                serverGrouping: false,
                serverAggregates: false,
                batch: false
            },
            clone: function () {
                return this;
            },
            online: function (value) {
                if (value !== undefined) {
                    if (this._online != value) {
                        this._online = value;
                        if (value) {
                            return this.sync();
                        }
                    }
                    return $.Deferred().resolve().promise();
                } else {
                    return this._online;
                }
            },
            offlineData: function (state) {
                if (this.options.offlineStorage == null) {
                    return null;
                }
                if (state !== undefined) {
                    return this._storage.setItem(state);
                }
                return this._storage.getItem() || [];
            },
            _isServerGrouped: function () {
                var group = this.group() || [];
                return this.options.serverGrouping && group.length;
            },
            _pushCreate: function (result) {
                this._push(result, 'pushCreate');
            },
            _pushUpdate: function (result) {
                this._push(result, 'pushUpdate');
            },
            _pushDestroy: function (result) {
                this._push(result, 'pushDestroy');
            },
            _push: function (result, operation) {
                var data = this._readData(result);
                if (!data) {
                    data = result;
                }
                this[operation](data);
            },
            _flatData: function (data, skip) {
                if (data) {
                    if (this._isServerGrouped()) {
                        return flattenGroups(data);
                    }
                    if (!skip) {
                        for (var idx = 0; idx < data.length; idx++) {
                            data.at(idx);
                        }
                    }
                }
                return data;
            },
            parent: noop,
            get: function (id) {
                var idx, length, data = this._flatData(this._data);
                for (idx = 0, length = data.length; idx < length; idx++) {
                    if (data[idx].id == id) {
                        return data[idx];
                    }
                }
            },
            getByUid: function (id) {
                var idx, length, data = this._flatData(this._data);
                if (!data) {
                    return;
                }
                for (idx = 0, length = data.length; idx < length; idx++) {
                    if (data[idx].uid == id) {
                        return data[idx];
                    }
                }
            },
            indexOf: function (model) {
                return indexOfModel(this._data, model);
            },
            at: function (index) {
                return this._data.at(index);
            },
            data: function (value) {
                var that = this;
                if (value !== undefined) {
                    that._detachObservableParents();
                    that._data = this._observe(value);
                    that._pristineData = value.slice(0);
                    that._storeData();
                    that._ranges = [];
                    that.trigger('reset');
                    that._addRange(that._data);
                    that._total = that._data.length;
                    that._pristineTotal = that._total;
                    that._process(that._data);
                } else {
                    if (that._data) {
                        for (var idx = 0; idx < that._data.length; idx++) {
                            that._data.at(idx);
                        }
                    }
                    return that._data;
                }
            },
            view: function (value) {
                if (value === undefined) {
                    return this._view;
                } else {
                    this._view = this._observeView(value);
                }
            },
            _observeView: function (data) {
                var that = this;
                replaceWithObservable(data, that._data, that._ranges, that.reader.model || ObservableObject, that._isServerGrouped());
                var view = new LazyObservableArray(data, that.reader.model);
                view.parent = function () {
                    return that.parent();
                };
                return view;
            },
            flatView: function () {
                var groups = this.group() || [];
                if (groups.length) {
                    return flattenGroups(this._view);
                } else {
                    return this._view;
                }
            },
            add: function (model) {
                return this.insert(this._data.length, model);
            },
            _createNewModel: function (model) {
                if (this.reader.model) {
                    return new this.reader.model(model);
                }
                if (model instanceof ObservableObject) {
                    return model;
                }
                return new ObservableObject(model);
            },
            insert: function (index, model) {
                if (!model) {
                    model = index;
                    index = 0;
                }
                if (!(model instanceof Model)) {
                    model = this._createNewModel(model);
                }
                if (this._isServerGrouped()) {
                    this._data.splice(index, 0, this._wrapInEmptyGroup(model));
                } else {
                    this._data.splice(index, 0, model);
                }
                return model;
            },
            pushInsert: function (index, items) {
                if (!items) {
                    items = index;
                    index = 0;
                }
                if (!isArray(items)) {
                    items = [items];
                }
                var pushed = [];
                var autoSync = this.options.autoSync;
                this.options.autoSync = false;
                try {
                    for (var idx = 0; idx < items.length; idx++) {
                        var item = items[idx];
                        var result = this.insert(index, item);
                        pushed.push(result);
                        var pristine = result.toJSON();
                        if (this._isServerGrouped()) {
                            pristine = this._wrapInEmptyGroup(pristine);
                        }
                        this._pristineData.push(pristine);
                        index++;
                    }
                } finally {
                    this.options.autoSync = autoSync;
                }
                if (pushed.length) {
                    this.trigger('push', {
                        type: 'create',
                        items: pushed
                    });
                }
            },
            pushCreate: function (items) {
                this.pushInsert(this._data.length, items);
            },
            pushUpdate: function (items) {
                if (!isArray(items)) {
                    items = [items];
                }
                var pushed = [];
                for (var idx = 0; idx < items.length; idx++) {
                    var item = items[idx];
                    var model = this._createNewModel(item);
                    var target = this.get(model.id);
                    if (target) {
                        pushed.push(target);
                        target.accept(item);
                        target.trigger(CHANGE);
                        this._updatePristineForModel(target, item);
                    } else {
                        this.pushCreate(item);
                    }
                }
                if (pushed.length) {
                    this.trigger('push', {
                        type: 'update',
                        items: pushed
                    });
                }
            },
            pushDestroy: function (items) {
                var pushed = this._removeItems(items);
                if (pushed.length) {
                    this.trigger('push', {
                        type: 'destroy',
                        items: pushed
                    });
                }
            },
            _removeItems: function (items) {
                if (!isArray(items)) {
                    items = [items];
                }
                var destroyed = [];
                var autoSync = this.options.autoSync;
                this.options.autoSync = false;
                try {
                    for (var idx = 0; idx < items.length; idx++) {
                        var item = items[idx];
                        var model = this._createNewModel(item);
                        var found = false;
                        this._eachItem(this._data, function (items) {
                            for (var idx = 0; idx < items.length; idx++) {
                                var item = items.at(idx);
                                if (item.id === model.id) {
                                    destroyed.push(item);
                                    items.splice(idx, 1);
                                    found = true;
                                    break;
                                }
                            }
                        });
                        if (found) {
                            this._removePristineForModel(model);
                            this._destroyed.pop();
                        }
                    }
                } finally {
                    this.options.autoSync = autoSync;
                }
                return destroyed;
            },
            remove: function (model) {
                var result, that = this, hasGroups = that._isServerGrouped();
                this._eachItem(that._data, function (items) {
                    result = removeModel(items, model);
                    if (result && hasGroups) {
                        if (!result.isNew || !result.isNew()) {
                            that._destroyed.push(result);
                        }
                        return true;
                    }
                });
                this._removeModelFromRanges(model);
                this._updateRangesLength();
                return model;
            },
            destroyed: function () {
                return this._destroyed;
            },
            created: function () {
                var idx, length, result = [], data = this._flatData(this._data);
                for (idx = 0, length = data.length; idx < length; idx++) {
                    if (data[idx].isNew && data[idx].isNew()) {
                        result.push(data[idx]);
                    }
                }
                return result;
            },
            updated: function () {
                var idx, length, result = [], data = this._flatData(this._data);
                for (idx = 0, length = data.length; idx < length; idx++) {
                    if (data[idx].isNew && !data[idx].isNew() && data[idx].dirty) {
                        result.push(data[idx]);
                    }
                }
                return result;
            },
            sync: function () {
                var that = this, created = [], updated = [], destroyed = that._destroyed;
                var promise = $.Deferred().resolve().promise();
                if (that.online()) {
                    if (!that.reader.model) {
                        return promise;
                    }
                    created = that.created();
                    updated = that.updated();
                    var promises = [];
                    if (that.options.batch && that.transport.submit) {
                        promises = that._sendSubmit(created, updated, destroyed);
                    } else {
                        promises.push.apply(promises, that._send('create', created));
                        promises.push.apply(promises, that._send('update', updated));
                        promises.push.apply(promises, that._send('destroy', destroyed));
                    }
                    promise = $.when.apply(null, promises).then(function () {
                        var idx, length;
                        for (idx = 0, length = arguments.length; idx < length; idx++) {
                            if (arguments[idx]) {
                                that._accept(arguments[idx]);
                            }
                        }
                        that._storeData(true);
                        that._change({ action: 'sync' });
                        that.trigger(SYNC);
                    });
                } else {
                    that._storeData(true);
                    that._change({ action: 'sync' });
                }
                return promise;
            },
            cancelChanges: function (model) {
                var that = this;
                if (model instanceof kendo.data.Model) {
                    that._cancelModel(model);
                } else {
                    that._destroyed = [];
                    that._detachObservableParents();
                    that._data = that._observe(that._pristineData);
                    if (that.options.serverPaging) {
                        that._total = that._pristineTotal;
                    }
                    that._ranges = [];
                    that._addRange(that._data);
                    that._change();
                    that._markOfflineUpdatesAsDirty();
                }
            },
            _markOfflineUpdatesAsDirty: function () {
                var that = this;
                if (that.options.offlineStorage != null) {
                    that._eachItem(that._data, function (items) {
                        for (var idx = 0; idx < items.length; idx++) {
                            var item = items.at(idx);
                            if (item.__state__ == 'update' || item.__state__ == 'create') {
                                item.dirty = true;
                            }
                        }
                    });
                }
            },
            hasChanges: function () {
                var idx, length, data = this._flatData(this._data);
                if (this._destroyed.length) {
                    return true;
                }
                for (idx = 0, length = data.length; idx < length; idx++) {
                    if (data[idx].isNew && data[idx].isNew() || data[idx].dirty) {
                        return true;
                    }
                }
                return false;
            },
            _accept: function (result) {
                var that = this, models = result.models, response = result.response, idx = 0, serverGroup = that._isServerGrouped(), pristine = that._pristineData, type = result.type, length;
                that.trigger(REQUESTEND, {
                    response: response,
                    type: type
                });
                if (response && !isEmptyObject(response)) {
                    response = that.reader.parse(response);
                    if (that._handleCustomErrors(response)) {
                        return;
                    }
                    response = that.reader.data(response);
                    if (!isArray(response)) {
                        response = [response];
                    }
                } else {
                    response = $.map(models, function (model) {
                        return model.toJSON();
                    });
                }
                if (type === 'destroy') {
                    that._destroyed = [];
                }
                for (idx = 0, length = models.length; idx < length; idx++) {
                    if (type !== 'destroy') {
                        models[idx].accept(response[idx]);
                        if (type === 'create') {
                            pristine.push(serverGroup ? that._wrapInEmptyGroup(models[idx]) : response[idx]);
                        } else if (type === 'update') {
                            that._updatePristineForModel(models[idx], response[idx]);
                        }
                    } else {
                        that._removePristineForModel(models[idx]);
                    }
                }
            },
            _updatePristineForModel: function (model, values) {
                this._executeOnPristineForModel(model, function (index, items) {
                    kendo.deepExtend(items[index], values);
                });
            },
            _executeOnPristineForModel: function (model, callback) {
                this._eachPristineItem(function (items) {
                    var index = indexOfPristineModel(items, model);
                    if (index > -1) {
                        callback(index, items);
                        return true;
                    }
                });
            },
            _removePristineForModel: function (model) {
                this._executeOnPristineForModel(model, function (index, items) {
                    items.splice(index, 1);
                });
            },
            _readData: function (data) {
                var read = !this._isServerGrouped() ? this.reader.data : this.reader.groups;
                return read.call(this.reader, data);
            },
            _eachPristineItem: function (callback) {
                this._eachItem(this._pristineData, callback);
            },
            _eachItem: function (data, callback) {
                if (data && data.length) {
                    if (this._isServerGrouped()) {
                        eachGroupItems(data, callback);
                    } else {
                        callback(data);
                    }
                }
            },
            _pristineForModel: function (model) {
                var pristine, idx, callback = function (items) {
                        idx = indexOfPristineModel(items, model);
                        if (idx > -1) {
                            pristine = items[idx];
                            return true;
                        }
                    };
                this._eachPristineItem(callback);
                return pristine;
            },
            _cancelModel: function (model) {
                var pristine = this._pristineForModel(model);
                this._eachItem(this._data, function (items) {
                    var idx = indexOfModel(items, model);
                    if (idx >= 0) {
                        if (pristine && (!model.isNew() || pristine.__state__)) {
                            items[idx].accept(pristine);
                            if (pristine.__state__ == 'update') {
                                items[idx].dirty = true;
                            }
                        } else {
                            items.splice(idx, 1);
                        }
                    }
                });
            },
            _submit: function (promises, data) {
                var that = this;
                that.trigger(REQUESTSTART, { type: 'submit' });
                that.trigger(PROGRESS);
                that.transport.submit(extend({
                    success: function (response, type) {
                        var promise = $.grep(promises, function (x) {
                            return x.type == type;
                        })[0];
                        if (promise) {
                            promise.resolve({
                                response: response,
                                models: promise.models,
                                type: type
                            });
                        }
                    },
                    error: function (response, status, error) {
                        for (var idx = 0; idx < promises.length; idx++) {
                            promises[idx].reject(response);
                        }
                        that.error(response, status, error);
                    }
                }, data));
            },
            _sendSubmit: function (created, updated, destroyed) {
                var that = this, promises = [];
                if (that.options.batch) {
                    if (created.length) {
                        promises.push($.Deferred(function (deferred) {
                            deferred.type = 'create';
                            deferred.models = created;
                        }));
                    }
                    if (updated.length) {
                        promises.push($.Deferred(function (deferred) {
                            deferred.type = 'update';
                            deferred.models = updated;
                        }));
                    }
                    if (destroyed.length) {
                        promises.push($.Deferred(function (deferred) {
                            deferred.type = 'destroy';
                            deferred.models = destroyed;
                        }));
                    }
                    that._submit(promises, {
                        data: {
                            created: that.reader.serialize(toJSON(created)),
                            updated: that.reader.serialize(toJSON(updated)),
                            destroyed: that.reader.serialize(toJSON(destroyed))
                        }
                    });
                }
                return promises;
            },
            _promise: function (data, models, type) {
                var that = this;
                return $.Deferred(function (deferred) {
                    that.trigger(REQUESTSTART, { type: type });
                    that.trigger(PROGRESS);
                    that.transport[type].call(that.transport, extend({
                        success: function (response) {
                            deferred.resolve({
                                response: response,
                                models: models,
                                type: type
                            });
                        },
                        error: function (response, status, error) {
                            deferred.reject(response);
                            that.error(response, status, error);
                        }
                    }, data));
                }).promise();
            },
            _send: function (method, data) {
                var that = this, idx, length, promises = [], converted = that.reader.serialize(toJSON(data));
                if (that.options.batch) {
                    if (data.length) {
                        promises.push(that._promise({ data: { models: converted } }, data, method));
                    }
                } else {
                    for (idx = 0, length = data.length; idx < length; idx++) {
                        promises.push(that._promise({ data: converted[idx] }, [data[idx]], method));
                    }
                }
                return promises;
            },
            read: function (data) {
                var that = this, params = that._params(data);
                var deferred = $.Deferred();
                that._queueRequest(params, function () {
                    var isPrevented = that.trigger(REQUESTSTART, { type: 'read' });
                    if (!isPrevented) {
                        that.trigger(PROGRESS);
                        that._ranges = [];
                        that.trigger('reset');
                        if (that.online()) {
                            that.transport.read({
                                data: params,
                                success: function (data) {
                                    that._ranges = [];
                                    that.success(data, params);
                                    deferred.resolve();
                                },
                                error: function () {
                                    var args = slice.call(arguments);
                                    that.error.apply(that, args);
                                    deferred.reject.apply(deferred, args);
                                }
                            });
                        } else if (that.options.offlineStorage != null) {
                            that.success(that.offlineData(), params);
                            deferred.resolve();
                        }
                    } else {
                        that._dequeueRequest();
                        deferred.resolve(isPrevented);
                    }
                });
                return deferred.promise();
            },
            _readAggregates: function (data) {
                return this.reader.aggregates(data);
            },
            success: function (data) {
                var that = this, options = that.options;
                that.trigger(REQUESTEND, {
                    response: data,
                    type: 'read'
                });
                if (that.online()) {
                    data = that.reader.parse(data);
                    if (that._handleCustomErrors(data)) {
                        that._dequeueRequest();
                        return;
                    }
                    that._total = that.reader.total(data);
                    if (that._aggregate && options.serverAggregates) {
                        that._aggregateResult = that._readAggregates(data);
                    }
                    data = that._readData(data);
                    that._destroyed = [];
                } else {
                    data = that._readData(data);
                    var items = [];
                    var itemIds = {};
                    var model = that.reader.model;
                    var idField = model ? model.idField : 'id';
                    var idx;
                    for (idx = 0; idx < this._destroyed.length; idx++) {
                        var id = this._destroyed[idx][idField];
                        itemIds[id] = id;
                    }
                    for (idx = 0; idx < data.length; idx++) {
                        var item = data[idx];
                        var state = item.__state__;
                        if (state == 'destroy') {
                            if (!itemIds[item[idField]]) {
                                this._destroyed.push(this._createNewModel(item));
                            }
                        } else {
                            items.push(item);
                        }
                    }
                    data = items;
                    that._total = data.length;
                }
                that._pristineTotal = that._total;
                that._pristineData = data.slice(0);
                that._detachObservableParents();
                that._data = that._observe(data);
                that._markOfflineUpdatesAsDirty();
                that._storeData();
                that._addRange(that._data);
                that._process(that._data);
                that._dequeueRequest();
            },
            _detachObservableParents: function () {
                if (this._data && this._shouldDetachObservableParents) {
                    for (var idx = 0; idx < this._data.length; idx++) {
                        if (this._data[idx].parent) {
                            this._data[idx].parent = noop;
                        }
                    }
                }
            },
            _storeData: function (updatePristine) {
                var serverGrouping = this._isServerGrouped();
                var model = this.reader.model;
                function items(data) {
                    var state = [];
                    for (var idx = 0; idx < data.length; idx++) {
                        var dataItem = data.at(idx);
                        var item = dataItem.toJSON();
                        if (serverGrouping && dataItem.items) {
                            item.items = items(dataItem.items);
                        } else {
                            item.uid = dataItem.uid;
                            if (model) {
                                if (dataItem.isNew()) {
                                    item.__state__ = 'create';
                                } else if (dataItem.dirty) {
                                    item.__state__ = 'update';
                                }
                            }
                        }
                        state.push(item);
                    }
                    return state;
                }
                if (this.options.offlineStorage != null) {
                    var state = items(this._data);
                    var destroyed = [];
                    for (var idx = 0; idx < this._destroyed.length; idx++) {
                        var item = this._destroyed[idx].toJSON();
                        item.__state__ = 'destroy';
                        destroyed.push(item);
                    }
                    this.offlineData(state.concat(destroyed));
                    if (updatePristine) {
                        this._pristineData = this._readData(state);
                    }
                }
            },
            _addRange: function (data) {
                var that = this, start = that._skip || 0, end = start + that._flatData(data, true).length;
                that._ranges.push({
                    start: start,
                    end: end,
                    data: data,
                    timestamp: new Date().getTime()
                });
                that._ranges.sort(function (x, y) {
                    return x.start - y.start;
                });
            },
            error: function (xhr, status, errorThrown) {
                this._dequeueRequest();
                this.trigger(REQUESTEND, {});
                this.trigger(ERROR, {
                    xhr: xhr,
                    status: status,
                    errorThrown: errorThrown
                });
            },
            _params: function (data) {
                var that = this, options = extend({
                        take: that.take(),
                        skip: that.skip(),
                        page: that.page(),
                        pageSize: that.pageSize(),
                        sort: that._sort,
                        filter: that._filter,
                        group: that._group,
                        aggregate: that._aggregate
                    }, data);
                if (!that.options.serverPaging) {
                    delete options.take;
                    delete options.skip;
                    delete options.page;
                    delete options.pageSize;
                }
                if (!that.options.serverGrouping) {
                    delete options.group;
                } else if (that.reader.model && options.group) {
                    options.group = convertDescriptorsField(options.group, that.reader.model);
                }
                if (!that.options.serverFiltering) {
                    delete options.filter;
                } else if (that.reader.model && options.filter) {
                    options.filter = convertFilterDescriptorsField(options.filter, that.reader.model);
                }
                if (!that.options.serverSorting) {
                    delete options.sort;
                } else if (that.reader.model && options.sort) {
                    options.sort = convertDescriptorsField(options.sort, that.reader.model);
                }
                if (!that.options.serverAggregates) {
                    delete options.aggregate;
                } else if (that.reader.model && options.aggregate) {
                    options.aggregate = convertDescriptorsField(options.aggregate, that.reader.model);
                }
                return options;
            },
            _queueRequest: function (options, callback) {
                var that = this;
                if (!that._requestInProgress) {
                    that._requestInProgress = true;
                    that._pending = undefined;
                    callback();
                } else {
                    that._pending = {
                        callback: proxy(callback, that),
                        options: options
                    };
                }
            },
            _dequeueRequest: function () {
                var that = this;
                that._requestInProgress = false;
                if (that._pending) {
                    that._queueRequest(that._pending.options, that._pending.callback);
                }
            },
            _handleCustomErrors: function (response) {
                if (this.reader.errors) {
                    var errors = this.reader.errors(response);
                    if (errors) {
                        this.trigger(ERROR, {
                            xhr: null,
                            status: 'customerror',
                            errorThrown: 'custom error',
                            errors: errors
                        });
                        return true;
                    }
                }
                return false;
            },
            _shouldWrap: function (data) {
                var model = this.reader.model;
                if (model && data.length) {
                    return !(data[0] instanceof model);
                }
                return false;
            },
            _observe: function (data) {
                var that = this, model = that.reader.model;
                that._shouldDetachObservableParents = true;
                if (data instanceof ObservableArray) {
                    that._shouldDetachObservableParents = false;
                    if (that._shouldWrap(data)) {
                        data.type = that.reader.model;
                        data.wrapAll(data, data);
                    }
                } else {
                    var arrayType = that.pageSize() && !that.options.serverPaging ? LazyObservableArray : ObservableArray;
                    data = new arrayType(data, that.reader.model);
                    data.parent = function () {
                        return that.parent();
                    };
                }
                if (that._isServerGrouped()) {
                    wrapGroupItems(data, model);
                }
                if (that._changeHandler && that._data && that._data instanceof ObservableArray) {
                    that._data.unbind(CHANGE, that._changeHandler);
                } else {
                    that._changeHandler = proxy(that._change, that);
                }
                return data.bind(CHANGE, that._changeHandler);
            },
            _updateTotalForAction: function (action, items) {
                var that = this;
                var total = parseInt(that._total, 10);
                if (!isNumber(that._total)) {
                    total = parseInt(that._pristineTotal, 10);
                }
                if (action === 'add') {
                    total += items.length;
                } else if (action === 'remove') {
                    total -= items.length;
                } else if (action !== 'itemchange' && action !== 'sync' && !that.options.serverPaging) {
                    total = that._pristineTotal;
                } else if (action === 'sync') {
                    total = that._pristineTotal = parseInt(that._total, 10);
                }
                that._total = total;
            },
            _change: function (e) {
                var that = this, idx, length, action = e ? e.action : '';
                if (action === 'remove') {
                    for (idx = 0, length = e.items.length; idx < length; idx++) {
                        if (!e.items[idx].isNew || !e.items[idx].isNew()) {
                            that._destroyed.push(e.items[idx]);
                        }
                    }
                }
                if (that.options.autoSync && (action === 'add' || action === 'remove' || action === 'itemchange')) {
                    var handler = function (args) {
                        if (args.action === 'sync') {
                            that.unbind('change', handler);
                            that._updateTotalForAction(action, e.items);
                        }
                    };
                    that.first('change', handler);
                    that.sync();
                } else {
                    that._updateTotalForAction(action, e ? e.items : []);
                    that._process(that._data, e);
                }
            },
            _calculateAggregates: function (data, options) {
                options = options || {};
                var query = new Query(data), aggregates = options.aggregate, filter = options.filter;
                if (filter) {
                    query = query.filter(filter);
                }
                return query.aggregate(aggregates);
            },
            _process: function (data, e) {
                var that = this, options = {}, result;
                if (that.options.serverPaging !== true) {
                    options.skip = that._skip;
                    options.take = that._take || that._pageSize;
                    if (options.skip === undefined && that._page !== undefined && that._pageSize !== undefined) {
                        options.skip = (that._page - 1) * that._pageSize;
                    }
                }
                if (that.options.serverSorting !== true) {
                    options.sort = that._sort;
                }
                if (that.options.serverFiltering !== true) {
                    options.filter = that._filter;
                }
                if (that.options.serverGrouping !== true) {
                    options.group = that._group;
                }
                if (that.options.serverAggregates !== true) {
                    options.aggregate = that._aggregate;
                    that._aggregateResult = that._calculateAggregates(data, options);
                }
                result = that._queryProcess(data, options);
                that.view(result.data);
                if (result.total !== undefined && !that.options.serverFiltering) {
                    that._total = result.total;
                }
                e = e || {};
                e.items = e.items || that._view;
                that.trigger(CHANGE, e);
            },
            _queryProcess: function (data, options) {
                return Query.process(data, options);
            },
            _mergeState: function (options) {
                var that = this;
                if (options !== undefined) {
                    that._pageSize = options.pageSize;
                    that._page = options.page;
                    that._sort = options.sort;
                    that._filter = options.filter;
                    that._group = options.group;
                    that._aggregate = options.aggregate;
                    that._skip = that._currentRangeStart = options.skip;
                    that._take = options.take;
                    if (that._skip === undefined) {
                        that._skip = that._currentRangeStart = that.skip();
                        options.skip = that.skip();
                    }
                    if (that._take === undefined && that._pageSize !== undefined) {
                        that._take = that._pageSize;
                        options.take = that._take;
                    }
                    if (options.sort) {
                        that._sort = options.sort = normalizeSort(options.sort);
                    }
                    if (options.filter) {
                        that._filter = options.filter = normalizeFilter(options.filter);
                    }
                    if (options.group) {
                        that._group = options.group = normalizeGroup(options.group);
                    }
                    if (options.aggregate) {
                        that._aggregate = options.aggregate = normalizeAggregate(options.aggregate);
                    }
                }
                return options;
            },
            query: function (options) {
                var result;
                var remote = this.options.serverSorting || this.options.serverPaging || this.options.serverFiltering || this.options.serverGrouping || this.options.serverAggregates;
                if (remote || (this._data === undefined || this._data.length === 0) && !this._destroyed.length) {
                    return this.read(this._mergeState(options));
                }
                var isPrevented = this.trigger(REQUESTSTART, { type: 'read' });
                if (!isPrevented) {
                    this.trigger(PROGRESS);
                    result = this._queryProcess(this._data, this._mergeState(options));
                    if (!this.options.serverFiltering) {
                        if (result.total !== undefined) {
                            this._total = result.total;
                        } else {
                            this._total = this._data.length;
                        }
                    }
                    this._aggregateResult = this._calculateAggregates(this._data, options);
                    this.view(result.data);
                    this.trigger(REQUESTEND, { type: 'read' });
                    this.trigger(CHANGE, { items: result.data });
                }
                return $.Deferred().resolve(isPrevented).promise();
            },
            fetch: function (callback) {
                var that = this;
                var fn = function (isPrevented) {
                    if (isPrevented !== true && isFunction(callback)) {
                        callback.call(that);
                    }
                };
                return this._query().then(fn);
            },
            _query: function (options) {
                var that = this;
                return that.query(extend({}, {
                    page: that.page(),
                    pageSize: that.pageSize(),
                    sort: that.sort(),
                    filter: that.filter(),
                    group: that.group(),
                    aggregate: that.aggregate()
                }, options));
            },
            next: function (options) {
                var that = this, page = that.page(), total = that.total();
                options = options || {};
                if (!page || total && page + 1 > that.totalPages()) {
                    return;
                }
                that._skip = that._currentRangeStart = page * that.take();
                page += 1;
                options.page = page;
                that._query(options);
                return page;
            },
            prev: function (options) {
                var that = this, page = that.page();
                options = options || {};
                if (!page || page === 1) {
                    return;
                }
                that._skip = that._currentRangeStart = that._skip - that.take();
                page -= 1;
                options.page = page;
                that._query(options);
                return page;
            },
            page: function (val) {
                var that = this, skip;
                if (val !== undefined) {
                    val = math.max(math.min(math.max(val, 1), that.totalPages()), 1);
                    that._query({ page: val });
                    return;
                }
                skip = that.skip();
                return skip !== undefined ? math.round((skip || 0) / (that.take() || 1)) + 1 : undefined;
            },
            pageSize: function (val) {
                var that = this;
                if (val !== undefined) {
                    that._query({
                        pageSize: val,
                        page: 1
                    });
                    return;
                }
                return that.take();
            },
            sort: function (val) {
                var that = this;
                if (val !== undefined) {
                    that._query({ sort: val });
                    return;
                }
                return that._sort;
            },
            filter: function (val) {
                var that = this;
                if (val === undefined) {
                    return that._filter;
                }
                that.trigger('reset');
                that._query({
                    filter: val,
                    page: 1
                });
            },
            group: function (val) {
                var that = this;
                if (val !== undefined) {
                    that._query({ group: val });
                    return;
                }
                return that._group;
            },
            total: function () {
                return parseInt(this._total || 0, 10);
            },
            aggregate: function (val) {
                var that = this;
                if (val !== undefined) {
                    that._query({ aggregate: val });
                    return;
                }
                return that._aggregate;
            },
            aggregates: function () {
                var result = this._aggregateResult;
                if (isEmptyObject(result)) {
                    result = this._emptyAggregates(this.aggregate());
                }
                return result;
            },
            _emptyAggregates: function (aggregates) {
                var result = {};
                if (!isEmptyObject(aggregates)) {
                    var aggregate = {};
                    if (!isArray(aggregates)) {
                        aggregates = [aggregates];
                    }
                    for (var idx = 0; idx < aggregates.length; idx++) {
                        aggregate[aggregates[idx].aggregate] = 0;
                        result[aggregates[idx].field] = aggregate;
                    }
                }
                return result;
            },
            _wrapInEmptyGroup: function (model) {
                var groups = this.group(), parent, group, idx, length;
                for (idx = groups.length - 1, length = 0; idx >= length; idx--) {
                    group = groups[idx];
                    parent = {
                        value: model.get(group.field),
                        field: group.field,
                        items: parent ? [parent] : [model],
                        hasSubgroups: !!parent,
                        aggregates: this._emptyAggregates(group.aggregates)
                    };
                }
                return parent;
            },
            totalPages: function () {
                var that = this, pageSize = that.pageSize() || that.total();
                return math.ceil((that.total() || 0) / pageSize);
            },
            inRange: function (skip, take) {
                var that = this, end = math.min(skip + take, that.total());
                if (!that.options.serverPaging && that._data.length > 0) {
                    return true;
                }
                return that._findRange(skip, end).length > 0;
            },
            lastRange: function () {
                var ranges = this._ranges;
                return ranges[ranges.length - 1] || {
                    start: 0,
                    end: 0,
                    data: []
                };
            },
            firstItemUid: function () {
                var ranges = this._ranges;
                return ranges.length && ranges[0].data.length && ranges[0].data[0].uid;
            },
            enableRequestsInProgress: function () {
                this._skipRequestsInProgress = false;
            },
            _timeStamp: function () {
                return new Date().getTime();
            },
            range: function (skip, take) {
                this._currentRequestTimeStamp = this._timeStamp();
                this._skipRequestsInProgress = true;
                skip = math.min(skip || 0, this.total());
                var that = this, pageSkip = math.max(math.floor(skip / take), 0) * take, size = math.min(pageSkip + take, that.total()), data;
                data = that._findRange(skip, math.min(skip + take, that.total()));
                if (data.length) {
                    that._pending = undefined;
                    that._skip = skip > that.skip() ? math.min(size, (that.totalPages() - 1) * that.take()) : pageSkip;
                    that._currentRangeStart = skip;
                    that._take = take;
                    var paging = that.options.serverPaging;
                    var sorting = that.options.serverSorting;
                    var filtering = that.options.serverFiltering;
                    var aggregates = that.options.serverAggregates;
                    try {
                        that.options.serverPaging = true;
                        if (!that._isServerGrouped() && !(that.group() && that.group().length)) {
                            that.options.serverSorting = true;
                        }
                        that.options.serverFiltering = true;
                        that.options.serverPaging = true;
                        that.options.serverAggregates = true;
                        if (paging) {
                            that._detachObservableParents();
                            that._data = data = that._observe(data);
                        }
                        that._process(data);
                    } finally {
                        that.options.serverPaging = paging;
                        that.options.serverSorting = sorting;
                        that.options.serverFiltering = filtering;
                        that.options.serverAggregates = aggregates;
                    }
                    return;
                }
                if (take !== undefined) {
                    if (!that._rangeExists(pageSkip, size)) {
                        that.prefetch(pageSkip, take, function () {
                            if (skip > pageSkip && size < that.total() && !that._rangeExists(size, math.min(size + take, that.total()))) {
                                that.prefetch(size, take, function () {
                                    that.range(skip, take);
                                });
                            } else {
                                that.range(skip, take);
                            }
                        });
                    } else if (pageSkip < skip) {
                        that.prefetch(size, take, function () {
                            that.range(skip, take);
                        });
                    }
                }
            },
            _findRange: function (start, end) {
                var that = this, ranges = that._ranges, range, data = [], skipIdx, takeIdx, startIndex, endIndex, rangeData, rangeEnd, processed, options = that.options, remote = options.serverSorting || options.serverPaging || options.serverFiltering || options.serverGrouping || options.serverAggregates, flatData, count, length;
                for (skipIdx = 0, length = ranges.length; skipIdx < length; skipIdx++) {
                    range = ranges[skipIdx];
                    if (start >= range.start && start <= range.end) {
                        count = 0;
                        for (takeIdx = skipIdx; takeIdx < length; takeIdx++) {
                            range = ranges[takeIdx];
                            flatData = that._flatData(range.data, true);
                            if (flatData.length && start + count >= range.start) {
                                rangeData = range.data;
                                rangeEnd = range.end;
                                if (!remote) {
                                    var sort = normalizeGroup(that.group() || []).concat(normalizeSort(that.sort() || []));
                                    processed = that._queryProcess(range.data, {
                                        sort: sort,
                                        filter: that.filter()
                                    });
                                    flatData = rangeData = processed.data;
                                    if (processed.total !== undefined) {
                                        rangeEnd = processed.total;
                                    }
                                }
                                startIndex = 0;
                                if (start + count > range.start) {
                                    startIndex = start + count - range.start;
                                }
                                endIndex = flatData.length;
                                if (rangeEnd > end) {
                                    endIndex = endIndex - (rangeEnd - end);
                                }
                                count += endIndex - startIndex;
                                data = that._mergeGroups(data, rangeData, startIndex, endIndex);
                                if (end <= range.end && count == end - start) {
                                    return data;
                                }
                            }
                        }
                        break;
                    }
                }
                return [];
            },
            _mergeGroups: function (data, range, skip, take) {
                if (this._isServerGrouped()) {
                    var temp = range.toJSON(), prevGroup;
                    if (data.length) {
                        prevGroup = data[data.length - 1];
                    }
                    mergeGroups(prevGroup, temp, skip, take);
                    return data.concat(temp);
                }
                return data.concat(range.slice(skip, take));
            },
            skip: function () {
                var that = this;
                if (that._skip === undefined) {
                    return that._page !== undefined ? (that._page - 1) * (that.take() || 1) : undefined;
                }
                return that._skip;
            },
            currentRangeStart: function () {
                return this._currentRangeStart || 0;
            },
            take: function () {
                return this._take || this._pageSize;
            },
            _prefetchSuccessHandler: function (skip, size, callback, force) {
                var that = this;
                var timestamp = that._timeStamp();
                return function (data) {
                    var found = false, range = {
                            start: skip,
                            end: size,
                            data: [],
                            timestamp: that._timeStamp()
                        }, idx, length, temp;
                    that._dequeueRequest();
                    that.trigger(REQUESTEND, {
                        response: data,
                        type: 'read'
                    });
                    data = that.reader.parse(data);
                    temp = that._readData(data);
                    if (temp.length) {
                        for (idx = 0, length = that._ranges.length; idx < length; idx++) {
                            if (that._ranges[idx].start === skip) {
                                found = true;
                                range = that._ranges[idx];
                                break;
                            }
                        }
                        if (!found) {
                            that._ranges.push(range);
                        }
                    }
                    range.data = that._observe(temp);
                    range.end = range.start + that._flatData(range.data, true).length;
                    that._ranges.sort(function (x, y) {
                        return x.start - y.start;
                    });
                    that._total = that.reader.total(data);
                    if (force || (timestamp >= that._currentRequestTimeStamp || !that._skipRequestsInProgress)) {
                        if (callback && temp.length) {
                            callback();
                        } else {
                            that.trigger(CHANGE, {});
                        }
                    }
                };
            },
            prefetch: function (skip, take, callback) {
                var that = this, size = math.min(skip + take, that.total()), options = {
                        take: take,
                        skip: skip,
                        page: skip / take + 1,
                        pageSize: take,
                        sort: that._sort,
                        filter: that._filter,
                        group: that._group,
                        aggregate: that._aggregate
                    };
                if (!that._rangeExists(skip, size)) {
                    clearTimeout(that._timeout);
                    that._timeout = setTimeout(function () {
                        that._queueRequest(options, function () {
                            if (!that.trigger(REQUESTSTART, { type: 'read' })) {
                                that.transport.read({
                                    data: that._params(options),
                                    success: that._prefetchSuccessHandler(skip, size, callback),
                                    error: function () {
                                        var args = slice.call(arguments);
                                        that.error.apply(that, args);
                                    }
                                });
                            } else {
                                that._dequeueRequest();
                            }
                        });
                    }, 100);
                } else if (callback) {
                    callback();
                }
            },
            _multiplePrefetch: function (skip, take, callback) {
                var that = this, size = math.min(skip + take, that.total()), options = {
                        take: take,
                        skip: skip,
                        page: skip / take + 1,
                        pageSize: take,
                        sort: that._sort,
                        filter: that._filter,
                        group: that._group,
                        aggregate: that._aggregate
                    };
                if (!that._rangeExists(skip, size)) {
                    if (!that.trigger(REQUESTSTART, { type: 'read' })) {
                        that.transport.read({
                            data: that._params(options),
                            success: that._prefetchSuccessHandler(skip, size, callback, true)
                        });
                    }
                } else if (callback) {
                    callback();
                }
            },
            _rangeExists: function (start, end) {
                var that = this, ranges = that._ranges, idx, length;
                for (idx = 0, length = ranges.length; idx < length; idx++) {
                    if (ranges[idx].start <= start && ranges[idx].end >= end) {
                        return true;
                    }
                }
                return false;
            },
            _removeModelFromRanges: function (model) {
                var result, found, range;
                for (var idx = 0, length = this._ranges.length; idx < length; idx++) {
                    range = this._ranges[idx];
                    this._eachItem(range.data, function (items) {
                        result = removeModel(items, model);
                        if (result) {
                            found = true;
                        }
                    });
                    if (found) {
                        break;
                    }
                }
            },
            _updateRangesLength: function () {
                var startOffset = 0, range, rangeLength;
                for (var idx = 0, length = this._ranges.length; idx < length; idx++) {
                    range = this._ranges[idx];
                    range.start = range.start - startOffset;
                    rangeLength = this._flatData(range.data, true).length;
                    startOffset = range.end - rangeLength;
                    range.end = range.start + rangeLength;
                }
            }
        });
        var Transport = {};
        Transport.create = function (options, data, dataSource) {
            var transport, transportOptions = options.transport ? $.extend({}, options.transport) : null;
            if (transportOptions) {
                transportOptions.read = typeof transportOptions.read === STRING ? { url: transportOptions.read } : transportOptions.read;
                if (options.type === 'jsdo') {
                    transportOptions.dataSource = dataSource;
                }
                if (options.type) {
                    kendo.data.transports = kendo.data.transports || {};
                    kendo.data.schemas = kendo.data.schemas || {};
                    if (!kendo.data.transports[options.type]) {
                        kendo.logToConsole('Unknown DataSource transport type \'' + options.type + '\'.\nVerify that registration scripts for this type are included after Kendo UI on the page.', 'warn');
                    } else if (!isPlainObject(kendo.data.transports[options.type])) {
                        transport = new kendo.data.transports[options.type](extend(transportOptions, { data: data }));
                    } else {
                        transportOptions = extend(true, {}, kendo.data.transports[options.type], transportOptions);
                    }
                    options.schema = extend(true, {}, kendo.data.schemas[options.type], options.schema);
                }
                if (!transport) {
                    transport = isFunction(transportOptions.read) ? transportOptions : new RemoteTransport(transportOptions);
                }
            } else {
                transport = new LocalTransport({ data: options.data || [] });
            }
            return transport;
        };
        DataSource.create = function (options) {
            if (isArray(options) || options instanceof ObservableArray) {
                options = { data: options };
            }
            var dataSource = options || {}, data = dataSource.data, fields = dataSource.fields, table = dataSource.table, select = dataSource.select, idx, length, model = {}, field;
            if (!data && fields && !dataSource.transport) {
                if (table) {
                    data = inferTable(table, fields);
                } else if (select) {
                    data = inferSelect(select, fields);
                    if (dataSource.group === undefined && data[0] && data[0].optgroup !== undefined) {
                        dataSource.group = 'optgroup';
                    }
                }
            }
            if (kendo.data.Model && fields && (!dataSource.schema || !dataSource.schema.model)) {
                for (idx = 0, length = fields.length; idx < length; idx++) {
                    field = fields[idx];
                    if (field.type) {
                        model[field.field] = field;
                    }
                }
                if (!isEmptyObject(model)) {
                    dataSource.schema = extend(true, dataSource.schema, { model: { fields: model } });
                }
            }
            dataSource.data = data;
            select = null;
            dataSource.select = null;
            table = null;
            dataSource.table = null;
            return dataSource instanceof DataSource ? dataSource : new DataSource(dataSource);
        };
        function inferSelect(select, fields) {
            select = $(select)[0];
            var options = select.options;
            var firstField = fields[0];
            var secondField = fields[1];
            var data = [];
            var idx, length;
            var optgroup;
            var option;
            var record;
            var value;
            for (idx = 0, length = options.length; idx < length; idx++) {
                record = {};
                option = options[idx];
                optgroup = option.parentNode;
                if (optgroup === select) {
                    optgroup = null;
                }
                if (option.disabled || optgroup && optgroup.disabled) {
                    continue;
                }
                if (optgroup) {
                    record.optgroup = optgroup.label;
                }
                record[firstField.field] = option.text;
                value = option.attributes.value;
                if (value && value.specified) {
                    value = option.value;
                } else {
                    value = option.text;
                }
                record[secondField.field] = value;
                data.push(record);
            }
            return data;
        }
        function inferTable(table, fields) {
            var tbody = $(table)[0].tBodies[0], rows = tbody ? tbody.rows : [], idx, length, fieldIndex, fieldCount = fields.length, data = [], cells, record, cell, empty;
            for (idx = 0, length = rows.length; idx < length; idx++) {
                record = {};
                empty = true;
                cells = rows[idx].cells;
                for (fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++) {
                    cell = cells[fieldIndex];
                    if (cell.nodeName.toLowerCase() !== 'th') {
                        empty = false;
                        record[fields[fieldIndex].field] = cell.innerHTML;
                    }
                }
                if (!empty) {
                    data.push(record);
                }
            }
            return data;
        }
        var Node = Model.define({
            idField: 'id',
            init: function (value) {
                var that = this, hasChildren = that.hasChildren || value && value.hasChildren, childrenField = 'items', childrenOptions = {};
                kendo.data.Model.fn.init.call(that, value);
                if (typeof that.children === STRING) {
                    childrenField = that.children;
                }
                childrenOptions = {
                    schema: {
                        data: childrenField,
                        model: {
                            hasChildren: hasChildren,
                            id: that.idField,
                            fields: that.fields
                        }
                    }
                };
                if (typeof that.children !== STRING) {
                    extend(childrenOptions, that.children);
                }
                childrenOptions.data = value;
                if (!hasChildren) {
                    hasChildren = childrenOptions.schema.data;
                }
                if (typeof hasChildren === STRING) {
                    hasChildren = kendo.getter(hasChildren);
                }
                if (isFunction(hasChildren)) {
                    var hasChildrenObject = hasChildren.call(that, that);
                    if (hasChildrenObject && hasChildrenObject.length === 0) {
                        that.hasChildren = false;
                    } else {
                        that.hasChildren = !!hasChildrenObject;
                    }
                }
                that._childrenOptions = childrenOptions;
                if (that.hasChildren) {
                    that._initChildren();
                }
                that._loaded = !!(value && value._loaded);
            },
            _initChildren: function () {
                var that = this;
                var children, transport, parameterMap;
                if (!(that.children instanceof HierarchicalDataSource)) {
                    children = that.children = new HierarchicalDataSource(that._childrenOptions);
                    transport = children.transport;
                    parameterMap = transport.parameterMap;
                    transport.parameterMap = function (data, type) {
                        data[that.idField || 'id'] = that.id;
                        if (parameterMap) {
                            data = parameterMap(data, type);
                        }
                        return data;
                    };
                    children.parent = function () {
                        return that;
                    };
                    children.bind(CHANGE, function (e) {
                        e.node = e.node || that;
                        that.trigger(CHANGE, e);
                    });
                    children.bind(ERROR, function (e) {
                        var collection = that.parent();
                        if (collection) {
                            e.node = e.node || that;
                            collection.trigger(ERROR, e);
                        }
                    });
                    that._updateChildrenField();
                }
            },
            append: function (model) {
                this._initChildren();
                this.loaded(true);
                this.children.add(model);
            },
            hasChildren: false,
            level: function () {
                var parentNode = this.parentNode(), level = 0;
                while (parentNode && parentNode.parentNode) {
                    level++;
                    parentNode = parentNode.parentNode ? parentNode.parentNode() : null;
                }
                return level;
            },
            _updateChildrenField: function () {
                var fieldName = this._childrenOptions.schema.data;
                this[fieldName || 'items'] = this.children.data();
            },
            _childrenLoaded: function () {
                this._loaded = true;
                this._updateChildrenField();
            },
            load: function () {
                var options = {};
                var method = '_query';
                var children, promise;
                if (this.hasChildren) {
                    this._initChildren();
                    children = this.children;
                    options[this.idField || 'id'] = this.id;
                    if (!this._loaded) {
                        children._data = undefined;
                        method = 'read';
                    }
                    children.one(CHANGE, proxy(this._childrenLoaded, this));
                    if (this._matchFilter) {
                        options.filter = {
                            field: '_matchFilter',
                            operator: 'eq',
                            value: true
                        };
                    }
                    promise = children[method](options);
                } else {
                    this.loaded(true);
                }
                return promise || $.Deferred().resolve().promise();
            },
            parentNode: function () {
                var array = this.parent();
                return array.parent();
            },
            loaded: function (value) {
                if (value !== undefined) {
                    this._loaded = value;
                } else {
                    return this._loaded;
                }
            },
            shouldSerialize: function (field) {
                return Model.fn.shouldSerialize.call(this, field) && field !== 'children' && field !== '_loaded' && field !== 'hasChildren' && field !== '_childrenOptions';
            }
        });
        function dataMethod(name) {
            return function () {
                var data = this._data, result = DataSource.fn[name].apply(this, slice.call(arguments));
                if (this._data != data) {
                    this._attachBubbleHandlers();
                }
                return result;
            };
        }
        var HierarchicalDataSource = DataSource.extend({
            init: function (options) {
                var node = Node.define({ children: options });
                if (options.filter && !options.serverFiltering) {
                    this._hierarchicalFilter = options.filter;
                    options.filter = null;
                }
                DataSource.fn.init.call(this, extend(true, {}, {
                    schema: {
                        modelBase: node,
                        model: node
                    }
                }, options));
                this._attachBubbleHandlers();
            },
            _attachBubbleHandlers: function () {
                var that = this;
                that._data.bind(ERROR, function (e) {
                    that.trigger(ERROR, e);
                });
            },
            read: function (data) {
                var result = DataSource.fn.read.call(this, data);
                if (this._hierarchicalFilter) {
                    this.filter(this._hierarchicalFilter);
                }
                return result;
            },
            remove: function (node) {
                var parentNode = node.parentNode(), dataSource = this, result;
                if (parentNode && parentNode._initChildren) {
                    dataSource = parentNode.children;
                }
                result = DataSource.fn.remove.call(dataSource, node);
                if (parentNode && !dataSource.data().length) {
                    parentNode.hasChildren = false;
                }
                return result;
            },
            success: dataMethod('success'),
            data: dataMethod('data'),
            insert: function (index, model) {
                var parentNode = this.parent();
                if (parentNode && parentNode._initChildren) {
                    parentNode.hasChildren = true;
                    parentNode._initChildren();
                }
                return DataSource.fn.insert.call(this, index, model);
            },
            filter: function (val) {
                if (val === undefined) {
                    return this._filter;
                }
                if (!this.options.serverFiltering) {
                    this._markHierarchicalQuery(val);
                    val = {
                        logic: 'or',
                        filters: [
                            val,
                            {
                                field: '_matchFilter',
                                operator: 'equals',
                                value: true
                            }
                        ]
                    };
                }
                this.trigger('reset');
                this._query({
                    filter: val,
                    page: 1
                });
            },
            _markHierarchicalQuery: function (expressions) {
                var compiled;
                var predicate;
                var fields;
                var operators;
                var filter;
                expressions = normalizeFilter(expressions);
                if (!expressions || expressions.filters.length === 0) {
                    return this;
                }
                compiled = Query.filterExpr(expressions);
                fields = compiled.fields;
                operators = compiled.operators;
                predicate = filter = new Function('d, __f, __o', 'return ' + compiled.expression);
                if (fields.length || operators.length) {
                    filter = function (d) {
                        return predicate(d, fields, operators);
                    };
                }
                this._updateHierarchicalFilter(filter);
            },
            _updateHierarchicalFilter: function (filter) {
                var current;
                var data = this._data;
                var result = false;
                for (var idx = 0; idx < data.length; idx++) {
                    current = data[idx];
                    if (current.hasChildren) {
                        current._matchFilter = current.children._updateHierarchicalFilter(filter);
                        if (!current._matchFilter) {
                            current._matchFilter = filter(current);
                        }
                    } else {
                        current._matchFilter = filter(current);
                    }
                    if (current._matchFilter) {
                        result = true;
                    }
                }
                return result;
            },
            _find: function (method, value) {
                var idx, length, node, children;
                var data = this._data;
                if (!data) {
                    return;
                }
                node = DataSource.fn[method].call(this, value);
                if (node) {
                    return node;
                }
                data = this._flatData(this._data);
                for (idx = 0, length = data.length; idx < length; idx++) {
                    children = data[idx].children;
                    if (!(children instanceof HierarchicalDataSource)) {
                        continue;
                    }
                    node = children[method](value);
                    if (node) {
                        return node;
                    }
                }
            },
            get: function (id) {
                return this._find('get', id);
            },
            getByUid: function (uid) {
                return this._find('getByUid', uid);
            }
        });
        function inferList(list, fields) {
            var items = $(list).children(), idx, length, data = [], record, textField = fields[0].field, urlField = fields[1] && fields[1].field, spriteCssClassField = fields[2] && fields[2].field, imageUrlField = fields[3] && fields[3].field, item, id, textChild, className, children;
            function elements(collection, tagName) {
                return collection.filter(tagName).add(collection.find(tagName));
            }
            for (idx = 0, length = items.length; idx < length; idx++) {
                record = { _loaded: true };
                item = items.eq(idx);
                textChild = item[0].firstChild;
                children = item.children();
                list = children.filter('ul');
                children = children.filter(':not(ul)');
                id = item.attr('data-id');
                if (id) {
                    record.id = id;
                }
                if (textChild) {
                    record[textField] = textChild.nodeType == 3 ? textChild.nodeValue : children.text();
                }
                if (urlField) {
                    record[urlField] = elements(children, 'a').attr('href');
                }
                if (imageUrlField) {
                    record[imageUrlField] = elements(children, 'img').attr('src');
                }
                if (spriteCssClassField) {
                    className = elements(children, '.k-sprite').prop('className');
                    record[spriteCssClassField] = className && $.trim(className.replace('k-sprite', ''));
                }
                if (list.length) {
                    record.items = inferList(list.eq(0), fields);
                }
                if (item.attr('data-hasChildren') == 'true') {
                    record.hasChildren = true;
                }
                data.push(record);
            }
            return data;
        }
        HierarchicalDataSource.create = function (options) {
            options = options && options.push ? { data: options } : options;
            var dataSource = options || {}, data = dataSource.data, fields = dataSource.fields, list = dataSource.list;
            if (data && data._dataSource) {
                return data._dataSource;
            }
            if (!data && fields && !dataSource.transport) {
                if (list) {
                    data = inferList(list, fields);
                }
            }
            dataSource.data = data;
            return dataSource instanceof HierarchicalDataSource ? dataSource : new HierarchicalDataSource(dataSource);
        };
        var Buffer = kendo.Observable.extend({
            init: function (dataSource, viewSize, disablePrefetch) {
                kendo.Observable.fn.init.call(this);
                this._prefetching = false;
                this.dataSource = dataSource;
                this.prefetch = !disablePrefetch;
                var buffer = this;
                dataSource.bind('change', function () {
                    buffer._change();
                });
                dataSource.bind('reset', function () {
                    buffer._reset();
                });
                this._syncWithDataSource();
                this.setViewSize(viewSize);
            },
            setViewSize: function (viewSize) {
                this.viewSize = viewSize;
                this._recalculate();
            },
            at: function (index) {
                var pageSize = this.pageSize, itemPresent = true;
                if (index >= this.total()) {
                    this.trigger('endreached', { index: index });
                    return null;
                }
                if (!this.useRanges) {
                    return this.dataSource.view()[index];
                }
                if (this.useRanges) {
                    if (index < this.dataOffset || index >= this.skip + pageSize) {
                        itemPresent = this.range(Math.floor(index / pageSize) * pageSize);
                    }
                    if (index === this.prefetchThreshold) {
                        this._prefetch();
                    }
                    if (index === this.midPageThreshold) {
                        this.range(this.nextMidRange, true);
                    } else if (index === this.nextPageThreshold) {
                        this.range(this.nextFullRange);
                    } else if (index === this.pullBackThreshold) {
                        if (this.offset === this.skip) {
                            this.range(this.previousMidRange);
                        } else {
                            this.range(this.previousFullRange);
                        }
                    }
                    if (itemPresent) {
                        return this.dataSource.at(index - this.dataOffset);
                    } else {
                        this.trigger('endreached', { index: index });
                        return null;
                    }
                }
            },
            indexOf: function (item) {
                return this.dataSource.data().indexOf(item) + this.dataOffset;
            },
            total: function () {
                return parseInt(this.dataSource.total(), 10);
            },
            next: function () {
                var buffer = this, pageSize = buffer.pageSize, offset = buffer.skip - buffer.viewSize + pageSize, pageSkip = math.max(math.floor(offset / pageSize), 0) * pageSize;
                this.offset = offset;
                this.dataSource.prefetch(pageSkip, pageSize, function () {
                    buffer._goToRange(offset, true);
                });
            },
            range: function (offset, nextRange) {
                if (this.offset === offset) {
                    return true;
                }
                var buffer = this, pageSize = this.pageSize, pageSkip = math.max(math.floor(offset / pageSize), 0) * pageSize, dataSource = this.dataSource;
                if (nextRange) {
                    pageSkip += pageSize;
                }
                if (dataSource.inRange(offset, pageSize)) {
                    this.offset = offset;
                    this._recalculate();
                    this._goToRange(offset);
                    return true;
                } else if (this.prefetch) {
                    dataSource.prefetch(pageSkip, pageSize, function () {
                        buffer.offset = offset;
                        buffer._recalculate();
                        buffer._goToRange(offset, true);
                    });
                    return false;
                }
                return true;
            },
            syncDataSource: function () {
                var offset = this.offset;
                this.offset = null;
                this.range(offset);
            },
            destroy: function () {
                this.unbind();
            },
            _prefetch: function () {
                var buffer = this, pageSize = this.pageSize, prefetchOffset = this.skip + pageSize, dataSource = this.dataSource;
                if (!dataSource.inRange(prefetchOffset, pageSize) && !this._prefetching && this.prefetch) {
                    this._prefetching = true;
                    this.trigger('prefetching', {
                        skip: prefetchOffset,
                        take: pageSize
                    });
                    dataSource.prefetch(prefetchOffset, pageSize, function () {
                        buffer._prefetching = false;
                        buffer.trigger('prefetched', {
                            skip: prefetchOffset,
                            take: pageSize
                        });
                    });
                }
            },
            _goToRange: function (offset, expanding) {
                if (this.offset !== offset) {
                    return;
                }
                this.dataOffset = offset;
                this._expanding = expanding;
                this.dataSource.range(offset, this.pageSize);
                this.dataSource.enableRequestsInProgress();
            },
            _reset: function () {
                this._syncPending = true;
            },
            _change: function () {
                var dataSource = this.dataSource;
                this.length = this.useRanges ? dataSource.lastRange().end : dataSource.view().length;
                if (this._syncPending) {
                    this._syncWithDataSource();
                    this._recalculate();
                    this._syncPending = false;
                    this.trigger('reset', { offset: this.offset });
                }
                this.trigger('resize');
                if (this._expanding) {
                    this.trigger('expand');
                }
                delete this._expanding;
            },
            _syncWithDataSource: function () {
                var dataSource = this.dataSource;
                this._firstItemUid = dataSource.firstItemUid();
                this.dataOffset = this.offset = dataSource.skip() || 0;
                this.pageSize = dataSource.pageSize();
                this.useRanges = dataSource.options.serverPaging;
            },
            _recalculate: function () {
                var pageSize = this.pageSize, offset = this.offset, viewSize = this.viewSize, skip = Math.ceil(offset / pageSize) * pageSize;
                this.skip = skip;
                this.midPageThreshold = skip + pageSize - 1;
                this.nextPageThreshold = skip + viewSize - 1;
                this.prefetchThreshold = skip + Math.floor(pageSize / 3 * 2);
                this.pullBackThreshold = this.offset - 1;
                this.nextMidRange = skip + pageSize - viewSize;
                this.nextFullRange = skip;
                this.previousMidRange = offset - viewSize;
                this.previousFullRange = skip - pageSize;
            }
        });
        var BatchBuffer = kendo.Observable.extend({
            init: function (dataSource, batchSize) {
                var batchBuffer = this;
                kendo.Observable.fn.init.call(batchBuffer);
                this.dataSource = dataSource;
                this.batchSize = batchSize;
                this._total = 0;
                this.buffer = new Buffer(dataSource, batchSize * 3);
                this.buffer.bind({
                    'endreached': function (e) {
                        batchBuffer.trigger('endreached', { index: e.index });
                    },
                    'prefetching': function (e) {
                        batchBuffer.trigger('prefetching', {
                            skip: e.skip,
                            take: e.take
                        });
                    },
                    'prefetched': function (e) {
                        batchBuffer.trigger('prefetched', {
                            skip: e.skip,
                            take: e.take
                        });
                    },
                    'reset': function () {
                        batchBuffer._total = 0;
                        batchBuffer.trigger('reset');
                    },
                    'resize': function () {
                        batchBuffer._total = Math.ceil(this.length / batchBuffer.batchSize);
                        batchBuffer.trigger('resize', {
                            total: batchBuffer.total(),
                            offset: this.offset
                        });
                    }
                });
            },
            syncDataSource: function () {
                this.buffer.syncDataSource();
            },
            at: function (index) {
                var buffer = this.buffer, skip = index * this.batchSize, take = this.batchSize, view = [], item;
                if (buffer.offset > skip) {
                    buffer.at(buffer.offset - 1);
                }
                for (var i = 0; i < take; i++) {
                    item = buffer.at(skip + i);
                    if (item === null) {
                        break;
                    }
                    view.push(item);
                }
                return view;
            },
            total: function () {
                return this._total;
            },
            destroy: function () {
                this.buffer.destroy();
                this.unbind();
            }
        });
        extend(true, kendo.data, {
            readers: { json: DataReader },
            Query: Query,
            DataSource: DataSource,
            HierarchicalDataSource: HierarchicalDataSource,
            Node: Node,
            ObservableObject: ObservableObject,
            ObservableArray: ObservableArray,
            LazyObservableArray: LazyObservableArray,
            LocalTransport: LocalTransport,
            RemoteTransport: RemoteTransport,
            Cache: Cache,
            DataReader: DataReader,
            Model: Model,
            Buffer: Buffer,
            BatchBuffer: BatchBuffer
        });
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.tabstrip', ['kendo.data'], f);
}(function () {
    var __meta__ = {
        id: 'tabstrip',
        name: 'TabStrip',
        category: 'web',
        description: 'The TabStrip widget displays a collection of tabs with associated tab content.',
        depends: ['data']
    };
    (function ($, undefined) {
        var kendo = window.kendo, ui = kendo.ui, keys = kendo.keys, map = $.map, each = $.each, trim = $.trim, extend = $.extend, isFunction = kendo.isFunction, template = kendo.template, outerWidth = kendo._outerWidth, outerHeight = kendo._outerHeight, Widget = ui.Widget, excludedNodesRegExp = /^(a|div)$/i, NS = '.kendoTabStrip', IMG = 'img', HREF = 'href', PREV = 'prev', SHOW = 'show', LINK = 'k-link', LAST = 'k-last', CLICK = 'click', ERROR = 'error', EMPTY = ':empty', IMAGE = 'k-image', FIRST = 'k-first', SELECT = 'select', ACTIVATE = 'activate', CONTENT = 'k-content', CONTENTURL = 'contentUrl', MOUSEENTER = 'mouseenter', MOUSELEAVE = 'mouseleave', CONTENTLOAD = 'contentLoad', DISABLEDSTATE = 'k-state-disabled', DEFAULTSTATE = 'k-state-default', ACTIVESTATE = 'k-state-active', FOCUSEDSTATE = 'k-state-focused', HOVERSTATE = 'k-state-hover', TABONTOP = 'k-tab-on-top', NAVIGATABLEITEMS = '.k-item:not(.' + DISABLEDSTATE + ')', HOVERABLEITEMS = '.k-tabstrip-items > ' + NAVIGATABLEITEMS + ':not(.' + ACTIVESTATE + ')', DEFAULTDISTANCE = 200, templates = {
                content: template('<div class=\'k-content\'#= contentAttributes(data) # role=\'tabpanel\'>#= content(item) #</div>'),
                itemWrapper: template('<#= tag(item) # class=\'k-link\'#= contentUrl(item) ##= textAttributes(item) #>' + '#= image(item) ##= sprite(item) ##= text(item) #' + '</#= tag(item) #>'),
                item: template('<li class=\'#= wrapperCssClass(group, item) #\' role=\'tab\' #=item.active ? "aria-selected=\'true\'" : \'\'#>' + '#= itemWrapper(data) #' + '</li>'),
                image: template('<img class=\'k-image\' alt=\'\' src=\'#= imageUrl #\' />'),
                sprite: template('<span class=\'k-sprite #= spriteCssClass #\'></span>'),
                empty: template('')
            }, rendering = {
                wrapperCssClass: function (group, item) {
                    var result = 'k-item', index = item.index;
                    if (item.enabled === false) {
                        result += ' k-state-disabled';
                    } else {
                        result += ' k-state-default';
                    }
                    if (index === 0) {
                        result += ' k-first';
                    }
                    if (index == group.length - 1) {
                        result += ' k-last';
                    }
                    return result;
                },
                textAttributes: function (item) {
                    return item.url ? ' href=\'' + item.url + '\'' : '';
                },
                text: function (item) {
                    return item.encoded === false ? item.text : kendo.htmlEncode(item.text);
                },
                tag: function (item) {
                    return item.url ? 'a' : 'span';
                },
                contentAttributes: function (content) {
                    return content.active !== true ? ' style=\'display:none\' aria-hidden=\'true\' aria-expanded=\'false\'' : '';
                },
                content: function (item) {
                    return item.content ? item.content : item.contentUrl ? '' : '&nbsp;';
                },
                contentUrl: function (item) {
                    return item.contentUrl ? kendo.attr('content-url') + '="' + item.contentUrl + '"' : '';
                }
            };
        function updateTabClasses(tabs) {
            tabs.children(IMG).addClass(IMAGE);
            tabs.children('a').addClass(LINK).children(IMG).addClass(IMAGE);
            tabs.filter(':not([disabled]):not([class*=k-state-disabled])').addClass(DEFAULTSTATE);
            tabs.filter('li[disabled]').addClass(DISABLEDSTATE).removeAttr('disabled');
            tabs.filter(':not([class*=k-state])').children('a').filter(':focus').parent().addClass(ACTIVESTATE + ' ' + TABONTOP);
            tabs.attr('role', 'tab');
            tabs.filter('.' + ACTIVESTATE).attr('aria-selected', true);
            tabs.each(function () {
                var item = $(this);
                if (!item.children('.' + LINK).length) {
                    item.contents().filter(function () {
                        return !this.nodeName.match(excludedNodesRegExp) && !(this.nodeType == 3 && !trim(this.nodeValue));
                    }).wrapAll('<span UNSELECTABLE=\'on\' class=\'' + LINK + '\'/>');
                }
            });
        }
        function updateFirstLast(tabGroup) {
            var tabs = tabGroup.children('.k-item');
            tabs.filter('.k-first:not(:first-child)').removeClass(FIRST);
            tabs.filter('.k-last:not(:last-child)').removeClass(LAST);
            tabs.filter(':first-child').addClass(FIRST);
            tabs.filter(':last-child').addClass(LAST);
        }
        function scrollButtonHtml(buttonClass, iconClass) {
            return '<span class=\'k-button k-button-icon k-bare k-tabstrip-' + buttonClass + '\' unselectable=\'on\'><span class=\'k-icon ' + iconClass + '\'></span></span>';
        }
        var TabStrip = Widget.extend({
            init: function (element, options) {
                var that = this, value;
                Widget.fn.init.call(that, element, options);
                that._animations(that.options);
                options = that.options;
                that._contentUrls = options.contentUrls || [];
                that._wrapper();
                that._isRtl = kendo.support.isRtl(that.wrapper);
                that._tabindex();
                that._updateClasses();
                that._dataSource();
                if (options.dataSource) {
                    that.dataSource.fetch();
                }
                that._tabPosition();
                that._scrollable();
                if (that._contentUrls.length) {
                    that.wrapper.find('.k-tabstrip-items > .k-item').each(function (index, item) {
                        var url = that._contentUrls[index];
                        if (typeof url === 'string') {
                            $(item).find('>.' + LINK).data(CONTENTURL, url);
                        }
                    });
                } else {
                    that._contentUrls.length = that.tabGroup.find('li.k-item').length;
                }
                that.wrapper.on(MOUSEENTER + NS + ' ' + MOUSELEAVE + NS, HOVERABLEITEMS, that._toggleHover).on('focus' + NS, $.proxy(that._active, that)).on('blur' + NS, function () {
                    that._current(null);
                });
                that._keyDownProxy = $.proxy(that._keydown, that);
                if (options.navigatable) {
                    that.wrapper.on('keydown' + NS, that._keyDownProxy);
                }
                if (that.options.value) {
                    value = that.options.value;
                }
                that.wrapper.children('.k-tabstrip-items').on(CLICK + NS, '.k-state-disabled .k-link', false).on(CLICK + NS, ' > ' + NAVIGATABLEITEMS, function (e) {
                    var wr = that.wrapper[0];
                    if (wr !== document.activeElement) {
                        var msie = kendo.support.browser.msie;
                        if (msie) {
                            try {
                                wr.setActive();
                            } catch (j) {
                                wr.focus();
                            }
                        } else {
                            wr.focus();
                        }
                    }
                    if (that._click($(e.currentTarget))) {
                        e.preventDefault();
                    }
                });
                var selectedItems = that.tabGroup.children('li.' + ACTIVESTATE), content = that.contentHolder(selectedItems.index());
                if (selectedItems[0] && content.length > 0 && content[0].childNodes.length === 0) {
                    that.activateTab(selectedItems.eq(0));
                }
                that.element.attr('role', 'tablist');
                if (that.element[0].id) {
                    that._ariaId = that.element[0].id + '_ts_active';
                }
                that.value(value);
                kendo.notify(that);
            },
            _active: function () {
                var item = this.tabGroup.children().filter('.' + ACTIVESTATE);
                item = item[0] ? item : this._endItem('first');
                if (item[0]) {
                    this._current(item);
                }
            },
            _endItem: function (action) {
                return this.tabGroup.children(NAVIGATABLEITEMS)[action]();
            },
            _item: function (item, action) {
                var endItem;
                if (action === PREV) {
                    endItem = 'last';
                } else {
                    endItem = 'first';
                }
                if (!item) {
                    return this._endItem(endItem);
                }
                item = item[action]();
                if (!item[0]) {
                    item = this._endItem(endItem);
                }
                if (item.hasClass(DISABLEDSTATE)) {
                    item = this._item(item, action);
                }
                return item;
            },
            _current: function (candidate) {
                var that = this, focused = that._focused, id = that._ariaId;
                if (candidate === undefined) {
                    return focused;
                }
                if (focused) {
                    if (focused[0].id === id) {
                        focused.removeAttr('id');
                    }
                    focused.removeClass(FOCUSEDSTATE);
                }
                if (candidate) {
                    if (!candidate.hasClass(ACTIVESTATE)) {
                        candidate.addClass(FOCUSEDSTATE);
                    }
                    that.element.removeAttr('aria-activedescendant');
                    id = candidate[0].id || id;
                    if (id) {
                        candidate.attr('id', id);
                        that.element.attr('aria-activedescendant', id);
                    }
                }
                that._focused = candidate;
            },
            _keydown: function (e) {
                var that = this, key = e.keyCode, current = that._current(), rtl = that._isRtl, isHorizontal = /top|bottom/.test(that.options.tabPosition), action;
                if (e.target != e.currentTarget) {
                    return;
                }
                if (key == keys.DOWN || key == keys.RIGHT) {
                    action = rtl && isHorizontal ? PREV : 'next';
                } else if (key == keys.UP || key == keys.LEFT) {
                    action = rtl && isHorizontal ? 'next' : PREV;
                } else if (key == keys.ENTER || key == keys.SPACEBAR) {
                    that._click(current);
                    e.preventDefault();
                } else if (key == keys.HOME) {
                    that._click(that._endItem('first'));
                    e.preventDefault();
                    return;
                } else if (key == keys.END) {
                    that._click(that._endItem('last'));
                    e.preventDefault();
                    return;
                }
                if (action) {
                    that._click(that._item(current, action));
                    e.preventDefault();
                }
            },
            _dataSource: function () {
                var that = this;
                if (that.dataSource && that._refreshHandler) {
                    that.dataSource.unbind('change', that._refreshHandler);
                } else {
                    that._refreshHandler = $.proxy(that.refresh, that);
                }
                that.dataSource = kendo.data.DataSource.create(that.options.dataSource).bind('change', that._refreshHandler);
            },
            setDataSource: function (dataSource) {
                var that = this;
                that.options.dataSource = dataSource;
                that._dataSource();
                that.dataSource.fetch();
            },
            _animations: function (options) {
                if (options && 'animation' in options && !options.animation) {
                    options.animation = {
                        open: { effects: {} },
                        close: { effects: {} }
                    };
                }
            },
            refresh: function (e) {
                var that = this, options = that.options, text = kendo.getter(options.dataTextField), content = kendo.getter(options.dataContentField), contentUrl = kendo.getter(options.dataContentUrlField), image = kendo.getter(options.dataImageUrlField), url = kendo.getter(options.dataUrlField), sprite = kendo.getter(options.dataSpriteCssClass), idx, tabs = [], tab, action, view = that.dataSource.view(), length;
                e = e || {};
                action = e.action;
                if (action) {
                    view = e.items;
                }
                for (idx = 0, length = view.length; idx < length; idx++) {
                    tab = { text: text(view[idx]) };
                    if (options.dataContentField) {
                        tab.content = content(view[idx]);
                    }
                    if (options.dataContentUrlField) {
                        tab.contentUrl = contentUrl(view[idx]);
                    }
                    if (options.dataUrlField) {
                        tab.url = url(view[idx]);
                    }
                    if (options.dataImageUrlField) {
                        tab.imageUrl = image(view[idx]);
                    }
                    if (options.dataSpriteCssClass) {
                        tab.spriteCssClass = sprite(view[idx]);
                    }
                    tabs[idx] = tab;
                }
                if (e.action == 'add') {
                    if (e.index < that.tabGroup.children().length) {
                        that.insertBefore(tabs, that.tabGroup.children().eq(e.index));
                    } else {
                        that.append(tabs);
                    }
                } else if (e.action == 'remove') {
                    for (idx = 0; idx < view.length; idx++) {
                        that.remove(e.index);
                    }
                } else if (e.action == 'itemchange') {
                    idx = that.dataSource.view().indexOf(view[0]);
                    if (e.field === options.dataTextField) {
                        that.tabGroup.children().eq(idx).find('.k-link').text(view[0].get(e.field));
                    }
                    if (e.field === options.dataUrlField) {
                        that._contentUrls[idx] = view[0].get(e.field);
                    }
                } else {
                    that.trigger('dataBinding');
                    that.remove('li');
                    that._contentUrls = [];
                    that.append(tabs);
                    that.trigger('dataBound');
                }
            },
            value: function (value) {
                var that = this;
                if (value !== undefined) {
                    if (value != that.value()) {
                        that.tabGroup.children().each(function () {
                            if ($.trim($(this).text()) == value) {
                                that.select(this);
                            }
                        });
                    }
                } else {
                    return that.select().text();
                }
            },
            items: function () {
                return this.tabGroup[0].children;
            },
            setOptions: function (options) {
                var that = this, animation = that.options.animation;
                that._animations(options);
                if (options.contentUrls) {
                    that._contentUrls = options.contentUrls;
                }
                options.animation = extend(true, animation, options.animation);
                if (options.navigatable) {
                    that.wrapper.on('keydown' + NS, that._keyDownProxy);
                } else {
                    that.wrapper.off('keydown' + NS, that._keyDownProxy);
                }
                Widget.fn.setOptions.call(that, options);
            },
            events: [
                SELECT,
                ACTIVATE,
                SHOW,
                ERROR,
                CONTENTLOAD,
                'change',
                'dataBinding',
                'dataBound'
            ],
            options: {
                name: 'TabStrip',
                dataTextField: '',
                dataContentField: '',
                dataImageUrlField: '',
                dataUrlField: '',
                dataSpriteCssClass: '',
                dataContentUrlField: '',
                tabPosition: 'top',
                animation: {
                    open: {
                        effects: 'expand:vertical fadeIn',
                        duration: 200
                    },
                    close: { duration: 200 }
                },
                collapsible: false,
                navigatable: true,
                contentUrls: false,
                scrollable: { distance: DEFAULTDISTANCE }
            },
            destroy: function () {
                var that = this, scrollWrap = that.scrollWrap;
                Widget.fn.destroy.call(that);
                if (that._refreshHandler) {
                    that.dataSource.unbind('change', that._refreshHandler);
                }
                that.wrapper.off(NS);
                that.wrapper.children('.k-tabstrip-items').off(NS);
                if (that._scrollableModeActive) {
                    that._scrollPrevButton.off().remove();
                    that._scrollNextButton.off().remove();
                }
                kendo.destroy(that.wrapper);
                scrollWrap.children('.k-tabstrip').unwrap();
            },
            select: function (element) {
                var that = this;
                if (arguments.length === 0) {
                    return that.tabGroup.children('li.' + ACTIVESTATE);
                }
                if (!isNaN(element)) {
                    element = that.tabGroup.children().get(element);
                }
                element = that.tabGroup.find(element);
                $(element).each(function (index, item) {
                    item = $(item);
                    if (!item.hasClass(ACTIVESTATE) && !that.trigger(SELECT, {
                            item: item[0],
                            contentElement: that.contentHolder(item.index())[0]
                        })) {
                        that.activateTab(item);
                    }
                });
                return that;
            },
            enable: function (element, state) {
                this._toggleDisabled(element, state !== false);
                return this;
            },
            disable: function (element) {
                this._toggleDisabled(element, false);
                return this;
            },
            reload: function (element) {
                element = this.tabGroup.find(element);
                var that = this;
                var contentUrls = that._contentUrls;
                element.each(function () {
                    var item = $(this), contentUrl = item.find('.' + LINK).data(CONTENTURL) || contentUrls[item.index()], content = that.contentHolder(item.index());
                    if (contentUrl) {
                        that.ajaxRequest(item, content, null, contentUrl);
                    }
                });
                return that;
            },
            append: function (tab) {
                var that = this, inserted = that._create(tab);
                each(inserted.tabs, function (idx) {
                    var contents = inserted.contents[idx];
                    that.tabGroup.append(this);
                    if (that.options.tabPosition == 'bottom') {
                        that.tabGroup.before(contents);
                    } else if (that._scrollableModeActive) {
                        that._scrollPrevButton.before(contents);
                    } else {
                        that.wrapper.append(contents);
                    }
                    that.angular('compile', function () {
                        return { elements: [contents] };
                    });
                });
                updateFirstLast(that.tabGroup);
                that._updateContentElements();
                that.resize(true);
                return that;
            },
            _appendUrlItem: function (url) {
                this._contentUrls.push(url);
            },
            _moveUrlItem: function (from, to) {
                this._contentUrls.splice(to, 0, this._contentUrls.splice(from, 1)[0]);
            },
            _removeUrlItem: function (index) {
                this._contentUrls.splice(index, 1);
            },
            insertBefore: function (tab, referenceTab) {
                if ($(tab).is($(referenceTab))) {
                    referenceTab = this.tabGroup.find(referenceTab).next();
                } else {
                    referenceTab = this.tabGroup.find(referenceTab);
                }
                var that = this, inserted = that._create(tab), referenceContent = that.element.find('#' + referenceTab.attr('aria-controls'));
                each(inserted.tabs, function (idx) {
                    var contents = inserted.contents[idx];
                    var fromIndex = inserted.newTabsCreated ? that._contentUrls.length - (inserted.tabs.length - idx) : $(contents).index() - 1;
                    referenceTab.before(this);
                    referenceContent.before(contents);
                    that._moveUrlItem(fromIndex, $(this).index());
                    that.angular('compile', function () {
                        return { elements: [contents] };
                    });
                });
                updateFirstLast(that.tabGroup);
                that._updateContentElements(inserted.newTabsCreated);
                that.resize(true);
                return that;
            },
            insertAfter: function (tab, referenceTab) {
                if ($(tab).is($(referenceTab))) {
                    referenceTab = this.tabGroup.find(referenceTab).prev();
                } else {
                    referenceTab = this.tabGroup.find(referenceTab);
                }
                var that = this, inserted = that._create(tab), referenceContent = that.element.find('#' + referenceTab.attr('aria-controls'));
                each(inserted.tabs, function (idx) {
                    var contents = inserted.contents[idx];
                    var fromIndex = inserted.newTabsCreated ? that._contentUrls.length - (inserted.tabs.length - idx) : $(contents).index() - 1;
                    referenceTab.after(this);
                    referenceContent.after(contents);
                    that._moveUrlItem(fromIndex, $(this).index());
                    that.angular('compile', function () {
                        return { elements: [contents] };
                    });
                });
                updateFirstLast(that.tabGroup);
                that._updateContentElements(inserted.newTabsCreated);
                that.resize(true);
                return that;
            },
            remove: function (elements) {
                var that = this;
                var type = typeof elements;
                var contents;
                if (type === 'string') {
                    elements = that.tabGroup.find(elements);
                } else if (type === 'number') {
                    elements = that.tabGroup.children().eq(elements);
                }
                contents = elements.map(function () {
                    var idx = $(this).index();
                    var content = that.contentElement(idx);
                    kendo.destroy(content);
                    that._removeUrlItem(idx);
                    return content;
                });
                elements.remove();
                contents.empty();
                contents.remove();
                that._updateContentElements();
                that.resize(true);
                return that;
            },
            _create: function (tab) {
                var that = this, tabs, contents, content, newTabsCreated = false;
                tab = tab instanceof kendo.data.ObservableArray ? tab.toJSON() : tab;
                if ($.isPlainObject(tab) || $.isArray(tab)) {
                    tab = $.isArray(tab) ? tab : [tab];
                    newTabsCreated = true;
                    tabs = map(tab, function (value, idx) {
                        that._appendUrlItem(tab[idx].contentUrl || null);
                        return $(TabStrip.renderItem({
                            group: that.tabGroup,
                            item: extend(value, { index: idx })
                        }));
                    });
                    contents = map(tab, function (value, idx) {
                        if (typeof value.content == 'string' || value.contentUrl) {
                            return $(TabStrip.renderContent({ item: extend(value, { index: idx }) }));
                        }
                    });
                } else {
                    if (typeof tab == 'string' && tab[0] != '<') {
                        tabs = that.element.find(tab);
                    } else {
                        tabs = $(tab);
                    }
                    contents = $();
                    tabs.each(function () {
                        if (/k-tabstrip-items/.test(this.parentNode.className)) {
                            var element = that.element.find('#' + this.getAttribute('aria-controls'));
                            content = element;
                        } else {
                            content = $('<div class=\'' + CONTENT + '\'/>');
                        }
                        contents = contents.add(content);
                    });
                    updateTabClasses(tabs);
                }
                return {
                    tabs: tabs,
                    contents: contents,
                    newTabsCreated: newTabsCreated
                };
            },
            _toggleDisabled: function (element, enable) {
                element = this.tabGroup.find(element);
                element.each(function () {
                    $(this).toggleClass(DEFAULTSTATE, enable).toggleClass(DISABLEDSTATE, !enable);
                });
            },
            _updateClasses: function () {
                var that = this, tabs, activeItem, activeTab;
                that.wrapper.addClass('k-widget k-header k-tabstrip');
                that.tabGroup = that.wrapper.children('ul').addClass('k-tabstrip-items k-reset');
                if (!that.tabGroup[0]) {
                    that.tabGroup = $('<ul class=\'k-tabstrip-items k-reset\'/>').appendTo(that.wrapper);
                }
                tabs = that.tabGroup.find('li').addClass('k-item');
                if (tabs.length) {
                    activeItem = tabs.filter('.' + ACTIVESTATE).index();
                    activeTab = activeItem >= 0 ? activeItem : undefined;
                    that.tabGroup.contents().filter(function () {
                        return this.nodeType == 3 && !trim(this.nodeValue);
                    }).remove();
                }
                if (activeItem >= 0) {
                    tabs.eq(activeItem).addClass(TABONTOP);
                }
                that.contentElements = that.wrapper.children('div');
                that.contentElements.addClass(CONTENT).eq(activeTab).addClass(ACTIVESTATE).css({ display: 'block' });
                if (tabs.length) {
                    updateTabClasses(tabs);
                    updateFirstLast(that.tabGroup);
                    that._updateContentElements(true);
                }
            },
            _elementId: function (element, idx) {
                var elementId = element.attr('id');
                var wrapperId = this.element.attr('id');
                if (!elementId || elementId.indexOf(wrapperId + '-') > -1) {
                    var tabStripID = (wrapperId || kendo.guid()) + '-';
                    return tabStripID + (idx + 1);
                }
                return elementId;
            },
            _updateContentElements: function (isInitialUpdate) {
                var that = this, contentUrls = that._contentUrls, items = that.tabGroup.children('.k-item'), contentElements = that.wrapper.children('div'), _elementId = that._elementId.bind(that);
                if (contentElements.length && items.length > contentElements.length) {
                    contentElements.each(function (idx) {
                        var id = _elementId($(this), idx);
                        var item = items.filter('[aria-controls=' + (this.id || 0) + ']')[0];
                        if (!item && isInitialUpdate) {
                            item = items[idx];
                        }
                        if (item) {
                            item.setAttribute('aria-controls', id);
                        }
                        this.setAttribute('id', id);
                    });
                } else {
                    items.each(function (idx) {
                        var currentContent = contentElements.eq(idx);
                        var id = _elementId(currentContent, idx);
                        this.setAttribute('aria-controls', id);
                        if (!currentContent.length && contentUrls[idx]) {
                            $('<div class=\'' + CONTENT + '\'/>').appendTo(that.wrapper).attr('id', id);
                        } else {
                            currentContent.attr('id', id);
                            if (!$(this).children('.k-loading')[0] && !contentUrls[idx]) {
                                $('<span class=\'k-loading k-complete\'/>').prependTo(this);
                            }
                        }
                        currentContent.attr('role', 'tabpanel');
                        currentContent.filter(':not(.' + ACTIVESTATE + ')').attr('aria-hidden', true).attr('aria-expanded', false);
                        currentContent.filter('.' + ACTIVESTATE).attr('aria-expanded', true);
                    });
                }
                that.contentElements = that.contentAnimators = that.wrapper.children('div');
                that.tabsHeight = outerHeight(that.tabGroup) + parseInt(that.wrapper.css('border-top-width'), 10) + parseInt(that.wrapper.css('border-bottom-width'), 10);
                if (kendo.kineticScrollNeeded && kendo.mobile.ui.Scroller) {
                    kendo.touchScroller(that.contentElements);
                    that.contentElements = that.contentElements.children('.km-scroll-container');
                }
            },
            _wrapper: function () {
                var that = this;
                if (that.element.is('ul')) {
                    that.wrapper = that.element.wrapAll('<div />').parent();
                } else {
                    that.wrapper = that.element;
                }
                that.scrollWrap = that.wrapper.parent('.k-tabstrip-wrapper');
                if (!that.scrollWrap[0]) {
                    that.scrollWrap = that.wrapper.wrapAll('<div class=\'k-tabstrip-wrapper\' />').parent();
                }
            },
            _tabPosition: function () {
                var that = this, tabPosition = that.options.tabPosition;
                that.wrapper.addClass('k-floatwrap k-tabstrip-' + tabPosition);
                if (tabPosition == 'bottom') {
                    that.tabGroup.appendTo(that.wrapper);
                }
                that.resize(true);
            },
            _setContentElementsDimensions: function () {
                var that = this, tabPosition = that.options.tabPosition;
                if (tabPosition == 'left' || tabPosition == 'right') {
                    var contentDivs = that.wrapper.children('.k-content'), activeDiv = contentDivs.filter(':visible'), marginStyleProperty = 'margin-' + tabPosition, tabGroup = that.tabGroup, margin = outerWidth(tabGroup);
                    var minHeight = Math.ceil(tabGroup.height()) - parseInt(activeDiv.css('padding-top'), 10) - parseInt(activeDiv.css('padding-bottom'), 10) - parseInt(activeDiv.css('border-top-width'), 10) - parseInt(activeDiv.css('border-bottom-width'), 10);
                    setTimeout(function () {
                        contentDivs.css(marginStyleProperty, margin).css('min-height', minHeight);
                    });
                }
            },
            _resize: function () {
                this._setContentElementsDimensions();
                this._scrollable();
            },
            _sizeScrollWrap: function (element) {
                if (element.is(':visible')) {
                    var tabPosition = this.options.tabPosition;
                    var h = Math.floor(outerHeight(element, true)) + (tabPosition === 'left' || tabPosition === 'right' ? 2 : this.tabsHeight);
                    this.scrollWrap.css('height', h).css('height');
                }
            },
            _toggleHover: function (e) {
                $(e.currentTarget).toggleClass(HOVERSTATE, e.type == MOUSEENTER);
            },
            _click: function (item) {
                var that = this, link = item.find('.' + LINK), href = link.attr(HREF), collapse = that.options.collapsible, index = item.index(), contentHolder = that.contentHolder(index), prevent, isAnchor;
                if (item.closest('.k-widget')[0] != that.wrapper[0]) {
                    return;
                }
                if (item.is('.' + DISABLEDSTATE + (!collapse ? ',.' + ACTIVESTATE : ''))) {
                    return true;
                }
                isAnchor = link.data(CONTENTURL) || that._contentUrls[index] || href && (href.charAt(href.length - 1) == '#' || href.indexOf('#' + that.element[0].id + '-') != -1);
                prevent = !href || isAnchor;
                if (that.tabGroup.children('[data-animating]').length) {
                    return prevent;
                }
                if (that.trigger(SELECT, {
                        item: item[0],
                        contentElement: contentHolder[0]
                    })) {
                    return true;
                }
                if (prevent === false) {
                    return;
                }
                if (collapse && item.is('.' + ACTIVESTATE)) {
                    that.deactivateTab(item);
                    return true;
                }
                if (that.activateTab(item)) {
                    prevent = true;
                }
                return prevent;
            },
            _scrollable: function () {
                var that = this, options = that.options, wrapperOffsetWidth, tabGroupScrollWidth, scrollPrevButton, scrollNextButton;
                if (that._scrollableAllowed()) {
                    that.wrapper.addClass('k-tabstrip-scrollable');
                    wrapperOffsetWidth = that.wrapper[0].offsetWidth;
                    tabGroupScrollWidth = that.tabGroup[0].scrollWidth;
                    if (tabGroupScrollWidth > wrapperOffsetWidth && !that._scrollableModeActive) {
                        that._nowScrollingTabs = false;
                        that._isRtl = kendo.support.isRtl(that.element);
                        var mouseDown = kendo.support.mobileOS ? 'touchstart' : 'mousedown';
                        var mouseUp = kendo.support.mobileOS ? 'touchend' : 'mouseup';
                        that.wrapper.append(scrollButtonHtml('prev', 'k-i-arrow-60-left') + scrollButtonHtml('next', 'k-i-arrow-60-right'));
                        scrollPrevButton = that._scrollPrevButton = that.wrapper.children('.k-tabstrip-prev');
                        scrollNextButton = that._scrollNextButton = that.wrapper.children('.k-tabstrip-next');
                        that.tabGroup.css({
                            //z
                            marginLeft: 20,//outerWidth(scrollPrevButton) + 9,
                            marginRight: 26//outerWidth(scrollNextButton) + 12
                        });
                        scrollPrevButton.on(mouseDown + NS, function () {
                            that._nowScrollingTabs = true;
                            that._scrollTabsByDelta(options.scrollable.distance * (that._isRtl ? 1 : -1));
                        });
                        scrollNextButton.on(mouseDown + NS, function () {
                            that._nowScrollingTabs = true;
                            that._scrollTabsByDelta(options.scrollable.distance * (that._isRtl ? -1 : 1));
                        });
                        scrollPrevButton.add(scrollNextButton).on(mouseUp + NS, function () {
                            that._nowScrollingTabs = false;
                        });
                        that._scrollableModeActive = true;
                        that._toggleScrollButtons();
                    } else if (that._scrollableModeActive && tabGroupScrollWidth <= wrapperOffsetWidth) {
                        that._scrollableModeActive = false;
                        that.wrapper.removeClass('k-tabstrip-scrollable');
                        that._scrollPrevButton.off().remove();
                        that._scrollNextButton.off().remove();
                        that.tabGroup.css({
                            marginLeft: '',
                            marginRight: ''
                        });
                    } else if (!that._scrollableModeActive) {
                        that.wrapper.removeClass('k-tabstrip-scrollable');
                    } else {
                        that._toggleScrollButtons();
                    }
                }
            },
            _scrollableAllowed: function () {
                var options = this.options;
                if (options.scrollable && !options.scrollable.distance) {
                    options.scrollable = { distance: DEFAULTDISTANCE };
                }
                return options.scrollable && !isNaN(options.scrollable.distance) && (options.tabPosition == 'top' || options.tabPosition == 'bottom');
            },
            _scrollTabsToItem: function (item) {
                var that = this, tabGroup = that.tabGroup, currentScrollOffset = tabGroup.scrollLeft(), itemWidth = outerWidth(item), itemOffset = that._isRtl ? item.position().left : item.position().left - tabGroup.children().first().position().left, tabGroupWidth = tabGroup[0].offsetWidth, tabGroupPadding = Math.ceil(parseFloat(tabGroup.css('padding-left'))), itemPosition;
                if (that._isRtl) {
                    if (itemOffset < 0) {
                        itemPosition = currentScrollOffset + itemOffset - (tabGroupWidth - currentScrollOffset) - tabGroupPadding;
                    } else if (itemOffset + itemWidth > tabGroupWidth) {
                        itemPosition = currentScrollOffset + itemOffset - itemWidth + tabGroupPadding * 2;
                    }
                } else {
                    if (currentScrollOffset + tabGroupWidth < itemOffset + itemWidth) {
                        itemPosition = itemOffset + itemWidth - tabGroupWidth + tabGroupPadding * 2;
                    } else if (currentScrollOffset > itemOffset) {
                        itemPosition = itemOffset - tabGroupPadding;
                    }
                }
                tabGroup.finish().animate({ 'scrollLeft': itemPosition }, 'fast', 'linear', function () {
                    that._toggleScrollButtons();
                });
            },
            _scrollTabsByDelta: function (delta) {
                var that = this;
                var tabGroup = that.tabGroup;
                var scrLeft = tabGroup.scrollLeft();
                tabGroup.finish().animate({ 'scrollLeft': scrLeft + delta }, 'fast', 'linear', function () {
                    if (that._nowScrollingTabs && !jQuery.fx.off) {
                        that._scrollTabsByDelta(delta);
                    } else {
                        that._toggleScrollButtons();
                    }
                });
            },
            _toggleScrollButtons: function () {
                var that = this, ul = that.tabGroup, scrollLeft = ul.scrollLeft();
                //z
                // that._scrollPrevButton.toggle(that._isRtl ? scrollLeft < ul[0].scrollWidth - ul[0].offsetWidth - 1 : scrollLeft !== 0);
                // that._scrollNextButton.toggle(that._isRtl ? scrollLeft !== 0 : scrollLeft < ul[0].scrollWidth - ul[0].offsetWidth - 1);
            },
            deactivateTab: function (item) {
                var that = this, animationSettings = that.options.animation, animation = animationSettings.open, close = extend({}, animationSettings.close), hasCloseAnimation = close && 'effects' in close;
                item = that.tabGroup.find(item);
                close = extend(hasCloseAnimation ? close : extend({ reverse: true }, animation), { hide: true });
                if (kendo.size(animation.effects)) {
                    item.kendoAddClass(DEFAULTSTATE, { duration: animation.duration });
                    item.kendoRemoveClass(ACTIVESTATE, { duration: animation.duration });
                } else {
                    item.addClass(DEFAULTSTATE);
                    item.removeClass(ACTIVESTATE);
                }
                item.removeAttr('aria-selected');
                that.contentAnimators.filter('.' + ACTIVESTATE).kendoStop(true, true).kendoAnimate(close).removeClass(ACTIVESTATE).attr('aria-hidden', true);
            },
            activateTab: function (item) {
                if (this.tabGroup.children('[data-animating]').length) {
                    return;
                }
                item = this.tabGroup.find(item);
                var that = this, animationSettings = that.options.animation, animation = animationSettings.open, close = extend({}, animationSettings.close), hasCloseAnimation = close && 'effects' in close, neighbours = item.parent().children(), oldTab = neighbours.filter('.' + ACTIVESTATE), itemIndex = neighbours.index(item);
                close = extend(hasCloseAnimation ? close : extend({ reverse: true }, animation), { hide: true });
                if (kendo.size(animation.effects)) {
                    oldTab.kendoRemoveClass(ACTIVESTATE, { duration: close.duration });
                    item.kendoRemoveClass(HOVERSTATE, { duration: close.duration });
                } else {
                    oldTab.removeClass(ACTIVESTATE);
                    item.removeClass(HOVERSTATE);
                }
                var contentAnimators = that.contentAnimators;
                if (that.inRequest) {
                    that.xhr.abort();
                    that.inRequest = false;
                }
                if (contentAnimators.length === 0) {
                    that.tabGroup.find('.' + TABONTOP).removeClass(TABONTOP);
                    item.addClass(TABONTOP).css('z-index');
                    item.addClass(ACTIVESTATE);
                    that._current(item);
                    that.trigger('change');
                    if (that._scrollableModeActive) {
                        that._scrollTabsToItem(item);
                    }
                    return false;
                }
                var visibleContents = contentAnimators.filter('.' + ACTIVESTATE), contentHolder = that.contentHolder(itemIndex), contentElement = contentHolder.closest('.k-content');
                that.tabsHeight = outerHeight(that.tabGroup) + parseInt(that.wrapper.css('border-top-width'), 10) + parseInt(that.wrapper.css('border-bottom-width'), 10);
                that._sizeScrollWrap(visibleContents);
                if (contentHolder.length === 0) {
                    visibleContents.removeClass(ACTIVESTATE).attr('aria-hidden', true).kendoStop(true, true).kendoAnimate(close);
                    return false;
                }
                item.attr('data-animating', true);
                var isAjaxContent = (item.children('.' + LINK).data(CONTENTURL) || that._contentUrls[itemIndex] || false) && contentHolder.is(EMPTY), showContentElement = function () {
                        that.tabGroup.find('.' + TABONTOP).removeClass(TABONTOP);
                        item.addClass(TABONTOP).css('z-index');
                        if (kendo.size(animation.effects)) {
                            oldTab.kendoAddClass(DEFAULTSTATE, { duration: animation.duration });
                            item.kendoAddClass(ACTIVESTATE, { duration: animation.duration });
                        } else {
                            oldTab.addClass(DEFAULTSTATE);
                            item.addClass(ACTIVESTATE);
                        }
                        oldTab.removeAttr('aria-selected');
                        item.attr('aria-selected', true);
                        that._current(item);
                        that._sizeScrollWrap(contentElement);
                        contentElement.addClass(ACTIVESTATE).removeAttr('aria-hidden').kendoStop(true, true).attr('aria-expanded', true).kendoAnimate(extend({
                            init: function () {
                                that.trigger(SHOW, {
                                    item: item[0],
                                    contentElement: contentHolder[0]
                                });
                                kendo.resize(contentHolder);
                            }
                        }, animation, {
                            complete: function () {
                                item.removeAttr('data-animating');
                                that.trigger(ACTIVATE, {
                                    item: item[0],
                                    contentElement: contentHolder[0]
                                });
                                kendo.resize(contentHolder);
                                that.scrollWrap.css('height', '').css('height');
                                if (kendo.support.browser.msie || kendo.support.browser.edge) {
                                    contentHolder.finish().animate({ opacity: 0.9 }, 'fast', 'linear', function () {
                                        contentHolder.finish().animate({ opacity: 1 }, 'fast', 'linear');
                                    });
                                }
                            }
                        }));
                    }, showContent = function () {
                        if (!isAjaxContent) {
                            showContentElement();
                            that.trigger('change');
                        } else {
                            item.removeAttr('data-animating');
                            that.ajaxRequest(item, contentHolder, function () {
                                item.attr('data-animating', true);
                                showContentElement();
                                that.trigger('change');
                            });
                        }
                        if (that._scrollableModeActive) {
                            that._scrollTabsToItem(item);
                        }
                    };
                visibleContents.removeClass(ACTIVESTATE);
                visibleContents.attr('aria-hidden', true);
                visibleContents.attr('aria-expanded', false);
                if (visibleContents.length) {
                    visibleContents.kendoStop(true, true).kendoAnimate(extend({ complete: showContent }, close));
                } else {
                    showContent();
                }
                return true;
            },
            contentElement: function (itemIndex) {
                if (isNaN(itemIndex - 0)) {
                    return undefined;
                }
                var contentElements = this.contentElements && this.contentElements[0] && !kendo.kineticScrollNeeded ? this.contentElements : this.contentAnimators;
                var id = $(this.tabGroup.children()[itemIndex]).attr('aria-controls');
                if (contentElements) {
                    for (var i = 0, len = contentElements.length; i < len; i++) {
                        if (contentElements.eq(i).closest('.k-content')[0].id == id) {
                            return contentElements[i];
                        }
                    }
                }
                return undefined;
            },
            contentHolder: function (itemIndex) {
                var contentElement = $(this.contentElement(itemIndex)), scrollContainer = contentElement.children('.km-scroll-container');
                return kendo.support.touch && scrollContainer[0] ? scrollContainer : contentElement;
            },
            ajaxRequest: function (element, content, complete, url) {
                element = this.tabGroup.find(element);
                var that = this, xhr = $.ajaxSettings.xhr, link = element.find('.' + LINK), data = {}, halfWidth = element.width() / 2, fakeProgress = false, statusIcon = element.find('.k-loading').removeClass('k-complete');
                if (!statusIcon[0]) {
                    statusIcon = $('<span class=\'k-loading\'/>').prependTo(element);
                }
                var endState = halfWidth * 2 - statusIcon.width();
                var oldProgressAnimation = function () {
                    statusIcon.animate({ marginLeft: (parseInt(statusIcon.css('marginLeft'), 10) || 0) < halfWidth ? endState : 0 }, 500, oldProgressAnimation);
                };
                if (kendo.support.browser.msie && kendo.support.browser.version < 10) {
                    setTimeout(oldProgressAnimation, 40);
                }
                url = url || link.data(CONTENTURL) || that._contentUrls[element.index()] || link.attr(HREF);
                that.inRequest = true;
                var ajaxOptions = {
                    type: 'GET',
                    cache: false,
                    url: url,
                    dataType: 'html',
                    data: data,
                    xhr: function () {
                        var current = this, request = xhr(), event = current.progressUpload ? 'progressUpload' : current.progress ? 'progress' : false;
                        if (request) {
                            $.each([
                                request,
                                request.upload
                            ], function () {
                                if (this.addEventListener) {
                                    this.addEventListener('progress', function (evt) {
                                        if (event) {
                                            current[event](evt);
                                        }
                                    }, false);
                                }
                            });
                        }
                        current.noProgress = !(window.XMLHttpRequest && 'upload' in new XMLHttpRequest());
                        return request;
                    },
                    progress: function (evt) {
                        if (evt.lengthComputable) {
                            var percent = parseInt(evt.loaded / evt.total * 100, 10) + '%';
                            statusIcon.stop(true).addClass('k-progress').css({
                                'width': percent,
                                'marginLeft': 0
                            });
                        }
                    },
                    error: function (xhr, status) {
                        if (that.trigger('error', {
                                xhr: xhr,
                                status: status
                            })) {
                            this.complete();
                        }
                    },
                    stopProgress: function () {
                        clearInterval(fakeProgress);
                        statusIcon.stop(true).addClass('k-progress')[0].style.cssText = '';
                    },
                    complete: function (xhr) {
                        that.inRequest = false;
                        if (this.noProgress) {
                            setTimeout(this.stopProgress, 500);
                        } else {
                            this.stopProgress();
                        }
                        if (xhr.statusText == 'abort') {
                            statusIcon.remove();
                        }
                    },
                    success: function (data) {
                        statusIcon.addClass('k-complete');
                        try {
                            var current = this, loaded = 10;
                            if (current.noProgress) {
                                statusIcon.width(loaded + '%');
                                fakeProgress = setInterval(function () {
                                    current.progress({
                                        lengthComputable: true,
                                        loaded: Math.min(loaded, 100),
                                        total: 100
                                    });
                                    loaded += 10;
                                }, 40);
                            }
                            that.angular('cleanup', function () {
                                return { elements: content.get() };
                            });
                            kendo.destroy(content);
                            content.html(data);
                        } catch (e) {
                            var console = window.console;
                            if (console && console.error) {
                                console.error(e.name + ': ' + e.message + ' in ' + url);
                            }
                            this.error(this.xhr, 'error');
                        }
                        if (complete) {
                            complete.call(that, content);
                        }
                        that.angular('compile', function () {
                            return { elements: content.get() };
                        });
                        that.trigger(CONTENTLOAD, {
                            item: element[0],
                            contentElement: content[0]
                        });
                    }
                };
                if (typeof url === 'object') {
                    ajaxOptions = $.extend(true, {}, ajaxOptions, url);
                    if (isFunction(ajaxOptions.url)) {
                        ajaxOptions.url = ajaxOptions.url();
                    }
                }
                that.xhr = $.ajax(ajaxOptions);
            }
        });
        extend(TabStrip, {
            renderItem: function (options) {
                options = extend({
                    tabStrip: {},
                    group: {}
                }, options);
                var empty = templates.empty, item = options.item;
                return templates.item(extend(options, {
                    image: item.imageUrl ? templates.image : empty,
                    sprite: item.spriteCssClass ? templates.sprite : empty,
                    itemWrapper: templates.itemWrapper
                }, rendering));
            },
            renderContent: function (options) {
                return templates.content(extend(options, rendering));
            }
        });
        kendo.ui.plugin(TabStrip);
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.notification', [
        'kendo.core',
        'kendo.popup'
    ], f);
}(function () {
    var __meta__ = {
        id: 'notification',
        name: 'Notification',
        category: 'web',
        description: 'The Notification widget displays user alerts.',
        depends: [
            'core',
            'popup'
        ],
        features: [{
                id: 'notification-fx',
                name: 'Animation',
                description: 'Support for animation',
                depends: ['fx']
            }]
    };
    (function ($, undefined) {
        var kendo = window.kendo, Widget = kendo.ui.Widget, proxy = $.proxy, extend = $.extend, setTimeout = window.setTimeout, CLICK = 'click', SHOW = 'show', HIDE = 'hide', KNOTIFICATION = 'k-notification', KICLOSE = '.k-notification-wrap .k-i-close', KHIDING = 'k-hiding', INFO = 'info', SUCCESS = 'success', WARNING = 'warning', ERROR = 'error', TOP = 'top', LEFT = 'left', BOTTOM = 'bottom', RIGHT = 'right', UP = 'up', NS = '.kendoNotification', WRAPPER = '<div class="k-widget k-notification"></div>', TEMPLATE = '<div class="k-notification-wrap">' + '<span class="k-icon k-i-#=typeIcon#" title="#=typeIcon#"></span>' + '#=content#' + '<span class="k-icon k-i-close" title="Hide"></span>' + '</div>', SAFE_TEMPLATE = TEMPLATE.replace('#=content#', '#:content#');
        var Notification = Widget.extend({
            init: function (element, options) {
                var that = this;
                Widget.fn.init.call(that, element, options);
                options = that.options;
                if (!options.appendTo || !$(options.appendTo).is(element)) {
                    that.element.hide();
                }
                that._compileTemplates(options.templates);
                that._guid = '_' + kendo.guid();
                that._isRtl = kendo.support.isRtl(element);
                that._compileStacking(options.stacking, options.position.top, options.position.left);
                kendo.notify(that);
            },
            events: [
                SHOW,
                HIDE
            ],
            options: {
                name: 'Notification',
                position: {
                    pinned: true,
                    top: null,
                    left: null,
                    bottom: 20,
                    right: 20
                },
                stacking: 'default',
                hideOnClick: true,
                button: false,
                allowHideAfter: 0,
                autoHideAfter: 5000,
                appendTo: null,
                width: null,
                height: null,
                templates: [],
                animation: {
                    open: {
                        effects: 'fade:in',
                        duration: 300
                    },
                    close: {
                        effects: 'fade:out',
                        duration: 600,
                        hide: true
                    }
                }
            },
            _compileTemplates: function (templates) {
                var that = this;
                var kendoTemplate = kendo.template;
                that._compiled = {};
                $.each(templates, function (key, value) {
                    that._compiled[value.type] = kendoTemplate(value.template || $('#' + value.templateId).html());
                });
                that._defaultCompiled = kendoTemplate(TEMPLATE);
                that._safeCompiled = kendoTemplate(SAFE_TEMPLATE);
            },
            _getCompiled: function (type, safe) {
                var defaultCompiled = safe ? this._safeCompiled : this._defaultCompiled;
                return type ? this._compiled[type] || defaultCompiled : defaultCompiled;
            },
            _compileStacking: function (stacking, top, left) {
                var that = this, paddings = {
                        paddingTop: 0,
                        paddingRight: 0,
                        paddingBottom: 0,
                        paddingLeft: 0
                    }, horizontalAlignment = left !== null ? LEFT : RIGHT, origin, position;
                switch (stacking) {
                case 'down':
                    origin = BOTTOM + ' ' + horizontalAlignment;
                    position = TOP + ' ' + horizontalAlignment;
                    delete paddings.paddingBottom;
                    break;
                case RIGHT:
                    origin = TOP + ' ' + RIGHT;
                    position = TOP + ' ' + LEFT;
                    delete paddings.paddingRight;
                    break;
                case LEFT:
                    origin = TOP + ' ' + LEFT;
                    position = TOP + ' ' + RIGHT;
                    delete paddings.paddingLeft;
                    break;
                case UP:
                    origin = TOP + ' ' + horizontalAlignment;
                    position = BOTTOM + ' ' + horizontalAlignment;
                    delete paddings.paddingTop;
                    break;
                default:
                    if (top !== null) {
                        origin = BOTTOM + ' ' + horizontalAlignment;
                        position = TOP + ' ' + horizontalAlignment;
                        delete paddings.paddingBottom;
                    } else {
                        origin = TOP + ' ' + horizontalAlignment;
                        position = BOTTOM + ' ' + horizontalAlignment;
                        delete paddings.paddingTop;
                    }
                    break;
                }
                that._popupOrigin = origin;
                that._popupPosition = position;
                that._popupPaddings = paddings;
            },
            _attachPopupEvents: function (options, popup) {
                var that = this, allowHideAfter = options.allowHideAfter, attachDelay = !isNaN(allowHideAfter) && allowHideAfter > 0, closeIcon;
                function attachClick(target) {
                    target.on(CLICK + NS, function () {
                        that._hidePopup(popup);
                    });
                }
                if (options.hideOnClick) {
                    popup.bind('activate', function () {
                        if (attachDelay) {
                            setTimeout(function () {
                                attachClick(popup.element);
                            }, allowHideAfter);
                        } else {
                            attachClick(popup.element);
                        }
                    });
                } else if (options.button) {
                    closeIcon = popup.element.find(KICLOSE);
                    if (attachDelay) {
                        setTimeout(function () {
                            attachClick(closeIcon);
                        }, allowHideAfter);
                    } else {
                        attachClick(closeIcon);
                    }
                }
            },
            _showPopup: function (wrapper, options) {
                var that = this, autoHideAfter = options.autoHideAfter, x = options.position.left, y = options.position.top, popup, openPopup;
                openPopup = $('.' + that._guid + ':not(.' + KHIDING + ')').last();
                popup = new kendo.ui.Popup(wrapper, {
                    anchor: openPopup[0] ? openPopup : document.body,
                    origin: that._popupOrigin,
                    position: that._popupPosition,
                    animation: options.animation,
                    modal: true,
                    collision: '',
                    isRtl: that._isRtl,
                    close: function () {
                        that._triggerHide(this.element);
                    },
                    deactivate: function (e) {
                        e.sender.element.off(NS);
                        e.sender.element.find(KICLOSE).off(NS);
                        e.sender.destroy();
                    }
                });
                that._attachPopupEvents(options, popup);
                if (openPopup[0]) {
                    popup.open();
                } else {
                    if (x === null) {
                        x = $(window).width() - wrapper.width() - options.position.right;
                    }
                    if (y === null) {
                        y = $(window).height() - wrapper.height() - options.position.bottom;
                    }
                    popup.open(x, y);
                }
                popup.wrapper.addClass(that._guid).css(extend({
                    margin: 0,
                    zIndex: 10050
                }, that._popupPaddings));
                if (options.position.pinned) {
                    popup.wrapper.css('position', 'fixed');
                    if (openPopup[0]) {
                        that._togglePin(popup.wrapper, true);
                    }
                } else if (!openPopup[0]) {
                    that._togglePin(popup.wrapper, false);
                }
                if (autoHideAfter > 0) {
                    setTimeout(function () {
                        that._hidePopup(popup);
                    }, autoHideAfter);
                }
            },
            _hidePopup: function (popup) {
                popup.wrapper.addClass(KHIDING);
                popup.close();
            },
            _togglePin: function (wrapper, pin) {
                var win = $(window), sign = pin ? -1 : 1;
                wrapper.css({
                    top: parseInt(wrapper.css(TOP), 10) + sign * win.scrollTop(),
                    left: parseInt(wrapper.css(LEFT), 10) + sign * win.scrollLeft()
                });
            },
            _attachStaticEvents: function (options, wrapper) {
                var that = this, allowHideAfter = options.allowHideAfter, attachDelay = !isNaN(allowHideAfter) && allowHideAfter > 0;
                function attachClick(target) {
                    target.on(CLICK + NS, proxy(that._hideStatic, that, wrapper));
                }
                if (options.hideOnClick) {
                    if (attachDelay) {
                        setTimeout(function () {
                            attachClick(wrapper);
                        }, allowHideAfter);
                    } else {
                        attachClick(wrapper);
                    }
                } else if (options.button) {
                    if (attachDelay) {
                        setTimeout(function () {
                            attachClick(wrapper.find(KICLOSE));
                        }, allowHideAfter);
                    } else {
                        attachClick(wrapper.find(KICLOSE));
                    }
                }
            },
            _showStatic: function (wrapper, options) {
                var that = this, autoHideAfter = options.autoHideAfter, animation = options.animation, insertionMethod = options.stacking == UP || options.stacking == LEFT ? 'prependTo' : 'appendTo';
                wrapper.addClass(that._guid)[insertionMethod](options.appendTo).hide().kendoAnimate(animation.open || false);
                that._attachStaticEvents(options, wrapper);
                if (autoHideAfter > 0) {
                    setTimeout(function () {
                        that._hideStatic(wrapper);
                    }, autoHideAfter);
                }
            },
            _hideStatic: function (wrapper) {
                wrapper.kendoAnimate(extend(this.options.animation.close || false, {
                    complete: function () {
                        wrapper.off(NS).find(KICLOSE).off(NS);
                        wrapper.remove();
                    }
                }));
                this._triggerHide(wrapper);
            },
            _triggerHide: function (element) {
                this.trigger(HIDE, { element: element });
                this.angular('cleanup', function () {
                    return { elements: element };
                });
            },
            show: function (content, type, safe) {
                var that = this, options = that.options, wrapper = $(WRAPPER), args, defaultArgs;
                if (!type) {
                    type = INFO;
                }
                if (content !== null && content !== undefined && content !== '') {
                    if (kendo.isFunction(content)) {
                        content = content();
                    }
                    defaultArgs = {
                        typeIcon: type,
                        content: ''
                    };
                    if ($.isPlainObject(content)) {
                        args = extend(defaultArgs, content);
                    } else {
                        args = extend(defaultArgs, { content: content });
                    }
                    wrapper.addClass(KNOTIFICATION + '-' + type).toggleClass(KNOTIFICATION + '-button', options.button).attr('data-role', 'alert').css({
                        width: options.width,
                        height: options.height
                    }).append(that._getCompiled(type, safe)(args));
                    that.angular('compile', function () {
                        return {
                            elements: wrapper,
                            data: [{ dataItem: args }]
                        };
                    });
                    if ($(options.appendTo)[0]) {
                        that._showStatic(wrapper, options);
                    } else {
                        that._showPopup(wrapper, options);
                    }
                    that.trigger(SHOW, { element: wrapper });
                }
                return that;
            },
            showText: function (content, type) {
                this.show(content, type, true);
            },
            info: function (content) {
                return this.show(content, INFO);
            },
            success: function (content) {
                return this.show(content, SUCCESS);
            },
            warning: function (content) {
                return this.show(content, WARNING);
            },
            error: function (content) {
                return this.show(content, ERROR);
            },
            hide: function () {
                var that = this, openedNotifications = that.getNotifications();
                if (that.options.appendTo) {
                    openedNotifications.each(function (idx, element) {
                        that._hideStatic($(element));
                    });
                } else {
                    openedNotifications.each(function (idx, element) {
                        var popup = $(element).data('kendoPopup');
                        if (popup) {
                            that._hidePopup(popup);
                        }
                    });
                }
                return that;
            },
            getNotifications: function () {
                var that = this, guidElements = $('.' + that._guid + ':not(.' + KHIDING + ')');
                if (that.options.appendTo) {
                    return guidElements;
                } else {
                    return guidElements.children('.' + KNOTIFICATION);
                }
            },
            setOptions: function (newOptions) {
                var that = this, options;
                Widget.fn.setOptions.call(that, newOptions);
                options = that.options;
                if (newOptions.templates !== undefined) {
                    that._compileTemplates(options.templates);
                }
                if (newOptions.stacking !== undefined || newOptions.position !== undefined) {
                    that._compileStacking(options.stacking, options.position.top, options.position.left);
                }
            },
            destroy: function () {
                Widget.fn.destroy.call(this);
                this.getNotifications().off(NS).find(KICLOSE).off(NS);
            }
        });
        kendo.ui.plugin(Notification);
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));