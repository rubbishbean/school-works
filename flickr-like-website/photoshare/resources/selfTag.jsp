<%--

--%>
<%@ page import="java.util.List" %>
<%@ page import="photoshare.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>show tag search result</title>
</head>
<body>
<%
    String tag = request.getParameter("tag");
    NewUserDao currentUser = new NewUserDao();
    String username = "";
    int userid = 1;
    if(request.getUserPrincipal() != null){
        username = request.getUserPrincipal().getName();
        userid = currentUser.getUserIdByEmail(username);
    }else{

    }

%>
  <h1>Pictures in your albums with tag: <%= tag%></h1><br>
  <p><a href="siteTag.jsp?tag=<%= tag%>">see pictures in whole site with tag <%= tag%></a></p>
  <table>
    <tr>
      <%
          TagDao tagDao = new TagDao();
          List<Integer> pictureIds = tagDao.getUserPicByTag(tag,userid);
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