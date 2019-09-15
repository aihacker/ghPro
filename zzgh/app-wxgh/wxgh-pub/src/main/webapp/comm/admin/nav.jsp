<%@ page import="java.util.List" %>
<%@ page import="liuhe.sys.util.nav.Nav" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="liuhe.sys.util.nav.NavItem" %>
<%@ page import="liuhe.sys.entity.Admin" %>
<%@ page import="liuhe.web.tribe.admin.session.AdminSession" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/16
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Nav> navs = new ArrayList<>();

    Admin admin = AdminSession.getToken();

    //第一列
    Nav nav1 = new Nav("用户管理", "fa-user-o");
    List<NavItem> item1 = new ArrayList<>();
    item1.add(new NavItem("加入审核", "tribe/admin/user/verify.html?status=0"));
    item1.add(new NavItem("用户列表", "tribe/admin/user/manager.html?status=1"));
    nav1.setItems(item1);
    navs.add(nav1);

    //第二列
    Nav nav2 = new Nav("往期回顾", "fa-star-o");
    List<NavItem> item2 = new ArrayList<>();
    item2.add(new NavItem("推送成果", "tribe/admin/actresult/notice.html"));
    item2.add(new NavItem("往期列表", "tribe/admin/act/result.html"));
    nav2.setItems(item2);
    navs.add(nav2);

    //第三列
    Nav nav3 = new Nav("积分管理", "fa-database");
    List<NavItem> item3 = new ArrayList<>();
    item3.add(new NavItem("积分结算", "tribe/admin/point/count_point.html"));
    nav3.setItems(item3);
    navs.add(nav3);

    //第四列
    Nav nav4 = new Nav("活动管理", "fa-gift");
    List<NavItem> item4 = new ArrayList<>();
    if (admin != null && admin.getAdminType() == 4) {
        item4.add(new NavItem("活动审核", "tribe/admin/act/shehe.html"));
    }
    item4.add(new NavItem("活动预告", "tribe/admin/act/index.html?status=1"));
    item4.add(new NavItem("正在众筹", "tribe/admin/act/index.html?status=2"));
    item4.add(new NavItem("历史活动", "tribe/admin/act/index.html?status=3"));
    item4.add(new NavItem("活动发布", "tribe/admin/act/add.html"));
    nav4.setItems(item4);
    navs.add(nav4);

    if (admin != null && admin.getAdminType() == 4) {
        Nav nav5 = new Nav("新闻管理", "fa-newspaper-o");
        List<NavItem> item5 = new ArrayList<>();
        item5.add(new NavItem("新闻推送", "tribe/news/send.html"));
        item5.add(new NavItem("新闻列表", "tribe/news/list.html"));
        nav5.setItems(item5);
        navs.add(nav5);
    }


    int i = 0;
%>

<div class="col-lg-2 col-md-2 ui-nav">
    <h3 style="margin-left: 30px;">青年部落</h3>

    <div class="panel-group" id="navLeft" role="tablist" aria-multiselectable="true">

        <% for (Nav nav : navs) {%>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a class="ui-a-title" role="button" data-toggle="collapse" data-parent="#navLeft"
                       href="#collapse<%=i%>"
                       aria-expanded="true">
                        <span class="fa <%=nav.getIcon()%>"></span>
                        <%=nav.getName()%>
                        <span class="fa fa-angle-down"></span>
                    </a>
                </h4>
            </div>
            <div id="collapse<%=i%>" class="panel-collapse collapse" role="tabpanel">
                <div class="panel-body">
                    <ul class="list-group ui-list-group">
                        <%for (NavItem item : nav.getItems()) {%>
                        <li class="list-group-item">
                            <a target="indexIframe" href="${home}/<%=item.getUrl()%>"><%=item.getName()%>
                            </a>
                        </li>
                        <%}%>
                    </ul>
                </div>
            </div>
        </div>
        <%i++;%>
        <%}%>
    </div>
</div>
