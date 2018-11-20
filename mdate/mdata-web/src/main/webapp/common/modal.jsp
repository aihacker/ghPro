<%--
  Created by IntelliJ IDEA.
  User: WangXiaoGang
  Date: 2018/6/7 0007
  Time: 15:41
--%>
<!-- 模态框（Modal） -->
<!--tabindex属性:将tabIndex赋值为-1，则在使用[Tab]键时，此元素被忽略。注意：如果使用-1值时，onfocus与onblur事件仍被启动。   tabIndex的值可为0至32767之间的任意数字-->
<!--role属性:role的发挥的作用是供有障碍的人士使用，这个元素所扮演的角色，主要是供残疾人使用。使用role可以增强文本的可读性和语义化。，-->
<!--aria-label属性:正常情况下，form表单的input组件都有对应的label.当input组件获取到焦点时，屏幕阅读器会读出相应的label里的文本。-->
<!--aria-labelledby属性:当想要的标签文本已在其他元素中存在时，可以使用aria-labelledby，并将其值为所有读取的元素的id。。-->
<!--aria-hidden="true":隐藏要弹出的内容,这个是用于屏幕阅读器的，帮助残障人士更好的访问网站。-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content " style="width:800px;margin: 100px -100px ">
            <div class="modal-header" style="height: 25px">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div  style="verstical-align: middle; text-align: center;">
                <div class="table-responsive">
                    <%--<table id="myTable" class="tablesorter">--%>
                    <table id="myTable" class="table table-bordered tablesorter">
                        <thead>
                        <tr width="100%" bgcolor="#E6E6FA" style="height:30px;">

                            <th>序号</th>
                            <th>供应商</th>
                            <th>合同编号</th>
                            <th>合同名称</th>
                            <th>合同类型</th>
                            <th>合同金额</th>
                            <th>经办部门</th>
                            <th>采购方式</th>
                            <th>定稿时间</th>
                        </tr>
                        </thead>
                        <tbody id="message" ></tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" onclick="onCheck()" data-dismiss="modal" id="submit">提交</button>--%>
                <button type="button" class="btn btn-default" data-dismiss="modal" >返回</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
