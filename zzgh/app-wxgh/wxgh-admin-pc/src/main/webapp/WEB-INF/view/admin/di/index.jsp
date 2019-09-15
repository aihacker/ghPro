<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/26
  Time: 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
<style>
    .ui-exame-name {
        cursor: pointer;
        padding: 4px 0;
    }

    .ui-exame-name > label {
    }

    .ui-exame-name > span {
        font-weight: 600;
    }

    .ui-exame-name > input {
        width: 80%;
        display: inline-block;
        height: 30px;
        padding: 2px 10px;
    }

    .ui-exame-body > label {
        display: block;
        font-weight: 500;
    }

    .ui-exame-name > .fa {
        margin-left: 10px;
    }

    .ui-exame-body {
        padding-left: 15px;
    }

    .ui-exame-body > label > input[type=text] {
        width: 80%;
        display: inline-block;
        height: 30px;
        padding: 2px 10px;
    }

    .ui-exame-body > label > .fa {
        font-size: 16px;
        color: #777;
    }

    .ui-exame-body > label a.btn {
        padding: 2px 12px;
    }

    .ui-exame-body > label > .fa:hover,
    .ui-exame-body > label > .fa:active {
        opacity: .6;
    }

    #exameType {
        width: auto;
        display: inline-block;
        height: 28px;
        padding: 0 12px;
    }
