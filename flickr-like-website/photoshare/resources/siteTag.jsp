<%--

--%>
<%@ page import="java.util.List" %>
<%@ page import="photoshare.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>show tag search result in whole site</title>
</head>
<body>
<%
    String tag = request.getParameter("tag");
%>
<h1>Pictures in whole site with tag: <%= tag%></h1><br>
<table>
    <tr>
        <%
            TagDao tagDao = new TagDao();
            List<Integer> pictureIds = tagDao.getAllPicByTag(tag);
            for (Integer pictureId : pictureIds) {

        %>
        <td><a href="/photoshare/picture.jsp?picture_id=<%= pictureId%>" />
            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
            </a>
        </td>

        <%
            }
        %>

    </tr>
</table>

</body>
</html>