<%--

--%>
<%@ page import="java.util.List" %>
<%@ page import="photoshare.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Friend List</title>
</head>
<body>
<%
    NewUserDao currentUser = new NewUserDao();
    String username = request.getUserPrincipal().getName();
    int userid = currentUser.getUserIdByEmail(username);

%>
<h1>Here are your friends!</h1><br>
<table>
    <tr>
        <%
            String friendName = "";
            NewUserDao newUserDao = new NewUserDao();
            List<Integer> friendList = newUserDao.getFriendList(userid);
            for (Integer friendId :friendList) {
                friendName = newUserDao.getEmailById(friendId);

        %>
        <td><a href="browseUserSite.jsp?user_name=<%= friendName%>" />
            <h3><b><%= friendName%></b></h3>
            </a>
        </td>

        <%
            }
        %>

    </tr>
</table>

</body>
</html>