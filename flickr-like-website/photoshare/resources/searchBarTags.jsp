<%--

--%>
<%@ page import="java.util.List" %>
<%@ page import="photoshare.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>tag search result</title>
</head>
<body>
<%
    String inputTags = request.getParameter("tag_input");

%>
<h1>Pictures found with tags: <%= inputTags%></h1><br>
<table>
    <tr>
        <%
           TagDao tagDao = new TagDao();
            List<Integer> picResults = tagDao.searchTags(inputTags);
            for (Integer picId : picResults) {

        %>
        <td><a href="/photoshare/picture.jsp?picture_id=<%= picId%>" />
            <img src="/photoshare/img?t=1&picture_id=<%= picId %>"/>
            </a>
        </td>

        <%
            }
        %>

    </tr>
</table>

</body>
</html>