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
    <form class="col-lg-4 col-md-4 col-lg-offset-1 col-md-offset-1">
        <div class="form-group">
            <label>问卷名称</label>
            <input id="judgeName" class="form-control" type="text"/>
        </div>

        <div class="form-group">
            <%--<label>面向对象：</label>
            <select name="type" class="ui-select">
                <option value="1" class="op-1">全体</option>
                <option value="2" class="op-2">特定群</option>
                <option value="3" class="op-3">支部评述</option>
                <option value="4" class="op-3">分党委</option>
            </select>--%>
            <div id="partyDept" style="display: none">
            <select name="dept" id="dept" class="form-control">
                <option value="0">全部</option>
                <c:forEach items="${depts}" var="d">
                    <option value="${d.deptid}">${d.deptname}</option>
                </c:forEach>
            </select>
            </div>
            <div id="partyBranch" style="display: none">
                <select name="branch" id="branch" class="form-control">
                    <c:forEach items="${branch}" var="b">
                        <option value="${b.deptid}">${b.deptname}</option>
                    </c:forEach>
                </select>
            </div>
            <div id="group" style="display: none">
                <c:forEach items="${gList}" var="item">
                    <label class="checkbox-inline">
                        <input name="groupids" type="checkbox" value="${item.value}">${item.name}
                    </label>
                </c:forEach>
            </div>
        </div>

        <div class="form-group">
            <label>简&nbsp;&nbsp;介</label>
            <textarea name="" id="judgeInfo" class="form-control" rows="3" maxlength="200"></textarea>
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
            <div class="ui-exame">
                <div class="ui-exame-title">
                    <label>考试题目：</label>
                    <select class="form-control" id="exameType">
                        <option value="1">单选</option>
                        <%--<option value="2">多选</option>--%>
                    </select>
                    <a id="addExamBtn" class="btn btn-link">添加试题</a>
                    <a id="addExamQ" class="btn btn-link">添加问答</a>
                </div>
            </div>
        </div>

        <div>
            <button id="subBtn" type="button" class="btn btn-theme">确认发布</button>
        </div>
    </form>
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
                <%--<input type="{{=it.type}}" name="exame{{=it.num}}">&nbsp;--%>
                <span class="ui-answer-name hidden">{{=it.typeName}}1</span>&nbsp;
                <input type="text" class="form-control ui-answer-name-input" value="{{=it.typeName}}1"/>
            </label>
            <label class="ui-answer-item">
                <%--<input type="{{=it.type}}" name="exame{{=it.num}}">&nbsp;--%>
                <span class="ui-answer-name hidden">{{=it.typeName}}2</span>&nbsp;
                <input type="text" class="form-control ui-answer-name-input" value="{{=it.typeName}}2"/>
            </label>
            <a data-num="{{=it.num}}" class="btn btn-link ui-add-answer">添加选项...</a>
        </div>
    </div>
</script>

<script type="text/template" id="exameQItem">
    <div class="ui-exame-item">
        <div title="收取" data-toggle="collapse" data-target="#exame{{=it.num}}" class="ui-exame-name">
            <label>{{=it.num}}.&nbsp;</label>
            <span class="ui-name hidden">问答名称</span>
            <input type="text" class="form-control ui-name-input" value="问答名称">
            <span class="fa fa-caret-down"></span>
            <a class="btn btn-link ui-exame-del">删除</a>
        </div>
    </div>
</script>

<script type="text/template" id="answerItem">
    <label class="ui-answer-item">
        <%--<input type="{{=it.type}}" name="exame{{=it.num}}">&nbsp;--%>
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
        var $form = $('form')
        $form.form()

        var $exam = $('.ui-exame')
        $exam.data('num', 1)
        var $examType = $('#exameType')
        var $exameQItem = $('#exameQItem');
        var tpl = doT.template($('#exameItem').html())
        var tplQ = doT.template($('#exameQItem').html())
        var answerTpl = doT.template($('#answerItem').html())

        // 添加试题
        $('#addExamBtn').click(function () {
            create_item()
        })

        // 添加问答
        $('#addExamQ').click(function () {
            create_question()
        })


        function submit() {
            var name = $form.find('#judgeName').val().trim()
            var info = $form.find("#judgeInfo").val().trim()
            var type = 1;//$('select[name=type]').val();
            var dept = null;
            var branch = null;
            var infos = $form.serializeJson();
            var groupId = "1"


            if(infos['type'] == 2){
                if(!infos['groupids']){
                    ui.alert("请选择群");
                    return;
                }else
                    infos['groupIds'] = infos['groupids'].toString();
                    groupId = infos['groupIds']
            }
            if(infos['type'] == 3){
                branch = $('select[name=branch]').val();
                debugger
                if(branch == 0){
                    ui.alert("请选择支部");
                    return;
                } else {
                    groupId = branch;
                }
            }
            if(infos['type'] == 4){
                dept = $('select[name=dept]').val();
                debugger
                if(dept == 0){
                    ui.alert("请选择分党委");
                    return;
                } else {
                    groupId = dept;
                }
            }
            var data = {
                name: name,
                info: info,
                questions: exams,
                type:type,
                groupId:groupId
            }
            var json_data = JSON.stringify(data)

            console.log(data);
            console.log(json_data);

            var url = 'party/judge/api/add.json';

            var push = $form.find('input[name=push]:checked').val();
            ui.post(url, {json: json_data, push: push}, function (data) {
                ui.alert(data)
                setInterval(function () {
                    window.location.href = '';
                }, 3000);
            });
        }

        var exams;
        $('#subBtn').click(function () {

            var name = $form.find('#judgeName').val().trim()
            var info = $form.find("#judgeInfo").val().trim()

            if (!name) {
                ui.alert('请输入问卷名称！！！')
                return
            }
            exams = get_exam();
            for (i in exams) {
                var f = false;
                var question = exams[i]['answers']
//                for (j in question) {
//                    if (question[j]['isAnswer'] == 1)
//                        f = true;
//                }
//                if (f === false) {
//                    ui.alert('题目' + (Number(i) + 1) + '请选择一个答案！！！')
//                    return;
//                }
            }
            if (!exams || exams.length == 0) {
                ui.alert('请输入选择一个题目！！！')
                return;
            }
            if (!info) {
                ui.alert('请输入问卷介绍！！！')
                return
            }

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
            debugger
            init_event($examItm)
            $exam.data('num', Number(nm) + 1)
        }

        function create_question(){
            var type = 3;
            var nm = $exam.data('num')
            var info = {
                num: nm,
                typeInt: type,
                type: type == 1 ? 'radio' : 'checkbox',
                typeName: type == 1 ? '单选' : '多选'
            }
            var $exameQItem = $(tplQ(info))
            $exameQItem.data('data', info)
            $exam.append($exameQItem)
            init_eventQ($exameQItem)
            $exam.data('num', Number(nm) + 1)
        }

        function init_eventQ($item) {
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

        $("select[name=type]").change(function () {
            var i = $(this).val()
            if(i == 2){
                $("#group").show()
            }else{
                $("#group").hide()
            }
            if(i == 3){
                $("#partyBranch").show();
            } else {
                $("#partyBranch").hide();
            }
            if(i == 4){
                $("#partyDept").show();
            } else {
                $("#partyDept").hide();
            }
        })

    })
</script>