</style>
<div class="row">
    <form class="col-lg-4 col-md-4 col-lg-offset-4 col-md-offset-4">
        <div class="form-group">
            <label>学习计划</label>
            <select id="plan" class="form-control">
                <option value="-1">请选择</option>
                <c:forEach items="${plans}" var="g">
                    <option value="${g.value}">${g.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>考试名称</label>
            <input id="exameName" class="form-control" type="text"/>
        </div>
        <div class="form-group">
            <label>简&nbsp;&nbsp;介</label>
            <textarea name="" id="exameInfo" class="form-control" rows="3" maxlength="200"></textarea>
            <small class="pull-right"></small>
        </div>

        <div class="form-group">
            <label>是否推送</label>
            <div>
                <label class="checkbox-inline">
                    <input type="radio" checked value="1" name="push"> 推送
                </label>
                <label class="checkbox-inline">
                    <input type="radio" value="0" name="push"> 不推送
                </label>
            </div>
        </div>

        <div class="form-group">
            <label>面向群体</label>
            <select id="groupId" class="form-control">
                <option value="-1">请选择</option>
                <c:forEach items="${groups}" var="g">
                    <option value="${g.value}">${g.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>截止时间</label>
            <input id="endTime" type="text" class="form-control form_datetime" readonly>
        </div>

        <div class="form-group">
            <label>学习内容</label>
            <script type="text/plain" id="myEditor" style="height:250px;"></script>
        </div>

        <div class="form-group">
            <label>封&nbsp;&nbsp;面</label>
            <div class="ui-file-div" id="converFileDiv">
                <div id="uimage">选择图片</div>
                <div id="ilist">

                </div>
            </div>
        </div>
        <div class="form-group">
            <label>附件上传</label>
            <div class="ui-file-div" id="mfileDiv">
                <div id="ufile">选择文件</div>

            </div>

        </div>

        <div class="form-group">
            <div id="flist">

            </div>
        </div>

        <hr/>
        <div class="form-group">
            <div class="ui-exame">
                <div class="ui-exame-title">
                    <label>考试题目：</label>
                    <select class="form-control" id="exameType">
                        <option value="1">单选</option>
                        <option value="2">多选</option>
                    </select>
                    <a id="addExamBtn" class="btn btn-link">添加试题</a>
                </div>
            </div>
        </div>

        <div>
            <button id="subBtn" type="button" class="btn btn-theme">确认发布</button>
        </div>
    </form>
</div>
</div>

<script type="text/template" id="exameItem">
    <div class="ui-exame-item">

        <div title="收取" data-toggle="collapse" data-target="#exame{{=it.num}}" class="ui-exame-name">
            <label>{{=it.num}}.&nbsp;</label>
            <span class="ui-name hidden">题目名称</span>
            <input type="text" class="form-control ui-name-input" value="题目名称">
            <span class="fa fa-caret-down"></span>
            <a class="btn btn-link ui-exame-del">删除</a>
        </div>

        <div class="ui-exame-body collapse in" id="exame{{=it.num}}">
            <label class="ui-answer-item">
                <input type="{{=it.type}}" name="exame{{=it.num}}">&nbsp;
                <span class="ui-answer-name hidden">{{=it.typeName}}1</span>&nbsp;
                <input type="text" class="form-control ui-answer-name-input" value="{{=it.typeName}}1"/>
            </label>
            <label class="ui-answer-item">
                <input type="{{=it.type}}" name="exame{{=it.num}}">&nbsp;
                <span class="ui-answer-name hidden">{{=it.typeName}}2</span>&nbsp;
                <input type="text" class="form-control ui-answer-name-input" value="{{=it.typeName}}2"/>
            </label>
            <a data-num="{{=it.num}}" class="btn btn-link ui-add-answer">添加选项...</a>
        </div>
    </div>
</script>
<script type="text/template" id="answerItem">
    <label class="ui-answer-item">
        <input type="{{=it.type}}" name="exame{{=it.num}}">&nbsp;
        <span class="hidden ui-answer-name">{{=it.typeName}}答案</span>&nbsp;
        <input type="text" class="form-control ui-answer-name-input" value="{{=it.typeName}}答案"/>&nbsp;
        <span title="删除" class="fa fa-close"></span>
    </label>
</script>

<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<jsp:include page="/comm/admin/upload.jsp"/>
<jsp:include page="/comm/admin/umeditor.jsp"/>

<script>
    $(function () {

        var um = UM.getEditor('myEditor');

        $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});

        var $form = $('form')
        $form.form()

        var $exam = $('.ui-exame')
        $exam.data('num', 1)
        var $examType = $('#exameType')
        var tpl = doT.template($('#exameItem').html())
        var answerTpl = doT.template($('#answerItem').html())

        // 添加试题
        $('#addExamBtn').click(function () {
            create_item()
        })

        var $converFileDiv = $("#converFileDiv")
        var $converImgInput = $('#converImg')
        $converImgInput.on('change', function () {
            $converFileDiv.find('.ui-file-item:not(:first)').remove()
            var file = this.files[0]
            var reader = new FileReader()
            reader.onload = function () {
                create_file_item(this.result, $converFileDiv, $converImgInput[0])
            }
            reader.readAsDataURL(file)
        })

        function create_file_item(img, $div, self) {
            var $item = $('<div class="ui-file-item">' +
                '<img src="' + img + '"/><span class="fa fa-close"></span></div>')
            if (!self) $item.find('span').hide()
            $div.append($item)

            $item.on('click', '.fa-close', function () {
                if (self) {
                    ui.clearFile(self)
                }
                $(this).parent().remove()
            })
        }

        var $mfileInput = $('#fileInput')
        var $mfileDiv = $('#mfileDiv')
        $mfileInput.on('change', function () {
            $mfileDiv.find('.ui-file-item:not(:first)').remove()
            var files = this.files[0]
            for (var i in files) {
                create_file_item(ui.get_file(files[i].name), $mfileDiv)
            }
        })


        // 图片
        var upload = homePath + '/admin/di/upload.json';
        var cover_id;
        var files;

        // 上传文件回调
        var ufile = up.uploadFile(upload, 9, 'flist', 'ufile', function (ret) {
            files = ret;
            submit();
        }, function () {

        }, null, 100, 100);

        // 上传封面回调
        var uimage = up.upload(upload, 1, 'ilist', 'uimage', function (ret) {
            console.log(ret);
            cover_id = ret;
            up.getFileNum() > 0 ? ufile.upload() : submit();
        }, function () {

        }, null, 100, 100);

        function submit() {
            var name = $form.find('#exameName').val().trim()
            var info = $form.find("#exameInfo").val().trim()
            var endTime = $form.find("#endTime").val();
            var groupId = $('#groupId').val();
            var planId = $form.find('#plan').val();

            var cnt = encodeURIComponent(um.getContent())
            var data = {
                name: name,
                info: info,
                coverId: cover_id,
                endTime: endTime,
                files: files,
                questions: exams,
                content: cnt,
                groupId: groupId,
                planId: planId
            }
            var json_data = JSON.stringify(data)

            console.log(data);
            console.log(json_data);
            var url = '/admin/di/add.json';

            var push = $form.find('input[name=push]').val();
            ui.post(url, {json: json_data, push: push}, function (data) {
                console.log(data);
                ui.alert(data)
                setInterval(function () {
                    window.location.href = '';
                }, 3000);
            }, function () {

            });
        }


        var exams;
        $('#subBtn').click(function () {

            var name = $form.find('#exameName').val().trim()
            var info = $form.find("#exameInfo").val().trim()
            var endTime = $form.find("#endTime").val();
            var planId = $form.find('#plan').val();

            if (planId == -1) {
                ui.alert('请选择学习计划！')
                return;
            }

            if (!name) {
                ui.alert('请输入考试名称！！！')
                return
            }
            exams = get_exam();
            for (i in exams) {
                var f = false;
                var question = exams[i]['answers']
                for (j in question) {
                    if (question[j]['isAnswer'] == 1)
                        f = true;
                }
                if (f === false) {
                    ui.alert('题目' + (Number(i) + 1) + '请选择一个答案！！！')
                    return;
                }
            }
            if (!exams) {
                ui.alert('请输入选择一个答案！！！')
                return;
            }
            if (!info) {
                ui.alert('请输入考试介绍！！！')
                return
            }

            var groupId = $('#groupId').val()
            if (groupId == -1) {
                ui.alert('请选择面向群体')
                return
            }

            if (!endTime) {
                ui.alert('请输入结束时间！！！')
                return;
            }

            if (!up.getImageNum()) {
                ui.alert('请选择封面上传！！！')
                return;
            }

            if (!um.hasContents()) {
                ui.alert('请编辑学习内容！！！')
                return;
            }


            if (up.getImageNum() > 0)
                uimage.upload();
            else if (up.getFileNum() > 0)
                ufile.upload();
            else
                submit();
            return;
        })

        function get_exam() {
            var exams = []
            $.each($exam.find('.ui-exame-item'), function () {
                var $self = $(this)
                var data = $self.data('data')
                //题目名称
                var name = $.trim($self.find('.ui-exame-name .ui-name').text())

                var exa = {
                    type: data.typeInt,
                    name: name,
                    orderNumb: data.num
                }
                //获取选项
                var answers = []
                $.each($self.find('.ui-exame-body .ui-answer-item'), function () {
                    var $ansItm = $(this)
                    var name = $.trim($ansItm.find('.ui-answer-name-input').val())
                    var num = $ansItm.index()
                    var isAnser = $ansItm.find('input[name]').is(':checked') ? 1 : 0
                    answers.push({
                        name: name,
                        orderNumb: num,
                        isAnswer: isAnser
                    })
                })
                exa['answers'] = answers
                exams.push(exa)
            })
            return exams;
        }

        function create_item() {
            var type = $examType.val()
            var nm = $exam.data('num')
            var info = {
                num: nm,
                typeInt: type,
                type: type == 1 ? 'radio' : 'checkbox',
                typeName: type == 1 ? '单选' : '多选'
            }
            var $examItm = $(tpl(info))
            $examItm.data('data', info)
            $exam.append($examItm)
            init_event($examItm)
            $exam.data('num', Number(nm) + 1)
        }

        function init_event($item) {
            $item.on('show.bs.collapse', function () {
                console.log('show')
                var $self = $(this)
                $self.attr('title', '收起')
                $self.find('span.fa').eq(0).removeClass('fa-caret-right').addClass('fa-caret-down')
            }).on('hide.bs.collapse', function () {
                var $self = $(this)
                $self.attr('title', '展开')
                $self.find('span.fa').eq(0).removeClass('fa-caret-down').addClass('fa-caret-right')
            })

            //添加选项
            $item.on('click', '.ui-add-answer', function () {
                var num = $(this).data('num')
                var $answer = $(answerTpl($item.data('data')))
                $answer.insertBefore($(this))
                init_answer_item($answer)
            })

            //题目名称
            $item.on('click', '.ui-name', function (e) {
                e.stopPropagation()
                var $self = $(this)
                $self.addClass('hidden')
                $self.next().removeClass('hidden')
            })
            $item.on('click', '.ui-name-input', function (e) {
                e.stopPropagation()
            })
            $item.on('blur', '.ui-name-input', function () {
                var $self = $(this)
                $self.prev().removeClass('hidden')
                $self.addClass('hidden')
            })

            $item.on('keyup', '.ui-name-input', function () {
                var $self = $(this)
                $self.prev().text($self.val())
            })

            //删除
            $item.on('click', '.ui-exame-del', function () {
                var nm = $exam.data('num')
                $exam.data('num', Number(nm) - 1)
                $item.remove()
            })

            init_answer_item($(this).find('.ui-exame-body'))
        }

        function init_answer_item($answer) {
            $answer.on('click', '.fa-close', function (e) {
                e.stopPropagation()
                $(this).parent().remove()
            })

            $answer.on('keyup', '.ui-answer-name-input', function () {
                var $self = $(this)
                $self.prev().text($self.val())
            })

            //选项名称点击事件
            $answer.on('click', '.ui-answer-name', function () {
                var $self = $(this)
                $self.addClass('hidden')
                $self.next().removeClass('hidden')
            })

            $answer.on('blur', '.ui-answer-name-input', function () {
                var $self = $(this)
                $self.prev().removeClass('hidden')
                $self.addClass('hidden')
            })
        }


    })
</script>
</body>
</html>
