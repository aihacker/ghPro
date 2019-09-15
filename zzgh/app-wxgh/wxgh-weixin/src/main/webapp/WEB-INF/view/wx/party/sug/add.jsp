<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/10
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>提出建议</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body {
            background-color: #fff;
        }

        .ui-textarea-div {
            position: relative;
        }

        .ui-textarea-div .ui-btn-group {
            position: absolute;
            bottom: -10px;
        }

        .ui-btn-group .fa {
            font-size: 18px;
            color: #777;
            margin: 0 5px;
        }

        .ui-btn-group .ui-txt {
            font-size: 13px;
            margin: 0;
            position: relative;
            top: -2px;
        }

        div[contenteditable] {
            padding: 5px 0 15px 0;
            font-size: 16px;
            color: #777;
            min-height: 120px;
            -webkit-user-select: text;
        }

        .ui-textarea-div .line {
            position: absolute;
            height: 1px;
            width: 94%;
            background-color: #ddd;
            bottom: 20px;
            left: 3%;
        }

        .ui-inline-block {
            display: inline-block;
        }
    </style>
</head>
<body>

<div class="mui-content">
    <div class="ui-textarea-div">
        <div id="addContent" contenteditable="true">
            <div class="ui-tip">你想说点什么呢...</div>
        </div>
        <div class="line"></div>
        <small>0 / 200</small>
       <%-- <div id="addUserBtn" class="ui-btn-group">
            <span class="fa fa-at"></span><span class="ui-txt">他</span>
        </div>--%>
    </div>
    <div class="ui-fixed-bottom">
        <button id="addBtn" type="button" class="mui-btn mui-btn-primary ui-btn mui-btn-block">立即发送</button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>
<script type="text/javascript">
    $(function () {
        wxgh.wxInit('${weixin}')

        var $cnt = $('#addContent')
        var $tip = $cnt.find('.ui-tip').clone()
        var $small = $('.ui-textarea-div small')
        $cnt.on('focus', function () {
            $cnt.find('.ui-tip').remove()
        })
        $cnt.keyup(function () {
            var $self = $(this)
            var val = $self.text()
            if (!val) {
                $cnt.empty()
                $cnt.append($tip)
            } else {
                $cnt.find('.ui-tip').remove()
            }
            console.log(val)
            if (val.length > 200) {
                val = val.substring(0, 200)
                $self.text(val)
            }
            $small.text(val.length + ' / ' + 200)
        })

       /* $('#addUserBtn').on('tap', function () {
            wxgh.wxContactOpen('${wx_contact}', function (all, users) {
                if (all) {
                    alert('不能@全部人哦！')
                }
                $cnt.find('.ui-tip').remove()

                if (users && users.length > 0) {
                    for (var i in users) {
                        var id = users[i].id
                        if ($cnt.find('a[data-id=' + id + ']').length > 0) {
                            continue
                        }
                        var $a = $('<a data-id="' + id + '">@' + users[i].name + '</a>')
                        $a.css('tabindex', -1)
                        $cnt.append($a)
                        $cnt.append(' ')
                    }
                    $cnt.append('<div class="ui-inline-block">&nbsp;</div>')
                }
                $cnt.focus()
                toEnd()
            })*/
            //{tagIds: [1, 2, 3], type: 'tag'} })


        $('#addBtn').on('tap', function () {
            var cnt = $cnt.html().replace($tip.prop('outerHTML'), '')
            if (!cnt) {
                alert('请输入内容哦！')
                return;
            }

            /*var userids = []
            $.each($cnt.find('a[data-id]'), function () {
                userids.push($(this).data('id'))
            })*/

            /*if (userids.length <= 0) {
                alert('必须@领导哦！')
                return
            }*/
            var url = homePath + '/wx/party/sug/add1.json'
            var info = {
//                action: 'add',
                content: cnt
                /*users: userids.toString()*/
            }
            mui.post(url, info, function (res) {
                if (res.ok) {
                    alert('发送成功！')
                    wx.closeWindow()
                } else {
                    alert('发送失败！')
                }
            }, 'json')

        })

        function toEnd() {
            var textbox = $cnt[0];
            var sel = window.getSelection();
            var range = document.createRange();
            range.selectNodeContents(textbox);
            range.collapse(false);
            sel.removeAllRanges();
            sel.addRange(range);
        }
    })
</script>
</body>
</html>
