<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/24
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/30
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${notice.title}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>



<div class="ui-html">
    <p style="font-size: 20px;font-family: '微软雅黑';color: black;    padding: 10px 10px 3px 10px;margin: 0px;">${notice.title}</p>
    ${notice.content}
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
</body>

</html>
