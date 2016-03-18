<%--

--%>
<%@ page import="java.util.List" %>
<%@ page import="photoshare.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user search result</title>
</head>
<body>
<%
    String inputName = request.getParameter("user_name_input");

%>
<h1>Users found contain name: <%= inputName%></h1><br>
<table>
    <tr>
        <%
            NewUserDao newUserDao = new NewUserDao();
            List<String> userResults = newUserDao.searchUsers(inputName);
            for (String user : userResults) {

        %>
        <td><a href="browseUserSite.jsp?user_name=<%= user%>" />
            <h3><b><%= user%></b></h3>
            </a>
        </td>

        <%
            }
        %>

    </tr>
</table>

</body>
</html>