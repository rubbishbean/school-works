<%--
--%>
<%@ page import="photoshare.AlbumDao"  %>
<%@ page import="photoshare.NewUserDao"  %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new album</title>
</head>
<body>
<%
    String err= "";
    String albumName = request.getParameter("album_name");
    String username = request.getUserPrincipal().getName();
    if(albumName != null){
        AlbumDao albumDao = new AlbumDao();
        NewUserDao currentUser = new NewUserDao();
        int ownerId = currentUser.getUserIdByEmail(username);
        boolean created = albumDao.createAlbum(albumName,ownerId);
        if(!created){
            err = "Failed to create new album :( ";
        }
    }


%>
<jsp:forward page="/" />
</body>
</html>