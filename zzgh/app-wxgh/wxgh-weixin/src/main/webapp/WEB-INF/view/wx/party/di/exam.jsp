<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/18
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <title>【${e.name}】考试</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body {
            background-color: #fff;
        }

        .exam-title {
            color: #777;
            font-size: 16px;
            padding: 8px 10px;
            position: relative;
        }

        .exam-title:not(.no):after {
            position: absolute;
            right: 0;
            bottom: 0;
            left: 5px;
            height: 1px;
            content: '';
            -webkit-transform: scaleY(.5);
            transform: scaleY(.5);
            background-color: #c8c7cc;
        }

        .exam-content .mui-table-view-cell {
            padding-left: 20px;
            font-size: 15px;
        }

        .exam-content .exam-title {
            color: #080808;
            padding-top: 10px;
        }

        .mui-table-view-cell.mui-selected {
            color: #007aff;
        }

        .ui-fixed-bottom.no {
            padding: 0;
        }

        .ui-fixed-bottom.ui-flex.no button {
            border: none;
            margin: 0;
            border-radius: 0;
        }

        #questionAnsers .mui-table-view-cell > a:not(.mui-btn) {
            white-space: normal;
        }
    </style>
</head>

<body>
<div class="mui-scroll-wrapper" style="bottom: 60px;">
    <div class="mui-scroll">
        <div>
            <div class="exam-title">
                <span style="display: inline-block;margin-right: 40px;" class="mui-ellipsis-2">【${e.name}】考试</span>
                <span style="position: absolute;right: 8px;top:8px;" class="mui-pull-right">
							<span style="color: #007aff;"
                                  class="fa fa-heart"></span> <span id="curNumb">1</span>/<span>${e.total}</span>
						</span>
            </div>
            <div class="exam-content">
                <div id="questionName" class="exam-title no">
                </div>
                <ul class="mui-table-view no" id="questionAnsers">
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="ui-fixed-bottom ui-flex no">
    <button id="prevBtn" class="mui-btn mui-btn-grey mui-disabled">上一题</button>
    <button id="nextBtn" class="mui-btn mui-btn-blue">下一题</button>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    var examId = '${e.id}';
    var total = '${e.total}';
    $(function () {
        mui('.mui-scroll-wrapper').scroll({
            deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
        });

        var ZM = ['A', 'B', 'C', 'D', 'E', 'F', 'G']
        var TYPE = ['单选', '多选']

        var $body = $('body')

        var $questionName = $('#questionName')
        $body.data('numb', 1)
        var $answers = $('#questionAnsers')
        var $prev = $('#prevBtn')
        var $next = $('#nextBtn')
        var $curNumb = $('#curNumb')

        $prev.on('tap', function () {
            var numb = $body.data('numb')
            if (numb > 1) {
                numb = Number(numb) - 1
                $body.data('numb', numb)

                get_question(numb)
            } else {
                mui.toast('没有上一题啦~')
            }
            console.log(numb)
        })

        $next.on('tap', function () {
            var numb = $body.data('numb')
            if (numb < total) {
                numb = Number(numb) + 1
                $body.data('numb', numb)

                //添加记录
                add_record(function () {
                    get_question(numb)
                })
            } else {
                mui.toast('没有下一题啦~')
                add_record(function () {
                    ui.showToast('答题结束~', function () {
                        mui.openWindow('exam_result.html?id=' + examId)
                    })
                }, true)
            }
        })

        var load = ui.loading('加载中...')

        get_question()

        /**
         * 请求获取考题
         * @param numb
         */
        function get_question(numb) {
            load.show()
            if (!numb) numb = $body.data('numb')
            wxgh.request.post('/wx/party/di/api/question_list.json', {id: examId, numb: numb}, function (d) {
                var name = d.orderNumb + '、【' + (TYPE[Number(d.type) - 1]) + '】' + d.name
                $questionName.text(name)
                $curNumb.text(d.orderNumb)
                var answers = d.answers

                $body.data('questionId', d.id)

                $answers.empty()
                if (d.type == 1) {
                    $answers.removeClass('ui-checkbox')
                    $answers.addClass('mui-table-view-radio')
                } else {
                    $answers.removeClass('mui-table-view-radio')
                    $answers.addClass('ui-checkbox')
                }
                var answerId = d.answerId
                for (var i in answers) {
                    var $item = $('<li class="mui-table-view-cell">' +
                        '<a class="mui-navigate-right">' + (ZM[Number(answers[i].orderNumb)] + '、' + answers[i].name) + '</a> </li>')
                    $item.data('data', answers[i])
                    if (answers[i].id == answerId) {
                        $item.addClass('mui-selected')
                    }
                    if (d.type == 2) {
                        $item.on('tap', function () {
                            $(this).toggleClass('mui-selected')
                        })
                    }
                    $answers.append($item)
                }

                if (d.orderNumb == 1) {
                    $prev.addClass('mui-disabled')
                } else {
                    $prev.removeClass('mui-disabled')
                }
            })
        }

        function add_record(func, last) {
            var $li = $answers.find('.mui-table-view-cell.mui-selected')
            if ($li.length <= 0) {
                mui.toast('请选择你的答案哦~')
                return
            }
            var answerId = []
            $.each($li, function () {
                answerId.push($(this).data('data').id)
            })
            var questionId = $body.data('questionId')

            var info = {
                answerId: answerId.toString(),
                questionId: questionId,
                examId: examId
            }
            if (last == true) info['last'] = last

            wxgh.request.post('/wx/party/di/api/add_record.json', info, function () {
                if (func) func()
            })
        }
    })
</script>
</body>

</html>
