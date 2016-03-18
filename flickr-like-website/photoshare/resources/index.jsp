<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>

<%@ page import="java.util.List" %>


<%@ page import="photoshare.Album" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Photo Sharing</title></head>

<body>
<h1>Photo Share!!</h1>
<%
    NewUserDao currentUser = new NewUserDao();
    String username = request.getUserPrincipal().getName();
    int userid = currentUser.getUserIdByEmail(username);
%>
Hello <b><code name="username"><%= username  %></code></b>, click here to
<a href="/photoshare/logout.jsp">log out</a>

<div class="search_bars">
    <ul>
        <li sytle="display:inline">
            <form action="searchBarUser.jsp" method="post">
                <div class="bar"><input name="user_name_input" type="text" placeholder="input user's name"/></div>
                <div class="btn"><input name="search_btn" type="submit" value="search user" /></div>
            </form>
        </li>        &nbsp&nbsp&nbsp
        <li sytle="display:inline">
            <form action="searchBarTags.jsp" method="post">
                <div class="bar"><input name="tag_input" type="text" placeholder="input tags"/></div>
                <div class="btn"><input name="search_btn" type="submit" value="search tags" /></div>
            </form>
        </li>
    </ul>
</div>

<h2>Create a new album</h2>
<div class="add_album">
  <input type="submit" value="new album" class="new_btn"/><br/>
</div>


<form action="albumToDB.jsp" method="post">
  <div class="album_set">
    Album Name:<input type="text" name="album_name" class="album_name"/><br/>
    <input type="submit" value="create" class="album_create"/><br/>
  </div>
</form>



<h2>Existing Albums</h2>
<table>
    <tr>
        <%
            AlbumDao albumDao = new AlbumDao();
            List<Album> albums = albumDao.getAllAlbums(userid);
            for (Album album : albums) {
        %>
        <td><a href="/photoshare/album.jsp?albumid=<%= album.getId() %>">
            <p><%=album.getName()%></p>
        </a>
        </td>
        <%
            }
        %>
    </tr>
</table>

</body>
</html>
