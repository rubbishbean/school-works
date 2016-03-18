<%--

--%>
<%@ page import="java.util.List" %>
<%@ page import="photoshare.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Make friends!</title>
</head>
<body>
<%
    NewUserDao currentUser = new NewUserDao();
    String username = request.getUserPrincipal().getName();
    int userid = currentUser.getUserIdByEmail(username);

%>
<h1>Find more friends here</h1><br>
<table>
    <tr>
        <%
            NewUserDao newUserDao = new NewUserDao();
            List<String> availableFriends = newUserDao.getAvailabeFriends(userid);
            for (String aFriend : availableFriends) {

        %>
        <td><a href="browseUserSite.jsp?user_name=<%= aFriend%>" />
            <h3><b><%= aFriend%></b></h3>
            <form action="searchBarTags.jsp" method="post">
                <div class="bar"><input name="add_friend_name" type="hidden" value="<%=aFriend%>"/></div>
                <div class="btn"><input name="add_friend" type="submit" value="add" /></div>
            </form>
            </a>
        </td>

        <%
            }
        %>

    </tr>
</table>

</body>
</html>